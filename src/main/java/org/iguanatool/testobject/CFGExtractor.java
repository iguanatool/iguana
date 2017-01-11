package org.iguanatool.testobject;

import org.iguanatool.testobject.cparser.*;
import org.iguanatool.testobject.structure.CFG;
import org.iguanatool.testobject.structure.CFGNode;
import org.iguanatool.testobject.structure.ConnectedNode;
import org.iguanatool.testobject.structure.condition.*;

import java.util.*;

public class CFGExtractor {

    public static CFG extractCFG(ASTFunctionDefinition functionDefinition) {
        FunctionCFGExtractor extractor = new FunctionCFGExtractor();
        return extractor.extractCFG(functionDefinition);
    }

    public static Map<String, CFG> extractCFGs(ASTParseUnit parseUnit) {
        return extractCFGs(parseUnit, null);
    }

    public static Map<String, CFG> extractCFGs(ASTParseUnit parseUnit, Collection<String> functionNames) {
        class CFGCollator extends RecursiveVisitorAdapter {
            FunctionCFGExtractor extractor = new FunctionCFGExtractor();
            Map<String, CFG> cfgs = new TreeMap<String, CFG>();
            Collection<String> functionNames;

            CFGCollator(ASTParseUnit parseUnit, Collection<String> functionNames) {
                this.functionNames = functionNames;
                parseUnit.childrenAccept(this);
            }

            public void visit(ASTFunctionDefinition functionDefinition) {
                String functionName = functionDefinition.getFunctionName();
                if (functionNames == null || functionNames.contains(functionName)) {
                    CFG cfg = extractor.extractCFG(functionDefinition);
                    cfgs.put(functionName, cfg);
                }
            }
        }
        return new CFGCollator(parseUnit, functionNames).cfgs;
    }

    private static class FunctionCFGExtractor extends RecursiveVisitorAdapter {

        private CFG cfg;
        private Predecessors currentPredecessors;
        private Predecessors exitPredecessors;
        private Map<String, CFGNode> identifiers;
        private Map<String, Set<CFGNode>> gotos;

        public CFG extractCFG(ASTFunctionDefinition functionDefinition) {
            // initialise all instance variables
            cfg = new CFG();
            currentPredecessors = new Predecessors();
            exitPredecessors = new Predecessors();
            identifiers = new TreeMap<String, CFGNode>();
            gotos = new TreeMap<String, Set<CFGNode>>();

            // descend through the AST
            currentPredecessors.add(cfg.getStartNode());
            functionDefinition.childrenAccept(this);
            currentPredecessors.setSuccessor(cfg.getEndNode());

            // add arcs from return statements to end node
            exitPredecessors.setSuccessor(cfg.getEndNode());

            // add arcs from gotos to identifiers
            for (String label : gotos.keySet()) {
                CFGNode identifierCFGNode = identifiers.get(label);
                Set<CFGNode> gotoCFGNodes = gotos.get(label);
                for (CFGNode gotoCFGNode : gotoCFGNodes) {
                    gotoCFGNode.addSuccessor(identifierCFGNode);
                }
            }

            // compute control dependencies
            cfg.computeControlDependencies();
            return cfg;
        }

        private CFGNode createCFGNode() {
            return cfg.createNode();
        }

        private CFGNode createCFGNode(SimpleNode simpleNode) {
            CFGNode cfgNode = createCFGNode();
            simpleNode.setCFGNode(cfgNode);
            cfgNode.setCode(simpleNode.getCode());
            return cfgNode;
        }

        public void visit(ASTBreakStatement breakStatement) {
            CFGNode breakCFGNode = createCFGNode(breakStatement);
            currentPredecessors.setSuccessor(breakCFGNode);
        }

        public void visit(ASTCaseStatement caseStatement) {
            CFGNode caseStatementCFGNode = createCFGNode(caseStatement);
            if (caseStatement.isFirstSwitchClause()) {
                currentPredecessors.setSuccessor(caseStatementCFGNode);
            }
            currentPredecessors.add(caseStatementCFGNode, true);
            caseStatement.childrenAccept(this);
        }

        public void visit(ASTContinueStatement astContinueStatement) {
            CFGNode continueCFGNode = createCFGNode(astContinueStatement);
            currentPredecessors.setSuccessor(continueCFGNode);
        }

