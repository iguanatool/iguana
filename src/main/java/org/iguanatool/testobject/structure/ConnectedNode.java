package org.iguanatool.testobject.structure;

import java.io.Serializable;

public class ConnectedNode implements Comparable<ConnectedNode>, Serializable {
	
	private static final long serialVersionUID = -4327291223748021793L;
	
	public CFGNode node;
	public Boolean edge;
	
	public ConnectedNode(CFGNode node) {
		this.node = node;
		this.edge = null;
	}
	
	public ConnectedNode(CFGNode node, boolean edge) {
		this.node = node;
		this.edge = edge;
	}	
	
	public ConnectedNode(CFGNode node, Boolean edge) {
		this.node = node;
		this.edge = edge;
	}		
	
	public ConnectedNode(CFGNode node, boolean edge, boolean hasEdge) {
		this.node = node;
		if (hasEdge) {
			this.edge = edge;
		} else {
			this.edge = null;
		}
	}
	
	public int compareTo(ConnectedNode connectedNode) {
		int nodeCompare = node.compareTo(connectedNode.node);
		
		if (nodeCompare == 0) {
			if (edge != null && connectedNode.edge != null) {
				return edge.compareTo(connectedNode.edge);
			} else if (edge == null) {
				return -1;
			} else if (connectedNode.edge == null) {
				return 1;
			}
			return 0;
		} else {
			return nodeCompare;
		}
    }	
	
	public String toString() {
		String string = "CFGNode: "+node;
		if (edge != null) string += " edge:"+edge; 
		return string;
	}	
}
