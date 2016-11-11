package org.iguanatool.search.evolve.migration;

import org.iguanatool.search.evolve.population.SubPopulation;
import org.iguanatool.search.solution.Solution;

import java.util.Vector;

/**
 * User: phil
 * Date: 18-Feb-2006
 * Time: 13:17:46
 */
public interface MigrantSelector {

    public Vector<Solution> select(SubPopulation p,
                                   int numIndividuals);
}
