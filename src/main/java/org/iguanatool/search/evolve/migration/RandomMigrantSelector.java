package org.iguanatool.search.evolve.migration;

import org.iguanatool.search.evolve.population.SubPopulation;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.Solution;

import java.util.Vector;

/**
 * User: phil
 * Date: 18-Feb-2006
 * Time: 13:17:52
 */
public class RandomMigrantSelector implements MigrantSelector {

    private RandomNumberGenerator r;

    public RandomMigrantSelector(RandomNumberGenerator r) {
        this.r = r;
    }

    public Vector<Solution> select(SubPopulation p,
                                   int numIndividuals) {

        Vector<Solution> selection = new Vector<>();

        for (int i = 0; i < numIndividuals; i++) {
            int index = r.nextInt(p.getNumIndividuals());
            selection.add(p.removeIndividual(index));
        }

        return selection;
    }
}
