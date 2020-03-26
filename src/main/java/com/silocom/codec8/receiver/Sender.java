/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8.receiver;

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

    public void commands(String command) {

        switch (command) {

            case "getgps":  //	Current GPS data, date and time

                byte[] getgps = GETGPS.getBytes();
                
                rec.sendMessage(getgps);

                break;

            case "getIO":  //Readout analog input,digital input and output

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

    public CodecReport getGPS() {

        byte[] getGPS = Utils.stringToHex(GETGPS);
        CodecReport report = rec.sendMessage(getGPS);

        for (int i = 0; report == null && i < retry; i++) {
            report = rec.sendMessage(getGPS);
        }
        return report;
    }

    public boolean setOutput(boolean value) {

        if (value) {
            rec.sendMessage(Utils.stringToHex(SETDIGOUT1));
        } else {
            rec.sendMessage(Utils.stringToHex(SETDIGOUT0));
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
                }
            }
        }

        return report;
    }

    private CodecReport getIO() {
        byte[] getIO = Utils.stringToHex(GETIO);
        CodecReport report = rec.sendMessage(getIO);

        for (int i = 0; report == null && i < retry; i++) {
            report = rec.sendMessage(getIO);
        }
        return report;
    }

    private CodecReport getBattery() {
        byte[] getBattery = Utils.stringToHex(BATTERY);
        CodecReport report = rec.sendMessage(getBattery);

        for (int i = 0; report == null && i < retry; i++) {
            report = rec.sendMessage(getBattery);
        }
        return report;
    }

}
