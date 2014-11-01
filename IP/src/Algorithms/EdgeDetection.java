/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algorithms;

import java.awt.Color;
import java.awt.image.BufferedImage;
/**
 *
 * @author sankaa
 */
public class EdgeDetection {
    
    BufferedImage img;
    BufferedImage temp;
    int[][] edgeDirections;
    double[][] edgeGradient;
    
    public BufferedImage edgeDetect(BufferedImage image, int upper, int lower){
        int upperB = upper;
        int lowerB = lower;
        img = image;
        temp = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        edgeDirections = new int [img.getWidth()][img.getHeight()];
        edgeGradient = new double [img.getWidth()][img.getHeight()];
        
        int[][] xMask = {   {-1, 0, 1},
                            {-2, 0, 2}, 
                            {-1, 0, 1}};
        int[][] yMask = {   {1, 2, 1},
                            {0, 0, 0},
                            {-1, -2, -1}};
        int Gx = 0, Gy = 0;
        int arrayGx[][] = new int [img.getWidth()][img.getHeight()];
        int arrayGy[][] = new int [img.getWidth()][img.getHeight()];
        
        
        for(int row = 1; row < img.getWidth() - 1; row++){
            for(int col = 1; col < img.getHeight() - 1; col++){
                
                Gx = 0;
                Gy = 0;
                
                for(int i = -1; i <= 1; i++){
                    for(int j = -1; j <= 1; j++){
                        Gx += (img.getRGB(row + i, col + j) * xMask[i + 1][j + 1]);
                        Gy += (img.getRGB(row + i, col + j) * yMask[i + 1][j + 1]);
                    }
                }
                edgeGradient[row][col] = Math.sqrt(Math.pow(Gx, 2) + Math.pow(Gy, 2));
                arrayGx[row][col] = Gx;
                arrayGy[row][col] = Gy;
                float k = 0;
                double myAng = 181;
                
                if(edgeGradient[row][col] > 25000){
                    if (Gx != 0 && Gy != 0){
                        k = (float)Gy / (float)Gx;
                        myAng = Math.atan(k) * 180;
                        //System.out.println(myAng);
                    }else { myAng = 90; }
                }
                
                
                if(( myAng > -22.5 && myAng < 22.5 ) || (myAng < -157.5 || myAng > 157.5))
                    edgeDirections[row][col] = 0;
                else if(( myAng > 22.5 && myAng < 67.5 ) || (myAng > -157.5 && myAng < -112.5))
                    edgeDirections[row][col] = 45;
                else if(( myAng > 67.5 && myAng < 112.5 ) || (myAng > -112.5 && myAng < -67.5))
                     edgeDirections[row][col] = 90;
                else if(( myAng > 112.5 && myAng < 157.5 ) || (myAng > -67.5 && myAng < -22.5))
                     edgeDirections[row][col] = 135;
                
                if(myAng == 181) edgeDirections[row][col] = 181;
                
                temp.setRGB(row, col, Color.YELLOW.getRGB());
                
            }
            //System.out.println(""); 
        }
      /* Debug area for edge detection code  
       for(int row = 1; row < img.getWidth() - 1; row++){
            for(int col = 1; col < img.getHeight() - 1; col++){
                if(edgeDirections[row][col] != 181) System.out.print(edgeDirections[row][col] + " ");
                else System.out.print("- ");
                
                //System.out.print(edgeGradient[row][col] + " ");
                
                //System.out.print(arrayGx[row][col]+ " ");
            }
             System.out.println("");
         }
       */
        
        //Trace along edges in the image
        for(int row = 1; row < img.getWidth() - 1; row++){
            for(int col = 1; col < img.getHeight() - 1; col++){
                if(edgeGradient[row][col] > upperB){
                    switch (edgeDirections[row][col]){
                        case 0:
                            findEdge(0,1, row, col, 0, lowerB, image.getWidth(), image.getHeight());
                            break;
                        case 45:
                            findEdge(1,1, row, col, 45, lowerB, image.getWidth(), image.getHeight());
                            break;                            
                        case 90:
                            findEdge(1,0, row, col, 90, lowerB, image.getWidth(), image.getHeight());
                            break;
                        case 135:
                            findEdge(1,-1, row, col, 135, lowerB, image.getWidth(), image.getHeight());
                            break;
                        default:
                            temp.setRGB(row, col, Color.YELLOW.getRGB());
                            //findEdge(0,1, row, col, 0, lowerB);
                            break;
                    }
                }
            }
        }
        return temp;
        
    }
    void findEdge(int xShift,int yShift, int x, int y, int d, int b, int W, int H){
        boolean endEdge = false;
        int newX = x;
        int newY = y;
        int dir =d;
        int lowerB = b;
        do{
            if(xShift < 0){
                if(x > 0){
                    newX += xShift;
                }
                else endEdge = true;
            }
            else{
                if(x < W - 1){
                    newX += xShift;
                }
                else endEdge = true;
            }
            if(yShift < 0){
                if(y > 0){
                    newY += yShift;
                }
                else endEdge = true;
            }
            else{
                if(y < H - 1){
                    newY += yShift;
                }
                else endEdge = true;
            }
            if(( edgeDirections[newX][newY] == dir)){
                switch (dir){
                    case 0:
                        temp.setRGB(newX, newY, Color.BLUE.getRGB());
                        break;
                    case 45:
                        //System.out.println("dir 45");
                        temp.setRGB(newX, newY, Color.GREEN.getRGB());
                        break;
                    case 90:
                        //System.out.println("dir 90");
                        temp.setRGB(newX, newY, Color.RED.getRGB());
                        break;
                    case 135:
                        temp.setRGB(newX, newY, Color.BLACK.getRGB());
                        break;
                    default:
                        temp.setRGB(newX, newY, Color.YELLOW.getRGB());
                        break;
                            
                }
                
                //System.out.println(newX + " " + newY);
            }
                
            
        
        }while(( edgeDirections[newX][newY] == dir) && !endEdge && (edgeGradient[newX][newY] > lowerB));
    }
}
