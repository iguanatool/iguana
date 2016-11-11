package org.iguanatool.search.local.neighbourhoodsearch;

import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Slice extends NeighbourhoodSearch {

	private RandomNumberGenerator r;

	public Slice(RandomNumberGenerator r) {
		this.r = r;
	}

	@Override
	public Solution neighbourhoodSearch(Solution solution,
			ObjectiveFunction objectiveFunction, SearchMonitor monitor) {

		Map<Double, Integer> map = new HashMap<Double, Integer>();

		NumericalSolutionType type = (NumericalSolutionType) solution.getType();

		solution = type.generateRandomSolution(r);

		int element = r.nextInt(type.getVectorSize());

		List<Double> list = new ArrayList<>();		

		int x = (int) type.getMin(element);

		while (x <= type.getMax(element)) {
			NumericalSolution s = (NumericalSolution) solution.clone();
			s.setElement(element, x);
			s.evaluateObjectiveValue(objectiveFunction);
			list.add(s.getObjectiveValue().getNumericalValue());
			x++;
		}

		java.util.ListIterator<Double> it = list.listIterator(0);

		double previous, current, next;

		while (it.hasNext()) {

			if (it.hasPrevious()) {
				previous = it.previous();
				it.next();
			} else {
				previous = Double.POSITIVE_INFINITY;
			}
			
			current = it.next();

			if (it.hasNext()) {
				next = it.next();
				it.previous();
			} else {
				next = Double.POSITIVE_INFINITY;
			}

			if (current <= previous && current <= next) {
				if (map.containsKey(current)) {
					map.put(current, map.get(current) + 1);
				} else {
					map.put(current, 1);
				}				
			}

		}
		
		System.out.print("Modality\t");

		if (map.size() == 1) {
			int frequency = (int) map.values().toArray()[0];
			if (frequency == 1) {
				System.out.println("S");			
			} else {
				System.out.println("W");
			}
		} else {
			System.out.println("M");
		}

		return solution;
	}
}