package com.chenk.mqcommon.util;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.jms.IllegalStateException;

/**
 * @Author chenk
 * @create 2021/1/23 16:06
 */
public class MyProClient {
    private static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616?keepAlive=true";
    public String clientid = "admin";
    private String userName = "admin";
    private String passWord = "2188a3b0-c071-4159-922b-ac8ad1ab2f44";

    private MessageProducer producer;
    private Connection connection;
    private Session session;

    public synchronized void conn() throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        connection = activeMQConnectionFactory.createConnection(userName, passWord);
//        connection = ActiveMQPoolsUtil.getConnection();
        connection.setClientID(clientid);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public void send(String topic, String message, String clientId, Boolean retain) throws JMSException {
        if (session == null) {
            conn();
        }
        Destination destination;
        try {
            destination = session.createTopic(topic);
        } catch (IllegalStateException e) {
            conn();
            destination = session.createTopic(topic);
        }
        producer = session.createProducer(destination);
        producer.setDeliveryMode(retain ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT);
        TextMessage textMessage = session.createTextMessage(message);
        if (clientId != null && !"".equals(clientId)) {
            textMessage.setStringProperty("_CLIENTID", clientId);
        }
        producer.send(textMessage);
        System.out.println("已发送的消息：" + textMessage.getText());
    }

    public void close() throws JMSException {
        producer.close();
        session.close();
        connection.close();
    }
}
