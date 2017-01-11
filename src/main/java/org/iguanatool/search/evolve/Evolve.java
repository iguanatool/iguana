package org.iguanatool.search.evolve;

import org.iguanatool.search.Search;
import org.iguanatool.search.SearchException;
import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.SearchResult;
import org.iguanatool.search.evolve.population.Population;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.SolutionType;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:32:49
 */
public class Evolve extends Search {

    private Population population;
    private int generation;

    public Evolve(RandomNumberGenerator randomNumberGenerator, int maxEvaluations) {
        super(randomNumberGenerator, maxEvaluations);
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public SearchResult search(SolutionType solutionType,
                               ObjectiveFunction objectiveFunction) {

        if (population == null) {
            throw new SearchException("No population set");
        }

        SearchMonitor monitor = new SearchMonitor(objectiveFunction, maxEvaluations);

        // generation '0'
        population.initialize(solutionType, objectiveFunction, monitor);

        // generation 1 onwards
        generation = 1;

        //System.out.println(generation+": "+monitor.getBestSolution().getObjectiveValue().getNumericalValue()+";  "+monitor.getBestSolution());

        while (!monitor.terminate()) {
            generation++;

            if (!monitor.terminate()) {
                population.evolve();
            }

            //System.out.println(generation+": "+monitor.getBestSolution().getObjectiveValue().getNumericalValue()+";  "+monitor.getBestSolution());
        }

        return monitor.getSearchResult();
    }
}
 