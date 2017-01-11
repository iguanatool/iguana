package org.iguanatool.inputgeneration.aim;

import org.iguanatool.inputgeneration.aim.log.AimLog;
import org.iguanatool.inputgeneration.inputvector.InputVectorType;
import org.iguanatool.inputgeneration.objectivefunction.target.BranchTargetObjectiveFunction;
import org.iguanatool.inputgeneration.variableinclusion.VariableIncluder;
import org.iguanatool.search.Search;
import org.iguanatool.search.SearchResult;
import org.iguanatool.testobject.TestObject;
import org.iguanatool.testobject.structure.CFGNode;

public class BranchAim extends Aim {

    private CFGNode branchingNode;
    private boolean branch;

    public BranchAim(TestObject testObject,
                     VariableIncluder variableIncluder,
                     Search search,
                     AimLog aimLog,
                     CFGNode branchingNode,
                     boolean branch) {

        super(testObject, variableIncluder, search, aimLog);
        this.branchingNode = branchingNode;
        this.branch = branch;
    }

    public void attemptAim() {
        String branchName = branchingNode + (branch ? "T" : "F");

        boolean[] variableInclusion = variableIncluder.performAnalysis(branchName, testObject);
        InputVectorType inputVectorType = new InputVectorType(testObject, variableInclusion);

        BranchTargetObjectiveFunction objectiveFunction = new BranchTargetObjectiveFunction(testObject);
        objectiveFunction.setBranch(branchingNode, branch);

        SearchResult searchResult = generateTestData(inputVectorType, objectiveFunction);

        aimLog.logAim(branchName, searchResult);
    }
}
