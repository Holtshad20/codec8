/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8.receiver;

import com.silocom.codec8.receiver.CodecReport.IOvalue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            for (int i = 0; i < timestamp.length; i++) {
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

            int Angle = ((message[index] & 0xFF) << 8) | (message[index + 1] & 0xFF);
            index += 2;

            //calc Angle
            int satInUse = message[index] & 0xFF;
            index++;

            int speed = ((message[index] & 0xFF) << 8) | (message[index + 1] & 0xFF);
            index += 2;

            byte code = message[index];
            index++;

            byte nOfTotalIO = message[index];
            index++;

            // Falta implementar las entradas y salidas
            CodecReport report = new CodecReport();
            report.setDate(date);
            report.setPriority(priority);
            report.setLatitude(latitude);
            report.setLongitude(longitude);
            report.setAngle(Angle);
            report.setSatInUse(satInUse);
            report.setSpeed(speed);
            report.setCode(code);

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

    public static CodecReport codec12Parser_getinfo(byte[] codec12Data) {
        // 0123456789 11 13 15
        //"RTC:2020/3/11 20:30 Init:2020/3/11 17:33 UpTime:10582s PWR:SoftReset RST:0 GPS:3 SAT:7 TTFF:25 TTLF:1 NOGPS:0:0 SR:66 FG:0 FL:34 SMS:0 REC:886 MD:0 DB:0";

        return null;

    }

    public static CodecReport codec12Parser_getstatus(byte[] codec12Data) {
        return null;
        //Data Link: 1 GPRS: 1 Phone: 0 SIM: 0 OP: 73402 Signal: 5 NewSMS: 0 Roaming: 0 SMSFull: 0 LAC: 1305 Cell ID: 10171 NetType: 1 FwUpd:0

    }

    public static CodecReport codec12Parser_getgps(byte[] codec12Data) {
        //GPS:1 Sat:13 Lat:10.494710 Long:-66.831467 Alt:872 Speed:0 Dir:356 Date: 2020/3/13 Time: 13:37:15
        CodecReport answer = new CodecReport();

        String toDecode = new String(codec12Data);

        Map<String, String> values = new HashMap();

        String patternStr = "\\s[aA-zZ]{3,}[':']"; //falta patron
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(toDecode);

        while (matcher.find()) {
            int index2 = matcher.start();
            String[] val = toDecode.substring(0, index2).split(":");
            values.put(val[0], val[1]);
            toDecode = toDecode.substring(index2 + 1);
            matcher = pattern.matcher(toDecode);
        }
        int index = toDecode.indexOf(":");

        values.put(toDecode.substring(0, index), toDecode.substring(index + 1));
        String dateString = "";

        for (String key : values.keySet()) {
            switch (key) {
                case "Sat":

                    answer.setSatInUse(Integer.parseInt(values.get(key)));

                    break;

                case "Lat":

                    answer.setLatitude(Double.parseDouble(values.get(key)));

                    break;

                case "Long":

                    answer.setLongitude(Double.parseDouble(values.get(key)));

                    break;

                case "Speed":

                    answer.setSpeed(Integer.parseInt(values.get(key)));

                    break;

                case "Date":

                    dateString = values.get(key) + dateString;

                    break;
                case "Time":

                    dateString = dateString + values.get(key);

                    break;
            }

        }

        try {
            if (!dateString.isEmpty()) {

                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = format.parse(dateString);

                answer.setDate(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;

    }

    public static CodecReport codec12Parser_getio(byte[] codec12Data) {
        //DI1:0 AIN1:174 DO1:0
        CodecReport answer = new CodecReport();

        String toDecode = new String(codec12Data);

        Map<String, String> values = new HashMap();

        String patternStr = "\\s[A-Z]{2,}[0-9][':']"; //falta patron
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(toDecode);

        while (matcher.find()) {
            int index2 = matcher.start();
            String[] val = toDecode.substring(0, index2).split(":");
            values.put(val[0], val[1]);
            toDecode = toDecode.substring(index2 + 1);
            matcher = pattern.matcher(toDecode);
        }
        int index = toDecode.indexOf(":");

        values.put(toDecode.substring(0, index), toDecode.substring(index + 1));

        for (String key : values.keySet()) {
            switch (key) {
                case "DI1": {
                    int val = Integer.parseInt(values.get(key).trim());
                    byte[] value = new byte[1];
                    value[0] = (byte) (val & 0xFF);
                    answer.addIOvalue(new IOvalue(IOvalue.DIGITAL_INPUT_1, value));
                }

                break;
                case "AIN1": {
                    int val = Integer.parseInt(values.get(key).trim());
                    byte[] value = new byte[4];
                    value[0] = (byte) (val & 0xFF);
                    value[1] = (byte) ((val >> 8) & 0xFF);
                    value[2] = (byte) ((val >> 16) & 0xFF);
                    value[3] = (byte) ((val >> 24) & 0xFF);
                    answer.addIOvalue(new IOvalue(IOvalue.ANALOG_INPUT_1, value));
                }
                break;
                case "DO1": {
                    int val = Integer.parseInt(values.get(key).trim());
                    byte[] value = new byte[1];
                    value[0] = (byte) (val & 0xFF);
                    answer.addIOvalue(new IOvalue(IOvalue.DIGITAL_OUTPUT_1, value));
                }

                break;

            }
        }

        return answer;

    }

    public static CodecReport codec12Parser_battery(byte[] codec12Data) {
        //BatState: 1 FSMState: ACTIVE ChargerIC: DONE ExtV: 11859 BatV: 4115 BatI: 0
        CodecReport answer = new CodecReport();

        String toDecode = new String(codec12Data);

        Map<String, String> values = new HashMap();

        String patternStr = "\\s[aA-zZ]{3,}[':']"; //falta patron
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(toDecode);

        while (matcher.find()) {
            int index2 = matcher.start();
            String[] val = toDecode.substring(0, index2).split(":");
            values.put(val[0], val[1]);
            toDecode = toDecode.substring(index2 + 1);
            matcher = pattern.matcher(toDecode);
        }
        int index = toDecode.indexOf(":");

        values.put(toDecode.substring(0, index), toDecode.substring(index + 1));

        for (String key : values.keySet()) {
            switch (key) {
                case "ExtV": {
                    int val = Integer.parseInt(values.get(key).trim());
                    byte[] value = new byte[4];
                    value[0] = (byte) (val & 0xFF);
                    value[1] = (byte) ((val >> 8) & 0xFF);
                    value[2] = (byte) ((val >> 16) & 0xFF);
                    value[3] = (byte) ((val >> 24) & 0xFF);
                    answer.addIOvalue(new IOvalue(IOvalue.EXTERNAL_VOLTAGE, value));
                }
                break;

                case "BatV": {
                    int val = Integer.parseInt(values.get(key).trim());
                    byte[] value = new byte[4];
                    value[0] = (byte) (val & 0xFF);
                    value[1] = (byte) ((val >> 8) & 0xFF);
                    value[2] = (byte) ((val >> 16) & 0xFF);
                    value[3] = (byte) ((val >> 24) & 0xFF);
                    answer.addIOvalue(new IOvalue(IOvalue.BATTERY_VOLTAGE, value));
                }
                break;

            }
        }

        return answer;
    }

}
