package com.landray.kmss.sys.oms.in;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.oms.OMSConfig;
import com.landray.kmss.sys.oms.OMSPlugin;
import com.landray.kmss.sys.oms.SysOMSConstant;
import com.landray.kmss.sys.oms.in.interfaces.IOMSElementBaseAttribute;
import com.landray.kmss.sys.oms.in.interfaces.IOMSResultSet;
import com.landray.kmss.sys.oms.in.interfaces.IOMSSynchroInIteratorProvider;
import com.landray.kmss.sys.oms.in.interfaces.IOrgDept;
import com.landray.kmss.sys.oms.in.interfaces.IOrgElement;
import com.landray.kmss.sys.oms.in.interfaces.IOrgGroup;
import com.landray.kmss.sys.oms.in.interfaces.IOrgOrg;
import com.landray.kmss.sys.oms.in.interfaces.IOrgPerson;
import com.landray.kmss.sys.oms.in.interfaces.IOrgPost;
import com.landray.kmss.sys.oms.in.interfaces.ValueMapTo;
import com.landray.kmss.sys.oms.in.interfaces.ValueMapType;
import com.landray.kmss.sys.oms.notify.service.ISynchroOrgNotify;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgOrg;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.IKmssPasswordEncoder;
import com.landray.kmss.sys.organization.service.ISysOrgDeptService;
import com.landray.kmss.sys.organization.service.ISysOrgElementBakService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.sys.organization.service.ISysOrgOrgService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SecureUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

