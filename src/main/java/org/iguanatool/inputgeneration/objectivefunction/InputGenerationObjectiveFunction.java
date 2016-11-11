package org.iguanatool.inputgeneration.objectivefunction;

import org.iguanatool.inputgeneration.inputvector.InputVector;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.objective.ObjectiveValue;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.testobject.TestObject;
import org.iguanatool.testobject.trace.TraceNode;

import java.util.List;

/**
 * User: phil
 * Date: 19-Mar-2006
 * Time: 11:10:39
 */
public abstract class InputGenerationObjectiveFunction extends ObjectiveFunction {

    protected TestObject testObject;

    public InputGenerationObjectiveFunction(TestObject testObject) {
        this.testObject = testObject;
    }

    protected ObjectiveValue computeObjectiveValue(Solution solution) {
        return evaluateTarget(testObject.invocate(((InputVector) solution).getInputValues()));
    }

    public abstract InputGenerationObjectiveValue evaluateTarget(List<TraceNode> trace);
}
