package com.landray.kmss.third.pda.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaTabViewConfigMain;
import com.landray.kmss.util.AutoArrayList;

public class PdaTabViewConfigMainForm extends ExtendForm {

	/**
	 * 功能区名称
	 */
	protected String fdName = null;

	/**
	 * @return 功能区名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            功能区名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 排序号
	 */
	protected String fdOrder = null;

	/**
	 * @return 排序号
	 */
	public String getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 模块说明
	 */
	protected String fdDescription;

	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	/**
	 * 创建时间
	 */
	protected String fdCreateTime = null;

	/**
	 * @return 创建时间
	 */
	public String getFdCreateTime() {
		return fdCreateTime;
	}

	/**
	 * @param fdCreateTime
	 *            创建时间
	 */
	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/**
	 * 修改时间
	 */
	protected String docAlterTime = null;

	/**
	 * @return 修改时间
	 */
	public String getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            修改时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 状态
	 */
	protected String fdStatus = null;

	/**
	 * @return 状态
	 */
	public String getFdStatus() {
		return fdStatus;
	}

	/**
	 * @param fdStatus
	 *            状态
	 */
	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	/**
	 * 创建人的ID
	 */
	protected String docCreatorId = null;

	/**
	 * @return 创建人的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            创建人的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建人的名称
	 */
	protected String docCreatorName = null;

	/**
	 * @return 创建人的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}

	/**
	 * @param docCreatorName
	 *            创建人的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * 修改人的ID
	 */
	protected String docAlterorId = null;

	/**
	 * @return 修改人的ID
	 */
	public String getDocAlterorId() {
		return docAlterorId;
	}

	/**
	 * @param docAlterorId
	 *            修改人的ID
	 */
	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	/**
	 * 修改人的名称
	 */
	protected String docAlterorName = null;

	/**
	 * @return 修改人的名称
	 */
	public String getDocAlterorName() {
		return docAlterorName;
	}

	/**
	 * @param docAlterorName
	 *            修改人的名称
	 */
	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	/**
	 * 所属模块名称
	 */
	public String fdModuleName = null;

	public String getFdModuleName() {
		return fdModuleName;
	}

	public void setFdModuleName(String fdModuleName) {
		this.fdModuleName = fdModuleName;
	}

	/**
	 * 所属模块id
	 */
	public String fdModuleId = null;

	public String getFdModuleId() {
		return fdModuleId;
	}

	public void setFdModuleId(String fdModuleId) {
		this.fdModuleId = fdModuleId;
	}

	/**
	 * 标签列表
	 */
	private AutoArrayList fdLabelList = new AutoArrayList(
			PdaTabViewLabelListForm.class);

	public AutoArrayList getFdLabelList() {
		return fdLabelList;
	}

	public void setFdLabelList(AutoArrayList fdLabelList) {
		this.fdLabelList = fdLabelList;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		fdCreateTime = null;
		docAlterTime = null;
		fdStatus = "1";
		docCreatorId = null;
		docCreatorName = null;
		docAlterorId = null;
		docAlterorName = null;
		fdModuleName = null;
		fdModuleId = null;
		fdLabelList = new AutoArrayList(PdaTabViewLabelListForm.class);
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return PdaTabViewConfigMain.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));
			toModelPropertyMap.put("docAlterorId", new FormConvertor_IDToModel(
					"docAlteror", SysOrgElement.class));
			toModelPropertyMap.put("fdModuleId", new FormConvertor_IDToModel(
					"fdModule", PdaModuleConfigMain.class));
			toModelPropertyMap.put("fdLabelList",
					new FormConvertor_FormListToModelList("fdLabelList",
							"fdPdaTabViewConfigMain"));
		}
		return toModelPropertyMap;
	}
}
