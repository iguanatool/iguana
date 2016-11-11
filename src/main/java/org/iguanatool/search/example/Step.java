package org.iguanatool.search.example;

import org.iguanatool.search.objective.NumericalMinimizingObjectiveValue;
import org.iguanatool.search.objective.ObjectiveValue;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.search.solution.SolutionType;

public class Step extends Example {
	
	private static int VECTOR_SIZE = 10;
	private static int TOP = 75;
	private static int BOTTOM = 50;
	
    public String getObjectiveDescription() {
    	return "Optimise a vector so that all values are in the range "+BOTTOM+"-"+TOP;
    }
    
    protected ObjectiveValue computeObjectiveValue(Solution soln) {
        NumericalSolution soln2 = (NumericalSolution) soln;
        int fitness = 0;
        for (int i=0; i < VECTOR_SIZE; i++) {
            if (soln2.getElement(i) < BOTTOM || soln2.getElement(i) > TOP) {
            	fitness ++;
            }
        }
        return new NumericalMinimizingObjectiveValue(fitness, 0);
    }

    protected SolutionType makeCandidateSolutionType() {
    	return new NumericalSolutionType(VECTOR_SIZE, 0, 250, 0);
    }
       
    public static void main(String[] args) {
    	Example.run(new Step(), args);
    }	
}
