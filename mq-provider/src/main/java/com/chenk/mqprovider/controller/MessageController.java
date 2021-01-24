package com.chenk.mqprovider.controller;

import com.chenk.mqprovider.MyProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import pojo.MqMessage;
import pojo.Result;
import util.MyProClient;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author chenk
 * @create 2020/12/23 17:08
 */
@RestController
@EnableScheduling
@RequestMapping("/message")
public class MessageController {

    MyProClient myClient = MyProviderClient.getMyProviderClinet();

    @PostMapping(value = "/send")
    public Result<String> publishTopic(@RequestBody MqMessage mqMessage) {
        try {
            myClient.send(mqMessage.getTopic(), mqMessage.getMessage(), mqMessage.getClientId());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return new Result("发送成功", true, "");
    }

    @Scheduled(fixedRate=120000)
    public void sc() throws JMSException {
        myClient.send("heartBeat", "1", null);
    }

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @PostMapping("/send2")
    public void send(@RequestParam("message") String msg, @RequestParam("clientId") String clientId) {
        // 指定消息发送的目的地及内容
        System.out.println("@@@@@@@@@@@@@@" + msg);
        String topicName = "CKTopicTest";
        Topic topic = new Topic() {
            @Override
            public String getTopicName() throws JMSException {
                return topicName;
            }
        };
        Map<String, String> map = new HashMap<>();
        map.put("msg", msg);
        if (clientId != null && !"".equals(clientId)) {
            map.put("_CLIENTID", clientId);
        }
        this.jmsMessagingTemplate.convertAndSend(topic, msg);
    }

}
