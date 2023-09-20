package com.landray.kmss.third.weixin.model;

public class ThirdWeixinContactTag {

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    private String group_name;

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    private String tag_name;
    private String tag_id;
    private Integer type;

    public ThirdWeixinContactTag(String group_name,String tag_id,String tag_name,Integer type){
        this.group_name = group_name;
        this.tag_id = tag_id;
        this.tag_name = tag_name;
        this.type = type;
    }
}
