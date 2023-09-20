package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.dao.ISysOrganizationRecentContactDao;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationRecentContact;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrganizationRecentContactService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrganizationRecentContactConstant;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 最近联系人业务接口实现
 *
 * @author
 * @version 1.0 2015-08-03
 */
public class SysOrganizationRecentContactServiceImp extends BaseServiceImp
		implements ISysOrganizationRecentContactService, SysOrgConstant {
	private static final Logger logger = LoggerFactory.getLogger(SysOrganizationRecentContactServiceImp.class);

	private ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		return sysOrgPersonService;
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		return sysOrgElementService;
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private IOrgRangeService orgRangeService;

	public void setOrgRangeService(IOrgRangeService orgRangeService) {
		this.orgRangeService = orgRangeService;
	}

	@Override
	public void addContacts(SysOrgPerson person, String[] contactIds)
			throws Exception {
		Date current = new Date();
		for (String contactId : contactIds) {
			SysOrgElement element = (SysOrgElement) sysOrgElementService
					.findByPrimaryKey(contactId.trim());
			// 如果选择的是群组或角色线，不插入记录
			if (element.getFdOrgType() > 8) {
				continue;
			}
			SysOrganizationRecentContact contact = new SysOrganizationRecentContact();
			contact.setDocCreateTime(current);
			contact.setFdUser(person);
			contact.setFdContact(element);
			addContact(contact);
		}
	}

	@Override
	public void deleteContacts(SysOrgPerson person, String[] contactIds)
			throws Exception {
		((ISysOrganizationRecentContactDao) getBaseDao()).delContacts(person
				.getFdId(), contactIds);
	}

	@Override
	public void addContact(SysOrganizationRecentContact contact)
			throws Exception {
		// TODO 自动生成的方法存根
		SysOrganizationRecentContact sysOrganizationRecentContact = findByUserAndContact(
				contact.getFdUser().getFdId(), contact.getFdContact().getFdId());
		if (sysOrganizationRecentContact != null) {
			sysOrganizationRecentContact.setDocCreateTime(contact
					.getDocCreateTime());
			update(sysOrganizationRecentContact);
		} else {
			add(contact);
		}
	}

	@Override
	public SysOrganizationRecentContact findByUserAndContact(String userId,
															 String contactId) throws Exception {
		if (StringUtil.isNull(userId) || StringUtil.isNull(contactId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info
				.setWhereBlock("sysOrganizationRecentContact.fdUser.fdId = :userId and sysOrganizationRecentContact.fdContact.fdId = :contactId");
		info.setParameter("userId", userId);
		info.setParameter("contactId", contactId);
		List list = getBaseDao().findList(info);
		if (list != null && list.size() > 0) {
			return (SysOrganizationRecentContact) list.get(0);
		}
		return null;
	}

	@Override
	public List findRecentContacts(String personId, int orgType) {
		return findRecentContacts(personId, orgType,
				SysOrganizationRecentContactConstant.ORG_RECENT_CONTACT_ROW_SIZE, null);
	}

	@Override
	public List findRecentContacts(String personId, int orgType, int count) {
		return findRecentContacts(personId, orgType, count, null);
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
			int i = 0;
			TransactionStatus status = null;
			try {
				status = TransactionUtils.beginNewTransaction();
				for (String personId : personIds) {
					if (i > 0 && i % 200 == 0) {
						TransactionUtils.commit(status);
						status = TransactionUtils.beginNewTransaction();
					}
					((ISysOrganizationRecentContactDao) getBaseDao())
							.clearOldContacts(personId);
					i++;
				}
				TransactionUtils.commit(status);
			} catch (Exception e) {
				context.logError("清理数据失败", e);
				if (status != null) {
					TransactionUtils.rollback(status);
				}
			}
		} catch (Exception e) {
			context.logError("清理数据失败", e);
		}
		Date end = new Date();
		context.logMessage("清除旧联系人数据结束，共对" + personCount
				+ "个用户的最近联系人数据执行了清理操作，共耗时" + (end.getTime() - start.getTime())
				+ "毫秒");
	}

	@Override
	public List findRecentContacts(String personId, int orgType, Boolean isExternal) {
		return findRecentContacts(personId, orgType, SysOrganizationRecentContactConstant.ORG_RECENT_CONTACT_ROW_SIZE,
				isExternal);
	}

	@Override
	public List findRecentContacts(String personId, int orgType, int count, Boolean isExternal) {
		return findRecentContacts(personId, orgType, count, isExternal, null);
	}

	@Override
	public List findRecentContactsByCate(String personId, int orgType, String cateId, Boolean isExternal) {
		return findRecentContacts(personId, orgType, SysOrganizationRecentContactConstant.ORG_RECENT_CONTACT_ROW_SIZE,
				isExternal, cateId);
	}

	@Override
	public List findRecentContactsByCate(String personId, int orgType, int count, String cateId, Boolean isExternal) {
		return findRecentContacts(personId, orgType, count, isExternal, cateId);
	}

	/**
	 * 查询最近联系人
	 * @param personId
	 * @param orgType
	 * @param count
	 * @param isExternal
	 * @param cateId
	 * @return
	 */
	private List findRecentContacts(String personId, int orgType, int count, Boolean isExternal, String cateId) {
		List<SysOrgElement> es = new ArrayList<SysOrgElement>();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setFromBlock("SysOrgElement sysOrgElement ,SysOrganizationRecentContact sysOrganizationRecentContact");
			String whereBlock = "sysOrgElement.fdOrgType < 16 and sysOrgElement.fdId=sysOrganizationRecentContact.fdContact.fdId and sysOrganizationRecentContact.fdUser.fdId = :userId ";
			whereBlock = SysOrgHQLUtil.buildWhereBlock(orgType, whereBlock, "sysOrgElement");
			hqlInfo.setParameter("userId", personId);
			if (SysOrgEcoUtil.IS_ENABLED_ECO) {
				if (isExternal != null) {
					whereBlock = StringUtil.linkString(whereBlock, " and ", " sysOrgElement.fdIsExternal = :isExternal");
					hqlInfo.setParameter("isExternal", isExternal);
				}
			} else {
				// 未开启生态组织，查询默认值
				whereBlock = StringUtil.linkString(whereBlock, " and ", " (sysOrgElement.fdIsExternal is null or sysOrgElement.fdIsExternal = :isExternal)");
				hqlInfo.setParameter("isExternal", Boolean.FALSE);
			}

			if (StringUtil.isNotNull(cateId)) {
				SysOrgElement cate = (SysOrgElement) sysOrgElementService.findByPrimaryKey(cateId);
				if (cate != null) {
					whereBlock = StringUtil.linkString(whereBlock, " and ", " sysOrgElement.fdHierarchyId like :cateHid");
					hqlInfo.setParameter("cateHid", cate.getFdHierarchyId() + "%");
				}
			}

			hqlInfo.setWhereBlock(whereBlock);
			// 查询最近联系属于前端操作
			if (!UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "DIALOG_READER");
			}

			hqlInfo.setOrderBy("sysOrganizationRecentContact.docCreateTime desc");
			hqlInfo.setPageNo(1);
			hqlInfo.setRowSize(count);
			// hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			Page page = sysOrgElementService.findPage(hqlInfo);
			es = page.getList();
		} catch (Exception e) {
			logger.error("获取最近联系人失败：", e);
		}
		return es;
	}

}
