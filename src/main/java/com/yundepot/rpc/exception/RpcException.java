package com.yundepot.rpc.exception;

/**
 * @author zhaiyanan
 * @date 2019/6/28 20:18
 */
public class RpcException extends RuntimeException{
    private static final long serialVersionUID = -8007741694331746305L;

    public RpcException() {
        super();
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }
}
