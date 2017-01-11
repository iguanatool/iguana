package org.iguanatool.search.example;

import org.iguanatool.search.objective.NumericalMinimizingObjectiveValue;
import org.iguanatool.search.objective.ObjectiveValue;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.search.solution.SolutionType;

public class AllZeros extends Example {

    private static int VECTOR_SIZE = 10;

    public static void main(String[] args) {
        Example.run(new AllZeros(), args);
    }

    public String getObjectiveDescription() {
        return "Optimise a vector so that all values are zero";
    }

    protected ObjectiveValue computeObjectiveValue(Solution soln) {
        NumericalSolution soln2 = (NumericalSolution) soln;
        double dist = 0;
        for (int i = 0; i < VECTOR_SIZE; i++) {
            dist += Math.abs(soln2.getElement(i) - 0);
        }

        return new NumericalMinimizingObjectiveValue(dist, 0);
    }

    protected SolutionType makeCandidateSolutionType() {
        return new NumericalSolutionType(VECTOR_SIZE, -20000, 20000, 0);
    }
}