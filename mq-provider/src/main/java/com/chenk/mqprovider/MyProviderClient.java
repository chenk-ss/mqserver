package com.chenk.mqprovider;

import util.MyClient;

/**
 * @Author chenk
 * @create 2021/1/21 9:19
 */
public class MyProviderClient {

    private static MyClient myClient;

    private MyProviderClient() {

    }

    public static MyClient getMyProviderClinet() {
        if (myClient == null) {
            synchronized (MyProviderClient.class) {
                if (myClient == null) {
                    myClient = new MyClient(true);
                }
            }
        }
        return myClient;
    }
}
