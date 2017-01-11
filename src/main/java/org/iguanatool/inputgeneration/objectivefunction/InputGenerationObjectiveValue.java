package org.iguanatool.inputgeneration.objectivefunction;

import org.iguanatool.search.objective.ObjectiveValue;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:49:47
 */

public class InputGenerationObjectiveValue extends ObjectiveValue {

    public static final InputGenerationObjectiveValue IDEAL = new InputGenerationObjectiveValue(0, 0, 0, 0);
    public static final InputGenerationObjectiveValue WORST = new InputGenerationObjectiveValue(1000, 0, 0, 0);
    private int approachLevel;
    private int totalConditions;
    private int unencounteredConditions;
    private double distance;

    public InputGenerationObjectiveValue(int approachLevel,
                                         int unencounteredConditions,
                                         int totalConditions,
                                         double distance) {

        this.approachLevel = approachLevel;
        this.unencounteredConditions = unencounteredConditions;
        this.totalConditions = totalConditions;
        this.distance = distance;
    }

    public int getApproachLevel() {
        return approachLevel;
    }

    public void setApproachLevel(int approachLevel) {
        this.approachLevel = approachLevel;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getTotalConditions() {
        return totalConditions;
    }

    public void setTotalConditions(int totalConditions) {
        this.totalConditions = totalConditions;
    }

    public int getUnencounteredConditions() {
        return unencounteredConditions;
    }

    public void setUnencounteredConditions(int unencounteredConditions) {
        this.unencounteredConditions = unencounteredConditions;
    }

    public boolean isIdeal() {
        return approachLevel == 0 &&
                distance == 0;
    }

    public int compareTo(ObjectiveValue objectiveValue) {
        if (isIdeal() && !objectiveValue.isIdeal()) {
            return 1;
        } else if (isIdeal() && objectiveValue.isIdeal()) {
            return 0;
        } else if (!isIdeal() && objectiveValue.isIdeal()) {
            return -1;
        } else {
            if (objectiveValue instanceof InputGenerationObjectiveValue) {
                InputGenerationObjectiveValue v = (InputGenerationObjectiveValue) objectiveValue;
                int compare = compareApproachLevel(v);
                if (compare == 0) {
                    compare = compareUnencounteredConditions(v);
                    if (compare == 0) {
                        return compareDistance(v);
                    } else {
                        return compare;
                    }
                } else {
                    return compare;
                }
            } else {
                throw new RuntimeException("Object passed to compare method of InputGenerationObjectiveValue not instances of that class");
            }
        }
    }

    private int compareApproachLevel(InputGenerationObjectiveValue v) {
        if (approachLevel < v.getApproachLevel()) {
            return 1;
        } else if (approachLevel == v.getApproachLevel()) {
            return 0;
        } else {
            return -1;
        }
    }

    private int compareUnencounteredConditions(InputGenerationObjectiveValue v) {
        if (unencounteredConditions < v.getUnencounteredConditions()) {
            return 1;
        } else if (unencounteredConditions == v.getUnencounteredConditions()) {
            return 0;
        } else {
            return -1;
        }
    }

    private int compareDistance(InputGenerationObjectiveValue v) {
        if (distance < v.getDistance()) {
            return 1;
        } else if (distance == v.getDistance()) {
            return 0;
        } else {
            return -1;
        }
    }

    public double getNumericalValue() {
        double unencounteredDistance = unencounteredConditions / (double) totalConditions;
        double conditionDistance = normalizeDistance();

        double val = approachLevel +
                unencounteredDistance +
                conditionDistance / totalConditions;

        return val;
    }

    private double normalizeDistance() {
        return 1 - Math.pow(1.001, -distance);
    }

    public boolean equals(Object obj) {
        if (obj instanceof InputGenerationObjectiveValue) {
            return compareTo((ObjectiveValue) obj) == 0;
        } else {
            return false;
        }
    }

    public String toString() {
        return "A:" + approachLevel +
                " U:" + unencounteredConditions + "/" + totalConditions +
                " D:" + distance;
    }
}
