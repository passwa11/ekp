package com.landray.kmss.third.weixin.work.action;

import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.appconfig.forms.SysAppConfigForm;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.quartz.model.SysQuartzJob;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.third.weixin.cluster.ThirdWeixinConfigMessage;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.oms.WxOmsConfig;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkContactService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WeixinWorkConfigAction extends SysAppConfigAction {

	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			//System.out.println("------update-------");
			WeixinWorkConfig config = WeixinWorkConfig.newInstance();
			Map<String, String> map = config.getDataMap();
			if (map != null) {
				// 兼容配置页面数据
				String value = map.get("wxPersonOrder"); // 人员排序
				if (StringUtil.isNotNull(value)) {
					map.put("wxPersonOrder", "");
				}

				value = map.get("wxDeptOrder"); // 部门排序
				if (StringUtil.isNotNull(value)) {
					map.put("wxDeptOrder", "");
				}

				value = map.get("wxPostEnabled"); // 职务
				if (StringUtil.isNotNull(value)) {
					map.put("wxPostEnabled", "");
				}

				value = map.get("wxOfficePhone"); // 办公电话
				if (StringUtil.isNotNull(value)) {
					map.put("wxOfficePhone", "");
				}

				// 通讯录同步开关 wxOmsOutEnabled
				value = map.get("wxOmsOutEnabled"); // 办公电话
				if (StringUtil.isNotNull(value)) {
					map.put("wxOmsOutEnabled", "");
				}
			}

			SysAppConfigForm appConfigForm = (SysAppConfigForm) form;
			IThirdWeixinWorkContactService thirdWeixinWorkContactService = (IThirdWeixinWorkContactService)
					SpringBeanUtil.getBean("thirdWeixinWorkContactService");
			thirdWeixinWorkContactService.changeOrgTypeMapping((String)appConfigForm.getMap().get("syncContact.orgType.setting"));

			super.update(mapping, form, request, response);

			if("true".equals(appConfigForm.getValue("wxEnabled"))) {
				if("true".equals(appConfigForm.getValue("corpGroupIntegrateEnable")) && "true".equals(appConfigForm.getValue("syncCorpGroupOrgEnable"))) {
					WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
					wxworkApiService.resetAppShareInfoMap();
					try {
						MessageCenter
								.getInstance()
								.sendToOther(
										new ThirdWeixinConfigMessage(
												"resetAppShareInfoMap",
												appConfigForm.getDataMap()));
					} catch (Exception e) {
						logger.error("", e);
					}
				}
				String syncSelection = (String)appConfigForm.getValue("syncSelection");
				if("toWx".equals(syncSelection)){
					String wxOrgId_new = (String)appConfigForm.getValue("wxOrgId");
					String wxOrgId_old = map.get("wxOrgId");
					if(StringUtil.isNotNull(wxOrgId_new) && !wxOrgId_new.equals(wxOrgId_old)){
						WxOmsConfig wxOmsConfig = new WxOmsConfig();
						wxOmsConfig.setLastUpdateTime("");
						wxOmsConfig.save();
					}
				}
			}

			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return this.getActionForward("success", mapping, form, request,
					response);
		} catch (Exception e) {
			messages.addError((Throwable) e);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return this.getActionForward("failure", mapping, form, request,
					response);
		}
	}

	protected ActionForward getActionForward(String defaultForward,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String para = request.getParameter("forward");
		if (!StringUtil.isNull(para) && !"failure".equals(defaultForward)) {
            defaultForward = para;
        }
		return mapping.findForward(defaultForward);
	}

	public ActionForward deleteCustom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-appList", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");

		try {
			WeixinWorkConfig config = new WeixinWorkConfig();
			Map<String, String> dataMap = config.getDataMap();
			Set<String> set = dataMap.keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				if (key.contains("org2wxWork.custom.[")) {
					iterator.remove();
					dataMap.remove(key);
				}
			}
			config.getDataMap().putAll(dataMap);
			config.superSave();
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

	public ActionForward syncCorpGtoup2ekp(ActionMapping mapping, ActionForm form,
									  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-syncCorpGtoup2ekp", true, getClass());
		KmssMessages messages = new KmssMessages();

		try {
			ISysQuartzJobService sysQuartzJobService = (ISysQuartzJobService)SpringBeanUtil.getBean("sysQuartzJobService");
			List<SysQuartzJob> list = sysQuartzJobService.findList("fdJobService='synchroCorpgroupOrg2Ekp' and fdJobMethod='triggerSynchro'",null);
			if(list==null || list.isEmpty()){
				throw new Exception("找不到定时任务【同步上下游组织到EKP生态组织】，请先执行导入定时任务");
			}
			String jobId = list.get(0).getFdId();
			response.sendRedirect(request.getContextPath()+"/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=view&fdId="+jobId);
			//request.getRequestDispatcher("/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=view&fdId="+jobId).forward(request,response);
			return null;
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-syncCorpGtoup2ekp", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}


}
