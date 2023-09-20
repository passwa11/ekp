package com.landray.kmss.third.weixin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.third.weixin.spi.model.WxOmsRelationModel;
import com.landray.kmss.third.weixin.spi.service.IWxOmsRelationService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class WxUserAction extends BaseAction {

	private IWxOmsRelationService wxOmsRelationService;

	protected IWxOmsRelationService getOmsRelationService() {
		if (wxOmsRelationService == null) {
			wxOmsRelationService = (IWxOmsRelationService) getBean("wxOmsRelationService");
		}
		return wxOmsRelationService;
	}

	@SuppressWarnings("unchecked")
	public ActionForward getUserId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("wxOmsRelationModel.fdEkpId=:fdEkpId");
				hqlInfo.setParameter("fdEkpId", fdId);
				List<WxOmsRelationModel> omsRelationModels = getOmsRelationService().findList(hqlInfo);
				if (omsRelationModels.size() > 0) {
					result.accumulate("userId", omsRelationModels.get(0).getFdAppPkId());
					if (UserOperHelper.allowLogOper("getUserId",
							getOmsRelationService().getModelName())) {
						UserOperContentHelper.putFinds(omsRelationModels);
						UserOperHelper.logMessage(result.toString());
					}
				}
			}
			request.setAttribute("lui-source", JSONObject.fromObject(result));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

}
