package com.chenk.mqconsumer;

import util.MyClient;

/**
 * @Author chenk
 * @create 2021/1/10 17:43
 */
public class Consumer {

    public static void main(String[] args) {
//        new MyClient("CKConsumerTest1", true).consume("CKTopicTest");

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new MyClient("CKConsumerTest" + finalI, true).consume("CKTopicTest");
                }
            }).start();
        }
    }
}
