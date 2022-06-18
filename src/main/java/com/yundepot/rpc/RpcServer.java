package com.yundepot.rpc;


import com.yundepot.adam.AdamServer;
import com.yundepot.adam.processor.ProcessorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaiyanan
 * @date 2019/6/9 16:06
 */
public class RpcServer extends AdamServer {

    /**
     * 存储接口和实现类的对应关系
     */
    private Map<String, Object> handlerMap = new HashMap<>();

    public RpcServer(int port) {
        super(port);
    }

    public RpcServer(String ip, int port) {
        super(ip, port);
    }

    @Override
    public void start() {
        RpcServerProcessor rpcServerProcessor = new RpcServerProcessor(this);
        ProcessorManager.registerProcessor(rpcServerProcessor);
        super.start();
    }


    public RpcServer addService(String interfaceName, Object serviceBean) {
        if (!handlerMap.containsKey(interfaceName)) {
            handlerMap.put(interfaceName, serviceBean);
        }
        return this;
    }

    public Map<String, Object> getHandlerMap() {
        return handlerMap;
    }
}
