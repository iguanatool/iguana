package org.iguanatool.testobject.trace.condition;

/**
 * User: phil
 * Date: 23-Aug-2006
 * Time: 09:22:11
 */
public class RefEqualsTraceCondition extends TraceCondition {

    private boolean refEqualsIsTrue;

    public RefEqualsTraceCondition(int id, boolean refEqualsIsTrue) {
        super(id);
        this.refEqualsIsTrue = refEqualsIsTrue;
    }

    public double distance(boolean outcome) {
        return outcome ? refEqualsDistance(refEqualsIsTrue) : refNotEqualsDistance(refEqualsIsTrue);
    }

    public String toString() {
        return "RefEqualsDistance ("+id+") equals="+refEqualsIsTrue+
               " T:"+distance(true)+
               " F:"+distance(false);
    }
}
