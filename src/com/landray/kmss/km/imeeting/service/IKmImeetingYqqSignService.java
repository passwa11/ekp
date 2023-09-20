package com.landray.kmss.km.imeeting.service;

import java.util.List;

import com.landray.kmss.km.imeeting.model.KmImeetingSummary;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

public interface IKmImeetingYqqSignService {

	public abstract boolean sendEasyYqq(KmImeetingSummary kmImeetingSummary,
			String phone, String fdEnterprise) throws Exception;

	public abstract void sendYqq(KmImeetingSummary kmImeetingSummary,
			String phone, List<SysOrgPerson> elements, String processType)
			throws Exception;

}
