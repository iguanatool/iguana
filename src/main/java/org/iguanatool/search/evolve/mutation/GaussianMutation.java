package org.iguanatool.search.evolve.mutation;

import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 10:55:09
 */
public class GaussianMutation extends NumericalMutation {

    private double standardDeviation = 1.0;

    private double domainUnits;
    private boolean haveDomainUnits = false;

    private double domainPercentage;
    private boolean haveDomainPercentage = false;

    public GaussianMutation(RandomNumberGenerator r) {
        super(r);
    }

    public GaussianMutation(RandomNumberGenerator r,
                            double standardDeviation) {
        super(r);
        this.standardDeviation = standardDeviation;
    }

    public void setDomainUnits(double domainUnits) {
        this.domainUnits = domainUnits;
        this.haveDomainUnits = true;
        this.haveDomainPercentage = false;
    }

    public void setDomainPercentage(double domainPercentage) {
        this.domainPercentage = domainPercentage;
        this.haveDomainPercentage = true;
        this.haveDomainUnits = false;
    }

    protected void mutateGene(NumericalSolution ind, int locus) {
        if (haveDomainPercentage) {
            NumericalSolutionType species = (NumericalSolutionType) ind.getType();
            double standardDeviation = (species.getMax(locus) - species.getMin(locus))
                    * Math.pow(10, species.getAccuracy(locus));
            standardDeviation *= domainPercentage;
            mutateGene(ind, locus, standardDeviation);
        } else if (haveDomainUnits) {
            int precision = ((NumericalSolutionType) ind.getType()).getAccuracy(locus);
            double standardDeviation = Math.pow(10, -precision) * domainUnits;
            mutateGene(ind, locus, standardDeviation);
        } else {
            mutateGene(ind, locus, standardDeviation);
        }
    }

    private void mutateGene(NumericalSolution ind,
                            int locus,
                            double standardDeviation) {
        ind.setElement(locus, ind.getRawElement(locus) + r.nextGaussian(standardDeviation));
    }
}
