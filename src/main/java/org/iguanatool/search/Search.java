package org.iguanatool.search;

import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.SolutionType;

/**
 * User: phil
 * Date: 10-Sep-2006
 * Time: 12:58:25
 */
public abstract class Search {

    public static final int INFINITE_EVALUATIONS = -1;
    protected int maxEvaluations;
    protected RandomNumberGenerator randomNumberGenerator;

    public Search(RandomNumberGenerator randomNumberGenerator, int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public RandomNumberGenerator getRandomNumberGenerator() {
        return randomNumberGenerator;
    }

    public abstract SearchResult search(SolutionType candidateSolutionType,
                                        ObjectiveFunction objectiveFunction);
}
