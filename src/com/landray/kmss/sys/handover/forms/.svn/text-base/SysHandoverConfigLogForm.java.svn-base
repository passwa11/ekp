package com.landray.kmss.sys.handover.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLog;
import com.landray.kmss.sys.handover.model.SysHandoverConfigMain;

/**
 * 日志条目 Form
 * 
 * @author
 * @version 1.0 2014-07-22
 */
@SuppressWarnings("serial")
public class SysHandoverConfigLogForm extends ExtendForm {

	/**
	 * 模块_key
	 */
	protected String fdmodule = null;

	/**
	 * @return 模块_key
	 */
	public String getFdmodule() {
		return fdmodule;
	}

	/**
	 * @param fdmodule
	 *            模块_key
	 */
	public void setFdmodule(String fdmodule) {
		this.fdmodule = fdmodule;
	}

	/**
	 * 模块名称
	 */
	protected String fdModuleName = null;

	/**
	 * @return 模块名称
	 */
	public String getFdModuleName() {
		return fdModuleName;
	}

	/**
	 * @param fdModuleName
	 *            模块名称
	 */
	public void setFdModuleName(String fdModuleName) {
		this.fdModuleName = fdModuleName;
	}

	/**
	 * 交接项_key
	 */
	protected String fdItem = null;

	/**
	 * @return 交接项_key
	 */
	public String getFdItem() {
		return fdItem;
	}

	/**
	 * @param fdItem
	 *            交接项_key
	 */
	public void setFdItem(String fdItem) {
		this.fdItem = fdItem;
	}

	/**
	 * 交接项名称
	 */
	protected String fdItemName = null;

	/**
	 * @return 交接项名称
	 */
	public String getFdItemName() {
		return fdItemName;
	}

	/**
	 * @param fdItemName
	 *            交接项名称
	 */
	public void setFdItemName(String fdItemName) {
		this.fdItemName = fdItemName;
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
	 * 总数
	 */
	protected String fdCount = null;

	/**
	 * @return 总数
	 */
	public String getFdCount() {
		return fdCount;
	}

	/**
	 * @param fdCount
	 *            总数
	 */
	public void setFdCount(String fdCount) {
		this.fdCount = fdCount;
	}

	/**
	 * 是否成功
	 */
	protected String fdIsSucc = null;

	/**
	 * @return 是否成功
	 */
	public String getFdIsSucc() {
		return fdIsSucc;
	}

	/**
	 * @param fdIsSucc
	 *            是否成功
	 */
	public void setFdIsSucc(String fdIsSucc) {
		this.fdIsSucc = fdIsSucc;
	}

	/**
	 * 开始时间
	 */
	protected String fdStartTime = null;

	/**
	 * @return 开始时间
	 */
	public String getFdStartTime() {
		return fdStartTime;
	}

	/**
	 * @param fdStartTime
	 *            开始时间
	 */
	public void setFdStartTime(String fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	/**
	 * 结束时间
	 */
	protected String fdEndedTime = null;

	/**
	 * @return 结束时间
	 */
	public String getFdEndedTime() {
		return fdEndedTime;
	}

	/**
	 * @param fdEndedTime
	 *            结束时间
	 */
	public void setFdEndedTime(String fdEndedTime) {
		this.fdEndedTime = fdEndedTime;
	}

	/**
	 * 主日志的ID
	 */
	protected String fdMainId = null;

	/**
	 * @return 主日志的ID
	 */
	public String getFdMainId() {
		return fdMainId;
	}

	/**
	 * @param fdMainId
	 *            主日志的ID
	 */
	public void setFdMainId(String fdMainId) {
		this.fdMainId = fdMainId;
	}

	/**
	 * 主日志的名称
	 */
	protected String fdMainName = null;

	/**
	 * @return 主日志的名称
	 */
	public String getFdMainName() {
		return fdMainName;
	}

	/**
	 * @param fdMainName
	 *            主日志的名称
	 */
	public void setFdMainName(String fdMainName) {
		this.fdMainName = fdMainName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdmodule = null;
		fdModuleName = null;
		fdItem = null;
		fdItemName = null;
		fdStatus = null;
		fdCount = null;
		fdIsSucc = null;
		fdStartTime = null;
		fdEndedTime = null;
		fdMainId = null;
		fdMainName = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<?> getModelClass() {
		return SysHandoverConfigLog.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdMainId", new FormConvertor_IDToModel(
					"fdMain", SysHandoverConfigMain.class));
		}
		return toModelPropertyMap;
	}
}
