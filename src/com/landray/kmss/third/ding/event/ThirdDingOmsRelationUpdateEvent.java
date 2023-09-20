package com.landray.kmss.third.ding.event;

import org.springframework.context.ApplicationEvent;

/**
 * 关联关系更新事件
 *
 */
public class ThirdDingOmsRelationUpdateEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private String orgId;
	private String fdType;
	private String fdAppId;

	public ThirdDingOmsRelationUpdateEvent(String orgId,String fdType,String fdAppId) {
		super(orgId);
		this.orgId = orgId;
		this.fdType = fdType;
		this.fdAppId = fdAppId;
	}

	public String getOrgId() {
		return orgId;
	}
	
	public String getFdType() {
		return fdType;
	}

	public String getFdAppId() {
		return fdAppId;
	}
}
