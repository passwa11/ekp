package com.landray.kmss.km.imeeting.webservice;

public class KmImeetingResResult {
    /**
     * 返回结果
     * 0失败,1成功
     */
    private int resultState;
    /**
     * 返回相关信息
     * 	 返回状态值为0时，该值错误信息
     * 	 返回状态值为1时，该值返回数据结果,格式为json数组.如:
     * 	 [{"id":"1","name":"test01","detail":"message1"...}]
     */
    private String message;
    /**
     * 返回数据的总条目数
     */
    private int count;

    public int getResultState() {
        return resultState;
    }

    public void setResultState(int resultState) {
        this.resultState = resultState;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
