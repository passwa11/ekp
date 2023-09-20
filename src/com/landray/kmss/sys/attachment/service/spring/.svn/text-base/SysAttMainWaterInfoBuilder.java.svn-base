package com.landray.kmss.sys.attachment.service.spring;

import com.landray.kmss.sys.attachment.util.SysAttViewerUtil;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 水印构造器
 */
public class SysAttMainWaterInfoBuilder {
    private static final Logger logger = LoggerFactory.getLogger(SysAttMainWaterInfoBuilder.class);

    JSONObject waterMarkInfo = new JSONObject();

    /**
     * 基础信息
     *
     * @param watermarkCfg
     * @return
     */
    public SysAttMainWaterInfoBuilder addBaseData(net.sf.json.JSONObject watermarkCfg) {
        waterMarkInfo.put("showWaterMark", watermarkCfg.get("showWaterMark")); // 是否水印
        waterMarkInfo.put("markType", watermarkCfg.get("markType")); // 水印类型 ：pic(图片)  word(文本)

        return this;
    }

    /**
     * 显示文字水印
     * @param request
     * @param watermarkCfg
     * @param wordWater
     * @return
     */
    public SysAttMainWaterInfoBuilder addWordWaterData(HttpServletRequest request,
                                                       net.sf.json.JSONObject watermarkCfg,Boolean wordWater) {
        if (wordWater) {
            String  waterText = SysAttViewerUtil.getVarMarkWord(
                    watermarkCfg.getString("markWordVar"), request);
            waterMarkInfo.put("waterText", waterText); // 水印信息
            waterMarkInfo.put("markWordFontFamily", watermarkCfg.get("markWordFontFamily")); // 字体
            waterMarkInfo.put("markWordFontColor", watermarkCfg.get("markWordFontColor"));// 字体颜色
            waterMarkInfo.put("markWordFontSize", watermarkCfg.get("markWordFontSize"));// 字体大小
            waterMarkInfo.put("markOpacity", watermarkCfg.get("markOpacity"));// 透明度
        }

        return this;
    }

    /**
     * 显示图片水印
     * @param request
     * @param watermarkCfg
     * @param pictureWater
     * @return
     */
    public SysAttMainWaterInfoBuilder addPictureWaterData(HttpServletRequest request,
                                                          net.sf.json.JSONObject watermarkCfg,Boolean pictureWater) {
        if (pictureWater) {
            waterMarkInfo.put("picUrl", request.getContextPath()       // 图片
                    + "/sys/attachment/sys_att_watermark/sysAttWaterMark.do?method=getWaterMarkPNG");
            waterMarkInfo.put("markRotateAngel", watermarkCfg.get("markRotateAngel"));// 图片旋转度
            waterMarkInfo.put("markOpacity", watermarkCfg.get("markOpacity"));// 透明度
        }
        return this;
    }

    /**
     * 返回信息
     * @return
     */
    public JSONObject build() {
        if (logger.isDebugEnabled()) {
            logger.debug("水印信息：{}", waterMarkInfo.toString().replaceAll("\"", "'"));
        }
        return waterMarkInfo;
    }
}
