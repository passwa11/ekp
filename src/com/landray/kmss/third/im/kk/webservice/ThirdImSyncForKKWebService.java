package com.landray.kmss.third.im.kk.webservice;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.authentication.ssoclient.SSOClientConstant;
import com.landray.kmss.sys.config.action.SysConfigAdminUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.notify.constant.SysNotifyConstants;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.model.SysNotifyTodoDoneInfo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoDoneInfoService;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.notify.webservice.NotifyTodoAppResult;
import com.landray.kmss.sys.notify.webservice.NotifyTodoGetContext;
import com.landray.kmss.sys.notify.webservice.NotifyTodoGetCountContext;
import com.landray.kmss.sys.notify.webservice.SysNotifyTodoWebServiceConstant;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationVisible;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsOrgService;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceMainService;
import com.landray.kmss.third.im.kk.util.PluginUtil;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaRowsPerPageConfig;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.third.pda.service.spring.PdaModuleSelectDialog;
import com.landray.kmss.util.*;
import com.landray.kmss.web.annotation.RestApi;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 提供对kk的接口
 * @author 孙佳
 * @date 2017年8月14日
 */
@Controller
@RestApi(docUrl = "/third/im/kk/restservice/kk_help.jsp",
		name = "thirdImSyncForKKRestService",
		resourceKey = "KK集成restservice服务")
@RequestMapping(value = "/api/third-im-kk/thirdImSyncForKKWebService",
		method = RequestMethod.POST)
