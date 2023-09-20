package com.landray.kmss.sys.organization.webservice.out;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementExtProp;
import com.landray.kmss.sys.organization.model.SysOrgElementExtPropEnum;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.sys.organization.model.SysOrgElementHideRange;
import com.landray.kmss.sys.organization.model.SysOrgElementRange;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgGroupCate;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.model.SysOrgRoleConfCate;
import com.landray.kmss.sys.organization.model.SysOrgRoleLine;
import com.landray.kmss.sys.organization.model.SysOrgRoleLineDefaultRole;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupCateService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleConfCateService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleConfService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleLineDefaultRoleService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleLineService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.webservice.SysOrgWebserviceConstant;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsOrgService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping(value = "/api/sys-organization/sysSynchroGetOrg", method = RequestMethod.POST)
@RestApi(docUrl = "/sys/organization/rest/sysOrg_out_rest_help.jsp", name = "sysSynchroGetOrgWebService", resourceKey = "sys-organization:sysSynchroGetOrg.title")
public class SysSynchroGetOrgWebServiceImp implements
		ISysSynchroGetOrgWebService, SysOrgWebserviceConstant, SysOrgConstant {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysSynchroGetOrgWebServiceImp.class);

	private ISysOrgElementService sysOrgElementService;

	private ISysOrgCoreService sysOrgCoreService;

	private ISysOrgElementExternalService sysOrgElementExternalService;

	private static Map<String, SysSynchroOrgCache> sysSynchroOrgCacheMap = new ConcurrentHashMap<String, SysSynchroOrgCache>();

	private static Timer timer = new Timer();

	static {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				List<String> expiredCaches = new ArrayList<String>();
				long current = new Date().getTime();
				for (String key : sysSynchroOrgCacheMap.keySet()) {
					SysSynchroOrgCache cache = sysSynchroOrgCacheMap.get(key);
					if (cache.getExpirationTime() < current) {
						expiredCaches.add(key);
					}
				}
				for (String key : expiredCaches) {
					sysSynchroOrgCacheMap.remove(key);
				}
			}
		}, 7200000L, 7200000L);
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}
	
	public ISysOrgElementService getSysOrgElementService() {
		return this.sysOrgElementService;
	}

	public static void clearOrgCache() {
		sysSynchroOrgCacheMap.clear();
	}

	public void setSysOrgElementExternalService(
			ISysOrgElementExternalService sysOrgElementExternalService) {
		this.sysOrgElementExternalService = sysOrgElementExternalService;
	}

	/**
	 * 获取组织架构所有基本信息
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = "/getElementsBaseInfo", method = RequestMethod.POST)
	public SysSynchroOrgResult getElementsBaseInfo(
			@RequestBody SysSynchroGetOrgBaseInfoContext orgContext)
			throws Exception {

		List<Integer> orgRtnType = new ArrayList<Integer>();
		List<String> orgRtnInfo = new ArrayList<String>();

		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		if (orgContext != null) {
			if (!checkNullIfNecessary(orgContext, orgResult, orgRtnType,
					orgRtnInfo)) {
				return orgResult;
			}
		}
		logger.debug("开始读取所有组织架构基本信息。");
		List baseInfoList = sysOrgElementService.findValue(getHqlByOrgContext(
				orgContext, true, orgRtnType, orgRtnInfo, false));
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("所有组织架构共计" + baseInfoList.size() + "条。");
		for (int i = 0; i < baseInfoList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			Object[] baseInfo = (Object[]) baseInfoList.get(i);
			jsonObj.put(ID, String.valueOf(baseInfo[0]));
			jsonObj.put(LUNID, String.valueOf(baseInfo[0]));
			jsonObj.put(NAME, String.valueOf(baseInfo[1]));
			jsonObj.put(TYPE, getOrgType((Integer) baseInfo[2]));
			if (orgRtnInfo != null && !orgRtnInfo.isEmpty()) {
				for (int j = 0; j < orgRtnInfo.size(); j++) {
					if (baseInfo[4 + j] != null) {
						jsonObj.put(orgRtnInfo.get(j), String
								.valueOf(baseInfo[4 + j]));
					}
				}
			}
			jsonArr.add(jsonObj);
			count++;
		}
		orgResult.setCount(baseInfoList.size());
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("组织架构基本信息读取结束。");
		return orgResult;
	}

	/**
	 * 获取外部组织架构所有基本信息
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = "/getElementsBaseInfoForEco", method = RequestMethod.GET)
	public SysSynchroOrgResult getElementsBaseInfoForEco()
			throws Exception {

		List<Integer> orgRtnType = new ArrayList<Integer>();
		List<String> orgRtnInfo = new ArrayList<String>();
		SysSynchroGetOrgBaseInfoContext orgContext = new SysSynchroGetOrgBaseInfoContext();
		orgContext.setReturnOrgType("");
		orgContext.setReturnType("");
		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		if (orgContext != null) {
			if (!checkNullIfNecessary(orgContext, orgResult, orgRtnType,
					orgRtnInfo)) {
				return orgResult;
			}
		}
		logger.debug("开始读取所有组织架构基本信息。");
		List baseInfoList = sysOrgElementService.findValue(getHqlByOrgContext(
				orgContext, true, orgRtnType, orgRtnInfo, true));
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("所有组织架构共计" + baseInfoList.size() + "条。");
		for (int i = 0; i < baseInfoList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			Object[] baseInfo = (Object[]) baseInfoList.get(i);
			jsonObj.put(ID, String.valueOf(baseInfo[0]));
			jsonObj.put(LUNID, String.valueOf(baseInfo[0]));
			jsonObj.put(NAME, String.valueOf(baseInfo[1]));
			jsonObj.put(TYPE, getOrgType((Integer) baseInfo[2]));
			jsonObj.put(IS_EXTERNAL, baseInfo[3]); //是否外部元素
			if (orgRtnInfo != null && !orgRtnInfo.isEmpty()) {
				for (int j = 0; j < orgRtnInfo.size(); j++) {
					if (baseInfo[4 + j] != null) {
						jsonObj.put(orgRtnInfo.get(j), String
								.valueOf(baseInfo[4 + j]));
					}
				}
			}
			jsonArr.add(jsonObj);
			count++;
		}
		orgResult.setCount(baseInfoList.size());
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("组织架构基本信息读取结束。");
		return orgResult;
	}

	/**
	 * 获取需要更新的组织机构信息
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = "/getUpdatedElementsForEco", method = RequestMethod.POST)
	public SysSynchroOrgResult getUpdatedElementsForEco(
			@RequestBody SysSynchroGetOrgInfoContext orgContext)
			throws Exception {

		List<Integer> orgRtnType = new ArrayList<Integer>();
		List<String> orgRtnInfo = new ArrayList<String>();

		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		if (!checkNullIfNecessary(orgContext, orgResult, orgRtnType, orgRtnInfo)) {
			return orgResult;
		}
		logger.debug("开始读取需要更新的组织架构信息。");
		Date tmpStamp = null;
		List baseInfoList = sysOrgElementService.findList(getHqlByOrgContext(
				orgContext, false, orgRtnType, orgRtnInfo, true));
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("需要更新的组织架构信息共计" + baseInfoList.size() + "条。");
		for (int i = 0; i < baseInfoList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			// SysOrgElement orgElement = (SysOrgElement) baseInfoList.get(i);
			Object[] baseInfo = (Object[]) baseInfoList.get(i);
			if ((i + 1) > orgContext.getCount() && tmpStamp != null
					&& baseInfo[2] != null
					&& ((Date) baseInfo[2]).after(tmpStamp)) {
				break;
			}
			SysOrgElement tmp = new SysOrgElement();
			tmp.setFdId(String.valueOf(baseInfo[0]));
			tmp.setFdOrgType((Integer) baseInfo[1]);
			SysOrgElement orgElement = sysOrgElementService.format(tmp);
			setOrgBaseInfo(jsonObj, orgElement);
			int orgType = orgElement.getFdOrgType();
			setRelationInfo(jsonObj, orgElement, orgType, true);
			count++;
			jsonArr.add(jsonObj);
			tmpStamp = orgElement.getFdAlterTime();
		}
		orgResult.setCount(count);
		if (orgContext == null || count == 0) {
			orgResult.setTimeStamp(orgContext.getBeginTimeStamp());
		} else {
			orgResult.setTimeStamp(timeConvert(tmpStamp));
		}
		logger.debug("返回时间戳为：" + orgResult.getTimeStamp());
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("组织架构更新信息读取结束。");
		return orgResult;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getDynamicExternalData", method = RequestMethod.GET)
	public SysSynchroOrgResult getDynamicExternalData() throws Exception {
		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
		List<SysOrgElementExternal> externalList = sysOrgElementExternalService.findList(hqlInfo);

		JSONArray jsonArr = new JSONArray();
		for (SysOrgElementExternal external : externalList) {
			JSONObject externalObj = new JSONObject();
			externalObj.put("elementId", external.getFdId());
			String deptTable = external.getFdDeptTable();
			externalObj.put("deptTable", deptTable);
			String personTable = external.getFdPersonTable();
			externalObj.put("personTable", personTable);

			List<SysOrgElementExtProp> dProps = external.getFdDeptProps();
			externalObj.put("d_list", buildProps(dProps));
			List<SysOrgElementExtProp> pProps = external.getFdPersonProps();
			externalObj.put("p_list", buildProps(pProps));
			externalObj.put("d_data", getDynamicTableData(deptTable, dProps));
			externalObj.put("p_data", getDynamicTableData(personTable, pProps));
			List<SysOrgElement> authReaders = external.getAuthReaders();
			if (CollectionUtils.isNotEmpty(authReaders)) {
				JSONArray readerArr = new JSONArray();
				for (int i = 0;i < authReaders.size();i++) {
					readerArr.add(authReaders.get(i).getFdId());
				}
				externalObj.put("authReaders", readerArr);
			}

			jsonArr.add(externalObj);
		}

		orgResult.setCount(externalList.size());
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);

		return orgResult;
	}

	private JSONArray buildProps(List<SysOrgElementExtProp> props) {
		JSONArray propArr = new JSONArray();
		if (CollectionUtils.isNotEmpty(props)) {
			for (SysOrgElementExtProp prop : props) {
				JSONObject propObj = new JSONObject();
				propObj.put("fdId", prop.getFdId());
				propObj.put("fdType", prop.getFdType());
				propObj.put("fdName", prop.getFdName());
				if (prop.getFdOrder() != null) {
					propObj.put("fdOrder", prop.getFdOrder());
				}
				propObj.put("fdFieldName", prop.getFdFieldName());
				propObj.put("fdColumnName", prop.getFdColumnName());
				propObj.put("fdFieldType", prop.getFdFieldType());
				propObj.put("fdFieldLength", prop.getFdFieldLength());
				propObj.put("fdScale", prop.getFdScale());
				propObj.put("fdRequired", prop.getFdRequired());
				propObj.put("fdStatus", prop.getFdStatus());
				propObj.put("fdShowList", prop.getFdShowList());
				propObj.put("fdDisplayType", prop.getFdDisplayType());
				List<SysOrgElementExtPropEnum> enums = prop.getFdFieldEnums();
				JSONArray enumArr = new JSONArray();
				for (SysOrgElementExtPropEnum propEnum : enums) {
					JSONObject enumObj = new JSONObject();
					enumObj.put("fdId", propEnum.getFdId());
					enumObj.put("fdOrder", propEnum.getFdOrder());
					enumObj.put("fdName", propEnum.getFdName());
					enumObj.put("fdValue", propEnum.getFdValue());
					enumArr.add(enumObj);
				}
				propObj.put("enums", enumArr);
				propArr.add(propObj);
			}
		}

		return propArr;
	}

	private JSONArray getDynamicTableData(String tableName, List<SysOrgElementExtProp> props) throws Exception {
		JSONArray tableArr = new JSONArray();
		if (CollectionUtils.isNotEmpty(props)) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT fd_id");
			String[] columArr = new String[props.size() + 1];
			columArr[0] = "fd_id";
			for (int i = 0;i < props.size();i++) {
				sql.append(", ").append(props.get(i).getFdColumnName());
				columArr[i + 1] = props.get(i).getFdColumnName();
			}
			sql.append(" FROM ").append(tableName);
			NativeQuery query = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				for (Object[] objs : list) {
					JSONArray rowArr = new JSONArray();
					for (int i = 0; i < objs.length; i++) {
						JSONObject obj = new JSONObject();
						Object _value = objs[i];
						if (_value == null) {
							obj.put(columArr[i], _value);
						} else {
							obj.put(columArr[i], _value.toString());
						}
						rowArr.add(obj);
					}
					tableArr.add(rowArr);
				}
			}
		}
		return tableArr;
	}

	/**
	 * 获取需要更新的组织机构信息
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = "/getUpdatedElements", method = RequestMethod.POST)
	public SysSynchroOrgResult getUpdatedElements(
			@RequestBody SysSynchroGetOrgInfoContext orgContext)
			throws Exception {

		List<Integer> orgRtnType = new ArrayList<Integer>();
		List<String> orgRtnInfo = new ArrayList<String>();

		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		if (!checkNullIfNecessary(orgContext, orgResult, orgRtnType, orgRtnInfo)) {
			return orgResult;
		}
		logger.debug("开始读取需要更新的组织架构信息。");
		Date tmpStamp = null;
		List baseInfoList = sysOrgElementService.findList(getHqlByOrgContext(
				orgContext, false, orgRtnType, orgRtnInfo, false));
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("需要更新的组织架构信息共计" + baseInfoList.size() + "条。");
		for (int i = 0; i < baseInfoList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			// SysOrgElement orgElement = (SysOrgElement) baseInfoList.get(i);
			Object[] baseInfo = (Object[]) baseInfoList.get(i);
			/*if(count == orgContext.getCount()) {
				break;
			}*/
			if ((i + 1) > orgContext.getCount() && tmpStamp != null
					&& baseInfo[2] != null
					&& ((Date) baseInfo[2]).after(tmpStamp)) {
				break;
			}
			SysOrgElement tmp = new SysOrgElement();
			tmp.setFdId(String.valueOf(baseInfo[0]));
			tmp.setFdOrgType((Integer) baseInfo[1]);
			SysOrgElement orgElement = sysOrgElementService.format(tmp);
			setOrgBaseInfo(jsonObj, orgElement);
			int orgType = orgElement.getFdOrgType();
			setRelationInfo(jsonObj, orgElement, orgType, true);
			count++;
			jsonArr.add(jsonObj);
			tmpStamp = orgElement.getFdAlterTime();
		}
		orgResult.setCount(count);
		if (orgContext == null || count == 0) {
			orgResult.setTimeStamp(orgContext.getBeginTimeStamp());
		} else {
			orgResult.setTimeStamp(timeConvert(tmpStamp));
		}
		logger.debug("返回时间戳为：" + orgResult.getTimeStamp());
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("组织架构更新信息读取结束。");
		return orgResult;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getUpdatedElementsByToken", method = RequestMethod.POST)
	public SysSynchroOrgTokenResult getUpdatedElementsByToken(
			@RequestBody SysSynchroGetOrgInfoTokenContext orgContext)
			throws Exception {

		List<Integer> orgRtnType = new ArrayList<Integer>();
		List<String> orgRtnInfo = new ArrayList<String>();

		SysSynchroOrgTokenResult orgResult = new SysSynchroOrgTokenResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		if (!checkNullIfNecessaryToken(orgContext, orgResult, orgRtnType,
				orgRtnInfo)) {
			return orgResult;
		}
		logger.debug("开始读取需要更新的组织架构信息。");

		String token = orgContext.getToken();
		int pageCount = orgContext.getCount();
		int pageNo = orgContext.getPageNo();
		List baseInfoList = null;
		if (StringUtil.isNull(token)) {
			token = IDGenerator.generateID();
			baseInfoList = sysOrgElementService.findList(getHqlByOrgContext(
					orgContext, false, orgRtnType, orgRtnInfo, false));
			SysSynchroOrgCache newCache = new SysSynchroOrgCache();
			newCache.setBaseInfoList(baseInfoList);
			newCache.setExpirationTime(new Date().getTime() + 3600000L);
			newCache.setPageCount(pageCount);
			newCache.setPageNo(pageNo);
			sysSynchroOrgCacheMap.put(token, newCache);
		} else {
			SysSynchroOrgCache cache = sysSynchroOrgCacheMap.get(token);
			if (cache == null || cache.getBaseInfoList() == null) {
				baseInfoList = sysOrgElementService
						.findList(getHqlByOrgContext(orgContext, false,
								orgRtnType, orgRtnInfo, false));
				SysSynchroOrgCache newCache = new SysSynchroOrgCache();
				newCache.setBaseInfoList(baseInfoList);
				newCache.setExpirationTime(new Date().getTime() + 3600000L);
				newCache.setPageCount(pageCount);
				newCache.setPageNo(pageNo);
				sysSynchroOrgCacheMap.put(token, newCache);
			} else {
				baseInfoList = cache.getBaseInfoList();
				cache.setPageCount(pageCount);
				cache.setPageNo(pageNo);
			}
		}

		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("需要更新的组织架构信息共计" + baseInfoList.size() + "条。");
		for (int i = pageCount * (pageNo - 1); i < baseInfoList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			Object[] baseInfo = (Object[]) baseInfoList.get(i);
			if ((count + 1) > pageCount) {
				break;
			}
			SysOrgElement tmp = new SysOrgElement();
			tmp.setFdId(String.valueOf(baseInfo[0]));
			tmp.setFdOrgType((Integer) baseInfo[1]);
			SysOrgElement orgElement = sysOrgElementService.format(tmp);
			setOrgBaseInfo(jsonObj, orgElement);
			int orgType = orgElement.getFdOrgType();
			setRelationInfo(jsonObj, orgElement, orgType, true);
			count++;
			jsonArr.add(jsonObj);
		}
		orgResult.setCount(count);
		orgResult.setToken(token);

		logger.debug("返回令牌为：" + orgResult.getToken());
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("组织架构更新信息读取结束。");

		// 取数结束，清除缓存
		if (count < pageCount) {
			sysSynchroOrgCacheMap.remove(token);
		}
		return orgResult;
	}

	protected void setRelationInfo(JSONObject jsonObj, SysOrgElement orgElement,
								 int orgType, boolean isGetStaffingLevel) throws Exception {
		if (orgType == ORG_TYPE_ORG || orgType == ORG_TYPE_DEPT) {// 机构/部门同步
			if (orgElement.getHbmParent() != null) {
				jsonObj.put(PARENT, orgElement.getHbmParent().getFdId());
			}
			if (orgElement.getHbmThisLeader() != null) {
				jsonObj.put(THIS_LEADER, orgElement.getHbmThisLeader()
						.getFdId());
			}
			if (orgElement.getHbmSuperLeader() != null) {
				jsonObj.put(SUPER_LEADER, orgElement.getHbmSuperLeader()
						.getFdId());
			}
			if (orgElement.getDocCreator() != null) {
				jsonObj.put(DOC_CREATOR, orgElement.getDocCreator().getFdId());
			}
			// 管理员
			List<SysOrgElement> admins = orgElement.getAuthElementAdmins();
			if (admins != null && !admins.isEmpty()) {
				JSONArray array = new JSONArray();
				for (SysOrgElement element : admins) {
					array.add(element.getFdId());
				}
				jsonObj.put("admins", array);
			}

			//生态组织可查看范围
			setRange(orgElement, jsonObj);
			//组织隐藏范围
			setHideRange(orgElement, jsonObj);
		} else if (orgType == ORG_TYPE_GROUP) {// 群组同步
			SysOrgGroup sysOrgGroup = (SysOrgGroup) orgElement;
			List<SysOrgElement> member = sysOrgGroup.getFdMembers();
			if (member != null && !member.isEmpty()) {
				JSONArray array = new JSONArray();
				for (SysOrgElement element : member) {
					array.add(element.getFdId());
				}
				jsonObj.put(MEMBERS, array);
			}
			SysOrgGroupCate sysOrgGroupCate = sysOrgGroup.getFdGroupCate();
			if (sysOrgGroupCate != null) {
				jsonObj.put("groupCateId", sysOrgGroupCate.getFdId());
			}
			List<SysOrgElement> authReaders = sysOrgGroup.getAuthReaders();
			if (authReaders != null && !authReaders.isEmpty()) {
				JSONArray array = new JSONArray();
				for (SysOrgElement element : authReaders) {
					array.add(element.getFdId());
				}
				jsonObj.put("authReaders", array);
			}
			List<SysOrgElement> authEditors = sysOrgGroup.getAuthEditors();
			if (authEditors != null && !authEditors.isEmpty()) {
				JSONArray array = new JSONArray();
				for (SysOrgElement element : authEditors) {
					array.add(element.getFdId());
				}
				jsonObj.put("authEditors", array);
			}

			boolean authReaderFlag = sysOrgGroup.getAuthReaderFlag();
			jsonObj.put("authReaderFlag", authReaderFlag);
		} else if (orgType == ORG_TYPE_POST) {// 岗位同步
			SysOrgPost sysOrgPost = (SysOrgPost) orgElement;
			if (sysOrgPost.getHbmParent() != null) {
				jsonObj.put(PARENT, sysOrgPost.getHbmParent().getFdId());
			}
			if (sysOrgPost.getHbmThisLeader() != null) {
				jsonObj.put(THIS_LEADER, sysOrgPost.getHbmThisLeader()
						.getFdId());
			}
			List<SysOrgElement> persons = sysOrgPost.getHbmPersons();
			if (persons != null && !persons.isEmpty()) {
				JSONArray array = new JSONArray();
				for (SysOrgElement element : persons) {
					array.add(element.getFdId());
				}
				jsonObj.put(PERSONS, array);
			}
		} else if (orgType == ORG_TYPE_PERSON) {// 个人同步
			SysOrgPerson sysOrgPerson = (SysOrgPerson) orgElement;
			if (sysOrgPerson.getHbmParent() != null) {
				jsonObj.put(PARENT, sysOrgPerson.getHbmParent().getFdId());
			}
			if (orgElement.getDocCreator() != null) {
				jsonObj.put(DOC_CREATOR, orgElement.getDocCreator().getFdId());
			}
			List<SysOrgElement> posts = sysOrgPerson.getHbmPosts();
			if (posts != null && !posts.isEmpty()) {
				JSONArray array = new JSONArray();
				for (SysOrgElement element : posts) {
					array.add(element.getFdId());
				}
				jsonObj.put(POSTS, array);
			}
			
			SysOrganizationStaffingLevel sysOrganizationStaffingLevel = sysOrgPerson.getFdStaffingLevel();
			if(null == sysOrganizationStaffingLevel && isGetStaffingLevel) {
				sysOrganizationStaffingLevel = sysOrgCoreService.getStaffingLevel(sysOrgPerson);
			}
			
			if (sysOrganizationStaffingLevel != null) {
				jsonObj.put("staffingLevelName", sysOrganizationStaffingLevel
						.getFdNameOri());
				jsonObj.put("staffingLevelValue", sysOrganizationStaffingLevel
						.getFdLevel());
				if (SysLangUtil.isLangEnabled()) {
					Map<String, String> dynamicMap = sysOrganizationStaffingLevel
							.getDynamicMap();
					for (String key : dynamicMap.keySet()) {
						if (key.startsWith("fdName")
								&& dynamicMap.get(key) != null) {
							jsonObj.put(
									key.replace("fdName", "staffingLevelName"),
									dynamicMap.get(key));
						}
					}
				}
			}

			// 自定义属性
			jsonObj.put("customProps", DynamicAttributeUtil
					.convertCustomPropToString(sysOrgPerson));
			setOrgPersonInfo(jsonObj, sysOrgPerson);
		}
	}

	/**
	 * 设置查看范围
	 * @param orgElement
	 * @param jsonObj
	 */
	private void setRange(SysOrgElement orgElement, JSONObject jsonObj) {
		SysOrgElementRange range = orgElement.getFdRange();
		if (range != null) {
			JSONObject rangeJson = new JSONObject();
			rangeJson.put("fdId", String.valueOf(range.getFdId()));
			rangeJson.put("fdIsOpenLimit", String.valueOf(range.getFdIsOpenLimit()));
			rangeJson.put("fdViewType", String.valueOf(range.getFdViewType()));
			if (StringUtil.isNotNull(range.getFdViewSubType())) {
				rangeJson.put("fdViewSubType", String.valueOf(range.getFdViewSubType()));
			}
			if (StringUtil.isNotNull(range.getFdInviteUrl())) {
				rangeJson.put("fdInviteUrl", String.valueOf(range.getFdInviteUrl()));
			}
			List<SysOrgElement> others = range.getFdOthers();
			if (CollectionUtils.isNotEmpty(others)) {
				JSONArray array = new JSONArray();
				for (SysOrgElement element : others) {
					array.add(element.getFdId());
				}
				rangeJson.put("fdOthers", array);
			}

			jsonObj.put("range", rangeJson);
		}
	}

	/**
	 * 设置组织隐藏范围
	 * @description:
	 * @param orgElement
	 * @param jsonObj
	 * @return: void
	 * @author: wangjf
	 * @time: 2021/9/28 4:17 下午
	 */
	private void setHideRange(SysOrgElement orgElement, JSONObject jsonObj) {
		SysOrgElementHideRange fdHideRange = orgElement.getFdHideRange();
		if(fdHideRange != null){
			JSONObject rangeJson = new JSONObject();
			rangeJson.put("fdId", String.valueOf(fdHideRange.getFdId()));
			rangeJson.put("fdIsOpenLimit", String.valueOf(fdHideRange.getFdIsOpenLimit()));
			rangeJson.put("fdViewType", String.valueOf(fdHideRange.getFdViewType()));
			List<SysOrgElement> hideOthers = fdHideRange.getFdOthers();
			if (CollectionUtils.isNotEmpty(hideOthers)) {
				JSONArray array = new JSONArray();
				for (SysOrgElement element : hideOthers) {
					array.add(element.getFdId());
				}
				rangeJson.put("fdOthers", array);
			}

			jsonObj.put("hideRange", rangeJson);
		}
	}

	private void setOrgPersonInfo(JSONObject synOrgElement,
								  SysOrgPerson orgElement) {
		if (StringUtil.isNotNull(orgElement.getFdLoginName())) {
			synOrgElement.put(LOGIN_NAME, orgElement.getFdLoginName());
		}

		if (StringUtil.isNotNull(orgElement.getFdNickName())) {
			synOrgElement.put(NICK_NAME, orgElement.getFdNickName());
		}

		if (StringUtil.isNotNull(orgElement.getFdEmail())) {
			synOrgElement.put(EMAIL, orgElement.getFdEmail());
		}

		if (StringUtil.isNotNull(orgElement.getFdMobileNo())) {
			synOrgElement.put(MOBILE_NO, orgElement.getFdMobileNo());
		}

		if (StringUtil.isNotNull(orgElement.getFdWorkPhone())) {
			synOrgElement.put(WORK_PHONE, orgElement.getFdWorkPhone());
		}

		if (StringUtil.isNotNull(orgElement.getFdRtxNo())) {
			synOrgElement.put(RTX_NO, orgElement.getFdRtxNo());
		}

		if (StringUtil.isNotNull(orgElement.getFdWechatNo())) {
			synOrgElement.put(WECHAT_NO, orgElement.getFdWechatNo());
		}

		if (StringUtil.isNotNull(orgElement.getFdPassword())) {
			synOrgElement.put(PASSWORD, orgElement.getFdPassword());
		}

		if (StringUtil.isNotNull(orgElement.getFdSex())) {
			synOrgElement.put(SEX, orgElement.getFdSex());
		}

		if (StringUtil.isNotNull(orgElement.getFdShortNo())) {
			synOrgElement.put(SHORT_NO, orgElement.getFdShortNo());
		}

		if (StringUtil.isNotNull(orgElement.getFdWechatNo())) {
			synOrgElement.put(SCARD, orgElement.getFdCardNo());
		}

		if (orgElement.getFdIsActivated() != null) {
			synOrgElement.put(IS_ACTIVATED, orgElement.getFdIsActivated());
		}

		if (orgElement.getFdCanLogin() != null) {
			synOrgElement.put(CAN_LOGIN, orgElement.getFdCanLogin());
		}

		if (StringUtil.isNotNull(orgElement.getFdLoginNameLower())) {
			synOrgElement.put(LOGIN_NAME_LOWER, orgElement.getFdLoginNameLower());
		}
	}

	protected void setOrgBaseInfo(JSONObject synOrgElement,
								SysOrgElement orgElement) {
		synOrgElement.put(ID, orgElement.getFdId());
		synOrgElement.put(LUNID, orgElement.getFdId());
		synOrgElement.put(NAME, orgElement.getFdNameOri());
		synOrgElement.put(TYPE, getOrgType(orgElement.getFdOrgType()));
		synOrgElement.put(IS_EXTERNAL, orgElement.getFdIsExternal());
		if (StringUtil.isNotNull(orgElement.getFdOrgEmail())) {
			synOrgElement.put(ORG_EMAIL, orgElement.getFdOrgEmail());
		}
		if (StringUtil.isNotNull(orgElement.getFdNo())) {
			synOrgElement.put(NO, orgElement.getFdNo());
		}
		if (orgElement.getFdOrder() != null) {
			synOrgElement.put(ORDER, String.valueOf(orgElement.getFdOrder()));
		}
		if (StringUtil.isNotNull(orgElement.getFdKeyword())) {
			synOrgElement.put(KEYWORD, orgElement.getFdKeyword());
		}
		synOrgElement.put(IS_AVAILABLE, orgElement.getFdIsAvailable());
		synOrgElement.put(HIERARCHY, orgElement.getFdHierarchyId());
		if (StringUtil.isNotNull(orgElement.getFdMemo())) {
			synOrgElement.put(MEMO, StringUtil.escape(orgElement.getFdMemo()));
		}
		if (orgElement.getFdAlterTime() != null) {
			synOrgElement.put(ALTERTIME, timeConvert(orgElement
					.getFdAlterTime()));
		}
		synOrgElement.put("langProps", orgElement.getDynamicMap());
		synOrgElement.put(IS_BUSINESS, orgElement.getFdIsBusiness());
	}

	protected String getOrgType(int orgType) {
		String orgWebType = null;
		switch (orgType) {
			case ORG_TYPE_ORG:
				orgWebType = ORG_WEB_TYPE_ORG;
				break;
			case ORG_TYPE_DEPT:
				orgWebType = ORG_WEB_TYPE_DEPT;
				break;
			case ORG_TYPE_GROUP:
				orgWebType = ORG_WEB_TYPE_GROUP;
				break;
			case ORG_TYPE_POST:
				orgWebType = ORG_WEB_TYPE_POST;
				break;
			case ORG_TYPE_PERSON:
				orgWebType = ORG_WEB_TYPE_PERSON;
				break;
		}
		return orgWebType;
	}

	private HQLInfo getHqlByOrgContext(SysSynchroGetOrgContext orgContext,
									   boolean isGetBase, List<Integer> orgRtnType, List<String> orgRtnInfo, Boolean isExternal) {
		HQLInfo hqlInfo = new HQLInfo();
		// select
		if (isGetBase) {
			StringBuffer selectBlock = new StringBuffer();
			selectBlock
					.append("sysOrgElement.fdId,sysOrgElement.fdName,sysOrgElement.fdOrgType,sysOrgElement.fdIsExternal");
			if (orgRtnInfo != null && !orgRtnInfo.isEmpty()) {
				for (int i = 0; i < orgRtnInfo.size(); i++) {
					String orgInfo = orgRtnInfo.get(i);
					orgInfo = "sysOrgElement.fd"
							+ orgInfo.substring(0, 1).toUpperCase()
							+ orgInfo.substring(1);
					selectBlock.append("," + orgInfo);
				}
			}
			hqlInfo.setSelectBlock(selectBlock.toString());
			logger.debug("搜索select语句：" + selectBlock.toString());
		} else {
			StringBuffer selectBlock = new StringBuffer();
			selectBlock
					.append("sysOrgElement.fdId,sysOrgElement.fdOrgType,sysOrgElement.fdAlterTime");
			hqlInfo.setSelectBlock(selectBlock.toString());
		}
		// where
		StringBuffer whereBlock = new StringBuffer();

		if (orgContext instanceof SysSynchroGetOrgBaseInfoContextV2) {
			SysSynchroGetOrgBaseInfoContextV2 v2 = (SysSynchroGetOrgBaseInfoContextV2) orgContext;
			Boolean isBusiness = v2.getIsBusiness();
			if (isBusiness != null) {
				whereBlock.append("sysOrgElement.fdIsBusiness=:isBus");
				hqlInfo.setParameter("isBus", isBusiness);
			} else {
				whereBlock.append("1=1");
			}
		} else if (orgContext instanceof SysSynchroGetOrgInfoContextV2) {
			SysSynchroGetOrgInfoContextV2 v2 = (SysSynchroGetOrgInfoContextV2) orgContext;
			Boolean isBusiness = v2.getIsBusiness();
			if (isBusiness != null) {
				whereBlock.append("sysOrgElement.fdIsBusiness=:isBus");
				hqlInfo.setParameter("isBus", isBusiness);
			} else {
				whereBlock.append("1=1");
			}
		} else {
			whereBlock.append("sysOrgElement.fdIsBusiness=:isBus");
			hqlInfo.setParameter("isBus", true);
		}
		// 是否外部组织
		if (isExternal != null) {
			whereBlock.append(" and sysOrgElement.fdIsExternal=:isExternal");
			hqlInfo.setParameter("isExternal", isExternal);
		}
		if (!isGetBase) {
			if (orgContext != null
					&& StringUtil
					.isNotNull(((SysSynchroGetOrgInfoContext) orgContext)
							.getBeginTimeStamp())) {
				whereBlock.append(" and sysOrgElement.fdAlterTime>:beginTime ");
				hqlInfo.setParameter("beginTime",
						timeConvert(((SysSynchroGetOrgInfoContext) orgContext)
								.getBeginTimeStamp()));
			}
		}
		if (orgRtnType != null && !orgRtnType.isEmpty()) {
			whereBlock.append(" and sysOrgElement.fdOrgType in( ");
			String inStr = "";
			for (int i = 0; i < orgRtnType.size(); i++) {
				inStr += ", " + orgRtnType.get(i);
			}
			whereBlock.append(inStr.substring(1));
			whereBlock.append(" )");
		} else {
			whereBlock.append(" and sysOrgElement.fdOrgType in("
					+ ORG_TYPE_PERSON + " ," + ORG_TYPE_POST + " ,"
					+ ORG_TYPE_GROUP + " ," + ORG_TYPE_DEPT + " , "
					+ ORG_TYPE_ORG + " )");
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		logger.debug("搜索where语句：" + whereBlock.toString());

		// order
		hqlInfo.setOrderBy("sysOrgElement.fdAlterTime asc");
		return hqlInfo;
	}

	protected boolean checkNullIfNecessary(SysSynchroGetOrgContext orgContext,
										 SysSynchroOrgResult orgResult, List<Integer> orgRtnType,
										 List<String> orgRtnInfo) {
		if (orgContext == null) {
			orgResult.setReturnState(OPT_ORG_STATUS_FAIL);
			orgResult.setMessage("请求组织架构数据上下文不能为空!");
			logger.debug("请求组织架构数据上下文不能为空!");
			return false;

		}
		if ((orgContext instanceof SysSynchroGetOrgInfoContext)) {
			if (((SysSynchroGetOrgInfoContext) orgContext).getCount() <= 0) {
				orgResult.setReturnState(OPT_ORG_STATUS_FAIL);
				orgResult.setMessage("请求组织架构数据的条目数不能为空!");
				logger.debug("请求组织架构数据的条目数不能为空!");
				return false;
			}
		}
		if (StringUtil.isNotNull(orgContext.getReturnOrgType())) {
			JSONArray orgTypeArr = (JSONArray) JSONValue.parse(orgContext
					.getReturnOrgType());
			for (int i = 0; i < orgTypeArr.size(); i++) {
				JSONObject typeObj = (JSONObject) orgTypeArr.get(i);
				String orgType = (String) typeObj.get("type");
				if (ORG_WEB_TYPE_PERSON.equalsIgnoreCase(orgType)) {
					if (orgRtnType == null) {
						orgRtnType = new ArrayList<Integer>();
					}
					orgRtnType.add(ORG_TYPE_PERSON);
				} else if (ORG_WEB_TYPE_POST.equalsIgnoreCase(orgType)) {
					if (orgRtnType == null) {
						orgRtnType = new ArrayList<Integer>();
					}
					orgRtnType.add(ORG_TYPE_POST);
				} else if (ORG_WEB_TYPE_GROUP.equalsIgnoreCase(orgType)) {
					if (orgRtnType == null) {
						orgRtnType = new ArrayList<Integer>();
					}
					orgRtnType.add(ORG_TYPE_GROUP);
				} else if (ORG_WEB_TYPE_ORG.equalsIgnoreCase(orgType)) {
					if (orgRtnType == null) {
						orgRtnType = new ArrayList<Integer>();
					}
					orgRtnType.add(ORG_TYPE_ORG);
				} else if (ORG_WEB_TYPE_DEPT.equalsIgnoreCase(orgType)) {
					if (orgRtnType == null) {
						orgRtnType = new ArrayList<Integer>();
					}
					orgRtnType.add(ORG_TYPE_DEPT);
				} else {
					orgResult.setReturnState(OPT_ORG_STATUS_FAIL);
					orgResult.setMessage("需要返回的组织架构类型设置不合法,传入的信息为:"
							+ orgContext.getReturnOrgType());
					logger.debug("需要返回的组织架构类型设置不合法,传入的信息为:"
							+ orgContext.getReturnOrgType());
					return false;
				}
			}
		}
		if (orgContext instanceof SysSynchroGetOrgBaseInfoContext) {
			SysSynchroGetOrgBaseInfoContext orgBaseContext = (SysSynchroGetOrgBaseInfoContext) orgContext;
			if (StringUtil.isNotNull(orgBaseContext.getReturnType())) {
				JSONArray rtnTypeArr = (JSONArray) JSONValue
						.parse(orgBaseContext.getReturnType());
				for (int i = 0; i < rtnTypeArr.size(); i++) {
					JSONObject typeObj = (JSONObject) rtnTypeArr.get(i);
					String orgType = (String) typeObj.get("type");
					if (NO.equalsIgnoreCase(orgType)
							|| ORDER.equalsIgnoreCase(orgType)
							|| KEYWORD.equalsIgnoreCase(orgType)) {
						orgRtnInfo.add(orgType);
					} else {
						orgResult.setReturnState(OPT_ORG_STATUS_FAIL);
						orgResult.setMessage("需要返回的组织架构基本信息设置不合法,传入的信息为:"
								+ orgBaseContext.getReturnType());
						logger.debug("需要返回的组织架构基本信息设置不合法,传入的信息为:"
								+ orgContext.getReturnOrgType());
						return false;
					}
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String s = "[{'type':'person'}]";
		JSONArray orgTypeArr = (JSONArray) JSONValue.parse(s);
		System.out.println(orgTypeArr.size());
	}

	private boolean checkNullIfNecessaryToken(
			SysSynchroGetOrgInfoTokenContext orgContext,
			SysSynchroOrgTokenResult orgResult, List<Integer> orgRtnType,
			List<String> orgRtnInfo) {
		if (orgContext == null) {
			orgResult.setReturnState(OPT_ORG_STATUS_FAIL);
			orgResult.setMessage("请求组织架构数据上下文不能为空!");
			logger.debug("请求组织架构数据上下文不能为空!");
			return false;

		}

		int pageCount = orgContext.getCount();
		if (pageCount <= 0) {
			orgResult.setReturnState(OPT_ORG_STATUS_FAIL);
			orgResult.setMessage("返回记录条数不能为空且必须大于0!");
			logger.debug("返回记录条数不能为空!");
			return false;
		}
		int pageNo = orgContext.getPageNo();
		if (pageNo <= 0) {
			orgResult.setReturnState(OPT_ORG_STATUS_FAIL);
			orgResult.setMessage("页码不能为空且必须大于0!");
			logger.debug("页码不能为空!");
			return false;
		}

		if (StringUtil.isNotNull(orgContext.getReturnOrgType())) {
			JSONArray orgTypeArr = (JSONArray) JSONValue.parse(orgContext
					.getReturnOrgType());
			for (int i = 0; i < orgTypeArr.size(); i++) {
				JSONObject typeObj = (JSONObject) orgTypeArr.get(i);
				String orgType = (String) typeObj.get("type");
				if (ORG_WEB_TYPE_PERSON.equalsIgnoreCase(orgType)) {
					if (orgRtnType == null) {
						orgRtnType = new ArrayList<Integer>();
					}
					orgRtnType.add(ORG_TYPE_PERSON);
				} else if (ORG_WEB_TYPE_POST.equalsIgnoreCase(orgType)) {
					if (orgRtnType == null) {
						orgRtnType = new ArrayList<Integer>();
					}
					orgRtnType.add(ORG_TYPE_POST);
				} else if (ORG_WEB_TYPE_GROUP.equalsIgnoreCase(orgType)) {
					if (orgRtnType == null) {
						orgRtnType = new ArrayList<Integer>();
					}
					orgRtnType.add(ORG_TYPE_GROUP);
				} else if (ORG_WEB_TYPE_ORG.equalsIgnoreCase(orgType)) {
					if (orgRtnType == null) {
						orgRtnType = new ArrayList<Integer>();
					}
					orgRtnType.add(ORG_TYPE_ORG);
				} else if (ORG_WEB_TYPE_DEPT.equalsIgnoreCase(orgType)) {
					if (orgRtnType == null) {
						orgRtnType = new ArrayList<Integer>();
					}
					orgRtnType.add(ORG_TYPE_DEPT);
				} else {
					orgResult.setReturnState(OPT_ORG_STATUS_FAIL);
					orgResult.setMessage("需要返回的组织架构类型设置不合法,传入的信息为:"
							+ orgContext.getReturnOrgType());
					logger.debug("需要返回的组织架构类型设置不合法,传入的信息为:"
							+ orgContext.getReturnOrgType());
					return false;
				}
			}
		}

		return true;
	}

	protected Date timeConvert(String beginTime) {
		return DateUtil.convertStringToDate(beginTime,
				"yyyy-MM-dd HH:mm:ss.SSS");
	}

	protected String timeConvert(Date time) {
		return DateUtil.convertDateToString(time, "yyyy-MM-dd HH:mm:ss.SSS");

	}

	private ISysOrgRoleConfService sysOrgRoleConfService;

	private ISysOrgRoleLineService sysOrgRoleLineService;

	private ISysOrgRoleService sysOrgRoleService;

	private ISysOrgRoleLineDefaultRoleService sysOrgRoleLineDefaultRoleService;

	private ISysOrgGroupCateService sysOrgGroupCateService;

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	private ISysOrgRoleConfCateService sysOrgRoleConfCateService;

	public ISysOrgGroupCateService getSysOrgGroupCateService() {
		return sysOrgGroupCateService;
	}

	public void setSysOrgGroupCateService(
			ISysOrgGroupCateService sysOrgGroupCateService) {
		this.sysOrgGroupCateService = sysOrgGroupCateService;
	}

	private void setOrgRoleConf(JSONObject synOrgRoleConf,
								SysOrgRoleConf sysOrgRoleConf) {
		synOrgRoleConf.put(ID, sysOrgRoleConf.getFdId());
		if (sysOrgRoleConf.getFdNameOri() != null) {
			synOrgRoleConf.put(NAME, sysOrgRoleConf.getFdNameOri());
		}
		if (sysOrgRoleConf.getFdOrder() != null) {
			synOrgRoleConf.put(ORDER, String.valueOf(sysOrgRoleConf
					.getFdOrder()));
		}
		if (sysOrgRoleConf.getFdIsAvailable() != null) {
			synOrgRoleConf.put(IS_AVAILABLE, sysOrgRoleConf.getFdIsAvailable());
		}
		if (sysOrgRoleConf.getFdRoleConfCate() != null) {
			synOrgRoleConf.put("roleConfCateId", sysOrgRoleConf
					.getFdRoleConfCate().getFdId());
		}
		List<SysOrgElement> sysRoleLineEditors = sysOrgRoleConf
				.getSysRoleLineEditors();

		JSONArray roleLineEditorsArray = new JSONArray();
		for (SysOrgElement element : sysRoleLineEditors) {
			roleLineEditorsArray.add(element.getFdId());
		}
		synOrgRoleConf.put("roleLineEditors", roleLineEditorsArray);

		// List<SysOrgRoleLineDefaultRole> defaultRoleList =
		// sysOrgRoleConf.getDefaultRoleList();
		// for (SysOrgRoleLineDefaultRole element : defaultRoleList) {
		// roleLineEditorsArray.add(element.getFdId());
		// }
		// synOrgRoleConf.put("roleLineEditors", roleLineEditorsArray);
		synOrgRoleConf.put("langProps", sysOrgRoleConf.getDynamicMap());
	}

	private void setOrgRoleLine(JSONObject synOrgRoleLine,
								SysOrgRoleLine sysOrgRoleLine) {
		synOrgRoleLine.put(ID, sysOrgRoleLine.getFdId());
		if (sysOrgRoleLine.getFdNameOri() != null) {
			synOrgRoleLine.put(NAME, sysOrgRoleLine.getFdNameOri());
		}
		if (sysOrgRoleLine.getFdOrder() != null) {
			synOrgRoleLine.put(ORDER, String.valueOf(sysOrgRoleLine
					.getFdOrder()));
		}
		synOrgRoleLine.put(HIERARCHY, sysOrgRoleLine.getFdHierarchyId());
		synOrgRoleLine.put("createTime", DateUtil.convertDateToString(
				sysOrgRoleLine.getFdCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		if (sysOrgRoleLine.getFdParent() != null) {
			synOrgRoleLine.put(PARENT, sysOrgRoleLine.getFdParent().getFdId());
		}
		if (sysOrgRoleLine.getSysOrgRoleConf() != null) {
			synOrgRoleLine.put("roleConf", sysOrgRoleLine.getSysOrgRoleConf()
					.getFdId());
		}
		if (sysOrgRoleLine.getSysOrgRoleMember() != null) {
			synOrgRoleLine.put("member", sysOrgRoleLine.getSysOrgRoleMember()
					.getFdId());
		}
		synOrgRoleLine.put("langProps", sysOrgRoleLine.getDynamicMap());
	}

	private void setOrgRoleLineDefaultRole(
			JSONObject synOrgRoleLineDefaultRole,
			SysOrgRoleLineDefaultRole sysOrgRoleLineDefaultRole) {
		synOrgRoleLineDefaultRole.put(ID, sysOrgRoleLineDefaultRole.getFdId());
		if (sysOrgRoleLineDefaultRole.getSysOrgRoleConf() != null) {
			synOrgRoleLineDefaultRole.put("roleConfId",
					sysOrgRoleLineDefaultRole.getSysOrgRoleConf().getFdId());
		}
		if (sysOrgRoleLineDefaultRole.getFdPerson() != null) {
			synOrgRoleLineDefaultRole.put("personId", sysOrgRoleLineDefaultRole
					.getFdPerson().getFdId());
		}
		if (sysOrgRoleLineDefaultRole.getFdPost() != null) {
			synOrgRoleLineDefaultRole.put("postId", sysOrgRoleLineDefaultRole
					.getFdPost().getFdId());
		}

	}

	private void setOrgRole(JSONObject synOrgRole, SysOrgRole sysOrgRole) {
		setOrgBaseInfo(synOrgRole, sysOrgRole);
		// synOrgRole.put(ID, sysOrgRole.getFdId());
		synOrgRole.put("plugin", sysOrgRole.getFdPlugin());
		if (sysOrgRole.getFdParameter() != null) {
			synOrgRole.put("parameter", sysOrgRole.getFdParameter());
		}
		synOrgRole.put("isMultiple", sysOrgRole.getFdIsMultiple());
		if (sysOrgRole.getFdRtnValue() != null) {
			synOrgRole.put("rtnValue", sysOrgRole.getFdRtnValue());
		}
		if (sysOrgRole.getFdRoleConf() != null) {
			synOrgRole.put("roleConf", sysOrgRole.getFdRoleConf().getFdId());
		}

	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getRoleConfInfo", method = RequestMethod.POST)
	public SysSynchroOrgResult getRoleConfInfo(
			@RequestBody SysSynchroGetOrgInfoContext orgContext)
			throws Exception {
		HQLInfo info = new HQLInfo();
		if (orgContext != null) {
			String beginTimeStamp_str = orgContext.getBeginTimeStamp();
			if (StringUtil.isNotNull(beginTimeStamp_str)) {
				info.setWhereBlock("sysOrgRoleConf.fdAlterTime>=:beginTime");
				info.setParameter("beginTime", timeConvert(beginTimeStamp_str));
			}
		}
		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		List roleConfList = sysOrgRoleConfService.findList(info);
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("角色线配置信息共计" + roleConfList.size() + "条。");
		for (int i = 0; i < roleConfList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			SysOrgRoleConf sysOrgRoleConf = (SysOrgRoleConf) roleConfList
					.get(i);
			setOrgRoleConf(jsonObj, sysOrgRoleConf);
			count++;
			jsonArr.add(jsonObj);
		}
		orgResult.setCount(count);
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("角色线配置信息读取结束。");
		return orgResult;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getRoleInfo", method = RequestMethod.POST)
	public SysSynchroOrgResult getRoleInfo(
			@RequestBody SysSynchroGetOrgInfoContext orgContext)
			throws Exception {
		HQLInfo info = new HQLInfo();
		if (orgContext != null) {
			String beginTimeStamp_str = orgContext.getBeginTimeStamp();
			if (StringUtil.isNotNull(beginTimeStamp_str)) {
				info.setWhereBlock("sysOrgRole.fdAlterTime>=:beginTime");
				info.setParameter("beginTime", timeConvert(beginTimeStamp_str));
			}
		}
		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		List roleList = sysOrgRoleService.findList(info);
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("通用岗位信息共计" + roleList.size() + "条。");
		for (int i = 0; i < roleList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			SysOrgRole sysOrgRole = (SysOrgRole) roleList.get(i);
			setOrgRole(jsonObj, sysOrgRole);
			count++;
			jsonArr.add(jsonObj);
		}
		orgResult.setCount(count);
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("通用岗位信息读取结束。");
		return orgResult;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getRoleLineDefaultRoleInfo", method = RequestMethod.POST)
	public SysSynchroOrgResult getRoleLineDefaultRoleInfo(
			@RequestBody SysSynchroGetOrgInfoContext orgContext)
			throws Exception {
		HQLInfo info = new HQLInfo();
		if (orgContext != null) {
			String beginTimeStamp_str = orgContext.getBeginTimeStamp();
			if (StringUtil.isNotNull(beginTimeStamp_str)) {
				info
						.setWhereBlock("sysOrgRoleLineDefaultRole.fdAlterTime>=:beginTime");
				info.setParameter("beginTime", timeConvert(beginTimeStamp_str));
			}
		}
		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		List roleLineDefaultRoleList = sysOrgRoleLineDefaultRoleService
				.findList(info);
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("角色线配置默认角色信息共计" + roleLineDefaultRoleList.size() + "条。");
		for (int i = 0; i < roleLineDefaultRoleList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			SysOrgRoleLineDefaultRole sysOrgRoleLineDefaultRole = (SysOrgRoleLineDefaultRole) roleLineDefaultRoleList
					.get(i);
			setOrgRoleLineDefaultRole(jsonObj, sysOrgRoleLineDefaultRole);
			count++;
			jsonArr.add(jsonObj);
		}
		orgResult.setCount(count);
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("角色线配置默认角色信息读取结束。");
		return orgResult;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getRoleLineInfo", method = RequestMethod.POST)
	public SysSynchroOrgResult getRoleLineInfo(
			@RequestBody SysSynchroGetOrgInfoContext orgContext)
			throws Exception {
		HQLInfo info = new HQLInfo();
		if (orgContext != null) {
			String beginTimeStamp_str = orgContext.getBeginTimeStamp();
			if (StringUtil.isNotNull(beginTimeStamp_str)) {
				info.setWhereBlock("sysOrgRoleLine.fdAlterTime>=:beginTime");
				info.setParameter("beginTime", timeConvert(beginTimeStamp_str));
			}
		}
		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		List roleLineList = sysOrgRoleLineService.findList(info);
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("角色线信息共计" + roleLineList.size() + "条。");
		for (int i = 0; i < roleLineList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			SysOrgRoleLine sysOrgRoleLine = (SysOrgRoleLine) roleLineList
					.get(i);
			setOrgRoleLine(jsonObj, sysOrgRoleLine);
			count++;
			jsonArr.add(jsonObj);
		}
		orgResult.setCount(count);
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("角色线信息读取结束。");
		return orgResult;
	}

	public void setSysOrgRoleConfService(
			ISysOrgRoleConfService sysOrgRoleConfService) {
		this.sysOrgRoleConfService = sysOrgRoleConfService;
	}

	public ISysOrgRoleConfService getSysOrgRoleConfService() {
		return sysOrgRoleConfService;
	}

	public void setSysOrgRoleLineService(
			ISysOrgRoleLineService sysOrgRoleLineService) {
		this.sysOrgRoleLineService = sysOrgRoleLineService;
	}

	public ISysOrgRoleLineService getSysOrgRoleLineService() {
		return sysOrgRoleLineService;
	}

	public void setSysOrgRoleService(ISysOrgRoleService sysOrgRoleService) {
		this.sysOrgRoleService = sysOrgRoleService;
	}

	public ISysOrgRoleService getSysOrgRoleService() {
		return sysOrgRoleService;
	}

	public void setSysOrgRoleLineDefaultRoleService(
			ISysOrgRoleLineDefaultRoleService sysOrgRoleLineDefaultRoleService) {
		this.sysOrgRoleLineDefaultRoleService = sysOrgRoleLineDefaultRoleService;
	}

	public ISysOrgRoleLineDefaultRoleService getSysOrgRoleLineDefaultRoleService() {
		return sysOrgRoleLineDefaultRoleService;
	}

	private void setOrgGroupCate(JSONObject synOrgGroupCate,
								 SysOrgGroupCate sysOrgGroupCate) {
		synOrgGroupCate.put(ID, sysOrgGroupCate.getFdId());
		synOrgGroupCate.put(NAME, sysOrgGroupCate.getFdNameOri());
		if (sysOrgGroupCate.getFdKeyword() != null) {
			synOrgGroupCate.put(KEYWORD, String.valueOf(sysOrgGroupCate
					.getFdKeyword()));
		}
		if (sysOrgGroupCate.getFdParent() != null) {
			synOrgGroupCate
					.put(PARENT, sysOrgGroupCate.getFdParent().getFdId());
		}
		synOrgGroupCate.put("langProps", sysOrgGroupCate.getDynamicMap());

	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getOrgGroupCateInfo", method = RequestMethod.POST)
	public SysSynchroOrgResult getOrgGroupCateInfo(
			@RequestBody SysSynchroGetOrgInfoContext orgContext)
			throws Exception {
		HQLInfo info = new HQLInfo();

		String beginTimeStamp_str = orgContext.getBeginTimeStamp();
		if (StringUtil.isNotNull(beginTimeStamp_str)) {
			info.setWhereBlock("sysOrgGroupCate.fdAlterTime>=:beginTime");
			info.setParameter("beginTime", timeConvert(beginTimeStamp_str));
		}

		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		List roleConfList = sysOrgGroupCateService.findList(info);
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("群组分类信息共计" + roleConfList.size() + "条。");
		for (int i = 0; i < roleConfList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			SysOrgGroupCate sysOrgGroupCate = (SysOrgGroupCate) roleConfList
					.get(i);
			setOrgGroupCate(jsonObj, sysOrgGroupCate);
			count++;
			jsonArr.add(jsonObj);
		}
		orgResult.setCount(count);
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("群组分类信息读取结束。");
		return orgResult;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		return sysOrgCoreService;
	}

	private void setOrgStaffingLevel(JSONObject synOrgStaffingLevel,
									 SysOrganizationStaffingLevel sysOrgStaffingLevel) {
		synOrgStaffingLevel.put(ID, sysOrgStaffingLevel.getFdId());
		synOrgStaffingLevel.put(NAME, sysOrgStaffingLevel.getFdNameOri());
		synOrgStaffingLevel.put("isDefault", sysOrgStaffingLevel
				.getFdIsDefault());
		synOrgStaffingLevel.put("level", sysOrgStaffingLevel.getFdLevel());
		if (sysOrgStaffingLevel.getFdDescription() != null) {
			synOrgStaffingLevel.put("description", String
					.valueOf(sysOrgStaffingLevel.getFdDescription()));
		}
		if (sysOrgStaffingLevel.getDocCreator() != null) {
			synOrgStaffingLevel.put("creator", sysOrgStaffingLevel
					.getDocCreator().getFdId());
		}
		try {
			if (sysOrgStaffingLevel.getFdPersons() != null) {
				JSONArray array = new JSONArray();
				for (SysOrgPerson person : sysOrgStaffingLevel.getFdPersons()) {
					array.add(person.getFdId());
				}
				synOrgStaffingLevel.put("persons", array);
			}
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		synOrgStaffingLevel.put("langProps",
				sysOrgStaffingLevel.getDynamicMap());

	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getOrgStaffingLevelInfo", method = RequestMethod.POST)
	public SysSynchroOrgResult getOrgStaffingLevelInfo(
			@RequestBody SysSynchroGetOrgInfoContext orgContext)
			throws Exception {
		HQLInfo info = new HQLInfo();

		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		List staffingLevelList = sysOrganizationStaffingLevelService
				.findList(info);
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("职级信息共计" + staffingLevelList.size() + "条。");
		for (int i = 0; i < staffingLevelList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			SysOrganizationStaffingLevel sysOrganizationStaffingLevel = (SysOrganizationStaffingLevel) staffingLevelList
					.get(i);
			setOrgStaffingLevel(jsonObj, sysOrganizationStaffingLevel);
			count++;
			jsonArr.add(jsonObj);
		}
		orgResult.setCount(count);
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("职级信息读取结束。");
		return orgResult;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	private void setOrgRoleConfCate(JSONObject synOrgRoleConfCate,
									SysOrgRoleConfCate sysOrgRoleConfCate) {
		synOrgRoleConfCate.put(ID, sysOrgRoleConfCate.getFdId());
		synOrgRoleConfCate.put(NAME, sysOrgRoleConfCate.getFdNameOri());
		if (sysOrgRoleConfCate.getFdKeyword() != null) {
			synOrgRoleConfCate.put(KEYWORD, String.valueOf(sysOrgRoleConfCate
					.getFdKeyword()));
		}
		if (sysOrgRoleConfCate.getFdParent() != null) {
			synOrgRoleConfCate.put(PARENT, sysOrgRoleConfCate.getFdParent()
					.getFdId());
		}
		synOrgRoleConfCate.put("langProps", sysOrgRoleConfCate.getDynamicMap());

	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getRoleConfCateInfo", method = RequestMethod.POST)
	public SysSynchroOrgResult getRoleConfCateInfo(
			@RequestBody SysSynchroGetOrgInfoContext orgContext)
			throws Exception {
		HQLInfo info = new HQLInfo();

		String beginTimeStamp_str = orgContext.getBeginTimeStamp();
		if (StringUtil.isNotNull(beginTimeStamp_str)) {
			info.setWhereBlock("sysOrgRoleConfCate.fdAlterTime>=:beginTime");
			info.setParameter("beginTime", timeConvert(beginTimeStamp_str));
		}

		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		List roleConfList = sysOrgRoleConfCateService.findList(info);
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("角色线分类信息共计" + roleConfList.size() + "条。");
		for (int i = 0; i < roleConfList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			SysOrgRoleConfCate sysOrgRoleConfCate = (SysOrgRoleConfCate) roleConfList
					.get(i);
			setOrgRoleConfCate(jsonObj, sysOrgRoleConfCate);
			count++;
			jsonArr.add(jsonObj);
		}
		orgResult.setCount(count);
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("角色线分类信息读取结束。");
		return orgResult;
	}

	public void setSysOrgRoleConfCateService(
			ISysOrgRoleConfCateService sysOrgRoleConfCateService) {
		this.sysOrgRoleConfCateService = sysOrgRoleConfCateService;
	}

	public ISysOrgRoleConfCateService getSysOrgRoleConfCateService() {
		return sysOrgRoleConfCateService;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getElementsBaseInfoV2", method = RequestMethod.POST)
	public SysSynchroOrgResult getElementsBaseInfoV2(
			SysSynchroGetOrgBaseInfoContextV2 orgContext) throws Exception {
		return getElementsBaseInfo(orgContext);
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getUpdatedElementsV2", method = RequestMethod.POST)
	public SysSynchroOrgResult getUpdatedElementsV2(
			SysSynchroGetOrgInfoContextV2 orgContext) throws Exception {
		return getUpdatedElements(orgContext);
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getRoleConfMemberInfo", method = RequestMethod.POST)
	public SysSynchroOrgResult getRoleConfMemberInfo(
			@RequestBody SysSynchroGetOrgInfoContext orgContext)
			throws Exception {
		HQLInfo info = new HQLInfo();
		if (orgContext != null) {
			String beginTimeStamp_str = orgContext.getBeginTimeStamp();
			if (StringUtil.isNotNull(beginTimeStamp_str)) {
				info.setWhereBlock(
						"sysOrgRoleConf.fdRoleLineAlterTime>=:beginTime");
				info.setParameter("beginTime", timeConvert(beginTimeStamp_str));
			}
		}
		SysSynchroOrgResult orgResult = new SysSynchroOrgResult();
		orgResult.setReturnState(OPT_ORG_STATUS_NOOPT);
		List roleConfList = sysOrgRoleConfService.findList(info);
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("通用角色线配置信息共计" + roleConfList.size() + "条。");
		for (int i = 0; i < roleConfList.size(); i++) {
			SysOrgRoleConf sysOrgRoleConf = (SysOrgRoleConf) roleConfList
					.get(i);
			JSONObject jsonObj = setOrgRoleConfMember(sysOrgRoleConf);
			count++;
			jsonArr.add(jsonObj);
		}
		orgResult.setCount(count);
		orgResult.setMessage(jsonArr.toJSONString());
		orgResult.setReturnState(OPT_ORG_STATUS_SUCCESS);
		logger.debug("角色线成员读取结束。");
		return orgResult;
	}

	private ISysWsOrgService sysWsOrgService;

	public void setSysWsOrgService(ISysWsOrgService sysWsOrgService) {
		this.sysWsOrgService = sysWsOrgService;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/findByExtendPara", method = RequestMethod.POST)
	public SysSynchroOrgResult findByExtendPara(@RequestBody SysSynchroGetOrgInfoContextV2 orgContext) throws Exception {
		SysSynchroOrgResult result = new SysSynchroOrgResult();
		try{
			String param = orgContext.getExtendPara();
			if(StringUtil.isNotNull(param)){
				// 查询组织数据
				SysOrgElement element = sysWsOrgService.findSysOrgElement(param);
				JSONObject jsonObject = new JSONObject();
				setOrgBaseInfo(jsonObject,element);
				result.setMessage(jsonObject.toJSONString());
				result.setReturnState(OPT_ORG_STATUS_SUCCESS);
				result.setCount(1);
			}else{
				result.setReturnState(OPT_ORG_STATUS_NOOPT);
				logger.debug("查询参数为空");
			}
		}catch (Exception e){
			logger.debug("根据登录名查询用户信息失败：",e);
		}
		return result;
	}

	private JSONObject setOrgRoleConfMember(SysOrgRoleConf sysOrgRoleConf)
			throws Exception {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("confId", sysOrgRoleConf.getFdId());
		JSONArray membersArray = new JSONArray();

		HQLInfo info = new HQLInfo();
		info.setSelectBlock("fdId");
		info.setWhereBlock("sysOrgRoleConf.fdId=:confId");
		info.setParameter("confId", sysOrgRoleConf.getFdId());
		List<String> members = sysOrgRoleLineService.findValue(info);
		for (String member : members) {
			membersArray.add(member);
		}
		jsonObj.put("members", membersArray);

		return jsonObj;
	}

	@Override
	public SysSynchroOrgResult getUpdatedElementsCount(SysSynchroGetOrgInfoContext orgContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
