/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algorithms;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import junit.framework.TestCase;

/**
 *
 * @author Sanka
 */
public class LineDetectionTest extends TestCase {
    
    public LineDetectionTest(String testName) {
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
     * Test of noteDetect method, of class LineDetection.
     */
    public static void testNoteDetect() throws IOException {
        System.out.println("noteDetect");
         BufferedImage in = null;
        try {
            in = ImageIO.read(new File("C:\\Users\\Sanka\\Documents\\SEP\\Tests\\blank.png"));
        } catch (IOException ex) {
            Logger.getLogger(LineDetectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        int[] expResult = {0,0,0,0,0,0,0,0,0,0};
        int[] staffin = {5,10,15,20,25};
        int[][] result = LineDetection.noteDetect(in, staffin, in);
        BufferedImage backUp = in;
        for(int i = 0; i < result.length; i ++){
            assert(result[i][0] >= 0);
            assert(result[i][1] >= 0);
        }
        //int[][] result = LineDetection.noteDetect(in, staffin, backUp);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of staffDetect method, of class LineDetection.
     */
    public void testStaffDetect() throws IOException {
        System.out.println("staffDetect");
        BufferedImage in = ImageIO.read(new File("C:\\Users\\Sanka\\Documents\\SEP\\Tests\\blank.png"));
        int[] expResult = {0,0,0,0,0,0,0,0,0,0};
        int[] result = LineDetection.staffDetect(in);
        assertEquals(expResult[0], result[0]);
        assertEquals(expResult[1], result[1]);
        assertEquals(expResult[2], result[2]);
        assertEquals(expResult[3], result[3]);
        assertEquals(expResult[4], result[4]);
        assertEquals(expResult[5], result[5]);
        assertEquals(expResult[6], result[6]);
        // TODO review the generated test code and remove the default call to fail.*/
        //fail("The test case is a prototype.");
    }

    /**
     * Test of HoughLines method, of class LineDetection.
     */
    public void testHoughLines() {
        System.out.println("HoughLines");        
        BufferedImage in = null;
        try {
            in = ImageIO.read(new File("C:\\Users\\Sanka\\Documents\\SEP\\Tests\\blank.png"));
        } catch (IOException ex) {
            Logger.getLogger(LineDetectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        int[] expResult = {0,0,0,0,0,0,0,0,0,0};
        int[] result = LineDetection.staffDetect(in);
        //LineDetection.HoughLines(in);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
