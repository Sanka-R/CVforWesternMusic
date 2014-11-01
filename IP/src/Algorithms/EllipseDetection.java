/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algorithms;

/**
 *
 * @author Sanka
 */

import java.awt.image.BufferedImage;
import org.bytedeco.javacpp.Loader;
import static org.bytedeco.javacpp.opencv_core.CV_FILLED;
import static org.bytedeco.javacpp.opencv_core.CV_RGB;
import static org.bytedeco.javacpp.opencv_core.CV_WHOLE_SEQ;
import org.bytedeco.javacpp.opencv_core.CvContour;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvCreateMemStorage;
import static org.bytedeco.javacpp.opencv_core.cvDrawContours;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_core.cvScalar;
import static org.bytedeco.javacpp.opencv_core.cvXorS;
import static org.bytedeco.javacpp.opencv_core.cvZero;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_CHAIN_APPROX_SIMPLE;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RETR_CCOMP;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.cvContourArea;
import static org.bytedeco.javacpp.opencv_imgproc.cvDilate;
import static org.bytedeco.javacpp.opencv_imgproc.cvFindContours;
import static org.bytedeco.javacpp.opencv_imgproc.cvFindContours;
import static org.bytedeco.javacpp.opencv_imgproc.cvThreshold;
import java.util.Random;


public class EllipseDetection {
    
    public static final double M_PI = 3.14159265358979323846;
    public static final double MIN_AREA = 100.00;
    public static final double MAX_TOL = 100.00;

    private static int[] array = { 0 };
    //
    // We need this to be high enough to get rid of things that are too small
    // too
    // have a definite shape. Otherwise, they will end up as ellipse false
    // positives.
    //
    //
    // One way to tell if an object is an ellipse is to look at the relationship
    // of its area to its dimensions. If its actual occupied area can be
    // estimated
    // using the well-known area formula Area = PI*A*B, then it has a good
    // chance of
    // being an ellipse.
    //
    // This value is the maximum permissible error between actual and estimated
    // area.
    //

    public static void fitEllipse (BufferedImage in) {
          //IplImage src = cvLoadImage(args[1], 0);
            IplImage src = IplImage.createFrom(in);
            //System.out.println("done this");
          if (src == null) {
             System.out.println("!!! Unable to load image: ");
             return;
          }
        // the first command line parameter must be file name of binary
        // (black-n-white) image
            IplImage dst = cvCreateImage(cvGetSize(src), 8, 3);
            CvMemStorage storage = cvCreateMemStorage(0);
            CvSeq contour = new CvContour();
            // maybe: = new CvSeq(0)
            cvThreshold(src, src, 1, 255, CV_THRESH_BINARY);
            //System.out.println("here");
            //
            // Invert the image such that white is foreground, black is
            // background.
            // Dilate to get rid of noise.
            //
            cvXorS(src, cvScalar(255, 0, 0, 0), src, null);
            cvDilate(src, src, null, 2);

            cvFindContours(src, storage, contour,
                    Loader.sizeof(CvContour.class), CV_RETR_CCOMP,
                    CV_CHAIN_APPROX_SIMPLE, cvPoint(0, 0));
            cvZero(dst);
            System.out.println(contour.flags());
            for (; contour.flags() != 0; contour = contour.h_next()) {
                // if not working: use contour.isNull()
                double actual_area = Math.abs(cvContourArea(contour,
                        CV_WHOLE_SEQ, 0));
                if (actual_area < MIN_AREA)
                    continue;

                //
                // FIXME:
                // Assuming the axes of the ellipse are vertical/perpendicular.
                //
                System.out.println("here");
                System.out.println(contour);
                CvRect rect = ((CvContour) contour).rect();
                int A = rect.width() / 2;
                int B = rect.height() / 2;
                double estimated_area = Math.PI * A * B;
                double error = Math.abs(actual_area - estimated_area);
                if (error > MAX_TOL)
                    continue;
                System.out.printf("center x: %d y: %d A: %d B: %d\n", rect.x()
                        + A, rect.y() + B, A, B);

                CvScalar color = CV_RGB(0,0,0);
                cvDrawContours(dst, contour, color, color, -1, CV_FILLED, 8,
                        cvPoint(0, 0));
            }

            cvSaveImage("coins.png", dst, array);
        
    }

} 
