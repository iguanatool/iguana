package org.iguanatool.testobject;

import org.iguanatool.library.Array;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InputSpecification {

    protected List<Double> min;
    protected List<Double> max;
    protected List<Integer> accuracy;

    public InputSpecification() {
        clear();
    }

    public void clear() {
        min = new ArrayList<>();
        max = new ArrayList<>();
        accuracy = new ArrayList<>();
    }

    public void addDouble(int quantity, double minVal, double maxVal, int precVal) {
        for (int i = 0; i < quantity; i++) {
            min.add(minVal);
            max.add(maxVal);
            accuracy.add(precVal);
        }
    }

    public void addDouble(double minVal, double maxVal, int precVal) {
        addDouble(1, minVal, maxVal, precVal);
    }

    public void addInt(int quantity, int minVal, int maxVal) {
        for (int i = 0; i < quantity; i++) {
            min.add((double) minVal);
            max.add((double) maxVal);
            accuracy.add(0);
        }
    }

    public void addInt(int minVal, int maxVal) {
        addInt(1, minVal, maxVal);
    }

    public double[] getArgsMin() {
        return Array.doubleListToArray(min);
    }

    public double[] getArgsMax() {
        return Array.doubleListToArray(max);
    }

    public int[] getArgsAccuracy() {
        return Array.intListToArray(accuracy);
    }

    public int getNumArgs() {
        if (min.size() != max.size() || min.size() != accuracy.size() || max.size() != accuracy.size()) {
            throw new RuntimeException("Argument min/max/precision lists do not have matching getNumArgs");
        }
        return min.size();
    }

    public BigDecimal getDomainSize() {
        BigDecimal domainSize = null;

        for (int i = 0; i < getNumArgs(); i++) {
            double varAccuracy = Math.pow(10, accuracy.get(i));
            double maxMinusMin = Math.abs(max.get(i) - min.get(i));

            BigDecimal varSize = new BigDecimal(maxMinusMin);
            varSize = varSize.multiply(new BigDecimal(varAccuracy));

            if (domainSize == null) {
                domainSize = varSize;
            } else {
                domainSize = domainSize.multiply(varSize);
            }
        }

        return domainSize;
    }

    public int getDomainSize10Power() {
        BigDecimal d = getDomainSize();
        return d.precision() - 1;
    }

    public String formatInput(double[] input) {
        String formattedInput = "";
        boolean first = true;
        formattedInput += "[";
        for (double d : input) {
            if (!first) {
                formattedInput += ", ";
            } else {
                first = false;
            }
            formattedInput += d;
        }
        formattedInput += "]";
        return formattedInput;
    }
}
