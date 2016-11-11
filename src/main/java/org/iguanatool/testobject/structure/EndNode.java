package org.iguanatool.testobject.structure;

public class EndNode extends CFGNode {

	private static final long serialVersionUID = -6634153974821905754L;
	
	static final int END_NODE_ID = Integer.MAX_VALUE;
	static final String END_NODE_ID_STRING = "e";
	
	public EndNode() {
		super(END_NODE_ID);
	}
	
	public boolean isEndNode() {
		return true;
	}

	public void setCode(String code) {
		throw new RuntimeException("Cannot add code to an end node");
	}
	
	public void addSuccessor(ConnectedNode connectedNode) {
		throw new RuntimeException("Cannot add a successor to a start node");
	}	
	
	public String toString() {
		return END_NODE_ID_STRING;
	}
}
