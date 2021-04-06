package com.franklin.client.impl.client.abilities;


import com.franklin.client.sdk.client.ConnectionInfo;
import com.franklin.client.sdk.client.connection.IConnectionManager;

/**
 * Created by xuhao on 2017/6/30.
 */

public interface IConnectionSwitchListener {
    void onSwitchConnectionInfo(IConnectionManager manager, ConnectionInfo oldInfo, ConnectionInfo newInfo);
}
