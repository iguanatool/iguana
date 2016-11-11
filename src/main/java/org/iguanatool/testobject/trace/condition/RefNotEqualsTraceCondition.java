package org.iguanatool.testobject.trace.condition;

/**
 * User: phil
 * Date: 23-Aug-2006
 * Time: 09:22:11
 */
public class RefNotEqualsTraceCondition extends TraceCondition {

    private boolean refEqualsIsTrue;

    public RefNotEqualsTraceCondition(int id, boolean refEqualsIsTrue) {
        super(id);
        this.refEqualsIsTrue = refEqualsIsTrue;
    }

    public double distance(boolean outcome) {
        return outcome ? refNotEqualsDistance(refEqualsIsTrue) : refEqualsDistance(refEqualsIsTrue);
    }

    public String toString() {
        return "RefNotEqualsDistance ("+id+") equals="+refEqualsIsTrue+
               " T:"+distance(true)+
               " F:"+distance(false);
    }
}
