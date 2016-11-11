package org.iguanatool.search.evolve.population.rank;

import org.iguanatool.search.evolve.population.SubPopulation;
import org.iguanatool.search.evolve.rank.RankingUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

/**
 * User: phil
 * Date: 22-Feb-2006
 * Time: 15:30:46
 */
public class PopulationLinearRanking implements PopulationRankingMethod {

    private double divisionPressure;
    private Comparator<SubPopulation> comparator;

    public PopulationLinearRanking(double selectionPressure,
                                   Comparator<SubPopulation> comparator) {
        this.divisionPressure = selectionPressure;
        this.comparator = comparator;
    }

    public void rank(Vector<SubPopulation> populations,
                     double[] fitness) {
        // sort from worst to best
        Collections.sort(populations, comparator);

        // get fitness array
        RankingUtil.linearRanking(fitness, divisionPressure);
    }

}
