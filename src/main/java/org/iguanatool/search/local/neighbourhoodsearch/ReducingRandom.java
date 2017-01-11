package org.iguanatool.search.local.neighbourhoodsearch;

import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;

public class ReducingRandom extends NeighbourhoodSearch {

    private RandomNumberGenerator r;
    private int steps;
    private int delay;
    private int iteration, iterationDelay;

    public ReducingRandom(RandomNumberGenerator r, int steps, int delay) {
        this.r = r;
        this.steps = steps;
        this.delay = delay;
        this.iteration = 0;
    }

    public void reduce() {
        iterationDelay++;
        if (iterationDelay > delay) {
            iterationDelay = 0;
            if (iteration < steps) {
                iteration++;
            }
        }
    }

    public Solution neighbourhoodSearch(Solution solution,
                                        ObjectiveFunction objectiveFunction,
                                        SearchMonitor monitor) {

        NumericalSolution numericalSolution = (NumericalSolution) solution.clone();
        NumericalSolutionType type = (NumericalSolutionType) numericalSolution.getType();

        for (int i = 0; i < numericalSolution.getNumElements(); i++) {
            int accuracy = (steps - iteration) + type.getAccuracy(i);
            double epsilon = Math.pow(10, accuracy);
            numericalSolution.setElement(i, numericalSolution.getRawElement(i) + r.nextDouble(-epsilon, epsilon));
        }

        return numericalSolution;
    }
}
