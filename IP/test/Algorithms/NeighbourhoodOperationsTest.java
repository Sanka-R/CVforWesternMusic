/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algorithms;

import junit.framework.TestCase;

/**
 *
 * @author Sanka
 */
public class NeighbourhoodOperationsTest extends TestCase {
    
    public NeighbourhoodOperationsTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of smooth method, of class NeighbourhoodOperations.
     */
    public void testSmooth() {
        System.out.println("smooth");
        String filename = "C:\\Users\\Sanka\\Documents\\SEP\\Tests\\blank.png\\";
        NeighbourhoodOperations.smooth(filename);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
