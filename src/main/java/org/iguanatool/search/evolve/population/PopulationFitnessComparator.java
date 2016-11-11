package org.iguanatool.search.evolve.population;

import java.util.Comparator;

/**
 * User: phil
 * Date: 22-Feb-2006
 * Time: 13:40:48
 */
public class PopulationFitnessComparator implements Comparator<SubPopulation> {

    public int compare(SubPopulation p1, SubPopulation p2) {
        if (p1.getFitness() > p2.getFitness()) return -1;
        if (p1.getFitness() < p2.getFitness()) return 1;
        return 0;
    }

}
