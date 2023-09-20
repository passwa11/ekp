package com.landray.kmss.third.im.kk.actions;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.security.core.AuthenticationException;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.authentication.token.Token;
import com.landray.kmss.sys.authentication.token.TokenGenerator;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.constant.KkQrcodeConstants;
import com.landray.kmss.third.im.kk.model.QRCode;
import com.landray.kmss.third.im.kk.model.ThirdImLogin;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.third.im.kk.service.IKkUserService;
import com.landray.kmss.third.im.kk.service.IThirdImLoginService;
import com.landray.kmss.third.im.kk.util.HttpRequest;
import com.landray.kmss.third.im.kk.util.QRCodeUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SignUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.Globals;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <P>
 * kk二维码扫描登录EKP服务
 * </P>
 * 
 * @author 孙佳
 */
public class KkUserAction extends ExtendAction {

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(KkUserAction.class);

	private static final Integer EXPIRED_TIME = 1;

	protected IKkUserService kkUserService;

	protected IKkUserService getKkUserService() {
		if (kkUserService == null) {
			kkUserService = (IKkUserService) getBean("kkUserService");
		}
		return kkUserService;
	}

	protected IThirdImLoginService thirdImLoginService;

	protected IThirdImLoginService getThirdKkSmloginService() {
		if (thirdImLoginService == null) {
			thirdImLoginService = (IThirdImLoginService) getBean("thirdImLoginService");
		}
		return thirdImLoginService;
	}

	protected IKkImConfigService kkImConfigService;

	@Override
    protected IKkImConfigService getServiceImp(HttpServletRequest request) {
		if (kkImConfigService == null) {
			kkImConfigService = (IKkImConfigService) getBean("kkImConfigService");
		}
		return kkImConfigService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	protected ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	protected ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public ActionForward getUserId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				SysOrgPerson person = (SysOrgPerson) getSysOrgPersonService().findByPrimaryKey(fdId);
				if (person != null) {
					result.accumulate("userId", person.getFdLoginName());
				}
			}
			JSONObject json = JSONObject.fromObject(result);
			if (UserOperHelper.allowLogOper("getUserId", null)) {
				UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin"));
				UserOperHelper.logMessage(json.toString());
				UserOperHelper.setOperSuccess(true);
			}
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	/**
	 * <p>
	 * 3.1 二维码显示界面（用于浏览器调用）
	 * </p>
	 * 
	 * @return
	 * @author 孙佳
	 */
	public void getSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();

