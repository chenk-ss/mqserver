package com.chenk.mqconsumer;

import org.eclipse.paho.client.mqttv3.MqttException;
import util.MyClient;

/**
 * @Author chenk
 * @create 2021/1/10 17:43
 */
public class Main {

    public static void main(String[] args) throws MqttException {
        new MyClient("CK2");
        MyClient.consume("CKTOPIC");
    }
}
