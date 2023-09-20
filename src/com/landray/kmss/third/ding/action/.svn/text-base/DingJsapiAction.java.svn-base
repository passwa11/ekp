package com.landray.kmss.third.ding.action;

import com.dingtalk.api.response.OapiUserListbypageResponse.Userlist;
import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseCreateInfoModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.common.model.IDocSubjectModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.config.design.SysCfgFlowDef;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.lbpm.engine.integrate.expecterlog.ILbpmExpecterLogService;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmExpecterLog;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.third.ding.model.DingCodeModel;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IDingCodeService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.*;

/**
 * 获取钉钉授权信息
 */
public class DingJsapiAction extends BaseAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingJsapiAction.class);

	public ActionForward jsapiSignature(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		KmssMessages messages = new KmssMessages();
		logger.debug("jsapiSignature start");
		try {
			// 钉钉集成没开则不进行鉴权
			if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
				logger.debug("-----没有开启钉钉集成开关，不进行鉴权----");
				return null;
			}
			String url = request.getParameter("url");
			String queryString = null;
			DingApiService dingApiService = DingUtils.getDingApiService();
			if (StringUtil.isNotNull(url) && url.indexOf("?") > -1) {
				queryString = url.substring(url.indexOf("?") + 1, url.length());
				url = url.substring(0, url.indexOf("?"));
			}
			logger.debug("dingApiService.getConfig end");
			String result = dingApiService.getConfig(url, queryString);
			logger.debug("dingApiService.getConfig end");
			if (UserOperHelper.allowLogOper("jsapiSignature", "*")) {
				UserOperHelper.logMessage(result);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		logger.debug("jsapiSignature end");
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		return null;
	}

	public ActionForward pcJsapiSignature(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-pcJsapiSignature", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			// 钉钉集成没开则不进行鉴权
			if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
				logger.debug("-----没有开启钉钉集成开关，不进行鉴权----");
				return null;
			}
			String signedUrl = URLDecoder.decode(request.getParameter("url"), "UTF-8");
			DingApiService dingApiService = DingUtils.getDingApiService();
			String nonceStr = "abcdefg";
			long timeStamp = System.currentTimeMillis() / 1000;
			String ticket = dingApiService.getJsapiTicket();
			String signature = dingApiService.sign(ticket, nonceStr, timeStamp, signedUrl);

			JSONObject result = new JSONObject();
			DingConfig config = DingConfig.newInstance();
			String dev = config.getDevModel();
			result.put("corpId", DingUtil.getCorpId());
			if ("1".equals(dev)) {
				result.put("appId", "");// 老secret的方式
			} else if ("2".equals(dev)) {
				result.put("appId", DingUtil.getAgentIdByCorpId(null));// appkey和appsecret
			} else {
				result.put("appId", DingUtil.getAgentIdByCorpId(null));// customkey和customsecret
			}
			result.put("nonceStr", nonceStr);
			result.put("signature", signature);
			result.put("timeStamp", timeStamp);
			result.put("signedUrl", signedUrl);
			logger.info(result.toString());
			response.setCharacterEncoding("UTF-8");
			if (UserOperHelper.allowLogOper("pcJsapiSignature", "*")) {
				UserOperHelper.logMessage(result.toString());
			}
			response.getWriter().write(result.toString());
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-pcJsapiSignature", true, getClass());
		return null;
	}

	public ActionForward userinfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = request.getParameter("code");
		response.setCharacterEncoding("UTF-8");
		try {
			DingApiService dingApiService = DingUtils.getDingApiService();
			String userinfo = null;
			String addAppKey = ResourceUtil
					.getKmssConfigString("kmss.ding.addAppKey");
			if (StringUtil.isNotNull(addAppKey)
					&& "true".equals(addAppKey)) {
				logger.debug(
						"----------------F4 addAppKey-------------------");
				String dingAppKey = request.getParameter("dingAppKey");
				if (StringUtil.isNull(dingAppKey)) {
					logger.warn("F4 dingAppKey为空");
				}
				userinfo = DingUtils.getDingApiService()
						.getUserInfoByDingAppKey(code, dingAppKey).toString();

			} else {
				userinfo = dingApiService.getUserInfo(code).toString(2);
			}
			logger.debug("userinfo::" + userinfo);

			if ("dingpc".equals(request.getParameter("from"))) {
				setDingUserCode(code, userinfo);
			}
			if (UserOperHelper.allowLogOper("userinfo", "*")) {
				UserOperHelper.logMessage(userinfo);
			}
			response.getWriter().append(userinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append("");
		}
		return null;
	}

	private void setDingUserCode(String code, String userinfo) throws Exception {
		JSONObject o = JSONObject.fromObject(userinfo);
		if (o.containsKey("userid")) {
			IDingCodeService dingCodeService = (IDingCodeService) SpringBeanUtil.getBean("dingCodeService");
			DingCodeModel m = new DingCodeModel();
			m.setFdCode(code);
			m.setFdUserid(o.getString("userid"));
			m.setFdExpiresTime(System.currentTimeMillis());
			dingCodeService.add(m);
		}
	}

	public static String getDocCreatorId(String fdId, String modelName) throws Exception {
		IBaseService baseService = (IBaseService) SpringBeanUtil.getBean("KmssBaseService");
		IBaseModel model = baseService.getBaseDao().findByPrimaryKey(fdId, modelName, true);
		if (model instanceof IBaseCreateInfoModel) {
			IBaseCreateInfoModel cm = (IBaseCreateInfoModel) model;
			if(cm.getDocCreator()==null){
				return "";
			}else{
				return cm.getDocCreator().getFdId();
			}
		}
		return "";
	}

	public static Map<String, String> getDingInfos(String fdId, String modelName) throws Exception {
		Map<String, String> infos = new HashMap<String, String>();

		ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
		ILbpmExpecterLogService lbpmExpecterLogService = (ILbpmExpecterLogService) SpringBeanUtil
				.getBean("lbpmExpecterLogService");
		List<LbpmExpecterLog> expectersLog = lbpmExpecterLogService.findActiveByProcessId(fdId, true);
		StringBuffer sb = new StringBuffer();
		int n = 0;
		String id = null;
		for (LbpmExpecterLog expecter : expectersLog) {
			if (n > 0) {
				sb.append(",");
			}
			if (expecter.getFdHandler().getFdOrgType() == SysOrgElement.ORG_TYPE_PERSON) {
				SysOrgPerson person = (SysOrgPerson) sysOrgCoreService.format(expecter.getFdHandler());
				if (person != null) {
					id = getOmsRelation(person.getFdId());
					if (StringUtil.isNotNull(id)) {
						sb.append("'" + id + "'");
					}else{
						sb.append("'" + person.getFdLoginName() + "'");
					}
				}
				n++;
			}
			if (expecter.getFdHandler().getFdOrgType() == SysOrgElement.ORG_TYPE_POST) {
				SysOrgPost post = (SysOrgPost) sysOrgCoreService.format(expecter.getFdHandler());
				for (int j = 0; j < post.getFdPersons().size(); j++) {
					SysOrgPerson person = (SysOrgPerson) post.getFdPersons().get(j);
					if (person != null) {
						id = getOmsRelation(person.getFdId());
						if (StringUtil.isNotNull(id)) {
							sb.append("'" + id + "'");
						}else{
							sb.append("'" + person.getFdLoginName() + "'");
						}
					}
					n++;
				}
			}
		}
		infos.put("currHandler", sb.toString());

		IBaseService baseService = (IBaseService) SpringBeanUtil.getBean("KmssBaseService");
		IBaseModel model = baseService.getBaseDao().findByPrimaryKey(fdId, modelName, true);
		if (model instanceof IBaseCreateInfoModel) {
			IBaseCreateInfoModel cm = (IBaseCreateInfoModel) model;
			infos.put("docCreatorId", cm.getDocCreator().getFdId());
			infos.put("docCreatorName", cm.getDocCreator().getFdName());
			//获取当前登录的用户信息
			infos.put("loginUserId", UserUtil.getUser().getFdId());
			infos.put("loginUserName", UserUtil.getUser().getFdName());
		} else {
			infos.put("docCreatorName", "");
		}
		if (model instanceof IDocSubjectModel) {
			IDocSubjectModel sm = (IDocSubjectModel) model;
			infos.put("docSubject", sm.getDocSubject());
		} else {
			infos.put("docSubject", "");
		}
		String docUrl = ModelUtil.getModelUrl(model);
		infos.put("docUrl", docUrl + "&oauth=ekp");

		infos.put("templateName", getTemplateName(model));

		logger.debug("infos::" + infos);
		return infos;

	}

	private static String getTemplateName(IBaseModel model) throws Exception {
		SysCfgFlowDef def = SysConfigs.getInstance().getFlowDefByMain(ModelUtil.getModelClassName(model));
		Object value = null;
		value = PropertyUtils.getProperty(model, def.getTemplatePropertyName());
		if (value == null) {
			return "";
		}
		if (value instanceof IBaseTemplateModel) {
			IBaseTemplateModel template = (IBaseTemplateModel) value;
			return template.getFdName();
		}
		if (value instanceof ISysSimpleCategoryModel) {
			ISysSimpleCategoryModel cate = (ISysSimpleCategoryModel) value;
			return cate.getFdName();
		}
		return "";
	}

	public ActionForward userLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");

		String callbackUrl = request.getParameter("callbackUrl");
		logger.debug("callbackUrl:" + callbackUrl);

		String addAppKey = ResourceUtil
				.getKmssConfigString("kmss.ding.addAppKey");		
		JSONObject rtnjson = new JSONObject();
		try {
			AutoLoginHelper autoLoginHelper = (AutoLoginHelper) SpringBeanUtil.getBean("autoLoginHelper");
			if (!autoLoginHelper.hasLogin()) {
				long time = System.currentTimeMillis();
				String userid = null;
				logger.debug("钉钉返回CODE=" + code);
				if (StringUtil.isNotNull(code)) {
					// JSONObject json =
					// DingUtils.getDingApiService().getUserInfo(code);
					JSONObject json = new JSONObject();
					if (StringUtil.isNotNull(addAppKey)
							&& "true".equals(addAppKey)) {
						logger.debug(
								"----------------F4 移动端单点-------------------");
						// String dingAppKey =
						// request.getParameter("dingAppKey");
						logger.debug("跳转    url:" + callbackUrl);
						String dingAppKey = null;
						if (StringUtil.isNotNull(callbackUrl)
								&& callbackUrl.contains("dingAppKey")) {
							dingAppKey = StringUtil.getParameter(
									callbackUrl.replace("?", "&"),
									"dingAppKey");
						}
						if (StringUtil.isNull(dingAppKey)) {
							logger.warn("F4 单点登录，dingAppKey为空");
						}
						json = DingUtils.getDingApiService()
								.getUserInfoByDingAppKey(code, dingAppKey);

					} else {
						json = DingUtils.getDingApiService().getUserInfo(code);
					}

					logger.debug("钉钉返回userInfo=" + json.toString());
					if ("0".equals(json.getString("errcode"))) {
						userid = json.getString("userid");
					}
					logger.debug("EKP与钉钉开放授权  OK username=" + userid);
					if (StringUtil.isNotNull(userid)) {
							IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
									.getBean("omsRelationService");
							HQLInfo hqlInfo = new HQLInfo();
						String whereBlock = "omsRelationModel.fdAppPkId = :fdAppPkId";
						if (StringUtil.isNotNull(addAppKey)
								&& "true".equals(addAppKey)) {
							logger.debug(
									"----------------F4 pc单点-------------------");
							whereBlock = "omsRelationModel.fdAppPkId = :fdAppPkId and omsRelationModel.fdType = 8 and omsRelationModel.fdAppKey=:fdAppKey";
							logger.debug("跳转    url:" + callbackUrl);
							String dingAppKey = null;
							if (StringUtil.isNotNull(callbackUrl)
									&& callbackUrl.contains("dingAppKey")) {
								dingAppKey = StringUtil.getParameter(
										callbackUrl.replace("?", "&"),
										"dingAppKey");
							}
							logger.debug("--------F4------  dingAppKey:"
									+ dingAppKey);
							if (StringUtil.isNull(dingAppKey)) {
								logger.warn(
										"-----  F4 单点链接url没有 dingAppKey 参数，无法单点验证------");
								rtnjson.put("error", 1);
								rtnjson.put("msg",
										"F4 单点链接url没有 dingAppKey参数，无法单点验证");
							} else {
								hqlInfo.setParameter("fdAppPkId", userid);
								hqlInfo.setParameter("fdAppKey", dingAppKey);
								hqlInfo.setWhereBlock(whereBlock);
								OmsRelationModel relation = (OmsRelationModel) omsRelationService
										.findFirstOne(hqlInfo);
								String fdLoginName = null;
								if (relation != null) {
									String ekpid = relation.getFdEkpId();
									fdLoginName = autoLoginHelper.doAutoLogin(
											ekpid, "id", request.getSession());
								} else {
									logger.warn(
											"------F4 开启了addAppKey参数，不直接通过userId作为登录名校验--------");
								}
								if (StringUtil.isNotNull(fdLoginName)) {
									request.getSession()
											.setAttribute("S_PADFlag", "1");
									rtnjson.put("error", 0);
									rtnjson.put("msg", "OK");
								} else {
									rtnjson.put("error", 1);
									rtnjson.put("msg", "钉钉映射表中不存在当前EKP用户的映射信息("
											+ userid + "),无法单点EKP系统");
								}

							}

						} else {
							hqlInfo.setParameter("fdAppPkId", userid);
							hqlInfo.setWhereBlock(whereBlock);
							OmsRelationModel relation = (OmsRelationModel) omsRelationService.findFirstOne(hqlInfo);
							String fdLoginName = null;
							if (relation != null) {
								String ekpid = relation.getFdEkpId();
								fdLoginName = autoLoginHelper.doAutoLogin(ekpid, "id", request.getSession());
							} else {
								fdLoginName = autoLoginHelper.doAutoLogin(userid, request.getSession());
								logger.debug("钉钉中间映射表不存在映射，直接使用LoginName=" + userid + "进行登录，登录成功后返回的数据：" + fdLoginName);
							}
							if (StringUtil.isNotNull(fdLoginName)){
								request.getSession().setAttribute("S_PADFlag", "1");
								rtnjson.put("error", 0);
								rtnjson.put("msg", "OK");
							}else{
								rtnjson.put("error", 1);
								rtnjson.put("msg", "钉钉映射表中不存在当前EKP用户的映射信息("+userid+"),无法单点EKP系统");
							}
						}
							// logger.debug("UserUtil.getUser().getFdLoginName()返回的数据：" + UserUtil.getUser().getFdLoginName());
							// userData.changeCurrentUser(fdLoginName, null);
					} else {
						rtnjson.put("error", 1);
						rtnjson.put("msg", "EKP根据钉钉的授权码无法获取钉钉的用户信息,无法实现EKP的单点登录,单点失败");
						logger.error("EKP根据钉钉的授权码无法获取钉钉的用户信息,无法实现EKP的单点登录,单点失败");
					}
				} else {
					rtnjson.put("error", 1);
					rtnjson.put("msg", "钉钉返回CODE=" + code + ",无法获取钉钉登录名,单点失败");
					logger.error("钉钉返回CODE=" + code + ",无法获取钉钉登录名,单点失败");
				}
				logger.debug("==========登录耗时(毫秒)：" + (System.currentTimeMillis() - time));
			}else{
				rtnjson.put("error", 0);
				rtnjson.put("msg", "已经登录直接跳过登录步骤");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtnjson.put("error", 1);
			rtnjson.put("msg", e.getMessage());
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(rtnjson.toString());
		return null;
	}
	
	//=====================================F4Demo获取数据======================================//
	/**
	 * @return 为F4平台提供最新的文档信息
	 * @throws Exception
	 */
	/*public ActionForward platform(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-platform", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONArray ja = new JSONArray();
		try {
			List<?> list = null;
			String modelName = null;
			String type = request.getParameter("type");
			int size = 10;
			String rowsize = request.getParameter("rowsize");
			if(StringUtil.isNull(type))
				type = "news";
			if(StringUtil.isNotNull(rowsize))
				size = Integer.parseInt(rowsize);
			HQLInfo info = new HQLInfo();
			info.setPageNo(1);
			info.setRowSize(size);
			String whereBlock = "docStatus=:docStatus";;
			if(type.equalsIgnoreCase("news")){
				modelName = "com.landray.kmss.sys.news.model.SysNewsMain";
				info.setModelName(modelName);
			}else if(type.equalsIgnoreCase("institution")){
				modelName = "com.landray.kmss.km.institution.model.KmInstitutionKnowledge";
				whereBlock += " and docIsNewVersion =:docIsNewVersion";
				info.setModelName(modelName);
				info.setParameter("docIsNewVersion", true);
			}else if(type.equalsIgnoreCase("kmsdoc")){
				modelName = "com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge";
				whereBlock += " and docIsNewVersion =:docIsNewVersion";
				info.setModelName(modelName);
				info.setParameter("docIsNewVersion", true);
			}
			info.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
			info.setWhereBlock(whereBlock);
			if(type.equalsIgnoreCase("news")){
				info.setOrderBy("fdIsTop desc, fdTopTime desc, docPublishTime desc");
			}else{
				info.setOrderBy("docPublishTime desc");
			}
			ISysNewsMainService sysNewsMainService = (ISysNewsMainService) SpringBeanUtil.getBean("sysNewsMainService");
			info.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
			Page page = sysNewsMainService.findPage(info);
			list = page.getList();
			JSONObject jo = null;
			Map modelMap = null;
			Object model = null;
			String fdId = null;
			for (int i = 0; i < list.size(); i++) {
				jo = new JSONObject();
				model = list.get(i);
				modelMap = BeanUtils.describe(model);
				fdId = BeanUtils.getProperty(model, "fdId");
				jo.put("title","");
				if(modelMap.containsKey("docSubject")){
					jo.put("title", BeanUtils.getSimpleProperty(model, "docSubject"));
				}
				jo.put("source", "");
				if(modelMap.containsKey("docCreator")){
					jo.put("source", BeanUtils.getProperty(model, "docCreator.fdName"));
				}
				Date dateTime = new Date();
				if(modelMap.containsKey("docPublishTime")){
					dateTime = (Date) PropertyUtils.getProperty(model, "docPublishTime");
				}
				jo.put("dateTime", DateUtil.convertDateToString(dateTime, DateUtil.TYPE_DATE, null));
				String url = ModelUtil.getModelUrl(modelName);
				url = url.replace("${fdId}", fdId);
				jo.put("link", StringUtil.formatUrl(url));
				jo.put("image", StringUtil.formatUrl("/km/doc/resource/images/unConverted.png"));
				String imageUrl = docThumb(fdId,modelName);
				if(StringUtil.isNotNull(imageUrl))
					jo.put("image", StringUtil.formatUrl(imageUrl));
				ja.add(jo);
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-platform", false, getClass());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(ja.toString());
		return null;
	}
	
	public String docThumb(String fdId,String modelName) throws Exception {
		String imgAttUrl = "";
		SysAttMain imgAttMain = null;
		ISysAttMainCoreInnerService sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService");
		List<SysAttMain> attMainList = sysAttMainCoreInnerService.findByModelKey(modelName, fdId, "spic");
		// 如果上传了封面图片
		if (attMainList.size() > 0) {
			imgAttMain = attMainList.get(0);
			imgAttUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=download&open=1&fdId="
					+ imgAttMain.getFdId();
		} else {
			// 如果有上传附件
			List<SysAttMain> attachments = sysAttMainCoreInnerService.findByModelKey(modelName, fdId, "attachment");
			for(SysAttMain main:attachments){
				if(main.getFdContentType().indexOf("image")>-1){
					imgAttUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=download&open=1&fdId="
							+ main.getFdId();
					break;
				}
			}
		}
		return imgAttUrl;
	}*/
	//=====================================F4Demo获取数据======================================//
	
	private static String getOmsRelation(String fdId) throws Exception {
		IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock("fdEkpId='" + fdId + "'");
		return (String) omsRelationService.findFirstOne(hqlInfo);
	}
	
	private DingApiService dingApiService = DingUtils.getDingApiService();
	
	public ActionForward deleteDing(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject ret = dingApiService.departGet();
		if (ret == null) {
			logger.warn("获取钉钉部门列表发生不可预知的错误");
		} else {
			if (ret.getInt("errcode") == 0) {
				JSONArray depts = ret.getJSONArray("department");
				Map<String, String> hierMaps = new HashMap<String, String>(depts.size());
				Map<String, JSONObject> deptMaps = new HashMap<String, JSONObject>(depts.size());
				String id = null;
				JSONObject jdept = null;
				StringBuffer parents = new StringBuffer();
				Map<String, String> map = new HashMap<String, String>();
				for (int k = 0; k < depts.size(); k++) {
					jdept = depts.getJSONObject(k);
					if (!jdept.containsKey("parentid") || "1".equals(jdept.getString("id"))) {
						map.put(jdept.getString("id"), "");
					} else if (jdept.containsKey("parentid")) {
						map.put(jdept.getString("id"), jdept.getString("parentid"));
					}
				}
				for (int k = 0; k < depts.size(); k++) {
					parents.setLength(0);
					id = depts.getJSONObject(k).getString("id");
					getParent(id, parents, map);
					hierMaps.put(id, parents.toString());
					deptMaps.put(id, depts.getJSONObject(k));
				}
				List<String> hiers = new ArrayList<String>(hierMaps.values());
				Collections.sort(hiers, new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						if (o1.length() > o2.length()) {
							return -1;
						} else if (o1.length() < o2.length()) {
							return 1;
						} else {
							return 0;
						}
					}
				});
				logger.warn("从钉钉获取部门数:" + depts.size());
								
				for (String deptId : hiers) {
					logger.warn("部门层级："+deptId);
					jdept = deptMaps.get(deptId.split("\\|")[0]);
					Long deptId1 = jdept.getLong("id");
					if (deptId1 == 1) {
                        continue;
                    }
					String name = jdept.getString("name");
					String msg = "\t开始删除钉钉部门及部门(名称：${name},dingId=${deptId})下的员工".replace("${name}", name).replace("${deptId}",
							deptId1.toString());
					List<Userlist> userlists = new ArrayList<Userlist>();
					dingApiService.userList(userlists, deptId1, 0L);
					logger.warn(msg + "(员工数:" + userlists.size() + ")");
					for(Userlist ulist:userlists){
						if(ulist.getIsAdmin()){
							JSONObject person = new JSONObject();
							person.accumulate("userid", ulist.getUserid());
							person.accumulate("name", ulist.getName());
							JSONArray adepts = new JSONArray();
							adepts.add("1");
							person.accumulate("department", adepts);
							dingApiService.userUpdate(person);
						}else{
							dingApiService.userDelete(ulist.getUserid());
						}
					}
					dingApiService.departDelete(deptId1+"");
				}
				
			} else {
				logger.warn(" 失败,出错信息：" + ret.getString("errmsg"));
			}
		}
		return null;
	}
	
	private void getParent(String id, StringBuffer ids, Map<String, String> map) {
		if (map.containsKey(id)) {
			ids.append(id + "|");
			getParent(map.get(id), ids, map);
		}
	}
	/**
	 * 获取id相关信息
	 * @param str
	 * @return
	 * @throws Exception 
	 */
	public static String getUserInfos(String str) throws Exception{
		ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
		String[] ids = str.split(";");
		List<String> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		String id = null;
		int n = 0;
		//去重
		for(int i = 0;i < ids.length; i++){
			if(!list.contains(ids[i]) && !UserUtil.getUser().getFdId().equals(ids[i])){
				list.add(ids[i]);
			}
		}
		for (String idStr : list) {
			SysOrgElement element = sysOrgCoreService.findByPrimaryKey(idStr);
			if (n > 0) {
				sb.append(",");
			}
			if(element != null){
				if(element.getFdOrgType() == SysOrgElement.ORG_TYPE_PERSON){
					SysOrgPerson person = (SysOrgPerson) sysOrgCoreService.format(element);
					id = getOmsRelation(person.getFdId());
					if (StringUtil.isNotNull(id)) {
						sb.append("'" + id + "'");
					}else{
						sb.append("'" + person.getFdLoginName() + "'");
					}
				}else if(element.getFdOrgType() == SysOrgElement.ORG_TYPE_POST){
					SysOrgPost post = (SysOrgPost) sysOrgCoreService.format(element);
					for (int j = 0; j < post.getFdPersons().size(); j++) {
						SysOrgPerson person = (SysOrgPerson) post.getFdPersons().get(j);
						if (person != null) {
							id = getOmsRelation(person.getFdId());
							if (StringUtil.isNotNull(id)) {
								sb.append("'" + id + "'");
							}else{
								sb.append("'" + person.getFdLoginName() + "'");
							}
						}
					}
				}
			}
			n++;
		}
		return sb.toString();
	}
}
