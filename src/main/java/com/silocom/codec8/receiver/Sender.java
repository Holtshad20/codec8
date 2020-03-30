/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8.receiver;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author silocom01
 */
public class Sender {

    Receiver rec;
    private int retry;

    private final static String GETINFO = "000000000000000F0C010500000007676574696E666F0100004312";   //Todos los mensajes de comando estan creados ya
    private final static String GETVER = "000000000000000E0C010500000006676574766572010000A4C2";
    private final static String GETSTATUS = "00000000000000110C0105000000096765747374617475730100008CD7";
    private final static String GETGPS = "000000000000000E0C010500000006676574677073010000CCD7";
    private final static String GETIO = "000000000000000D0C010500000005676574696f01000000CB";
    private final static String BATTERY = "000000000000000F0C01050000000762617474657279010000FE37";
    private final static String SETDIGOUT1 = "00000000000000130C01050000000B7365746469676f7574203101000087A2";
    private final static String SETDIGOUT0 = "00000000000000130C01050000000B7365746469676f7574203001000017A3";

    public Sender(Receiver rec, int retry) {
        this.rec = rec;
        this.retry = retry;
    }

    public CodecReport getGPS() {

        byte[] getGPS = Utils.stringToHex(GETGPS);
        CodecReport report = rec.sendMessage(getGPS,1);

        for (int i = 0; report == null && i < retry; i++) {
            report = rec.sendMessage(getGPS,1);
        }
        return report;
    }

    public boolean setOutput(boolean value) {

        if (value) {
            rec.sendMessage(Utils.stringToHex(SETDIGOUT1),4);
        } else {
            rec.sendMessage(Utils.stringToHex(SETDIGOUT0),4);
        }

        return true;
    }

    public CodecReport getReport() {

        CodecReport answer = new CodecReport();

        CodecReport report = getGPS();

        if (report != null) {
            answer.setLatitude(report.getLatitude());
            answer.setLongitude(report.getLongitude());
            answer.setSatInUse(report.getSatInUse());
            answer.setSpeed(report.getSpeed());
            answer.setDate(report.getDate());

            report = getIO();
            if (report != null) {

                for (CodecReport.IOvalue value : report.getIoValues()) {
                    answer.addIOvalue(value);
                }

                report = getBattery();
                if (report != null) {
                    for (CodecReport.IOvalue value : report.getIoValues()) {
                        answer.addIOvalue(value);
                    }
                } else {
                    System.out.println("get battery null");
                }
            } else {
                System.out.println("get io null");
            }
        } else {
            System.out.println("get gps null");
        }

        return answer;
    }

    private CodecReport getIO() {
        byte[] getIO = Utils.stringToHex(GETIO);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        CodecReport report = rec.sendMessage(getIO,2);

        for (int i = 0; report == null && i < retry; i++) {
            report = rec.sendMessage(getIO,2);
        }
        return report;
    }

    private CodecReport getBattery() {
        byte[] getBattery = Utils.stringToHex(BATTERY);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        CodecReport report = rec.sendMessage(getBattery,3);

        for (int i = 0; report == null && i < retry; i++) {
            report = rec.sendMessage(getBattery,3);
        }
        return report;
    }

}
