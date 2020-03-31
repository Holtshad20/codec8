/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8.receiver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author silocom01
 */
public class CodecReport {

    private Date date;
    private byte[] priority;
    private double latitude;
    private double longitude;
    private int satInUse;
    private int speed;
    private int angle;
    private int code;
    private int do1;
    private int ain1;
    private int di1;

    private List<IOvalue> ioValues = new ArrayList();

    public CodecReport() {

    }

    public CodecReport(Date date, byte[] priority, double latitude, double longitude, int satInUse, int speed,
            int angle, int code, int do1, int ain1, int di1) {

        this.date = date;
        this.priority = priority;
        this.latitude = latitude;
        this.longitude = longitude;
        this.satInUse = satInUse;
        this.speed = speed;
        this.angle = angle;
        this.code = code;
        this.do1 = do1;
        this.ain1 = ain1;
        this.di1 = di1;

    }

    public int getDo1() {
        return do1;
    }

    public int getAin1() {
        return ain1;
    }

    public int getDi1() {
        return di1;
    }

    public int getCode() {
        return code;
    }

    public int getAngle() {
        return angle;
    }

    public Date getDate() {
        return date;
    }

    public byte[] getPriority() {
        return priority;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getSatInUse() {
        return satInUse;
    }

    public int getSpeed() {   
        return speed;
    }

    public void setDo1(int do1) {
        this.do1 = do1;
    }

    public void setAin1(int ain1) {
        this.ain1 = ain1;
    }

    public void setDi1(int di1) {
        this.di1 = di1;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPriority(byte[] priority) {
        this.priority = priority;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setSatInUse(int satInUse) {
        this.satInUse = satInUse;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void addIOvalue(IOvalue value) {

        this.ioValues.add(value);

    }

    public List<IOvalue> getIoValues() {
        return ioValues;  
    }

    public static class IOvalue {

        public static final byte IGNITION = (byte) 239;
        public static final byte EXTERNAL_VOLTAGE = (byte) 66;
        public static final byte BATTERY_VOLTAGE = (byte) 67;
        public static final byte DIGITAL_INPUT_1 = (byte) 1;
        public static final byte ANALOG_INPUT_1 = (byte) 9;
        public static final byte DIGITAL_OUTPUT_1 = (byte) 179;

        public void setId(byte id) {
            this.id = id;
        }

        public void setValue(byte[] value) {
            this.value = value;
        }

        public byte getId() {
            return id;
        }

        public byte[] getValue() {
            return value;
        }

        byte id;
        byte[] value;

        public IOvalue(byte id, byte[] value) {
            this.id = id;
            this.value = value;
        }
    }

}
