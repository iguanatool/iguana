package org.iguanatool.search.evolve.mutation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.iguanatool.search.randomnumbergenerator.NonRandomNumberGenerator;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;

/**
 * User: phil
 * Date: 15-Feb-2006
 * Time: 20:25:46
 */
public class UniformMutationTest extends TestCase {

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

    public void testUniformMutation() {
        // mutation at last locus
        double[] doubles = {0.3, 0.3, 0.3, 0.3, 0.1, 3.2};
        RandomNumberGenerator r = new NonRandomNumberGenerator(doubles);
        UniformMutation um = new UniformMutation(r);

        um.mutate(ind);
        assertEquals("Ind gene locus 4", 3.0, ind.getRawElement(4), 0.1);

    }

    public static Test suite() {
        return new TestSuite(UniformMutationTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}