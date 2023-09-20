package com.landray.kmss.sys.organization.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.organization.forms.SysOrgGroupForm;
import com.landray.kmss.sys.organization.interfaces.ISysOrgGroup;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 群组
 * 
 * @author 叶中奇
 */
public class SysOrgGroup extends SysOrgElement implements ISysOrgGroup {
	public SysOrgGroup() {
		super();
		setFdOrgType(new Integer(ORG_TYPE_GROUP));
	}

	/*
	 * 群组类别
	 */
	private SysOrgGroupCate fdGroupCate;

	public SysOrgGroupCate getFdGroupCate() {
		return getHbmGroupCate();
	}

	public void setFdGroupCate(SysOrgGroupCate groupCate) {
		setHbmGroupCate(groupCate);
	}

	public SysOrgGroupCate getHbmGroupCate() {
		return fdGroupCate;
	}

	public void setHbmGroupCate(SysOrgGroupCate groupCate) {
		this.fdGroupCate = groupCate;
	}

	/*
	 * 可阅读者
	 */
	protected List authReaders = new ArrayList();

	public List getAuthReaders() {
		return authReaders;
	}

	public void setAuthReaders(List authReaders) {
		this.authReaders = authReaders;
	}

	/*
	 * 可编辑者
	 */
	protected List authEditors = new ArrayList();

	public List getAuthEditors() {
		return authEditors;
	}

	public void setAuthEditors(List authEditors) {
		this.authEditors = authEditors;
	}

	/*
	 * 所有人可阅读标记
	 */
	protected Boolean authReaderFlag;

	public Boolean getAuthReaderFlag() {
		if (ArrayUtil.isEmpty(getAuthReaders())) {
            authReaderFlag = new Boolean(true);
        } else {
            authReaderFlag = new Boolean(false);
        }
		return authReaderFlag;
	}

	public void setAuthReaderFlag(Boolean authReaderFlag) {
	}

	@Override
	public void recalculateFields() {
		super.recalculateFields();
		if (ArrayUtil.isEmpty(getAuthReaders())) {
			// 生态组织内外隔离
			SysOrgPerson creator = getDocCreator();
			if (creator == null) {
				creator = UserUtil.getUser();
			}
			if (BooleanUtils.isTrue(creator.getFdIsExternal())) {
				if (creator.getFdParent() != null) {
					getAuthReaders().add(creator.getFdParent());
				}
			}
		}
	}

	/*
	 * 群组成员
	 */
	private List fdMembers;

	@Override
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
			toFormPropertyMap.put("authReaders",
					new ModelConvertor_ModelListToString(
							"authReaderIds:authReaderNames", "fdId:fdName"));
			toFormPropertyMap.put("authEditors",
					new ModelConvertor_ModelListToString(
							"authEditorIds:authEditorNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}
}
