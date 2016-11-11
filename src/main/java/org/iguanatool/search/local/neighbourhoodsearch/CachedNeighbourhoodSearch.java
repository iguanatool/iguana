/* saves the fitness values of search points so if they are rediscovered they need not be re-evaluated.
   also allows the order in which the search cycles through variables to be permuted */

package org.iguanatool.search.local.neighbourhoodsearch;

import org.iguanatool.search.SearchException;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.objective.ObjectiveValue;
import org.iguanatool.search.randomnumbergenerator.MersenneTwister;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CachedNeighbourhoodSearch extends NeighbourhoodSearch {
	
	protected MersenneTwister rng; // random number generator

	protected int nVariables;

	protected List<Integer> indicies; // order to cycle through variables

	protected Map<NumericalSolution, ObjectiveValue> cache = new HashMap<NumericalSolution, ObjectiveValue>();

	protected CachedNeighbourhoodSearch(MersenneTwister rng) {
		this.rng = rng;
	}
	
	// configure the number of variables and the order which to cycle through variables. MUST BE CALLED ONCE AT THE START OF A SEARCH
	protected void initialize(Solution solution) {
		indicies = new ArrayList<Integer>(nVariables = solution.getNumElements());
		for (int index = 0; index < nVariables; ++index) {
			indicies.add(index);
		}		
	}

	protected double getElement(NumericalSolution solution, int index) {
		return solution.getElement(indicies.get(index));
	}

	protected void setElement(NumericalSolution solution, int index, double value) {
		solution.setElement(indicies.get(index), value);
	}

	protected void evaluateObjectiveValue(NumericalSolution solution, ObjectiveFunction objectiveFunction) {
		if (cache.containsKey(solution)) {
			solution.setObjectiveValue(cache.get(solution));
		} else {
			solution.evaluateObjectiveValue(objectiveFunction);
			cache.put(solution, solution.getObjectiveValue());
			if (solution.isIdeal()) {
				throw new SearchException(new Exception()); // notify success!
			}
		}
	}
}
