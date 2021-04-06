package com.franklin.core.protocol;

import java.nio.ByteOrder;

/**
 *
 * Albert 这个版本把协议这块去掉了。
 */
public interface IReaderProtocol {
    /**
     * 返回包头长度,该长度将告知框架,解析服务端数据时,哪一部分数据属于包头.
     *
     * @return 包头的长度, 该长度应该是一个固定的包头长度
     */
    int getHeaderLength();

    /**
     * 交由开发者进行解析,开发者应该从参数 {@link #getBodyLength(byte[], ByteOrder)}}header中解析出该次通讯服务端返回的包体长度<br>
     *
     * @param header    根据getHeaderLength()方法获得的包头原始数据.开发者应该从此header种解析出包体长度数据.
     * @param byteOrder 当前包头字节数组种,包头数据的字节序类型.
     * @return 开发者应该从此header种解析出包体长度数据.此值不应该是一个字面量定值, 应该是解析出来的一个数值.
     */
    int getBodyLength(byte[] header, ByteOrder byteOrder);
}
