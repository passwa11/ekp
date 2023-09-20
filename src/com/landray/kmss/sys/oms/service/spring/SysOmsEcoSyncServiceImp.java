package com.landray.kmss.sys.oms.service.spring;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.config.constant.TypeEnum;
import com.landray.kmss.sys.config.service.SysTmplServiceImp;
import com.landray.kmss.sys.oms.OMSPlugin;
import com.landray.kmss.sys.oms.SysOMSConstant;
import com.landray.kmss.sys.oms.in.IOMSSynchroInIteratorProviderRunner;
import com.landray.kmss.sys.oms.in.interfaces.HideRange;
import com.landray.kmss.sys.oms.in.interfaces.IOMSElementBaseAttribute;
import com.landray.kmss.sys.oms.in.interfaces.IOMSResultEcoSet;
import com.landray.kmss.sys.oms.in.interfaces.IOMSResultSet;
import com.landray.kmss.sys.oms.in.interfaces.IOMSSynchroIteratorEcoProvider;
import com.landray.kmss.sys.oms.in.interfaces.IOrgDept;
import com.landray.kmss.sys.oms.in.interfaces.IOrgElement;
import com.landray.kmss.sys.oms.in.interfaces.IOrgGroup;
import com.landray.kmss.sys.oms.in.interfaces.IOrgOrg;
import com.landray.kmss.sys.oms.in.interfaces.IOrgPerson;
import com.landray.kmss.sys.oms.in.interfaces.IOrgPost;
import com.landray.kmss.sys.oms.in.interfaces.ValueMapTo;
import com.landray.kmss.sys.oms.in.interfaces.ValueMapType;
import com.landray.kmss.sys.oms.in.interfaces.ViewRange;
import com.landray.kmss.sys.oms.service.ISysOmsEcoSyncService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementExtProp;
import com.landray.kmss.sys.organization.model.SysOrgElementExtPropEnum;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.sys.organization.model.SysOrgElementHideRange;
import com.landray.kmss.sys.organization.model.SysOrgElementRange;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgOrg;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.IKmssPasswordEncoder;
import com.landray.kmss.sys.organization.service.ISysOrgDeptService;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.sys.organization.service.ISysOrgOrgService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SecureUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionStatus;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.landray.kmss.sys.organization.model.SysOrgElementExtProp.TYPE_DEPT;
import static com.landray.kmss.sys.organization.model.SysOrgElementExtProp.TYPE_PERSON;

