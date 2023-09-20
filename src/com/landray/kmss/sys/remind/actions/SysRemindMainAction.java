package com.landray.kmss.sys.remind.actions;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.remind.model.SysRemindMain;
import com.landray.kmss.sys.remind.model.SysRemindMainReceiver;
import com.landray.kmss.sys.remind.model.SysRemindMainTrigger;
import com.landray.kmss.sys.remind.service.ISysRemindMainService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 提醒设置
 * 
 * @author panyh
 * @date Jun 28, 2020
 */
public class SysRemindMainAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private ISysRemindMainService sysRemindMainService;

	@Override
	protected ISysRemindMainService getServiceImp(HttpServletRequest request) {
		if (sysRemindMainService == null) {
			sysRemindMainService = (ISysRemindMainService) getBean("sysRemindMainService");
		}
		return sysRemindMainService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String tmplId = request.getParameter("tmplId");
		if (StringUtil.isNotNull(tmplId)) {
			hqlInfo.setWhereBlock("fdTemplate.fdId = :tmplId");
			hqlInfo.setParameter("tmplId", tmplId);
		}
	}

	/**
	 * 根据ID获取提醒设置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void getById(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String cloneId = request.getParameter("cloneId");
			String mainId = fdId;
			if (StringUtil.isNotNull(cloneId)) {
				mainId = cloneId;
			}
			if (StringUtil.isNotNull(mainId) && !mainId.startsWith("new_")) {
				SysRemindMain model = (SysRemindMain) getServiceImp(request).findByPrimaryKey(mainId);
				if (model != null) {
					json.put("fdId", fdId);
					json.put("fdIsEnable", model.getFdIsEnable());
					json.put("fdName", model.getFdName());
					json.put("fdIsFilter", model.getFdIsFilter());
					json.put("fdConditionId", model.getFdConditionId());
					json.put("fdConditionName", model.getFdConditionName());
					json.put("fdNotifyType", model.getFdNotifyType());
					json.put("fdSenderType", model.getFdSenderType());
					json.put("fdSenderId", model.getFdSenderId());
					json.put("fdSenderName", model.getFdSenderName());
					json.put("fdSubjectId", model.getFdSubjectId());
					json.put("fdSubjectName", model.getFdSubjectName());
					json.put("fdOrder", model.getFdOrder());
					json.put("fdTemplateId", model.getFdTemplate().getFdId());
					List<SysRemindMainReceiver> list1 = model.getFdReceivers();
					JSONArray receivers = new JSONArray();
					for (SysRemindMainReceiver receiver : list1) {
						JSONObject obj = new JSONObject();
						obj.put("fdId", IDGenerator.generateID());
						obj.put("fdType", receiver.getFdType());
						obj.put("fdReceiverId", receiver.getFdReceiverId());
						obj.put("fdReceiverName", receiver.getFdReceiverName());
						obj.put("fdOrder", receiver.getFdOrder());
						obj.put("fdRemindId", fdId);
						List<SysOrgElement> orgs = receiver.getFdReceiverOrgs();
						StringBuffer orgIds = new StringBuffer();
						StringBuffer orgNames = new StringBuffer();
						if (CollectionUtils.isNotEmpty(orgs)) {
							for (int i = 0; i < orgs.size(); i++) {
								SysOrgElement org = orgs.get(i);
								if (i > 0) {
									orgIds.append(";");
									orgNames.append(";");
								}
								orgIds.append(org.getFdId());
								orgNames.append(org.getFdName());
							}
						}
						obj.put("fdReceiverOrgIds", orgIds.toString());
						obj.put("fdReceiverOrgNames", orgNames.toString());
						receivers.add(obj);
					}
					json.put("fdReceivers", receivers);
					List<SysRemindMainTrigger> list2 = model.getFdTriggers();
					JSONArray triggers = new JSONArray();
					for (SysRemindMainTrigger trigger : list2) {
						JSONObject obj = new JSONObject();
						obj.put("fdId", IDGenerator.generateID());
						obj.put("fdFieldId", trigger.getFdFieldId());
						obj.put("fdFieldName", trigger.getFdFieldName());
						obj.put("fdMode", trigger.getFdMode());
						obj.put("fdDay", trigger.getFdDay());
						obj.put("fdHour", trigger.getFdHour());
						obj.put("fdMinute", trigger.getFdMinute());
						obj.put("fdTime", trigger.getFdTime());
						obj.put("fdOrder", trigger.getFdOrder());
						obj.put("fdRemindId", fdId);
						triggers.add(obj);
					}
					json.put("fdTriggers", triggers);
				}
			}
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			logger.error("获取提醒设置失败：", e);
		}
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
