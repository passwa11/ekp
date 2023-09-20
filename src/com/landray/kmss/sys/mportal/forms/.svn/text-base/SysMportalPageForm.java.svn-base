package com.landray.kmss.sys.mportal.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.mportal.model.SysMportalPage;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.forms.AuthForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.StringUtil;

/**
 * 移动公共门户页面 Form
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public class SysMportalPageForm extends AuthForm {

	/**
	 * 标题，对应浏览器的title属性
	 */
	private String fdTitle;

	public String getFdTitle() {
		return fdTitle;
	}

	public void setFdTitle(String fdTitle) {
		this.fdTitle = fdTitle;
	}

	/**
	 * 图标
	 */
	private String fdIcon;

	public String getFdIcon() {

		if (StringUtil.isNull(fdIcon)) {
			return "mui-approval";
		}
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
	 * 排序号
	 */
	private String fdOrder;

	/**
	 * @return 排序号
	 */
	public String getFdOrder() {
		return this.fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 是否有效
	 */
	private String fdEnabled;

	/**
	 * @return 是否有效
	 */
	public String getFdEnabled() {
		return this.fdEnabled;
	}

	/**
	 * @param fdEnabled
	 *            是否有效
	 */
	public void setFdEnabled(String fdEnabled) {
		this.fdEnabled = fdEnabled;
	}

	private String fdLang;

	public String getFdLang() {
		return fdLang;
	}

	public void setFdLang(String fdLang) {
		this.fdLang = fdLang;
	}

	/**
	 * 最后修改时间
	 */
	private String docAlterTime;

	/**
	 * @return 最后修改时间
	 */
	public String getDocAlterTime() {
		return this.docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
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

	private List cards = new AutoArrayList(SysMportalPageCardForm.class);

	public List getCards() {
		return cards;
	}

	public void setCards(List cards) {
		this.cards = cards;
	}

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
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdTitle = null;
		fdName = null;
		docCreateTime = null;
		fdOrder = null;
		fdEnabled = null;
		docAlterTime = null;
		docCreatorId = null;
		docCreatorName = null;
		fdType = null;
		fdLogo = null;
		fdUrl = null;
		fdMd5 = null;
		cards.clear();
		super.reset(mapping, request);
	}

	@Override
    public Class<SysMportalPage> getModelClass() {
		return SysMportalPage.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));

			toModelPropertyMap.put("cards",
					new FormConvertor_FormListToModelList("cards",
							"sysMportalPage"));
		}
		return toModelPropertyMap;
	}
}
