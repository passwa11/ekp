package com.landray.kmss.third.ding.util;

import com.alibaba.fastjson.JSONArray;
import com.dingtalk.api.response.OapiProcessWorkrecordUpdateResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.appconfig.model.BaseAppconfigCache;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.ding.model.*;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.*;
import com.landray.kmss.third.ding.xform.util.ThirdDingXFormTemplateUtil;
import com.landray.kmss.util.*;
import com.opensymphony.util.BeanUtils;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DingUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingUtil.class);

	public static IOmsRelationService omsRelationService;

	public static IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			omsRelationService = (IOmsRelationService) SpringBeanUtil
					.getBean("omsRelationService");
		}
		return omsRelationService;
	}

	private static IThirdDingDinstanceXformService thirdDingDinstanceXformService;

	public static IThirdDingDinstanceXformService
			getThirdDingDinstanceXformService() {
		if (thirdDingDinstanceXformService == null) {
			thirdDingDinstanceXformService = (IThirdDingDinstanceXformService) SpringBeanUtil
					.getBean("thirdDingDinstanceXformService");
		}
		return thirdDingDinstanceXformService;
	}

	public static List<String> getAllReltion() {
		try {
			IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
					.getBean("omsRelationService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("omsRelationModel.fdEkpId ");
			hqlInfo.setWhereBlock("fdType=:fdType");
			hqlInfo.setParameter("fdType", "8");
			List<String> list = omsRelationService.findList(hqlInfo);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}


    public void setOmsRelationService(IOmsRelationService omsRelationService) {
		DingUtil.omsRelationService = omsRelationService;
	}

	private IThirdDingCategoryXformService thirdDingCategoryXformService;

	public IThirdDingCategoryXformService getThirdDingCategoryXformService() {
		if (thirdDingCategoryXformService == null) {
			thirdDingCategoryXformService = (IThirdDingCategoryXformService) SpringBeanUtil
					.getBean("thirdDingCategoryXformService");
		}
		return thirdDingCategoryXformService;
	}

	public void setThirdDingCategoryXformService(
			IThirdDingCategoryXformService thirdDingCategoryXformService) {
		this.thirdDingCategoryXformService = thirdDingCategoryXformService;
	}

	public static String getModelTemplateProperyId(IBaseModel baseModel, String property, Locale local)
			throws Exception {
		String fdTemplateId = null;
		String tempString = (String) getModelPropertyString(baseModel, property,
				"", local);
		if (StringUtil.isNotNull(tempString)) {
			Object fdTemplate = PropertyUtils.getProperty(baseModel, property);
			if (null != fdTemplate) {
				fdTemplateId = ((IBaseModel) fdTemplate).getFdId();
			}
		}
		return fdTemplateId;
	}

	public static Map<String, Object> getExtendDataModelInfo(IBaseModel baseModel) throws Exception {
		Map<String, Object> modelData = null;
		String extendDataXML = (String) getModelPropertyString(baseModel,
				"extendDataXML", "", null);
		if (StringUtil.isNotNull(extendDataXML)) {
			if (modelData == null) {
				if (StringUtil.isNull(extendDataXML)) {
					modelData = new HashMap<String, Object>();
				} else {
					List datas = ObjectXML.objectXMLDecoderByString(extendDataXML);
					if (datas != null && !datas.isEmpty()) {
						modelData = (Map) datas.get(0);
					} else {
						modelData = new HashMap<String, Object>();
					}
				}
			}
		}
		return modelData;
	}

	public static String getModelDocCreatorProperyValue(IBaseModel baseModel, String property, Locale local)
			throws Exception {
		String rtnStr = "";
		String tempString = (String) getModelPropertyString(baseModel, property,
				"", local);
		if (StringUtil.isNotNull(tempString)) {
			SysOrgPerson sysOrgPerson = (SysOrgPerson) PropertyUtils.getProperty(baseModel, property);
			if (sysOrgPerson != null) {
				rtnStr = sysOrgPerson.getFdLoginName();
			}
		}
		return rtnStr;
	}


	public static String getModelSimpleClassName(String modelName, String fdId) {
		try {
			SysDictModel sysDictModel = SysDataDict.getInstance().getModel(modelName);
			IBaseService baseService = (IBaseService) SpringBeanUtil.getBean(sysDictModel.getServiceBean());
			IBaseModel baseModel;

			baseModel = baseService.findByPrimaryKey(fdId);

			// 如果传入的是一个String类型的全类名，则不需要再解析了
			String rtnVal = baseModel.getClass().getSimpleName();
			int i = rtnVal.indexOf('$');
			if (i > -1) {
				rtnVal = rtnVal.substring(0, i);
			}
			return rtnVal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Set<String> getDingIdSet(List<String> ekpIds)
			throws Exception {
		IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
				.getBean("omsRelationService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(
				" omsRelationModel.fdAppPkId ");
		hqlInfo.setWhereBlock(
				HQLUtil.buildLogicIN("omsRelationModel.fdEkpId", ekpIds));
		List<String> list = omsRelationService.findList(hqlInfo);

		Set<String> dingIdSet = new HashSet<String>();
		if (list == null) {
			return null;
		}
		for (String dingId : list) {
			if (StringUtil.isNotNull(dingId) && !"null".equals(dingId)) {
				dingIdSet.add(dingId);
			}
		}
		return dingIdSet;
	}

	public static Map<String, String> getDingIdMap(
			List<String> ekpIds)
			throws Exception {
		if (ekpIds == null || ekpIds.isEmpty()) {
			return new HashMap<String, String>();
		}
		IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
				.getBean("omsRelationService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(
				" omsRelationModel.fdAppPkId, omsRelationModel.fdEkpId ");
		hqlInfo.setWhereBlock(
				HQLUtil.buildLogicIN("omsRelationModel.fdEkpId", ekpIds));
		List<Object[]> list = omsRelationService.findList(hqlInfo);

		Map<String, String> dingIdMap = new HashMap<String, String>();
		if (list == null) {
			return null;
		}
		for (Object[] o : list) {
			String dingId = (String) o[0];
			String ekpId = (String) o[1];
			if (StringUtil.isNotNull(dingId) && !"null".equals(dingId)) {
				dingIdMap.put(dingId, ekpId);
			}
		}
		return dingIdMap;
	}

	public static Map<String, String> getDingUnionIdMap(
			List<String> ekpIds)
			throws Exception {
		if (ekpIds == null || ekpIds.isEmpty()) {
			return new HashMap<String, String>();
		}
		IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
				.getBean("omsRelationService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(
				" omsRelationModel.fdUnionId, omsRelationModel.fdEkpId ");
		hqlInfo.setWhereBlock(
				HQLUtil.buildLogicIN("omsRelationModel.fdEkpId", ekpIds)+"  and omsRelationModel.fdUnionId is Not Null");

		List<Object[]> list = omsRelationService.findList(hqlInfo);

		Map<String, String> dingIdMap = new HashMap<String, String>();
		if (list == null) {
			return null;
		}
		for (Object[] o : list) {
			String dingId = (String) o[0];
			String ekpId = (String) o[1];
			if (StringUtil.isNotNull(dingId) && !"null".equals(dingId)) {
				dingIdMap.put(dingId, ekpId);
			}else{
				logger.warn("用户(fdId:{})的映射表unionId异常，请检查该用户的映射关系",ekpId);
			}
		}
		return dingIdMap;
	}

	public static String getViewUrl(SysNotifyTodo todo) {
		String viewUrl = null;
		String domainName = DingConfig.newInstance().getDingDomain();
		if (StringUtil.isNull(domainName)) {
			domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
		}
		if (StringUtil.isNotNull(todo.getFdId())) {
			viewUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
					+ todo.getFdId() + "&oauth=ekp";
			if (StringUtils.isNotEmpty(domainName)) {
				viewUrl = domainName + viewUrl;
			} else {
				viewUrl = StringUtil.formatUrl(viewUrl);
			}
		} else {
			if (StringUtil.isNull(domainName)) {
				domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
			}
			if (domainName.endsWith("/")) {
				domainName = domainName.substring(0, domainName.length() - 1);
			}
			viewUrl = StringUtil.formatUrl(todo.getFdLink(), domainName);
			if (viewUrl.indexOf("?") == -1) {
				viewUrl = viewUrl + "?oauth=ekp";
			} else {
				viewUrl = viewUrl + "&oauth=ekp";
			}
		}
		// 添加f4的dingAppKey参数
		viewUrl = viewUrl + DingUtil.getDingAppKeyByEKPUserId("&",
				todo.getDocCreator() == null ? null
						: todo.getDocCreator().getFdId());
		return viewUrl;
	}

	public static String getPcInnerViewUrl(SysNotifyTodo todo) {
		String viewUrl = null;
		String domainName = getDingDomin();
		if (StringUtil.isNotNull(todo.getFdId())) {
			viewUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
					+ todo.getFdId();
			if (StringUtils.isNotEmpty(domainName)) {
				viewUrl = domainName + viewUrl;
			} else {
				viewUrl = StringUtil.formatUrl(viewUrl);
			}
		} else {
			viewUrl = StringUtil.formatUrl(todo.getFdLink(), domainName);
		}

		viewUrl = viewUrl + DingUtil.getDingAppKeyByEKPUserId("&",
				todo.getDocCreator() == null ? null
						: todo.getDocCreator().getFdId());
		// 中间页面打开
		viewUrl = domainName + "/third/ding/pc/web_wnd.jsp?url="
				+ URLEncoder.encode(viewUrl);

		//加上这个ddtab参数后，钉钉工作台会有一个空白页面出现
		//viewUrl = StringUtil.setQueryParameter(viewUrl, "ddtab", "true");
		return viewUrl;
	}

	public static String getPcViewUrl(SysNotifyTodo todo) {
		String dingTodoPcOpenType = DingConfig.newInstance()
				.getDingTodoPcOpenType();
		if ("in".equals(dingTodoPcOpenType)) {
			return getPcInnerViewUrl(todo);
		}
		String domainName = getDingDomin();
		String pcViewUrl = domainName;
		if ("true".equals(DingConfig.newInstance().getDingTodoSsoEnabled())) {
			pcViewUrl = StringUtil
					.formatUrl("/resource/jsp/sso_redirect.jsp?url="
							+ SecureUtil.BASE64Encoder(getViewUrl(todo)));
		} else {
			if (StringUtil.isNotNull(todo.getFdId())) {
				pcViewUrl = domainName + "/third/ding/pc/pcopen.jsp?fdTodoId="
						+ todo.getFdId()
						+ "&appId=" + DingConfig.newInstance().getDingAgentid()
						+ "&oauth=ekp";
			} else {
				pcViewUrl = StringUtil.formatUrl(todo.getFdLink(), domainName);
				if (pcViewUrl.indexOf("?") == -1) {
					pcViewUrl = pcViewUrl + "?oauth=ekp";
				} else {
					pcViewUrl = pcViewUrl + "&oauth=ekp";
				}
				pcViewUrl = domainName + "/third/ding/pc/pcopen.jsp?url="
						+ SecureUtil.BASE64Encoder(pcViewUrl)
						+ "&oauth=ekp";
			}
		}
		pcViewUrl = pcViewUrl + DingUtil.getDingAppKeyByEKPUserId("&",
				todo.getDocCreator() == null ? null
						: todo.getDocCreator().getFdId());
		return pcViewUrl;
	}

	public static String getValueByLang(String key, String bundle,
										String lang) {
		Locale locale = null;
		if (StringUtil.isNotNull(lang)
				&& lang.contains("-")) {
			locale = new Locale(lang.split("-")[0],
					lang.split("-")[1]);
		}
		return ResourceUtil.getStringValue(key, bundle, locale);
	}

	public static boolean checkNotifyApiType(String apiType) {
		DingConfig dingConfig = DingConfig.newInstance();
		String notifyApiType = dingConfig.getNotifyApiType();
		if (apiType.equals(notifyApiType)) {
			return true;
		}
		return false;
	}

	/**
	 *  构造钉钉返回url信息
	 * @param url
	 * @return
	 */
	public static String getDingPcUrl(String url) {
		if(StringUtil.isNull(url)){
			return null;
		}
		url = url.trim();
		String dingDomain = getDingDomin();
		if (url.indexOf("http") != 0) {
			if (url.startsWith("/")) {
				url = dingDomain + url.trim();
			} else {
				url = dingDomain + "/" + url.trim();
			}
		}
		logger.debug("url:" + url);

		// 判断是否是跳出外部浏览器
		String dingTodoPcOpenType = DingConfig.newInstance()
				.getDingTodoPcOpenType();
		if ("in".equals(dingTodoPcOpenType)) {
			// 内部打开
			url = dingDomain + "/third/ding/pc/web_wnd.jsp?url="
					+ URLEncoder.encode(url);
			return url;
		}
		String dingAppKey = "";
		if (url.contains("dingAppKey")) {
			dingAppKey = "&dingAppKey=" + StringUtil
					.getParameter(url.replace("?", "&"), "dingAppKey");
		}
		url = dingDomain + "/third/ding/pc/url_out.jsp?pg="
				+ SecureUtil.BASE64Encoder(url) + dingAppKey;
		logger.debug("外部打开 url:" + url);

		return url;

	}

	public static String getDingOutPcUrl(String domain,String url){
		if(StringUtil.isNull(url)){
			return null;
		}
		String dingAppKey = "";
		if (url.contains("dingAppKey")) {
			dingAppKey = "&dingAppKey=" + StringUtil
					.getParameter(url.replace("?", "&"), "dingAppKey");
		}
		if(StringUtil.isNull(domain)){
			domain=getDingDomin();
		}
		return domain + "/third/ding/pc/url_out.jsp?pg="
				+ SecureUtil.BASE64Encoder(url) + dingAppKey;
	}

	public static String getDingDomin(){
		String dingDomain = DingConfig.newInstance().getDingDomain();
		if (StringUtil.isNull(dingDomain)) {
			dingDomain = ResourceUtil
					.getKmssConfigString("kmss.urlPrefix");
		}
		dingDomain =dingDomain.trim();
		if (dingDomain.endsWith("/")) {
			dingDomain = dingDomain.substring(0,dingDomain.length() - 1);
		}
		return dingDomain;
	}

	/**
	 * 获取F4接口参数dingAppKey 当admin.do中kmss.ding.addAppKey =
	 * true的时候，请求url添加：dingAppKey=OmsRelationModel表中fdAppKey的值
	 *
	 * @return
	 */
	public static String getDingAppKey(String symbol, String dingAppKey) {

		String addAppKey = ResourceUtil
				.getKmssConfigString("kmss.ding.addAppKey");
		// addAppKey = "true"; // 暂时都传这个参数，后面移除
		if (StringUtil.isNotNull(addAppKey)
				&& "true".equalsIgnoreCase(addAppKey)) {
			if (StringUtil.isNull(symbol)) {
				symbol = "?";
			}
			if (StringUtil.isNull(dingAppKey)) {
				// 如果dingAppKey为空，则取当前登录的用户信息
				SysOrgPerson user = UserUtil.getUser();
				if (user == null) {
					return "";
				}

				try {
					@SuppressWarnings("unchecked")
					OmsRelationModel model = (OmsRelationModel) ((IOmsRelationService) SpringBeanUtil
							.getBean("omsRelationService")).findFirstOne(
							"fdEkpId='" + user.getFdId() + "'", null);
					if (model != null) {
						dingAppKey = model.getFdAppKey();
					} else {
						logger.warn("根据当前登录用户在omsRelationModel找不到对应的记录");
						return "";
					}
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}

			}
			return symbol + "dingAppKey=" + dingAppKey;

		} else {
			return "";
		}
	}

	public static String getDingAppKeyByUser(String symbol, SysOrgPerson user) {

		String addAppKey = ResourceUtil
				.getKmssConfigString("kmss.ding.addAppKey");
		// addAppKey = "true"; // 暂时都传这个参数，后面移除
		if (StringUtil.isNotNull(addAppKey)
				&& "true".equalsIgnoreCase(addAppKey)) {
			if (StringUtil.isNull(symbol)) {
				symbol = "?";
			}
			if (user == null) {
				return "";
			}
			try {
				OmsRelationModel model = (OmsRelationModel) ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService")).findFirstOne(
						"fdEkpId='" + user.getFdId() + "'", null);
				if ( model != null) {
					return symbol + "dingAppKey=" + model.getFdAppKey();
				} else {
					logger.warn("根据用户在omsRelationModel找不到对应的记录");
					return "";
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "";
			}

		} else {
			return "";
		}
	}

	public static String getDingAppKeyByEKPUserId(String symbol, String fdId) {

		String addAppKey = ResourceUtil
				.getKmssConfigString("kmss.ding.addAppKey");
		if (StringUtil.isNotNull(addAppKey)
				&& "true".equalsIgnoreCase(addAppKey)) {
			if (StringUtil.isNull(symbol)) {
				symbol = "?";
			}
			try {
				if (StringUtil.isNull(fdId)) {
					SysOrgPerson user = UserUtil.getUser();
					if (user != null) {
						fdId = user.getFdId();
					} else {
						return "";
					}
				}
				OmsRelationModel model = (OmsRelationModel) ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService")).findFirstOne(
						"fdEkpId='" + fdId + "'", null);
				if (model != null) {
					return symbol + "dingAppKey=" + model.getFdAppKey();
				} else {
					logger.warn("根据fdId在omsRelationModel找不到对应的记录:fdId" + fdId
							+ "  尝试获取当前登录用户去获取appKey");
					SysOrgPerson user = UserUtil.getUser();
					if (user != null) {
						fdId = user.getFdId();
						model = (OmsRelationModel) ((IOmsRelationService) SpringBeanUtil
								.getBean("omsRelationService")).findFirstOne(
								"fdEkpId='" + fdId + "'", null);
						if (model != null) {
							return symbol + "dingAppKey="
									+ model.getFdAppKey();
						} else {
							logger.error("当前登录用户" + user.getFdName() + "("
									+ user.getFdId() + ")与钉钉无对应映射关系");
							return "";
						}
					} else {
						return "";
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "";
			}

		} else {
			return "";
		}
	}

	public static String getCorpId() {

		String addAppKey = ResourceUtil
				.getKmssConfigString("kmss.ding.addAppKey");
		if (StringUtil.isNotNull(addAppKey)
				&& "true".equalsIgnoreCase(addAppKey)) {
			try {
				SysOrgPerson user = UserUtil.getUser();
				if (user == null) {
					logger.warn("当前登录用户为空！");
					return null;
				}
				OmsRelationModel model = (OmsRelationModel) ((IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService")).findFirstOne(
						"fdEkpId='" + user.getFdId() + "'", null);
				if (model != null) {
					return model.getFdAppKey();
				} else {
					logger.warn("根据fdId在omsRelationModel找不到对应的记录:fdId"
							+ user.getFdId() + "  name:"
							+ user.getFdName());
					return null;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "";
			}

		} else {
			return DingConfig.newInstance().getDingCorpid();
		}
	}


	public static String getAgentIdByCorpId(String corpId) {
		String addAppKey = ResourceUtil
				.getKmssConfigString("kmss.ding.addAppKey");
		if (StringUtil.isNotNull(addAppKey)
				&& "true".equalsIgnoreCase(addAppKey)) {
			//ISV
			try {
				if (StringUtil.isNull(corpId)) {
					SysOrgPerson user = UserUtil.getUser();
					if (user == null) {
						logger.warn("当前登录用户为空！");
						return null;
					}
					OmsRelationModel model = (OmsRelationModel) ((IOmsRelationService) SpringBeanUtil
							.getBean("omsRelationService")).findFirstOne(
							"fdEkpId='" + user.getFdId() + "'", null);
					if (model != null) {
						corpId = model.getFdAppKey();
					} else {
						logger.warn("根据fdId在omsRelationModel找不到对应的记录:fdId"
								+ user.getFdId() + "  name:"
								+ user.getFdName());
						return null;
					}
				}
				JSONObject rs = DingUtils.getDingApiService()
						.getAgentIdByCorpId(corpId);
				logger.debug("rs:" + rs);
				if (rs != null && rs.containsKey("success")) {
					if (rs.getBoolean("success")) {
						JSONObject data = rs.getJSONObject("data");
						if (data == null || data.isEmpty()) {
							return null;
						}
						return rs.getJSONObject("data").getString("agentId");
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return DingConfig.newInstance().getDingAgentid();

	}

	/**
	 * 是否走高级审批接口： 1.开启高级审批，流程管理模块 2.待办旧接口+开启了套件
	 *
	 * @return
	 */
	public static boolean isXformTemplate(SysNotifyTodo todo) {
		try {
			if (todo == null) {
				return false;
			}
			String modelName = todo.getFdModelName();
			if (!"com.landray.kmss.km.review.model.KmReviewMain"
					.equals(modelName)) {
				return false;
			}
			DingConfig config = DingConfig.newInstance();
			IBaseService obj = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
			IBaseModel kmReviewBaseModel = obj
					.findByPrimaryKey(todo.getFdModelId());
			String type = ThirdDingXFormTemplateUtil
					.getXFormTemplateType(kmReviewBaseModel);
			logger.debug("type:" + type);
			if ("true".equals(config.getDingEnabled())
					&& ("true".equals(config.getAttendanceEnabled())
					|| "true".equals(config.getDingSuitEnabled())
					&& StringUtil.isNotNull(type))) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;

	}

	/**
	 * 根据待办判断是否在高级审批是否存在待审的实例
	 *
	 * @param todo
	 * @return
	 */
	public static boolean hasInstanceInXform(SysNotifyTodo todo) {
		//有实例而且不是套件实例
		if ("com.landray.kmss.km.review.model.KmReviewMain".equals(todo.getFdModelName())
				&& getInstanceXform(todo)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 *有非套件实例
	 */
	private static boolean getInstanceXform(SysNotifyTodo todo){
		if(StringUtil.isNull(todo.getFdModelId())){
			return false;
		}
		try {
			String sql = "SELECT instance.fd_id FROM third_ding_dinstance_xform instance LEFT JOIN third_ding_dtemplate_xform temp ON instance.fd_template_id=temp.fd_id " +
					"WHERE fd_ekp_instance_id='"+todo.getFdModelId()+"' AND fd_status='20' AND temp.fd_flow = 'common'";
			List list = getThirdDingDinstanceXformService().getBaseDao().getHibernateSession().createNativeQuery(sql).list();
			if(list.size()>0){
				return true;
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(),e);
		}
		return false;
	}

	/**
	 * （套件）删除钉钉主文档，实例更新
	 * @param reviewMainId
	 */
	public void deleteReviewMain(String reviewMainId) {
		logger.warn("----流程主文档删除----"+reviewMainId);
		TransactionStatus status = null;
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"fdEkpInstanceId =:fdEkpInstanceId and fdStatus=:fdStatus");
			hqlInfo.setParameter("fdEkpInstanceId", reviewMainId);
			hqlInfo.setParameter("fdStatus", "20");
			List<ThirdDingDinstanceXform> list = getThirdDingDinstanceXformService()
					.findList(hqlInfo);
			if (list != null && list.size() > 0) {
				status = TransactionUtils.beginNewTransaction();
				for (int i = 0; i < list.size(); i++) {
					ThirdDingDinstanceXform dinstanceXform = list.get(i);
					OapiProcessWorkrecordUpdateResponse response = DingNotifyUtil
							.updateInstanceState(
									DingUtils.dingApiService
											.getAccessToken(),
									dinstanceXform.getFdInstanceId(),
									Long.valueOf(list.get(i).getFdTemplate()
											.getFdAgentId()),
									dinstanceXform.getFdEkpUser().getFdId(),
									false);

					if (response != null && response.getErrcode() == 0) {
						logger.warn("更新实例成功: 实例id->{}",dinstanceXform.getFdInstanceId());
						dinstanceXform.setFdStatus("00");
						getThirdDingDinstanceXformService().update(dinstanceXform);
					} else {
						logger.warn("!!!更新实例失败！reviewMainId:{},结果：{}", reviewMainId,(response==null?"null":response.getBody()));
					}

				}
				TransactionUtils.getTransactionManager().commit(status);
			}
		} catch (Exception e) {
			if (status != null) {
				try {
					TransactionUtils.getTransactionManager().rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
		}
	}


	/**
	 * （套件）处理模板同步
	 * @param method
	 * @param type
	 * @param templateId
	 * @param categoryId
	 * @param templateName
	 * @param allReader
	 * @param desc
	 */
	public void dealWithDingTemplate(String method, String type,
			String templateId, String categoryId, String templateName,
			List allReader, String desc) {
		logger.warn("流程模版变更  method：" + method + "   type:" + type
				+ "  templateId:" + templateId);
		try {
			DingConfig config = DingConfig.newInstance();
			if ("true".equals(config.getDingEnabled())
					&& ("true".equals(config.getAttendanceEnabled())
							|| "true".equals(config.getDingSuitEnabled())
									&& StringUtil.isNotNull(type))) {
				logger.debug("钉钉高级审批开启");
				IThirdDingDtemplateXformService thirdDingDtemplateXformService = (IThirdDingDtemplateXformService) SpringBeanUtil
						.getBean("thirdDingDtemplateXformService");
				if ("delete".equals(method)) {
					logger.warn("------删除钉钉模板-------" + templateName);
					HQLInfo hql = new HQLInfo();
					hql.setWhereBlock(
							"fdTemplateId=:fdTemplateId and fdIsAvailable=:fdIsAvailable");
					hql.setParameter("fdTemplateId", templateId);
					hql.setParameter("fdIsAvailable", true);
					List<ThirdDingDtemplateXform> temp_list = thirdDingDtemplateXformService
							.findList(hql);
					if (temp_list != null && temp_list.size() > 0) {
						for (int j = 0; j < temp_list.size(); j++) {
							ThirdDingDtemplateXform temp = temp_list.get(j);
							logger.debug("删除钉钉模板：" + temp.getFdName());
							thirdDingDtemplateXformService.deleteTemplate(temp);
						}

					} else {
						logger.debug(
								"---------------钉钉模板中找不到对应的记录----------------");
					}

				} else {
					if (StringUtil.isNull(type)) {
						type = "common";
					}
					JSONObject parm = new JSONObject();
					parm.put("desc", desc);
					parm.put("name", templateName);
					parm.put("fdId", templateId);
					parm.put("categoryId", categoryId);
					parm.put("type", type);
					List<String> titleList = new ArrayList<String>();
					// 展示名称
					if ("attendance".equals(type)) {
						// 请假
						titleList.add("请假类型");
						titleList.add("开始时间");
						titleList.add("结束时间");
						titleList.add("时长");
						titleList.add("请假原因");
					} else if ("batchLeave".equals(type)) {
						// 批量请假
						titleList.add("请假类型");
						titleList.add("总时长");
						titleList.add("请假原因");
						titleList.add("时长");
						titleList.add("开始时间");
						titleList.add("结束时间");
					} else if ("workOverTime".equals(type)) {
						// 加班
						titleList.add("加班人");
						titleList.add("开始时间");
						titleList.add("结束时间");
					} else if ("goOut".equals(type)) {
						// 外出
						titleList.add("开始时间");
						titleList.add("结束时间");
						titleList.add("时长");
					} else if ("businessTrip".equals(type)) {
						// 出差套件
						titleList.add("出差事由");
						titleList.add("交通工具");
						titleList.add("单程往返");
					} else if ("changeOff".equals(type)) {
						// 换班套件
						titleList.add("申请人");
						titleList.add("替班人");
						titleList.add("换班日期");
					} else if ("replacement".equals(type)) {
						// 补卡套件
						titleList.add("补卡时间");
						titleList.add("补卡理由");
						titleList.add("补卡班次");
					} else if ("destroyLeave".equals(type)) {
						// 销假套件
						titleList.add("请假单");
						titleList.add("销假时长");
						titleList.add("剩余请假时长");
						// titleList.add("请假明细");
						// titleList.add("销假说明");
					} else if ("batchReplacement".equals(type)) {
						// 批量补卡
						titleList.add("补卡时间");
						titleList.add("补卡原因");
					} else if ("batchCancel".equals(type)) {
						// 批量销假套件
						titleList.add("请假单");
						titleList.add("销假时长");
						titleList.add("剩余请假时长");
					} else if ("batchChange".equals(type)) {
						// 批量换班
						titleList.add("申请人");
						titleList.add("替班人");
						titleList.add("换班日期");
						titleList.add("还班日期");
						titleList.add("换班说明");
						titleList.add("还班说明");
					} else if ("batchWorkOverTime".equals(type)) {
						// 批量加班
						titleList.add("加班人");
						titleList.add("开始时间");
						titleList.add("结束时间");
						titleList.add("时长");
						titleList.add("加班补偿");
					}

					String dirId = "other";
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setSelectBlock("fdDirid");
					hqlInfo.setWhereBlock(
							"fdTemplateId=:fdTemplateId and fdIsAvailable=:fdIsAvailable and fdCorpid=:fdCorpid");
					hqlInfo.setParameter("fdTemplateId", categoryId);
					hqlInfo.setParameter("fdIsAvailable", true);
					hqlInfo.setParameter("fdCorpid", getCorpId());

					String fdDirid = (String) getThirdDingCategoryXformService()
							.findFirstOne(hqlInfo);
					if (StringUtils.isNotBlank(fdDirid)) {
						dirId = fdDirid;
					}

					String icon = "common";
					if ("attendance".equals(type)
							|| "batchLeave".equals(type)) {
						icon = "leave";
					} else if ("workOverTime".equals(type)) {
						icon = "common";
					} else if ("goOut".equals(type)) {
						icon = "out";
					} else if ("businessTrip".equals(type)) {
						icon = "trip";
					} else if ("changeOff".equals(type)) {
						icon = "common";
					} else if ("replacement".equals(type)) {
						icon = "common";
					} else if ("destroyLeave".equals(type)
							|| "batchCancel".equals(type)) {
						icon = "leave#orange";
					} else if ("batchReplacement".equals(type)) {
						icon = "leave#yellow";
					} else if ("batchChange".equals(type)) {
						icon = "relieve#blue";
					} else if ("batchWorkOverTime".equals(type)) {
						icon = "timefades";
					}

					String domainName = DingConfig.newInstance()
							.getDingDomain();
					if (StringUtil.isNull(domainName)) {
						domainName = ResourceUtil
								.getKmssConfigString("kmss.urlPrefix");
					}
					String instanceUrl = domainName
							+ "/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId="
							+ templateId + getDingAppKeyByEKPUserId("&", null);
					String template_edit_url = domainName
							+ "/km/review/km_review_template/kmReviewTemplate.do?method=edit&fdId="
							+ templateId + getDingAppKeyByEKPUserId("&", null)
							+ "&from=dingmng";
					JSONObject process_config = new JSONObject();
					process_config.put("disable_form_edit", true);
					process_config.put("fake_template_edit_url",
							template_edit_url);
					process_config.put("template_edit_url", template_edit_url);
					process_config.put("hidden", false);
					// 模版新参数
					JSONObject save_config = new JSONObject();
					save_config.put("process_config", process_config);
					save_config.put("create_instance_pc_url", instanceUrl);
					save_config.put("create_instance_mobile_url", instanceUrl);
					save_config.put("dir_id", dirId);
					// save_config.put("origin_dir_id", origin_dir_id);
					save_config.put("icon", icon);
					parm.put("save_config", save_config);

					if ("add".equals(method)) {
						logger.debug("------新增钉钉模板-------");

						thirdDingDtemplateXformService.addCommonTemplate(null,
								null,
								titleList, parm, allReader);

					} else if ("update".equals(method)) {
						logger.debug("------更新钉钉模板-------");
						hqlInfo = new HQLInfo();
						hqlInfo.setWhereBlock(
								"fdTemplateId=:fdTemplateId and fdIsAvailable=:fdIsAvailable");
						hqlInfo.setParameter("fdTemplateId", templateId);
						hqlInfo.setParameter("fdIsAvailable", true);
						ThirdDingDtemplateXform xform = (ThirdDingDtemplateXform) thirdDingDtemplateXformService
								.findFirstOne(hqlInfo);
						if (xform != null) {
							logger.debug("更新钉钉模板：" + xform.getFdName());
							thirdDingDtemplateXformService.addCommonTemplate(
									xform,
									null,
									titleList, parm, allReader);
						} else {
							logger.warn("找不到对应的钉钉模板或者模板已不可用,新增模板");
							thirdDingDtemplateXformService.addCommonTemplate(
									null,
									null,
									titleList, parm, allReader);
						}
					}
					updateTodoTemplate(type, templateId, templateName);
				}
			} else {
				logger.debug("-----------钉钉高级审批功能不开启--------------");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}

	}

	private void updateTodoTemplate(String type, String templateId,
									String name) {
		try {
			IThirdDingTodoTemplateService thirdDingTodoTemplateService = (IThirdDingTodoTemplateService) SpringBeanUtil
					.getBean("thirdDingTodoTemplateService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"thirdDingTodoTemplate.fdModelName=:fdModelName and thirdDingTodoTemplate.fdTemplateId=:fdTemplateId");
			hqlInfo.setParameter("fdModelName",
					"com.landray.kmss.km.review.model.KmReviewMain");
			hqlInfo.setParameter("fdTemplateId", templateId);
			List list = thirdDingTodoTemplateService.findList(hqlInfo);
			if (list == null || list.size() == 0) {
				logger.info("-------套件没有推送模板,准备创建推送模板--------");
				ThirdDingTodoTemplate thirdDingTodoTemplate = new ThirdDingTodoTemplate(
						name, "1", "0", templateId,
						"com.landray.kmss.km.review.model.KmReviewMain", name,
						"com.landray.kmss.km.review.model.KmReviewTemplate",
						"流程管理", "1;2");
				thirdDingTodoTemplate.setFdDetail(getDetailInfo(type));
				thirdDingTodoTemplateService.add(thirdDingTodoTemplate);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private String getDetailInfo(String type) {
		String lang = "中文|zh-CN";
		String defLang = ResourceUtil.getKmssConfigString("kmss.lang.official");
		if (StringUtil.isNotNull(defLang)) {
			lang = defLang;
		}
		JSONObject option = new JSONObject();
		JSONArray data = new JSONArray();
		data.add(getOption("docSubject", "主题", false, lang));
		if ("batchLeave".equals(type)) {
			data.add(getOption("$suiteTable$_leaveTime", "请假时间", true, lang));
			data.add(getOption("$suiteTable$_fdSumDuration", "请假总时长", true,
					lang));
		} else if ("batchReplacement".equals(type)) {
			data.add(getOption("$suiteTable$_fdUser", "补卡人", true, lang));
			data.add(getOption("$suiteTable$_fdReplacementTime", "补卡时间", true,
					lang));
		} else if ("batchCancel".equals(type)) {
			data.add(getOption("$suiteTable$_fd_cancel_user", "销假人", true,
					lang));
			data.add(getOption("$suiteTable$_fd_form_name", "销假单", true,
					lang));
		} else if ("batchChange".equals(type)) {
			data.add(getOption("$suiteTable$_fdChangeApplyUser", "换班人", true,
					lang));
			data.add(getOption("$suiteTable$_fdChangeSwapUser", "替班人", true,
					lang));
		} else if ("batchWorkOverTime".equals(type)) {
			// 批量加班
			data.add(getOption("$suiteTable$_users", "加班人", true, lang));
			data.add(
					getOption("$suiteTable$_workOverTime", "加班时间", true, lang));
		}
		option.put("data", data);
		return option.toString();
	}

	private JSONObject getOption(String key, String name, boolean fromForm,
								 String lang) {
		JSONObject option = new JSONObject();
		option.put("key", key);
		option.put("name", name);
		option.put("fromForm", fromForm);

		JSONArray langs = new JSONArray();
		JSONObject _lang = new JSONObject();
		_lang.put("lang", lang);
		_lang.put("value", name);
		langs.add(_lang);
		option.put("title", langs);

		option.put("lastSelectType", "String");
		return option;
	}

	/**
	 * 
	 * @param url
	 * @return true:表示加入待办清理任务 ；false：不处理
	 */
	public static boolean checkUrlByDomain(String url) {

		String dealWithAll = DingConfig.newInstance().getDealWithAllErrNotify();
		if (StringUtil.isNotNull(dealWithAll)
				&& "true".equalsIgnoreCase(dealWithAll)) {
			return true;
		} else {
			String domainName = DingConfig.newInstance()
					.getDingDomain();
			if (StringUtil.isNull(domainName)) {
				domainName = ResourceUtil
						.getKmssConfigString("kmss.urlPrefix");
			}
			domainName = domainName.trim();
			logger.debug("domainName:" + domainName);
			if (StringUtil.isNotNull(url)
					&& !url.startsWith(domainName)) {
				return false;
			}
		}
		return true;
	}

	public static Object getModelPropertyString(Object model,
			String propertyName, String splitStr, Locale locale)
			throws Exception {
		return BeanUtils.getValue(model, propertyName);
	}

	public static String getUnionIdByEkpId(String ekpUserId){
		String unionId = null;
		try {
			OmsRelationModel omsRelationModel = (OmsRelationModel) getOmsRelationService().findFirstOne("fdEkpId='"+ekpUserId+"'",null);
			if (omsRelationModel != null){
				if(StringUtil.isNull(omsRelationModel.getFdUnionId())){
					logger.warn("人员UnionId为空，请先维护人员对照表 (ekp人员Id:"+ekpUserId+")");
					return null;
				}else{
					return omsRelationModel.getFdUnionId();
				}
			}else {
				logger.warn("对照表中找不到人员："+ekpUserId+" 的记录，请先维护对照表信息");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return unionId;
	}

	public static String getUseridByEkpId(String ekpUserId){
		try {
			OmsRelationModel model = (OmsRelationModel) getOmsRelationService().findFirstOne("fdEkpId='"+ekpUserId+"'",null);
			if (model != null){
				return model.getFdAppPkId();
			}else {
				logger.warn("对照表中找不到人员："+ekpUserId+" 的记录，请先维护对照表信息");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	public static String getEkpIdByUnionid(String unionid){
		try {
			OmsRelationModel model = (OmsRelationModel) getOmsRelationService().findFirstOne("fdUnionId='"+unionid+"'",null);
			if (model != null){
				return model.getFdEkpId();
			}else {
				logger.warn("对照表中找不到人员unionid："+unionid+" 的记录，请先维护对照表信息");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	public static String getEkpIdByUserid(String userid){
		try {
			return getOmsRelationService().getEkpUserIdByDingUserId(userid);
		} catch (Exception e) {
			logger.warn(e.getMessage(),e);
		}
		return null;
	}

	public static Boolean isExistUnionid(String unionid){
		try {
			List<OmsRelationModel> list = getOmsRelationService().findList("fdUnionId='"+unionid+"'",null);
			if (list!=null&&list.size()>0){
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return false;
	}

	public static String getIdUnionidByDingId(String DingId){
		try {
			OmsRelationModel model = (OmsRelationModel) getOmsRelationService().findFirstOne("fdAppPkId='"+DingId+"'",null);
			if (model != null){
				return model.getFdUnionId();
			}else {
				logger.warn("对照表中找不到人员DingId："+DingId+" 的记录，请先维护对照表信息");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 获取一个钉钉管理员的unionId
	 * @return
	 */
	public static String getDingAdminUinionId() {
		try {
			JSONObject admins =DingUtils.getDingApiService().getAdmin();
			if(admins!=null&&admins.getInt("errcode")==0){
				String userid = admins.getJSONArray("adminList").getJSONObject(0).getString("userid");
				JSONObject admin = DingUtils.getDingApiService().userGet(userid,null);
				logger.debug(admin.toString());
				if(admin!=null&&admin.getInt("errcode")==0){
					return admin.getString("unionid");
				}
			}else {
				logger.warn("获取管理员失败："+admins);
			}
			logger.warn(admins.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		return null;
	}

	/**
	 * 获取待办默认语言
	 * @return
	 */
	public static String getTodoDefaultLang() {
		String defLang = ResourceUtil.getKmssConfigString("kmss.lang.official");
		if(StringUtil.isNotNull(defLang)){
			//中文|zh-CN
			if(defLang.contains("|")){
				return defLang.split("\\|")[1];
			}
			return defLang;
		}
		return "zh-CN";
	}


	/**
	 * 过滤子组织
	 *
	 * @param elems
	 * @return
	 */
	public static Set<SysOrgElement> filterSubOrg(Set<SysOrgElement> elems) {
		Map<String, SysOrgElement> map = new HashMap<String, SysOrgElement>();
		Set<String> hids = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(elems)) {
			for (SysOrgElement elem : elems) {
				hids.add(elem.getFdHierarchyId());
				map.put(elem.getFdHierarchyId(), elem);
			}
			// 过滤子组织
			hids = filterRangeByHid(hids);
			elems.clear();
			for (String hid : hids) {
				elems.add(map.get(hid));
			}
		}
		return elems;
	}

	/**
	 * 根据层级ID过滤子组织
	 *
	 * @param hids
	 * @return
	 */
	public static Set<String> filterRangeByHid(Set<String> hids) {
		if (CollectionUtils.isEmpty(hids)) {
			return Collections.EMPTY_SET;
		}
		Set<String> resultTemp = new HashSet<String>();
		Set<String> addTemp = new HashSet<String>();
		Set<String> delTemp = new HashSet<String>();
		for (String hid1 : hids) {
			boolean add = true;
			for (String hid2 : resultTemp) {
				if (hid2.startsWith(hid1)) {
					add = false;
					delTemp.add(hid2);
					addTemp.add(hid1);
					continue;
				} else if (hid1.startsWith(hid2)) {
					add = false;
					break;
				}
			}
			if (add) {
				resultTemp.add(hid1);
			} else {
				resultTemp.removeAll(delTemp);
				resultTemp.addAll(addTemp);
				delTemp.clear();
				addTemp.clear();
			}
		}
		return resultTemp;
	}

	/**
	 * 删除钉钉拓展字段同步配置
	 * @throws Exception
	 */
	public static void deleteCustomData() throws Exception{
		String sql = "delete from SysAppConfig where fdField like 'org2ding.custom.%' and fdKey = 'com.landray.kmss.third.ding.model.DingConfig'";
		int d = ((ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService")).getBaseDao().getHibernateSession().createQuery(sql).executeUpdate();
		logger.debug("--删除自定义行数为：---"+d);
		BaseAppconfigCache.update("com.landray.kmss.third.ding.model.DingConfig");
	}

	/**
	 * 获取钉钉的跟部门
	 */
	public static String getDingTopDeptIds(){
		String topDeptIds = null;
		DingConfig dingConfig = DingConfig.newInstance();
		String syncSelection = dingConfig.getSyncSelection();
		if(StringUtil.isNotNull(syncSelection)){
			//ekp同步到钉钉或者不同步仅匹配方向
			if(("1".equals(syncSelection)||"3".equals(syncSelection)) && StringUtil.isNotNull(dingConfig.getDingDeptid())){
				//同步范围的全员
				topDeptIds= dingConfig.getDingDeptid();
			}else if("2".equals(syncSelection) && StringUtil.isNotNull(dingConfig.getDingOrgId2ekp())){
				topDeptIds= dingConfig.getDingOrgId2ekp();
			}
		}
		//过滤只配了钉钉顶层部门的情况
		if(topDeptIds!=null && "1".equals(topDeptIds.trim())){
			topDeptIds = null;
		}
		return topDeptIds;
	}

	/**
	 * 根据钉钉部门id获取部门名称（优先从ekp获取，ekp查不到则从钉钉获取）
	 * @param dingDeptIds
	 * @return
	 * @throws Exception
	 */
	public static String getDeptNameByDingIds(String dingDeptIds) throws Exception {
		if(StringUtil.isNull(dingDeptIds)){
			return null;
		}
		ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		if(dingDeptIds.contains(";")){
			//查询匹配上的数据
			Session hibernateSession = getOmsRelationService().getBaseDao().getHibernateSession();
			List<String> dingDeptIdList = ArrayUtil.asList(dingDeptIds.split(";"));
			String sql = "SELECT fd_name,fd_id FROM sys_org_element WHERE fd_id in (SELECT fd_ekp_id FROM oms_relation_model WHERE "+ HQLUtil.buildLogicIN("fd_app_pk_id", dingDeptIdList)+")";
			List<Object[]> dingUserIds = hibernateSession.createNativeQuery(sql).list();
			StringBuilder names = new StringBuilder();
			if(dingUserIds!=null && dingUserIds.size() == dingDeptIdList.size()){
				//id都在映射表中
				dingUserIds.stream().forEach(data->{
					names.append(data[0]).append(";");
				});
			}else {
				//有的钉钉部门id不存在ekp中
				 sql = "SELECT fd_app_pk_id,fd_ekp_id FROM oms_relation_model WHERE "+ HQLUtil.buildLogicIN("fd_app_pk_id", dingDeptIdList);
				List<Object[]> modelIds = hibernateSession.createNativeQuery(sql).list();
				Map<String,String> modelMap = new HashMap<>();
				Map<String,String> ekpNameMap = new HashMap<>();
				modelIds.forEach(data->{modelMap.put((String) data[0],(String) data[1]);});
				dingUserIds.forEach(data->{ekpNameMap.put((String)data[1],(String)data[0]);});
				dingDeptIdList.forEach(dingId->{
					if(modelMap.containsKey(dingId) && ekpNameMap.containsKey(modelMap.get(dingId))){
						names.append((ekpNameMap.get(modelMap.get(dingId)))).append(";");
					}else{
						//ekp查不到
						names.append(findDeptNameById(dingId)).append(";");
					}
				});

			}
			return names.toString();
		}else{
			//单个部门
			String ekpId = getEkpIdByUserid(dingDeptIds);
			if(StringUtil.isNotNull(ekpId)){
				SysOrgElement sysOrgElement= (SysOrgElement) sysOrgElementService.findByPrimaryKey(ekpId,null,true);
				if(sysOrgElement != null){
					return sysOrgElement.getFdName();
				}
			}
			//查钉钉的部门
			return findDeptNameById(dingDeptIds);
		}
	}

	/**
	 * 根据钉钉部门id获取部门名称
	 * @param dingDeptIds
	 * @return
	 */
	private static String findDeptNameById(String dingDeptIds) {
		try {
			JSONObject result = DingUtils.getDingApiService().departGet(Long.valueOf(dingDeptIds));
			if (result != null && result.getInt("errcode") == 0){
				return result.getString("name")+"(钉钉)";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return dingDeptIds+"(异常)";
	}

	/**
	 * 截取字符串长度，包括处理中文
	 * @param str
	 * @param limit
	 * @return
	 */
	public static String getString(String str, int limit) {
		if (StringUtil.isNull(str)) {
			return str;
		}
		String regEx = "[\u0391-\uFFE5]";
		// 判断是否存在中文字符
		if (str.getBytes().length == str.length()) {
			if(str.length() <  limit){
				return str;
			}
			return str.substring(0, limit);
		} else {
			int length = 0;
			StringBuilder newStr = new StringBuilder();
			for (int i = 0; i < str.length(); i++) {
				String c = str.substring(i, i + 1);
				if (c.matches(regEx)) {
					length += 2;
				} else {
					length += 1;
				}
				if(length < limit){
					newStr.append(c);
				}
				else{
					logger.warn("原字符[" + str + "]长度已经超过" + limit + "长度限制!截取后："+ newStr);
					return newStr.toString();
				}
			}
			return str;
		}
	}
}
