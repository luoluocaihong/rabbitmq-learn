package com.luoluocaihong.rmq.learn.helloworld.consumer.exchange.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Created by xh on 2019/3/6.
 */
@Service
public class TopicMessageReceive {
    @RabbitListener(queues="orange")
    public void processHello(String str1) {
        System.out.println("TReceive Hello:"+str1);
    }
    @RabbitListener(queues="lazy")
    public void processTest(String str) {
        System.out.println("TReceive Test:"+str);
    }
}
