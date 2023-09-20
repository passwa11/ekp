package com.landray.kmss.km.imeeting.webservice;

public class KmImeetingBookResulut {
    /**
     * 返回结果
     * 0失败,1成功
     */
    private int resultState;

    private String message;

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
