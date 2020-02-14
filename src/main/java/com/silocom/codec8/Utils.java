/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8;

/**
 *
 * @author silocom01
 */
public class Utils {

    public static String hexToString(byte[] message) {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < message.length; i++) {

            if ((message[i] & 0xff) < 0x10) {
                answer.append("0");
            }

            answer.append(Integer.toHexString(message[i] & 0xFF));

        }
        return answer.toString();
    }

    public static int byteArrayToInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }

}
