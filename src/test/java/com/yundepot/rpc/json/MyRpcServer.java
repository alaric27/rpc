package com.yundepot.rpc.json;


import com.yundepot.oaa.serialize.SerializerManager;
import com.yundepot.rpc.RpcServer;
import com.yundepot.rpc.quickstart.HelloService;
import com.yundepot.rpc.quickstart.HelloServiceImpl;
import com.yundepot.rpc.serialize.JsonSerializer;

/**
 * @author zhaiyanan
 * @date 2019/6/9 16:46
 */
public class MyRpcServer {

    public static void main(String[] args) {
        SerializerManager.addSerializer((byte) 3, new JsonSerializer());
        RpcServer rpcServer = new RpcServer(8099);
        HelloService helloService = new HelloServiceImpl();
        rpcServer.addService(HelloService.class.getName(), helloService);
        rpcServer.start();

    }
}
