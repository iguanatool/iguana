package org.iguanatool.inputgeneration.objectivefunction;

import org.iguanatool.testobject.structure.CFG;
import org.iguanatool.testobject.structure.CFGNode;
import org.iguanatool.testobject.structure.ControlDependency;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * User: phil
 * Date: 13-Jun-2006
 * Time: 16:55:46
 */
public class ComputeNodeDependencyTest extends TestCase {

    public static Test suite() {
        return new TestSuite(ComputeNodeDependencyTest.class);
    }

    public void testComputeNodeDependency() {
        CFG cfg = new CFG();
        CFGNode n0 = cfg.createNode();
        CFGNode n1 = cfg.createNode();
        CFGNode n2 = cfg.createNode();
        CFGNode n3 = cfg.createNode();
        CFGNode n4 = cfg.createNode();
        CFGNode n5 = cfg.createNode();

        n0.addControlDependency(new ControlDependency(n1, true));
        n1.addControlDependency(new ControlDependency(n2, false));
        n1.addControlDependency(new ControlDependency(n3, true));
        n2.addControlDependency(new ControlDependency(n4, true));
        n4.addControlDependency(new ControlDependency(n5, true));
        n3.addControlDependency(new ControlDependency(n5, false));

        ComputeNodeDependency cnd = new ComputeNodeDependency();
        cnd.compute(n0, n5);
        assertEquals(3, cnd.getApproachLevel());
    }

    public void testCyclicControlDependency() {
        CFG cfg = new CFG();
        CFGNode n0 = cfg.createNode();
        CFGNode n1 = cfg.createNode();
        CFGNode n2 = cfg.createNode();
        CFGNode n3 = cfg.createNode();

        n0.addControlDependency(new ControlDependency(n3, true));
        n1.addControlDependency(new ControlDependency(n0, true));
        n2.addControlDependency(new ControlDependency(n1, true));
        n1.addControlDependency(new ControlDependency(n2, true));

        ComputeNodeDependency cnd = new ComputeNodeDependency();
        cnd.compute(n1, n0);
        assertEquals(1, cnd.getApproachLevel());

        cnd.compute(n2, n0);
        assertEquals(2, cnd.getApproachLevel());

        cnd.compute(n2, n3);
        assertEquals(3, cnd.getApproachLevel());
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}