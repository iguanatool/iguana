package org.iguanatool.search.evolve.rank;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * User: phil
 * Date: 20-Feb-2006
 * Time: 20:13:25
 */

public class RankingTest extends TestCase {

    public void testSum() {
        double fitness[] = {4, 8, 12};
        assertEquals(24, RankingUtil.sum(fitness), 0.1);
    }

    public void testProportionateFitnesses() {
        double fitness[] = {4, 8, 12};
        double[] exp = {0.166, 0.333, 0.5};
        double[] got = RankingUtil.proportion(fitness);

        for (int i=0; i < fitness.length; i++) {
            assertEquals(exp[i], got[i], 0.001);
        }
    }

    protected void assertLinearRanking(double[] expected,
                                       double selectionPressure) {
        double[] fitness = new double[expected.length];
        RankingUtil.linearRanking(fitness, selectionPressure);
        for (int i=0; i < fitness.length; i++) {
            assertEquals("ObjectiveValue "+i+": ",expected[i], fitness[i], 0.001);
        }
    }

    public void testLinearRanking1() {
        double[] expected = {0.900, 0.920, 0.940, 0.960, 0.980,
                             1.00, 1.02, 1.04, 1.06, 1.08, 1.10};
        assertLinearRanking(expected, 1.1);
    }

    public void testLinearRanking2() {
        double[] expected = {0.0, 0.2, 0.4, 0.6, 0.8,
                             1.0, 1.2, 1.4, 1.6, 1.8, 2.0};
        assertLinearRanking(expected, 2.0);
    }

    public static Test suite() {
        return new TestSuite(RankingTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
