package com.yundepot.rpc.quickstart;

import java.io.Serializable;

/**
 * @author zhaiyanan
 * @date 2019/6/9 16:57
 */
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
