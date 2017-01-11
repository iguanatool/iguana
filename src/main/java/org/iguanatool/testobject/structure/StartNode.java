package org.iguanatool.testobject.structure;

public class StartNode extends CFGNode {

    static final int START_NODE_ID = Integer.MIN_VALUE;
    static final String START_NODE_ID_STRING = "s";
    private static final long serialVersionUID = 2989509459048586702L;

    public StartNode() {
        super(START_NODE_ID);
    }

    public boolean isStartNode() {
        return true;
    }

    public void setCode(String code) {
        throw new RuntimeException("Cannot add code to a start node");
    }

    public void addPrecessor(ConnectedNode connectedNode) {
        throw new RuntimeException("Cannot add a predecessor to a start node");
    }

    public String toString() {
        return START_NODE_ID_STRING;
    }
}
