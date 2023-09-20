package com.landray.kmss.sys.zone.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.util.AutoArrayList;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;

import com.landray.kmss.sys.zone.model.SysZonePersonAttenFan;
import com.landray.kmss.sys.organization.model.SysOrgElement;



/**
 * 关注/粉丝信息 Form
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePersonAttenFanForm  extends ExtendForm  {

	/**
	 * 关注/粉时间
	 */
	protected String fdCreateTime = null;
	
	/**
	 * @return 关注/粉时间
	 */
	public String getFdCreateTime() {
		return fdCreateTime;
	}
	
	/**
	 * @param fdCreateTime 关注/粉时间
	 */
	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}
	
	/**
	 * 关注者组织ID的ID
	 */
	protected String fdFromElementId = null;
	
	/**
	 * @return 关注者组织ID的ID
	 */
	public String getFdFromElementId() {
		return fdFromElementId;
	}
	
	/**
	 * @param fdFromElementId 关注者组织ID的ID
	 */
	public void setFdFromElementId(String fdFromElementId) {
		this.fdFromElementId = fdFromElementId;
	}
	
	/**
	 * 关注者组织ID的名称
	 */
	protected String fdFromElementName = null;
	
	/**
	 * @return 关注者组织ID的名称
	 */
	public String getFdFromElementName() {
		return fdFromElementName;
	}
	
	/**
	 * @param fdFromElementName 关注者组织ID的名称
	 */
	public void setFdFromElementName(String fdFromElementName) {
		this.fdFromElementName = fdFromElementName;
	}
	
	/**
	 * 被关注者组织ID的ID
	 */
	protected String fdToElementId = null;
	
	/**
	 * @return 被关注者组织ID的ID
	 */
	public String getFdToElementId() {
		return fdToElementId;
	}
	
	/**
	 * @param fdToElementId 被关注者组织ID的ID
	 */
	public void setFdToElementId(String fdToElementId) {
		this.fdToElementId = fdToElementId;
	}
	
	/**
	 * 被关注者组织ID的名称
	 */
	protected String fdToElementName = null;
	
	/**
	 * @return 被关注者组织ID的名称
	 */
	public String getFdToElementName() {
		return fdToElementName;
	}
	
	/**
	 * @param fdToElementName 被关注者组织ID的名称
	 */
	public void setFdToElementName(String fdToElementName) {
		this.fdToElementName = fdToElementName;
	}
	
//机制开始 

//机制结束
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdCreateTime = null;
		fdFromElementId = null;
		fdFromElementName = null;
		fdToElementId = null;
		fdToElementName = null;
		
		 
 
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysZonePersonAttenFan.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdFromElementId",
					new FormConvertor_IDToModel("fdFromElement",
						SysOrgElement.class));
			toModelPropertyMap.put("fdToElementId",
					new FormConvertor_IDToModel("fdToElement",
						SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
