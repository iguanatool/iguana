package org.iguanatool.inputgeneration.objectivefunction.condition;

import org.iguanatool.inputgeneration.objectivefunction.InputGenerationObjectiveValue;
import org.iguanatool.testobject.structure.condition.*;
import org.iguanatool.testobject.trace.condition.TraceCondition;

import java.util.List;

/**
 * User: phil
 * Date: 12-Jun-2006
 * Time: 16:18:35
 */

public class StdConditionObjectiveFunction extends ConditionObjectiveFunction
                                           implements ConditionVisitor {

    private List<TraceCondition> traceConditions;

    public void compute(Condition condition,
                        List<TraceCondition> traceConditions) {
        this.traceConditions = traceConditions;
        condition.accept(this);
    }

    public void visit(AtomicCondition ac) {
        for (TraceCondition tc : traceConditions) {
            if (tc.getID() == ac.getID()) {
                trueOutcome = new InputGenerationObjectiveValue(0, 0, 1, tc.distance(true));
                falseOutcome = new InputGenerationObjectiveValue(0, 0, 1, tc.distance(false));
                return;
            }
        }
        trueOutcome  = new InputGenerationObjectiveValue(0, 1, 1, 0);
        falseOutcome = new InputGenerationObjectiveValue(0, 1, 1, 0);
    }

    public void visit(NotCondition notCondition) {
        StdConditionObjectiveFunction fun = new StdConditionObjectiveFunction();
        fun.compute(notCondition.getSubCondition(), traceConditions);

        trueOutcome = fun.getFalseOutcome();
        falseOutcome = fun.getTrueOutcome();
    }

    public void visit(AndCondition andCondition) {
        composedCondition(andCondition);
    }

    public void visit(OrCondition orCondition) {
        composedCondition(orCondition);
    }

    private void composedCondition(ComposedCondition composedCondition) {
        List<Condition> subConditions = composedCondition.getSubConditions();
        int total = 0;
        int unencountered = 0;
        double trueDistance = 0;
        double falseDistance = 0;

        for (Condition c: subConditions) {
            StdConditionObjectiveFunction fun = new StdConditionObjectiveFunction();
            fun.compute(c, traceConditions);

            InputGenerationObjectiveValue t = fun.getTrueOutcome();
            InputGenerationObjectiveValue f = fun.getFalseOutcome();

            if (composedCondition instanceof AndCondition) {
                trueDistance = andDistance(t, trueDistance);
                falseDistance = orDistance(f, falseDistance, total);
            }

            if (composedCondition instanceof OrCondition) {
                trueDistance = orDistance(t, trueDistance, total);
                falseDistance = andDistance(f, falseDistance);
            }

            if (t.getUnencounteredConditions() > 0) {
                unencountered += t.getUnencounteredConditions();
            }

            total += t.getTotalConditions();
        }

        trueOutcome  = new InputGenerationObjectiveValue(0, unencountered, total, trueDistance);
        falseOutcome = new InputGenerationObjectiveValue(0, unencountered, total, falseDistance);
    }

    private double andDistance(InputGenerationObjectiveValue v, double distance) {
        if (v.getDistance() > 0) {
            distance = v.getDistance();
        }
        return distance;
    }

    private double orDistance(InputGenerationObjectiveValue v, double distance, int total) {
        if (total == 0 || v.getDistance() < distance) {
            distance = v.getDistance();
        }
    	
    	// the following code removes the OR plateaux problem
    	//if (v.getDistance() == 0) {
    	//	distance = 0;
    	//} else { 
        //    distance += v.getDistance();
        //}
        
        return distance;
    }
}
