package com.chenk.mqcommon.util;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@Slf4j
public class PushCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable e) {
        // 连接丢失后，在这里面进行重连
        log.error("连接断开，可以做重连");
//        while (!MyClient.isConnected()) {
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//            log.error("---reconnect---");
//            try {
//                MyClient.client.reconnect();
//                log.info("---reconnect success---");
//                break;
//            } catch (MqttException ex) {
//                log.error("---reconnect failed---");
//                ex.printStackTrace();
//            }
//        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("deliveryComplete---------" + token.isComplete());

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        log.info("接收消息主题 : " + topic);
        log.info("接收消息Qos : " + message.getQos());
        log.info("接收消息内容 : " + new String(message.getPayload()));

    }

}
