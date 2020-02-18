/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

import com.silocom.m2m.layer.physical.Connection;
import com.silocom.m2m.layer.physical.MessageListener;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author silocom01
 */
public class Receiver implements MessageListener {

    Connection con;
    private final byte[] message;
    private final byte[] IMEIExpected;

    public Receiver(Connection con, byte[] rawData, byte[] IMEIExpected) {
        this.con = con;
        this.message = rawData;
        this.IMEIExpected = IMEIExpected;

    }

    public void receiveMessage(byte[] message) {

        //Si el IMEI recibido es el IMEI esperado, parsear y despues de parsear responder 01, en caso contrario, no hacer nada y responder 00
        System.out.println(" rawdata: " + Utils.hexToString(message));
        if (message.length == 17) {

            byte[] IMEIReceived = new byte[15];
            try {
                for (int i = 0, j = 2; i < message.length - 1; i++, j++) {
                    IMEIReceived[i] = message[j];
                }
            } catch (Exception e) {

            }

            //System.out.println(" IMEIReceived: " + Utils.hexToString(IMEIReceived));
            // System.out.println(" IMEIExpected: " + Utils.hexToString(IMEIExpected));
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
            for (int i = 0, j = 4; (i < dataFieldLenght.length - 1); i++, j++) {
                dataFieldLenght[i] = message[j];
            }

            byte[] codecID = new byte[1];
            codecID[0] = message[8];

            byte[] NofData1 = new byte[1];
            NofData1[0] = message[9];

            byte[] timestamp = new byte[8];
            for (int i = 0, j = 10; (i < timestamp.length); i++, j++) {
                timestamp[i] = message[j];
            }  
           // int pomAsInt = ByteBuffer.wrap(timestamp).getInt();


            byte[] priority = new byte[1];
            priority[0] = message[18];

            byte[] lon = new byte[4];
            for (int i = 0, j = 19; (i < lon.length); i++, j++) {
                lon[i] = message[j];
            }
            int longitude = GPScalculator.longitude(lon);

            byte[] lat = new byte[4];
            for (int i = 0, j = 23; (i < lat.length); i++, j++) {
                lat[i] = message[j];
            }
            int latitude = GPScalculator.latitude(lat);

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

            byte[] eventIO_ID = new byte[1];
            eventIO_ID[0] = message[34];
            //int eventIO = Utils.byteArrayToInt(eventIO_ID);

            if (eventIO_ID != null) {  //Si hay un evento de I/O que disparo un evento

                byte[] nTotalID = new byte[1];
                nTotalID[0] = message[35];   //Dice cuantos records hay, importante para ajustar a la cantidad de records recibidos

                byte[] frstIO_ID = new byte[1];
                frstIO_ID[0] = message[37];
                byte[] frstIO_Value = new byte[1];
                frstIO_Value[0] = message[38];

                byte[] scndIO_ID = new byte[1];
                scndIO_ID[0] = message[39];

                byte[] scndIO_Value = new byte[1];
                scndIO_Value[0] = message[40];

                byte[] thrdIO_ID = new byte[1];
                thrdIO_ID[0] = message[41];
                byte[] thrdIO_Value = new byte[2];
                for (int i = 0, j = 42; i < thrdIO_Value.length; i++, j++) {
                    thrdIO_Value[i] = message[j];
                }

                byte[] frthIO_ID = new byte[1];
                frthIO_ID[0] = message[44];
                byte[] frthIO_Value = new byte[4];
                for (int i = 0, j = 45; i < frthIO_Value.length; i++, j++) {
                    frthIO_Value[i] = message[j];
                }

                byte[] fthIO_ID = new byte[1];
                fthIO_ID[0] = message[49];
                byte[] fthIO_Value = new byte[8];
                for (int i = 0, j = 50; i < fthIO_Value.length; i++, j++) {
                    fthIO_Value[i] = message[j];
                }

                /*System.out.println(" frstIO_ID: " + Utils.hexToString(frstIO_ID));
                System.out.println(" frstIO_Value: " + Utils.hexToString(frstIO_Value));
                System.out.println(" scndIO_ID: " + Utils.hexToString(scndIO_ID));
                System.out.println(" scndIO_Value: " + Utils.hexToString(scndIO_Value));
                System.out.println(" thrdIO_ID: " + Utils.hexToString(thrdIO_ID));
                System.out.println(" thrdIO_Value: " + Utils.hexToString(thrdIO_Value));
                System.out.println(" frthIO_ID: " + Utils.hexToString(frthIO_ID));
                System.out.println(" frthIO_Value: " + Utils.hexToString(frthIO_Value));*/
                //TODO: que pasa si son mas de 5 records o menos? Ajustar para la cantidad de records
            } else {
                eventIO_ID[0] = 0;   //No se ha disparado nigun evento
            }

            System.out.println(" header: " + Utils.hexToString(header));
            System.out.println(" dataFieldLenght: " + Utils.hexToString(dataFieldLenght));
            System.out.println(" codecID: " + Utils.hexToString(codecID));
            System.out.println(" timestamp: " + timestamp);
            //System.out.println(" NofData1: " + Utils.hexToString(NofData1));
          
            // System.out.println(" priority: " + Utils.hexToString(priority));
            //  System.out.println(" longitude: " + longitude);
            //  System.out.println(" latitude: " + latitude);
            //   System.out.println(" altitude: " + Utils.hexToString(altitude));
            //   System.out.println(" angle: " + Utils.hexToString(angle));
            System.out.println(" satellites: " + Utils.hexToString(satellites));
            System.out.println(" speed: " + Utils.hexToString(speed));
        }

    }

    @Override
    public void receiveMessage(byte[] message, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
