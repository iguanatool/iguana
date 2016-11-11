package org.iguanatool.search.objective;

/**
 * User: phil
 * Date: 15-Feb-2006
 * Time: 20:36:47
 */
public class NumericalMaximizingObjectiveValue extends ObjectiveValue {

    protected double fitness;
    protected double optimum;

    public NumericalMaximizingObjectiveValue(double fitness, double optimum) {
        this.fitness = fitness;
        this.optimum = optimum;
    }

    public NumericalMaximizingObjectiveValue(double optimum) {
        this.optimum = optimum;
    }

    public double getNumericalValue() {
        return fitness;
    }

    public void setValue(double fitness) {
        this.fitness = fitness;
    }

    public boolean isIdeal() {
        return fitness >= optimum;
    }

    public int compareTo(ObjectiveValue objectiveValue) {
        if (objectiveValue instanceof NumericalMaximizingObjectiveValue) {
            NumericalMaximizingObjectiveValue fitness2 = (NumericalMaximizingObjectiveValue) objectiveValue;

            if (getNumericalValue() > fitness2.getNumericalValue()) return 1;
            if (getNumericalValue() < fitness2.getNumericalValue()) return -1;
            return 0;
            
        } else {
            throw new RuntimeException("Object passed to compare method of "+this.getClass()+" not instances of that class");
        }
    }

    public String toString() {
        return "NumericalMaximizingObjectiveValue, value:"+getNumericalValue()+" optimum: "+optimum;
    }
}
