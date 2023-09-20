package com.landray.kmss.sys.organization.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.forms.SysOrganizationStaffingLevelForm;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 职级配置
 * 
 * @author
 * @version 1.0 2015-07-23
 */
public class SysOrganizationStaffingLevel extends BaseModel {

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
	 * 最后修改时间
	 */
	protected Date docAlterTime;

	/**
	 * @return 最后修改时间
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 描述
	 */
	protected String fdDescription;

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
	 * 名称
	 */
	protected String fdName;

	/**
	 * @return 名称
	 */
	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}

	/**
	 * @param fdName
	 *            名称
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

	/**
	 * 级别
	 */
	protected Integer fdLevel;

	/**
	 * @return 级别
	 */
	public Integer getFdLevel() {
		return fdLevel;
	}

	/**
	 * @param fdLevel
	 *            级别
	 */
	public void setFdLevel(Integer fdLevel) {
		this.fdLevel = fdLevel;
	}

	/**
	 * 创建者
	 */
	protected SysOrgElement docCreator;

	/**
	 * @return 创建者
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	/*
	 * 导入的数据的对应键值
	 */
	private String fdImportInfo;

	public String getFdImportInfo() {
		return fdImportInfo;
	}

	public void setFdImportInfo(String fdImportInfo) {
		this.fdImportInfo = fdImportInfo;
	}

	/**
	 * @param docCreator
	 *            创建者
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	@Override
	public Class getFormClass() {
		return SysOrganizationStaffingLevelForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdPersons",
					new ModelConvertor_ModelListToString(
							"fdPersonIds:fdPersonNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	/**
	 * 是否为默认
	 */
	protected Boolean fdIsDefault = Boolean.FALSE;

	public Boolean getFdIsDefault() {
		return fdIsDefault;
	}

	public void setFdIsDefault(Boolean fdIsDefault) {
		this.fdIsDefault = fdIsDefault;
	}

	/*
	 * 个人列表（岗位使用）
	 */
	public List<SysOrgPerson> fdPersons = null;

	public List<SysOrgPerson> getFdPersons() throws Exception {

		if (fdPersons == null) {
            fdPersons = getSysOrganizationStaffingLevelService().getPersons(
                    this.fdId);
        }
		return fdPersons;
	}

	public void setFdPersons(List<SysOrgPerson> persons) {
		if (this.fdPersons == persons) {
            return;
        }
		if (this.fdPersons == null) {
            this.fdPersons = new ArrayList<SysOrgPerson>();
        } else {
            this.fdPersons.clear();
        }
		if (persons != null) {
            this.fdPersons.addAll(persons);
        }
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		if (sysOrganizationStaffingLevelService == null) {
			sysOrganizationStaffingLevelService = (ISysOrganizationStaffingLevelService) SpringBeanUtil
					.getBean("sysOrganizationStaffingLevelService");
		}
		return sysOrganizationStaffingLevelService;
	}

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;
}
