package org.iguanatool.search;

import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.solution.Solution;

public class SearchMonitor {

    protected int numEvaluations;
    protected Solution bestSolution;
    private int maxEvaluations;

    public SearchMonitor(ObjectiveFunction objectiveFunction,
                         int maxEvaluations) {
        objectiveFunction.setSearchMonitor(this);
        this.maxEvaluations = maxEvaluations;
        numEvaluations = 0;
        bestSolution = null;
    }

    public void monitorSolution(Solution solution) {
        if (terminate()) {
            throw new SearchException("Search not terminated normally");
        }

        numEvaluations++;
        if (bestSolution == null || solution.betterThan(bestSolution)) {
            bestSolution = solution;
        }
    }

    public boolean terminate() {
        return success() || evaluationsExhausted();
    }

    public boolean evaluationsExhausted() {
        return numEvaluations >= maxEvaluations &&
                maxEvaluations != Search.INFINITE_EVALUATIONS;
    }

    public int getNumEvaluations() {
        return numEvaluations;
    }

    public boolean success() {
        return bestSolution != null && bestSolution.isIdeal();
    }

    public Solution getBestSolution() {
        return bestSolution;
    }

    public SearchResult getSearchResult() {
        return new SearchResult(success(),
                numEvaluations,
                bestSolution);
    }
}
