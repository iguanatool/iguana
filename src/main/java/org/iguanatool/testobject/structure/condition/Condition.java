package org.iguanatool.testobject.structure.condition;

import java.io.Serializable;

public abstract class Condition implements Serializable {

	private static final long serialVersionUID = 3669744034295075643L;

	public abstract void accept(ConditionVisitor visitor);    
}
