package com.example.oksocketalbert.ende;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.CRC32;

import one.example.com.myapplication3.ui.socket.ende.ProtocolConstant;
import one.example.com.myapplication3.ui.socket.ende.inner.NumOfCallUtil;

import com.example.oksocketalbert.ende.inner.AbstractEncryption;
import com.example.oksocketalbert.ende.inner.ParamCache;

public class EncryptionImp extends AbstractEncryption {
    private String TAG = "EncryptionImp";

    @Override
    public int getCmdType() {
        return ProtocolConstant.CMDTYPE1101;
    }

    @Override
    public String getEquipNo() {
        return ParamCache.getInstance().getEquipNo();
    }

    @Override
    public long getTimeStamp() {
        return System.currentTimeMillis();
    }

    public int getLen() {
        return getDataArea().toString().length();
    }

    public String getCrc() {
        CRC32 crc32 = new CRC32();
        crc32.update(getDataArea().toString().getBytes());
        return Long.toHexString(crc32.getValue());
    }

    public JSONObject getDataArea() {
        JSONObject areaObj = new JSONObject();
        try {
            areaObj.put("opt", 0);
            areaObj.put("minProtocolVer", "V1.00.00");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return areaObj;
    }

    @Override
    public int getCallNum() {
        return NumOfCallUtil.getInstance().getCallNumAdd();
    }
}
