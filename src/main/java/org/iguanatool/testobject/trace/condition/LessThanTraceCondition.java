package org.iguanatool.testobject.trace.condition;

/**
 * User: phil
 * Date: 13-Mar-2006
 * Time: 20:42:18
 */
public class LessThanTraceCondition extends TraceCondition {

    private double a, b;

    public LessThanTraceCondition(int id, double a, double b) {
        super(id);
        this.a = a;
        this.b = b;
    }

    public double distance(boolean outcome) {
        return outcome ? lessThanDistance(a, b) : greaterThanOrEqualDistance(a, b);
    }

    public String toString() {
        return "LessThan ("+id+") a="+a+" b="+b+
               " T:"+distance(true)+
               " F:"+distance(false);
    }

}
