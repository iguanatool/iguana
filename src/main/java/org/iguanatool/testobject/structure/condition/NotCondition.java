package org.iguanatool.testobject.structure.condition;

public class NotCondition extends Condition {

    static final long serialVersionUID = 541916354537052881L;

    protected Condition subCondition;

    public NotCondition() {
    }

    public NotCondition(Condition subCondition) {
        setSubCondition(subCondition);
    }

    public Condition getSubCondition() {
        return subCondition;
    }

    public void setSubCondition(Condition subCondition) {
        this.subCondition = subCondition;
    }

    public void accept(ConditionVisitor visitor) {
        visitor.visit(this);
    }

    public String toString() {
        return "(!" + subCondition.toString() + ")";
    }
}