        public void visit(ASTDeclaration declaration) {
            CFGNode declarationCFGNode = createCFGNode(declaration);
            currentPredecessors.setSuccessor(declarationCFGNode);
            currentPredecessors.add(declarationCFGNode);
        }

        public void visit(ASTDefaultStatement defaultStatement) {
            CFGNode defaultStatementCFGNode = createCFGNode(defaultStatement);
            if (defaultStatement.isFirstSwitchClause()) {
                currentPredecessors.setSuccessor(defaultStatementCFGNode);
            }
            currentPredecessors.add(defaultStatementCFGNode);
            defaultStatement.childrenAccept(this);
        }

        public void visit(ASTDoStatement doStatement) {
            CFGNode doCFGNode = createCFGNode();
            currentPredecessors.setSuccessor(doCFGNode);
            currentPredecessors.add(doCFGNode);
            doStatement.childrenAccept(this);

            CFGNode whileCFGNode = createCFGNode(doStatement);
            currentPredecessors.setSuccessor(whileCFGNode);
            currentPredecessors.add(whileCFGNode, true);
            currentPredecessors.setSuccessor(doCFGNode);

            currentPredecessors.add(whileCFGNode, false);

            addContinueArcs(doStatement, whileCFGNode);
            addBreaksToPriorNodes(doStatement);

            resolveCondition(doStatement);
        }

        public void visit(ASTExpressionStatement expressionStatement) {
            CFGNode expresssionCFGNode = createCFGNode(expressionStatement);
            currentPredecessors.setSuccessor(expresssionCFGNode);
            if (expressionStatement.isExit()) {
                exitPredecessors.add(expresssionCFGNode);
            } else {
                currentPredecessors.add(expresssionCFGNode);
            }
        }

        public void visit(ASTForStatement forStatement) {
            loopStatement(forStatement);
        }

        public void visit(ASTGotoStatement gotoStatement) {
            CFGNode gotoCFGNode = createCFGNode(gotoStatement);
            currentPredecessors.setSuccessor(gotoCFGNode);

            String label = gotoStatement.getLabel();

            Set<CFGNode> gotoCFGNodes = gotos.get(label);
            if (gotoCFGNodes == null) {
                gotoCFGNodes = new HashSet<CFGNode>();
                gotos.put(label, gotoCFGNodes);
            }
            gotoCFGNodes.add(gotoCFGNode);
        }

        public void visit(ASTIdentifierStatement identifierStatement) {
            CFGNode identifierCFGNode = createCFGNode(identifierStatement);
            currentPredecessors.setSuccessor(identifierCFGNode);
            currentPredecessors.add(identifierCFGNode);

            String label = identifierStatement.getLabel();
            identifiers.put(label, identifierCFGNode);

            SimpleNode node = identifierStatement.getChild(0);
            if (node != null) {
                node.jjtAccept(this);
            }
        }

        public void visit(ASTIfStatement ifStatement) {
            CFGNode ifCFGNode = createCFGNode(ifStatement);
            currentPredecessors.setSuccessor(ifCFGNode);

            // true branch
            currentPredecessors.add(ifCFGNode, true);
            SimpleNode node = ifStatement.getTrueChild();
            node.jjtAccept(this);
            Predecessors trueBranchPredecessors = currentPredecessors;

            // false branch
            currentPredecessors = new Predecessors();
            currentPredecessors.add(ifCFGNode, false);
            node = ifStatement.getFalseChild();
            if (node != null) {
                node.jjtAccept(this);
            }

            // merge prior nodes from false and true branches
            currentPredecessors.merge(trueBranchPredecessors);

            resolveCondition(ifStatement);
        }

        public void visit(ASTReturnStatement returnStatement) {
            CFGNode returnCFGNode = createCFGNode(returnStatement);
            currentPredecessors.setSuccessor(returnCFGNode);
            exitPredecessors.add(returnCFGNode);
        }

