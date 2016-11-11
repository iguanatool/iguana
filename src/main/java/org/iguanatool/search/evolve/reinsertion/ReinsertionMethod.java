package org.iguanatool.search.evolve.reinsertion;

import org.iguanatool.search.solution.Solution;

import java.util.Vector;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 10:47:41
 */
public interface ReinsertionMethod {

    public Vector<Solution> reinsert(Vector<Solution> parents,
                                     Vector<Solution> children);
}
