package org.iguanatool.testobject;

import org.iguanatool.testobject.cparser.*;
import org.iguanatool.testobject.structure.CFGNode;
import org.iguanatool.testobject.structure.condition.AtomicCondition;

import java.util.*;

public class Instrumenter {

    public static String instrument(ASTParseUnit parseUnit, String functionName) {
        return new FunctionInstrumenter(parseUnit, functionName).content.toString();
    }

    private static class FunctionInstrumenter extends ContentExtractor {

        private String functionName;

        FunctionInstrumenter(ASTParseUnit parseUnit, String functionName) {
            this.functionName = functionName;
            parseUnit.childrenAccept(this);
        }

        public void visit(ASTFunctionDefinition functionDefinition) {
            if (functionName.equals(functionDefinition.getFunctionName())) {
                content.append(new BranchingNodeInstrumenter(functionDefinition).content);
            } else {
                functionDefinition.childrenAccept(this);
            }
        }

        private static class BranchingNodeInstrumenter extends ContentExtractor {

            private static final String NODE_INSTRUMENTATION_FUNCTION = "node";
            private static final String BOOLEAN_CONDITION_INSTRUMENTATION_FUNCTION = "is_true";
            private static final String EARLY_EXIT_CALL = "early_exit();";
            private static final String SWITCH_TEMPORARY_VARIABLE = "__ins_switch_temp";
            private static Map<String, String> instrumentationFunctions = new TreeMap<String, String>();

            static {
                instrumentationFunctions.put("==", "equals");
                instrumentationFunctions.put("!=", "not_equals");
                instrumentationFunctions.put("<", "less_than");
                instrumentationFunctions.put(">", "greater_than");
                instrumentationFunctions.put(">=", "greater_than_or_equal");
                instrumentationFunctions.put("<=", "less_than_or_equal");
            }

            private Deque<SimpleNode> branchingNodes;

            BranchingNodeInstrumenter(SimpleNode SimpleNode) {
                branchingNodes = new ArrayDeque<SimpleNode>();
                SimpleNode.childrenAccept(this);
            }

            private static String instrumentRelationalExpression(String operator, int conditionID, String lhs, String rhs) {
                return " " + instrumentationFunctions.get(operator) + "(" + conditionID + ", " + lhs + ", " + rhs + ") ";
            }

            private static String instrumentBooleanExpression(int conditionID, String expression) {
                return " " + BOOLEAN_CONDITION_INSTRUMENTATION_FUNCTION + "(" + conditionID + ", " + expression + ") ";
            }

            private void startNodeInstrumentation(int branchingNodeID) {
                content.append(NODE_INSTRUMENTATION_FUNCTION);
                content.append("(");
                content.append(branchingNodeID);
                content.append(", ");
            }

            private void endNodeInstrumentation() {
                content.append(")");
            }

            public void visit(ASTBranchingExpression branchingExpression) {
                SimpleNode lastBranchingNode = branchingNodes.pop();
                startNodeInstrumentation(lastBranchingNode.getCFGNode().getID());
                content.append(new ConditionInstrumenter(branchingExpression).content);
                endNodeInstrumentation();
            }

            public void visit(ASTDoStatement doStatement) {
                branchingNodes.push(doStatement);
                super.visit(doStatement);
            }

            public void visit(ASTExpressionStatement expressionStatement) {
                if (expressionStatement.isExit()) {
                    content.append(EARLY_EXIT_CALL);
                } else {
                    super.visit(expressionStatement);
                }
            }

            public void visit(ASTIfStatement ifStatement) {
                branchingNodes.push(ifStatement);
                super.visit(ifStatement);
            }

            public void visit(ASTForStatement forStatement) {
                branchingNodes.push(forStatement);
                super.visit(forStatement);
            }

            public void visit(ASTWhileStatement whileStatement) {
                branchingNodes.push(whileStatement);
                super.visit(whileStatement);
            }

            public void visit(ASTSwitchStatement switchStatement) {
                String switchExpression = new VisitorAdapter() {
                    private String content;

                    public String getContents(ASTSwitchStatement switchStatment) {
                        switchStatment.childrenAccept(this);
                        return content;
                    }

                    public void visit(ASTSwitchExpression switchExpression) {
                        content = switchExpression.getContent();
                    }
                }.getContents(switchStatement);

                List<ASTCaseStatement> caseStatements = new RecursiveVisitorAdapter() {
                    List<ASTCaseStatement> caseStatements = new ArrayList<ASTCaseStatement>();

                    List<ASTCaseStatement> getCaseStatements(ASTSwitchStatement switchStatement) {
                        switchStatement.childrenAccept(this);
                        return caseStatements;
                    }

                    public void visit(ASTCaseStatement caseStatement) {
                        caseStatements.add(caseStatement);
                    }

                    public void visit(ASTSwitchStatement switchStatement) {
                    }
                }.getCaseStatements(switchStatement);

                content.append(SWITCH_TEMPORARY_VARIABLE + " = " + switchExpression + ";");

                boolean first = true;
                for (ASTCaseStatement caseStatement : caseStatements) {
                    ASTCaseExpression caseExpression = new VisitorAdapter() {
                        ASTCaseExpression caseExpression;

                        ASTCaseExpression getCaseExpression(ASTCaseStatement caseStatement) {
                            caseStatement.childrenAccept(this);
                            return caseExpression;
                        }

                        public void visit(ASTSwitchStatement switchStatement) {
                        }

                        public void visit(ASTCaseStatement caseStatement) {
                        }

                        public void visit(ASTCaseExpression caseExpression) {
                            this.caseExpression = caseExpression;
                        }
                    }.getCaseExpression(caseStatement);


                    if (first) first = false;
                    else content.append(" else");

                    CFGNode cfgNode = caseStatement.getCFGNode();
                    int nodeID = cfgNode.getID();
                    int conditionID = ((AtomicCondition) cfgNode.getCondition()).getID();

                    content.append(" if (");
                    startNodeInstrumentation(nodeID);

                    content.append(instrumentRelationalExpression(
                            "==", conditionID,
                            SWITCH_TEMPORARY_VARIABLE,
                            caseExpression.getCode()));

                    endNodeInstrumentation();
                    content.append(") {}");
                }

                super.visit(switchStatement);
            }

            public void visit(ASTSwitchExpression switchExpression) {
                content.append(SWITCH_TEMPORARY_VARIABLE);
            }

            private class ConditionInstrumenter extends ContentExtractor {

                ConditionInstrumenter(SimpleNode node) {
                    node.childrenAccept(this);
                }

                public void visit(ASTRelationalExpression relationalExpression) {

                    class RelationalOperatorFinder extends VisitorAdapter {
                        String operator;

                        RelationalOperatorFinder(ASTRelationalExpression relationalExpression) {
                            relationalExpression.childrenAccept(this);
                        }

                        public void visit(ASTRelationalOperator relationalOperator) {
                            operator = relationalOperator.getCode();
                        }
                    }

                    String operator = new RelationalOperatorFinder(relationalExpression).operator;
                    int conditionID = ((AtomicCondition) relationalExpression.getCondition()).getID();

                    if (operator != null) {
                        String lhs = relationalExpression.getLHS().getCode();
                        String rhs = relationalExpression.getRHS().getCode();
                        content.append(instrumentRelationalExpression(operator, conditionID, lhs, rhs));

                    } else {
                        String expression = relationalExpression.getCode();
                        content.append(instrumentBooleanExpression(conditionID, expression));
                    }
                }
            }
        }
    }
}