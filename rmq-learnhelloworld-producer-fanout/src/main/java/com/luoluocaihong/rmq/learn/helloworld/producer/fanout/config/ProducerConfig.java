package com.luoluocaihong.rmq.learn.helloworld.producer.fanout.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * Created by xh on 2019/3/5.
 */
@Configuration
public class ProducerConfig {
    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

    @Bean
    public Queue testQueue() {
        return new Queue("test");
    }

    //配置广播路由器
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("luoluocaihongExchange");
    }

    @Bean
    Binding bindingExchangeHello(@Qualifier("helloQueue") Queue helloQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(helloQueue).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeTest(@Qualifier("testQueue") Queue testQueue, FanoutExchange fanoutExchange) {
        //等价于 return BindingBuilder.bind(testQueue).to(fanoutExchange);  因为exchange类型为fanout时忽略routingKey
        return new Binding(testQueue.getName(), Binding.DestinationType.QUEUE, fanoutExchange.getName(), "AA", new HashMap<String, Object>());
    }
}
