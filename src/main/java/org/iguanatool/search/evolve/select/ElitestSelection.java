package org.iguanatool.search.evolve.select;

import org.iguanatool.search.solution.Solution;

import java.util.Collections;
import java.util.Vector;

/**
 * User: phil
 * Date: 22-Feb-2006
 * Time: 16:16:32
 */
public class ElitestSelection implements SelectionMethod {

    public Vector<Solution> select(Vector<Solution> pool,
                                   int numParents) {
        // sort from worst to best
        Collections.sort(pool);

        Vector<Solution> selected = new Vector<Solution>();

        int poolIndex = pool.size() - 1;
        for (int i = 0; i < numParents; i++, poolIndex--) {
            Solution ind = pool.elementAt(poolIndex);
            selected.add(ind);
        }

        return selected;
    }

}
