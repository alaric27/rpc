package com.yundepot.rpc;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author zhaiyanan
 * @date 2019/6/9 15:52
 */
public class RpcRequest implements Serializable {
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public RpcRequest() {

    }

    public RpcRequest(Method method, Object[] args) {
        this.className = method.getDeclaringClass().getName();
        this.methodName = method.getName();
        this.parameterTypes = method.getParameterTypes();
        this.parameters = args;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
