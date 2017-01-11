package org.iguanatool.search.evolve.population.rank;

import org.iguanatool.search.evolve.population.SubPopulation;

import java.util.Vector;

/**
 * User: phil
 * Date: 22-Feb-2006
 * Time: 15:29:58
 */
public interface PopulationRankingMethod {

    void rank(Vector<SubPopulation> populations,
              double[] fitness);
}
