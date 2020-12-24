package com.chenk.mqprovider.controller;

import com.chenk.mqprovider.pojo.MqMessage;
import com.chenk.mqprovider.pojo.MyMqttMessage;
import com.chenk.mqprovider.pojo.Result;
import com.chenk.mqprovider.util.MyClient;
import org.apache.activemq.broker.region.MessageReference;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<String> publishTopic(@RequestBody MqMessage mqMessage) throws MqttException {
        MyMqttMessage mqttMessage = new MyMqttMessage();
        mqttMessage.setQos(2);
        mqttMessage.setRetained(true);
        mqttMessage.setPayload(mqMessage.getMessage().getBytes());
        if (!"".equals(mqMessage.getClientId())) {
            Map map = new HashMap<>();
            map.put("PTP_CLIENTID", mqMessage.getClientId());
            map.put("MESSAGE", mqMessage.getMessage());
            mqttMessage.setPayload(map.toString().getBytes());
        }
        MyClient.publish(mqMessage.getTopic(), mqttMessage);

        Result result = new Result();
        result.setMessage("发送成功");
        result.setSuccess(true);
        return result;
    }

}
