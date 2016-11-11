package org.iguanatool.search.evolve.population;

import org.iguanatool.search.solution.Solution;

import java.util.List;
import java.util.Vector;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:34:40
 */
public abstract class SubPopulation extends Population {

    public static PopulationFitnessComparator FITNESS_COMPARATOR = new PopulationFitnessComparator();
    public static PopulationProgressComparator PROGRESS_COMPARATOR = new PopulationProgressComparator();

    private double fitness;
    private double progress;

    public SubPopulation(String id) {
        super(id);
    }

    public abstract void addIndividual(Solution candidateSolution);

    public abstract void addIndividuals(Vector<Solution> individuals);

    public abstract Solution removeIndividual(int index);

    public int getNumIndividuals() {
        List<Solution> individuals = getIndividuals();
        return individuals.size();
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
