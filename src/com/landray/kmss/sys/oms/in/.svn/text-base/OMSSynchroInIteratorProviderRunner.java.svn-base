package com.landray.kmss.sys.oms.in;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.oms.OMSConfig;
import com.landray.kmss.sys.oms.OMSPlugin;
import com.landray.kmss.sys.oms.SysOMSConstant;
import com.landray.kmss.sys.oms.in.interfaces.*;
import com.landray.kmss.sys.oms.model.*;
import com.landray.kmss.sys.oms.notify.service.ISynchroOrgNotify;
import com.landray.kmss.sys.oms.service.*;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.*;
import com.landray.kmss.sys.organization.service.spring.KmssPasswordEncoder;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.TimestampUtil;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionStatus;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.Date;
import java.util.*;

public class OMSSynchroInIteratorProviderRunner implements
		IOMSSynchroInIteratorProviderRunner, SysOrgConstant, SysOMSConstant,
		BaseTreeConstant {
	public static String OMS_SYNCHRO_IN_BATCH_SIZE = "kmss.oms.in.batch.size";

	public static String OMS_SYNCHRO_IN_DELETE_SIZE = "kmss.oms.in.delete.size";

	public static String OMS_SYNCHRO_IN_ORGANIZATION_BACKUP = "kmss.oms.in.organization.backup";

	private ISynchroOrgNotify synchroOrgNotify;

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(OMSSynchroInIteratorProviderRunner.class);

	private IOMSSynchroInIteratorProvider provider = null;

	private Date lastUpdateTime = null;

	private SysQuartzJobContext jobContext = null;

	private ISysOrgElementService orgElementService = null;

	private ISysOrgOrgService orgOrgService = null;

	private ISysOrgDeptService orgDeptService = null;

	private ISysOrgPersonService orgPersonService = null;

	private IKmssPasswordEncoder passwordEncoder;

	private ISysOrgPostService orgPostService = null;

	private ISysOrgGroupService orgGroupService = null;

	private ISysOrgCoreService orgCoreService = null;

	private ISysOrgElementBakService sysOrgElementBakService = null;

	private ISysOmsEcoSyncService sysOmsEcoSyncService;

	private Map<Integer, List<String>> requiredOmsMap = null;

	public void setSynchroOrgNotify(ISynchroOrgNotify synchroOrgNotify) {
		this.synchroOrgNotify = synchroOrgNotify;
	}

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

	public void setSysOmsEcoSyncService(
			ISysOmsEcoSyncService sysOmsEcoSyncService) {
		this.sysOmsEcoSyncService = sysOmsEcoSyncService;
	}

	public void setSysOmsOrgService(ISysOmsOrgService sysOmsOrgService) {
		this.sysOmsOrgService = sysOmsOrgService;
	}

	public void setSysOmsDeptService(ISysOmsDeptService sysOmsDeptService) {
		this.sysOmsDeptService = sysOmsDeptService;
	}

	private DataSource dataSource;

	private DataSource getDataSource() {
		if (dataSource == null) {
			dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		}
		return dataSource;
	}

	@Override
	public SysQuartzJobContext getJobContext() {
		return jobContext;
	}

	public void
	setSysOmsPersonService(ISysOmsPersonService sysOmsPersonService) {
		this.sysOmsPersonService = sysOmsPersonService;
	}

	public void setSysOmsPostService(ISysOmsPostService sysOmsPostService) {
		this.sysOmsPostService = sysOmsPostService;
	}

	public void setSysOmsGroupService(ISysOmsGroupService sysOmsGroupService) {
		this.sysOmsGroupService = sysOmsGroupService;
	}

	private ISysOmsOrgService sysOmsOrgService;
	private ISysOmsDeptService sysOmsDeptService;
	private ISysOmsPersonService sysOmsPersonService;
	private ISysOmsPostService sysOmsPostService;
	private ISysOmsGroupService sysOmsGroupService;

	private long startTime;
	private long lastEchoTime;
	private int updated;
	private int updated_null;
	private List<String[]> deptcache;

	@Override
	public List<String[]> getDeptcache() {
		if (CollectionUtils.isEmpty(deptcache)) {
			deptcache = new ArrayList<String[]>();
		}
		return deptcache;
	}

	private void echoInfo() {
		long elapsedTime = System.currentTimeMillis() - lastEchoTime;
		if (elapsedTime > (60 * 1000)) {
			lastEchoTime = System.currentTimeMillis();
			String info = "";
			long xxx = (lastEchoTime - startTime) / 1000;
			if (xxx > 3600) {
				info += "" + xxx / 3600 + "小时,";
				xxx = xxx % 3600;
			}
			if (xxx > 60) {
				info += "" + xxx / 60 + "分钟,";
				xxx = xxx % 60;
			}
			info += "" + xxx + "秒";

			if (updated > 0) {
				jobContext.logMessage("组织架构同步正在运行：已经运行" + info + ".更新数目:" + updated);
				if (logger.isInfoEnabled()) {
					logger.info("组织架构同步正在运行：已经运行" + info + ".更新数目:" + updated);
				}
			} else {
				jobContext.logMessage("组织架构同步正在运行：已经运行" + info + ".");
				if (logger.isInfoEnabled()) {
					logger.info("组织架构同步正在运行：已经运行" + info + ".");
				}
			}
		}
	}

	/**
	 * 根据importInfo和组织类型查找对应的记录
	 *
	 * @param keyword
	 * @param orgType
	 * @return
	 * @throws Exception
	 */
	private SysOrgElement getSynchroOrgRecord(String keyword, int orgType)
			throws Exception {
		keyword = provider.getKey() + keyword;
		SysOrgElement sysOrgElement = orgCoreService.format(orgCoreService
				.findByImportInfoAndOrgtype(keyword, orgType));
		if (sysOrgElement == null) {
            return null;
        }
		return sysOrgElement;
	}

	/**
	 * 删除无效的组织
	 *
	 * @throws Exception
	 */
	private void flagDeleted() throws Exception {
		logger.debug("设置组织架构待删除标识");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getDataSource().getConnection();
			conn.setAutoCommit(false);
			ps = conn
					.prepareStatement(
							"update sys_org_element set fd_flag_deleted = ? where fd_is_external = ? and fd_import_info like '"
									+ provider.getKey() + "%'");
			ps.setBoolean(1, true);
			ps.setBoolean(2, false);

			ps.executeUpdate();
			conn.commit();
		} catch (Exception ex) {
			logger.error("设置待删除标识时出错", ex);
			conn.rollback();
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
	}

	/**
	 * 处理新增和删除的记录
	 *
	 * @throws Exception
	 */
	private void handleDelAndAdd() throws Exception {
		if ("com.landray.kmss.third.ekp.java.oms.in.EkpSynchroInIteratorProviderImp"
				.equals(provider.getKey())) {
			// ekp-j同步不需要处理删除的记录，因为ekp组织架构不存在物理删除的情况
			Set<String> notDelete = new HashSet<String>();
			importAllRecordBaseAttributes(
					provider.getAllRecordBaseAttributes(), notDelete);
		} else {
			flagDeleted(); // 将所有组织架构flagDelete的值设置为1
			// 不需要删除的记录
			Set<String> notDelete = new HashSet<String>();
			importAllRecordBaseAttributes(
					provider.getAllRecordBaseAttributes(), notDelete);
			String[] deleteKeywords = getKeywordsForDelete(provider.getKey());
			Set<String> toDelete = new HashSet<String>();
			Collections.addAll(toDelete, deleteKeywords);
			// 减去不需要删除的记录
			toDelete.removeAll(notDelete);
			echoInfo();
			TransactionStatus deleteStatus = TransactionUtils
					.beginNewTransaction();
			try {
				// 删除无效组织
				delete(toDelete, provider.getKey());
				TransactionUtils.commit(deleteStatus);
			} catch (Exception ex) {
				TransactionUtils.rollback(deleteStatus);
				throw ex;
			}
		}
	}

	private void handleUpdated() throws Exception {
		insert2Temp();

		this.updated = 0;
		update2Org();
	}

	/**
	 * 获取更新的记录写到临时表中
	 *
	 * @throws Exception
	 */
	private void insert2Temp() throws Exception {
		logger.debug("获取更新数据写到临时表");
		TransactionStatus updateDtatus = null;
		echoInfo();
		int batchUpdateSize = 500;
		int idx = 0;
		IOMSResultSet rs = provider.getSynchroRecords();
		try {
			OmsTempConfig config = new OmsTempConfig();
			config.getDataMap().clear();
			updateDtatus = TransactionUtils.beginNewTransaction();
			while (rs.next()) {
				echoInfo();
				if (batchUpdateSize > 0 && updated > 0
						&& ((updated % batchUpdateSize) == 0)) {
					TransactionUtils.commit(updateDtatus);
					updateDtatus = TransactionUtils.beginNewTransaction();
					idx++;
					if (logger.isInfoEnabled()) {
						logger.info("第" + idx + "批次提交事务");
					}
				}
				IOrgElement element = rs.getElement();
				if (element != null) {
					try {
						// 写入到临时表
						if (logger.isDebugEnabled()) {
							logger.debug("写入临时表，importInfo:{},name:{},ldapDN:{}",
									element.getImportInfo(), element.getName(),
									element.getLdapDN());
						}
						config.setRequiredOms(element);
						addTemp(element);
					} catch (Exception e) {
						jobContext.logMessage("写入临时表出错:importinfo="
								+ element.getImportInfo() + ";error="
								+ e.getMessage());
						logger.error("写入临时表出错:importinfo="
								+ element.getImportInfo() + ";error="
								+ e.getMessage(), e);
					}
					updated++;
				} else {
					logger.debug("element为空");
					updated_null++;
				}
			}
			TransactionUtils.commit(updateDtatus);
			config.saveRequiredOms();
		} catch (Exception ex) {
			TransactionUtils.rollback(updateDtatus);
			throw ex;
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private IBaseService getOmsElementService(Integer type) {
		switch (type) {
			case 1:
				return sysOmsOrgService;
			case 2:
				return sysOmsDeptService;
			case 4:
				return sysOmsPostService;
			case 8:
				return sysOmsPersonService;
			case 16:
				return sysOmsGroupService;
			default:
				return null;
		}
	}

	/**
	 * 更新单条记录
	 *
	 * @param omsElement
	 * @throws Exception
	 */
	private void update2Org(SysOmsElement omsElement) throws Exception {
		echoInfo();
		if (logger.isDebugEnabled()) {
			logger.debug("更新记录详细信息：" + omsElement.getFdImportinfo() + "---" + omsElement.getFdLdapDn());
		}
		try {
			update(omsElement);
			// 设置处理状态为成功
			omsElement.setFdHandleStatus(3);
			updated++;
		} catch (Exception e) {
			jobContext.logMessage("组织架构同步时出错:importinfo="
					+ omsElement.getFdImportinfo() + ";error="
					+ e.getMessage());
			logger.error("组织架构同步时出错:importinfo="
					+ omsElement.getFdImportinfo() + ";error="
					+ e.getMessage(), e);
			// 设置处理状态为失败
			omsElement.setFdHandleStatus(4);
		} finally {
			getOmsElementService(omsElement.getOrgType()).update(omsElement);
		}
	}

	/**
	 * 设置查看范围
	 */
	private ValueMapType[] valueMpaType = new ValueMapType[]{ValueMapType.ORG, ValueMapType.DEPT, ValueMapType.POST, ValueMapType.PERSON};
	private ValueMapTo valueMapTo = ValueMapTo.ID;


	/**
	 * 设置隐藏范围
	 * @description:
	 * @param sysOrgElement
	 * @param json
	 * @return: void
	 * @author: wangjf
	 * @time: 2021/9/30 9:10 上午
	 */
	private void setHideRange(SysOrgElement sysOrgElement, String json) throws Exception {
		HideRange hideRange = null;
		if (StringUtil.isNotNull(json) && JSONValidator.from(json).validate()) {
			hideRange = JSON.parseObject(json, HideRange.class);
		}
		SysOrgElementHideRange fdHideRange = sysOrgElement.getFdHideRange();
		if (fdHideRange == null) {
			fdHideRange = new SysOrgElementHideRange();
		}
		if (hideRange == null) {
			// 默认值
			fdHideRange.setFdIsOpenLimit(false);
			fdHideRange.setFdViewType(1);
		} else {
			fdHideRange.setFdIsOpenLimit(hideRange.getFdIsOpenLimit() == null ? false : hideRange.getFdIsOpenLimit());
			fdHideRange.setFdViewType(hideRange.getFdViewType() == null ? 1 : hideRange.getFdViewType());
			String[] other = hideRange.getFdOther();
			if (other != null && other.length > 0) {
				setHideRangeOther(fdHideRange, hideRange);
			}
		}
		fdHideRange.setFdElement(sysOrgElement);
		sysOrgElement.setFdHideRange(fdHideRange);
	}

	/**
	 * 设置隐藏可见组织或人员
	 * @description:
	 * @param fdHideRange
	 * @param hideRange
	 * @return: void
	 * @author: wangjf
	 * @time: 2021/9/30 9:10 上午
	 */
	private void setHideRangeOther(SysOrgElementHideRange fdHideRange, HideRange hideRange) throws Exception {
		String[] other = hideRange.getFdOther();
		if (other == null || other.length == 0) {
			fdHideRange.setFdOthers(null);
			return;
		}
		if (valueMapTo == null) {
			fdHideRange.setFdOthers(null);
			return;
		}
		List list = getForeignKey(valueMpaType, valueMapTo, other);
		fdHideRange.setFdOthers(list);
	}

	/**
	 * 设置管理员
	 * @description:
	 * @param sysOrgElement
	 * @param json
	 * @return: void
	 * @author: wangjf
	 * @time: 2021/10/18 5:39 下午
	 */
	private void setAuthElementAdmins(SysOrgElement sysOrgElement, String json) throws Exception {
		//说明是异构系统之间的同步比如LDAP，异构系统没有管理员选项，如果是这样情况则不能修改ekp已经设置的管理员值
		//同构系统之间采用""进行说明没有管理员
		if("null".equals(json)){
			return;
		}

		List<String> adminList = null;
		if (StringUtil.isNotNull(json) && JSONValidator.from(json).validate()) {
			adminList = JSON.parseArray(json, String.class);
		}
		if(CollectionUtils.isNotEmpty(adminList)){
			String[] strings = new String[adminList.size()];
			adminList.toArray(strings);
			List list = getForeignKey(valueMpaType, valueMapTo, strings);
			sysOrgElement.setAuthElementAdmins(list);
		}else{
			sysOrgElement.setAuthElementAdmins(new ArrayList<>());
		}
	}

	private void setRange(SysOrgElement sysOrgElement, String json) throws Exception {
		ViewRange viewRange = null;
		if (StringUtil.isNotNull(json) && JSONValidator.from(json).validate()) {
			viewRange = JSON.parseObject(json, ViewRange.class);
		}
		SysOrgElementRange range = sysOrgElement.getFdRange();
		if (range == null) {
			range = new SysOrgElementRange();
		}
		if (viewRange == null) {
			// 默认值
			range.setFdIsOpenLimit(false);
			range.setFdViewType(1);
		} else {
			range.setFdIsOpenLimit(viewRange.getFdIsOpenLimit() == null ? false : viewRange.getFdIsOpenLimit());
			range.setFdViewType(viewRange.getFdViewType() == null ? 1 : viewRange.getFdViewType());
			range.setFdViewSubType(StringUtil.isNull(viewRange.getFdViewSubType()) ? null : viewRange.getFdViewSubType());
			range.setFdInviteUrl(StringUtil.isNull(viewRange.getFdInviteUrl()) ? null : viewRange.getFdInviteUrl());
			String[] other = viewRange.getFdOther();
			if (other != null && other.length > 0) {
				setRangeOther(range, viewRange);
			}
		}
		range.setFdElement(sysOrgElement);
		sysOrgElement.setFdRange(range);
	}

	private void setRangeOther(SysOrgElementRange range, ViewRange viewRange) throws Exception {
		String[] other = viewRange.getFdOther();
		if (other == null || other.length == 0) {
			range.setFdOthers(null);
			return;
		}
		if (valueMapTo == null) {
			range.setFdOthers(null);
			return;
		}
		List list = getForeignKey(valueMpaType, valueMapTo, other);
		range.setFdOthers(list);
	}

	private List findList(int orgType) throws Exception {
		TransactionStatus status = null;
		Exception t = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			List list = Collections.EMPTY_LIST;
			if (orgType == ORG_TYPE_ORG) {
				list = sysOmsOrgService.findList(null, null);
			} else if (orgType == ORG_TYPE_DEPT) {
				list = sysOmsDeptService.findList(null, null);
			} else if (orgType == ORG_TYPE_POST) {
				list = sysOmsPostService.findValue("fdId", null, null);
			} else if (orgType == ORG_TYPE_PERSON) {
				list = sysOmsPersonService.findValue("fdId", null, null);
			} else if (orgType == ORG_TYPE_GROUP) {
				list = sysOmsGroupService.findValue("fdId", null, null);
			}
			TransactionUtils.commit(status);
			return list;
		} catch (Exception e) {
			logger.error("批量更新层级关系失败：", e);
			t = e;
			throw e;
		} finally {
			if (t != null && status != null) {
				TransactionUtils.rollback(status);
			}
		}
	}

	/**
	 * 把临时表中的数据更新到组织架构中
	 *
	 * @throws Exception
	 */
	private void update2Org() throws Exception {
		logger.debug("开始增量更新记录");
		echoInfo();
		OmsTempConfig config = new OmsTempConfig();
		requiredOmsMap = config.initRequiredOms();
		try {
			String batchUpdateSizeString = new OMSConfig()
					.getValue(OMS_SYNCHRO_IN_BATCH_SIZE);
			int batchUpdateSize = 2000;
			if (StringUtil.isNotNull(batchUpdateSizeString)) {
				batchUpdateSize = Integer.parseInt(batchUpdateSizeString);
			}
			// 更新过程中不修改层级ID
			orgElementService.setNotToUpdateRelation(true);
			orgDeptService.setNotToUpdateRelation(true);
			orgOrgService.setNotToUpdateRelation(true);
			orgPersonService.setNotToUpdateRelation(true);
			orgPostService.setNotToUpdateRelation(true);
			orgGroupService.setNotToUpdateRelation(true);

			// 更新机构
			logger.info("开始更新机构");
			List<SysOmsElement> list = findList(ORG_TYPE_ORG);
			update2OrgDept(list, batchUpdateSize, ORG_TYPE_ORG);
			logger.info("机构更新完成");
			echoInfo();

			// 更新部门
			logger.info("开始更新部门");
			list = findList(ORG_TYPE_DEPT);
			update2OrgDept(list, batchUpdateSize, ORG_TYPE_DEPT);
			logger.info("部门更新完成");
			echoInfo();
			if (!list.isEmpty()) {
				// 更新层级ID
				logger.info("开始更新层级ID");
				orgElementService.updateRelationBatch(false);
				logger.info("层级ID更新完成");
				echoInfo();
			}

			// 更新人员
			List<String> ids = findList(ORG_TYPE_PERSON);
			if (CollectionUtils.isNotEmpty(ids)) {
				logger.info("开始更新人员");
				update2PostPersonGroup(sysOmsPersonService, ids, batchUpdateSize, ORG_TYPE_PERSON);
				logger.info("人员更新完成");
				echoInfo();
			}

			// 更新岗位
			ids = findList(ORG_TYPE_POST);
			if (CollectionUtils.isNotEmpty(ids)) {
				logger.info("开始更新岗位");
				update2PostPersonGroup(sysOmsPostService, ids, batchUpdateSize, ORG_TYPE_POST);
				logger.info("岗位更新完成");
				echoInfo();
			}

			// 更新群组
			ids = findList(ORG_TYPE_GROUP);
			if (CollectionUtils.isNotEmpty(ids)) {
				logger.info("开始更新群组");
				update2PostPersonGroup(sysOmsGroupService, ids, batchUpdateSize, ORG_TYPE_GROUP);
				logger.info("群组更新完成");
				echoInfo();
			}
			jobContext.logMessage("内部组织更新完成");
			echoInfo();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			orgDeptService.setNotToUpdateRelation(null);
			orgOrgService.setNotToUpdateRelation(null);
			orgPersonService.setNotToUpdateRelation(null);
			orgPostService.setNotToUpdateRelation(null);
			orgGroupService.setNotToUpdateRelation(null);
			orgElementService.setNotToUpdateRelation(null);
		}
	}

	/**
	 * 按批次更新机构、部门
	 *
	 * @param list
	 * @param batchSize
	 * @throws Exception
	 */
	private void update2OrgDept(List<SysOmsElement> list, int batchSize, int orgType) throws Exception {
		// 按批量大小进行拆分
		String msg = null;
		String label = orgType == ORG_TYPE_ORG ? "机构" : orgType == ORG_TYPE_DEPT ? "部门" : "";
		if (list.size() > batchSize) {
			List<List<SysOmsElement>> temps = ArrayUtil.averageAssign(list, batchSize);
			msg = "批量更新" + label + "，总数量：" + list.size();
			if (logger.isInfoEnabled()) {
				logger.info(msg);
			}
			jobContext.logMessage(msg);
			int idx = 0;
			for (List<SysOmsElement> temp : temps) {
				idx++;
				msg = "第" + idx + "次批量更新" + label + "：" + temp.size();
				if (logger.isInfoEnabled()) {
					logger.info(msg);
				}
				jobContext.logMessage(msg);
				update2Element(temp);
			}
		} else {
			msg = "批量更新" + label + "，总数量：" + list.size();
			if (logger.isInfoEnabled()) {
				logger.info(msg);
			}
			jobContext.logMessage(msg);
			update2Element(list);
		}
	}

	private void update2Element(List<SysOmsElement> list) throws Exception {
		TransactionStatus status = null;
		Exception t = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			for (SysOmsElement ele : list) {
				update2Org(ele);
			}
			echoInfo();
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logger.error("批量更新层级关系失败：", e);
			t = e;
			throw e;
		} finally {
			if (t != null && status != null) {
				TransactionUtils.rollback(status);
			}
		}
	}

	/**
	 * 按批次更新岗位、人员、群组
	 *
	 * @param service
	 * @param ids
	 */
	private void update2PostPersonGroup(IBaseService service, List<String> ids, int batchSize, int orgType) throws Exception {
		// 按批量大小进行拆分
		String msg = null;
		String label = orgType == ORG_TYPE_POST ? "岗位" : orgType == ORG_TYPE_PERSON ? "人员" : orgType == ORG_TYPE_GROUP ? "群组" : "";
		if (ids.size() > batchSize) {
			List<List<String>> temps = ArrayUtil.averageAssign(ids, batchSize);
			msg = "批量更新" + label + "，总数量：" + ids.size();
			if (logger.isInfoEnabled()) {
				logger.info(msg);
			}
			jobContext.logMessage(msg);
			int idx = 0;
			for (List<String> temp : temps) {
				List<SysOmsElement> list = service.findByPrimaryKeys(temp.toArray(new String[]{}));
				idx++;
				msg = "第" + idx + "次批量更新" + label + "：" + list.size();
				if (logger.isInfoEnabled()) {
					logger.info(msg);
				}
				jobContext.logMessage(msg);
				update2Element(list);
			}
		} else {
			List<SysOmsElement> list = service.findByPrimaryKeys(ids.toArray(new String[]{}));
			msg = "批量更新" + label + "，总数量：" + list.size();
			if (logger.isInfoEnabled()) {
				logger.info(msg);
			}
			jobContext.logMessage(msg);
			update2Element(list);
		}
	}

	private void setTempBase(IOrgElement element, SysOmsElement omsElement) {
		omsElement.setFdAlterTime(element.getAlterTime());
		omsElement.setFdCreateTime(new Date());
		omsElement.setFdCreator(element.getCreator());
		omsElement
				.setFdCustomMap(JSON.toJSONString(element.getCustomMap()));
		omsElement.setFdDynamicMap(
				JSON.toJSONString(element.getDynamicMap()));
		omsElement.setFdHandleStatus(1);
		omsElement.setFdImportinfo(element.getImportInfo());
		omsElement.setFdIsAvailable(element.getIsAvailable());
		omsElement.setFdIsBusiness(element.getIsBusiness());
		omsElement.setFdKeyword(element.getKeyword());
		omsElement.setFdLdapDn(element.getLdapDN());
		omsElement.setFdMemo(element.getMemo());
		omsElement.setFdName(element.getName());
		omsElement.setFdNo(element.getNo());
		omsElement.setFdOrder(element.getOrder());
		omsElement.setFdOrgEmail(element.getOrgEmail());
		omsElement.setFdParent(element.getParent());
		omsElement.setFdRecordStatus(element.getRecordStatus());
		omsElement.setFdShortName(element.getShortName());
		omsElement.setFdIsExternal(element.getExternal());
	}

	private void addTempOrg(IOrgElement element) throws Exception {
		IOrgOrg org = (IOrgOrg) element;
		SysOmsOrg omsOrg = new SysOmsOrg();
		setTempBase(element, omsOrg);
		omsOrg.setFdAuthAdmins(
				JSON.toJSONString(org.getAuthElementAdmins()));
		omsOrg.setFdSuperLeader(org.getSuperLeader());
		omsOrg.setFdThisLeader(org.getThisLeader());
		omsOrg.setFdViewRange(JSON.toJSONString(org.getViewRange()));
		omsOrg.setFdHideRange(JSON.toJSONString(org.getHideRange()));
		sysOmsOrgService.add(omsOrg);
	}

	private void addTempDept(IOrgElement element) throws Exception {
		IOrgDept dept = (IOrgDept) element;
		SysOmsDept omsDept = new SysOmsDept();
		setTempBase(element, omsDept);
		omsDept.setFdAuthAdmins(
				JSON.toJSONString(dept.getAuthElementAdmins()));
		omsDept.setFdSuperLeader(dept.getSuperLeader());
		omsDept.setFdThisLeader(dept.getThisLeader());
		omsDept.setFdViewRange(JSON.toJSONString(dept.getViewRange()));
		omsDept.setFdHideRange(JSON.toJSONString(dept.getHideRange()));
		sysOmsDeptService.add(omsDept);
	}

	private void addTempPerson(IOrgElement element) throws Exception {
		IOrgPerson person = (IOrgPerson) element;
		SysOmsPerson omsPerson = new SysOmsPerson();
		setTempBase(element, omsPerson);
		omsPerson.setFdViewRange(JSON.toJSONString(person.getViewRange()));
		omsPerson.setFdAttendanceCardNumber(person.getAttendanceCardNumber());
		omsPerson.setFdEmail(person.getEmail());
		omsPerson.setFdLang(person.getLang());
		omsPerson.setFdLoginName(person.getLoginName());
		omsPerson.setFdMobileNo(person.getMobileNo());
		omsPerson.setFdShortno(person.getShortNo());
		omsPerson.setFdNickName(person.getNickName());
		omsPerson.setFdPassword(person.getPassword());
		omsPerson.setFdPosts(JSON.toJSONString(person.getPosts()));
		omsPerson.setFdRtx(person.getRtx());
		omsPerson.setFdScard(person.getScard());
		omsPerson.setFdSex(person.getSex());
		omsPerson.setFdWechat(person.getWechat());
		omsPerson.setFdWorkPhone(person.getWorkPhone());
		omsPerson.setFdIsActivated(person.getActivated());
		omsPerson.setFdCanLogin(person.getCanLogin());
		omsPerson.setFdLoginNameLower(person.getLoginNameLower());
		sysOmsPersonService.add(omsPerson);
	}

	private void addTempPost(IOrgElement element) throws Exception {
		IOrgPost post = (IOrgPost) element;
		SysOmsPost omsPost = new SysOmsPost();
		setTempBase(element, omsPost);
		omsPost.setFdPersons(JSON.toJSONString(post.getPersons()));
		omsPost.setFdThisLeader(element.getThisLeader());
		sysOmsPostService.add(omsPost);
	}

	private void addTempGroup(IOrgElement element) throws Exception {
		IOrgGroup group = (IOrgGroup) element;
		SysOmsGroup omsGroup = new SysOmsGroup();
		setTempBase(element, omsGroup);
		omsGroup.setFdMembers(JSON.toJSONString(group.getMembers()));
		sysOmsGroupService.add(omsGroup);
	}

	private void addTemp(IOrgElement element)
			throws Exception {
		switch (element.getOrgType()) {
			case ORG_TYPE_ORG:
				addTempOrg(element);
				break;
			case ORG_TYPE_DEPT:
				addTempDept(element);
				break;
			case ORG_TYPE_POST:
				addTempPost(element);
				break;
			case ORG_TYPE_PERSON:
				addTempPerson(element);
				break;
			case ORG_TYPE_GROUP:
				addTempGroup(element);
				break;
		}
	}

	/**
	 * 删除临时表中已经处理过的数据
	 *
	 * @throws Exception
	 */
	private void deleteHandledOmsOrg() throws Exception {
		TransactionStatus status = null;
		Exception t = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			sysOmsOrgService.deleteHandledOrg();
			sysOmsDeptService.deleteHandledOrg();
			sysOmsPersonService.deleteHandledOrg();
			sysOmsPostService.deleteHandledOrg();
			sysOmsGroupService.deleteHandledOrg();
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logger.error("批量更新层级关系失败：", e);
			t = e;
			throw e;
		} finally {
			if (t != null && status != null) {
				TransactionUtils.rollback(status);
			}
		}
	}

	/**
	 * 如果临时表中还存在未处理的数据，表示上次同步没有执行完
	 *
	 * @return
	 * @throws Exception
	 */
	private boolean lastSynchroFinished() throws Exception {
		List list = sysOmsOrgService.findValue("fdId", null, null);
		if (list != null && !list.isEmpty()) {
			return false;
		}
		list = sysOmsDeptService.findValue("fdId", null, null);
		if (list != null && !list.isEmpty()) {
			return false;
		}
		list = sysOmsPersonService.findValue("fdId", null, null);
		if (list != null && !list.isEmpty()) {
			return false;
		}
		list = sysOmsGroupService.findValue("fdId", null, null);
		if (list != null && !list.isEmpty()) {
			return false;
		}
		list = sysOmsPostService.findValue("fdId", null, null);
		if (list != null && !list.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 初始化deptcache，查找部门关系的时候会用到。LDAP同步的情况下，可以通过编号、关键字、LDAP DN、importInfo查找关系
	 *
	 * @throws Exception
	 */
	private void initDeptCache() throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"fdIsAvailable=1 and fdIsExternal=0 and (fdOrgType=1 or fdOrgType=2) and fdImportInfo is not null");
		info.setSelectBlock("fdId,fdNo,fdKeyword,fdLdapDN,fdImportInfo");
		List<Object[]> list = orgElementService.findList(info);
		for (Object[] fields : list) {
			String fdId = (String) fields[0];
			String fdNo = (String) fields[1];
			String fdKeyword = (String) fields[2];
			String fdLdapDN = (String) fields[3];
			String fdImportInfo = (String) fields[4];
			String[] dept = new String[5];
			dept[0] = fdId;
			dept[1] = fdNo;
			dept[2] = fdKeyword;
			dept[3] = fdLdapDN;
			dept[4] = fdImportInfo;
			deptcache.add(dept);
		}
	}

	private void handleEcoSync() throws Exception {
		try {
			logger.info("\r\n=============== 准备更新生态组织 ================\r\n");
			jobContext.logMessage("\r\n=============== 准备更新生态组织 ================\r\n");
			sysOmsEcoSyncService.handleEcoSync(this);
			echoInfo();
			// 统一更新层级字段fd_hierarchy_id
			orgElementService.updateRelationBatch(true);
			echoInfo();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void synchro(IOMSSynchroInIteratorProvider provider,
						SysQuartzJobContext jobContext) throws Exception {
		Long oms_synchro_start = new Date().getTime();
		logger.info("组织架构同步开始运行");
		OMSConfig config = new OMSConfig();
		String backup = config.getValue(OMS_SYNCHRO_IN_ORGANIZATION_BACKUP);
		if ("true".equals(backup)) {
			// 备份组织架构相关表
			sysOrgElementBakService.clean();
			sysOrgElementBakService.backUp();
			jobContext.logMessage("备份组织架构成功");
		}

		this.updated = 0;
		this.updated_null = 0;
		this.deptcache = new ArrayList<String[]>();
		this.lastUpdateTime = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 365L);
		this.startTime = System.currentTimeMillis();
		this.lastEchoTime = System.currentTimeMillis();
		this.provider = provider;
		this.jobContext = jobContext;
		this.provider.init();
		try {
			echoInfo();
			// 删除临时表中已处理的数据
			deleteHandledOmsOrg();
			// 如果上次同步没同步完，那么继续上次的同步
			if (!lastSynchroFinished()) {
				logger.warn("上次更新组织没有完成，继续从临时表更新数据");
				jobContext.logMessage("上次更新组织没有完成，继续从临时表更新数据");
				initDeptCache();
				update2Org();
			} else {
				handleDelAndAdd();
				handleUpdated();
			}
			if (SysOrgEcoUtil.IS_ENABLED_ECO) {
				// 生态组织同步
				handleEcoSync();
			}
			provider.setLastUpdateTime(lastUpdateTime);

		} catch (Exception ex) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			ex.printStackTrace(printWriter);
			StringBuffer error = stringWriter.getBuffer();
			jobContext.logMessage("组织架构同步时出错:" + error.toString());
			ex.printStackTrace();
			throw ex;
		} finally {
			jobContext.logMessage("组织架构同步结束,共更新" + updated + "条记录");
			logger.info("组织架构同步结束,共更新" + updated + "条记录");
			logger.info("element为空的记录,共" + updated_null + "条");
			this.deptcache = null;
			this.updated = 0;
			long oms_synchro_end = new Date().getTime();
			jobContext.logMessage("组织架构同步耗时："
					+ (oms_synchro_end - oms_synchro_start) / 1000 + "秒");

			Map dataMap = getSysAppConfigService().findByKey(
					"com.landray.kmss.third.ekp.java.EkpJavaConfig");

			provider.terminate();
			jobContext.logMessage("组织架构同步完成，保存同步时间戳：" + DateUtil.convertDateToString(lastUpdateTime, "yyyy-MM-dd HH:mm:ss.SSS"));
			if ("true".equals(dataMap.get("kmss.oms.in.java.synchro.roleLine"))) {
				long final_end = new Date().getTime();
				jobContext.logMessage("角色线同步耗时："
						+ (final_end - oms_synchro_end) / 1000 + "秒");
			}
		}
	}

	/**
	 * 需要删除的数据
	 *
	 * @param keywords
	 * @throws Exception
	 */
	private void delete(Set<String> keywords, String providerKey)
			throws Exception {
		logger.info("开始处理需要删除的记录");
		if (keywords == null) {
			return;
		}
		jobContext.logMessage("删除组织机构记录数:" + keywords.size() + "条记录");
		logger.info("删除组织机构记录数:" + keywords.size() + "条记录");
		String deleteSizeStr = new OMSConfig()
				.getValue(OMS_SYNCHRO_IN_DELETE_SIZE);
		int deleteSize = 0;
		if (StringUtil.isNotNull(deleteSizeStr)) {
			try {
				deleteSize = Integer.parseInt(deleteSizeStr);
				if (keywords.size() >= deleteSize) {
					jobContext.logMessage("删除组织机构记录数大于:" + deleteSize
							+ "。不直接执行“置为无效”操作，由管理员手动执行");
					// 通知管理员
					try {
						NotifyContext notifyContext = synchroOrgNotify
								.getSyncExceptionNotifyContext();
						if (notifyContext != null) {
							notifyContext
									.setSubject("组织架构导入时需要删除的记录为："
											+ keywords.size()
											+ "。请检查这些数据是否都是需要删除的，如果是则手动执行”置为无效“的操作，否则请检查数据以及oms相关配置！");
							notifyContext
									.setContent("组织架构导入时需要删除的记录为："
													+ keywords.size()
													+ "。请检查这些数据是否都是需要删除的，如果是则手动执行”置为无效“的操作，否则请检查数据以及oms相关配置！"
									);
							notifyContext
									.setLink(
											"/sys/organization/sys_org_dept/index.jsp?all=true&fdFlagDeleted=true&fdImportInfo="
													+ providerKey);
							synchroOrgNotify.send(null, notifyContext, null);
						}
					} catch (Exception ex) {
						logger.error("组织架构接入通知发送失败", ex);
					}
					return;
				}
			} catch (Exception e) {
				logger.error("组织导入时删除记录数阀值设置不正确", e);
			}
		}

		long time = System.currentTimeMillis();
		for (String keyword : keywords) {
			echoInfo();
			SysOrgElement sysOrgElement = orgCoreService
					.findByPrimaryKey(keyword);
			if (sysOrgElement != null) {
				sysOrgElement.setFdIsAvailable(new Boolean(false));
				sysOrgElement.getHbmChildren().clear();
				sysOrgElement.setFdFlagDeleted(new Boolean(false));
				// 停用帐号与删除帐号时，都不要将fdImportInfo清空！不然停用后，再启用同一帐号会出现问题。
				// sysOrgElement.setFdImportInfo(null);
				orgElementService.update(sysOrgElement);
				logger.warn("记录置为无效：" + sysOrgElement.getFdId() + "---"
						+ sysOrgElement.getFdNameOri());
			}
		}
		jobContext.logMessage("删除组织机构耗时:" + (System.currentTimeMillis() - time) / 1000 + "s");
	}

	/**
	 * 对所有的组织进行全量对比，不存在的记录新增，已存在的记录更新映射关系字段（fdNo,fdKeyword,fdImportInfo,fdLdapDN）；同时更新fd_deleted字段
	 *
	 * @param rs
	 * @throws Exception
	 */
	private void importAllRecordBaseAttributes(IOMSResultSet rs,
											   Set<String> notDelete)
			throws Exception {
		logger.info("开始同步所有记录");
		Connection conn = null;
		PreparedStatement psselect_all = null;
		PreparedStatement psinsert = null;
		PreparedStatement psinsert2 = null;
		PreparedStatement psupdate = null;
		PreparedStatement psupdate_importInfo = null;
		PreparedStatement psupdate_no = null;
		PreparedStatement psupdate_keyword = null;
		PreparedStatement psupdate_all = null;
		PreparedStatement psinsert_oms_cache = null;
		Set<String> ekpIds = new HashSet<String>();
		Map<String, String> importInfos = new HashMap<String, String>();
		Map<String, OmsInRelationField> relationFieldMap = new HashMap<String, OmsInRelationField>();

		Set<String> outTypes = OMSPlugin.getOutTypes();
		try {
			conn = getDataSource().getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			int count_baseAtts_null = 0;

			// 查询所有内部组织
			psselect_all = conn.prepareStatement("select fd_id,fd_import_info,fd_org_type,fd_keyword,fd_no,fd_ldap_dn from sys_org_element");
			ResultSet rs_all = psselect_all.executeQuery();
			while (rs_all.next()) {
				String fd_id = rs_all.getString(1);
				String fd_import_info = rs_all.getString(2);
				String fd_org_type = rs_all.getString(3);
				String fd_keyword = rs_all.getString(4);
				String fd_no = rs_all.getString(5);
				String fd_ldap_dn = rs_all.getString(6);
				// 映射字段数据，主要是为了判断映射字段的值是否有改变，没有改变的话则不需要执行更新操作
				OmsInRelationField field = new OmsInRelationField(fd_keyword,
						fd_no, fd_ldap_dn);
				relationFieldMap.put(fd_id, field);
				ekpIds.add(fd_id);
				if (StringUtil.isNotNull(fd_import_info)) {
					// fd_import_info有可能相同，但同个组织类型中fd_import_info是唯一的
					importInfos.put(fd_import_info + "#" + fd_org_type, fd_id);
					if ("1".equals(fd_org_type)) {
						// 机构类型当成部门
						importInfos.put(fd_import_info + "#2", fd_id);
					}
				}
			}

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_element(fd_id,fd_name,fd_org_type,fd_no,fd_keyword,fd_is_available,fd_is_business,fd_import_info,fd_ldap_dn,fd_flag_deleted,fd_create_time,fd_is_external) values(?,?,?,?,?,?,?,?,?,?,?,?)");
			psinsert2 = conn
					.prepareStatement(
							"insert into sys_org_person(fd_id,fd_password,fd_init_password,fd_user_type) values(?,?,?,'0')");

			psupdate_all = conn
					.prepareStatement(
							"update sys_org_element set fd_name=?,fd_no=?,fd_keyword=?,fd_ldap_dn=?,fd_flag_deleted=? where fd_id=?");
			psupdate = conn
					.prepareStatement(
							"update sys_org_element set fd_name=?,fd_ldap_dn=?,fd_flag_deleted=? where fd_id=?");
			psupdate_no = conn
					.prepareStatement(
							"update sys_org_element set fd_name=?,fd_ldap_dn=?,fd_flag_deleted=?,fd_no=? where fd_id=?");
			psupdate_keyword = conn
					.prepareStatement(
							"update sys_org_element set fd_name=?,fd_ldap_dn=?,fd_flag_deleted=?,fd_keyword=? where fd_id=?");
			psinsert_oms_cache = conn
					.prepareStatement(
							"insert into sys_oms_cache(fd_id,fd_org_element_id,fd_app_name,fd_op_type) values(?,?,?,?)");

			psupdate_importInfo = conn
					.prepareStatement(
							"update sys_org_element set fd_name=?,fd_import_info=?,fd_flag_deleted=? where fd_id=?");

			SysOrgDefaultConfig sysOrgDefaultConfig = new SysOrgDefaultConfig();
			String psw = sysOrgDefaultConfig.getOrgDefaultPassword();

			KmssPasswordEncoder passwordEncoder = (KmssPasswordEncoder) SpringBeanUtil
					.getBean("passwordEncoder");

			while (rs.next()) {
				IOMSElementBaseAttribute baseAtts = rs
						.getElementBaseAttribute();
				if (baseAtts != null) {
					if (loop > 0 && (loop % 2000 == 0)) {
						psinsert.executeBatch();
						psinsert2.executeBatch();
						psupdate.executeBatch();
						psupdate_no.executeBatch();
						psupdate_keyword.executeBatch();
						psupdate_all.executeBatch();
						psinsert_oms_cache.executeBatch();
						psupdate_importInfo.executeBatch();
						conn.commit();
						echoInfo();
					}
					int orgType = baseAtts.getElementType();
					String uuid = provider.getKey()
							+ baseAtts.getElementUUID();
					String elementid = baseAtts.getElementId();
					String fd_id = null;
					if (ekpIds.contains(elementid)) {
						fd_id = elementid;
					} else {
						fd_id = importInfos.get(uuid + "#" + orgType);
					}
					// 如果组织架构中存在对应的记录
					if (StringUtil.isNotNull(fd_id)) {
						// ekp-j只需要判断importInfo是否为空，如果不为空则不需要更新
						// LDAP同步需要更新关联字段的值，否则后面更新记录详细信息的时候，根据关联字段的值可能查出来的是错误的记录
						if ("com.landray.kmss.third.ekp.java.oms.in.EkpSynchroInIteratorProviderImp"
								.equals(provider.getKey())) {
							String importInfo = importInfos
									.get(uuid + "#" + orgType);
							if (StringUtil.isNull(importInfo) || !importInfo
									.equals(uuid)) {
								psupdate_importInfo.setString(1,
										baseAtts.getElementName());
								psupdate_importInfo.setString(2, uuid);
								psupdate_importInfo.setBoolean(3, false);
								psupdate_importInfo.setString(4, fd_id);
								psupdate_importInfo.addBatch();
							}
						} else {
							logger.debug("更新记录：" + baseAtts.getElementUUID()
									+ "---" + baseAtts.getElementLdapDN());
							OmsInRelationField field = relationFieldMap
									.get(fd_id);
							if (baseAtts.isElementKeywordNeedSynchro()) {
								if (baseAtts.isElementNumberNeedSynchro()) {
									if (field.isEqualAll(baseAtts
													.getElementKeyword(), baseAtts
													.getElementNumber(),
											baseAtts
													.getElementLdapDN())) {
										// 如果关联字段的值没变则不执行更新动作
										notDelete.add(fd_id);
									} else {
										psupdate_all.setString(1, baseAtts
												.getElementName());
										psupdate_all.setString(2, baseAtts
												.getElementNumber());
										psupdate_all.setString(3, baseAtts
												.getElementKeyword());
										psupdate_all.setString(4, baseAtts
												.getElementLdapDN());
										psupdate_all.setBoolean(5, false);
										psupdate_all.setString(6, fd_id);
										psupdate_all.addBatch();
									}
								} else {
									if (field.isEqualKeywordAndDN(baseAtts
											.getElementKeyword(), baseAtts
											.getElementLdapDN())) {
										notDelete.add(fd_id);
									} else {
										// update sys_org_element set
										// fd_name=?,fd_ldap_dn=?,fd_flag_deleted=?,fd_keyword=?
										// where fd_id=?
										psupdate_keyword.setString(1, baseAtts
												.getElementName());
										psupdate_keyword.setString(2, baseAtts
												.getElementLdapDN());
										psupdate_keyword.setBoolean(3, false);
										psupdate_keyword.setString(4, baseAtts
												.getElementKeyword());
										psupdate_keyword.setString(5, fd_id);
										psupdate_keyword.addBatch();
									}
								}
							} else {
								if (baseAtts.isElementNumberNeedSynchro()) {
									if (field.isEqualNoAndDN(baseAtts
											.getElementNumber(), baseAtts
											.getElementLdapDN())) {
										notDelete.add(fd_id);
									} else {
										psupdate_no.setString(1, baseAtts
												.getElementName());
										psupdate_no.setString(2, baseAtts
												.getElementLdapDN());
										psupdate_no.setBoolean(3, false);
										psupdate_no.setString(4, baseAtts
												.getElementNumber());
										psupdate_no.setString(5, fd_id);
										psupdate_no.addBatch();
									}
								} else {
									if (field.isEqualLdapDN(baseAtts
											.getElementLdapDN())) {
										notDelete.add(fd_id);
									} else {
										psupdate
												.setString(1,
														baseAtts.getElementName());
										psupdate.setString(2, baseAtts
												.getElementLdapDN());
										psupdate.setBoolean(3, false);
										psupdate.setString(4, fd_id);
										psupdate.addBatch();
									}
								}
							}
						}

					} else {
						logger.debug("新增记录：" + baseAtts.getElementUUID()
								+ "---" + baseAtts.getElementLdapDN());
						if (StringUtil.isNotNull(elementid)) {
							fd_id = elementid;
						}
						if (StringUtil.isNull(fd_id)) {
                            fd_id = IDGenerator.generateID();
                        }
						psinsert.setString(1, fd_id);
						psinsert.setString(2, baseAtts.getElementName());
						psinsert.setInt(3, baseAtts.getElementType());
						psinsert.setString(4, baseAtts.getElementNumber());
						psinsert.setString(5, baseAtts.getElementKeyword());
						psinsert.setBoolean(6, true);
						psinsert.setBoolean(7, true);
						psinsert.setString(8, provider.getKey()
								+ baseAtts.getElementUUID());
						psinsert.setString(9, baseAtts.getElementLdapDN());
						psinsert.setBoolean(10, false);
						psinsert.setTimestamp(11,
								new Timestamp(new java.util.Date().getTime()));
						psinsert.setBoolean(12, false);
						psinsert.addBatch();
						if (baseAtts.getElementType() == 8) {
							psinsert2.setString(1, fd_id);
							String password = null;
							String initPassword = null;
							if (StringUtil.isNotNull(psw)
									&& passwordEncoder != null) {
								password = passwordEncoder.encodePassword(psw);
								initPassword = PasswordUtil.desEncrypt(psw);
							}
							psinsert2.setString(2, password);
							psinsert2.setString(3, initPassword);
							psinsert2.addBatch();
						}
						// huangwq 2012-10-12 将新增的记录保存到OMSCACHE中
						for (String type : outTypes) {
							psinsert_oms_cache.setString(1, IDGenerator
									.generateID());
							psinsert_oms_cache.setString(2, fd_id);
							psinsert_oms_cache.setString(3, type);
							psinsert_oms_cache.setInt(4, 1);
							psinsert_oms_cache.addBatch();
						}
						ekpIds.add(fd_id);
						importInfos.put(provider.getKey()
								+ baseAtts.getElementUUID() + "#"
								+ baseAtts.getElementType(), fd_id);
					}
					// 部门数据放到cache中，方便后面查找上级部门关系
					if (baseAtts.getElementType() == ORG_TYPE_ORG
							|| baseAtts.getElementType() == ORG_TYPE_DEPT) {
						String[] dept = new String[5];
						dept[0] = fd_id;
						dept[1] = baseAtts.getElementNumber();
						dept[2] = baseAtts.getElementKeyword();
						dept[3] = baseAtts.getElementLdapDN();
						dept[4] = baseAtts.getElementUUID();
						this.deptcache.add(dept);
					}
					loop++;
				} else {
					logger.debug("baseAtts为空");
					count_baseAtts_null++;
				}
			}
			psinsert.executeBatch();
			psinsert2.executeBatch();
			psupdate.executeBatch();
			psupdate_importInfo.executeBatch();
			psupdate_no.executeBatch();
			psupdate_keyword.executeBatch();
			psupdate_all.executeBatch();
			psinsert_oms_cache.executeBatch();
			conn.commit();
			jobContext.logMessage("更新importInfo信息:" + loop);
			jobContext.logMessage("baseAtts为空的记录数:" + count_baseAtts_null);
			logger.info("更新importInfo信息：" + loop);
			logger.info("baseAtts为空的记录数：" + count_baseAtts_null);
		} catch (Exception ex) {
			logger.error("同步所有记录时出错", ex);
			conn.rollback();
			throw ex;
		} finally {
			// 关闭流
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					logger.error("关闭流出错", ex);
				} catch (Throwable ex) {
					logger.error("关闭流出错", ex);
				}
			}
			JdbcUtils.closeStatement(psselect_all);
			JdbcUtils.closeStatement(psinsert);
			JdbcUtils.closeStatement(psinsert2);
			JdbcUtils.closeStatement(psupdate);
			JdbcUtils.closeStatement(psupdate_importInfo);
			JdbcUtils.closeStatement(psupdate_no);
			JdbcUtils.closeStatement(psupdate_keyword);
			JdbcUtils.closeStatement(psupdate_all);
			JdbcUtils.closeStatement(psinsert_oms_cache);
			JdbcUtils.closeConnection(conn);
		}
	}

	/**
	 * 获取需要删除的记录
	 *
	 * @param providerKey
	 * @return
	 * @throws Exception
	 */
	public String[] getKeywordsForDelete(String providerKey) throws Exception {
		String sql = " SELECT fd_id from sys_org_element where fd_import_info like '"
				+ providerKey
				+ "%' and fd_is_available = ? and fd_flag_deleted = ? and fd_is_external = ?";
		List ids = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getDataSource().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setBoolean(1, true);
			ps.setBoolean(2, true);
			ps.setBoolean(3, false);
			rs = ps.executeQuery();
			while (rs.next()) {
				ids.add(rs.getString(1));
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
		return (String[]) ids.toArray(new String[0]);
	}

	/**
	 * 更新组织详细信息
	 *
	 * @param element
	 * @throws Exception
	 */
	private void update(SysOmsElement element)
			throws Exception {
		SysOrgElement sysOrgElement = getSynchroOrgRecord(
				element.getFdImportinfo(), element.getOrgType());
		if (sysOrgElement == null) {
			logger.error("组织架构里面找不到对应的记录,importInfo:{},type:{}:,name:{}",
					element.getFdImportinfo(), element.getOrgType(),
					element.getFdName());
			return;
		}
		if (element.getFdAlterTime() == null) {
			logger.error("记录的更新时间为空，请检查配置和数据：importInfo:{},type:{}:,name:{}",
					element.getFdImportinfo(), element.getOrgType(),
					element.getFdName());
		} else if (element.getFdAlterTime().after(lastUpdateTime)) {
			lastUpdateTime = element.getFdAlterTime();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("更新记录,importInfo:{},type:{}:,name:{}",
					element.getFdImportinfo(), element.getOrgType(),
					element.getFdName());
		}
		sysOrgElement.setFdIsExternal(element.getFdIsExternal() != null ? element.getFdIsExternal() : false);
		if (sysOrgElement instanceof SysOrgOrg) {
			SysOrgOrg sysOrgOrg = (SysOrgOrg) sysOrgElement;
			SysOmsOrg omsOrg = (SysOmsOrg) element;
			setSysOrgOrg(omsOrg, (SysOrgOrg) sysOrgElement);
			// 设置机构层级ID
			sysOrgOrg.setFdHierarchyId(HIERARCHY_ID_SPLIT + sysOrgOrg.getFdId()
					+ HIERARCHY_ID_SPLIT);
			if (element.getFdIsAvailable().booleanValue()) {
				setOrgParent(sysOrgOrg, element, provider
								.getDeptParentValueMapType(), provider
								.getDeptParentValueMapTo(),
						element.getFdParent());
				setThisLeader(sysOrgOrg, element, provider
								.getDeptLeaderValueMapType(), provider
								.getDeptLeaderValueMapTo(),
						omsOrg.getFdThisLeader());
				setSuperLeader(sysOrgOrg, element, provider
								.getDeptSuperLeaderValueMapType(), provider
								.getDeptSuperLeaderValueMapTo(),
						omsOrg
								.getFdSuperLeader());
			} else {
				// 无效组织设置层级ID为0
				sysOrgOrg.setFdHierarchyId("0");
			}
			// 设置管理员
			setAuthElementAdmins(sysOrgOrg,omsOrg.getFdAuthAdmins());
			// 设置查看范围
			setRange(sysOrgOrg, omsOrg.getFdViewRange());
			setHideRange(sysOrgOrg, omsOrg.getFdHideRange());
			orgOrgService.update(sysOrgOrg);
		}
		if (sysOrgElement instanceof SysOrgDept) {
			SysOrgElement sysOrgDept = sysOrgElement;
			SysOmsDept omsDept = (SysOmsDept) element;
			setSysOrgDept(omsDept, (SysOrgDept) sysOrgElement);
			if (element.getFdIsAvailable().booleanValue()) {
				setOrgParent(sysOrgDept, element, provider
								.getDeptParentValueMapType(), provider
								.getDeptParentValueMapTo(),
						element.getFdParent());
				setThisLeader(sysOrgDept, element, provider
								.getDeptLeaderValueMapType(), provider
								.getDeptLeaderValueMapTo(),
						omsDept.getFdThisLeader());
				setSuperLeader(sysOrgDept, element, provider
								.getDeptSuperLeaderValueMapType(), provider
								.getDeptSuperLeaderValueMapTo(),
						omsDept
								.getFdSuperLeader());
			} else {
				sysOrgDept.setFdHierarchyId("0");
			}
			// 设置管理员
			setAuthElementAdmins(sysOrgDept,omsDept.getFdAuthAdmins());
			// 设置查看范围
			setRange(sysOrgDept, omsDept.getFdViewRange());
			// 设置隐藏信息
			setHideRange(sysOrgDept, omsDept.getFdHideRange());
			orgDeptService.update(sysOrgDept);
		}
		if (sysOrgElement instanceof SysOrgPerson) {
			SysOrgPerson sysOrgPerson = (SysOrgPerson) sysOrgElement;
			SysOmsPerson omsPerson = (SysOmsPerson) element;
			setSysOrgPerson(omsPerson, (SysOrgPerson) sysOrgElement);
			if (element.getFdIsAvailable().booleanValue()) {
				setOrgParent(sysOrgPerson, element, provider
								.getPersonDeptValueMapType(), provider
								.getPersonDeptValueMapTo(),
						element.getFdParent());
				setPosts(sysOrgPerson, omsPerson, provider
						.getPersonPostValueMapType(), provider
						.getPersonPostValueMapTo());
			} else {
				sysOrgPerson.setFdHierarchyId("0");
			}
			orgPersonService.update(sysOrgPerson);
		}
		if (sysOrgElement instanceof SysOrgPost) {
			SysOrgPost sysOrgPost = (SysOrgPost) sysOrgElement;
			SysOmsPost omsPost = (SysOmsPost) element;
			setSysOrgPost(omsPost, (SysOrgPost) sysOrgElement);
			if (element.getFdIsAvailable().booleanValue()) {
				setOrgParent(sysOrgPost, element, provider
								.getPostDeptValueMapType(), provider
								.getPostDeptValueMapTo(),
						element.getFdParent());
				setPersons(sysOrgPost, omsPost, provider
						.getPostPersonValueMapType(), provider
						.getPostPersonValueMapTo());
				setThisLeader(sysOrgPost, element, provider
								.getPostLeaderValueMapType(), provider
								.getPostLeaderValueMapTo(),
						omsPost.getFdThisLeader());
			} else {
				sysOrgPost.setFdHierarchyId("0");
			}
			orgPostService.update(sysOrgPost);
		}
		if (sysOrgElement instanceof SysOrgGroup) {
			SysOrgGroup sysOrgGroup = (SysOrgGroup) sysOrgElement;
			SysOmsGroup omsGroup = (SysOmsGroup) element;
			setSysOrgGroup(omsGroup, (SysOrgGroup) sysOrgElement);
			if (element.getFdIsAvailable().booleanValue()) {
				setMembers(sysOrgGroup, omsGroup, provider
						.getGroupMemberValueMapType(), provider
						.getGroupMemberValueMapTo());
			}
			orgGroupService.update(sysOrgGroup);
		}
	}

	/**
	 * 更新基本信息
	 *
	 * @param element
	 * @param sysOrgElement
	 * @throws Exception
	 */
	private void setSysOrgElement(SysOmsElement element,
								  SysOrgElement sysOrgElement) throws Exception {
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("no")) {
			sysOrgElement.setFdNo(element.getFdNo());
		}
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("name")) {
			sysOrgElement.setFdName(element.getFdName());
		}
		sysOrgElement.setFdAlterTime(new Date());
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("order")) {
			sysOrgElement.setFdOrder(element.getFdOrder());
			if (logger.isDebugEnabled()) {
				logger.debug("设置排序号，ID：" + sysOrgElement.getFdId() + ",名称："
						+ sysOrgElement.getFdName() + ",排序号："
						+ sysOrgElement.getFdOrder());
			}
		}
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("keyword")) {
			sysOrgElement.setFdKeyword(element.getFdKeyword());
		}
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("memo")) {
			String memo = element.getFdMemo();
			if (StringUtil.isNotNull(memo)) {
				memo = StringUtil.unescape(memo);
			}
			sysOrgElement.setFdMemo(memo);
		}
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("importInfo")) {
			sysOrgElement.setFdImportInfo(provider.getKey()
					+ element.getFdImportinfo());
		}
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("isAvailable")) {
			if (logger.isDebugEnabled()) {
				logger.debug("设置是否有效，" + element.getFdIsAvailable());
			}
			sysOrgElement.setFdIsAvailable(element.getFdIsAvailable());
		}
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("isBusiness")) {
			sysOrgElement.setFdIsBusiness(element.getFdIsBusiness());
		}
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("langProps")) {
			if (logger.isDebugEnabled()) {
				logger.debug("设置多语言字段，" + element.getDynamicMap());
			}
			sysOrgElement.getDynamicMap().putAll(element.getDynamicMap());
		}
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("customProps")) {
			HashMap<String, Object> map = JSONObject
					.parseObject(element.getFdCustomMap(), HashMap.class);
			Map dynamicMap = DynamicAttributeUtil.convertCustomProp(
					SysOrgPerson.class.getName(), map);
			if (logger.isDebugEnabled()) {
				logger.debug("设置自定义字段，" + dynamicMap);
			}
			sysOrgElement.getCustomPropMap().putAll(dynamicMap);
		}
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("orgEmail")) {
			sysOrgElement.setFdOrgEmail(element.getFdOrgEmail());
		}
		// 避免更新时间相同导致通过webservice接口获取组织数据时出现死循环
		sysOrgElement
				.setFdAlterTime(new Date(TimestampUtil.uniqueCurrentTimeMS()));
	}

	/**
	 * 更新人员相关字段
	 *
	 * @param person
	 * @param sysOrgPerson
	 * @throws Exception
	 */
	private void setSysOrgPerson(SysOmsPerson person, SysOrgPerson sysOrgPerson)
			throws Exception {
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("loginName")) {
			setPersonAccount(person, sysOrgPerson);
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("password")) {
			setPersonPassord(person, sysOrgPerson);
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("email")) {
			sysOrgPerson.setFdEmail(person.getFdEmail());
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("mobileNo")) {
			sysOrgPerson.setFdMobileNo(person.getFdMobileNo());
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("attendanceCardNumber")) {
			sysOrgPerson.setFdAttendanceCardNumber(person
					.getFdAttendanceCardNumber());
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("workPhone")) {
			sysOrgPerson.setFdWorkPhone(person.getFdWorkPhone());
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("lang")) {
			sysOrgPerson.setFdDefaultLang(person.getFdLang());
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("rtx")) {
			sysOrgPerson.setFdRtxNo(person.getFdRtx());
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("wechat")) {
			sysOrgPerson.setFdWechatNo(person.getFdWechat());
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("scard")) {
			sysOrgPerson.setFdCardNo(person.getFdScard());
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("sex")) {
			sysOrgPerson.setFdSex(person.getFdSex());
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("shortNo")) {
			sysOrgPerson.setFdShortNo(person.getFdShortno());
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("nickName")) {
			sysOrgPerson.setFdNickName(person.getFdNickName());
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("canLogin")) {
			sysOrgPerson.setFdCanLogin(person.getFdCanLogin());
		}
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("isBusiness")) {
			sysOrgPerson.setFdIsBusiness(person.getFdIsBusiness());
		}
		setSysOrgElement(person, sysOrgPerson);
	}

	private void setPersonAccount(SysOmsPerson person,
								  SysOrgPerson sysOrgPerson) {
		if (sysOrgPerson.getFdId() != null) {
			if ((provider.getAccountType()
					& ACCOUNT_TYPE_SYNCHRO_UPDATE) == ACCOUNT_TYPE_SYNCHRO_UPDATE) {
				sysOrgPerson.setFdLoginName(person.getFdLoginName());
			}
		} else {
			sysOrgPerson.setFdLoginName(person.getFdLoginName());
		}
	}

	private void setPersonPassord(SysOmsPerson person,
								  SysOrgPerson sysOrgPerson)
			throws Exception {
		if(StringUtil.isNull(person.getFdPassword())){
			return;
		}
		if ((provider.getPasswordType()
				& PASSWORD_TYPE_REQUIRED) == PASSWORD_TYPE_REQUIRED) {
			String password = person.getFdPassword();
			if ((provider.getPasswordType()
					& PASSWORD_TYPE_NOT_TRANSFER) != PASSWORD_TYPE_NOT_TRANSFER) {
				String plain = SecureUtil.BASE64Decoder(person.getFdPassword());
				password = passwordEncoder.encodePassword(plain);
			}
			if ((provider.getPasswordType()
					& PASSWORD_TYPE_CREATE_SYNCHRO) == PASSWORD_TYPE_CREATE_SYNCHRO) {
				if (person.getFdRecordStatus() == SysOrgConstant.OMS_OP_FLAG_ADD
						&& sysOrgPerson != null) {
					sysOrgPerson.setFdPassword(password);
					sysOrgPerson.setFdInitPassword(
							PasswordUtil.desEncrypt(person.getFdPassword()));
				} else if (sysOrgPerson != null
						&& orgPersonService.findByPrimaryKey(sysOrgPerson
						.getFdId(), SysOrgPerson.class, true) == null) {
					sysOrgPerson.setFdPassword(password);
					sysOrgPerson.setFdInitPassword(
							PasswordUtil.desEncrypt(person.getFdPassword()));
				}
			} else {
				sysOrgPerson.setFdPassword(password);
				sysOrgPerson.setFdInitPassword(
						PasswordUtil.desEncrypt(person.getFdPassword()));
			}
		}

	}

	private void setSysOrgOrg(SysOmsElement org, SysOrgOrg sysOrgOrg)
			throws Exception {
		setSysOrgElement(org, sysOrgOrg);
	}

	private void setSysOrgDept(SysOmsDept dept, SysOrgElement sysOrgDept)
			throws Exception {
		setSysOrgElement(dept, sysOrgDept);
	}

	private void setSysOrgPost(SysOmsPost post, SysOrgPost sysOrgPost)
			throws Exception {
		setSysOrgElement(post, sysOrgPost);
	}

	private void setSysOrgGroup(SysOmsGroup group, SysOrgGroup sysOrgGroup)
			throws Exception {
		setSysOrgElement(group, sysOrgGroup);
	}

	/**
	 * 查找关联的组织
	 *
	 * @param searchType  组织类型
	 * @param searchName  关联字段
	 * @param searchValue 字段值
	 * @return
	 * @throws Exception
	 */
	private List getForeignKey(ValueMapType[] searchType,
							   ValueMapTo searchName, String[] searchValue) throws Exception {
		String[] fdids;
		// 如果是关联部门，直接从deptcache中查找
		if (searchType.length == 1 && searchType[0] == ValueMapType.DEPT) {
			List ids = new ArrayList();
			// 1,number;2,keyword;3,dn;,4importInfo
			for (int i = 0; i < deptcache.size(); i++) {
				String[] dept = deptcache.get(i);
				if (searchName == ValueMapTo.ID) {
					for (int j = 0; j < searchValue.length; j++) {
						if (dept[0] != null
								&& dept[0].equalsIgnoreCase(searchValue[j])) {
							ids.add(dept[0]);
						}
					}
				}
				if (searchName == ValueMapTo.NUMBER) {
					for (int j = 0; j < searchValue.length; j++) {
						if (dept[1] != null
								&& dept[1].equalsIgnoreCase(searchValue[j])) {
							ids.add(dept[0]);
						}
					}
				}
				if (searchName == ValueMapTo.KEYWORD) {
					for (int j = 0; j < searchValue.length; j++) {
						if (dept[2] != null
								&& dept[2].equalsIgnoreCase(searchValue[j])) {
							ids.add(dept[0]);
						}
					}
				}
				if (searchName == ValueMapTo.LDAPDN) {
					for (int j = 0; j < searchValue.length; j++) {
						if (dept[3] != null
								&& dept[3].equalsIgnoreCase(searchValue[j])) {
							ids.add(dept[0]);
						}
					}
				}
				if (searchName == ValueMapTo.IMPORTINFO) {
					for (int j = 0; j < searchValue.length; j++) {
						if (dept[4] != null
								&& dept[4].equalsIgnoreCase(searchValue[j])) {
							ids.add(dept[0]);
						}
					}
				}
			}
			fdids = (String[]) ids.toArray(new String[0]);
		} else {
			HQLInfo hqlInfo = new HQLInfo();
			String selectWhere = " ";
			String temp = "";
			if (searchType != null) {
				// 设置查找的组织类型
				if (searchType.length == 1) {
					selectWhere += " sysOrgElement.fdOrgType=:orgType";
					hqlInfo.setParameter("orgType", searchType[0]
							.getTypeValue());
				} else {
					for (int i = 0; i < searchType.length; i++) {
						temp += "," + searchType[i].getTypeValue();
					}
					if (temp.length() > 0) {
						temp = temp.substring(1);
						selectWhere += " sysOrgElement.fdOrgType in( " + temp
								+ ") ";
					}
				}
			}
			if (searchValue != null) {
				if (searchValue.length == 1) {
					// 单值用=查询
					selectWhere += " and sysOrgElement."
							+ searchName.getColumnName()
							+ " =:searchName and sysOrgElement.fdIsAvailable=:fdIsAvailable";
					hqlInfo.setParameter("fdIsAvailable", true);
					if (searchName == ValueMapTo.IMPORTINFO) {
						hqlInfo.setParameter("searchName", provider.getKey()
								+ searchValue[0]);
					} else {
						hqlInfo.setParameter("searchName", searchValue[0]);
					}
				} else {
					// 多值用in查询，oracle下单个in中的元素最大为1000
					temp = "";
					List<String> temps = new ArrayList<String>();
					for (int i = 0; i < searchValue.length; i++) {
						if (i > 0 && i % 1000 == 0) {
							temps.add(temp);
							temp = "";
						}
						if (StringUtil.isNotNull(searchValue[i])) {
							if (searchName == ValueMapTo.IMPORTINFO) {
                                temp += ",'" + provider.getKey()
                                        + searchValue[i] + "'";
                            } else {
                                temp += ",'" + searchValue[i] + "'";
                            }
						}
					}
					if (StringUtil.isNotNull(temp)) {
						temps.add(temp);
					}
					String inBlock = "";
					for (String in : temps) {
						in = in.substring(1);
						inBlock += " or sysOrgElement."
								+ searchName.getColumnName() + " in (" + in
								+ ")";
					}
					if (StringUtil.isNotNull(inBlock)) {
						selectWhere += " and (" + inBlock.substring(4)
								+ ")  and sysOrgElement.fdIsAvailable=:fdIsAvailable";
						hqlInfo.setParameter("fdIsAvailable", true);
					}
				}
			}
			hqlInfo.setSelectBlock("sysOrgElement.fdId");
			hqlInfo.setWhereBlock(selectWhere);
			fdids = (String[]) orgElementService.getBaseDao()
					.findValue(hqlInfo).toArray(new String[0]);
		}
		return orgElementService.findByPrimaryKeys(fdids);

	}

	/**
	 * 获取需要同步的字段
	 *
	 * @param element
	 * @return
	 * @throws Exception
	 */
	private List<String> getRequiredOms(SysOmsElement element)
			throws Exception {
		if (requiredOmsMap == null) {
			throw new Exception("requiredOmsMap为空");
		}
		return requiredOmsMap.get(element.getOrgType());
	}

	/**
	 * 设置上级部门 比如，需要通过编号查找上级部门，那么valueMpaType为Dept,valueMapTo为no,value为部门编号的值
	 *
	 * @param sysOrgElement 需要更新的组织
	 * @param element       同步来源数据
	 * @param valueMpaType  查找的组织类型
	 * @param valueMapTo    关联字段的名称
	 * @param value         映射值
	 * @throws Exception
	 */
	private void setOrgParent(SysOrgElement sysOrgElement,
							  SysOmsElement element,
							  ValueMapType[] valueMpaType, ValueMapTo valueMapTo, String value)
			throws Exception {
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("parent")) {
			if (StringUtil.isNull(value)) {
				sysOrgElement.setFdParent(null);
				sysOrgElement.setFdHierarchyId(HIERARCHY_ID_SPLIT
						+ sysOrgElement.getFdId() + HIERARCHY_ID_SPLIT);
				logger.debug("设置上级部门为空");
				return;
			}
			if (valueMapTo == null) {
				sysOrgElement.setFdParent(null);
				sysOrgElement.setFdHierarchyId(HIERARCHY_ID_SPLIT
						+ sysOrgElement.getFdId() + HIERARCHY_ID_SPLIT);
				logger.debug("设置上级部门为空");
				return;
			}
			List perents = getForeignKey(valueMpaType, valueMapTo,
					new String[]{value});
			if (perents != null && perents.size() > 0) {
				SysOrgElement parent = (SysOrgElement) perents.get(0);
				if (logger.isDebugEnabled()) {
					logger.debug("设置上级部门，ID：" + parent.getFdId() + "，名称："
							+ parent.getFdName() + "，LDAP DN："
							+ parent.getFdLdapDN());
				}
				if (parent.getFdIsAvailable()) {
					sysOrgElement.setFdParent(parent);
					if (StringUtil.isNotNull(parent.getFdHierarchyId())) {
						sysOrgElement.setFdHierarchyId(parent.getFdHierarchyId()
								+ sysOrgElement.getFdId() + HIERARCHY_ID_SPLIT);
					} else {
						sysOrgElement.setFdHierarchyId(HIERARCHY_ID_SPLIT
								+ sysOrgElement.getFdId() + HIERARCHY_ID_SPLIT);
					}
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("找不到上级部门，ID：" + sysOrgElement.getFdId() + "，名称："
							+ sysOrgElement.getFdName() + "，LDAP DN："
							+ sysOrgElement.getFdLdapDN());
				}
			}
		}
	}

	/**
	 * 设置本级领导
	 *
	 * @param sysOrgElement
	 * @param element
	 * @param valueMpaType
	 * @param valueMapTo
	 * @param value
	 * @throws Exception
	 */
	private void setThisLeader(SysOrgElement sysOrgElement,
							   SysOmsElement element, ValueMapType[] valueMpaType,
							   ValueMapTo valueMapTo, String value) throws Exception {
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("thisLeader")) {
			if (StringUtil.isNull(element.getFdThisLeader())) {
				sysOrgElement.setHbmThisLeader(null);
				logger.debug("设置本级领导为空");
				return;
			}
			if (valueMapTo == null) {
				sysOrgElement.setHbmThisLeader(null);
				logger.debug("设置本级领导为空");
				return;
			}
			List thisLeaders = getForeignKey(valueMpaType, valueMapTo,
					new String[]{value});
			if (thisLeaders != null && thisLeaders.size() > 0) {
				SysOrgElement thisLeader = (SysOrgElement) thisLeaders.get(0);
				if (logger.isDebugEnabled()) {
					logger.debug("设置本级领导，ID：" + thisLeader.getFdId() + "，名称："
							+ thisLeader.getFdName() + "，LDAP DN："
							+ thisLeader.getFdLdapDN());
				}
				if (thisLeader.getFdIsAvailable()) {
					sysOrgElement.setHbmThisLeader(thisLeader);
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("找不到本级领导，ID：" + sysOrgElement.getFdId() + "，名称："
							+ sysOrgElement.getFdName() + "，LDAP DN："
							+ sysOrgElement.getFdLdapDN());
				}
			}
		}
	}

	/**
	 * 设置上级领导
	 *
	 * @param sysOrgElement
	 * @param element
	 * @param valueMpaType
	 * @param valueMapTo
	 * @param value
	 * @throws Exception
	 */
	private void setSuperLeader(SysOrgElement sysOrgElement,
								SysOmsElement element, ValueMapType[] valueMpaType,
								ValueMapTo valueMapTo, String value) throws Exception {
		if (getRequiredOms(element) == null
				|| getRequiredOms(element).contains("superLeader")) {
			if (StringUtil.isNull(element.getFdSuperLeader())) {
				sysOrgElement.setHbmSuperLeader(null);
				logger.debug("设置上级领导为空");
				return;
			}
			if (valueMapTo == null) {
				sysOrgElement.setHbmSuperLeader(null);
				logger.debug("设置上级领导为空");
				return;
			}
			List superLeaders = getForeignKey(valueMpaType, valueMapTo,
					new String[]{value});
			if (superLeaders != null && superLeaders.size() > 0) {
				SysOrgElement superLeader = (SysOrgElement) superLeaders.get(0);
				if (logger.isDebugEnabled()) {
					logger.debug("设置上级领导，ID：" + superLeader.getFdId() + "，名称："
							+ superLeader.getFdName() + "，LDAP DN："
							+ superLeader.getFdLdapDN());
				}
				if (superLeader.getFdIsAvailable()) {
					sysOrgElement.setHbmSuperLeader(superLeader);
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("找不到上级领导，ID：" + sysOrgElement.getFdId() + "，名称："
							+ sysOrgElement.getFdName() + "，LDAP DN："
							+ sysOrgElement.getFdLdapDN());
				}
			}
		}
	}

	/**
	 * 设置人员岗位
	 *
	 * @param sysOrgPerson
	 * @param person
	 * @param valueMpaType
	 * @param valueMapTo
	 * @throws Exception
	 */
	private void setPosts(SysOrgPerson sysOrgPerson, SysOmsPerson person,
						  ValueMapType[] valueMpaType, ValueMapTo valueMapTo)
			throws Exception {
		if (getRequiredOms(person) == null
				|| getRequiredOms(person).contains("posts")) {
			List<String> postList = JSON.parseArray(person.getFdPosts(),
					String.class);
			if (postList == null) {
				sysOrgPerson.setFdPosts(null);
				logger.debug("设置岗位列表为空");
				return;
			}
			String[] posts = new String[postList.size()];
			posts = postList.toArray(posts);
			if (posts == null || posts.length == 0) {
				sysOrgPerson.setFdPosts(null);
				logger.debug("设置人员岗位列表为空");
				return;
			}
			if (valueMapTo == null) {
				sysOrgPerson.setFdPosts(null);
				logger.debug("设置人员岗位列表为空");
				return;
			}
			List list = getForeignKey(valueMpaType, valueMapTo, posts);
			// 是否覆盖原岗位
			Map dataMap = getSysAppConfigService().findByKey(
					"com.landray.kmss.third.ekp.java.EkpJavaConfig");
			if ("true".equals(
					dataMap.get("kmss.oms.in.java.synchro.post.cover"))) {
				// 不覆盖
				List<SysOrgPost> ori_posts = sysOrgPerson.getFdPosts();
				List<SysOrgPost> sys_posts = new ArrayList<SysOrgPost>();
				for (int i = 0; i < ori_posts.size(); i++) {
					if (StringUtil.isNull(ori_posts.get(i).getFdImportInfo())
							|| !ori_posts.get(i).getFdImportInfo().startsWith(
							"com.landray.kmss.third.ekp.java.oms.in.EkpSynchroInIteratorProviderImp")) {
						sys_posts.add(ori_posts.get(i));
					}
				}
				if (sys_posts != null && !sys_posts.isEmpty()) {
					list.addAll(sys_posts);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("设置人员岗位：" + getPrintMsg(list));
			}
			sysOrgPerson.setFdPosts(list);
		}
	}

	/**
	 * 设置岗位成员
	 *
	 * @param sysOrgPost
	 * @param post
	 * @param valueMpaType
	 * @param valueMapTo
	 * @throws Exception
	 */
	private void setPersons(SysOrgPost sysOrgPost, SysOmsPost post,
							ValueMapType[] valueMpaType, ValueMapTo valueMapTo)
			throws Exception {
		if (getRequiredOms(post) == null
				|| getRequiredOms(post).contains("persons")) {
			List<String> personList = JSON.parseArray(post.getFdPersons(),
					String.class);
			if (personList == null) {
				sysOrgPost.setFdPosts(null);
				return;
			}
			String[] persons = new String[personList.size()];
			persons = personList.toArray(persons);
			if (persons == null || persons.length == 0) {
				sysOrgPost.setFdPersons(null);
				logger.debug("设置岗位成员为空");
				return;
			}
			if (valueMapTo == null) {
				sysOrgPost.setFdPersons(null);
				logger.debug("设置岗位成员为空");
				return;
			}
			List list = getForeignKey(valueMpaType, valueMapTo, persons);
			if (logger.isDebugEnabled()) {
				logger.debug("设置岗位成员：" + getPrintMsg(list));
			}
			sysOrgPost.setFdPersons(list);
		}
	}

	/**
	 * 设置群组成员
	 *
	 * @param sysOrgGroup
	 * @param group
	 * @param valueMpaType
	 * @param valueMapTo
	 * @throws Exception
	 */
	private void setMembers(SysOrgGroup sysOrgGroup, SysOmsGroup group,
							ValueMapType[] valueMpaType, ValueMapTo valueMapTo)
			throws Exception {
		if (getRequiredOms(group) == null
				|| getRequiredOms(group).contains("members")) {
			List<String> memberList = JSON.parseArray(group.getFdMembers(),
					String.class);
			if (memberList == null) {
				sysOrgGroup.setFdMembers(null);
				logger.debug("设置群组成员为空");
				return;
			}
			String[] members = new String[memberList.size()];
			members = memberList.toArray(members);
			if (members == null || members.length == 0) {
				sysOrgGroup.setFdMembers(null);
				logger.debug("设置群组成员为空");
				return;
			}
			if (valueMapTo == null) {
				sysOrgGroup.setFdMembers(null);
				logger.debug("设置群组成员为空");
				return;
			}
			List list = getForeignKey(valueMpaType, valueMapTo, members);
			if (logger.isDebugEnabled()) {
				logger.debug("设置群组成员：" + getPrintMsg(list));
			}
			sysOrgGroup.setFdMembers(list);
		}
	}

	public void setSysOrgElementBakService(
			ISysOrgElementBakService sysOrgElementBakService) {
		this.sysOrgElementBakService = sysOrgElementBakService;
	}

	public ISysOrgElementBakService getSysOrgElementBakService() {
		return sysOrgElementBakService;
	}

	private static ISysAppConfigService sysAppConfigService;

	private static ISysAppConfigService getSysAppConfigService() {
		if (sysAppConfigService == null) {
            sysAppConfigService = (ISysAppConfigService) SpringBeanUtil
                    .getBean("sysAppConfigService");
        }
		return sysAppConfigService;
	}

	private String getPrintMsg(List<SysOrgElement> list) {
		if (list == null) {
			return null;
		}
		String msg = "";
		for (SysOrgElement e : list) {
			msg += "{ID:" + e.getFdId() + ",name:" + e.getFdName() + "},";
		}
		msg = "[" + msg + "]";
		return msg;
	}
}
