package com.landray.kmss.sys.attachment.integrate.dianju;

import com.landray.kmss.util.StringUtil;

/**
 * 点聚工具类
 */
public class DianjuUtil {

    /**
     * 获取后缀名
     * @param fileName 文件名
     * @return
     */
    public static String getFileSuffix(String fileName) {
        String suffix = "";
        if (StringUtil.isNotNull(fileName)) {
            suffix = fileName.substring(fileName.lastIndexOf("."),
                    fileName.length());
        }

        return suffix;
    }

    /**
     * 获取文件名
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName) {
        String name = "";
        if(StringUtil.isNotNull(fileName)) {
            name = fileName.substring(0, fileName.lastIndexOf("."));
        }

        return name;
    }
}
