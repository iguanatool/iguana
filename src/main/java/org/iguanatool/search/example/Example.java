package org.iguanatool.search.example;

import org.iguanatool.search.Search;
import org.iguanatool.search.SearchFactory;
import org.iguanatool.search.SearchResult;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.randomnumbergenerator.SimpleRandomNumberGenerator;
import org.iguanatool.search.solution.SolutionType;

public abstract class Example extends ObjectiveFunction {
	
	protected abstract SolutionType makeCandidateSolutionType();
		
    protected static void run(Example example, String[] args) {   	
    	RandomNumberGenerator rnd = new SimpleRandomNumberGenerator();
    	
    	if (args.length > 1) {
    		long seed = Long.parseLong(args[1]);
    		rnd.setSeed(seed);
    	}
    	
    	int maxEvaluations = 100000;
    	if (args.length > 2) {
    		maxEvaluations = Integer.parseInt(args[2]);
    	}
    	
    	Search s = SearchFactory.instantiateSearch(args[0], rnd, maxEvaluations);
    	SearchResult r = s.search(example.makeCandidateSolutionType(), example);
    	
    	System.out.println("Success: "+(r.wasSuccess()?"Yes":"No"));
    	System.out.println("Best solution: "+r.getBestSolution());
    	System.out.println("Objective Value: "+r.getBestSolution().getObjectiveValue().getNumericalValue());
    	System.out.println("Number of evaluations: "+r.getNumEvaluations());
    }		
}
