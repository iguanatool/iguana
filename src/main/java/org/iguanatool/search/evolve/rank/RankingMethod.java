package org.iguanatool.search.evolve.rank;

import org.iguanatool.search.solution.Solution;

import java.util.Vector;

/**
 * User: phil
 * Date: 20-Feb-2006
 * Time: 20:31:20
 */
public interface RankingMethod {

    void rank(Vector<Solution> solutions,
              double[] fitness);
}
