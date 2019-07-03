package com.yundepot.rpc.quickstart;

/**
 * @author zhaiyanan
 * @date 2019/6/9 16:45
 */
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
