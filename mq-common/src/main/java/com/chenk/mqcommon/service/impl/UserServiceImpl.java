package com.chenk.mqcommon.service.impl;

import com.chenk.mqcommon.bean.UserBean;
import com.chenk.mqcommon.service.UserService;
import com.chenk.mqcommon.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author chenk
 * @create 2021/2/1 9:32
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Override
    public UserBean queryUserByUserName(String userName) {
        UserBean userBean = new UserBean();
        userBean.setUserName(userName);
        Example<UserBean> example = Example.of(userBean);
        List<UserBean> beans = userRepository.findAll(example);
        if (beans == null || beans.isEmpty()) {
            return null;
        }
        return beans.get(0);
    }
}
