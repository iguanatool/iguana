package org.iguanatool.search;

import org.iguanatool.search.evolve.Evolve;
import org.iguanatool.search.evolve.competition.RankingCompetitionManager;
import org.iguanatool.search.evolve.migration.CompleteNetMigrationManager;
import org.iguanatool.search.evolve.migration.RandomMigrantSelector;
import org.iguanatool.search.evolve.mutation.GaussianMutation;
import org.iguanatool.search.evolve.mutation.MuhlenbeinMutation;
import org.iguanatool.search.evolve.mutation.UniformMutation;
import org.iguanatool.search.evolve.population.BreedingPopulation;
import org.iguanatool.search.evolve.population.CoPopulation;
import org.iguanatool.search.evolve.population.MemeticPopulation;
import org.iguanatool.search.evolve.population.SubPopulation;
import org.iguanatool.search.evolve.population.rank.PopulationLinearRanking;
import org.iguanatool.search.evolve.rank.LinearRanking;
import org.iguanatool.search.evolve.recombination.*;
import org.iguanatool.search.evolve.reinsertion.ElitestReinsertion;
import org.iguanatool.search.evolve.select.ElitestSelection;
import org.iguanatool.search.evolve.select.StochasticUniversalSampling;
import org.iguanatool.search.local.Anneal;
import org.iguanatool.search.local.HillClimb;
import org.iguanatool.search.local.SeededHillClimb;
import org.iguanatool.search.local.coolingschedule.CoolingSchedule;
import org.iguanatool.search.local.coolingschedule.FastCoolingSchedule;
import org.iguanatool.search.local.neighbourhoodsearch.*;
import org.iguanatool.search.local.restarter.MutationRestarter;
import org.iguanatool.search.local.restarter.RandomRestarter;
import org.iguanatool.search.local.restarter.Restarter;
import org.iguanatool.search.pso.Swarm;
import org.iguanatool.search.randomnumbergenerator.MersenneTwisterRandomNumberGenerator;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;

import java.lang.reflect.Method;

public class SearchFactory {

	public static Search annealSearch(RandomNumberGenerator r, int maxEvaluations) {
		Anneal a = new Anneal(r, maxEvaluations);
		
		CoolingSchedule c = new FastCoolingSchedule(400);
		ReducingRandom n = new ReducingRandom(r, 4, 10);
		
		a.setCoolingSchedule(c);
		a.setNeighbourhoodSearch(n);
		a.setIterationsPerTemperatureLevel(50);
		
		return a;
	}
	
    public static Search simpleEvolveSearch(RandomNumberGenerator r, int maxEvaluations) {
    	Evolve e = new Evolve(r, maxEvaluations);
    	BreedingPopulation p = new BreedingPopulation(
    			"Simple breeding population", // id
    			100,                          // initialSize
    			0.9,                          // generationGap
    			new ElitestSelection(),       // selector
    			new DiscreteRecombination(r), // recombinator
    			new GaussianMutation(r),      // mutator
    			new ElitestReinsertion(),     // reinserter
    			r);                           // randomNumberGenerator
    	e.setPopulation(p);
    	return e;
    }  
    
    public static Search dcEvolveUniformMutationSearch(RandomNumberGenerator r, int maxEvaluations) {
    	Evolve e = new Evolve(r, maxEvaluations);
    	BreedingPopulation p = new BreedingPopulation(
    			"Simple breeding population", // id
    			300,                          // initialSize
    			0.9,                          // generationGap
    			new ElitestSelection(),       // selector
    			new DiscreteRecombination(r), // recombinator
    			new UniformMutation(r),      // mutator
    			new ElitestReinsertion(),     // reinserter
    			r);                           // randomNumberGenerator
    	e.setPopulation(p);
    	return e;
    }    
    
    public static Search memeticSearch(RandomNumberGenerator r, int maxEvaluations) {
    	Evolve e = new Evolve(r, maxEvaluations);
    	MemeticPopulation m = new MemeticPopulation(
    			"Simple breeding population", // id
    			300,                          // initialSize
    			0.9,                          // generationGap	
    			new ElitestSelection(),       // selector
    			new DiscreteRecombination(r), // recombinator
    			new UniformMutation(r),       // mutator
    			new ElitestReinsertion(),     // reinserter
    			r,                            // randomNumberGenerator
    			new PatternAcceleration());   // neighbourhoodSearch
    	e.setPopulation(m);
    	return e;    	
    }
    
    public static Search dcEvolveSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation", 
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 50, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new DiscreteRecombination(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }
    
