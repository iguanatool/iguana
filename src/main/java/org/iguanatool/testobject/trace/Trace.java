package org.iguanatool.testobject.trace;

import org.iguanatool.testobject.structure.CFG;
import org.iguanatool.testobject.structure.CFGNode;
import org.iguanatool.testobject.trace.condition.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: phil
 * Date: 10-Mar-2006
 * Time: 16:09:42
 */
public class Trace {

    protected List<TraceNode> path = new ArrayList<>();
    protected TraceNode currentNode;
    protected CFG cfg;

    public Trace() {
        currentNode = new TraceNode();
    }

    public void setCfg(CFG cfg) {
        this.cfg = cfg;
    }

    public List<TraceNode> getTraceNodes() {
        return Collections.unmodifiableList(path);
    }

    public boolean node(int nodeID, boolean outcome) {
        // add the cfgNode to the trace
        CFGNode cfgNode = cfg.getNode(nodeID);
        currentNode.setCFGNode(cfgNode);
        currentNode.setConditionOutcome(outcome);
        path.add(currentNode);

        // initialize for next condition
        currentNode = new TraceNode();

        return outcome;
    }

    public boolean isTrue(int id, double a) {
        TraceCondition c = new IsTrueTraceCondition(id, a);
        currentNode.addCondition(c);
        return c.isTrue();
    }

    public boolean notTrue(int id, double a) {
        TraceCondition c = new NotTrueTraceCondition(id, a);
        currentNode.addCondition(c);
        return c.isTrue();
    }

    public boolean refEquals(int id, boolean refEqualsIsTrue) {
        TraceCondition c = new RefEqualsTraceCondition(id, refEqualsIsTrue);
        currentNode.addCondition(c);
        return c.isTrue();
    }

    public boolean refNotEquals(int id, boolean refEqualsIsTrue) {
        TraceCondition c = new RefNotEqualsTraceCondition(id, refEqualsIsTrue);
        currentNode.addCondition(c);
        return c.isTrue();
    }

    public boolean equals(int id, double a, double b) {
        TraceCondition c = new EqualsTraceCondition(id, a, b);
        currentNode.addCondition(c);
        return c.isTrue();
    }

    public boolean notEquals(int id, double a, double b) {
        TraceCondition c = new NotEqualsTraceCondition(id, a, b);
        currentNode.addCondition(c);
        return c.isTrue();
    }

    public boolean lessThan(int id, double a, double b) {
        TraceCondition c = new LessThanTraceCondition(id, a, b);
        currentNode.addCondition(c);
        return c.isTrue();
    }

    public boolean lessThanOrEqual(int id, double a, double b) {
        TraceCondition c = new LessThanOrEqualsTraceCondition(id, a, b);
        currentNode.addCondition(c);
        return c.isTrue();
    }

    public boolean greaterThan(int id, double a, double b) {
        TraceCondition c = new GreaterThanTraceCondition(id, a, b);
        currentNode.addCondition(c);
        return c.isTrue();
    }

    public boolean greaterThanOrEqual(int id, double a, double b) {
        TraceCondition c = new GreaterThanOrEqualsTraceCondition(id, a, b);
        currentNode.addCondition(c);
        return c.isTrue();
    }
}
