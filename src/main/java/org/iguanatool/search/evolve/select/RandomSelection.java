package org.iguanatool.search.evolve.select;

import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.Solution;

import java.util.Vector;

/**
 * User: phil
 * Date: 22-Feb-2006
 * Time: 21:33:01
 */
public class RandomSelection implements SelectionMethod {

    private RandomNumberGenerator r;

    public RandomSelection(RandomNumberGenerator r) {
        this.r = r;
    }

    public Vector<Solution> select(Vector<Solution> pool,
                                     int numParents) {
        
        Vector<Solution> selection = new Vector<Solution>();

        for (int i=0; i < numParents; i++) {
            selection.add(pool.elementAt(r.nextInt(pool.size())));
        }

        return selection;
    }

}
