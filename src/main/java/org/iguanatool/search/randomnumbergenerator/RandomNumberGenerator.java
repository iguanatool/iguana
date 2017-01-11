package org.iguanatool.search.randomnumbergenerator;

/**
 * User: phil
 * Date: 15-Feb-2006
 * Time: 17:56:01
 */
public abstract class RandomNumberGenerator {

    public abstract void setSeed(long seed);

    public abstract long getInitialSeed();

    public abstract void reset();

    public abstract boolean nextBoolean();

    public abstract int nextInt();

    public abstract int nextInt(int max);

    public abstract int nextInt(int min, int max);

    public abstract double nextDouble();

    public abstract double nextDouble(double max);

    public abstract double nextDouble(double min, double max);

    public abstract double nextGaussian();

    public abstract double nextGaussian(double standardDeviation);
}
