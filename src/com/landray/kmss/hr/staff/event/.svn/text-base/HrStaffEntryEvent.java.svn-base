package com.landray.kmss.hr.staff.event;

import org.springframework.context.ApplicationEvent;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.hr.staff.model.HrStaffEntry;

public class HrStaffEntryEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7379019756232298113L;

	private HrStaffEntry hrStaffEntry;

	private RequestContext requestContext;

	public HrStaffEntryEvent(HrStaffEntry hrStaffEntry,
			RequestContext requestContext) {
		super(requestContext);
		this.hrStaffEntry = hrStaffEntry;
		this.requestContext = requestContext;
	}

	public HrStaffEntry getHrStaffEntry() {
		return hrStaffEntry;
	}

	public void setHrStaffEntry(HrStaffEntry hrStaffEntry) {
		this.hrStaffEntry = hrStaffEntry;
	}

	public RequestContext getRequestContext() {
		return requestContext;
	}

	public void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

}
