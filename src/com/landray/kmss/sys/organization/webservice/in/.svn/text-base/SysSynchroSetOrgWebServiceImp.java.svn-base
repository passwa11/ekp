package com.landray.kmss.sys.organization.webservice.in;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.oms.OMSPlugin;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.*;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.organization.webservice.SysOrgWebserviceConstant;
import com.landray.kmss.sys.organization.webservice.org.SysSynchroElementBaseInfo;
import com.landray.kmss.sys.organization.webservice.org.SysSynchroOrgElement;
import com.landray.kmss.sys.organization.webservice.org.SysSynchroStaffingLevelInfo;
import com.landray.kmss.sys.organization.webservice.org.SysSyncroOrgConfig;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.annotation.RestApi;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.*;

@Controller
@RequestMapping(value = "/api/sys-organization/sysSynchroSetOrg", method = RequestMethod.POST)
@RestApi(docUrl = "/sys/organization/rest/sysOrg_in_rest_help.jsp", name = "sysSynchroSetOrgRestService", resourceKey = "sys-organization:sysSynchroSetOrg.title")
public class SysSynchroSetOrgWebServiceImp implements
		ISysSynchroSetOrgWebService, SysOrgWebserviceConstant, SysOrgConstant {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysSynchroSetOrgWebServiceImp.class);

	private final int batchCount = 1000;

	private ISysOrgElementService orgElementService = null;

	private ISysOrgOrgService orgOrgService = null;

	private ISysOrgDeptService orgDeptService = null;

	private ISysOrgGroupService orgGroupService = null;

	private ISysOrgPostService orgPostService = null;

	private ISysOrgPersonService orgPersonService = null;

	private IKmssPasswordEncoder passwordEncoder;

	private ISysOrgCoreService orgCoreService = null;

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService = null;

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
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

	/**
	 * 同步所有基本信息,删除没有组织的机构
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = "/syncOrgElementsBaseInfo", method = RequestMethod.POST)
	public SysSynchroSetResult syncOrgElementsBaseInfo(
			@RequestBody SysSynchroSetOrgContext setOrgContext)
			throws Exception {
		SysSynchroSetResult setResult = new SysSynchroSetResult();
		setResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		Map<String, Object> arguContext = new HashMap<String, Object>();
		if (!checkNullIfNecessary(setOrgContext, setResult, arguContext)) {
            return setResult;
        }
		Boolean deleteNoInOrgJsonData = setOrgContext
				.getDeleteNoInOrgJsonData();
		if (deleteNoInOrgJsonData) {
			updateDeletedFlag(arguContext);
		}
		insertOrUpdateBaseInfo((JSONArray) JSONValue.parse(setOrgContext
				.getOrgJsonData()), arguContext);
		if (deleteNoInOrgJsonData) {
			String[] deleteKeywords = getKeywordsForDelete(arguContext);
			TransactionStatus deleteStatus = TransactionUtils
					.beginNewTransaction();
			try {
				delete(deleteKeywords);
				TransactionUtils.getTransactionManager().commit(deleteStatus);
			} catch (Exception ex) {
				TransactionUtils.getTransactionManager().rollback(deleteStatus);
				throw ex;
			}
		}
		setResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		return setResult;
	}

	/**
	 * 同步需要更新的组织架构信息
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = "/syncOrgElements", method = RequestMethod.POST)
	public SysSynchroSetResult syncOrgElements(
			@RequestBody SysSynchroSetOrgContext setOrgContext)
			throws Exception {
		SysSynchroSetResult setResult = new SysSynchroSetResult();
		setResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		Map<String, Object> arguContext = new HashMap<String, Object>();
		if (!checkNullIfNecessary(setOrgContext, setResult, arguContext)) {
            return setResult;
        }
		executeUpdate((JSONArray) JSONValue.parse(setOrgContext
				.getOrgJsonData()), arguContext);
		setResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		return setResult;
	}

	/**
	 * 执行同步操作
	 */
	private void executeUpdate(JSONArray orgElements,
			Map<String, Object> arguContext) throws Exception {
		logger.debug("执行组织架构更新操作..");
		TransactionStatus updateDtatus = TransactionUtils.beginNewTransaction();
		int loop = 0;
		try {
			orgElementService.setNotToUpdateRelation(true);
			orgDeptService.setNotToUpdateRelation(true);
			orgOrgService.setNotToUpdateRelation(true);
			orgPersonService.setNotToUpdateRelation(true);
			orgPostService.setNotToUpdateRelation(true);
			orgGroupService.setNotToUpdateRelation(true);

			logger.debug("共" + orgElements.size() + "条组织架构数据,需要更新处理.");
			for (int i = 0; i < orgElements.size(); i++) {
				if (loop > 0 && (loop % batchCount == 0)) {
					TransactionUtils.getTransactionManager().commit(
							updateDtatus);
					orgElementService.getBaseDao().getHibernateSession()
							.clear();
					logger.debug("第" + loop / batchCount + "批次提交组织架构更新处理.");
					updateDtatus = TransactionUtils.beginNewTransaction();
				}
				SysSynchroOrgElement syncOrgElement = new SysSynchroOrgElement(
						(JSONObject) orgElements.get(i));
				if (StringUtil.isNull(syncOrgElement.getId())) {
					throw new Exception("id不能为空，对象信息：" + orgElements.get(i));
				}
				if (syncOrgElement.getOrgType() == null) {
					throw new Exception(
							"组织架构类型不能为空，对象信息：" + orgElements.get(i));
				}
				if (StringUtil.isNull(syncOrgElement.getName())) {
					throw new Exception("名称不能为空，对象信息：" + orgElements.get(i));
				}

				String updateId = syncOrgElement.getId();
				Integer orgType = syncOrgElement.getOrgType();
				SysOrgElement orgElment = null;
				if (orgType == null) {
					orgElment = orgCoreService
						.format(getSynchroOrgRecord(updateId, arguContext));
				} else {
					orgElment = orgCoreService
							.format(getSynchroOrgRecord(orgType, updateId,
									arguContext));
				}
				if (orgElment == null) {
                    continue;
                }
				if(ORG_TYPE_PERSON==orgElment.getFdOrgType()&&StringUtil.isNotNull(syncOrgElement.getLoginName())){
					SysOrgPerson orgPerson = orgCoreService.findByLoginName(syncOrgElement.getLoginName());
					//组织架构人员登录名要唯一
					if(null!=orgPerson&&!orgPerson.getFdId().equals(orgElment.getFdId())){
						throw new Exception("系统已存在相同的登陆名，对象信息：" + orgElements.get(i));
					}
 				}
				update(orgElment, syncOrgElement, arguContext);
			}
			TransactionUtils.getTransactionManager().commit(updateDtatus);
			// 统一更新层级字段fd_hierarchy_id
			logger.debug("统一更新组织架构层级ID.");
			updateDtatus = TransactionUtils.beginNewTransaction();
			orgElementService.updateRelation();
			TransactionUtils.getTransactionManager().commit(updateDtatus);
			logger.info("组织架构同步结束!");
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
		}
	}

	/**
	 * 根据临时表数据和现有数据得到需要删除的元素
	 * 
	 * @param arguContext
	 * @return
	 * @throws Exception
	 */
	private String[] getKeywordsForDelete(Map<String, Object> arguContext)
			throws Exception {
		String sql = " SELECT fd_import_info from sys_org_element where fd_import_info like '"
				+ arguContext.get("orgInappName")
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
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
		return (String[]) ids.toArray(new String[0]);
	}

	/**
	 * 需要删除的数据
	 * 
	 */
	private void delete(String[] keywords) throws Exception {
		if (keywords == null) {
            return;
        }
		logger.debug("删除组织机构记录数::" + keywords.length + "条记录");
		long time = System.currentTimeMillis();
		for (int i = 0; i < keywords.length; i++) {
			SysOrgElement sysOrgElement = orgCoreService
					.findByImportInfo(keywords[i]);
			if (sysOrgElement != null) {
				sysOrgElement.setFdIsAvailable(new Boolean(false));
				sysOrgElement.getHbmChildren().clear();
				sysOrgElement.setFdFlagDeleted(new Boolean(false));
				// 停用帐号与删除帐号时，都不要将fdImportInfo清空！不然停用后，再启用同一帐号会出现问题。
				orgElementService.update(sysOrgElement);
			}
		}
		logger.debug("删除组织机构耗时::" + (System.currentTimeMillis() - time) / 1000
				+ "s");
	}

	/**
	 * 校验,设置组织架构来源
	 * 
	 * @param orgContext
	 * @return
	 */
	private boolean checkNullIfNecessary(SysSynchroSetOrgContext orgContext,
			SysSynchroSetResult orgResult, Map<String, Object> arguContext) {
		if (orgContext == null) {
			orgResult.setReturnState(OPT_ORG_STATUS_FAIL);
			orgResult.setMessage("组织架构接入上下文为空!");
			return false;
		}
		if (StringUtil.isNull(orgContext.getOrgJsonData())) {
			orgResult.setReturnState(OPT_ORG_STATUS_FAIL);
			orgResult.setMessage("组织架构接入上下文数据为空,不进行同步处理!");
			return false;
		}
		arguContext.put("orgConfig", new SysSyncroOrgConfig(orgContext
				.getOrgSyncConfig()));
		if (StringUtil.isNotNull(orgContext.getAppName())) {
			arguContext.put("orgInappName", orgContext.getAppName());
		} else {
			arguContext.put("orgInappName", ISysSynchroSetOrgWebService.class
					.getName());
		}
		return true;
	}

	/**
	 * 同步组织架构前设置删除标识位
	 * 
	 * @throws Exception
	 */
	private void updateDeletedFlag(Map<String, Object> arguContext)
			throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement updateSql = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			updateSql = conn
					.prepareStatement("update sys_org_element set fd_flag_deleted = ? where fd_import_info like '"
							+ arguContext.get("orgInappName") + "%'");
			updateSql.setBoolean(1, true);
			updateSql.executeUpdate();
			conn.commit();
		} catch (Exception ex) {
			if(conn!=null) {
                conn.rollback();
            }
			throw ex;
		} finally {
			JdbcUtils.closeStatement(updateSql);
			JdbcUtils.closeConnection(conn);
		}
	}

	/**
	 * 插入新建数据以及关联关系更改数据
	 * 
	 * @param orgElments
	 * @param arguContext
	 * @throws Exception
	 */
	private void insertOrUpdateBaseInfo(JSONArray orgElments,
			Map<String, Object> arguContext) throws Exception {
		String orgInappName = (String) arguContext.get("orgInappName");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection connect = null;
		PreparedStatement orgSelectIdSql = null;
		PreparedStatement orgInsertSql = null;
		PreparedStatement cacheInsertSql = null;
		PreparedStatement orgUpdateSql = null;
		PreparedStatement personInsertSql = null;
		ResultSet resultSet = null;
		Set<String> outTypes = OMSPlugin.getOutTypes();
		try {
			connect = dataSource.getConnection();
			connect.setAutoCommit(false);
			long loop = 0;
			orgSelectIdSql = connect
					.prepareStatement("select fd_id from sys_org_element"
							+ " where (fd_import_info = ? and fd_org_type = ?) or fd_id = ?");
			// 增加名称拼音与简拼
			orgInsertSql = connect
					.prepareStatement("insert into sys_org_element( fd_id, fd_org_type, fd_name, fd_order, "
							+ "fd_no, fd_keyword, fd_is_available, fd_is_business, fd_import_info, fd_flag_deleted, fd_name_pinyin, fd_name_simple_pinyin, fd_create_time, fd_alter_time, fd_hierarchy_id, fd_is_external)"
							+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			cacheInsertSql = connect.prepareStatement(
					"insert into sys_oms_cache(fd_id,fd_org_element_id,fd_app_name,fd_op_type) values(?,?,?,?)");

			// 增加名称拼音与简拼
			orgUpdateSql = connect
					.prepareStatement("update sys_org_element set fd_name=?, fd_org_type=?, fd_order=? ,fd_no=? ,"
							+ "fd_keyword=? , fd_flag_deleted=?, fd_name_pinyin=?, fd_name_simple_pinyin=?, fd_alter_time=? where fd_id=?");

			personInsertSql = connect
					.prepareStatement("insert into sys_org_person(fd_id,fd_password,fd_init_password) values(?,?,?)");

			for (int i = 0; i < orgElments.size(); i++) {
				if (loop > 0 && (loop % batchCount == 0)) {
					orgInsertSql.executeBatch();
					cacheInsertSql.executeBatch();
					orgUpdateSql.executeBatch();
					personInsertSql.executeBatch();
					connect.commit();
					logger
							.debug("第" + loop / batchCount
									+ "批次提交新增或更新基本组织架构数据.");
				}
				SysSynchroElementBaseInfo orgElement = new SysSynchroElementBaseInfo(
						(JSONObject) orgElments.get(i));
				if (StringUtil.isNull(orgElement.getId())) {
					throw new Exception("id不能为空，对象信息：" + orgElments.get(i));
				}
				if (StringUtil.isNull(orgElement.getType())) {
					throw new Exception("组织架构类型不能为空，对象信息：" + orgElments.get(i));
				}
				if (StringUtil.isNull(orgElement.getName())) {
					throw new Exception("名称不能为空，对象信息：" + orgElments.get(i));
				}
				String inportInfo = orgInappName + orgElement.getId();
				int orgType = getOrgType(orgElement.getType());
				String fdId = orgElement.getLunid();
				orgSelectIdSql.setString(1, inportInfo);
				orgSelectIdSql.setInt(2, orgType);
				orgSelectIdSql.setString(3, fdId);
				resultSet = orgSelectIdSql.executeQuery();
				String orgName = orgElement.getName();
				SysOrgElement sysOrgElement = null;
				if (resultSet.next()) {
					if (ORG_TYPE_PERSON == orgType) {
						sysOrgElement = new SysOrgPerson();
					}else if(ORG_TYPE_DEPT == orgType){
						sysOrgElement = new SysOrgDept();
					}else if(ORG_TYPE_ORG == orgType){
						sysOrgElement = new SysOrgOrg();
					}else if(ORG_TYPE_POST == orgType){
						sysOrgElement = new SysOrgPost();
					}else if(ORG_TYPE_GROUP == orgType){
						sysOrgElement = new SysOrgGroup();
					}
					fdId = resultSet.getString(1);
					orgUpdateSql.setString(1, orgName);
					orgUpdateSql.setInt(2, orgType);
					if (orgElement.getOrder() == null) {
                        orgUpdateSql.setNull(3, Types.INTEGER);
                    } else {
                        orgUpdateSql.setInt(3, Integer.valueOf(orgElement
                                .getOrder()));
						sysOrgElement.setFdOrder(Integer.valueOf(orgElement.getOrder()));
                    }
					orgUpdateSql.setString(4, orgElement.getNo());
					orgUpdateSql.setString(5, orgElement.getKeyword());
					orgUpdateSql.setBoolean(6, false);
					// 增加名称拼音与简拼
					String fullPinyin = SysOrgUtil.getFullPinyin(orgName);
					String simplePinyin = SysOrgUtil.getSimplePinyin(orgName);
					orgUpdateSql.setString(7, fullPinyin);
					orgUpdateSql.setString(8, simplePinyin);
					orgUpdateSql.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
					orgUpdateSql.setString(10, fdId);
					orgUpdateSql.addBatch();
					//记录变更日志
					sysOrgElement.setFdId(fdId);
					sysOrgElement.setFdName(orgName);
					sysOrgElement.setFdOrgType(orgType);
					sysOrgElement.setFdNo(orgElement.getNo());
					sysOrgElement.setFdKeyword(orgElement.getKeyword());
					sysOrgElement.setFdFlagDeleted(Boolean.FALSE);
					sysOrgElement.setFdNamePinYin(fullPinyin);
					sysOrgElement.setFdNameSimplePinyin(simplePinyin);
					orgElementService.addOrgModifyLog(sysOrgElement,false);
				} else {
					SysOrgDefaultConfig sysOrgDefaultConfig = new SysOrgDefaultConfig();
					if (StringUtil.isNull(fdId)) {
						fdId = IDGenerator.generateID();
					}
					orgInsertSql.setString(1, fdId);
					orgInsertSql.setInt(2, getOrgType(orgElement.getType()));
					orgInsertSql.setString(3, orgName);
					if (orgElement.getOrder() == null){
						
						Integer order = null;
						
						if (ORG_TYPE_PERSON == orgType) {
							order = sysOrgDefaultConfig.getOrgPersonDefaultOrder();
							sysOrgElement = new SysOrgPerson();
						}else if(ORG_TYPE_DEPT == orgType){
							order = sysOrgDefaultConfig.getOrgDeptDefaultOrder();
							sysOrgElement = new SysOrgDept();
						}else if(ORG_TYPE_ORG == orgType){
							order = sysOrgDefaultConfig.getOrgOrgDefaultOrder();
							sysOrgElement = new SysOrgOrg();
						}else if(ORG_TYPE_POST == orgType){
							order = sysOrgDefaultConfig.getOrgPostDefaultOrder();
							sysOrgElement = new SysOrgPost();
						}else if(ORG_TYPE_GROUP == orgType){
							order = sysOrgDefaultConfig.getOrgGroupDefaultOrder();
							sysOrgElement = new SysOrgGroup();
						}
						if(order==null) {
                            orgInsertSql.setNull(4, Types.INTEGER);
                        } else {
                            orgInsertSql.setInt(4, order);
							sysOrgElement.setFdOrder(order);
                        }
					}else {
						if (ORG_TYPE_PERSON == orgType) {
							sysOrgElement = new SysOrgPerson();
						}else if(ORG_TYPE_DEPT == orgType){
							sysOrgElement = new SysOrgDept();
						}else if(ORG_TYPE_ORG == orgType){
							sysOrgElement = new SysOrgOrg();
						}else if(ORG_TYPE_POST == orgType){
							sysOrgElement = new SysOrgPost();
						}else if(ORG_TYPE_GROUP == orgType){
							sysOrgElement = new SysOrgGroup();
						}
                        orgInsertSql.setInt(4, Integer.valueOf(orgElement
                                .getOrder()));
						sysOrgElement.setFdOrder(Integer.valueOf(orgElement.getOrder()));
                    }
					orgInsertSql.setString(5, orgElement.getNo());
					orgInsertSql.setString(6, orgElement.getKeyword());
					orgInsertSql.setBoolean(7, true);
					orgInsertSql.setBoolean(8, true);
					orgInsertSql.setString(9, inportInfo);
					orgInsertSql.setBoolean(10, false);
					// 增加名称拼音与简拼
					String fullPinyin = SysOrgUtil.getFullPinyin(orgName);
					String simplePinyin = SysOrgUtil.getSimplePinyin(orgName);
					orgInsertSql.setString(11, fullPinyin);
					orgInsertSql.setString(12, simplePinyin);
					orgInsertSql.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
					orgInsertSql.setTimestamp(14, new Timestamp(System.currentTimeMillis()));
					orgInsertSql.setString(15,"x"+fdId+"x");
					orgInsertSql.setBoolean(16,false);
					orgInsertSql.addBatch();

					if (ORG_TYPE_PERSON == orgType) {
						personInsertSql.setString(1, fdId);
						String psw = sysOrgDefaultConfig.getOrgDefaultPassword();

						if(StringUtil.isNotNull(psw)){
							personInsertSql.setString(2, passwordEncoder.encodePassword(psw));
							personInsertSql.setString(3, PasswordUtil.desEncrypt(psw));
						}else{
							personInsertSql.setString(2, "");
							personInsertSql.setString(3, "");
						}						
						personInsertSql.addBatch();
					}
					//添加组织架构变更日志
					sysOrgElement.setFdId(fdId);
					sysOrgElement.setFdName(orgName);
					sysOrgElement.setFdOrgType(orgType);
					sysOrgElement.setFdOrder(orgElement.getOrder() == null ? null : Integer.valueOf(orgElement.getOrder()));
					sysOrgElement.setFdNo(orgElement.getNo());
					sysOrgElement.setFdKeyword(orgElement.getKeyword());
					sysOrgElement.setFdFlagDeleted(Boolean.FALSE);
					sysOrgElement.setFdIsAvailable(Boolean.TRUE);
					sysOrgElement.setFdIsBusiness(Boolean.TRUE);
					sysOrgElement.setFdImportInfo(inportInfo);
					sysOrgElement.setFdNamePinYin(fullPinyin);
					sysOrgElement.setFdNameSimplePinyin(simplePinyin);
					orgElementService.addOrgModifyLog(sysOrgElement,true);

					// huangwq 将新增的记录保存到OMSCACHE中
					for (String type : outTypes) {
						cacheInsertSql.setString(1, IDGenerator
								.generateID());
						cacheInsertSql.setString(2, fdId);
						cacheInsertSql.setString(3, type);
						cacheInsertSql.setInt(4, 1);
						cacheInsertSql.addBatch();
					}
				}
				loop++;
			}
			logger.debug("新增或更新基本组织架构数据" + loop + "条.");
			orgInsertSql.executeBatch();
			cacheInsertSql.executeBatch();
			orgUpdateSql.executeBatch();
			personInsertSql.executeBatch();
			connect.commit();
			logger.debug("批量处理完毕.");
		} catch (SQLException ex) {
			if(connect!=null) {
                connect.rollback();
            }
			throw ex;
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception ex) {
				}
			}
			JdbcUtils.closeStatement(orgSelectIdSql);
			JdbcUtils.closeStatement(orgInsertSql);
			JdbcUtils.closeStatement(cacheInsertSql);
			JdbcUtils.closeStatement(orgUpdateSql);
			JdbcUtils.closeStatement(personInsertSql);
			JdbcUtils.closeConnection(connect);
		}
	}

	/**
	 * 密码加密
	 * 
	 * @param passStr
	 * @return
	 */
	private String getPersonPass(String passStr) {
		if (StringUtil.isNull(passStr)) {
            return null;
        }
		return passwordEncoder
				.encodePassword(SecureUtil.BASE64Decoder(passStr));
	}

	/**
	 * 组织架构类型读取
	 * 
	 * @param orgWebType
	 * @return
	 */
	private int getOrgType(String orgWebType) {
		int orgType = 0;
		if (ORG_WEB_TYPE_ORG.equalsIgnoreCase(orgWebType)) {
			orgType = ORG_TYPE_ORG;
		} else if (ORG_WEB_TYPE_DEPT.equalsIgnoreCase(orgWebType)) {
			orgType = ORG_TYPE_DEPT;
		} else if (ORG_WEB_TYPE_GROUP.equalsIgnoreCase(orgWebType)) {
			orgType = ORG_TYPE_GROUP;
		} else if (ORG_WEB_TYPE_POST.equalsIgnoreCase(orgWebType)) {
			orgType = ORG_TYPE_POST;
		} else if (ORG_WEB_TYPE_PERSON.equalsIgnoreCase(orgWebType)) {
			orgType = ORG_TYPE_PERSON;
		}
		return orgType;
	}

	private SysOrgElement getSynchroOrgRecord(String keyword,
			Map<String, Object> arguContext) throws Exception {
		keyword = (String) arguContext.get("orgInappName") + keyword;
		SysOrgElement sysOrgElement = orgCoreService.findByImportInfo(keyword);
		if (sysOrgElement == null) {
            return null;
        }
		return sysOrgElement;
	}

	private SysOrgElement getSynchroOrgRecord(Integer orgType, String keyword,
			Map<String, Object> arguContext) throws Exception {
		keyword = (String) arguContext.get("orgInappName") + keyword;
		SysOrgElement sysOrgElement = orgCoreService
				.findByImportInfoAndOrgtype(keyword, orgType);
		if (sysOrgElement == null) {
            return null;
        }
		return sysOrgElement;
	}

	/**
	 * 更新组织架构
	 * 
	 * @param orgElment
	 * @param syncOrgElement
	 * @throws Exception
	 */
	private void update(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, Map<String, Object> arguContext)
			throws Exception {
		if (syncOrgElement.getIsAvailable() != true) {
			orgElment.setFdIsAvailable(new Boolean(false));
			orgElment.getHbmChildren().clear();
			orgElment.setFdFlagDeleted(new Boolean(false));
			orgElementService.update(orgElment);
			return;
		}
		SysSyncroOrgConfig orgConfig = (SysSyncroOrgConfig) arguContext
				.get("orgConfig");
		if (orgElment instanceof SysOrgOrg) {
			setBaseInfo(orgElment, syncOrgElement, orgConfig.getOrg(),
					arguContext);
			SysOrgOrg orgOrg = (SysOrgOrg) orgElment;
			if (orgConfig.getOrg() == null
					|| orgConfig.getOrg().contains(PARENT)) {
				if (StringUtil.isNotNull(syncOrgElement.getParent())) {
					List tmpList = getForeignObj(ArrayUtil
							.convertArrayToList(new String[] { syncOrgElement
									.getParent() }), (String) arguContext
							.get("orgInappName"));
					if (tmpList != null && !tmpList.isEmpty()) {
						SysOrgElement orgTemp = (SysOrgElement) tmpList.get(0);
						orgOrg.setFdParent(orgTemp);
					} else {
                        orgOrg.setFdParent(null);
                    }
				} else {
                    orgOrg.setFdParent(null);
                }
			}
			if (orgConfig.getOrg() == null
					|| orgConfig.getOrg().contains(THIS_LEADER)) {
				if (StringUtil.isNotNull(syncOrgElement.getThisLeader())) {
					List tmpList = getForeignObj(ArrayUtil
							.convertArrayToList(new String[] { syncOrgElement
									.getThisLeader() }), (String) arguContext
							.get("orgInappName"));
					if (tmpList != null && !tmpList.isEmpty()) {
						SysOrgElement orgTemp = (SysOrgElement) tmpList.get(0);
						orgOrg.setHbmThisLeader(orgTemp);
					} else {
                        orgOrg.setHbmThisLeader(null);
                    }
				} else {
                    orgOrg.setHbmThisLeader(null);
                }
			}
			if (orgConfig.getOrg() == null
					|| orgConfig.getOrg().contains(SUPER_LEADER)) {
				if (StringUtil.isNotNull(syncOrgElement.getSuperLeader())) {
					List tmpList = getForeignObj(ArrayUtil
							.convertArrayToList(new String[] { syncOrgElement
									.getSuperLeader() }), (String) arguContext
							.get("orgInappName"));
					if (tmpList != null && !tmpList.isEmpty()) {
						SysOrgElement orgTemp = (SysOrgElement) tmpList.get(0);
						orgOrg.setHbmSuperLeader(orgTemp);
					} else {
                        orgOrg.setHbmSuperLeader(null);
                    }
				} else {
                    orgOrg.setHbmSuperLeader(null);
                }
			}
			if (syncOrgElement.contains("customProps")) {
				JSONObject props = syncOrgElement.getCustomProps();
				Map<String, Object> map = new HashMap<String, Object>();
				for (Object key : props.keySet()) {
					map.put(key.toString(), props.get(key).toString());
				}
				Map propsMap = DynamicAttributeUtil
						.convertCustomProp(SysOrgOrg.class.getName(), map);
				orgOrg.getCustomPropMap().putAll(propsMap);
			}					
			orgOrgService.update(orgOrg);
		}
		if (orgElment instanceof SysOrgDept) {
			setBaseInfo(orgElment, syncOrgElement, orgConfig.getDept(),
					arguContext);
			SysOrgDept orgDept = (SysOrgDept) orgElment;
			if (orgConfig.getDept() == null
					|| orgConfig.getDept().contains(PARENT)) {
				if (StringUtil.isNotNull(syncOrgElement.getParent())) {
					List tmpList = getForeignObj(ArrayUtil
							.convertArrayToList(new String[] { syncOrgElement
									.getParent() }), (String) arguContext
							.get("orgInappName"));
					if (tmpList != null && !tmpList.isEmpty()) {
						SysOrgElement orgTemp = (SysOrgElement) tmpList.get(0);
						orgDept.setFdParent(orgTemp);
					} else {
                        orgDept.setFdParent(null);
                    }
				} else {
                    orgDept.setFdParent(null);
                }
			}
			if (orgConfig.getDept() == null
					|| orgConfig.getDept().contains(THIS_LEADER)) {
				if (StringUtil.isNotNull(syncOrgElement.getThisLeader())) {
					List tmpList = getForeignObj(ArrayUtil
							.convertArrayToList(new String[] { syncOrgElement
									.getThisLeader() }), (String) arguContext
							.get("orgInappName"));
					if (tmpList != null && !tmpList.isEmpty()) {
						SysOrgElement orgTemp = (SysOrgElement) tmpList.get(0);
						orgDept.setHbmThisLeader(orgTemp);
					} else {
                        orgDept.setHbmThisLeader(null);
                    }
				} else {
                    orgDept.setHbmThisLeader(null);
                }
			}
			if (orgConfig.getDept() == null
					|| orgConfig.getDept().contains(SUPER_LEADER)) {
				if (StringUtil.isNotNull(syncOrgElement.getSuperLeader())) {
					List tmpList = getForeignObj(ArrayUtil
							.convertArrayToList(new String[] { syncOrgElement
									.getSuperLeader() }), (String) arguContext
							.get("orgInappName"));
					if (tmpList != null && !tmpList.isEmpty()) {
						SysOrgElement orgTemp = (SysOrgElement) tmpList.get(0);
						orgDept.setHbmSuperLeader(orgTemp);
					} else {
                        orgDept.setHbmSuperLeader(null);
                    }
				} else {
                    orgDept.setHbmSuperLeader(null);
                }
			}
			
			if (syncOrgElement.contains("customProps")) {
				JSONObject props = syncOrgElement.getCustomProps();
				Map<String, Object> map = new HashMap<String, Object>();
				for (Object key : props.keySet()) {
					map.put(key.toString(), props.get(key).toString());
				}
				Map propsMap = DynamicAttributeUtil
						.convertCustomProp(SysOrgDept.class.getName(), map);
				orgDept.getCustomPropMap().putAll(propsMap);
			}				
			orgDeptService.update(orgDept);
		}
		if (orgElment instanceof SysOrgGroup) {
			setBaseInfo(orgElment, syncOrgElement, orgConfig.getGroup(),
					arguContext);
			SysOrgGroup orgGroup = (SysOrgGroup) orgElment;
			if (orgConfig.getGroup() == null
					|| orgConfig.getGroup().contains(MEMBERS)) {
				if (syncOrgElement.getMembers() != null) {
					List tmpList = getForeignObj(syncOrgElement.getMembers(),
							(String) arguContext.get("orgInappName"));
					if (tmpList != null && !tmpList.isEmpty()) {
						orgGroup.setFdMembers(tmpList);
					} else {
                        orgGroup.setFdMembers(null);
                    }
				} else {
                    orgGroup.setFdMembers(null);
                }
			}
			orgGroupService.update(orgGroup);
		}
		if (orgElment instanceof SysOrgPost) {
			setBaseInfo(orgElment, syncOrgElement, orgConfig.getPost(),
					arguContext);
			SysOrgPost orgPost = (SysOrgPost) orgElment;
			if (orgConfig.getPost() == null
					|| orgConfig.getPost().contains(PARENT)) {
				if (StringUtil.isNotNull(syncOrgElement.getParent())) {
					List tmpList = getForeignObj(ArrayUtil
							.convertArrayToList(new String[] { syncOrgElement
									.getParent() }), (String) arguContext
							.get("orgInappName"));
					if (tmpList != null && !tmpList.isEmpty()) {
						SysOrgElement orgTemp = (SysOrgElement) tmpList.get(0);
						orgPost.setFdParent(orgTemp);
					} else {
                        orgPost.setFdParent(null);
                    }
				} else {
                    orgPost.setFdParent(null);
                }
			}
			if (orgConfig.getPost() == null
					|| orgConfig.getPost().contains(THIS_LEADER)) {
				if (StringUtil.isNotNull(syncOrgElement.getThisLeader())) {
					List tmpList = getForeignObj(ArrayUtil
							.convertArrayToList(new String[] { syncOrgElement
									.getThisLeader() }), (String) arguContext
							.get("orgInappName"));
					if (tmpList != null && !tmpList.isEmpty()) {
						SysOrgElement orgTemp = (SysOrgElement) tmpList.get(0);
						orgPost.setHbmThisLeader(orgTemp);
					} else {
                        orgPost.setHbmThisLeader(null);
                    }
				} else {
                    orgPost.setHbmThisLeader(null);
                }
			}
			if (orgConfig.getPost() == null
					|| orgConfig.getPost().contains(PERSONS)) {
				if (syncOrgElement.getPersons() != null) {
					List tmpList = getForeignObj(syncOrgElement.getPersons(),
							(String) arguContext.get("orgInappName"));
					if (tmpList != null && !tmpList.isEmpty()) {
						orgPost.setFdPersons(tmpList);
					} else {
                        orgPost.setFdPersons(null);
                    }
				} else {
                    orgPost.setFdPersons(null);
                }
			}
			orgPostService.update(orgPost);
		}
		if (orgElment instanceof SysOrgPerson) {
			SysOrgPerson orgPerson = (SysOrgPerson) orgElment;
			setOrgPerson(orgPerson, syncOrgElement, orgConfig.getPerson(),
					arguContext);
			if (orgConfig.getPerson() == null
					|| orgConfig.getPerson().contains(PARENT)) {
				if (StringUtil.isNotNull(syncOrgElement.getParent())) {
					List tmpList = getForeignObj(ArrayUtil
							.convertArrayToList(new String[] { syncOrgElement
									.getParent() }), (String) arguContext
							.get("orgInappName"));
					if (tmpList != null && !tmpList.isEmpty()) {
						SysOrgElement orgTemp = (SysOrgElement) tmpList.get(0);
						orgPerson.setFdParent(orgTemp);
					} else {
                        orgPerson.setFdParent(null);
                    }
				} else {
                    orgPerson.setFdParent(null);
                }
			}
			if (orgConfig.getPerson() == null
					|| orgConfig.getPerson().contains(POSTS)) {
				if (syncOrgElement.getPosts() != null) {
					List tmpList = getForeignObj(syncOrgElement.getPosts(),
							(String) arguContext.get("orgInappName"));
					if (tmpList != null && !tmpList.isEmpty()) {
						orgPerson.setFdPosts(tmpList);
					} else {
                        orgPerson.setFdPosts(null);
                    }
				} else {
                    orgPerson.setFdPosts(null);
                }
			}
			if (syncOrgElement.contains("customProps")) {
				JSONObject props = syncOrgElement.getCustomProps();
				Map<String, Object> map = new HashMap<String, Object>();
				for (Object key : props.keySet()) {
					map.put(key.toString(), props.get(key).toString());
				}
				Map propsMap = DynamicAttributeUtil
						.convertCustomProp(SysOrgPerson.class.getName(), map);
				orgPerson.getCustomPropMap().putAll(propsMap);
			}
			orgPersonService.update(orgPerson);
		}
	}

	private void setBaseInfo(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, List rules,
			Map<String, Object> arguContext) {
		orgElment.setFdName(syncOrgElement.getName());
		if (rules == null || rules.contains(NO)) {
			orgElment.setFdNo(syncOrgElement.getNo());
		}
		if (rules == null || rules.contains(ORDER)) {
			if (StringUtil.isNotNull(syncOrgElement.getOrder())) {
                orgElment
                        .setFdOrder(Integer.valueOf(syncOrgElement.getOrder()));
            } else {
                orgElment.setFdOrder(null);
            }
		}
		if (rules == null || rules.contains(KEYWORD)) {
			orgElment.setFdKeyword(syncOrgElement.getKeyword());
		}
		if (rules == null || rules.contains(MEMO)) {
			orgElment.setFdMemo(syncOrgElement.getMemo());
		}
		orgElment.setFdIsAvailable(syncOrgElement.getIsAvailable());
		orgElment.setFdAlterTime(new Date(System.currentTimeMillis()));
		orgElment.setFdImportInfo(arguContext.get("orgInappName")
				+ syncOrgElement.getId());
		if (syncOrgElement.contains("langProps")) {
			JSONObject props = syncOrgElement.getLangProps();
			Map<String, String> map = new HashMap<String, String>();
			for (Object key : props.keySet()) {
				map.put(key.toString(), props.get(key).toString());
			}
			orgElment.getDynamicMap().putAll(map);
		}
	}

	private void setOrgPerson(SysOrgPerson orgPerson,
			SysSynchroOrgElement syncOrgElement, List rules,
			Map<String, Object> arguContext) {
		if (rules == null || rules.contains(LOGIN_NAME)) {
			orgPerson.setFdLoginName(syncOrgElement.getLoginName());
		}
		if (rules == null || rules.contains(NICK_NAME)) {
			orgPerson.setFdNickName(syncOrgElement.getNickName());
		}
		if (rules == null || rules.contains(PASSWORD)) {
			orgPerson
					.setFdPassword(getPersonPass(syncOrgElement.getPassword()));
			orgPerson.setFdInitPassword(
					PasswordUtil.desEncrypt(SecureUtil
							.BASE64Decoder(syncOrgElement.getPassword())));
		}
		if (rules == null || rules.contains(EMAIL)) {
			orgPerson.setFdEmail(syncOrgElement.getEmail());
		}
		if (rules == null || rules.contains(MOBILE_NO)) {
			orgPerson.setFdMobileNo(syncOrgElement.getMobileNo());
		}
		if (rules == null || rules.contains(MOBILE_NO)) {
			orgPerson.setFdAttendanceCardNumber(syncOrgElement
					.getAttendanceCardNumber());
		}
		if (rules == null || rules.contains(WORK_PHONE)) {
			orgPerson.setFdWorkPhone(syncOrgElement.getWorkPhone());
		}
		if (rules == null || rules.contains(RTX_NO)) {
			orgPerson.setFdRtxNo(syncOrgElement.getRtx());
		}
		if (rules == null || rules.contains(WECHAT_NO)) {
			orgPerson.setFdWechatNo(syncOrgElement.getWechat());
		}
		if (rules == null || rules.contains(SEX)) {
			orgPerson.setFdSex(syncOrgElement.getSex());
		}
		if (rules == null || rules.contains(SHORT_NO)) {
			orgPerson.setFdShortNo(syncOrgElement.getShortNo());
		}
		setBaseInfo(orgPerson, syncOrgElement, rules, arguContext);
	}

	private List getForeignObj(List searchValue, String orgInappName)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = " ";
		String temp = "";
		String[] fdids = null;
		if (searchValue != null && !ArrayUtil.isEmpty(searchValue)) {
			if (searchValue.size() == 1) {
				whereBlock = "sysOrgElement.fdImportInfo=:searchName or sysOrgElement.fdId=:fdId";
				hqlInfo.setParameter("searchName", orgInappName
						+ searchValue.get(0));
				hqlInfo.setParameter("fdId", searchValue.get(0));
			} else if (searchValue.size() < 1000) {
				temp = "";
				String id_temp = "";
				for (int i = 0; i < searchValue.size(); i++) {
					if (StringUtil.isNotNull((String) searchValue.get(i))) {
						temp += ",'" + orgInappName + searchValue.get(i) + "'";
						id_temp += ",'" + searchValue.get(i) + "'";
					}
				}
				if (temp.length() > 0) {
					temp = temp.substring(1);
					id_temp = id_temp.substring(1);
					whereBlock = " sysOrgElement.fdImportInfo in ( " + temp
							+ ") or sysOrgElement.fdId in ( " + id_temp
							+ ")";
				}
			} else {
				temp = "";
				String id_temp = "";
				List<String> tempList = new ArrayList<String>();
				List<String> id_tempList = new ArrayList<String>();
				for (int i = 0; i < searchValue.size(); i++) {
					if (StringUtil.isNotNull((String) searchValue.get(i))) {
						temp += ",'" + orgInappName + searchValue.get(i) + "'";
						id_temp += ",'" + searchValue.get(i) + "'";
					}
					if (i > 0 && i % 500 == 0 && StringUtil.isNotNull(temp)) {
						tempList.add(temp.substring(1));
						id_tempList.add(id_temp.substring(1));
						temp = "";
						id_temp = "";
					}
				}
				if (!tempList.isEmpty()) {
					List all = new ArrayList();
					for (int i = 0; i < tempList.size(); i++) {
						whereBlock = "sysOrgElement.fdImportInfo in ( "
								+ tempList.get(i)
								+ ") or sysOrgElement.fdId in ( "
								+ id_tempList.get(i)
								+ ")";
						hqlInfo.setSelectBlock("sysOrgElement.fdId");
						hqlInfo.setWhereBlock(whereBlock);
						String[] fdids_ = (String[]) orgElementService
								.getBaseDao()
								.findValue(hqlInfo).toArray(new String[0]);
						all.addAll(orgElementService.findByPrimaryKeys(fdids_));
						return all;
					}
				} else {
					return new ArrayList();
				}
			}
			hqlInfo.setSelectBlock("sysOrgElement.fdId");
			hqlInfo.setWhereBlock(whereBlock);
			fdids = (String[]) orgElementService.getBaseDao()
					.findValue(hqlInfo).toArray(new String[0]);
			return orgElementService.findByPrimaryKeys(fdids);
		} else {
            return null;
        }
	}

	private String[] getForeignObjIds(List searchValue, String orgInappName)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = " ";
		String temp = "";
		String[] fdids = null;
		if (searchValue != null && !ArrayUtil.isEmpty(searchValue)) {
			if (searchValue.size() == 1) {
				whereBlock = "(sysOrgElement.fdImportInfo=:searchName or sysOrgElement.fdId=:fdId)";
				hqlInfo.setParameter("searchName", orgInappName
						+ searchValue.get(0));
				hqlInfo.setParameter("fdId", searchValue.get(0));
			} else {
				temp = "";
				String id_temp = "";
				for (int i = 0; i < searchValue.size(); i++) {
					if (StringUtil.isNotNull((String) searchValue.get(i))) {
						temp += ",'" + orgInappName + searchValue.get(i) + "'";
						id_temp += ",'" + searchValue.get(i) + "'";
					}
				}
				if (temp.length() > 0) {
					temp = temp.substring(1);
					id_temp = id_temp.substring(1);
					whereBlock = "( sysOrgElement.fdImportInfo in ( " + temp
							+ ") or sysOrgElement.fdId in ( " + id_temp
							+ "))";
				}
			}
			hqlInfo.setSelectBlock("sysOrgElement.fdId");
			hqlInfo.setWhereBlock(whereBlock);
			fdids = (String[]) orgElementService.getBaseDao()
					.findValue(hqlInfo).toArray(new String[0]);
			return fdids;
		} else {
            return null;
        }
	}

	private String addStaffingLevel(
			SysSynchroStaffingLevelInfo syncOrgStaffingLevel,
			String orgInappName) throws Exception {
		SysOrganizationStaffingLevel sysOrgStaffingLevel = new SysOrganizationStaffingLevel();
		if (StringUtil.isNotNull(syncOrgStaffingLevel.getId())) {
			sysOrgStaffingLevel.setFdId(syncOrgStaffingLevel.getId());
		}
		setStaffingLevelValue(sysOrgStaffingLevel, syncOrgStaffingLevel,
				orgInappName);
		return sysOrganizationStaffingLevelService.add(sysOrgStaffingLevel);

	}

	private String updateStaffingLevel(
			SysSynchroStaffingLevelInfo syncOrgStaffingLevel,
			String orgInappName) throws Exception {
		String id = syncOrgStaffingLevel.getId();
		if (StringUtil.isNull(id)) {
			return addStaffingLevel(syncOrgStaffingLevel, orgInappName);
		}
		SysOrganizationStaffingLevel sysOrgStaffingLevel = (SysOrganizationStaffingLevel) sysOrganizationStaffingLevelService
				.findByPrimaryKey(id, null, true);
		if (sysOrgStaffingLevel == null) {
			return addStaffingLevel(syncOrgStaffingLevel, orgInappName);
		} else {
			sysOrgStaffingLevel.getFdPersons();
		}
		setStaffingLevelValue(sysOrgStaffingLevel, syncOrgStaffingLevel,
				orgInappName);
		sysOrganizationStaffingLevelService.update(sysOrgStaffingLevel);
		return id;
	}

	private void setStaffingLevelValue(
			SysOrganizationStaffingLevel sysOrgStaffingLevel,
			SysSynchroStaffingLevelInfo syncOrgStaffingLevel,
			String orgInappName) throws Exception {
		sysOrgStaffingLevel.setFdName(syncOrgStaffingLevel.getName());
		sysOrgStaffingLevel.setFdLevel(syncOrgStaffingLevel.getLevel());
		if (syncOrgStaffingLevel.getDescription() != null) {
			sysOrgStaffingLevel.setFdDescription(syncOrgStaffingLevel
					.getDescription());
		}
		sysOrgStaffingLevel.setFdIsDefault(false);

		if (syncOrgStaffingLevel.getPersons() != null) {
			if (syncOrgStaffingLevel.getPersons().size() > 0) {
				String[] ids = getForeignObjIds(syncOrgStaffingLevel
						.getPersons(), orgInappName);
				if (ids != null && ids.length > 0) {
					sysOrgStaffingLevel.setFdPersons(orgPersonService
							.findByPrimaryKeys(ids));
				} else {
                    sysOrgStaffingLevel.setFdPersons(null);
                }
			} else if (syncOrgStaffingLevel.getPersons().size() == 0) {
				sysOrgStaffingLevel.setFdPersons(null);
			}
		}
	}

	private List<String> executeUpdateStaffingLevel(
			JSONArray orgStaffingLevels, String orgInappName) throws Exception {
		logger.debug("执行职务更新操作..");
		TransactionStatus updateDtatus = TransactionUtils.beginNewTransaction();
		int loop = 0;
		try {
			logger.debug("共" + orgStaffingLevels.size() + "条职务数据,需要更新处理.");
			List<String> ids = new ArrayList<String>();
			//SysSynchroStaffingLevelInfo syncOrgStaffingLevel_defaule = null;
			String id_default = null;
			for (int i = 0; i < orgStaffingLevels.size(); i++) {
				if (loop > 0 && (loop % batchCount == 0)) {
					TransactionUtils.getTransactionManager().commit(
							updateDtatus);
					sysOrganizationStaffingLevelService.getBaseDao()
							.getHibernateSession().clear();
					logger.debug("第" + loop / batchCount + "批次提交职务更新处理.");
					updateDtatus = TransactionUtils.beginNewTransaction();
				}
				SysSynchroStaffingLevelInfo syncOrgStaffingLevel = new SysSynchroStaffingLevelInfo(
						(JSONObject) orgStaffingLevels.get(i));
				
				String id = updateStaffingLevel(syncOrgStaffingLevel,
						orgInappName);
				if(syncOrgStaffingLevel.getIsDefault()){
					id_default = id;
				}
				ids.add(id);
			}
			TransactionUtils.getTransactionManager().commit(updateDtatus);
			
			
			SysOrganizationStaffingLevel sysOrgStaffingLevel = (SysOrganizationStaffingLevel) sysOrganizationStaffingLevelService
			.findByPrimaryKey(id_default, null, true);
			if (sysOrgStaffingLevel != null) {
				sysOrgStaffingLevel.setFdIsDefault(true);
				sysOrganizationStaffingLevelService.update(sysOrgStaffingLevel);
			} 
	
	
	
			logger.info("职务同步结束!");
			return ids;
		} catch (Exception ex) {
			TransactionUtils.getTransactionManager().rollback(updateDtatus);
			logger.error("", ex);
			throw ex;
		}
	}

	private List<String> executeDelStaffingLevel(JSONArray orgStaffingLevels) {
		logger.debug("执行职务删除操作..");

		logger.debug("共" + orgStaffingLevels.size() + "条职务数据,需要删除处理.");
		List<String> results = new ArrayList<String>();
		for (int i = 0; i < orgStaffingLevels.size(); i++) {

			SysSynchroStaffingLevelInfo syncOrgStaffingLevel = new SysSynchroStaffingLevelInfo(
					(JSONObject) orgStaffingLevels.get(i));
			try {
				sysOrganizationStaffingLevelService.delete(syncOrgStaffingLevel
						.getId());
				results.add("success");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.toString());
				results.add(e.getMessage());
			}
		}
		logger.info("职务删除结束!");
		return results;

	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/updateOrgStaffingLevels", method = RequestMethod.POST)
	public SysSynchroSetResult updateOrgStaffingLevels(
			@RequestBody SysSynchroSetOrgContext setOrgContext)
			throws Exception {
		SysSynchroSetResult setResult = new SysSynchroSetResult();
		setResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		String orgInappName = null;
		if (StringUtil.isNotNull(setOrgContext.getAppName())) {
			orgInappName = setOrgContext.getAppName();
		} else {
			orgInappName = ISysSynchroSetOrgWebService.class.getName();
		}
		List<String> ids = executeUpdateStaffingLevel((JSONArray) JSONValue
				.parse(setOrgContext.getOrgJsonData()), orgInappName);
		String message = "";
		for (String id : ids) {
			message += ",\"" + id + "\"";
		}
		if (message.length() > 0) {
			message = message.substring(1);
		}
		message = "[" + message + "]";
		setResult.setMessage(message);
		setResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		return setResult;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/delOrgStaffingLevels", method = RequestMethod.POST)
	public SysSynchroSetResult delOrgStaffingLevels(
			@RequestBody SysSynchroSetOrgContext setOrgContext)
			throws Exception {
		SysSynchroSetResult setResult = new SysSynchroSetResult();
		setResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		List<String> results = executeDelStaffingLevel((JSONArray) JSONValue
				.parse(setOrgContext.getOrgJsonData()));
		String message = "";
		for (String result : results) {
			message += ",\"" + result + "\"";
		}
		if (message.length() > 0) {
			message = message.substring(1);
		}
		message = "[" + message + "]";
		setResult.setMessage(message);
		setResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		return setResult;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/updateOrgElement", method = RequestMethod.POST)
	public SysSynchroSetResult updateOrgElement(
			@RequestBody SysSynchroSetOrgContext setOrgContext)
			throws Exception {
		SysSynchroSetResult setResult = new SysSynchroSetResult();
		setResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		Map<String, Object> arguContext = new HashMap<String, Object>();
		if (!checkNullIfNecessary(setOrgContext, setResult, arguContext)) {
            return setResult;
        }
		JSONObject object = (JSONObject) JSONValue.parse(setOrgContext
				.getOrgJsonData());
		if (!object.containsKey(ID)) {
			setResult.setReturnState(OPT_ORG_STATUS_FAIL);
			setResult.setMessage("id属性不能为空!");
			return setResult;
		}
		if (!object.containsKey(TYPE)) {
			setResult.setReturnState(OPT_ORG_STATUS_FAIL);
			setResult.setMessage("type属性不能为空!");
			return setResult;
		}
		executeAddOrUpdate(object, arguContext);
		setResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		return setResult;
	}

	private void executeAddOrUpdate(JSONObject orgElement,
			Map<String, Object> arguContext) throws Exception {
		logger.debug("执行组织架构更新操作..");
		try {
			SysSynchroOrgElement syncOrgElement = new SysSynchroOrgElement(
					orgElement);
			String updateId = syncOrgElement.getId();
			Integer org_type = syncOrgElement.getOrgType();
			SysOrgElement orgElment = null;
			if (org_type == null) {
				orgElment = orgCoreService
					.format(getSynchroOrgRecord(updateId, arguContext));
			} else {
				orgElment = orgCoreService
						.format(getSynchroOrgRecord(org_type, updateId,
								arguContext));
			}
			
			if (orgElment == null) {
				int orgType = 0;
				Object type = orgElement.get(TYPE);
				if (type != null) {
					orgType = getOrgType((String) type);
				}
				if (orgType == 0) {
					throw new Exception("组织架构类型设置不正确：" + orgType);
				}
				switch (orgType) {
				case ORG_TYPE_ORG:
					orgElment = new SysOrgOrg();
					break;
				case ORG_TYPE_DEPT:
					orgElment = new SysOrgDept();
					break;
				case ORG_TYPE_PERSON:
					orgElment = new SysOrgPerson();
					break;
				case ORG_TYPE_GROUP:
					orgElment = new SysOrgGroup();
					break;
				case ORG_TYPE_POST:
					orgElment = new SysOrgPost();
					break;

				}
				if(ORG_TYPE_PERSON==orgType&&StringUtil.isNotNull(syncOrgElement.getLoginName())){
					SysOrgPerson orgPerson = orgCoreService.findByLoginName(syncOrgElement.getLoginName());
					if(null!=orgPerson){
						throw new Exception("组织架构人员登陆名重复：" + syncOrgElement.getLoginName());
					}
				}
				add(orgElment, syncOrgElement,
						(String) arguContext.get("orgInappName"));
			} else {
				if(ORG_TYPE_PERSON==orgElment.getFdOrgType()&&StringUtil.isNotNull(syncOrgElement.getLoginName())){
					SysOrgPerson orgPerson = orgCoreService.findByLoginName(syncOrgElement.getLoginName());
					if(null!=orgPerson&&!orgPerson.getFdId().equals(orgElment.getFdId())){
						throw new Exception("组织架构人员登陆名重复：" + syncOrgElement.getLoginName());
					}
				}
				update(orgElment, syncOrgElement,
						(String) arguContext.get("orgInappName"));
			}
			// 统一更新层级字段fd_hierarchy_id
			logger.info("组织架构同步结束!");
		} catch (Exception ex) {
			logger.error("", ex);
			throw ex;
		}
	}

	private void updateParent(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, String appName)
			throws Exception {
		if (syncOrgElement.contains(PARENT)) {
			if (StringUtil.isNotNull(syncOrgElement.getParent())) {
				List tmpList = getForeignObj(ArrayUtil
						.convertArrayToList(new String[] { syncOrgElement
								.getParent() }),
						appName);
				if (tmpList != null && !tmpList.isEmpty()) {
					SysOrgElement orgTemp = (SysOrgElement) tmpList.get(0);
					orgElment.setFdParent(orgTemp);
				} else {
                    orgElment.setFdParent(null);
                }
			} else {
                orgElment.setFdParent(null);
            }
		}
	}

	private void updateThisLeader(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, String appName)
			throws Exception {
		if (syncOrgElement.contains(THIS_LEADER)) {
			if (StringUtil.isNotNull(syncOrgElement.getThisLeader())) {
				List tmpList = getForeignObj(ArrayUtil
						.convertArrayToList(new String[] { syncOrgElement
								.getThisLeader() }),
						appName);
				if (tmpList != null && !tmpList.isEmpty()) {
					SysOrgElement orgTemp = (SysOrgElement) tmpList.get(0);
					orgElment.setHbmThisLeader(orgTemp);
				} else {
                    orgElment.setHbmThisLeader(null);
                }
			} else {
                orgElment.setHbmThisLeader(null);
            }
		}
	}

	private void updateSuperLeader(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, String appName)
			throws Exception {
		if (syncOrgElement.contains(SUPER_LEADER)) {
			if (StringUtil.isNotNull(syncOrgElement.getSuperLeader())) {
				List tmpList = getForeignObj(ArrayUtil
						.convertArrayToList(new String[] { syncOrgElement
								.getSuperLeader() }),
						appName);
				if (tmpList != null && !tmpList.isEmpty()) {
					SysOrgElement orgTemp = (SysOrgElement) tmpList.get(0);
					orgElment.setHbmSuperLeader(orgTemp);
				} else {
                    orgElment.setHbmSuperLeader(null);
                }
			} else {
                orgElment.setHbmSuperLeader(null);
            }
		}
	}

	private SysOrgOrg setOrgInfo(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, String appName)
			throws Exception {
		setBaseInfo(orgElment, syncOrgElement, appName);
		SysOrgOrg orgOrg = (SysOrgOrg) orgElment;
		updateParent(orgElment, syncOrgElement, appName);
		updateThisLeader(orgElment, syncOrgElement, appName);
		updateSuperLeader(orgElment, syncOrgElement, appName);
		if (syncOrgElement.contains("customProps")) {
			JSONObject props = syncOrgElement.getCustomProps();
			Map<String, Object> map = new HashMap<String, Object>();
			for (Object key : props.keySet()) {
				map.put(key.toString(), props.get(key).toString());
			}
			Map propsMap = DynamicAttributeUtil
					.convertCustomProp(SysOrgOrg.class.getName(), map);
			orgOrg.getCustomPropMap().putAll(propsMap);
		}			
		orgOrgService.update(orgOrg);		
		return orgOrg;
	}

	private SysOrgDept setDeptInfo(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, String appName)
			throws Exception {
		setBaseInfo(orgElment, syncOrgElement, appName);
		SysOrgDept orgDept = (SysOrgDept) orgElment;
		updateParent(orgElment, syncOrgElement, appName);
		updateThisLeader(orgElment, syncOrgElement, appName);
		updateSuperLeader(orgElment, syncOrgElement, appName);
		if (syncOrgElement.contains("customProps")) {
			JSONObject props = syncOrgElement.getCustomProps();
			Map<String, Object> map = new HashMap<String, Object>();
			for (Object key : props.keySet()) {
				map.put(key.toString(), props.get(key).toString());
			}
			Map propsMap = DynamicAttributeUtil
					.convertCustomProp(SysOrgDept.class.getName(), map);
			orgDept.getCustomPropMap().putAll(propsMap);
		}			
		return orgDept;
	}

	private SysOrgPerson setPersonInfo(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, String appName)
			throws Exception {
		SysOrgPerson orgPerson = (SysOrgPerson) orgElment;
		setOrgPerson(orgPerson, syncOrgElement, appName);
		updateParent(orgElment, syncOrgElement, appName);
		if (syncOrgElement.contains(POSTS)) {
			if (syncOrgElement.getPosts() != null) {
				List tmpList = getForeignObj(syncOrgElement.getPosts(),
						appName);
				if (tmpList != null && !tmpList.isEmpty()) {
					orgPerson.setFdPosts(tmpList);
				} else {
                    orgPerson.setFdPosts(null);
                }
			} else {
                orgPerson.setFdPosts(null);
            }
		}
		if (syncOrgElement.contains("customProps")) {
			JSONObject props = syncOrgElement.getCustomProps();
			Map<String, Object> map = new HashMap<String, Object>();
			for (Object key : props.keySet()) {
				map.put(key.toString(), props.get(key).toString());
			}
			Map propsMap = DynamicAttributeUtil
					.convertCustomProp(SysOrgPerson.class.getName(), map);
			orgPerson.getCustomPropMap().putAll(propsMap);
		}
		return orgPerson;
	}

	private SysOrgGroup setGroupInfo(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, String appName)
			throws Exception {
		setBaseInfo(orgElment, syncOrgElement, appName);
		SysOrgGroup orgGroup = (SysOrgGroup) orgElment;
		if (syncOrgElement.contains(MEMBERS)) {
			if (syncOrgElement.getMembers() != null) {
				List tmpList = getForeignObj(syncOrgElement.getMembers(),
						appName);
				if (tmpList != null && !tmpList.isEmpty()) {
					orgGroup.setFdMembers(tmpList);
				} else {
                    orgGroup.setFdMembers(null);
                }
			} else {
                orgGroup.setFdMembers(null);
            }
		}
		return orgGroup;
	}

	private SysOrgPost setPostInfo(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, String appName)
			throws Exception {
		setBaseInfo(orgElment, syncOrgElement, appName);
		SysOrgPost orgPost = (SysOrgPost) orgElment;
		updateParent(orgElment, syncOrgElement, appName);
		updateThisLeader(orgElment, syncOrgElement, appName);
		if (syncOrgElement.contains(PERSONS)) {
			if (syncOrgElement.getPersons() != null) {
				List tmpList = getForeignObj(syncOrgElement.getPersons(),
						appName);
				if (tmpList != null && !tmpList.isEmpty()) {
					orgPost.setFdPersons(tmpList);
				} else {
                    orgPost.setFdPersons(null);
                }
			} else {
                orgPost.setFdPersons(null);
            }
		}
		return orgPost;
	}

	private void update(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, String appName)
			throws Exception {
		if (syncOrgElement.getIsAvailable() != true) {
			orgElment.setFdIsAvailable(new Boolean(false));
			orgElment.getHbmChildren().clear();
			orgElment.setFdFlagDeleted(new Boolean(false));
			orgElementService.update(orgElment);
			return;
		}
		if (orgElment instanceof SysOrgOrg) {
			SysOrgOrg orgOrg = setOrgInfo(orgElment, syncOrgElement, appName);
			orgOrgService.update(orgOrg);
		}
		if (orgElment instanceof SysOrgDept) {
			SysOrgDept orgDept = setDeptInfo(orgElment, syncOrgElement,
					appName);
			orgDeptService.update(orgDept);
		}
		if (orgElment instanceof SysOrgGroup) {
			SysOrgGroup orgGroup = setGroupInfo(orgElment, syncOrgElement,
					appName);
			orgGroupService.update(orgGroup);
		}
		if (orgElment instanceof SysOrgPost) {
			SysOrgPost orgPost = setPostInfo(orgElment, syncOrgElement,
					appName);
			orgPostService.update(orgPost);
		}
		if (orgElment instanceof SysOrgPerson) {
			SysOrgPerson orgPerson = setPersonInfo(orgElment, syncOrgElement,
					appName);
			orgPersonService.update(orgPerson);
		}
	}

	private void setBaseInfo(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, String appName) {
		orgElment.setFdName(syncOrgElement.getName());
		if (syncOrgElement.contains(NO)) {
			orgElment.setFdNo(syncOrgElement.getNo());
		}
		if (syncOrgElement.contains(ORDER)) {
			if (StringUtil.isNotNull(syncOrgElement.getOrder())) {
                orgElment
                        .setFdOrder(Integer.valueOf(syncOrgElement.getOrder()));
            } else {
                orgElment.setFdOrder(null);
            }
		}
		if (syncOrgElement.contains(KEYWORD)) {
			orgElment.setFdKeyword(syncOrgElement.getKeyword());
		}
		if (syncOrgElement.contains(MEMO)) {
			orgElment.setFdMemo(syncOrgElement.getMemo());
		}
		orgElment.setFdIsAvailable(syncOrgElement.getIsAvailable());
		orgElment.setFdAlterTime(new Date(System.currentTimeMillis()));
		orgElment.setFdImportInfo(appName
				+ syncOrgElement.getId());
	}

	private void setOrgPerson(SysOrgPerson orgPerson,
			SysSynchroOrgElement syncOrgElement, String appName) {
		if (syncOrgElement.contains(LOGIN_NAME)) {
			orgPerson.setFdLoginName(syncOrgElement.getLoginName());
		}
		if (syncOrgElement.contains(NICK_NAME)) {
			orgPerson.setFdNickName(syncOrgElement.getNickName());
		}
		if (syncOrgElement.contains(PASSWORD)) {
			orgPerson
					.setFdPassword(getPersonPass(syncOrgElement.getPassword()));
			orgPerson.setFdInitPassword(
					PasswordUtil.desEncrypt(SecureUtil
							.BASE64Decoder(syncOrgElement.getPassword())));
		}
		if (syncOrgElement.contains(EMAIL)) {
			orgPerson.setFdEmail(syncOrgElement.getEmail());
		}
		if (syncOrgElement.contains(MOBILE_NO)) {
			orgPerson.setFdMobileNo(syncOrgElement.getMobileNo());
		}
		if (syncOrgElement.contains(ATTENDANCE_CARD_NUMBER)) {
			orgPerson.setFdAttendanceCardNumber(syncOrgElement
					.getAttendanceCardNumber());
		}
		if (syncOrgElement.contains(WORK_PHONE)) {
			orgPerson.setFdWorkPhone(syncOrgElement.getWorkPhone());
		}
		if (syncOrgElement.contains(RTX_NO)) {
			orgPerson.setFdRtxNo(syncOrgElement.getRtx());
		}
		if (syncOrgElement.contains(WECHAT_NO)) {
			orgPerson.setFdWechatNo(syncOrgElement.getWechat());
		}
		if (syncOrgElement.contains(SEX)) {
			orgPerson.setFdSex(syncOrgElement.getSex());
		}
		if (syncOrgElement.contains(SHORT_NO)) {
			orgPerson.setFdShortNo(syncOrgElement.getShortNo());
		}
		setBaseInfo(orgPerson, syncOrgElement, appName);
	}

	private void add(SysOrgElement orgElment,
			SysSynchroOrgElement syncOrgElement, String appName)
			throws Exception {
		if (syncOrgElement.contains(LUNID)) {
			orgElment.setFdId(syncOrgElement.getLunid());
		}
		SysOrgDefaultConfig sysOrgDefaultConfig = new SysOrgDefaultConfig();
		
		Integer order = null;

		if (orgElment instanceof SysOrgOrg) {
			order = sysOrgDefaultConfig.getOrgOrgDefaultOrder();
			SysOrgOrg orgOrg = setOrgInfo(orgElment, syncOrgElement, appName);
			if(orgOrg.getFdOrder() == null){
				if(order!=null) {
                    orgOrg.setFdOrder(order);
                }
			}
			orgOrgService.add(orgOrg);
		}
		if (orgElment instanceof SysOrgDept) {
			order = sysOrgDefaultConfig.getOrgDeptDefaultOrder();
			SysOrgDept orgDept = setDeptInfo(orgElment, syncOrgElement,
					appName);
			if(orgDept.getFdOrder() == null){
				if(order!=null) {
                    orgDept.setFdOrder(order);
                }
			}
			orgDeptService.add(orgDept);
		}
		if (orgElment instanceof SysOrgGroup) {
			order = sysOrgDefaultConfig.getOrgGroupDefaultOrder();
			SysOrgGroup orgGroup = setGroupInfo(orgElment, syncOrgElement,
					appName);
			if(orgGroup.getFdOrder() == null){
				if(order!=null) {
                    orgGroup.setFdOrder(order);
                }
			}
			orgGroupService.add(orgGroup);
		}
		if (orgElment instanceof SysOrgPost) {
			order = sysOrgDefaultConfig.getOrgPostDefaultOrder();
			SysOrgPost orgPost = setPostInfo(orgElment, syncOrgElement,
					appName);
			if(orgPost.getFdOrder() == null){
				if(order!=null) {
                    orgPost.setFdOrder(order);
                }
			}
			orgPostService.add(orgPost);
		}
		if (orgElment instanceof SysOrgPerson) {
			order = sysOrgDefaultConfig.getOrgPersonDefaultOrder();
			SysOrgPerson orgPerson = setPersonInfo(orgElment, syncOrgElement,
					appName);
			if(orgPerson.getFdOrder() == null){
				if(order!=null) {
                    orgPerson.setFdOrder(order);
                }
			}
			
			String psw = sysOrgDefaultConfig.getOrgDefaultPassword();
			
			if(StringUtil.isNull(syncOrgElement.getPassword())){
				if(StringUtil.isNotNull(psw)){
					orgPerson.setFdPassword(passwordEncoder.encodePassword(psw));
					orgPerson.setFdInitPassword(PasswordUtil.desEncrypt(psw));
				}
			}
			
			orgPersonService.add(orgPerson);
		}
	}

}
