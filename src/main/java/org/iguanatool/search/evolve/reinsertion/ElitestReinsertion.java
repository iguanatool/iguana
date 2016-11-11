package org.iguanatool.search.evolve.reinsertion;

import org.iguanatool.search.solution.Solution;

import java.util.Collections;
import java.util.Vector;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 10:58:08
 */
public class ElitestReinsertion implements ReinsertionMethod {

    public Vector<Solution> reinsert(Vector<Solution> parents,
                                     Vector<Solution> children) {
        // order from worst to best objectiveValue
        Collections.sort(parents);
        Collections.sort(children);

        // copy best children over worst parents
        int parentListPos = 0;

        for (int childListPos=children.size()-1;
                childListPos >= 0;
                childListPos --, parentListPos++) {
            parents.setElementAt(children.elementAt(childListPos),
                                 parentListPos);
        }

        return parents;
    }
}
