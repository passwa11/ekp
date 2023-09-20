package com.landray.kmss.sys.filestore.service.spring;

import com.landray.kmss.util.ResourceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 转换有扩展名构造器
 */
public class ExtendFileNameTreeBuilder {

    private List result = new ArrayList();

    private static class Singleton {
        private static ExtendFileNameTreeBuilder instance = new ExtendFileNameTreeBuilder();
    }

    public static ExtendFileNameTreeBuilder getInstance() {
        return Singleton.instance;
    }

    /**
     * Office系列
     *
     * @return
     */
    public ExtendFileNameTreeBuilder buildOffice() {
        HashMap office = new HashMap();
        office.put("text", ResourceUtil.getString("sysFilestore.conversion.office",
                "sys-filestore"));
        office.put("value", "office");
        office.put("isAutoFetch", "0");
        result.add(office);
        return this;
    }

    /**
     * WPS系列
     * @return
     */
    public ExtendFileNameTreeBuilder buildWPS() {
        HashMap wps = new HashMap();
        wps.put("text", ResourceUtil.getString("sysFilestore.conversion.wps",
                "sys-filestore"));
        wps.put("value", "wps");
        wps.put("isAutoFetch", "0");
        result.add(wps);
        return this;
    }

    /**
     * 压缩
     * @return
     */
    public ExtendFileNameTreeBuilder buildPress() {
        HashMap press = new HashMap();
        press.put("text", ResourceUtil.getString("sysFilestore.conversion.zip",
                "sys-filestore"));
        press.put("value", "press");
        press.put("isAutoFetch", "0");
        result.add(press);
        return this;
    }

    /**
     * 版式文档
     * @return
     */
    public ExtendFileNameTreeBuilder buildLayout() {
        HashMap layoutDocument = new HashMap();
        layoutDocument.put("text", ResourceUtil.getString("sysFilestore.conversion.layoutDocument",
                "sys-filestore"));
        layoutDocument.put("value", "layoutDocument");
        layoutDocument.put("isAutoFetch", "0");
        result.add(layoutDocument);
        return this;
    }

    /**
     * 文本
     * @return
     */
    public ExtendFileNameTreeBuilder buildTxt() {
        HashMap txt = new HashMap();
        txt.put("text", ResourceUtil.getString("sysFilestore.conversion.txt",
                "sys-filestore"));
        txt.put("value", "txt");
        txt.put("isAutoFetch", "0");
        result.add(txt);
        return this;
    }

    /**
     * 图纸
     * @return
     */
    public ExtendFileNameTreeBuilder buildPicPaper() {
        HashMap picturePaper = new HashMap();
        picturePaper.put("text", ResourceUtil.getString("sysFilestore.conversion.pic",
                "sys-filestore"));
        picturePaper.put("value", "picturePaper");
        picturePaper.put("isAutoFetch", "0");
        result.add(picturePaper);

        return this;
    }

    /**
     * 音频
     * @return
     */
    public ExtendFileNameTreeBuilder buildAudio() {
        HashMap audio = new HashMap();
        audio.put("text", ResourceUtil.getString("sysFilestore.conversion.audio",
                "sys-filestore"));
        audio.put("value", "audio");
        audio.put("isAutoFetch", "0");
        result.add(audio);

        return this;
    }

    /**
     * 视频
     * @return
     */
    public ExtendFileNameTreeBuilder buildVideo() {
        HashMap video = new HashMap();
        video.put("text", ResourceUtil.getString("sysFilestore.conversion.video",
                "sys-filestore"));
        video.put("value", "video");
        video.put("isAutoFetch", "0");
        result.add(video);

        return this;
    }

    /**
     * 其它
     * @return
     */
    public ExtendFileNameTreeBuilder buildOthers() {
        HashMap others = new HashMap();
        others.put("text", ResourceUtil.getString("sysFilestore.conver.server.others",
                "sys-filestore")); //其它
        others.put("value", "others");
        others.put("isAutoFetch", "0");
        result.add(others);

        return this;
    }

    /**
     * 返回结果
     * @return
     */
    public List list() {
        return result;
    }
}
