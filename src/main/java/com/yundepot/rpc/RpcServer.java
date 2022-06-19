package com.yundepot.rpc;


import com.yundepot.adam.AdamServer;
import com.yundepot.adam.processor.ProcessorManager;
import com.yundepot.rpc.config.ServiceManager;

/**
 * @author zhaiyanan
 * @date 2019/6/9 16:06
 */
public class RpcServer extends AdamServer {
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


    public RpcServer addService(String serviceName, Object serviceBean) {
        ServiceManager.addService(serviceName, serviceBean);
        return this;
    }

    public Object getService(String service) {
        return ServiceManager.getService(service);
    }
}
