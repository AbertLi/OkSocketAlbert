package com.franklin.core.pojo;

import java.io.Serializable;

/**
 * 原始数据结构体
 * Created by xuhao on 2017/5/16.
 */
public final class OriginalData implements Serializable {
    /**
     * 原始数据包体字节数组
     */
    private byte[] mBodyBytes;

    public byte[] getBodyBytes() {
        return mBodyBytes;
    }

    public void setBodyBytes(byte[] bodyBytes) {
        mBodyBytes = bodyBytes;
    }
}
