// implementation of geometric search algorithm
package org.iguanatool.search.local.neighbourhoodsearch;

import org.iguanatool.search.SearchException;
import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.MersenneTwisterRandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.Solution;

public class Geometric_Opposite_Direction_Bias extends CachedNeighbourhoodSearch {
	
	public Geometric_Opposite_Direction_Bias(MersenneTwisterRandomNumberGenerator rng) {
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
					NumericalSolution left = (NumericalSolution) current.clone();
					NumericalSolution right = (NumericalSolution) current.clone();
					setElement(left, index, getElement(current, index) - 1);
					setElement(right, index, getElement(current, index) + 1);
					evaluateObjectiveValue(current, objectiveFunction);
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
					if (next.betterThan(current)) {
						NumericalSolution previous;
						// accelerate
						do {
							previous = current;
							current = next;
							next = (NumericalSolution) current.clone();
							setElement(next, index, getElement(current, index) + (velocity *= 2));
							evaluateObjectiveValue(next, objectiveFunction);
						} while (next.betterThan(current));
						// calculate interval
						if (velocity < 0) {
							left = next;
							right = previous;
						} else {
							left = previous;
							right = next;
						}
						// elimination search
						while (getElement(left, index) != getElement(right, index)) {
							NumericalSolution middleLeft = (NumericalSolution) current.clone();
							NumericalSolution middleRight = (NumericalSolution) current.clone();					
							setElement(middleLeft, index, getElement(left, index) + Math.floor((getElement(right, index) - getElement(left, index)) / 2.0));
							setElement(middleRight, index, getElement(middleLeft, index) + 1);
							evaluateObjectiveValue(middleLeft, objectiveFunction);
							evaluateObjectiveValue(middleRight, objectiveFunction);
							if (middleLeft.betterThan(middleRight) || !middleLeft.worseThan(middleRight) && velocity > 0) {
								right = middleLeft;
							} else {
								left = middleRight;
							}
						}
						current = left;
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