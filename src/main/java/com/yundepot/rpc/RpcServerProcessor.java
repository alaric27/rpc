package com.yundepot.rpc;

import com.yundepot.adam.processor.AsyncContext;
import com.yundepot.adam.processor.AsyncProcessor;
import com.yundepot.oaa.invoke.InvokeContext;
import com.yundepot.rpc.exception.ExceptionCode;

import java.lang.reflect.Method;

/**
 * @author zhaiyanan
 * @date 2019/6/9 15:55
 */
public class RpcServerProcessor extends AsyncProcessor<RpcRequest> {

    private RpcServer rpcServer;

    public RpcServerProcessor(RpcServer rpcServer) {
        this.rpcServer = rpcServer;
    }

    @Override
    public void handleRequest(InvokeContext remotingContext, AsyncContext asyncCtx, RpcRequest request) {
        RpcResponse response = new RpcResponse();
        try {
            Object result = handle(request);
            response.setResult(result);
        } catch (Throwable t) {
            response.setCode(ExceptionCode.SERVER_HANDLER_ERROR.getCode());
            response.setMessage(ExceptionCode.SERVER_HANDLER_ERROR.getMessage());
            response.setResult(t);
        }
        asyncCtx.sendResponse(response);
    }

    @Override
    public String interest() {
        return RpcRequest.class.getName();
    }

    private Object handle(RpcRequest request) throws Throwable {
        String className   = request.getClassName();
        Object serviceBean = rpcServer.getHandlerMap().get(className);

        Class<?>   serviceClass   = serviceBean.getClass();
        String     methodName     = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[]   parameters     = request.getParameters();

        Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);
    }
}
