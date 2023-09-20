package com.landray.kmss.third.weixin.work.action;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authentication.interfaces.EKPValidateService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.api.aes.WXBizMsgCrypt;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinWorkForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinAuthLog;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWork;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinAuthLogService;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkService;
import com.landray.kmss.third.weixin.work.spi.model.WxworkMenuModel;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkMenuService;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 应用配置 Action
 * 
 * @author
 * @version 1.0 2017-05-02
 */
public class ThirdWeixinWorkAction extends ExtendAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWeixinWorkAction.class);
	
	protected IThirdWeixinWorkService thirdWeixinWorkService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdWeixinWorkService == null) {
			thirdWeixinWorkService = (IThirdWeixinWorkService) getBean(
					"thirdWeixinWorkService");
		}
		return thirdWeixinWorkService;
	}

	private IWxworkMenuService wxworkMenuService;

	protected IWxworkMenuService getwxworkMenuServiceImp(
			HttpServletRequest request) {
		if (wxworkMenuService == null) {
			wxworkMenuService = (IWxworkMenuService) getBean(
					"wxworkMenuService");
		}
		return wxworkMenuService;
	}

	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		ThirdWeixinWork weixinWork = null;
		String id = request.getParameter("fdId");
		String url = "/resource/third/wxwork/cpEndpoint.do?method=service&agentId=";
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id, null,
					true);
			if (model != null) {
				UserOperHelper.logFind(model);
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				ThirdWeixinWorkForm tform = (ThirdWeixinWorkForm) rtnForm;
				weixinWork = (ThirdWeixinWork) model;
				if (StringUtil.isNotNull(tform.getFdAgentid())) {
					String wxdomain = WeixinWorkConfig.newInstance()
							.getWxDomain();
					if (!(wxdomain + url + tform.getFdAgentid().trim())
							.equals(tform.getFdCallbackUrl())) {
						tform.setFdCallbackUrl(
								wxdomain + url + tform.getFdAgentid().trim());
						weixinWork.setFdCallbackUrl(tform.getFdCallbackUrl());
						weixinWork.setFdSystemUrl(wxdomain);
						getServiceImp(request).update(weixinWork);
					}
				}
				WxworkMenuModel module = (WxworkMenuModel) getwxworkMenuServiceImp(request).findFirstOne(
						"fdAgentId='" + weixinWork.getFdAgentid() + "'", null);
				if (module != null) {
					request.setAttribute("fdMenuId", module.getFdId());
					request.setAttribute("fdMenuPuslished", module.getFdPublished());
				}
			}
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

	private WxworkApiService wxworkApiService = null;

	public ActionForward appList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-appList", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		JSONArray ja = new JSONArray();
		JSONObject jn = null;
		JSONObject jo = null;
		String url = WxworkUtils.getWxworkApiUrl() +"/agent/get?";
		String agentSimple = null;
		try {
			wxworkApiService = WxworkUtils.getWxworkApiService();

			List<ThirdWeixinWork> wxlist = getServiceImp(request).findList(null,
					"docCreateTime desc");
			UserOperHelper.logFindAll(wxlist,
					getServiceImp(request).getModelName());
			for (ThirdWeixinWork wk : wxlist) {
				agentSimple = wxworkApiService.agentGet(wk.getFdAgentid());

				if (StringUtil.isNotNull(agentSimple)) {
					jo = JSONObject.fromObject(agentSimple);

					if (301002 == jo.getInt("errcode")) {
						logger.info(
								"AgentId=" + wk.getFdAgentid() + "的配置异常，直接跳过");
						continue;
					} else {
						if (jo.containsKey("errcode")
								&& 0 == jo.getInt("errcode")
								&& jo.containsKey("close")
								&& 0 == jo.getInt("close")) {
							jn = new JSONObject();
							jn.put("name", wk.getFdName());
							jn.put("appId", wk.getFdAgentid());
							ja.add(jn);
						}
					}
				}
			}
			json.put("data", ja);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-appList", false, getClass());
		return null;
	}

	private String getBody(HttpServletRequest request) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder("");
		try {
			br = request.getReader();
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			br.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return sb.toString();
	}

	private EKPValidateService ekpValidateService;

	public EKPValidateService getEkpValidateService() {
		if (ekpValidateService == null) {
            ekpValidateService = (EKPValidateService) SpringBeanUtil
                    .getBean("ekpValidateService");
        }
		return ekpValidateService;
	}

	private IWxworkOmsRelationService wxworkOmsRelationService;

	public IWxworkOmsRelationService getWxworkOmsRelationService() {
		if (wxworkOmsRelationService == null) {
            wxworkOmsRelationService = (IWxworkOmsRelationService) SpringBeanUtil
                    .getBean("wxworkOmsRelationService");
        }
		return wxworkOmsRelationService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
            sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
                    .getBean("sysOrgPersonService");
        }
		return sysOrgPersonService;
	}

	private IThirdWeixinAuthLogService thirdWeixinAuthLogService;

	public IThirdWeixinAuthLogService getThirdWeixinAuthLogService() {
		if (thirdWeixinAuthLogService == null) {
            thirdWeixinAuthLogService = (IThirdWeixinAuthLogService) SpringBeanUtil
                    .getBean("thirdWeixinAuthLogService");
        }
		return thirdWeixinAuthLogService;
	}

	private String responseOut(WXBizMsgCrypt crypt,
			HttpServletResponse response,
			String data) throws Exception {
		String return_timestamp = System.currentTimeMillis() + "";
		String result = crypt.EncryptMsg(data, return_timestamp,
				"123456");
		logger.debug("result:" + result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);
		return result;
	}

	public ActionForward authCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-authCheck", true, getClass());
		JSONObject result = new JSONObject();
		WXBizMsgCrypt crypt = null;
		ThirdWeixinAuthLog log = new ThirdWeixinAuthLog();
		log.setDocCreateTime(new Date());
		log.setFdUrl(request.getQueryString());
		log.setFdLogType("1");
		try {
			WeixinWorkConfig config = new WeixinWorkConfig();
			if (!"true".equals(config.getWxEnabled())
					|| !"true".equals(config.getWxAuthCheckEnabled())) {
				throw new Exception("未启用企业微信内部登录功能");
			}
			String msg_signature = request.getParameter("msg_signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String encryptString = getBody(request);
			log.setFdReqDataOri(encryptString);

			String EncodingAESKey = config.getWxAuchCheckAESKey();
			String corpId = config.getWxCorpid();
			crypt = new WXBizMsgCrypt(
					config.getWxAuchCheckToken(),
					EncodingAESKey,
					corpId);

			String msg = crypt.DecryptMsgNew(msg_signature, timestamp, nonce,
					encryptString);

			JSONObject o = JSONObject.fromObject(msg);
			String username = o.getString("username");
			String userid = o.getString("userid");
			log.setFdUserId(userid);
			String password = o.getString("password");
			o.put("password", "***");
			log.setFdReqDataDecyed(o.toString());

			boolean validateResult = getEkpValidateService().validate(username,
					password);
			if (!validateResult) {
				result.put("status", 1000);
				result.put("errmsg", "账号密码错误");
				log.setFdResult(2);
			} else {
				result.put("status", 0);
				log.setFdResult(1);
			}
			log.setFdResDataOri(result.toString());
			String result_encryed = responseOut(crypt, response,
					result.toString());
			log.setFdResDataEncryed(result_encryed);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("status", 1000);
			result.put("errmsg", e.getMessage());
			log.setFdResDataOri(result.toString());
			log.setFdErrMsg(e.getMessage());
			log.setFdResult(2);
			if (crypt != null) {
				log.setFdResDataOri(result.toString());
				String result_encryed = responseOut(crypt, response,
						result.toString());
				log.setFdResDataEncryed(result_encryed);
			} else {
				throw e;
			}
		} finally {
			log.setFdExpireTime(System.currentTimeMillis()
					- log.getDocCreateTime().getTime());
			getThirdWeixinAuthLogService().add(log);
		}

		return null;
	}

	public ActionForward updatePass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updatePass", true, getClass());
		JSONObject result = new JSONObject();
		WXBizMsgCrypt crypt = null;
		ThirdWeixinAuthLog log = new ThirdWeixinAuthLog();
		log.setDocCreateTime(new Date());
		log.setFdUrl(request.getQueryString());
		log.setFdLogType("2");
		try {
			WeixinWorkConfig config = new WeixinWorkConfig();
			if (!"true".equals(config.getWxEnabled())
					|| !"true".equals(config.getWxAuthCheckEnabled())) {
				throw new Exception("未启用企业微信内部登录功能");
			}
			String msg_signature = request.getParameter("msg_signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String encryptString = getBody(request);
			log.setFdReqDataOri(encryptString);

			String EncodingAESKey = config.getWxUpdatePassAESKey();
			String corpId = config.getWxCorpid();
			crypt = new WXBizMsgCrypt(
					config.getWxUpdatePassToken(),
					EncodingAESKey,
					corpId);

			String msg = crypt.DecryptMsgNew(msg_signature, timestamp, nonce,
					encryptString);

			JSONObject o = JSONObject.fromObject(msg);
			String userid = o.getString("userid");
			log.setFdUserId(userid);
			String old_pwd = o.getString("old_pwd");
			String new_pwd = o.getString("new_pwd");
			o.put("old_pwd", "***");
			o.put("new_pwd", "***");
			log.setFdReqDataDecyed(o.toString());
			WxworkOmsRelationModel relation = getWxworkOmsRelationService()
					.findByUserId(userid);
			if (relation == null) {
				throw new Exception("没有找到该用户的映射关系，" + userid);
			}

			SysOrgPerson person = (SysOrgPerson) getSysOrgPersonService()
					.findByPrimaryKey(relation.getFdEkpId());
			if (person == null) {
				throw new Exception("EKP中没有找到该用户，" + userid);
			}

			boolean validateResult = getEkpValidateService().validate(
					person.getFdLoginName(),
					old_pwd);
			if (!validateResult) {
				result.put("status", 1000);
				result.put("errmsg", "账号密码错误");
				log.setFdResult(2);
			} else {
				getSysOrgPersonService().saveNewPassword(person.getFdId(),
						new_pwd, new RequestContext(request));
				result.put("status", 0);
				log.setFdResult(1);
			}
			String result_entryed = responseOut(crypt, response,
					result.toString());
			log.setFdResDataEncryed(result_entryed);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			log.setFdErrMsg(e.getMessage());
			log.setFdResult(2);
			result.put("status", 1000);
			result.put("errmsg", e.getMessage());
			if (crypt != null) {
				log.setFdResDataOri(result.toString());
				String result_encryed = responseOut(crypt, response,
						result.toString());
				log.setFdResDataEncryed(result_encryed);
			} else {
				throw e;
			}
		} finally {
			log.setFdExpireTime(System.currentTimeMillis()
					- log.getDocCreateTime().getTime());
			getThirdWeixinAuthLogService().add(log);
		}

		return null;
	}



}
