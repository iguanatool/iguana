package org.iguanatool.testobject.trace.condition;

/**
 * User: phil
 * Date: 13-Mar-2006
 * Time: 20:41:38
 */
public class NotTrueTraceCondition extends TraceCondition {

    private double a;

    public NotTrueTraceCondition(int id, double a) {
        super(id);
        this.a = a;
    }

    public double distance(boolean outcome) {
        return outcome ? notTrueDistance(a) : trueDistance(a);
    }

    public String toString() {
        return "NotTrue (" + id + ") a=" + a +
                " T:" + distance(true) +
                " F:" + distance(false);
    }

}
