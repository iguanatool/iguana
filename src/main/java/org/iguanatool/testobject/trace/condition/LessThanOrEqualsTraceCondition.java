package org.iguanatool.testobject.trace.condition;

/**
 * User: phil
 * Date: 13-Mar-2006
 * Time: 20:43:59
 */
public class LessThanOrEqualsTraceCondition extends TraceCondition {

    private double a, b;

    public LessThanOrEqualsTraceCondition(int id, double a, double b) {
        super(id);
        this.a = a;
        this.b = b;
    }

    public double distance(boolean outcome) {
        return outcome ? lessThanOrEqualDistance(a, b) : greaterThanDistance(a, b);
    }

    public String toString() {
        return "LessThanOrEqual ("+id+") a="+a+" b="+b+
               " T:"+distance(true)+
               " F:"+distance(false);
    }
}
