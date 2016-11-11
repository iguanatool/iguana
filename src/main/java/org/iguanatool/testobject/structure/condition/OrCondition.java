package org.iguanatool.testobject.structure.condition;

public class OrCondition extends ComposedCondition {

	static final long serialVersionUID = -6214345274151072212L;	
	
    public String operator() {
        return "||";
    }

    public void accept(ConditionVisitor visitor) {
        visitor.visit(this);
    }
}
