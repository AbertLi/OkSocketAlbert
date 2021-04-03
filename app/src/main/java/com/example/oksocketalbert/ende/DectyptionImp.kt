package com.example.oksocketalbert.ende

import android.util.Log
import com.example.oksocketalbert.ende.inner.AbstractDectyption
import com.example.oksocketalbert.ende.inner.ParamCache


class DectyptionImp : AbstractDectyption() {
    override fun saveEquipNo(equipNo: String) {
        ParamCache.getInstance().equipNo = equipNo
    }

    override fun getEquipNo(): String {
        return ParamCache.getInstance().equipNo
    }

    override fun detyptionSuc(jsonString: String) {

    }

    override fun detyptionFail(error: String) {
        Log.i("DectyptionImp",error)
    }

}