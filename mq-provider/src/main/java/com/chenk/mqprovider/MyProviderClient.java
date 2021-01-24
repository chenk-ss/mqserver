package com.chenk.mqprovider;

import util.MyProClient;

/**
 * @Author chenk
 * @create 2021/1/21 9:19
 */
public class MyProviderClient {

    private static MyProClient myProClient;

    private MyProviderClient() {

    }

    public static MyProClient getMyProviderClinet() {
        if (myProClient == null) {
            synchronized (MyProviderClient.class) {
                if (myProClient == null) {
                    myProClient = new MyProClient();
                }
            }
        }
        return myProClient;
    }
}
