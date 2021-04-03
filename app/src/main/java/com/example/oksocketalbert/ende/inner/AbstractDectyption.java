package com.example.oksocketalbert.ende.inner;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.oksocketalbert.ende.DeviceCodesumUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;

import one.example.com.myapplication3.ui.socket.ende.ProtocolConstant;

/**
 * 解密
 */
public abstract class AbstractDectyption implements IDecryption {
    private String TAG = "AbstractDectyption";
    private String dectyption = "";
    private boolean isDe;

    @Override
    public String decode(byte[] ba) {
        byte[] result = decodeToByteArray(ba);
        dectyption = new String(result, Charset.defaultCharset());
        detyptionSuc(dectyption);
        return dectyption;
    }

    @Override
    public byte[] decodeToByteArray(byte[] ba) {
        byte[] result = new byte[0];
        try {
            result = decodeByteArray(ba);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "JSONException = " + e.toString());
            detyptionFail("decodeToByteArray() " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception = " + e.toString());
            detyptionFail("decodeToByteArray() " + e.toString());
        }
        isDe = true;
        return result;
    }

    private byte[] decodeByteArray(byte[] ba) throws Exception {
        byte[] byteArray = new byte[ba.length];
        int commaIndex = 0;//第几个逗号
        int quotationMarksIndex = 0;//第几个引号
        boolean isEnCode = false;//是否开始加密
        boolean isPrefixFlag = false;
        byte[] byteArrayPrefix;
        for (int i = 0; i < ba.length; i++) {
            byte b = ba[i];
            if (b == (byte) ',') {
                commaIndex++;
            }
            if (commaIndex >= 2) {
                if (isEnCode) {
                    b -= DeviceCodesumUtil.getInstance().getCacheSum();
                    b -= i;
                    byteArray[i] = b;
                } else {
                    byteArray[i] = b;
                }
                isEnCode = true;
            } else {
                byteArray[i] = b;
            }
            if (b == (byte) '"') {
                quotationMarksIndex++;
            }
            if (quotationMarksIndex == 6 && !isPrefixFlag) {
                isPrefixFlag = true;
                byteArrayPrefix = new byte[i + 2];
                savePrefix(byteArray, byteArrayPrefix);
            }
        }
        return byteArray;
    }

    private void savePrefix(byte[] arrayAll, byte[] byteArrayPrefix) throws Exception {
        int byteArrayPrefixLen = byteArrayPrefix.length;
        int LenReduceOne = byteArrayPrefixLen - 1;
        for (int i = 0; i < byteArrayPrefixLen; i++) {
            if (i == LenReduceOne) {
                byteArrayPrefix[i] = '}';
            } else {
                byteArrayPrefix[i] = arrayAll[i];
            }
        }
        String jsonPrefix = new String(byteArrayPrefix);
        JSONObject obj;

        obj = new JSONObject(jsonPrefix);
        int camType = obj.getInt(ProtocolConstant.cmdType);
        String equipNo = obj.getString(ProtocolConstant.equipNo);
        saveCmdTpeAndEquipNo(camType,equipNo);
        if (camType == 9999) {
            throw new Exception("camType is 9999,The lower computer returns abnormal data.");
        }
    }

    private void checkDectyption() throws Exception {
        if (!isDe) {
            throw new Exception("The decryption method has not been called yet, please call the decryption method first.");
        }
    }

    private Object getValue(String key) {
        JSONObject objJson = null;
        Object obj = null;
        try {
            objJson = new JSONObject(dectyption);
            obj = objJson.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }


    public int getCmdType() {
        try {
            checkDectyption();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception = " + e.toString());
            detyptionFail("getCmdType() " + e.toString());
        }
        return (int) getValue(ProtocolConstant.cmdType);
    }

    public int getType() {
        try {
            checkDectyption();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception = " + e.toString());
            detyptionFail(" getType() " + e.toString());
        }
        return (int) getValue(ProtocolConstant.type);
    }

    public int getTimeStamp() {
        try {
            checkDectyption();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception = " + e.toString());
            detyptionFail("getTimeStamp() " + e.toString());
        }
        return (int) getValue(ProtocolConstant.timeStamp);
    }

    public int getSnno() {
        try {
            checkDectyption();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception = " + e.toString());
            detyptionFail("getSnno() " + e.toString());
        }
        return (int) getValue(ProtocolConstant.snno);
    }

    public int getLen() {
        try {
            checkDectyption();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception = " + e.toString());
            detyptionFail("getLen() " + e.toString());
        }
        return (int) getValue(ProtocolConstant.len);
    }

    public String getCrc() {
        try {
            checkDectyption();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception = " + e.toString());
            detyptionFail("getCrc() " + e.toString());
        }
        return (String) getValue(ProtocolConstant.crc);
    }

    public String getDataArea() {
        try {
            checkDectyption();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception = " + e.toString());
            detyptionFail("getDataArea() " + e.toString());
        }
        return (String) getValue(ProtocolConstant.dataArea);
    }

    public abstract void detyptionSuc(@NonNull String jsonString);

    public abstract void detyptionFail(@NonNull String error);

    public abstract void saveEquipNo(@NonNull String equipNo);

    private void saveCmdTpeAndEquipNo(int cmdType, String equipNo) {
        saveEquipNo(equipNo);//和getEquipNoSUM方法的顺序不能交换
        ParamCache.getInstance().setCmdType(cmdType);
        DeviceCodesumUtil.getInstance().getEquipNoSUM(getEquipNo());
    }
}
