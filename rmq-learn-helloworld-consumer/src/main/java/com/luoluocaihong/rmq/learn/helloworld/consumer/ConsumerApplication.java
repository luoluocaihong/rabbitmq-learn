package com.luoluocaihong.rmq.learn.helloworld.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by xh on 2019/3/1.
 */
@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