        public void visit(ASTSwitchStatement switchStatement) {
            class SwitchClauseVisitor extends RecursiveVisitorAdapter {
                boolean encounteredFirst;

                void markFirst(ASTSwitchStatement switchStatement) {
                    switchStatement.childrenAccept(this);
                }

                public void visit(ASTCaseStatement caseStatement) {
                    if (!encounteredFirst) {
                        caseStatement.setFirstSwitchClause(true);
                        encounteredFirst = true;
                    }
                }

                public void visit(ASTDefaultStatement defaultStatement) {
                    if (!encounteredFirst) {
                        defaultStatement.setFirstSwitchClause(true);
                        encounteredFirst = true;
                    }
                }

                public void visit(ASTSwitchStatement node) {
                }
            }

            class SwitchClauseCFGNodeCollator extends RecursiveVisitorAdapter {
                List<CFGNode> caseCFGNodes = new ArrayList<CFGNode>();
                CFGNode defaultCFGNode;

                SwitchClauseCFGNodeCollator(ASTSwitchStatement switchStatement) {
                    switchStatement.childrenAccept(this);
                }

                public void visit(ASTCaseStatement caseStatement) {
                    caseCFGNodes.add(caseStatement.getCFGNode());
                    caseStatement.childrenAccept(this);
                }

                public void visit(ASTDefaultStatement defaultStatement) {
                    defaultCFGNode = defaultStatement.getCFGNode();
                    defaultStatement.childrenAccept(this);
                }

                public void visit(ASTSwitchStatement node) {
                }
            }

            CFGNode switchCFGNode = createCFGNode(switchStatement);
            currentPredecessors.setSuccessor(switchCFGNode);
            currentPredecessors.add(switchCFGNode);

            new SwitchClauseVisitor().markFirst(switchStatement);
            switchStatement.childrenAccept(this);

            SwitchClauseCFGNodeCollator collator = new SwitchClauseCFGNodeCollator(switchStatement);
            CFGNode lastCaseCFGNode = null;
            for (CFGNode caseCFGNode : collator.caseCFGNodes) {
                caseCFGNode.setCondition(new AtomicCondition());
                if (lastCaseCFGNode != null) {
                    lastCaseCFGNode.addSuccessor(caseCFGNode, false);
                }
                lastCaseCFGNode = caseCFGNode;
            }
            if (collator.defaultCFGNode != null) {
                lastCaseCFGNode.addSuccessor(collator.defaultCFGNode, false);
            }

            addBreaksToPriorNodes(switchStatement);
        }

        public void visit(ASTWhileStatement whileStatement) {
            loopStatement(whileStatement);
        }

        private void addBreaksToPriorNodes(SimpleNode SimpleNode) {
            class BreakCFGNodeCollator extends RecursiveVisitorAdapter {
                Set<CFGNode> breakCFGNodes = new HashSet<CFGNode>();

                BreakCFGNodeCollator(SimpleNode node) {
                    node.childrenAccept(this);
                }

                public void visit(ASTBreakStatement breakStatement) {
                    breakCFGNodes.add(breakStatement.getCFGNode());
                }

                public void visit(ASTWhileStatement node) {
                }

                public void visit(ASTDoStatement node) {
                }

                public void visit(ASTForStatement node) {
                }

                public void visit(ASTSwitchStatement node) {
                }
            }

            currentPredecessors.add(new BreakCFGNodeCollator(SimpleNode).breakCFGNodes);
        }

        private void addContinueArcs(SimpleNode SimpleNode, CFGNode continueToCFGNode) {
            class ContinueCFGNodeCollator extends RecursiveVisitorAdapter {
                Set<CFGNode> continueCFGNodes = new HashSet<CFGNode>();

                ContinueCFGNodeCollator(SimpleNode node) {
                    node.childrenAccept(this);
                }

                public void visit(ASTContinueStatement continueStatement) {
                    continueCFGNodes.add(continueStatement.getCFGNode());
                }

                public void visit(ASTWhileStatement node) {
                }

                public void visit(ASTDoStatement node) {
                }

                public void visit(ASTForStatement node) {
                }

                public void visit(ASTSwitchStatement node) {
                }
            }

            Predecessors continueCFGNodes = new Predecessors();
            continueCFGNodes.add(new ContinueCFGNodeCollator(SimpleNode).continueCFGNodes);
            continueCFGNodes.setSuccessor(continueToCFGNode);
        }

