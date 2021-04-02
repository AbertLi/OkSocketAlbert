package com.example.oksocketalbert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xuhao.didi.core.iocore.interfaces.IPulseSendable;
import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    private IConnectionManager manager;
    private String data = " %s,%s,%s,%s"; //"071 135790246811222,2018-7-9 18:06:20,98,-72,bs[460:0:28730:20736:34]"
    private String deviceImei;
    private int counts = 0; //发送数据次数
    private int reconnCounts = 0; //重连次数
    private ConnectionInfo connInfo;
    private Timer dataTimer;
    private boolean isSendData = false;
    private String nmeaLogPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void sendData() {
        if (manager != null) {
            isSendData = true;
            int dataLen = format.length() + 3;
            String len;
            if (dataLen < 10) {
                len = "00" + dataLen;
            } else if (dataLen < 100) {
                len = "0" + dataLen;
            } else {
                len = "" + dataLen;
            }
            String data = len + format;
            manager.send(new SendData(data));
        } else {
            showTipsDialog("Please Connect To Server!");
        }
    }

    public void onViewClicked2() {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                PhoneUtil.getBsSignal(MainActivity.this);
            }
        }, 100, 1000);

        if (manager != null) {
            String time = etTime.getText().toString().trim();
            if (TextUtils.isEmpty(time)) {
                showTipsDialog("IP和Port不能为空");
                return;
            }
            int timeInt = Integer.parseInt(time);
            if (timeInt <= 0) {
                showTipsDialog("请输入大于0的整数");
                return;
            }
            PFUtils.setPrefInt(MainActivity.this, "sim_time", timeInt);
            if (!isSendData) {
                dataTimer = new Timer();
                dataTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        sendData();
                    }
                }, 100, timeInt);
            } else {
                showTipsDialog("data sending!");
            }
        } else {
            showTipsDialog("Please Connect To Server!");
        }
    }

    public void onViewClicked() {
        int port = Integer.parseInt(trimPort);
        //连接参数设置(IP,端口号),这也是一个连接的唯一标识,不同连接,该参数中的两个值至少有其一不一样
        connInfo = new ConnectionInfo(trimIp, port);
        //调用OkSocket,开启这次连接的通道,拿到通道Manager
        manager = OkSocket.open(connInfo);
        //注册Socket行为监听器,SocketActionAdapter是回调的Simple类,其他回调方法请参阅类文档
        manager.registerReceiver(mSocketAdapter);
        manager.connect(); //调用通道进行连接
    }

    SocketActionAdapter mSocketAdapter = new SocketActionAdapter() {
        @Override
        public void onSocketIOThreadStart( String action) {
            super.onSocketIOThreadStart( action);
            Log.e(TAG, "onSocketIOThreadStart:" + action);
        }

        @Override
        public void onSocketIOThreadShutdown(String action, Exception e) {
            super.onSocketIOThreadShutdown( action, e);
            Log.e(TAG, "onSocketIOThreadShutdown:" + action + " Error:" + e.getMessage());
        }

        @Override
        public void onSocketDisconnection( ConnectionInfo info, String action, Exception e) {
            super.onSocketDisconnection( info, action, e);
            Log.e(TAG, "onSocketDisconnection:" + action + " Error:" + e.getMessage());
        }

        @Override
        public void onSocketConnectionSuccess( ConnectionInfo info, String action) {
            super.onSocketConnectionSuccess( info, action);
            Log.e(TAG, "onSocketConnectionSuccess:" + action);
            Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
            super.onSocketConnectionFailed(info, action, e);
            Log.e(TAG, "onSocketConnectionFailed:" + action + " Error:" + e.getMessage());
            reconnCounts++;
        }

        @Override
        public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
            super.onSocketReadResponse( info, action, data);
            Log.e(TAG, "onSocketReadResponse:" + action);
        }

        @Override
        public void onSocketWriteResponse( ConnectionInfo info, String action, ISendable data) {
            super.onSocketWriteResponse( info, action, data);
            Log.e(TAG, "onSocketWriteResponse:数据发送成功" + data.toString());
            counts++;
        }

        @Override
        public void onPulseSend(  ConnectionInfo info, IPulseSendable data) {
            super.onPulseSend( info, data);
            Log.e(TAG, "onPulseSend:" + data.toString());
        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null) manager.disconnect();
        if (dataTimer != null) dataTimer.cancel();
    }

}