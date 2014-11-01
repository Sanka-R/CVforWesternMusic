/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ip;

import java.math.BigInteger;

/**
 *
 * @author sankaa
 */
public class Decrypt {
    public static void main(String args[]){
        BigInteger c;
        c = new BigInteger("1630866511392489104");
        String ans = "";
        while(true){
            int k = c.mod(new BigInteger("47")).intValue();
            c = c.divide(new BigInteger("47"));
            char x = (char)(k + 'a');
            ans += x;
            //System.out.print(" " + x);
            if(c.equals(31) || c.compareTo(new BigInteger("31")) == -1) break;
        }
        
        for(int i = ans.length() - 2; i > -1; i --){
            System.out.print(ans.charAt(i));
        }
        
    }
}
