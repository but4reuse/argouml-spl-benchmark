// $Id: TestActionStateDiagram.java 43 2010-04-03 20:28:12Z marcusvnac $
// Copyright (c) 2003-2007 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.uml.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.argouml.model.InitializeModel;
import org.argouml.model.Model;
import org.argouml.profile.init.InitProfileSubsystem;
import org.argouml.ui.targetmanager.TargetManager;
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.UMLMutableGraphSupport;
import org.tigris.gef.undo.UndoableAction;

/**
 * TODO: This needs more work.
 * Test e.g. creation when an operation is selected.
 *
 * @author jaap.branderhorst@xs4all.nl
 * @since Jan 9, 2003
 */
public class TestActionStateDiagram extends TestCase {

    /**
     * The action to be tested.
     */
    private UndoableAction action;

    /**
     * The namespace a created diagram should have.
     */
    private Object ns;

    /**
     * A list with namespaces that should be valid for the diagram to be
     * created.
     */
    private List validNamespaces;

    /**
     * Constructor.
     *
     * @param arg0 test case name
     */
    public TestActionStateDiagram(String arg0) {
        super(arg0);
        InitializeModel.initializeDefault();
        new InitProfileSubsystem().init();
    }

    /*
     * @see org.argouml.uml.ui.AbstractTestActionAddDiagram#getAction()
     */
    protected UndoableAction getAction() {
        return new ActionStateDiagram();
    }

    /*
     * @see org.argouml.uml.ui.AbstractTestActionAddDiagram#getNamespace()
     */
    protected Object getNamespace() {
        return Model.getCoreFactory().createClass();
    }

    /*
     * @see AbstractTestActionAddDiagram#getValidNamespaceClasses()
     */
    protected List getValidNamespaceClasses() {
        List rl = new ArrayList();
        /*
         * This needs to be a concrete metatype, so we can't use
         * the general, but abstract, Classifier.  Replace with its
         * concrete subtypes.
         */
        rl.add(Model.getMetaTypes().getUMLClass());
        rl.add(Model.getMetaTypes().getInterface());
        rl.add(Model.getMetaTypes().getDataType());
        rl.add(Model.getMetaTypes().getNode());
        rl.add(Model.getMetaTypes().getComponent());

        // TODO: this should fail, but it doesn't:
        rl.add(Model.getMetaTypes().getTransition());
        return rl;
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        action = getAction();
        ns = getNamespace();
        validNamespaces = getValidNamespaceClasses();

        TargetManager.getInstance().setTarget(ns);
    }

    /**
     * Test to create a diagram.
     */
    public void testCreateDiagram() {
        Model.getPump().flushModelEvents();
        action.actionPerformed(null);
        Object d = TargetManager.getInstance().getTarget();
        assertTrue("No diagram generated", d instanceof ArgoDiagram);
        Model.getPump().flushModelEvents();
        ArgoDiagram diagram = (ArgoDiagram) d;
        assertNotNull(
                      "The diagram has no namespace",
                      diagram.getNamespace());
        assertNotNull(
                      "The diagram has no graphmodel",
                      diagram.getGraphModel());
        assertTrue("The graphmodel of the diagram is not a "
                   + "UMLMutableGraphSupport",
                   diagram.getGraphModel() instanceof UMLMutableGraphSupport);
        assertNotNull("The diagram has no name", diagram.getName());
    }


    /**
     * Tests if the list with namespaces defined in getValidNamespaceClasses
     * contains only valid namespaces.
     *
     * TODO: This test does not test anything, really!
     */
    public void testValidNamespaces() {
        Iterator it = validNamespaces.iterator();
        while (it.hasNext()) {
            Object type = it.next();

            Object o = Model.getUmlFactory().buildNode(type);
            String objDesc = "" + o;
            if (o != null) {
                objDesc += " (" + o.getClass() + ")";
            }
            TargetManager.getInstance().setTarget(o);
            Model.getPump().flushModelEvents();
            action.actionPerformed(null);
            Object d = TargetManager.getInstance().getTarget();
            assertTrue(
                    objDesc
                    + " is not valid namespace for the diagram",
                    d instanceof ArgoDiagram);
        }
    }

}
