package org.iguanatool.search.evolve.select;

import org.iguanatool.search.evolve.rank.RankingMethod;
import org.iguanatool.search.evolve.rank.RankingUtil;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.Solution;

import java.util.Vector;

/**
 * User: phil
 * Date: 19-Feb-2006
 * Time: 08:17:43
 */
public class StochasticUniversalSampling implements SelectionMethod {

    private RandomNumberGenerator r;
    private RankingMethod ranker;

    public StochasticUniversalSampling(RandomNumberGenerator r,
                                       RankingMethod ranker) {
        this.r = r;
        this.ranker = ranker;
    }

    public Vector<Solution> select(Vector<Solution> pool,
                                   int numParents) {

        Vector<Solution> parents = new Vector<>();

        // get ranking
        double[] fitness = new double[pool.size()];
        ranker.rank(pool, fitness);
        fitness = RankingUtil.proportion(fitness);

        // get equally spaced pointers
        double pointerSpace = 1.0 / numParents;
        double pointerVal = r.nextDouble(pointerSpace);
        int fitnessIndex = 0;
        double fitnessTotal = 0;

        for (int i = 0; i < numParents; i++) {
            while (fitnessIndex < fitness.length) {
                if (pointerVal < fitnessTotal + fitness[fitnessIndex]) {
                    parents.add(pool.elementAt(fitnessIndex));
                    break;
                }
                fitnessTotal += fitness[fitnessIndex];
                fitnessIndex++;
            }
            pointerVal += pointerSpace;
        }

        return parents;
    }
}
