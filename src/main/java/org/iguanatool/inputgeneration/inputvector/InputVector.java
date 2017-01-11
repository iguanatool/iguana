package org.iguanatool.inputgeneration.inputvector;

import org.iguanatool.search.solution.NumericalSolution;

/**
 * User: phil
 * Date: 08-Feb-2007
 * Time: 20:19:57
 */
public class InputVector extends NumericalSolution {

    protected InputVectorType inputVectorType;

    public InputVector(InputVectorType type) {
        super(type);
        this.inputVectorType = type;
    }

    public double[] getInputValues() {
        double[] excludedInputValues = inputVectorType.getExcludedInputValues();
        boolean[] variableInclusion = inputVectorType.getVariableInclusion();

        double[] inputValues = new double[elements.length + excludedInputValues.length];

        int elementsPos = 0;
        int inputValuesPos = 0;

        for (int i = 0; i < variableInclusion.length; i++) {
            if (variableInclusion[i]) {
                inputValues[i] = getElement(elementsPos);
                elementsPos++;
            } else {
                inputValues[i] = excludedInputValues[inputValuesPos];
                inputValuesPos++;
            }
        }

        return inputValues;
    }

    public Object clone() {
        InputVector clone = new InputVector(inputVectorType);
        for (int i = 0; i < elements.length; i++) {
            clone.elements[i] = elements[i];
        }
        return clone;
    }
}
