package org.iguanatool.search.evolve.competition;

import org.iguanatool.search.evolve.population.CoPopulation;

/**
 * User: phil
 * Date: 18-Feb-2006
 * Time: 15:44:09
 */
public abstract class CompetitionManager {

    private int competeEvery;
    private int generationsToCompetition = 0;

    public CompetitionManager(int competeEvery) {
        this.competeEvery = competeEvery;
    }

    public void competition(CoPopulation coPopulation) {
        monitor(coPopulation);

        generationsToCompetition++;
        if (generationsToCompetition == competeEvery) {
            redistribute(coPopulation);
            generationsToCompetition = 0;
        }
    }

    public void reset() {
        generationsToCompetition = 0;
    }

    protected abstract void monitor(CoPopulation coPopulation);

    protected abstract void redistribute(CoPopulation coPopulation);
}

