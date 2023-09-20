package com.landray.kmss.sys.filestore.scheduler.third.dianju.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数构造
 */
public class ConvertRequestParameterBuilder {
   private static final Logger logger = LoggerFactory.getLogger(ConvertRequestParameterBuilder.class);

   private Map<String, Object> callConvertRequest = new HashMap<>();

    /**
     * 基础数据
     * @param sysId
     * @param serailNumber
     * @return
     */
    public ConvertRequestParameterBuilder createBaseData(String sysId, String serailNumber) {
        Map<String, String> baseData = new HashMap<String, String>();
        baseData.put("SYS_ID", sysId); // 应用系统编号 (需要从配置文件中读取)
        baseData.put("SERIAL_NUMBER", serailNumber); // 请求流水号
        baseData.put("VERSION", "0"); // 0：标准版本
        callConvertRequest.put("BASE_DATA",baseData);
        return this;
    }

    /**
     * 元数据
     * @return
     */
    public ConvertRequestParameterBuilder createMetaData() {
        Map<String, String> metaData = new HashMap<String, String>();
        metaData.put("IS_ASYN","true");
        callConvertRequest.put("META_DATA", metaData);
        return this;
    }

    /**
     * 水印数据
     * @return
     */
    public ConvertRequestParameterBuilder createWaterMarkData() {
        Map<String, String> waterMarkData = new HashMap<String, String>();
        callConvertRequest.put("WATERMARK", waterMarkData);
        return this;
    }

    /**
     * 文档信息
     * @param fileName
     * @param fileType
     * @param convertType
     * @param filePath
     * @return
     */
    public ConvertRequestParameterBuilder createFileData(String fileName, String fileType,
                                                         String convertType, String filePath) {
        // 文档列表
        List<Map<String, String>> fileListData = new ArrayList<Map<String, String>>();
        Map<String, String> fileInfoData = new HashMap<String, String>();
        fileInfoData.put("FILE_NO", fileName); // 文档名称
        fileInfoData.put("FILE_ATTRIBUTE", "0"); // 附件标记，0主件，1附件
        fileInfoData.put("FILE_INDEX", "1"); // 文件索引，文件可以包含多个附件，附件索引越低，合并排序越靠前
        fileInfoData.put("FILE_TYPE", fileType); // 附件标记，0主件，1附件
        fileInfoData.put("CONVERT_TYPE", convertType); // 文档转换类型pdf,ofd,图片类型
        fileInfoData.put("RESPONSE_TYPE", "1"); // 件下载方式 1.http, 2.ftp, 3.服务器本地读取
        fileInfoData.put("FILE_PATH", filePath); // 文档http下载路径
        fileListData.add(fileInfoData);
        callConvertRequest.put("FILE_LIST",fileListData);
        return this;
    }

    public ConvertRequestParameterBuilder createSearchInfo(String serialNumber) {
        callConvertRequest.put("SERIAL_NUMBER",serialNumber);
        return this;
    }
    public  Map<String, Object> create() {
        if(logger.isDebugEnabled()) {
            logger.debug("构造请求的参数信息：{}", callConvertRequest.toString());
        }
        return callConvertRequest;
    }

}
