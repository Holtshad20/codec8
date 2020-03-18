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
class CodecReport {

    private Date date;
    private byte[] priority;
    private double latitude;
    private double longitude;
    private int satInUse;
    private byte[] speed;
    private List<IOvalue> ioValues = new ArrayList();

    public CodecReport() {

    }

    public CodecReport(Date date, byte[] priority, double latitude, double longitude, int satInUse, byte[] speed) {
        this.date = date;
        this.priority = priority;
        this.latitude = latitude;
        this.longitude = longitude;
        this.satInUse = satInUse;
        this.speed = speed;

    }

    public Date getDate(Date date) {
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

    public byte[] getSpeed() {    //hacer public los getters y setter
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

    public void setSpeed(byte[] speed) {
        this.speed = speed;
    }

    public void addIOvalue(IOvalue value) {

        this.ioValues.add(value);

    }

    public static class IOvalue {

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
