/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

import com.silocom.m2m.layer.physical.PhysicalLayer;

/**
 *
 * @author silocom01
 */
public class Main {

    public static void main(String[] args) {
       String payload = "08010000016B40D8EA30010000000000000000000000000000000105021503010101425E0F01F10000601A014E000000000000000001";
       String IpSrc = "192.168.2.40"; 
       com.silocom.m2m.layer.physical.Connection con = PhysicalLayer.addConnection(1, 17500, IpSrc);  //colocar tipo de conexion e IP valido
       
       System.out.println(" CRC16: 0x0000" + CRC16.calcCRC16(payload)); // 
    }
}
