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
    private final byte[] imeiExpected;
    final int timeout;

    public Receiver(Connection con, byte[] imeiExpected, int timeout) {
        this.con = con;
        this.imeiExpected = imeiExpected;
        this.timeout = timeout;
    }

    public void receiveMessage(byte[] message) {

        final int codec8 = 0x08;
        final int codec8E = 0x8E;
        final int codec12 = 0x0C;
        final int imeiLength = 17;

        if (message.length == imeiLength) {
            System.out.println("IMEI message: " + Utils.hexToString(message));

            byte[] imeiReceived = new byte[15];

            imeiReceived[0] = message[2];
            imeiReceived[1] = message[3];
            imeiReceived[2] = message[4];
            imeiReceived[3] = message[5];
            imeiReceived[4] = message[6];
            imeiReceived[5] = message[7];
            imeiReceived[6] = message[8];
            imeiReceived[7] = message[9];
            imeiReceived[8] = message[10];
            imeiReceived[9] = message[11];
            imeiReceived[10] = message[12];
            imeiReceived[11] = message[13];
            imeiReceived[12] = message[14];
            imeiReceived[13] = message[15];
            imeiReceived[14] = message[16];

            if (Arrays.equals(imeiReceived, imeiExpected)) {

                byte[] accept = new byte[]{0x01};   //acepta el IMEI
                con.sendMessage(accept);
            }

        } else {

            int messageType = message[8] & 0xFF;  //en el byte 8 del mensaje que no es IMEI, se encuentra el tipo de protocolo utilizado
            switch (messageType) {

                case codec8:

                    System.out.println(" codec8 message: " + Utils.hexToString(message));

                    byte[] crc16Codec8Parsed = new byte[4];
                    System.arraycopy(message, message.length - 4, crc16Codec8Parsed, 0, 4);

                    byte[] CRC16Codec8_Calculated = new byte[4];
                    CRC16Codec8_Calculated[0] = 0x00;
                    CRC16Codec8_Calculated[1] = 0x00;
                    CRC16Codec8_Calculated[2] = CRC16.calcCRC16(Arrays.copyOfRange(message, 8, message.length - 4))[0];
                    CRC16Codec8_Calculated[3] = CRC16.calcCRC16(Arrays.copyOfRange(message, 8, message.length - 4))[1];
                    System.out.println(" CRC16 " + Utils.hexToString(CRC16Codec8_Calculated));
                    System.out.println(" CRC16 calculated " + Utils.hexToString(CRC16Codec8_Calculated));

                    byte[] AVLData = Arrays.copyOfRange(message, 10, message.length - 5);   //Todos los records
                    //Parser.parserCodec8(AVLData); //Envio la data (puede ser 1 o mas records, maximo 255 records por paquete) a pasear al metodo parser 

                    byte[] NofData1 = new byte[4];
                    NofData1[0] = 0x00;
                    NofData1[1] = 0x00;
                    NofData1[2] = 0x00;
                    NofData1[3] = message[9];

                    byte[] NofData2 = new byte[4];
                    NofData2[0] = 0x00;
                    NofData2[1] = 0x00;
                    NofData2[2] = 0x00;
                    NofData2[3] = message[message.length - 5];

                    System.out.println(" NofData1: " + Integer.parseInt(Utils.hexToString(NofData1), 16));
                    System.out.println(" NofData2: " + Integer.parseInt(Utils.hexToString(NofData2), 16));

                    con.sendMessage(NofData1);

                    break;

                case codec8E:

                    System.out.println(" codec8E message: " + Utils.hexToString(message));

                    byte[] crc16Codec8EParsed = new byte[4];
                    System.arraycopy(message, message.length - 4, crc16Codec8EParsed, 0, 4);

                    byte[] CRC16Codec8E_Calculated = new byte[4];
                    CRC16Codec8E_Calculated[0] = 0x00;
                    CRC16Codec8E_Calculated[1] = 0x00;
                    CRC16Codec8E_Calculated[2] = CRC16.calcCRC16(Arrays.copyOfRange(message, 8, message.length - 4))[0];
                    CRC16Codec8E_Calculated[3] = CRC16.calcCRC16(Arrays.copyOfRange(message, 8, message.length - 4))[1];
                    System.out.println(" CRC16 " + Utils.hexToString(CRC16Codec8E_Calculated));
                    System.out.println(" CRC16 calculated " + Utils.hexToString(CRC16Codec8E_Calculated));
                    if (Arrays.equals(crc16Codec8EParsed, CRC16Codec8E_Calculated)) {

                        //TODO: Implementacion de codec8 Extended
                    }

                    break;

                case codec12:
                    System.out.println(" codec12 message: " + Utils.hexToString(message));

                    //separar la información de cabecera de la data
                    byte[] codec12Data = Arrays.copyOfRange(message, 15, message.length - 5);
                    
                    byte[] toDecode = new byte[3];
                    
                    toDecode[0] = message[15];  //First word 
                    toDecode[1] = message[16];  //...
                    toDecode[3] = message[17];  //...
                    
                    String decoded = new String(toDecode);
                    //de alguna forma verificar la primera variable recibida en el mensaje y hacer switch/case
                    switch (decoded) {
                        case "RTC":  //mensaje de getinfo   0x525443 -- RTC en HEX
                            Parser.codec12Parser_getinfo(codec12Data);
                            break;

                        case "Ver": //mensaje de getver  0x566572 
                            Parser.codec12Parser_getver(codec12Data);
                            break;

                        case "Dat": //getstatus   0x446174 
                            Parser.codec12Parser_getstatus(codec12Data);
                            break;

                        case "GPS": //mensaje de getgps    0x475053
                            Parser.codec12Parser_getgps(codec12Data);
                            break;

                        case "DI1": //mensaje de getio    0x444931
                            Parser.codec12Parser_getio(codec12Data);
                            break;

                        case "Bat": //mensaje de battery
                            Parser.codec12Parser_battery(codec12Data);
                            break;
                    }
                    
                    break;

            }
        }
    }

    @Override
    public void receiveMessage(byte[] message, Connection con
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
        /*  byte[] IMEIReceived = new byte[15];
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
        }*/

    }

}
