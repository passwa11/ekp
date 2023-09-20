package com.landray.kmss.sys.attachment.restservice.foxit.controller;

import java.util.HashMap;
import java.util.Map;

public class FoxitPermission {
    private  Map<String, Object> data = new HashMap<>();

    //权限总开关	  0 -> 无任何权限,文档不可读；2 ->有权限并根据其他权限信息开始管控；
    public FoxitPermission setPermission(String permission) {
        data.put("permission", permission);
        return this;
    }
    //LOGO栏		 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setHead(String head) {
        data.put("head", head);
        return this;
    }
    //打开⽂件 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setOpenFileBtn(String openFileBtn) {
        data.put("openFileBtn", openFileBtn);
        return this;
    }
    //保存⽂件 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setSaveBtn(String saveBtn) {
        data.put("saveBtn", saveBtn);
        return this;
    }
    //打印 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setPrintBtn(String printBtn) {
        data.put("printBtn", printBtn);
        return this;
    }
    //跳转指定⻚框体 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setGoToPageBox(String goToPageBox) {
        data.put("goToPageBox", goToPageBox);
        return this;
    }
    //⻚⾯缩放 0 -> 隐藏；1 -> 开启
    public FoxitPermission setZoomPageBox(String zoomPageBox) {
        data.put("zoomPageBox", zoomPageBox);
        return this;
    }
    //⻚⾯布局 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setPageLayoutBtn(String pageLayoutBtn) {
        data.put("pageLayoutBtn", pageLayoutBtn);
        return this;
    }
    //⼿型⼯具 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setHandToolBtn(String handToolBtn) {
        data.put("handToolBtn", handToolBtn);
        return this;
    }
    //选择⽂本⼯具 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setTextSelectBtn(String textSelectBtn) {
        data.put("textSelectBtn", textSelectBtn);
        return this;
    }
    //⾼亮 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setHeightLightBtn(String heightLightBtn) {
        data.put("heightLightBtn", heightLightBtn);
        return this;
    }
    //下划线 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setUnderlineBtn(String underlineBtn) {
        data.put("underlineBtn", underlineBtn);
        return this;
    }
    //铅笔 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setPencilBtn(String pencilBtn) {
        data.put("pencilBtn", pencilBtn);
        return this;
    }
    //其他标注 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setDrawingAnnotBtn(String drawingAnnotBtn) {
        data.put("drawingAnnotBtn", drawingAnnotBtn);
        return this;
    }
    //注释 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setCommentsBtn(String commentsBtn) {
        data.put("commentsBtn", commentsBtn);
        return this;
    }
    //签章 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setElecSignatureBtn(String elecSignatureBtn) {
        data.put("elecSignatureBtn", elecSignatureBtn);
        return this;
    }
    //验章 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setCheckElecSignatureBtn(String checkElecSignatureBtn) {
        data.put("checkElecSignatureBtn", checkElecSignatureBtn);
        return this;
    }
    //⻚⾯旋转 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setRotateSwitchBtn(String rotateSwitchBtn) {
        data.put("rotateSwitchBtn", rotateSwitchBtn);
        return this;
    }
    //下载⽂档 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setExportBtn(String exportBtn) {
        data.put("exportBtn", exportBtn);
        return this;
    }
    //⼤纲 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setOutlineBtn(String outlineBtn) {
        data.put("outlineBtn", outlineBtn);
        return this;
    }
    //缩略图 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setThumbnailBtn(String thumbnailBtn) {
        data.put("thumbnailBtn", thumbnailBtn);
        return this;
    }
    //注释评论 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setCommentListBtn(String commentListBtn) {
        data.put("commentListBtn", commentListBtn);
        return this;
    }
    //查找 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setSearchBtn(String searchBtn) {
        data.put("searchBtn", searchBtn);
        return this;
    }
    //语义树 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setSemanticTreeBtn(String semanticTreeBtn) {
        data.put("semanticTreeBtn", semanticTreeBtn);
        return this;
    }
    //附件 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setAttachmentBtn(String attachmentBtn) {
        data.put("attachmentBtn", attachmentBtn);
        return this;
    }
    //可读⻚码控制；”ALL“或空值不限制、”None“全部⻚不可读、⾃定义格式：“1-3，5-6”或“1,3,5”；
    //LOGO栏 0 -> 隐藏；1 -> 开启；
    public FoxitPermission setPageRange(String pageRange) {
        data.put("pageRange", pageRange);
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
