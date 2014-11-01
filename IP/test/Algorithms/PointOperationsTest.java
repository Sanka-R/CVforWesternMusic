/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algorithms;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import junit.framework.TestCase;

/**
 *
 * @author Sanka
 */
public class PointOperationsTest extends TestCase {
    
    public PointOperationsTest(String testName) {
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
     * Test of ConvertToBnW method, of class PointOperations.
     */
    public void testConvertToBnW() throws IOException {
        System.out.println("ConvertToBnW");
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Sanka\\Documents\\SEP\\Tests\\blank.png"));
        BufferedImage expResult = null;
        BufferedImage result = PointOperations.ConvertToBnW(image);
        assertEquals(result.getWidth(), image.getWidth());
        assertEquals(result.getHeight(), image.getHeight());
        
        for(int i = 0; i < result.getWidth(); i ++){
            for(int j = 0; j < result.getHeight(); j ++){
                assert(result.getRGB(i, j) == Color.black.getRGB() || result.getRGB(i, j) == Color.white.getRGB());
            }
        }
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of Transpose method, of class PointOperations.
     */
    public void testTranspose() throws IOException {
        System.out.println("Transpose");
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Sanka\\Documents\\SEP\\Tests\\blank.png"));
        BufferedImage expResult = null;
        BufferedImage result = PointOperations.Transpose(image);
        assertEquals(result.getWidth(), image.getHeight());
        assertEquals(result.getHeight(), image.getWidth());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of VerticalFlip method, of class PointOperations.
     */
    public void testVerticalFlip() throws IOException {
        System.out.println("VerticalFlip");
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Sanka\\Documents\\SEP\\Tests\\blank.png"));
        BufferedImage expResult = null;
        BufferedImage result = PointOperations.VerticalFlip(image);
        assertEquals(result.getWidth(), image.getWidth());
        assertEquals(result.getHeight(), image.getHeight());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of HorizontalFlip method, of class PointOperations.
     */
    public void testHorizontalFlip() throws IOException {
        System.out.println("HorizontalFlip");
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Sanka\\Documents\\SEP\\Tests\\blank.png"));
        BufferedImage expResult = null;
        BufferedImage result = PointOperations.HorizontalFlip(image);
        assertEquals(image.getWidth(), result.getWidth());
        assertEquals(image.getHeight(), result.getHeight());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
