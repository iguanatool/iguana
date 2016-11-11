package org.iguanatool.testobject.structure;

import java.io.Serializable;

public class ControlDependency implements Comparable<ControlDependency>, Serializable {

	private static final long serialVersionUID = -1953922633148052900L;
	
	public CFGNode node;
    public boolean edge;

    public ControlDependency(CFGNode node, boolean edge) {
        this.node = node;
        this.edge = edge;
    }
    
    public int compareTo(ControlDependency controlDependency) {
    	int nodeComparison = node.compareTo(controlDependency.node); 
        if (nodeComparison == 0) {
        	if (edge == controlDependency.edge) {
        		return 0;
        	} else if (edge == false) {
        		return -1;
        	} else {
        		return 1;
        	}
        } else {
        	return nodeComparison;
        }
    }    
    
    public String toString() {
    	return node.toString() + (edge ? "T" : "F");
    }
}
