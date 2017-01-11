package org.iguanatool.testobject.trace;

import org.iguanatool.inputgeneration.objectivefunction.condition.ConditionObjectiveFunction;
import org.iguanatool.testobject.structure.CFGNode;
import org.iguanatool.testobject.trace.condition.TraceCondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: phil
 * Date: 10-Mar-2006
 * Time: 16:03:02
 */
public class TraceNode {

    private CFGNode cfgNode;
    private List<TraceCondition> traceConditions = new ArrayList<TraceCondition>();

    private boolean conditionOutcome;

    public TraceNode() {
    }

    public CFGNode getCFGNode() {
        return cfgNode;
    }

    public void setCFGNode(CFGNode cfgNode) {
        this.cfgNode = cfgNode;
    }

    public void computeOutcome(ConditionObjectiveFunction cof) {
        if (cfgNode.getCondition() == null) {
            System.out.println(cfgNode + "here");
        }
        cof.compute(cfgNode.getCondition(), traceConditions);
    }

    public void addCondition(TraceCondition condition) {
        traceConditions.add(condition);
    }

    public List<TraceCondition> getTraceConditions() {
        return Collections.unmodifiableList(traceConditions);
    }

    public boolean getConditionOutcome() {
        return conditionOutcome;
    }

    public void setConditionOutcome(boolean conditionOutcome) {
        this.conditionOutcome = conditionOutcome;
    }

    public String toString() {
        return "Node " + cfgNode.getID() + ", " + traceConditions.size() + " condition(s) read";
    }
}
