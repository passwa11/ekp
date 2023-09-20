package com.landray.kmss.third.weixin.work.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.work.oms.ISynchroOrg2WxworkCheck;
import com.landray.kmss.third.weixin.work.oms.SynchroOrg2Wxwork;
import com.landray.kmss.third.weixin.work.oms.WxOmsConfig;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SynchroOrg2WxCheckAction extends ExtendAction {

	protected ISynchroOrg2WxworkCheck synchroOrg2WxCheck;

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(SynchroOrg2WxCheckAction.class);
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (synchroOrg2WxCheck == null) {
            synchroOrg2WxCheck = (ISynchroOrg2WxworkCheck) getBean(
                    "wxworkSynchroOrg2WxCheck");
        }
		return synchroOrg2WxCheck;
	}

	public ActionForward checkData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			List<String> hiers = ((ISynchroOrg2WxworkCheck) getServiceImp(
					request)).getCheckHierarchy();
			// 人员信息
			Long start = System.currentTimeMillis();
			List<String> errorPerson = ((ISynchroOrg2WxworkCheck) getServiceImp(
					request)).checkPersonInfo(hiers);
			JSONArray ja = new JSONArray();
			JSONObject jo = null;
			for (String ep : errorPerson) {
				jo = new JSONObject();
				jo.put("info", ep);
				ja.add(jo);
			}
			json.put("errorPerson", ja.toString());
			logger.warn("检查人员信息耗时："+(System.currentTimeMillis()-start)/1000+" 秒");
			start = System.currentTimeMillis();
			// 部门信息
			List<String> errorDept = ((ISynchroOrg2WxworkCheck) getServiceImp(
					request)).checkDeptInfo(hiers);
			logger.warn("检查部门信息耗时："+(System.currentTimeMillis()-start)/1000+" 秒");
			ja = new JSONArray();
			for (String ed : errorDept) {
				jo = new JSONObject();
				jo.put("info", ed);
				ja.add(jo);
			}
			json.put("errorDept", ja.toString());

			json.put("suc", "1");
			json.put("msg", "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.put("suc", "0");
			json.put("msg", "检查出错，请联系管理员!");
		}
		// 记录日志信息
		if (UserOperHelper.allowLogOper("checkData", "*")) {
			UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil
					.getString(
							"third-weixin-work:third.weixin.work.config.setting"));
			UserOperHelper.logMessage(json.toString());
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(json.toString());
		return null;
	}

	public ActionForward check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("check", mapping, form, request, response);
		}
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
			WxOmsConfig wxOmsConfig = new WxOmsConfig();
			wxOmsConfig.setLastUpdateTime("");
			wxOmsConfig.save();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		// 记录日志信息
		if (UserOperHelper.allowLogOper("cleanTime", "*")) {
			UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil
					.getString(
							"third-weixin-work:third.weixin.work.config.setting"));
			UserOperHelper.logMessage(json.toString());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-cleanTime", false, getClass());
		return null;
	}

	public ActionForward cleanWxworkPerson(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-cleanTime", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
		   // WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
		    
			String ids = request.getParameter("ids");
			String names = request.getParameter("names");
			System.out.println("---------ids:" + ids);
			if(StringUtil.isNotNull(ids)){
				SynchroOrg2Wxwork synchroOrg2Wxwork = (SynchroOrg2Wxwork) SpringBeanUtil.getBean("wxworkSynchroOrg2WxTarget");
				String[] idsArr = ids.split(";");
				String[] namesArr = names.split(";");
				String errorInfo = "";
				String successInfo = "";
				for(int i=0;i<idsArr.length;i++){
					String rs = synchroOrg2Wxwork.deleteWxUserByEkpId(idsArr[i]);
					if("ok".equals(rs)){
						successInfo +=namesArr[i]+";";
					}else{
						errorInfo +="【"+namesArr[i]+"】"+rs+" \n ";
					}
				}
				
				if (StringUtil.isNotNull(errorInfo)) {
					json.put("status", 0);
					json.put("msg", "清理异常：\n" + errorInfo);
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		// 记录日志信息
		if (UserOperHelper.allowLogOper("cleanTime", "*")) {
			UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil
					.getString(
							"third-weixin-work:third.weixin.work.config.setting"));
			UserOperHelper.logMessage(json.toString());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-cleanTime", false, getClass());
		return null;
	}
}
