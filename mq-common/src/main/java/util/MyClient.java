package util;

import com.google.gson.Gson;
import listener.MyIMqttMessageListener;
import lombok.Data;
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
@Data
@Slf4j
public class MyClient {

    public static MqttClient client;

    static Gson GSON = new Gson();

    private static String HOST = "tcp://127.0.0.1:1883";
    public static String clientid = "CK1";
    private static String userName = "admin";
    private static String passWord = "a123";


    public MyClient(boolean retained){
        try {
            client = retained ? new MqttClient(HOST, clientid, new MemoryPersistence()) : new MqttClient(HOST, clientid);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        connect();
    }

    public MyClient(String clientid, boolean retained){
        this.clientid = clientid;
        new MyClient(retained);
    }

    public static Boolean isConnected() {
        return client.isConnected();
    }

    private static void connect() {
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
//            connect();
        }
    }

    public static MqttDeliveryToken publish(String topic, MyMqttMessage message){
        try {
            log.info("message:{}" + GSON.toJson(message));
            return client.getTopic(topic).publish(message.getPayload(), message.getQos(), message.isRetained());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void consume(String topic){
        try {
            client.subscribeWithResponse(topic, new MyIMqttMessageListener());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
