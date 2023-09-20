package com.landray.kmss.third.ding.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.dingtalk.api.request.OapiProcessListbyuseridRequest;
import com.dingtalk.api.response.OapiProcessListbyuseridResponse;
import com.dingtalk.api.response.OapiProcessListbyuseridResponse.ProcessTopVo;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.forms.ThirdDingOrmTempForm;
import com.landray.kmss.third.ding.model.ThirdDingOrmTemp;
import com.landray.kmss.third.ding.service.IThirdDingOrmTempService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class ThirdDingOrmTempAction extends ExtendAction {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingOrmTempAction.class);

	private IThirdDingOrmTempService thirdDingOrmTempService;

	@Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdDingOrmTempService == null) {
			thirdDingOrmTempService = (IThirdDingOrmTempService) getBean("thirdDingOrmTempService");
		}
		return thirdDingOrmTempService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingOrmTemp.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
	}

	@Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
		ThirdDingOrmTempForm thirdDingOrmTempForm = (ThirdDingOrmTempForm) super.createNewForm(mapping, form, request,
				response);
		((IThirdDingOrmTempService) getServiceImp(request)).initFormSetting((IExtendForm) form,
				new RequestContext(request));
		if (StringUtil.isNull(thirdDingOrmTempForm.getFdDingTemplateType())) {
			thirdDingOrmTempForm.setFdDingTemplateType("0");
		}
		if (StringUtil.isNull(thirdDingOrmTempForm.getFdStartFlow())) {
			thirdDingOrmTempForm.setFdStartFlow("1");
		}
		return thirdDingOrmTempForm;
	}

	public ActionForward check(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-check", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			String fdId = request.getParameter("fdId");
			String fdTemplateId = request.getParameter("fdTemplateId");
			String fdProcessCode = request.getParameter("fdProcessCode");
			List list = (List) getServiceImp(request).findList("fdIsAvailable=1 and fdId != '" + fdId
					+ "' and (fdProcessCode='" + fdProcessCode + "' or fdTemplateId='" + fdTemplateId + "')", null);
			if (list != null && list.size() > 0) {
				json.put("status", 0);
				json.put("msg", "流程不能设置双向同步！");
			} else {
				json.put("status", 1);
				json.put("msg", "成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-check", false, getClass());
		return null;
	}

	/**
	 * <p>
	 * 获取用户可见的审批模板
	 * </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	public ActionForward getProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-check", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String userId = request.getParameter("userId");
			String dingUrl = DingConstant.DING_PREFIX
					+ "/topapi/process/listbyuserid"
					+ DingUtil.getDingAppKeyByEKPUserId("?", null);
			logger.debug("获取用户可见的审批模板接口：" + dingUrl);
			ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
			OapiProcessListbyuseridRequest oapiRequest = new OapiProcessListbyuseridRequest();
			if (StringUtil.isNotNull(userId)) {
				oapiRequest.setUserid(userId);
			}
			oapiRequest.setOffset(0L);
			oapiRequest.setSize(100L);
			OapiProcessListbyuseridResponse oapiResponse = client.execute(oapiRequest,
					DingUtils.getDingApiService().getAccessToken());
			if (oapiResponse.getErrcode() == 0) {
				List<ProcessTopVo> processList = oapiResponse.getResult().getProcessList();
				json.put("processList", processList);
			}
			System.out.println("获取用户可见的审批模板，返回码：" + oapiResponse.getErrcode() + ",返回信息：" + oapiResponse.getErrmsg()
					+ ",返回结果：" + oapiResponse.getResult());
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-check", false, getClass());
		return null;
	}
}
