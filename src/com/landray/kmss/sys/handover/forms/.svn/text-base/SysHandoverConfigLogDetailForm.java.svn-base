package com.landray.kmss.sys.handover.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLog;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLogDetail;

/**
 * 日志明细 Form
 * 
 * @author
 * @version 1.0 2014-07-22
 */
@SuppressWarnings("serial")
public class SysHandoverConfigLogDetailForm extends ExtendForm {

	/**
	 * 主文档ID
	 */
	protected String fdModelId = null;

	/**
	 * @return 主文档ID
	 */
	public String getFdModelId() {
		return fdModelId;
	}

	/**
	 * @param fdModelId
	 *            主文档ID
	 */
	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	/**
	 * 主文档名称
	 */
	protected String fdModelName = null;

	/**
	 * @return 主文档名称
	 */
	public String getFdModelName() {
		return fdModelName;
	}

	/**
	 * @param fdModelName
	 *            主文档名称
	 */
	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	/**
	 * 描述
	 */
	protected String fdDescription = null;

	/**
	 * @return 描述
	 */
	public String getFdDescription() {
		return fdDescription;
	}

	/**
	 * @param fdDescription
	 *            描述
	 */
	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	/**
	 * 日志的ID
	 */
	protected String fdLogId = null;

	/**
	 * @return 日志的ID
	 */
	public String getFdLogId() {
		return fdLogId;
	}

	/**
	 * @param fdLogId
	 *            日志的ID
	 */
	public void setFdLogId(String fdLogId) {
		this.fdLogId = fdLogId;
	}

	/**
	 * 日志的名称
	 */
	protected String fdLogName = null;

	/**
	 * @return 日志的名称
	 */
	public String getFdLogName() {
		return fdLogName;
	}

	/**
	 * @param fdLogName
	 *            日志的名称
	 */
	public void setFdLogName(String fdLogName) {
		this.fdLogName = fdLogName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdModelId = null;
		fdModelName = null;
		fdDescription = null;
		fdLogId = null;
		fdLogName = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<?> getModelClass() {
		return SysHandoverConfigLogDetail.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdLogId", new FormConvertor_IDToModel(
					"fdLog", SysHandoverConfigLog.class));
		}
		return toModelPropertyMap;
	}
}
