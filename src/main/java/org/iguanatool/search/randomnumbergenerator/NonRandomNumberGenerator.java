package org.iguanatool.search.randomnumbergenerator;

/**
 * User: phil
 * Date: 15-Feb-2006
 * Time: 17:59:34
 */
public class NonRandomNumberGenerator extends RandomNumberGenerator {

    private boolean[] booleans;
    private int booleanCounter = 0;

    private int[] integers;
    private int integerCounter = 0;

    private double[] doubles;
    private int doubleCounter = 0;

    private double[] gaussians;
    private int gaussianCounter = 0;

    public NonRandomNumberGenerator(boolean[] booleans,
                                    int[] integers,
                                    double[] doubles,
                                    double[] gaussians) {
        this.booleans  = booleans;
        this.integers  = integers;
        this.doubles   = doubles;
        this.gaussians = gaussians;
    }

    public NonRandomNumberGenerator(boolean[] booleans) {
        this.booleans  = booleans;
    }

    public NonRandomNumberGenerator(boolean[] booleans,
                                    double[] doubles) {
        this.booleans  = booleans;
        this.doubles   = doubles;
    }

    public NonRandomNumberGenerator(boolean[] booleans,
                                    int[] integers) {
        this.booleans = booleans;
        this.integers = integers;
    }

    public NonRandomNumberGenerator(int[] integers) {
        this.integers = integers;
    }

    public NonRandomNumberGenerator(int[] integers,
                                    double[] doubles) {
        this.integers = integers;
        this.doubles  = doubles;
    }

    public NonRandomNumberGenerator(double[] doubles) {
        this.doubles  = doubles;
    }

    public NonRandomNumberGenerator(double[] doubles,
                                    double[] gaussians) {
        this.doubles   = doubles;
        this.gaussians = gaussians;
    }

    public void setSeed(long seed) {
    }

    public long getInitialSeed() {
        return -1;
    }
    
    public void reset() {
    }

    public boolean nextBoolean() {
        boolean nextBoolean = booleans[booleanCounter];
        booleanCounter ++;
        return nextBoolean;
    }

    public double nextDouble() {
        double nextDouble = doubles[doubleCounter];
        doubleCounter ++;
        return nextDouble;
    }

    public double nextDouble(double max) {
        return nextDouble();
    }

    public double nextDouble(double min, double max) {
        return nextDouble();
    }

    public int nextInt() {
        int nextInt = integers[integerCounter];
        integerCounter ++;
        return nextInt;
    }

    public int nextInt(int max) {
        return nextInt();
    }

    public int nextInt(int min, int max) {
        return nextInt();
    }

    public double nextGaussian() {
        double nextGaussian = gaussians[gaussianCounter];
        gaussianCounter ++;
        return nextGaussian;
    }

    public double nextGaussian(double standardDeviation) {
        return nextGaussian();
    }

}
