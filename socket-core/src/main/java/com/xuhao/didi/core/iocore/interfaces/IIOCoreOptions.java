package com.xuhao.didi.core.iocore.interfaces;

import com.xuhao.didi.core.protocol.IReaderProtocol;

import java.nio.ByteOrder;

public interface IIOCoreOptions {

    ByteOrder getReadByteOrder();

    int getMaxReadDataByte();

    IReaderProtocol getReaderProtocol();

    ByteOrder getWriteByteOrder();

    int getWritePackageBytes();

    boolean isDebug();

}
