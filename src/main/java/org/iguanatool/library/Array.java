package org.iguanatool.library;

import java.util.List;

public class Array {

    public static int[] intListToArray(List<Integer> intVector) {
        Object[] arr = intVector.toArray();
        int[] intArray = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArray[i] = (Integer) arr[i];
        }
        return intArray;
    }

    public static double[] doubleListToArray(List<Double> doubleVector) {
        Object[] arr = doubleVector.toArray();
        double[] doubleArray = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            doubleArray[i] = (Double) arr[i];
        }
        return doubleArray;
    }

    public static int[] doubleArrayToIntArray(double[] doubleArray) {
        return doubleArrayToIntArray(doubleArray, 0, doubleArray.length - 1);
    }

    public static int[] doubleArrayToIntArray(double[] doubleArray, int start, int end) {
        int[] intArray = new int[end - start + 1];
        for (int i = start; i <= end; i++) {
            intArray[i] = (int) doubleArray[i];
        }
        return intArray;
    }

    public static int[] doubleArrayToString(double[] doubleArray) {
        return doubleArrayToIntArray(doubleArray, 0, doubleArray.length - 1);
    }

    public static String doubleArrayToString(double[] doubleArray, int start, int end) {
        String str = "";
        for (int i = start; i <= end; i++) {
            str += (char) doubleArray[i];
        }
        return str;
    }
}
