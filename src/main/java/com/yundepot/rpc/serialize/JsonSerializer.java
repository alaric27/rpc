package com.yundepot.rpc.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yundepot.oaa.exception.DeserializationException;
import com.yundepot.oaa.exception.SerializationException;
import com.yundepot.oaa.serialize.Serializer;
import com.yundepot.rpc.RpcRequest;
import com.yundepot.rpc.RpcResponse;
import com.yundepot.rpc.config.ClassManager;
import com.yundepot.rpc.config.RpcHeadOption;
import com.yundepot.rpc.config.ServiceManager;
import com.yundepot.rpc.util.MethodUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * json序列化器
 * 只支持0个或1个参数的序列化
 * @author zhaiyanan
 * @date 2022/6/19  15:26
 */
@Slf4j
public class JsonSerializer implements Serializer {

    public static final byte serializerCode = 3;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object object, Map<String, String> context) throws SerializationException {
        try {
            if (object instanceof RpcRequest) {
                RpcRequest request = (RpcRequest) object;
                context.put(RpcHeadOption.RPC_SERVICE_NAME, request.getServiceName());
                context.put(RpcHeadOption.RPC_METHOD_NAME, request.getMethodName());

                Object[] methodArgs = request.getMethodArgs();
                if (methodArgs == null || methodArgs.length == 0) {
                    return null;
                }

                if (methodArgs.length != 1) {
                    throw new SerializationException("JsonSerializer most support one param ");
                }

                return objectMapper.writeValueAsBytes(request.getMethodArgs()[0]);
            }
            return objectMapper.writeValueAsBytes(object);
        } catch (Exception e) {
            log.error("JsonSerializer serialize error ", e);
            throw new SerializationException();
        }
    }

    @Override
    public Object deserialize(byte[] data, Map<String, String> context) throws DeserializationException {
        try {
            String serviceName = context.get(RpcHeadOption.RPC_SERVICE_NAME);
            String methodName = context.get(RpcHeadOption.RPC_METHOD_NAME);
            String responseError = context.get(RpcHeadOption.RPC_RESPONSE_ERROR);

            if (responseError != null) {
                Class clazz = ClassManager.getClass(serviceName);
                Method method = MethodUtil.getMethod(clazz, methodName);
                Class<?>[] parameterTypes = method.getParameterTypes();
                RpcResponse response = objectMapper.readValue(data, RpcResponse.class);

                if (parameterTypes != null && parameterTypes.length == 1 && data != null) {
                    byte[] bytes = objectMapper.writeValueAsBytes(response.getResult());
                    response.setResult(objectMapper.readValue(bytes, parameterTypes[0]));
                }
                return response;
            } else {
                Object serviceBean = ServiceManager.getService(serviceName);
                Method method = MethodUtil.getMethod(serviceBean.getClass(), methodName);
                Class<?>[] parameterTypes = method.getParameterTypes();
                RpcRequest request = new RpcRequest();
                request.setMethodName(methodName);
                request.setServiceName(serviceName);

                if (parameterTypes != null && parameterTypes.length == 1 && data != null) {
                    Object obj = objectMapper.readValue(data, parameterTypes[0]);
                    request.setMethodArgs(new Object[]{obj});
                }
                return request;
            }
        } catch (Exception e) {
            log.error("JsonSerializer deserialize error ", e);
            throw new DeserializationException();
        }
    }
}
