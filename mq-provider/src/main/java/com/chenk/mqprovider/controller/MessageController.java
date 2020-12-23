package com.chenk.mqprovider.controller;

import com.chenk.mqprovider.pojo.PushCallback;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author chenk
 * @create 2020/12/23 17:08
 */
@Controller
@RequestMapping("/message")
public class MessageController {
    private static final String HOST = "tcp://127.0.0.1:1883";
    private static String TOPIC = "YLTOPIC";
    private static String MESSAGE = "MESSGAE";
    private static final String clientid = "YLClient";

    private static MqttClient client;
    private static MqttTopic mqttTopic;

    private static String userName = "admin";
    private static String passWord = "password";

    private MqttMessage mqttMessage;

    @PostMapping(value = "/send")
    public String doPublish(@RequestParam("topic") String topic, @RequestParam("message") String message) throws MqttException {
        TOPIC = topic;
        MESSAGE = message;
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        connect();
        mqttMessage = new MqttMessage();
        mqttMessage.setQos(2);
        mqttMessage.setRetained(true);
        mqttMessage.setPayload(MESSAGE.getBytes());
        //发布
        publish(mqttTopic, mqttMessage);

        System.out.println("已发送");

        return "result";
    }

    private void connect() {
        // new mqttConnection 用来设置一些连接的属性
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        // 换而言之，设置为false时可以客户端可以接受离线消息
        options.setCleanSession(false);
        // 设置连接的用户名和密码
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(60);
        try {
            // 设置回调类
            client.setCallback(new PushCallback());
            // 连接
            client.connect(options);
            // 获取activeMQ上名为TOPIC的topic
            mqttTopic = client.getTopic(TOPIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException {
        // 发布的方法
        // new mqttDeliveryToken
        MqttDeliveryToken token = topic.publish(message);
        // 发布
        token.waitForCompletion();
        System.out.println("message is published completely! "
                + token.isComplete());
    }
}
