package org.iguanatool.search.evolve.population;

import org.iguanatool.search.SearchException;
import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.evolve.mutation.MutationOperator;
import org.iguanatool.search.evolve.recombination.RecombinationOperator;
import org.iguanatool.search.evolve.reinsertion.ReinsertionMethod;
import org.iguanatool.search.evolve.select.SelectionMethod;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.search.solution.SolutionType;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:36:54
 */
public class BreedingPopulation extends SubPopulation {

    protected SolutionType candidateSolutionType;
    protected double generationGap;
    protected int initialSize;

    protected Vector<Solution> currentGeneration;

    protected SelectionMethod selector;
    protected RecombinationOperator recombinator;
    protected MutationOperator mutator;
    protected ReinsertionMethod reinserter;
    protected ObjectiveFunction objectiveFunction;
    protected SearchMonitor monitor;

    protected RandomNumberGenerator randomNumberGenerator;

    public BreedingPopulation(String id,
                              int initialSize,
                              double generationGap,
                              SelectionMethod selector,
                              RecombinationOperator recombinator,
                              MutationOperator mutator,
                              ReinsertionMethod reinserter,
                              RandomNumberGenerator randomNumberGenerator) {
        super(id);
        this.selector = selector;
        this.recombinator = recombinator;
        this.mutator = mutator;
        this.reinserter = reinserter;
        this.initialSize = initialSize;
        this.generationGap = generationGap;
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void initialize(SolutionType solutionType,
                           ObjectiveFunction objectiveFunction,
                           SearchMonitor monitor) {

        this.candidateSolutionType = solutionType;
        this.objectiveFunction = objectiveFunction;
        this.monitor = monitor;

        createInitialGeneration(solutionType, initialSize);
        evaluateObjectiveValues(currentGeneration);
    }

    private void createInitialGeneration(SolutionType candidateSolutionType, int size) {
        currentGeneration = new Vector<Solution>();
        for (int i = 0; i < size; i++) {
            Solution ind = candidateSolutionType.generateRandomSolution(randomNumberGenerator);
            currentGeneration.add(ind);
        }
    }

    public void evolve() {
        // ensure even number of parents to mate
        int numToMate = calcNoOfNewIndiviudals();
        if (numToMate % 2 != 0) {
            numToMate++;
        }

        Vector<Solution> parents = selector.select(currentGeneration, numToMate);
        Vector<Solution> offspring = recombine(parents);
        Vector<Solution> mutatedOffspring = mutate(offspring);

        evaluateObjectiveValues(mutatedOffspring);

        if (!monitor.terminate()) {
            currentGeneration = reinserter.reinsert(currentGeneration, mutatedOffspring);
        }
    }

    private void evaluateObjectiveValues(Vector<Solution> individuals) {
        for (Solution ind : individuals) {
            if (ind.getObjectiveValue() == null) {
                if (!monitor.terminate()) {
                    ind.evaluateObjectiveValue(objectiveFunction);
                }
            }
        }
    }

    private Vector<Solution> recombine(Vector<Solution> parents) {
        if (parents.size() % 2 != 0) {
            throw new SearchException(parents.size() + " is an uneven number of parents");
        }

        int numChildren = calcNoOfNewIndiviudals();

        Vector<Solution> children = new Vector<Solution>();
        int i = 0;
        int numParents = parents.size();

        while (i < numParents && children.size() < numChildren) {
            Solution parent1 = parents.remove(randomNumberGenerator.nextInt(parents.size()));
            Solution parent2 = parents.remove(randomNumberGenerator.nextInt(parents.size()));

            SolutionType candidateSolutionType = parent1.getType();

            Solution child1 = candidateSolutionType.solutionInstanceOfThisType();
            Solution child2 = candidateSolutionType.solutionInstanceOfThisType();

            recombinator.recombine(parent1, parent2, child1, child2);

            children.add(child1);

            if (children.size() < numChildren) {
                children.add(child2);
            }
            i += 2;
        }

        return children;
    }

    private Vector<Solution> mutate(Vector<Solution> individuals) {
        Vector<Solution> mutatedOffspring = new Vector<Solution>();

        for (Solution ind : individuals) {
            mutatedOffspring.add(mutator.mutate(ind));
        }

        return mutatedOffspring;
    }

    private int calcNoOfNewIndiviudals() {
        return (int) Math.round(currentGeneration.size() * generationGap);
    }

    public int getNumIndividuals() {
        return currentGeneration.size();
    }

    public void addIndividual(Solution candidateSolution) {
        if (candidateSolution.getType().equals(candidateSolutionType)) {
            currentGeneration.add(candidateSolution);
        } else {
            throw new SearchException("Can not add a different type of candidateSolutionType to this population");
        }
    }

    public void addIndividuals(Vector<Solution> individuals) {
        for (Solution candidateSolution : individuals) {
            addIndividual(candidateSolution);
        }
    }

    public Solution removeIndividual(int index) {
        Solution ind = currentGeneration.elementAt(index);
        currentGeneration.removeElementAt(index);
        return ind;
    }

    public List<Solution> getIndividuals() {
        return Collections.unmodifiableList(currentGeneration);
    }
}
