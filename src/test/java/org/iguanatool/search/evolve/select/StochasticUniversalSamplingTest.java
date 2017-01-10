package org.iguanatool.search.evolve.select;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.iguanatool.search.evolve.rank.LinearRanking;
import org.iguanatool.search.objective.NumericalMaximizingObjectiveValue;
import org.iguanatool.search.randomnumbergenerator.NonRandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;

import java.util.Vector;

/**
 * User: phil
 * Date: 20-Feb-2006
 * Time: 20:58:29
 */
public class StochasticUniversalSamplingTest extends TestCase {

    NumericalSolutionType nvs;

    public void setUp() {
        double[] min = {0, 0, 0, 0, 0};
        double[] max = {5, 5, 5, 5, 5};
        int[] precision = {0, 0, 0, 0, 0};
        nvs = new NumericalSolutionType(5, min, max, precision);
    }

    public void testSUS1() {
        Vector<Solution> pool = new Vector<Solution>();

        Solution i1 = new NumericalSolution(nvs);
        i1.setObjectiveValue(new NumericalMaximizingObjectiveValue(2, 0));

        Solution i2 = new NumericalSolution(nvs);
        i2.setObjectiveValue(new NumericalMaximizingObjectiveValue(43, 0));

        Solution i3 = new NumericalSolution(nvs);
        i3.setObjectiveValue(new NumericalMaximizingObjectiveValue(24, 0));

        Solution i4 = new NumericalSolution(nvs);
        i4.setObjectiveValue(new NumericalMaximizingObjectiveValue(10, 0));

        Solution i5 = new NumericalSolution(nvs);
        i5.setObjectiveValue(new NumericalMaximizingObjectiveValue(15, 0));

        Solution i6 = new NumericalSolution(nvs);
        i6.setObjectiveValue(new NumericalMaximizingObjectiveValue(31, 0));

        pool.add(i1);
        pool.add(i2);
        pool.add(i3);
        pool.add(i4);
        pool.add(i5);
        pool.add(i6);

        double[] nos = {0.12};

        StochasticUniversalSampling s = new StochasticUniversalSampling(
                new NonRandomNumberGenerator(nos),
                new LinearRanking(1.7));

        //           0, 1,  2,  3,  4,  5
        // order is: 2, 10, 15, 24, 31, 43
        // objectiveValue is: 0.3, 0.58, 0.86, 1.14, 1.42, 1.7
        // proportionate is: 0.05, 0.09, 0.143, 0.189, 0.236, 0.283
        // sum is: 0.05, 0.14, 0.283, 0.472, 0.708, 1
        // to select: 4
        // pointer space is: 0.25
        // pointers: 0.12, 0.37, 0.62, 0.87
        // selected: 10, 24, 31, 43

        Vector<Solution> selected = s.select(pool, 4);
        assertEquals("selected 1", 10,
                ((NumericalMaximizingObjectiveValue)selected.elementAt(0).getObjectiveValue()).getNumericalValue(), 0.1);

        assertEquals("selected 2", 24,
                ((NumericalMaximizingObjectiveValue)selected.elementAt(1).getObjectiveValue()).getNumericalValue(), 0.1);

        assertEquals("selected 3", 31,
                ((NumericalMaximizingObjectiveValue)selected.elementAt(2).getObjectiveValue()).getNumericalValue(), 0.1);

        assertEquals("selected 4", 43,
                ((NumericalMaximizingObjectiveValue)selected.elementAt(3).getObjectiveValue()).getNumericalValue(), 0.1);
    }

    public void testSUS2() {
        Vector<Solution> pool = new Vector<Solution>();

        Solution i1 = new NumericalSolution(nvs);
        i1.setObjectiveValue(new NumericalMaximizingObjectiveValue(2, 0));

        Solution i2 = new NumericalSolution(nvs);
        i2.setObjectiveValue(new NumericalMaximizingObjectiveValue(43, 0));

        Solution i3 = new NumericalSolution(nvs);
        i3.setObjectiveValue(new NumericalMaximizingObjectiveValue(24, 0));

        pool.add(i1);
        pool.add(i2);
        pool.add(i3);

        double[] nos = {0.12};

        StochasticUniversalSampling s = new StochasticUniversalSampling(
                new NonRandomNumberGenerator(nos),
                new LinearRanking(2.0));

        //           0, 1,  2
        // order is: 2, 24, 43
        // objectiveValue is: 0, 1, 2
        // proportionate is: 0, 0.33, 0.66
        // sum is: 0, 0.33, 1
        // to select: 6
        // pointer space is: 0.16667
        // pointers: 0.12, 0.28667, 0.45334, 0.62001, 0.78668, 0.95
        // selected (order): 1, 1, 2, 2, 2, 2
        // selected: 24, 24, 43, 43, 43, 43

        Vector<Solution> selected = s.select(pool, 6);

        assertEquals("selected 1", 24,
                ((NumericalMaximizingObjectiveValue)selected.elementAt(0).getObjectiveValue()).getNumericalValue(), 0.1);

        assertEquals("selected 2", 24,
                ((NumericalMaximizingObjectiveValue)selected.elementAt(1).getObjectiveValue()).getNumericalValue(), 0.1);

        assertEquals("selected 3", 43,
                ((NumericalMaximizingObjectiveValue)selected.elementAt(2).getObjectiveValue()).getNumericalValue(), 0.1);

        assertEquals("selected 4", 43,
                ((NumericalMaximizingObjectiveValue)selected.elementAt(3).getObjectiveValue()).getNumericalValue(), 0.1);

        assertEquals("selected 5", 43,
                 ((NumericalMaximizingObjectiveValue)selected.elementAt(4).getObjectiveValue()).getNumericalValue(), 0.1);

        assertEquals("selected 6", 43,
                 ((NumericalMaximizingObjectiveValue)selected.elementAt(5).getObjectiveValue()).getNumericalValue(), 0.1);


    }

    public static Test suite() {
        return new TestSuite(StochasticUniversalSamplingTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
