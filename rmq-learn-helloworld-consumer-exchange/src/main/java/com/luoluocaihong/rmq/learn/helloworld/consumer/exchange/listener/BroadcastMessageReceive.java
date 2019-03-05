package com.luoluocaihong.rmq.learn.helloworld.consumer.fanout.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xh on 2019/3/5.
 */
@Configuration
public class BroadcastMessageReceive {
    @RabbitListener(queues="hello")
    public void processHello(String str1) {
        System.out.println("Receive Hello:"+str1);
    }
    @RabbitListener(queues="test")
    public void processTest(String str) {
        System.out.println("Receive Test:"+str);
    }
    @RabbitListener(queues="fine")
    public void processFine(String str) {
        System.out.println("Receive Fine:"+str);
    }
}
