package com.yundepot.rpc.util;

import com.yundepot.adam.common.ResponseStatus;
import com.yundepot.adam.protocol.command.ResponseCommand;
import com.yundepot.oaa.util.StringUtils;
import com.yundepot.rpc.exception.RpcException;

/**
 * @author zhaiyanan
 * @date 2019/5/31 09:45
 */
public class ResponseResolver {

    public static Object resolveResponseObject(ResponseCommand response, String address) throws Exception {
        preProcess(response, address);
        return response.getBody();
    }

    private static void preProcess(ResponseCommand response, String address) throws Exception {
        String msg;
        if (response == null) {
            msg = String.format("invocation timeout[response null]! the address is %s", address);
            throw new RpcException(msg);
        }

        if (ResponseStatus.SUCCESS.value() == response.getStatus()) {
            return;
        }
        msg = String.format(" invocation failed! the address is %s", address);
        throw new RpcException((int) response.getStatus(), detailErrorMsg(msg, response), toThrowable(response));
    }

    private static Throwable toThrowable(ResponseCommand response) {
        Object ex = response.getBody();
        if (ex != null && ex instanceof Throwable) {
            return (Throwable) ex;
        }
        return null;
    }

    private static String detailErrorMsg(String clientErrMsg, ResponseCommand response) {
        if (StringUtils.isNotBlank(response.getErrorMsg())) {
            return String.format("%s, ServerErrorMsg:%s", clientErrMsg, response.getErrorMsg());
        } else {
            return String.format("%s, ServerErrorMsg:null", clientErrMsg);
        }
    }
}
