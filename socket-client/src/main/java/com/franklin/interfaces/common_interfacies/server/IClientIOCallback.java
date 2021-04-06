package com.franklin.interfaces.common_interfacies.server;


import com.franklin.core.iocore.interfaces.ISendable;
import com.franklin.core.pojo.OriginalData;

public interface IClientIOCallback {

    void onClientRead(OriginalData originalData, IClient client, IClientPool<IClient, String> clientPool);

    void onClientWrite(ISendable sendable, IClient client, IClientPool<IClient, String> clientPool);

}
