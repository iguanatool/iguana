package org.iguanatool.search.evolve.recombination;

import junit.framework.TestCase;
import org.iguanatool.search.randomnumbergenerator.NonRandomNumberGenerator;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;

/**
 * User: phil
 * Date: 15-Feb-2006
 * Time: 20:10:58
 */
public class UniformCrossoverTest extends TestCase {

    NumericalSolutionType nvs;
    NumericalSolution parent1, parent2, child1, child2;

    public void setUp() {
        double[] min = {0, 0, 0, 0, 0};
        double[] max = {5, 5, 5, 5, 5};
        int[] precision = {0, 0, 0, 0, 0};
        nvs = new NumericalSolutionType(5, min, max, precision);

        parent1 = new NumericalSolution(nvs);
        parent2 = new NumericalSolution(nvs);
        child1 = new NumericalSolution(nvs);
        child2 = new NumericalSolution(nvs);

        double[] genome1 = {1, 2, 3, 4, 5};
        parent1.setElements(genome1);

        double[] genome2 = {6, 7, 8, 9, 10};
        parent2.setElements(genome2);
    }

    public void testUniformCrossover() {
        boolean[] booleans = {true, false, false, true, true};  // x-over points
        RandomNumberGenerator r = new NonRandomNumberGenerator(booleans);
        UniformCrossover upc = new UniformCrossover(r);
        upc.recombine(parent1, parent2, child1, child2);

        assertEquals("Child1 gene locus 0", 1, child1.getRawElement(0), 0.1);
        assertEquals("Child1 gene locus 1", 7, child1.getRawElement(1), 0.1);
        assertEquals("Child1 gene locus 2", 8, child1.getRawElement(2), 0.1);
        assertEquals("Child1 gene locus 3", 4, child1.getRawElement(3), 0.1);
        assertEquals("Child1 gene locus 4", 5, child1.getRawElement(4), 0.1);

        assertEquals("Child2 gene locus 0", 6, child2.getRawElement(0), 0.1);
        assertEquals("Child2 gene locus 1", 2, child2.getRawElement(1), 0.1);
        assertEquals("Child2 gene locus 2", 3, child2.getRawElement(2), 0.1);
        assertEquals("Child2 gene locus 3", 9, child2.getRawElement(3), 0.1);
        assertEquals("Child2 gene locus 4", 10, child2.getRawElement(4), 0.1);
    }

    public static Test suite() {
        return new TestSuite(UniformCrossoverTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}