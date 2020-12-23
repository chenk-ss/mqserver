package com.chenk.mqprovider.pojo;

import lombok.Data;

/**
 * @Author chenk
 * @create 2020/12/23 23:10
 */
@Data
public class Result<T> {
    private String message;
    private Boolean success;
    private T data;
}
