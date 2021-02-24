package com.chenk.mqcommon.service.impl;

import com.chenk.mqcommon.bean.UserBean;
import com.chenk.mqcommon.service.UserService;
import com.chenk.mqcommon.repository.UserRepository;
import org.springframework.data.domain.*;
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

    @Override
    public List<UserBean> query(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<UserBean> list = userRepository.findAll(pageable);
        list.get().forEach(userBean -> userBean.setPassword(""));
        return list.toList();
    }
}
