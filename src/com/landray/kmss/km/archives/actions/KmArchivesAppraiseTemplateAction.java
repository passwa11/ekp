package com.landray.kmss.km.archives.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.archives.forms.KmArchivesAppraiseTemplateForm;
import com.landray.kmss.km.archives.model.KmArchivesAppraiseTemplate;
import com.landray.kmss.km.archives.service.IKmArchivesAppraiseTemplateService;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class KmArchivesAppraiseTemplateAction extends ExtendAction {

	private IKmArchivesAppraiseTemplateService kmArchivesAppraiseTemplateService;

    @Override
	public IBaseService getServiceImp(HttpServletRequest request) {
		if (kmArchivesAppraiseTemplateService == null) {
			kmArchivesAppraiseTemplateService = (IKmArchivesAppraiseTemplateService) getBean(
					"kmArchivesAppraiseTemplateService");
        }
		return kmArchivesAppraiseTemplateService;
    }

    @Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo,
				KmArchivesAppraiseTemplate.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		KmArchivesAppraiseTemplateForm kmArchivesAppraiseTemplateForm = (KmArchivesAppraiseTemplateForm) super.createNewForm(
				mapping, form, request, response);
		((IKmArchivesAppraiseTemplateService) getServiceImp(request))
				.initFormSetting((IExtendForm) form,
						new RequestContext(request));
		return kmArchivesAppraiseTemplateForm;
    }

	/**
	 * 获取所有借用流程模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTemplete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONArray rtnArray = new JSONArray();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
			List<KmArchivesAppraiseTemplate> kmArchivesAppraiseTemplateList = getServiceImp(
					request).findList(hqlInfo);
			for (int i = 0; i < kmArchivesAppraiseTemplateList.size(); i++) {
				KmArchivesAppraiseTemplate kmArchivesAppraiseTemplate = kmArchivesAppraiseTemplateList
						.get(i);
				JSONObject json = new JSONObject();
				json.put("fdId", kmArchivesAppraiseTemplate.getFdId());
				json.put("fdName", kmArchivesAppraiseTemplate.getFdName());
				rtnArray.add(json);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(rtnArray.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * 筛选项获得所有借阅申请模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward criteria(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			List<KmArchivesAppraiseTemplate> list = getServiceImp(request)
					.findList(new HQLInfo());
			if (list != null) {
				for (KmArchivesAppraiseTemplate kmArchivesAppraiseTemplate : list) {
					JSONObject obj = new JSONObject();
					obj.put("text", kmArchivesAppraiseTemplate.getFdName());
					obj.put("value", kmArchivesAppraiseTemplate.getFdId());
					array.add(obj);
				}
			}
			request.setAttribute("lui-source", array);

		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, "method=delete&fdId=${id}");
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
			messages.setHasError();
			messages.addMsg(new KmssMessage(
					"km-archives:kmArchivesTemplate.deleteall.tip"));
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("success");
        }
	}
}
