package com.example.oksocketalbert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.oksocketalbert.ende.DectyptionImp;
import com.xuhao.didi.core.iocore.interfaces.IPulseSendable;
import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    private IConnectionManager manager;
    private ConnectionInfo connInfo;
    private String ip;

    DectyptionImp dectyptionImp = new DectyptionImp();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onViewClicked(null);
    }


    public void sendData(View v) {
        if (manager != null) {
            manager.send(new SendData());
        } else {
        }
    }


    public OkSocketOptions getOptions(){
        OkSocketOptions okSocketOptions = new OkSocketOptions
                .Builder(OkSocketOptions.getDefault())
                .setMaxReadDataByte(2000)//读取时候的字符byte数
                .setReaderProtocol(new NormalReaderProtocol())
                .build();
        return okSocketOptions;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ip = WifiUtil.INSTANCE.getWIFILYIP(this.getApplication());
    }

    public void onViewClicked(View v) {
        ip = WifiUtil.INSTANCE.getWIFILYIP(this.getApplication());
        //连接参数设置(IP,端口号),这也是一个连接的唯一标识,不同连接,该参数中的两个值至少有其一不一样
        connInfo = new ConnectionInfo(ip, 9000);
        //调用OkSocket,开启这次连接的通道,拿到通道Manager
        manager = OkSocket.open(connInfo).option(getOptions());
        //注册Socket行为监听器,SocketActionAdapter是回调的Simple类,其他回调方法请参阅类文档
        manager.registerReceiver(mSocketAdapter);
        manager.connect(); //调用通道进行连接
    }

    SocketActionAdapter mSocketAdapter = new SocketActionAdapter() {
        @Override
        public void onSocketIOThreadStart(String action) {
            super.onSocketIOThreadStart(action);
            Log.e(TAG, "onSocketIOThreadStart:" + action);
        }

        @Override
        public void onSocketIOThreadShutdown(String action, Exception e) {
            super.onSocketIOThreadShutdown(action, e);
            Log.e(TAG, "onSocketIOThreadShutdown:" + action + " Error:" + e.getMessage());
        }

        @Override
        public void onSocketDisconnection(ConnectionInfo info, String action, Exception e) {
            super.onSocketDisconnection(info, action, e);
            Log.e(TAG, "onSocketDisconnection:" + action + " Error:" + e.getMessage());
        }

        @Override
        public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
            super.onSocketConnectionSuccess(info, action);
            Log.e(TAG, "onSocketConnectionSuccess:" + action+" isMainThread  = "+(Looper.myLooper() ==getMainLooper()));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
            super.onSocketConnectionFailed(info, action, e);
            Log.e(TAG, "onSocketConnectionFailed:" + action + " Error:" + e.getMessage()+" isMainThread  = "+(Looper.myLooper() ==getMainLooper()));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
            super.onSocketReadResponse(info, action, data);
            Log.e(TAG, "onSocketReadResponse:" + action +" data len = "+data.getBodyBytes().length);
            Log.e(TAG, "onSocketReadResponse:  解密的结果 = "+new String(dectyptionImp.decodeToByteArray(data.getBodyBytes())));
        }

        @Override
        public void onSocketWriteResponse(ConnectionInfo info, String action, ISendable data) {
            super.onSocketWriteResponse(info, action, data);
            Log.e(TAG, "onSocketWriteResponse:数据发送成功:" + data.toString());
        }

        @Override
        public void onPulseSend(ConnectionInfo info, IPulseSendable data) {
            super.onPulseSend(info, data);
            Log.e(TAG, "onPulseSend:" + data.toString());
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null) manager.disconnect();
    }

}