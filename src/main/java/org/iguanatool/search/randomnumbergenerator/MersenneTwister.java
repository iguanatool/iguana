package org.iguanatool.search.randomnumbergenerator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MersenneTwister extends RandomNumberGenerator {

  private Random rng;

  private long seed;

  public MersenneTwister() {
    //rng = ...
  }

  @Override
  public void setSeed(long seed) {
    rng.setSeed(this.seed = seed);
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
    return rng.nextBoolean();
  }

  @Override
  public int nextInt() {
    return rng.nextInt();
  }

  @Override
  public int nextInt(int max) {
    return rng.nextInt(max);
  }

  @Override
  public int nextInt(int min, int max) {
    return min + nextInt(max - min);
  }

  @Override
  public double nextDouble() {
    return rng.nextDouble();
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
    return rng.nextGaussian();
  }

  @Override
  public double nextGaussian(double standardDeviation) {
    return standardDeviation * nextGaussian();
  }

  public void shuffle(List<Integer> indicies) {
    Collections.shuffle(indicies, rng);
  }
}
