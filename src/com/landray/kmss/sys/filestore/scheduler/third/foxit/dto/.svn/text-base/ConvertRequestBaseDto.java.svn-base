package com.landray.kmss.sys.filestore.scheduler.third.foxit.dto;

import java.util.List;

/**
 * 转换请求参数
 */
public class ConvertRequestBaseDto extends BaseRequestDto{
    /**
     * 文件集
     */
    private List<OriginFileInfo> originFileInfos;


    /**
     * 文件信息
     */
    public static class OriginFileInfo {

        private String originFileName; // 文件名
        private String srcFileType;  // 文件类型

        public String getOriginFileName() {
            return originFileName;
        }

        public void setOriginFileName(String originFileName) {
            this.originFileName = originFileName;
        }

        public String getSrcFileType() {
            return srcFileType;
        }

        public void setSrcFileType(String srcFileType) {
            this.srcFileType = srcFileType;
        }
    }

    public List<OriginFileInfo> getOriginFileInfos() {
        return originFileInfos;
    }

    public void setOriginFileInfos(List<OriginFileInfo> originFileInfos) {
        this.originFileInfos = originFileInfos;
    }

}
