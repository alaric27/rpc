package com.yundepot.rpc.proxy.jdk;

import com.yundepot.rpc.RpcRequest;
import com.yundepot.rpc.RpcResponse;
import com.yundepot.rpc.exception.RpcException;
import com.yundepot.rpc.proxy.Invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zhaiyanan
 * @date 2019/6/30 20:58
 */
public class InvokerInvocationHandler implements InvocationHandler {
    private final Invoker invoker;

    public InvokerInvocationHandler(Invoker handler) {
        this.invoker = handler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(invoker, args);
        }
        if ("toString".equals(methodName) && parameterTypes.length == 0) {
            return invoker.toString();
        }
        if ("hashCode".equals(methodName) && parameterTypes.length == 0) {
            return invoker.hashCode();
        }
        if ("equals".equals(methodName) && parameterTypes.length == 1) {
            return invoker.equals(args[0]);
        }

        RpcResponse response = invoker.invoke(new RpcRequest(method, args));
        if (!response.checkSuccess()) {
            if (response.getResult() != null && response.getResult() instanceof Throwable) {
                throw (Throwable) response.getResult();
            } else {
                throw new RpcException("response code: " + response.getCode() + "response message: " + response.getMessage());
            }
        }
        return response.getResult();
    }
}
