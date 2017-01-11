package org.iguanatool.search.pso;

import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.NumericalSolutionType;
import org.iguanatool.search.solution.Solution;
import org.iguanatool.search.solution.SolutionType;

public class Particle {

    private NumericalSolutionType solutionType;
    private NumericalSolution current, best;
    private int dimensions;
    private double[] velocities;
    private RandomNumberGenerator randomNumberGenerator;
    private ObjectiveFunction objectiveFunction;
    private ParticleLog log;

    public Particle(SolutionType candidateSolutionType,
                    RandomNumberGenerator randomNumberGenerator,
                    ObjectiveFunction objectiveFunction) {
        this.randomNumberGenerator = randomNumberGenerator;
        this.solutionType = (NumericalSolutionType) candidateSolutionType;
        this.objectiveFunction = objectiveFunction;
    }

    public Solution getSolution() {
        return current;
    }

    public Solution getBest() {
        return best;
    }

    public void setLog(ParticleLog log) {
        this.log = log;
    }

    private void evaluateObjectiveValue(ObjectiveFunction objectiveFunction) {
        current.evaluateObjectiveValue(objectiveFunction);
        if (best == null || current.betterThan(best)) {
            best = current;
        }
    }

    public void initialize() {
        current = (NumericalSolution) solutionType.generateRandomSolution(randomNumberGenerator);
        dimensions = solutionType.getVectorSize();
        velocities = new double[dimensions];
        evaluateObjectiveValue(objectiveFunction);

        if (log != null) {
            log.update(this);
            log.flush();
        }
    }

    public void move(NumericalSolution globalBest,
                     double w, double c1, double c2) {
        current = (NumericalSolution) current.clone();
        for (int i = 0; i < velocities.length; i++) {
            double r1 = randomNumberGenerator.nextDouble();
            double r2 = randomNumberGenerator.nextDouble();
            double e = current.getElement(i);

            velocities[i] = w * velocities[i] +
                    c1 * r1 * (best.getElement(i) - e) +
                    c2 * r2 * (globalBest.getElement(i) - e);

            current.setElement(i, e + velocities[i]);
        }
        evaluateObjectiveValue(objectiveFunction);

        if (log != null) {
            log.update(this, globalBest);
            log.flush();
        }
    }
}
