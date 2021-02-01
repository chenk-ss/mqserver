package com.chenk.mqprovider.controller;

import com.chenk.mqcommon.bean.UserBean;
import com.chenk.mqcommon.pojo.LoginParam;
import com.chenk.mqcommon.pojo.Result;
import com.chenk.mqcommon.service.TopicPowerService;
import com.chenk.mqcommon.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


/**
 * @Author chenk
 * @create 2021/1/29 10:49
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private UserService userService;
    @Resource
    private TopicPowerService topicPowerService;

    @PostMapping(value = "/login")
    public Result<String> publishTopic(@RequestBody LoginParam loginParam) {
        UserBean userBean = userService.queryUserByUserName(loginParam.getUserName());
        if (userBean == null) {
            return new Result("登陆失败", false, "");
        }

        if (userBean.getUserName().equals(loginParam.getUserName())
                && userBean.getPassword().equals(loginParam.getPassword())) {
            String token = UUID.randomUUID().toString();
            if ("admin".equals(loginParam.getUserName())) {
                token = "2188a3b0-c071-4159-922b-ac8ad1ab2f44";
            }
            redisTemplate.opsForValue().set("TokenOf" + loginParam.getUserName(), token);

            List<String> topics = topicPowerService.getTopics(loginParam.getUserName());
            if (topics != null) {
                redisTemplate.opsForList().rightPushAll("TopicOf" + loginParam.getUserName(), topics);
            }

            return new Result("登陆成功", true, token);
        }
        return new Result("登陆失败", false, "");
    }
}
