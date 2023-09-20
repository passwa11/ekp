package com.landray.kmss.fssc.voucher.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.fssc.voucher.forms.FsscVoucherModelConfigForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 凭证规则模块配置
  */
public class FsscVoucherModelConfig extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdModelName;

    private String fdName;

    private String fdCategoryName;

    private String fdCategoryPropertyName;
    
    private String fdPath;	//模块路径

    private Date docCreateTime;

    private Date docAlterTime;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    @Override
    public Class<FsscVoucherModelConfigForm> getFormClass() {
        return FsscVoucherModelConfigForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 模块modelNAme
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 模块modelNAme
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 模块名
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 模块名
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 对应分类或者模版的model
     */
    public String getFdCategoryName() {
        return this.fdCategoryName;
    }

    /**
     * 对应分类或者模版的model
     */
    public void setFdCategoryName(String fdCategoryName) {
        this.fdCategoryName = fdCategoryName;
    }
    /**
     * 对应分类或者模版的字段名
     */
    public String getFdCategoryPropertyName() {
        return fdCategoryPropertyName;
    }
    /**
     * 对应分类或者模版的字段名
     */
    public void setFdCategoryPropertyName(String fdCategoryPropertyName) {
        this.fdCategoryPropertyName = fdCategoryPropertyName;
    }
    
    /**
     * 模块路径
     * @return
     */
    public String getFdPath() {
        return fdPath;
    }
    
    /**
     * 模块路径
     * @param fdPath
     */
    public void setFdPath(String fdPath) {
        this.fdPath = fdPath;
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 创建人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 修改人
     */
    public SysOrgPerson getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * 修改人
     */
    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
    }
}
