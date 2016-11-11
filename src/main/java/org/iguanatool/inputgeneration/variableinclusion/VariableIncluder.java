package org.iguanatool.inputgeneration.variableinclusion;

import org.iguanatool.testobject.TestObject;

/**
 * User: phil
 * Date: 08-Feb-2007
 * Time: 23:40:12
 */
public interface VariableIncluder {

    public boolean[] performAnalysis(String aimDescription,
                                     TestObject testObject);
}
