package com.landray.kmss.sys.attachment.restservice.foxit.controller.dao;


/**
 * 回调基础类
 */
public class CallbackBase {

    // 0,成功 1,失败
    private Integer code;

    // 信息
    private String msg;

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
}
