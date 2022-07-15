# rpc
该项目的定位是:提供一个简单的RPC调用，方便造其他轮子。
使用adam协议通信，只支持直连方式。目前功能比较简单，还在完善中。

# quick start
1、定义接口及其实现类
```java
public interface HelloService {
    String sayHello(String name);
    Student learn(Student student);
}
```
```java
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello : " + name;
    }

    @Override
    public Student learn(Student student) {
        student.setScore("id:" + student.getId() + " name:" + student.getName() + " :100");
        return student;
    }
}
```

```java
public class Student implements Serializable {
    private long id;
    private String name;
    private String score;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
```

2、启动服务端
```java
public class MyRpcServer {
    public static void main(String[] args) {
        RpcServer rpcServer = new RpcServer(8099);
        HelloService helloService = new HelloServiceImpl();
        rpcServer.addService(HelloService.class.getName(), helloService);
        rpcServer.start();

    }
}
```

3、客户端调用
```java
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
```
