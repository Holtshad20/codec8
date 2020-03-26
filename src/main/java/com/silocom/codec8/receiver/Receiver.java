/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8.receiver;

import com.silocom.m2m.layer.physical.Connection;
import com.silocom.m2m.layer.physical.MessageListener;
import java.util.Arrays;

/**
 *
 * @author silocom01
 */
public class Receiver implements MessageListener {

    Connection con;
    private final byte[] imeiExpected;
    private final int imeiLength = 17;
    long timeout;

    private final Object SYNC = new Object();

    private CodecReport answer;

    public Receiver(Connection con, byte[] imeiExpected, long timeout) {
        this.con = con;
        this.imeiExpected = imeiExpected;
        this.timeout = timeout;
    }

    @Override
    public void receiveMessage(byte[] message) {

        final int codec8 = 0x08;
        final int codec8E = 0x8E;
        final int codec12 = 0x0C;

        if (message.length == imeiLength) {
            System.out.println("IMEI message: " + Utils.hexToString(message));

            byte[] imeiReceived = new byte[15];

            System.arraycopy(message, 2, imeiReceived, 0, imeiReceived.length);

            if (Arrays.equals(imeiReceived, imeiExpected)) {
                System.out.println("IMEI equals");
                byte[] accept = new byte[]{0x01};   //acepta el IMEI
                con.sendMessage(accept);
            }

        } else if (message.length > 9) {
            System.out.println("message " + Utils.hexToString(message));
            int messageType = message[8] & 0xFF;  //en el byte 8 del mensaje que no es IMEI, se encuentra el tipo de protocolo utilizado
            System.out.println("mtype " + messageType);
            switch (messageType) {

                case codec8:

                    System.out.println(" codec8 message: " + Utils.hexToString(message));

                    byte[] crc16Codec8Parsed = new byte[4];
                    System.arraycopy(message, message.length - 4, crc16Codec8Parsed, 0, 4);

                    byte[] CRC16Codec8_Calculated = new byte[4];
                    CRC16Codec8_Calculated[0] = 0x00;
                    CRC16Codec8_Calculated[1] = 0x00;
                    byte[] crc = CRC16.calcCRC16(Arrays.copyOfRange(message, 8, message.length - 4));
                    CRC16Codec8_Calculated[2] = crc[0];
                    CRC16Codec8_Calculated[3] = crc[1];
                    System.out.println(" CRC16 " + Utils.hexToString(CRC16Codec8_Calculated));
                    System.out.println(" CRC16 calculated " + Utils.hexToString(CRC16Codec8_Calculated));

                    byte[] AVLData = Arrays.copyOfRange(message, 10, message.length - 5);   //Todos los records

                    try {
                        Parser.parserCodec8(AVLData); //Envio la data (puede ser 1 o mas records, maximo 255 records por paquete) a pasear al metodo parser 
                    } catch (Exception e) {
                        System.err.println("error " + Utils.hexToString(AVLData));
                        e.printStackTrace();
                    }
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

                    //separar el header y demas de la data
                    byte[] codec12Data = Arrays.copyOfRange(message, 15, message.length - 5);

                    byte[] toDecode = new byte[3];

                    toDecode[0] = message[15];  //Primera letra del comando
                    toDecode[1] = message[16];  //...
                    toDecode[2] = message[17];  //...
           
                    String decoded = new String(toDecode);
                    /* verificar las tres primeras letras de cada mensaje para saber 
                                                             que tipo de comando es, se tomo un arreglo de 3 bytes para hacerlo estandar*/
          System.out.println("decoded " + decoded);
                    switch (decoded) {
                        case "GPS": //mensaje de getgps    0x475053
                            synchronized (SYNC) {
                                answer = Parser.codec12Parser_getgps(codec12Data);
                                SYNC.notifyAll();
                            }
                            break;

                        case "DI1": //mensaje de getio    0x444931
                            synchronized (SYNC) {
                                answer = Parser.codec12Parser_getio(codec12Data);
                                SYNC.notifyAll();
                            }
                            break;

                        case "Bat": //mensaje de battery
                            synchronized (SYNC) {
                                answer = Parser.codec12Parser_battery(codec12Data);
                                SYNC.notifyAll();
                            }

                            break;
                    }

                    break;

            }
        }
    }

    // @Override
    public void receiveMessage(byte[] message, Connection con) {
        //  throw new UnsupportedOperationException("Not supported yet.");

        if (message.length == imeiLength) {
            byte[] imeiReceived = new byte[15];

            System.arraycopy(message, 2, imeiReceived, 0, imeiReceived.length);

            if (Arrays.equals(imeiReceived, imeiExpected)) {  //Es el IMEI esperado?
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

    public CodecReport sendMessage(byte[] toSend) {
        answer = null;
        System.out.println("tosend " + Utils.byteToString(toSend));
        con.sendMessage(toSend);

        if (answer == null) {

            synchronized (SYNC) {
                try {
                    SYNC.wait(timeout);
                } catch (InterruptedException ex) {
                }
            }
        }
        return answer;
    }
}
