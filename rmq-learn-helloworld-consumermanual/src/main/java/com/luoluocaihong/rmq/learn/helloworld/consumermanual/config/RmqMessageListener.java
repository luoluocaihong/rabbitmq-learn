package com.luoluocaihong.rmq.learn.helloworld.consumermanual.config;

import com.luoluocaihong.rmq.learn.helloworld.consumermanual.listener.MessageReceiveAckManual;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xh on 2019/3/4.
 */
@Configuration
public class RmqMessageListener {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public MessageListener messageReceive() {
        return new MessageReceiveAckManual();
    }


    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(SimpleRabbitListenerContainerFactory factory) {
        SimpleMessageListenerContainer container = factory.createListenerContainer();
        //设置监听的队列名
        String[] types = {"hello"};
        container.setQueueNames(types);
        container.setMessageListener(messageReceive());

        return container;
    }
}
