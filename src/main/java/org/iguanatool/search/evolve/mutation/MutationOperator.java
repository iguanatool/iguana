package org.iguanatool.search.evolve.mutation;

import org.iguanatool.search.solution.Solution;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:32:20
 */
public interface MutationOperator {

    Solution mutate(Solution soln);
}
