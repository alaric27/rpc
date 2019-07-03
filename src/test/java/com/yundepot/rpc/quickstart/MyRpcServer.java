package com.yundepot.rpc.quickstart;


import com.yundepot.rpc.RpcServer;

/**
 * @author zhaiyanan
 * @date 2019/6/9 16:46
 */
public class MyRpcServer {

    public static void main(String[] args) {
        RpcServer rpcServer = new RpcServer(8099);
        HelloService helloService = new HelloServiceImpl();
        rpcServer.addService(HelloService.class.getName(), helloService);
        rpcServer.start();

    }
}
