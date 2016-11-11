package org.iguanatool.search.solution;

import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.objective.ObjectiveValue;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:37:32
 */
public abstract class Solution implements Comparable<Solution>,
                                          Cloneable {

    protected ObjectiveValue objectiveValue;

    public Solution() {        
    }

    public ObjectiveValue getObjectiveValue() {
        return objectiveValue;
    }

    public void setObjectiveValue(ObjectiveValue objectiveValue) {
        this.objectiveValue = objectiveValue;
    }

    public void evaluateObjectiveValue(ObjectiveFunction objectiveFunction) {
        if (objectiveValue == null) {
        	objectiveValue = objectiveFunction.evaluate(this);
        }
    }

    public int compareTo(Solution soln2) {
        ObjectiveValue objectiveValue2 = soln2.getObjectiveValue();
        return objectiveValue.compareTo(objectiveValue2);
    }
    
    public boolean betterThan(Solution soln2) {
    	return compareTo(soln2) > 0;    	
    }
    
    public boolean worseThan(Solution soln2) {
    	return compareTo(soln2) < 0;
    }
    
    public boolean isIdeal() {
        return objectiveValue.isIdeal();
    }

    public abstract SolutionType getType();

    public abstract void copyElement(Solution soln2, int location);

    public abstract int getNumElements();
    
    public abstract Object clone();
}
