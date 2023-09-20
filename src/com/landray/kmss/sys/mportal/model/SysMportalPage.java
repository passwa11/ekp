package com.landray.kmss.sys.mportal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.mportal.forms.SysMportalPageForm;
import com.landray.kmss.sys.portal.model.AuthModel;

/**
 * 移动公共门户页面
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public class SysMportalPage extends AuthModel {

	private String fdTitle;

	public String getFdTitle() {
		return fdTitle;
	}

	public void setFdTitle(String fdTitle) {
		this.fdTitle = fdTitle;
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

	/**
	 * 是否有效
	 */
	private Boolean fdEnabled;

	/**
	 * @return 是否有效
	 */
	public Boolean getFdEnabled() {
		return this.fdEnabled;
	}

	/**
	 * @param fdEnabled
	 *            是否有效
	 */
	public void setFdEnabled(Boolean fdEnabled) {
		this.fdEnabled = fdEnabled;
	}

	protected String fdLang;

	public String getFdLang() {
		return fdLang;
	}

	public void setFdLang(String fdLang) {
		this.fdLang = fdLang;
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

	// 页面卡片
	protected List<SysMportalPageCard> cards = new ArrayList<SysMportalPageCard>();

	public List<SysMportalPageCard> getCards() {
		return cards;
	}

	public void setCards(List<SysMportalPageCard> cards) {
		this.cards = cards;
	}

	/**
	 * 类型
	 */
	private String fdType;

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	private String fdLogo;

	public String getFdLogo() {
		return fdLogo;
	}

	public void setFdLogo(String fdLogo) {
		this.fdLogo = fdLogo;
	}

	/**
	 * 图标
	 */
	private String fdIcon;

	public String getFdIcon() {
		return fdIcon;
	}

	public void setFdIcon(String fdIcon) {
		this.fdIcon = fdIcon;
	}


	/**
	 * 素材库
	 */
	private String fdImg;

	public String getFdImg() {
		return fdImg;
	}

	public void setFdImg(String fdImg) {
		this.fdImg = fdImg;
	}

	private String fdUrl;

	public String getFdUrl() {
		return fdUrl;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}

	private String fdMd5;

	public String getFdMd5() {
		return fdMd5;
	}

	public void setFdMd5(String fdMd5) {
		this.fdMd5 = fdMd5;
	}

	@Override
    public Class<SysMportalPageForm> getFormClass() {
		return SysMportalPageForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");

			toFormPropertyMap.put("cards",
					new ModelConvertor_ModelListToFormList("cards"));
		}
		return toFormPropertyMap;
	}

	@Override
	public void recalculateFields() {
		super.recalculateFields();
		int order = 0;
		for (SysMportalPageCard item : this.getCards()) {
			order++;
			item.setFdOrder(order);
		}
	}
}
