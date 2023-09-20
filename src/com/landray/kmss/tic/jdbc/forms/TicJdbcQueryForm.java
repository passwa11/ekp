package com.landray.kmss.tic.jdbc.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.tic.jdbc.model.TicJdbcDataSet;
import com.landray.kmss.tic.jdbc.model.TicJdbcQuery;
import com.landray.kmss.web.action.ActionMapping;

public class TicJdbcQueryForm extends ExtendForm {
	/**
	 * 函数查询标题
	 */
	protected String docSubject = null;

	/**
	 * @return 函数查询标题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            函数查询标题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 创建时间
	 */
	protected String docCreateTime = null;

	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 创建者的ID
	 */
	protected String docCreatorId = null;

	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建者的名称
	 */
	protected String docCreatorName = null;

	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}

	/**
	 * @param docCreatorName
	 *            创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * 所属函数的ID
	 */
	protected String ticJdbcDataSetId = null;

	/**
	 * 所属函数的名称
	 */
	protected String ticJdbcDataSetName = null;

	public String getTicJdbcDataSetId() {
		return ticJdbcDataSetId;
	}

	public void setTicJdbcDataSetId(String ticJdbcDataSetId) {
		this.ticJdbcDataSetId = ticJdbcDataSetId;
	}

	public String getTicJdbcDataSetName() {
		return ticJdbcDataSetName;
	}

	public void setTicJdbcDataSetName(String ticJdbcDataSetName) {
		this.ticJdbcDataSetName = ticJdbcDataSetName;
	}

	protected String fdJsonResult = null;

	public String getFdJsonResult() {
		return fdJsonResult;
	}

	public void setFdJsonResult(String fdJsonResult) {
		this.fdJsonResult = fdJsonResult;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docSubject = null;
		docCreateTime = null;
		docCreatorId = null;
		docCreatorName = null;
		ticJdbcDataSetId = null;
		ticJdbcDataSetName = null;

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return TicJdbcQuery.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
							SysOrgElement.class));
			toModelPropertyMap.put("ticJdbcDataSetId",
					new FormConvertor_IDToModel("ticJdbcDataSet",
							TicJdbcDataSet.class));
		}
		return toModelPropertyMap;
	}

	public String getDocSubjectShow() {
		if (docSubject != null) {
			return docSubject.replaceAll("\"", "&quot;");
		}
		return docSubject;
	}

}
