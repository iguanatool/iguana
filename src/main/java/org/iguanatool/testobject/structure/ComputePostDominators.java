package org.iguanatool.testobject.structure;

import java.util.*;

/*
 *  The algorithms in this class are due to: 
 *  "A Simple, Fast Dominance Algorithm"
 *  Keith D. Cooper, Timothy J. Harvey and Ken Kennedy
 *  Software Practice and Experience 2001; 4:1-10
 * 
 *  .. but computing for post-dominance, rather than dominance
 */

class ComputePostDominators {

    CFGNode endNode;
    List<CFGNode> nodes;
    SortedMap<CFGNode, Integer> reversePostOrder;

    void compute(CFG graph) {
        // initialize
        endNode = graph.getEndNode();
        nodes = new LinkedList<>();
        reversePostOrder = new TreeMap<>();

        // sort nodes into reverse post-order
        sortNodes();
        endNode.setImmediatePostDominator(endNode);

        // reverse the order of the node list, so that we move from the end of the CFG to the start
        Collections.reverse(nodes);

        boolean changed = true;
        while (changed) {
            changed = false;

            for (CFGNode node : nodes) {
                // System.out.println("Current: "+node);

                if (node.equals(endNode)) {
                    continue;
                }

                Set<CFGNode> successors = node.getSuccessorNodes();
                CFGNode currentPostDominator = node.getImmediatePostDominator();
                CFGNode newIntermediatePostDominator = null;

                for (CFGNode successor : successors) {
                    if (newIntermediatePostDominator == null) {
                        if (successor.getImmediatePostDominator() != null) {
                            newIntermediatePostDominator = successor;
                            //System.out.println("... Successor " + successor + " = new intermediate post dominator");
                        }
                    }
                }

                for (CFGNode successor : successors) {
                    if (!successor.equals(newIntermediatePostDominator)) {
                        CFGNode successorPostDominator = successor.getImmediatePostDominator();
                        //System.out.println("... Successor " + successor + ", post dominator: " + successorPostDominator);

                        if (successorPostDominator != null) {
                            //System.out.print("...... intersect(" + successor + ", " + newIntermediatePostDominator + ")");
                            newIntermediatePostDominator = intersect(successor, newIntermediatePostDominator);
                            //System.out.println(" = new intermediate post dominator " + newIntermediatePostDominator);
                        }
                    }
                }

                if (!newIntermediatePostDominator.equals(currentPostDominator)) {
                    //System.out.println("... new post dominator " + newIntermediatePostDominator);

                    node.setImmediatePostDominator(newIntermediatePostDominator);
                    changed = true;
                }
            }

            //System.out.println("--------------------");
            //for (CFGNode node: nodes) {
            //	System.out.println(node + "-" + node.getImmediatePostDominator());
            //}
            //System.out.println("--------------------");
        }
    }

    // walk up the post-dominator tree until a common post-dominator is found
    CFGNode intersect(CFGNode node1, CFGNode node2) {
        CFGNode finger1Node = node1;
        CFGNode finger2Node = node2;

        int finger1 = reversePostOrder.get(finger1Node);
        int finger2 = reversePostOrder.get(finger2Node);

        while (finger1 != finger2) {

            while (finger1 < finger2) {
                finger1Node = finger1Node.getImmediatePostDominator();
                finger1 = reversePostOrder.get(finger1Node);
            }

            while (finger2 < finger1) {
                finger2Node = finger2Node.getImmediatePostDominator();
                finger2 = reversePostOrder.get(finger2Node);
            }
        }

        return finger1Node;
    }

    // sort the nodes topologically from the point of view of the end node
    // (i.e. into reverse post order)
    void sortNodes() {

        new Object() {
            Set<CFGNode> traversed;

            void traverse() {
                traversed = new TreeSet<>();
                add(endNode);
            }

            void add(CFGNode node) {
                if (!traversed.contains(node)) {
                    traversed.add(node);
                    for (CFGNode predecessor : node.getPredecessorNodes()) {
                        add(predecessor);
                    }
                    nodes.add(node);
                    reversePostOrder.put(node, nodes.size());
                }
            }
        }.traverse();
    }
}
