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
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import org.bytedeco.javacpp.Pointer;
import static org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import org.bytedeco.javacv.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;
import sun.util.BuddhistCalendar;

public  class LineDetection {
    
    public static int [][] noteDetect(BufferedImage in, int [] staffin, BufferedImage backUp){
        int tol = 10; // to make sure the edges of the image are not detected as clef lines
        int staff = 1; // number of staffs in the image
        int lineX[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int lineY[][] = {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
        
        //String fileName = in;
        IplImage src = IplImage.createFrom(in);
        //System.out.println(fileName);
        //IplImage src = cvLoadImage(fileName, 0);
        IplImage dst;
        IplImage colorDst;
        CvMemStorage storage = cvCreateMemStorage(0);
        CvSeq lines = new CvSeq();

        //CanvasFrame source = new CanvasFrame("Source");
        if (src == null) {
            System.out.println("Couldn't load source image.");
        }

        dst = cvCreateImage(cvGetSize(src), src.depth(), 1);
        colorDst = cvCreateImage(cvGetSize(src), src.depth(), 3);

        cvCanny(src, dst, 50, 200, 3);
        cvCvtColor(dst, colorDst, CV_GRAY2BGR);

        /*
         * apply the probabilistic hough transform
         * which returns for each line deteced two points ((x1, y1); (x2,y2))
         * defining the detected segment
         */
        
        System.out.println("Using the Probabilistic Hough Transform");
        lines = cvHoughLines2(dst, storage, CV_HOUGH_PROBABILISTIC, 1, Math.PI / 180, 10, 10, 10);
     
        int[] lengths = new int[lines.total()];
        int tempX = 0;
        
        /*for(int i = 0; i < lines.total(); i ++){
            Pointer line = cvGetSeqElem(lines, i);
            CvPoint pt1  = new CvPoint(line).position(0);
            CvPoint pt2  = new CvPoint(line).position(1);
            if(pt1.x() > tempX + tol){
                lengths[i] = (pt1.x() - pt2.x()) * (pt1.x() - pt2.x());
                System.out.println(pt1.x() + " - " + pt2.x() + " ==> " + lengths[i] + " ");
            }
            else
                lengths[i] = -1;
            tempX = pt1.y();
        }*/
        
        int [] trueLen = lengths;
        Arrays.sort(lengths);
        
        for(int i = 0; i < lines.total(); i ++) System.out.print(lengths[i] + " ");
        
        if(lines.total() > 6)
        System.out.println("\nhighest lengths accepted: " + lengths[lines.total() - 6]);
        
        int cnt = 0;
        boolean okay = false;
        for (int i = 0; i < lines.total(); i++) {
            // from JavaCPP, the equivalent of the C code:
            // CvPoint* line = (CvPoint*)cvGetSeqElem(lines,i);
            // CvPoint first=line[0], second=line[1]
            // is:
            // CvPoint first=line.position(0), secon=line.position(1);

            Pointer line = cvGetSeqElem(lines, i);
            CvPoint pt1  = new CvPoint(line).position(0);
            CvPoint pt2  = new CvPoint(line).position(1);

            if(pt1.x() > tol * tol && pt1.x() < (in.getWidth()- tol * tol) && (pt1.x() - pt2.x()) * (pt1.x() - pt2.x()) < tol){
            
            okay = true;
            for(int k = 0; k <= cnt; k ++){
                
                if(((pt1.x() - lineX[k]) * (pt1.x() - lineX[k]) < 50)){
                    System.out.println( "rejected " + pt1.x() + " " + (pt1.x() - lineX[k]) * (pt1.x() - lineX[k]));
                    okay = false;
                    break;
                }
            }
            if(okay){
                System.out.println("note spotted: ");
                System.out.println("\t pt1: " + pt1);
                System.out.println("\t pt2: " + pt2);
                cvLine(colorDst, pt1, pt2, CV_RGB(0, 0, 255), 3, CV_AA, 0); // draw the segment on the image
                lineX[cnt] = pt1.x();
                /*if(pt1.y() <= pt2.y())
                    lineY[cnt] = pt1.y();
                else
                    lineY[cnt] = pt2.y();*/
                cnt ++;
            }
                
            //cvLine(colorDst, pt1, pt2, CV_RGB(0, 0, 255), 3, CV_AA, 0); // draw the segment on the image    
            }   
        }
        
        int currX = 0, currY = 0;
        int value;
        System.out.println(staffin.length );
        
        for(int i = 0; i < staffin.length; i ++) staffin[i] = -1 * staffin[i];
        
        Arrays.sort(staffin);
        
        for(int i = 0; i < staffin.length; i ++) staffin[i] = -1 * staffin[i];
        
        int staffwidth = staffin[0] - staffin[1];
        System.out.println(staffwidth + " "+ in.getWidth() + " : " + in.getHeight());
        //loop through all notes
        for(int i = 0; i < cnt; i ++){
            
            int max = -1;
            int pos = 0;
            
            currX = lineX[i] - staffwidth / 2;
            
            //cernter possitions within staff lines
            for(int j = 0; j < staffin.length; j ++){
                currY = staffin[j] - staffwidth * 3 / 4 ;
                value = 0;
                for(int m = currX; m < (currX + staffwidth / 2); m ++){
                    for(int n = currY; n < (currY + staffwidth / 2); n ++){
                        if(m < in.getWidth() && n < in.getHeight() && m > 0 && n > 0){
                            Color temp = new Color(backUp.getRGB(m, n));
                            //System.out.println(m + ":" + n + "=" + temp.toString());
                            if(temp.getRed() == 0 && temp.getGreen() == 0 && temp.getBlue() == 0){
                                value ++; 
                                //System.out.println(temp.toString());
                            }
                        }
                        else{
                            //System.out.println(m + ":" + n + "REJECTED!");
                        }
                    }
                }
                if(max <= value){
                    pos = (currY+staffwidth/4);
                    max = value;
                }
                System.out.println(currX + " " + (currY+staffwidth/4) + " : value= " + value);
            }
            //on lines of the staff
            for(int j = 0; j < staffin.length; j ++){
                currY = staffin[j] - staffwidth / 4 ;
                value = 0;
                for(int m = currX; m < (currX + staffwidth / 2); m ++){
                    for(int n = currY; n < (currY + staffwidth / 2); n ++){
                        if(m < in.getWidth() && n < in.getHeight() && m > 0 && n > 0){
                            Color temp = new Color(backUp.getRGB(m, n));
                            //System.out.println(m + ":" + n + "=" + temp.toString());
                            if(temp.getRed() == 0 && temp.getGreen() == 0 && temp.getBlue() == 0){
                                value ++; 
                                //System.out.println(temp.toString());
                            }
                        }
                        else{
                            //System.out.println(m + ":" + n + "REJECTED!");
                        }
                    }
                }
                if(max <= value){
                    pos = (currY+staffwidth/4);
                    max = value;
                }
                System.out.println(currX + " " + (currY+staffwidth/4) + " : value= " + value);
            }
            // Remove bad cases
            if(max > staffwidth * staffwidth / 8){
                for(int l = 0; l < staffin.length; l ++) System.out.print(staffin[l] + " ");
                System.out.println(" ");
                System.out.println("max= " + max + " value= " + pos);
                System.out.println(" __ ");
                CvPoint pt1  = new CvPoint();
                pt1.put(currX, pos);
                CvPoint pt2 = new CvPoint();
                pt2.put(currX +1, pos +1);
                lineY[i][0] = pos;
                lineY[i][1] = currX;
                System.out.println(currX + " " + pos);
                cvLine(colorDst, pt1, pt2, CV_RGB(0, 255, 0), 3, CV_AA, 0);
            }
        }
        openDialog(colorDst);
        //source.showImage(src);
        cvSaveImage("C:\\test.jpg", src);
        cvReleaseImage(src);
        return lineY;
        //source.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //hough.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
       
    public static int[] staffDetect(BufferedImage in){
        int tol = 10; // to make sure the edges of the image are not detected as clef lines
        int staff = 1; // number of staffs in the image
        int lineY[] = {0,0,0,0,0,0,0,0,0,0}; // to save the staff line y cordinate values
        //String fileName = in;
        IplImage src = IplImage.createFrom(in);
        //System.out.println(fileName);
        //IplImage src = cvLoadImage(fileName, 0);
        IplImage dst;
        IplImage colorDst;
        CvMemStorage storage = cvCreateMemStorage(0);
        CvSeq lines = new CvSeq();

        //CanvasFrame source = new CanvasFrame("Source");
        if (src == null) {
            System.out.println("Couldn't load source image.");
        }

        dst = cvCreateImage(cvGetSize(src), src.depth(), 1);
        colorDst = cvCreateImage(cvGetSize(src), src.depth(), 3);

        cvCanny(src, dst, 50, 200, 3);
        cvCvtColor(dst, colorDst, CV_GRAY2BGR);

        /*
         * apply the probabilistic hough transform
         * which returns for each line deteced two points ((x1, y1); (x2,y2))
         * defining the detected segment
         */
        
        System.out.println("Using the Probabilistic Hough Transform");
        lines = cvHoughLines2(dst, storage, CV_HOUGH_PROBABILISTIC, 1, Math.PI / 180, 40, 50, 10);
        
        int[] lengths = new int[lines.total()];
        int tempY = 0;
        
        for(int i = 0; i < lines.total(); i ++){
            Pointer line = cvGetSeqElem(lines, i);
            CvPoint pt1  = new CvPoint(line).position(0);
            CvPoint pt2  = new CvPoint(line).position(1);
           
            if(pt1.y() > tempY + tol){
                lengths[i] = (pt1.x() - pt2.x()) * (pt1.x() - pt2.x());
                 /* print out the lengths of each line segment
                System.out.println(pt1.x() + " - " + pt2.x() + " ==> " + lengths[i] + " ");
                */
            }
            else
                lengths[i] = -1;
            tempY = pt1.y();
        }
        
        int [] trueLen = lengths;
        Arrays.sort(lengths);
        
        //for(int i = 0; i < lines.total(); i ++) System.out.print(lengths[i] + " ");
        if(lines.total() > 6)
        System.out.println("\nhighest lengths accepted: " + lengths[lines.total() - 6]);
        
        int cnt = 0;
        for (int i = 0; i < lines.total(); i++) {
            // from JavaCPP, the equivalent of the C code:
            // CvPoint* line = (CvPoint*)cvGetSeqElem(lines,i);
            // CvPoint first=line[0], second=line[1]
            // is:
            // CvPoint first=line.position(0), secon=line.position(1);

            Pointer line = cvGetSeqElem(lines, i);
            CvPoint pt1  = new CvPoint(line).position(0);
            CvPoint pt2  = new CvPoint(line).position(1);
            boolean okay= true;
            // && ((pt1.x() - pt2.x()) * ((pt1.x() - pt2.x())) > lengths[lines.total() - staff * 5])
            if(pt1.y() > tol && pt1.y() < (in.getHeight() - tol) && pt1.y() == pt2.y()){
            okay = true;
            for(int k = 0; k <= 5; k ++){
                if((pt1.y() - lineY[k]) * (pt1.y() - lineY[k]) < 75){
                    okay = false;
                    break;
                }
            }
            if(okay){
                System.out.println("Line spotted: ");
                System.out.println("\t pt1: " + pt1);
                System.out.println("\t pt2: " + pt2);
                System.out.println("\t length: " + trueLen[i]);
                lineY[cnt] = pt1.y();
                cnt ++;
                cvLine(colorDst, pt1, pt2, CV_RGB(255, 0, 0), 3, CV_AA, 0); // draw the segment on the image
            }

            }
        }
        
        //source.showImage(src);
        openDialog(colorDst);
        cvSaveImage("C:\\test.jpg", src);
        cvReleaseImage(src);
        return lineY; // staff detected lines ( can be more than 5 )
    }
    private static void openDialog(IplImage colorDst){
        
        CanvasFrame hough = new CanvasFrame("Notes Detected");
        hough.showImage(colorDst);
    } 
    
   public static void HoughLines(BufferedImage in) {
        
        int tol = 10; // to make sure the edges of the image are not detected as clef lines
        int staff = 1; // number of staffs in the image
        int lineY[] = {0,0,0,0,0,0,0,0,0,0};
        //String fileName = in;
        IplImage src = IplImage.createFrom(in);
        //System.out.println(fileName);
        //IplImage src = cvLoadImage(fileName, 0);
        IplImage dst;
        IplImage colorDst;
        CvMemStorage storage = cvCreateMemStorage(0);
        CvSeq lines = new CvSeq();

        CanvasFrame source = new CanvasFrame("Source");
        CanvasFrame hough = new CanvasFrame("Hough");
        if (src == null) {
            System.out.println("Couldn't load source image.");
            return;
        }

        dst = cvCreateImage(cvGetSize(src), src.depth(), 1);
        colorDst = cvCreateImage(cvGetSize(src), src.depth(), 3);

        cvCanny(src, dst, 50, 200, 3);
        cvCvtColor(dst, colorDst, CV_GRAY2BGR);

        /*
         * apply the probabilistic hough transform
         * which returns for each line deteced two points ((x1, y1); (x2,y2))
         * defining the detected segment
         */
        
        System.out.println("Using the Probabilistic Hough Transform");
        lines = cvHoughLines2(dst, storage, CV_HOUGH_PROBABILISTIC, 1, Math.PI / 180, 40, 50, 10);
        
        
        
        int[] lengths = new int[lines.total()];
        int tempY = 0;
        
        for(int i = 0; i < lines.total(); i ++){
            Pointer line = cvGetSeqElem(lines, i);
            CvPoint pt1  = new CvPoint(line).position(0);
            CvPoint pt2  = new CvPoint(line).position(1);
            if(pt1.y() > tempY + tol){
                lengths[i] = (pt1.x() - pt2.x()) * (pt1.x() - pt2.x());
                System.out.println(pt1.x() + " - " + pt2.x() + " ==> " + lengths[i] + " ");
            }
            else
                lengths[i] = -1;
            tempY = pt1.y();
        }
        
        
        
        /*for (int i = 0; i < lines.total(); i++) {
            Pointer line = cvGetSeqElem(lines, i);
            CvPoint pt1  = new CvPoint(line).position(0);
            CvPoint pt2  = new CvPoint(line).position(1);
            lengths[i] = (pt1.x() - pt2.x()) * (pt1.x() - pt2.x());
            System.out.println(pt1.x() + " - " + pt2.x() + " ==> " + lengths[i] + " ");
        }*/
        int [] trueLen = lengths;
        Arrays.sort(lengths);
        
        //for(int i = 0; i < lines.total(); i ++) System.out.print(lengths[i] + " ");
        
        System.out.println("\nhighest lengths accepted: " + lengths[lines.total() - 6]);
        
        int cnt = 0;
        for (int i = 0; i < lines.total(); i++) {
            // from JavaCPP, the equivalent of the C code:
            // CvPoint* line = (CvPoint*)cvGetSeqElem(lines,i);
            // CvPoint first=line[0], second=line[1]
            // is:
            // CvPoint first=line.position(0), secon=line.position(1);

            Pointer line = cvGetSeqElem(lines, i);
            CvPoint pt1  = new CvPoint(line).position(0);
            CvPoint pt2  = new CvPoint(line).position(1);
            boolean okay= true;
            if(pt1.y() > tol && pt1.y() < (in.getHeight() - tol) && pt1.y() == pt2.y() && 
            ((pt1.x() - pt2.x()) * ((pt1.x() - pt2.x())) > lengths[lines.total() - staff * 5])){
            okay = true;
            for(int k = 0; k <= 5; k ++){
                if((pt1.y() - lineY[k]) * (pt1.y() - lineY[k]) < 50){
                    okay = false;
                    break;
                }
            }
            if(okay){
                System.out.println("Line spotted: ");
                System.out.println("\t pt1: " + pt1);
                System.out.println("\t pt2: " + pt2);
                System.out.println("\t length: " + trueLen[i]);
                lineY[cnt] = pt1.y();
                cnt ++;
                cvLine(colorDst, pt1, pt2, CV_RGB(255, 0, 0), 3, CV_AA, 0); 
            }
            // draw the segment on the image
            }
            
            
            if(pt1.x() > tol * tol && pt2.x() < (in.getWidth() - tol * tol) && pt1.x() - pt2.x() <= 10 &&
                    pt1.y() - pt2.y() > 5){
            
            System.out.println("note spotted: ");
            System.out.println("\t pt1: " + pt1);
            System.out.println("\t pt2: " + pt2);
            cvLine(colorDst, pt1, pt2, CV_RGB(0, 255, 0), 3, CV_AA, 0); // draw the segment on the image
            }
            
        }
        
        /*
         * Apply the multiscale hough transform which returns for each line two float parameters (rho, theta)
         * rho: distance from the origin of the image to the line
         * theta: angle between the x-axis and the normal line of the detected line
         */
        /*
        else if(args.length==2 && args[1].contentEquals("multiscale")){
                        System.out.println("Using the multiscale Hough Transform"); //
            lines = cvHoughLines2(dst, storage, CV_HOUGH_MULTI_SCALE, 1, Math.PI / 180, 40, 1, 1);
            for (int i = 0; i < lines.total(); i++) {
                CvPoint2D32f point = new CvPoint2D32f(cvGetSeqElem(lines, i));

                float rho=point.x();
                float theta=point.y();

                double a = Math.cos((double) theta), b = Math.sin((double) theta);
                double x0 = a * rho, y0 = b * rho;
                CvPoint pt1 = new CvPoint((int) Math.round(x0 + 1000 * (-b)), (int) Math.round(y0 + 1000 * (a))), pt2 = new CvPoint((int) Math.round(x0 - 1000 * (-b)), (int) Math.round(y0 - 1000 * (a)));
                System.out.println("Line spoted: ");
                System.out.println("\t rho= " + rho);
                System.out.println("\t theta= " + theta);
                cvLine(colorDst, pt1, pt2, CV_RGB(255, 0, 0), 3, CV_AA, 0);
            }
        }*/
        /*
         * Default: apply the standard hough transform. Outputs: same as the multiscale output.
         */
        /*
        else {
            System.out.println("Using the Standard Hough Transform");
            lines = cvHoughLines2(dst, storage, CV_HOUGH_STANDARD, 1, Math.PI / 180, 90, 0, 0);
            for (int i = 0; i < lines.total(); i++) {
                CvPoint2D32f point = new CvPoint2D32f(cvGetSeqElem(lines, i));

                float rho=point.x();
                float theta=point.y();

                double a = Math.cos((double) theta), b = Math.sin((double) theta);
                double x0 = a * rho, y0 = b * rho;
                CvPoint pt1 = new CvPoint((int) Math.round(x0 + 1000 * (-b)), (int) Math.round(y0 + 1000 * (a))), pt2 = new CvPoint((int) Math.round(x0 - 1000 * (-b)), (int) Math.round(y0 - 1000 * (a)));
                System.out.println("Line spotted: ");
                System.out.println("\t rho= " + rho);
                System.out.println("\t theta= " + theta);
                cvLine(colorDst, pt1, pt2, CV_RGB(255, 0, 0), 3, CV_AA, 0);
            }
        }*/
        
        source.showImage(src);
        hough.showImage(colorDst);
        cvSaveImage("C:\\test.jpg", src);
        cvReleaseImage(src);
        
        //source.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //hough.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
     
}
