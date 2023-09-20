package com.landray.kmss.sys.fans.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.fans.forms.SysFansMainForm;

/**
 * 关注机制
 * 
 * @author 
 * @version 1.0 2015-02-13
 */
public class SysFansMain extends BaseModel {
	
	/**
	 * 用户
	 */
	protected String fdUserId;
	public String getFdUserId() {
		return fdUserId;
	}

	public void setFdUserId(String fdUserId) {
		this.fdUserId = fdUserId;
	}
	
	/**
	 * 粉丝
	 */
	protected String fdFansId;
	public String getFdFansId() {
		return fdFansId;
	}

	public void setFdFansId(String fdFansId) {
		this.fdFansId = fdFansId;
	}
	
	/**
	 * 关注时间
	 */
	protected Date fdFollowTime;
	
	/**
	 * @return 关注时间
	 */
	public Date getFdFollowTime() {
		return fdFollowTime;
	}
	
	/**
	 * @param fdFollowTime 关注时间
	 */
	public void setFdFollowTime(Date fdFollowTime) {
		this.fdFollowTime = fdFollowTime;
	}
	
	/**
	 * 关系类型
	 */
	protected Integer fdRelationType;
	
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
	protected Integer fdUserType;
	
	/**
	 * @return 用户类型
	 */
	public Integer getFdUserType() {
		return fdUserType;
	}
	
	/**
	 * @param fdUserType 用户类型
	 */
	public void setFdUserType(Integer fdUserType) {
		this.fdUserType = fdUserType;
	}
	
	/**
	 * 能否取消关注
	 */
	protected Boolean fdCanUnfollow;
	
	/**
	 * @return 能否取消关注
	 */
	public Boolean getFdCanUnfollow() {
		return fdCanUnfollow;
	}
	
	/**
	 * @param fdCanUnfollow 能否取消关注
	 */
	public void setFdCanUnfollow(Boolean fdCanUnfollow) {
		this.fdCanUnfollow = fdCanUnfollow;
	}
	
	/**
	 * 主模块名称
	 */
	protected String fdModelName;
	
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
    public Class getFormClass() {
		return SysFansMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdUser.fdId", "fdUserId");
			toFormPropertyMap.put("fdUser.fdName", "fdUserName");
			toFormPropertyMap.put("fdFans.fdId", "fdFansId");
			toFormPropertyMap.put("fdFans.fdName", "fdFansName");
		}
		return toFormPropertyMap;
	}
}
