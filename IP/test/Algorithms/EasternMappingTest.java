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
public class EasternMappingTest extends TestCase {
    
    public EasternMappingTest(String testName) {
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
     * Test of note method, of class EasternMapping.
     */
    public void testNote() {
        System.out.println("note");
        int[] staff = {5,10,15,20,25};
        int posY = 1;
        String expResult = "Pa* ";
        String result = EasternMapping.note(staff, posY);
        assertEquals(expResult, result);
        
        posY = 36;
        expResult = "Sa ";
        result = EasternMapping.note(staff, posY);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
