package org.iguanatool.inputgeneration.aim;

import org.iguanatool.inputgeneration.aim.log.AimLog;
import org.iguanatool.inputgeneration.variableinclusion.VariableIncluder;
import org.iguanatool.search.Search;
import org.iguanatool.testobject.TestObject;
import org.iguanatool.testobject.structure.CFGNode;

import java.util.SortedSet;

/**
 * User: phil
 * Date: 09-Sep-2006
 * Time: 10:48:25
 */

public class BranchCoverageAim extends Aim {
   
	public BranchCoverageAim(TestObject testObject,
							 VariableIncluder variableIncluder,
							 Search search,
							 AimLog aimLog) {
		
		super(testObject, variableIncluder, search, aimLog);
	}
	
    public void attemptAim() {
    	SortedSet<CFGNode> branchingNodes = testObject.getCFG().getBranchingNodes();
    	
        for (CFGNode branchingNode: branchingNodes) {
            BranchAim b1 = new BranchAim(testObject, variableIncluder, search, aimLog, branchingNode, true);
            b1.attemptAim();

            BranchAim b2 = new BranchAim(testObject, variableIncluder, search, aimLog, branchingNode, false);
            b2.attemptAim();            
        }
    }    
}
