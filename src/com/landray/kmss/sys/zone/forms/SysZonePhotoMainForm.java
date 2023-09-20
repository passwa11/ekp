package com.landray.kmss.sys.zone.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.util.AutoArrayList;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;

import com.landray.kmss.sys.zone.model.SysZonePhotoMain;


/**
 * 照片墙 Form
 * 
 * @author 
 * @version 1.0 2014-09-11
 */
public class SysZonePhotoMainForm extends ExtendForm {

	/**
	 * 名称
	 */
	protected String fdName = null;
	
	/**
	 * @return 名称
	 */
	public String getFdName() {
		return fdName;
	}
	
	/**
	 * @param fdName 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * 创建时间
	 */
	protected String docCreateTime = null;
	
	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	/**
	 * 更新时间
	 */
	protected String fdLastModifiedTime = null;
	
	/**
	 * @return 更新时间
	 */
	public String getFdLastModifiedTime() {
		return fdLastModifiedTime;
	}
	
	/**
	 * @param fdLastModifiedTime 更新时间
	 */
	public void setFdLastModifiedTime(String fdLastModifiedTime) {
		this.fdLastModifiedTime = fdLastModifiedTime;
	}
	
	/**
	 * 是否默认
	 */
	protected String fdIsDefault = null;
	
	/**
	 * @return 是否默认
	 */
	public String getFdIsDefault() {
		return fdIsDefault;
	}
	
	/**
	 * @param fdIsDefault 是否默认
	 */
	public void setFdIsDefault(String fdIsDefault) {
		this.fdIsDefault = fdIsDefault;
	}
	
	/**
	 * 模板id
	 */
	protected String fdTemplateId = null;
	
	/**
	 * @return 模板id
	 */
	public String getFdTemplateId() {
		return fdTemplateId;
	}
	
	/**
	 * @param fdTemplateId 模板id
	 */
	public void setFdTemplateId(String fdTemplateId) {
		this.fdTemplateId = fdTemplateId;
	}
	
	/**
	 * 模板名
	 */
	protected String fdTemplateName = null;
	
	/**
	 * @return 模板名
	 */
	public String getFdTemplateName() {
		return fdTemplateName;
	}
	
	/**
	 * @param fdTemplateName 模板名
	 */
	public void setFdTemplateName(String fdTemplateName) {
		this.fdTemplateName = fdTemplateName;
	}
	
	/**
	 * 生成路径
	 */
	protected String fdFilePath = null;
	
	/**
	 * @return 生成路径
	 */
	public String getFdFilePath() {
		return fdFilePath;
	}
	
	/**
	 * @param fdFilePath 生成路径
	 */
	public void setFdFilePath(String fdFilePath) {
		this.fdFilePath = fdFilePath;
	}
	
	/**
	 * 图片文件名
	 */
	protected String fdImgName = null;
	
	/**
	 * @return 图片文件名
	 */
	public String getFdImgName() {
		return fdImgName;
	}
	
	/**
	 * @param fdImgName 图片文件名
	 */
	public void setFdImgName(String fdImgName) {
		this.fdImgName = fdImgName;
	}
	
	/**
	 * html文件名
	 */
	protected String fdHtmlName = null;
	
	/**
	 * @return html文件名
	 */
	public String getFdHtmlName() {
		return fdHtmlName;
	}
	
	/**
	 * @param fdHtmlName html文件名
	 */
	public void setFdHtmlName(String fdHtmlName) {
		this.fdHtmlName = fdHtmlName;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		docCreateTime = null;
		fdLastModifiedTime = null;
		fdIsDefault = null;
		fdTemplateId = null;
		fdTemplateName = null;
		fdFilePath = null;
		fdImgName = null;
		fdHtmlName = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysZonePhotoMain.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}
