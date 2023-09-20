package com.landray.kmss.third.ding.model.api;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class TodoTask {

    private String id;
    private String sourceId;
    private String subject;
    private String creatorId;
    private String description;
    private Long dueTime;
    private List executorIds;
    private List participantIds;
    private DetailUrl detailUrl;
    private String cardTypeId;
    private List<Filed> contentFieldList;

    @JSONField(name = "isOnlyShowExecutor")
    private boolean isOnlyShowExecutor;

    public TodoTask() {
    }

    public TodoTask(String id, String sourceId, String subject, String creatorId, String description, Long dueTime, List executorIds, List participantIds, DetailUrl detailUrl, String cardTypeId, List<Filed> contentFieldList) {
        this.id = id;
        this.sourceId = sourceId;
        this.subject = subject;
        this.creatorId = creatorId;
        this.description = description;
        this.dueTime = dueTime;
        this.executorIds = executorIds;
        this.participantIds = participantIds;
        this.detailUrl = detailUrl;
        this.cardTypeId = cardTypeId;
        this.contentFieldList = contentFieldList;
    }

    public boolean isOnlyShowExecutor() {
        return isOnlyShowExecutor;
    }

    public void setOnlyShowExecutor(boolean onlyShowExecutor) {
        isOnlyShowExecutor = onlyShowExecutor;
    }

    public String getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(String cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public List<Filed> getContentFieldList() {
        return contentFieldList;
    }

    public void setContentFieldList(List<Filed> contentFieldList) {
        this.contentFieldList = contentFieldList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDueTime() {
        return dueTime;
    }

    public void setDueTime(Long dueTime) {
        this.dueTime = dueTime;
    }

    public List getExecutorIds() {
        return executorIds;
    }

    public void setExecutorIds(List executorIds) {
        this.executorIds = executorIds;
    }

    public List getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List participantIds) {
        this.participantIds = participantIds;
    }

    public DetailUrl getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(DetailUrl detailUrl) {
        this.detailUrl = detailUrl;
    }

    public static class DetailUrl {

        @JSONField(name = "appUrl")
        private String appUrl;

        @JSONField(name = "pcUrl")
        private String pcUrl;
        public DetailUrl() {
            super();
        }
        public DetailUrl(String appUrl, String pcUrl) {
            this.appUrl = appUrl;
            this.pcUrl = pcUrl;
        }

        public String getAppUrl() {
            return appUrl;
        }

        public void setAppUrl(String appUrl) {
            this.appUrl = appUrl;
        }

        public String getPcUrl() {
            return pcUrl;
        }

        public void setPcUrl(String pcUrl) {
            this.pcUrl = pcUrl;
        }
    }

    public static class Filed {
        @JSONField(name = "fieldKey")
        private String fieldKey;

        @JSONField(name = "fieldValue")
        private String fieldValue;

        public Filed() {
        }

        public Filed(String fieldKey, String fieldValue) {
            this.fieldKey = fieldKey;
            this.fieldValue = fieldValue;
        }

        public String getFieldKey() {
            return fieldKey;
        }

        public void setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }
    }
}
