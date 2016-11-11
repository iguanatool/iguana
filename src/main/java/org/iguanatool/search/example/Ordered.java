package org.iguanatool.search.example;

import org.iguanatool.search.objective.NumericalMinimizingObjectiveValue;
import org.iguanatool.search.objective.ObjectiveValue;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.search.solution.SolutionType;

public class Ordered extends Example {
	    
	private static int VECTOR_SIZE = 10;

    public String getObjectiveDescription() {
    	return "Optimise a vector so that all values are zero";
    }
    
    protected ObjectiveValue computeObjectiveValue(Solution soln) {
        NumericalSolution soln2 = (NumericalSolution) soln;
        double dist = 0;
        for (int i=0; i < VECTOR_SIZE-1; i++) {
        	if (soln2.getElement(i+1) > soln2.getElement(i)) {
        		dist += soln2.getElement(i+1) - soln2.getElement(i);
        	}
        }

        return new NumericalMinimizingObjectiveValue(dist, 0);
    }

    protected SolutionType makeCandidateSolutionType() {
    	return new NumericalSolutionType(VECTOR_SIZE, -100, 100, 0);
    }
       
    public static void main(String[] args) {
    	Example.run(new Ordered(), args);
    }	
}