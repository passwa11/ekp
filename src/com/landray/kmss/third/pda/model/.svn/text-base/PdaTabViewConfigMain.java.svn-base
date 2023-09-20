package com.landray.kmss.third.pda.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.pda.forms.PdaTabViewConfigMainForm;

public class PdaTabViewConfigMain extends BaseModel {

	/**
	 * 功能区名称
	 */
	protected String fdName;

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
	protected Integer fdOrder;

	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 创建时间
	 */
	protected Date fdCreateTime;

	/**
	 * @return 创建时间
	 */
	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	/**
	 * @param fdCreateTime
	 *            创建时间
	 */
	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/**
	 * 修改时间
	 */
	protected Date docAlterTime;

	/**
	 * @return 修改时间
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 状态
	 */
	protected String fdStatus;

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
	 * 创建人
	 */
	protected SysOrgElement docCreator;

	/**
	 * @return 创建人
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	/**
	 * @param docCreator
	 *            创建人
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	/**
	 * 修改人
	 */
	protected SysOrgElement docAlteror;

	/**
	 * @return 修改人
	 */
	public SysOrgElement getDocAlteror() {
		return docAlteror;
	}

	/**
	 * @param docAlteror
	 *            修改人
	 */
	public void setDocAlteror(SysOrgElement docAlteror) {
		this.docAlteror = docAlteror;
	}

	/**
	 * 所属模块id
	 */
	public PdaModuleConfigMain fdModule;

	public PdaModuleConfigMain getFdModule() {
		return fdModule;
	}

	public void setFdModule(PdaModuleConfigMain fdModule) {
		this.fdModule = fdModule;
	}

	/**
	 * 标签列表
	 */
	protected List<PdaTabViewLabelList> fdLabelList = new ArrayList<PdaTabViewLabelList>();

	public List<PdaTabViewLabelList> getFdLabelList() {
		return fdLabelList;
	}

	public void setFdLabelList(List<PdaTabViewLabelList> fdLabelList) {
		this.fdLabelList = fdLabelList;
	}

	@Override
    public Class getFormClass() {
		return PdaTabViewConfigMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			toFormPropertyMap.put("fdModule.fdId", "fdModuleId");
			toFormPropertyMap.put("fdModule.fdName", "fdModuleName");
			toFormPropertyMap.put("fdLabelList",
					new ModelConvertor_ModelListToFormList("fdLabelList"));
		}
		return toFormPropertyMap;
	}
}
