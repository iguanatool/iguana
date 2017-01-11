package org.iguanatool.testobject.trace.condition;

/**
 * User: phil
 * Date: 10-Mar-2006
 * Time: 16:03:26
 */
public abstract class TraceCondition {

    protected static final double K = 1.0;
    protected int id;

    public TraceCondition(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public abstract double distance(boolean outcome);

    public boolean isTrue() {
        return distance(true) == 0;
    }

    protected final double trueDistance(double a) {
        return notEqualDistance(a, 0);
    }

    protected final double notTrueDistance(double a) {
        return equalDistance(a, 0);
    }

    protected final double refEqualsDistance(boolean refIsEqual) {
        if (refIsEqual) return 0;
        else return K;
    }

    protected final double refNotEqualsDistance(boolean refIsEqual) {
        if (refIsEqual) return K;
        else return 0;
    }

    protected final double equalDistance(double a, double b) {
        double dist = Math.abs(a - b);
        if (dist == 0) return 0;
        else return dist + K;
    }

    protected final double notEqualDistance(double a, double b) {
        double dist = Math.abs(a - b);
        if (dist != 0) return 0;
        else return K;
    }

    protected final double lessThanDistance(double a, double b) {
        double dist = a - b;
        if (dist < 0) return 0;
        else return dist + K;
    }

    protected final double lessThanOrEqualDistance(double a, double b) {
        double dist = a - b;
        if (dist <= 0) return 0;
        else return dist + K;
    }

    protected final double greaterThanDistance(double a, double b) {
        double dist = b - a;
        if (dist < 0) return 0;
        else return dist + K;
    }

    protected final double greaterThanOrEqualDistance(double a, double b) {
        double dist = b - a;
        if (dist <= 0) return 0;
        else return dist + K;
    }
}
