package com.luoluocaihong.rmq.learn.helloworld.producer.exchange.dto;

import java.io.Serializable;

/**
 * Created by xh on 2019/3/6.
 */
public class Person implements Serializable {
    private Long id;

    private String name;

    private Long age;

    private String job;


    public Person(Long id, String name, Long age, String job) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
