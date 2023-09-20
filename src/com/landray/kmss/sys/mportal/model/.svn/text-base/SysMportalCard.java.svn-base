package com.landray.kmss.sys.mportal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.mportal.forms.SysMportalCardForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 推送配置模型
 * 
 * @author
 * @version 1.0 2015-09-14
 */
public class SysMportalCard extends BaseModel {

	// 页面卡片
	protected List<SysMportalPageCard> cards = new ArrayList<SysMportalPageCard>();

	public List<SysMportalPageCard> getCards() {
		return cards;
	}

	public void setCards(List<SysMportalPageCard> cards) {
		this.cards = cards;
	}
	
	// 复合门户页面卡片
	protected List<SysMportalCpageCard> cpageCards = new ArrayList<SysMportalCpageCard>();

	public List<SysMportalCpageCard> getCpageCards() {
		return cpageCards;
	}

	public void setCpageCards(List<SysMportalCpageCard> cpageCards) {
		this.cpageCards = cpageCards;
	}
	
	

	/**
	 * 名称
	 */
	private String fdName;

	/**
	 * @param fdName
	 *            名称
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

	/**
	 * 创建时间
	 */
	private Date docCreateTime;

	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return this.docCreateTime;
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
	private Date docAlterTime;

	/**
	 * @return 最后修改时间
	 */
	public Date getDocAlterTime() {
		return this.docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 是否启用
	 */
	private Boolean fdEnabled;

	/**
	 * @return 是否启用
	 */
	public Boolean getFdEnabled() {
		return this.fdEnabled;
	}

	/**
	 * @param fdEnabled
	 *            是否启用
	 */
	public void setFdEnabled(Boolean fdEnabled) {
		this.fdEnabled = fdEnabled;
	}

	/**
	 * 排序号
	 */
	private Integer fdOrder;

	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return this.fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	private String fdPortletConfig;

	public String getFdPortletConfig() {
		return fdPortletConfig;
	}

	public void setFdPortletConfig(String fdPortletConfig) {
		this.fdPortletConfig = fdPortletConfig;
	}

	/**
	 * 创建者
	 */
	private SysOrgPerson docCreator;

	/**
	 * @return 创建者
	 */
	public SysOrgPerson getDocCreator() {
		return this.docCreator;
	}

	/**
	 * @param docCreator
	 *            创建者
	 */
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	// 机制开始
	// 机制结束

	/**
	 * 是否推送到个人门户
	 */
	private Boolean fdIsPushed;

	public Boolean getFdIsPushed() {
		return fdIsPushed;
	}

	public void setFdIsPushed(Boolean fdIsPushed) {
		this.fdIsPushed = fdIsPushed;
	}
	
	/**
	 * 类型，是否多页签
	 */
	private Boolean fdType = new Boolean(false);

	public Boolean getFdType() {
		return fdType;
	}

	public void setFdType(Boolean fdType) {
		this.fdType = fdType;
	}

	/*
	 * 所属分类
	 */
	protected SysMportalModuleCate fdModuleCate;

	public SysMportalModuleCate getFdModuleCate() {
		return fdModuleCate;
	}

	public void setFdModuleCate(SysMportalModuleCate fdModuleCate) {
		this.fdModuleCate = fdModuleCate;
	}

	@Override
    public Class<SysMportalCardForm> getFormClass() {
		return SysMportalCardForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdModuleCate.fdId", "fdModuleCateId");
			toFormPropertyMap.put("fdModuleCate.fdName", "fdModuleCateName");
			toFormPropertyMap.put("authEditors",
					new ModelConvertor_ModelListToString(
							"authEditorIds:authEditorNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}
}
