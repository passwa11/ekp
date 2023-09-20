package com.landray.kmss.third.ekp.java.oms.in;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.util.SpringBeanUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.oms.in.interfaces.IOMSResultSet;
import com.landray.kmss.sys.oms.in.interfaces.OMSBaseSynchroInIteratorProvider;
import com.landray.kmss.sys.oms.in.interfaces.ValueMapTo;
import com.landray.kmss.sys.oms.in.interfaces.ValueMapType;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgGroupCate;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupCateService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.third.ekp.java.EkpJavaConfig;
import com.landray.kmss.third.ekp.java.oms.in.client.SysSynchroGetOrgBaseInfoContext;
import com.landray.kmss.third.ekp.java.oms.in.client.SysSynchroGetOrgBaseInfoContextV2;
import com.landray.kmss.third.ekp.java.oms.in.client.SysSynchroGetOrgInfoContext;
import com.landray.kmss.third.ekp.java.oms.in.client.SysSynchroGetOrgInfoContextV2;
import com.landray.kmss.third.ekp.java.oms.in.client.SysSynchroOrgResult;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

public class EkpSynchroInIteratorProviderImp extends
		OMSBaseSynchroInIteratorProvider {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EkpSynchroInIteratorProviderImp.class);

	protected int preCount = 1000;

	private IEkpSynchro ekpSynchro = null;

	protected String lastUpdateTime = null;

	private String lastUpdateTime_tmp = null;

	private EkpRoleSynchroServiceImp ekpRoleSynchroService;

	private ISysOrgGroupService sysOrgGroupService;

	private ISysOrgPersonService sysOrgPersonService;

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	private ISysAppConfigService sysAppConfigService;

	public ISysAppConfigService getSysAppConfigService() {
		if (sysAppConfigService == null) {
			sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
		}
		return sysAppConfigService;
	}

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	public void setEkpSynchro(IEkpSynchro ekpSynchro) {
		this.ekpSynchro = ekpSynchro;
	}

	@Override
	public void init() throws Exception {
		TransactionStatus status = null;
		Exception t = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			Map<String, String> map = getSysAppConfigService().findByKey(EkpOmsConfig.class.getName());
			lastUpdateTime = map.get("lastUpdateTime");
			lastUpdateTime_tmp = map.get("lastUpdateTime");
			logger.info("同步内部组织：lastUpdateTime=" + lastUpdateTime);
			TransactionUtils.commit(status);
		} catch (Exception e) {
			t = e;
			throw e;
		} finally {
			if (t != null && status != null) {
				TransactionUtils.rollback(status);
			}
		}
	}

	@Override
	public IOMSResultSet getAllRecordBaseAttributes() throws Exception {
		EkpJavaConfig ekpJavaConfig = new EkpJavaConfig();
		String synchroNoBusiness = ekpJavaConfig
				.getValue("kmss.oms.in.java.synchro.business.no");
		EkpResultSet resultSet = null;
		SysSynchroOrgResult orgResult = null;
		if ("true".equals(synchroNoBusiness)) {
			SysSynchroGetOrgBaseInfoContextV2 baseInfo = new SysSynchroGetOrgBaseInfoContextV2();
			baseInfo.setReturnOrgType("");
			baseInfo.setReturnType("");
			baseInfo.setIsBusiness(null);
			orgResult = ekpSynchro
					.getElementsBaseInfoV2(baseInfo);
		} else {
			SysSynchroGetOrgBaseInfoContext baseInfo = new SysSynchroGetOrgBaseInfoContext();
			baseInfo.setReturnOrgType("");
			baseInfo.setReturnType("");
			orgResult = ekpSynchro
					.getElementsBaseInfo(baseInfo);
		}

		logger.debug("orgResult返回状态值为：" + orgResult.getCount() + ",信息为:"
				+ orgResult.getMessage());
		if (orgResult.getReturnState() == 2) {
			if (StringUtil.isNotNull(orgResult.getMessage())) {
				resultSet = new EkpResultSet(orgResult.getMessage());
			} else {
				resultSet = new EkpResultSet(null);
			}
		} else {
			logger.error("获取组织架构所有基本信息出错,返回状态值为:" + orgResult.getCount()
					+ ",错误信息为:" + orgResult.getMessage());
		}
		return resultSet;
	}

	@Override
	public IOMSResultSet getSynchroRecords() throws Exception {
		EkpResultSet resultSet = null;
		EkpJavaConfig ekpJavaConfig = new EkpJavaConfig();
		String synchroNoBusiness = ekpJavaConfig
				.getValue("kmss.oms.in.java.synchro.business.no");
		List<String> resultList = new ArrayList<String>();
		SysSynchroGetOrgInfoContext infoContext = null;
		if ("true".equals(synchroNoBusiness)) {
			infoContext = new SysSynchroGetOrgInfoContextV2();
			((SysSynchroGetOrgInfoContextV2) infoContext).setIsBusiness(null);
		} else {
			infoContext = new SysSynchroGetOrgInfoContext();
		}
		infoContext.setReturnOrgType("");
		infoContext.setCount(preCount);

		if (StringUtil.isNotNull(lastUpdateTime)) {
			Date date = DateUtil.convertStringToDate(lastUpdateTime,
					"yyyy-MM-dd HH:mm:ss.SSS");
			date = new Date(date.getTime() - 1000);
			lastUpdateTime = DateUtil.convertDateToString(date,
					"yyyy-MM-dd HH:mm:ss.SSS");
		}
		while (true) {
			infoContext.setBeginTimeStamp(lastUpdateTime);
			SysSynchroOrgResult orgResult = null;
			if ("true".equals(synchroNoBusiness)) {
				orgResult = ekpSynchro
						.getUpdatedElementsV2(
								(SysSynchroGetOrgInfoContextV2) infoContext);
			} else {
				orgResult = ekpSynchro
						.getUpdatedElements(infoContext);
			}
			if (orgResult.getReturnState() == 2) {
				String resultStr = orgResult.getMessage();
				if (StringUtil.isNotNull(resultStr)) {
					resultList.add(resultStr);
					lastUpdateTime = orgResult.getTimeStamp();
				}
				if (orgResult.getCount() < preCount) {
					break;
				}
			} else {
				logger.error("分批获取组织架构所有信息出错,返回状态值为:" + orgResult.getCount()
						+ ",错误信息为:" + orgResult.getMessage());
				break;
			}
		}
		if (!resultList.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < resultList.size(); i++) {
				String tmpStr = resultList.get(i);
				if (i == 0) {
					sb.append(tmpStr.substring(0, tmpStr.length() - 1));
				} else {
					sb.append("," + tmpStr.substring(1, tmpStr.length() - 1));
				}
			}
			sb.append("]");
			resultSet = new EkpResultSet(sb.toString());
		} else {
			resultSet = new EkpResultSet(null);
		}
		return resultSet;
	}

	@Override
	public void setLastUpdateTime(Date date) {
		if (date != null && StringUtil.isNull(this.lastUpdateTime)) {
			this.lastUpdateTime = DateUtil.convertDateToString(date,
					"yyyy-MM-dd HH:mm:ss.SSS");
		} else {
			logger.warn("同步组织架构时，设置的同步时间戳异常，已阻止。当前lastUpdateTime：" + lastUpdateTime + "，异常lastUpdateTime：" +
					DateUtil.convertDateToString(date, "yyyy-MM-dd HH:mm:ss.SSS"));
		}
	}

	@Override
	public String getKey() {
		return EkpSynchroInIteratorProviderImp.class.getName();
	}

	private void terminateItem(int type) throws Exception {
		TransactionStatus status = null;
		Exception t = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			switch (type) {
				case 1: {
					// 更新群组分类
					updateOrgGroupCate();
					break;
				}
				case 2: {
					// 更新群组的所属分类
					updateOrgGroupCateOfGroup();
					break;
				}
				case 3: {
					// 更新职务信息
					updateOrgStaffingLevel();
					break;
				}
				case 4: {
					// 更新同步时间戳
					EkpOmsConfig ekpOmsConfig = new EkpOmsConfig();
					ekpOmsConfig.setLastUpdateTime(lastUpdateTime);
					ekpOmsConfig.save();
					break;
				}
				default: {
					break;
				}
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
	}

	@Override
	public void terminate() throws Exception {
		// 更新群组分类
		terminateItem(1);
		// 更新群组的所属分类
		terminateItem(2);
		// 更新职务信息
		terminateItem(3);

		if (StringUtil.isNotNull(lastUpdateTime)) {
			if(logger.isInfoEnabled()) {
				logger.info("组织架构同步完成，保存同步时间戳：" + lastUpdateTime);
			}
			// 更新同步时间戳
			terminateItem(4);
		}

		ekpRoleSynchroService.syncRoleLineDatas(lastUpdateTime);
	}

	// TODO 根据需求更改关联关系
	protected static ValueMapTo valueMapTo = ValueMapTo.IMPORTINFO;

	@Override
	public ValueMapTo getDeptLeaderValueMapTo() {
		return valueMapTo;
	}

	@Override
	public ValueMapTo getDeptParentValueMapTo() {
		return valueMapTo;
	}

	@Override
	public ValueMapTo getDeptSuperLeaderValueMapTo() {
		return valueMapTo;
	}

	@Override
	public ValueMapTo getGroupMemberValueMapTo() {
		return valueMapTo;
	}

	@Override
	public ValueMapTo getPersonDeptValueMapTo() {
		return valueMapTo;
	}

	@Override
	public ValueMapTo getPersonPostValueMapTo() {
		return valueMapTo;
	}

	@Override
	public ValueMapTo getPostDeptValueMapTo() {
		return valueMapTo;
	}

	@Override
	public ValueMapTo getPostLeaderValueMapTo() {
		return valueMapTo;
	}

	@Override
	public ValueMapTo getPostPersonValueMapTo() {
		return valueMapTo;
	}

	@Override
	public ValueMapType[] getDeptParentValueMapType() {
		return new ValueMapType[] { ValueMapType.ORG, ValueMapType.DEPT };
	}

	@Override
	public ValueMapType[] getPersonDeptValueMapType() {
		return new ValueMapType[] { ValueMapType.ORG, ValueMapType.DEPT };
	}

	@Override
	public ValueMapType[] getPostDeptValueMapType() {
		return new ValueMapType[] { ValueMapType.ORG, ValueMapType.DEPT };
	}

	@Override
	public ValueMapType[] getGroupMemberValueMapType() {
		return new ValueMapType[] { ValueMapType.ORG, ValueMapType.DEPT,
				ValueMapType.POST, ValueMapType.PERSON };
	}

	@Override
	public int getPasswordType() {
		return PASSWORD_TYPE_REQUIRED | PASSWORD_TYPE_NOT_TRANSFER;
	}

	public void setEkpRoleSynchroService(
			EkpRoleSynchroServiceImp ekpRoleSynchroService) {
		this.ekpRoleSynchroService = ekpRoleSynchroService;
	}

	public EkpRoleSynchroServiceImp getEkpRoleSynchroService() {
		return ekpRoleSynchroService;
	}

	public ISysOrgGroupService getSysOrgGroupService() {
		return sysOrgGroupService;
	}

	public void setSysOrgGroupService(ISysOrgGroupService sysOrgGroupService) {
		this.sysOrgGroupService = sysOrgGroupService;
	}

	private ISysOrgGroupCateService sysOrgGroupCateService;

	public ISysOrgGroupCateService getSysOrgGroupCateService() {
		return sysOrgGroupCateService;
	}

	public void setSysOrgGroupCateService(
			ISysOrgGroupCateService sysOrgGroupCateService) {
		this.sysOrgGroupCateService = sysOrgGroupCateService;
	}

	private SysOrgGroupCate buildOrgGroupCate(JSONObject jsonObject,
											  boolean isBase) throws Exception {
		SysOrgGroupCate groupCate = new SysOrgGroupCate();
		groupCate.setFdId((String) jsonObject.get("id"));
		groupCate.setFdName((String) jsonObject.get("name"));
		if (jsonObject.containsKey("langProps")) {
			JSONObject o = (JSONObject) jsonObject.get("langProps");
			if (o != null) {
				Map map = new HashMap();
				for (String key : (Set<String>) o.keySet()) {
					map.put(key, o.get(key));
				}
				groupCate.setDynamicMap(map);
			}
		}
		if (!isBase) {
			if (jsonObject.containsKey("keyword")) {
				groupCate.setFdKeyword((String) jsonObject.get("keyword"));
			}
			if (jsonObject.containsKey("parent")) {
				String parentId = (String) jsonObject.get("parent");
				if (StringUtil.isNotNull(parentId)) {
					groupCate
							.setFdParent((SysOrgGroupCate) sysOrgGroupCateService
									.findByPrimaryKey(parentId));
				}
			}
		}

		return groupCate;
	}

	private List<SysOrgGroupCate> getOrgGroupCates(String json, boolean isBase)
			throws Exception {
		List<SysOrgGroupCate> sysOrgGroupCates = new ArrayList<SysOrgGroupCate>();
		JSONArray array = (JSONArray) JSONValue.parse(json);
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = (JSONObject) array.get(i);
			sysOrgGroupCates.add(buildOrgGroupCate(jsonObject, isBase));
		}
		return sysOrgGroupCates;
	}

	private void updateOrgGroupCate() throws Exception {
		SysSynchroOrgResult orgResult = null;
		try {
			orgResult = ekpSynchro
					.getOrgGroupCates(new SysSynchroGetOrgInfoContext());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取群组分类信息出错，跳过群组分类同步", e);
			return;
		}

		if (orgResult.getReturnState() == 2) {
			String resultStr = orgResult.getMessage();
			if (StringUtil.isNotNull(resultStr)) {
				List<SysOrgGroupCate> sysOrgGroupCates = getOrgGroupCates(
						resultStr, true);

				TransactionStatus updateDtatus = TransactionUtils
						.beginNewTransaction();
				try {

					for (SysOrgGroupCate groupCate : sysOrgGroupCates) {
						addGroupCate(groupCate);
					}
					sysOrgGroupCates = getOrgGroupCates(resultStr, false);
					for (SysOrgGroupCate groupCate : sysOrgGroupCates) {
						updateGroupCate(groupCate);
					}
					TransactionUtils.getTransactionManager().commit(
							updateDtatus);
				} catch (Exception e) {
					TransactionUtils.getTransactionManager().rollback(
							updateDtatus);
					throw e;
				}

			}
		} else {
			logger.error("获取组织架构角色分类信息出错,返回状态值为:" + orgResult.getCount()
					+ ",错误信息为:" + orgResult.getMessage());
		}
	}

	private void addGroupCate(SysOrgGroupCate groupCate) throws Exception {
		IBaseModel origin = sysOrgGroupCateService.findByPrimaryKey(groupCate
				.getFdId(), null, true);
		if (origin == null) {
			Date current = new Date();
			groupCate.setFdCreateTime(current);
			groupCate.setFdAlterTime(current);
			sysOrgGroupCateService.add(groupCate);
		}
	}

	private void updateGroupCate(SysOrgGroupCate groupCate) throws Exception {
		SysOrgGroupCate origin = (SysOrgGroupCate) sysOrgGroupCateService
				.findByPrimaryKey(groupCate.getFdId(), null, true);
		origin.setFdAlterTime(new Date());
		origin.setFdKeyword(groupCate.getFdKeyword());
		origin.setFdName(groupCate.getFdName());
		origin.getDynamicMap().putAll(groupCate.getDynamicMap());
		origin.setFdParent(groupCate.getFdParent());

		sysOrgGroupCateService.update(origin);
	}

	private void updateOrgGroupCateOfGroup() throws Exception {
		SysSynchroGetOrgInfoContext infoContext = new SysSynchroGetOrgInfoContext();
		infoContext.setReturnOrgType("[{\"type\":\"group\"}]");
		infoContext.setCount(1000000);
		infoContext.setBeginTimeStamp(lastUpdateTime_tmp);
		SysSynchroOrgResult orgResult = ekpSynchro
				.getUpdatedElements(infoContext);

		if (orgResult.getReturnState() == 2) {
			String resultStr = orgResult.getMessage();
			if (StringUtil.isNotNull(resultStr)) {
				JSONArray array = (JSONArray) JSONValue.parse(resultStr);

				TransactionStatus updateDtatus = TransactionUtils
						.beginNewTransaction();
				try {
					for (int i = 0; i < array.size(); i++) {
						JSONObject jsonObject = (JSONObject) array.get(i);
						String id = (String) jsonObject.get("id");
						SysOrgGroup group = (SysOrgGroup) sysOrgGroupService
								.findByPrimaryKey(id, null, true);
						if (group == null) {
							continue;
						}
						if (jsonObject.containsKey("groupCateId")) {
							String groupCateId = (String) jsonObject
									.get("groupCateId");
							if (StringUtil.isNotNull(groupCateId)) {
								SysOrgGroupCate groupCate = (SysOrgGroupCate) sysOrgGroupCateService
										.findByPrimaryKey(groupCateId);
								if (groupCate == null || group == null) {
									logger.error("找不到对应的群组或群组分类，群组ID：" + id
											+ "，群组分类ID：" + groupCateId);
								} else {
									group.setFdGroupCate(groupCate);
									// sysOrgGroupService.update(group);
								}
							}
						}
						if (jsonObject.containsKey("authReaders")) {
							JSONArray authReaders = (JSONArray) jsonObject
									.get("authReaders");
							List readers = new ArrayList();
							for (int j = 0; j < authReaders.size(); j++) {
								String readerId = (String) authReaders.get(j);
								SysOrgElement reader = (SysOrgElement) sysOrgElementService
										.findByPrimaryKey(readerId, null, true);
								if (reader != null) {
									readers.add(reader);
								}
							}
							group.setAuthReaders(readers);
						}
						if (jsonObject.containsKey("authEditors")) {
							JSONArray authEditors = (JSONArray) jsonObject
									.get("authEditors");
							List editors = new ArrayList();
							for (int j = 0; j < authEditors.size(); j++) {
								String editorId = (String) authEditors.get(j);
								SysOrgElement editor = (SysOrgElement) sysOrgElementService
										.findByPrimaryKey(editorId, null, true);
								if (editor != null) {
									editors.add(editor);
								}
							}
							group.setAuthEditors(editors);
						}
						if (jsonObject.containsKey("authReaderFlag")) {
							boolean authReaderFlag = (boolean) jsonObject
									.get("authReaderFlag");
							group.setAuthReaderFlag(authReaderFlag);
						}

						sysOrgGroupService.update(group);
					}
					TransactionUtils.getTransactionManager().commit(
							updateDtatus);
				} catch (Exception e) {
					TransactionUtils.getTransactionManager().rollback(
							updateDtatus);
					throw e;
				}

			}
		}
	}

	private SysOrganizationStaffingLevel buildOrgStaffingLevel(
			JSONObject jsonObject) throws Exception {
		SysOrganizationStaffingLevel staffingLevel = new SysOrganizationStaffingLevel();
		staffingLevel.setFdId((String) jsonObject.get("id"));
		staffingLevel.setFdName((String) jsonObject.get("name"));
		if (jsonObject.containsKey("langProps")) {
			JSONObject o = (JSONObject) jsonObject.get("langProps");
			if (o != null) {
				Map map = new HashMap();
				for (String key : (Set<String>) o.keySet()) {
					map.put(key, o.get(key));
				}
				staffingLevel.setDynamicMap(map);
			}
		}
		Object levelObj = jsonObject.get("level");
		Integer level;
		if (levelObj instanceof Long) {
			level = ((Long) levelObj).intValue();
		} else {
			level = (Integer) levelObj;
		}
		staffingLevel.setFdLevel(level);
		if (jsonObject.containsKey("description")) {
			staffingLevel.setFdDescription((String) jsonObject
					.get("description"));
		}
		if (jsonObject.containsKey("creator")) {
			SysOrgPerson docCreator = (SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey((String) jsonObject.get("creator"), null,
							true);
			if (docCreator != null) {
				staffingLevel.setDocCreator(docCreator);
			}
		}
		if (jsonObject.containsKey("persons")) {
			JSONArray persons = (JSONArray) jsonObject.get("persons");
			List<SysOrgPerson> fdPersons = new ArrayList<SysOrgPerson>();
			for (Object id : persons) {
				SysOrgPerson person = (SysOrgPerson) sysOrgPersonService
						.findByPrimaryKey((String) id, null, true);
				if (person != null) {
					fdPersons.add(person);
				} else {
					logger.error("更新职务信息‘" + staffingLevel.getFdId() + "-"
							+ staffingLevel.getFdName() + "'时，找不到用户：" + id);
				}
			}
			staffingLevel.setFdPersons(fdPersons);
		}

		return staffingLevel;
	}

	private List<SysOrganizationStaffingLevel> getOrgStaffingLevels(
			String json, boolean isBase) throws Exception {
		List<SysOrganizationStaffingLevel> sysOrganizationStaffingLevels = new ArrayList<SysOrganizationStaffingLevel>();
		JSONArray array = (JSONArray) JSONValue.parse(json);
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = (JSONObject) array.get(i);
			sysOrganizationStaffingLevels
					.add(buildOrgStaffingLevel(jsonObject));
		}
		return sysOrganizationStaffingLevels;
	}

	private void addOrUpdateStaffingLevel(
			SysOrganizationStaffingLevel staffingLevel) throws Exception {
		IBaseModel origin = sysOrganizationStaffingLevelService
				.findByPrimaryKey(staffingLevel.getFdId(), null, true);
		if (origin == null) {
			sysOrganizationStaffingLevelService.add(staffingLevel);
		} else {
			SysOrganizationStaffingLevel staffingLevel_db = (SysOrganizationStaffingLevel) origin;
			staffingLevel_db.setFdName(staffingLevel.getFdName());
			staffingLevel_db.getDynamicMap()
					.putAll(staffingLevel.getDynamicMap());
			staffingLevel_db.setFdDescription(staffingLevel.getFdDescription());
			staffingLevel_db.setFdLevel(staffingLevel.getFdLevel());
			staffingLevel_db.setFdIsDefault(staffingLevel.getFdIsDefault());
			staffingLevel_db.setFdPersons(staffingLevel.getFdPersons());
			sysOrganizationStaffingLevelService.update(staffingLevel_db);
		}
	}

	private void updateOrgStaffingLevel() throws Exception {

		SysSynchroOrgResult orgResult = null;
		try {
			orgResult = ekpSynchro
					.getOrgStaffingLevels(new SysSynchroGetOrgInfoContext());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取职级信息出错，跳过职级同步", e);
			return;
		}

		if (orgResult.getReturnState() == 2) {
			String resultStr = orgResult.getMessage();
			if (StringUtil.isNotNull(resultStr)) {
				List<SysOrganizationStaffingLevel> sysOrganizationStaffingLevels = getOrgStaffingLevels(
						resultStr, true);
				TransactionStatus updateDtatus = TransactionUtils
						.beginNewTransaction();
				try {
					for (SysOrganizationStaffingLevel staffingLevel : sysOrganizationStaffingLevels) {
						addOrUpdateStaffingLevel(staffingLevel);
					}
					TransactionUtils.getTransactionManager().commit(
							updateDtatus);
				} catch (Exception e) {
					TransactionUtils.getTransactionManager().rollback(
							updateDtatus);
					throw e;
				}
			}
		} else {
			logger.error("获取组织架构角色分类信息出错,返回状态值为:" + orgResult.getCount()
					+ ",错误信息为:" + orgResult.getMessage());
		}
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public ISysOrgPersonService getSysOrgPersonService() {
		return sysOrgPersonService;
	}

	@Override
	public boolean isSynchroInEnable() throws Exception {
		// TODO 自动生成的方法存根
		EkpJavaConfig config = new EkpJavaConfig();
		String synchroInEnable = config.getValue("kmss.oms.in.java.enabled");
		if ("true".equals(synchroInEnable)) {
			return true;
		}
		return false;
	}

	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		return sysOrgElementService;
	}

	public void
	setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

}
