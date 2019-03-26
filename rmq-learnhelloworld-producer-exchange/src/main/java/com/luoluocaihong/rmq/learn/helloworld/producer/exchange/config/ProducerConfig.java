package com.luoluocaihong.rmq.learn.helloworld.producer.exchange.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

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

    //配置fanout广播路由器
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("luoluocaihongFanout");
    }

    @Bean
    Binding bindingFanoutHello(@Qualifier("helloQueue") Queue helloQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(helloQueue).to(fanoutExchange);
    }

    @Bean
    Binding bindingFanoutTest(@Qualifier("testQueue") Queue testQueue, FanoutExchange fanoutExchange) {
        //等价于 return BindingBuilder.bind(testQueue).to(fanoutExchange);  因为exchange类型为fanout时忽略routingKey
        return new Binding(testQueue.getName(), Binding.DestinationType.QUEUE, fanoutExchange.getName(), "AA", new HashMap<String, Object>());
    }



    @Bean
    public Queue okQueue() {
        return new Queue("ok");
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("luoluocaihongDirect");
    }

    @Bean
    Binding bindingDirectHello(@Qualifier("helloQueue") Queue helloQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(helloQueue).to(directExchange).with("YELLOW");
    }
    @Bean
    Binding bindingDirectTest(@Qualifier("testQueue") Queue testQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(testQueue).to(directExchange).with("YELLOW");
    }
    @Bean
    Binding bindingDirectOk(@Qualifier("okQueue") Queue okQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(okQueue).to(directExchange).with("BLUE");
    }




    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("luoluocaihongTopic");
    }
    @Bean
    public Queue orangeQueue() {
        return new Queue("orange");
    }

    @Bean
    public Queue lazyQueue() {
        return new Queue("lazy");
    }
    @Bean
    Binding bindingTopicHello(@Qualifier("orangeQueue") Queue orangeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(orangeQueue).to(topicExchange).with("*.orange.*");
    }
    @Bean
    Binding bindingTopicTest(@Qualifier("lazyQueue") Queue lazyQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(lazyQueue).to(topicExchange).with("lazy.#");
    }
}
