package com.landray.kmss.hr.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.organization.model.HrOrganizationGrade;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
  * 职级
  */
public class HrOrganizationRankForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

	private String fdName;

    private String docCreateTime;

    private String docAlterTime;

    private String fdGradeId;

    private String fdGradeName;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

	private Integer fdWeight;

	public Integer getFdWeight() {
		return fdWeight;
	}

	public void setFdWeight(Integer fdWeight) {
		this.fdWeight = fdWeight;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
        docCreateTime = null;
        docAlterTime = null;
        fdGradeId = null;
        fdGradeName = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<HrOrganizationRank> getModelClass() {
        return HrOrganizationRank.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdGradeId", new FormConvertor_IDToModel("fdGrade", HrOrganizationGrade.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 岗位职级
     */

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
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
     * 职等
     */
    public String getFdGradeId() {
        return this.fdGradeId;
    }

    /**
     * 职等
     */
    public void setFdGradeId(String fdGradeId) {
        this.fdGradeId = fdGradeId;
    }

    /**
     * 职等
     */
    public String getFdGradeName() {
        return this.fdGradeName;
    }

    /**
     * 职等
     */
    public void setFdGradeName(String fdGradeName) {
        this.fdGradeName = fdGradeName;
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

	/* 文件导入 */
	protected FormFile file = null;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
	/* 文件导入 */
}
