package com.landray.kmss.sys.fans.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.fans.constant.SysFansConstant;
import com.landray.kmss.sys.fans.model.SysFansMain;
import com.landray.kmss.sys.organization.model.SysOrgElement;


/**
 * 关注机制 Form
 * 
 * @author 
 * @version 1.0 2015-02-13
 */
public class SysFansMainForm extends ExtendForm {
	
	/**
	 * 用户ID
	 */
	protected String fdUserId = null;
	
	/**
	 * @return 用户ID
	 */
	public String getFdUserId() {
		return fdUserId;
	}
	
	/**
	 * @param fdUserId 用户ID
	 */
	public void setFdUserId(String fdUserId) {
		this.fdUserId = fdUserId;
	}
	
	/**
	 * 粉丝ID
	 */
	protected String fdFansId = null;
	
	/**
	 * @return 粉丝ID
	 */
	public String getFdFansId() {
		return fdFansId;
	}
	
	/**
	 * @param fdFansId 粉丝ID
	 */
	public void setFdFansId(String fdFansId) {
		this.fdFansId = fdFansId;
	}
	
	/**
	 * 关注时间
	 */
	protected String fdFollowTime = null;
	
	/**
	 * @return 关注时间
	 */
	public String getFdFollowTime() {
		return fdFollowTime;
	}
	
	/**
	 * @param fdFollowTime 关注时间
	 */
	public void setFdFollowTime(String fdFollowTime) {
		this.fdFollowTime = fdFollowTime;
	}
	
	/**
	 * 关系类型
	 */
	protected Integer fdRelationType = SysFansConstant.RELA_TYPE_FAN;
	
	/**
	 * @return 关系类型
	 */
	public Integer getFdRelationType() {
		return fdRelationType;
	}
	
	/**
	 * @param fdRelationType 关系类型
	 */
	public void setFdRelationType(Integer fdRelationType) {
		this.fdRelationType = fdRelationType;
	}
	
	/**
	 * 用户类型
	 */
	protected String fdUserType = null;
	
	/**
	 * @return 用户类型
	 */
	public String getFdUserType() {
		return fdUserType;
	}
	
	/**
	 * @param fdUserType 用户类型
	 */
	public void setFdUserType(String fdUserType) {
		this.fdUserType = fdUserType;
	}
	
	/**
	 * 能否取消关注
	 */
	protected String fdCanUnfollow = null;
	
	/**
	 * @return 能否取消关注
	 */
	public String getFdCanUnfollow() {
		return fdCanUnfollow;
	}
	
	/**
	 * @param fdCanUnfollow 能否取消关注
	 */
	public void setFdCanUnfollow(String fdCanUnfollow) {
		this.fdCanUnfollow = fdCanUnfollow;
	}
	
	/**
	 * 主模块名称
	 */
	protected String fdModelName = null;
	
	/**
	 * @return 主模块名称
	 */
	public String getFdModelName() {
		return fdModelName;
	}
	
	/**
	 * @param fdModelName 主模块名称
	 */
	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdUserId = null;
		fdFansId = null;
		fdFollowTime = null;
		fdRelationType = SysFansConstant.RELA_TYPE_FAN;
		fdUserType = null;
		fdCanUnfollow = null;
		fdModelName = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysFansMain.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdUserId",
					new FormConvertor_IDToModel("fdUser",
						SysOrgElement.class));
			toModelPropertyMap.put("fdFansId",
					new FormConvertor_IDToModel("fdFans",
						SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
