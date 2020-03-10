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

    private Date date;
    private byte[] priority;
    private double latitude;
    private double longitude;
    private byte[] satInUse;
    private byte[] speed;
    private List<IOvalue> ioValues = new ArrayList();

    public CodecReport(){
    
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

    
    
    public static class IOvalue{
    
        byte id;
        byte[] value;
        
        //hacer getter y setter de id y value
    
    }
    
}

