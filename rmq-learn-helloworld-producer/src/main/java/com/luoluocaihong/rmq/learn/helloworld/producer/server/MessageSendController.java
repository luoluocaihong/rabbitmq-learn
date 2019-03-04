package com.luoluocaihong.rmq.learn.helloworld.producer.server;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xh on 2019/3/1.
 */
@RestController
public class MessageSendController {
    private final static String QUEUE_NAME = "hello";

    @Autowired
    RabbitTemplate rabbitTemplate;


    @GetMapping("/send")
    public String sendMessage() {
        String message = "Hello World!";
        //内部convertMessageIfNecessary(message)
        //MessageDeliveryMode deliveryMode = MessageDeliveryMode.PERSISTENT
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);

        return message;
    }
}
