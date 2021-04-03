package com.example.oksocketalbert.ende.inner

import com.example.oksocketalbert.ende.inner.IBaseEnDe

interface IEncryption : IBaseEnDe {
    fun encodeToByteArray(): ByteArray                      //加密
}