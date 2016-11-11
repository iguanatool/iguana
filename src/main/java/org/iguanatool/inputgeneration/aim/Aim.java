package org.iguanatool.inputgeneration.aim;

import org.iguanatool.inputgeneration.aim.log.AimLog;
import org.iguanatool.inputgeneration.inputvector.InputVectorType;
import org.iguanatool.inputgeneration.objectivefunction.InputGenerationObjectiveFunction;
import org.iguanatool.inputgeneration.variableinclusion.VariableIncluder;
import org.iguanatool.search.Search;
import org.iguanatool.search.SearchResult;
import org.iguanatool.testobject.TestObject;

public abstract class Aim {
	
	protected TestObject testObject;
	protected VariableIncluder variableIncluder;
	protected Search search;
	protected AimLog aimLog;
	
	public Aim(TestObject testObject, 
			   VariableIncluder variableIncluder, 
			   Search search, 
			   AimLog log) {
		
		this.testObject = testObject;
		this.variableIncluder = variableIncluder;
		this.search = search;
		this.aimLog = log;
	}
	
	public abstract void attemptAim();
	
	protected SearchResult generateTestData(InputVectorType i,
                                            InputGenerationObjectiveFunction f) {
		search.getRandomNumberGenerator().reset();    
		return search.search(i, f);    	 
	}
}
