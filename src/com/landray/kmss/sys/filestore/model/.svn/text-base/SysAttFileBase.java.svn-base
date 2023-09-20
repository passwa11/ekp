package com.landray.kmss.sys.filestore.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;

import java.util.Date;

public class SysAttFileBase extends BaseModel {
    /**
     * 文件MD5码
     */
    protected String fdMd5;

    /**
     * @return 文件MD5码
     */
    public String getFdMd5() {
        return fdMd5;
    }

    /**
     * @param fdMd5 文件MD5码
     */
    public void setFdMd5(String fdMd5) {
        this.fdMd5 = fdMd5;
    }

    /**
     * 文件大小
     */
    protected Long fdFileSize;

    /**
     * @return 文件大小
     */
    public Long getFdFileSize() {
        return fdFileSize;
    }

    /**
     * @param fdFileSize 文件大小
     */
    public void setFdFileSize(Long fdFileSize) {
        this.fdFileSize = fdFileSize;
    }

    /**
     * 文件存储地址
     */
    protected String fdFilePath;

    /**
     * @return 文件存储地址
     */
    public String getFdFilePath() {
        return fdFilePath;
    }

    /**
     * @param fdFilePath 文件存储地址
     */
    public void setFdFilePath(String fdFilePath) {
        this.fdFilePath = fdFilePath;
    }

    /**
     * 状态
     */
    protected Integer fdStatus;

    /**
     * @return 状态
     */
    public Integer getFdStatus() {
        return fdStatus;
    }

    /**
     * @param fdStatus 状态
     */
    public void setFdStatus(Integer fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 创建时间
     */
    protected Date docCreateTime;

    /**
     * @return 创建时间
     */
    public Date getDocCreateTime() {
        return docCreateTime;
    }

    /**
     * @param docCreateTime 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 存储目录id
     */
    protected SysAttCatalog fdCata;

    /**
     * @return 存储目录id
     */
    public SysAttCatalog getFdCata() {
        return fdCata;
    }

    /**
     * @param fdCata 存储目录id
     */
    public void setFdCata(SysAttCatalog fdCata) {
        this.fdCata = fdCata;
    }

    /**
     * 存储位置
     */
    protected String fdAttLocation;

    /**
     * @param fdAttLocation 存储位置
     */
    public String getFdAttLocation() {
        return fdAttLocation;
    }

    /**
     * @param fdAttLocation 存储位置
     */
    public void setFdAttLocation(String fdAttLocation) {
        this.fdAttLocation = fdAttLocation;
    }

    @Override
    public Class getFormClass() {
        return this.getClass();
    }

    private static ModelToFormPropertyMap toFormPropertyMap;

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdCata.fdId", "fdCataId");
            toFormPropertyMap.put("fdCata.fdName", "fdCataName");
        }
        return toFormPropertyMap;
    }
}
