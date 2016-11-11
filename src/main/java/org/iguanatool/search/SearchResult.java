package org.iguanatool.search;

import org.iguanatool.search.solution.Solution;

public class SearchResult {

	private boolean success;
	private int numEvaluations;
	private Solution bestSolution;
	
	public SearchResult(boolean success,
						int numEvaluations,
						Solution bestSolution) {
		
		this.success = success;
		this.numEvaluations = numEvaluations;
		this.bestSolution = bestSolution;
	}
	
	public Solution getBestSolution() {
		return bestSolution;
	}
	
	public int getNumEvaluations() {
		return numEvaluations;
	}
	
	public boolean wasSuccess() {
		return success;
	}
}
