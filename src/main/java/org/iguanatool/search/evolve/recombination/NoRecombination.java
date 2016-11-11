package org.iguanatool.search.evolve.recombination;

import org.iguanatool.search.SearchException;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.search.solution.SolutionType;

/**
 * User: phil
 * Date: 23-Feb-2006
 * Time: 16:02:09
 */
public class NoRecombination implements RecombinationOperator {

    public void recombine(Solution parent1,
                          Solution parent2,
                          Solution child1,
                          Solution child2) {

        SolutionType parent1Type = parent1.getType();
        SolutionType parent2Type = parent2.getType();

        if (!parent1Type.getClass().equals(parent2Type.getClass())) {
            throw new SearchException("Can not recombination different types of type");
        }

        int vectorSize = parent1Type.getVectorSize();

        for (int i=0; i < vectorSize; i++) {
            child1.copyElement(parent1, i);
            child2.copyElement(parent2, i);
        }
    }
}
