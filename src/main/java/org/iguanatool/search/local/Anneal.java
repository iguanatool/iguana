package org.iguanatool.search.local;

import org.iguanatool.search.Search;
import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.SearchResult;
import org.iguanatool.search.local.coolingschedule.CoolingSchedule;
import org.iguanatool.search.local.neighbourhoodsearch.ReducingRandom;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.search.solution.SolutionType;

public class Anneal extends Search {

	private int itsPerTempLevel;
	private CoolingSchedule coolingSchedule;
	private ReducingRandom neighbourhoodSearch;
	
    public Anneal(RandomNumberGenerator randomNumberGenerator, int maxEvaluations) {
    	super(randomNumberGenerator, maxEvaluations);
    }

    public void setIterationsPerTemperatureLevel(int itsPerTempLevel) {
    	this.itsPerTempLevel = itsPerTempLevel;
    }
    
    public void setCoolingSchedule(CoolingSchedule coolingSchedule) {
    	this.coolingSchedule = coolingSchedule;
    }
    
    public void setNeighbourhoodSearch(ReducingRandom neighbourhoodSearch) {
    	this.neighbourhoodSearch = neighbourhoodSearch;
    }
    
	public SearchResult search(SolutionType candidateSolutionType,
			                   ObjectiveFunction objectiveFunction) {
		
    	SearchMonitor monitor = new SearchMonitor(objectiveFunction, maxEvaluations);
    	Solution currentSolution = candidateSolutionType.generateRandomSolution(randomNumberGenerator);
		currentSolution.evaluateObjectiveValue(objectiveFunction);
		
		while (!monitor.terminate()) {
			int its = 0;				
			while (its < itsPerTempLevel && !monitor.terminate()) {				
				Solution newSolution = neighbourhoodSearch.neighbourhoodSearch(
						currentSolution, objectiveFunction, monitor);				
				
				newSolution.evaluateObjectiveValue(objectiveFunction);
				
				if (newSolution.betterThan(currentSolution)) {
					currentSolution = newSolution;
				} else {
					double rand = randomNumberGenerator.nextDouble();
					double diff = newSolution.getObjectiveValue().getNumericalValue()
								  - currentSolution.getObjectiveValue().getNumericalValue();
					double temp = coolingSchedule.getTemperature();
					
					if (rand < Math.pow(Math.E, -diff/temp)) {
						currentSolution = newSolution;
					}
				}
				its ++;
			}						
			coolingSchedule.cool();
			neighbourhoodSearch.reduce();
		}		
    	return monitor.getSearchResult();
	}
}
