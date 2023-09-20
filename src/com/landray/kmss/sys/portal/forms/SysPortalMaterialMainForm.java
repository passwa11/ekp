package com.landray.kmss.sys.portal.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.portal.model.SysPortalMaterialMain;
import com.landray.kmss.sys.portal.model.SysPortalMaterialTag;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 素材库
 */
public class SysPortalMaterialMainForm extends ExtendForm {

	private static FormToModelPropertyMap toModelPropertyMap;

	private String fdName;

	private String docCreateTime;

	private String docAlterTime;

	private String fdSize;

	private String fdType;

	private String fdWidth;

	private String fdLength;

	private String docSubject;

	private String docCreatorId;

	private String docCreatorName;

	private String docAlterorId;

	private String docAlterorName;

	private String fdAttId;

	private String fdImgUrl;//素材库内置图标路径

	private String fdTagIds;

	private String fdTagNames;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {

		fdName = null;
		docCreateTime = null;
		docAlterTime = null;
		fdSize = null;
		fdType = null;
		fdWidth = null;
		fdLength = null;
		docCreatorId = null;
		docCreatorName = null;
		docAlterorId = null;
		docAlterorName = null;
		fdAttId = null;
		fdImgUrl=null;
		fdTagIds = null;
		fdTagNames = "";
		super.reset(mapping, request);
	}

	@Override
    public Class<SysPortalMaterialMain> getModelClass() {
		return SysPortalMaterialMain.class;
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.addNoConvertProperty("docCreateTime");
			toModelPropertyMap.addNoConvertProperty("docAlterTime");
			toModelPropertyMap.put("fdTagIds", new FormConvertor_IDsToModelList(
					"fdTags", SysPortalMaterialTag.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 创建时间
	 */
	public String getDocCreateTime() {
		return this.docCreateTime;
	}

	/**
	 * 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 更新时间
	 */
	public String getDocAlterTime() {
		return this.docAlterTime;
	}

	/**
	 * 更新时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 大小
	 */
	public String getFdSize() {
		return this.fdSize;
	}

	/**
	 * 大小
	 */
	public void setFdSize(String fdSize) {
		this.fdSize = fdSize;
	}

	/**
	 * 类型
	 */
	public String getFdType() {
		return this.fdType;
	}

	/**
	 * 类型
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	/**
	 * 宽
	 */
	public String getFdWidth() {
		return this.fdWidth;
	}

	/**
	 * 宽
	 */
	public void setFdWidth(String fdWidth) {
		this.fdWidth = fdWidth;
	}

	/**
	 * 长
	 */
	public String getFdLength() {
		return this.fdLength;
	}

	/**
	 * 长
	 */
	public void setFdLength(String fdLength) {
		this.fdLength = fdLength;
	}

	/**
	 * 标题
	 */
	public String getDocSubject() {
		return this.docSubject;
	}

	/**
	 * 标题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 创建人
	 */
	public String getDocCreatorId() {
		return this.docCreatorId;
	}

	/**
	 * 创建人
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建人
	 */
	public String getDocCreatorName() {
		return this.docCreatorName;
	}

	/**
	 * 创建人
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * 修改人
	 */
	public String getDocAlterorId() {
		return this.docAlterorId;
	}

	/**
	 * 修改人
	 */
	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	/**
	 * 修改人
	 */
	public String getDocAlterorName() {
		return this.docAlterorName;
	}

	/**
	 * 修改人
	 */
	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	/**
	 * 素材标签引用
	 */
	public String getFdTagIds() {
		return this.fdTagIds;
	}

	/**
	 * 素材标签引用
	 */
	public void setFdTagIds(String fdTagIds) {
		this.fdTagIds = fdTagIds;
	}

	/**
	 * 素材标签引用
	 */
	public String getFdTagNames() {
		if(this.fdTagNames == null){
			this.fdTagNames ="";
		}
		return this.fdTagNames;
	}

	/**
	 * 素材标签引用
	 */
	public void setFdTagNames(String fdTagNames) {
		this.fdTagNames = fdTagNames;
	}

	/**
	 * 附件Id
	 */
	public String getFdAttId() {
		return fdAttId;
	}

	/**
	 * 附件Id
	 */
	public void setFdAttId(String fdAttId) {
		this.fdAttId = fdAttId;
	}

	/**
	 * 素材库内置图标路径
	 */
	public String getFdImgUrl() {
		return fdImgUrl;
	}

	public void setFdImgUrl(String fdImgUrl) {
		this.fdImgUrl = fdImgUrl;
	}

}
