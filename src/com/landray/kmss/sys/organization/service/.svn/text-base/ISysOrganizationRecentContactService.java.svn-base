package com.landray.kmss.sys.organization.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationRecentContact;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 最近联系人业务对象接口
 * 
 * @author
 * @version 1.0 2015-08-03
 */
public interface ISysOrganizationRecentContactService extends IBaseService {

	public void addContacts(SysOrgPerson person, String[] contactIds)
			throws Exception;

	public void addContact(SysOrganizationRecentContact contact)
			throws Exception;

	public SysOrganizationRecentContact findByUserAndContact(String userId,
			String contactId) throws Exception;

	public void clearOldContacts(SysQuartzJobContext context) throws Exception;

	public List findRecentContacts(String personId, int orgType);

	public List findRecentContacts(String personId, int orgType, int count);

	public List findRecentContacts(String personId, int orgType, Boolean isExternal);

	public List findRecentContacts(String personId, int orgType, int count, Boolean isExternal);

	public List findRecentContactsByCate(String personId, int orgType, String cateId, Boolean isExternal);

	public List findRecentContactsByCate(String personId, int orgType, int count, String cateId, Boolean isExternal);

	public void deleteContacts(SysOrgPerson person, String[] contactIds)
			throws Exception;

}
