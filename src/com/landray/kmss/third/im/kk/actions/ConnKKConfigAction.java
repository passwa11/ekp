package com.landray.kmss.third.im.kk.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.webservice2.model.SysWebserviceMain;
import com.landray.kmss.sys.webservice2.model.SysWebserviceUser;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceMainService;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceUserService;
import com.landray.kmss.third.im.kk.KkConfig;
import com.landray.kmss.third.im.kk.cluster.KKNotifyMessage;
import com.landray.kmss.third.im.kk.cluster.KKNotifyMessageType;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.model.KkImConfig;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.third.im.kk.util.AES;
import com.landray.kmss.third.im.kk.util.HttpRequest;
import com.landray.kmss.third.im.kk.util.KKConfigUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 连接kk配置页面
 * @author 孙佳
 * @date 2017年8月15日
 */
public class ConnKKConfigAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ConnKKConfigAction.class);

	protected IKkImConfigService kkImConfigService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if(kkImConfigService == null){
			kkImConfigService = (IKkImConfigService)getBean("kkImConfigService");
		}
		return kkImConfigService;
	}

	protected ISysWebserviceUserService sysWebserviceUserService;

	protected ISysWebserviceUserService getWebServiceUserImp(HttpServletRequest request) {
		if (sysWebserviceUserService == null) {
			sysWebserviceUserService = (ISysWebserviceUserService) getBean("sysWebserviceUserService");
		}
		return sysWebserviceUserService;
	}

	protected ISysWebserviceMainService sysWebserviceMainService;

	protected ISysWebserviceMainService getWebserviceMainImp(HttpServletRequest request) {
		if (sysWebserviceMainService == null) {
			sysWebserviceMainService = (ISysWebserviceMainService) getBean("sysWebserviceMainService");
		}
		return sysWebserviceMainService;
	}

	/**
	 * <p>检查是否已经配置kk集成</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @author 孙佳
	 */
	public String checkKKConfig(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject map = new JSONObject();
			boolean kkConfigFlag = true;
			String kkConfigStatus = getValuebyKey(request, KeyConstants.KK_CONFIG_SATUS);
			if (StringUtil.isNull(kkConfigStatus)) {
				kkConfigFlag = false;
			}
			map.put("kkConfigFlag", kkConfigFlag);
			// 记录操作日志
			if (UserOperHelper.allowLogOper("checkKKConfig", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
				UserOperHelper.logMessage(map.toString());
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(map.toString());
			response.getWriter().flush();
			response.getWriter().close();

		} catch (Exception e) {
			logger.debug("效验出错啦");
		}
		return null;
	}

	/**
	 * 检查是否配置内网、外网地址
	 */
	public String checkUrl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject map = new JSONObject();
			boolean checkFlag = true;
			if (StringUtil.isNull(ResourceUtil.getKmssConfigString("kmss.urlPrefix"))) {
				checkFlag = false;
			}
			if(StringUtil.isNull(ResourceUtil.getKmssConfigString("kmss.innerUrlPrefix"))){
				checkFlag = false;
			}
			//增加初始化密钥
			if (checkFlag) {
				saveKKParam(request, KeyConstants.EKP_SECRETKEY, "");
			}
			map.put("checkFlag", checkFlag);
			// 记录操作日志
			if (UserOperHelper.allowLogOper("checkUrl", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
				UserOperHelper.logMessage(map.toString());
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(map.toString());
			response.getWriter().flush();
			response.getWriter().close();

		} catch (Exception e) {
			logger.debug("效验出错啦");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 连接配置页面(不需要传递密钥)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward simpleConnectPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			//查询控制台地址
			String conAddress = getValuebyKey(request, KeyConstants.KK_CONSOLE_ADDRESS);
			request.setAttribute("conAddress", conAddress);
			if (UserOperHelper.allowLogOper("simpleConnectPage", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
				UserOperHelper.logMessage(conAddress);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return getActionForward("simpleConnect", mapping, form, request, response);
		}

	}

	/**
	 * 连接配置页面(传递密钥)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward connectPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			//产生连接密钥,并保存
			String secret = getSecret(request);
			//查询控制台地址
			String conAddress = getValuebyKey(request, KeyConstants.KK_CONSOLE_ADDRESS);
			request.setAttribute("conAddress", conAddress);
			request.setAttribute("secret", secret);
			if (UserOperHelper.allowLogOper("connectPage", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
				UserOperHelper.logMessage("{secret:" + secret + ",conAddress:"
						+ conAddress + "}");
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return getActionForward("connect", mapping, form, request, response);
		}

	}

	/**
	 * <p>修改kk控制台地址</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author 孙佳
	 */
	public void modifyConAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject map = new JSONObject();
		try {
			String conAddress = request.getParameter("conAddress");
			saveKKParam(request, KeyConstants.KK_CONSOLE_ADDRESS, conAddress);
			map.put("result", "0");
			if (UserOperHelper.allowLogOper("modifyConAddress", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
				UserOperHelper.logMessage(map.toString());
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(map.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			logger.debug("修改kk控制台地址出错啦");
			e.printStackTrace();
		}
	}

	/**
	 * 登录kk控制台
	 */
	public void loginKK(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JSONObject map = new JSONObject();
		try {
			String conAddress = getValuebyKey(request, KeyConstants.KK_CONSOLE_ADDRESS);
			//签名
			String secretkey = getValuebyKey(request, KeyConstants.EKP_SECRETKEY);
			String sign = KKConfigUtil.getCurrDateSign(secretkey);
			map.put("result", "0");
			map.put("conAddress", conAddress);
			map.put("sign", sign);
			if (UserOperHelper.allowLogOper("loginKK", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
				UserOperHelper.logMessage(map.toString());
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(map.toString());
			response.getWriter().flush();
			response.getWriter().close();

		} catch (Exception e) {
			logger.debug("登录kk控制台出错啦");
			e.printStackTrace();
		}
	}

	/**
	 * <p>kk服务器SAY HELLO</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author 孙佳
	 */
	public void kkHello(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("-----------------------调用kk服务器SAY HELLO---------------------------");

		JSONObject map = new JSONObject();
		try {
			String serverjAuthId = getValuebyKey(request, KeyConstants.KK_SERVERJ_AUTHID);
			String sererrjAuthKey = getValuebyKey(request, KeyConstants.KK_SERERRJ_AUTHKEY);
			if (StringUtil.isNull(serverjAuthId) || StringUtil.isNull(sererrjAuthKey)) {
				throw new Exception();
			}
			//签名
			String retStrFormatNowDate = String.valueOf(System.currentTimeMillis());
			String serverjAuthSign = AES.encrypt2str(serverjAuthId + "|" + retStrFormatNowDate, sererrjAuthKey);
			
			String innerDomain = getValuebyKey(request, KeyConstants.KK_INNER_DOMAIN);
			//kk服务器SAY HELLO
			String url = innerDomain + "serverj/permision/hello.ajax";
			Map<String, String> param = new HashMap<String, String>();
			param.put("serverj-auth-id", serverjAuthId);
			param.put("serverj-auth-sign", serverjAuthSign);
			String result = HttpRequest.sendGetParamByHeader(url, param);
			JSONObject json = JSONObject.fromObject(result);
			logger.info("kkHello" + result);
			map.put("result", json.get("result").toString());
			map.put("error_msg", json.get("error_msg"));
			if (UserOperHelper.allowLogOper("kkHello", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
				UserOperHelper.logMessage(map.toString());
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(map.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			logger.debug("kk服务器SAY HELLO请求出错");
			e.printStackTrace();
		}
	}


	/**
	 * 调用kk 参数交换接口
	 */
	public String exchange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		logger.info("-----------------------调用kk 参数交换接口---------------------------");

		JSONObject map = new JSONObject();
		try {
			String conAddress = request.getParameter("conAddress");
			//---------------------begin兼容旧的加密方式-------------------
			String encryptType = request.getParameter("encryptType");
			if (StringUtil.isNotNull(encryptType)) {
				saveKKParam(request, KeyConstants.ENCRYP_TTYPE, encryptType);
			}
			//---------------------end兼容旧的加密方式-------------------

			//签名
			String secretkey = getValuebyKey(request, KeyConstants.EKP_SECRETKEY);
			String sign = KKConfigUtil.getCurrDateSign(secretkey);
			// 获取配置的ekp内网、外网地址
			String urlPrefix = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
			String innerUrlPrefix = ResourceUtil.getKmssConfigString("kmss.innerUrlPrefix");

			//webservice账号密码
			String ws_user = null, ws_pwd = null;
			//查询是否有账号,没有新增webservice账号
			if (null == getValuebyKey(request, KeyConstants.WS_USER)) {
				getwebServiceUser(request);
			}
			ws_user = getValuebyKey(request, KeyConstants.WS_USER);
			ws_pwd = getValuebyKey(request, KeyConstants.WS_PWD);

			//密钥
			String secret = getSecret(request);

			// 调用kk交换参数接口
			JSONObject param = new JSONObject();
			param.put("inner_domain", innerUrlPrefix);
			param.put("outer_domain", urlPrefix);
			param.put("ws_user", ws_user);
			param.put("ws_pwd", ws_pwd);
			param.put("secretkey", secret);
			String url = conAddress + "ekp_exchange?action=exchange&sign=" + sign;
			String result = HttpRequest.sendPost(url, param.toString());
			// 保存kk返回参数

			JSONObject json = JSONObject.fromObject(result);

			if ("0".equals(json.get("result").toString())) {
				JSONObject jsonData = JSONObject.fromObject(json.get("data"));
				//保存kk内网地址
				saveKKParam(request, KeyConstants.KK_INNER_DOMAIN, jsonData.get("inner_domain").toString());
				//保存kk外网地址
				saveKKParam(request, KeyConstants.KK_OUTER_DOMAIN, jsonData.get("outer_domain").toString());
				//保存KK 接口鉴权身份ID
				saveKKParam(request, KeyConstants.KK_SERVERJ_AUTHID, jsonData.get("serverj_auth_id").toString());
				//保存KK 接口鉴权身份密钥
				saveKKParam(request, KeyConstants.KK_SERERRJ_AUTHKEY, jsonData.get("sererrj_auth_key").toString());
				//保存kk控制台地址
				saveKKParam(request, KeyConstants.KK_CONSOLE_ADDRESS, conAddress);
			}
			logger.info("exchange" + result);
			map.put("result", json.get("result"));
			map.put("error_msg", json.get("error_msg"));
			if (UserOperHelper.allowLogOper("exchange", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
				UserOperHelper.logMessage(map.toString());
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(map.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			logger.debug("调用exchange接口出错啦");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>如果密钥为空，则生成密钥，否则return</p>
	 * @author 孙佳
	 * @throws Exception 
	 */
	private String getSecret(HttpServletRequest request) throws Exception {
		String secret = getValuebyKey(request, KeyConstants.EKP_SECRETKEY);
		if (StringUtil.isNull(secret)) {
			//生成密钥
			secret = KKConfigUtil.getRandomString(16);
			saveKKParam(request, KeyConstants.EKP_SECRETKEY, secret);
		}
		return secret;
	}

	/**
	 * <p>保存kk返回参数</p>
	 * @author 孙佳
	 */
	private void saveKKParam(HttpServletRequest request, String key, String value) {
		KkImConfig config = null;
		try {
			//保存kk内网地址
			config = new KkImConfig();
			config.setFdKey(key);
			config.setFdValue(value);
			if (!checkByKey(request, key)) {
				getServiceImp(request).add(config);
			} else {
				((IKkImConfigService) getServiceImp(request)).updateValueBykey(key, value);
			}
		} catch (Exception e) {
			logger.debug("保存kk参数出错啦！");
			e.printStackTrace();
		}
	}


	/**
	 * 新增webservice账号
	 * @throws Exception 
	 */
	private void getwebServiceUser(HttpServletRequest request) {
		try {
			String userName = KKConfigUtil.getRandomString(6);
			String password = KKConfigUtil.getRandomString(6);
			HQLInfo hqlInfo = new HQLInfo();
			SysWebserviceUser webserviceUser = new SysWebserviceUser();
			webserviceUser.setFdLoginId(userName);
			webserviceUser.setFdPassword(password);
			webserviceUser.setFdName(userName);
			webserviceUser.setFdPolicy("0");
			webserviceUser.setFdAuthType("0");

			//获取webserviceMain
			hqlInfo.setWhereBlock("fdServiceBean in('thirdImSyncForKKWebService','sysSynchroGetOrgWebService','sysNotifyTodoWebService')");
			List<SysWebserviceMain> mianList = getWebserviceMainImp(request).findList(hqlInfo);
			if (null == mianList) {
				throw new Exception();
			}
			webserviceUser.setFdService(mianList);
			getWebServiceUserImp(request).add(webserviceUser);

			// 保存webservice账号密码
			KkImConfig wsUser = new KkImConfig();
			wsUser.setFdKey(KeyConstants.WS_USER);
			wsUser.setFdValue(userName);
			getServiceImp(request).add(wsUser);

			KkImConfig wsPwd = new KkImConfig();
			wsPwd.setFdKey(KeyConstants.WS_PWD);
			wsPwd.setFdValue(webserviceUser.getFdPassword());
			getServiceImp(request).add(wsPwd);
		} catch (Exception e) {
			logger.debug("新增webservice账号失败！");
			e.printStackTrace();
		}
	}


	/**
	 * 同步配置页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward synConfigPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (UserOperHelper.allowLogOper("synConfigPage", null)) {
			UserOperHelper.setModelNameAndModelDesc(null,
					ResourceUtil.getString("third-im-kk:module.third.im.kk"));
		}
		return getActionForward("syn", mapping, form, request,
				response);
	}

	/**
	 * <p>同步配置定义接口</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	public void syncDefine(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject map = new JSONObject();
		try {
			String selected = request.getParameter("selected");
			String[] select = selected.substring(0, selected.length() - 1).split(",");
			boolean org = false, sso = false, pc_app = false, mobile_app = false, org_visible = false, sms = false,
					robot = false, extend = false;
			for (int i = 0; i < select.length; i++) {
				if ("org".equals(select[i])) {
					org = true;
					continue;
				}
				if ("sso".equals(select[i])) {
					sso = true;
					continue;
				}
				if ("pcApp".equals(select[i])) {
					pc_app = true;
					continue;
				}
				if ("mobileApp".equals(select[i])) {
					mobile_app = true;
					continue;
				}
				if ("orgVisible".equals(select[i])) {
					org_visible = true;
					continue;
				}
				if ("sms".equals(select[i])) {
					sms = true;
					continue;
				}
				if ("robot".equals(select[i])) {
					robot = true;
					continue;
				}
				if ("extend".equals(select[i])) {
					extend = true;
					continue;
				}
			}

			String result = KKSyncDefine(request, org, sso, pc_app, mobile_app, org_visible, sms, robot, extend);
			JSONObject json = JSONObject.fromObject(result);
			//保存同步配置
			if ("0".equals(json.get("result").toString())) {
				saveKKParam(request, KeyConstants.EKP_ORG, String.valueOf(org));
				saveKKParam(request, KeyConstants.EKP_SSO, String.valueOf(sso));
				saveKKParam(request, KeyConstants.EKP_PC_APP, String.valueOf(pc_app));
				saveKKParam(request, KeyConstants.EKP_MOBILE_APP, String.valueOf(mobile_app));
				saveKKParam(request, KeyConstants.EKP_ORG_VISIBLE, String.valueOf(org_visible));
				saveKKParam(request, KeyConstants.EKP_SMS, String.valueOf(sms));
				saveKKParam(request, KeyConstants.ROBOT, String.valueOf(robot));
				saveKKParam(request, KeyConstants.EXTEND_APP, String.valueOf(extend));
				saveKKParam(request, KeyConstants.EKP_NOTIFY, String.valueOf(true));
				saveKKParam(request, KeyConstants.EKP_NOTIFY_TOREAD, String.valueOf(true));
				saveKKParam(request, KeyConstants.EKP_NOTIFY_TODO, String.valueOf(true));
				saveKKParam(request, KeyConstants.EKP_NOTIFY_TYPE, "corpNotify");
				saveKKParam(request, KeyConstants.EKP_LOGBAK_DAYS, "30");
				saveKKParam(request, KeyConstants.EKP_SELECT_SERVICE, null);
				//是否推送待办来源信息（针对其他系统）,默认不推送
				saveKKParam(request, KeyConstants.TODO_SOURCE, String.valueOf(false));
				saveKKParam(request, KeyConstants.EKP_IMTRANSFERENABLE,
						String.valueOf(true));
			}

			map.put("result", json.get("result"));
			map.put("error_msg", json.get("error_msg"));
			if (UserOperHelper.allowLogOper("syncDefine", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
				UserOperHelper.logMessage(map.toString());
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(map.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			logger.debug("调用kk同步配置接口出错啦");
			e.printStackTrace();
		}
	}

	/**
	 * <p>KK同步配置定义接口</p>
	 * @return
	 * @author 孙佳
	 * @throws Exception 
	 */
	private String KKSyncDefine(HttpServletRequest request, boolean org, boolean sso, boolean pc_app,
			boolean mobile_app, boolean org_visible, boolean sms, boolean robot, boolean extend) throws Exception {
		logger.info("-----------------------调用kkKK同步配置定义接口---------------------------");

		String conAddress = getValuebyKey(request, "kkConAddress");
		//签名
		String secretkey = getValuebyKey(request, "secretkey");
		String sign = KKConfigUtil.getCurrDateSign(secretkey);

		// 调用kk同步配置定义接口
		JSONObject param = new JSONObject();
		param.put("org", org);
		param.put("sso", sso);
		param.put("pc_app", pc_app);
		param.put("mobile_app", mobile_app);
		param.put("org_visible", org_visible);
		param.put("sms", sms);
		param.put("robot", robot);
		param.put("extend_app", extend);
		String url = conAddress + "ekp_exchange?action=syncDefine&sign=" + sign;
		String result = HttpRequest.sendPost(url, param.toString());
		return result;
	}

	/**
	 * 展示配置信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward infoConfigPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		List<String> list = new ArrayList<String>();
		String selected = request.getParameter("selected");
		String[] select = selected.substring(0, selected.length() - 1).split(",");
		for (int i = 0; i < select.length; i++) {
			list.add(select[i]);
		}
		// 记录操作日志
		if (UserOperHelper.allowLogOper("infoConfigPage", null)) {
			UserOperHelper.setModelNameAndModelDesc(null,
					ResourceUtil.getString("third-im-kk:module.third.im.kk"));
			UserOperHelper.logErrorMessage(selected);
		}
		request.setAttribute("list", list);
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return getActionForward("info", mapping, form, request, response);
		}
	}

	/**
	 * <p>同步通知接口</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author 孙佳
	 */
	public void syncNotify(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject map = new JSONObject();
		try {
			String selected = request.getParameter("selected");
			String[] select = selected.substring(0, selected.length() - 1).split(",");
			boolean org = false, sso = false, pc_app = false, mobile_app = false, org_visible = false, robot = false,
					extend = false;
			for (int i = 0; i < select.length; i++) {
				if ("org".equals(select[i])) {
					org = true;
					continue;
				}
				if ("sso".equals(select[i])) {
					sso = true;
					continue;
				}
				if ("pcApp".equals(select[i])) {
					pc_app = true;
					continue;
				}
				if ("mobileApp".equals(select[i])) {
					mobile_app = true;
					continue;
				}
				if ("orgVisible".equals(select[i])) {
					org_visible = true;
					continue;
				}
				if ("robot".equals(select[i])) {
					robot = true;
					continue;
				}
				if ("extend".equals(select[i])) {
					extend = true;
					continue;
				}
			}

			String result = KKNotify(request, org, sso, pc_app, mobile_app, org_visible, robot, extend);
			JSONObject json = JSONObject.fromObject(result);

			if ("0".equals(json.get("result").toString())) {
				//默认开启kk集成配置
				saveKKParam(request, KeyConstants.KK_CONFIG_SATUS, String.valueOf(true));
			}

			map.put("result", json.get("result"));
			map.put("error_msg", json.get("error_msg"));
			// 记录操作日志
			if (UserOperHelper.allowLogOper("syncNotify", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
				UserOperHelper.logErrorMessage(json.toString());
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(map.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			logger.debug("调用kk 同步通知接口出错啦");
			e.printStackTrace();
		}
	}

	/**
	 * <p>调用KK同步通知接口</p>
	 * @return
	 * @author 孙佳
	 * @throws Exception 
	 */
	private String KKNotify(HttpServletRequest request, boolean org, boolean sso, boolean pc_app, boolean mobile_app,
			boolean org_visible, boolean robot, boolean extend_app) throws Exception {
		logger.info("-----------------------调用KK同步通知接口---------------------------");

		String conAddress = getValuebyKey(request, KeyConstants.KK_CONSOLE_ADDRESS);
		//签名
		String secretkey = getValuebyKey(request, KeyConstants.EKP_SECRETKEY);
		String sign = KKConfigUtil.getCurrDateSign(secretkey);

		// 调用kk同步通知接口
		JSONObject param = new JSONObject();
		param.put("org", org);
		param.put("sso", sso);
		param.put("pc_app", pc_app);
		param.put("mobile_app", mobile_app);
		param.put("org_visible", org_visible);
		param.put("robot", robot);
		param.put("extend_app", extend_app);
		String url = conAddress + "ekp_exchange?action=notify&sign=" + sign;
		String result = HttpRequest.sendPost(url, param.toString());
		return result;
	}


	/**
	 * kk集成配置展示页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward configView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			Map<String, String> map = new HashMap<String, String>();

			List<KkImConfig> list = getServiceImp(request).findList(new HQLInfo());
			for (KkImConfig config : list) {
				map.put(config.getFdKey(), config.getFdValue());
			}
			// 记录操作日志
			if (UserOperHelper.allowLogOper("configView", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
				JSONObject json = JSONObject.fromObject(map);
				String msssage = json.toString();
				UserOperHelper.logMessage(msssage);
			}
			request.setAttribute("map", map);

		} catch (Exception e) {
			logger.debug("kk集成配置展示页面出错啦！");
			e.printStackTrace();
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return getActionForward("view", mapping, form, request, response);
		}
	}

	/**
	 * 重置kk集成配置
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetConfig(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			//获取webservice账号密码
			String wsUser = getValuebyKey(request, KeyConstants.WS_USER);
			String wsPwd = getValuebyKey(request, KeyConstants.WS_PWD);

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" fdLoginId = :fdLoginId and fdPassword = :fdPassword");
			hqlInfo.setParameter("fdLoginId", wsUser);
			hqlInfo.setParameter("fdPassword", wsPwd);
			List<SysWebserviceUser> webserviceUser = getWebServiceUserImp(request).findValue(hqlInfo);
			if (null != webserviceUser && webserviceUser.size() > 0) {
				getWebServiceUserImp(request).delete(webserviceUser.get(0));
			}

			((IKkImConfigService) getServiceImp(request)).deleteAll();
			// 记录操作日志
			if (UserOperHelper.allowLogOper("resetConfig", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
			}
		} catch (Exception e) {
			logger.debug("重置kk集成配置出错啦！");
			e.printStackTrace();
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return getActionForward("choose", mapping, form, request, response);
		}
	}

	/**
	 * <p>保存kk集成配置</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	public ActionForward saveKKConfig(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		String resultCode = null, errorMsg = null;
		JSONObject obj = new JSONObject();
		try {
			JSONObject object = JSONObject.fromObject(request.getParameter("data"));
			Iterator iterator = object.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				String value = object.getString(key);
				((IKkImConfigService) getServiceImp(request)).updateValueBykey(key, value);
			}
			//添加缓存
			updateConfigCache(request);

			//获取配置参数
			boolean org = Boolean.valueOf(getValuebyKey(request, KeyConstants.EKP_ORG));
			boolean sso = Boolean.valueOf(getValuebyKey(request, KeyConstants.EKP_SSO));
			boolean pc_app = Boolean.valueOf(getValuebyKey(request, KeyConstants.EKP_PC_APP));
			boolean mobile_app = Boolean.valueOf(getValuebyKey(request, KeyConstants.EKP_MOBILE_APP));
			boolean org_visible = Boolean.valueOf(getValuebyKey(request, KeyConstants.EKP_ORG_VISIBLE));
			boolean sms = Boolean.valueOf(getValuebyKey(request, KeyConstants.EKP_SMS));
			boolean robot = Boolean.valueOf(getValuebyKey(request, KeyConstants.ROBOT));
			boolean extend_app = Boolean.valueOf(getValuebyKey(request, KeyConstants.EXTEND_APP));

			// 调用kk调用kk同步配置定义接口

			String result = KKSyncDefine(request, org, sso, pc_app, mobile_app, org_visible, sms, robot, extend_app);
			JSONObject sysJson = JSONObject.fromObject(result);
			resultCode = sysJson.get("result").toString();
			if ("0".equals(resultCode)) {
				result = KKNotify(request, org, sso, pc_app, mobile_app, org_visible, robot, extend_app);
			} else {
				errorMsg = sysJson.get("error_msg").toString();
			}
			JSONObject resultJson = JSONObject.fromObject(result);
			logger.debug(resultJson.toString());
			// 记录操作日志
			if (UserOperHelper.allowLogOper("saveKKConfig", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil
								.getString("third-im-kk:module.third.im.kk"));
				UserOperHelper.logMessage(resultJson.toString());
				UserOperHelper.setOperSuccess(true);
			}
		} catch (Exception e) {
			logger.debug("保存kk集成配置出错啦！");
			e.printStackTrace();
		}

		obj.accumulate("msg", errorMsg);
		request.setAttribute("lui-source", obj);

		if (messages.hasError()) {
			obj.accumulate("result", false);
			return getActionForward("lui-failure", mapping, form, request, response);
		}
		if ("0".equals(resultCode)) {
			obj.accumulate("result", true);
		}

		return getActionForward("lui-source", mapping, form, request, response);
	}

	/**
	 * 根据key获取对应的value
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getValuebyKey(HttpServletRequest request,String key) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(" kkImConfig.fdKey = :key");
		hqlInfo.setParameter("key", key);
		List<KkImConfig> list = getServiceImp(request).findList(hqlInfo);
		if (null != list && list.size() > 0) {
			return list.get(0).getFdValue();
		}
		return null;
	}

	/**
	 * <p>检查key对应的记录是否存在</p>
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	private boolean checkByKey(HttpServletRequest request, String key) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(" kkImConfig.fdKey = :key");
		hqlInfo.setParameter("key", key);
		List<KkImConfig> list = getServiceImp(request).findList(hqlInfo);
		if (null != list && list.size() > 0) {
			if(StringUtil.isNotNull(list.get(0).getFdKey())){
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>更新缓存中的kk集成配置</p>
	 * @author 孙佳
	 */
	private void updateConfigCache(HttpServletRequest request) {

		try {
			KkConfig kkConfig = new KkConfig();
			Map<String, String> map = new HashMap<String, String>();
			List<KkImConfig> list = getServiceImp(request).findList(new HQLInfo());
			if (null != list && list.size() > 0) {
				for (KkImConfig config : list) {
					map.put(config.getFdKey(), config.getFdValue());
				}
			}
			//添加数据到缓存
			kkConfig.getDataMap().putAll(map);

			MessageCenter.getInstance().sendToOther(
					new KKNotifyMessage(KKNotifyMessageType.NOTIFY_MESSAGE_KK_CONFIG_UPDATE, kkConfig.getDataMap()));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
	}
}
