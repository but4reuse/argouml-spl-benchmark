// $Id: TestActivityGraphsFactory.java 43 2010-04-03 20:28:12Z marcusvnac $
// Copyright (c) 2002-2007 The Regents of the University of California. All
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

package org.argouml.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;


/**
 * Test the ActivityGraphsFactoryImpl class.
 *
 */
public class TestActivityGraphsFactory extends TestCase {

    /**
     * All the ModelElements we are going to test.
     */
    private static String[] allModelElements = {
        "ActivityGraph",
        "ActionState",
        "CallState",
        "ClassifierInState",
        "ObjectFlowState",
        "Partition",
        "SubactivityState",
    };
    
    private static String[] skippedModelElements = {
        "ActivityGraph",
    };

    /**
     * The constructor.
     *
     * @param n the name
     */
    public TestActivityGraphsFactory(String n) {
	super(n);
        InitializeModel.initializeDefault();
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception {
	super.setUp();
    }

    /**
     * Test the singleton pattern for the ActivityGraphsFactoryImpl class.
     */
    public void testSingleton() {
	Object o1 = Model.getActivityGraphsFactory();
	Object o2 = Model.getActivityGraphsFactory();
	assertTrue("Different singletons", o1 == o2);
    }

    /**
     * The test for creation.
     */
    public void testCreates() {
	CheckUMLModelHelper.createAndRelease(Model.getActivityGraphsFactory(),
					     getTestableModelElements());
    }

    /**
     * @return Returns the allModelElements.
     */
    static String[] getAllModelElements() {
        return allModelElements;
    }
    
    /**
     * @return all testable model elements
     */
    static List<String> getTestableModelElements() {
        List<String> result = 
            new ArrayList<String>(Arrays.asList(allModelElements));
        result.removeAll(Arrays.asList(skippedModelElements));
        return result;
    } 
}
