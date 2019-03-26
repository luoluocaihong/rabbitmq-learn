package com.luoluocaihong.rmq.learn.helloworld.consumer.exchange.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Created by xh on 2019/3/5.
 */
@Service
public class BroadcastMessageReceive {
    @RabbitListener(queues="hello")
    public void processHello(String str1) {
        System.out.println("BReceive Hello:"+str1);
    }
    @RabbitListener(queues="test")
    public void processTest(String str) {
        System.out.println("BReceive Test:"+str);
    }
    @RabbitListener(queues="fine")
    public void processFine(String str) {
        System.out.println("BReceive Fine:"+str);
    }
}
