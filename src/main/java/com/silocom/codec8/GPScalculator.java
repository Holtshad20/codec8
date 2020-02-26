/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author silocom01
 */
public class GPScalculator {

    public static double longitude(byte[] lon) {
       
       return (int)Long.parseLong(Utils.bytesToHex(lon), 16) / 1e7;
    
    }

    public static double latitude(byte[] lat) {

      return (int)Long.parseLong(Utils.bytesToHex(lat), 16) / 1e7;
    }

}
