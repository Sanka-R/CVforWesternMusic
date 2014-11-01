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
public class Scale {
    
    public static BufferedImage scaleDownBy2(BufferedImage img){
        BufferedImage inp = img;
        BufferedImage send = null;
        send = new BufferedImage(inp.getWidth()/2, inp.getHeight()/2, 1);
        
        for(int i = 0; i < inp.getWidth() / 2; i ++){
            for(int j = 0; j < inp.getHeight() / 2; j ++)
                send.setRGB(i, j, inp.getRGB(2 * i, 2 * j));
        }
        
        return send;
    }
    
    public static BufferedImage scaleUpBy2Duplication(BufferedImage img){
        BufferedImage inp = img;
        BufferedImage send = null;
        send = new BufferedImage(inp.getWidth() * 2, inp.getHeight() * 2, 1);
        for(int i = 0; i < inp.getWidth() * 2; i ++){
            for(int j = 0; j < inp.getHeight() * 2; j ++){
                send.setRGB(i, j, inp.getRGB(i/2, j/2));
            }
        }
        
        return send;
    }

    public static BufferedImage scaleUpBy2LinearInterpolation(BufferedImage image) {
        BufferedImage inp = image;
        System.out.println(inp.getWidth() + " " + inp.getHeight());
        BufferedImage send = null;
        send = new BufferedImage(inp.getWidth() * 2, inp.getHeight() * 2, image.getType());
        for(int i = 0; i < inp.getWidth() * 2 - 2; i ++){
            for(int j = 0; j < inp.getHeight() * 2 - 2; j ++){
                Color c1 = new Color(inp.getRGB(i/2, j/2));
                Color c2 = new Color(inp.getRGB(i/2 + 1, j/2 + 1));
                
                int k = interpolateColor(c1, c2, 0.5f);
                Color set = new Color(k);
                
                send.setRGB(i, j, set.getRGB());
            }
        }
        for(int i = 0; i < inp.getWidth() * 2; i ++){
            send.setRGB(i, (inp.getHeight() * 2 - 1), inp.getRGB(i/2, inp.getHeight() - 1));
            send.setRGB(i, (inp.getHeight() * 2 - 2), inp.getRGB(i/2, inp.getHeight() - 1));
        }
        for(int i = 0; i < inp.getHeight() * 2; i ++){
            send.setRGB(inp.getWidth() * 2 - 1, i, inp.getRGB(inp.getWidth() - 1, i/2));
            send.setRGB(inp.getWidth() * 2 - 2, i, inp.getRGB(inp.getWidth() - 1, i/2));
        }
        
        
        //send.setRGB(inp.getWidth() * 2 - 1, inp.getHeight() * 2 - 1, inp.getRGB(inp.getWidth() - 1, inp.getHeight() - 1));
        return send;
    }
    
    
    private static float interpolate(float a, float b, float proportion) {
        return (a + ((b - a) * proportion));
    }

  /** Returns an interpoloated color, between <code>a</code> and <code>b</code> */
    private static int interpolateColor(Color a, Color b, float proportion) {
        float[] hsva = new float[3];
        float[] hsvb = new float[3];
        Color.RGBtoHSB(a.getRed(), a.getGreen(), a.getBlue(), hsva);
        Color.RGBtoHSB(b.getRed(), b.getGreen(), b.getBlue(), hsvb);
        for (int i = 0; i < 3; i++) {
        hsvb[i] = interpolate(hsva[i], hsvb[i], proportion);
        }
        return Color.HSBtoRGB(hsvb[0], hsvb[1], hsvb[2]);
  }
}
