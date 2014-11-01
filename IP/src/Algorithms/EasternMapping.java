/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algorithms;

import java.util.Arrays;

/**
 *
 * @author Sanka
 */
public class EasternMapping {
    public static String note (int [] staff, int posY){
        
        for(int i = 0; i < staff.length; i ++) staff[i] *= -1;
        Arrays.sort(staff);
        for(int i = 0; i < staff.length; i ++) staff[i] *= -1;
        
        int x1 = staff[0];
        int x2 = staff[1];
        int x3 = staff[2];
        int x4 = staff[3];
        int x5 = staff[4];
        int x0 = x1 + (x1 - x2) / 2;
        int x1_2 = (x1 + x2) / 2;
        int x2_3 = (x2 + x3) / 2;
        int x3_4 = (x3 + x4) / 2;
        int x4_5 = (x4 + x5) / 2;
        int x6 = x5 - (x4 - x5)/2;
        
        
        if(posY < (x6 + x5)/2)
            //System.out.println("G*");
            return "Pa* ";
        else if(posY < (x5 + x4_5) / 2)
            //System.out.println("F*");
            return "Ma* ";
        else if(posY < (x4_5 + x4) / 2)
            //System.out.println("E*");
            return "Ga* ";
        else if(posY < (x4 + x3_4) / 2)
            //System.out.println("D*");
            return "Ri* ";
        else if(posY < (x3_4 + x3) / 2)
            //System.out.println("C*");
            return "Sa* ";
        else if(posY < (x3 + x2_3) / 2)
            //System.out.println("B");
            return "Ni ";
        else if(posY < (x2_3 + x2) / 2)
            //System.out.println("A");
            return "Da ";
        else if(posY < (x2 + x1_2) / 2)
            //System.out.println("G");
            return "Pa ";
        else if(posY < (x1_2 + x1) / 2)
            //System.out.println("F");
            return "Ma ";
        else if(posY < (x1 + x0) / 2)
            //System.out.println("E");
            return "Ga ";
        else if(posY < x0 + (x1 - x0) / 2)
            //System.out.println("D");
            return "Ri ";
        else
            //System.out.println("C");
            return "Sa ";
        
       // return "";
    }
}
