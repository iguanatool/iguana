package org.iguanatool.inputgeneration.objectivefunction.target;

import org.iguanatool.inputgeneration.objectivefunction.ComputeNodeDependency;
import org.iguanatool.inputgeneration.objectivefunction.InputGenerationObjectiveFunction;
import org.iguanatool.inputgeneration.objectivefunction.InputGenerationObjectiveValue;
import org.iguanatool.inputgeneration.objectivefunction.condition.ConditionObjectiveFunction;
import org.iguanatool.inputgeneration.objectivefunction.condition.StdConditionObjectiveFunction;
import org.iguanatool.testobject.TestObject;
import org.iguanatool.testobject.structure.CFGNode;
import org.iguanatool.testobject.trace.TraceNode;

import java.util.List;

/**
 * User: phil
 * Date: 13-Jun-2006
 * Time: 14:18:40
 */

public class BranchTargetObjectiveFunction extends InputGenerationObjectiveFunction {

    protected CFGNode branchStartNode;
    protected boolean branch;

    public BranchTargetObjectiveFunction(TestObject t) {
        super(t);
    }
    
    public String getObjectiveDescription() {
    	return "Branch "+branchStartNode+((branch)?"T":"F");
    }
    
    public void setBranch(CFGNode branchStartNode, boolean branch) {
        this.branchStartNode = branchStartNode;
        this.branch = branch;

        if (!branchStartNode.isBranching()) {
            throw new RuntimeException("Branch node "+branchStartNode +" has no condition -- not a branch node?");
        }    	
    }

    public InputGenerationObjectiveValue evaluateTarget(List<TraceNode> trace) {
        InputGenerationObjectiveValue objVal = InputGenerationObjectiveValue.WORST;
    	ComputeNodeDependency cnd = new ComputeNodeDependency();
        ConditionObjectiveFunction cof = new StdConditionObjectiveFunction();

        for (TraceNode traceNode: trace) {
        	InputGenerationObjectiveValue traceNodeObjVal = null;        	
         	
        	if (traceNode.getCFGNode() == branchStartNode) {
        		traceNode.computeOutcome(cof);
        		traceNodeObjVal = cof.getOutcome(branch);
             } else {
                 cnd.compute(branchStartNode, traceNode.getCFGNode());
                 if (cnd.getApproachLevel() != -1) {                	 
                	 traceNode.computeOutcome(cof);
                     traceNodeObjVal = cof.getOutcome(cnd.getEdge());
                     traceNodeObjVal.setApproachLevel(cnd.getApproachLevel());
                 }
             }

            if (traceNodeObjVal != null) {
            	if (objVal == null || objVal.compareTo(traceNodeObjVal) < 0) {
            		objVal = traceNodeObjVal;
            	}   
            
            	if (traceNodeObjVal.isIdeal()) {
            		break;
            	}
            }
        }

        return objVal;
    }

}
