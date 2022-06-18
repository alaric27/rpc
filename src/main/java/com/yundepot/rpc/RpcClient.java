package com.yundepot.rpc;

import com.yundepot.adam.AdamClient;
import com.yundepot.adam.protocol.command.ResponseCommand;
import com.yundepot.rpc.proxy.DefaultInvoker;
import com.yundepot.rpc.proxy.ProxyFactory;
import com.yundepot.rpc.proxy.jdk.JdkProxyFactory;
import com.yundepot.rpc.util.ResponseResolver;

/**
 * @author zhaiyanan
 * @date 2019/6/9 16:34
 */
public class RpcClient extends AdamClient {

    private String address;
    private ProxyFactory proxyFactory;
    public RpcClient(String address) {
        this.address = address;
        this.proxyFactory = new JdkProxyFactory();
    }

    public RpcClient(String host, int port) {
        this(host + ":" + port);
    }

    public Object invokeSync(final Object request, final int timeoutMillis) throws Exception{
        ResponseCommand response = (ResponseCommand) this.invokeSync(address, request, null, timeoutMillis);
        return ResponseResolver.resolveResponseObject(response, address);
    }

    public <T> T create(Class<T> interfaceClass) {
        return proxyFactory.getProxy(new DefaultInvoker(interfaceClass, this));
    }
}
