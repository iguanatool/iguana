package org.iguanatool.search.evolve.mutation;

import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 16:01:59
 */
public class UniformMutation extends NumericalMutation {

    private boolean haveEpsilon = false;
    private double epsilon;

    public UniformMutation(RandomNumberGenerator r) {
        super(r);
    }

    public UniformMutation(RandomNumberGenerator r, double epsilon) {
        super(r);
        setEpsilon(epsilon);
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
        this.haveEpsilon = true;
    }

    protected void mutateGene(NumericalSolution ind2, int i) {
        if (haveEpsilon) {
            ind2.setElement(i, ind2.getRawElement(i) + r.nextDouble(-epsilon, epsilon));
        } else {
            ind2.setElementAtRandom(r, i);
        }
    }

}
