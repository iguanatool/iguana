package org.iguanatool.search.objective;

/**
 * User: phil
 * Date: 15-Feb-2006
 * Time: 20:46:34
 */
public class NumericalMinimizingObjectiveValue extends NumericalMaximizingObjectiveValue {

    public NumericalMinimizingObjectiveValue(double fitness, double optimum) {
        super(fitness, optimum);
    }

    public NumericalMinimizingObjectiveValue(double optimum) {
        super(optimum);
    }

    public boolean isIdeal() {
        return fitness <= optimum;
    }

    public int compareTo(ObjectiveValue objectiveValue) {
        if (objectiveValue instanceof NumericalMinimizingObjectiveValue) {
            NumericalMinimizingObjectiveValue fitness2 = (NumericalMinimizingObjectiveValue) objectiveValue;

            if (getNumericalValue() > fitness2.getNumericalValue()) return -1;
            if (getNumericalValue() < fitness2.getNumericalValue()) return 1;
            return 0;

        } else {
            throw new RuntimeException("Object passed to compare method of "+this.getClass()+" not instances of that class");
        }
    }

    public String toString() {
        return "NumericalMinimizingObjectiveValue, value:"+getNumericalValue()+" optimum: "+optimum;
    }   
}
