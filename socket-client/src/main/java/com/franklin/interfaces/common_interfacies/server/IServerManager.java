package com.franklin.interfaces.common_interfacies.server;

import com.franklin.core.iocore.interfaces.IIOCoreOptions;

public interface IServerManager<E extends IIOCoreOptions> extends IServerShutdown {

    void listen();

    void listen(E options);

    boolean isLive();

    IClientPool<String, IClient> getClientPool();
}
