package com.landray.kmss.km.imeeting.webservice;

/**
 * 会议室信息
 */
public class KmImeetingResParamterForm {
    //会议室唯一标识
    private String fdId;
    //会议室名称
    private String fdName;
    // 设备详情
    private String  fdDetail;
    //会议室楼层
    private String fdAddressFloor;
    //容纳人数
    private String fdSeats;
    //创建人
    private String docCreator;
    //保管人
    private String docKeeper;
    //是否有效
    private String fdIsAvailable;
    //排序号
    private String fdOrder;
    //所属分类
    private String docCategoryId;

    //最大使用时长(小时)
    private String fdUserTime;

    public String getFdId() { return fdId; }

    public void setFdId(String fdId) { this.fdId = fdId; }

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getFdDetail() {
        return fdDetail;
    }

    public void setFdDetail(String fdDetail) {
        this.fdDetail = fdDetail;
    }

    public String getFdAddressFloor() {
        return fdAddressFloor;
    }

    public void setFdAddressFloor(String fdAddressFloor) {
        this.fdAddressFloor = fdAddressFloor;
    }

    public String getFdSeats() {
        return fdSeats;
    }

    public void setFdSeats(String fdSeats) {
        this.fdSeats = fdSeats;
    }

    public String getDocCreator() {
        return docCreator;
    }

    public void setDocCreator(String docCreator) {
        this.docCreator = docCreator;
    }

    public String getDocKeeper() {
        return docKeeper;
    }

    public void setDocKeeper(String docKeeper) {
        this.docKeeper = docKeeper;
    }

    public String getFdIsAvailable() { return fdIsAvailable; }

    public void setFdIsAvailable(String fdIsAvailable) { this.fdIsAvailable = fdIsAvailable; }

    public String getFdOrder() { return fdOrder; }

    public void setFdOrder(String fdOrder) { this.fdOrder = fdOrder; }

    public String getDocCategoryId() {
        return docCategoryId;
    }

    public void setDocCategoryId(String docCategoryId) {
        this.docCategoryId = docCategoryId;
    }

    public String getFdUserTime() {
        return fdUserTime;
    }

    public void setFdUserTime(String fdUserTime) {
        this.fdUserTime = fdUserTime;
    }
}
