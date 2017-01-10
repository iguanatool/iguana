package org.iguanatool.inputgeneration.objectivefunction.target;

import org.iguanatool.inputgeneration.objectivefunction.ComputeNodeDependency;
import org.iguanatool.inputgeneration.objectivefunction.InputGenerationObjectiveValue;
import org.iguanatool.inputgeneration.objectivefunction.condition.ConditionObjectiveFunction;
import org.iguanatool.inputgeneration.objectivefunction.condition.StdConditionObjectiveFunction;
import org.iguanatool.testobject.TestObject;
import org.iguanatool.testobject.trace.TraceNode;

import java.util.List;

public class NestingBranchTargetObjectiveFunction extends BranchTargetObjectiveFunction {
	
    public NestingBranchTargetObjectiveFunction(TestObject t) {
    	super(t);
    }
    
    public InputGenerationObjectiveValue evaluateTarget(List<TraceNode> trace) {
        InputGenerationObjectiveValue objVal = new InputGenerationObjectiveValue(0, 0, 0, 0);
    	ComputeNodeDependency cnd = new ComputeNodeDependency();
        ConditionObjectiveFunction cof = new StdConditionObjectiveFunction();
        
        for (TraceNode traceNode: trace) {
        	cnd.compute(branchStartNode, traceNode.getCFGNode());	
            if (cnd.getApproachLevel() != -1) {
                traceNode.computeOutcome(cof);
                
                InputGenerationObjectiveValue traceNodeObjVal;
                
                if (cnd.getApproachLevel() == 0) {
                	traceNodeObjVal = cof.getOutcome(branch);
                } else {
                	traceNodeObjVal = cof.getOutcome(cnd.getEdge());
                }
                
                if (traceNodeObjVal.getDistance() > 0){
                	objVal.setDistance(objVal.getDistance() + 
                					   traceNodeObjVal.getDistance());
                	
                	objVal.setTotalConditions(objVal.getTotalConditions() + 
                			                  traceNodeObjVal.getTotalConditions());
                	
                	objVal.setUnencounteredConditions(objVal.getUnencounteredConditions() + 
                			                          traceNodeObjVal.getUnencounteredConditions());
                }             
                
                
                /*System.out.println(traceNode);
                List<TraceCondition> traceConditions = traceNode.getTraceConditions();
        		for (TraceCondition traceCondition: traceConditions) {
        			System.out.println(traceCondition);
        		}
        		System.out.println("T: "+traceNodeObjVal);
        		System.out.println("O: "+objVal);*/ 
        		
            }
        }	
        
        return objVal;
    }
}
