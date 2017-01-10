// implementation of lattice search algorithm
package org.iguanatool.search.local.neighbourhoodsearch;

import org.iguanatool.search.SearchException;
import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.MersenneTwisterRandomNumberGenerator;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.Solution;

public class Lattice_Opposite_Direction_Bias extends CachedNeighbourhoodSearch {
	
	private static long[] F = new long[49]; // fibonacci numbers
	
	// F[48] = 4807526976 >= Integer.MAX_VALUE - Integer.MIN_VALUE = 2^31 - 1 - (-2^31) = 2^32 - 1 = 4294967295

	static {
		F[0] = 0; F[1] = 1;
		for (int i = 1; i < F.length - 1; ++i) {
			F[i + 1] = F[i - 1] + F[i];
		}
	}

	public Lattice_Opposite_Direction_Bias(RandomNumberGenerator rng) {
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
						// determine smallest fibonacci number for interval
						int n = 4;
						while (n < F.length && F[n] < (long) getElement(right, index) - (long) getElement(left, index) + 2) {
							++n;
						}
						// elimination search
						do {
							NumericalSolution middleLeft = (NumericalSolution) current.clone();
							NumericalSolution middleRight = (NumericalSolution) current.clone();					
							setElement(middleLeft, index, getElement(left, index) + F[n - 2] - 1);
							setElement(middleRight, index, getElement(left, index) + F[n - 1] - 1);
							if (getElement(middleRight, index) <= getElement(right, index)) {
								evaluateObjectiveValue(middleLeft, objectiveFunction);
								evaluateObjectiveValue(middleRight, objectiveFunction);
								if (middleRight.betterThan(middleLeft) || !middleRight.worseThan(middleLeft) && velocity < 0) {
									left = (NumericalSolution) current.clone();
									setElement(left, index, getElement(middleLeft, index) + 1);
								}
							}
						} while (--n != 3);
						evaluateObjectiveValue(left, objectiveFunction);
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