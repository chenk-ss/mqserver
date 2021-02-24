package com.chenk.mqconsumer;

import com.chenk.mqcommon.util.MyClient;

/**
 * @Author chenk
 * @create 2021/1/10 17:43
 */
public class Consumer {

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new MyClient("CKConsumerTest" + finalI).consume("CKTopicTest");
                }
            }).start();
        }
    }
}
