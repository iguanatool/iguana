package org.iguanatool.search.randomnumbergenerator;

import org.apache.commons.math3.random.MersenneTwister;

public class MersenneTwisterRandomNumberGenerator extends RandomNumberGenerator {

    private MersenneTwister mt;

    private long seed;

    public MersenneTwisterRandomNumberGenerator() {
        mt = new MersenneTwister();
    }

    @Override
    public void setSeed(long seed) {
        mt.setSeed(this.seed = seed);
    }

    @Override
    public long getInitialSeed() {
        return seed;
    }

    @Override
    public void reset() {
        setSeed(seed);
    }

    @Override
    public boolean nextBoolean() {
        return mt.nextBoolean();
    }

    @Override
    public int nextInt() {
        return mt.nextInt();
    }

    @Override
    public int nextInt(int max) {
        return mt.nextInt(max);
    }

    @Override
    public int nextInt(int min, int max) {
        return min + nextInt(max - min);
    }

    @Override
    public double nextDouble() {
        return mt.nextDouble();
    }

    @Override
    public double nextDouble(double max) {
        return max * nextDouble();
    }

    @Override
    public double nextDouble(double min, double max) {
        return min + nextDouble(max - min);
    }

    @Override
    public double nextGaussian() {
        return mt.nextGaussian();
    }

    @Override
    public double nextGaussian(double standardDeviation) {
        return standardDeviation * nextGaussian();
    }
}
