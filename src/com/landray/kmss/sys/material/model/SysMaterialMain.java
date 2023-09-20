package com.landray.kmss.sys.material.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.material.forms.SysMaterialMainForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoHashMap;

/**
 * 图片素材库
 * 
 * @author
 * @version 1.0 2014-11-26
 */
public class SysMaterialMain extends BaseModel implements IAttachment {

	private static final long serialVersionUID = -5463255597604702126L;

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
	 * 附件id
	 */
	protected String fdAttId;

	/**
	 * @return 附件id
	 */
	public String getFdAttId() {
		return fdAttId;
	}

	/**
	 * @param fdAttId
	 *            附件id
	 */
	public void setFdAttId(String fdAttId) {
		this.fdAttId = fdAttId;
	}

	/**
	 * 模块名
	 */
	protected String fdModelTitle;

	/**
	 * @return 模块名
	 */
	public String getFdModelTitle() {
		return fdModelTitle;
	}

	/**
	 * @param fdModelTitle
	 *            模块名
	 */
	public void setFdModelTitle(String fdModelTitle) {
		this.fdModelTitle = fdModelTitle;
	}

	/**
	 * 所属模块
	 */
	protected String fdModelName;

	/**
	 * @return 所属模块
	 */
	public String getFdModelName() {
		return fdModelName;
	}

	/**
	 * @param fdModelName
	 *            所属模块
	 */
	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	/**
	 * 业务类型
	 */
	protected String fdType;

	/**
	 * @return 业务类型
	 */
	public String getFdType() {
		return fdType;
	}

	/**
	 * @param fdType
	 *            业务类型
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
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

	/**
	 * @param docCreator
	 *            创建者
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	// 机制开始

	// 机制结束

	@Override
    public Class<?> getFormClass() {
		return SysMaterialMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
		}
		return toFormPropertyMap;
	}
	
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
	
	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}
}
