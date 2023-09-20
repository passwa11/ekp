package com.landray.kmss.third.ding.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingApp;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IThirdDingAppService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * <P>钉钉应用管理action</P>
 * @author 孙佳
 * 2018年12月11日
 */
public class DingMicroAppAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingMicroAppAction.class);

	private IThirdDingAppService thirdDingAppService;

	@Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdDingAppService == null) {
			thirdDingAppService = (IThirdDingAppService) getBean("thirdDingAppService");
		}
		return thirdDingAppService;
	}

	protected IPdaModuleConfigMainService pdaModuleConfigMainService;

	protected IBaseService getPdaModuleConfigMainServiceImp() {
		if (pdaModuleConfigMainService == null) {
            pdaModuleConfigMainService = (IPdaModuleConfigMainService) getBean("pdaModuleConfigMainService");
        }
		return pdaModuleConfigMainService;
	}

	/**
	 * <p>创建钉钉应用</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	public ActionForward createMicroApp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String[] ids = request.getParameterValues("List_Selected");
			JSONObject result = new JSONObject();
			result.put("status", "true");
			DingApiService dingApiService = DingUtils.getDingApiService();
			String dingDomain = DingConfig.newInstance().getDingDomain();
			JSONObject app = null;
			if (null == ids || ids.length <= 0) {
				logger.warn("id不能为空！！！");
				return null;
			}
			List<PdaModuleConfigMain> pdaModule = getPdaModuleConfigMainServiceImp().findByPrimaryKeys(ids);

			if (null != pdaModule && pdaModule.size() > 0) {
				for (PdaModuleConfigMain main : pdaModule) {
					app = new JSONObject();
					//先调用钉钉上传接口，上传应用icon
					String mediaId = ((IThirdDingAppService) getServiceImp(request))
							.uploadAppIconByDing(PluginConfigLocationsUtil.getWebContentPath() + main.getFdIconUrl());
					if (StringUtil.isNotNull(mediaId)) {
						app.put("appIcon", mediaId); //应用图标
						app.put("appName", main.getFdName()); //应用名称
						app.put("appDesc", StringUtil.isNotNull(main.getFdDescription()) ? main.getFdDescription()
								: main.getFdName()); //应用描述
						app.put("homepageUrl",
								dingDomain
										+ "/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=open&fdId="
										+ main.getFdId() + "&oauth=ekp"); //应用的移动端主页
						app.put("pcHomepageUrl",
								dingDomain + "/third/ding/pc/pcpg.jsp?pg=" + main.getFdUrlPrefix() + "&oauth=ekp"); //应用的PC端主页

						logger.debug("创建钉钉应用请求参数：" + app);
						JSONObject object = dingApiService.appCreate(app);
						if ("0".equals(object.get("errcode").toString())) {
							ThirdDingApp modelObj = new ThirdDingApp();
							modelObj.setFdEkpId(main.getFdId());
							modelObj.setFdDingId(object.get("agentid").toString());
							getServiceImp(request).add(modelObj);
						} else {
							logger.warn("创建钉钉应用失败：返回错误码：" + object.get("errcode") + "，错误信息：" + object.get("errmsg"));
							result.put("msg", object.get("errmsg").toString());
							result.put("status", "false");
						}
					}
				}
			}
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		return null;
	}
}
