package org.iguanatool.inputgeneration.objectivefunction;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * User: phil
 * Date: 18-Feb-2006
 * Time: 11:12:14
 */
public class InputGenerationObjectiveTest extends TestCase {

    public void testOrder() {
        InputGenerationObjectiveValue f1 = new InputGenerationObjectiveValue(0, 0, 1, 10);
        InputGenerationObjectiveValue f2 = new InputGenerationObjectiveValue(0, 0, 1, 12);
        InputGenerationObjectiveValue f3 = new InputGenerationObjectiveValue(0, 0, 1, 10);
        InputGenerationObjectiveValue f4 = new InputGenerationObjectiveValue(1, 0, 1, 10);

        assertEquals(1, f1.compareTo(f2));
        assertEquals(0, f1.compareTo(f3));
        assertEquals(-1, f2.compareTo(f1));
        assertEquals(1, f1.compareTo(f4));

        InputGenerationObjectiveValue f5 = new InputGenerationObjectiveValue(0, 2, 2, 10);
        InputGenerationObjectiveValue f6 = new InputGenerationObjectiveValue(0, 1, 2, 10);
        InputGenerationObjectiveValue f7 = new InputGenerationObjectiveValue(0, 1, 2, 10);
        assertEquals(-1, f5.compareTo(f6));
        assertEquals(1, f6.compareTo(f5));
        assertEquals(0, f7.compareTo(f6));
    }

    public static Test suite() {
        return new TestSuite(InputGenerationObjectiveTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}