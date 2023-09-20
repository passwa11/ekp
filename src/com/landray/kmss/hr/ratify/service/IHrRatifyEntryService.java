package com.landray.kmss.hr.ratify.service;

import com.landray.kmss.hr.ratify.model.HrRatifyEntry;

public interface IHrRatifyEntryService extends IHrRatifyMainService {

	/**
	 * 入职帐号注册及档案写入，通过附加选项处理
	 * 
	 * @param entry
	 * @throws Exception
	 */
	public abstract void addOrgPerson(HrRatifyEntry entry) throws Exception;

}
