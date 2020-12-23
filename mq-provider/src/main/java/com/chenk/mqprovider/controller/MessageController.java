package com.chenk.mqprovider.controller;

import com.chenk.mqprovider.pojo.MqMessage;
import com.chenk.mqprovider.pojo.Result;
import com.chenk.mqprovider.util.MyClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author chenk
 * @create 2020/12/23 17:08
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    private static MqttClient client = MyClient.client;

    @PostMapping(value = "/send")
    public Result<String> doPublish(MqMessage mqMessage) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(2);
        mqttMessage.setRetained(true);
        mqttMessage.setPayload(mqMessage.getMessage().getBytes());
        MyClient.publish(MyClient.getMqttTopic(mqMessage.getTopic()), mqttMessage);

        Result result = new Result();
        result.setMessage("发送成功");
        result.setSuccess(true);
        return result;
    }

}
