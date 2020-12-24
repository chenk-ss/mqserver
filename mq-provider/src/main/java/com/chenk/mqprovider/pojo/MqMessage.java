package com.chenk.mqprovider.pojo;

import lombok.Data;

/**
 * @Author chenk
 * @create 2020/12/23 23:14
 */
@Data
public class MqMessage {
    private String topic;
    private String message;
    private String clientId;
}
