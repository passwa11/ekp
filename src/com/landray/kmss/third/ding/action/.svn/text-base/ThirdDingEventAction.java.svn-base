package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.request.OapiCallBackDeleteCallBackRequest;
import com.dingtalk.api.response.OapiCallBackDeleteCallBackResponse;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.forms.ThirdDingEventForm;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingEvent;
import com.landray.kmss.third.ding.service.IThirdDingEventService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdDingEventAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingEventAction.class);
	
    private IThirdDingEventService thirdDingEventService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingEventService == null) {
            thirdDingEventService = (IThirdDingEventService) getBean("thirdDingEventService");
        }
        return thirdDingEventService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingEvent.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingEventForm thirdDingEventForm = (ThirdDingEventForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingEventService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        if(StringUtil.isNull(thirdDingEventForm.getFdCallbackUrl())){
        	thirdDingEventForm.setFdCallbackUrl(DingConfig.newInstance().getDingCallbackurl());
        }
        if(StringUtil.isNull(thirdDingEventForm.getFdIsAvailable())){
        	thirdDingEventForm.setFdIsAvailable("true");
        }
		if (StringUtil.isNull(thirdDingEventForm.getFdIsStatus())) {
			thirdDingEventForm.setFdIsStatus("true");
		}
        return thirdDingEventForm;
    }
    
    @Override
    public ActionForward data(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			if(page.getList()==null||page.getList().size()==0){
				
				String dingUrl = DingConstant.DING_PREFIX
						+ "/call_back/delete_call_back"
						+ DingUtil.getDingAppKeyByEKPUserId("?", null);
				logger.debug("钉钉接口（删除回调接口）："+dingUrl);
				ThirdDingTalkClient  client = new ThirdDingTalkClient(dingUrl);
		    	OapiCallBackDeleteCallBackRequest req = new OapiCallBackDeleteCallBackRequest();
		    	req.setTopHttpMethod("GET");
		    	OapiCallBackDeleteCallBackResponse res = client.execute(req,DingUtils.getDingApiService().getAccessToken());
		    	if(res.getErrcode()==0){
		    		
		    	}else{
		    		logger.error("删除失败，详细："+res.getBody());
		    	}
		    	logger.debug("token==="+DingUtils.getDingApiService().getAccessToken());
			}
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}

	/**
	 * <p>导入</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @author 孙佳
	 */
	public ActionForward importEvent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		KmssMessages messages = new KmssMessages();
		JSONObject obj = new JSONObject();
		try {
			((IThirdDingEventService) getServiceImp(request)).deleteAll();
			String kkPcAppInit = "/third/ding/resource/json/ding_event_init.json";
			String dingEvent = FileUtil.getFileString(PluginConfigLocationsUtil.getWebContentPath() + kkPcAppInit,
					"UTF-8");
			String urlPrefix = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
			JSONArray array = JSONArray.fromObject(dingEvent);
			JSONObject jsonObject = new JSONObject();
			ThirdDingEvent event = new ThirdDingEvent();
			for (int system = 0; system < array.size(); system++) {
				event = new ThirdDingEvent();
				JSONObject json = JSONObject.fromObject(array.get(system));
				event.setFdName(json.getString("fdName").toString());
				event.setFdCallbackUrl(urlPrefix + json.getString("fdCallbackUrl"));
				event.setFdIsStatus("1".equals(json.getString("fdIsStatus")) ? true : false);
				event.setFdTag(json.getString("fdTag"));
				if (UserOperHelper.allowLogOper("importEvent",
						getServiceImp(request).getModelName())) {
					UserOperContentHelper.putAdd(event, "fdName",
							"fdCallbackUrl", "fdIsStatus", "fdTag");
				}
				getServiceImp(request).add(event);
			}

		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		obj.accumulate("result", true);
		if (messages.hasError()) {
			obj.accumulate("result", false);
			return getActionForward("lui-failure", mapping, form, request, response);
		}
		request.setAttribute("lui-source", obj);
		return getActionForward("lui-source", mapping, form, request, response);
    }

	/**
	 * <p>启用or禁用</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	public ActionForward updateStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateStatus", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject object = new JSONObject();
		String fdId = request.getParameter("fdId");
		String fdIsStatus = request.getParameter("status");
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			ThirdDingEvent event = (ThirdDingEvent) getServiceImp(request).findByPrimaryKey(fdId);
			object = ((IThirdDingEventService) getServiceImp(request)).updateCallBack(event);
			if (UserOperHelper.allowLogOper("updateStatus",
					getServiceImp(request).getModelName())) {
				if (Boolean.valueOf(fdIsStatus)) {
					UserOperHelper.setEventType(ResourceUtil.getString(
							"third-ding:third.ding.oper.enable"));
				} else {
					UserOperHelper.setEventType(ResourceUtil.getString(
							"third-ding:third.ding.oper.disable"));
				}
			}
			if ("0".equals(object.get("code"))) {
				Boolean oldIsStatus = event.getFdIsStatus();
				event.setFdIsStatus(Boolean.valueOf(fdIsStatus));
				if (UserOperHelper.allowLogOper("updateStatus",
						getServiceImp(request).getModelName())) {
					UserOperContentHelper.putUpdate(event)
							.putSimple("updateStatus", oldIsStatus,
									event.getFdIsStatus());
				}
				getServiceImp(request).update(event);
			}

		} catch (Exception e) {
			logger.error("", e);
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(object.toString());
		TimeCounter.logCurrentTime("Action-updateStatus", false, getClass());
		return null;
	}

}
