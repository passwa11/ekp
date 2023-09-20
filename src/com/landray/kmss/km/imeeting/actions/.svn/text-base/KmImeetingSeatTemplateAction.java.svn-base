package com.landray.kmss.km.imeeting.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.imeeting.model.KmImeetingSeatTemplate;
import com.landray.kmss.km.imeeting.service.IKmImeetingSeatTemplateService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class KmImeetingSeatTemplateAction extends ExtendAction {

	private IKmImeetingSeatTemplateService kmImeetingSeatTemplateService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmImeetingSeatTemplateService == null) {
			kmImeetingSeatTemplateService = (IKmImeetingSeatTemplateService) getBean(
					"kmImeetingSeatTemplateService");
		}
		return kmImeetingSeatTemplateService;
	}
	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isNull(whereBlock)) {
			sb.append(" 1=1 ");
		}
		String fdName = request.getParameter("q.fdName");
		if (!StringUtil.isNull(fdName)) {
			sb.append(" and kmImeetingSeatTemplate.fdName like :fdName");
			hqlInfo.setParameter("fdName", String.format("%%%s%%", fdName));
		}
		hqlInfo.setWhereBlock(sb.toString());
	}

	/**
	 * 获取坐席明细
	 */
	public ActionForward getSeatDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject rtnObj = new JSONObject();
		try {
			String fdSeatTemplateId = request.getParameter("fdSeatTemplateId");
			if (StringUtil.isNotNull(fdSeatTemplateId)) {
				KmImeetingSeatTemplate seatTemplate = (KmImeetingSeatTemplate) getServiceImp(
						request).findByPrimaryKey(fdSeatTemplateId);
				if (seatTemplate != null) {
					rtnObj.put("seatCount", seatTemplate.getFdSeatCount());
					rtnObj.put("seatDetail", seatTemplate.getFdSeatDetail());
					rtnObj.put("fdCols", seatTemplate.getFdCols());
					rtnObj.put("fdRows", seatTemplate.getFdRows());
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		request.setAttribute("lui-source", rtnObj);
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

	/**
	 * 另存为模版
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdName = request.getParameter("fdName");
			String fdSeatCount = request.getParameter("fdSeatCount");
			String fdSeatDetail = request.getParameter("fdSeatDetail");
			String fdCols = request.getParameter("fdCols");
			String fdRows = request.getParameter("fdRows");
			KmImeetingSeatTemplate template = new KmImeetingSeatTemplate();
			template.setFdName(fdName);
			template.setFdSeatCount(fdSeatCount);
			template.setFdSeatDetail(fdSeatDetail);
			template.setFdCols(fdCols);
			template.setFdRows(fdRows);
			getServiceImp(request).add(template);
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

}
