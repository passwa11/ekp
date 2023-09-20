package com.landray.kmss.third.ding.model.api;

import java.util.List;

public class TodoCard {

    public static final Integer CARDTYPE_STANDARD=1; //标准卡片
    public static final Integer CARDTYPE_CUSTOM=2; //自定义卡片

    private Integer cardType;
    private String icon;
    private String description;
    private String pcDetailUrlOpenMode;
    private List<CardField> contentFieldList;
    private List<ActionList> actionList;

    public List<ActionList> getActionList() {
        return actionList;
    }

    public void setActionList(List<ActionList> actionList) {
        this.actionList = actionList;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPcDetailUrlOpenMode() {
        return pcDetailUrlOpenMode;
    }

    public void setPcDetailUrlOpenMode(String pcDetailUrlOpenMode) {
        this.pcDetailUrlOpenMode = pcDetailUrlOpenMode;
    }

    public List<CardField> getContentFieldList() {
        return contentFieldList;
    }

    public void setContentFieldList(List<CardField> contentFieldList) {
        this.contentFieldList = contentFieldList;
    }

    public static class CardField {
        private String fieldKey;
        private String fieldType;
        private NameI18n nameI18n;

        public CardField() {
            fieldType = "text"; //目前钉钉只支持text
        }

        public CardField(String fieldKey, NameI18n nameI18n) {
            this.fieldKey = fieldKey;
            this.nameI18n = nameI18n;
            fieldType = "text";
        }

        public CardField(String fieldKey, String fieldType, NameI18n nameI18n) {
            this.fieldKey = fieldKey;
            this.fieldType = fieldType;
            this.nameI18n = nameI18n;
        }

        public String getFieldKey() {
            return fieldKey;
        }

        public void setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
        }

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public NameI18n getNameI18n() {
            return nameI18n;
        }

        public void setNameI18n(NameI18n nameI18n) {
            this.nameI18n = nameI18n;
        }
    }

    public static class NameI18n {
        private String zh_CN;
        private String zh_TW;
        private String zh_HK;
        private String en_US;
        private String vi_VN;
        private String ja_JP;

        public String getZh_CN() {
            return zh_CN;
        }

        public void setZh_CN(String zh_CN) {
            this.zh_CN = zh_CN;
        }

        public String getZh_TW() {
            return zh_TW;
        }

        public void setZh_TW(String zh_TW) {
            this.zh_TW = zh_TW;
        }

        public String getZh_HK() {
            return zh_HK;
        }

        public void setZh_HK(String zh_HK) {
            this.zh_HK = zh_HK;
        }

        public String getEn_US() {
            return en_US;
        }

        public void setEn_US(String en_US) {
            this.en_US = en_US;
        }

        public String getVi_VN() {
            return vi_VN;
        }

        public void setVi_VN(String vi_VN) {
            this.vi_VN = vi_VN;
        }

        public String getJa_JP() {
            return ja_JP;
        }

        public void setJa_JP(String ja_JP) {
            this.ja_JP = ja_JP;
        }

        public NameI18n() {
        }

        public NameI18n(String zh_CN, String zh_TW, String zh_HK, String en_US, String vi_VN, String ja_JP) {
            this.zh_CN = zh_CN;
            this.zh_TW = zh_TW;
            this.zh_HK = zh_HK;
            this.en_US = en_US;
            this.vi_VN = vi_VN;
            this.ja_JP = ja_JP;
        }

        public NameI18n(String name) {
            this.zh_CN = name;
            this.zh_TW = name;
            this.zh_HK = name;
            this.en_US = name;
            this.vi_VN = name;
            this.ja_JP = name;
        }
    }

    public static class ActionList {
        private String actionKey;
        private Integer buttonStyleType;
        private Integer actionType;
        private String url;
        private NameI18n nameI18n;

        public ActionList() {
        }

        public ActionList(String actionKey, Integer buttonStyleType, Integer actionType, String url, NameI18n nameI18n) {
            this.actionKey = actionKey;
            this.buttonStyleType = buttonStyleType;
            this.actionType = actionType;
            this.url = url;
            this.nameI18n = nameI18n;
        }

        public String getActionKey() {
            return actionKey;
        }

        public void setActionKey(String actionKey) {
            this.actionKey = actionKey;
        }

        public Integer getButtonStyleType() {
            return buttonStyleType;
        }

        public void setButtonStyleType(Integer buttonStyleType) {
            this.buttonStyleType = buttonStyleType;
        }

        public Integer getActionType() {
            return actionType;
        }

        public void setActionType(Integer actionType) {
            this.actionType = actionType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public NameI18n getNameI18n() {
            return nameI18n;
        }

        public void setNameI18n(NameI18n nameI18n) {
            this.nameI18n = nameI18n;
        }
    }

}
