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
public class Sender{

    Connection con;
    private final String getinfo;   //Todos los mensajes de comando estan creados ya
    private final String getver;
    private final String getstatus;
    private final String getgps;
    private final String getio;
    private final String readio;
    private final String battery;
    private final String setdigout1;
    private final String setdigout0;
    private final String ggps;
    
    public Sender(Connection con, String getinfo, String getver, String getstatus, String getgps, String getio, String ggps,
            String readio, String battery, String setdiout1, String setdigout0) {
        
        this.con = con;
        this.getinfo = getinfo;
        this.getver = getver;
        this.getstatus = getstatus;
        this.getgps = getgps;
        this.ggps = ggps;
        this.getio = getio;
        this.readio = readio;
        this.battery = battery;
        this.setdigout1 = setdiout1;
        this.setdigout0 = setdigout0;
    }

    

    public void commands(String command) {
        //Scanner input = new Scanner(System.in);
        
        switch (command) {

            case "getinfo":   //Device runtime system information

                byte[] getinfor = getinfo.getBytes();
                con.sendMessage(getinfor);

                break;

            case "getver":  //Returns code version, device IMEI, modem app version, RTC time, Init time, Uptime and BT MAC address.

                byte[] getvers = getver.getBytes();
                con.sendMessage(getvers);

                break;

            case "getstatus": //Modem Status information

                byte[] getstat = getstatus.getBytes();
                con.sendMessage(getstat);

                break;

            case "getgps":  //	Current GPS data, date and time

                byte[] getgpss = getgps.getBytes();
                con.sendMessage(getgpss);

                break;

            case "getio":  //Readout analog input,digital input and output

                byte[] getioo = getio.getBytes();
                con.sendMessage(getioo);

                break;

            case "ggps": //Returns location information with Google maps link

                byte[] ggpss = ggps.getBytes();  //Not sure
                con.sendMessage(ggpss);

                break;

            case "readio":  //Returns IO status, # AVL ID
                byte[] readioo = readio.getBytes();  //Not sure
                con.sendMessage(readioo);

                break;

            case "battery":  //Returns battery state info

                byte[] batteryy = battery.getBytes();  //Not sure
                con.sendMessage(batteryy);

                break;

            case "setdigout 1":  //Set digital output ## DOUT1 DOUT2

                byte[] setdigout1_ = setdigout1.getBytes();
                con.sendMessage(setdigout1_);

                break;

            case "setdigout 0":

                byte[] setdigout0_ = setdigout0.getBytes();
                con.sendMessage(setdigout0_);

                break;

        }
    }


}
