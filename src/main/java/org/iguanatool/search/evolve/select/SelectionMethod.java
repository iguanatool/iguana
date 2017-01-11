package org.iguanatool.search.evolve.select;

import org.iguanatool.search.solution.Solution;

import java.util.Vector;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 10:47:33
 */
public interface SelectionMethod {

    Vector<Solution> select(Vector<Solution> pool,
                            int numParents);

}
