package com.chenk.mqcommon.service;

import com.chenk.mqcommon.bean.UserBean;

/**
 * @Author chenk
 * @create 2021/2/1 9:31
 */
public interface UserService {
    UserBean queryUserByUserName(String userName);
}