        private void resolveCondition(SimpleNode node) {
            class ConditionExtractor extends RecursiveVisitorAdapter {
                Condition condition;
                int nextConditionID;

                public ConditionExtractor(SimpleNode node) {
                    this(node, 0);
                }

                public ConditionExtractor(SimpleNode node, int nextConditionID) {
                    this.nextConditionID = nextConditionID;
                    node.jjtAccept(this);
                }

                public void visit(ASTRelationalExpression relationalExpression) {
                    condition = new AtomicCondition(nextConditionID);
                    nextConditionID++;

                    // so that the instrumenter can retrieve the condition ID later:
                    relationalExpression.setCondition(condition);
                }

                public void visit(ASTLogicalANDExpression logicalANDExpression) {
                    composedCondition(logicalANDExpression, new AndCondition());
                }

                public void visit(ASTLogicalORExpression logicalORExpression) {
                    composedCondition(logicalORExpression, new OrCondition());
                }

                public void visit(ASTLogicalNOTExpression logicalNOTExpression) {
                    NotCondition notCondition = new NotCondition();
                    SimpleNode child = (SimpleNode) logicalNOTExpression.jjtGetChild(0);
                    ConditionExtractor conditionExtractor = new ConditionExtractor(child, nextConditionID);
                    notCondition.setSubCondition(conditionExtractor.condition);

                    condition = notCondition;
                    nextConditionID = conditionExtractor.nextConditionID;
                }

                private void composedCondition(SimpleNode node, ComposedCondition composedCondition) {
                    int numChildren = node.jjtGetNumChildren();

                    // it's only really an OR or AND if it has more than one child:
                    if (numChildren > 1) {
                        for (int i = 0; i < numChildren; i++) {
                            SimpleNode child = (SimpleNode) node.jjtGetChild(i);
                            ConditionExtractor conditionExtractor = new ConditionExtractor(child, nextConditionID);
                            composedCondition.addCondition(conditionExtractor.condition);
                            nextConditionID = conditionExtractor.nextConditionID;
                        }
                        condition = composedCondition;
                    } else {
                        // not an OR or an AND ... something else:
                        node.childrenAccept(this);
                    }
                }
            }

            ASTBranchingExpression branchingExpression = new RecursiveVisitorAdapter() {
                ASTBranchingExpression branchingExpression;

                ASTBranchingExpression getBranchingExpression(SimpleNode node) {
                    node.childrenAccept(this);
                    return branchingExpression;
                }

                public void visit(ASTBranchingExpression branchingExpression) {
                    this.branchingExpression = branchingExpression;
                }

                public void visit(ASTDoStatement doStatement) {
                }

                public void visit(ASTForStatement forStatement) {
                }

                public void visit(ASTIfStatement ifStatement) {
                }

                public void visit(ASTWhileStatement whileStatement) {
                }

                public void visit(ASTSwitchStatement switchStatement) {
                }
            }.getBranchingExpression(node);

            if (branchingExpression != null) {
                Condition condition = new ConditionExtractor(branchingExpression).condition;
                node.getCFGNode().setCondition(condition);
            }
        }

        private void loopStatement(SimpleNode loopStatement) {
            CFGNode loopHeaderCFGNode = createCFGNode(loopStatement);
            currentPredecessors.setSuccessor(loopHeaderCFGNode);
            currentPredecessors.add(loopHeaderCFGNode, true);

            loopStatement.childrenAccept(this);
            currentPredecessors.setSuccessor(loopHeaderCFGNode);
            currentPredecessors.add(loopHeaderCFGNode, false);

            addContinueArcs(loopStatement, loopHeaderCFGNode);
            addBreaksToPriorNodes(loopStatement);

            resolveCondition(loopStatement);
        }

        private class Predecessors {
            private Set<ConnectedNode> predecessors;

            Predecessors() {
                predecessors = new TreeSet<ConnectedNode>();
            }

            void add(CFGNode cfgNode) {
                predecessors.add(new ConnectedNode(cfgNode));
            }

            void add(Set<CFGNode> cfgNodes) {
                for (CFGNode cfgNode : cfgNodes) {
                    add(cfgNode);
                }
            }

            void add(CFGNode cfgNode, boolean edge) {
                predecessors.add(new ConnectedNode(cfgNode, edge));
            }

            void merge(Predecessors toMerge) {
                for (ConnectedNode predecessor : toMerge.predecessors) {
                    this.predecessors.add(predecessor);
                }
            }

            void setSuccessor(CFGNode successor) {
                for (ConnectedNode predecessor : predecessors) {
                    predecessor.node.addSuccessor(successor, predecessor.edge);
                }
                predecessors.clear();
            }

            public String toString() {
                StringBuilder s = new StringBuilder();
                for (ConnectedNode predecessor : predecessors) {
                    s.append(predecessor);
                }
                return s.toString();
            }
        }
    }
}