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
                
                byte[] getinfo = "000000000000000F0C010500000007676574696E666F0100004312".getBytes();
                con.sendMessage(getinfo);

                break;

            case "getver":
                
                byte[] getver = "000000000000000E0C010500000006676574766572010000A4C2".getBytes();
                con.sendMessage(getver);

                break;

            case "getstatus":
                
                byte[] getstatus = "getstatus".getBytes();  //Not sure
                con.sendMessage(getstatus);

                break;

            case "getgps":
               
                byte[] getgps = "getgps".getBytes();  //Not sure
                con.sendMessage(getgps);

                break;

            case "getio":
                
                byte[] getio = "getio".getBytes();  //Not sure
                con.sendMessage(getio);
                
                break;

            case "ggps":
               
                byte[] ggps = "ggps".getBytes();  //Not sure
                con.sendMessage(ggps);
                
                break;

            case "readio":
                byte[] readio = "readio".getBytes();  //Not sure
                con.sendMessage(readio);

                break;

            case "battery":
                
                byte[] battery = "battery".getBytes();  //Not sure
                con.sendMessage(battery);

                break;

            case "setdigout 11":

                byte[] setdigout1_1 = "".getBytes();
                con.sendMessage(setdigout1_1);
                
                break;

            case "setdigout 10":
                
                byte[] setdigout1_0 = "".getBytes();
                con.sendMessage(setdigout1_0);
                
                break;

            case "setdigout 01":
                
                byte[] setdigout0_1 = "".getBytes();
                con.sendMessage(setdigout0_1);
                
                break;

            case "setdigout 00":
                
                byte[] setdigout0_0 = "".getBytes();
                con.sendMessage(setdigout0_0);
                
                break;

            case "setdigout ??":
                
                byte[] setdigoutXX = "".getBytes();
                con.sendMessage(setdigoutXX);
                
                break;

            case "setdigout ?1":
                
                byte[] setdigoutX1 = "".getBytes();
                con.sendMessage(setdigoutX1);
               
                break;

            case "setdigout 1?":
                
                byte[] setdigout1X = "".getBytes();
                con.sendMessage(setdigout1X);
                
                break;

            case "setdigout ?0":
                
                byte[] setdigoutX0 = "".getBytes();
                con.sendMessage(setdigoutX0);

                break;

            case "setdigout 0?":
                
                byte[] setdigout0X = "".getBytes();
                con.sendMessage(setdigout0X);
                
                break;

        }

    }

}
