package com.landray.kmss.hr.organization.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.organization.model.HrOrganizationRecentContact;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 最近联系人业务对象接口
 * 
 * @author
 * @version 1.0 2015-08-03
 */
public interface IHrOrganizationRecentContactService extends IBaseService {

	public void addContacts(SysOrgPerson person, String[] contactIds)
			throws Exception;

	public void addContact(HrOrganizationRecentContact contact)
			throws Exception;

	public HrOrganizationRecentContact findByUserAndContact(String userId,
			String contactId) throws Exception;

	public void clearOldContacts(SysQuartzJobContext context) throws Exception;

	public List findRecentContacts(String personId, int orgType) throws Exception;

	public List findRecentContacts(String personId, int orgType, int count) throws Exception;

	public void deleteContacts(SysOrgPerson person, String[] contactIds)
			throws Exception;

}
