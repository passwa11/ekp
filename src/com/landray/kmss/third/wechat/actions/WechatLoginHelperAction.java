package com.landray.kmss.third.wechat.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.third.wechat.forms.WechatConfigForm;
import com.landray.kmss.third.wechat.model.WechatConfig;
import com.landray.kmss.third.wechat.model.WechatMainConfig;
import com.landray.kmss.third.wechat.service.IWechatConfigService;
import com.landray.kmss.third.wechat.util.AESUtil;
import com.landray.kmss.third.wechat.util.HttpClientUtil;
import com.landray.kmss.third.wechat.util.LicenseCheckUtil;
import com.landray.kmss.third.wechat.util.WeChatConfigUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public class WechatLoginHelperAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WechatLoginHelperAction.class);

	private IWechatConfigService wechatConfigService;
	
	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (wechatConfigService == null) {
            wechatConfigService = (IWechatConfigService) getBean("wechatConfigService");
        }
		return wechatConfigService;
	}

	private AutoLoginHelper autoLoginHelper;

	protected AutoLoginHelper getAutoLoginHelper() {
		if (autoLoginHelper == null) {
            autoLoginHelper = (AutoLoginHelper) getBean("autoLoginHelper");
        }
		return autoLoginHelper;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
        }
		return sysOrgCoreService;
	}
	
	private static MultiThreadedHttpConnectionManager connectionManager;

	private static MultiThreadedHttpConnectionManager getConnectionManager() {
		if (connectionManager == null) {
			connectionManager = new MultiThreadedHttpConnectionManager();
		}
		return connectionManager;
	}

	public static  String SYSLICENSE = null;
	static {
		SYSLICENSE=LicenseCheckUtil.checkLicense();
	}

	/**
	 * 模拟登陆 跳转
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/**public ActionForward wechatLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		// 获取post数据
		String key = request.getParameter("key");
		String key2 = request.getParameter("key2");
		String key3 = request.getParameter("key3");

		if (StringUtil.isNull(key)) {
			logger.error("WechatLoginHelperAction.wechatLogin,发送的参数数据为空");
			jsonObject.put("flag", "0");
			jsonObject.put("msg", "发送的参数中key为空");
			out.print(jsonObject.toString());
			out.flush();
			return null;
		}
		if (StringUtil.isNull(key2)) {
			logger.error("WechatLoginHelperAction.wechatLogin,发送的参数中key2为空");
			jsonObject.put("flag", "0");
			jsonObject.put("msg", "发送的参数中key2为空");
			out.print(jsonObject.toString());
			out.flush();
			return null;
		}

		// 根据key2 ekpid 查找随机码
		HQLInfo hqlinfo=new HQLInfo();
		hqlinfo.setWhereBlock("fdEkpid = :key2");
		hqlinfo.setParameter("key2", key2);
		List<WechatConfig> resultList = getServiceImp(request).findList(hqlinfo);
		String randomCode = "";
		if (resultList != null && resultList.size() > 0) {
			WechatConfig wc = resultList.get(0);
			randomCode = wc.getFdQyRandom();
			if(StringUtils.isBlank(key3)){
				randomCode = wc.getFdRandom();
			}
			if (StringUtil.isNull(SYSLICENSE)) {
				logger.error("WechatLoginHelperAction.wechatLogin,不支持老版本license");
				jsonObject.put("flag", "0");
				jsonObject.put("msg", "不支持老版本license");
				out.print(jsonObject.toString());
				out.flush();
				return null;
			}
		}
		String aesKey = SYSLICENSE.substring(0, SYSLICENSE.length() / 2)
				+ (StringUtil.isNull(randomCode) ? "" : randomCode.substring(0, randomCode.length() / 2));
		key = AESUtil.decrypt(key, aesKey);

		String[] values = key.split("#@\\$");
		String user = values[0];

		String url = "";
		boolean flag = false; // 外系统获取数据标志
		if (values.length > 1) {
			String str = values[1];
			String[] info = str.split("___");
			if (info.length == 1) {
				url = str + "&_clear=1";
			} else {
				String link = info[0];
				String temp = info[1];
				url = link;
				if ("outer".equals(temp)) {
					flag = true;
				}
			}
		}
		// 登陆状态
		String loginflag = null;
		boolean hasLogin = false;
		if (!user.equals(getAutoLoginHelper().getCurrentUserLoginName())) {
			HttpSession session = request.getSession();
			loginflag = getAutoLoginHelper().doAutoLogin(user, session);
		} else {
			if (!getAutoLoginHelper().hasLogin()) {
				HttpSession session = request.getSession();
				loginflag = getAutoLoginHelper().doAutoLogin(user, session);
			} else {
				hasLogin = true;
			}
		}

		if (loginflag != null || hasLogin) {
			if(StringUtils.isNotBlank(key3)){
				// 存公众号的cookie
				String wePublicId = request.getParameter("wePublicId");
				if (StringUtil.isNotNull(wePublicId)) {
					Cookie cookie = new Cookie("wePublicId", wePublicId);
					response.addCookie(cookie);
				}
			}
			if (flag) {
				response.sendRedirect(url);
				return null;
			}
			// 登录成功
			if (StringUtil.isNotNull(url)) {
				if (url.startsWith("http")) {
					response.sendRedirect(url);
				} else {
					String content = request.getContextPath();
					if (url.indexOf(content) != -1) {
						url = url.substring(content.length(), url.length());
					}
					response.sendRedirect("../../"+url);
					}
				
			} else {
				response.sendRedirect("../pda/index.jsp");
			}
			return null;
		} else {
			// 登录失败
			logger.error("WechatLoginHelperAction.wechatLogin,对接的系统出错");
			jsonObject.put("flag", "0");
			jsonObject.put("msg", "对接的系统出错");
			out.print(jsonObject.toString());
			out.flush();
			return null;
		}
	}
	*/	
	/**
	 * 跳转至绑定系统
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 已经进行加密后的数据
		String postData = request.getParameter("key");
		if (StringUtils.isNotEmpty(postData)) {
			if (StringUtils.isNotEmpty(SYSLICENSE)) {
				String paraData = AESUtil.decrypt(postData, SYSLICENSE);
				// 记录日志信息
				if (UserOperHelper.allowLogOper("toLogin",
						getServiceImp(request).getModelName())) {
					UserOperHelper.logMessage(paraData);
				}
				String[] postDataArr = paraData.split("#@\\$");
				String openId = "", wepublicCode = "", fansId = "", nickName = "", fdScene = "";

				if (postDataArr != null && postDataArr.length > 0) {
					openId = postDataArr[0];

					if (postDataArr.length >= 2) {
						wepublicCode = postDataArr[1];
					}

					if (postDataArr.length >= 3) {
						fansId = postDataArr[2];
					}

					if (postDataArr.length >= 4) {
						nickName = postDataArr[3];
						nickName = URLDecoder.decode(nickName, "utf-8");
					}
					if (postDataArr.length >= 5) {
						fdScene = postDataArr[4];
					}
				}
				
				if(WeChatConfigUtil.scene==null || "".equals(WeChatConfigUtil.scene)){
					WeChatConfigUtil.scene = fdScene;
				}

				WechatConfigForm wechatConfigForm = (WechatConfigForm) form;
				wechatConfigForm.setFdOpenid(openId);
				wechatConfigForm.setFdPublicCode(wepublicCode);
				wechatConfigForm.setFdFansId(fansId);
				wechatConfigForm.setFdNickname(nickName);
				wechatConfigForm.setFdScene(fdScene);
				
				return getActionForward("login", mapping, wechatConfigForm,
						request, response);
			}else{
				logger.error("WechatLoginHelperAction.toLogin,License值为空");
			}
		}else{
			logger.error("WechatLoginHelperAction.toLogin,请求数据参数postData为NULL");
		}
		return null;
	}

	
	/**
	 * 微信绑定系统-- 微云使用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 *             public ActionForward toBindSystem(ActionMapping mapping,
	 *             ActionForm form, HttpServletRequest request,
	 *             HttpServletResponse response) throws Exception {
	 * 
	 *             KmssMessages messages = new KmssMessages(); WechatConfigForm
	 *             wechatConfigForm = (WechatConfigForm) form; String loginName
	 *             = wechatConfigForm.getFdLoginName(); String passwd =
	 *             wechatConfigForm.getFdPasswd(); SysOrgPerson person =
	 *             getSysOrgCoreService().findByLoginName(loginName);
	 * 
	 *             boolean hasLogin = false; boolean bindFlag = false; if
	 *             (person != null) { // 记录日志信息 if
	 *             (UserOperHelper.allowLogOper("toBindSystem",
	 *             getServiceImp(request).getModelName())) {
	 *             UserOperContentHelper.putFind(person); } String realPasswd =
	 *             person.getFdPassword(); boolean ldapBool = false; boolean
	 *             md5Bool = MD5Util.getMD5String(passwd).equals(realPasswd); if
	 *             (!md5Bool) { // 获取LDAP服务 Object ldapService = null; try {
	 *             Class<?> clazz =
	 *             ClassUtils.forName("com.landray.kmss.third.ldap.LdapService");
	 *             ldapService = clazz.newInstance();
	 * 
	 *             Class[] args1 = new Class[2]; args1[0] = String.class;
	 *             args1[1] = String.class; Method method =
	 *             clazz.getDeclaredMethod("validateUser", args1); ldapBool =
	 *             ((Boolean) method.invoke(ldapService, loginName,
	 *             passwd)).booleanValue(); } catch (Exception e) {
	 *             logger.error("WechatLoginHelperAction.toBindSystem,异常信息:"+e.getMessage());
	 *             } } if (md5Bool || ldapBool) { // 登陆状态 String loginFlag =
	 *             null; if (!loginName.equals(getAutoLoginHelper()
	 *             .getCurrentUserLoginName())) { HttpSession session =
	 *             request.getSession(); loginFlag =
	 *             getAutoLoginHelper().doAutoLogin(loginName, session); } else
	 *             { // 未登录进行登录 if (!getAutoLoginHelper().hasLogin()) {
	 *             HttpSession session = request.getSession(); loginFlag =
	 *             getAutoLoginHelper().doAutoLogin(loginName, session); } else
	 *             { hasLogin = true; } }
	 * 
	 *             if (loginFlag != null || hasLogin) { Map<String, String>
	 *             resultMap = new HashMap<String, String>(); if
	 *             (StringUtils.isNotEmpty(SYSLICENSE)) { String openId =
	 *             wechatConfigForm.getFdOpenid(); String publicCode =
	 *             wechatConfigForm.getFdPublicCode();
	 * 
	 *             String userName = person.getFdName(); String userId =
	 *             person.getFdId(); String deptName = person.getFdParent() ==
	 *             null ? "" : person.getFdParent().getFdName(); long
	 *             currentTime = System.currentTimeMillis(); String fansId =
	 *             wechatConfigForm.getFdFansId(); String nickName =
	 *             wechatConfigForm.getFdNickname(); //
	 *             if(StringUtil.isNotNull(nickName)) // nickName =
	 *             EmojiFilter.filterEmoji(nickName); String fdScene =
	 *             wechatConfigForm.getFdScene();
	 * 
	 *             // 检查是否已经绑定过该系统 List<WechatConfig> resultList =
	 *             getServiceImp(request) .findList("fdEkpid='" + userId + "'",
	 *             null); WechatConfig wechatConfig = null; if (resultList ==
	 *             null || resultList.size() == 0) { wechatConfig = new
	 *             WechatConfig(); wechatConfig.setFdId(wechatConfig.getFdId());
	 * 
	 *             } else { wechatConfig = resultList.get(0); } //
	 *             每次绑定存储一个随机串作为密钥的一部分
	 *             wechatConfig.setFdRandom(IDGenerator.generateID());
	 *             wechatConfig.setFdEkpid(userId);
	 *             wechatConfig.setFdNickname(nickName);
	 *             wechatConfig.setFdScene(fdScene);
	 * 
	 *             WechatMainConfig config = new WechatMainConfig(); String
	 *             wecharUrl = config.getLwechat_wyUrl();
	 *             if(StringUtil.isNotNull(wecharUrl)){ String[] sp =
	 *             wecharUrl.split(";"); wecharUrl = sp[0]; }
	 * 
	 *             String imgUrl = wecharUrl + "/resource/wefanslogo/" +
	 *             publicCode + "/" + fansId + ".jpeg";
	 *             wechatConfig.setFdImage(imgUrl);
	 *             wechatConfig.setFdOpenid(openId);
	 *             wechatConfig.setFdPushProcess(StringUtil
	 *             .isNull(wechatConfig.getFdPushProcess()) ? "1" :
	 *             wechatConfig.getFdPushProcess());
	 *             wechatConfig.setFdPushRead(StringUtil
	 *             .isNull(wechatConfig.getFdPushRead()) ? "0" :
	 *             wechatConfig.getFdPushRead());
	 *             wechatConfig.setFdUrlAccess(StringUtil
	 *             .isNull(wechatConfig.getFdUrlAccess()) ? "0" :
	 *             wechatConfig.getFdUrlAccess());
	 * 
	 *             // ================将相关数据进行加密处理发送到lwe========================
	 *             String curentTimeStr = String.valueOf(currentTime); String
	 *             bindResult = "1"; userName = URLEncoder.encode(userName,
	 *             "utf-8"); loginName = URLEncoder.encode(loginName, "utf-8");
	 *             deptName = URLEncoder.encode(deptName, "utf-8");
	 * 
	 *             String postData = openId + "#@$" + publicCode + "#@$" +
	 *             wechatConfig.getFdRandom() + "#@$" + userName + "#@$" +
	 *             loginName + "#@$" + deptName + "#@$" + curentTimeStr + "#@$"
	 *             + bindResult;
	 * 
	 *             // 加密 String uKey = userId; if (uKey.length() > 32) { uKey =
	 *             uKey.substring(0, 32); } else if (uKey.length() < 32) { int
	 *             add = 32 - uKey.length(); for (int i = 0; i < add; i++) {
	 *             uKey += "0"; } }
	 * 
	 *             String aesK = SYSLICENSE.substring(0, SYSLICENSE .length() /
	 *             2) + uKey.substring(0, uKey.length() / 2); String resultData
	 *             = AESUtil.encrypt(postData, aesK); resultMap.put("resultKid",
	 *             fdScene); resultMap.put("resultUid", userId);
	 *             resultMap.put("resultData", resultData);
	 * 
	 *             JSONObject jsonObject = JSONObject .fromObject(resultMap);
	 * 
	 *             // 数据传送到lwe接口 String url = wecharUrl +
	 *             "/app/systemsso/bind2"; HttpClient httpClient = new
	 *             HttpClient( getConnectionManager()); // 设置超时
	 *             httpClient.setConnectionTimeout(15 * 1000);
	 *             httpClient.setTimeout(15 * 1000);
	 * 
	 *             PostMethod post = new PostMethod(url); RequestEntity entity =
	 *             new StringRequestEntity( jsonObject.toString(), null,
	 *             "utf-8"); post.setRequestEntity(entity); String resString =
	 *             ""; // 可能网络问题， 设置失败后多发1次请求。 for (int i = 0; i < 2; i++) { int
	 *             result = httpClient.executeMethod(post); if (result == 200) {
	 *             // lwe系统返回绑定信息 resString = post.getResponseBodyAsString(); if
	 *             ("1".equals(resString)) { // 成功标志、ekp写入绑定数据 bindFlag = true;
	 *             wechatConfigService.update(wechatConfig); break; } else { //
	 *             记录lwe信息到日志
	 *             logger.error("WechatLoginHelperAction.toBindSystem,微云端执行时发生异常,异常信息:"+resString);
	 *             } } else { resString = post.getResponseBodyAsString();
	 *             logger.error("WechatLoginHelperAction.toBindSystem,向微云回写请求发生异常,执行状态码:"+result+"
	 *             异常信息:"+resString); } } } } } // 密码检验有误 else {
	 *             request.setAttribute("info", "密码输入有误,请返回重新绑定"); return
	 *             getActionForward("bindFail", mapping, wechatConfigForm,
	 *             request, response); } } // 输入的账号有误 else {
	 *             request.setAttribute("info", "账号输入有误,请返回重新绑定"); return
	 *             getActionForward("bindFail", mapping, wechatConfigForm,
	 *             request, response); }
	 * 
	 *             if (bindFlag) { return getActionForward("bindSuccess",
	 *             mapping, wechatConfigForm, request, response); } if
	 *             (wechatConfigForm.getFdCount() == null ||
	 *             wechatConfigForm.getFdCount() < 2) { int c =
	 *             wechatConfigForm.getFdCount() == null ? 0 :
	 *             wechatConfigForm.getFdCount() + 1;
	 *             wechatConfigForm.setFdCount(c); return
	 *             getActionForward("relogin", mapping, wechatConfigForm,
	 *             request, response); } else { return
	 *             getActionForward("bindFail", mapping, wechatConfigForm,
	 *             request, response); } }
	 */
	
	@Override
    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		WechatConfigForm wf = (WechatConfigForm) form;
		
		String openid = request.getParameter("openid");
		String nickname = request.getParameter("nickname");
		String image = request.getParameter("image");
		String id = request.getParameter("uid");
		if(id==null || "".equals(id)){
			// 根据当前用户查找
			KMSSUser user = UserUtil.getKMSSUser();
			if (user.isAnonymous()) {
				messages.addError(new Exception("未登录系统"));
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
				return getActionForward("failure", mapping, wf, request,
						response);
			}
			id = user.getUserId();
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdEkpid=:id");
		hqlInfo.setParameter("id", id);
		WechatConfig wc = (WechatConfig) getServiceImp(request).findFirstOne(hqlInfo);
		if (wc == null) {
			ActionForm newForm = createNewForm(mapping, wf, request, response);
			WechatConfigForm newcForm = (WechatConfigForm) newForm;
			if (newcForm.getFdEkpid() == null
					|| "".equals(newcForm.getFdEkpid())) {
				newcForm.setFdEkpid(id);
			}
			newcForm.setFdOpenid(openid);
			newcForm.setFdNickname(nickname);
			newcForm.setFdImage(image);
			request.setAttribute("nickname", nickname);
			request.setAttribute("image", image);
			if (newcForm != form) {
                request.setAttribute(getFormName(newcForm, request), newForm);
            }
		} else {
			if (openid != null && !"".equals(openid)) {
				wf.setFdOpenid(openid);
				wc.setFdOpenid(openid);
			}
			if (nickname != null && !"".equals(nickname)) {
				wf.setFdNickname(nickname);
				wc.setFdNickname(nickname);
		    }
			if (image != null && !"".equals(image)) {
				wf.setFdImage(image);
				wc.setFdImage(image);
			}
			request.setAttribute("nickname", wc.getFdNickname());
			request.setAttribute("image", wc.getFdImage());
			UserOperHelper.logFind(wc);// 记录日志信息
			IExtendForm rtnForm = getServiceImp(request).convertModelToForm(
					(IExtendForm) wf, wc, new RequestContext(request));

			request.setAttribute(getFormName(rtnForm, request), rtnForm);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, wf, request, response);
		} else {
			return getActionForward("edit", mapping, wf, request, response);
		}
	}

	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		WechatConfigForm wf = (WechatConfigForm) form;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).add(wf, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}


	/**
	 * 获取参数url的数据 与ekp做sso 或不需要登陆的url。待测
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward urlData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {


		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		JSONObject jsonObject = new JSONObject();
		// 获取post数据
		String key = request.getParameter("key");
		String key2 = request.getParameter("key2");
		String key3 = request.getParameter("key3");

		if (StringUtil.isNull(key) || StringUtil.isNull(key2)) {
			logger.error("WechatLoginHelperAction.urlData,发送的参数数据为空");
			jsonObject.put("flag", "0");
			jsonObject.put("msg", "发送的参数数据为空");
			// 记录日志信息
			if (UserOperHelper.allowLogOper("urlData",
					getServiceImp(request).getModelName())) {
				UserOperHelper.logMessage(jsonObject.toString());
			}
			out.print(jsonObject.toString());
			out.flush();
			return null;
		}

		// 根据key2 ekpid 查找随机码
		HQLInfo hqlinfo=new HQLInfo();
		hqlinfo.setWhereBlock("fdEkpid = :key2");
		hqlinfo.setParameter("key2", key2);
		WechatConfig wc = (WechatConfig) getServiceImp(request).findFirstOne(hqlinfo);
		if (wc == null) {
			logger.error("WechatLoginHelperAction.urlData,查找fdEkpid="+ URLEncoder.encode(key2,"utf-8")+"的WechatConfig配置信息不存在");
			return null;
		}
		String randomCode = wc.getFdQyRandom();
		if(StringUtils.isBlank(key3)){
			randomCode = wc.getFdRandom();
		}
		if (StringUtil.isNull(SYSLICENSE)) {
			logger.error("WechatLoginHelperAction.urlData,不支持老版本license");
			jsonObject.put("flag", "0");
			jsonObject.put("msg", "不支持老版本license");
			// 记录日志信息
			if (UserOperHelper.allowLogOper("urlData",
					getServiceImp(request).getModelName())) {
				UserOperHelper.logMessage(jsonObject.toString());
			}
			out.print(jsonObject.toString());
			out.flush();
			return null;
		}
		String aesKey = SYSLICENSE.substring(0, SYSLICENSE.length() / 2)
				+ randomCode.substring(0, randomCode.length() / 2);
		key = AESUtil.decrypt(key, aesKey);
		String[] values = key.split("#@\\$");
		String user = values[0];
		String url = values[1];
		
		// 登陆状态
		String loginflag = null;
		boolean hasLogin = false;
		if (!user.equals(getAutoLoginHelper().getCurrentUserLoginName())) {
			HttpSession session = request.getSession();
			loginflag = getAutoLoginHelper().doAutoLogin(user, session);
		} else {
			if (!getAutoLoginHelper().hasLogin()) {
				HttpSession session = request.getSession();
				loginflag = getAutoLoginHelper().doAutoLogin(user, session);
			} else {
				hasLogin = true;
			}
		}

		if (loginflag != null || hasLogin) {
			Cookie[] cookies = request.getCookies();
			if(cookies !=null && cookies.length>0){
				String cook=cookies[0].getValue() + "=" + cookies[0].getValue();
				for (int i = 1; i < cookies.length; i++) {
                     cook += "; " + cookies[i].getName() + "=" + cookies[i].getValue();
                }
				cookies[0].setValue(cook);
			}
			String res = HttpClientUtil.httpPost(url,cookies[0]);
			jsonObject.put("data", res);
			// 记录日志信息
			if (UserOperHelper.allowLogOper("urlData",
					getServiceImp(request).getModelName())) {
				UserOperHelper.logMessage(jsonObject.toString());
			}
			out.print(jsonObject.toString());
			out.flush();
			return null;
		}
		return null;
	}
	
	
	
	/**
	 * kms微信入口
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward kmsUrlData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		JSONObject jsonObject = new JSONObject();
		// 获取post数据
		String key = request.getParameter("key");
		String key2 = request.getParameter("key2");
		String key3 = request.getParameter("key3");

		if (StringUtil.isNull(key) || StringUtil.isNull(key2)) {
			logger.error("WechatLoginHelperAction.kmsUrlData,发送的参数数据为空");
			jsonObject.put("flag", "0");
			jsonObject.put("msg", "发送的参数数据为空");
			// 记录日志信息
			if (UserOperHelper.allowLogOper("kmsUrlData",
					getServiceImp(request).getModelName())) {
				UserOperHelper.logMessage(jsonObject.toString());
			}
			out.print(jsonObject.toString());
			out.flush();
			return null;
		}

		// 根据key2 ekpid 查找随机码
		HQLInfo hqlinfo=new HQLInfo();
		hqlinfo.setWhereBlock("fdEkpid = :key2");
		hqlinfo.setParameter("key2", key2);
		WechatConfig wc = (WechatConfig) getServiceImp(request).findFirstOne(hqlinfo);
		if (wc == null) {
			logger.error("WechatLoginHelperAction.kmsUrlData,查找fdEkpid="+URLEncoder.encode(key2,"utf-8")+"的WechatConfig配置信息不存在");
			return null;
		}
		String randomCode = wc.getFdQyRandom();
		if(StringUtils.isBlank(key3)){
			randomCode = wc.getFdRandom();
		}
		
		if (StringUtil.isNull(SYSLICENSE)) {
			logger.error("WechatLoginHelperAction.kmsUrlData,不支持老版本license");
			jsonObject.put("flag", "0");
			jsonObject.put("msg", "不支持老版本license");
			// 记录日志信息
			if (UserOperHelper.allowLogOper("kmsUrlData",
					getServiceImp(request).getModelName())) {
				UserOperHelper.logMessage(jsonObject.toString());
			}
			out.print(jsonObject.toString());
			out.flush();
			return null;
		}
		String aesKey = SYSLICENSE.substring(0, SYSLICENSE.length() / 2)
				+ randomCode.substring(0, randomCode.length() / 2);
		key = AESUtil.decrypt(key, aesKey);

		String[] values = key.split("#@\\$");
		String user = values[0];
		String hostUrl = values[1];
		
		// 登陆状态
		String loginflag = null;
		boolean hasLogin = false;
		if (!user.equals(getAutoLoginHelper().getCurrentUserLoginName())) {
			HttpSession session = request.getSession();
			loginflag = getAutoLoginHelper().doAutoLogin(user, session);
		} else {
			if (!getAutoLoginHelper().hasLogin()) {
				HttpSession session = request.getSession();
				loginflag = getAutoLoginHelper().doAutoLogin(user, session);
			} else {
				hasLogin = true;
			}
		}
	
		String keyName=values[2];
		Map<String,String> map=getRequestMenuUrl(keyName);
		if(map!=null && map.size()>0){
			String type = map.get("type");
			String partUrl=map.get("url");
			String postUrl= hostUrl+partUrl;
			String JSESSIONID=request.getSession().getId();
			if(StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(partUrl)){
				if("click".equals(type)){
					String jessionStr="JSESSIONID="+JSESSIONID;
					String res = HttpClientUtil.httpPost2(postUrl,jessionStr);
					logger.info("WechatLoginHelperAction.kmsUrlData,请求的类型为click,请求的路径postUrl="+postUrl+",请求结果:"+res);
					res =dataCommonTrans(res);
					jsonObject.put("data", res);
					// 记录日志信息
					if (UserOperHelper.allowLogOper("kmsUrlData",
							getServiceImp(request).getModelName())) {
						UserOperHelper.logMessage(jsonObject.toString());
					}
					out.print(jsonObject.toString());
					out.flush();
				}else if("view".equals(type)){
					response.sendRedirect(postUrl);
				}
			}else{
				logger.error("WechatLoginHelperAction.kmsUrlData,请求的partUrl路径为NULL或者请求类型type为NULL");
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private String  dataCommonTrans(String result){
		List<Map<String,String>>resultList = new ArrayList<Map<String,String>>();
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isNotEmpty(result)){
			JSONObject jo = JSONObject.fromObject(result);
			net.sf.json.JSONArray dataArray = (net.sf.json.JSONArray)jo.get("datas");
			if(dataArray!=null && dataArray.size()>0){
				Iterator iterator = dataArray.iterator(); 
				while (iterator.hasNext()) {
					net.sf.json.JSONArray tempObj = (net.sf.json.JSONArray) iterator.next();
					Iterator iterator2 = tempObj.iterator();
					Map<String,String> dataMap = new HashMap<String,String>();
					while ( iterator2.hasNext()) {
						JSONObject dataProperty =(JSONObject) iterator2.next();
						String propertyName =(String) dataProperty.get("col");
						String propertyValue = (String) dataProperty.get("value");
						if("href".equals(propertyName)){
							dataMap.put("href", propertyValue);
						}
						
						if("docSubject".equals(propertyName)){
							dataMap.put("subject", propertyValue);
						}else if("label".equals(propertyName)){
							dataMap.put("subject", propertyValue);
						}
					}
					resultList.add(dataMap);
				}
				net.sf.json.JSONArray resultArray = net.sf.json.JSONArray.fromObject(resultList);
				return resultArray.toString();
			}
		}
		return null;
	}
	
	
	/**
	 * 获取请求的部分url地址
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private Map<String ,String>  getRequestMenuUrl(String key) throws Exception{
		
		    Map<String,String> map = new HashMap<String,String>();
			/*IPdaDataExtendConfigService pdaDataExtendConfigService =(IPdaDataExtendConfigService) SpringBeanUtil.getBean("pdaDataExtendConfigService");
		    String pdaConfigModelName=  pdaDataExtendConfigService.getModelName();
		    String pdaConfigModelTableName = ModelUtil.getModelTableName(pdaConfigModelName);
			HQLInfo pdahqlInfo = new HQLInfo();
			
			String pdawhereBlock = pdahqlInfo.getWhereBlock();
			if(pdawhereBlock==null){
				pdawhereBlock="";
			}
			pdawhereBlock += "(" + pdaConfigModelTableName+ ".fdKey =:key)";
			pdahqlInfo.setParameter("key", key);
			pdahqlInfo.setWhereBlock(pdawhereBlock);
			List<PdaDataExtendConfig> pdaDataExtendConfigList =pdaDataExtendConfigService.findList(pdahqlInfo);
			
			if(pdaDataExtendConfigList!=null && pdaDataExtendConfigList.size()>0){
				if(pdaDataExtendConfigList.size()==1){
					PdaDataExtendConfig pdaDataExtendConfig =pdaDataExtendConfigList.get(0);
					String url =pdaDataExtendConfig.getFdValue();
					String type =pdaDataExtendConfig.getFdType();
					map.put("url", url);
					map.put("type", type);
			   }
		   }*/
			return map;
	}
	


	/**
	 * 推送下载附件主键
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward wechatDownload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		PostMethod post = null;
		try {
			String params = request.getParameter("params");
			JSONObject jsonObj = JSONObject.fromObject(params);
			jsonObj.accumulate("license", SYSLICENSE);
			// 登录者ID
			jsonObj.accumulate("personId", UserUtil.getUser().getFdId());
			// 传公众号ID
			HttpSession session = request.getSession();
			String wePublicId = null;
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if ("wePublicId".equals(cookie.getName())) {
					wePublicId = cookie.getValue();
				}
			}
			jsonObj.accumulate("wePublicId", wePublicId);
			HttpClient httpClient = new HttpClient(getConnectionManager());
			// 设置超时
			//httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
			//httpClient.getHttpConnectionManager().getParams().setSoTimeout(15000);
			WechatMainConfig config = new WechatMainConfig();
			String baseUrl =config.getLwechat_qyDownloadUrl();// properties.getProperty("lwechat.qydownload");
			post = new PostMethod(baseUrl +"/app/attachmentresource/reqdownloadresource");
			RequestEntity entity = new StringRequestEntity(jsonObj.toString(), null, "utf-8");
			post.setRequestEntity(entity);
			int result = httpClient.executeMethod(post);
			if (result == 200) {
				out.write("1");
			} else {
				String resString = post.getResponseBodyAsString();
				out.write(resString);
			}
			// 记录日志信息
			if (UserOperHelper.allowLogOper("wechatDownload",
					getServiceImp(request).getModelName())) {
				UserOperHelper.logMessage(out.toString());
			}
			out.flush();
			return null;
		} catch (Exception e) {
			logger.error("WechatLoginHelperAction.wechatDownload,微信企业号附件下载发生异常,异常信息:"+e.getMessage());
			out.write(e.getMessage());
		} finally {
			out.close();
			if (post != null) {
				post.releaseConnection();
			}
		}
		
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("view");
		}
	}
	
}
