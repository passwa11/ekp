package com.landray.kmss.third.ding.action;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DingUserAction extends BaseAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingUserAction.class);

	private IOmsRelationService omsRelationService;

	protected IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			omsRelationService = (IOmsRelationService) getBean("omsRelationService");
		}
		return omsRelationService;
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
				hqlInfo.setSelectBlock("fdAppPkId");
				hqlInfo.setWhereBlock("omsRelationModel.fdEkpId=:fdEkpId");
				hqlInfo.setParameter("fdEkpId", fdId);
				String fdAppPkId = (String) getOmsRelationService().findFirstOne(hqlInfo);
				if (StringUtils.isNotBlank(fdAppPkId)) {
					result.accumulate("userId", fdAppPkId);
				}
			}
			if (UserOperHelper.allowLogOper("getUserId", "*")) {
				UserOperHelper.logMessage(result.toString());
			}
			result.accumulate("corpId", DingUtil.getCorpId());
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

	/**
	 * <p>根据ekp用户id获取钉钉的用户id</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 孙佳
	 */
	public void getDingUserId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		List<String> dingUserId = new ArrayList<String>();
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("omsRelationModel.fdEkpId", Arrays.asList(fdId.split(";"))));
				List<OmsRelationModel> omsRelationModels = getOmsRelationService().findList(hqlInfo);
				if (omsRelationModels.size() > 0) {
					for (OmsRelationModel omsRelationModel : omsRelationModels) {
						dingUserId.add(omsRelationModel.getFdAppPkId());
					}
				}
			}
			json.put("dingUserId", dingUserId.toArray(new String[0]));
			response.setCharacterEncoding("UTF-8");
			if (UserOperHelper.allowLogOper("getDingUserId", "*")) {
				UserOperHelper.logMessage(json.toString());
			}
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("",e);
		}
	}

}
