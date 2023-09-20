package com.landray.kmss.sys.material.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.material.model.SysMaterialMain;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoHashMap;



/**
 * 图片素材库 Form
 * 
 * @author 
 * @version 1.0 2014-11-26
 */
public class SysMaterialMainForm  extends ExtendForm implements IAttachmentForm {

	private static final long serialVersionUID = -238322655134621619L;
	
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
	 * 附件id
	 */
	protected String fdAttId = null;
	
	/**
	 * @return 附件id
	 */
	public String getFdAttId() {
		return fdAttId;
	}
	
	/**
	 * @param fdAttId 附件id
	 */
	public void setFdAttId(String fdAttId) {
		this.fdAttId = fdAttId;
	}
	
	/**
	 * 模块名
	 */
	protected String fdModelTitle = null;
	
	/**
	 * @return 模块名
	 */
	public String getFdModelTitle() {
		return fdModelTitle;
	}
	
	/**
	 * @param fdModelTitle 模块名
	 */
	public void setFdModelTitle(String fdModelTitle) {
		this.fdModelTitle = fdModelTitle;
	}
	
	/**
	 * 所属模块
	 */
	protected String fdModelName = null;
	
	/**
	 * @return 所属模块
	 */
	public String getFdModelName() {
		return fdModelName;
	}
	
	/**
	 * @param fdModelName 所属模块
	 */
	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}
	
	/**
	 * 业务类型
	 */
	protected String fdType = null;
	
	/**
	 * @return 业务类型
	 */
	public String getFdType() {
		return fdType;
	}
	
	/**
	 * @param fdType 业务类型
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}
	
	/**
	 * 创建者的ID
	 */
	protected String docCreatorId = null;
	
	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}
	
	/**
	 * @param docCreatorId 创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}
	
	/**
	 * 创建者的名称
	 */
	protected String docCreatorName = null;
	
	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}
	
	/**
	 * @param docCreatorName 创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}
	
//机制开始 

//机制结束
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docCreateTime = null;
		fdAttId = null;
		fdModelTitle = null;
		fdModelName = null;
		fdType = null;
		docCreatorId = null;
		docCreatorName = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class<?> getModelClass() {
		return SysMaterialMain.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
						SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
	
	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}
}
