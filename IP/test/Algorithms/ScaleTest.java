/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algorithms;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import junit.framework.TestCase;

/**
 *
 * @author Sanka
 */
public class ScaleTest extends TestCase {
    
    public ScaleTest(String testName) {
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
     * Test of scaleDownBy2 method, of class Scale.
     */
    public void testScaleDownBy2() throws IOException {
        System.out.println("scaleDownBy2");
        BufferedImage img = ImageIO.read(new File("C:\\Users\\Sanka\\Documents\\SEP\\Tests\\blank.png"));
        int testWidth = img.getWidth();
        int testHeight = img.getHeight();
        BufferedImage result = Scale.scaleDownBy2(img);
        assertEquals(testWidth / 2, result.getWidth());
        
        assertEquals(testHeight / 2, result.getHeight());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of scaleUpBy2Duplication method, of class Scale.
     */
    public void testScaleUpBy2Duplication() throws IOException {
        System.out.println("scaleUpBy2Duplication");
        BufferedImage img = ImageIO.read(new File("C:\\Users\\Sanka\\Documents\\SEP\\Tests\\blank.png"));
        int testWidth = img.getWidth();
        int testHeight = img.getHeight();
        BufferedImage result = Scale.scaleUpBy2Duplication(img);
        assertEquals(testWidth * 2, result.getWidth());
        
        assertEquals(testHeight * 2, result.getHeight());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of scaleUpBy2LinearInterpolation method, of class Scale.
     */
    public void testScaleUpBy2LinearInterpolation() throws IOException {
        System.out.println("scaleUpBy2LinearInterpolation");
        BufferedImage img = ImageIO.read(new File("C:\\Users\\Sanka\\Documents\\SEP\\Tests\\blank.png"));
        int testWidth = img.getWidth();
        int testHeight = img.getHeight();
        BufferedImage result = Scale.scaleUpBy2LinearInterpolation(img);
        assertEquals(testWidth * 2, result.getWidth());
        
        assertEquals(testHeight * 2, result.getHeight());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
