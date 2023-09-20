package com.landray.kmss.sys.mportal.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.component.bklink.util.CompBklinkUtil;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.mportal.model.SysMportalModuleCate;
import com.landray.kmss.sys.mportal.service.ISysMportalModuleCateService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysMportalModuleCateAction extends ExtendAction{

	protected ISysMportalModuleCateService sysMportalModuleCateService = null;

	@Override
	protected ISysMportalModuleCateService
			getServiceImp(HttpServletRequest request) {
		if (sysMportalModuleCateService == null) {
            sysMportalModuleCateService = (ISysMportalModuleCateService) getBean(
                    "sysMportalModuleCateService");
        }
		return sysMportalModuleCateService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysMportalModuleCate.class);
	}
	
	public ActionForward criteria(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.list(mapping, form, request, response);
		Page page = (Page) request.getAttribute("queryPage");
		JSONArray source = new JSONArray();
		for (Object object : page.getList()) {
			SysMportalModuleCate cate = (SysMportalModuleCate) object;
			JSONObject resObj = new JSONObject();
			resObj.put("value", cate.getFdId());
			resObj.put("text", cate.getFdName());
			source.add(resObj);
		}
		UserOperHelper.setOperSuccess(true);
		request.setAttribute("lui-source", source);
		return getActionForward("lui-source", mapping, form, request, response);
	}
	
	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		String[] ids = null;
		String forward = "failure";
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			ids = request.getParameterValues("List_Selected");

			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String queryString = "method=delete&fdId=${id}";
				String fdModelName = request.getParameter("fdModelName");
				if (fdModelName != null && !"".equals(fdModelName)) {
					queryString += "&fdModelName=" + fdModelName;
				}
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, queryString);
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
                    getServiceImp(request).delete(authIds);
                }
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
			}

		} catch (Exception e) {
			messages.addError(e);
			String forwardTemp = CompBklinkUtil.isDeleteConstraintException(e);
			String tempIds = CompBklinkUtil.getIds(ids);
			String httpURL = request.getRequestURI();
			request.setAttribute("httpURL", httpURL);
			request.setAttribute("ids", tempIds);
			request.setAttribute("modelName",
					"com.landray.kmss.sys.mportal.model.SysMportalModuleCate");
			if (StringUtil.isNotNull(forwardTemp)) {
				forward = forwardTemp;
			}
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward(forward, mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

}
