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
        System.out.println(" IMEIExpected: " + Utils.hexToString(IMEIExpected));

        if (rawData.length == 17) {
            byte[] IMEIlenght = new byte[2];
            IMEIlenght[0] = rawData[0];
            IMEIlenght[1] = rawData[1];

            byte[] IMEIReceived = new byte[15];

            IMEIReceived[0] = rawData[2];
            IMEIReceived[1] = rawData[3];
            IMEIReceived[2] = rawData[4];
            IMEIReceived[3] = rawData[5];
            IMEIReceived[4] = rawData[6];
            IMEIReceived[5] = rawData[7];
            IMEIReceived[6] = rawData[8];
            IMEIReceived[7] = rawData[9];
            IMEIReceived[8] = rawData[10];
            IMEIReceived[9] = rawData[11];
            IMEIReceived[10] = rawData[12];
            IMEIReceived[11] = rawData[13];
            IMEIReceived[12] = rawData[14];
            IMEIReceived[13] = rawData[15];
            IMEIReceived[14] = rawData[16];
            System.out.println(" IMEIReceived: " + Utils.hexToString(IMEIReceived));
            if (Arrays.equals(IMEIReceived, IMEIExpected)) {

                //Send 0x01 to the device
            } else {

                //Send 0x00 to the device
            }

        } else {

            System.out.println(" DATA ");
            byte[] header = new byte[4];
            System.arraycopy(rawData, 0, header, 0, 3);

            byte[] dataFieldLenght = new byte[4];
            dataFieldLenght[0] = rawData[4];
            dataFieldLenght[1] = rawData[5];
            dataFieldLenght[2] = rawData[6];
            dataFieldLenght[3] = rawData[7];

            byte[] codecID = new byte[1];
            codecID[0] = rawData[8];

            byte[] NofData1 = new byte[1];
            NofData1[0] = rawData[9];
            System.out.println(" rawData: " + Utils.hexToString(rawData));
            System.out.println(" header: " + Utils.hexToString(header));
            System.out.println(" dataFieldLenght: " + Utils.hexToString(dataFieldLenght));
            System.out.println(" codecID: " + Utils.hexToString(codecID));
            System.out.println(" NofData1: " + Utils.hexToString(NofData1));
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
