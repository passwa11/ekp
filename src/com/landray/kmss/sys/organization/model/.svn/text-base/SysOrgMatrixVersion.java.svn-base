package com.landray.kmss.sys.organization.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixVersionForm;

/**
 * 组织矩阵版本
 *
 * @author 潘永辉 2020年2月24日
 */
public class SysOrgMatrixVersion extends BaseModel implements Comparable<SysOrgMatrixVersion> {
    private static final long serialVersionUID = 1L;

    /**
     * 版本名称
     */
    private String fdName;

    /**
     * 版本号
     */
    private Integer fdVersion;

    /**
     * 所属矩阵
     */
    private SysOrgMatrix fdMatrix;

    /**
     * 创建时间
     */
    private Date fdCreateTime = new Date();

    /**
     * 是否启用
     */
    private Boolean fdIsEnable;

    /**
     * 是否删除
     */
    private Boolean fdIsDelete;

    @Override
    public Class<?> getFormClass() {
        return SysOrgMatrixVersionForm.class;
    }

    private static ModelToFormPropertyMap toFormPropertyMap;

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.put("fdMatrix.fdId", "fdMatrixId");
            toFormPropertyMap.put("fdMatrix.fdName", "fdMatrixName");
        }
        return toFormPropertyMap;
    }

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public Integer getFdVersion() {
        return fdVersion;
    }

    public void setFdVersion(Integer fdVersion) {
        this.fdVersion = fdVersion;
    }

    public SysOrgMatrix getFdMatrix() {
        return getHbmMatrix();
    }

    public void setFdMatrix(SysOrgMatrix fdMatrix) {
        setHbmMatrix(fdMatrix);
    }

    public SysOrgMatrix getHbmMatrix() {
        return fdMatrix;
    }

    public void setHbmMatrix(SysOrgMatrix fdMatrix) {
        this.fdMatrix = fdMatrix;
    }

    public Date getFdCreateTime() {
        return fdCreateTime;
    }

    public void setFdCreateTime(Date fdCreateTime) {
        this.fdCreateTime = fdCreateTime;
    }

    public Boolean getFdIsEnable() {

        // 兼容历史数据，默认启用
        if (fdIsEnable == null) {
            fdIsEnable = true;
        }
        return fdIsEnable;
    }

    public void setFdIsEnable(Boolean fdIsEnable) {
        this.fdIsEnable = fdIsEnable;
    }

    public Boolean getFdIsDelete() {
        if (fdIsDelete == null) {
            fdIsDelete = false;
        }
        return fdIsDelete;
    }

    public void setFdIsDelete(Boolean fdIsDelete) {
        this.fdIsDelete = fdIsDelete;
    }

    @Override
    public int compareTo(SysOrgMatrixVersion o) {
        return this.fdVersion - o.getFdVersion();
    }
}
