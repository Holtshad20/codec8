/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8.receiver;

import com.silocom.codec8.receiver.CodecReport.IOvalue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author silocom01
 */
public class Parser {

    public static List<CodecReport> parserCodec8(byte[] message) {
        List<CodecReport> answer = new ArrayList();
        int index = 0;
        while (index < (message.length - 25)) {

            byte[] timestamp = new byte[8];
            for (int i = 0; (i < timestamp.length); i++) {
                timestamp[i] = message[index];
                index++;
            }
            Date date = Utils.timeCalc(timestamp);

            byte[] priority = new byte[1];
            priority[0] = message[index];
            index++;

            byte[] lon = new byte[4];
            for (int i = 0; (i < lon.length); i++) {
                lon[i] = message[index];
                index++;
            }

            double longitude = Utils.longitude(lon);

            byte[] lat = new byte[4];
            for (int i = 0; (i < lat.length); i++) {
                lat[i] = message[index];
                index++;
            }
            double latitude = Utils.latitude(lat);

            byte[] altitude = new byte[2];
            for (int i = 0; i < altitude.length; i++) {
                altitude[i] = message[index];
                index++;
            }

            byte[] satInUse = new byte[1];
            satInUse[0] = message[index];
            index++;

            byte[] speed = new byte[2];
            for (int i = 0; i < speed.length; i++) {
                speed[i] = message[index];
                index++;
            }

            byte[] nOfTotalIO = new byte[1];
            nOfTotalIO[0] = message[index];
            index++;

            // Falta implementar las entradas y salidas
            CodecReport report = new CodecReport(); //Tienes que crear la clase
            report.setDate(date);
            report.setPriority(priority);
            report.setLatitude(latitude);
            report.setLongitude(longitude);
            report.setSatInUse(satInUse);
            report.setSpeed(speed);

            int noByte1 = message[index] & 0xFF;
            index++;

            for (int i = 0; i < noByte1; i++) {
                byte idIO = message[index];
                index++;
                byte[] valueIO = new byte[1];
                valueIO[0] = message[index];
                index++;
                report.addIOvalue(new IOvalue(idIO, valueIO));
            }

            int noByte2 = message[index] & 0xFF;
            index++;

            for (int i = 0; i < noByte2; i++) {
                byte idIO = message[index];
                index++;
                byte[] valueIO = new byte[2];
                for (int j = 0; j < valueIO.length; j++) {
                    valueIO[j] = message[index];
                    index++;
                }
                report.addIOvalue(new IOvalue(idIO, valueIO));
            }

            int noByte4 = message[index] & 0xFF;
            index++;

            for (int i = 0; i < noByte4; i++) {
                byte idIO = message[index];
                index++;
                byte[] valueIO = new byte[4];
                for (int j = 0; j < valueIO.length; j++) {
                    valueIO[j] = message[index];
                    index++;
                }
                report.addIOvalue(new IOvalue(idIO, valueIO));
            }

            int noByte8 = message[index] & 0xFF;
            index++;

            for (int i = 0; i < noByte8; i++) {
                byte idIO = message[index];
                index++;
                byte[] valueIO = new byte[8];
                for (int j = 0; j < valueIO.length; j++) {
                    valueIO[j] = message[index];
                    index++;
                }
                report.addIOvalue(new IOvalue(idIO, valueIO));
            }

            answer.add(report);
            System.out.println(report.toString());
        }
        return answer;

    }

    public static byte[] codec12Parser_getinfo(byte[] codec12Data) {
        // 0123456789 11 13 15
        //"RTC:2020/3/11 20:30 Init:2020/3/11 17:33 UpTime:10582s PWR:SoftReset RST:0 GPS:3 SAT:7 TTFF:25 TTLF:1 NOGPS:0:0 SR:66 FG:0 FL:34 SMS:0 REC:886 MD:0 DB:0";
        String toDecode = new String(codec12Data);
        String rtcMatch = new String();
        String satMatch = new String();

        Pattern rtcPattern = Pattern.compile("RTC:(?:(?!RTC|Init).)*");
        Matcher rtcMatcher = rtcPattern.matcher(toDecode);

        Pattern satPattern = Pattern.compile("SAT:(?:(?!SAT|TTFF).)*");
        Matcher satMatcher = satPattern.matcher(toDecode);

        while (rtcMatcher.find() && satMatcher.find()) {
            rtcMatch = rtcMatcher.group();
            satMatch = satMatcher.group();
        }

        String rtcValue = rtcMatch.replaceAll("[/: ]+", " ");
        String satValue = satMatch.replaceAll("[/: ]+", " ");

        /*byte[] byteValue = rtcValue.getBytes();

        byte[] valueTosend = new byte[byteValue.length];
        System.arraycopy(byteValue, 3, valueTosend, 0, valueTosend.length - 3);  //fecha y hora  RTC sin espacios, "RTC"*/
        return null;

    }

    public static void codec12Parser_getstatus(byte[] codec12Data) {
        //Data Link: 1 GPRS: 1 Phone: 0 SIM: 0 OP: 73402 Signal: 5 NewSMS: 0 Roaming: 0 SMSFull: 0 LAC: 1305 Cell ID: 10171 NetType: 1 FwUpd:0

        String toDecode = new String(codec12Data);
        String signalMatch = new String();
        Pattern signalPattern = Pattern.compile("Signal:(?:(?!Signal|NewSMS).)*");  //Pattern
        Matcher signalMatcher = signalPattern.matcher(toDecode);

        while (signalMatcher.find()) {
            signalMatch = signalMatcher.group();
        }

        String signalValue = signalMatch.replaceAll("[/: ]+", " ");

    }

    public static void codec12Parser_getgps(byte[] codec12Data) {
        //GPS:1 Sat:13 Lat:10.494710 Long:-66.831467 Alt:872 Speed:0 Dir:356 Date: 2020/3/13 Time: 13:37:15

        String toDecode = new String(codec12Data);
        String match = new String();
        Pattern pattern = Pattern.compile("RTC:(?:(?!RTC|Init).)*");  //Pattern
        Matcher matcher = pattern.matcher(toDecode);

        while (matcher.find()) {
            match = matcher.group();
        }

        String replacedValue = match.replaceAll("[/: ]+", "");

    }

    public static void codec12Parser_getio(byte[] codec12Data) {
        //DI1:0 AIN1:174 DO1:0
    }

    public static void codec12Parser_battery(byte[] codec12Data) {
        //BatState: 1 FSMState: ACTIVE ChargerIC: DONE ExtV: 11859 BatV: 4115 BatI: 0
    }

}
