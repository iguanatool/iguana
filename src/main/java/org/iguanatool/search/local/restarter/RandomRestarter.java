package org.iguanatool.search.local.restarter;

import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.Solution;

public class RandomRestarter extends Restarter {

	protected RandomNumberGenerator r;
	
	public RandomRestarter(int restartType, RandomNumberGenerator r) {
		super(restartType);
		this.r = r;
	}
	
	protected Solution generateRestartSolution(Solution currentSolution) {
		return currentSolution.getType().generateRandomSolution(r);		
	}
	
}
