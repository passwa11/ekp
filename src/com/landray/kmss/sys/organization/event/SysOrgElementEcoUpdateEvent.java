package com.landray.kmss.sys.organization.event;

import org.springframework.context.ApplicationEvent;

import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 生态组织更新事件
 *
 * @author 潘永辉 Apr 7, 2020
 */
public class SysOrgElementEcoUpdateEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private SysOrgElement sysOrgElement;

	public SysOrgElementEcoUpdateEvent(SysOrgElement sysOrgElement) {
		super(sysOrgElement);
		this.sysOrgElement = sysOrgElement;
	}

	public SysOrgElement getSysOrgElement() {
		return sysOrgElement;
	}

}