public class SysOmsEcoSyncServiceImp implements ISysOmsEcoSyncService, SysOrgConstant, SysOMSConstant {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOmsEcoSyncServiceImp.class);

	private IOMSSynchroIteratorEcoProvider provider;

	private IOMSSynchroInIteratorProviderRunner runner = null;

	private ISysOrgCoreService orgCoreService = null;

	private ISysOrgElementService orgElementService = null;

	private ISysOrgOrgService orgOrgService = null;

	private ISysOrgDeptService orgDeptService = null;

	private ISysOrgPersonService orgPersonService = null;

	private ISysOrgPostService orgPostService = null;

	private ISysOrgGroupService orgGroupService = null;

	private IKmssPasswordEncoder passwordEncoder;

	private ISysOrgElementExternalService sysOrgElementExternalService;

	private SysTmplServiceImp sysTmplService;

	private Date lastUpdateTime;

	public void setOrgCoreService(ISysOrgCoreService orgCoreService) {
		this.orgCoreService = orgCoreService;
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

	public void setOrgPostService(ISysOrgPostService orgPostService) {
		this.orgPostService = orgPostService;
	}

	public void setOrgGroupService(ISysOrgGroupService orgGroupService) {
		this.orgGroupService = orgGroupService;
	}

	public void setPasswordEncoder(IKmssPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setSysTmplService(SysTmplServiceImp sysTmplService) {
		this.sysTmplService = sysTmplService;
	}

	public void setSysOrgElementExternalService(ISysOrgElementExternalService sysOrgElementExternalService) {
		this.sysOrgElementExternalService = sysOrgElementExternalService;
	}

	public void setProvider(IOMSSynchroIteratorEcoProvider provider) {
		this.provider = provider;
	}

	private DataSource dataSource;

	private DataSource getDataSource() {
		if (dataSource == null) {
			dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		}
		return dataSource;
	}

	private long startTime;
	private long lastEchoTime;
	private int updated;

	/**
	 * 打印日志
	 */
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
				this.runner.getJobContext().logMessage("生态组织同步正在运行：已经运行" + info + ".更新数目:" + updated);
				if (logger.isInfoEnabled()) {
					logger.info("生态组织同步正在运行：已经运行" + info + ".更新数目:" + updated);
				}
			} else {
				this.runner.getJobContext().logMessage("生态组织同步正在运行：已经运行" + info + ".");
				if (logger.isInfoEnabled()) {
					logger.info("生态组织同步正在运行：已经运行" + info + ".");
				}
			}
		}
	}

	@Override
	public void handleEcoSync(IOMSSynchroInIteratorProviderRunner runner) {
		IOMSSynchroIteratorEcoProvider ecoProvider = (IOMSSynchroIteratorEcoProvider) OMSPlugin.getEcoSynchroExtension();
		if (ecoProvider == null) {
			logger.error("获取ekp-j的生态组织扩展点失败！");
			return;
		}
		this.provider = ecoProvider;
		try {
			provider.init();
		} catch (Exception e1) {
			return;
		}
		this.startTime = System.currentTimeMillis();
		this.runner = runner;
		this.lastUpdateTime = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 365L);
		Long startTime = System.currentTimeMillis();
		try {
			// 生态基本数据
			updated = 0;
			this.runner.getJobContext().logMessage("准备更新生态组织基本数据");
			logger.info("准备更新生态组织基本数据");
			handleEcoBaseSync();
			this.runner.getJobContext().logMessage("生态组织基本数据更新完成");
			logger.info("生态组织基本数据更新完成");
			echoInfo();
			// 生态查看权限
			updated = 0;
			this.runner.getJobContext().logMessage("准备更新生态组织详情信息（包含查看权限）");
			logger.info("准备更新生态组织详情信息（包含查看权限）");
			handleViewRangeSync();
			this.runner.getJobContext().logMessage("生态组织详情信息（包含查看权限）更新完成");
			logger.info("生态组织详情信息（包含查看权限）更新完成");
			echoInfo();
			// 生态动态扩展数据
			updated = 0;
			this.runner.getJobContext().logMessage("准备更新生态组织动态扩展数据");
			logger.info("准备更新生态组织动态扩展数据");
			handleEcoDynamicSync();
			this.runner.getJobContext().logMessage("生态组织动态扩展数据更新完成");
			logger.info("生态组织动态扩展数据更新完成");
			echoInfo();
			provider.setLastUpdateTime(lastUpdateTime);
		} catch (Exception e) {
			this.runner.getJobContext().logError("生态组织同步失败", e);
			logger.error("生态组织同步失败！", e);
		} finally {
			try {
				provider.terminate();
			} catch (Exception e) {
				this.runner.getJobContext().logError(provider.getClass().getName() + "的terminate()调用失败！", e);
				logger.error(provider.getClass().getName() + "的terminate()调用失败！");
			}
		}
		this.runner.getJobContext().logMessage("生态组织耗时：" + (System.currentTimeMillis() - startTime) / 1000.0 + "秒");
		if (logger.isInfoEnabled()) {
			logger.info("生态组织耗时：" + (System.currentTimeMillis() - startTime) / 1000.0 + "秒");
		}
	}

	private void handleEcoBaseSync() {
		// 判断是否处理生态组织
		TransactionStatus status = null;
		Exception t = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			IOMSResultSet resultSet = provider.getEcoRecordBaseAttributesByHttp();
			if (resultSet != null) {
				while (resultSet.next()) {
					// 置为删除状态
					flagDeleted();

					// 将生态组织基本属性插入更新
					importEcoRecordBaseAttributes(resultSet);

					// 删除数据
					delete();
				}
			}
			TransactionUtils.commit(status);
		} catch (Exception e) {
			t = e;
			logger.error("生态组织同步，同步基础数据错误！", e);
		} finally {
			if (t != null && status != null) {
				TransactionUtils.rollback(status);
			}
		}
	}

	/**
	 * 更新详情
	 */
	private void handleViewRangeSync() {
		TransactionStatus status = null;
		Exception t = null;
		int count = 0;
		try {
			status = TransactionUtils.beginNewTransaction();
			// 更新全量数据
			long stime = System.currentTimeMillis();
			logger.info("正在调用远程接口获取生态组织详情信息");
			this.runner.getJobContext().logMessage("正在调用远程接口获取生态组织详情信息");
			IOMSResultSet rs = provider.getSynchroRecordsByHttp(null);
			String msg = "远程接口调用成功，耗时：" + ((System.currentTimeMillis() - stime) / 1000) + "秒，正在同步生态组织详情信息";
			logger.info(msg);
			this.runner.getJobContext().logMessage(msg);
			echoInfo();
			if (rs != null) {
				while (rs.next()) {
					IOrgElement element = rs.getElement();
					if (element != null) {
						if (count > 0 && count % 1000 == 0) {
							TransactionUtils.commit(status);
							status = TransactionUtils.beginNewTransaction();
							if (logger.isInfoEnabled()) {
								logger.info("批量提交事务（更新生态组织详情）");
							}
							echoInfo();
						}
						SysOrgElement sysOrgElement = getSynchroOrgRecord(element.getId(), element.getOrgType());
						if (sysOrgElement == null) {
							msg = "生态系统同步错误，通过provider:" + provider.getKey() + ", fdId:" + element.getId() + "在系统中未找到！";
							this.runner.getJobContext().logMessage(msg);
							logger.error(msg);
							continue;
						}
						try {
							update(sysOrgElement, element);
							count++;
							updated++;
						} catch (Exception e) {
							msg = "组织同步更新查看范围时出错:importinfo=" + element.getImportInfo() + ";error=" + e.getMessage();
							this.runner.getJobContext().logMessage(msg);
							logger.error(msg, e);
						}
					}
				}
			} else {
				logger.warn("生态组织同步，数据方可能不包含生态系统。");
				this.runner.getJobContext().logMessage("生态组织同步，数据方可能不包含生态系统。");
			}
			TransactionUtils.commit(status);
		} catch (Exception e) {
			t = e;
			logger.error("组织同步，同步查看范围数据错误！", e);
			this.runner.getJobContext().logMessage("组织同步，同步查看范围数据错误！");
		} finally {
			if (t != null && status != null) {
				TransactionUtils.rollback(status);
			}
		}
	}

	private void importEcoRecordBaseAttributes(IOMSResultSet rs)
			throws Exception {
		logger.info("开始同步所有记录");
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
			conn = getDataSource().getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			int count_baseAtts_null = 0;
			psselect = conn
					.prepareStatement("select fd_id from sys_org_element where fd_is_external = ? and ((fd_import_info = ? and (fd_org_type = ? or fd_org_type = ?)) or fd_id = ?)");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_element(fd_id,fd_name,fd_org_type,fd_no,fd_keyword,fd_is_available,fd_is_business,fd_import_info,fd_ldap_dn,fd_flag_deleted,fd_create_time,fd_is_external) values(?,?,?,?,?,?,?,?,?,?,?,?)");
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
				IOMSElementBaseAttribute baseAtts = rs.getElementBaseAttribute();
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
					}
					psselect.setBoolean(1, Boolean.TRUE);
					psselect.setString(2, provider.getKey()
							+ baseAtts.getElementUUID());
					int orgType = baseAtts.getElementType();
					psselect.setInt(3, orgType);
					// 兼容LDAP同步，当部门设置为机构时，会找不到原来的记录的问题
					if (orgType == 2) {
						psselect.setInt(4, 1);
					} else {
						psselect.setInt(4, orgType);
					}
					String fd_id = baseAtts.getElementId();
					if (StringUtil.isNull(fd_id)) {
						psselect.setString(5, null);
					} else {
						psselect.setString(5, fd_id);
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
						psinsert.setBoolean(12, true); // 外部组织
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
						runner.getDeptcache().add(dept);
					}
					loop++;
				} else {
					logger.debug("baseAtts为空");
					count_baseAtts_null++;
				}
				updated++;
			}
			psinsert.executeBatch();
			psinsert2.executeBatch();
			psupdate.executeBatch();
			psupdate_no.executeBatch();
			psupdate_keyword.executeBatch();
			psupdate_all.executeBatch();
			psinsert_oms_cache.executeBatch();
			conn.commit();
			if (logger.isInfoEnabled()) {
				logger.info("更新importInfo信息：" + loop);
				logger.info("baseAtts为空的记录数：" + count_baseAtts_null);
			}
			this.runner.getJobContext().logMessage("更新importInfo信息：" + loop);
		} catch (Exception ex) {
			logger.error("同步所有记录时出错", ex);
			this.runner.getJobContext().logError("同步所有记录时出错", ex);
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

	private void flagDeleted() throws Exception {
		logger.debug("设置生态组织组织待删除标识");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getDataSource().getConnection();
			conn.setAutoCommit(false);
			ps = conn
					.prepareStatement("update sys_org_element set fd_flag_deleted = ? where fd_is_external = ? and fd_import_info like '"
							+ provider.getKey() + "%'");
			ps.setBoolean(1, Boolean.TRUE);
			ps.setBoolean(2, Boolean.TRUE);
			ps.executeUpdate();
			conn.commit();
		} catch (Exception ex) {
			logger.error("设置生态组织待删除标识时出错", ex);
			conn.rollback();
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
	}

	private void delete() throws Exception {
		String sql = " SELECT fd_id from sys_org_element where fd_import_info like '"
				+ provider.getKey()
				+ "%' and fd_is_available = ? and fd_flag_deleted = ? and fd_is_external = ?";
		List<String> ids = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getDataSource().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setBoolean(1, Boolean.TRUE);
			ps.setBoolean(2, Boolean.TRUE);
			ps.setBoolean(3, Boolean.TRUE);
			rs = ps.executeQuery();
			while (rs.next()) {
				ids.add(rs.getString(1));
			}

			logger.info("开始处理生态组织需要删除的记录");
			if (CollectionUtils.isEmpty(ids)) {
				return;
			}
			logger.info("删除生态组织记录数:" + ids.size() + "条记录");

			for (int i = 0; i < ids.size(); i++) {
				SysOrgElement sysOrgElement = orgCoreService.findByPrimaryKey(ids.get(i));
				if (sysOrgElement != null) {
					sysOrgElement.setFdIsAvailable(Boolean.FALSE);
					sysOrgElement.getHbmChildren().clear();
					sysOrgElement.setFdFlagDeleted(Boolean.FALSE);
					// 停用帐号与删除帐号时，都不要将fdImportInfo清空！不然停用后，再启用同一帐号会出现问题。
					// sysOrgElement.setFdImportInfo(null);
					orgElementService.update(sysOrgElement);
					logger.warn("记录生态组织置为无效：" + sysOrgElement.getFdId() + "---"
							+ sysOrgElement.getFdNameOri());
				}
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
	}

	private void handleEcoDynamicSync() throws Exception {
		IOMSResultEcoSet rs;
		try {
			rs = provider.getDynamicExternalData();
		} catch (Exception e1) {
			logger.error("生态组织同步获取动态扩展数据http失败！", e1);
			return;
		}
		if (rs != null) {
			List<SysOrgElementExternal> externals = new ArrayList<>();
			JSONArray attrArr = new JSONArray();
			Object elemenId = new Object();
			while (rs.next()) {
				JSONObject obj = rs.getObject();
				elemenId = obj.get("elementId");
				if (elemenId == null) {
					logger.error("生态组织同步错误,同步过来得组织类型id为空!");
					continue;
				}

				TransactionStatus status = null;
				Exception t = null;
				try {
					status = TransactionUtils.beginNewTransaction();
					String fdId = (String) elemenId;
					String deptTable = (String) obj.get("deptTable");
					String personTable = (String) obj.get("personTable");
					IBaseModel model = sysOrgElementExternalService.findByPrimaryKey(fdId, null, true);
					if (model != null) {
						sysOrgElementExternalService.delete(model);
						if (isExistTable(deptTable)) {
							clearTable(deptTable);
						}
						if (isExistTable(personTable)) {
							clearTable(personTable);
						}
					} else {
						logger.error("组织类型不存在，不更新此组织：id=" + fdId);
						//注释跳过代码，如果跳到下一次循环则永远都无法把数据添加至sys_org_element_external表中（说明是新增，需要继续往下执行）#152200
						//continue;
					}

					SysOrgElement elem = getSynchroOrgRecord(fdId, ORG_TYPE_ORG);
					if (elem == null) {
						logger.error("组织类型不存在，不更新此组织：id=" + fdId);
						continue;
					}
					// 1.基础数据
					SysOrgElementExternal external = new SysOrgElementExternal();

					external.setFdId(fdId);
					external.setFdDeptTable(deptTable);
					external.setFdPersonTable(personTable);
					external.setFdElement((SysOrgOrg) elem);

					// 2.属性表数据
					Object deptPropsObj = obj.get("d_list");
					if (deptPropsObj == null) {
						logger.error("生态组织同步错误,组织类型id： " + elem.getFdId() + "组织类型部门属性列表数据为空!");
						continue;
					}
					Object personPropsObj = obj.get("p_list");
					if (personPropsObj == null) {
						logger.error("生态组织同步错误,组织类型id： " + elem.getFdId() + "组织类型人员属性列表数据为空!");
						continue;
					}
					List<SysOrgElementExtProp> deptProps = buildProps((JSONArray) deptPropsObj);
					List<SysOrgElementExtProp> personProps = buildProps((JSONArray) personPropsObj);
					external.setFdDeptProps(deptProps);
					external.setFdPersonProps(personProps);

					// 3.更新或者插入属性表
					updateTable(external);

					// 4.组织管理员数据
					Object authReaders = obj.get("authReaders");
					if (authReaders != null) {
						JSONArray authReadersArr = (JSONArray) authReaders;
						if (authReadersArr.size() > 0) {
							List reders = getForeignKey(provider.getAdminsValueMapType(),
									provider.getAdminsValueMapTo(), toStringArr(authReadersArr));
							external.setAuthReaders(reders);
						}
					}

					sysOrgElementExternalService.addExternal(external);
					externals.add(external);

					JSONObject attrObj = new JSONObject();
					attrObj.put("deptTable", deptTable);
					attrObj.put("personTable", personTable);
					attrObj.put("d_list", deptPropsObj);
					attrObj.put("p_list", personPropsObj);
					Object deptData = obj.get("d_data");
					if (deptData != null) {
						attrObj.put("d_data", deptData);
					}
					Object personData = obj.get("p_data");
					if (personData != null) {
						attrObj.put("p_data", personData);
					}
					attrArr.add(attrObj);
					TransactionUtils.commit(status);
				} catch (Exception e) {
					logger.error("生态组织同步错误！fdId: " + elemenId + "的组织类型同步失败了！", e);
					t = e;
					throw e;
				} finally {
					if (t != null && status != null) {
						TransactionUtils.rollback(status);
					}
				}
			}

			// 保存生态属性
			TransactionStatus status = null;
			Exception t = null;
			try {
				status = TransactionUtils.beginNewTransaction();
				for (int i = 0; i < attrArr.size(); i++) {
					JSONObject obj = (JSONObject) attrArr.get(i);
					Object deptData = obj.get("d_data");
					if (deptData != null) {
						String deptTable = (String) obj.get("deptTable");
						List<SysOrgElementExtProp> deptProps = buildProps((JSONArray) obj.get("d_list"));
						batchInsertProps(deptTable, deptProps, (JSONArray) deptData);
					}
					Object personData = obj.get("p_data");
					if (personData != null) {
						String personTable = (String) obj.get("personTable");
						List<SysOrgElementExtProp> personProps = buildProps((JSONArray) obj.get("p_list"));
						batchInsertProps(personTable, personProps, (JSONArray) personData);
					}
					updated++;
				}
				TransactionUtils.commit(status);
			} catch (Exception e) {
				t = e;
				throw e;
			} finally {
				if (t != null && status != null) {
					TransactionUtils.rollback(status);
				}
			}

			this.runner.getJobContext().logMessage("更新生态组织动态属性：" + externals.size() + "条");
		} else {
			logger.info("生态组织同步，数据方可能不包含生态系统。");
		}
	}

	private String[] toStringArr(JSONArray array) {
		if (array == null) {
			return null;
		}
		String[] result = new String[array.size()];
		for (int i = 0; i < array.size(); i++) {
			result[i] = (String) array.get(i);
		}
		return result;
	}

	private void updateTable(SysOrgElementExternal external) throws Exception {
		// 更新部门关联
		if (!isExistTable(external.getFdDeptTable())) {
			generTable(external.getFdDeptTable(), external.getFdDeptProps());
		} else {
			JSONObject oriDeptRelations = getOriProps(external.getFdId(), TYPE_DEPT);
			if (oriDeptRelations.size() == 0) {
				// 事务问题特殊处理
				deleteTable(external.getFdDeptTable());
				generTable(external.getFdDeptTable(), external.getFdDeptProps());
			} else {
				updateProps(external.getFdDeptTable(), external.getFdDeptProps(), oriDeptRelations);
			}
		}

		// 更新人员关联
		if (!isExistTable(external.getFdPersonTable())) {
			generTable(external.getFdPersonTable(), external.getFdPersonProps());
		} else {
			JSONObject oriPersonRelations = getOriProps(external.getFdId(), TYPE_PERSON);
			if (oriPersonRelations.size() == 0) {
				// 事务问题特殊处理
				deleteTable(external.getFdPersonTable());
				generTable(external.getFdPersonTable(), external.getFdPersonProps());
			} else {
				updateProps(external.getFdPersonTable(), external.getFdPersonProps(), oriPersonRelations);
			}
		}
	}

	private List<Object> executeQuery(String sql, List<String> params) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("执行SQL：" + sql);
			logger.info("执行SQL参数：" + params);
		}
		NativeQuery query = sysOrgElementExternalService.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
        // 启用二级缓存
        query.setCacheable(true);
        // 设置缓存模式
        query.setCacheMode(CacheMode.NORMAL);
        // 设置缓存区域
        query.setCacheRegion("sys-oms");

		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				String param = params.get(i);
				query.setParameter(i, param);
			}
		}
		return query.list();
	}

	private void updateProps(String tableName, List<SysOrgElementExtProp> props, JSONObject oriProps) throws Exception {
		if (CollectionUtils.isNotEmpty(props)) {
			for (SysOrgElementExtProp prop : props) {
				if (StringUtil.isNull(prop.getFdColumnName())) {
					continue;
				}
				// 判断是否新增
				if (!oriProps.containsKey(prop.getFdColumnName())) {
					// 新增字段
					// ALTER TABLE table_name ADD column_name VARCHAR(20) NULL
					try {
						executeUpdate("ALTER TABLE " + tableName + " ADD " + prop.getFdColumnName() + " " + getColumnType(prop) + " NULL", null);
					} catch (Exception e) {
						logger.error("生态组织增加扩展字段失败：", e);
					}
				} else {
					oriProps.remove(prop.getFdColumnName());
				}
			}
			// 删除不存在的字段
			if (!oriProps.isEmpty()) {
				for (Object key : oriProps.keySet()) {
					String columnName = key.toString();
					deleteProp(tableName, columnName, (String) oriProps.get(columnName));
				}
			}
		}
	}

	private void deleteProp(String tableName, String columnName, String propId) throws Exception {
		// 判断此属性是否有数据
		if (countByColumn(tableName, columnName) > 0) {
			throw new KmssRuntimeException(new KmssMessage("sys-organization:sysOrgElementExtProp.column.not.empty", columnName));
		}
		try {
			// 删除字段
			// ALTER TABLE table_name DROP COLUMN column_name
			executeUpdate("ALTER TABLE " + tableName + " DROP COLUMN " + columnName, null);
		} catch (Exception e) {
			logger.error("删除扩展字段失败：", e);
		}
		List<Object> params = new ArrayList<>();
		params.add(propId);
		// 删除枚举数据
		executeUpdate("DELETE FROM sys_org_element_ext_prop_enum WHERE fd_ext_prop_id = ?", params);
		// 删除关系
		executeUpdate("DELETE FROM sys_org_element_ext_prop WHERE fd_id = ?", params);
	}

	/**
	 * 查询某一列是否有数据
	 */
	public int countByColumn(String tableName, String fieldName) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(fd_id) FROM ").append(tableName).append(" WHERE ").append(fieldName).append(" IS NOT NULL");
		try {
			List<Object> list = executeQuery(sql.toString(), null);
			return Integer.valueOf(list.get(0).toString());
		} catch (Exception e) {
			logger.error("查询扩展字段内容失败：", e);
			return 0;
		}
	}

	/**
	 * 获取原来的属性关系
	 *
	 * @param externalId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private JSONObject getOriProps(String externalId, String type) throws Exception {
		String sql = "SELECT fd_id, fd_column_name FROM sys_org_element_ext_prop WHERE fd_external_id = ? AND fd_type = ?";
		List<String> params = new ArrayList<String>();
		params.add(externalId);
		params.add(type);
		List<Object> list = executeQuery(sql.toString(), params);
		JSONObject json = new JSONObject();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				json.put(objs[1], objs[0]);
			}
		}
		return json;
	}

	private boolean isExistTable(String tableName) {
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getDataSource().getConnection();
			rs = conn.getMetaData().getTables(null, null, tableName, new String[]{"TABLE"});
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeConnection(conn);
		}
	}

	private List<SysOrgElementExtProp> buildProps(JSONArray arr) {
		List<SysOrgElementExtProp> props = new ArrayList<>();
		if (arr != null && arr.size() > 0) {
			for (int i = 0; i < arr.size(); i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				SysOrgElementExtProp prop = new SysOrgElementExtProp();
				Object fdId = obj.get("fdId");
				if (fdId != null) {
					prop.setFdId((String) fdId);
				}
				Object fdType = obj.get("fdType");
				if (fdType != null) {
					prop.setFdType((String) fdType);
				}
				Object fdName = obj.get("fdName");
				if (fdName != null) {
					prop.setFdName((String) fdName);
				}
				Object fdOrder = obj.get("fdOrder");
				if (fdOrder != null) {
					prop.setFdOrder(Integer.parseInt(fdOrder.toString()));
				}
				Object fdFieldName = obj.get("fdFieldName");
				if (fdFieldName != null) {
					prop.setFdFieldName((String) fdFieldName);
				}
				Object fdColumnName = obj.get("fdColumnName");
				if (fdColumnName != null) {
					prop.setFdColumnName((String) fdColumnName);
				}
				Object fdFieldType = obj.get("fdFieldType");
				if (fdFieldType != null) {
					prop.setFdFieldType((String) fdFieldType);
				}
				Object fdFieldLength = obj.get("fdFieldLength");
				if (fdName != null) {
					prop.setFdFieldLength(Integer.parseInt(fdFieldLength.toString()));
				}
				Object fdScale = obj.get("fdScale");
				if (fdScale != null) {
					prop.setFdScale(Integer.parseInt(fdScale.toString()));
				}
				Object fdRequired = obj.get("fdRequired");
				if (fdRequired != null) {
					prop.setFdRequired(Boolean.parseBoolean(fdRequired.toString()));
				}
				Object fdStatus = obj.get("fdStatus");
				if (fdStatus != null) {
					prop.setFdStatus(Boolean.parseBoolean(fdStatus.toString()));
				}
				Object fdShowList = obj.get("fdShowList");
				if (fdShowList != null) {
					prop.setFdShowList(Boolean.parseBoolean(fdShowList.toString()));
				}
				Object fdDisplayType = obj.get("fdDisplayType");
				if (fdDisplayType != null) {
					prop.setFdDisplayType(fdDisplayType.toString());
				}
				Object enums = obj.get("enums");
				List<SysOrgElementExtPropEnum> enumList = new ArrayList<>();
				if (enums != null) {
					JSONArray enumArr = (JSONArray) enums;
					for (int n = 0; n < enumArr.size(); n++) {
						SysOrgElementExtPropEnum propEnum = new SysOrgElementExtPropEnum();
						propEnum.setFdExtProp(prop);
						JSONObject enumObj = (JSONObject) enumArr.get(n);
						Object _fdId = enumObj.get("fdId");
						if (_fdId != null) {
							propEnum.setFdId((String) _fdId);
						}
						Object _fdOrder = enumObj.get("fdOrder");
						if (_fdOrder != null) {
							propEnum.setFdOrder(Integer.parseInt(_fdOrder.toString()));
						}
						Object _fdName = enumObj.get("fdName");
						if (_fdName != null) {
							propEnum.setFdName((String) _fdName);
						}
						Object _fdValue = enumObj.get("fdValue");
						if (_fdValue != null) {
							propEnum.setFdValue((String) _fdValue);
						}
						enumList.add(propEnum);
					}
					prop.setFdFieldEnums(enumList);
				}
				props.add(prop);
			}
		}
		return props;
	}

	private void batchInsertProps(String tableName, List<SysOrgElementExtProp> props, JSONArray arr) {
		if (CollectionUtils.isEmpty(arr)) {
			return;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ").append(tableName).append(" (fd_id");
		StringBuilder sb = new StringBuilder();
		Map<String, String> timeStrMap = new HashMap<>();
		for (int i = 0; i < props.size(); i++) {
			SysOrgElementExtProp prop = props.get(i);
			sql.append(", ").append(prop.getFdColumnName());
			sb.append("?, ");
			if ("java.util.Date".equals(prop.getFdFieldType())) {
				timeStrMap.put(prop.getFdColumnName(), prop.getFdDisplayType());
			}
		}
		sql.append(") VALUES (").append(sb.toString()).append("?)");
		Connection conn = null;
		PreparedStatement psinsert = null;
		try {
			conn = getDataSource().getConnection();
			psinsert = conn.prepareStatement(sql.toString());
			conn.setAutoCommit(false);
			for (int i = 0; i < arr.size(); i++) {
				if (i > 0 && i % 2000 == 0) {
					psinsert.executeBatch();
					conn.commit();
				}
				JSONArray rowData = (JSONArray) arr.get(i);
				for (int k = 0; k < rowData.size(); k++) {
					JSONObject cloumObj = (JSONObject) rowData.get(k);
					Set<Map.Entry<String, Object>> entrySet = cloumObj.entrySet();
					for (Map.Entry<String, Object> entry : entrySet) {
						Object _value = entry.getValue();
						if (_value == null) { // 值可能为空
							psinsert.setString(k + 1, null);
							continue;
						}
						String value = entry.getValue().toString();
						if (timeStrMap.keySet().contains(entry.getKey())) {
							String pattern = ResourceUtil.getString("date.format." + timeStrMap.get(entry.getKey()));
							// 兼容HH:mm报错的问题
							if ("HH:mm".equals(pattern)) {
								pattern = ResourceUtil.getString("date.format.datetime");
							}
							Date date = DateUtil.convertStringToDate(value, pattern);
							psinsert.setTimestamp(k + 1, new Timestamp(date.getTime()));
						} else {
							psinsert.setString(k + 1, value);
						}
					}
				}
				psinsert.addBatch();
			}
			psinsert.executeBatch();
			conn.commit();
		} catch (Exception e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			logger.error("生态同步批量插入属性数据错误！表名：" + tableName + ", 组织类型fdId:" + props.get(0).getFdId(), e);
		} finally {
			JdbcUtils.closeStatement(psinsert);
			JdbcUtils.closeConnection(conn);
		}
	}

	private void generTable(String tableName, List<SysOrgElementExtProp> props) throws Exception {
		// 排序
		Collections.sort(props);
		// 生成表
		executeUpdate(getCreateTableSql(tableName, props), null);
	}

	private String getCreateTableSql(String tableName, List<SysOrgElementExtProp> props) {
		StringBuilder sql = new StringBuilder();
		// 子表字段总长度（数据库中字段总长度最大不能超过65535）
		int totalLength = 36;
		sql.append("CREATE TABLE ").append(tableName).append(" (").append("fd_id VARCHAR(36) NOT NULL, ");
		if (CollectionUtils.isNotEmpty(props)) {
			for (SysOrgElementExtProp prop : props) {
				String columnType = getColumnType(prop);
				if ("java.lang.String".equals(prop.getFdFieldType())) {
					totalLength += prop.getFdFieldLength().intValue();
				}
				sql.append(prop.getFdColumnName()).append(" ").append(columnType).append(", ");
			}
		}
		sql.append("PRIMARY KEY (fd_id))");
		// 如果字段总长度超过65535，是不能创建表的
		if (totalLength >= 65535) {
			throw new RuntimeException(
					ResourceUtil.getString("sysOrgMatrix.create.table.excessive.length", "sys-organization"));
		}
		return sql.toString();
	}

	private String getColumnType(SysOrgElementExtProp prop) {
		StringBuilder columnType = new StringBuilder();
		// 对应数据类型：字符串(java.lang.String)、整数(java.lang.Integer)、浮点(java.lang.Double)、日期(java.util.Date)
		String type = prop.getFdFieldType();
		if ("java.lang.String".equals(type)) {
			columnType.append(sysTmplService.getColumnType(TypeEnum.FD_STRING)).append("(")
					.append(prop.getFdFieldLength().intValue()).append(")");
		} else if ("java.lang.Integer".equals(type)) {
			columnType.append(HibernateUtil.getColumnType(Types.INTEGER));
		} else if ("java.lang.Double".equals(type)) {
			columnType.append(HibernateUtil.getColumnType(Types.DOUBLE));
		} else if ("java.util.Date".equals(type)) {
			columnType.append(sysTmplService.getColumnType(TypeEnum.FD_DATE));
		}
		return columnType.toString();
	}

	private void deleteTable(String tableName) throws Exception {
		executeUpdate("DROP TABLE " + tableName, null);
	}

	private void clearTable(String tableName) throws Exception {
		executeUpdate("DELETE FROM " + tableName, null);
	}

	private void executeUpdate(String sql, List<Object> params) throws Exception {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("执行SQL：" + sql);
			}
			TransactionUtils.doInNewTransaction(() -> {
				try {
					NativeQuery query = sysOrgElementExternalService.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
					// 启用二级缓存
					query.setCacheable(true);
					// 设置缓存模式
					query.setCacheMode(CacheMode.NORMAL);
					// 设置缓存区域
					query.setCacheRegion("sys-oms");
					if (params != null && !params.isEmpty()) {
						for (int i = 0; i < params.size(); i++) {
							Object param = params.get(i);
							query.setParameter(i, param);
						}
					}
					query.executeUpdate();
					if (logger.isInfoEnabled()) {
						logger.info("执行更新：" + sql);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
		} catch (Exception e) {
			logger.error("执行错误的sql:" + sql + ", 参数：" + JSON.toJSONString(params));
			throw e;
		}
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
				setCreator(sysOrgOrg, element, provider
						.getCreatorValueMapType(), provider
						.getCreatorValueMapTo(), element
						.getCreator());
				setAdmins(sysOrgOrg, element, provider
						.getAdminsValueMapType(), provider
						.getAdminsValueMapTo());
			}

			// 设置查看范围
			setRange(sysOrgOrg, element);
			//设置隐藏范围
			setHideRange(sysOrgOrg, element);
			orgOrgService.update(sysOrgOrg);
		}
		if (sysOrgElement instanceof SysOrgDept) {
			SysOrgElement sysOrgDept = (SysOrgElement) sysOrgElement;
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
				setCreator(sysOrgDept, element, provider
						.getCreatorValueMapType(), provider
						.getCreatorValueMapTo(), element
						.getCreator());
				setAdmins(sysOrgDept, element, provider
						.getAdminsValueMapType(), provider
						.getAdminsValueMapTo());
			}

			// 设置查看范围
			setRange(sysOrgDept, element);
			//设置隐藏范围
			setHideRange(sysOrgDept, element);
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
				setCreator(sysOrgPerson, element, provider
						.getCreatorValueMapType(), provider
						.getCreatorValueMapTo(), element
						.getCreator());
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

	/**
	 * 设置隐藏范围
	 * @description:
	  * @param sysOrgElement
	 * @param element
	 * @return: void
	 * @author: wangjf
	 * @time: 2021/9/29 7:52 下午
	 */
	private void setHideRange(SysOrgElement sysOrgElement, IOrgElement element) throws Exception {
		HideRange hideRange = element.getHideRange();
		SysOrgElementHideRange fdHiderange = sysOrgElement.getFdHideRange();
		if (fdHiderange == null) {
			fdHiderange = new SysOrgElementHideRange();
		}
		if (hideRange == null) {
			// 默认值
			fdHiderange.setFdIsOpenLimit(false);
			fdHiderange.setFdViewType(1);
		} else {
			fdHiderange.setFdIsOpenLimit(hideRange.getFdIsOpenLimit() == null ? false : hideRange.getFdIsOpenLimit());
			fdHiderange.setFdViewType(hideRange.getFdViewType() == null ? 1 : hideRange.getFdViewType());
			String[] other = hideRange.getFdOther();
			if (other != null && other.length > 0) {
				setHideRangeOther(fdHiderange, hideRange, provider
						.getRangeOtherValueMapType(), provider
						.getRangeOtherValueMapTo());
			}
		}
		fdHiderange.setFdElement(sysOrgElement);
		sysOrgElement.setFdHideRange(fdHiderange);
	}

	/**
	 * 设置隐藏可见组织
	 * @description:
	 * @param fdHideRange
	 * @param hideRange
	 * @param valueMpaType
	 * @param valueMapTo
	 * @return: void
	 * @author: wangjf
	 * @time: 2021/9/29 7:56 下午
	 */
	private void setHideRangeOther(SysOrgElementHideRange fdHideRange,
							   HideRange hideRange, ValueMapType[] valueMpaType,
							   ValueMapTo valueMapTo) throws Exception {

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

	private void setRange(SysOrgElement sysOrgElement, IOrgElement element) throws Exception {
		ViewRange viewRange = element.getViewRange();
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
				setRangeOther(range, viewRange, provider
						.getRangeOtherValueMapType(), provider
						.getRangeOtherValueMapTo());
			}
		}
		range.setFdElement(sysOrgElement);
		sysOrgElement.setFdRange(range);
	}

	private void setRangeOther(SysOrgElementRange range,
							   ViewRange viewRange, ValueMapType[] valueMpaType,
							   ValueMapTo valueMapTo) throws Exception {

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
					new String[]{value});
			if (perents != null && perents.size() > 0) {
				SysOrgElement parent = (SysOrgElement) perents.get(0);
				if (parent.getFdIsAvailable()) {
					sysOrgElement.setFdParent(parent);
				}
			}
		}
	}

	private List getForeignKey(ValueMapType[] searchType,
							   ValueMapTo searchName, String[] searchValue) throws Exception {
		String[] fdids;
		if (searchType.length == 1 && searchType[0] == ValueMapType.DEPT) {
			List ids = new ArrayList();
			// 1,number;2,keyword;3,dn;,4importInfo
			for (int i = 0; i < runner.getDeptcache().size(); i++) {
				String[] dept = runner.getDeptcache().get(i);
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
							+ " =:searchName and sysOrgElement.fdIsAvailable=1";
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
								+ ")  and sysOrgElement.fdIsAvailable=1";
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
					new String[]{value});
			if (thisLeaders != null && thisLeaders.size() > 0) {
				SysOrgElement thisLeader = (SysOrgElement) thisLeaders.get(0);
				if (thisLeader.getFdIsAvailable()) {
					sysOrgElement.setHbmThisLeader(thisLeader);
				}
			}
		}
	}

	private void setCreator(SysOrgElement sysOrgElement,
							IOrgElement element, ValueMapType[] valueMpaType,
							ValueMapTo valueMapTo, String value) throws Exception {
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("docCreator")) {
			if (StringUtil.isNull(element.getCreator())) {
				sysOrgElement.setDocCreator(null);
				return;
			}
			if (valueMapTo == null) {
				sysOrgElement.setDocCreator(null);
				return;
			}
			List creators = getForeignKey(valueMpaType, valueMapTo,
					new String[]{value});
			if (creators != null && creators.size() > 0) {
				SysOrgElement caretor = orgCoreService.format((SysOrgElement) creators.get(0));
				if (caretor.getFdIsAvailable() && caretor instanceof SysOrgPerson) {
					sysOrgElement.setDocCreator((SysOrgPerson) caretor);
				}
			}
		}
	}

	private void setAdmins(SysOrgElement sysOrgElement, IOrgElement element,
						   ValueMapType[] valueMpaType, ValueMapTo valueMapTo)
			throws Exception {
		if (element.getRequiredOms() == null
				|| element.getRequiredOms().contains("admins")) {
			String[] admins = element.getAuthElementAdmins();
			if (admins == null || admins.length == 0) {
				sysOrgElement.setAuthElementAdmins(null);
				return;
			}
			if (valueMapTo == null) {
				sysOrgElement.setFdPosts(null);
				return;
			}
			List list = getForeignKey(valueMpaType, valueMapTo, element
					.getAuthElementAdmins());
			sysOrgElement.setAuthElementAdmins(list);
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
					new String[]{value});
			if (superLeaders != null && superLeaders.size() > 0) {
				SysOrgElement superLeader = (SysOrgElement) superLeaders.get(0);
				if (superLeader.getFdIsAvailable()) {
					sysOrgElement.setHbmSuperLeader(superLeader);
				}
			}
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
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("isActivated")) {
			sysOrgPerson.setFdIsAvailable(person.getIsAvailable());
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("canLogin")) {
			sysOrgPerson.setFdCanLogin(person.getCanLogin());
		}
		if (person.getRequiredOms() == null
				|| person.getRequiredOms().contains("loginNameLower")) {
			sysOrgPerson.setFdLoginNameLower(person.getLoginNameLower());
		}
		setSysOrgElement(person, sysOrgPerson);
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
}
