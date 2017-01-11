package org.iguanatool.testobject.structure.condition;

public interface ConditionVisitor {

    void visit(AtomicCondition atomicCondition);

    void visit(NotCondition notCondition);

    void visit(AndCondition andCondition);

    void visit(OrCondition orCondition);
}
