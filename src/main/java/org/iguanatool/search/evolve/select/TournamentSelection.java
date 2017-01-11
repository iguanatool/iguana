package org.iguanatool.search.evolve.select;

import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.Solution;

import java.util.Collections;
import java.util.Vector;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 10:56:40
 */
public class TournamentSelection implements SelectionMethod {

    private int tournamentSize;
    private RandomNumberGenerator r;

    public TournamentSelection(RandomNumberGenerator r, int tournamentSize) {
        this.r = r;
        this.tournamentSize = tournamentSize;
    }

    public Vector<Solution> select(Vector<Solution> pool,
                                   int numParents) {
        Vector<Solution> selected = new Vector<Solution>();

        for (int i = 0; i < numParents; i++) {
            selected.add(doTournament(pool));
        }

        return selected;
    }

    Solution doTournament(Vector<Solution> pool) {
        Vector<Solution> competitors = new Vector<Solution>();

        for (int i = 0; i < tournamentSize; i++) {
            Solution ind = pool.elementAt(r.nextInt(pool.size()));
            competitors.add(ind);
        }

        return Collections.max(competitors);
    }

}
