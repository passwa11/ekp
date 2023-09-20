package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgGroupCate;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.UserUtil;

/**
 * 群组
 * 
 * @author 叶中奇
 */
public class SysOrgGroupForm extends SysOrgElementForm {
	/*
	 * 群组类别
	 */
	private String fdGroupCateId;

	private String fdGroupCateName;

	public String getFdGroupCateId() {
		return fdGroupCateId;
	}

	public void setFdGroupCateId(String fdGroupCateId) {
		this.fdGroupCateId = fdGroupCateId;
	}

	public String getFdGroupCateName() {
		return fdGroupCateName;
	}

	public void setFdGroupCateName(String fdGroupCateName) {
		this.fdGroupCateName = fdGroupCateName;
	}

	/*
	 * 群组成员
	 */
	private String fdMemberIds;

	private String fdMemberNames;

	public String getFdMemberIds() {
		return fdMemberIds;
	}

	public void setFdMemberIds(String fdMemberIds) {
		this.fdMemberIds = fdMemberIds;
	}

	public String getFdMemberNames() {
		return fdMemberNames;
	}

	public void setFdMemberNames(String fdMemberNames) {
		this.fdMemberNames = fdMemberNames;
	}

	/*
	 * 可阅读者
	 */
	protected String authReaderIds = null;

	public String getAuthReaderIds() {
		return authReaderIds;
	}

	public void setAuthReaderIds(String authReaderIds) {
		this.authReaderIds = authReaderIds;
	}

	/*
	 * 可阅读者名称
	 */
	protected String authReaderNames = null;

	public String getAuthReaderNames() {
		return authReaderNames;
	}

	public void setAuthReaderNames(String authReaderNames) {
		this.authReaderNames = authReaderNames;
	}

	/*
	 * 可编辑者
	 */
	protected String authEditorIds = null;

	public String getAuthEditorIds() {
		return authEditorIds;
	}

	public void setAuthEditorIds(String authEditorIds) {
		this.authEditorIds = authEditorIds;
	}

	/*
	 * 可编辑者名称
	 */
	protected String authEditorNames = null;

	public String getAuthEditorNames() {
		return authEditorNames;
	}

	public void setAuthEditorNames(String authEditorNames) {
		this.authEditorNames = authEditorNames;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdGroupCateId = null;
		fdGroupCateName = null;
		fdMemberIds = null;
		fdMemberNames = null;
		authReaderIds = null;
		authReaderNames = null;
		SysOrgPerson user = UserUtil.getUser();
		authEditorIds = user.getFdId();
		authEditorNames = user.getFdName();
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysOrgGroup.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdGroupCateId",
					new FormConvertor_IDToModel("fdGroupCate",
							SysOrgGroupCate.class));
			toModelPropertyMap.put("fdMemberIds",
					new FormConvertor_IDsToModelList("fdMembers",
							SysOrgElement.class));
			toModelPropertyMap.put("authReaderIds",
					new FormConvertor_IDsToModelList("authReaders",
							SysOrgElement.class));
			toModelPropertyMap.put("authEditorIds",
					new FormConvertor_IDsToModelList("authEditors",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
