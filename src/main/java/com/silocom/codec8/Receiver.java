/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

import com.silocom.m2m.layer.physical.Connection;
import com.silocom.m2m.layer.physical.MessageListener;
import java.util.Arrays;
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

                byte[] AVLData = Arrays.copyOfRange(message, 10, message.length - 5);   //Todos los records

//Si el CRC16 no concuerda, no procesa la data
                byte[] CRC16_Calculated = new byte[4];
                CRC16_Calculated[0] = 0x00;
                CRC16_Calculated[1] = 0x00;
                CRC16_Calculated[2] = CRC16.calcCRC16(Arrays.copyOfRange(message, 8, message.length - 4))[0];
                CRC16_Calculated[3] = CRC16.calcCRC16(Arrays.copyOfRange(message, 8, message.length - 4))[1];

                byte[] codecID = new byte[1];
                codecID[0] = message[8];

                if (Arrays.equals(CRC16_parsed, CRC16_Calculated)) {

                    byte[] NofData1 = new byte[4];
                    NofData1[0] = 0x00;
                    NofData1[1] = 0x00;
                    NofData1[2] = 0x00;
                    NofData1[3] = message[9];

                    byte[] NofData2 = new byte[1];
                    NofData2[0] = messageReversed[4];

                    Parser.Parser(AVLData); //Los records empiezan desde el timestamp hasta el proximo timestamp

                    con.sendMessage(NofData1);

                    System.out.println(" NofData1: " + Integer.parseInt(Utils.hexToString(NofData1), 16));
                    

                } else {
                    //No procesar
                }
            
        }

    }

    @Override
    public void receiveMessage(byte[] message, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
