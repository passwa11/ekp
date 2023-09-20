package com.landray.kmss.km.imeeting.webservice;

/**
 * 会议室预定
 */
public class KmImeetingBookParamterForm {
    //会议预定唯一标识
    private String fdId;
    //会议名称
    private String fdName;
    //会议登记人
    private String docCreator;
    //召开时间
    private String fdHoldDate;
    //结束时间
    private String fdFinishDate;
    //会议历时
    private String fdHoldDuration;
    //会议地点
    private String fdPlace;
    //备注
    private String fdRemark;

    public String getFdId() { return fdId; }

    public void setFdId(String fdId) { this.fdId = fdId; }
    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getDocCreator() {
        return docCreator;
    }

    public void setDocCreator(String docCreator) {
        this.docCreator = docCreator;
    }

    public String getFdHoldDate() {
        return fdHoldDate;
    }

    public void setFdHoldDate(String fdHoldDate) {
        this.fdHoldDate = fdHoldDate;
    }

    public String getFdFinishDate() {
        return fdFinishDate;
    }

    public void setFdFinishDate(String fdFinishDate) {
        this.fdFinishDate = fdFinishDate;
    }

    public String getFdHoldDuration() {
        return fdHoldDuration;
    }

    public void setFdHoldDuration(String fdHoldDuration) {
        this.fdHoldDuration = fdHoldDuration;
    }

    public String getFdRemark() {
        return fdRemark;
    }

    public void setFdRemark(String fdRemark) {
        this.fdRemark = fdRemark;
    }

    public String getFdPlace() { return fdPlace; }

    public void setFdPlace(String fdPlace) { this.fdPlace = fdPlace; }
}
