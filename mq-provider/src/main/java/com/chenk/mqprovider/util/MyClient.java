package com.chenk.mqprovider.util;

import com.chenk.mqprovider.pojo.PushCallback;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @Author chenk
 * @create 2020/12/23 22:37
 */
@Slf4j
public class MyClient {

    public static MqttClient client;

    private static String HOST = "tcp://127.0.0.1:1883";
    private static String clientid = "YLClient";
    private static String userName = "admin";
    private static String passWord = "password";
    private static String TOPIC = "YLTOPIC";


    public MyClient() throws MqttException {
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        connect();
    }

    private void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MqttTopic getMqttTopic(String topic) {
        return client.getTopic(TOPIC);
    }

    public static void publish(MqttTopic topic, MqttMessage message) throws MqttException {
        log.info("TOPIC:" + topic.toString());
        log.info("MESSAGE:" + message.toString());
        // 发布的方法
        MqttDeliveryToken token = topic.publish(message);
        // 发布
        token.waitForCompletion();
        log.info("message is published completely! "  + token.isComplete());
    }
}
