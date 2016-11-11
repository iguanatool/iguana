package org.iguanatool.search.evolve.population;

import java.util.Comparator;

/**
 * User: phil
 * Date: 22-Feb-2006
 * Time: 14:05:52
 */
public class PopulationProgressComparator implements Comparator<SubPopulation> {

    public int compare(SubPopulation p1, SubPopulation p2) {
        if (p1.getProgress() > p2.getProgress()) return 1;
        if (p1.getProgress() < p2.getProgress()) return -1;
        return 0;
    }
}
