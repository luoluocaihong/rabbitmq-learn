package com.luoluocaihong.rmq.learn.helloworld.consumermanual.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * Created by xh on 2019/3/4.
 */
public class MessageReceiveAckManual implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            System.out.println("I received message:" + new String(message.getBody()));
            System.out.println("Message information:" + message.toString());
            int a = 10/0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
        catch(Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
