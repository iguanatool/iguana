package org.iguanatool.testobject;

import org.iguanatool.library.SimpleIO;
import org.iguanatool.library.SystemCommand;
import org.iguanatool.library.SystemCommandException;
import org.iguanatool.testobject.structure.CFG;
import org.iguanatool.testobject.structure.CFGNode;
import org.iguanatool.testobject.structure.ConnectedNode;
import org.iguanatool.testobject.structure.ControlDependency;

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;

public class ControlGraphImageGenerator {

    private final static int NUM_CODE_STRING_CHARACTERS = 10;

    private StringBuffer dotBuffer;
    private CFG cfg;

    public ControlGraphImageGenerator(CFG cfg) {
        this.cfg = cfg;
    }

    public void writeDotForFlowGraph(String filename) throws IOException {
        writeDotForFlowGraph(new File(filename));
    }

    public void writeDotForFlowGraph(File file) throws IOException {
        SimpleIO.writeText(file, dotForFlowGraph(), true);
    }

    public String dotForFlowGraph() {
        dotBuffer = new StringBuffer();
        startGraph();

        SortedSet<CFGNode> nodes = cfg.getNodes();

        for (CFGNode node : nodes) {
            String label = node.codeString(NUM_CODE_STRING_CHARACTERS).replace("\"", "\\\"");
            writeNode(node.toString(), label);
        }

        for (CFGNode node : nodes) {
            SortedSet<ConnectedNode> successors = node.getSuccessors();

            for (ConnectedNode successor : successors) {
                String edgeLabel = "";
                if (successor.edge != null) {
                    edgeLabel = successor.edge ? "T" : "F";
                }
                writeEdge(node.toString(), successor.node.toString(), edgeLabel);
            }
        }

        endGraph();
        return dotBuffer.toString();
    }

    public void writeDotForDependenceGraph(String filename) throws IOException {
        writeDotForDependenceGraph(new File(filename));
    }

    public void writeDotForDependenceGraph(File file) throws IOException {
        SimpleIO.writeText(file, dotForDependenceGraph(), true);
    }

    public String dotForDependenceGraph() {
        dotBuffer = new StringBuffer();
        startGraph();

        SortedSet<CFGNode> nodes = cfg.getNodes();

        writeNode("entry", "entry");
        for (CFGNode node : nodes) {
            if (!node.isStartNode() && !node.isEndNode()) {
                writeNode(node.toString(), node.toString());
            }
        }

        CFGNode immediatePostDominator = cfg.getStartNode().getImmediatePostDominator();

        while (immediatePostDominator != cfg.getEndNode()) {
            writeEdge("entry", immediatePostDominator.toString(), null);
            immediatePostDominator = immediatePostDominator.getImmediatePostDominator();
        }

        for (CFGNode node : nodes) {
            SortedSet<ControlDependency> controlDependencies = node.getControlDependencies();
            for (ControlDependency controlDependency : controlDependencies) {
                String label = controlDependency.edge ? "T" : "F";
                writeEdge(controlDependency.node.toString(), node.toString(), label);
            }
        }

        endGraph();
        return dotBuffer.toString();
    }

    private void startGraph() {
        dotBuffer.append("digraph dg_0 {\n");
    }

    private void writeNode(String id, String label) {
        dotBuffer.append("\t" + id + " [label=\"" + label + "\"];\n");
    }

    private void writeEdge(String id1, String id2, String edgeLabel) {
        String label = "";
        if (edgeLabel != null) {
            label = " [label=\"" + edgeLabel + "\"]";
        }

        dotBuffer.append("\t" + id1 + " -> " + id2 + label + ";\n");
    }

    private void endGraph() {
        dotBuffer.append("}\n");
    }

    public void makeGraphic(String dotFileName, String graphicFileName, String graphicType) throws IOException, SystemCommandException {
        makeGraphic(new File(dotFileName), new File(graphicFileName), graphicType);
    }

    public void makeGraphic(File dotFile, File graphicFile, String graphicType) throws IOException, SystemCommandException {
        SystemCommand.execute("dot -T" + graphicType + " -o \"" + graphicFile + "\" \"" + dotFile + "\"");
    }
}
