package com.landray.kmss.sys.attachment.restservice.foxit.controller;


import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.attachment.util.SysAttUtil;
import com.landray.kmss.sys.attachment.util.SysAttViewerUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 福昕水印
 */
public class FoxitWaterInfo {
    private  Map<String, Object> data = new HashMap<>();
    private net.sf.json.JSONObject watermarkCfg = SysAttViewerUtil.getWaterMarkConfigInDB(true);

    /**
     * 基础信息
     * @return
     */
    public FoxitWaterInfo getBaseInfo() {
        if(!isWordWater() && !isPicWater()) {
            data.put("isExplicit", "0");
            return this;
        }

        data.put("isExplicit", "1");       //总开关； 0 -> 不添加⽔印 1 -> 添加⽔印; 此开关是总开关，⽤于对显示时的⽔印与打印时的⽔印进⾏管理
        data.put("isShowExplicit","1"); //是否显示 0 -> 不显示； 1 -> 显示；
        data.put("isPrintExplicit", "1");   //是否打印 0 -> 不打印； 1 -> 打印；
        //⽔印位置,依次为左上、左中、左下、中上、居中、中下、右上、右中、右下;由0和1表示,0为当前位置不添加⽔印,该字段为⻓度为9的字符串
        data.put("position", "000111000"); //例如：000111000 表示中上、居中、中下添加⽔印
        data.put("rotation", watermarkCfg.get("markRotateAngel"));  //旋转⻆度,取值为0-360间的整数
        data.put("diaphaneity", watermarkCfg.get("markOpacity"));  //透明度,取值为0-1间的⼩数
        return this;
    }

    /**
     * 文本水印
     * @param request
     * @return
     */
    public  FoxitWaterInfo getWordWater(HttpServletRequest request, String userId) {
        String isText = "0";
        if(isWordWater()) {
            isText = "1";
            JSONObject json = new JSONObject();
            json.put("userId", userId);
            String content = SysAttViewerUtil.getVarMarkWord(
                    watermarkCfg.getString("markWordVar"), request, json);

            data.put("content", content);  //⽂本⽔印内容 此处内容会显示在⻚⾯上
            data.put("fontStyle", watermarkCfg.get("markWordFontFamily")); //⽂本⽔印字体，默认宋体
            data.put("fontSize", watermarkCfg.get("markWordFontSize")); //⽔印字号 例如：48 单位：pt
            data.put("fontColor", watermarkCfg.get("markWordFontColor")); //⽔印颜⾊，取值为颜⾊16进制码
        }

        data.put("isText", isText);  //是否为⽂本⽔印 0 -> ⾮； 1 -> 是；

        return this;
    }

    /**
     * 图片水印
     * @return
     */
    public FoxitWaterInfo getPicWater(HttpServletRequest request) {
        String isImage = "0";
        if(isPicWater()) {
            isImage = "1";
            data.put("image", SysAttUtil.imageToBase64(request));  //当isImage为1时，此处为PNG图⽚Base64字符串;否则⽆意义；
            data.put("scale", "1.5");     //缩放⽐例,取值为0-5间的浮点型值,例如：1.5
        }

        data.put("isImage", isImage);  //是否包含图⽚⽔印，0 -> 不包含；1 -> 包含(包含的情况下需要将图⽚Base64字符串写⼊image字段)

        return this;
    }

    /**
     * 是否显示文字水印
     */
    private Boolean isWordWater() {

        return watermarkCfg.get("markWordVar") != null
                && "true".equals(watermarkCfg.get("showWaterMark"))
                && "word".equals(watermarkCfg.get("markType"));
    }

    /**
     * 是否显示图片水印
     */
    private Boolean isPicWater() {
        return  watermarkCfg.get("markWordVar") != null
                && "true".equals(watermarkCfg.get("showWaterMark"))
                && "pic".equals(watermarkCfg.get("markType"));
    }

    public Map<String, Object> getData() {
        return data;
    }
}