public class ThirdImSyncForKKWebService
		implements IThirdImSyncForKKWebService, SysNotifyTodoWebServiceConstant, SysOrgConstant {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdImSyncForKKWebService.class);

	public IPdaModuleConfigMainService pdaModuleConfigMainService;

	public void setPdaModuleConfigMainService(IPdaModuleConfigMainService pdaModuleConfigMainService) {
		this.pdaModuleConfigMainService = pdaModuleConfigMainService;
	}

	public ISysOrganizationVisibleService sysOrganizationVisibleService;

	public void setSysOrganizationVisibleService(ISysOrganizationVisibleService sysOrganizationVisibleService) {
		this.sysOrganizationVisibleService = sysOrganizationVisibleService;
	}

	public ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public ISysWebserviceMainService sysWebserviceMainService;

	public void setSysWebserviceMainService(ISysWebserviceMainService sysWebserviceMainService) {
		this.sysWebserviceMainService = sysWebserviceMainService;
	}

	private ISysNotifyTodoDoneInfoService sysNotifyTodoDoneInfoService;

	public void setSysNotifyTodoDoneInfoService(ISysNotifyTodoDoneInfoService sysNotifyTodoDoneInfoService) {
		this.sysNotifyTodoDoneInfoService = sysNotifyTodoDoneInfoService;
	}

	private ISysNotifyTodoService sysNotifyTodoService;

	public void setSysNotifyTodoService(ISysNotifyTodoService sysNotifyTodoService) {
		this.sysNotifyTodoService = sysNotifyTodoService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private ISysWsOrgService sysWsOrgService;

	public void setSysWsOrgService(ISysWsOrgService sysWsOrgService) {
		this.sysWsOrgService = sysWsOrgService;
	}

	private ISysAppConfigService sysAppConfigService;

	public void setSysAppConfigService(ISysAppConfigService sysAppConfigService) {
		this.sysAppConfigService = sysAppConfigService;
	}

	/**
	 * 4.1 ekp握手接口（say hello）
	 */
	@Override
    @ResponseBody
	@RequestMapping("/sayHello")
	public ThirdImKKSyncResult sayHello() {
		ThirdImKKSyncResult thirdImKKSyncResult = new ThirdImKKSyncResult();
		thirdImKKSyncResult.setReturnState(2);
		thirdImKKSyncResult.setMessage("");
		return thirdImKKSyncResult;
	}


	/**
	 * 4.3 PC应用导出接口
	 */
	@Override
    @ResponseBody
	@RequestMapping("/getEkpAppInfo")
	public ThirdImKKSyncResult getEkpAppInfo() {
		ThirdImKKSyncResult thirdImKKSyncResult = new ThirdImKKSyncResult();
		thirdImKKSyncResult.setReturnState(2);
		try {
			String kkPcAppInit = "/third/im/kk/resource/json/kk_pc_app_init.json";
			String ekpAPP = FileUtil.getFileString(PluginConfigLocationsUtil.getWebContentPath() + kkPcAppInit,
					"UTF-8");

			JSONArray resultArray = new JSONArray();
			JSONObject resultObject = null;
			JSONObject nameObject = null;
			JSONObject categoryObject = null;
			JSONObject detailObject = null;

			JSONArray array = JSONArray.fromObject(ekpAPP);

			for(int system=0; system<array.size();system++){
				JSONObject json = JSONObject.fromObject(array.get(system));
				JSONArray systemArray = JSONArray.fromObject(json.get("data"));
				for(int module= 0; module<systemArray.size(); module++){
					JSONObject object = JSONObject.fromObject(systemArray.get(module));
					resultObject = new JSONObject();
					nameObject = new JSONObject();
					categoryObject = new JSONObject();
					nameObject.put("zh-CN", object.get("name"));
					categoryObject.put("zh-CN", object.get("category"));

					resultObject.put("id", object.get("id"));
					resultObject.put("name", nameObject);
					resultObject.put("url", object.get("url"));
					resultObject.put("icon", object.get("icon"));
					resultObject.put("category", categoryObject);
					if (null != object.get("todo") && null != object.get("read")) {
						detailObject = new JSONObject();
						detailObject.put("todo", object.get("todo"));
						detailObject.put("read", object.get("read"));
						resultObject.put("detail", detailObject);
					}
					resultArray.add(resultObject);
				}
			}
			thirdImKKSyncResult.setMessage(resultArray.toString());

			logger.info("PC应用导出接口" + thirdImKKSyncResult);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return thirdImKKSyncResult;

	}

	/**
	 * 4.4 移动应用导出接口
	 */
	@Override
    @ResponseBody
	@RequestMapping("/getEkpMobileInfo")
	public ThirdImKKSyncResult getEkpMobileInfo() {
		ThirdImKKSyncResult thirdImKKSyncResult = new ThirdImKKSyncResult();
		thirdImKKSyncResult.setReturnState(2);
		JSONArray message = new JSONArray();
		JSONArray readersArray = new JSONArray();
		JSONObject resultObject = null;
		JSONObject readersObject = null;
		JSONObject categoryObject = null;
		JSONObject detailObject = null;
		JSONObject nameObject = null;
		try {
			List<PdaModuleConfigMain> list = pdaModuleConfigMainService.findList(new HQLInfo());

			for (PdaModuleConfigMain configMain : list) {
				if ("1".equals(configMain.getFdStatus())) {
					resultObject = new JSONObject();
					categoryObject = new JSONObject();
					nameObject = new JSONObject();
					resultObject.put("id", configMain.getFdId());
					nameObject.put("zh-CN", StringUtil.isNotNull(
							configMain.getDynamicMap().get("fdNameCN"))
									? configMain.getDynamicMap().get("fdNameCN")
									: configMain.getFdName());
					nameObject.put("en-US", StringUtil.isNotNull(configMain.getDynamicMap().get("fdNameUS")) ? configMain.getDynamicMap().get("fdNameUS") : "");
					//繁体名称
					nameObject.put("zh-TW", StringUtil.isNotNull(configMain.getDynamicMap().get("fdNameHK"))
							? configMain.getDynamicMap().get("fdNameHK") : "");
					resultObject.put("name", nameObject);
					String url = configMain.getFdSubDocLink();
					if (StringUtil.isNull(url)) {
						url = "/" + configMain.getFdUrlPrefix() + "/mobile/index.jsp";
					}
					resultObject.put("url", url);
					resultObject.put("icon", configMain.getFdIconUrl());
					//如果是待办服务  获取webservice地址
					if (StringUtil.isNotNull(configMain.getFdUrlPrefix()) && "sys/notify".equals(configMain.getFdUrlPrefix())) {
						detailObject = new JSONObject();
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setSelectBlock("fdAddress");
						hqlInfo.setWhereBlock("fdServiceBean = 'sysNotifyTodoWebService'");
						String fdAddress = (String) sysWebserviceMainService.findFirstOne(hqlInfo);
						if (StringUtils.isBlank(fdAddress)) {
							thirdImKKSyncResult.setReturnState(2);
							thirdImKKSyncResult.setMessage("没有配置待办的webservice");
							return thirdImKKSyncResult;
							//throw new Exception();
						}
						detailObject.put("url", "/sys/webservice" + fdAddress + "?wsdl");
						resultObject.put("detail", detailObject);
					}
					categoryObject.put("zh-CN",
							null != configMain.getFdModuleCate() ? configMain.getFdModuleCate().getFdName() : "");
					categoryObject.put("en-US", null != configMain.getFdModuleCate() ? configMain.getFdModuleCate().getDynamicMap().get("fdNameUS") : "");
					resultObject.put("category", categoryObject);
					List<SysOrgElement> orgList = configMain.getAuthReaders();
					readersArray = new JSONArray();
					for (SysOrgElement orgElement : orgList) {
						readersObject = new JSONObject();
						readersObject.put("id", orgElement.getFdId());

						readersObject.put("type", getOrgType(orgElement.getFdOrgType()));
						readersArray.add(readersObject);
					}
					resultObject.put("readers", readersArray);
					message.add(resultObject);
				}
			}

			thirdImKKSyncResult.setMessage(message.toString());

			logger.info("移动应用导出接口" + thirdImKKSyncResult);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return thirdImKKSyncResult;
	}

	/**
	 * 4.5 SSO配置导出接口
	 */
	@Override
    @ResponseBody
	@RequestMapping("/getSSOInfo")
	public ThirdImKKSyncResult getSSOInfo() {
		ThirdImKKSyncResult thirdImKKSyncResult = new ThirdImKKSyncResult();
		JSONObject message = new JSONObject();
		JSONObject config = new JSONObject();
		boolean enable = true;
		try {
			String filePath = ConfigLocationsUtil.getWebContentPath() + "/WEB-INF";
			//获取SSO配置
			Map<String, String> ssoConfig = readFile(filePath + SSOClientConstant.CONFIG_FILE_PATH);
			//获取密钥
			Map<String, String> ssoTOkenConfig = readFile(filePath + SSOClientConstant.TOKEN_KEY_FILE_PATH);

			thirdImKKSyncResult.setReturnState(2);
			if(StringUtil.isNull(ssoTOkenConfig.get("instance.class"))){
				thirdImKKSyncResult.setReturnState(1);
				thirdImKKSyncResult.setMessage("ekp没有配置SSO!");
				return thirdImKKSyncResult;
			}
			String cookieName = null;
			String type = ssoTOkenConfig.get("instance.class");
			String security = ssoTOkenConfig.get("domino.security.key");
			String publicSecurity = ssoTOkenConfig.get("security.key.public");
			String privateSecurity = ssoTOkenConfig.get("security.key.private");
			config.put("type", type);
			config.put("domain", ssoTOkenConfig.get("cookie.domain"));
			config.put("maxAge", ssoTOkenConfig.get("cookie.maxAge"));
			if ("LtpaTokenGenerator".equals(type)) {
				cookieName = ssoTOkenConfig.get("domino.cookie.name");
			} else if ("LRTokenGenerator".equals(type)) {
				cookieName = ssoTOkenConfig.get("cookie.name");
			}
			config.put("cookieName", cookieName);
			config.put("security", StringUtil.isNull(security) ? "" : security);
			config.put("publicSecurity", StringUtil.isNull(publicSecurity) ? "" : publicSecurity);
			config.put("privateSecurity", StringUtil.isNull(privateSecurity) ? "" : privateSecurity);
			config.put("userKey", ssoTOkenConfig.get("domino.user.key"));

		} catch (Exception e) {
			enable = false;
			thirdImKKSyncResult.setReturnState(2);
			thirdImKKSyncResult.setMessage("ekp没有配置SSO!");
		}
		message.put("enable", enable);
		message.put("config", config);
		thirdImKKSyncResult.setMessage(message.toString());

		logger.info("SSO配置导出接口" + thirdImKKSyncResult);
		return thirdImKKSyncResult;
	}

	/**
	 * 读取文件完整信息
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	private Map<String, String> readFile(String filePath) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		Properties prop = new Properties();
		//InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		//prop.load(in); ///加载属性列表
		SysConfigAdminUtil.loadProperties(prop, SysConfigAdminUtil.loadTokenKeyFileProperties(filePath,SysConfigAdminUtil.isEncryptEnabled()), null);

		Iterator<String> it = prop.stringPropertyNames().iterator();
		while (it.hasNext()) {
			String key = it.next();
			System.out.println(key + ":" + prop.getProperty(key));
			map.put(key, prop.getProperty(key));
		}
		//in.close();

		return map;
	}


	/**
	 * 4.6 可见性导出接口
	 */
	@Override
    @ResponseBody
	@RequestMapping("/getOrgSyncCfgInfo")
	public ThirdImKKSyncResult getOrgSyncCfgInfo() {
		ThirdImKKSyncResult thirdImKKSyncResult = new ThirdImKKSyncResult();
		JSONObject resultObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		JSONArray sourcesArray = null;
		JSONArray targetsArray = null;
		JSONObject sources = null;
		JSONObject targets = null;
		thirdImKKSyncResult.setReturnState(2);
		try {
			SysOrgConfig orgConfig = new SysOrgConfig();
			String isOrgVisibleEnable = orgConfig.getOrgVisibleEnable();
			String defaultVisibleLevel = orgConfig.getDefaultVisibleLevel();

			List<SysOrganizationVisible> sysOrganizationVisibles = sysOrganizationVisibleService.findList(new HQLInfo());
			for (SysOrganizationVisible organizationVisible : sysOrganizationVisibles) {
				sourcesArray = new JSONArray();
				targetsArray = new JSONArray();
				for (SysOrgElement sysOrgElement : organizationVisible.getVisiblePrincipals()) {
					sources = new JSONObject();
					sources.put("id", sysOrgElement.getFdId());
					sources.put("type", getOrgType(sysOrgElement.getFdOrgType()));
					sourcesArray.add(sources);
				}

				jsonObject.put("sources", sourcesArray);

				for (SysOrgElement orgElement : organizationVisible.getVisibleSubordinates()) {
					targets = new JSONObject();
					targets.put("id", orgElement.getFdId());
					targets.put("type", getOrgType(orgElement.getFdOrgType()));
					targetsArray.add(targets);
				}
				jsonObject.put("targets", targetsArray);
				jsonArray.add(jsonObject);
			}

			resultObject.put("enable", Boolean.valueOf(isOrgVisibleEnable));
			resultObject.put("level", defaultVisibleLevel);
			resultObject.put("excepts", jsonArray);
			thirdImKKSyncResult.setMessage(resultObject.toString());

			logger.info("可见性导出接口" + thirdImKKSyncResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return thirdImKKSyncResult;
	}

	/**
	 * 4.7 人员联系方式修改接口
	 */
	@Override
    @ResponseBody
	@RequestMapping("/updateOrgInfo")
	public ThirdImKKSyncResult updateOrgInfo(@RequestBody ThirdImKKOrgSyncContext thirdImKKOrgSyncContext) {
		ThirdImKKSyncResult thirdImKKSyncResult = new ThirdImKKSyncResult();
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			if (StringUtil.isNull(thirdImKKOrgSyncContext.getOrgInfo())) {
				thirdImKKSyncResult.setReturnState(1);
				thirdImKKSyncResult.setMessage("请输入正确的参数!");
				return thirdImKKSyncResult;
			}
			JSONArray orgInfoArray = JSONArray.fromObject(thirdImKKOrgSyncContext.getOrgInfo());

			for (int array = 0; array < orgInfoArray.size(); array++) {
				JSONObject orgInfoObject = JSONObject.fromObject(orgInfoArray.get(array));
				JSONObject org = JSONObject.fromObject(orgInfoObject.get("org"));
				JSONObject info = JSONObject.fromObject(orgInfoObject.get("info"));
				logger.info(org.toString());
				logger.info(info.toString());
				//更新ekp人员联系方式
				SysOrgPerson orgPerson = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(org.get("id").toString());

				if (null != info) {
					String workPhone = info.get("workPhone").toString();
					String mobileNo = info.get("mobileNo").toString();
					String shortNo = info.get("shortNo").toString();
					String aliasName = info.get("aliasName").toString();
					if (StringUtil.isNotNull(workPhone)) {
						orgPerson.setFdWorkPhone(workPhone);
					}
					if (StringUtil.isNotNull(mobileNo)) {
						orgPerson.setFdMobileNo(mobileNo);
					}
					if (StringUtil.isNotNull(shortNo)) {
						orgPerson.setFdShortNo(shortNo);
					}
					//花名
					if (StringUtil.isNotNull(aliasName)) {
						orgPerson.setFdNickName(aliasName);
					}
				}
				sysOrgPersonService.update(orgPerson);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		thirdImKKSyncResult.setReturnState(2);
		thirdImKKSyncResult.setMessage("");
		return thirdImKKSyncResult;
	}

	private String getOrgType(int orgType) {
		String type = null;
		switch (orgType) {
		case 1:
			type = "org";
			break;
		case 2:
			type = "dept";
			break;
		case 4:
			type = "post";
			break;
		case 8:
			type = "person";
			break;
		case 10:
			type = "group";
			break;
		default:
			break;
		}
		return type;
	}

	/**
	 * 4.8 获取待办信息(消息接口增加锁字段，如果有锁表示移动端无法打开，获取模块是否配置了移动端)
	 */
	@Override
    @ResponseBody
	@RequestMapping("/getTodo")
	public NotifyTodoAppResult getTodo(@RequestBody NotifyTodoGetContext todoContext) throws Exception {
		NotifyTodoAppResult result = new NotifyTodoAppResult();
		if (!checkNullIfNecessary(todoContext, METHOD_CONSTANT_NAME_GETTODO, result)) {
			result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
			return result;
		}
		String whereBlock = "";
		HQLInfo info = new HQLInfo();
		List<HQLParameter> paramList = new ArrayList<HQLParameter>();
		List targetsList = sysOrgCoreService.expandToPersonIds(parseOrgToPersons(todoContext.getTargets()));
		if (targetsList != null && !targetsList.isEmpty()) {
			whereBlock += " and sysNotifyTodo.fdDeleteFlag is null and "
					+ HQLUtil.buildLogicIN("sysNotifyTodo.hbmTodoTargets.fdId ", targetsList);
		} else {
			result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
			result.setMessage(ResourceUtil.getString("sysNotifyTodo.webservice.warning.org", "sys-notify", null,
					new Object[] { todoContext.getTargets() }));
			logger.debug("参数信息中,待办所属人信息解析为空.传入信息为:" + todoContext.getTargets());
			return result;
		}
		int type = todoContext.getType();
		if (type == SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL || type == SysNotifyConstant.NOTIFY_TODOTYPE_ONCE
				|| type == SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL_SUSPEND
				|| type == SysNotifyConstants.NOTIFY_TODOTYPE_MANUALSUSPEND) {
			if (type == SysNotifyConstants.NOTIFY_TODOTYPE_MANUALSUSPEND) {
				List<Integer> _typeList = new ArrayList<Integer>();
				_typeList.add(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
				_typeList.add(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL_SUSPEND);
				whereBlock += " and " + HQLUtil.buildLogicIN("sysNotifyTodo.fdType ", _typeList);
			} else {
				whereBlock += getTodoSqlBlock("type", type, paramList);
			}
		}
		String otherwhereBlock = null;
		// 设置已办的where查询语句
		if (type == SysNotifyConstants.NOTIFY_TODOTYPE_MANUAL_DONE) {
			whereBlock = " and sysNotifyTodoDoneInfo.todo.fdDeleteFlag is null and "
					+ HQLUtil.buildLogicIN("sysNotifyTodoDoneInfo.orgElement.fdId ", targetsList);
		}
		otherwhereBlock = getWhereBlockFromJson(todoContext.getOtherCond(), paramList, type);
		whereBlock += StringUtil.isNotNull(otherwhereBlock) ? (" and " + otherwhereBlock) : "";
		whereBlock = whereBlock.substring(5);

		info.setWhereBlock(whereBlock);
		if (type == SysNotifyConstants.NOTIFY_TODOTYPE_MANUAL_DONE) {
			info.setOrderBy("sysNotifyTodoDoneInfo.fdFinishTime desc");
			info.setDistinctType(HQLInfo.DISTINCT_YES);
		} else {
			info.setOrderBy("sysNotifyTodo.fdCreateTime desc");
		}
		info.setParameter(paramList);
		info.setRowSize(todoContext.getRowSize() > 0 ? todoContext.getRowSize() : SysConfigParameters.getRowSize());
		int pageNo = todoContext.getPageNo();
		info.setPageNo(pageNo > 0 ? pageNo : 1);
		Page page = null;
		if (type == SysNotifyConstants.NOTIFY_TODOTYPE_MANUAL_DONE) {
			page = sysNotifyTodoDoneInfoService.findPage(info);
			sysNotifyTodoDoneInfoService.clearHibernateSession();
		} else {
			page = sysNotifyTodoService.findPage(info);
			sysNotifyTodoService.clearHibernateSession();
		}
		result.setMessage(formatRecordData(page));
		result.setReturnState(RETURN_CONSTANT_STATUS_SUCESS);
		return result;
	}

	private String formatRecordData(Page queryPage) throws Exception {
		JSONObject viewObj = new JSONObject();
		if (queryPage == null || queryPage.getTotalrows() < 1) {
			viewObj.accumulate("errorPage", "true");
			viewObj.accumulate("message", ResourceUtil.getString("return.noRecord"));
			return viewObj.toString();
		}
		viewObj.accumulate("pageCount", queryPage.getTotal()); // 所有页数
		viewObj.accumulate("pageno", queryPage.getPageno()); // 当前页码
		viewObj.accumulate("count", queryPage.getTotalrows()); // 文档总数

		JSONArray docArr = new JSONArray();
		for (Object notifyModel : queryPage.getList()) {
			JSONObject model = new JSONObject();
			SysNotifyTodo todo = null;
			if (notifyModel instanceof SysNotifyTodoDoneInfo) {
				todo = ((SysNotifyTodoDoneInfo) notifyModel).getTodo();
			} else {
				todo = (SysNotifyTodo) notifyModel;
			}
			model.accumulate("id", todo.getFdId());
			model.accumulate("subject", todo.getSubject4View());
			model.accumulate("type", todo.getFdType());
			model.accumulate("key", todo.getFdKey());
			model.accumulate("param1", todo.getFdParameter1());
			model.accumulate("param2", todo.getFdParameter2());
			model.accumulate("appName", todo.getFdAppName());
			model.accumulate("modelName", todo.getFdModelName());
			//消息列表增加标识待办紧急程度
			model.accumulate("level", String.valueOf(todo.getFdLevel()));
			// 获取模块名称
			SysDictModel sysDict = SysDataDict.getInstance().getModel(todo.getFdModelName());
			model.accumulate("moduleName", sysDict == null ? "" : ResourceUtil.getString(sysDict.getMessageKey()));
			model.accumulate("moduleName_US",
					sysDict == null ? "" : ResourceUtil.getStringValue(sysDict.getMessageKey(), null, Locale.US));
			//繁体名称
			model.put("moduleName_TW", sysDict == null ? ""
					: ResourceUtil.getStringValue(sysDict.getMessageKey(), null, new Locale("zh", "HK")));
			model.accumulate("modelId", todo.getFdModelId());
			model.accumulate("createTime",
					DateUtil.convertDateToString(todo.getFdCreateTime(), DATE_TIME_FORMAT_STRING));
			if (todo.getDocCreator() != null) {
				SysOrgPerson creator = todo.getDocCreator();
				model.accumulate("creator", creator.getFdLoginName());
				model.accumulate("creatorName", creator.getFdNameOri());
				model.put("langProps", creator.getDynamicMap());
			}
			if (isLock(todo.getFdLink())) {
//				if (notifyModel instanceof SysNotifyTodoDoneInfo) {
//					model.accumulate("link", todo.getFdLink());
//				} else {
					model.accumulate("link",
							"/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=" + todo.getFdId());
//				}
				model.accumulate("isLock", false);
			} else {
				//加锁
				model.accumulate("isLock", true);
				model.accumulate("link", "");
			}
			docArr.element(model);
		}
		viewObj.accumulate("docs", docArr);
		return viewObj.toString();
	}

	private String getWhereBlockFromJson(String jsonStr, List<HQLParameter> hqlParam, int type) {
		String whereBlock = "";
		if (StringUtil.isNull(jsonStr)) {
			return null;
		}
		JSONArray condArr = JSONArray.fromObject(jsonStr);
		for (int i = 0; i < condArr.size(); i++) {
			JSONObject element = (JSONObject) condArr.get(i);
			if (element.containsKey("subject")) {
				if (SysNotifyConstants.NOTIFY_TODOTYPE_MANUAL_DONE == type) {
					whereBlock += getTodoneSqlBlock("subject", (String) element.get("subject"), hqlParam);
				} else {
					whereBlock += getTodoSqlBlock("subject", (String) element.get("subject"), hqlParam);
				}
			}
			if (element.containsKey("link")) {
				if (SysNotifyConstants.NOTIFY_TODOTYPE_MANUAL_DONE == type) {
					whereBlock += getTodoneSqlBlock("link", (String) element.get("link"), hqlParam);
				} else {
					whereBlock += getTodoSqlBlock("link", (String) element.get("link"), hqlParam);
				}
			}
			if (element.containsKey("key")) {
				if (SysNotifyConstants.NOTIFY_TODOTYPE_MANUAL_DONE == type) {
					whereBlock += getTodoneSqlBlock("key", (String) element.get("key"), hqlParam);
				} else {
					whereBlock += getTodoSqlBlock("key", (String) element.get("key"), hqlParam);
				}
			}
			if (element.containsKey("param1")) {
				if (SysNotifyConstants.NOTIFY_TODOTYPE_MANUAL_DONE == type) {
					whereBlock += getTodoneSqlBlock("parameter1", (String) element.get("param1"), hqlParam);
				} else {
					whereBlock += getTodoSqlBlock("parameter1", (String) element.get("param1"), hqlParam);
				}
			}
			if (element.containsKey("param2")) {
				if (SysNotifyConstants.NOTIFY_TODOTYPE_MANUAL_DONE == type) {
					whereBlock += getTodoneSqlBlock("parameter2", (String) element.get("param2"), hqlParam);
				} else {
					whereBlock += getTodoSqlBlock("parameter2", (String) element.get("param2"), hqlParam);
				}
			}
			if (element.containsKey("appName")) {
				if (SysNotifyConstants.NOTIFY_TODOTYPE_MANUAL_DONE == type) {
					whereBlock += getTodoneSqlBlock("appName", (String) element.get("appName"), hqlParam);
				} else {
					whereBlock += getTodoSqlBlock("appName", (String) element.get("appName"), hqlParam);
				}
			}
			if (element.containsKey("modelName")) {
				if (SysNotifyConstants.NOTIFY_TODOTYPE_MANUAL_DONE == type) {
					whereBlock += getTodoneSqlBlock("modelName", (String) element.get("modelName"), hqlParam);
				} else {
					whereBlock += getTodoSqlBlock("modelName", (String) element.get("modelName"), hqlParam);
				}
			}
			if (element.containsKey("modelId")) {
				if (SysNotifyConstants.NOTIFY_TODOTYPE_MANUAL_DONE == type) {
					whereBlock += getTodoneSqlBlock("modelId", (String) element.get("modelId"), hqlParam);
				} else {
					whereBlock += getTodoSqlBlock("modelId", (String) element.get("modelId"), hqlParam);
				}
			}
		}
		return StringUtil.isNotNull(whereBlock) ? whereBlock.substring(5) : "";
	}

	private String getTodoneSqlBlock(String keyword, Object value, List<HQLParameter> hqlParam) {
		return getTodoneSqlBlock(keyword, value, "=", keyword, hqlParam);
	}

	private String getTodoneSqlBlock(String keyword, Object value, String optStr, String extendKey,
			List<HQLParameter> hqlParam) {
		hqlParam.add(new HQLParameter(extendKey, value));
		String key = keyword.substring(0, 1).toUpperCase() + keyword.substring(1);
		return " and sysNotifyTodoDoneInfo.todo.fd" + key + optStr + ":" + extendKey;
	}

	private String getTodoSqlBlock(String keyword, Object value, List<HQLParameter> hqlParam) {
		return getTodoSqlBlock(keyword, value, "=", keyword, hqlParam);
	}

	private String getTodoSqlBlock(String keyword, Object value, String optStr, String extendKey,
			List<HQLParameter> hqlParam) {
		hqlParam.add(new HQLParameter(extendKey, value));
		String key = keyword.substring(0, 1).toUpperCase() + keyword.substring(1);
		return " and sysNotifyTodo.fd" + key + optStr + ":" + extendKey;
	}

	private List<?> parseOrgToPersons(String jsonPerson) throws Exception {
		if (StringUtil.isNull(jsonPerson)) {
			return null;
		}
		List orgList = null;
		if (jsonPerson.indexOf("[") > -1) {
			orgList = sysWsOrgService.findSysOrgList(jsonPerson);
		} else {
			orgList = new ArrayList();
			SysOrgElement tmpOrg = sysWsOrgService.findSysOrgElement(jsonPerson);
			if (tmpOrg != null) {
				orgList.add(tmpOrg);
			}
		}
		boolean isPerson = true;
		if (orgList != null && !orgList.isEmpty()) {
			List expendList = new ArrayList();
			List personList = new ArrayList();
			for (int i = 0; i < orgList.size(); i++) {
				SysOrgElement org = (SysOrgElement) orgList.get(i);
				if (org.getFdOrgType() != ORG_TYPE_PERSON) {
					expendList.add(org);
					isPerson = false;
				} else {
					personList.add(sysOrgCoreService.format(org));
				}
			}
			if (!isPerson) {
				expendList = sysOrgCoreService.expandToPerson(expendList);
			}
			if (!expendList.isEmpty()) {
				personList.addAll(expendList);
				orgList = personList;
			}
		}
		return orgList;
	}

	private boolean checkNullIfNecessary(Object todoContext, String methodKey, NotifyTodoAppResult result)
			throws Exception {
		if (null == todoContext) {
			result.setMessage(ResourceUtil.getString("sysNotifyTodo.webservice.warning.class", "sys-notify"));
			logger.debug("待办上下文为空!");
			return false;
		}
		String fields = "";
		if (METHOD_CONSTANT_NAME_SEND.equalsIgnoreCase(methodKey)) {
			fields = "modelId;subject;link;type;targets;createTime";
		} else if (METHOD_CONSTANT_NAME_DELETE.equalsIgnoreCase(methodKey)) {
			fields = "modelId;optType";
		} else if (METHOD_CONSTANT_NAME_SETDONE.equalsIgnoreCase(methodKey)) {
			fields = "modelId;optType";
		} else if (METHOD_CONSTANT_NAME_UPDATETODO.equalsIgnoreCase(methodKey)) {
			fields = "modelId;subject;link;type";
		} else if (METHOD_CONSTANT_NAME_GETTODO.equalsIgnoreCase(methodKey)) {
			fields = "targets";
		} else if (METHOD_CONSTANT_NAME_GETTODOCOUNT.equalsIgnoreCase(methodKey)) {
			fields = "target";
		}

		String[] fileArr = fields.split(";");
		for (int i = 0; i < fileArr.length; i++) {
			if (isNullProperty(todoContext, fileArr[i])) {
				String filedName = ResourceUtil.getString("sysNotifyTodo.webservice." + fileArr[i], "sys-notify");
				result.setMessage(ResourceUtil.getString("sysNotifyTodo.webservice.warning.property", "sys-notify",
						null, new Object[] { methodKey, filedName }));
				logger.debug("方法" + methodKey + "中,不允许待办上下文中\"" + filedName + "\"信息为空!");
				return false;
			}
		}
		return true;
	}

	private boolean isNullProperty(Object obj, String name) throws Exception {
		Object tmpObj = PropertyUtils.getProperty(obj, name);
		if (tmpObj instanceof String) {
			return StringUtil.isNull((String) tmpObj) || "null".equalsIgnoreCase((String) tmpObj);
		} else if (tmpObj instanceof Integer) {
			return ((Integer) tmpObj) == 0;
		} else {
			return tmpObj == null;
		}
	}

	/**
	 * <p>检查是否加锁</p>
	 * @return
	 * @author 孙佳
	 * @throws Exception 
	 */
	private boolean isLock(String fdLink) throws Exception {
		boolean isDisplay = false;
		String[] extendArr = (new PdaRowsPerPageConfig()).getFdExtendsUrl();
		PdaModuleSelectDialog pdaModuleSelectDialog = ((PdaModuleSelectDialog)SpringBeanUtil.getBean("pdaModuleSelectDialog"));
		String allowModules = pdaModuleSelectDialog.getAllowModules();
		Boolean allowAllModuleFlag = pdaModuleSelectDialog.isAllowAllModuleFlag();
		List<String> enabledModuleList = new ArrayList<String>();
		if (!allowAllModuleFlag) {
			if (StringUtil.isNotNull(allowModules)) {
				String[] enabledModuleArray = allowModules.split("\\s*[,;]\\s*");
				if (enabledModuleArray != null) {
					enabledModuleList = Arrays.asList(enabledModuleArray);
				}
			}
		}
		//异系统解锁匹配
		if (StringUtil.isNotNull(fdLink) && extendArr != null) {
			for (int i = 0; i < extendArr.length; i++) {
				if (StringUtil.isNotNull(extendArr[i])
						&& fdLink.toLowerCase().indexOf(extendArr[i].toLowerCase()) > -1) {
					isDisplay = true;
					break;
				}
			}
		}
		//模块前缀匹配
		if (!isDisplay && StringUtil.isNotNull(fdLink) && !enabledModuleList.isEmpty() && fdLink.startsWith("/")) {
			Iterator<String> ite = enabledModuleList.iterator();
			while (ite.hasNext()) {
				String urlPrefix = ite.next();
				if (allowAllModuleFlag || StringUtil.isNotNull(urlPrefix)
						&& fdLink.toLowerCase().indexOf(urlPrefix.toLowerCase()) > -1) {
					isDisplay = true;
					break;
				}
			}
		}

		return isDisplay;
	}

	/**
	 * 获取用户待办数
	 */
	@Override
    @ResponseBody
	@RequestMapping("/getTodoCount")
	public NotifyTodoAppResult getTodoCount(@RequestBody NotifyTodoGetCountContext todoContext) throws Exception {
		NotifyTodoAppResult result = new NotifyTodoAppResult();
		if (!checkNullIfNecessary(todoContext, METHOD_CONSTANT_NAME_GETTODOCOUNT, result)) {
			result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
			return result;
		}

		SysOrgElement target = parseToPerson(todoContext.getTarget());
		if (target == null) {

			result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
			result.setMessage(ResourceUtil.getString("sysNotifyTodo.webservice.warning.org", "sys-notify", null,
					new Object[] { todoContext.getTarget() }));
			logger.debug("参数信息中,待办所属人信息解析为空.传入信息为:" + todoContext.getTarget());
			return result;
		}

		String types_str = todoContext.getTypes();
		List<Integer> types = new ArrayList<Integer>();

		if (StringUtil.isNotNull(types_str)) {
			JSONArray orgTypeArr = JSONArray.fromObject(types_str);
			for (int i = 0; i < orgTypeArr.size(); i++) {
				JSONObject typeObj = (JSONObject) orgTypeArr.get(i);
				int type = typeObj.getInt("type");
				types.add(type);
			}
		} else {
			types.add(0);
		}

		JSONArray array = new JSONArray();
		for (int type : types) {
			Long count = getTodoCount(target.getFdId(), type);
			JSONObject object = new JSONObject();
			object.accumulate(type + "", count);
			array.add(object);
		}

		result.setMessage(array.toString());
		result.setReturnState(RETURN_CONSTANT_STATUS_SUCESS);
		return result;
	}

	private SysOrgElement parseToPerson(String jsonPerson) throws Exception {
		if (StringUtil.isNull(jsonPerson)) {
			return null;
		}
		if (jsonPerson.indexOf("[") > -1) {
			return null;
		} else {
			SysOrgElement tmpOrg = sysWsOrgService.findSysOrgElement(jsonPerson);
			if (tmpOrg != null && tmpOrg.getFdOrgType() == 8) {
				return tmpOrg;
			}
		}
		return null;
	}

	private Long getTodoCount(String personId, int type) throws Exception {
		if (type == SysNotifyConstants.NOTIFY_TODOTYPE_MANUAL_DONE) {
			String whereBlock = null;
			HQLInfo info = new HQLInfo();
			whereBlock = "sysNotifyTodoDoneInfo.orgElement.fdId = :personId";
			info.setWhereBlock(whereBlock);
			info.setDistinctType(HQLInfo.DISTINCT_YES);
			info.setParameter("personId", personId);
			info.setSelectBlock("count(*)");

			int count = 0;
			count = sysNotifyTodoDoneInfoService.countTodoDoneInfo(info);
			sysNotifyTodoDoneInfoService.clearHibernateSession();

			return Long.parseLong(count + "");
		} else {
			Integer fdType = null;
			if (type > 0) {
				fdType = type;
			}
			Long count = sysNotifyTodoService.getTodoCount(personId, fdType);
			return count;
		}
	}

	/**
	 * 获取ekp智能助手配置
	 */
	@Override
    @ResponseBody
	@RequestMapping("/getEkpRobot")
	public ThirdImKKSyncResult getEkpRobot() {
		ThirdImKKSyncResult thirdImKKSyncResult = new ThirdImKKSyncResult();
		JSONObject message = new JSONObject();
		try {
			Map<String, String> config = sysAppConfigService
					.findByKey("com.landray.kmss.third.intell.model.IntellConfig");
			String itEnabled = config.get("itEnabled");
			if (StringUtil.isNotNull(itEnabled) && "true".equals(itEnabled)) {
				message.put("open", itEnabled);
				message.put("consoleUrl", config.get("itConfigUrl"));
				message.put("clientUrl", config.get("itUrl"));
			} else {
				message.put("open", "false");
			}
			thirdImKKSyncResult.setReturnState(2);
			thirdImKKSyncResult.setMessage(message.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return thirdImKKSyncResult;
	}

	/**
	 * 获取其它应用参数
	 */
	@Override
    @ResponseBody
	@RequestMapping("/getExtendApp")
	public ThirdImKKSyncResult getExtendApp() {
		ThirdImKKSyncResult thirdImKKSyncResult = new ThirdImKKSyncResult();
		JSONObject message = new JSONObject();
		try {
			Map<String, Map<String, String>> map = PluginUtil.getTransferMap();
			for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
				Map<String, String> mapValue = entry.getValue();
				String serviceName = mapValue.get(PluginUtil.SERVICE);
				String methodName = mapValue.get(PluginUtil.METHOD);
				Method method = ReflectionUtils.findMethod(SpringBeanUtil.getBean(serviceName).getClass(), methodName);
				Object result = ReflectionUtils.invokeMethod(method, SpringBeanUtil.getBean(serviceName), null);
				message.put(entry.getKey(), result);
			}
			thirdImKKSyncResult.setReturnState(2);
			thirdImKKSyncResult.setMessage(message.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return thirdImKKSyncResult;
	}

}
