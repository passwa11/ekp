package com.landray.kmss.sys.organization.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.forms.SysOrgRoleConfForm;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 陈亮 角色线配置
 */
public class SysOrgRoleConf extends BaseModel {

	/*
	 * 名称
	 */
	protected String fdName;
	/*
	 * 排序号
	 */
	protected Long fdOrder;

	/*
	 * 是否有效
	 */
	private Boolean fdIsAvailable;

	public Boolean getFdIsAvailable() {
		if (fdIsAvailable == null) {
            fdIsAvailable = new Boolean(true);
        }
		return fdIsAvailable;
	}

	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	private List defaultRoleList = new ArrayList();

	public List getDefaultRoleList() {
		return defaultRoleList;
	}

	public void setDefaultRoleList(List defaultRoleList) {
		this.defaultRoleList = defaultRoleList;
	}

	public Long getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Long fdOrder) {
		this.fdOrder = fdOrder;
	}

	public SysOrgRoleConf() {
		super();
	}

	/**
	 * @return 返回 名称
	 */
	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}

	/**
	 * @param fdName
	 *            要设置的 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	public String getFdNameOri() {
		return fdName;
	}

	public void setFdNameOri(String fdName) {
		this.fdName = fdName;
	}

	/*
	 * 维护人员
	 */
	protected List sysRoleLineEditors = new ArrayList();

	public List getSysRoleLineEditors() {
		return sysRoleLineEditors;
	}

	public void setSysRoleLineEditors(List sysRoleLineEditors) {
		this.sysRoleLineEditors = sysRoleLineEditors;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdRoleConfCate.fdId", "fdRoleConfCateId");
			toFormPropertyMap.put("fdRoleConfCate.fdName", "fdRoleConfCateName");
			toFormPropertyMap.put("sysRoleLineEditors",
					new ModelConvertor_ModelListToString(
							"fdRoleLineEditorIds:fdRoleLineEditorNames",
							"fdId:fdName"));
			toFormPropertyMap.put("sysRoleLineReaders",
					new ModelConvertor_ModelListToString(
							"fdRoleLineReaderIds:fdRoleLineReaderNames",
							"fdId:fdName"));
			toFormPropertyMap.put("defaultRoleList",
					new ModelConvertor_ModelListToFormList("defaultRoleList"));
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysOrgRoleConfForm.class;
	}
	
	/*
	 * 可使用者
	 */
	protected List sysRoleLineReaders = new ArrayList();

	public List getSysRoleLineReaders() {
		return sysRoleLineReaders;
	}

	public void setSysRoleLineReaders(List sysRoleLineReaders) {
		this.sysRoleLineReaders = sysRoleLineReaders;
	}

	@Override
	public void recalculateFields() {
		super.recalculateFields();
		if (ArrayUtil.isEmpty(getSysRoleLineReaders())) {
			// 生态组织内外隔离
			SysOrgPerson creator = UserUtil.getUser();
			if (BooleanUtils.isTrue(creator.getFdIsExternal())) {
				if (creator.getFdParent() != null) {
					getSysRoleLineReaders().add(creator.getFdParent());
				}
			}
		}
	}

	/*
	 * 更新时间
	 */
	private Date fdAlterTime = new Date();

	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}

	public Date getFdAlterTime() {
		return fdAlterTime;
	}

	/*
	 * 创建时间
	 */
	private Date fdCreateTime = new Date();

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}
	
	/*
	 * 角色线类别
	 */
	private SysOrgRoleConfCate fdRoleConfCate;

	public SysOrgRoleConfCate getFdRoleConfCate() {
		return fdRoleConfCate;
	}

	public void setFdRoleConfCate(SysOrgRoleConfCate fdRoleConfCate) {
		this.fdRoleConfCate = fdRoleConfCate;
	}

	public Date getFdRoleLineAlterTime() {
		return fdRoleLineAlterTime;
	}

	public void setFdRoleLineAlterTime(Date fdRoleLineAlterTime) {
		this.fdRoleLineAlterTime = fdRoleLineAlterTime;
	}

	private Date fdRoleLineAlterTime = new Date();

}
