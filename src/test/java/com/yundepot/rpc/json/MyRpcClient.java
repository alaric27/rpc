package com.yundepot.rpc.json;


import com.yundepot.oaa.serialize.SerializerManager;
import com.yundepot.rpc.RpcClient;
import com.yundepot.rpc.quickstart.HelloService;
import com.yundepot.rpc.quickstart.Student;
import com.yundepot.rpc.serialize.JsonSerializer;

/**
 * @author zhaiyanan
 * @date 2019/6/9 16:47
 */
public class MyRpcClient {
    public static void main(String[] args) {
        SerializerManager.addSerializer((byte) 3, new JsonSerializer());

        RpcClient rpcClient = new RpcClient("127.0.0.1:8099");
        rpcClient.setSerializer(JsonSerializer.serializerCode);
        rpcClient.start();
        HelloService helloService = rpcClient.create(HelloService.class);

/*
        System.out.println(helloService.sayHello("alaric"));

        Student student = new Student();
        student.setId(10);
        student.setName("alaric");
        System.out.println(helloService.learn(student));
*/

//        helloService.touch();

        System.out.println(helloService.add(3, 5));

    }
}
