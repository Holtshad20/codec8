/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8.receiver;

import java.util.Date;

/**
 *
 * @author silocom01
 */
class CodecReport {

    Date date;
    byte[] priority;
    double latitude;
    double longitude;
    byte[] satInUse;
    byte[] speed;
    byte nOfTotalIO;

    public CodecReport(Date date, byte[] priority, double latitude, double longitude, byte[] satInUse, byte[] speed, byte nOfTotalIO) {
        this.date = date;
        this.priority = priority;
        this.latitude = latitude;
        this.longitude = longitude;
        this.satInUse = satInUse;
        this.speed = speed;
        this.nOfTotalIO = spenOfTotalIOed;
    }
    
    
    void getDate(Date date) {
         return date;
    }

    void getPriority(byte[] priority) {
         return priority;
    }

    void getLatitude(double latitude) {
         return latitude;
    }

    void getLongitude(double longitude) {
         return longitude;
    }

    void getSatInUse(byte[] satInUse) {
        return satInUse;
    }

    void getSpeed(byte[] speed) {
        return speed;  
    }

    void getnOfTotalIO(byte[] nOfTotalIO) {
        return nOfTotalIO;
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

    void setnOfTotalIO(byte[] nOfTotalIO) {
    this.nOfTotalIO = nOfTotalIO;
    }
    
    public static class IOvalue{
    
        byte id;
        byte[] value;
    
    }
    
}

