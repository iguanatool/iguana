package org.iguanatool.testobject.structure;

import java.util.Set;

class ComputeControlDependencies {

    void compute(CFG graph) {
        Set<CFGNode> nodes = graph.getNodes();

        for (CFGNode node : nodes) {
            Set<ConnectedNode> successors = node.getSuccessors();

            for (ConnectedNode successor : successors) {
                if (successor.edge != null) {
                    addControlDependencies(node, successor.node, successor.edge);
                }
            }
        }
    }

    private void addControlDependencies(CFGNode node, CFGNode successor, boolean edge) {
        CFGNode runner = successor;
        while (runner != node.getImmediatePostDominator()) {
            runner.addControlDependency(new ControlDependency(node, edge));
            runner = runner.getImmediatePostDominator();
        }
    }
}
