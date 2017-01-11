package org.iguanatool.inputgeneration.inputvector;

import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.testobject.InputSpecification;
import org.iguanatool.testobject.TestObject;

/**
 * User: phil
 * Date: 08-Feb-2007
 * Time: 22:07:42
 */
public class InputVectorType extends NumericalSolutionType {

    protected double[] excludedInputValues;
    protected boolean[] variableInclusion;

    public InputVectorType(TestObject testObject) {
        InputSpecification in = testObject.getInputSpecification();

        setAttributes(in.getNumArgs(),
                in.getArgsMin(),
                in.getArgsMax(),
                in.getArgsAccuracy());

        variableInclusion = new boolean[in.getNumArgs()];
        for (int i = 0; i < variableInclusion.length; i++) {
            variableInclusion[i] = true;
        }

        excludedInputValues = new double[0];
    }

    public InputVectorType(TestObject testObject,
                           boolean[] variableInclusion) {
        InputSpecification in = testObject.getInputSpecification();
        int included = 0;

        for (int i = 0; i < variableInclusion.length; i++) {
            if (variableInclusion[i]) {
                included++;
            }
        }
        double[] excludedInputValues = new double[in.getNumArgs() - included];

        double[] argsMin = in.getArgsMin();
        double[] argsMax = in.getArgsMax();
        int[] argsPrecision = in.getArgsAccuracy();

        double[] min = new double[included];
        double[] max = new double[included];
        int[] precision = new int[included];

        int includedPos = 0;
        int excludedPos = 0;

        for (int i = 0; i < variableInclusion.length; i++) {
            if (variableInclusion[i]) {
                min[includedPos] = argsMin[i];
                max[includedPos] = argsMax[i];
                precision[includedPos] = argsPrecision[i];
                includedPos++;
            } else {
                excludedInputValues[excludedPos] =
                        argsMin[i] +
                                (argsMax[i] - argsMin[i]) / 2;
                excludedPos++;
            }
        }

        setAttributes(includedPos, min, max, precision);
        this.variableInclusion = variableInclusion;
        this.excludedInputValues = excludedInputValues;
    }

    public double[] getExcludedInputValues() {
        return excludedInputValues;
    }

    public boolean[] getVariableInclusion() {
        return variableInclusion;
    }

    public Solution solutionInstanceOfThisType() {
        return new InputVector(this);
    }

    public Solution generateRandomSolution(RandomNumberGenerator r) {
        InputVector inputVector = new InputVector(this);
        inputVector.setElementsAtRandom(r);
        return inputVector;
    }
}