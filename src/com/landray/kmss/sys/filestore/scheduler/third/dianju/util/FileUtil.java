package com.landray.kmss.sys.filestore.scheduler.third.dianju.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件处理工具类
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取文件名
     * @param originName 源文件名
     * @return
     */
    public static String getFileName(String originName) {
        if(logger.isDebugEnabled()) {
            logger.debug("文件处理获取文件名的源文件名：{}" , originName);
        }

        return originName.substring(0, originName.lastIndexOf("."));
    }

    /**
     * 获取文件类型
     * @param fileName 源文件名
     * @return
     */
    public static String getFileType(String fileName) {
        if(logger.isDebugEnabled()) {
            logger.debug("文件处理获取文件的类型,源文件名：" + fileName);
        }

        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    public static String getFileConvertType(String fileConvertType) {
        if(logger.isDebugEnabled()) {
            logger.debug("文件处理获取转换类型原值：" + fileConvertType);
        }

        if ("toPDF".equals(fileConvertType)) {
            return "pdf";
        } else {
            return "ofd";
        }
    }
}
