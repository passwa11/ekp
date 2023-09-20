package com.landray.kmss.sys.attend.forms;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.attend.model.SysAttendReport;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.BaseAuthForm;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 月统计报表 Form
 * 
 * @author
 * @version 1.0 2017-07-27
 */
public class SysAttendReportForm extends BaseAuthForm {

	/**
	 * 月份
	 */
	private String fdMonth;

	/**
	 * @return 月份
	 */
	public String getFdMonth() {
		return this.fdMonth;
	}

	/**
	 * @param fdMonth
	 *            月份
	 */
	public void setFdMonth(String fdMonth) {
		this.fdMonth = fdMonth;
	}

	public String getFdYearPart() {
		if (StringUtil.isNull(this.fdMonth)) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.convertStringToDate(this.fdMonth,
				DateUtil.TYPE_DATETIME, null));
		return String.valueOf(cal.get(Calendar.YEAR));
	}

	public String getFdMonthPart() {
		if (StringUtil.isNull(this.fdMonth)) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.convertStringToDate(this.fdMonth,
				DateUtil.TYPE_DATETIME, null));
		return String.valueOf(cal.get(Calendar.MONTH) + 1);
	}

	/**
	 * 创建时间
	 */
	private String docCreateTime;

	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return this.docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 名称
	 */
	private String fdName;

	/**
	 * @return 名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 创建者的ID
	 */
	private String docCreatorId;

	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return this.docCreatorId;
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
	private String docCreatorName;

	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return this.docCreatorName;
	}

	/**
	 * @param docCreatorName
	 *            创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * 部门的ID列表
	 */
	private String fdDeptIds;

	/**
	 * @return 部门的ID列表
	 */
	public String getFdDeptIds() {
		return this.fdDeptIds;
	}

	/**
	 * @param fdDeptIds
	 *            部门的ID列表
	 */
	public void setFdDeptIds(String fdDeptIds) {
		this.fdDeptIds = fdDeptIds;
	}

	/**
	 * 部门的名称列表
	 */
	private String fdDeptNames;

	/**
	 * @return 部门的名称列表
	 */
	public String getFdDeptNames() {
		return this.fdDeptNames;
	}

	/**
	 * @param fdDeptNames
	 *            部门的名称列表
	 */
	public void setFdDeptNames(String fdDeptNames) {
		this.fdDeptNames = fdDeptNames;
	}

	private String fdIsQuit;

	public String getFdIsQuit() {
		return fdIsQuit;
	}

	public void setFdIsQuit(String fdIsQuit) {
		this.fdIsQuit = fdIsQuit;
	}

	private String fdCategoryIds;

	public String getFdCategoryIds() {
		return fdCategoryIds;
	}

	public void setFdCategoryIds(String fdCategoryIds) {
		this.fdCategoryIds = fdCategoryIds;
	}

	private String fdCategoryNames;

	public String getFdCategoryNames() {
		return fdCategoryNames;
	}

	public void setFdCategoryNames(String fdCategoryNames) {
		this.fdCategoryNames = fdCategoryNames;
	}

	private String fdTargetType;

	public String getFdTargetType() {
		return fdTargetType;
	}

	public void setFdTargetType(String fdTargetType) {
		this.fdTargetType = fdTargetType;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdMonth = null;
		docCreateTime = null;
		fdName = null;
		docCreatorId = null;
		docCreatorName = null;
		fdDeptIds = null;
		fdDeptNames = null;
		fdCategoryIds = null;
		fdCategoryNames = null;
		fdTargetType = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<SysAttendReport> getModelClass() {
		return SysAttendReport.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
							SysOrgPerson.class));
			toModelPropertyMap.put("fdDeptIds",
					new FormConvertor_IDsToModelList(
							"fdDepts", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
