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
    private List<IOvalue> ioValues = new ArrayList();

    public CodecReport() {

    }

    public CodecReport(Date date, byte[] priority, double latitude, double longitude, int satInUse, int speed) {
        this.date = date;
        this.priority = priority;
        this.latitude = latitude;
        this.longitude = longitude;
        this.satInUse = satInUse;
        this.speed = speed;

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

    public int getSpeed() {    //hacer public los getters y setter
        return speed;
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
        return ioValues;  //
    }

    @Override
    public String toString() {
        return "CodecReport{" + "date=" + date + ", priority=" + priority + ", latitude=" + latitude + ", longitude=" + longitude + ", satInUse=" + satInUse + ", speed=" + speed + ", ioValues=" + ioValues + '}';
    }
   
    public static class IOvalue {
        public static final byte IGNITION = (byte) 239;
        public static final byte EXTERNAL_VOLTAGE = (byte) 66;
        public static final byte BATTERY_VOLTAGE = (byte) 67;
        
        
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
