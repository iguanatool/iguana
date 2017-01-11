package org.iguanatool.search.pso;

import org.iguanatool.search.Search;
import org.iguanatool.search.SearchMonitor;
import org.iguanatool.search.SearchResult;
import org.iguanatool.search.objective.ObjectiveFunction;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.NumericalSolution;
import org.iguanatool.search.solution.SolutionType;

public class Swarm extends Search {

    private int numParticles;
    private double w, c1, c2;

    public Swarm(RandomNumberGenerator randomNumberGenerator, int maxEvaluations) {
        super(randomNumberGenerator, maxEvaluations);
    }

    public void setNumParticles(int numParticles) {
        this.numParticles = numParticles;
    }

    public void setW(double w) {
        this.w = w;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public SearchResult search(SolutionType candidateSolutionType,
                               ObjectiveFunction objectiveFunction) {

        SearchMonitor monitor = new SearchMonitor(objectiveFunction, maxEvaluations);
        Particle[] particles = new Particle[this.numParticles];
        int i = 0;

        while (i < particles.length && !monitor.terminate()) {
            particles[i] = new Particle(candidateSolutionType, randomNumberGenerator, objectiveFunction);
            particles[i].initialize();
            particles[i].setLog(new ParticleLog());
            i++;
        }

        while (!monitor.terminate()) {
            i = 0;
            while (i < particles.length && !monitor.terminate()) {
                NumericalSolution best = (NumericalSolution) monitor.getBestSolution();
                particles[i].move(best, w, c1, c2);
                i++;
            }
        }

        return monitor.getSearchResult();
    }
}
