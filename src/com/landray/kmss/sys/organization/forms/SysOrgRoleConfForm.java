package com.landray.kmss.sys.organization.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.model.SysOrgRoleConfCate;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 陈亮
 */
public class SysOrgRoleConfForm extends ExtendForm {
	/*
	 * 名称
	 */
	private String fdName = null;
	
	
	/*
	 * 角色线类别
	 */
	private String fdRoleConfCateId;
	private String fdRoleConfCateName;
	

	public String getFdRoleConfCateId() {
		return fdRoleConfCateId;
	}

	public void setFdRoleConfCateId(String fdRoleConfCateId) {
		this.fdRoleConfCateId = fdRoleConfCateId;
	}

	public String getFdRoleConfCateName() {
		return fdRoleConfCateName;
	}

	public void setFdRoleConfCateName(String fdRoleConfCateName) {
		this.fdRoleConfCateName = fdRoleConfCateName;
	}


	/*
	 * 是否有效
	 */
	private String fdIsAvailable = "true";

	public String getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(String fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/*
	 * 排序号
	 */
	private String fdOrder = null;

	private String fdRoleLineEditorIds;

	private String fdRoleLineEditorNames;

	private List defaultRoleList = new AutoArrayList(
			SysOrgRoleLineDefaultRoleForm.class);

	public List getDefaultRoleList() {
		return defaultRoleList;
	}

	public void setDefaultRoleList(List defaultRoleList) {
		this.defaultRoleList = defaultRoleList;
	}

	public String getFdRoleLineEditorIds() {
		return fdRoleLineEditorIds;
	}

	public void setFdRoleLineEditorIds(String fdRoleLineEditorIds) {
		this.fdRoleLineEditorIds = fdRoleLineEditorIds;
	}

	public String getFdRoleLineEditorNames() {
		return fdRoleLineEditorNames;
	}

	public void setFdRoleLineEditorNames(String fdRoleLineEditorNames) {
		this.fdRoleLineEditorNames = fdRoleLineEditorNames;
	}

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * @return 返回 名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            要设置的 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdRoleConfCateId = null;
		fdRoleConfCateName = null;
		fdName = null;
		fdOrder = null;
		fdIsAvailable = "true";
		fdRoleLineReaderIds = null;
		fdRoleLineReaderNames = null;
		SysOrgPerson user = UserUtil.getUser();
		fdRoleLineEditorIds = user.getFdId();
		fdRoleLineEditorNames = user.getFdName();
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdRoleConfCateId",
					new FormConvertor_IDToModel("fdRoleConfCate",
							SysOrgRoleConfCate.class));
			toModelPropertyMap.put("fdRoleLineEditorIds",
					new FormConvertor_IDsToModelList("sysRoleLineEditors",
							SysOrgElement.class));
			toModelPropertyMap.put("defaultRoleList",
					new FormConvertor_FormListToModelList("defaultRoleList",
							"sysOrgRoleConf"));
			toModelPropertyMap.put("fdRoleLineReaderIds",
					new FormConvertor_IDsToModelList("sysRoleLineReaders",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	@Override
    public Class getModelClass() {
		return SysOrgRoleConf.class;
	}

	/*
	 * 可使用者ID
	 */
	protected String fdRoleLineReaderIds = null;
	
	public String getFdRoleLineReaderIds() {
		return fdRoleLineReaderIds;
	}

	public void setFdRoleLineReaderIds(String fdRoleLineReaderIds) {
		this.fdRoleLineReaderIds = fdRoleLineReaderIds;
	}

	/*
	 * 可使用者名称
	 */
	protected String fdRoleLineReaderNames = null;

	public String getFdRoleLineReaderNames() {
		return fdRoleLineReaderNames;
	}

	public void setFdRoleLineReaderNames(String fdRoleLineReaderNames) {
		this.fdRoleLineReaderNames = fdRoleLineReaderNames;
	}


}
