package com.chenk.mqprovider.controller;

import com.chenk.mqprovider.MyProviderClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chenk.mqcommon.pojo.MqMessage;
import com.chenk.mqcommon.pojo.Result;
import com.chenk.mqcommon.util.MyProClient;

import javax.jms.JMSException;

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
            myClient.send(mqMessage.getTopic(), mqMessage.getMessage(), mqMessage.getClientId(), mqMessage.getRetain());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return new Result("发送成功", true, "");
    }
}
