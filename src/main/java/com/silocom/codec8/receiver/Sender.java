/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8.receiver;

import com.silocom.codec8.receiver.Utils;
import com.silocom.m2m.layer.physical.Connection;
import com.silocom.m2m.layer.physical.MessageListener;
import java.util.Scanner;

/**
 *
 * @author silocom01
 */
public class Sender {

    Receiver rec;

    private final static String GETINFO = "000000000000000F0C010500000007676574696E666F0100004312";   //Todos los mensajes de comando estan creados ya
    private final static String GETVER = "000000000000000E0C010500000006676574766572010000A4C2";
    private final static String GETSTATUS = "00000000000000110C0105000000096765747374617475730100008CD7";
    private final static String GETGPS = "000000000000000E0C010500000006676574677073010000CCD7";
    private final static String GETIO = "000000000000000D0C010500000005676574696f01000000CB";
    private final static String BATTERY = "000000000000000F0C01050000000762617474657279010000FE37";
    private final static String SETDIGOUT1 = "00000000000000130C01050000000B7365746469676f7574203101000087A2";
    private final static String SETDIGOUT0 = "00000000000000130C01050000000B7365746469676f7574203001000017A3";

    public void commands(String command) {

        switch (command) {

            case "getinfo":   //Device runtime system information

                byte[] getinfo = GETINFO.getBytes();
               CodecReport report = rec.sendMessage(getinfo);
              
                if (report == null) {
                    
                    //TODO
                }else{}
                break;

            case "getver":  //Returns code version, device IMEI, modem app version, RTC time, Init time, Uptime and BT MAC address.

                byte[] getver = GETVER.getBytes();
                rec.sendMessage(getver);

                break;

            case "getstatus": //Modem Status information

                byte[] getstatus = GETSTATUS.getBytes();
                //    rec.getCon().sendMessage(getstatus);

                break;

            case "getgps":  //	Current GPS data, date and time

                byte[] getgps = GETGPS.getBytes();
                //     rec.getCon().sendMessage(getgps);

                break;

            case "getio":  //Readout analog input,digital input and output

                byte[] getio = GETIO.getBytes();
                rec.sendMessage(getio);

                break;

            case "battery":  //Returns battery state info

                byte[] battery = BATTERY.getBytes();  //Not sure
                rec.sendMessage(battery);

                break;

            case "setdigout 1":  //Set digital output ## DOUT1 DOUT2

                byte[] setdigout1 = SETDIGOUT1.getBytes();
                rec.sendMessage(setdigout1);

                break;

            case "setdigout 0":

                byte[] setdigout0 = SETDIGOUT0.getBytes();
                rec.sendMessage(setdigout0);

                break;

        }
    }

}
