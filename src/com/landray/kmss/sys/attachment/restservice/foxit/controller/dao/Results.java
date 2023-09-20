package com.landray.kmss.sys.attachment.restservice.foxit.controller.dao;


import java.io.Serializable;

/**
 * 结果集信息
 */

public class Results<T> implements Serializable {

    private static final long serialVersionUID = 4240749068084741071L;

    /**
     * 状态码
     * 状态 0 为 成功, 1 为失败
     */
    private Integer code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T data;

    public Results() {

    }

    public Results(String msg, Integer code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public static <T> Results<T> ok() {
        return new Results<>("success", 0, null);
    }

    public static <T> Results<T> ok(T data) {
        return new Results<>("success", 0, data);
    }

    public static <T> Results<T> ok(String msg) {
        return new Results<>(msg, 0, null);
    }

    public static <T> Results<T> ok(String msg, T data) {
        return new Results<>(msg, 0, data);
    }

    public static <T> Results<T> fail() {
        return new Results<>("fail", 1, null);
    }

    public static <T> Results<T> fail(String msg) {
        return new Results<>(msg, 1, null);
    }

    public static  <T> Results<T> fail(int code, String msg) {
        return new Results<>(msg, code, null);
    }

    public Results<T> data(T data) {
        this.data = data;
        return this;
    }

    public boolean isOk(){
        return  this.getCode() == 0 ;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}