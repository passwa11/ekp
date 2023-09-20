package com.landray.kmss.sys.organization.webservice.out;

import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.metadata.dict.SysDictExtendDynamicProperty;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementHideRange;
import com.landray.kmss.sys.organization.model.SysOrgElementRange;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgGroupCate;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.util.IntegerWrapper;
import com.landray.kmss.web.annotation.RestApi;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Controller
@RequestMapping(value = "/api/sys-organization/sysSynchroGetOrg", method = RequestMethod.POST)
@RestApi(docUrl = "/sys/organization/rest/sysOrg_out_rest_help.jsp", name = "sysSynchroGetOrgWebService", resourceKey = "sys-organization:sysSynchroGetOrg.title")
public class SysSynchroGetOrgWebServiceImpl extends SysSynchroGetOrgWebServiceImp {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysSynchroGetOrgWebServiceImpl.class);

	/**
	 * 字符传对象，转换成对象需要特定类型
	 * 
	 * @param <T>
	 * @param index
	 * @param person	
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T typeConversion(int index, Object[] person, T clazz) {
		if (clazz instanceof Boolean) {
			return (T) (null != person[index] ? Boolean.valueOf(person[index].toString()) : null);
		} else if (clazz instanceof Date) {
			String dateStr = (null != person[index] ? person[index].toString() : null);
			if (null != dateStr) {
				int idx = dateStr.lastIndexOf(".");
				if (dateStr.length() > 1) {
					String tempStr = dateStr.substring(idx + 1, dateStr.length());
					if (1 == tempStr.length()) {
						dateStr = dateStr + "00";
					}
					if (2 == tempStr.length()) {
						dateStr = dateStr + "0";
					}
				}
			}
			return (T) (null != person[index] ? timeConvert(dateStr) : null);
		} else if (clazz instanceof Integer) {
			return (T) (null != person[index] ? Integer.valueOf(person[index].toString()) : null);
		} else {
			return (T) (null != person[index] ? person[index].toString() : null);
		}
	}

	/**
	 * 把中间表的两个关联ID，处理为Map数据，查询第一个字段为key，第二字段ID为List的value
	 * @param relationMap
	 * @param relationSql
	 * @throws Exception
	 */
	private Map<String, List<String>> getRelationData(String relationSql) throws Exception {
		Map<String, List<String>> relationMap = new HashMap<String, List<String>>();
		List<?> relationList = getSysOrgElementService().getBaseDao().getHibernateSession().createNativeQuery(relationSql)
				.list();
		for (Object obj : relationList) {
			Object[] relation = (Object[]) obj;
			String fdId = relation[0].toString();
			if (!relationMap.containsKey(fdId)) {
				List<String> ids = new ArrayList<String>();
				ids.add(relation[1].toString());
				relationMap.put(fdId, ids);
			} else {
				List<String> ids = relationMap.get(fdId);
				ids.add(relation[1].toString());
			}
		}
		return relationMap;
	}

	/**
	 * 根据数字字典模型类，获取多语言字段，用于SQL查询字段
	 * 
	 * @param dynamicPropertyString
	 * @param dynamicCustomProps
	 * @param orgElementDictModel
	 * @param alias
	 */
	private void getDynamicLangColumn(StringBuilder dynamicPropertyString, List<String> dynamicCustomProps,
			SysDictModel orgElementDictModel, String alias) {
		//拼接动态字段用于select查询字段
		List<SysDictCommonProperty> list = orgElementDictModel.getLanguageSuportList();
		if (list != null) {
			Map<String, String> langs = SysLangUtil.getSupportedLangs();
			for (SysDictCommonProperty prop : list) {
				for (String lang : langs.keySet()) {
					String column = prop.getColumn();
					if (column.length() > 27) {
						column = column.substring(0, 27);
					}

					String columnName = column + "_" + lang;
					if (0 > dynamicPropertyString.indexOf("." + columnName)) {
						dynamicPropertyString.append(",");
						dynamicPropertyString.append(alias + "." + columnName);
						dynamicCustomProps.add(prop.getName() + lang);
					}
				}
			}
		}
	}

	/**
	 * 根据数字字典模型类，获取多动态字段，用于SQL查询字段
	 * 
	 * @param dynamicPropertyString
	 * @param dynamicLangProps
	 * @param orgElementDictModel
	 * @param alias
	 */
	private void getDynamicCustomColumn(StringBuilder dynamicPropertyString, List<String> dynamicLangProps,
			SysDictModel orgElementDictModel, String alias) {
		List<SysDictCommonProperty> orgElementDictPropertyList = orgElementDictModel.getPropertyList();

		//拼接动态字段用于select查询字段
		for (SysDictCommonProperty property : orgElementDictPropertyList) {
			//判断是动态字段
			if (property instanceof SysDictExtendDynamicProperty) {
				if (0 > dynamicPropertyString.indexOf("." + property.getColumn())) {
					dynamicPropertyString.append(",");
					dynamicPropertyString.append(alias + "." + property.getColumn());
					dynamicLangProps.add(property.getName());
				}
			}
		}
	}

	private boolean isBatchSubmit(int limit, int resultLength, int batchCount, int curerentCount, Integer index) {
		boolean isSubmit = false;
		if (limit <= batchCount) {
			batchCount = limit;
		} else if (resultLength <= batchCount) {
			batchCount = resultLength;
		}

		if (curerentCount >= batchCount) {
			isSubmit = true;
			return isSubmit;
		}

		//limit小于查询列表总数
		if (limit == index + 1) {
			isSubmit = true;
			return isSubmit;
		}

		//最后一批可能走这个逻辑
		if (index + 1 == resultLength) {
			isSubmit = true;
			return isSubmit;
		}

		return isSubmit;
	}

	/**
	 * 数组转换成对象
	 * 
	 * @param struct
	 * @param orgElement
	 */
	private void toOrgElement(Object[] struct, SysOrgElement orgElement) {
		orgElement.setFdId(typeConversion(0, struct, new String()));
		orgElement.setFdOrgType(typeConversion(1, struct, 0));
		orgElement.setFdName(typeConversion(2, struct, new String()));
		orgElement.setFdNamePinYin(typeConversion(3, struct, new String()));
		orgElement.setFdNameSimplePinyin(typeConversion(4, struct, new String()));
		orgElement.setFdOrder(typeConversion(5, struct, 0));
		orgElement.setFdNo(typeConversion(6, struct, new String()));
		orgElement.setFdKeyword(typeConversion(7, struct, new String()));
		orgElement.setFdIsAvailable(typeConversion(8, struct, false));
		orgElement.setFdIsAbandon(typeConversion(9, struct, false));
		orgElement.setFdIsBusiness(typeConversion(10, struct, false));
		orgElement.setFdImportInfo(typeConversion(11, struct, new String()));
		orgElement.setFdFlagDeleted(typeConversion(12, struct, false));
		orgElement.setFdLdapDN(typeConversion(13, struct, new String()));
		orgElement.setFdMemo(typeConversion(14, struct, new String()));
		orgElement.setFdHierarchyId(typeConversion(15, struct, new String()));
		orgElement.setFdCreateTime(typeConversion(16, struct, new Date()));
		orgElement.setFdAlterTime(typeConversion(17, struct, new Date()));
		orgElement.setFdOrgEmail(typeConversion(18, struct, new String()));
		orgElement.setFdPersonsNumber(typeConversion(19, struct, 0));
		orgElement.setFdPreDeptId(typeConversion(20, struct, new String()));
		orgElement.setFdPrePostIds(typeConversion(21, struct, new String()));
		orgElement.setFdIsExternal(typeConversion(22, struct, false));

		//fd_this_leaderid
		String fdThisLeaderid = typeConversion(23, struct, new String());
		if (null != fdThisLeaderid) {
			SysOrgElement fdThisLeader = new SysOrgElement();
			fdThisLeader.setFdId(fdThisLeaderid);
			orgElement.setHbmThisLeader(fdThisLeader);
		}

		//fd_super_leaderid
		String fdSuperLeaderid = typeConversion(24, struct, new String());
		if (null != fdSuperLeaderid) {
			SysOrgElement fdSuperLeader = new SysOrgElement();
			fdSuperLeader.setFdId(fdSuperLeaderid);
			orgElement.setHbmSuperLeader(fdSuperLeader);
		}

		//fd_parentorgid
		String fdParentOrgId = typeConversion(25, struct, new String());
		if (null != fdParentOrgId) {
			SysOrgElement parentOrg = new SysOrgElement();
			parentOrg.setFdId(fdParentOrgId);
			parentOrg.setHbmParentOrg(parentOrg);
		}

		//fd_parentid
		String fdParentId = typeConversion(26, struct, new String());
		if (null != fdParentId) {
			SysOrgElement parent = new SysOrgElement();
			parent.setFdId(fdParentId);
			orgElement.setHbmParent(parent);
		}

		//doc_creator_id
		String docCreatorId = typeConversion(27, struct, new String());
		if (null != docCreatorId) {
			SysOrgPerson docCreator = new SysOrgPerson();
			docCreator.setFdId(docCreatorId);
			orgElement.setDocCreator(docCreator);
		}
	}

	/**
	 * 数组转换成部门/机构
	 * 
	 * @param struct
	 * @param orgElement
	 */
	private SysOrgElement toOrgOrDept(Object[] struct, List<String> dynamicLangProps, List<String> dynamicCustomProps)
			throws Exception {
		SysOrgElement orgElement = new SysOrgElement();
		toOrgElement(struct, orgElement);

		int index = 28;
		Map<String, String> dynamicMap = new HashMap<String, String>();
		dynamicMap.put("fdName", orgElement.getFdName());
		for (int z = 0; z < dynamicLangProps.size(); z++) {
			String key = dynamicLangProps.get(z);
			index = index + 1;
			dynamicMap.put(key, typeConversion(index, struct, new String()));
		}
		orgElement.setDynamicMap(dynamicMap);
		return orgElement;
	}

	/**
	 * 数组转换成群组
	 * 
	 * @param struct
	 * @param orgElement
	 */
	private SysOrgGroup toGroup(Object[] struct, List<String> dynamicLangProps) throws Exception {
		SysOrgGroup sysOrgGroup = new SysOrgGroup();
		toOrgElement(struct, sysOrgGroup);

		//fd_cateid
		String fdCateid = typeConversion(28, struct, new String());
		if (null != fdCateid) {
			SysOrgGroupCate groupCate = new SysOrgGroupCate();
			groupCate.setFdId(fdCateid);
			sysOrgGroup.setFdGroupCate(groupCate);
		}

		sysOrgGroup.setAuthReaderFlag(typeConversion(29, struct, false));

		//添加动态属性字段
		int index = 30;
		Map<String, String> dynamicMap = new HashMap<String, String>();
		dynamicMap.put("fdName", sysOrgGroup.getFdName());
		for (int z = 0; z < dynamicLangProps.size(); z++) {
			String key = dynamicLangProps.get(z);
			index = index + 1;
			dynamicMap.put(key, typeConversion(index, struct, new String()));
		}

		sysOrgGroup.setDynamicMap(dynamicMap);
		return sysOrgGroup;
	}

	/**
	 * 数组转换成岗位
	 * 
	 * @param struct
	 * @param orgElement
	 */
	private SysOrgPost toPost(Object[] struct, List<String> dynamicLangProps) throws Exception {
		SysOrgPost orgPost = new SysOrgPost();
		toOrgElement(struct, orgPost);

		//添加动态属性字段
		int index = 28;
		Map<String, String> dynamicMap = new HashMap<String, String>();
		dynamicMap.put("fdName", orgPost.getFdName());
		for (int z = 0; z < dynamicLangProps.size(); z++) {
			String key = dynamicLangProps.get(z);
			index = index + 1;
			dynamicMap.put(key, typeConversion(index, struct, new String()));
		}

		orgPost.setDynamicMap(dynamicMap);
		return orgPost;
	}

	/**
	 * 数组转换成人员
	 * 
	 * @param struct
	 * @param orgElement
	 */
	private Object[] toPerson(Object[] struct, List<String> dynamicLangProps, List<String> dynamicCustomProps)
			throws Exception {
		Object[] returnObj = new Object[2];
		SysOrgPerson orgPerson = new SysOrgPerson();
		toOrgElement(struct, orgPerson);

		orgPerson.setFdMobileNo(typeConversion(28, struct, new String()));
		orgPerson.setFdEmail(typeConversion(29, struct, new String()));
		orgPerson.setFdLoginName(typeConversion(30, struct, new String()));
		orgPerson.setFdLoginNameLower(typeConversion(31, struct, new String()));
		orgPerson.setFdPassword(typeConversion(32, struct, new String()));
		orgPerson.setFdInitPassword(typeConversion(33, struct, new String()));
		orgPerson.setFdRtxNo(typeConversion(34, struct, new String()));
		orgPerson.setFdWechatNo(typeConversion(35, struct, new String()));
		orgPerson.setFdCardNo(typeConversion(36, struct, new String()));
		orgPerson.setFdAttendanceCardNumber(typeConversion(37, struct, new String()));
		orgPerson.setFdWorkPhone(typeConversion(38, struct, new String()));
		orgPerson.setFdDefaultLang(typeConversion(39, struct, new String()));
		orgPerson.setFdSex(typeConversion(40, struct, new String()));
		orgPerson.setFdLastChangePwd(typeConversion(41, struct, new Date()));
		orgPerson.setFdLockTime(typeConversion(42, struct, new Date()));
		orgPerson.setFdShortNo(typeConversion(43, struct, new String()));
		orgPerson.setFdDoubleValidation(typeConversion(44, struct, new String()));
		orgPerson.setFdUserType(typeConversion(45, struct, new String()));
		orgPerson.setFdNickName(typeConversion(46, struct, new String()));
		orgPerson.setFdIsActivated(typeConversion(47, struct, false));
		orgPerson.setFdHiredate(typeConversion(48, struct, new Date()));
		orgPerson.setFdLeaveDate(typeConversion(49, struct, new Date()));
		orgPerson.setFdCanLogin(typeConversion(50, struct, false));

		//fd_staffing_level_id
		String fdStaffingLevelId = typeConversion(51, struct, new String());
		if (null != fdStaffingLevelId) {
			SysOrganizationStaffingLevel fdStaffingLevel = new SysOrganizationStaffingLevel();
			fdStaffingLevel.setFdId(fdStaffingLevelId);
			orgPerson.setFdStaffingLevel(fdStaffingLevel);
		}

		//添加动态属性字段
		int index = 51;
		Map<String, String> dynamicMap = new HashMap<String, String>();
		dynamicMap.put("fdName", orgPerson.getFdName());
		for (int z = 0; z < dynamicLangProps.size(); z++) {
			String key = dynamicLangProps.get(z);
			index = index + 1;
			dynamicMap.put(key, typeConversion(index, struct, new String()));
		}

		orgPerson.setDynamicMap(dynamicMap);

		Map<String, String> customMap = new HashMap<String, String>();
		for (int z = 0; z < dynamicCustomProps.size(); z++) {
			index = index + 1;
			String key = dynamicCustomProps.get(z);
			customMap.put(key, typeConversion(index, struct, new String()));
		}

		returnObj[0] = orgPerson;
		returnObj[1] = customMap;

		return returnObj;

	}

	private StringBuilder setQueryOrgElementsWhere(SysSynchroGetOrgContext orgContext, Integer orgType, Boolean isExternal,
			List<Object> paramsList) {
		StringBuilder whereBlock = new StringBuilder();
		whereBlock.append(" 1=1 ");

		if (orgType != null) {
			whereBlock.append("AND orgelement.fd_org_type= ?");
			paramsList.add(orgType);
		}

		if (orgContext instanceof SysSynchroGetOrgBaseInfoContextV2) {
			SysSynchroGetOrgBaseInfoContextV2 v2 = (SysSynchroGetOrgBaseInfoContextV2) orgContext;
			Boolean isBusiness = v2.getIsBusiness();
			if (isBusiness != null) {
				whereBlock.append(" AND orgelement.fd_is_business = ?");
				paramsList.add(isBusiness);
			}
		} else if (orgContext instanceof SysSynchroGetOrgInfoContextV2) {
			SysSynchroGetOrgInfoContextV2 v2 = (SysSynchroGetOrgInfoContextV2) orgContext;
			Boolean isBusiness = v2.getIsBusiness();
			if (isBusiness != null) {
				whereBlock.append(" AND orgelement.fd_is_business = ?");
				paramsList.add(isBusiness);
			}
		} else {
			whereBlock.append(" AND orgelement.fd_is_business = ?");
			paramsList.add(Boolean.TRUE);
		}

		// 是否外部组织
		if (isExternal != null) {
			whereBlock.append(" AND orgelement.fd_is_external = ?");
			paramsList.add(isExternal);
		}

		if (null != orgContext.getBeginTimeStamp()) {
			whereBlock.append(" AND orgelement.fd_alter_time > ? ");
			paramsList.add(orgContext.getBeginTimeStamp());
		}

		if (null != orgContext.getEndTimeStamp()) {
			whereBlock.append(" AND orgelement.fd_alter_time < ? ");
			paramsList.add(orgContext.getEndTimeStamp());
		}
		return whereBlock;
	}

	private int queryOrgElementsCount(SysSynchroGetOrgContext orgContext, List<Integer> orgRtnType, Boolean isExternal)
			throws Exception {
		List<Object> paramsList = new ArrayList<Object>();
		StringBuilder whereBlock = setQueryOrgElementsWhere(orgContext, null, isExternal, paramsList);
		whereBlock.append(" and fd_org_type in(");
		StringBuilder whereBlockIn = new StringBuilder();
		for (Integer orgType : orgRtnType) {
			whereBlockIn.append(",");
			whereBlockIn.append(orgType);
		}

		if (null != whereBlockIn && whereBlockIn.length() > 1) {
			whereBlock.append(whereBlockIn.substring(1, whereBlockIn.toString().length()));
		}

		whereBlock.append(")");
		String sql = "select count(fd_id) from sys_org_element orgelement where " + whereBlock.toString();
		NativeQuery<?> query = getSysOrgElementService().getBaseDao().getHibernateSession().createNativeQuery(sql);
		for (int i = 0; i < paramsList.size(); i++) {
			Object param = paramsList.get(i);
			query.setParameter(i, param);
		}

		int count = Integer.valueOf(query.list().get(0).toString());
		return count;
	}

	/**
	 * 根据接口参数处理查询条件，返回查询数据
	 * 
	 * @param orgContext
	 * @param orgType
	 * @param dynamicLangProps
	 * @param dynamicCustomProps
	 * @param isExternal
	 * @return
	 * @throws Exception
	 */
	private List<?> queryOrgElements(SysSynchroGetOrgContext orgContext, boolean isGetBase, Integer orgType,
			List<String> dynamicLangProps, List<String> dynamicCustomProps, List<String> orgRtnInfo, Boolean isExternal)
			throws Exception {
		String sql = null;
		StringBuilder dynamicPropertyString = new StringBuilder();
		StringBuilder slectBlock = new StringBuilder();

		if (isGetBase) {
			slectBlock.append("orgelement.fd_id,");
			slectBlock.append("orgelement.fd_name,");
			slectBlock.append("orgelement.fd_org_type,");
			slectBlock.append("orgelement.fd_is_external");
			if (orgRtnInfo != null && !orgRtnInfo.isEmpty()) {
				for (int i = 0; i < orgRtnInfo.size(); i++) {
					String orgInfo = orgRtnInfo.get(i);
					if (null != orgInfo && orgInfo.length() > 1) {
						orgInfo = "orgelement.fd_" + orgInfo.substring(0, 1).toLowerCase() + orgInfo.substring(1);
					}
					slectBlock.append("," + orgInfo);
				}
			}

		} else {
			slectBlock.append("orgelement.fd_id,");
			slectBlock.append("orgelement.fd_org_type,");
			slectBlock.append("orgelement.fd_name,");
			slectBlock.append("orgelement.fd_name_pinyin,");
			slectBlock.append("orgelement.fd_name_simple_pinyin,");
			slectBlock.append("orgelement.fd_order,");
			slectBlock.append("orgelement.fd_no,");
			slectBlock.append("orgelement.fd_keyword,");
			slectBlock.append("orgelement.fd_is_available,");
			slectBlock.append("orgelement.fd_is_abandon,");
			slectBlock.append("orgelement.fd_is_business,");
			slectBlock.append("orgelement.fd_import_info,");
			slectBlock.append("orgelement.fd_flag_deleted,");
			slectBlock.append("orgelement.fd_ldap_dn,");
			slectBlock.append("orgelement.fd_memo,");
			slectBlock.append("orgelement.fd_hierarchy_id,");
			slectBlock.append("orgelement.fd_create_time,");
			slectBlock.append("orgelement.fd_alter_time,");
			slectBlock.append("orgelement.fd_org_email,");
			slectBlock.append("orgelement.fd_persons_number,");
			slectBlock.append("orgelement.fd_pre_dept_id,");
			slectBlock.append("orgelement.fd_pre_post_ids,");
			slectBlock.append("orgelement.fd_is_external,");
			slectBlock.append("orgelement.fd_this_leaderid,");
			slectBlock.append("orgelement.fd_super_leaderid,");
			slectBlock.append("orgelement.fd_parentorgid,");
			slectBlock.append("orgelement.fd_parentid,");
			slectBlock.append("orgelement.doc_creator_id");
		}

		List<Object> paramsList = new ArrayList<Object>();
		StringBuilder whereBlock = setQueryOrgElementsWhere(orgContext, orgType, isExternal, paramsList);

		if (isGetBase) {
			sql = "SELECT " + slectBlock.toString() + " FROM sys_org_element orgelement " + "WHERE " + whereBlock.toString()
					+ " order by orgelement.fd_alter_time ASC";
		} else {

			if (orgType == ORG_TYPE_ORG || orgType == ORG_TYPE_DEPT) {
				/* 机构/部门同步 */
				SysDictModel orgElementDictModel = SysDataDict.getInstance().getModel(SysOrgElement.class.getCanonicalName());

				//获取多语言字段
				getDynamicLangColumn(dynamicPropertyString, dynamicLangProps, orgElementDictModel, "orgelement");

				sql = "SELECT " + slectBlock.toString() + dynamicPropertyString.toString() + " FROM sys_org_element orgelement "
						+ "WHERE " + whereBlock.toString() + " order by orgelement.fd_alter_time ASC";

			} else if (orgType == ORG_TYPE_GROUP) {
				/* 群组同步  */
				SysDictModel orgElementDictModel = SysDataDict.getInstance().getModel(SysOrgGroup.class.getCanonicalName());

				//获取多语言字段
				getDynamicLangColumn(dynamicPropertyString, dynamicLangProps, orgElementDictModel, "orgelement");

				sql = "SELECT " + slectBlock.toString() + ", orgelement.fd_cateid, orgelement.auth_reader_flag"
						+ dynamicPropertyString.toString() + " FROM sys_org_element orgelement " + "WHERE "
						+ whereBlock.toString() + " order by orgelement.fd_alter_time ASC";

			} else if (orgType == ORG_TYPE_POST) {
				/* 岗位同步 */
				SysDictModel orgElementDictModel = SysDataDict.getInstance().getModel(SysOrgPost.class.getCanonicalName());

				//获取多语言字段
				getDynamicLangColumn(dynamicPropertyString, dynamicLangProps, orgElementDictModel, "orgelement");

				sql = "SELECT " + slectBlock.toString() + dynamicPropertyString.toString() + " FROM sys_org_element orgelement "
						+ "WHERE " + whereBlock.toString() + " order by orgelement.fd_alter_time ASC";

			} else if (orgType == ORG_TYPE_PERSON) {

				/* 个人同步 */
				SysDictModel orgElementDictModel = SysDataDict.getInstance().getModel(SysOrgElement.class.getCanonicalName());
				SysDictModel orgPersonDictModel = SysDataDict.getInstance().getModel(SysOrgPerson.class.getCanonicalName());

				//获取多语言字段
				getDynamicLangColumn(dynamicPropertyString, dynamicLangProps, orgElementDictModel, "orgelement");
				getDynamicLangColumn(dynamicPropertyString, dynamicLangProps, orgPersonDictModel, "person");

				//获取定制动态字段
				getDynamicCustomColumn(dynamicPropertyString, dynamicCustomProps, orgElementDictModel, "orgelement");
				getDynamicCustomColumn(dynamicPropertyString, dynamicCustomProps, orgPersonDictModel, "person");

				sql = "SELECT " + slectBlock.toString()
						+ ", person.fd_mobile_no, person.fd_email, person.fd_login_name, person.fd_login_name_lower, person.fd_password, person.fd_init_password, person.fd_rtx_no, person.fd_wechat_no, person.fd_card_no, person.fd_attendance_card_number, person.fd_work_phone, person.fd_default_lang, person.fd_sex, person.fd_last_change_pwd, person.fd_lock_time, person.fd_short_no, person.fd_double_validation, person.fd_user_type, person.fd_nick_name, person.fd_is_activated, person.fd_hiredate, person.fd_leave_date, person.fd_can_login, person.fd_staffing_level_id"
						+ dynamicPropertyString.toString() + " FROM "
						+ "sys_org_element orgelement INNER JOIN sys_org_person person ON orgelement.fd_id= person.fd_id "
						+ "WHERE " + whereBlock.toString() + " order by orgelement.fd_alter_time ASC";

			}

		}

		NativeQuery<?> query = getSysOrgElementService().getBaseDao().getHibernateSession().createNativeQuery(sql);
		for (int i = 0; i < paramsList.size(); i++) {
			Object param = paramsList.get(i);
			query.setParameter(i, param);
		}

		List<?> elementList = query.list();

		return elementList;
	}

	@SuppressWarnings("unchecked")
	private SysOrgElement queryOrgOrDept(SysSynchroGetOrgInfoContext orgContext, IntegerWrapper count, int index,
			JSONArray jsonArr, Map<String, SysOrgElement> orgElementMap, List<String> dynamicLangProps,
			List<String> dynamicCustomProps, Object[] struct, int structLength) throws Exception {

		SysOrgElement element = toOrgOrDept(struct, dynamicLangProps, dynamicCustomProps);
		orgElementMap.put(element.getFdId(), element);

		//批量数量默认为500一批查询
		int batchCount = 1000;
		boolean isSubmit = isBatchSubmit(orgContext.getCount(), structLength, batchCount, orgElementMap.size(), index);

		if (isSubmit) {
			StringBuilder fdIdString = new StringBuilder();
			orgElementMap.forEach((key, value) -> {
				fdIdString.append(",");
				fdIdString.append("'");
				fdIdString.append(key);
				fdIdString.append("'");
			});

			String fdIds = fdIdString.toString();
			if (null != fdIds && fdIds.length() > 1) {
				fdIds = fdIds.substring(1, fdIds.length());
			}

			String relationSql = "SELECT fd_element_id,fd_admin_id FROM sys_org_element_admins WHERE fd_element_id IN(" + fdIds
					+ ")";
			Map<String, List<String>> authElementAdminsMap = getRelationData(relationSql);

			relationSql = "SELECT fd_rangeid,fd_otherid FROM sys_org_element_range_other WHERE fd_rangeid IN(" + fdIds + ")";
			Map<String, List<String>> orgElementRangeOtherMap = getRelationData(relationSql);

			relationSql = "SELECT fd_hideid,fd_otherid FROM sys_org_element_hide_other WHERE fd_hideid IN(" + fdIds + ")";
			Map<String, List<String>> orgElementHideRangeOtherMap = getRelationData(relationSql);

			String rangeSql = "SELECT fd_id,fd_is_open_limit,fd_view_type,fd_view_sub_type,fd_invite_url,fd_element_id FROM sys_org_element_range WHERE fd_element_id IN("
					+ fdIds + ")";
			List<?> rangeList = getSysOrgElementService().getBaseDao().getHibernateSession().createNativeQuery(rangeSql).list();
			for (Object obj : rangeList) {
				Object[] relation = (Object[]) obj;
				SysOrgElementRange range = new SysOrgElementRange();
				range.setFdId(typeConversion(0, relation, new String()));
				range.setFdIsOpenLimit(typeConversion(1, relation, false));
				range.setFdViewType(typeConversion(2, relation, 0));
				range.setFdViewSubType(typeConversion(3, relation, new String()));
				range.setFdInviteUrl(typeConversion(4, relation, new String()));
				SysOrgElement fdElement = new SysOrgElement();
				String elementId = typeConversion(5, relation, new String());
				fdElement.setFdId(elementId);
				range.setFdElement(fdElement);

				List<SysOrgElement> fdOthers = new ArrayList<SysOrgElement>();
				List<String> idsList = orgElementRangeOtherMap.get(typeConversion(0, relation, new String()));
				if (null != idsList) {
					for (String fdId : idsList) {
						SysOrgElement orgElment = new SysOrgElement();
						orgElment.setFdId(fdId);
						fdOthers.add(orgElment);
					}
					range.setFdOthers(fdOthers);
				}

				SysOrgElement tempElement = orgElementMap.get(elementId);
				tempElement.setHbmRange(range);
			}

			String hibeSql = "SELECT fd_id,fd_is_open_limit,fd_view_type,fd_element_id FROM sys_org_element_hide_range WHERE fd_element_id IN("
					+ fdIds + ")";
			List<?> hibeList = getSysOrgElementService().getBaseDao().getHibernateSession().createNativeQuery(hibeSql).list();
			for (Object obj : hibeList) {
				Object[] relation = (Object[]) obj;
				SysOrgElementHideRange hide = new SysOrgElementHideRange();
				hide.setFdId(typeConversion(0, relation, new String()));
				hide.setFdIsOpenLimit(typeConversion(1, relation, false));
				hide.setFdViewType(typeConversion(2, relation, 0));
				SysOrgElement fdElement = new SysOrgElement();
				String elementId = typeConversion(3, relation, new String());
				fdElement.setFdId(elementId);
				hide.setFdElement(fdElement);

				List<SysOrgElement> fdOthers = new ArrayList<SysOrgElement>();
				List<String> idsList = orgElementHideRangeOtherMap.get(typeConversion(0, relation, new String()));
				if (null != idsList) {
					for (String fdId : idsList) {
						SysOrgElement orgElment = new SysOrgElement();
						orgElment.setFdId(fdId);
						fdOthers.add(orgElment);
					}
					hide.setFdOthers(fdOthers);
				}

				SysOrgElement tempElement = orgElementMap.get(elementId);
				tempElement.setHbmHideRange(hide);
			}

			int currentCount = 0;
			for (Entry<String, SysOrgElement> entry : orgElementMap.entrySet()) {
				JSONObject jsonObj = new JSONObject();
				SysOrgElement value = entry.getValue();

				List<SysOrgElement> authElementAdmins = new ArrayList<SysOrgElement>();
				List<String> adminList = authElementAdminsMap.get(value.getFdId());
				if (null != adminList) {
					for (String id : adminList) {
						SysOrgElement admin = new SysOrgElement();
						admin.setFdId(id);
						authElementAdmins.add(admin);
					}
					value.setAuthElementAdmins(authElementAdmins);
				}

				try {
					setOrgBaseInfo(jsonObj, value);
					setRelationInfo(jsonObj, value, value.getFdOrgType(), false);
					jsonArr.add(jsonObj);
					currentCount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			count.setCount(currentCount);
			// 清除临时对象
			orgElementMap.clear();
		}

		return element;
	}

	@SuppressWarnings("unchecked")
	private SysOrgElement queryOrgGroup(SysSynchroGetOrgInfoContext orgContext, IntegerWrapper count, int index,
			JSONArray jsonArr, Map<String, SysOrgElement> orgElementMap, List<String> dynamicLangProps,
			List<String> dynamicCustomProps, Object[] struct, int structLength) throws Exception {

		SysOrgElement element = toGroup(struct, dynamicLangProps);
		orgElementMap.put(element.getFdId(), element);

		//批量数量默认为200一批查询
		int batchCount = 1000;
		boolean isSubmit = isBatchSubmit(orgContext.getCount(), structLength, batchCount, orgElementMap.size(), index);

		if (isSubmit) {
			StringBuilder fdIdString = new StringBuilder();
			orgElementMap.forEach((key, value) -> {
				fdIdString.append(",");
				fdIdString.append("'");
				fdIdString.append(key);
				fdIdString.append("'");
			});

			String fdIds = fdIdString.toString();
			if (null != fdIds && fdIds.length() > 1) {
				fdIds = fdIds.substring(1, fdIds.length());
			}

			String relationSql = "SELECT fd_groupid,fd_elementid FROM sys_org_group_element WHERE fd_groupid IN(" + fdIds + ")";
			Map<String, List<String>> groupMembersMap = getRelationData(relationSql);

			relationSql = "SELECT fd_group_id,auth_reader_id FROM sys_org_group_reader WHERE fd_group_id IN(" + fdIds + ")";
			Map<String, List<String>> groupAuthReadersMap = getRelationData(relationSql);

			relationSql = "SELECT fd_group_id,auth_editor_id FROM sys_org_group_editor WHERE fd_group_id IN(" + fdIds + ")";
			Map<String, List<String>> groupAuthEditorsMap = new HashMap<String, List<String>>();

			int currentCount = 0;
			for (Entry<String, SysOrgElement> entry : orgElementMap.entrySet()) {
				JSONObject jsonObj = new JSONObject();
				SysOrgElement value = entry.getValue();
				if (value instanceof SysOrgGroup) {
					SysOrgGroup sysOrgGroup = (SysOrgGroup) value;

					// 群组成员
					List<SysOrgElement> hbmMembers = new ArrayList<SysOrgElement>();
					List<String> membersList = groupMembersMap.get(sysOrgGroup.getFdId());
					if (null != membersList) {
						for (String id : membersList) {
							SysOrgElement member = new SysOrgElement();
							member.setFdId(id);
							hbmMembers.add(member);
						}
						sysOrgGroup.setHbmMembers(hbmMembers);
					}

					//阅读权限
					List<SysOrgElement> authReaders = new ArrayList<SysOrgElement>();
					List<String> readerList = groupAuthReadersMap.get(sysOrgGroup.getFdId());
					if (null != readerList) {
						for (String id : readerList) {
							SysOrgElement reader = new SysOrgElement();
							reader.setFdId(id);
							authReaders.add(reader);
						}
						sysOrgGroup.setAuthReaders(authReaders);
					}

					// 编辑权限
					List<SysOrgElement> authEditors = new ArrayList<SysOrgElement>();
					List<String> editorList = groupAuthEditorsMap.get(sysOrgGroup.getFdId());
					if (null != editorList) {
						for (String id : editorList) {
							SysOrgElement editor = new SysOrgElement();
							editor.setFdId(id);
							authEditors.add(editor);
						}
						sysOrgGroup.setAuthEditors(authEditors);
					}

					try {
						setOrgBaseInfo(jsonObj, value);
						setRelationInfo(jsonObj, value, value.getFdOrgType(), false);
						jsonArr.add(jsonObj);
						currentCount++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			count.setCount(currentCount);
			// 清除临时对象
			orgElementMap.clear();
		}

		return element;

	}

	@SuppressWarnings("unchecked")
	private SysOrgElement queryOrgPost(SysSynchroGetOrgInfoContext orgContext, IntegerWrapper count, int index,
			JSONArray jsonArr, Map<String, SysOrgElement> orgElementMap, List<String> dynamicLangProps,
			List<String> dynamicCustomProps, Object[] struct, int structLength) throws Exception {
		SysOrgElement element = toPost(struct, dynamicLangProps);
		orgElementMap.put(element.getFdId(), element);

		//批量数量默认为200一批查询
		int batchCount = 1000;
		boolean isSubmit = isBatchSubmit(orgContext.getCount(), structLength, batchCount, orgElementMap.size(), index);

		if (isSubmit) {
			StringBuilder fdIdString = new StringBuilder();
			orgElementMap.forEach((key, value) -> {
				fdIdString.append(",");
				fdIdString.append("'");
				fdIdString.append(key);
				fdIdString.append("'");
			});

			String fdIds = fdIdString.toString();
			if (null != fdIds && fdIds.length() > 1) {
				fdIds = fdIds.substring(1, fdIds.length());
			}

			String relationSql = "SELECT fd_postid,fd_personid FROM sys_org_post_person WHERE fd_postid IN(" + fdIds + ")";
			Map<String, List<String>> groupPersonsMap = getRelationData(relationSql);

			int currentCount = 0;
			for (Entry<String, SysOrgElement> entry : orgElementMap.entrySet()) {
				JSONObject jsonObj = new JSONObject();
				SysOrgElement value = entry.getValue();
				if (value instanceof SysOrgPost) {
					SysOrgPost post = (SysOrgPost) value;
					List<SysOrgPerson> persons = new ArrayList<SysOrgPerson>();
					List<String> personsList = groupPersonsMap.get(post.getFdId());
					if (null != personsList) {
						for (String id : personsList) {
							SysOrgPerson person = new SysOrgPerson();
							person.setFdId(id);
							persons.add(person);
						}
						post.setHbmPersons(persons);
					}
				}

				try {
					setOrgBaseInfo(jsonObj, value);
					setRelationInfo(jsonObj, value, value.getFdOrgType(), false);
					jsonArr.add(jsonObj);
					currentCount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			count.setCount(currentCount);

			// 清除临时对象
			orgElementMap.clear();
		}
		return element;

	}

	@SuppressWarnings("unchecked")
	private SysOrgElement queryOrgPerson(SysSynchroGetOrgInfoContext orgContext, IntegerWrapper count, int index,
			JSONArray jsonArr, Map<String, SysOrgElement> orgElementMap, List<String> dynamicLangProps,
			List<String> dynamicCustomProps, Object[] struct, int structLength,
			SysOrganizationStaffingLevel defaultStaffingLevel) throws Exception {
		Object[] returnObj = toPerson(struct, dynamicLangProps, dynamicCustomProps);
		SysOrgElement element = (SysOrgPerson) returnObj[0];
		orgElementMap.put(element.getFdId(), element);

		//批量数量默认为500一批查询
		int batchCount = 1000;
		boolean isSubmit = isBatchSubmit(orgContext.getCount(), structLength, batchCount, orgElementMap.size(), index);

		if (isSubmit) {

			StringBuilder fdIdString = new StringBuilder();
			orgElementMap.forEach((key, value) -> {
				fdIdString.append(",");
				fdIdString.append("'");
				fdIdString.append(key);
				fdIdString.append("'");
			});

			String fdIds = fdIdString.toString();
			if (null != fdIds && fdIds.length() > 1) {
				fdIds = fdIds.substring(1, fdIds.length());
			}

			String relationSql = "SELECT fd_personid,fd_postid FROM sys_org_post_person WHERE fd_personid IN(" + fdIds + ")";
			Map<String, List<String>> personPostsMap = getRelationData(relationSql);

			Map<String, SysOrganizationStaffingLevel> staffingLevelMap = new HashMap<String, SysOrganizationStaffingLevel>();
			relationSql = "SELECT fd_id,fd_name,fd_level FROM sys_org_staffing_level WHERE fd_id IN(" + fdIds + ")";
			List<?> staffingLevelList = getSysOrgElementService().getBaseDao().getHibernateSession()
					.createNativeQuery(relationSql).list();

			for (Object obj : staffingLevelList) {
				Object[] relation = (Object[]) obj;
				SysOrganizationStaffingLevel staffingLevel = new SysOrganizationStaffingLevel();
				staffingLevel.setFdId(typeConversion(0, relation, new String()));
				staffingLevel.setFdName(typeConversion(1, relation, new String()));
				staffingLevel.setFdLevel(typeConversion(2, relation, 0));

				Map<String, String> dynamicMap = new HashMap<String, String>();
				dynamicMap.put("fdName", staffingLevel.getFdName());
				for (int z = 0; z < dynamicLangProps.size(); z++) {
					String key = dynamicLangProps.get(z);
					index = index + 1;
					dynamicMap.put(key, typeConversion(index, struct, new String()));
				}

				staffingLevel.setDynamicMap(dynamicMap);
				staffingLevelMap.put(staffingLevel.getFdId(), staffingLevel);
			}

			int currentCount = 0;
			for (Entry<String, SysOrgElement> entry : orgElementMap.entrySet()) {
				JSONObject jsonObj = new JSONObject();
				SysOrgElement value = entry.getValue();
				if (value instanceof SysOrgPerson) {
					SysOrgPerson person = (SysOrgPerson) value;

					//岗位
					List<SysOrgElement> posts = new ArrayList<SysOrgElement>();
					List<String> postsList = personPostsMap.get(person.getFdId());
					if (null != postsList) {
						for (String id : postsList) {
							SysOrgElement post = new SysOrgElement();
							post.setFdId(id);
							posts.add(post);
						}
						person.setHbmPosts(posts);
					}

					//职级
					if (null != person.getFdStaffingLevel()) {
						SysOrganizationStaffingLevel staffingLevel = staffingLevelMap
								.get(person.getFdStaffingLevel().getFdId());
						person.setFdStaffingLevel(staffingLevel);
					} else {
						person.setFdStaffingLevel(defaultStaffingLevel);
					}

					try {
						setOrgBaseInfo(jsonObj, value);
						setRelationInfo(jsonObj, value, value.getFdOrgType(), false);
						// 自定义属性,覆盖 setRelationInfo 设置的customProps
						jsonObj.put("customProps", (Map<?, ?>) returnObj[1]);
						jsonArr.add(jsonObj);
						currentCount++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			count.setCount(currentCount);
			// 清除临时对象
			orgElementMap.clear();
		}
		return element;

	}

	private SysSynchroOrgResult getElementCount(SysSynchroGetOrgContext orgContext, Boolean isExternal) throws Exception {
		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		List<Integer> orgRtnType = new ArrayList<Integer>();
		List<String> orgRtnInfo = new ArrayList<String>();
		if (orgContext != null) {
			if (!checkNullIfNecessary(orgContext, orgResult, orgRtnType, orgRtnInfo)) {
				return orgResult;
			}
		}

		int total = 0;

		if (orgRtnType.isEmpty()) {
			orgRtnType.add(ORG_TYPE_ORG);
			orgRtnType.add(ORG_TYPE_DEPT);
			orgRtnType.add(ORG_TYPE_POST);
			orgRtnType.add(ORG_TYPE_GROUP);
			orgRtnType.add(ORG_TYPE_PERSON);
		}

		total = queryOrgElementsCount(orgContext, orgRtnType, isExternal);
		//orgResult.setTotal(total);
		return orgResult;
	}

	/**
	 * 针对 getUpdatedElements 接口做优化
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private SysSynchroOrgResult getElementsBaseInfo(SysSynchroGetOrgBaseInfoContext orgContext, Boolean isExternal,
			Boolean isEco) throws Exception {

		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		List<Integer> orgRtnType = new ArrayList<Integer>();
		List<String> orgRtnInfo = new ArrayList<String>();
		if (orgContext != null) {
			if (!checkNullIfNecessary(orgContext, orgResult, orgRtnType, orgRtnInfo)) {
				return orgResult;
			}
		}

		//最大只能取1万
		if (10000 > orgContext.getCount()) {
			orgContext.setCount(10000);
		}

		logger.info("开始读取所有组织架构基本信息.");
		int count = 0;
		JSONArray jsonArr = new JSONArray();
		if (orgRtnType.isEmpty()) {
			orgRtnType.add(ORG_TYPE_ORG);
			orgRtnType.add(ORG_TYPE_DEPT);
			orgRtnType.add(ORG_TYPE_POST);
			orgRtnType.add(ORG_TYPE_GROUP);
			orgRtnType.add(ORG_TYPE_PERSON);
		}

		for (Integer orgType : orgRtnType) {

			List<String> dynamicLangProps = new ArrayList<String>();
			List<String> dynamicCustomProps = new ArrayList<String>();
			List<?> elementList = queryOrgElements(orgContext, true, orgType, dynamicLangProps, dynamicCustomProps, orgRtnInfo,
					isExternal);
			logger.info("根据查询条件 beginTimeStamp:{},endTimeStamp:{},returnOrgType:{} 查询总数:{}", orgContext.getBeginTimeStamp(),
					orgContext.getEndTimeStamp(), getOrgType(orgType), elementList.size());

			for (int i = 0; i < elementList.size(); i++) {
				JSONObject jsonObj = new JSONObject();
				Object[] baseInfo = (Object[]) elementList.get(i);
				jsonObj.put(ID, String.valueOf(baseInfo[0]));
				jsonObj.put(LUNID, String.valueOf(baseInfo[0]));
				jsonObj.put(NAME, String.valueOf(baseInfo[1]));
				jsonObj.put(TYPE, getOrgType((Integer) baseInfo[2]));
				if (isEco) {
					jsonObj.put(IS_EXTERNAL, baseInfo[3]); //是否外部元素
				}
				if (orgRtnInfo != null && !orgRtnInfo.isEmpty()) {
					for (int j = 0; j < orgRtnInfo.size(); j++) {
						if (baseInfo[4 + j] != null) {
                            jsonObj.put(orgRtnInfo.get(j), String.valueOf(baseInfo[4 + j]));
                        }
					}
				}

				jsonArr.add(jsonObj);
				count++;
			}
		}

		orgResult.setCount(count);
		logger.info("返回时间戳为：" + orgResult.getTimeStamp());
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.info("组织架构基本信息读取结束.");

		return orgResult;
	}

	/**
	 * 针对 getUpdatedElements 接口做优化
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	private SysSynchroOrgResult getUpdatedElements(SysSynchroGetOrgInfoContext orgContext, Boolean isExternal)
			throws Exception {

		//最大只能取1万
		if (10000 > orgContext.getCount()) {
			orgContext.setCount(10000);
		}

		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		List<Integer> orgRtnType = new ArrayList<Integer>();
		List<String> orgRtnInfo = new ArrayList<String>();
		if (orgContext != null) {
			if (!checkNullIfNecessary(orgContext, orgResult, orgRtnType, orgRtnInfo)) {
				return orgResult;
			}
		}

		logger.info("开始读取需要更新的组织架构信息.");
		int count = 0;
		int total = 0;
		int limit = orgContext.getCount();
		Date latestAlertTime = null;
		boolean exitQuery = false;
		JSONArray jsonArr = new JSONArray();

		if (orgRtnType.isEmpty()) {
			orgRtnType.add(ORG_TYPE_ORG);
			orgRtnType.add(ORG_TYPE_DEPT);
			orgRtnType.add(ORG_TYPE_POST);
			orgRtnType.add(ORG_TYPE_GROUP);
			orgRtnType.add(ORG_TYPE_PERSON);
		}

		//total = queryOrgElementsCount(orgContext, orgRtnType, false, null, null, null, isExternal);
		SysOrganizationStaffingLevel defaultStaffingLevel = getSysOrganizationStaffingLevelService().getDefaultStaffingLevel();

		for (Integer orgType : orgRtnType) {

			List<String> dynamicLangProps = new ArrayList<String>();
			List<String> dynamicCustomProps = new ArrayList<String>();
			List<?> elementList = queryOrgElements(orgContext, false, orgType, dynamicLangProps, dynamicCustomProps, orgRtnInfo,
					isExternal);

			logger.info("根据查询条件 beginTimeStamp:{},endTimeStamp:{},returnOrgType:{} 查询总数:{}", orgContext.getBeginTimeStamp(),
					orgContext.getEndTimeStamp(), getOrgType(orgType), elementList.size());
			Map<String, SysOrgElement> orgElementMap = new LinkedHashMap<String, SysOrgElement>();
			IntegerWrapper currentCount = new IntegerWrapper();

			for (int i = 0; i < elementList.size(); i++) {
				Object[] struct = (Object[]) elementList.get(i);

				SysOrgElement element = null;
				if (orgType == ORG_TYPE_ORG || orgType == ORG_TYPE_DEPT) {
					// 传出机构/部门数据
					element = queryOrgOrDept(orgContext, currentCount, i, jsonArr, orgElementMap, dynamicLangProps,
							dynamicCustomProps, struct, elementList.size());

				} else if (orgType == ORG_TYPE_GROUP) {
					// 传出群组数据
					element = queryOrgGroup(orgContext, currentCount, i, jsonArr, orgElementMap, dynamicLangProps,
							dynamicCustomProps, struct, elementList.size());

				} else if (orgType == ORG_TYPE_POST) {
					// 传出岗位数据
					element = queryOrgPost(orgContext, currentCount, i, jsonArr, orgElementMap, dynamicLangProps,
							dynamicCustomProps, struct, elementList.size());

				} else if (orgType == ORG_TYPE_PERSON) {
					// 传出个人数据
					element = queryOrgPerson(orgContext, currentCount, i, jsonArr, orgElementMap, dynamicLangProps,
							dynamicCustomProps, struct, elementList.size(), defaultStaffingLevel);
				}

				//总数累积
				if (0 != currentCount.getCount()) {
					count = count + currentCount.getCount();
					orgContext.setCount(orgContext.getCount() - currentCount.getCount());
					currentCount.setCount(0);
				}

				latestAlertTime = element.getFdAlterTime();

				if (count >= limit) {
					exitQuery = true;
					break;
				}
			}

			if (exitQuery) {
				break;
			}
		}

		if (orgContext == null || count == 0) {
			orgResult.setTimeStamp(orgContext.getBeginTimeStamp());
		} else {
			orgResult.setTimeStamp(timeConvert(latestAlertTime));
		}

		orgResult.setCount(count);
		//orgResult.setTotal(total);
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);

		logger.info("返回时间戳为:{}", orgResult.getTimeStamp());
		logger.info("组织架构更新信息读取结束.");

		return orgResult;
	}

	/**
	 * 从v16.0.5版本开始存在该接口
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = "/getUpdatedElements", method = RequestMethod.POST)
	public SysSynchroOrgResult getUpdatedElements(@RequestBody SysSynchroGetOrgInfoContext orgContext) throws Exception {
		return getUpdatedElements(orgContext, false);
	}

	/**
	 * 从v16.0.5版本开始存在该接口
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = "/getUpdatedElementsCount", method = RequestMethod.POST)
	public SysSynchroOrgResult getUpdatedElementsCount(@RequestBody SysSynchroGetOrgInfoContext orgContext) throws Exception {
		return getElementCount(orgContext, false);
	}

	/**
	 * 从v16.0.5版本开始存在该接口
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = "/getUpdatedElementsForEco", method = RequestMethod.POST)
	public SysSynchroOrgResult getUpdatedElementsForEco(@RequestBody SysSynchroGetOrgInfoContext orgContext) throws Exception {
		return getUpdatedElements(orgContext, true);
	}

	/**
	 * 从v16.0.5版本开始存在该接口
	 * 获取组织架构所有基本信息
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = "/getElementsBaseInfo", method = RequestMethod.POST)
	public SysSynchroOrgResult getElementsBaseInfo(@RequestBody SysSynchroGetOrgBaseInfoContext orgContext) throws Exception {
		return getElementsBaseInfo(orgContext, false, false);
	}

	/**
	 * 从v16.0.5版本开始存在该接口
	 * 获取外部组织架构所有基本信息
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = "/getElementsBaseInfoForEco", method = RequestMethod.GET)
	public SysSynchroOrgResult getElementsBaseInfoForEco() throws Exception {
		SysSynchroGetOrgBaseInfoContext orgContext = new SysSynchroGetOrgBaseInfoContext();
		orgContext.setReturnOrgType("");
		orgContext.setReturnType("");
		return getElementsBaseInfo(orgContext, false, true);
	}
}
