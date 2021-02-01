package com.chenk.mqcommon.util;

import com.chenk.mqcommon.listener.MyIMqttMessageListener;
import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @Author chenk
 * @create 2020/12/23 22:37
 */
@Data
@Slf4j
public class MyClient {

    static Gson GSON = new Gson();

    private MqttClient client;
    private String HOST = "tcp://127.0.0.1:1883";
    public String clientid = "chenk";
    private String userName = "admin";
    private String passWord = "a123";

    public MyClient(String clientid, boolean retained) {
        try {
            this.client = retained ? new MqttClient(HOST, clientid, new MemoryPersistence()) : new MqttClient(HOST, clientid);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        connect();
    }

    public Boolean isConnected() {
        return client.isConnected();
    }

    private void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(60);
        try {
            client.setCallback(new PushCallback());
            client.connect(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consume(String topic) {
        try {
            client.subscribeWithResponse(topic, new MyIMqttMessageListener());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
