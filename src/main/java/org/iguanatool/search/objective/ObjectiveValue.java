package org.iguanatool.search.objective;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:28:59
 */
public abstract class ObjectiveValue implements Comparable<ObjectiveValue> {
    public abstract boolean isIdeal();

    public abstract double getNumericalValue();
}
