package iguana.inputgeneration.objectivefunction.target;

import java.util.List;

import iguana.inputgeneration.objectivefunction.ComputeNodeDependency;
import iguana.inputgeneration.objectivefunction.InputGenerationObjectiveValue;
import iguana.inputgeneration.objectivefunction.condition.ConditionObjectiveFunction;
import iguana.inputgeneration.objectivefunction.condition.StdConditionObjectiveFunction;
import iguana.testobject.trace.TraceNode;
import iguana.testobject.TestObject;

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
