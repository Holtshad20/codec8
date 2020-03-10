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
    private byte[] satInUse;
    private byte[] speed;
    private List<IOvalue> ioValues = new ArrayList();

    public CodecReport() {

    }

    public CodecReport(Date date, byte[] priority, double latitude, double longitude, byte[] satInUse, byte[] speed) {
        this.date = date;
        this.priority = priority;
        this.latitude = latitude;
        this.longitude = longitude;
        this.satInUse = satInUse;
        this.speed = speed;

    }

    Date getDate(Date date) {
        return date;
    }

    byte[] getPriority() {
        return priority;
    }

    double getLatitude() {
        return latitude;
    }

    double getLongitude() {
        return longitude;
    }

    byte[] getSatInUse() {
        return satInUse;
    }

    byte[] getSpeed() {    //hacer public los getters y setter
        return speed;
    }

    void setDate(Date date) {
        this.date = date;
    }

    void setPriority(byte[] priority) {
        this.priority = priority;
    }

    void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    void setSatInUse(byte[] satInUse) {
        this.satInUse = satInUse;
    }

    void setSpeed(byte[] speed) {
        this.speed = speed;
    }

    void addIOvalue(IOvalue value) {

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
