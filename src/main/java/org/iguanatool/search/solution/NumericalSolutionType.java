package org.iguanatool.search.solution;

import org.iguanatool.search.SearchException;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:24:16
 */
public class NumericalSolutionType extends SolutionType {

	private double[] min;
    private double[] max;
    private int[] accuracy;

    // provided so that child classes can use a default constructor
    protected NumericalSolutionType() {
    }
    
    public NumericalSolutionType(int vectorSize,
                                 double[] min,
                                 double[] max,
                                 int[] accuracy) {
    	setAttributes(vectorSize, min, max, accuracy);
    }

    public NumericalSolutionType(int vectorSize,
    							 double minForAllElements,
    							 double maxForAllElements,
    							 int accuracyForAllElements) {
    	double min[] = new double[vectorSize];
    	double max[] = new double[vectorSize];
    	int accuracy[] = new int[vectorSize];
    	
    	for (int i=0; i < vectorSize; i++) {
    		min[i] = minForAllElements;
    		max[i] = maxForAllElements;
    		accuracy[i] = accuracyForAllElements;
    	}
    	
    	setAttributes(vectorSize, min, max, accuracy);
    }
    
    protected void setAttributes(int vectorSize,
                       			 double[] min,
                       			 double[] max,
                       			 int[] accuracy) {	
        if (min.length != vectorSize) {
            throw new SearchException("length of min array does not match vector size");
        }

        if (max.length != vectorSize) {
            throw new SearchException("length of max array does not match vector size");
        }

        if (accuracy.length != vectorSize) {
            throw new SearchException("length of precision array does not match vector size");
        }

        for (int i=0; i < vectorSize; i++) {
            if (min[i] > max[i]) {
                throw new SearchException("min["+i+"] greater than max["+i+"]");
            }
        }

        this.vectorSize = vectorSize;
        this.min        = min;
        this.max        = max;
        this.accuracy   = accuracy;    
    }

    public double getMin(int index) {
        return min[index];
    }

    public double[] getMin() {
        return min;
    }

    public double getMax(int index) {
        return max[index];
    }

    public double[] getMax() {
        return max;
    }

    public int[] getAccuracy() {
    	return accuracy.clone();
    }
    
    public int getAccuracy(int index) {
        return accuracy[index];
    }

    public double getDomain(int index) {
        return max[index] - min[index];
    }
    
    public Solution solutionInstanceOfThisType()    {
        return new NumericalSolution(this);
    }

    public Solution generateRandomSolution(RandomNumberGenerator r) {
        NumericalSolution soln = new NumericalSolution(this);
        soln.setElementsAtRandom(r);
        return soln;
    }
}

