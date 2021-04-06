package com.franklin.core.iocore;

import com.franklin.core.exceptions.ReadException;
import com.franklin.core.iocore.interfaces.IOAction;
import com.franklin.core.pojo.OriginalData;

import java.io.IOException;

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
