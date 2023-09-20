package com.landray.kmss.tic.rest.connector.forms;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.util.encoders.Base64;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;
import com.landray.kmss.tic.rest.connector.model.TicRestQuery;
import com.landray.kmss.web.action.ActionMapping;

public class TicRestQueryForm extends ExtendForm {

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

	public String getDocSubjectShow() {
		return docSubject.replaceAll("\"", "&quot;");
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
	protected String ticRestMainId = null;

	public String getTicRestMainId() {
		return ticRestMainId;
	}

	public void setTicRestMainId(String ticRestMainId) {
		this.ticRestMainId = ticRestMainId;
	}

	/**
	 * 所属函数的名称
	 */
	protected String ticRestMainName = null;

	public String getTicRestMainName() {
		return ticRestMainName;
	}

	public void setTicRestMainName(String ticRestMainName) {
		this.ticRestMainName = ticRestMainName;
	}

	/**
	 * 查询参数
	 */
	protected String fdQueryParam;

	public String getFdQueryParam() {
		return fdQueryParam;
	}

	public String getFdQueryParamBase64() {
		try {
			return new String(Base64.encode(fdQueryParam.getBytes("UTF-8")),
					"UTF-8");
		} catch (UnsupportedEncodingException e) {

		}
		return fdQueryParam;
	}

	public void setFdQueryParam(String fdQueryParam) {
		this.fdQueryParam = fdQueryParam;
	}

	/**
	 * 错误信息
	 */
	protected String docFaultInfo;

	public String getDocFaultInfo() {
		return docFaultInfo;
	}

	public void setDocFaultInfo(String docFaultInfo) {
		this.docFaultInfo = docFaultInfo;
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
		ticRestMainId = null;
		ticRestMainName = null;
		docFaultInfo = null;
		fdQueryParam = null;
		fdJsonResult = null;
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return TicRestQuery.class;
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
			toModelPropertyMap.put("ticRestMainId",
					new FormConvertor_IDToModel("ticRestMain",
							TicRestMain.class));
		}
		return toModelPropertyMap;
	}
}
