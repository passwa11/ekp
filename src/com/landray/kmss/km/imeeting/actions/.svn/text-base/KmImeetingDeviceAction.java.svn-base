package com.landray.kmss.km.imeeting.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingDeviceForm;
import com.landray.kmss.km.imeeting.model.KmImeetingDevice;
import com.landray.kmss.km.imeeting.service.IKmImeetingDeviceService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 辅助设备 Action
 */
public class KmImeetingDeviceAction extends ExtendAction {
	protected IKmImeetingDeviceService kmImeetingDeviceService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmImeetingDeviceService == null) {
            kmImeetingDeviceService = (IKmImeetingDeviceService) getBean("kmImeetingDeviceService");
        }
		return kmImeetingDeviceService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmImeetingDeviceForm deviceForm = (KmImeetingDeviceForm) super
				.createNewForm(mapping, form, request, response);
		if (StringUtil.isNull(deviceForm.getFdIsAvailable())) {
			deviceForm.setFdIsAvailable("1");
		}
		return deviceForm;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, KmImeetingDevice.class);
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.list(mapping, form, request, response);
		String contentType = request.getParameter("contentType");
		if (!"failure".equals(forward.getName()) && "json".equals(contentType)) {
			return getActionForward("data", mapping, form, request, response);
		}
		return forward;
	}

	// AJAX形式，返回所有会议服务
	public ActionForm listDevices(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listDevices", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			
			HQLInfo hqlInfo = new HQLInfo();
			
			String orderby = request.getParameter("orderby");
			if(StringUtil.isNotNull(orderby) && "fdOrder".equals(orderby)) {
				String ordertype = request.getParameter("ordertype");
				
				if(StringUtil.isNotNull(ordertype)) {
					
					if("up".equals(ordertype)) {
						hqlInfo.setOrderBy("fdOrder asc");
					} else if("down".equals(ordertype)) {
						hqlInfo.setOrderBy("fdOrder desc");
					}
				}
			}
			hqlInfo.setWhereBlock(
					"kmImeetingDevice.fdIsAvailable =:fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
			// 使用权限过滤
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
			List<KmImeetingDevice> devices = getServiceImp(request).findList(
					hqlInfo);
			JSONArray array = new JSONArray();
			for (KmImeetingDevice device : devices) {
				JSONObject obj = new JSONObject();
				obj.put("fdId", device.getFdId());
				obj.put("fdName", StringUtil.XMLEscape(device.getFdName()));
				array.add(obj);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(array.toString());
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listDevices", false, getClass());
		return null;
	}

}
