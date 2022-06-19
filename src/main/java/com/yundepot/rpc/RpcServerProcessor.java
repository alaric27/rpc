package com.yundepot.rpc;

import com.yundepot.adam.processor.AsyncContext;
import com.yundepot.adam.processor.AsyncProcessor;
import com.yundepot.oaa.invoke.InvokeContext;
import com.yundepot.rpc.config.RpcHeadOption;
import com.yundepot.rpc.exception.ExceptionCode;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
        String errorFlag = "false";
        try {
            Object result = handle(request);
            response.setResult(result);
        } catch (Throwable t) {
            response.setCode(ExceptionCode.SERVER_HANDLER_ERROR.getCode());
            response.setMessage(ExceptionCode.SERVER_HANDLER_ERROR.getMessage());
            response.setResult(t);
            errorFlag = "true";
        }
        Map<String, String> header = new HashMap<>();
        header.put(RpcHeadOption.RPC_SERVICE_NAME, request.getServiceName());
        header.put(RpcHeadOption.RPC_METHOD_NAME, request.getMethodName());
        header.put(RpcHeadOption.RPC_RESPONSE_ERROR, errorFlag);
        asyncCtx.sendResponse(response, header);
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
