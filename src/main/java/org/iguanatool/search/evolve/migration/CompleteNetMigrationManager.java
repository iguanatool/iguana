package org.iguanatool.search.evolve.migration;

import org.iguanatool.search.evolve.population.CoPopulation;
import org.iguanatool.search.evolve.population.SubPopulation;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.Solution;

import java.util.List;
import java.util.Vector;

/**
 * User: phil
 * Date: 18-Feb-2006
 * Time: 13:32:38
 */
public class CompleteNetMigrationManager extends MigrationManager {

    private double percentageToMigrate;
    private RandomNumberGenerator r;
    private int[] numToMigrate;

    public CompleteNetMigrationManager(int migrateEvery,
                                       MigrantSelector migrantSelector,
                                       double percentageToMigrate,
                                       RandomNumberGenerator r) {
        super(migrateEvery, migrantSelector);
        this.percentageToMigrate = percentageToMigrate;
        this.r = r;
    }

    protected void doMigration(CoPopulation coPopulation) {

        calculateNumMigrants(coPopulation);
        Vector<Vector<Solution>> selected = getIndividuals(coPopulation);
        int numPopulations = coPopulation.getNumPopulations();

        for (int i = 0; i < numPopulations; i++) {
            putIndividuals(selected, i, coPopulation.getPopulation(i));
        }
    }

    private void calculateNumMigrants(CoPopulation coPopulation) {
        List<SubPopulation> subPopulations = coPopulation.getSubPopulations();
        numToMigrate = new int[subPopulations.size()];
        int i = 0;
        for (SubPopulation subPopulation : subPopulations) {
            numToMigrate[i] = (int) Math.round(percentageToMigrate *
                    subPopulation.getNumIndividuals());
            i++;
        }
    }

    private void putIndividuals(Vector<Vector<Solution>> selected,
                                int popIndex,
                                SubPopulation population) {

        int numToPut = numToMigrate[popIndex];

        // get from other populations
        numToPut = putIndividualsFromSelection(numToPut, selected, popIndex, population);

        // if none left in other populations - get own individuals back
        if (numToPut > 0) {
            Vector<Solution> ownIndividuals = selected.elementAt(popIndex);
            for (int i = 0; i < numToPut; i++) {
                int choice = r.nextInt(ownIndividuals.size());
                population.addIndividual(ownIndividuals.elementAt(choice));
            }
        }
    }

    private int putIndividualsFromSelection(int numToPut,
                                            Vector<Vector<Solution>> selection,
                                            int popIndex,
                                            SubPopulation population) {
        while (numToPut > 0) {
            if (selectionSize(selection, popIndex) > 0) {
                population.addIndividual(getRandomIndividual(selection, popIndex));
            } else {
                break;
            }
            numToPut--;
        }

        return numToPut;
    }

    private int selectionSize(Vector<Vector<Solution>> selection,
                              int popIndex) {
        int size = 0;
        int i = 0;
        for (Vector<Solution> popSelection : selection) {
            if (i != popIndex) {
                size += popSelection.size();
            }
            i++;
        }
        return size;
    }

    private Solution getRandomIndividual(Vector<Vector<Solution>> selection,
                                         int popIndex) {

        int selectionSize = selectionSize(selection, popIndex);
        int index = r.nextInt(selectionSize);
        int i = 0;

        for (Vector<Solution> popSelection : selection) {
            if (i != popIndex) {
                if (index < popSelection.size()) {
                    Solution ind = popSelection.elementAt(index);
                    popSelection.removeElementAt(index);
                    return ind;
                }
                index -= popSelection.size();
            }
            i++;
        }

        throw new RuntimeException("Could not get random individual");
    }

    private Vector<Vector<Solution>> getIndividuals(CoPopulation coPopulation) {
        Vector<Vector<Solution>> selected = new Vector<>();

        List<SubPopulation> populations = coPopulation.getSubPopulations();
        int i = 0;
        for (SubPopulation p : populations) {
            int numToGet = numToMigrate[i];
            Vector<Solution> populationIndividuals =
                    migrantSelector.select(p, numToGet);
            selected.add(populationIndividuals);
            i++;
        }

        return selected;
    }

}
