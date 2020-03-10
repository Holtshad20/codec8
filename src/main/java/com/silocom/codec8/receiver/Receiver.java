/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8.receiver;

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
    final int timeout;

    public Receiver(Connection con, byte[] IMEIExpected, int timeout) {
        this.con = con;
        this.IMEIExpected = IMEIExpected;
        this.timeout = timeout;
    }

    public void receiveMessage(byte[] message) {

        if (message.length == 17) {  //Si es un mensaje con IMEI
            System.out.println(" IMEI: " + Utils.hexToString(message));
            byte[] IMEIReceived = new byte[15];
            for (int i = 0, j = 2; i < message.length - 1; i++, j++) {
                IMEIReceived[i] = message[j];
            }

            byte[] IMEIlenght = new byte[2];
            System.arraycopy(message, 0, IMEIlenght, 0, 2);

            if (Arrays.equals(IMEIReceived, IMEIExpected)) {  //Es el IMEI esperado?

                //Send 0x01 to the device
                byte[] accept = new byte[]{0x01};
                con.sendMessage(accept);         //Envio accept al equipo si es el IMEI
            } else {
                /*byte[] deny = new byte[]{0x00};
                con.sendMessage(deny);  */        //envio deny al equipo si no es el IMEI
            }

        } else {   //Si no es mensaje con IMEI

            byte[] codecID = new byte[1];
            codecID[0] = message[8];
            byte[] codecID_Expected = new byte[]{0x08};

            if (Arrays.equals(codecID, codecID_Expected)) {   //Proceso mensaje si es codec 8

                System.out.println(" Message: " + Utils.hexToString(message));

                byte[] dataFieldLenght = new byte[4];
                for (int i = 0, j = 4; (i < dataFieldLenght.length); i++, j++) {
                    dataFieldLenght[i] = message[j];
                }

                byte[] messageReversed = new byte[message.length];
                messageReversed = message.clone();
                ArrayUtils.reverse(messageReversed);

                byte[] CRC16_parsed = new byte[4];
                for (int i = 0, j = 3; i < CRC16_parsed.length; i++, j--) {
                    CRC16_parsed[i] = messageReversed[j];
                }

                byte[] CRC16_Calculated = new byte[4];
                CRC16_Calculated[0] = 0x00;
                CRC16_Calculated[1] = 0x00;
                CRC16_Calculated[2] = CRC16.calcCRC16(Arrays.copyOfRange(message, 8, message.length - 4))[0];
                CRC16_Calculated[3] = CRC16.calcCRC16(Arrays.copyOfRange(message, 8, message.length - 4))[1];
                System.out.println(" CRC16 " + Utils.hexToString(CRC16_parsed));
                System.out.println(" CRC16 calculated " + Utils.hexToString(CRC16_Calculated));
                if (Arrays.equals(CRC16_parsed, CRC16_Calculated)) {  //Proceso mensaje si el crc es valido

                    byte[] NofData1 = new byte[4];
                    NofData1[0] = 0x00;
                    NofData1[1] = 0x00;
                    NofData1[2] = 0x00;
                    NofData1[3] = message[9];

                    byte[] AVLData = Arrays.copyOfRange(message, 10, message.length - 5);   //Todos los records
                    Parser.parserCodec8(AVLData); //Envio la data (puede ser 1 o mas records, maximo 255 records por paquete) a pasear al metodo parser

                    System.out.println(" NofData1: " + Integer.parseInt(Utils.hexToString(NofData1), 16));

                    con.sendMessage(NofData1); //Envio paquetes recibidos al equipo

                } else {
                    //si no coinciden los crc
                    //ESTO ES SOLO TEMPORAL
                    con.sendMessage("00000000000000150C01050000000D64656c6574657265636f7264730100001263".getBytes());  //delete records
                }

            } else { //implementar luego el timeout
                //No proceso mensajes que no sean codec8
            }
        }

    }

    @Override
    public void receiveMessage(byte[] message, Connection con) {

        byte[] IMEIReceived = new byte[15];
        for (int i = 0, j = 2; i < message.length - 1; i++, j++) {
            IMEIReceived[i] = message[j];
        }

        if (Arrays.equals(IMEIReceived, IMEIExpected)) {  //Es el IMEI esperado?
            this.con = con;  //si es IMEI
            con.addListener(this);
            //Send 0x01 to the device
            byte[] accept = new byte[]{0x01};
            con.sendMessage(accept);         //Envio accept al equipo si es el IMEI
        } else {
            byte[] deny = new byte[]{0x00};
            con.sendMessage(deny);         //envio deny al equipo si no es el IMEI
        }

    }

}
