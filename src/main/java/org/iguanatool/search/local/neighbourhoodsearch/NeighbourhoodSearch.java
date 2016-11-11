package org.iguanatool.search.local.neighbourhoodsearch;

import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.solution.Solution;

public abstract class NeighbourhoodSearch {
	
	public abstract Solution neighbourhoodSearch(Solution solution,
                                                 ObjectiveFunction objectiveFunction,
                                                 SearchMonitor monitor);
}
