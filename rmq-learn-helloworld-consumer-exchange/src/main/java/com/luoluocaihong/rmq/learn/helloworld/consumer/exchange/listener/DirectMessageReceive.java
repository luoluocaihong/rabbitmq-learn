package com.luoluocaihong.rmq.learn.helloworld.consumer.exchange.listener;

import com.alibaba.fastjson.JSONObject;
import com.luoluocaihong.rmq.learn.helloworld.consumer.exchange.dto.Person;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Created by xh on 2019/3/5.
 */
@Service
public class DirectMessageReceive {
    @RabbitListener(queues="hello")
    public void processHello(String str1) {
        //org.springframework.amqp.utils.SerializationUtils.deserialize(str1);
        System.out.println("DReceive Hello:"+ str1);
    }

    @RabbitListener(queues="test")
    public void processTest(String str1) {
        System.out.println("DReceive Test:"+str1);
    }

    @RabbitListener(queues="ok")
    public void processOk(String str1) {
        //Person p = JSONObject.parseObject(str1, Person.class);
        System.out.println("DReceive OK:"+str1);
    }
}
