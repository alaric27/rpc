package com.yundepot.rpc;

import java.io.Serializable;

/**
 * @author zhaiyanan
 * @date 2019/6/9 15:54
 */
public class RpcResponse implements Serializable {

    public static final int SUCCESS_CODE = 0;
    public static final String SUCCESS_MESSAGE = "成功";

    /**
     * 响应码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 业务返回或者业务异常
     */
    private Object result;

    public RpcResponse() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public RpcResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean checkSuccess() {
        return SUCCESS_CODE == this.code;
    }
}
