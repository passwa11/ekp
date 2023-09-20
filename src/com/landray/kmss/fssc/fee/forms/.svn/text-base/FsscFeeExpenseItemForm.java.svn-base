package com.landray.kmss.fssc.fee.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.fssc.fee.model.FsscFeeExpenseItem;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
  * 申请类型设置
  */
public class FsscFeeExpenseItemForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private String fdCompanyId;
    
    private String fdCompanyName;
    
    private FormFile fdFile;

    private String fdIsNeedBudget;

    private String fdName;

    private String fdTemplateId;

    private String fdTemplateName;

    private String fdItemListIds;

    private String fdItemListNames;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdCompanyId = null;
    	fdCompanyName = null;
    	fdIsNeedBudget = null;
        fdName = null;
        fdTemplateId = null;
        fdTemplateName = null;
        fdItemListIds = null;
        fdItemListNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscFeeExpenseItem> getModelClass() {
        return FsscFeeExpenseItem.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdTemplateId", new FormConvertor_IDToModel("fdTemplate", FsscFeeTemplate.class));
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("fdItemListIds", new FormConvertor_IDsToModelList("fdItemList", EopBasedataExpenseItem.class));
        }
        return toModelPropertyMap;
    }


    public String getFdCompanyId() {
		return fdCompanyId;
	}

	public void setFdCompanyId(String fdCompanyId) {
		this.fdCompanyId = fdCompanyId;
	}

	public String getFdCompanyName() {
		return fdCompanyName;
	}

	public void setFdCompanyName(String fdCompanyName) {
		this.fdCompanyName = fdCompanyName;
	}

	public String getFdIsNeedBudget() {
		return fdIsNeedBudget;
	}

	public void setFdIsNeedBudget(String fdIsNeedBudget) {
		this.fdIsNeedBudget = fdIsNeedBudget;
	}

	/**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 申请模板
     */
    public String getFdTemplateId() {
        return this.fdTemplateId;
    }

    /**
     * 申请模板
     */
    public void setFdTemplateId(String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }

    /**
     * 申请模板
     */
    public String getFdTemplateName() {
        return this.fdTemplateName;
    }

    /**
     * 申请模板
     */
    public void setFdTemplateName(String fdTemplateName) {
        this.fdTemplateName = fdTemplateName;
    }

    /**
     * 对应费用类型
     */
    public String getFdItemListIds() {
        return this.fdItemListIds;
    }

    /**
     * 对应费用类型
     */
    public void setFdItemListIds(String fdItemListIds) {
        this.fdItemListIds = fdItemListIds;
    }

    /**
     * 对应费用类型
     */
    public String getFdItemListNames() {
        return this.fdItemListNames;
    }

    /**
     * 对应费用类型
     */
    public void setFdItemListNames(String fdItemListNames) {
        this.fdItemListNames = fdItemListNames;
    }

	public FormFile getFdFile() {
		return fdFile;
	}

	public void setFdFile(FormFile fdFile) {
		this.fdFile = fdFile;
	}
}
