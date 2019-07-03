package com.yundepot.rpc.proxy;

import com.yundepot.rpc.RpcRequest;
import com.yundepot.rpc.RpcResponse;
import com.yundepot.rpc.exception.RpcException;

/**
 * @author zhaiyanan
 * @date 2019/6/28 20:20
 */
public interface Invoker<T> {

    /**
     * 获取代理的接口
     * @return
     */
    Class<T> getInterface();

    /**
     * 执行调用
     * @param request
     * @return
     * @throws RpcException
     */
    RpcResponse invoke(RpcRequest request) throws RpcException;
}
