package org.iguanatool.search.evolve.competition;

import org.iguanatool.search.evolve.population.CoPopulation;
import org.iguanatool.search.evolve.population.SubPopulation;
import org.iguanatool.search.evolve.population.rank.PopulationRankingMethod;
import org.iguanatool.search.evolve.rank.RankingMethod;
import org.iguanatool.search.evolve.rank.RankingUtil;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.search.solution.Solution;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * User: phil
 * Date: 22-Feb-2006
 * Time: 11:40:20
 */
public class RankingCompetitionManager extends CompetitionManager {

    private RankingMethod individualRanker;
    private PopulationRankingMethod populationRanker;
    private double percentageMaxTransfer;
    private int subpopulationMinimum;
    private RandomNumberGenerator randomNumberGenerator;

    public RankingCompetitionManager(int competeEvery,
                                     RankingMethod individualRanker,
                                     PopulationRankingMethod populationRanker,
                                     double percentageMaxTransfer,
                                     int subpopulationMinimum,
                                     RandomNumberGenerator randomNumberGenerator) {
        super(competeEvery);
        this.individualRanker = individualRanker;
        this.populationRanker = populationRanker;
        this.percentageMaxTransfer = percentageMaxTransfer;
        this.subpopulationMinimum = subpopulationMinimum;
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void monitor(CoPopulation coPopulation) {

        List<SubPopulation> populations = coPopulation.getSubPopulations();

        computeFitness(populations);
        computeProgress(populations);
    }

    private void computeProgress(List<SubPopulation> populations) {
        int i;
        Vector<SubPopulation> rank = new Vector<SubPopulation>(populations);
        Collections.sort(rank, SubPopulation.FITNESS_COMPARATOR);
        i = 0;
        for (SubPopulation p : rank) {
            setProgress(p, rank.size() - i);
            i++;
        }
    }

    private void computeFitness(List<SubPopulation> populations) {
        double[] populationFitness = populationFitness(populations);

        int i = 0;
        for (SubPopulation p : populations) {
            p.setFitness(populationFitness[i]);
            i++;
        }
    }

    private void setProgress(SubPopulation p, int rank) {
        double progress = 0.9 * p.getProgress() + 0.1 * rank;
        p.setProgress(progress);
    }

    private double[] populationFitness(List<SubPopulation> populations) {

        int numPopulations = populations.size();

        Vector<PopulationCandidateSolution> populationIndividuals
                = getIndividuals(populations);

        int numIndividuals = populationIndividuals.size();

        // sort from worst to best
        Collections.sort(populationIndividuals);

        // get ranking
        Vector<Solution> individuals = new Vector<Solution>();
        for (PopulationCandidateSolution pInd : populationIndividuals) {
            individuals.add(pInd.candidateSolution);
        }
        double[] fitness = new double[numIndividuals];
        individualRanker.rank(individuals, fitness);

        // now go through and find average fitnesses
        int[] indsPerPop = new int[numPopulations];
        double[] fitnessSum = new double[numPopulations];
        double[] populationFitness = new double[numPopulations];
        for (int i = 0; i < numIndividuals; i++) {
            PopulationCandidateSolution pInd = populationIndividuals.elementAt(i);
            indsPerPop[pInd.populationID]++;
            fitnessSum[pInd.populationID] += fitness[i];
        }
        for (int i = 0; i < numPopulations; i++) {
            populationFitness[i] = fitnessSum[i] / indsPerPop[i];
        }

        return populationFitness;
    }

    private Vector<PopulationCandidateSolution>
    getIndividuals(List<SubPopulation> populations) {

        Vector<PopulationCandidateSolution> populationIndividuals
                = new Vector<PopulationCandidateSolution>();

        int i = 0;
        for (SubPopulation p : populations) {
            Vector<Solution> individuals =
                    new Vector<Solution>(p.getIndividuals());

            for (Solution ind : individuals) {
                populationIndividuals.add(
                        new PopulationCandidateSolution(ind, i));
            }

            i++;
        }

        return populationIndividuals;
    }

    protected void redistribute(CoPopulation coPopulation) {
        Vector<SubPopulation> populations = new Vector<SubPopulation>(coPopulation.getSubPopulations());
        double[] proportion = computeProportionateFitness(populations);
        int[] reallocation = computeReallocation(populations, proportion);
        Vector<Solution> pool = extractIndsFromWorst(populations, reallocation);
        insertIndsIntoBest(populations, reallocation, pool);
    }

    private double[] computeProportionateFitness(Vector<SubPopulation> populations) {
        double[] fitness = new double[populations.size()];
        populationRanker.rank(populations, fitness);
        double[] proportion = RankingUtil.proportion(fitness);
        return proportion;
    }

    private void insertIndsIntoBest(Vector<SubPopulation> populations,
                                    int[] reallocation,
                                    Vector<Solution> pool) {

        int i = 0;
        for (SubPopulation p : populations) {
            if (reallocation[i] > 0) {
                for (int j = 0; j < reallocation[i]; j++) {
                    int index = randomNumberGenerator.nextInt(pool.size());
                    Solution ind = pool.elementAt(index);
                    pool.removeElementAt(index);
                    p.addIndividual(ind);
                }
            }
            i++;
        }
    }

    private Vector<Solution> extractIndsFromWorst(Vector<SubPopulation> populations,
                                                  int[] reallocation) {

        Vector<Solution> pool = new Vector<Solution>();
        int i = 0;
        for (SubPopulation p : populations) {
            if (reallocation[i] < 0) {
                for (int j = 0; j < -reallocation[i]; j++) {
                    pool.addElement(p.removeIndividual(randomNumberGenerator.nextInt(p.getNumIndividuals())));
                }
            }
            i++;
        }
        return pool;
    }

    private int[] computeReallocation(Vector<SubPopulation> populations,
                                      double[] proportion) {
        // find out the potential investments of each population,
        // and the total resource
        int numPopulations = populations.size();
        int[] invested = new int[numPopulations];
        int totalResource = 0;
        int i = 0;
        for (SubPopulation p : populations) {
            int resource = (int) Math.round(percentageMaxTransfer * p.getNumIndividuals());
            if (p.getNumIndividuals() - resource < subpopulationMinimum) {
                resource = p.getNumIndividuals() - subpopulationMinimum;
            }
            invested[i] = resource;
            totalResource += resource;
            i++;
        }

        // compute the reallocation
        int[] reallocation = new int[numPopulations];
        for (i = 0; i < numPopulations; i++) {
            reallocation[i] = ((int) Math.round(proportion[i] * totalResource))
                    - invested[i];
        }

        // check reallocation adds up to 1 due to rounding errors :-(
        int sum = 0;
        for (i = 0; i < numPopulations; i++) {
            sum += reallocation[i];
        }
        if (sum != 0) {
            reallocation[randomNumberGenerator.nextInt(numPopulations)] += -sum;
        }
        return reallocation;
    }
}

class PopulationCandidateSolution implements Comparable<PopulationCandidateSolution> {

    public Solution candidateSolution;
    public int populationID;

    public PopulationCandidateSolution(Solution candidateSolution, int populationID) {
        this.candidateSolution = candidateSolution;
        this.populationID = populationID;
    }

    public int compareTo(PopulationCandidateSolution soln2) {
        return candidateSolution.compareTo(soln2.candidateSolution);
    }
}