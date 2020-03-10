/*
 * @Author Fernando Gonzalez.
 */
package com.silocom.codec8.receiver;

import java.util.List;

/**
 *
 * @author silocom01
 */
public interface CodecListener {
    public void onData(List<CodecReport> reports);
}
