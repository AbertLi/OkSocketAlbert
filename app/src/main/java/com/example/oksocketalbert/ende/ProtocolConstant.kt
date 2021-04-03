package one.example.com.myapplication3.ui.socket.ende

/**
 * 本地通信协议常量
 */
object ProtocolConstant {
    //业务功能码说明
    const val CMDTYPE1101 = 1101    //c2s	请求设备基本信息命令
    const val CMDTYPE1102 = 1102    //s2c	基本信息应答命令
    const val CMDTYPE1103 = 1103    //c2s	通知IBG进入编址状态
    const val CMDTYPE1104 = 1104    //s2c	响应进入编址状态
    const val CMDTYPE1105 = 1105    //c2s	查询IBG编址结果
    const val CMDTYPE1106 = 1106    //s2c	返回IBG编址结果
    const val CMDTYPE1109 = 1109    //c2s	请求周围可用Wi-Fi热点命令
    const val CMDTYPE1110 = 1110    //s2c	下发周围可用Wi-Fi热点信息
    const val CMDTYPE1111 = 1111    //c2s	上传目标Wi-Fi热点的账号与密码
    const val CMDTYPE1112 = 1112    //s2c	下发目标Wi-Fi参数的设置结果
    const val CMDTYPE1113 = 1113    //c2s	查询IBG的网络连接状态
    const val CMDTYPE1114 = 1114    //s2c	回复IBG网络连接状态结果
    const val CMDTYPE1115 = 1115    //c2s	设置INFINITE储能系统绑定信息
    const val CMDTYPE1116 = 1116    //s2c	回复绑定信息的设置结果
    const val CMDTYPE1201 = 1201    //c2s	查询设置地理位置、时区信息
    const val CMDTYPE1202 = 1202    //s2c	回复地理位置与时区信息查询设置结果
    const val CMDTYPE1203 = 1203    //c2s	2030.5 URI查询设置命令
    const val CMDTYPE1204 = 1204    //s2c	2030.5信息查询设置结果回复
    const val CMDTYPE1301 = 1301    //c2s	功率、状态流图相关数据请求命令
    const val CMDTYPE1302 = 1302    //s2c	功率、状态流图相关数据回复
    const val CMDTYPE1303 = 1303    //c2s	实时功率曲线数据召唤命令
    const val CMDTYPE1304 = 1304    //s2c	实时功率曲线数据下发命令
    const val CMDTYPE1305 = 1305    //c2s	电量使用占比相关数据召唤命令
    const val CMDTYPE1306 = 1306    //s2c	电量使用占比相关数据下发命令
    const val CMDTYPE1401 = 1401    //c2s	负载开关状态读取/设置命令
    const val CMDTYPE1402 = 1402    //s2c	负载开关状态读取/设置命令回复
    const val CMDTYPE1403 = 1403    //c2s	本地模式查询设置命令
    const val CMDTYPE1404 = 1404    //s2c	本地模式查询设置命令回复
    const val CMDTYPE1405 = 1405    //c2s	SOC相关查询设置命令
    const val CMDTYPE1406 = 1406    //s2c	SOC相关查询设置命令回复
    const val CMDTYPE1407 = 1407    //c2s	TOU本地查询设置命令
    const val CMDTYPE1408 = 1408    //s2c	TOU本地查询设置命令回复
    const val CMDTYPE1501 = 1501    //c2s	设备升级触发命令
    const val CMDTYPE1502 = 1502    //s2c	设备升级触发命令回复
    const val CMDTYPE1503 = 1503    //s2c	设备升级结果下发
    const val CMDTYPE1601 = 1601    //c2s	电动车充电查询设置命令
    const val CMDTYPE1602 = 1602    //s2c	电动车充电查询设置命令回复
    const val CMDTYPE1701 = 1701    //c2s	系统参数查询设置命令
    const val CMDTYPE1702 = 1702    //s2c	系统参数查询设置命令回复
    const val CMDTYPE1703 = 1703    //c2s	PE参数查询和设置命令
    const val CMDTYPE1704 = 1704    //s2c	PE参数查询和设置命令回复
    const val CMDTYPE1705 = 1705    //c2s	BMS参数查询和设置命令
    const val CMDTYPE1706 = 1706    //s2c	BMS参数查询和设置命令回复
    const val CMDTYPE1707 = 1707    //c2s	系统设备信息查询和设置命令
    const val CMDTYPE1708 = 1708    //s2c	系统设备信息查询和设置命令回复
    const val CMDTYPE1709 = 1709    //c2s	故障信息查询和清除命令
    const val CMDTYPE1710 = 1710    //s2c	故障信息查询和清除命令回复


    //应用层协议帧数据结构
    const val cmdType = "cmdType"        //具体业务类型标识
    const val equipNo = "equipNo"        //设备编号IBG_SN，不超过32字节
    const val type = "type"              //0：设备网关IBG
    const val timeStamp = "timeStamp"    //时间戳定义
    const val snno = "snno"              //发送报文从1开始计数，若达到9999则再次至1开始编号。
    const val len = "len"                //数据域的总长度(json字节数)
    const val crc = "crc"                //CRC32	ASDU报文的总CRC-32校验值，不超过10字节
    const val dataArea = "dataArea"      //具体业务报文


    val keyArray = arrayOf(cmdType, equipNo, type, timeStamp, snno, len, crc, dataArea)
}