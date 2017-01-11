package org.iguanatool.search.evolve.recombination;

import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.Solution;

/**
 * User: phil
 * Date: 23-Feb-2006
 * Time: 16:02:09
 */
public class RandomRecombination implements RecombinationOperator {

    private RandomNumberGenerator r;

    public RandomRecombination(RandomNumberGenerator r) {
        this.r = r;
    }

    public void recombine(Solution parent1,
                          Solution parent2,
                          Solution child1,
                          Solution child2) {

        int vectorSize = parent1.getNumElements();

        for (int i = 0; i < vectorSize; i++) {
            if (r.nextBoolean()) {
                child1.copyElement(parent1, i);
            } else {
                ((NumericalSolution) child1).setElementAtRandom(r, i);
            }
        }

        for (int i = 0; i < vectorSize; i++) {
            if (r.nextBoolean()) {
                child2.copyElement(parent2, i);
            } else {
                ((NumericalSolution) child2).setElementAtRandom(r, i);
            }
        }
    }
}
