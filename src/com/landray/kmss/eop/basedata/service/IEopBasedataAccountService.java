package com.landray.kmss.eop.basedata.service;

import com.landray.kmss.eop.basedata.model.EopBasedataAccount;

public interface IEopBasedataAccountService extends IEopBasedataBusinessService {
	
	/**
	 * 根据员工id获取员工默认账户
	 * 
	 * @param fdPersonId
	 * @return
	 * @throws Exception
	 */
	public EopBasedataAccount getDefaultEopBasedataAccountByPersonId(String fdPersonId) throws Exception;
}
