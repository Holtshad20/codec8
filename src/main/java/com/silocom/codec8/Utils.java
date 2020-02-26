/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

import java.util.Date;

/**
 *
 * @author silocom01
 */
public class Utils {

   

    public static String hexToString(byte[] message) {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < message.length; i++) {

            if ((message[i] & 0xff) < 0x10) {
                answer.append("0");
            }

            answer.append(Integer.toHexString(message[i] & 0xFF));

        }
        return answer.toString();
    }
    
      public static String bytesToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }
      
      public static Date convert(String inHexString){
      
        Date dateResult = new Date(Long.parseLong(inHexString,16));
       
         return dateResult;
   }
    
}
