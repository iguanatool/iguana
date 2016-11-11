package org.iguanatool.inputgeneration.variableinclusion;

import org.iguanatool.testobject.TestObject;

/**
 * User: phil
 * Date: 08-Feb-2007
 * Time: 23:42:47
 */
public class IncludeAllVariables implements VariableIncluder {

    public boolean[] performAnalysis(String aimDescription,
                                     TestObject testObject) {
        int numVars = testObject.getInputSpecification().getNumArgs();
        boolean[] vars = new boolean[numVars];
        for (int i=0; i < numVars; i++) {
            vars[i] = true;
        }
        return vars;
    }
}
