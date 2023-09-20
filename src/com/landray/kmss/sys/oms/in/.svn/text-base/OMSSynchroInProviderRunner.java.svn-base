package com.landray.kmss.sys.oms.in;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.oms.OMSConfig;
import com.landray.kmss.sys.oms.SysOMSConstant;
import com.landray.kmss.sys.oms.in.interfaces.IOMSSynchroInProvider;
import com.landray.kmss.sys.oms.in.interfaces.IOrgDept;
import com.landray.kmss.sys.oms.in.interfaces.IOrgElement;
import com.landray.kmss.sys.oms.in.interfaces.IOrgGroup;
import com.landray.kmss.sys.oms.in.interfaces.IOrgOrg;
import com.landray.kmss.sys.oms.in.interfaces.IOrgPerson;
import com.landray.kmss.sys.oms.in.interfaces.IOrgPost;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgOrg;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.IKmssPasswordEncoder;
import com.landray.kmss.sys.organization.service.ISysOrgDeptService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.sys.organization.service.ISysOrgOrgService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.SecureUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

/**
 * 创建日期 2006-11-30
 * 
 * @author 吴兵 接入第三方平台定时同步服务
 */

public class OMSSynchroInProviderRunner implements IOMSSynchroInProviderRunner,
		SysOrgConstant, SysOMSConstant {
	
	public static String OMS_SYNCHRO_IN_BATCH_SIZE="kmss.oms.in.batch.size";

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(OMSSynchroInProviderRunner.class);

	private IOMSSynchroInProvider provider = null;

	private SysQuartzJobContext jobContext = null;

//	private Map kmssCache = null;

	private ISysOrgElementService orgElementService = null;

	private ISysOrgOrgService orgOrgService = null;

	private ISysOrgDeptService orgDeptService = null;

	private ISysOrgPersonService orgPersonService = null;

	private IKmssPasswordEncoder passwordEncoder;

	private ISysOrgPostService orgPostService = null;

	private ISysOrgGroupService orgGroupService = null;

	private ISysOrgCoreService orgCoreService = null;

	public void setOrgElementService(ISysOrgElementService orgElementService) {
		this.orgElementService = orgElementService;
	}

	public void setOrgOrgService(ISysOrgOrgService orgOrgService) {
		this.orgOrgService = orgOrgService;
	}

	public void setOrgDeptService(ISysOrgDeptService orgDeptService) {
		this.orgDeptService = orgDeptService;
	}

	public void setOrgPersonService(ISysOrgPersonService orgPersonService) {
		this.orgPersonService = orgPersonService;
	}

	public void setPasswordEncoder(IKmssPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setOrgPostService(ISysOrgPostService orgPostService) {
		this.orgPostService = orgPostService;
	}

	public void setOrgGroupService(ISysOrgGroupService orgGroupService) {
		this.orgGroupService = orgGroupService;
	}

	public void setOrgCoreService(ISysOrgCoreService orgCoreService) {
		this.orgCoreService = orgCoreService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void synchro(IOMSSynchroInProvider provider,
			SysQuartzJobContext jobContext) throws Exception {
		jobContext.logMessage("========" + provider.getKey() + "=========");
		this.provider = provider;
		this.jobContext = jobContext;
//		this.kmssCache = new HashMap();
		provider.init();
		try {
			String[] deleteKeywords = provider.getKeywordsForDelete();
			TransactionStatus status = TransactionUtils.beginNewTransaction();
			try{
				delete(deleteKeywords);
				provider.terminate();
				TransactionUtils.getTransactionManager().commit(status);
			}catch(Exception ex){
				TransactionUtils.getTransactionManager().rollback(status);
				jobContext.logMessage("删除数据时出错:" + ex.getMessage());
				throw ex;
			}
			Date start = new Date();
			List updateRecords = provider.getRecordsForUpdate();
			Date end = new Date();
			logger.debug("获取更新记录数耗时： "+((end.getTime()-start.getTime())/1000)+"秒");
			if (updateRecords != null && !updateRecords.isEmpty()) {
				int size = updateRecords.size();
				if (size > 5) {
                    orgElementService.setNotToUpdateRelation(true);
                }
				start = new Date();
				prepare(updateRecords);
				end = new Date();
				logger.debug("更新记录准备prepare数耗时： "+((end.getTime()-start.getTime())/1000)+"秒");
				start = new Date();
				update(updateRecords);
				end = new Date();
				logger.debug("更新记录update数耗时： "+((end.getTime()-start.getTime())/1000)+"秒");
				start = new Date();
				if (size > 5) {
                    orgElementService.updateRelation();
                }
				end = new Date();
				logger.debug("更新记录关系updateRelation数耗时： "+((end.getTime()-start.getTime())/1000)+"秒");
			}
		} finally {
			this.provider = null;
			this.jobContext = null;
//			this.kmssCache.clear();
//			this.kmssCache = null;
			orgElementService.setNotToUpdateRelation(null);
			provider.terminate();
		}
	}

	private void prepare(List elements) throws Exception {
		jobContext.logMessage("准备所要更新的记录数::" + elements.size() + "条记录");
		logger.debug("准备所要更新的记录数::" + elements.size() + "条记录");
		long time = System.currentTimeMillis();
		String batchUpdateSizeString = new OMSConfig().getValue(OMS_SYNCHRO_IN_BATCH_SIZE);
		int batchUpdateSize = 0;
		if(StringUtil.isNotNull(batchUpdateSizeString)){
			batchUpdateSize = Integer.parseInt(batchUpdateSizeString);
		} 
		TransactionStatus status = TransactionUtils.beginNewTransaction();
		long starttime = System.currentTimeMillis();
		try{
		for (int i = 0; i < elements.size(); i++) {
			long pertime = System.currentTimeMillis();
				if (batchUpdateSize > 0 && i >= (batchUpdateSize - 1)
						&& (i % batchUpdateSize  == 0)) {
					TransactionUtils.getTransactionManager().commit(status);
					status = TransactionUtils.beginNewTransaction();
					logger.debug(i+"本批次准备提交时间："+((System.currentTimeMillis()-starttime)/1000)+"秒.");
					starttime=System.currentTimeMillis();
				}
				Object object = (Object) elements.get(i);
				if (object instanceof IOrgOrg) {
					IOrgOrg org = (IOrgOrg) object;
					SysOrgOrg sysOrgOrg = (SysOrgOrg) getSynchroOrgRecord(org,
							ORG_TYPE_ORG);
					setSysOrgOrg((IOrgOrg) object, sysOrgOrg);
					org.setSysOrgElement(sysOrgOrg);
				}
				if (object instanceof IOrgDept) {
					IOrgDept dept = (IOrgDept) object;
					// 由于部门在EKP中可能会被映射成机构，所以对部门的处理不已OrgDept处理，而是以OrgElement处理
					SysOrgElement sysOrgDept = (SysOrgElement) getSynchroOrgRecord(
							dept, ORG_TYPE_DEPT);
					setSysOrgDept((IOrgDept) object, sysOrgDept);
					dept.setSysOrgElement(sysOrgDept);
				}
				if (object instanceof IOrgPerson) {
					IOrgPerson person = (IOrgPerson) object;
					SysOrgPerson sysOrgPerson = (SysOrgPerson) getSynchroOrgRecord(
							person, ORG_TYPE_PERSON);
					setSysOrgPerson((IOrgPerson) object, sysOrgPerson);
					person.setSysOrgElement(sysOrgPerson);
				}
				if (object instanceof IOrgPost) {
					IOrgPost post = (IOrgPost) object;
					SysOrgPost sysOrgPost = (SysOrgPost) getSynchroOrgRecord(
							post, ORG_TYPE_POST);
					setSysOrgPost((IOrgPost) object, sysOrgPost);
					post.setSysOrgElement(sysOrgPost);
				}
				if (object instanceof IOrgGroup) {
					IOrgGroup group = (IOrgGroup) object;
					SysOrgGroup sysOrgGroup = (SysOrgGroup) getSynchroOrgRecord(
							group, ORG_TYPE_GROUP);
					setSysOrgGroup((IOrgGroup) object, sysOrgGroup);
					group.setSysOrgElement(sysOrgGroup);
				}

				if (i % 100 == 0 && jobContext.getLogger().isDebugEnabled()) {
					// orgElementService.flushHibernateSession();
					jobContext.getLogger().debug(
							"prepare counter..............." + i);
					
				}
				if (i % 100 == 0 ) {
					logger.debug(i+"本批次单次准备时间："+((System.currentTimeMillis()-pertime))+"毫秒.对象："+object.getClass());
				}
				if (batchUpdateSize > 0 && i >= (batchUpdateSize - 1)
						&& ( i % batchUpdateSize  == 0)) {
					logger.debug(i+"本批次准备时间："+((System.currentTimeMillis()-starttime))+"毫秒.对象："+object.getClass());
				}
			}
			TransactionUtils.getTransactionManager().commit(status);
		}catch(Exception ex){
			TransactionUtils.getTransactionManager().rollback(status);
			jobContext.logMessage("准备数据时出错:" + ex.getMessage());
			throw ex;
		}
		jobContext.logMessage("=======准备所要更新的数据计耗时::"
				+ ((System.currentTimeMillis() - time) / 1000) + "s=====");
	}

	@SuppressWarnings("unchecked")
	private void update(List elements) throws Exception {
		jobContext.logMessage("更新组织机构记录数::" + elements.size() + "条记录");
		logger.debug("更新组织机构记录数::" + elements.size() + "条记录");
		long time = System.currentTimeMillis();
		String batchUpdateSizeString = new OMSConfig().getValue(OMS_SYNCHRO_IN_BATCH_SIZE);
		int batchUpdateSize = 0;
		if(StringUtil.isNotNull(batchUpdateSizeString)){
			batchUpdateSize = Integer.parseInt(batchUpdateSizeString);
		} 
		TransactionStatus status = TransactionUtils.beginNewTransaction();
		long starttime = System.currentTimeMillis();
		
		try{

		for (int i = 0; i < elements.size(); i++) {
			long pertime = System.currentTimeMillis();
			Object object = (Object) elements.get(i);
			if (batchUpdateSize > 0 && i >= (batchUpdateSize - 1)
					&& (i % batchUpdateSize  == 0)) {
				TransactionUtils.getTransactionManager().commit(status);
				status = TransactionUtils.beginNewTransaction();
				logger.debug(i+"本批次更新提交时间："+((System.currentTimeMillis()-starttime)/1000)+"秒.");
				starttime=System.currentTimeMillis();
			}
			if (object instanceof IOrgOrg) {
				IOrgOrg org = (IOrgOrg) object;
				SysOrgOrg sysOrgOrg = (SysOrgOrg) getSynchroOrgRecord(org
						.getImportInfo());
				if (org.getIsAvailable().booleanValue()) {
					setOrgParent(sysOrgOrg, org);
					setThisLeader(sysOrgOrg, org);
					setSuperLeader(sysOrgOrg, org);
				}
				orgOrgService.update(sysOrgOrg);
			}
			if (object instanceof IOrgDept) {
				IOrgDept dept = (IOrgDept) object;
				// 由于部门在EKP中可能会被映射成机构，所以对部门的处理不已OrgDept处理，而是以OrgElement处理
				SysOrgElement sysOrgDept = (SysOrgElement) getSynchroOrgRecord(dept
						.getImportInfo());
				if (dept.getIsAvailable().booleanValue()) {
					setOrgParent(sysOrgDept, dept);
					setThisLeader(sysOrgDept, dept);
					setSuperLeader(sysOrgDept, dept);
				}
				if (sysOrgDept instanceof SysOrgOrg) {
					orgOrgService.update(sysOrgDept);
				} else {
					orgDeptService.update(sysOrgDept);
				}
			}
			if (object instanceof IOrgPerson) {
				IOrgPerson person = (IOrgPerson) object;
				SysOrgPerson sysOrgPerson = (SysOrgPerson) getSynchroOrgRecord(person
						.getImportInfo());
				if (person.getIsAvailable().booleanValue()) {
					setOrgParent(sysOrgPerson, person);
					setThisLeader(sysOrgPerson, person);
					setPosts(sysOrgPerson, person);
				}
				orgPersonService.update(sysOrgPerson);
			}
			if (object instanceof IOrgPost) {
				IOrgPost post = (IOrgPost) object;
				SysOrgPost sysOrgPost = (SysOrgPost) getSynchroOrgRecord(post
						.getImportInfo());
				if (post.getIsAvailable().booleanValue()) {
					setOrgParent(sysOrgPost, post);
					setPersons(sysOrgPost, post);
					setThisLeader(sysOrgPost, post);
				}
				orgPostService.update(sysOrgPost);
			}
			if (object instanceof IOrgGroup) {
				IOrgGroup group = (IOrgGroup) object;
				SysOrgGroup sysOrgGroup = (SysOrgGroup) getSynchroOrgRecord(group
						.getImportInfo());
				if (group.getIsAvailable().booleanValue()) {
					setMembers(sysOrgGroup, group);
				}
				orgGroupService.update(sysOrgGroup);
			}
			if (i % 100 == 0 && jobContext.getLogger().isDebugEnabled()) {
				jobContext.getLogger().debug(
						"update counter..............." + i);
				logger.debug(i+"本批次单次更新时间："+((System.currentTimeMillis()-pertime))+"毫秒.对象："+object.getClass());
			}

			if (i % 100 == 0 ) {
				logger.debug(i+"本批次单次更新时间："+((System.currentTimeMillis()-pertime))+"毫秒.对象："+object.getClass());
			}
			if (batchUpdateSize > 0 && i >= (batchUpdateSize - 1)
					&& ( i % batchUpdateSize  == 0)) {
				logger.debug(i+"本批次更新时间："+((System.currentTimeMillis()-starttime))+"毫秒.对象："+object.getClass());
			}
		}
		TransactionUtils.getTransactionManager().commit(status);
		}catch(Exception ex){
			TransactionUtils.getTransactionManager().rollback(status);
			jobContext.logMessage("更新数据时出错:" + ex.getMessage());
			throw ex;
		}
		jobContext.logMessage("同步更新耗时::" + (System.currentTimeMillis() - time)
				/ 1000 + "s");
	}

	private void delete(String[] keywords) throws Exception {
		if (keywords == null) {
            return;
        }
		jobContext.logMessage("删除组织机构记录数::" + keywords.length + "条记录");
		logger.debug("删除组织机构记录数::" + keywords.length + "条记录");
		long time = System.currentTimeMillis();
		for (int i = 0; i < keywords.length; i++) {
			SysOrgElement sysOrgElement = orgCoreService
					.findByImportInfo(provider.getKey() + keywords[i]);
			if (sysOrgElement != null) {
				sysOrgElement.setFdIsAvailable(new Boolean(false));
				sysOrgElement.getHbmChildren().clear();
				// 停用帐号与删除帐号时，都不要将fdImportInfo清空！不然停用后，再启用同一帐号会出现问题。
				// sysOrgElement.setFdImportInfo(null);
				orgElementService.update(sysOrgElement);
			}
		}
		jobContext.logMessage("删除组织机构耗时::"
				+ (System.currentTimeMillis() - time) / 1000 + "s");
		logger.debug("删除组织机构耗时::"
				+ (System.currentTimeMillis() - time) / 1000 + "s");
	}

	@SuppressWarnings("unchecked")
	private SysOrgElement getSynchroOrgRecord(IOrgElement element, int type)
			throws Exception {
		SysOrgElement sysOrgElement = null;
		if (element.getRecordStatus() == SysOrgConstant.OMS_OP_FLAG_UPDATE) {
			sysOrgElement = getSynchroOrgRecord(element.getImportInfo());
		}
		String keyword = provider.getKey() + element.getImportInfo();
		if (sysOrgElement == null) {
			switch (type) {
			case ORG_TYPE_ORG:
				sysOrgElement = new SysOrgOrg();
				if (StringUtil.isNotNull(element.getId())) {
					sysOrgElement.setFdId(element.getId());
				}
				setSysOrgOrg((IOrgOrg) element, (SysOrgOrg) sysOrgElement);
				orgOrgService.add(sysOrgElement);
				break;
			case ORG_TYPE_DEPT:
				sysOrgElement = new SysOrgDept();
				if (StringUtil.isNotNull(element.getId())) {
					sysOrgElement.setFdId(element.getId());
				}
				setSysOrgDept((IOrgDept) element, (SysOrgDept) sysOrgElement);
				orgDeptService.add(sysOrgElement);
				break;
			case ORG_TYPE_PERSON:
				sysOrgElement = new SysOrgPerson();
				if (StringUtil.isNotNull(element.getId())) {
					sysOrgElement.setFdId(element.getId());
				}
				setSysOrgPerson((IOrgPerson) element,
						(SysOrgPerson) sysOrgElement);
				orgPersonService.add(sysOrgElement);
				break;
			case ORG_TYPE_POST:
				sysOrgElement = new SysOrgPost();
				if (StringUtil.isNotNull(element.getId())) {
					sysOrgElement.setFdId(element.getId());
				}
				setSysOrgPost((IOrgPost) element, (SysOrgPost) sysOrgElement);
				orgPostService.add(sysOrgElement);
				break;
			case ORG_TYPE_GROUP:
				sysOrgElement = new SysOrgGroup();
				if (StringUtil.isNotNull(element.getId())) {
					sysOrgElement.setFdId(element.getId());
				}
				setSysOrgGroup((IOrgGroup) element, (SysOrgGroup) sysOrgElement);
				orgGroupService.add(sysOrgElement);
				break;
			}
//			kmssCache.put(keyword, sysOrgElement);
		}
		return sysOrgElement;
	}

	@SuppressWarnings("unchecked")
	private SysOrgElement getSynchroOrgRecord(String keyword) throws Exception {
		keyword = provider.getKey() + keyword;
//		if (kmssCache.get(keyword) != null) {
//			return (SysOrgElement) kmssCache.get(keyword);
//		}

		SysOrgElement sysOrgElement = orgCoreService.format(orgCoreService
				.findByImportInfo(keyword));
		if (sysOrgElement == null) {
            return null;
        }
//		kmssCache.put(keyword, sysOrgElement);
		return sysOrgElement;
	}

	private void setSysOrgElement(IOrgElement element,
			SysOrgElement sysOrgElement) throws Exception {
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("no")) {
			sysOrgElement.setFdNo(element.getNo());
		}
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("name")) {
			sysOrgElement.setFdName(element.getName());
		}
		sysOrgElement.setFdAlterTime(new Date());
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("order")) {
			sysOrgElement.setFdOrder(element.getOrder());
		}
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("keyword")) {
			sysOrgElement.setFdKeyword(element.getKeyword());
		}
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("memo")) {
			sysOrgElement.setFdMemo(element.getMemo());
		}
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("importInfo")) {
			sysOrgElement.setFdImportInfo(provider.getKey()
					+ element.getImportInfo());
		}
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("isAvailable")) {
			sysOrgElement.setFdIsAvailable(element.getIsAvailable());
		}
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("isBusiness")) {
			sysOrgElement.setFdIsBusiness(element.getIsBusiness());
		}
	}

	private void setSysOrgPerson(IOrgPerson person, SysOrgPerson sysOrgPerson)
			throws Exception {
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("loginName")) {
			setPersonAccount(person, sysOrgPerson);
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("password")) {
			setPersonPassord(person, sysOrgPerson);
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("email")) {
			sysOrgPerson.setFdEmail(person.getEmail());
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("mobileNo")) {
			sysOrgPerson.setFdMobileNo(person.getMobileNo());
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("attendanceCardNumber")) {
			sysOrgPerson.setFdAttendanceCardNumber(person
					.getAttendanceCardNumber());
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("workPhone")) {
			sysOrgPerson.setFdWorkPhone(person.getWorkPhone());
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("lang")) {
			sysOrgPerson.setFdDefaultLang(person.getLang());
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("rtx")) {
			sysOrgPerson.setFdRtxNo(person.getRtx());
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("wechat")) {
			sysOrgPerson.setFdWechatNo(person.getWechat());
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("sex")) {
			sysOrgPerson.setFdSex(person.getSex());
		}
		setSysOrgElement(person, sysOrgPerson);
	}

	private void setPersonAccount(IOrgPerson person, SysOrgPerson sysOrgPerson) {
		if (sysOrgPerson.getFdId() != null) {
			if ((provider.getAccountType() & ACCOUNT_TYPE_SYNCHRO_UPDATE) == ACCOUNT_TYPE_SYNCHRO_UPDATE) {
				sysOrgPerson.setFdLoginName(person.getLoginName());
			}
		} else {
			sysOrgPerson.setFdLoginName(person.getLoginName());
		}
	}

	private void setPersonPassord(IOrgPerson person, SysOrgPerson sysOrgPerson)
			throws Exception {
		if ((provider.getPasswordType() & PASSWORD_TYPE_REQUIRED) == PASSWORD_TYPE_REQUIRED) {
			String password = person.getPassword();
			if ((provider.getPasswordType()
					& PASSWORD_TYPE_NOT_TRANSFER) != PASSWORD_TYPE_NOT_TRANSFER) {
				String plain = SecureUtil.BASE64Decoder(person.getPassword());
				password = passwordEncoder.encodePassword(plain);
			}
			if ((provider.getPasswordType() & PASSWORD_TYPE_CREATE_SYNCHRO) == PASSWORD_TYPE_CREATE_SYNCHRO) {
				if (person.getRecordStatus() == SysOrgConstant.OMS_OP_FLAG_ADD
						&& sysOrgPerson != null) {
					sysOrgPerson.setFdPassword(password);
					sysOrgPerson.setFdInitPassword(
							PasswordUtil.desEncrypt(person.getPassword()));
				} else if (sysOrgPerson != null
						&& orgPersonService.findByPrimaryKey(sysOrgPerson
								.getFdId(), SysOrgPerson.class, true) == null) {
					sysOrgPerson.setFdPassword(password);
					sysOrgPerson.setFdInitPassword(
							PasswordUtil.desEncrypt(person.getPassword()));
				}
			} else {
				sysOrgPerson.setFdPassword(password);
				sysOrgPerson.setFdInitPassword(
						PasswordUtil.desEncrypt(person.getPassword()));
			}
		}

	}

	private void setSysOrgOrg(IOrgOrg org, SysOrgOrg sysOrgOrg)
			throws Exception {
		setSysOrgElement(org, sysOrgOrg);
	}

	private void setSysOrgDept(IOrgDept dept, SysOrgElement sysOrgDept)
			throws Exception {
		setSysOrgElement(dept, sysOrgDept);
	}

	private void setSysOrgPost(IOrgPost post, SysOrgPost sysOrgPost)
			throws Exception {
		setSysOrgElement(post, sysOrgPost);
	}

	private void setSysOrgGroup(IOrgGroup group, SysOrgGroup sysOrgGroup)
			throws Exception {
		setSysOrgElement(group, sysOrgGroup);
	}

	private void setOrgParent(SysOrgElement sysOrgElement, IOrgElement element)
			throws Exception {
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("parent")) {
			if (element.getParent() == null) {
				sysOrgElement.setFdParent(null);
				return;
			}
			SysOrgElement parent = (SysOrgElement) getSynchroOrgRecord(element
					.getParent());
			if (parent != null && parent.getFdIsAvailable().booleanValue()) {
				sysOrgElement.setFdParent(parent);
			}
		}
	}

	private void setThisLeader(SysOrgElement sysOrgElement, IOrgElement element)
			throws Exception {
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("thisLeader")) {
			if (!((provider.getLeaderType() & LEADER_TYPE_REQUIRED) == LEADER_TYPE_REQUIRED)) {
                return;
            }
			if (element.getThisLeader() == null) {
				sysOrgElement.setHbmThisLeader(null);
				return;
			}
			SysOrgElement thisLeader = (SysOrgElement) getSynchroOrgRecord(element
					.getThisLeader());
			if (thisLeader != null
					&& thisLeader.getFdIsAvailable().booleanValue()) {
				sysOrgElement.setHbmThisLeader(thisLeader);
			}
		}
	}

	private void setSuperLeader(SysOrgElement sysOrgElement, IOrgElement element)
			throws Exception {
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("superLeader")) {
			if (!((provider.getLeaderType() & LEADER_TYPE_REQUIRED) == LEADER_TYPE_REQUIRED)) {
                return;
            }
			if (element.getSuperLeader() == null) {
				sysOrgElement.setHbmSuperLeader(null);
				return;
			}
			SysOrgElement superLeader = (SysOrgElement) getSynchroOrgRecord(element
					.getSuperLeader());
			if (superLeader != null
					&& superLeader.getFdIsAvailable().booleanValue()) {
				sysOrgElement.setHbmSuperLeader(superLeader);
			}
		}
	}

	private void setPosts(SysOrgPerson sysOrgPerson, IOrgPerson person)
			throws Exception {
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("posts")) {
			String[] posts = person.getPosts();
			if (posts == null) {
				sysOrgPerson.setFdPosts(null);
				return;
			}
			if (provider.getPostType() == (POST_TYPE_REQUIRED
					| POST_TYPE_PERSON | POST_TYPE_SELF)) {
				orgElementService.flushElement(sysOrgPerson);
			}
			List list = new ArrayList();
			for (int i = 0; i < posts.length; i++) {
				if (logger.isDebugEnabled()) {
					logger.debug("post-ref::" + posts[i]);
				}
				list.add(getSynchroOrgRecord(posts[i]));
			}
			if (logger.isDebugEnabled()) {
				logger.debug("posts::" + list);
			}
			sysOrgPerson.setFdPosts(list);
		}
	}

	private void setPersons(SysOrgPost sysOrgPost, IOrgPost post)
			throws Exception {
		if (post.getRequiredOms() == null
				|| post.getRequiredOms().contains("persons")) {
			String[] persons = post.getPersons();
			if (persons == null) {
				sysOrgPost.setFdPersons(null);
				return;
			}
			if (provider.getPostType() == (POST_TYPE_REQUIRED
					| POST_TYPE_PERSON | POST_TYPE_SELF)) {
				orgElementService.flushElement(sysOrgPost);
			}
			List list = new ArrayList();
			for (int i = 0; i < persons.length; i++) {
				list.add(getSynchroOrgRecord(persons[i]));
			}
			sysOrgPost.setFdPersons(list);
		}
	}

	private void setMembers(SysOrgGroup sysOrgGroup, IOrgGroup group)
			throws Exception {
		if (group.getRequiredOms() == null
				|| group.getRequiredOms().contains("members")) {
			String[] members = group.getMembers();
			if (members == null) {
				sysOrgGroup.setFdMembers(null);
				return;
			}
			List list = new ArrayList();
			for (int i = 0; i < members.length; i++) {
				list.add(getSynchroOrgRecord(members[i]));
			}
			sysOrgGroup.setFdMembers(list);
		}
	}
	
}
