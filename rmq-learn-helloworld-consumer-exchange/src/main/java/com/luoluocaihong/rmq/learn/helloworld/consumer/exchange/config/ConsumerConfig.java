package com.luoluocaihong.rmq.learn.helloworld.consumer.exchange.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xh on 2019/3/1.
 */
@Configuration
public class ConsumerConfig {

    @Bean
    public Queue helloFine() {
        return new Queue("fine");
    }

    @Bean
    public Queue okQueue() {
        return new Queue("ok");
    }
}
