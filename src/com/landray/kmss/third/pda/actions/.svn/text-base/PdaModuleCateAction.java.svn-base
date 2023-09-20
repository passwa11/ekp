package com.landray.kmss.third.pda.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.pda.model.PdaModuleCate;
import com.landray.kmss.third.pda.service.IPdaModuleCateService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author zhuhq
 */
public class PdaModuleCateAction extends ExtendAction{
	protected IPdaModuleCateService pdaModuleCateService=null;

	@Override
	protected IPdaModuleCateService getServiceImp(HttpServletRequest request) {
		if (pdaModuleCateService == null) {
            pdaModuleCateService = (IPdaModuleCateService) getBean("pdaModuleCateService");
        }
		return pdaModuleCateService;
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward =  super.list(mapping, form, request, response);
		String contentType = request.getParameter("contentType");
		if(!"failure".equals(forward.getName()) && "json".equals(contentType)){
			return getActionForward("data", mapping, form, request, response);
		}
		return forward;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, PdaModuleCate.class);
	}
	
	public ActionForward criteria(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.list(mapping, form, request, response);
		Page page = (Page) request.getAttribute("queryPage");
		JSONArray source = new JSONArray();
		for (Object object : page.getList()) {
			PdaModuleCate cate = (PdaModuleCate)object;
			JSONObject resObj = new JSONObject();
			resObj.put("value", cate.getFdId());
			resObj.put("text", cate.getFdName());
			source.add(resObj);
		}
		UserOperHelper.setOperSuccess(true);
		request.setAttribute("lui-source", source);
		return getActionForward("lui-source", mapping, form, request, response);
	}
	

}
