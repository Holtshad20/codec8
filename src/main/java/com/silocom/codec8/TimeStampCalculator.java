/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

import java.util.Date;


/**
 *
 * @author silocom01
 */
public class TimeStampCalculator {
    
    public static Date timeCalc(byte[] timeStamp){ 
        
       // long fecha = ByteBuffer.wrap(timeStamp).getInt();
       // System.out.println(" fecha en decimal: " + fecha);
       // int dec = Integer.parseInt(Utils.hexToString(timeStamp));
        
       
       
        return  Utils.convert(Utils.bytesToHex(timeStamp));
    }    
}
