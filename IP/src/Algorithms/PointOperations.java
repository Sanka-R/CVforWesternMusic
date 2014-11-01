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
public class PointOperations {
   public static BufferedImage ConvertToBnW(BufferedImage image){
       BufferedImage inp = image;
       BufferedImage temp = new BufferedImage(  inp.getWidth(), inp.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
       
       for(int i = 0; i < inp.getWidth(); i ++){
           for(int j = 0; j < inp.getHeight(); j ++){
               temp.setRGB(i, j, inp.getRGB(i, j));
               //System.out.print(temp.getRGB(i, j) + " ");
           }
           //System.out.println("");
       }
       
       return temp;
   }
    
    public static BufferedImage Transpose(BufferedImage image){
       BufferedImage inp = image;
       BufferedImage temp = new BufferedImage( inp.getHeight(), inp.getWidth(), inp.getType());
       
       for(int i = 0; i < inp.getWidth(); i++){
           for(int j = 0; j < inp.getHeight(); j ++)
               temp.setRGB(j, i, inp.getRGB(i, j)) ;
       }
       
       return temp;
    }

    public static BufferedImage VerticalFlip(BufferedImage image) {
       BufferedImage inp = image;
       BufferedImage temp = new BufferedImage( inp.getWidth(), inp.getHeight(), inp.getType());
       
       for(int i = 0; i < inp.getWidth(); i ++){
           for(int j = 0; j < inp.getHeight(); j++){
               temp.setRGB(inp.getWidth() - 1 - i, j, inp.getRGB(i, j));
           }
       }
       
       return temp;
    }

    public static BufferedImage HorizontalFlip(BufferedImage image) {
        BufferedImage inp = image;
        BufferedImage temp = new BufferedImage( inp.getWidth(), inp.getHeight(), inp.getType());
        
        for(int i = 0; i < inp.getWidth(); i++){
            for(int j = 0; j < inp.getHeight(); j++){
                temp.setRGB(i, inp.getHeight() - 1 - j, inp.getRGB(i, j));
            }
        }
        
        return temp;
    }
}
