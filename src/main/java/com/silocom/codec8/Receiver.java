/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

import com.silocom.m2m.layer.physical.Connection;
import com.silocom.m2m.layer.physical.MessageListener;
import java.util.Arrays;

/**
 *
 * @author silocom01
 */
public class Receiver {

    // Connection con;
    private final byte[] rawData;
    private final byte[] IMEIExpected;

    public Receiver(/*Connection con,*/byte[] rawData, byte[] IMEIExpected) {
        //this.con = con;
        this.rawData = rawData;
        this.IMEIExpected = IMEIExpected;
    }

    public void parser() {

        //Si el IMEI recibido es el IMEI esperado, parsear y despues de parsear responder 01, en caso contrario, no hacer nada y responder 00
        //System.out.println(" IMEIExpected: " + Utils.hexToString(IMEIExpected));
        if (rawData.length == IMEIExpected.length + 2) {
            byte[] IMEIReceived = new byte[15];

            for (int i = 0, j = 2; (i < IMEIReceived.length); i++, j++) {
                IMEIReceived[i] = rawData[j];
            }

            byte[] IMEIlenght = new byte[2];
            System.arraycopy(rawData, 0, IMEIlenght, 0, 2);

            if (Arrays.equals(IMEIReceived, IMEIExpected)) {

                //Send 0x01 to the device
            } else {

                //Do nothing
            }

        } else {

            byte[] header = new byte[4];
            System.arraycopy(rawData, 0, header, 0, 3);

            byte[] dataFieldLenght = new byte[4];
            for (int i = 0, j = 4; (i < dataFieldLenght.length); i++, j++) {
                dataFieldLenght[i] = rawData[j];
            }

            byte[] codecID = new byte[1];
            codecID[0] = rawData[8];

            byte[] NofData1 = new byte[1];
            NofData1[0] = rawData[9];

            byte[] timestamp = new byte[8];
            for (int i = 0, j = 10; (i < timestamp.length); i++, j++) {
                timestamp[i] = rawData[j];
            }

            byte[] priority = new byte[1];
            priority[0] = rawData[18];

            byte[] lon = new byte[4];
            for (int i = 0, j = 19; (i < lon.length); i++, j++) {
                lon[i] = rawData[j];
            }
            int longitude = GPScalculator.longitude(lon);

            byte[] lat = new byte[4];
            for (int i = 0, j = 23; (i < lat.length); i++, j++) {
                lat[i] = rawData[j];
            }
            int latitude = GPScalculator.latitude(lat);

            byte[] altitude = new byte[2];
            for (int i = 0, j = 27; i < altitude.length; i++) {
                altitude[i] = rawData[j];
            }

            byte[] angle = new byte[2];
            for (int i = 0, j = 29; i < angle.length; i++) {
                angle[i] = rawData[j];
            }

            byte[] satellites = new byte[1];
            satellites[0] = rawData[31];

            byte[] speed = new byte[2];
            for (int i = 0, j = 32; i < speed.length; i++) {
                speed[i] = rawData[j];
            }

            byte[] eventIO_ID = new byte[1];
            eventIO_ID[0] = rawData[34];
            int eventIO = Utils.byteArrayToInt(eventIO_ID);

            if (eventIO != 0) {  //Si hay un evento de I/O que disparo un evento

                byte[] nTotalID = new byte[1];
                nTotalID[0] = rawData[35];   //Dice cuantos records hay, importante para ajustar a la cantidad de records recibidos

     
                
                //TODO: que pasa si son mas de 5 records o menos? Ajustar para la cantidad de records

            } else {
                eventIO = 0;   //No se ha disparado nigun evento
            }

            System.out.println(" rawData: " + Utils.hexToString(rawData));
            System.out.println(" header: " + Utils.hexToString(header));
            System.out.println(" dataFieldLenght: " + Utils.hexToString(dataFieldLenght));
            System.out.println(" codecID: " + Utils.hexToString(codecID));
            System.out.println(" NofData1: " + Utils.hexToString(NofData1));
            System.out.println(" timestamp: " + Utils.hexToString(timestamp));
            System.out.println(" priority: " + Utils.hexToString(priority));
            System.out.println(" longitude: " + longitude);
            System.out.println(" latitude: " + latitude);
            System.out.println(" altitude: " + Utils.hexToString(altitude));
            System.out.println(" angle: " + Utils.hexToString(angle));
            System.out.println(" satellites: " + Utils.hexToString(satellites));
            System.out.println(" speed: " + Utils.hexToString(speed));
        }

    }

    /*  @Override
    public void receiveMessage(byte[] message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void receiveMessage(byte[] message, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