public class OMSSynchroInIteratorProviderRunner2 implements
		IOMSSynchroInIteratorProviderRunner, SysOrgConstant, SysOMSConstant {
	public static String OMS_SYNCHRO_IN_BATCH_SIZE = "kmss.oms.in.batch.size";

	public static String OMS_SYNCHRO_IN_DELETE_SIZE = "kmss.oms.in.delete.size";

	public static String OMS_SYNCHRO_IN_ORGANIZATION_BACKUP = "kmss.oms.in.organization.backup";

	private ISynchroOrgNotify synchroOrgNotify;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(OMSSynchroInIteratorProviderRunner2.class);

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

	@Override
	public SysQuartzJobContext getJobContext() {
		return jobContext;
	}

	private long startTime;
	private long lastEchoTime;
	private int updated;
	private int updated_null;
	private List<String[]> deptcache;

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
				logger.info("组织架构同步正在运行：已经运行" + info + ".更新数目:" + updated);
			} else {
				logger.info("组织架构同步正在运行：已经运行" + info + ".");
			}
		}
	}

	/**
	 * 根据ImportInfo查找组织架构元素
	 *
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	private SysOrgElement getSynchroOrgRecord(String keyword) throws Exception {
		keyword = provider.getKey() + keyword;
		SysOrgElement sysOrgElement = orgCoreService.format(orgCoreService
				.findByImportInfo(keyword));
		if (sysOrgElement == null) {
            return null;
        }
		return sysOrgElement;
	}

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

	private void flagDeleted() throws Exception {
		logger.debug("设置组织架构待删除标识");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			ps = conn
					.prepareStatement("update sys_org_element set fd_flag_deleted = ? where fd_import_info like '"
							+ provider.getKey() + "%'");
			ps.setBoolean(1, true);
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

	@Override
	public void synchro(IOMSSynchroInIteratorProvider provider,
						SysQuartzJobContext jobContext) throws Exception {
		Long oms_synchro_start = new Date().getTime();
		logger.info("组织架构同步开始运行");
		OMSConfig config = new OMSConfig();
		String backup = config.getValue(OMS_SYNCHRO_IN_ORGANIZATION_BACKUP);
		if ("true".equals(backup)) {
			sysOrgElementBakService.clean();
			sysOrgElementBakService.backUp();
		}
		jobContext.logMessage("备份组织架构成功");
		this.updated = 0;
		this.updated_null = 0;
		this.deptcache = new ArrayList<String[]>();
		this.lastUpdateTime = new Date(System.currentTimeMillis() - 1000 * 60
				* 60 * 24 * 365);
		this.startTime = System.currentTimeMillis();
		this.lastEchoTime = System.currentTimeMillis();
		this.provider = provider;
		this.jobContext = jobContext;
		provider.init();
		try {
			echoInfo(); // 清除零临时表
			flagDeleted(); // 将所有组织架构flagDelete的值设置为1，请清除临时表
			importAllRecordBaseAttributes(provider.getAllRecordBaseAttributes());
			String[] deleteKeywords = getKeywordsForDelete(provider.getKey());
			echoInfo();
			TransactionStatus deleteStatus = TransactionUtils
					.beginNewTransaction();
			try {
				delete(deleteKeywords, provider.getKey());
				TransactionUtils.getTransactionManager().commit(deleteStatus);
			} catch (Exception ex) {
				TransactionUtils.getTransactionManager().rollback(deleteStatus);
				throw ex;
			}

			logger.debug("开始增量更新记录");
			TransactionStatus updateDtatus = TransactionUtils
					.beginNewTransaction();
			echoInfo();
			IOMSResultSet rs = provider.getSynchroRecords();
			try {

				String batchUpdateSizeString = config
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
				while (rs.next()) {
					echoInfo();
					if (batchUpdateSize > 0 && updated > 0
							&& ((updated % batchUpdateSize) == 0)) {
						TransactionUtils.getTransactionManager().commit(
								updateDtatus);
						orgElementService.getBaseDao().getHibernateSession()
								.clear();
						// System.gc();
						updateDtatus = TransactionUtils.beginNewTransaction();
						logger.info("批次提交事务");
					}
					IOrgElement element = rs.getElement();
					if (element != null) {
						logger.debug("更新记录详细信息：" + element.getImportInfo()
								+ "---" + element.getLdapDN());
						SysOrgElement sysOrgElement = getSynchroOrgRecord(
								element.getImportInfo(), element.getOrgType());
						// 更新到数据，但不设置外键，外键设置为null
						try {
							update(sysOrgElement, element);
						} catch (Exception e) {
							jobContext.logMessage("组织架构同步时出错:importinfo="
									+ element.getImportInfo() + ";error="
									+ e.getMessage());
							logger.error("组织架构同步时出错:importinfo="
									+ element.getImportInfo() + ";error="
									+ e.getMessage(), e);
						}
						updated++;
					} else {
						logger.debug("element为空");
						updated_null++;
					}
				}
				TransactionUtils.getTransactionManager().commit(updateDtatus);
				updateDtatus = TransactionUtils.beginNewTransaction();
				echoInfo();
				// 统一更新层级字段fd_hierarchy_id
				orgElementService.updateRelation();
				echoInfo();
				// 提交事务
				TransactionUtils.getTransactionManager().commit(updateDtatus);
				provider.setLastUpdateTime(lastUpdateTime);
			} catch (Exception ex) {
				TransactionUtils.getTransactionManager().rollback(updateDtatus);
				throw ex;
			} finally {
				orgDeptService.setNotToUpdateRelation(null);
				orgOrgService.setNotToUpdateRelation(null);
				orgPersonService.setNotToUpdateRelation(null);
				orgPostService.setNotToUpdateRelation(null);
				orgGroupService.setNotToUpdateRelation(null);
				orgElementService.setNotToUpdateRelation(null);
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
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
	 * @param deleteKeys
	 * @throws Exception
	 */
	private void delete(String[] keywords, String providerKey) throws Exception {
		logger.info("开始处理需要删除的记录");
		if (keywords == null) {
            return;
        }
		jobContext.logMessage("删除组织机构记录数:" + keywords.length + "条记录");
		logger.info("删除组织机构记录数:" + keywords.length + "条记录");
		String deleteSizeStr = new OMSConfig()
				.getValue(OMS_SYNCHRO_IN_DELETE_SIZE);
		int deleteSize = 0;
		if (StringUtil.isNotNull(deleteSizeStr)) {
			try {
				deleteSize = Integer.parseInt(deleteSizeStr);
				if (keywords.length >= deleteSize) {
					jobContext.logMessage("删除组织机构记录数大于:" + deleteSize
							+ "。不直接执行“置为无效”操作，由管理员手动执行");
					// 通知管理员
					try {
						NotifyContext notifyContext = synchroOrgNotify
								.getSyncExceptionNotifyContext();
						if (notifyContext != null) {

							notifyContext
									.setSubject("组织架构导入时需要删除的记录为："
											+ keywords.length
											+ "。请检查这些数据是否都是需要删除的，如果是则手动执行”置为无效“的操作，否则请检查数据以及oms相关配置！");
							notifyContext
									.setContent("组织架构导入时需要删除的记录为："
													+ keywords.length
													+ "。请检查这些数据是否都是需要删除的，如果是则手动执行”置为无效“的操作，否则请检查数据以及oms相关配置！"
											// + "查看需要删除的组织架构："
											// + ResourceUtil
											// .getKmssConfigString("kmss.urlPrefix")
											// +
											// "/sys/organization/sys_org_dept/sysOrgDept.do?method=list&all=true&fdFlagDeleted=true&fdImportInfo="
											// + providerKey
									);
							notifyContext
									.setLink("/sys/organization/sys_org_dept/index.jsp?all=true&fdFlagDeleted=true&fdImportInfo="
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
		for (int i = 0; i < keywords.length; i++) {
			echoInfo();
			SysOrgElement sysOrgElement = orgCoreService
					.findByPrimaryKey(keywords[i]);
			if (sysOrgElement != null) {
				sysOrgElement.setFdIsAvailable(new Boolean(false));
				sysOrgElement.getHbmChildren().clear();
				sysOrgElement.setFdFlagDeleted(new Boolean(false));
				// 停用帐号与删除帐号时，都不要将fdImportInfo清空！不然停用后，再启用同一帐号会出现问题。
				// sysOrgElement.setFdImportInfo(null);
				orgElementService.update(sysOrgElement);
				logger.debug("记录置为无效：" + sysOrgElement.getFdId() + "---"
						+ sysOrgElement.getFdNameOri());
			}
		}
		jobContext.logMessage("删除组织机构耗时::"
				+ (System.currentTimeMillis() - time) / 1000 + "s");
	}

	/**
	 * 将所有的 RecordBaseAttributes 插入更新
	 *
	 * @param rs
	 * @throws Exception
	 */
	private void importAllRecordBaseAttributes(IOMSResultSet rs)
			throws Exception {
		logger.info("开始同步所有记录");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement psselect = null;
		PreparedStatement psinsert = null;
		PreparedStatement psinsert2 = null;
		PreparedStatement psupdate = null;
		PreparedStatement psupdate_no = null;
		PreparedStatement psupdate_keyword = null;
		PreparedStatement psupdate_all = null;
		PreparedStatement psinsert_oms_cache = null;

		Set<String> outTypes = OMSPlugin.getOutTypes();
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			int count_baseAtts_null = 0;
			psselect = conn
					.prepareStatement("select fd_id from sys_org_element where (fd_import_info = ? and (fd_org_type = ? or fd_org_type = ?)) or fd_id = ?");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_element(fd_id,fd_name,fd_org_type,fd_no,fd_keyword,fd_is_available,fd_is_business,fd_import_info,fd_ldap_dn,fd_flag_deleted,fd_create_time) values(?,?,?,?,?,?,?,?,?,?,?)");
			psinsert2 = conn
					.prepareStatement("insert into sys_org_person(fd_id) values(?)");

			psupdate_all = conn
					.prepareStatement("update sys_org_element set fd_name=?,fd_no=?,fd_keyword=?,fd_ldap_dn=?,fd_flag_deleted=? where fd_id=?");
			psupdate = conn
					.prepareStatement("update sys_org_element set fd_name=?,fd_ldap_dn=?,fd_flag_deleted=? where fd_id=?");
			psupdate_no = conn
					.prepareStatement("update sys_org_element set fd_name=?,fd_ldap_dn=?,fd_flag_deleted=?,fd_no=? where fd_id=?");
			psupdate_keyword = conn
					.prepareStatement("update sys_org_element set fd_name=?,fd_ldap_dn=?,fd_flag_deleted=?,fd_keyword=? where fd_id=?");

			psinsert_oms_cache = conn
					.prepareStatement("insert into sys_oms_cache(fd_id,fd_org_element_id,fd_app_name,fd_op_type) values(?,?,?,?)");

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
						conn.commit();
						echoInfo();
					}
					psselect.setString(1, provider.getKey()
							+ baseAtts.getElementUUID());
					int orgType = baseAtts.getElementType();
					psselect.setInt(2, orgType);
					// 兼容LDAP同步，当部门设置为机构时，会找不到原来的记录的问题
					if (orgType == 2) {
						psselect.setInt(3, 1);
					} else {
						psselect.setInt(3, orgType);
					}
					String fd_id = baseAtts.getElementId();
					if (StringUtil.isNull(fd_id)) {
                        psselect.setString(4, null);
                    } else {
                        psselect.setString(4, fd_id);
                    }
					ResultSet rs2 = psselect.executeQuery();
					if (rs2.next()) {
						logger.debug("更新记录：" + baseAtts.getElementUUID()
								+ "---" + baseAtts.getElementLdapDN());
						fd_id = rs2.getString(1);
						if (baseAtts.isElementKeywordNeedSynchro()) {
							if (baseAtts.isElementNumberNeedSynchro()) {
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
						} else {
							if (baseAtts.isElementNumberNeedSynchro()) {
								psupdate_no.setString(1, baseAtts
										.getElementName());
								psupdate_no.setString(2, baseAtts
										.getElementLdapDN());
								psupdate_no.setBoolean(3, false);
								psupdate_no.setString(4, baseAtts
										.getElementNumber());
								psupdate_no.setString(5, fd_id);
								psupdate_no.addBatch();
							} else {
								psupdate
										.setString(1, baseAtts.getElementName());
								psupdate.setString(2, baseAtts
										.getElementLdapDN());
								psupdate.setBoolean(3, false);
								psupdate.setString(4, fd_id);
								psupdate.addBatch();
							}
						}
					} else {
						logger.debug("新增记录：" + baseAtts.getElementUUID()
								+ "---" + baseAtts.getElementLdapDN());
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
						psinsert.addBatch();
						if (baseAtts.getElementType() == 8) {
							psinsert2.setString(1, fd_id);
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

					}
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
			JdbcUtils.closeStatement(psselect);
			JdbcUtils.closeStatement(psinsert);
			JdbcUtils.closeStatement(psinsert2);
			JdbcUtils.closeStatement(psupdate);
			JdbcUtils.closeStatement(psupdate_no);
			JdbcUtils.closeStatement(psupdate_keyword);
			JdbcUtils.closeStatement(psupdate_all);
			JdbcUtils.closeStatement(psinsert_oms_cache);
			JdbcUtils.closeConnection(conn);
		}
	}

	/**
	 * 更具临时表数据和现有数据得到需要删除的元素
	 *
	 * @param providerKey
	 * @return
	 * @throws Exception
	 */
	public String[] getKeywordsForDelete(String providerKey) throws Exception {
		String sql = " SELECT fd_id from sys_org_element where fd_import_info like '"
				+ providerKey
				+ "%' and fd_is_available = ? and fd_flag_deleted = ?";
		List ids = new ArrayList();
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setBoolean(1, true);
			ps.setBoolean(2, true);
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
	 * 数据需要更新
	 *
	 * @param sysOrgElement
	 * @param element
	 * @throws Exception
	 */
	private void update(SysOrgElement sysOrgElement, IOrgElement element)
			throws Exception {
		if (element.getAlterTime() == null) {
			logger.error("记录的更新时间为空，请检查配置和LDAP数据：" + element.getLdapDN());
		} else if (element.getAlterTime().after(lastUpdateTime)) {
			lastUpdateTime = element.getAlterTime();
		}
		if (sysOrgElement instanceof SysOrgOrg) {
			SysOrgOrg sysOrgOrg = (SysOrgOrg) sysOrgElement;
			setSysOrgOrg((IOrgOrg) element, (SysOrgOrg) sysOrgElement);
			if (element.getIsAvailable().booleanValue()) {
				setOrgParent(sysOrgOrg, element, provider
						.getDeptParentValueMapType(), provider
						.getDeptParentValueMapTo(), element.getParent());
				setThisLeader(sysOrgOrg, element, provider
						.getDeptLeaderValueMapType(), provider
						.getDeptLeaderValueMapTo(), element.getThisLeader());
				setSuperLeader(sysOrgOrg, element, provider
						.getDeptSuperLeaderValueMapType(), provider
						.getDeptSuperLeaderValueMapTo(), element
						.getSuperLeader());
			}
			orgOrgService.update(sysOrgOrg);
		}
		if (sysOrgElement instanceof SysOrgDept) {
			SysOrgElement sysOrgDept = sysOrgElement;
			setSysOrgDept((IOrgDept) element, (SysOrgDept) sysOrgElement);
			if (element.getIsAvailable().booleanValue()) {
				setOrgParent(sysOrgDept, element, provider
						.getDeptParentValueMapType(), provider
						.getDeptParentValueMapTo(), element.getParent());
				setThisLeader(sysOrgDept, element, provider
						.getDeptLeaderValueMapType(), provider
						.getDeptLeaderValueMapTo(), element.getThisLeader());
				setSuperLeader(sysOrgDept, element, provider
						.getDeptSuperLeaderValueMapType(), provider
						.getDeptSuperLeaderValueMapTo(), element
						.getSuperLeader());
			}
			orgDeptService.update(sysOrgDept);
		}
		if (sysOrgElement instanceof SysOrgPerson) {
			SysOrgPerson sysOrgPerson = (SysOrgPerson) sysOrgElement;
			setSysOrgPerson((IOrgPerson) element, (SysOrgPerson) sysOrgElement);
			if (element.getIsAvailable().booleanValue()) {
				setOrgParent(sysOrgPerson, element, provider
						.getPersonDeptValueMapType(), provider
						.getPersonDeptValueMapTo(), element.getParent());

				setPosts(sysOrgPerson, (IOrgPerson) element, provider
						.getPersonPostValueMapType(), provider
						.getPersonPostValueMapTo());
			}
			orgPersonService.update(sysOrgPerson);
		}
		if (sysOrgElement instanceof SysOrgPost) {
			SysOrgPost sysOrgPost = (SysOrgPost) sysOrgElement;
			setSysOrgPost((IOrgPost) element, (SysOrgPost) sysOrgElement);
			if (element.getIsAvailable().booleanValue()) {
				setOrgParent(sysOrgPost, element, provider
						.getPostDeptValueMapType(), provider
						.getPostDeptValueMapTo(), element.getParent());
				setPersons(sysOrgPost, (IOrgPost) element, provider
						.getPostPersonValueMapType(), provider
						.getPostPersonValueMapTo());
				setThisLeader(sysOrgPost, element, provider
						.getPostLeaderValueMapType(), provider
						.getPostLeaderValueMapTo(), element.getThisLeader());
			}
			orgPostService.update(sysOrgPost);
		}
		if (sysOrgElement instanceof SysOrgGroup) {
			SysOrgGroup sysOrgGroup = (SysOrgGroup) sysOrgElement;
			setSysOrgGroup((IOrgGroup) element, (SysOrgGroup) sysOrgElement);
			if (element.getIsAvailable().booleanValue()) {
				setMembers(sysOrgGroup, (IOrgGroup) element, provider
						.getGroupMemberValueMapType(), provider
						.getGroupMemberValueMapTo());
			}
			orgGroupService.update(sysOrgGroup);
		}
	}

	// /////
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
			String memo = element.getMemo();
			if (StringUtil.isNotNull(memo)) {
				memo = StringUtil.unescape(memo);
			}
			sysOrgElement.setFdMemo(memo);
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
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("langProps")) {
			sysOrgElement.getDynamicMap().putAll(element.getDynamicMap());
		}
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("customProps")) {
			Map dynamicMap = DynamicAttributeUtil.convertCustomProp(
					SysOrgPerson.class.getName(), element.getCustomMap());
			sysOrgElement.getCustomPropMap().putAll(dynamicMap);
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
				|| person.getRequiredOms().contains("scard")) {
			sysOrgPerson.setFdCardNo(person.getScard());
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("sex")) {
			sysOrgPerson.setFdSex(person.getSex());
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("shortNo")) {
			sysOrgPerson.setFdShortNo(person.getShortNo());
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
			if ((provider.getPasswordType() & PASSWORD_TYPE_NOT_TRANSFER) != PASSWORD_TYPE_NOT_TRANSFER) {
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

	/**
	 * 外键信息
	 *
	 * @param sql
	 * @throws Exception
	 */
	private List getForeignKey(ValueMapType[] searchType,
							   ValueMapTo searchName, String[] searchValue) throws Exception {
		String[] fdids;
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
					selectWhere += " and sysOrgElement."
							+ searchName.getColumnName()
							+ " =:searchName and sysOrgElement.fdIsAvailable=:fdIsAvailable";
					hqlInfo.setParameter("fdIsAvailable",true);
					if (searchName == ValueMapTo.IMPORTINFO) {
                        hqlInfo.setParameter("searchName", provider.getKey()
                                + searchValue[0]);
                    } else {
                        hqlInfo.setParameter("searchName", searchValue[0]);
                    }
				} else {
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
						hqlInfo.setParameter("fdIsAvailable",true);
					}
					// if (temp.length() > 0) {
					// temp = temp.substring(1);
					// selectWhere += " and sysOrgElement."
					// + searchName.getColumnName() + " in( " + temp
					// + ") and sysOrgElement.fdIsAvailable=1";
					// }
				}
			}
			hqlInfo.setSelectBlock("sysOrgElement.fdId");
			hqlInfo.setWhereBlock(selectWhere);
			fdids = (String[]) orgElementService.getBaseDao()
					.findValue(hqlInfo).toArray(new String[0]);
		}
		return orgElementService.findByPrimaryKeys(fdids);

	}

	private void setOrgParent(SysOrgElement sysOrgElement, IOrgElement element,
							  ValueMapType[] valueMpaType, ValueMapTo valueMapTo, String value)
			throws Exception {
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("parent")) {
			if (StringUtil.isNull(value)) {
				sysOrgElement.setFdParent(null);
				return;
			}
			if (valueMapTo == null) {
				sysOrgElement.setFdParent(null);
				return;
			}
			List perents = getForeignKey(valueMpaType, valueMapTo,
					new String[] { value });
			if (perents != null && perents.size() > 0) {
				SysOrgElement parent = (SysOrgElement) perents.get(0);
				if (parent.getFdIsAvailable()) {
					sysOrgElement.setFdParent(parent);
				}
			}
		}
	}

	private void setThisLeader(SysOrgElement sysOrgElement,
							   IOrgElement element, ValueMapType[] valueMpaType,
							   ValueMapTo valueMapTo, String value) throws Exception {
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("thisLeader")) {
			if (StringUtil.isNull(element.getThisLeader())) {
				sysOrgElement.setHbmThisLeader(null);
				return;
			}
			if (valueMapTo == null) {
				sysOrgElement.setHbmThisLeader(null);
				return;
			}
			List thisLeaders = getForeignKey(valueMpaType, valueMapTo,
					new String[] { value });
			if (thisLeaders != null && thisLeaders.size() > 0) {
				SysOrgElement thisLeader = (SysOrgElement) thisLeaders.get(0);
				if (thisLeader.getFdIsAvailable()) {
					sysOrgElement.setHbmThisLeader(thisLeader);
				}
			}
		}
	}

	private void setSuperLeader(SysOrgElement sysOrgElement,
								IOrgElement element, ValueMapType[] valueMpaType,
								ValueMapTo valueMapTo, String value) throws Exception {
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("superLeader")) {
			if (StringUtil.isNull(element.getSuperLeader())) {
				sysOrgElement.setHbmSuperLeader(null);
				return;
			}
			if (valueMapTo == null) {
				sysOrgElement.setHbmSuperLeader(null);
				return;
			}
			List superLeaders = getForeignKey(valueMpaType, valueMapTo,
					new String[] { value });
			if (superLeaders != null && superLeaders.size() > 0) {
				SysOrgElement superLeader = (SysOrgElement) superLeaders.get(0);
				if (superLeader.getFdIsAvailable()) {
					sysOrgElement.setHbmSuperLeader(superLeader);
				}
			}
		}
	}

	private void setPosts(SysOrgPerson sysOrgPerson, IOrgPerson person,
						  ValueMapType[] valueMpaType, ValueMapTo valueMapTo)
			throws Exception {
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("posts")) {
			String[] posts = person.getPosts();
			if (posts == null || posts.length == 0) {
				sysOrgPerson.setFdPosts(null);
				return;
			}
			if (valueMapTo == null) {
				sysOrgPerson.setFdPosts(null);
				return;
			}
			List list = getForeignKey(valueMpaType, valueMapTo, person
					.getPosts());
			sysOrgPerson.setFdPosts(list);
		}
	}

	private void setPersons(SysOrgPost sysOrgPost, IOrgPost post,
							ValueMapType[] valueMpaType, ValueMapTo valueMapTo)
			throws Exception {
		if (post.getRequiredOms() == null
				|| post.getRequiredOms().contains("persons")) {
			String[] persons = post.getPersons();
			if (persons == null || persons.length == 0) {
				sysOrgPost.setFdPersons(null);
				return;
			}
			if (valueMapTo == null) {
				sysOrgPost.setFdPersons(null);
				return;
			}
			List list = getForeignKey(valueMpaType, valueMapTo, post
					.getPersons());
			sysOrgPost.setFdPersons(list);
		}
	}

	private void setMembers(SysOrgGroup sysOrgGroup, IOrgGroup group,
							ValueMapType[] valueMpaType, ValueMapTo valueMapTo)
			throws Exception {
		if (group.getRequiredOms() == null
				|| group.getRequiredOms().contains("members")) {
			String[] members = group.getMembers();
			if (members == null || members.length == 0) {
				sysOrgGroup.setFdMembers(null);
				return;
			}
			if (valueMapTo == null) {
				sysOrgGroup.setFdMembers(null);
				return;
			}
			List list = getForeignKey(valueMpaType, valueMapTo, group
					.getMembers());
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

	@Override
	public List<String[]> getDeptcache() {
		// TODO Auto-generated method stub
		return null;
	}
}
