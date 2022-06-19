package com.yundepot.rpc;

import com.yundepot.adam.AdamClient;
import com.yundepot.adam.config.HeaderOption;
import com.yundepot.adam.protocol.command.ResponseCommand;
import com.yundepot.rpc.config.ClassManager;
import com.yundepot.rpc.proxy.DefaultInvoker;
import com.yundepot.rpc.proxy.ProxyFactory;
import com.yundepot.rpc.proxy.jdk.JdkProxyFactory;
import com.yundepot.rpc.util.ResponseResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaiyanan
 * @date 2019/6/9 16:34
 */
public class RpcClient extends AdamClient {

    private String address;
    private Byte serializer;
    private ProxyFactory proxyFactory;
    public RpcClient(String address) {
        this.address = address;
        this.proxyFactory = new JdkProxyFactory();
    }

    public RpcClient(String host, int port) {
        this(host + ":" + port);
    }

    public Object invokeSync(final Object request, final int timeoutMillis) throws Exception{
        Map<String, String> header = new HashMap();
        if (serializer != null) {
            header.put(HeaderOption.SERIALIZER.getKey(), serializer.toString());
        }

        ResponseCommand response = (ResponseCommand) this.invokeSync(address, request, header, timeoutMillis);
        return ResponseResolver.resolveResponseObject(response, address);
    }

    public <T> T create(String serviceName, Class<T> interfaceClass) {
        ClassManager.addCLass(serviceName, interfaceClass);
        return proxyFactory.getProxy(new DefaultInvoker(interfaceClass, this));
    }

    public <T> T create(Class<T> interfaceClass) {
        ClassManager.addCLass(interfaceClass.getName(), interfaceClass);
        return proxyFactory.getProxy(new DefaultInvoker(interfaceClass, this));
    }

    public void setSerializer(Byte serializer) {
        this.serializer = serializer;
    }
}
