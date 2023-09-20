package com.landray.kmss.third.welink.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.welink.model.ThirdWelinkDeptMapping;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 部门映射
  */
public class ThirdWelinkDeptMappingForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdWelinkId;

    private String fdWelinkName;

    private String docAlterTime;

	private String fdEkpDeptId;

	private String fdEkpDeptName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdWelinkId = null;
        fdWelinkName = null;
        docAlterTime = null;
		setFdEkpDeptId(null);
		setFdEkpDeptName(null);
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWelinkDeptMapping> getModelClass() {
        return ThirdWelinkDeptMapping.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
			// toModelPropertyMap.addNoConvertProperty("fdWelinkId");
			// toModelPropertyMap.addNoConvertProperty("fdWelinkName");
			// toModelPropertyMap.addNoConvertProperty("docAlterTime");
			toModelPropertyMap.put("fdEkpDeptId",
					new FormConvertor_IDToModel("fdEkpDept",
							SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * welink部门ID
     */
    public String getFdWelinkId() {
        return this.fdWelinkId;
    }

    /**
     * welink部门ID
     */
    public void setFdWelinkId(String fdWelinkId) {
        this.fdWelinkId = fdWelinkId;
    }

    /**
     * welink部门名称
     */
    public String getFdWelinkName() {
        return this.fdWelinkName;
    }

    /**
     * welink部门名称
     */
    public void setFdWelinkName(String fdWelinkName) {
        this.fdWelinkName = fdWelinkName;
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

	public String getFdEkpDeptId() {
		return fdEkpDeptId;
	}

	public void setFdEkpDeptId(String fdEkpDeptId) {
		this.fdEkpDeptId = fdEkpDeptId;
	}

	public String getFdEkpDeptName() {
		return fdEkpDeptName;
	}

	public void setFdEkpDeptName(String fdEkpDeptName) {
		this.fdEkpDeptName = fdEkpDeptName;
	}

	public String getFdWelinkDeptId() {
		return fdWelinkDeptId;
	}

	public void setFdWelinkDeptId(String fdWelinkDeptId) {
		this.fdWelinkDeptId = fdWelinkDeptId;
	}

	private String fdWelinkDeptId;


}
