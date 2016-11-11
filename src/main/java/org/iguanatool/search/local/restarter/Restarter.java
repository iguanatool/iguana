package org.iguanatool.search.local.restarter;

import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.solution.Solution;

public abstract class Restarter {
	
	public static final int ANY       = 0;
	public static final int IMPROVED  = 1;
	public static final int NOT_WORSE = 2;
	
	protected int restartType;
	
	public Restarter(int restartType) {
		this.restartType = restartType;
	}
	
	protected abstract Solution generateRestartSolution(Solution currentSolution);
	
	public Solution restart(Solution currentSolution, 
							ObjectiveFunction objectiveFunction,
							SearchMonitor monitor) {				
		Solution newSolution = null;
		boolean restarted = false;
		
		while (!restarted && !monitor.terminate()) {
			newSolution = generateRestartSolution(currentSolution);
			newSolution.evaluateObjectiveValue(objectiveFunction);
			
			if (restartType == IMPROVED && newSolution.betterThan(currentSolution)) {
				restarted = true;
			} 
			
			if (restartType == NOT_WORSE && !newSolution.worseThan(currentSolution)) {
				restarted = true;
			}
			
			if (restartType == ANY) {
				restarted = true;
			}			
		} 
		
		return restarted ? newSolution : currentSolution;
	}
}
