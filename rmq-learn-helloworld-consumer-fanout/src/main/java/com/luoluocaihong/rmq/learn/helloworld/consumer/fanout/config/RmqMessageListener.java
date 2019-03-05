package com.luoluocaihong.rmq.learn.helloworld.consumer.fanout.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xh on 2019/3/1.
 */
@Configuration
public class RmqMessageListener {

    @Bean
    public Queue helloFine() {
        return new Queue("fine");
    }
}
