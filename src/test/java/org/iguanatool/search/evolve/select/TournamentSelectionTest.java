package org.iguanatool.search.evolve.select;

import org.iguanatool.search.objective.NumericalMaximizingObjectiveValue;
import org.iguanatool.search.randomnumbergenerator.NonRandomNumberGenerator;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;

import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * User: phil
 * Date: 15-Feb-2006
 * Time: 20:35:03
 */
public class TournamentSelectionTest extends TestCase {

    NumericalSolutionType nvs;
    NumericalSolution competitor1, competitor2, competitor3;
    Vector<Solution> pool = new Vector<Solution>();

    public void setUp() {
        double[] min = {0, 0, 0, 0, 0};
        double[] max = {5, 5, 5, 5, 5};
        int[] precision = {0, 0, 0, 0, 0};
        nvs = new NumericalSolutionType(5, min, max, precision);

        competitor1 = new NumericalSolution(nvs);
        competitor2 = new NumericalSolution(nvs);
        competitor3 = new NumericalSolution(nvs);

        NumericalMaximizingObjectiveValue f1 = new NumericalMaximizingObjectiveValue(20);
        NumericalMaximizingObjectiveValue f2 = new NumericalMaximizingObjectiveValue(20);
        NumericalMaximizingObjectiveValue f3 = new NumericalMaximizingObjectiveValue(20);
        f1.setValue(10);
        f2.setValue(15);
        f3.setValue(5);

        competitor1.setObjectiveValue(f1);
        competitor2.setObjectiveValue(f2);
        competitor3.setObjectiveValue(f3);

        pool.add(competitor1);
        pool.add(competitor2);
        pool.add(competitor3);
    }

    public void testTournament() {
        int[] integers = {0, 2};
        RandomNumberGenerator r = new NonRandomNumberGenerator(integers);
        TournamentSelection ts = new TournamentSelection(r, 2);
        Solution ind = ts.doTournament(pool);
        assertEquals("Tourny1 winner", 10, ((NumericalMaximizingObjectiveValue) ind.getObjectiveValue()).getNumericalValue(), 0.1);

        int[] integers2 = {1, 2};
        r = new NonRandomNumberGenerator(integers2);
        TournamentSelection ts2 = new TournamentSelection(r, 2);
        ind = ts2.doTournament(pool);
        assertEquals("Tourny2 winner", 15, ((NumericalMaximizingObjectiveValue) ind.getObjectiveValue()).getNumericalValue(), 0.1);

        int[] integers3 = {0, 1};
        r = new NonRandomNumberGenerator(integers3);
        TournamentSelection ts3 = new TournamentSelection(r, 2);
        ind = ts3.doTournament(pool);
        assertEquals("Tourny3 winner", 15, ((NumericalMaximizingObjectiveValue) ind.getObjectiveValue()).getNumericalValue(), 0.1);
    }

    public static Test suite() {
        return new TestSuite(TournamentSelectionTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}