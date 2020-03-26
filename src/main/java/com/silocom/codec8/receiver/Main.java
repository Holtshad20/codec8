/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8.receiver;

import com.silocom.m2m.layer.physical.PhysicalLayer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author silocom01
 */
public class Main {

    public static void main(String[] args) {
        int timeout = 3000;
        byte[] IMEIExpected = new byte[]{0x33, 0x35, 0x32, 0x30, 0x39, 0x33, 0x30, 0x38, 0x37, 0x38, 0x36, 0x38, 0x32, 0x38, 0x36};

        com.silocom.m2m.layer.physical.Connection con = PhysicalLayer.addConnection(3, 17500, "192.168.210.1");
        Receiver rec = new Receiver(con, IMEIExpected, timeout);

        con.addListener(rec);

        while (!con.isConnected()) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
        }
        Sender sender = new Sender(rec, 3);
       while(true){
       try{
           System.out.println("Lectura " + sender.getReport().toString());
       }catch(Exception e){
       e.printStackTrace();
       }
       
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
       } 
        
    
    }
}
