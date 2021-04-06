package com.franklin.interfaces.common_interfacies.server;

import com.franklin.core.protocol.IReaderProtocol;
import com.franklin.interfaces.common_interfacies.client.IDisConnectable;
import com.franklin.interfaces.common_interfacies.client.ISender;

import java.io.Serializable;

public interface IClient extends IDisConnectable, ISender<IClient>, Serializable {

    String getHostIp();

    String getHostName();

    String getUniqueTag();

    void setReaderProtocol(IReaderProtocol protocol);

    void addIOCallback(IClientIOCallback clientIOCallback);

    void removeIOCallback(IClientIOCallback clientIOCallback);

    void removeAllIOCallback();

}
