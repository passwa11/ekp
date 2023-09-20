package com.landray.kmss.third.weixin.mutil.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.third.weixin.mutil.forms.ThirdWxWorkConfigForm;
import com.landray.kmss.third.weixin.mutil.model.ThirdWxBaseConfig;
import com.landray.kmss.third.weixin.mutil.model.ThirdWxWorkMutilConfig;
import com.landray.kmss.third.weixin.mutil.model.ThirdWxworkOmsMutilInit;
import com.landray.kmss.third.weixin.mutil.oms.WxOmsConfig;
import com.landray.kmss.third.weixin.mutil.service.IThirdWxWorkConfigService;
import com.landray.kmss.third.weixin.mutil.service.IThirdWxworkOmsInitService;
import com.landray.kmss.third.weixin.mutil.service.spring.Ekp2WxQuartzService;
import com.landray.kmss.third.weixin.mutil.spi.model.WxworkOmsRelationMutilModel;
import com.landray.kmss.third.weixin.mutil.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.mutil.util.WxmutilUtils;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdWxWorkConfigAction extends ExtendAction {

	private IThirdWxWorkConfigService thirdMutilWxWorkConfigService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdMutilWxWorkConfigService == null) {
			thirdMutilWxWorkConfigService = (IThirdWxWorkConfigService) getBean("thirdMutilWxWorkConfigService");
        }
		return thirdMutilWxWorkConfigService;
    }

	private ISysAppConfigService sysAppConfigService;

	public ISysAppConfigService getSysAppConfigService() {
		if (sysAppConfigService == null) {
            sysAppConfigService = (ISysAppConfigService) getBean("sysAppConfigService");
        }
		return sysAppConfigService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		//HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWxWorkMutilConfig.class);
		String whereBlock = StringUtil.isNotNull(hqlInfo.getWhereBlock()) ? hqlInfo.getWhereBlock() : "1=1";
		whereBlock += " and thirdWxWorkMutilConfig.fdId in (select max(config.fdId) from com.landray.kmss.third.weixin.mutil.model.ThirdWxWorkMutilConfig config group by config.fdKey)";
		hqlInfo.setSelectBlock(" thirdWxWorkMutilConfig.fdKey, thirdWxWorkMutilConfig.fdName ");
		hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWxWorkConfigForm thirdWxWorkConfigForm = (ThirdWxWorkConfigForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWxWorkConfigService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWxWorkConfigForm;
    }

	@Override
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String forwardUrl = "/third/weixin/mutil/weixin_config.jsp";
		try {
			String fdKey = request.getParameter("fdKey");
			ThirdWxWorkConfigForm configForm = (ThirdWxWorkConfigForm) form;
			Map<String, String> map = new HashMap<String, String>();
			if (!StringUtil.isNull(fdKey)) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(" fdKey =:fdkey");
				hqlInfo.setParameter("fdkey", fdKey);
				List<ThirdWxWorkMutilConfig> wxWorkConfig = (List<ThirdWxWorkMutilConfig>) getServiceImp(request)
						.findList(hqlInfo);
				for (ThirdWxWorkMutilConfig config : wxWorkConfig) {
					map.put(config.getFdField(), config.getFdValue());
				}
				configForm.setMap(map);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return new ActionForward(forwardUrl);
		}
	}

	@Override
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdkey = request.getParameter("fdkey");
			if (StringUtil.isNull(fdkey)) {
                messages.addError(new NoRecordException());
            } else {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(" fdKey =:fdkey");
				hqlInfo.setParameter("fdkey", fdkey);
				hqlInfo.setSelectBlock(" thirdWxWorkMutilConfig.fdId ");
				List<ThirdWxWorkMutilConfig> wxWorkConfig = (List<ThirdWxWorkMutilConfig>) getServiceImp(request)
						.findList(hqlInfo);
				getServiceImp(request).delete(wxWorkConfig.toArray(new String[0]));

				// 删除人员对照表数据
				IWxworkOmsRelationService mutilWxworkOmsRelationService = (IWxworkOmsRelationService) SpringBeanUtil
						.getBean("mutilWxworkOmsRelationService");
				HQLInfo delInfo = new HQLInfo();
				delInfo.setWhereBlock("fdWxKey=:fdWxKey");
				delInfo.setParameter("fdWxKey", fdkey);
				delInfo.setSelectBlock(" wxworkOmsRelationMutilModel.fdId ");
				List<WxworkOmsRelationMutilModel> wxworkOmsRelationMutilModel = mutilWxworkOmsRelationService
						.findList(delInfo);
				if (wxworkOmsRelationMutilModel.size() > 0) {
					mutilWxworkOmsRelationService.delete(
							wxworkOmsRelationMutilModel.toArray(new String[0]));
				}

				// 删除未匹配上的部门人员数据
				IThirdWxworkOmsInitService thirdMutilWxworkOmsInitService = (IThirdWxworkOmsInitService) SpringBeanUtil
						.getBean("thirdMutilWxworkOmsInitService");
				HQLInfo delInitInfo = new HQLInfo();
				delInitInfo.setWhereBlock("fdWxKey=:fdWxKey");
				delInitInfo.setParameter("fdWxKey", fdkey);
				delInitInfo.setSelectBlock(" thirdWxworkOmsMutilInit.fdId ");
				List<ThirdWxworkOmsMutilInit> thirdWxworkOmsMutilInit = thirdMutilWxworkOmsInitService
						.findList(delInitInfo);
				if (thirdWxworkOmsMutilInit.size() > 0) {
					thirdMutilWxworkOmsInitService
							.delete(thirdWxworkOmsMutilInit
									.toArray(new String[0]));
				}

				// 删除更新时间（置为空）
				WxOmsConfig wxOmsConfig = new WxOmsConfig();
				wxOmsConfig.setLastUpdateTime(fdkey, "");
				wxOmsConfig.save();

				// 删除定时任务
				Ekp2WxQuartzService ekp2WxQuartzService = (Ekp2WxQuartzService) getBean(
						"ekp2WxQuartzService");
				ekp2WxQuartzService.deleteConfig(fdkey);
			}
			refreshCache();
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdKey = request.getParameter("fdKey");
			String autoclose = request.getParameter("autoclose");
			if (StringUtil.isNotNull(autoclose) && "false".equals(autoclose)) {
				request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
			}
			if (StringUtil.isNull(fdKey)) {
				throw new NoRecordException();
			}
			ThirdWxWorkConfigForm configForm = (ThirdWxWorkConfigForm) form;
			((IThirdWxWorkConfigService) getServiceImp(request)).save(fdKey, configForm.getMap());
			
			Ekp2WxQuartzService ekp2WxQuartzService = (Ekp2WxQuartzService) getBean(
					"ekp2WxQuartzService");
			refreshCache();
			ekp2WxQuartzService.addConfig(fdKey); // 配置定时任务（人员对照表以及组织架构同步）
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton("button.back", "/third/weixin/mutil/third_wx_work_config/index.jsp", false).save(request);
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}

	/**
	 * <p>获取所有微信集成配置</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	public ActionForward wxConfigList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-wxConfigList", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		JSONArray ja = new JSONArray();
		JSONObject jn = null;
		try {
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = " 1=1 group by fdKey, fdName";
			hqlInfo.setSelectBlock(" thirdWxWorkMutilConfig.fdKey, thirdWxWorkMutilConfig.fdName ");
			hqlInfo.setWhereBlock(whereBlock);
			List<?> list = getServiceImp(request).findList(hqlInfo);
			for (int i = 0; i < list.size(); i++) {
				jn = new JSONObject();
				Object[] info = (Object[]) list.get(i);
				jn.put("key", info[0]);
				jn.put("name", info[1]);
				ja.add(jn);
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
		TimeCounter.logCurrentTime("Action-wxConfigList", false, getClass());
		return null;
	}

	/**
	 * <p>企业微信数据迁移</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	public ActionForward transfer(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			Map<String, String> map = getSysAppConfigService().findByKey("com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig");
			if (null != map && !map.isEmpty()) {
				map.put("wxName", "企业微信配置_1");
				map.put("wxKey", "wxWork_1");
				((IThirdWxWorkConfigService) getServiceImp(request)).save("wxWork_1", map);
				refreshCache();
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-transfer", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}


	/**
	 * <p>更新缓存配置</p>
	 * @author 孙佳
	 * 2019年1月23日
	 */
	private void refreshCache() {
		//更新配置缓存
		ThirdWxBaseConfig.newInstance().updateWxConfigCache();
		// 更新企业微信配置
		WxmutilUtils.resetWxworkConfig();
	}
}
