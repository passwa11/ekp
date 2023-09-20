package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataSupTypeForm;
import com.landray.kmss.eop.basedata.model.EopBasedataSupType;
import com.landray.kmss.eop.basedata.service.IEopBasedataSupTypeService;
import com.landray.kmss.eop.basedata.util.EopBasedataConstant;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangwh
 * @description:供应商类别action
 * @date 2021/5/7
 */
public class EopBasedataSupTypeAction extends ExtendAction {

	private IEopBasedataSupTypeService eopBasedataSupTypeService;

	@Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (eopBasedataSupTypeService == null) {
			eopBasedataSupTypeService = (IEopBasedataSupTypeService) getBean("eopBasedataSupTypeService");
		}
		return eopBasedataSupTypeService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataSupType.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, EopBasedataSupType.class);
		EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
	}

	@Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
		EopBasedataSupTypeForm eopBasedataSupTypeForm = (EopBasedataSupTypeForm) super.createNewForm(mapping, form,
				request, response);
		((IEopBasedataSupTypeService) getServiceImp(request)).initFormSetting((IExtendForm) form,
				new RequestContext(request));
		return eopBasedataSupTypeForm;
	}

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("EopBasedataSupTypeAction-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!EopBasedataConstant.REQUEST_METHOD_POST.equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");
			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String queryString = "method=delete&fdId=${id}";
				String fdModelName = request.getParameter("fdModelName");
				if(fdModelName != null && !"".equals(fdModelName))
				{
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
			if (e instanceof DataIntegrityViolationException || e instanceof ConstraintViolationException) {
				messages.addError(new KmssMessage("eop-basedata:error.delete.1"),e);
			}else {
				messages.addError(new KmssMessage("eop-basedata:error.delete.0"),e);
			}
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("EopBasedataSupTypeAction-deleteall", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		}else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("EopBasedataSupTypeAction-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!EopBasedataConstant.REQUEST_METHOD_GET.equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).delete(id);
			}
		}catch (DataIntegrityViolationException e) {
			messages.addError(new KmssMessage("eop-basedata:error.delete.2"));
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		TimeCounter.logCurrentTime("EopBasedataSupTypeAction-delete", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public void checkUnique(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");
		String fdName = request.getParameter("fdName");

		boolean check = IEopBasedataSupTypeService.class.cast(getServiceImp(request)).checkUnique(fdId, fdName);
		JSONObject json = new JSONObject();
		json.put("check", check);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
