/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

import java.util.Date;

/**
 *
 * @author silocom01
 */
public class Parser {

    public static void Parser(byte[] message) {

        byte[] timestamp = new byte[8];
        for (int i = 0, j = 0; (i < timestamp.length); i++, j++) {
            timestamp[i] = message[j];
        }
        Date date = TimeStampCalculator.timeCalc(timestamp);

        byte[] priority = new byte[1];
        priority[0] = message[8];

        byte[] lon = new byte[4];
        for (int i = 0, j = 9; (i < lon.length); i++, j++) {
            lon[i] = message[j];
        }

        double longitude = GPScalculator.longitude(lon);
        
        byte[] lat = new byte[4];
        for (int i = 0, j = 13; (i < lat.length); i++, j++) {
            lat[i] = message[j];
        }
        double latitude = GPScalculator.latitude(lat);

        byte[] altitude = new byte[2];
        for (int i = 0, j = 17; i < altitude.length; i++) {
            altitude[i] = message[j];
        }

        byte[] angle = new byte[2];
        for (int i = 0, j = 19; i < angle.length; i++) {
            angle[i] = message[j];
        }

        byte[] satellites = new byte[1];
        satellites[0] = message[21];

        byte[] speed = new byte[2];
        for (int i = 0, j = 22; i < speed.length; i++) {
            speed[i] = message[j];
        }

        byte[] nOfTotalIO = new byte[1];
        nOfTotalIO[0] = message[24];

        System.out.println(" timestamp: " + date);
        System.out.println(" priority: " + Utils.hexToString(priority));
        System.out.println(" latitude/longitude: " + latitude + "," + longitude);
        System.out.println(" satellites: " + Utils.hexToString(satellites));
        System.out.println(" speed: " + Utils.hexToString(speed));
        System.out.println(" nOfTotalIO: " + Utils.hexToString(nOfTotalIO));

    }

}
