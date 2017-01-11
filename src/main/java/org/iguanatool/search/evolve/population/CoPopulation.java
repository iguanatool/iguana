package org.iguanatool.search.evolve.population;

import org.iguanatool.search.SearchException;
import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.evolve.competition.CompetitionManager;
import org.iguanatool.search.evolve.migration.MigrationManager;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.search.solution.SolutionType;

import java.util.Collections;
import java.util.List;
import java.util.Vector;


/**
 * User: phil
 * Date: 18-Feb-2006
 * Time: 10:57:47
 */

public class CoPopulation extends SubPopulation {

    private Vector<SubPopulation> subPopulations = new Vector<SubPopulation>();

    private MigrationManager migrationManager;
    private CompetitionManager competitionManager;

    private int popIndexToAddNextIndividual = 0;

    public CoPopulation(String id,
                        MigrationManager migrationManager) {
        super(id);
        this.migrationManager = migrationManager;
    }

    public CoPopulation(String id,
                        CompetitionManager competitionManager) {
        super(id);
        this.competitionManager = competitionManager;
    }

    public CoPopulation(String id,
                        MigrationManager migrationManager,
                        CompetitionManager competitionManager) {
        super(id);
        this.migrationManager = migrationManager;
        this.competitionManager = competitionManager;
    }

    public void addPopulation(SubPopulation p) {
        subPopulations.add(p);
    }

    public int getNumPopulations() {
        return subPopulations.size();
    }

    public SubPopulation getPopulation(int index) {
        return subPopulations.elementAt(index);
    }

    public List<SubPopulation> getSubPopulations() {
        return Collections.unmodifiableList(subPopulations);
    }

    public void initialize(SolutionType candidateSolutionType,
                           ObjectiveFunction objectiveFunction,
                           SearchMonitor monitor) {

        for (SubPopulation p : subPopulations) {
            p.initialize(candidateSolutionType, objectiveFunction, monitor);
            p.setProgress(0);
        }

        if (competitionManager != null) {
            competitionManager.reset();
        }

        if (migrationManager != null) {
            migrationManager.reset();
        }
    }

    public void evolve() {
        for (SubPopulation p : subPopulations) {
            p.evolve();
        }

        if (competitionManager != null) {
            competitionManager.competition(this);
        }

        if (migrationManager != null) {
            migrationManager.migration(this);
        }
    }

    public int getNumIndividuals() {
        int sum = 0;
        for (SubPopulation p : subPopulations) {
            sum += p.getNumIndividuals();
        }
        return sum;
    }

    public void addIndividual(Solution solution) {
        if (subPopulations.size() == 0) {
            throw new SearchException("No subpopulations to add solution to");
        }

        subPopulations.elementAt(popIndexToAddNextIndividual).addIndividual(solution);
        popIndexToAddNextIndividual++;
        if (popIndexToAddNextIndividual > subPopulations.size() - 1) {
            popIndexToAddNextIndividual = 0;
        }
    }

    public void addIndividuals(Vector<Solution> individuals) {
        for (Solution candidateSolution : individuals) {
            addIndividual(candidateSolution);
        }
    }

    public Solution removeIndividual(int index) {
        int sumIndex = 0;
        for (SubPopulation p : subPopulations) {
            if (sumIndex + p.getNumIndividuals() > index) {
                return p.removeIndividual(index - sumIndex);
            }
            sumIndex += p.getNumIndividuals();
        }
        return null;
    }

    public List<Solution> getIndividuals() {
        Vector<Solution> individuals = new Vector<Solution>();

        for (SubPopulation p : subPopulations) {
            individuals.addAll(p.getIndividuals());
        }

        return Collections.unmodifiableList(individuals);
    }
}
