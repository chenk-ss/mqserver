package com.chenk.mqprovider.controller;

import com.chenk.mqcommon.bean.UserBean;
import com.chenk.mqcommon.pojo.LoginParam;
import com.chenk.mqcommon.pojo.Result;
import com.chenk.mqcommon.service.TopicPowerService;
import com.chenk.mqcommon.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private UserService userService;
    @Resource
    private TopicPowerService topicPowerService;

    public static String TOPIC = "TopicOf";
    public static String TOKEN = "TokenOf";

    @PostMapping(value = "login")
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
            stringRedisTemplate.opsForValue().set(TOKEN + loginParam.getUserName(), token);

            List<String> topics = topicPowerService.getTopics(loginParam.getUserName());
            if (topics != null && !topics.isEmpty()) {
                stringRedisTemplate.opsForList().rightPushAll(TOPIC + loginParam.getUserName(), topics);
            }
            return new Result("登陆成功", true, token);
        }
        return new Result("登陆失败", false, "");
    }

    @GetMapping(value = "list")
    public Result<List<UserBean>> query(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return new Result<>(userService.query(page, size));
    }

    @GetMapping(value = "power")
    public Result<List<String>> userPower(@RequestParam("userName") String userName) {
        return new Result<>(topicPowerService.getTopics(userName));
    }

    @PostMapping(value = "power/add")
    public Result<Boolean> addPower(@RequestParam("userName") String userName, @RequestParam("topic") String topic) {
        UserBean user = userService.queryUserByUserName(userName);
        if (user == null) {
            return new Result<>("用户名不存在", false, false);
        }
        topicPowerService.add(user.getId(), userName, topic);
        stringRedisTemplate.opsForList().rightPushAll(TOPIC + userName, topic);
        return new Result<>(true);
    }

    @PostMapping(value = "power/remove")
    public Result<Boolean> removePower(@RequestParam("userName") String userName, @RequestParam("topic") String topic) {
        topicPowerService.remove(userName, topic);
        List<String> topicList = stringRedisTemplate.opsForList().range(TOPIC + userName, 0, -1);
        int index = topicList.indexOf(topic);
        if (index != -1) {
            topicList.remove(index);
        }
        stringRedisTemplate.opsForList().leftPushAll(TOPIC + userName);
        if (topicList != null && !topicList.isEmpty()) {
            stringRedisTemplate.opsForList().rightPushAll(TOPIC + userName, topicList);
        }
        return new Result<>(true);
    }
}
