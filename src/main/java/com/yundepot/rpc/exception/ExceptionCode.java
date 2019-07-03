package com.yundepot.rpc.exception;

/**
 * @author zhaiyanan
 * @date 2019/4/17 06:49
 */

public enum ExceptionCode {

    SERVER_HANDLER_ERROR(500, "服务端处理异常"),
    CLIENT_INVOKE_ERROR(400, "客户端调用异常"),
    ;

    private Integer code;
    private String message;

    ExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
