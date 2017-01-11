package org.iguanatool.search.local;

import org.iguanatool.search.Search;
import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.SearchResult;
import org.iguanatool.search.local.neighbourhoodsearch.NeighbourhoodSearch;
import org.iguanatool.search.local.restarter.Restarter;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.search.solution.SolutionType;

/**
 * User: phil
 * Date: 17-Jun-2006
 * Time: 16:08:56
 */
public class HillClimb extends Search {

    protected NeighbourhoodSearch neighbourhoodSearch;
    private Restarter restarter;

    public HillClimb(RandomNumberGenerator randomNumberGenerator, int maxEvaluations) {
        super(randomNumberGenerator, maxEvaluations);
        neighbourhoodSearch = null;
        restarter = null;
    }

    public void setNeighbourhoodSearch(NeighbourhoodSearch neighbourhoodSearch) {
        this.neighbourhoodSearch = neighbourhoodSearch;
    }

    public void setRestarter(Restarter restarter) {
        this.restarter = restarter;
    }

    public SearchResult search(SolutionType candidateSolutionType,
                               ObjectiveFunction objectiveFunction) {

        SearchMonitor monitor = new SearchMonitor(objectiveFunction, maxEvaluations);
        Solution currentSolution = candidateSolutionType.generateRandomSolution(randomNumberGenerator);
        currentSolution.evaluateObjectiveValue(objectiveFunction);

        do {
            // hillclimb as far as possible
            if (neighbourhoodSearch != null) {
                currentSolution = neighbourhoodSearch.neighbourhoodSearch(
                        currentSolution, objectiveFunction, monitor);
            }

            // invoke restart strategy
            if (restarter != null) {
                currentSolution = restarter.restart(
                        currentSolution, objectiveFunction, monitor);
            }

        } while (restarter != null && !monitor.terminate());

        return monitor.getSearchResult();
    }
}
