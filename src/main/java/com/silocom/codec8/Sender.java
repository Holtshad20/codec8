/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

import com.silocom.m2m.layer.physical.Connection;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author silocom01
 */
public class Sender {

    Connection con;

    public Sender(Connection con) {
        this.con = con;
    }

    Scanner input = new Scanner(System.in);
    String command = input.nextLine();

    public void commands() {
        byte[] header = new byte[4];

        switch (command) {

            case "getinfo":   //676574696e666f
                byte[] getinfoHex = "getinfo".getBytes();
                byte[] sendGetInfo = new byte[19 + getinfoHex.length];

                sendGetInfo[0] = header[0];
                sendGetInfo[1] = header[1];
                sendGetInfo[2] = header[2];
                sendGetInfo[3] = header[3];

                sendGetInfo[4] = 0x00;
                sendGetInfo[5] = 0x00;
                sendGetInfo[6] = 0x00;
                sendGetInfo[7] = (byte) Arrays.copyOfRange(sendGetInfo, 8, sendGetInfo.length - 4).length;

                sendGetInfo[8] = 0x0C;  //always
                sendGetInfo[9] = 0x01;  //only one commmand
                sendGetInfo[10] = 0x05;  //for request

                sendGetInfo[11] = 0x00;
                sendGetInfo[12] = 0x00;
                sendGetInfo[13] = 0x00;
                sendGetInfo[14] = (byte) getinfoHex.length;

                sendGetInfo[15] = getinfoHex[0];
                sendGetInfo[16] = getinfoHex[1];
                sendGetInfo[17] = getinfoHex[2];
                sendGetInfo[18] = getinfoHex[3];
                sendGetInfo[19] = getinfoHex[4];
                sendGetInfo[20] = getinfoHex[5];
                sendGetInfo[21] = getinfoHex[6];

                sendGetInfo[22] = sendGetInfo[9];

                sendGetInfo[23] = 0x00;
                sendGetInfo[24] = 0x00;
                sendGetInfo[25] = CRC16.calcCRC16(Arrays.copyOfRange(sendGetInfo, 9, sendGetInfo.length - 4))[0];
                sendGetInfo[26] = CRC16.calcCRC16(Arrays.copyOfRange(sendGetInfo, 9, sendGetInfo.length - 4))[1];

                con.sendMessage(sendGetInfo);

                break;

            case "getver":
                byte[] getverHex = "getver".getBytes();
                byte[] sendGetVer = new byte[19 + getverHex.length];

                sendGetVer[0] = header[0];
                sendGetVer[1] = header[1];
                sendGetVer[2] = header[2];
                sendGetVer[3] = header[3];

                sendGetVer[4] = 0x00;
                sendGetVer[5] = 0x00;
                sendGetVer[6] = 0x00;
                sendGetVer[7] = (byte) Arrays.copyOfRange(sendGetVer, 8, sendGetVer.length - 4).length;

                sendGetVer[8] = 0x0C;  //always
                sendGetVer[9] = 0x01;  //only one commmand
                sendGetVer[10] = 0x05;  //for request

                sendGetVer[11] = 0x00;
                sendGetVer[12] = 0x00;
                sendGetVer[13] = 0x00;
                sendGetVer[14] = (byte) getverHex.length;

                sendGetVer[15] = getverHex[0];
                sendGetVer[16] = getverHex[1];
                sendGetVer[17] = getverHex[2];
                sendGetVer[18] = getverHex[3];
                sendGetVer[19] = getverHex[4];
                sendGetVer[20] = getverHex[5];

                sendGetVer[21] = sendGetVer[9];

                sendGetVer[22] = 0x00;
                sendGetVer[23] = 0x00;
                sendGetVer[24] = CRC16.calcCRC16(Arrays.copyOfRange(sendGetVer, 9, sendGetVer.length - 4))[0];
                sendGetVer[25] = CRC16.calcCRC16(Arrays.copyOfRange(sendGetVer, 9, sendGetVer.length - 4))[1];

                con.sendMessage(sendGetVer);

                break;

            case "getstatus":
                byte[] getstatusHex = "getstatus".getBytes();  //Not sure
                byte[] sendGetStatus = new byte[19 + getstatusHex.length];

                sendGetStatus[0] = header[0];
                sendGetStatus[1] = header[1];
                sendGetStatus[2] = header[2];
                sendGetStatus[3] = header[3];

                sendGetStatus[4] = 0x00;
                sendGetStatus[5] = 0x00;
                sendGetStatus[6] = 0x00;
                sendGetStatus[7] = (byte) Arrays.copyOfRange(sendGetStatus, 8, sendGetStatus.length - 4).length;

                sendGetStatus[8] = 0x0C;  //always
                sendGetStatus[9] = 0x01;  //only one commmand
                sendGetStatus[10] = 0x05;  //for request

                sendGetStatus[11] = 0x00;
                sendGetStatus[12] = 0x00;
                sendGetStatus[13] = 0x00;
                sendGetStatus[14] = (byte) getstatusHex.length;

                sendGetStatus[15] = getstatusHex[0];
                sendGetStatus[16] = getstatusHex[1];
                sendGetStatus[17] = getstatusHex[2];
                sendGetStatus[18] = getstatusHex[3];
                sendGetStatus[19] = getstatusHex[4];//9
                sendGetStatus[20] = getstatusHex[5];
                sendGetStatus[21] = getstatusHex[6];
                sendGetStatus[22] = getstatusHex[7];
                sendGetStatus[23] = getstatusHex[8];

                sendGetStatus[24] = sendGetStatus[9];

                sendGetStatus[25] = 0x00;
                sendGetStatus[26] = 0x00;
                sendGetStatus[27] = CRC16.calcCRC16(Arrays.copyOfRange(sendGetStatus, 9, sendGetStatus.length - 4))[0];
                sendGetStatus[28] = CRC16.calcCRC16(Arrays.copyOfRange(sendGetStatus, 9, sendGetStatus.length - 4))[1];

                con.sendMessage(sendGetStatus);

                break;

            case "getgps":
                byte[] getgpsHex = "getgps".getBytes();  //Not sure
                byte[] sendGetGps = new byte[19 + getgpsHex.length];

                sendGetGps[0] = header[0];
                sendGetGps[1] = header[1];
                sendGetGps[2] = header[2];
                sendGetGps[3] = header[3];

                sendGetGps[4] = 0x00;
                sendGetGps[5] = 0x00;
                sendGetGps[6] = 0x00;
                sendGetGps[7] = (byte) Arrays.copyOfRange(sendGetGps, 8, sendGetGps.length - 4).length;

                sendGetGps[8] = 0x0C;  //always
                sendGetGps[9] = 0x01;  //only one commmand
                sendGetGps[10] = 0x05;  //for request

                sendGetGps[11] = 0x00;
                sendGetGps[12] = 0x00;
                sendGetGps[13] = 0x00;
                sendGetGps[14] = (byte) getgpsHex.length;

                sendGetGps[15] = getgpsHex[0];
                sendGetGps[16] = getgpsHex[1];
                sendGetGps[17] = getgpsHex[2];
                sendGetGps[18] = getgpsHex[3];
                sendGetGps[19] = getgpsHex[4];
                sendGetGps[20] = getgpsHex[5];

                sendGetGps[21] = sendGetGps[9];

                sendGetGps[22] = 0x00;
                sendGetGps[23] = 0x00;
                sendGetGps[24] = CRC16.calcCRC16(Arrays.copyOfRange(sendGetGps, 9, sendGetGps.length - 4))[0];
                sendGetGps[25] = CRC16.calcCRC16(Arrays.copyOfRange(sendGetGps, 9, sendGetGps.length - 4))[1];

                con.sendMessage(sendGetGps);

                break;

            case "getio":
                byte[] getioHex = "getio".getBytes();  //Not sure
                byte[] sendGetIo = new byte[19 + getioHex.length];

                sendGetIo[0] = header[0];
                sendGetIo[1] = header[1];
                sendGetIo[2] = header[2];
                sendGetIo[3] = header[3];

                sendGetIo[4] = 0x00;
                sendGetIo[5] = 0x00;
                sendGetIo[6] = 0x00;
                sendGetIo[7] = (byte) Arrays.copyOfRange(sendGetIo, 8, sendGetIo.length - 4).length;

                sendGetIo[8] = 0x0C;  //always
                sendGetIo[9] = 0x01;  //only one commmand
                sendGetIo[10] = 0x05;  //for request

                sendGetIo[11] = 0x00;
                sendGetIo[12] = 0x00;
                sendGetIo[13] = 0x00;
                sendGetIo[14] = (byte) getioHex.length;

                sendGetIo[15] = getioHex[0];
                sendGetIo[16] = getioHex[1];
                sendGetIo[17] = getioHex[2];
                sendGetIo[18] = getioHex[3];
                sendGetIo[19] = getioHex[4];

                sendGetIo[20] = sendGetIo[9];

                sendGetIo[21] = 0x00;
                sendGetIo[22] = 0x00;
                sendGetIo[23] = CRC16.calcCRC16(Arrays.copyOfRange(sendGetIo, 9, sendGetIo.length - 4))[0];
                sendGetIo[24] = CRC16.calcCRC16(Arrays.copyOfRange(sendGetIo, 9, sendGetIo.length - 4))[1];

                con.sendMessage(sendGetIo);
                break;

            case "ggps":
                byte[] ggpsHex = "ggps".getBytes();  //Not sure
                byte[] sendggps = new byte[19 + ggpsHex.length];

                sendggps[0] = header[0];
                sendggps[1] = header[1];
                sendggps[2] = header[2];
                sendggps[3] = header[3];

                sendggps[4] = 0x00;
                sendggps[5] = 0x00;
                sendggps[6] = 0x00;
                sendggps[7] = (byte) Arrays.copyOfRange(sendggps, 8, sendggps.length - 4).length;

                sendggps[8] = 0x0C;  //always
                sendggps[9] = 0x01;  //only one commmand
                sendggps[10] = 0x05;  //for request

                sendggps[11] = 0x00;
                sendggps[12] = 0x00;
                sendggps[13] = 0x00;
                sendggps[14] = (byte) ggpsHex.length;

                sendggps[15] = ggpsHex[0];
                sendggps[16] = ggpsHex[1];
                sendggps[17] = ggpsHex[2];
                sendggps[18] = ggpsHex[3];

                sendggps[19] = sendggps[9];

                sendggps[20] = 0x00;
                sendggps[21] = 0x00;
                sendggps[22] = CRC16.calcCRC16(Arrays.copyOfRange(sendggps, 9, sendggps.length - 4))[0];
                sendggps[23] = CRC16.calcCRC16(Arrays.copyOfRange(sendggps, 9, sendggps.length - 4))[1];

                con.sendMessage(sendggps);
                break;

            case "readio":
                byte[] readioHex = "readio".getBytes();  //Not sure
                byte[] sendReadio = new byte[19 + readioHex.length];

                sendReadio[0] = header[0];
                sendReadio[1] = header[1];
                sendReadio[2] = header[2];
                sendReadio[3] = header[3];

                sendReadio[4] = 0x00;
                sendReadio[5] = 0x00;
                sendReadio[6] = 0x00;
                sendReadio[7] = (byte) Arrays.copyOfRange(sendReadio, 8, sendReadio.length - 4).length;

                sendReadio[8] = 0x0C;  //always
                sendReadio[9] = 0x01;  //only one commmand
                sendReadio[10] = 0x05;  //for request

                sendReadio[11] = 0x00;
                sendReadio[12] = 0x00;
                sendReadio[13] = 0x00;
                sendReadio[14] = (byte) readioHex.length;

                sendReadio[15] = readioHex[0];
                sendReadio[16] = readioHex[1];
                sendReadio[17] = readioHex[2];
                sendReadio[18] = readioHex[3];
                sendReadio[19] = readioHex[4];
                sendReadio[20] = readioHex[5];

                sendReadio[21] = sendReadio[9];

                sendReadio[22] = 0x00;
                sendReadio[23] = 0x00;
                sendReadio[24] = CRC16.calcCRC16(Arrays.copyOfRange(sendReadio, 9, sendReadio.length - 4))[0];
                sendReadio[25] = CRC16.calcCRC16(Arrays.copyOfRange(sendReadio, 9, sendReadio.length - 4))[1];

                con.sendMessage(sendReadio);

                break;

            case "battery":
                byte[] batteryHex = "battery".getBytes();  //Not sure
                byte[] sendBattery = new byte[19 + batteryHex.length];

                sendBattery[0] = header[0];
                sendBattery[1] = header[1];
                sendBattery[2] = header[2];
                sendBattery[3] = header[3];

                sendBattery[4] = 0x00;
                sendBattery[5] = 0x00;
                sendBattery[6] = 0x00;
                sendBattery[7] = (byte) Arrays.copyOfRange(sendBattery, 8, sendBattery.length - 4).length;

                sendBattery[8] = 0x0C;  //always
                sendBattery[9] = 0x01;  //only one commmand
                sendBattery[10] = 0x05;  //for request

                sendBattery[11] = 0x00;
                sendBattery[12] = 0x00;
                sendBattery[13] = 0x00;
                sendBattery[14] = (byte) batteryHex.length;

                sendBattery[15] = batteryHex[0];
                sendBattery[16] = batteryHex[1];
                sendBattery[17] = batteryHex[2];
                sendBattery[18] = batteryHex[3];
                sendBattery[19] = batteryHex[4];
                sendBattery[20] = batteryHex[5];
                sendBattery[21] = batteryHex[6];

                sendBattery[22] = sendBattery[9];

                sendBattery[23] = 0x00;
                sendBattery[24] = 0x00;
                sendBattery[25] = CRC16.calcCRC16(Arrays.copyOfRange(sendBattery, 9, sendBattery.length - 4))[0];
                sendBattery[26] = CRC16.calcCRC16(Arrays.copyOfRange(sendBattery, 9, sendBattery.length - 4))[1];

                con.sendMessage(sendBattery);
                
                break;
                
            case "setdigout 11":
          
                break;
           
            case "setdigout 10":
            
                break;
           
            case "setdigout 01":
            
                break;
            
            case "setdigout 00":
                
                break;
                
            case "setdigout ??":
         
                break;
          
            case "setdigout ?1":
            
                break;
           
            case "setdigout 1?":
                
                break;

            case "setdigout ?0":
                
                break;
            
            case "setdigout 0?":
            
                break;
            
        }

    }

    public void set() {

        
        
    }

}
