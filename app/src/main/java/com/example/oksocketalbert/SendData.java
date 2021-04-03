package com.example.oksocketalbert;

import com.example.oksocketalbert.ende.EncryptionImp;
import com.xuhao.didi.core.iocore.interfaces.ISendable;

public class SendData implements ISendable {
    EncryptionImp encryptionImp = new EncryptionImp();

    @Override
    public byte[] parse() {
        return encryptionImp.encodeToByteArray();
    }
}
