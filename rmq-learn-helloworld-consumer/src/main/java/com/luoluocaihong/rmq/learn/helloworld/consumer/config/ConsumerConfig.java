package com.luoluocaihong.rmq.learn.helloworld.consumer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xh on 2019/3/1.
 */
@Configuration
public class ConsumerConfig {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

}
