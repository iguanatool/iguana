package org.iguanatool.search.evolve.mutation;

import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;

/**
 * User: phil
 * Date: 01-Apr-2006
 * Time: 14:08:59
 */
public class MuhlenbeinMutation extends NumericalMutation {

    private double mutationRange;
    private int mutationPrecision;

    public MuhlenbeinMutation(RandomNumberGenerator r,
                              double mutationRange,
                              int mutationPrecision) {
        super(r);
        this.mutationRange = mutationRange;
        this.mutationPrecision = mutationPrecision;
    }

    protected void mutateGene(NumericalSolution ind, int locus) {
        double delta = 0;
        double alphaThreshold = 1.0 / (mutationPrecision + 1);
        for (int i = 0; i <= mutationPrecision; i++) {
            if (r.nextDouble() < alphaThreshold) {
                delta += Math.pow(2, -i);
            }
        }
        NumericalSolutionType species = (NumericalSolutionType) ind.getType();
        double range = species.getDomain(locus) * mutationRange;
        if (r.nextBoolean()) {
            range = -range;
        }
        ind.setElement(locus, ind.getRawElement(locus) + range * delta);
    }
}
