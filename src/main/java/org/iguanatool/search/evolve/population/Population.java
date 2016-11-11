package org.iguanatool.search.evolve.population;

import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.search.solution.SolutionType;

import java.util.List;

/**
 * User: phil
 * Date: 23-Mar-2006
 * Time: 10:19:38
 */
public abstract class Population {

    private String id;

    public Population() {
    }

    public Population(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public abstract void initialize(SolutionType candidateSolutionType,
			   						ObjectiveFunction objectiveFunction,
			   						SearchMonitor monitor);

    public abstract void evolve();

    public abstract List<Solution> getIndividuals();

    public String toString() {
        if (id != null) {
            return id;
        } else {
            return super.toString();
        }
    }
}
