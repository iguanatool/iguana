package org.iguanatool.search.objective;

import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.solution.Solution;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:32:03
 */
public abstract class ObjectiveFunction {

	private SearchMonitor monitor;
		
	public void setSearchMonitor(SearchMonitor monitor) {
		this.monitor = monitor;
	}
	
	public SearchMonitor getSearchMonitor() {
		return monitor;
	}
	
    public final ObjectiveValue evaluate(Solution solution) {
    	ObjectiveValue objectiveValue = computeObjectiveValue(solution);
    	solution.setObjectiveValue(objectiveValue);
    	
    	if (monitor != null) {
    		monitor.monitorSolution(solution);
    	}
    	
    	return objectiveValue;
    }

    protected abstract ObjectiveValue computeObjectiveValue(Solution solution);

	public abstract String getObjectiveDescription();
}
