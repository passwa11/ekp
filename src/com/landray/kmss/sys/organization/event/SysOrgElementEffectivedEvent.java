package com.landray.kmss.sys.organization.event;

import org.springframework.context.ApplicationEvent;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 组织架构置为有效时间
 *
 */
public class SysOrgElementEffectivedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 6100166205596797517L;

	private SysOrgElement sysOrgElement;

	private RequestContext requestContext;

	public SysOrgElementEffectivedEvent(SysOrgElement sysOrgElement,
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
