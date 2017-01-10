package org.iguanatool.search.evolve.mutation;

import org.iguanatool.search.randomnumbergenerator.NonRandomNumberGenerator;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.junit.Assert;

/**
 * User: phil
 * Date: 01-Apr-2006
 * Time: 14:58:27
 */
public class MuhlenbeinMutationTest extends TestCase {

    NumericalSolutionType nvs;
    NumericalSolution ind;

    public void setUp() {
        double[] min = {0, 0, 0, 0, 0};
        double[] max = {5, 5, 5, 5, 5};
        int[] precision = {0, 0, 0, 0, 0};
        nvs = new NumericalSolutionType(5, min, max, precision);

        ind = new NumericalSolution(nvs);

        double[] genome1 = {1, 2, 3, 4, 5};
        ind.setElements(genome1);
    }

    public void testMuhlenbienMutation() {
        // mutation at last locus
        double[] doubles = {0.3, 0.3, 0.3, 0.3, 0.1,
                            0.5, 0.01, 0.01, 0.5, 0.5};
        boolean[] booleans = {true};
        RandomNumberGenerator r = new NonRandomNumberGenerator(booleans, doubles);
        MuhlenbeinMutation mm = new MuhlenbeinMutation(r, 0.1, 4);

        // 2^-1 = 0.5
        // 2^-2 = 0.25
        // 0.1 * 5 = 0.5
        // delta = -0.375
        // gene 5 = 4.625
        mm.mutate(ind);
        Assert.assertEquals("Ind gene locus 4",
                            4.625, ind.getRawElement(4), 0.001);
    }


    public static Test suite() {
        return new TestSuite(MuhlenbeinMutationTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}