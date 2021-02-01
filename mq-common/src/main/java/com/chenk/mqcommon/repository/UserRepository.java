package com.chenk.mqcommon.repository;

import com.chenk.mqcommon.bean.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author chenk
 * @create 2021/2/1 9:25
 */
public interface UserRepository extends JpaRepository<UserBean, Integer> {
}
