package com.chenk.mqcommon.service;

import java.util.List;

/**
 * @Author chenk
 * @create 2021/2/1 9:37
 */
public interface TopicPowerService {
    List<String> getTopics(String userName);

    void add(String userId, String userName, String topic);

    void remove(String userName, String topic);
}
