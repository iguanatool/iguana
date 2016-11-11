package org.iguanatool.testobject.trace.condition;

/**
 * User: phil
 * Date: 13-Mar-2006
 * Time: 20:43:59
 */
public class EqualsTraceCondition extends TraceCondition {

    private double a, b;

    private double falseDistance, trueDistance;

    public EqualsTraceCondition(int id, double a, double b) {
        super(id);
        this.a = a;
        this.b = b;

        trueDistance = equalDistance(a, b);
        falseDistance = notEqualDistance(a, b);
    }

    public double distance(boolean outcome) {
        return outcome ? trueDistance : falseDistance;
    }

    public String toString() {
        return "Equal ("+id+") a="+a+" b="+b+
               " T:"+distance(true)+
               " F:"+distance(false);
    }
}
