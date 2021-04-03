package com.example.oksocketalbert.ende.inner;

import android.util.Log;

import com.example.oksocketalbert.ende.DeviceCodesumUtil;

import org.json.JSONException;
import org.json.JSONObject;
import one.example.com.myapplication3.ui.socket.ende.ProtocolConstant;

/**
 * 加密
 */
public abstract class AbstractEncryption implements IEncryption {
    private String TAG = "AbstractEncryption";

    /**
     * 加密
     *
     * @param stringJson
     * @param equipNoSum
     * @return
     */
    private byte[] encodeByteArray(String stringJson, int equipNoSum) {
        byte[] result = new byte[stringJson.length()];
        int commaIndex = 0;
        boolean isEnCode = false;//是否开始加密
        for (int i = 0; i < result.length; i++) {
            char ch = stringJson.charAt(i);
            if (ch == ',') {
                commaIndex++;
            }
            if (commaIndex >= 2) {
                if (isEnCode) {
                    byte sum = (byte) ((byte) equipNoSum + (byte) i + (byte) ch);
                    result[i] = sum;
                } else {
                    result[i] = (byte) ch;
                }
                isEnCode = true;
            } else {
                result[i] = (byte) ch;
            }
        }
        return result;
    }

    @Override
    public byte[] encodeToByteArray() {
        String jsonString = null;
        try {
            jsonString = getJsonString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return encodeByteArray(jsonString, DeviceCodesumUtil.getInstance().getEquipNoSUM(getEquipNo()));
    }

    public String getJsonString() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(ProtocolConstant.cmdType, getCmdType());
        object.put(ProtocolConstant.equipNo, getEquipNo());
        object.put(ProtocolConstant.type, ParamCache.getInstance().getType());
        object.put(ProtocolConstant.timeStamp, getTimeStamp());
        object.put(ProtocolConstant.snno, getCallNum());
        object.put(ProtocolConstant.len, getLen());
        object.put(ProtocolConstant.crc, getCrc());
        object.put(ProtocolConstant.dataArea, getDataArea());
        Log.i(TAG,"getJaonString = " + object.toString());
        return object.toString();
    }

    abstract public int getCmdType();

    abstract public long getTimeStamp();

    abstract public int getLen();

    abstract public String getCrc();

    abstract public JSONObject getDataArea();

    abstract public int getCallNum();
}
