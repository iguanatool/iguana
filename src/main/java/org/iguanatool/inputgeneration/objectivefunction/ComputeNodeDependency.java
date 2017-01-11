package org.iguanatool.inputgeneration.objectivefunction;

import org.iguanatool.testobject.structure.CFGNode;
import org.iguanatool.testobject.structure.ControlDependency;

import java.util.Set;
import java.util.TreeSet;

public class ComputeNodeDependency {

    private int approachLevel;
    private boolean edge;

    public int getApproachLevel() {
        return approachLevel;
    }

    public boolean getEdge() {
        return edge;
    }

    public void compute(CFGNode fromNode, CFGNode toNode) {
        compute(fromNode, toNode, new TreeSet<CFGNode>());
    }

    public void compute(CFGNode fromNode, CFGNode toNode, Set<CFGNode> visited) {
        approachLevel = -1;

        if (fromNode == toNode) {
            approachLevel = 0;
            return;
        }

        Set<ControlDependency> dependencies = fromNode.getControlDependencies();
        for (ControlDependency c : dependencies) {
            if (c.node == toNode) {
                approachLevel = 1;
                edge = c.edge;
                return;
            }
        }

        ComputeNodeDependency cnd = new ComputeNodeDependency();
        for (ControlDependency c : dependencies) {
            if (!visited.contains(c.node)) {
                visited.add(c.node);
                cnd.compute(c.node, toNode, visited);
            }

            int thisApproachLevel = cnd.getApproachLevel();
            if (thisApproachLevel != -1) {
                thisApproachLevel = thisApproachLevel + 1;
                if (approachLevel == -1 || thisApproachLevel < approachLevel) {
                    approachLevel = thisApproachLevel;
                    edge = cnd.getEdge();
                }
            }
        }
    }
}
