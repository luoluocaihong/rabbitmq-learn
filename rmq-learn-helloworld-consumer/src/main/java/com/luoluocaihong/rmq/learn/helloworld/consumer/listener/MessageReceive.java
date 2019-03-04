package com.luoluocaihong.rmq.learn.helloworld.consumer.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by xh on 2019/3/1.
 */
public class MessageReceive implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("I received message:" + new String(message.getBody()));

        try {
            Thread.sleep(50000);
            System.out.println("Message information:" + message.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
