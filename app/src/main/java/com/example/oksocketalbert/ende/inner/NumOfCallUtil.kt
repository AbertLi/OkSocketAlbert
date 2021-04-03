package one.example.com.myapplication3.ui.socket.ende.inner


class NumOfCallUtil {
    private constructor() {
    }
    //发送报文从1开始计数，若达到9999则再次至1开始编号。
    private var callNum = 0

    private object SingletonHolder {
        var single = NumOfCallUtil()
    }

    companion object {
        @JvmStatic
        fun getInstance(): NumOfCallUtil {
            return SingletonHolder.single
        }
    }

    fun getCallNum(): Int {
        return callNum
    }

    fun getCallNumAdd(): Int {
        callNum++
        if (callNum >= 9999) {//若达到9999则再次至1开始编号。
            callNum = 1
        }
        return callNum
    }
}