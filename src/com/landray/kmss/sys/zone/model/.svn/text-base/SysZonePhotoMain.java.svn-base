package com.landray.kmss.sys.zone.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.zone.forms.SysZonePhotoMainForm;
import com.landray.kmss.util.AutoHashMap;

/**
 * 照片墙
 * 
 * @author 
 * @version 1.0 2014-09-11
 */
public class SysZonePhotoMain extends BaseModel implements IAttachment {

	/**
	 * 名称
	 */
	protected String fdName;
	
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
	protected Date docCreateTime;
	
	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	/**
	 * 更新时间
	 */
	protected Date fdLastModifiedTime;
	
	/**
	 * @return 更新时间
	 */
	public Date getFdLastModifiedTime() {
		return fdLastModifiedTime;
	}
	
	/**
	 * @param fdLastModifiedTime 更新时间
	 */
	public void setFdLastModifiedTime(Date fdLastModifiedTime) {
		this.fdLastModifiedTime = fdLastModifiedTime;
	}
	
	/**
	 * 是否默认
	 */
	protected Boolean fdIsDefault;
	
	/**
	 * @return 是否默认
	 */
	public Boolean getFdIsDefault() {
		return fdIsDefault;
	}
	
	/**
	 * @param fdIsDefault 是否默认
	 */
	public void setFdIsDefault(Boolean fdIsDefault) {
		this.fdIsDefault = fdIsDefault;
	}
	
	/**
	 * 模板id
	 */
	protected String fdTemplateId;
	
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
	protected String fdTemplateName;
	
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
	
	
	
	@Override
    public Class getFormClass() {
		return SysZonePhotoMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
	
	/**附件**/
	protected AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}
	
	/**
	 * 数据源ID，比如注册“最新员工”,“推荐专家”时的注册id
	 */
	private String fdSourceId;

	public String getFdSourceId() {
		return fdSourceId;
	}

	public void setFdSourceId(String fdSourceId) {
		this.fdSourceId = fdSourceId;
	}
	
	/**
	 * 数据源name
	 */
	private String fdSourceName;

	public String getFdSourceName() {
		return fdSourceName;
	}

	public void setFdSourceName(String fdSourceName) {
		this.fdSourceName = fdSourceName;
	}
	
}
