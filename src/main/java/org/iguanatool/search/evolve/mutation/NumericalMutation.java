package org.iguanatool.search.evolve.mutation;

import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;

/**
 * User: phil
 * Date: 01-Apr-2006
 * Time: 14:24:12
 */
public abstract class NumericalMutation implements MutationOperator {

    protected RandomNumberGenerator r;

    protected NumericalMutation(RandomNumberGenerator r) {
        this.r = r;
    }

    public Solution mutate(Solution ind) {
        NumericalSolution ind2 = (NumericalSolution) ind;
        NumericalSolutionType species = (NumericalSolutionType) ind2.getType();
        int vectorSize = species.getVectorSize();
        double mutationProbability = 1.0 / vectorSize;

        for (int i=0; i < vectorSize; i++) {
            if (r.nextDouble() < mutationProbability) {
                mutateGene(ind2, i);
            }

            if (ind2.getRawElement(i) > species.getMax(i)) {
                ind2.setElement(i, species.getMax(i));
            }
            if (ind2.getRawElement(i) < species.getMin(i)) {
                ind2.setElement(i, species.getMin(i));
            }
        }

        return ind2;
    }

     protected abstract void mutateGene(NumericalSolution ind, int locus);
}
