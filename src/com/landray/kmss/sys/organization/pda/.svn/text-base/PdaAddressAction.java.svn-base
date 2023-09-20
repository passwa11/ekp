package com.landray.kmss.sys.organization.pda;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PdaAddressAction extends BaseAction {
	private IPdaAddressService pdaAddressService = null;

	public IPdaAddressService getPdaAddressService() {
		if (pdaAddressService == null) {
            pdaAddressService = (IPdaAddressService) SpringBeanUtil
                    .getBean("pdaAddressService");
        }
		return pdaAddressService;
	}

	// 进入地址本
	public ActionForward into(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(!access(request)) {
            return mapping.findForward("e404");
        }
		IPdaAddressService addressService = getPdaAddressService();
		String orgType = request.getParameter("fd_orgtype");
		String currentOrg = null;
		
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if (("fd_current"+getClassKey()).equalsIgnoreCase(cookies[i].getName())) {
				currentOrg = cookies[i].getValue();
				break;
			}
		}

		if (StringUtil.isNull(currentOrg)) {
			SysOrgPerson curPerson=UserUtil.getKMSSUser(request).getPerson();
			if(curPerson.getFdParent()!=null) {
                currentOrg = curPerson.getFdParent().getFdId();
            }
		}

		request.setAttribute("orgList", addressService.getDataList(
				new RequestContext(request), currentOrg));
		request.setAttribute("parentList", addressService.getParentList(
				new RequestContext(request), currentOrg));
		request.setAttribute("fd_orgtype", orgType);
		request.setAttribute("fd_current", currentOrg);
		if(StringUtil.isNotNull(currentOrg)){
			Cookie tmpCookie = new Cookie("fd_current" + getClassKey(), currentOrg);
			tmpCookie.setPath("/");
			tmpCookie.setMaxAge(3 * 24 * 60 * 60);
			response.addCookie(tmpCookie);
		}
		return mapping.findForward("address");
	}

	protected String getClassKey(){
		return this.getClass().getSimpleName();
	}
	
	protected boolean getCookieForce(){
		return true;
	}
	
	// 地址本搜索
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(!access(request)) {
            return mapping.findForward("e404");
        }
		IPdaAddressService addressService = getPdaAddressService();
		String orgType = request.getParameter("fd_orgtype");
		String search = request.getParameter("fd_keyword");
		List list = addressService.getSearchList(new RequestContext(request));
		if (list!=null && list.size() == IPdaAddressService.LIMIT_SEARCH_RESULT_SIZE + 1) {
            request.setAttribute("limit", "1");
        }
		request.setAttribute("searchList", list);
		request.setAttribute("fd_orgtype", orgType);
		request.setAttribute("fd_keyword", search);
		return mapping.findForward("search");
	}
	
	private boolean access(HttpServletRequest request){
		//旧版直接返回true (企业号首页访问通讯录无法获取该标识S_PADFlag)
		return true;
		//return "1".equals((String)request.getSession().getAttribute("S_PADFlag"));
	}
}
