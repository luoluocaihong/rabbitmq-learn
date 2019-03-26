package com.luoluocaihong.rmq.learn.helloworld.producer.exchange.server;

import ch.qos.logback.core.util.StringCollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.luoluocaihong.rmq.learn.helloworld.producer.exchange.dto.Person;
import com.rabbitmq.tools.json.JSONUtil;
import com.rabbitmq.tools.jsonrpc.JacksonJsonRpcMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.MapBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xh on 2019/3/1.
 */
@RestController
public class MessageSendController {
    private final static String QUEUE_NAME = "hello";

    private final static String ROUTING_KEY = "YELLOW";

    private final static String EXCHANGE_FANOUT_NAME = "luoluocaihongFanout";

    private final static String EXCHANGE_DIRECT_NAME = "luoluocaihongDirect";

    private final static String EXCHANGE_TOPIC_NAME = "luoluocaihongTopic";

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

    @GetMapping("/senddirect1")
    public String sendDirectMessage1() {
        //String message = "Hello World to direct! everyone!!";
        MapBuilder map = new MapBuilder();
        map.put("name", "luoluocaihong");
        map.put("age", 18L);
        rabbitTemplate.convertAndSend(EXCHANGE_DIRECT_NAME, ROUTING_KEY, map.get());
        return "luoluocaihong";
    }


    @GetMapping("/sendtopic/{key}")
    public String sendTopicMessage(@PathVariable("key") String key) {
        String message = "Hello World ! everyone!! It's topic";
        rabbitTemplate.convertAndSend(EXCHANGE_TOPIC_NAME, key, message);
        return message;
    }


    @GetMapping("/sendconfirm")
    public String sendWithConfirm() {
        String message = "Hello World,I'm waitting for your replication!!";
        CorrelationData data = new CorrelationData();
        data.setId(rabbitTemplate.getUUID());
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("消息已到达broker");
            }
            else {
                System.out.println("消息未到达broker");
            }
        });
        //消息没有到达queue则会调用这个方法
        rabbitTemplate.setReturnCallback((msg, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("ReturnCallback");
            System.out.println("msg:" + msg);
            System.out.println("replyCode:" + replyCode);
            System.out.println("replyText:" + replyText);
            System.out.println("exchange:" + exchange);
            System.out.println("routingKey:" + routingKey);
        });
        Object receive = rabbitTemplate.convertSendAndReceive(EXCHANGE_TOPIC_NAME, ROUTING_KEY, message, data);
        System.out.println(data.getReturnedMessage());
        System.out.println("======================");
        System.out.println(receive);
        return message;
    }
}
