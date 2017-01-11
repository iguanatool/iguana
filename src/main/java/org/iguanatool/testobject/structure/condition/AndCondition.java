package org.iguanatool.testobject.structure.condition;

public class AndCondition extends ComposedCondition {

    static final long serialVersionUID = 3852082842071656669L;

    public String operator() {
        return "&&";
    }

    public void accept(ConditionVisitor visitor) {
        visitor.visit(this);
    }
}
