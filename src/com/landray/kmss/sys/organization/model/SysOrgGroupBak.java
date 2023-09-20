package com.landray.kmss.sys.organization.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.organization.forms.SysOrgGroupForm;

/**
 * 群组
 * 
 * @author 叶中奇
 */
public class SysOrgGroupBak extends SysOrgElementBak implements IBaseModel {
	public SysOrgGroupBak() {
		super();
		setFdOrgType(new Integer(ORG_TYPE_GROUP));
	}

	/*
	 * 群组类别
	 */
	private String fdGroupCateId;

	/*
	 * 群组成员
	 */
	private List fdMembers;

	public List getFdMembers() {
		List rtnVal = new ArrayList();
		if (getHbmMembers() != null) {
            rtnVal.addAll(getHbmMembers());
        }
		return rtnVal;
	}

	public void setFdMembers(List members) {
		new KmssCache(SysOrgElement.class).clear();
		if (this.fdMembers == members) {
            return;
        }
		if (this.fdMembers == null) {
            this.fdMembers = new ArrayList();
        } else {
            this.fdMembers.clear();
        }
		if (members != null) {
            this.fdMembers.addAll(members);
        }
	}

	public List getHbmMembers() {
		return fdMembers;
	}

	public void setHbmMembers(List members) {
		this.fdMembers = members;
	}

	@Override
	public Class getFormClass() {
		return SysOrgGroupForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.put("fdGroupCate.fdId", "fdGroupCateId");
			toFormPropertyMap.put("fdGroupCate.fdName", "fdGroupCateName");
			toFormPropertyMap.put("fdMembers",
					new ModelConvertor_ModelListToString(
							"fdMemberIds:fdMemberNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	public void setFdGroupCateId(String fdGroupCateId) {
		this.fdGroupCateId = fdGroupCateId;
	}

	public String getFdGroupCateId() {
		return fdGroupCateId;
	}

	/*
	 * 所有人可阅读标记
	 */
	protected Boolean authReaderFlag;

	public Boolean getAuthReaderFlag() {
		return authReaderFlag;
	}

	public void setAuthReaderFlag(Boolean authReaderFlag) {
		this.authReaderFlag = authReaderFlag;
	}

}
