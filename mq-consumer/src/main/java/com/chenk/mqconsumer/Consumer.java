package com.chenk.mqconsumer;

import util.MyClient;

/**
 * @Author chenk
 * @create 2021/1/10 17:43
 */
public class Consumer {

    public static void main(String[] args) {
        new MyClient("CKConsumerTest", true).consume("CKTopicTest");
    }
}
