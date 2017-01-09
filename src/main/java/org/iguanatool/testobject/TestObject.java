package org.iguanatool.testobject;

import org.iguanatool.Config;
import org.iguanatool.library.SimpleIO;
import org.iguanatool.testobject.structure.CFG;
import org.iguanatool.testobject.structure.CFGNode;
import org.iguanatool.testobject.trace.Trace;
import org.iguanatool.testobject.trace.TraceNode;

import java.io.File;
import java.util.List;

public abstract class TestObject {
    
	protected static final String INPUT_SPECIFICATION_SETUP_PROPERTY = "input_specification_setup";
	
    protected CFG cfg;
    protected InputSpecification inputSpecification;
    
    // trace needs to be a field of the test object so that it can be accessed
    // by the instrumentation functions
    protected Trace t;
    
    public TestObject() {
		loadCFG();
		loadInputSpecification();    	
    }
    
    public CFG getCFG() {
        return cfg;
    }

    public CFGNode getCFGNode(int id) {
        return cfg.getNode(id);
    }
    
    public InputSpecification getInputSpecification() {
    	return inputSpecification;
    }
        
    public List<TraceNode> invocate(double[] args) {
    	t = new Trace();  
    	t.setCfg(cfg);
		call(args);
    	return t.getTraceNodes();
    }
    
	protected abstract void loadCFG();
	
	protected void loadInputSpecification() {
		String setup = Config.getInstance().getProperty(INPUT_SPECIFICATION_SETUP_PROPERTY);
		if (setup == null) {
			setup = "default";
		}
		String setupMethodName = setup + "Setup";
		
		inputSpecification = instantiateInputSpecification();
		try {
			// invoke setup method of input specification
			inputSpecification.getClass().getMethod(setupMethodName).invoke(inputSpecification, (Object[]) null);
		} catch (Exception e) {
			throw new RuntimeException("Could not setup input configuration \"" + setup + "\" for "+inputSpecification.getClass());
		} 
	}
	
	protected abstract InputSpecification instantiateInputSpecification();
	
	protected abstract int getTestObjectID();
	
	protected void loadCFG(File cfgFile) {
        try {
            cfg = SimpleIO.unserialize(cfgFile);
        } catch (Exception e) {
        	// TODO: throw this out of the method
            throw new RuntimeException("Error whilst unserializing object from " + 
            						   cfgFile+ "\n" + e.getMessage());
        }				
	}    

    protected abstract void call(double[] args);
}
