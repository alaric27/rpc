package com.yundepot.rpc.exception;

/**
 * @author zhaiyanan
 * @date 2019/6/28 20:18
 */
public class RpcException extends RuntimeException{
    private static final long serialVersionUID = -8007741694331746305L;
    private Integer code;

    public RpcException() {
        super();
    }

    public RpcException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public RpcException(Integer code, String message) {
        this(message);
        this.code = code;
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
