/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

import com.silocom.m2m.layer.physical.Connection;

/**
 *
 * @author silocom01
 */
public class Sender {

    Connection con;

    public static void request() {
        String getinfo = "000000000000000F0C010500000007676574696E666F0100004312";
 
    }

    public Sender(Connection con) {
        this.con = con;

    }
}
