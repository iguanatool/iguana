package org.iguanatool.search.evolve.rank;

import org.iguanatool.search.SearchException;
import org.iguanatool.search.solution.Solution;

import java.util.Collections;
import java.util.Vector;

/**
 * User: phil
 * Date: 19-Feb-2006
 * Time: 08:21:54
 */
public class LinearRanking implements RankingMethod {

    private double selectionPressure;

    public LinearRanking(double selectionPressure) {
        if (selectionPressure < 1.0 || selectionPressure > 2.0) {
            throw new SearchException("selectionPressure can not be less than 1 or greater than 2");
        }
        this.selectionPressure = selectionPressure;
    }

    public void rank(Vector<Solution> solutions, double[] fitness) {
        // sort from worst to best
        Collections.sort(solutions);

        // get fitness array
        RankingUtil.linearRanking(fitness, selectionPressure);
    }
}
