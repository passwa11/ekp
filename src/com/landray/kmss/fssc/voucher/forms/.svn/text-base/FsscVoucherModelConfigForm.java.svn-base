package com.landray.kmss.fssc.voucher.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.voucher.model.FsscVoucherModelConfig;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 凭证规则模块配置
  */
public class FsscVoucherModelConfigForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdModelName;

    private String fdName;

    private String fdCategoryName;

    private String fdCategoryPropertyName;
    
    private String fdPath;	//模块路径

    private String docCreateTime;

    private String docAlterTime;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdModelName = null;
        fdName = null;
        fdCategoryName = null;
        fdCategoryPropertyName = null;
        fdPath = null;
        docCreateTime = null;
        docAlterTime = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscVoucherModelConfig> getModelClass() {
        return FsscVoucherModelConfig.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
        }
        return toModelPropertyMap;
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
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(String docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 修改人
     */
    public String getDocAlterorId() {
        return this.docAlterorId;
    }

    /**
     * 修改人
     */
    public void setDocAlterorId(String docAlterorId) {
        this.docAlterorId = docAlterorId;
    }

    /**
     * 修改人
     */
    public String getDocAlterorName() {
        return this.docAlterorName;
    }

    /**
     * 修改人
     */
    public void setDocAlterorName(String docAlterorName) {
        this.docAlterorName = docAlterorName;
    }
}
