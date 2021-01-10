package util;

import listener.MyIMqttMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import pojo.MyMqttMessage;

/**
 * @Author chenk
 * @create 2020/12/23 22:37
 */
@Slf4j
public class MyClient {

    public static MqttClient client;

    private static String HOST = "tcp://127.0.0.1:1883";
    private static String clientid = "CK1";
    private static String userName = "admin";
    private static String passWord = "password";


    public MyClient() throws MqttException {
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        connect();
    }
    public MyClient(String clientid) throws MqttException {
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

    public static MqttDeliveryToken publish(String topic, MyMqttMessage message) throws MqttException {
        return client.getTopic(topic).publish(message.getPayload(), 2, true);
    }

    public static void consume(String topic) throws MqttException {
        client.subscribeWithResponse(topic, new MyIMqttMessageListener());
    }
}
