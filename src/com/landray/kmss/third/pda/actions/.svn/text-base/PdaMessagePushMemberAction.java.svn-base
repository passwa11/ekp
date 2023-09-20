package com.landray.kmss.third.pda.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.actions.BaseAction;

import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.pda.constant.PdaModuleConfigConstant;
import com.landray.kmss.third.pda.service.IPdaMessagePushMemberService;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class PdaMessagePushMemberAction extends BaseAction {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PdaMessagePushMemberAction.class);

	private IPdaMessagePushMemberService pdaMessagePushMemberService;

	protected IPdaMessagePushMemberService getServiceImp() {
		if (pdaMessagePushMemberService == null){
			pdaMessagePushMemberService = (IPdaMessagePushMemberService) SpringBeanUtil.getBean("pdaMessagePushMemberService");
		}
		return pdaMessagePushMemberService;
	}

	/**
	 * 手机token信息手机 不允许一个手机对应多个人的情况，也不允许一个人使用多个手机的情况
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward collectMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rtnStr = "";
		try {
			String deviceToken = request.getParameter("deviceToken");
			String msgStatus = request.getParameter("msgStatus");
			if (StringUtil.isNotNull(deviceToken) && StringUtil.isNotNull(msgStatus)) {
				SysOrgPerson curUser = UserUtil.getUser(request);
				String fdPersonFdId = curUser.getFdId();
				int appType = PdaFlagUtil.getPdaClientType(request);
				// 先删除所有的有关该设备或该个人的信息,再新增一条记录
				getServiceImp().deletePdaMessagePushMemberList(deviceToken,fdPersonFdId);
				if (MSG_STATUS.equals(msgStatus)) {
					getServiceImp().addPdaMessagePushMember(deviceToken, curUser, appType);
				}
				String dataUrl = PdaModuleConfigConstant.PDA_MSG_PUSH_DATA_URL+ "&orgId=" + curUser.getFdId() + "&m_seq="+ Math.random();
				request.setAttribute("dataUrl",dataUrl);
				logger.debug("采集用户:" + curUser.getFdName() + "信息, deviceToken =" + deviceToken);
			} else {
				if (StringUtil.isNull(deviceToken)){
					rtnStr += ResourceUtil.getString("third-pda:pda.app.deviceToken.isnull");
				}
				if (StringUtil.isNull(msgStatus)){
					rtnStr += ResourceUtil.getString("third-pda:pda.app.deviceToken.status.isnull");
				}
				logger.debug("采集用户信息不全:" + rtnStr);
			}
		} catch (Exception e) {
			rtnStr += e.getMessage();
			logger.error("收集用户信息错误:" + rtnStr);
		}
		request.setAttribute("rtnStr", rtnStr);
		setClearCache(response);
		return mapping.findForward("view");

	}

	/**
	 * 更改是否推送消息状态
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeMsg(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		return collectMsg(mapping, form, request, response);
	}

	private void setClearCache(HttpServletResponse response) {
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");
	}
	
	//是否启用接受推送设置(0 不启用,1 启用)
	private static final String MSG_STATUS = "1";
}
