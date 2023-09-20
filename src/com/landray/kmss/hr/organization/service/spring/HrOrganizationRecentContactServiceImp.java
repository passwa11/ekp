package com.landray.kmss.hr.organization.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.organization.constant.HrOrganizationRecentContactConstant;
import com.landray.kmss.hr.organization.dao.IHrOrganizationRecentContactDao;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationRecentContact;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.service.IHrOrganizationRecentContactService;
import com.landray.kmss.hr.organization.util.HrOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.HQLUtil;
import com.landray.sso.client.util.StringUtil;
import com.sunbor.web.tag.Page;

/**
 * 最近联系人业务接口实现
 * 
 * @author
 * @version 1.0 2015-08-03
 */
public class HrOrganizationRecentContactServiceImp extends BaseServiceImp
		implements IHrOrganizationRecentContactService, SysOrgConstant {

	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private IHrOrganizationElementService hrOrganizationElementService;

	public void setHrOrganizationElementService(IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	public ISysOrgPersonService getSysOrgPersonService() {
		return sysOrgPersonService;
	}

	public IHrOrganizationElementService getHrOrganizationElementService() {
		return hrOrganizationElementService;
	}

	@Override
	public void addContacts(SysOrgPerson person, String[] contactIds)
			throws Exception {
		Date current = new Date();
		for (String contactId : contactIds) {
			HrOrganizationElement element = (HrOrganizationElement) hrOrganizationElementService
					.findByPrimaryKey(contactId.trim());
			HrOrganizationRecentContact contact = new HrOrganizationRecentContact();
			contact.setDocCreateTime(current);
			contact.setFdUser(person);
			contact.setFdContact(element);
			addContact(contact);
		}
	}

	@Override
	public void deleteContacts(SysOrgPerson person, String[] contactIds)
			throws Exception {
		((IHrOrganizationRecentContactDao) getBaseDao()).delContacts(person
				.getFdId(), contactIds);
	}

	@Override
	public void addContact(HrOrganizationRecentContact contact)
			throws Exception {
		// TODO 自动生成的方法存根
		HrOrganizationRecentContact hrOrganizationRecentContact = findByUserAndContact(
				contact.getFdUser().getFdId(), contact.getFdContact().getFdId());
		if (hrOrganizationRecentContact != null) {
			hrOrganizationRecentContact.setDocCreateTime(contact
					.getDocCreateTime());
			update(hrOrganizationRecentContact);
		} else {
			add(contact);
		}
	}

	@Override
	public HrOrganizationRecentContact findByUserAndContact(String userId,
															String contactId) throws Exception {
		if (StringUtil.isNull(userId) || StringUtil.isNull(contactId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info
				.setWhereBlock(
						"hrOrganizationRecentContact.fdUser.fdId = :userId and hrOrganizationRecentContact.fdContact.fdId = :contactId");
		info.setParameter("userId", userId);
		info.setParameter("contactId", contactId);
		List list = getBaseDao().findList(info);
		if (list != null && list.size() > 0) {
			return (HrOrganizationRecentContact) list.get(0);
		}
		return null;
	}

	@Override
	public List findRecentContacts(String personId, int orgType) throws Exception {
		return findRecentContacts(personId, orgType,
				HrOrganizationRecentContactConstant.ORG_RECENT_CONTACT_ROW_SIZE);
	}

	@Override
	public List findRecentContacts(String personId, int orgType, int count) throws Exception {
		List<HrOrganizationElement> es = new ArrayList<HrOrganizationElement>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdContact.fdId");
		hqlInfo.setOrderBy("hrOrganizationRecentContact.docCreateTime desc");
		hqlInfo.setWhereBlock("hrOrganizationRecentContact.fdUser.fdId = :userId");
		hqlInfo.setParameter("userId", personId);
		List list = this.findList(hqlInfo);

		hqlInfo = new HQLInfo();
		String whereBlock = "hrOrganizationElement.fdOrgType <16 and " + HQLUtil.buildLogicIN("fdId", list);
		whereBlock = HrOrgHQLUtil.buildWhereBlock(orgType, whereBlock, "hrOrganizationElement");
		hqlInfo.setWhereBlock(whereBlock);

		hqlInfo.setAuthCheckType("DIALOG_READER");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(count);
		try {
			//es = hrOrganizationElementService.findList(hqlInfo);
			Page page = hrOrganizationElementService.findPage(hqlInfo);
			es = page.getList();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return es;

	}

	@Override
	public void clearOldContacts(SysQuartzJobContext context) {
		context.logMessage("清除旧联系人数据开始");
		Date start = new Date();
		int personCount = 0;
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("sysOrgPerson.fdId");
			List<String> personIds = (List<String>) sysOrgPersonService
					.findList(hqlInfo);
			personCount = personIds.size();
			for (String personId : personIds) {
				((IHrOrganizationRecentContactDao) getBaseDao())
						.clearOldContacts(personId);
			}
		} catch (Exception e) {
			context.logError("清理数据失败", e);
		}
		Date end = new Date();
		context.logMessage("清除旧联系人数据结束，共对" + personCount
				+ "个用户的最近联系人数据执行了清理操作，共耗时" + (end.getTime() - start.getTime())
				+ "毫秒");
	}

}
