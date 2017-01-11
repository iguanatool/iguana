package org.iguanatool.inputgeneration.aim;

import org.iguanatool.inputgeneration.aim.log.AimLog;
import org.iguanatool.inputgeneration.variableinclusion.VariableIncluder;
import org.iguanatool.search.Search;
import org.iguanatool.testobject.TestObject;
import org.iguanatool.testobject.structure.CFGNode;

public class AimFactory {

    private static final String BRANCH_COVERAGE = "branchcoverage";
    private static final String BRANCH = "branch";
    private static final String TRUE_BRANCH_SUFFIX = "T";
    private static final String FALSE_BRANCH_SUFFIX = "F";

    public static Aim instantiateAim(TestObject testObject,
                                     VariableIncluder variableIncluder,
                                     Search search,
                                     AimLog aimLog,
                                     String description) {
        // branch coverage
        if (description.equals(BRANCH_COVERAGE)) {
            return new BranchCoverageAim(testObject, variableIncluder, search, aimLog);

            // an individual branch
        } else if (description.substring(0, BRANCH.length()).equals(BRANCH)) {
            String branchID = description.substring(BRANCH.length());

            boolean branch;
            String suffix = branchID.substring(branchID.length() - 1);
            if (suffix.equals(TRUE_BRANCH_SUFFIX)) {
                branch = true;
            } else if (suffix.equals(FALSE_BRANCH_SUFFIX)) {
                branch = false;
            } else {
                throw new AimException("Unrecognised suffix '" + suffix + "' on branch '" + branchID + "'");
            }

            int branchingNode;
            String id = branchID.substring(0, branchID.length() - 1);
            try {
                branchingNode = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                throw new AimException("Unable to parse branch ID '" + id + "' in '" + branchID + "'");
            }

            CFGNode node;
            try {
                node = testObject.getCFGNode(branchingNode);
            } catch (Exception e) {
                throw new AimException("Unrecognised node '" + branchingNode + "' for '" + branchID + "'");
            }

            return new BranchAim(testObject, variableIncluder, search, aimLog, node, branch);

            // unknown aim
        } else {
            throw new AimException("Unknown aim: " + description);
        }
    }

}
