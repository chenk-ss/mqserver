package com.chenk.mqcommon.pojo;

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

    public Result(String message, Boolean success, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public Result(T data) {
        this.success = true;
        this.data = data;
    }
}
