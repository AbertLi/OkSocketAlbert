package com.xuhao.didi.core.iocore.interfaces;

import java.nio.ByteOrder;

public interface IIOCoreOptions {

    int getMaxReadDataByte();

    ByteOrder getWriteByteOrder();

    int getWritePackageBytes();

    boolean isDebug();

}
