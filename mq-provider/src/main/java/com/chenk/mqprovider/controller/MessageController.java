package com.chenk.mqprovider.controller;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.MqMessage;
import pojo.MyMqttMessage;
import pojo.Result;
import util.MyClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author chenk
 * @create 2020/12/23 17:08
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @PostMapping(value = "/send")
    public Result<String> publishTopic(@RequestBody MqMessage mqMessage){
        MyMqttMessage mqttMessage = new MyMqttMessage(1, true);
        mqttMessage.setPayload(mqMessage.getMessage().getBytes());
        if (!"".equals(mqMessage.getClientId())) {
            Map map = new HashMap<>();
            map.put("_CLIENTID", mqMessage.getClientId());
            map.put("MESSAGE", mqMessage.getMessage());
            mqttMessage.setPayload(map.toString().getBytes());
        }
        MyClient.publish(mqMessage.getTopic(), mqttMessage);
        return new Result("发送成功", true, "");
    }

}
