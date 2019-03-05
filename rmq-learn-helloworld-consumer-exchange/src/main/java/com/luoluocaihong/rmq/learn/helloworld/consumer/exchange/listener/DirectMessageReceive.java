package com.luoluocaihong.rmq.learn.helloworld.consumer.exchange.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xh on 2019/3/5.
 */
@Configuration
public class DirectMessageReceive {
    @RabbitListener(queues="hello")
    public void processHello(String str1) {
        System.out.println("DReceive Hello:"+str1);
    }

    @RabbitListener(queues="test")
    public void processTest(String str1) {
        System.out.println("DReceive Test:"+str1);
    }

    @RabbitListener(queues="ok")
    public void processOk(String str1) {
        System.out.println("DReceive OK:"+str1);
    }
}
