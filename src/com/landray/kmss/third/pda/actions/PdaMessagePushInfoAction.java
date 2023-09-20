package com.landray.kmss.third.pda.actions;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.third.pda.service.IPdaMessagePushInfoService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import edu.emory.mathcs.backport.java.util.Arrays;

public class PdaMessagePushInfoAction extends BaseAction {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PdaMessagePushInfoAction.class);

	private IPdaMessagePushInfoService pushInfoService;

	protected IPdaMessagePushInfoService getServiceImp() {
		if (pushInfoService == null) {
			pushInfoService = (IPdaMessagePushInfoService) SpringBeanUtil.getBean("pdaMessagePushInfoService");
		}
		return pushInfoService;
	}

	private ISysNotifyTodoService todoService;

	public ISysNotifyTodoService getTodoService() {
		if (todoService == null) {
			todoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
		}
		return todoService;
	}

	@SuppressWarnings("unchecked")
	public ActionForward list(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String orgId = request.getParameter("orgId");
		String fdPersonFdId = StringUtil.isNull(orgId) ? UserUtil.getUser(request).getFdId(): orgId;
		String hasRecord = "0";
		List infoList = getServiceImp().findPdaMessagePushInfo(fdPersonFdId);
		if (infoList != null && infoList.size() > 0) {
			String inSql = "";
			for (Iterator iterator = infoList.iterator(); iterator.hasNext();) {
				String notifyId = (String) iterator.next();
				inSql += "," + notifyId;
			}
			if (StringUtil.isNotNull(inSql)){
				inSql = inSql.substring(1);
			}
			if (StringUtil.isNotNull(inSql)) {
				List todoList = getTodoList(inSql);
				if (todoList != null && todoList.size() > 0) {
					hasRecord = "1";
					request.setAttribute("todoList", todoList);
					request.setAttribute("size", todoList.size());
					int updateInt = getServiceImp().updatePdaMessagePushInfoFdAvailable(fdPersonFdId);
					if (logger.isDebugEnabled()){
						logger.debug("查阅并更新消息文档：" + updateInt + "条！");
					}
				}
			}
		}
		request.setAttribute("hasRecord", hasRecord);
		setClearCache(response);
		return mapping.findForward("list");
	}

	@SuppressWarnings("unchecked")
	private List getTodoList(String inSql) throws Exception {
		HQLInfo hqlInfoNotify = new HQLInfo();
		hqlInfoNotify.setWhereBlock("sysNotifyTodo.fdId in(:fdId)");
		hqlInfoNotify.setParameter("fdId", Arrays.asList(inSql.split(",")));
		hqlInfoNotify.setOrderBy("sysNotifyTodo.fdCreateTime desc");
		List<SysNotifyTodo> todoList = getTodoService().findList(hqlInfoNotify);
		if (UserOperHelper.allowLogOper("getTodoList",
				getTodoService().getModelName())) {
			for (SysNotifyTodo todo : todoList) {
				UserOperContentHelper.putFind(todo.getFdId(),
						todo.getFdSubject(), getTodoService().getModelName());
			}
		}
		return todoList;
	}
	
	private void setClearCache(HttpServletResponse response){
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");
	}
}
