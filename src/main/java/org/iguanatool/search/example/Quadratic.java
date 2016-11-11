package org.iguanatool.search.example;

import org.iguanatool.search.objective.NumericalMinimizingObjectiveValue;
import org.iguanatool.search.objective.ObjectiveValue;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.search.solution.SolutionType;

public class Quadratic extends Example {

	private final static double A = 3;
	private final static double B = 2;
	private final static double C = -1;
	
    public String getObjectiveDescription() {
    	return "Find a root of a quadratic equation";
    }
    
    protected ObjectiveValue computeObjectiveValue(Solution soln) {
        NumericalSolution soln2 = (NumericalSolution) soln;
        double x = soln2.getElement(0);
        double y = (A * x * x) + (B * x) + C;
        return new NumericalMinimizingObjectiveValue(Math.abs(y), 0);
    }

    protected SolutionType makeCandidateSolutionType() {
    	return new NumericalSolutionType(1, -2000, 2000, 3);
    }
       
    public static void main(String[] args) {
    	Example.run(new Quadratic(), args);
    }	
}
