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
public class EdgeDetectionTest extends TestCase {
    
    public EdgeDetectionTest(String testName) {
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
     * Test of edgeDetect method, of class EdgeDetection.
     */
    public void testEdgeDetect() throws IOException {
        System.out.println("edgeDetect");
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Sanka\\Documents\\SEP\\Tests\\blank.png"));
        int upper = 5;
        int lower = 1;
        EdgeDetection instance = new EdgeDetection();
        BufferedImage expResult = null;
        BufferedImage result = instance.edgeDetect(image, upper, lower);
        
        assertEquals(image.getWidth(), result.getWidth());
        assertEquals(image.getHeight(), result.getHeight());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of findEdge method, of class EdgeDetection.
     */
    public void testFindEdge() {
        System.out.println("findEdge");
        int xShift = 0;
        int yShift = 0;
        int x = 0;
        int y = 0;
        int d = 0;
        int b = 0;
        int W = 0;
        int H = 0;
        EdgeDetection instance = new EdgeDetection();
        //instance.findEdge(xShift, yShift, x, y, d, b, W, H);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
