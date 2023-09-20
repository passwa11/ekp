package com.landray.kmss.hr.staff.event;

import org.springframework.context.ApplicationEvent;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;

public class HrStaffPersonInfoEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -771035328307093800L;

	private HrStaffPersonInfo hrStaffPersonInfo;

	private RequestContext requestContext;

	private String operType;

	public HrStaffPersonInfoEvent(HrStaffPersonInfo hrStaffPersonInfo,
			RequestContext requestContext) {
		super(requestContext);
		this.hrStaffPersonInfo = hrStaffPersonInfo;
		this.requestContext = requestContext;
	}

	public HrStaffPersonInfoEvent(HrStaffPersonInfo hrStaffPersonInfo,
			RequestContext requestContext, String operType) {
		super(requestContext);
		this.hrStaffPersonInfo = hrStaffPersonInfo;
		this.requestContext = requestContext;
		this.operType = operType;
	}

	public HrStaffPersonInfo getHrStaffPersonInfo() {
		return hrStaffPersonInfo;
	}

	public RequestContext getRequestContext() {
		return requestContext;
	}

	public String getOperType() {
		return operType;
	}

}
