package com.luoluocaihong.rmq.learn.helloworld.consumer.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by xh on 2019/3/5.
 */
public class BroadcastMessageReceive implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("I received message:" + new String(message.getBody()));
    }
}
