package org.iguanatool.search.local.neighbourhoodsearch;

import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.objective.ObjectiveValue;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;

/**
 * User: phil
 * Date: 17-Jun-2006
 * Time: 16:17:45
 */

public class LinearAcceleration extends NeighbourhoodSearch {

    public Solution neighbourhoodSearch(Solution solution,
                                        ObjectiveFunction objectiveFunction,
                                        SearchMonitor monitor) {

        NumericalSolution currentSolution = (NumericalSolution) solution;
        NumericalSolutionType type = (NumericalSolutionType) currentSolution.getType();

        int varNum = 0;
        int cycle = 0;
        NumericalSolution exploratory1, exploratory2, newSolution;

        while (cycle < type.getVectorSize() && !monitor.terminate()) {

            exploratory1 = makeMove(currentSolution, type, varNum, -1, objectiveFunction);
            if (exploratory1.getObjectiveValue().isIdeal()) {
                currentSolution = exploratory1;

            } else if (!monitor.terminate()) {
                exploratory2 = makeMove(currentSolution, type, varNum, 1, objectiveFunction);
                if (exploratory2.getObjectiveValue().isIdeal()) {
                    currentSolution = exploratory2;

                } else if (!monitor.terminate()) {
                    newSolution = linearAcceleration(
                            exploratory1, exploratory2, varNum, objectiveFunction, type);

                    if (newSolution != null && newSolution.betterThan(currentSolution)) {
                        currentSolution = newSolution;
                        cycle = 0;
                    } else {
                        varNum++;
                        if (varNum == type.getVectorSize()) {
                            varNum = 0;
                        }
                        cycle++;
                    }
                }
            }
        }

        return currentSolution;
    }

    private NumericalSolution linearAcceleration(NumericalSolution exploratory1,
                                                 NumericalSolution exploratory2,
                                                 int varNum,
                                                 ObjectiveFunction objectiveFunction,
                                                 NumericalSolutionType type) {
        // get gradient
        double val1 = exploratory1.getRawElement(varNum);
        double val2 = exploratory2.getRawElement(varNum);
        double obj1 = exploratory1.getObjectiveValue().getNumericalValue();
        double obj2 = exploratory2.getObjectiveValue().getNumericalValue();

        // if the objective values are the same, then we're already at the minimum
        if (obj2 - obj1 == 0) {
            return null;
        }

        double grad = (val2 - val1) / (obj2 - obj1);

        // get an extreme point and the objective value
        NumericalSolution extreme = (NumericalSolution) exploratory1.clone();
        double valExt = (obj2 < obj1) ? type.getMax(varNum) : type.getMin(varNum);
        extreme.setElement(varNum, valExt);
        extreme.evaluateObjectiveValue(objectiveFunction);
        ObjectiveValue extremeObjectiveValue = extreme.getObjectiveValue();

        if (extremeObjectiveValue.isIdeal()) {
            return extreme;
        }

        double objExt = extremeObjectiveValue.getNumericalValue();

        // solve for the minimum
        double valMin = (grad * (valExt + val2) + objExt - obj2) / (2 * grad);

		/*
		System.out.println("vnum: "+varNum);
		System.out.println("val1: "+val1);
		System.out.println("val2: "+val2);
		System.out.println("obj1: "+obj1);
		System.out.println("obj2: "+obj2);
		System.out.println("grad: "+grad);
		System.out.println("valE: "+valExt);
		System.out.println("objE: "+objExt);
		System.out.println("valM: "+valMin);
		System.out.println(valExt + val2);
		System.out.println(obj2 - objExt);
		System.exit(1);
		*/

        // return the solution
        NumericalSolution minimum = (NumericalSolution) exploratory1.clone();
        minimum.setElement(varNum, valMin);
        minimum.evaluateObjectiveValue(objectiveFunction);

        return minimum;
    }

    private NumericalSolution makeMove(NumericalSolution solution,
                                       NumericalSolutionType type,
                                       int varNum,
                                       int direction,
                                       ObjectiveFunction objectiveFunction) {

        NumericalSolution newSolution = (NumericalSolution) solution.clone();
        double varValue = newSolution.getElement(varNum);
        double accuracy = type.getAccuracy(varNum);
        double newVarValue = varValue + direction * Math.pow(10, -accuracy);
        newSolution.setElement(varNum, newVarValue);
        newSolution.evaluateObjectiveValue(objectiveFunction);
        return newSolution;
    }
}
