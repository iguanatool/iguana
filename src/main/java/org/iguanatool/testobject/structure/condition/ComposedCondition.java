package org.iguanatool.testobject.structure.condition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ComposedCondition extends Condition {

    private static final long serialVersionUID = -925529877113255765L;

    protected List<Condition> subConditions = new ArrayList<Condition>();

    public abstract String operator();

    public void addCondition(Condition c) {
        subConditions.add(c);
    }

    public List<Condition> getSubConditions() {
        return Collections.unmodifiableList(subConditions);
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        boolean first = true;
        output.append("(");

        for (Condition subCondition : subConditions) {
            output.append(subCondition);
            if (first) {
                output.append(" " + operator() + " ");
                first = false;
            }
        }
        output.append(")");
        return output.toString();
    }
}

