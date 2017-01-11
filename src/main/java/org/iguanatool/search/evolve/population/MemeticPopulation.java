package org.iguanatool.search.evolve.population;

import org.iguanatool.search.evolve.mutation.MutationOperator;
import org.iguanatool.search.evolve.recombination.RecombinationOperator;
import org.iguanatool.search.evolve.reinsertion.ReinsertionMethod;
import org.iguanatool.search.evolve.select.SelectionMethod;
import org.iguanatool.search.local.neighbourhoodsearch.NeighbourhoodSearch;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.Solution;

public class MemeticPopulation extends BreedingPopulation {

    private NeighbourhoodSearch neighbourhoodSearch;

    public MemeticPopulation(String id,
                             int initialSize,
                             double generationGap,
                             SelectionMethod selector,
                             RecombinationOperator recombinator,
                             MutationOperator mutator,
                             ReinsertionMethod reinserter,
                             RandomNumberGenerator randomNumberGenerator,
                             NeighbourhoodSearch neighbourhoodSearch) {

        super(id, initialSize, generationGap, selector, recombinator,
                mutator, reinserter, randomNumberGenerator);

        this.neighbourhoodSearch = neighbourhoodSearch;
    }

    public void evolve() {
        super.evolve();

        for (int i = 0; i < currentGeneration.size(); i++) {
            Solution locallyOptimalSolution =
                    neighbourhoodSearch.neighbourhoodSearch(
                            currentGeneration.get(i),
                            objectiveFunction,
                            monitor);

            currentGeneration.set(i, locallyOptimalSolution);
        }
    }
}
