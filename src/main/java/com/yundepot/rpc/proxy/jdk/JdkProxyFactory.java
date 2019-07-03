package com.yundepot.rpc.proxy.jdk;

import com.yundepot.rpc.exception.RpcException;
import com.yundepot.rpc.proxy.Invoker;
import com.yundepot.rpc.proxy.ProxyFactory;

import java.lang.reflect.Proxy;

/**
 * @author zhaiyanan
 * @date 2019/6/30 20:57
 */
public class JdkProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(Invoker invoker) throws RpcException {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{invoker.getInterface()}, new InvokerInvocationHandler(invoker));
    }
}
