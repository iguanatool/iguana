package org.iguanatool.testobject.trace.condition;

/**
 * User: phil
 * Date: 13-Mar-2006
 * Time: 20:34:59
 */
public class IsTrueTraceCondition extends TraceCondition {

    private double a;

    public IsTrueTraceCondition(int id, double a) {
        super(id);
        this.a = a;
    }

    public double distance(boolean outcome) {
        return outcome ? trueDistance(a) : notTrueDistance(a);
    }

    public String toString() {
        return "IsTrue ("+id+") a="+a+
               " T:"+distance(true)+
               " F:"+distance(false);
    }
}
