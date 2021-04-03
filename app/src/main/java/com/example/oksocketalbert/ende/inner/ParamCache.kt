package com.example.oksocketalbert.ende.inner


class ParamCache {
    var cmdType = 1101           //具体业务类型标识
    var equipNo = "00000000"   //设备编号IBG_SN，不超过32字节
    var type = 0              //0：设备网关IBG
//    var timeStamp = ""         //时间戳定义
//    var snno = ""              //发送报文从1开始计数，若达到9999则再次至1开始编号。
//    var len = ""                //数据域的总长度(json字节数)
//    var crc = ""                //CRC32	ASDU报文的总CRC-32校验值，不超过10字节
//    var dataArea = ""           //具体业务报文


    //{"cmdType":1101,"equipNo":"00000000","type":0,"timeStamp":1606966212,"snno":1,"len":84,"crc":"A474C16F","dataArea":{"opt":0,"minProtocolVer":"V1.00.00"}}

    private constructor() {
    }

    private object SingletonHolder {
        var single = ParamCache()
    }

    companion object {
        @JvmStatic
        fun getInstance(): ParamCache {
            return SingletonHolder.single
        }
    }


}