		try {
			// 签名
			json = getKkUserService().getSign();
			if (UserOperHelper.allowLogOper("getSign", null)) {
				UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin"));
				UserOperHelper.logMessage(json.toString());
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

	public void getNewSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			// 固定前缀
			String prefix = "32";
			// ekp服务器地址
			// String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			String basePath = ResourceUtil
					.getKmssConfigString("kmss.urlPrefix");// UUID
			String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
			// sign
			String sign = SignUtil.getHMAC(uuid, "key");
			String base64 = QRCodeUtil.toBase64WithLogo(prefix + "," + basePath + "," + uuid + "," + sign, 140, 140);
			QRCode qrcode = new QRCode();
			qrcode.setUuid(uuid);
			qrcode.setBase64Qrcode(base64);
			// 设置二维码失效时间 1分钟
			qrcode.setExpireTime(System.currentTimeMillis() + EXPIRED_TIME * 60 * 1000);
			logger.info(uuid);
			json.put("qrcode", qrcode);
			json.put("result", 0);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json.toString());
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			messages.addError(e);
			e.printStackTrace();
			json.put("result", 102);
			json.put("errorMsg", "生成二维码失败");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json.toString());
			return;
		}

	}

	public void longpolling(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 这里应该是 不断检测uuid是否有对应的用户信息，如果有则转到登录模块（携带登录信息）。
		// 轮询的时候 应该是根据这个uuid去查是否有对应的token
		// 如果有这个token 应该用某种算法解析出来 ？？？得到用户信息
		// 保存用户信息 登录系统
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		String uuid = request.getParameter("uuid");
		int code = 0;
		try {
			Thread.sleep(2000);
			// 0处理成功； // 340无效的二维码； // 341用户账号不存在； // 102其它错误
			// 判断是否扫码完成 根据uuid去查是否有对应的token
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("thirdImLogin.fdUuid = :fdUuid");
			hqlInfo.setParameter("fdUuid", uuid);
			Page page = getThirdKkSmloginService().findPage(hqlInfo);
			List<ThirdImLogin> thirdImLoginList = page.getList();
			// 记录操作日志
			UserOperHelper.logFindAll(thirdImLoginList, getServiceImp(request).getModelName());
			if (thirdImLoginList.size() > 1) {
				code = 102;
				json.put("code", code);
				json.put("errorMsg", "后台返回数据错误");
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(json.toString());
				return;
			} else {
				String token = null;
				for (int i = 0; i < thirdImLoginList.size(); i++) {
					ThirdImLogin login = thirdImLoginList.get(i);
					token = login.getFdToken();
				}
				if (null != token) {
					code = 0;// 成功
					json.put("code", code);
					json.put("token", token);
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write(json.toString());
					return;
				} else {
					code = 408;
					json.put("code", code);
					json.put("errorMsg", "未扫码");
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write(json.toString());
					return;
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			messages.addError(e);
			e.printStackTrace();
			json.put("result", 102);
			json.put("errorMsg", "扫码失败");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json.toString());
			return;
		}
	}

	/**
	 * ekp扫码登录通知接口 暴露给kk
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void notifyScanLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String token = request.getParameter("token");
			if (StringUtil.isNull(token)) {
				json.put("result", 102);
				json.put("errorMsg", "token不能为空");
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(json.toString());
				return;
			}
			String UUID = request.getParameter("UUID");
			if (StringUtil.isNull(UUID)) {
				json.put("result", 102);
				json.put("errorMsg", "UUID不能为空");
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(json.toString());
				return;
			}
			// 解析用户的ekpid 没有抛出异常
			// 解析token
			if (!TokenGenerator.isInitialized()) {
				String path = TokenGenerator.class.getResource("LRToken").getPath();
				if(null==path){
					json.put("result", 102);
					json.put("errorMsg", "加载密钥配置文件失败");
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write(json.toString());
					return;
				}
				TokenGenerator.loadFromKeyFile(path);
			}
			TokenGenerator generator = TokenGenerator.getInstance();
			Token t = generator.generateTokenByTokenString(token);
			if (t == null) {
				json.put("result", 102);
				json.put("errorMsg", "解密Token信息发生错误");
				logger.error(token);
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(json.toString());
				return;
			}
			SysOrgPerson person = getSysOrgCoreService().findByLoginName(t.getUsername());
			if(null==person){
				json.put("result", 102);
				json.put("errorMsg", "EKP组织架构中不存在此人");
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(json.toString());
				return;
			}else{
				// 保存
				ThirdImLogin thirdImLogin = new ThirdImLogin();
				thirdImLogin.setFdToken(token);
				thirdImLogin.setFdUuid(UUID);
				thirdImLogin.setFdLoginName(t.getUsername());
				thirdImLogin.setDocCreateTime(new Date());
				getThirdKkSmloginService().add(thirdImLogin);
				json.put("result", 0);
				json.put("errorMsg", "处理成功");
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(json.toString());
				return;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			messages.addError(e);
			e.printStackTrace();
			json.put("result", 102);
			json.put("errorMsg", "其他错误"+e);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json.toString());
			return;
		}
	}

	public ActionForward ekpLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			String token = request.getParameter("token");
			if (StringUtil.isNull(token)) {
				throw new Exception("token不能为空");
			}
			String userName = "";
			if (TokenGenerator.isInitialized()) {
				TokenGenerator generator = TokenGenerator.getInstance();
				Token t = generator.generateTokenByTokenString(token);
				userName = t.getUsername();
			}
			if (StringUtil.isNotNull(userName)) {
				// 自动登录
				kkAutoLogin(request, userName);
			} else {
				throw new Exception("用户名为空");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			messages.addError(e);
		}

		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return getActionForward("loginSuccess", mapping, form, request, response);
		}
	}

	/**
	 * <p>
	 * 5.1 EKP登录服务（用于KK页面跳转EKP使用）
	 * </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author 孙佳
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			String token = request.getParameter("token");
			if (StringUtil.isNull(token)) {
				throw new Exception("token不能为空");
			}
			// 调用用户信息查询接口
			String result = findUserBytoken(request, token);
			if (StringUtil.isNotNull(result)) {
				JSONObject json = JSONObject.fromObject(result);
				if ("0".equals(json.get("result").toString())) {
					// 调用ekp自动登录服务
					String userName = json.get("user").toString();
					if (StringUtil.isNotNull(userName)) {
						String loginResult = kkAutoLogin(request, userName);
						if (StringUtil.isNull(loginResult)) {
							AuthenticationException authException = new AuthenticationException(userName + "在EKP中不存在") {
								private static final long serialVersionUID = -5676023892741195699L;
							};
							request.getSession().setAttribute(Globals.SPRING_SECURITY_LAST_EXCEPTION_KEY,
									authException);
						}
					} else {
						throw new Exception("kk返回用户名为空");
					}
				} else {
					throw new Exception("kk返回token无效");
				}
				if (UserOperHelper.allowLogOper("login", null)) {
					UserOperHelper.setModelNameAndModelDesc(null,
							ResourceUtil.getString("sys-admin:home.nav.sysAdmin"));
					UserOperHelper.logMessage(json.toString());
					UserOperHelper.setOperSuccess(true);
				}
			} else {
				throw new Exception("kk用户信息查询接口无返回结果");
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return getActionForward("loginSuccess", mapping, form, request, response);
		}
	}

	/**
	 * 3.5 用户信息查询接口（用于EKP服务器调用）
	 * 
	 * @throws Exception
	 */
	private String findUserBytoken(HttpServletRequest request, String token) throws Exception {
		String innerDomain = getServiceImp(request).getValuebyKey(KeyConstants.KK_INNER_DOMAIN);
		String url = innerDomain + KkQrcodeConstants.KK_QRCODE_QUERYBIND;
		JSONObject param = new JSONObject();
		param.put("token", token);
		String result = HttpRequest.sendPost(url, param.toString());
		return result;
	}

	/**
	 * <p>
	 * 调用ekp自动登录
	 * </p>
	 * 
	 * @author 孙佳
	 */
	private String kkAutoLogin(HttpServletRequest request, String userName) {
		HttpSession session = request.getSession();
		AutoLoginHelper autoLoginHelper = (AutoLoginHelper) SpringBeanUtil.getBean("autoLoginHelper");
		String result = autoLoginHelper.doAutoLogin(userName, session);
		return result;
	}

	/**
	 * <p>
	 * 6.1 获取用户在线状态
	 * </p>
	 * 
	 * @return
	 * @author 孙佳
	 */
	public void getUserPresence(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject resultJson = new JSONObject();
		try {
			String id = request.getParameter("fdId");
			response.setContentType("text/json;charset=utf-8");
			if (getServiceImp(request).isEnableKKConfig() && StringUtil.isNotNull(id)) {
				SysOrgPerson person = (SysOrgPerson) getSysOrgPersonService().findByPrimaryKey(id);
				if (person != null) {
					String innerDomain = getServiceImp(request).getValuebyKey(KeyConstants.KK_INNER_DOMAIN);
					String url = innerDomain + KkQrcodeConstants.KK_USER_STATE;
					JSONObject param = new JSONObject();
					param.put("loginName", new String[] { person.getFdLoginName() });
					String result = HttpRequest.sendPost(url, param.toString());
					JSONObject resJson = JSONObject.fromObject(result);
					if ("0".equals(resJson.get("result").toString())) {
						JSONArray stateContent = JSONArray.fromObject(resJson.get("stateContent"));
						Iterator<Object> it = stateContent.iterator();
						while (it.hasNext()) {
							JSONObject ob = (JSONObject) it.next();
							ob.get("loginName");
							String pcState = ob.get("pc_state").toString(); // pc状态
																			// 1:在线
																			// 0:离线
							String mobileState = ob.get("mobile_state").toString(); // 移动状态
																					// 1:在线
																					// 0:离线

							resultJson.put("pcState", pcState);
							resultJson.put("mobileState", mobileState);
						}
					} else {
						logger.error("获取用户在线状态接口:错误码:" + resJson.get("result") + "错误信息:" + resJson.get("errorMsg"));
					}
				}
			} else {
				// 未开启KK集成或者id为空，则不显示“在线”字样
				resultJson.put("pcState", "2");
			}
			if (UserOperHelper.allowLogOper("getUserPresence", null)) {
				UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin"));
				UserOperHelper.logMessage(resultJson.toString());
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(resultJson.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
	}

	/**
	 * <p>
	 * 获取用户登录名
	 * </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author 孙佳
	 */
	public void getLoginNameByUserId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject resultJson = new JSONObject();
		try {
			String id = request.getParameter("fdId");
			SysOrgPerson person = null;
			if (StringUtil.isNotNull(id)) {
				person = (SysOrgPerson) getSysOrgPersonService().findByPrimaryKey(id);
				resultJson.put("loginName", person.getFdLoginName());
			}
			if (UserOperHelper.allowLogOper("getLoginNameByUserId", null)) {
				UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin"));
				UserOperHelper.logMessage(resultJson.toString());
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(resultJson.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
	}

}