    public static Search dcEvolve1PtXOverSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation", 
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r), 
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 50, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new OnePointCrossover(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),                    
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }    
    
    public static Search dcEvolveNoXOverSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation",
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 50, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new NoRecombination(),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }    
    
    public static Search dcEvolveRandomRecombinationSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation",
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 50, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new RandomRecombination(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }    
    
    public static Search dcEvolveUniformXOverSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation",
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 50, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new UniformCrossover(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }     
    
    public static Search dcEvolveUniformXOver30PopSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation",
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 5, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new UniformCrossover(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }      
    
    public static Search dcEvolveUniformXOver60PopSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation",
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 10, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new UniformCrossover(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }      
    
    public static Search dcEvolveUniformXOver90PopSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation",
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 15, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new UniformCrossover(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }     
    
    public static Search dcEvolveUniformXOver120PopSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation",
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 20, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new UniformCrossover(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }     
    
    public static Search dcEvolveUniformXOver150PopSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation",
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 25, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new UniformCrossover(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }     
    
    public static Search dcEvolveUniformXOver180PopSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation",
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 30, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new UniformCrossover(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }    
    
    public static Search dcEvolveUniformXOver210PopSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation",
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 35, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new UniformCrossover(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }   
    
    public static Search dcEvolveUniformXOver240PopSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation",
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 40, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new UniformCrossover(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }    
    
    public static Search dcEvolveUniformXOver270PopSearch(RandomNumberGenerator r, int maxEvaluations) {
        CoPopulation coPop = new CoPopulation(
                "CoPopulation",
                new CompleteNetMigrationManager(
                        20, new RandomMigrantSelector(r), 0.1, r),
                new RankingCompetitionManager(
                        4,
                        new LinearRanking(1.7),
                        new PopulationLinearRanking(1.7, SubPopulation.PROGRESS_COMPARATOR),
                        0.3,
                        5,
                        r)
                        );
    	
    	double[] ranges = {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
    	
    	for (int i=0; i < ranges.length; i++) {
    		coPop.addPopulation(new BreedingPopulation(
                    ""+i, 45, 0.9,
                    new StochasticUniversalSampling(r, new LinearRanking(1.7)),
                    new UniformCrossover(r),
                    new MuhlenbeinMutation(r, ranges[i], 16),
                    new ElitestReinsertion(),
                    r));
    	}
    	
    	Evolve e = new Evolve(r, maxEvaluations);
    	e.setPopulation(coPop);
    	return e;
    }
    
    public static Search nhcSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new PatternAcceleration());
    	h.setRestarter(new RandomRestarter(Restarter.ANY, r));
    	return h;
    }          
    
    public static Search seededSearch(RandomNumberGenerator r, int maxEvaluations) {
    	SeededHillClimb h = new SeededHillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new PatternAcceleration());
    	h.setRestarter(new MutationRestarter(Restarter.ANY, new UniformMutation(r)));
    	return h;
    }    
    
    public static Search mnhcSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new PatternAcceleration());
    	h.setRestarter(new MutationRestarter(Restarter.NOT_WORSE, new UniformMutation(r)));
    	return h;
    }        

    public static Search rmhcSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(null);
    	h.setRestarter(new MutationRestarter(Restarter.NOT_WORSE, new UniformMutation(r)));
    	return h;
    }            
    
    public static Search linSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new LinearAcceleration());
    	h.setRestarter(new RandomRestarter(Restarter.ANY, r));
    	return h;
    }
         
    public static Search swarmSearch(RandomNumberGenerator r, int maxEvaluations) {
    	Swarm s = new Swarm(r, 10000);
    	s.setNumParticles(300);
    	s.setW(0.9);
    	s.setC1(2);
    	s.setC2(2);
    	return s;
    }
    
    public static Search randomSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new Random(r));
    	return h;
    }
    
    public static Search ips_no_biasSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new IPS_No_Bias(r));
    	return h;
    }
    
    public static Search geometric_no_biasSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new Geometric_No_Bias(r));
    	return h;
    }
    
    public static Search lattice_no_biasSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new Lattice_No_Bias(r));
    	return h;
    }
    
    public static Search geometric_opposite_direction_biasSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new Geometric_Opposite_Direction_Bias(r));
    	return h;
    }
    
    public static Search lattice_opposite_direction_biasSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new Lattice_Opposite_Direction_Bias(r));
    	return h;
    }
    
    public static Search ips_right_biasSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new IPS_Right_Bias(r));
    	return h;
    }
    
    public static Search geometric_right_biasSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new Geometric_Right_Bias(r));
    	return h;
    }
    
    public static Search lattice_right_biasSearch(RandomNumberGenerator r, int maxEvaluations) {
    	HillClimb h = new HillClimb(r, maxEvaluations);
    	h.setNeighbourhoodSearch(new Lattice_Right_Bias(r));
    	return h;
    }
    
	public static Search instantiateSearch(String name, RandomNumberGenerator r, int maxEvaluations) {
    	if (!name.endsWith("Search")) {
    		name += "Search";
    	}
    	
    	Class<SearchFactory> c = SearchFactory.class;
    	Method methods[] = c.getMethods();
    	
    	for (Method m: methods) {
    		if (m.getName().equals(name)) {
    			try {
    				Object[] args = {r, maxEvaluations};
    				return (Search) m.invoke(null, args);
    			} catch (Exception e) {
    				throw new RuntimeException(e);
    			}
    		}
    	}
    	
    	throw new SearchException("Unknown search \""+name+"\"");	
    }
}
