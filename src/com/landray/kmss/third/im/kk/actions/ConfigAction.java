package com.landray.kmss.third.im.kk.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.action.SysConfigAdminAction;
import com.landray.kmss.sys.config.form.SysConfigAdminForm;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.im.kk.KkConfig;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.third.im.kk.util.KKConfigUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class ConfigAction extends SysConfigAdminAction{
	
	public IKkImConfigService getKkImConfigService() {
		return (IKkImConfigService) SpringBeanUtil.getBean("kkImConfigService");
	}
	
	@Override
    public ActionForward config(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			SysConfigAdminForm configForm = new SysConfigAdminForm();
			Map<String,String> map = new HashMap<String,String>();
			map.putAll(ResourceUtil.getKmssConfig("config.integrate.kk"));
			map.putAll(ResourceUtil.getKmssConfig("third.im.kk"));
			map.putAll(ResourceUtil.getKmssConfig("kmss.ims.notify.kk"));
			map.putAll(ResourceUtil.getKmssConfig("kmss.ims.kk"));
			map.putAll(ResourceUtil.getKmssConfig("kmss.ims.notify.todo.kk"));
			map.putAll(ResourceUtil.getKmssConfig("kmss.ims.notify.toread.kk"));
			// 记录日志
			if (UserOperHelper.allowLogOper("config", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil.getString("sys-admin:home.nav.sysAdmin"));
				JSONObject json = JSONObject.fromObject(map);
				String message = json.toString();
				UserOperHelper.logMessage(message);
			}
			configForm.setMap(map);
				request.setAttribute("configForm", configForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("configView");
		}
	}
	
	public ActionForward getKkConfigInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		TimeCounter.logCurrentTime("Action-getKkConfigInfo", true, getClass());
		KmssMessages messages = new KmssMessages();
		
		JSONObject result = new JSONObject();
		
		try {
			
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			
			String infoType = request.getParameter("infoType");
			if (StringUtil.isNotNull(infoType)) {
				if ("innerDomain".equals(infoType)) {
					
					result.put("value",
							new KkConfig().getValue(KeyConstants.KK_INNER_DOMAIN));
					
				} else if ("serverjAuthId".equals(infoType)) {
					
					result.put("value",
							getKkImConfigService().getValuebyKey(KeyConstants.KK_SERVERJ_AUTHID));
					
				} else if ("sererrjAuthKey".equals(infoType)) {
					
					result.put("value",
							getKkImConfigService().getValuebyKey(KeyConstants.KK_SERERRJ_AUTHKEY));
					
				} else if ("serverjSign".equals(infoType)) {
					String serverjAuthId = getKkImConfigService().getValuebyKey(KeyConstants.KK_SERVERJ_AUTHID);
					String sererrjAuthKey = getKkImConfigService().getValuebyKey(KeyConstants.KK_SERERRJ_AUTHKEY);
					if (StringUtil.isNotNull(serverjAuthId) && StringUtil.isNotNull(sererrjAuthKey)) {
						result.put("value",
								KKConfigUtil.getKKCurrDateSign(serverjAuthId, sererrjAuthKey));
					} else {
						result.put("value", "");
					}
					
				}
			}
			
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result.toString());
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}

}
