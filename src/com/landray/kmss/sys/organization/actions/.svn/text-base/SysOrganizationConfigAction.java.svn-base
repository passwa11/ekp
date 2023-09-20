package com.landray.kmss.sys.organization.actions;

import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.appconfig.forms.SysAppConfigForm;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.authentication.user.KMSSUserAuthInfoCache;
import com.landray.kmss.sys.organization.transfer.SysOrgPersonLoginNameChecker;
import com.landray.kmss.util.ClassUtils;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 处理参数配置相关
 *
 * @author 严明镜
 * @version 1.0 2021年04月07日
 */
public class SysOrganizationConfigAction extends SysAppConfigAction {

	/**
	 * 清理用户权限缓存
	 */
	public ActionForward clearUserAuthInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-clearUserAuthInfo", true, getClass());
		KmssMessages messages = new KmssMessages();
		String clearIds = request.getParameter("clearIds");
		if (StringUtil.isNotNull(clearIds)) {
			try {
				if ("all".equals(clearIds)) {
					//全部清理
					KMSSUserAuthInfoCache.getInstance().removeAll();
				} else {
					//选定清理
					Stream<String> ids = null;

					try {
						ids = Arrays.stream(clearIds.split(";"));

						ids.forEach(id ->
								KMSSUserAuthInfoCache.getInstance().remove(id)
						);
					} catch (Exception e){

					}finally {
						if(ids!=null) {
                            ids.close();
                        }
					}
				}
			} catch (Exception e) {
				messages.addError(e);
			}
		}

		TimeCounter.logCurrentTime("Action-clearUserAuthInfo", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject result = new JSONObject();
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			result.put("status", false);
			result.put("title", "操作失败");
			response.getWriter().print(result);
			return null;
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			result.put("status", true);
			result.put("title", "操作成功");
			response.getWriter().print(result);
			return null;
		}
	}

	/**
	 * 登录名迁移检查
	 */
	public ActionForward checkLoginNameTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-clearUserAuthInfo", true, getClass());
		SysOrgPersonLoginNameChecker checker = new SysOrgPersonLoginNameChecker();
		JSONObject result = new JSONObject();
		if (checker.isRuned()) {
			result.put("status", true);
		} else {
			result.put("status", false);
		}
		TimeCounter.logCurrentTime("Action-clearUserAuthInfo", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(result);
		return null;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String modelName = request.getParameter("modelName");
			String autoclose = request.getParameter("autoclose");
			if (StringUtil.isNull(modelName)) {
				throw new NoRecordException();
			}
			if (StringUtil.isNotNull(autoclose) && "false".equals(autoclose)) {
				request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
			}
			SysAppConfigForm appConfigForm = (SysAppConfigForm) form;
			BaseAppConfig appConfig = (BaseAppConfig) ClassUtils.forName(modelName).newInstance();

			if("com.landray.kmss.sys.organization.model.SysOrganizationConfig".equalsIgnoreCase(modelName)){
				String userAuthCacheLimitIds = (String) appConfigForm.getDataMap().get("userAuthCacheLimitIds");
				String userAuthCacheLimitNames = (String) appConfigForm.getDataMap().get("userAuthCacheLimitNames");
				//先清空原本的缓存数据，避免数据错误
				Iterator iterator = appConfig.getDataMap().keySet().iterator();
				while(iterator.hasNext()){
					String key = (String) iterator.next();
					if(key.startsWith("userAuthCacheLimitIds") || key.startsWith("userAuthCacheLimitNames")){
						iterator.remove();
					}
				}
				if(StringUtil.isNotNull(userAuthCacheLimitIds) && userAuthCacheLimitIds.length() > 1000){
					int size = userAuthCacheLimitIds.length() / 1000 + 1;
					for (int i = 0; i < size; i++) {
						String value = userAuthCacheLimitIds.substring(1000 * i,Math.min((i + 1) * 1000, userAuthCacheLimitIds.length()));
						String field = "userAuthCacheLimitIds" + i;
						appConfigForm.getDataMap().put(field, value);
					}
					appConfigForm.getDataMap().remove("userAuthCacheLimitIds");
				}
				if(StringUtil.isNotNull(userAuthCacheLimitNames) && userAuthCacheLimitNames.length() > 1000){
					int size = userAuthCacheLimitNames.length() / 1000 + 1;
					for (int i = 0; i < size; i++) {
						String value = userAuthCacheLimitNames.substring(1000 * i,Math.min((i + 1) * 1000, userAuthCacheLimitNames.length()));
						String field = "userAuthCacheLimitNames" + i;
						appConfigForm.getDataMap().put(field, value);
					}
					appConfigForm.getDataMap().remove("userAuthCacheLimitNames");
				}
			}
			Map appConfigMap = appConfigForm.getMap();
			appConfig.getDataMap().putAll(appConfigMap);
			appConfig.save();

			//发送update事件
			WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext())
					.publishEvent(new Event_Common(modelName));
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}
}
