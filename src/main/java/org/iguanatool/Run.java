package org.iguanatool;

import org.iguanatool.inputgeneration.aim.Aim;
import org.iguanatool.inputgeneration.aim.AimFactory;
import org.iguanatool.inputgeneration.aim.log.AimLog;
import org.iguanatool.inputgeneration.variableinclusion.IncludeAllVariables;
import org.iguanatool.search.Search;
import org.iguanatool.search.SearchFactory;
import org.iguanatool.search.randomnumbergenerator.MersenneTwister;
import org.iguanatool.search.randomnumbergenerator.RandomNumberGenerator;
import org.iguanatool.testobject.TestObject;
import org.iguanatool.testobject.TestObjectFactory;

import java.util.List;

public class Run {
	
	public Run(String[] configProperties) {
		if (configProperties.length > 0 && !configProperties[0].startsWith("-")) {
			configProperties[0] = "-test_object="+configProperties[0];
		}
		
		if (configProperties.length > 1 && !configProperties[1].startsWith("-")) {
			configProperties[1] = "-search="+configProperties[1];
		}
		
		Config config = new Config(configProperties);

		String testObjectName = config.getRequiredProperty("test_object");
		String searchName     = config.getRequiredProperty("search");
		String aimDescription = config.getRequiredProperty("aim");
		int maxEvaluations    = Integer.parseInt(config.getRequiredProperty("max_evaluations"));
		long seed 			  = Long.parseLong(config.getRequiredProperty("seed"));
		
		// initiate random number generator
		RandomNumberGenerator r = new MersenneTwister();
		r.setSeed(seed);
		
		// instantiate test object loader
		List<TestObject> testObjects = TestObjectFactory.instantiate(testObjectName);

		// start log
		AimLog aimLog = new AimLog();
		aimLog.attachSysOut();

		// assign filename for results and attach a file log
		String fileName = testObjectName.replace(".", "_")+"--"+searchName+"--"+seed;
		aimLog.attachFile(fileName);


		for (TestObject testObject: testObjects) {			
			// instantiate search
			Search search = SearchFactory.instantiateSearch(searchName+"Search", r, maxEvaluations);
			
			// instantiate aim and attempt
			aimLog.logStart(testObject);			
			Aim aim = AimFactory.instantiateAim(testObject,
												new IncludeAllVariables(),
												search, 
												aimLog, 
												aimDescription);
			aim.attemptAim();			
		}

		aimLog.close();	
	}
	
	public static void main(String[] args) {
		new Run(args);
	}
}
