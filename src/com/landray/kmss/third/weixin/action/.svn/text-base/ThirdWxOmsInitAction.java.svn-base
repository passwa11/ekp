package com.landray.kmss.third.weixin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.service.IThirdWxOmsInitService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * 组织初始化 Action
 * 
 * @author
 * @version 1.0 2017-06-08
 */
public class ThirdWxOmsInitAction extends ExtendAction {
	protected IThirdWxOmsInitService thirdWxOmsInitService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdWxOmsInitService == null) {
			thirdWxOmsInitService = (IThirdWxOmsInitService) getBean(
					"thirdWxOmsInitService");
		}
		return thirdWxOmsInitService;
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		hqlInfo.setWhereBlock("fdIsOrg=" + request.getParameter("fdIsOrg"));
		hqlInfo.setOrderBy("fdPath,fdHandleStatus");
	}
	
	private static Object lock = new Object();

	public ActionForward omsInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-omsInit", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			synchronized (lock) {
				String fdIsOrg = request.getParameter("fdIsOrg");
				//未匹配数据清除
				String sql = "delete from ThirdWxOmsInit";
				if ("org".equals(fdIsOrg)) {
					sql += " where fdIsOrg = 1";
				} else {
					sql += " where fdIsOrg = 0";
				}
				getServiceImp(request).getBaseDao().getHibernateSession().createQuery(sql).executeUpdate();
				getServiceImp(request).getBaseDao().getHibernateSession().flush();
				getServiceImp(request).getBaseDao().getHibernateSession().clear();
				//数据匹配
				if ("1".equals(fdIsOrg)) {
					if (UserOperHelper.allowLogOper("updateDept", "*")) {
						UserOperHelper.setEventType(ResourceUtil
								.getString(
										"third-weixin:table.thirdWxOmsInit"));
						UserOperHelper.setModelNameAndModelDesc(null,
								ResourceUtil.getString(
										"third-weixin:third.wx.menu.tree"));
					}
					((IThirdWxOmsInitService) getServiceImp(request))
							.updateDept(json);
				} else {
					if (UserOperHelper.allowLogOper("updatePerson", "*")) {
						UserOperHelper.setEventType(ResourceUtil
								.getString(
										"third-weixin:thirdWxOmsInit.person.init"));
						UserOperHelper.setModelNameAndModelDesc(null,
								ResourceUtil.getString(
										"third-weixin:third.wx.menu.tree"));
					}
					((IThirdWxOmsInitService) getServiceImp(request))
							.updatePerson(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-omsInit", false, getClass());
		return null;
	}

	public ActionForward wxDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-wxDel", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String type = request.getParameter("type");
			String parentId = request.getParameter("parentId");
			((IThirdWxOmsInitService) getServiceImp(request)).updateWx(fdId,
					parentId,type);
			json.put("status", 1);
			json.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-wxDel", false, getClass());
		return null;
	}

	public ActionForward ekpUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-wxDel", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			String type = request.getParameter("type");
			String fdEKPId = request.getParameter("fdEKPId");
			boolean flag = ((IThirdWxOmsInitService) getServiceImp(request)).updateEKP(fdId,
					fdEKPId,type);
			if(flag){
				json.put("status", 1);
				json.put("msg", "成功");
			}else{
				json.put("status", 0);
				json.put("msg", "请不要重复映射");
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-wxDel", false, getClass());
		return null;
	}

}
