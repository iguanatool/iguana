package org.iguanatool.search.randomnumbergenerator;

import java.util.Random;

public class SimpleRandomNumberGenerator extends RandomNumberGenerator {
    private Random random;
    private long initialSeed;

    public SimpleRandomNumberGenerator() {
        double rnd = Math.random();
		initialSeed = (long) (rnd * Math.pow(10, 16));
        random = new Random(initialSeed);
    }

    public void setSeed(long seed) {
    	initialSeed = seed;
        random.setSeed(seed);
    }

    public long getInitialSeed() {
        return initialSeed;
    }

    public void reset() {
    	setSeed(initialSeed);
    }
    
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    public int nextInt() {
        return random.nextInt();
    }

    public int nextInt(int max) {
        return random.nextInt(max);
    }

    public int nextInt(int min, int max) {
        int trueMax = max - min;
        return nextInt(trueMax) + min;
    }

    public double nextDouble() {
        return random.nextDouble();
    }

    public double nextDouble(double max) {
        return nextDouble() * max;
    }

    public double nextDouble(double min, double max) {
        return (nextDouble() * (max-min)) + min;
    }

    public double nextGaussian() {
        return random.nextGaussian();
    }

    public double nextGaussian(double standardDeviation) {
        return random.nextGaussian() * standardDeviation;
    }
}
