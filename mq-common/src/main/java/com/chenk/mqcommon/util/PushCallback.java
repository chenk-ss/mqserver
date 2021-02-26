package com.chenk.mqcommon.util;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

import java.util.List;

@Slf4j
public class PushCallback implements MqttCallback {

    private MqttClient mqttClient;
    private List<String> topics;

    public PushCallback(MqttClient mqttClient, List<String> topics) {
        this.mqttClient = mqttClient;
        this.topics = topics;
    }

    @Override
    public void connectionLost(Throwable e) {
        log.error("连接断开");
        try {
            Thread.sleep(60);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        MyClient myClient = new MyClient(mqttClient.getClientId());
        if (topics.size() > 0) {
            myClient.consume((String[]) topics.toArray());
        }
        log.info("重连结束");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("deliveryComplete---------" + token.isComplete());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Thread th = Thread.currentThread();
        log.info(th.getName());
        log.info("接收消息主题 : " + topic);
        log.info("接收消息Qos : " + message.getQos());
        log.info("接收消息内容 : " + new String(message.getPayload()));
    }
}
