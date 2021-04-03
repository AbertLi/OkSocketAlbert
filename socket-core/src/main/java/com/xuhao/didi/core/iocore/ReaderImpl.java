package com.xuhao.didi.core.iocore;

import com.xuhao.didi.core.exceptions.ReadException;
import com.xuhao.didi.core.iocore.interfaces.IOAction;
import com.xuhao.didi.core.pojo.OriginalData;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Albert
 *
 */

public class ReaderImpl extends AbsReader {
    @Override
    public void read() throws RuntimeException {
        OriginalData originalData = new OriginalData();
        try {
            originalData.setBodyBytes(readBodyFromChannel());
            mStateSender.sendBroadcast(IOAction.ACTION_READ_COMPLETE, originalData);
        } catch (Exception e) {
            ReadException readException = new ReadException(e);
            throw readException;
        }
    }

    private byte[] readBodyFromChannel() throws IOException {
        byte[] maxByteArray = new byte[mOkOptions.getMaxReadDataByte()];
        int isLen = mInputStream.read(maxByteArray);
        byte[] targetByteArray = new byte[isLen];
        saveTargetByteArray(targetByteArray, maxByteArray);
        return targetByteArray;
    }

    private void saveTargetByteArray(byte[] byteISArray, byte[] maxByteArray) {
        for (int i = 0; i < byteISArray.length; i++) {
            byteISArray[i] = maxByteArray[i];
        }
    }

}
