package com.chenk.mqcommon.util;

import com.chenk.mqcommon.listener.MyIMqttMessageListener;
import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author chenk
 * @create 2020/12/23 22:37
 */
@Data
@Slf4j
public class MyClient {

    static Gson GSON = new Gson();
    private MqttClient client;
    private String userName = "admin";
    private String passWord = "2188a3b0-c071-4159-922b-ac8ad1ab2f44";
    // ActiveMQ集群服务器IP+PORT
    private static List<String> urlLists = Arrays.asList("tcp://127.0.0.1:1883");
    private volatile static int num = 0;
    private String clientId;
    private List<String> topics = new ArrayList<>();
    public MyClient(String clientid) {
        this.clientId = clientid;
        int i = 0;
        while (true) {
            try {
                this.client = new MqttClient(urlLists.get(num), clientid, new MemoryPersistence());
                connect();
                log.info("连接成功" + urlLists.get(num));
                break;
            } catch (MqttException e) {
                log.info("连接失败" + urlLists.get(num));
                num = ++num % urlLists.size();
                i++;
                if (i >= urlLists.size()) {
                    break;
                }
            }
        }
    }

    private void connect() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(60);
        client.setCallback(new PushCallback(client, topics));
        client.connect(options);
    }

    public void consume(String topic) {
        try {
            topics.add(topic);
            client.subscribeWithResponse(topic, new MyIMqttMessageListener());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
