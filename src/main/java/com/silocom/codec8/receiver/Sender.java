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
    private final static String GETINFO = "command";   //Todos los mensajes de comando estan creados ya
    private final static String GETVER = "";
    private final static String GETSTATUS = "";
    private final static String GETGPS = "";
    private final static String GETIO = "";
    private final static String READIO = "";
    private final static String BATTERY = "";
    private final static String SETDIGOUT1 = "";
    private final static String SETDIGOUT0 = "";
    private final static String GGPS = "";


    public void commands(String command) {
        //Scanner input = new Scanner(System.in);

        switch (command) {

            case "getinfo":   //Device runtime system information

                byte[] getinfor = GETINFO.getBytes();
                rec.getCon().sendMessage(getinfor);

                break;

            case "getver":  //Returns code version, device IMEI, modem app version, RTC time, Init time, Uptime and BT MAC address.

                byte[] getvers = GETVER.getBytes();
                rec.getCon().sendMessage(getvers);

                break;

            case "getstatus": //Modem Status information

                byte[] getstat = GETSTATUS.getBytes();
               rec.getCon().sendMessage(getstat);

                break;

            case "getgps":  //	Current GPS data, date and time

                byte[] getgpss = GETGPS.getBytes();
                rec.getCon().sendMessage(getgpss);

                break;

            case "getio":  //Readout analog input,digital input and output

                byte[] getioo = GETIO.getBytes();
                rec.getCon().sendMessage(getioo);

                break;

            case "ggps": //Returns location information with Google maps link

                byte[] ggpss = GGPS.getBytes();  //Not sure
                rec.getCon().sendMessage(ggpss);

                break;

            case "readio":  //Returns IO status, # AVL ID
                byte[] readioo = READIO.getBytes();  //Not sure
                rec.getCon().sendMessage(readioo);

                break;

            case "battery":  //Returns battery state info

                byte[] batteryy = BATTERY.getBytes();  //Not sure
                rec.getCon().sendMessage(batteryy);

                break;

            case "setdigout 1":  //Set digital output ## DOUT1 DOUT2

                byte[] setdigout1_ = SETDIGOUT1.getBytes();
                rec.getCon().sendMessage(setdigout1_);

                break;

            case "setdigout 0":

                byte[] setdigout0_ = SETDIGOUT0.getBytes();
                rec.getCon().sendMessage(setdigout0_);

                break;

        }
    }

}
