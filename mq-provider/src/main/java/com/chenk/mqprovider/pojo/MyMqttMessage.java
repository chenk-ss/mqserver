package com.chenk.mqprovider.pojo;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Map;

/**
 * @Author chenk
 * @create 2020/12/24 15:35
 */
@Data
public class MyMqttMessage extends MqttMessage {
    private Map<String, Object> properties;
}
