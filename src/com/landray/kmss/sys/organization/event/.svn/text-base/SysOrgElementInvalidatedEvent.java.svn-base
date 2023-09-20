package com.landray.kmss.sys.organization.event;

import org.springframework.context.ApplicationEvent;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 组织架构置为无效事件
 * 
 * @author 潘永辉 2018年1月23日
 *
 */
public class SysOrgElementInvalidatedEvent extends ApplicationEvent {
	private static final long serialVersionUID = -4871919078648158936L;

	private SysOrgElement sysOrgElement;

	private RequestContext requestContext;

	public SysOrgElementInvalidatedEvent(SysOrgElement sysOrgElement,
			RequestContext requestContext) {
		super(sysOrgElement);
		this.sysOrgElement = sysOrgElement;
		this.requestContext = requestContext;
	}

	public SysOrgElement getSysOrgElement() {
		return sysOrgElement;
	}

	public RequestContext getRequestContext() {
		return requestContext;
	}

}
