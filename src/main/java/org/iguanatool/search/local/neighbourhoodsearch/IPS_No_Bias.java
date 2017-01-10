// implementation of iterated pattern search (ips) algorithm
package org.iguanatool.search.local.neighbourhoodsearch;

import org.iguanatool.search.SearchException;
import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.Solution;

public class IPS_No_Bias extends CachedNeighbourhoodSearch {

	public IPS_No_Bias(RandomNumberGenerator rng) {
		super(rng);
	}
	
	@Override
	public Solution neighbourhoodSearch(Solution solution,
                                        ObjectiveFunction objectiveFunction, SearchMonitor monitor) {
		try {
			initialize(solution);
			while (true) {
				int index = 0, lastImprovement = 0;
				// rng.shuffle(indicies); // permute variable order
				while (lastImprovement < nVariables) {
					NumericalSolution current = (NumericalSolution) solution;
					evaluateObjectiveValue(current, objectiveFunction);
					while (true) {
						NumericalSolution left = (NumericalSolution) current.clone();
						NumericalSolution right = (NumericalSolution) current.clone();
						setElement(left, index, getElement(current, index) - 1);
						setElement(right, index, getElement(current, index) + 1);
						evaluateObjectiveValue(left, objectiveFunction);
						evaluateObjectiveValue(right, objectiveFunction);
						long velocity;
						NumericalSolution next;
						// establish gradient
						if (left.betterThan(right) || !left.worseThan(right) && rng.nextBoolean()) {
							velocity = -1;
							next = left;
						} else {
							velocity = 1;
							next = right;
						}
						if (!next.betterThan(current)) {
							break;
						}
						// accelerate
						do {
							current = next;
							next = (NumericalSolution) current.clone();
							setElement(next, index, getElement(current, index) + (velocity *= 2));
							evaluateObjectiveValue(next, objectiveFunction);
						} while (next.betterThan(current));
					}
					// check for improvement
					if (current.betterThan(solution)) {
						lastImprovement = 0;
						solution = current;
					} else {
						++lastImprovement;
					}
					index = (index + 1) % nVariables; // cycle
				}
				solution = solution.getType().generateRandomSolution(rng); // restart
			}
		} catch (SearchException ex) {
			cache.clear();
		}
		return solution;
	}
}