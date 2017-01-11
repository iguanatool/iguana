package org.iguanatool.inputgeneration.variableinclusion;

import org.iguanatool.Config;
import org.iguanatool.testobject.TestObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * User: phil
 * Date: 08-Feb-2007
 * Time: 23:45:46
 */
public class IncludeVariablesFromFile implements VariableIncluder {

    public boolean[] performAnalysis(String aimDescription,
                                     TestObject testObject) {
        try {
            String className = testObject.getClass().toString().substring("class iguana.expt.".length());
            String[] parts = className.split("\\.");
            String dir = Config.getInstance().getIguanaPath() + "/ctestobj/src/" + parts[0] + "/vada/" + parts[1] + "/";
            String file = dir + aimDescription + ".txt";

            Vector<Boolean> includedVarsVector = new Vector<Boolean>();
            BufferedReader r = new BufferedReader(new FileReader(file));
            String line = r.readLine();
            while (line != null) {
                if (line.equals("INCLUDE")) {
                    includedVarsVector.add(true);
                } else if (line.equals("OMIT")) {
                    includedVarsVector.add(false);
                }
                line = r.readLine();
            }
            r.close();

            boolean[] includedVars = new boolean[includedVarsVector.size()];
            for (int i = 0; i < includedVars.length; i++) {
                includedVars[i] = includedVarsVector.get(i);
            }

            return includedVars;
        } catch (IOException e) {
            // to stop expts crashing when no variable inclusion file is found - include none of the vars
            int numVars = testObject.getInputSpecification().getNumArgs();
            boolean[] vars = new boolean[numVars];
            for (int i = 0; i < numVars; i++) {
                vars[i] = false;
            }
            return vars;
        }
    }
}
