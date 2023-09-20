package com.landray.kmss.sys.time.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 节假日设置 Form
 * 
 * @author
 * @version 1.0 2017-09-26
 */
public class SysTimeHolidayForm extends ExtendForm implements ISysAuthAreaForm {

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

	// 机制开始
	// 机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		docCreateTime = null;
		docCreatorId = null;
		docCreatorName = null;
		authAreaId = null;
		authAreaName = null;
		fdHolidayDetailList = new AutoArrayList(SysTimeHolidayDetailForm.class);
		super.reset(mapping, request);
	}

	@Override
    public Class<SysTimeHoliday> getModelClass() {
		return SysTimeHoliday.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));
			toModelPropertyMap.put("authAreaId", new FormConvertor_IDToModel(
					"authArea", SysAuthArea.class));
			toModelPropertyMap.put("fdHolidayDetailList",
					new FormConvertor_FormListToModelList("fdHolidayDetailList",
							"fdHolidayDetailList"));
		}
		return toModelPropertyMap;
	}

	private List fdHolidayDetailList = new AutoArrayList(
			SysTimeHolidayDetailForm.class);

	public List getFdHolidayDetailList() {
		return fdHolidayDetailList;
	}

	public void setFdHolidayDetailList(List fdHolidayDetailList) {
		this.fdHolidayDetailList = fdHolidayDetailList;
	}

	// 所属场所ID
	protected String authAreaId = null;

	@Override
    public String getAuthAreaId() {
		return authAreaId;
	}

	@Override
    public void setAuthAreaId(String authAreaId) {
		this.authAreaId = authAreaId;
	}

	// 所属场所名称
	protected String authAreaName = null;

	@Override
    public String getAuthAreaName() {
		return authAreaName;
	}

	@Override
    public void setAuthAreaName(String authAreaName) {
		this.authAreaName = authAreaName;
	}

}
