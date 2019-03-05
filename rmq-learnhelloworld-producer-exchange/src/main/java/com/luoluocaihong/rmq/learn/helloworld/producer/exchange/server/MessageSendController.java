package com.luoluocaihong.rmq.learn.helloworld.producer.exchange.server;

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

    private final static String ROUTING_KEY = "YELLOW";

    private final static String EXCHANGE_FANOUT_NAME = "luoluocaihongFanout";

    private final static String EXCHANGE_DIRECT_NAME = "luoluocaihongDirect";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendfanout")
    public String sendBroadcastMessage() {
        String message = "Hello World ! everyone!!";
        //fanout类型的exchange,即使发送的时候指定queuename,也会被这个exchange绑定的所有queue消费到,即fanout类型的exchange会忽略发送的时候指定的queue
        rabbitTemplate.convertAndSend(EXCHANGE_FANOUT_NAME, QUEUE_NAME, message);
        rabbitTemplate.convertAndSend(EXCHANGE_FANOUT_NAME, null, message);
        return message;
    }


    @GetMapping("/senddirect")
    public String sendDirectMessage() {
        String message = "Hello World to direct! everyone!!";
        rabbitTemplate.convertAndSend(EXCHANGE_DIRECT_NAME, ROUTING_KEY, message);
        return message;
    }
}
