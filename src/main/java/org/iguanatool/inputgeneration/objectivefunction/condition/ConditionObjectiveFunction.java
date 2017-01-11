package org.iguanatool.inputgeneration.objectivefunction.condition;

import org.iguanatool.inputgeneration.objectivefunction.InputGenerationObjectiveValue;
import org.iguanatool.testobject.structure.condition.Condition;
import org.iguanatool.testobject.trace.condition.TraceCondition;

import java.util.List;

/**
 * User: phil
 * Date: 13-Jun-2006
 * Time: 15:35:00
 */
public abstract class ConditionObjectiveFunction {

    protected InputGenerationObjectiveValue trueOutcome;
    protected InputGenerationObjectiveValue falseOutcome;

    public abstract void compute(Condition condition,
                                 List<TraceCondition> traceConditions);

    public InputGenerationObjectiveValue getTrueOutcome() {
        return trueOutcome;
    }

    public InputGenerationObjectiveValue getFalseOutcome() {
        return falseOutcome;
    }

    public InputGenerationObjectiveValue getOutcome(boolean outcome) {
        if (outcome) {
            return trueOutcome;
        } else {
            return falseOutcome;
        }
    }
}
