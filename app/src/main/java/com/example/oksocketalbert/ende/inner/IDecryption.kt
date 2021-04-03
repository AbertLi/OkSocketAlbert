package com.example.oksocketalbert.ende.inner

interface IDecryption : IBaseEnDe {
    fun decode(byteArray: ByteArray): String                //解密
    fun decodeToByteArray(byteArray: ByteArray): ByteArray  //解密

    fun getCmdType(): Int
    fun getType(): Int
    fun getTimeStamp(): Int
    fun getSnno(): Int
    fun getLen(): Int
    fun getCrc(): String
    fun getDataArea(): String
}