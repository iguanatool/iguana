package org.iguanatool.testobject.structure.condition;

public class AtomicCondition extends Condition {

    static final long serialVersionUID = 1422703707132894549L;

    protected int id;

    public AtomicCondition() {
        this.id = 0;
    }

    public AtomicCondition(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void accept(ConditionVisitor visitor) {
        visitor.visit(this);
    }

    public String toString() {
        return String.valueOf(id);
    }
}
