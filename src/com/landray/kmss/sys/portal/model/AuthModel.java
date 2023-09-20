package com.landray.kmss.sys.portal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;

public abstract class AuthModel extends BaseModel {
	/**
	 * 创建时间
	 */
	protected Date docCreateTime;

	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 创建人
	 */
	protected SysOrgElement docCreator;

	/**
	 * @return 创建人
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	/**
	 * @param docCreator
	 *            创建人
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
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
	 * 所有可阅读者
	 */
	protected List authAllReaders = new ArrayList();

	public List getAuthAllReaders() {
		return authAllReaders;
	}

	public void setAuthAllReaders(List authAllReaders) {
		this.authAllReaders = authAllReaders;
	}

	/*
	 * 所有可编辑者
	 */
	protected List authAllEditors = new ArrayList();

	public List getAuthAllEditors() {
		return authAllEditors;
	}

	public void setAuthAllEditors(List authAllEditors) {
		this.authAllEditors = authAllEditors;
	}

	/*
	 * 所有人可阅读标记
	 */
	protected Boolean authReaderFlag;

	public Boolean getAuthReaderFlag() {
		if (ArrayUtil.isEmpty(getAuthReaders())) {
            return new Boolean(true);
        } else {
            return new Boolean(false);
        }
	}

	public void setAuthReaderFlag(Boolean authReaderFlag) {
	}

	@Override
	public void recalculateFields() {
		super.recalculateFields();
		recalculateEditorField();
		recalculateReaderField();
	}

	protected void recalculateEditorField() {
		// 重新计算可编辑者
		if (authAllEditors == null) {
            authAllEditors = new ArrayList();
        } else {
            authAllEditors.clear();
        }
		authAllEditors.add(getDocCreator());
		List tmpList = getAuthEditors();
		if (tmpList != null) {
			ArrayUtil.concatTwoList(tmpList, authAllEditors);
		}
	}

	protected void recalculateReaderField() {
		// 重新计算可阅读者
		if (authAllReaders == null) {
            authAllReaders = new ArrayList();
        } else {
            authAllReaders.clear();
        }

		if (getAuthReaderFlag().booleanValue()) {
			// 创建人属于外部组织，处理为空则所有人可访问的逻辑
			SysOrgElement creator = getDocCreator();
			if (creator == null) {
				creator = UserUtil.getUser();
			}
			if (BooleanUtils.isTrue(creator.getFdIsExternal())) {
				SysOrgElement parent = creator.getFdParent();
				// 增加所属组织（外部人员的可访问者为空时，仅限于该组织及子组织可访问）
				if (parent != null) {
					authAllReaders.add(parent);
				}
			} else {
				// everyone
				authAllReaders.add(UserUtil.getEveryoneUser());
			}
			// 因为外部人员默认不能查看阅读权限为空的文档，所以这里还需要加入外部维护者
			if (CollectionUtils.isNotEmpty(authAllEditors)) {
				List outter = new ArrayList();
				for (Object obj : authAllEditors) {
					if (obj == null) {
						continue;
					}
					SysOrgElement elem = (SysOrgElement) obj;
					if (BooleanUtils.isTrue(elem.getFdIsExternal())) {
						outter.add(elem);
					}
				}
				ArrayUtil.concatTwoList(outter, authAllReaders);
			}
			return;
		}
		authAllReaders.add(getDocCreator());

		List tmpList = getAuthReaders();
		if (tmpList != null) {
			ArrayUtil.concatTwoList(tmpList, authAllReaders);
		}
		ArrayUtil.concatTwoList(authAllEditors, authAllReaders);
	}

	private ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
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
