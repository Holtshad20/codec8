/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8.receiver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author silocom01
 */
public class Parser {

    public static List<CodecReport> Parser(byte[] message) {
        List<CodecReport> answer = new ArrayList();
        int index = 0;
        while( index < (message.length - 25)){
            
            byte[] timestamp = new byte[8];
            for (int i = 0; (i < timestamp.length); i++) {
                timestamp[i] = message[index];
                index++;
            }
            Date date = Utils.timeCalc(timestamp);
           

            byte[] priority = new byte[1];
            priority[0] = message[index];
            index++;

            byte[] lon = new byte[4];
            for (int i = 0; (i < lon.length); i++) {
                lon[i] = message[index];
                index++;
            }

            double longitude = Utils.longitude(lon);

            byte[] lat = new byte[4];
            for (int i = 0; (i < lat.length); i++) {
                lat[i] = message[index];
                index++;
            }
            double latitude = Utils.latitude(lat);

            byte[] altitude = new byte[2];
            for (int i = 0; i < altitude.length; i++) {
                altitude[i] = message[index];
                index++;
            }


            byte[] satInUse = new byte[1];
            satInUse[0] = message[index];
            index++;

            byte[] speed = new byte[2];
            for (int i = 0; i < speed.length; i++) {
                speed[i] = message[index];
                index++;
            }

            byte[] nOfTotalIO = new byte[1];
            nOfTotalIO[0] = message[index];
            index++;

            // Falta implementar las entradas y salidas
            
            CodecReport report = new CodecReport(); //Tienes que crear la clase
            report.setDate(date);
            report.setPriority(priority);
            report.setLatitude(latitude);
            report.setLongitude(longitude);
            report.setSatInUse(satInUse);
            report.setSpeed(speed);
            report.setnOfTotalIO(nOfTotalIO);
            answer.add(report);
            System.out.println(report.toString()); //hay que implementar el toString
        }
        return answer;

    }

}
