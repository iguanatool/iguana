package org.iguanatool.search.evolve.rank;

import org.iguanatool.search.SearchException;

/**
 * User: phil
 * Date: 22-Feb-2006
 * Time: 11:24:38
 */
public class RankingUtil {

    public static double sum(double[] fitness) {
        double sum = 0;
        for (double d : fitness) {
            sum += d;
        }
        return sum;
    }

    public static double[] proportion(double[] fitness) {
        double sum = sum(fitness);

        double[] proportion = new double[fitness.length];
        for (int i = 0; i < proportion.length; i++) {
            proportion[i] = fitness[i] / sum;
        }

        return proportion;
    }

    public static void linearRanking(double[] fitness,
                                     double pressure) {

        if (pressure < 1.0 || pressure > 2.0) {
            throw new SearchException("pressure can not be less than 1 or greater than 2");
        }

        int numElements = fitness.length;

        double a = (2 - pressure);
        double b = (2 * (pressure - 1));

        for (int i = 1; i <= numElements; i++) {
            double c = i - 1;
            double d = numElements - 1;

            fitness[i - 1] = a + b * c / d;
        }
    }

}
