/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

import com.silocom.m2m.layer.physical.Connection;
import com.silocom.m2m.layer.physical.MessageListener;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author silocom01
 */
public class Receiver implements MessageListener {

    Connection con;
    private final byte[] IMEIExpected;

    public Receiver(Connection con, byte[] IMEIExpected) {
        this.con = con;
        this.IMEIExpected = IMEIExpected;

    }

    public void receiveMessage(byte[] message) {

        //Si el IMEI recibido es el IMEI esperado, parsear y despues de parsear responder 01, en caso contrario, no hacer nada y responder 00
        System.out.println(" message: " + Utils.hexToString(message));

        byte[] messageReversed = new byte[message.length];
        messageReversed = message.clone();
        ArrayUtils.reverse(messageReversed);
        //System.out.println(" messageReversed: " + Utils.hexToString(messageReversed));

        if (message.length == 17) {

            byte[] IMEIReceived = new byte[15];
            try {
                for (int i = 0, j = 2; i < message.length - 1; i++, j++) {
                    IMEIReceived[i] = message[j];
                }
            } catch (Exception e) {

            }

            byte[] IMEIlenght = new byte[2];
            System.arraycopy(message, 0, IMEIlenght, 0, 2);

            if (Arrays.equals(IMEIReceived, IMEIExpected)) {

                //Send 0x01 to the device
                byte[] accept = new byte[]{0x01};
                con.sendMessage(accept);
            } else {
                byte[] deny = new byte[]{0x00};
                con.sendMessage(deny);

            }

        } else {

            byte[] header = new byte[4];

            System.arraycopy(message, 0, header, 0, 3);

            byte[] dataFieldLenght = new byte[4];
            for (int i = 0, j = 4; (i < dataFieldLenght.length); i++, j++) {
                dataFieldLenght[i] = message[j];
            }

            byte[] CRC16_parsed = new byte[4];
            for (int i = 0, j = 3; i < CRC16_parsed.length; i++, j--) {
                CRC16_parsed[i] = messageReversed[j];
            }

            //CRC16.calcCRC16(header);  
            //Si el CRC16 no concuerda, no procesa la data
            byte[] codecID = new byte[1];
            codecID[0] = message[8];

            byte[] NofData1 = new byte[1];
            NofData1[0] = message[9];

            byte[] NofData2 = new byte[1];
            NofData2[0] = messageReversed[4];

            byte[] timestamp = new byte[8];
            for (int i = 0, j = 10; (i < timestamp.length); i++, j++) {
                timestamp[i] = message[j];
            }
            Date date = TimeStampCalculator.timeCalc(timestamp);

            byte[] priority = new byte[1];
            priority[0] = message[18];

            byte[] lon = new byte[4];
            for (int i = 0, j = 19; (i < lon.length); i++, j++) {
                lon[i] = message[j];
            }

            double longitude = GPScalculator.longitude(lon);
            byte[] lat = new byte[4];
            for (int i = 0, j = 23; (i < lat.length); i++, j++) {
                lat[i] = message[j];
            }
            double latitude = GPScalculator.latitude(lat);

            byte[] altitude = new byte[2];
            for (int i = 0, j = 27; i < altitude.length; i++) {
                altitude[i] = message[j];
            }

            byte[] angle = new byte[2];
            for (int i = 0, j = 29; i < angle.length; i++) {
                angle[i] = message[j];
            }

            byte[] satellites = new byte[1];
            satellites[0] = message[31];

            byte[] speed = new byte[2];
            for (int i = 0, j = 32; i < speed.length; i++) {
                speed[i] = message[j];
            }

            

            
            con.sendMessage(NofData1);

            System.out.println(" codecID: " + Utils.hexToString(codecID));
            System.out.println(" NofData1: " + Utils.hexToString(NofData1));
            System.out.println(" NofData2: " + Utils.hexToString(NofData2));
            System.out.println(" timestamp: " + date);


            System.out.println(" priority: " + Utils.hexToString(priority));
            System.out.println(" latitude: " + latitude);
            System.out.println(" longitude: " + longitude);

            System.out.println(" altitude: " + Utils.hexToString(altitude));
            System.out.println(" angle: " + Utils.hexToString(angle));
            System.out.println(" satellites: " + Utils.hexToString(satellites));
            System.out.println(" speed: " + Utils.hexToString(speed));
            System.out.println(" CRC16: " + Utils.hexToString(CRC16_parsed));
        }

    }

    @Override
    public void receiveMessage(byte[] message, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
