package org.iguanatool.search.solution;

import org.iguanatool.inputgeneration.objectivefunction.InputGenerationObjectiveValue;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;

import java.util.Arrays;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:28:18
 */
public class NumericalSolution extends Solution {

    protected NumericalSolutionType type;
    protected double[] elements;

    public NumericalSolution(NumericalSolutionType type) {
        this.type = type;
        elements = new double[type.getVectorSize()];
    }

    public SolutionType getType() {
        return type;
    }

    public void copyElement(Solution solution, int index) {
        NumericalSolution soln2 = (NumericalSolution) solution;
        setElement(index, soln2.getElement(index));
    }

    public double getElement(int index) {
        return getRawElement(index);
    }

    public double getRawElement(int index) {
        return elements[index];
    }

    public void setElement(int index, double value) {

        elements[index] = value;


        if (value < type.getMin(index)) {
            setObjectiveValue(new InputGenerationObjectiveValue(1000, 0, 0, type.getMin(index) - value));
        }

        if (value > type.getMax(index)) {
            setObjectiveValue(new InputGenerationObjectiveValue(1000, 0, 0, value - type.getMax(index)));
        }

    }

    public double[] getRawElements() {
        return elements;
    }

    public double[] getElements() {
        int size = this.elements.length;
        double[] genome = new double[size];
        for (int i = 0; i < size; i++) {
            genome[i] = getElement(i);
        }
        return genome;
    }

    public void setElements(double[] newElements) {
        if (elements.length != newElements.length) {
            throw new RuntimeException("New elements array does not match getNumArgs of candidate solution");
        } else {
            for (int i = 0; i < newElements.length; i++) {
                setElement(i, newElements[i]);
            }
        }
    }

    public int getNumElements() {
        return elements.length;
    }

    public void setElementAtRandom(RandomNumberGenerator r, int index) {
        setElement(index, Math.floor(r.nextDouble(type.getMin(index), type.getMax(index) + 1)));
    }

    public void setElementsAtRandom(RandomNumberGenerator r) {
        for (int i = 0; i < elements.length; i++) {
            setElementAtRandom(r, i);
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                s.append(", ");
            }
            s.append(getElement(i));
        }
        s.append("]");
        return s.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NumericalSolution)) {
            return false;
        }

        NumericalSolution soln = (NumericalSolution) obj;

        return Arrays.equals(getRawElements(), soln.getRawElements());
    }

    public Object clone() {
        NumericalSolution clone = new NumericalSolution(type);
        clone.setElements(getElements());
        return clone;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getRawElements());
    }
}
