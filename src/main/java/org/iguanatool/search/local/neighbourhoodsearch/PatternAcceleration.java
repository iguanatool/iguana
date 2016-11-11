package org.iguanatool.search.local.neighbourhoodsearch;

import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;

public class PatternAcceleration extends NeighbourhoodSearch {
	private int repeatBase;

	public PatternAcceleration() {
		repeatBase = 2;
	}

	public PatternAcceleration(int repeatBase) {
		this.repeatBase = repeatBase;
	}

	public Solution neighbourhoodSearch(Solution solution,
                                        ObjectiveFunction objectiveFunction,
                                        SearchMonitor monitor) {
		
		NumericalSolution currentSolution = (NumericalSolution) solution;
		NumericalSolutionType type = (NumericalSolutionType) currentSolution.getType();

		NumericalSolution newSolution;
		int varNum = 0;
		int direction = -1;
		int lastImprovement = 0;
		boolean completedCycle;

		do {
			newSolution = accelerate(currentSolution, type, objectiveFunction,
									 monitor, varNum, direction);

			if (newSolution.betterThan(currentSolution)) {
				currentSolution = newSolution;
				lastImprovement = 0;
			} else {
				lastImprovement++;
			}

			// change direction
			if (direction == -1) {
				direction = 1;
			} else {
				direction = -1;

				// change variable
				varNum++;
				if (varNum == type.getVectorSize()) {
					varNum = 0;
				}
			}

			completedCycle = lastImprovement >= (type.getVectorSize() * 2);

		} while (!completedCycle && !monitor.terminate());

		return currentSolution;
	}

	private NumericalSolution accelerate(NumericalSolution currentSolution,
										 NumericalSolutionType type, 
										 ObjectiveFunction objectiveFunction,
										 SearchMonitor monitor, 
										 int varNum, int direction) {
		NumericalSolution newSolution;
		int iteration = 0;

		while (!monitor.terminate()) {
			newSolution = makeMove(currentSolution, type, varNum, direction, iteration);
			newSolution.evaluateObjectiveValue(objectiveFunction);
			iteration++;

			if (newSolution.betterThan(currentSolution)) {
				currentSolution = newSolution;
			} else {
				break;
			}
		}

		return currentSolution;
	}

	private NumericalSolution makeMove(NumericalSolution solution,
									   NumericalSolutionType type, 
									   int varNum, int direction, int iteration) {

		NumericalSolution newSolution = (NumericalSolution) solution.clone();
		double var = newSolution.getElement(varNum);
		int power = type.getAccuracy(varNum);
		var += direction * Math.pow(10, -power) * Math.pow(repeatBase, iteration);
		newSolution.setElement(varNum, var);
		return newSolution;
	}
}
