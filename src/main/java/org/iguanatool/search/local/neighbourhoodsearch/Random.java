package org.iguanatool.search.local.neighbourhoodsearch;

import org.iguanatool.search.SearchException;
import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.Solution;

public class Random extends CachedNeighbourhoodSearch {

    public Random(RandomNumberGenerator rng) {
        super(rng);
    }

    @Override
    public Solution neighbourhoodSearch(Solution solution,
                                        ObjectiveFunction objectiveFunction,
                                        SearchMonitor monitor) {
        try {
            while (true) {
                evaluateObjectiveValue((NumericalSolution) solution, objectiveFunction);
                solution = solution.getType().generateRandomSolution(rng);
            }
        } catch (SearchException ex) {
            cache.clear();
        }
        return solution;
    }
}
