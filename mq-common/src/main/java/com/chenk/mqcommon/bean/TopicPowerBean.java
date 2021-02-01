package com.chenk.mqcommon.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author chenk
 * @create 2021/2/1 9:27
 */
@Entity
@Data
@Table(name = "tb_topic_power")
public class TopicPowerBean {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String userName;

    @Column(name = "userid")
    private String userid;

    @Column(name = "topic")
    private String topic;

    @Column(name = "send")
    private Boolean send;
}
