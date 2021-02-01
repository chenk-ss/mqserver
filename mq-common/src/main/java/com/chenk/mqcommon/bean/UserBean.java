package com.chenk.mqcommon.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author chenk
 * @create 2021/2/1 9:25
 */
@Entity
@Data
@Table(name = "tb_user")
public class UserBean {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;
}
