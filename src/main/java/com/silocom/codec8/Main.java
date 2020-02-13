/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

import com.silocom.m2m.layer.physical.PhysicalLayer;
import java.util.Arrays;

/**
 *
 * @author silocom01
 */
public class Main {

    public static void main(String[] args) {
        String payload = "08010000016B40D8EA30010000000000000000000000000000000105021503010101425E0F01F10000601A014E000000000000000001";
        String IpSrc = "192.168.2.40";
        String rawData = "000000000000003608010000016B40D8EA30010000000000000000000000000000000105021503010101425E0F01F10000601A014E0000000000000000010000C7CF";
       // String rawData = "000F333536333037303432343431303133";
        
byte[] IMEIExpected = new byte[]{0x33, 0x35, 0x36, 0x33, 0x30, 0x37, 0x30, 0x34, 0x32, 0x34, 0x34, 0x31, 0x30, 0x31, 0x33};
        
        byte[] rawDataHex = CRC16.hexStringToByteArray(rawData);
        

        com.silocom.m2m.layer.physical.Connection con = PhysicalLayer.addConnection(1, 17500, IpSrc);  //colocar tipo de conexion e IP valido
        Receiver rec = new Receiver(rawDataHex, IMEIExpected);

        rec.parser();

        System.out.println(" CRC16: 0x0000" + CRC16.calcCRC16(rawData)); // 
    }
}
