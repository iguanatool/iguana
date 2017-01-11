package org.iguanatool.testobject.structure;

import org.iguanatool.testobject.structure.condition.Condition;

import java.io.Serializable;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class CFGNode implements Comparable<CFGNode>, Serializable {

    private static final long serialVersionUID = -1439487687129189400L;

    private int id;
    private String code;

    private SortedSet<ConnectedNode> predecessors;
    private SortedSet<ConnectedNode> successors;

    private CFGNode immediatePostDominator;
    private SortedSet<ControlDependency> controlDependencies;

    private Condition condition;

    public CFGNode(int id) {
        this.id = id;
        predecessors = new TreeSet<ConnectedNode>();
        successors = new TreeSet<ConnectedNode>();
        controlDependencies = new TreeSet<ControlDependency>();
    }

    public boolean isStartNode() {
        return false;
    }

    public boolean isEndNode() {
        return true;
    }

    public int getID() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public boolean isBranching() {
        return condition != null;
    }

    public void addSuccessor(CFGNode successor) {
        addSuccessor(new ConnectedNode(successor));
    }

    public void addSuccessor(CFGNode successor, Boolean edge) {
        addSuccessor(new ConnectedNode(successor, edge));
    }

    public void addSuccessor(ConnectedNode successor) {
        successors.add(successor);
        successor.node.predecessors.add(new ConnectedNode(this, successor.edge));
    }

    public SortedSet<ConnectedNode> getPredecessors() {
        return Collections.unmodifiableSortedSet(predecessors);
    }

    public SortedSet<CFGNode> getPredecessorNodes() {
        SortedSet<CFGNode> nodes = new TreeSet<CFGNode>();
        for (ConnectedNode connectedNode : predecessors) {
            nodes.add(connectedNode.node);
        }
        return nodes;
    }

    public SortedSet<ConnectedNode> getSuccessors() {
        return Collections.unmodifiableSortedSet(successors);
    }

    public SortedSet<CFGNode> getSuccessorNodes() {
        SortedSet<CFGNode> nodes = new TreeSet<CFGNode>();
        for (ConnectedNode connectedNode : successors) {
            nodes.add(connectedNode.node);
        }
        return nodes;
    }

    public CFGNode getImmediatePostDominator() {
        return immediatePostDominator;
    }

    public void setImmediatePostDominator(CFGNode immediatePostDominator) {
        this.immediatePostDominator = immediatePostDominator;
    }

    public void clearControlDependencies() {
        controlDependencies.clear();
    }

    public void addControlDependency(ControlDependency controlDependency) {
        controlDependencies.add(controlDependency);
    }

    public SortedSet<ControlDependency> getControlDependencies() {
        return Collections.unmodifiableSortedSet(controlDependencies);
    }

    public int compareTo(CFGNode node) {
        int id1 = id;
        int id2 = node.id;

        if (id1 < id2) {
            return -1;
        } else if (id1 > id2) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CFGNode)) {
            return false;
        }

        CFGNode node = (CFGNode) obj;
        return id == node.id;
    }

    public String codeString(int cutoff) {
        StringBuilder sb = new StringBuilder(toString());

        if (code != null) {
            sb.append(". ");
            if (code.length() > cutoff) {
                sb.append(code.substring(0, cutoff));
                sb.append("...");
            } else {
                sb.append(code);
            }
        }

        return sb.toString();
    }

    public String toString() {
        return Integer.toString(id);
    }
}
