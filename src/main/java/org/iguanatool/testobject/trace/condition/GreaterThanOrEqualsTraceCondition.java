package org.iguanatool.testobject.trace.condition;

/**
 * User: phil
 * Date: 13-Mar-2006
 * Time: 20:43:59
 */
public class GreaterThanOrEqualsTraceCondition extends TraceCondition {

    private double a, b;

    public GreaterThanOrEqualsTraceCondition(int id, double a, double b) {
        super(id);
        this.a = a;
        this.b = b;
    }

    public double distance(boolean outcome) {
        return outcome ? greaterThanOrEqualDistance(a, b) : lessThanDistance(a, b);
    }

    public String toString() {
        return "GreaterThanOrEqual ("+id+") a="+a+" b="+b+
               " T:"+distance(true)+
               " F:"+distance(false);
    }
}
