package org.iguanatool.search.pso;

import org.iguanatool.log.Log;
import org.iguanatool.search.solution.Solution;

public class ParticleLog {

	private static int particleNum = 0;
	private Log log;
	
	public ParticleLog()
	{
		particleNum ++;
		log = new Log();
		log.attachFile("/log/particle"+particleNum+".txt");
	}
	
	public void update(Particle p) {
		log.println(particleString(p));
	}
	
	public void update(Particle p, Solution overallBest) {
		log.println(particleString(p)+"\t"+
				    overallBest.toString()+"\t"+
				    overallBest.getObjectiveValue().getNumericalValue());
	}
	
	public void flush() {
		log.flush();
	}
	
	private String particleString(Particle p) {
		return p.getSolution().toString()+"\t"+
	    	   p.getSolution().getObjectiveValue().getNumericalValue()+"\t"+
	    	   p.getBest().toString()+"\t"+
	    	   p.getBest().getObjectiveValue().getNumericalValue();
	}
}
