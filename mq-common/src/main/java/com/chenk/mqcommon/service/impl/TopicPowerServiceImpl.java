package com.chenk.mqcommon.service.impl;

import com.chenk.mqcommon.bean.TopicPowerBean;
import com.chenk.mqcommon.repository.TopicPowerRepository;
import com.chenk.mqcommon.service.TopicPowerService;
import com.google.gson.Gson;
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

    Gson GSON = new Gson();
    @Resource
    private TopicPowerRepository topicPowerRepository;

    @Override
    public List<String> getTopics(String userName) {
        TopicPowerBean topicPowerBean = new TopicPowerBean();
        topicPowerBean.setUserName(userName);
        Example<TopicPowerBean> example = Example.of(topicPowerBean);
        List<TopicPowerBean> beans = topicPowerRepository.findAll(example);
        log.info(GSON.toJson(beans));
        if (beans == null || beans.isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (TopicPowerBean bean : beans) {
            result.add(bean.getTopic());
        }
        return result;
    }
}
