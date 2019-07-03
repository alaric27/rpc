package com.yundepot.rpc;

import com.yundepot.adam.AdamClient;
import com.yundepot.adam.protocol.command.ResponseCommand;
import com.yundepot.adam.util.ResponseResolver;
import com.yundepot.rpc.proxy.DefaultInvoker;
import com.yundepot.rpc.proxy.ProxyFactory;
import com.yundepot.rpc.proxy.jdk.JdkProxyFactory;

/**
 * @author zhaiyanan
 * @date 2019/6/9 16:34
 */
public class RpcClient extends AdamClient {

    private String addr;
    private ProxyFactory proxyFactory;
    public RpcClient(String addr) {
        this.addr = addr;
        this.proxyFactory = new JdkProxyFactory();
    }

    public RpcClient(String host, int port) {
        this(host + ":" + port);
    }

    public Object invokeSync(final Object request, final int timeoutMillis) throws Exception{
        ResponseCommand response = (ResponseCommand) this.invokeSync(addr, request, null, timeoutMillis);
        return ResponseResolver.resolveResponseObject(response, addr);
    }

    public <T> T create(Class<T> interfaceClass) {
        return proxyFactory.getProxy(new DefaultInvoker(interfaceClass, this));
    }
}
