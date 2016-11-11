package org.iguanatool.search.random;

import org.iguanatool.search.Search;
import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.SearchResult;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.search.solution.SolutionType;

/**
 * User: phil
 * Date: 10-Sep-2006
 * Time: 16:18:05
 */

public class Random extends Search {
	
	public Random(RandomNumberGenerator randomNumberGenerator, int maxEvaluations) {
    	super(randomNumberGenerator, maxEvaluations);
    }
    
    public SearchResult search(SolutionType candidateSolutionType,
    						   ObjectiveFunction objectiveFunction) {    	
    	
    	SearchMonitor monitor = new SearchMonitor(objectiveFunction, maxEvaluations);    	
        
    	while (!monitor.terminate()) {
            Solution candidateSolution = candidateSolutionType.generateRandomSolution(randomNumberGenerator);
            candidateSolution.evaluateObjectiveValue(objectiveFunction);        
        }
    	
        return monitor.getSearchResult();
    }
}
