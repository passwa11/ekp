package com.landray.kmss.sys.attend.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.model.SysAttendSynConfig;
import com.landray.kmss.sys.attend.service.ISysAttendSynConfigService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

/**
 * @author linxiuxian
 *
 */
public class SysAttendSynConfigAction extends ExtendAction {
    
	private ISysAttendSynConfigService sysAttendSynConfigService;

	@Override
	protected ISysAttendSynConfigService getServiceImp(HttpServletRequest request) {
		if (sysAttendSynConfigService == null) {
		    sysAttendSynConfigService = (ISysAttendSynConfigService) getBean(
					"sysAttendSynConfigService");
		}
		return sysAttendSynConfigService;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysAttendSynConfig.class.getName());
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		hqlInfo.setGetCount(false);
		Page page = getServiceImp(request).findPage(hqlInfo);
		SysAttendSynConfig model = new SysAttendSynConfig();
		if (page.getList() != null && !page.getList().isEmpty()) {
			model = (SysAttendSynConfig) page.getList().get(0);
		}
		UserOperHelper.logFind(model);// 添加日志信息
		// 判断是否继承启动钉钉
		boolean isEnableDingConfig = AttendUtil.isEnableDingConfig();
		request.setAttribute("isEnableDingConfig", isEnableDingConfig);

		// 判断是否继承启动企业微信
		boolean isEnableWxConfig = AttendUtil.isEnableWx();
		request.setAttribute("isEnableWxConfig", isEnableWxConfig);
		
		rtnForm = getServiceImp(request).convertModelToForm(
				(IExtendForm) form, model, new RequestContext(request));

		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}
	
	public ActionForward cleanTime(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-cleanTime", true, getClass());
        KmssMessages messages = new KmssMessages();
        JSONObject json = new JSONObject();
        json.put("status", 1);
        json.put("msg", "成功");
        try {
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setModelName(SysAttendSynConfig.class.getName());
            hqlInfo.setPageNo(1);
            hqlInfo.setRowSize(1);
            hqlInfo.setGetCount(false);
            Page page = getServiceImp(request).findPage(hqlInfo);
            if (page.getList() != null && !page.getList().isEmpty()) {
                SysAttendSynConfig model = (SysAttendSynConfig) page.getList().get(0);
                model.setFdSyncTime(null);
                getServiceImp(request).update(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
            messages.addError(e);
            json.put("status", 0);
            json.put("msg", e.getMessage());
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json.toString());
        TimeCounter.logCurrentTime("Action-cleanTime", false, getClass());
        return null;
    }

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		
		// 清除缓存
		KmssCache cache = new KmssCache(SysAttendSynConfig.class);
		cache.clear();
				
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}
}
