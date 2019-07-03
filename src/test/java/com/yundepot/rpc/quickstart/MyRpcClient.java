package com.yundepot.rpc.quickstart;


import com.yundepot.rpc.RpcClient;

/**
 * @author zhaiyanan
 * @date 2019/6/9 16:47
 */
public class MyRpcClient {
    public static void main(String[] args) {
        RpcClient rpcClient = new RpcClient("127.0.0.1:8099");
        rpcClient.start();
        HelloService helloService = rpcClient.create(HelloService.class);
        System.out.println(helloService.sayHello("alaric"));

        Student student = new Student();
        student.setId(10);
        student.setName("alaric");
        System.out.println(helloService.learn(student));


    }
}
