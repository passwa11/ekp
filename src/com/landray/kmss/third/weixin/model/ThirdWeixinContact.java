package com.landray.kmss.third.weixin.model;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class ThirdWeixinContact {

    private String external_userid;

    public String getExternal_userid() {
        return external_userid;
    }

    public void setExternal_userid(String external_userid) {
        this.external_userid = external_userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCorp_name() {
        return corp_name;
    }

    public void setCorp_name(String corp_name) {
        this.corp_name = corp_name;
    }

    public String getCorp_full_name() {
        return corp_full_name;
    }

    public void setCorp_full_name(String corp_full_name) {
        this.corp_full_name = corp_full_name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public JSONObject getExternal_profile() {
        return external_profile;
    }

    public void setExternal_profile(JSONObject external_profile) {
        this.external_profile = external_profile;
    }

    public Map<String, ThirdWeixinContactTag> getTagsMap() {
        return tagsMap;
    }

    public void setTagsMap(Map<String, ThirdWeixinContactTag> tagsMap) {
        this.tagsMap = tagsMap;
    }

    private String name;
    private String position;
    private String corp_name;
    private String corp_full_name;
    private Integer type;
    private Integer gender;
    private String unionid;
    private JSONObject external_profile;
    public Map<String, ThirdWeixinContactTag> tagsMap = new HashMap<>();

    public ThirdWeixinContact(String external_userid,String name,String position,String corp_full_name,String corp_name,Integer gender){
        this.corp_full_name = corp_full_name;
        this.name =name;
        this.position = position;
        this.gender = gender;
        this.external_userid = external_userid;
        this.corp_name = corp_name;
    }

    public void addTag(ThirdWeixinContactTag tag){
        if(StringUtil.isNull(tag.getTag_id())||"null".equals(tag.getTag_id())){
            return;
        }
        if(tagsMap.containsKey(tag.getTag_id())){
            return;
        }
        tagsMap.put(tag.getTag_id(),tag);
    }

    @Override
    public String toString(){
        return JSONObject.toJSON(this).toString();
    }

}
