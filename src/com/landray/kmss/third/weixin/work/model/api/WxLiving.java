package com.landray.kmss.third.weixin.work.model.api;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class WxLiving implements Serializable {
    @JSONField(name = "livingid")
    private String livingid;
    @JSONField(name = "anchor_userid")
    private String anchorUserid;
    private String theme;
    private String description;
    private Integer type;
    @JSONField(name = "remind_time")
    private Integer remind_time;
    private Long agentid;
    @JSONField(name = "living_start")
    private Long livingStart;

    @JSONField(name = "living_duration")
    private Integer livingDuration;

    @JSONField(name = "activity_cover_mediaid")
    private String activityCoverMediaid;

    @JSONField(name = "activity_share_mediaid")
    private String activityShareMediaid;

    public String getLivingid() {
        return livingid;
    }

    public void setLivingid(String livingid) {
        this.livingid = livingid;
    }

    public String getAnchorUserid() {
        return anchorUserid;
    }

    public void setAnchorUserid(String anchorUserid) {
        this.anchorUserid = anchorUserid;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRemind_time() {
        return remind_time;
    }

    public void setRemind_time(Integer remind_time) {
        this.remind_time = remind_time;
    }

    public Long getAgentid() {
        return agentid;
    }

    public void setAgentid(Long agentid) {
        this.agentid = agentid;
    }

    public Long getLivingStart() {
        return livingStart;
    }

    public void setLivingStart(Long livingStart) {
        this.livingStart = livingStart;
    }

    public Integer getLivingDuration() {
        return livingDuration;
    }

    public void setLivingDuration(Integer livingDuration) {
        this.livingDuration = livingDuration;
    }

    public String getActivityCoverMediaid() {
        return activityCoverMediaid;
    }

    public void setActivityCoverMediaid(String activityCoverMediaid) {
        this.activityCoverMediaid = activityCoverMediaid;
    }

    public String getActivityShareMediaid() {
        return activityShareMediaid;
    }

    public void setActivityShareMediaid(String activityShareMediaid) {
        this.activityShareMediaid = activityShareMediaid;
    }
}
