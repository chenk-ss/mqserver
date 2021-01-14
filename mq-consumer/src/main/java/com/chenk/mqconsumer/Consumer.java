package com.chenk.mqconsumer;

import org.eclipse.paho.client.mqttv3.MqttException;
import util.MyClient;

/**
 * @Author chenk
 * @create 2021/1/10 17:43
 */
public class Consumer {

    public static void main(String[] args) throws MqttException {
        new MyClient("CKConsumerTest").consume("CKTopicTest");
    }
}
