package org.iguanatool.testobject.structure.condition;

public interface ConditionVisitor {

    public void visit(AtomicCondition atomicCondition);

    public void visit(NotCondition notCondition);

    public void visit(AndCondition andCondition);

    public void visit(OrCondition orCondition);
}
