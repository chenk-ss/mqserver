package com.chenk.mqcommon.service.impl;

import com.chenk.mqcommon.bean.TopicPowerBean;
import com.chenk.mqcommon.repository.TopicPowerRepository;
import com.chenk.mqcommon.service.TopicPowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author chenk
 * @create 2021/2/1 9:37
 */
@Slf4j
@Service
public class TopicPowerServiceImpl implements TopicPowerService {

    @Resource
    private TopicPowerRepository topicPowerRepository;

    @Override
    public List<String> getTopics(String userName) {
        TopicPowerBean topicPowerBean = new TopicPowerBean();
        topicPowerBean.setUserName(userName);
        Example<TopicPowerBean> example = Example.of(topicPowerBean);
        List<TopicPowerBean> beans = topicPowerRepository.findAll(example);
        if (beans == null || beans.isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>();
        beans.stream().forEach(bean -> result.add(bean.getTopic()));
        return result;
    }

    @Override
    public void add(String userId, String userName, String topic) {
        TopicPowerBean bean = new TopicPowerBean();
        bean.setUserName(userName);
        bean.setUserid(userId);
        bean.setTopic(topic);
        bean.setSend(true);
        topicPowerRepository.save(bean);
    }

    @Override
    public void remove(String userName, String topic) {
        TopicPowerBean bean = new TopicPowerBean();
        bean.setUserName(userName);
        bean.setTopic(topic);
        bean.setSend(true);
        topicPowerRepository.delete(bean);
    }
}
