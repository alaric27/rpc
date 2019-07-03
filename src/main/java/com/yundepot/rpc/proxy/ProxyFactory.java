package com.yundepot.rpc.proxy;

import com.yundepot.rpc.exception.RpcException;

/**
 * @author zhaiyanan
 * @date 2019/6/28 20:16
 */
public interface ProxyFactory {

    /**
     * 创建单例
     * @param invoker
     * @param <T>
     * @return
     * @throws RpcException
     */
    <T> T getProxy(Invoker invoker) throws RpcException;
}
