package org.iguanatool.testobject.trace.condition;

/**
 * User: phil
 * Date: 13-Mar-2006
 * Time: 20:42:18
 */
public class GreaterThanTraceCondition extends TraceCondition {

    private double a, b;

    public GreaterThanTraceCondition(int id, double a, double b) {
        super(id);
        this.a = a;
        this.b = b;
    }

    public double distance(boolean outcome) {
        return outcome ? greaterThanDistance(a, b) : lessThanOrEqualDistance(a, b);
    }

    public String toString() {
        return "GreaterThan (" + id + ") a=" + a + " b=" + b +
                " T:" + distance(true) +
                " F:" + distance(false);
    }

}
