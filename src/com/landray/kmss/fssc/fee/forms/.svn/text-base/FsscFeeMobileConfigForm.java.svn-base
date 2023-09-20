package com.landray.kmss.fssc.fee.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.fee.model.FsscFeeMobileConfig;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 移动端映射
  */
public class FsscFeeMobileConfigForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private String fdLeftShow;
    
    private String fdLeftShowId;
    
    private String fdRightShow;
    
    private String fdRightShowId;
    
    private String fdIsMulti;
    
    private String fdOrgType;
    
    private String fdOrder;

    private String fdFieldName;

    private String fdFieldType;

    private String fdFieldPosition;

    private String fdBelongTable;

    private String fdBelongTableId;

    private String fdTemplateField;

    private String fdTemplateFieldId;

    private String fdIsRequired;
    
    private String fdValidate;

    private String fdBaseOn;

    private String fdBaseOnId;

    private String fdDataService;
    
    private String fdShowStatus;
    
    private String fdInitOption;

    private String docMainId;

    private String docMainName;

    private String docIndex;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdLeftShow = null;
        fdLeftShowId = null;
        fdRightShow = null;
        fdRightShowId = null;
    	fdIsMulti = null;
    	fdOrgType = null;
    	fdOrder = null;
    	fdShowStatus = null;
    	fdInitOption = null;
        fdFieldName = null;
        fdFieldType = null;
        fdFieldPosition = null;
        fdBelongTable = null;
        fdBelongTableId = null;
        fdTemplateField = null;
        fdTemplateFieldId = null;
        fdIsRequired = null;
        fdValidate=null;
        fdBaseOn = null;
        fdBaseOnId = null;
        fdDataService = null;
        docIndex = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscFeeMobileConfig> getModelClass() {
        return FsscFeeMobileConfig.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", FsscFeeTemplate.class));
        }
        return toModelPropertyMap;
    }

    public String getFdLeftShow() {
		return fdLeftShow;
	}

	public void setFdLeftShow(String fdLeftShow) {
		this.fdLeftShow = fdLeftShow;
	}

	public String getFdLeftShowId() {
		return fdLeftShowId;
	}

	public void setFdLeftShowId(String fdLeftShowId) {
		this.fdLeftShowId = fdLeftShowId;
	}

	public String getFdRightShow() {
		return fdRightShow;
	}

	public void setFdRightShow(String fdRightShow) {
		this.fdRightShow = fdRightShow;
	}

	public String getFdRightShowId() {
		return fdRightShowId;
	}

	public void setFdRightShowId(String fdRightShowId) {
		this.fdRightShowId = fdRightShowId;
	}

    public String getFdIsMulti() {
		return fdIsMulti;
	}

	public void setFdIsMulti(String fdIsMulti) {
		this.fdIsMulti = fdIsMulti;
	}

	public String getFdOrgType() {
		return fdOrgType;
	}

	public void setFdOrgType(String fdOrgType) {
		this.fdOrgType = fdOrgType;
	}

	/**
     * 字段名称
     */
    public String getFdFieldName() {
        return this.fdFieldName;
    }

    /**
     * 字段名称
     */
    public void setFdFieldName(String fdFieldName) {
        this.fdFieldName = fdFieldName;
    }

    /**
     * 字段类型
     */
    public String getFdFieldType() {
        return this.fdFieldType;
    }

    /**
     * 字段类型
     */
    public void setFdFieldType(String fdFieldType) {
        this.fdFieldType = fdFieldType;
    }

    /**
     * 显示位置
     */
    public String getFdFieldPosition() {
        return this.fdFieldPosition;
    }

    /**
     * 显示位置
     */
    public void setFdFieldPosition(String fdFieldPosition) {
        this.fdFieldPosition = fdFieldPosition;
    }

    /**
     * 所属明细
     */
    public String getFdBelongTable() {
        return this.fdBelongTable;
    }

    /**
     * 所属明细
     */
    public void setFdBelongTable(String fdBelongTable) {
        this.fdBelongTable = fdBelongTable;
    }

    /**
     * 所属明细ID
     */
    public String getFdBelongTableId() {
        return this.fdBelongTableId;
    }

    /**
     * 所属明细ID
     */
    public void setFdBelongTableId(String fdBelongTableId) {
        this.fdBelongTableId = fdBelongTableId;
    }

    /**
     * 对应表单字段
     */
    public String getFdTemplateField() {
        return this.fdTemplateField;
    }

    /**
     * 对应表单字段
     */
    public void setFdTemplateField(String fdTemplateField) {
        this.fdTemplateField = fdTemplateField;
    }

    /**
     * 对应表单字段ID
     */
    public String getFdTemplateFieldId() {
        return this.fdTemplateFieldId;
    }

    /**
     * 对应表单字段ID
     */
    public void setFdTemplateFieldId(String fdTemplateFieldId) {
        this.fdTemplateFieldId = fdTemplateFieldId;
    }

    /**
     * 是否必填
     */
    public String getFdIsRequired() {
        return this.fdIsRequired;
    }

    /**
     * 是否必填
     */
    public void setFdIsRequired(String fdIsRequired) {
        this.fdIsRequired = fdIsRequired;
    }
    
    public String getFdValidate() {
		return fdValidate;
	}

	public void setFdValidate(String fdValidate) {
		this.fdValidate = fdValidate;
	}

    /**
     * 前提字段
     */
    public String getFdBaseOn() {
        return this.fdBaseOn;
    }

    /**
     * 前提字段
     */
    public void setFdBaseOn(String fdBaseOn) {
        this.fdBaseOn = fdBaseOn;
    }

    /**
     * 前提字段ID
     */
    public String getFdBaseOnId() {
        return this.fdBaseOnId;
    }

    /**
     * 前提字段ID
     */
    public void setFdBaseOnId(String fdBaseOnId) {
        this.fdBaseOnId = fdBaseOnId;
    }

    /**
     * 数据接口
     */
    public String getFdDataService() {
        return this.fdDataService;
    }

    /**
     * 数据接口
     */
    public void setFdDataService(String fdDataService) {
        this.fdDataService = fdDataService;
    }

    public String getDocMainId() {
        return this.docMainId;
    }

    public void setDocMainId(String docMainId) {
        this.docMainId = docMainId;
    }

    public String getDocMainName() {
        return this.docMainName;
    }

    public void setDocMainName(String docMainName) {
        this.docMainName = docMainName;
    }

    public String getDocIndex() {
        return this.docIndex;
    }

    public void setDocIndex(String docIndex) {
        this.docIndex = docIndex;
    }

	public String getFdShowStatus() {
		return fdShowStatus;
	}

	public void setFdShowStatus(String fdShowStatus) {
		this.fdShowStatus = fdShowStatus;
	}

	public String getFdInitOption() {
		return fdInitOption;
	}

	public void setFdInitOption(String fdInitOption) {
		this.fdInitOption = fdInitOption;
	}

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}
}
