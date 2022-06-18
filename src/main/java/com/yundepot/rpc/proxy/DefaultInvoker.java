package com.yundepot.rpc.proxy;

import com.yundepot.rpc.RpcClient;
import com.yundepot.rpc.RpcRequest;
import com.yundepot.rpc.RpcResponse;
import com.yundepot.rpc.exception.ExceptionCode;
import com.yundepot.rpc.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhaiyanan
 * @date 2019/6/29 07:44
 */
@Slf4j
public class DefaultInvoker<T> implements Invoker{

    /**
     * 代理类型
     */
    private final Class<T> type;

    /**
     * Rpc客户端
     */
    private RpcClient rpcClient;

    public DefaultInvoker(Class<T> type, RpcClient rpcClient) {
        this.type = type;
        this.rpcClient = rpcClient;
    }

    @Override
    public Class getInterface() {
        return type;
    }

    @Override
    public RpcResponse invoke(RpcRequest request) throws RpcException {
        RpcResponse response = null;
        try {
            response = (RpcResponse) rpcClient.invokeSync(request, 30 * 1000);
        } catch (Throwable e) {
            log.error("rpc invoke exception", e);
            response = buildRpcResponse(e);
        }
        return response;
    }

    private RpcResponse buildRpcResponse(Throwable throwable) {
        RpcResponse response = new RpcResponse();
        response.setCode(ExceptionCode.CLIENT_INVOKE_ERROR.getCode());
        response.setMessage(ExceptionCode.CLIENT_INVOKE_ERROR.getMessage());
        response.setResult(throwable);

        if (throwable instanceof RpcException) {
            RpcException exception = (RpcException) throwable;
            response.setCode(exception.getCode());
            response.setMessage(exception.getMessage());
        }
        return response;
    }
}
