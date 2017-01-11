package org.iguanatool.search.solution;

import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:24:06
 */
public abstract class SolutionType {

    protected int vectorSize;

    // provided so that child classes can use a default constructor
    protected SolutionType() {
    }

    public SolutionType(int vectorSize) {
        this.vectorSize = vectorSize;
    }

    public int getVectorSize() {
        return vectorSize;
    }

    public abstract Solution solutionInstanceOfThisType();

    public abstract Solution generateRandomSolution(RandomNumberGenerator randomNumberGenerator);
}
