package org.iguanatool.testobject.structure;

import java.io.Serializable;
import java.util.*;

public class CFG implements Serializable {

    private static final long serialVersionUID = -2264246272588609359L;

    private CFGNode startNode;
    private CFGNode endNode;

    private List<CFGNode> nodes;

    public CFG() {
        startNode = new StartNode();
        endNode = new EndNode();
        nodes = new ArrayList<CFGNode>();
    }

    public CFGNode getStartNode() {
        return startNode;
    }

    public CFGNode getEndNode() {
        return endNode;
    }

    public CFGNode getNode(int id) {
        if (id == startNode.getID()) {
            return startNode;
        }

        if (id == endNode.getID()) {
            return endNode;
        }
        return nodes.get(id - 1);
    }

    public CFGNode createNode() {
        int id = nodes.size() + 1;
        CFGNode node = new CFGNode(id);
        nodes.add(node);
        return node;
    }

    public SortedSet<CFGNode> getNodes() {
        SortedSet<CFGNode> nodeSet = new TreeSet<CFGNode>();
        nodeSet.add(startNode);
        nodeSet.add(endNode);
        nodeSet.addAll(nodes);
        return Collections.unmodifiableSortedSet(nodeSet);
    }

    public SortedSet<CFGNode> getBranchingNodes() {
        SortedSet<CFGNode> branchingNodes = new TreeSet<CFGNode>();
        for (CFGNode node : nodes) {
            if (node.isBranching()) {
                branchingNodes.add(node);
            }
        }
        return Collections.unmodifiableSortedSet(branchingNodes);
    }

    public void computeControlDependencies() {
        for (CFGNode node : nodes) {
            node.setImmediatePostDominator(null);
            node.clearControlDependencies();
        }

        new ComputePostDominators().compute(this);
        new ComputeControlDependencies().compute(this);
    }
}
