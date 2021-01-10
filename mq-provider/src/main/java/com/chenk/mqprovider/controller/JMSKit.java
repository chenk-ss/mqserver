package com.chenk.mqprovider.controller;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 通过JMS发送activeMQT消息，从而实现点对点发送
 * @Author chenk
 * @create 2020/12/24 14:27
 */
public class JMSKit {
    private static final String PTP_CLIENTID = "PTP_CLIENTID";
    private ActiveMQConnectionFactory factory;
    private Connection connection;
    private Session session;
    public boolean connect(String brokerURL, String clientId) {
        //factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            // 创建连接工厂
            factory = new ActiveMQConnectionFactory(brokerURL);
            // 鉴权，如没有开启可省略
            //factory.setUserName("admin");
            //factory.setPassword("admin123");
            // 创建JMS连接实例，并启动连接
            connection = factory.createConnection();
            connection.start();
            // 创建Session对象，不开启事务,采用自动应答
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            return true;
        } catch (JMSException e) {

            e.printStackTrace();
        }
        return false;
    }
    public void disconnect() {
        // 关闭连接
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    //向所有订阅者发送消息
    public boolean publish(String topic, String message, int QOS, boolean retained) {
        return publish(topic, message, QOS, retained, null);
    }
    //向指定clientId发送消息
    public boolean publish(String topic, String message, int QOS, boolean retained, String clientId) {
        // 创建主题
        try {
            Topic _topic = session.createTopic(topic);
            // 创建生成者
            MessageProducer producer = session.createProducer(_topic);
            // 设置消息是否持久化。默认消息需要持久化
            if(retained) {
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            }else {
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            }
            //创建消息
            TextMessage _message = session.createTextMessage(message);
            // 发送指定消息，配合主题分发策略使用，以附带用户ID ，分发策略对特定的主题进行拦截解析分发
            if (!"".equals(clientId)) {
                _message.setStringProperty(PTP_CLIENTID, clientId);
            }
            // 发送消息。non-persistent 默认异步发送；persistent 默认同步发送
            producer.send(_message);
            producer.close();
            return true;
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) throws JMSException {

        Scanner sc = new Scanner(System.in);
        boolean isStart = true;
        String userMsg = "";
        String msg = "";
        String[] messages = null;
        String clientId = null;
        while (isStart) {
            userMsg = sc.nextLine();
            if ("".equals(userMsg) || "stop".equals(userMsg)) {
                System.out.println("Stop producer message!");
                isStart = false;
            }
            messages = userMsg.split(":");
            msg = "Hello MQ,Client msg:" + messages[0];

            if (messages.length == 2) {
                clientId = messages[1];
            }
            JMSKit kit = new JMSKit();
            kit.connect("tcp://localhost:61616", "");
            if(clientId==null) {
                kit.publish("hello", msg, 2, false);
            }else {
                kit.publish("hello", msg, 2, true, clientId);
            }
            kit.disconnect();

        }
        sc.close();

    }
}
