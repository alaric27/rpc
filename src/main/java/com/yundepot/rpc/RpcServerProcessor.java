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
        String serviceName = request.getServiceName();
        Object serviceBean = rpcServer.getService(serviceName);

        Class serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Object[] methodArgs = request.getMethodArgs();

        // todo 删除该逻辑，直接获取对应method然后调用
        Class<?>[] parameterTypes = getParameterTypes(methodArgs);
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, methodArgs);
    }

    private Class[] getParameterTypes(Object[] methodArgs) {
        if (methodArgs == null || methodArgs.length == 0) {
            return null;
        }
        Class[] classList = new Class[methodArgs.length];

        for (int i = 0; i < methodArgs.length; i++) {
            classList[i] = methodArgs[i].getClass();
        }
        return classList;
    }
}
