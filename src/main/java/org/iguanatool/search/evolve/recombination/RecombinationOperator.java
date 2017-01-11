package org.iguanatool.search.evolve.recombination;

import org.iguanatool.search.solution.Solution;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:32:29
 */
public interface RecombinationOperator {

    void recombine(Solution parent1,
                   Solution parent2,
                   Solution child1,
                   Solution child2);
}
