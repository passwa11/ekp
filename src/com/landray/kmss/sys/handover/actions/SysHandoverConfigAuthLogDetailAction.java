package com.landray.kmss.sys.handover.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.handover.model.SysHandoverConfigAuthLogDetail;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigAuthLogDetailService;
import com.landray.kmss.sys.handover.service.spring.HandoverPluginUtils;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 文档权限交接日志明细
 * 
 * @author 潘永辉 2017年7月13日
 *
 */
public class SysHandoverConfigAuthLogDetailAction extends ExtendAction {
	protected ISysHandoverConfigAuthLogDetailService sysHandoverConfigAuthLogDetailService;

	@Override
	protected ISysHandoverConfigAuthLogDetailService getServiceImp(
			HttpServletRequest request) {
		if (sysHandoverConfigAuthLogDetailService == null) {
            sysHandoverConfigAuthLogDetailService = (ISysHandoverConfigAuthLogDetailService) getBean(
                    "sysHandoverConfigAuthLogDetailService");
        }
		return sysHandoverConfigAuthLogDetailService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		StringBuffer whereBlock = new StringBuffer();
		String _where = StringUtil.getString(hqlInfo.getWhereBlock());
		if (StringUtil.isNull(_where)) {
			whereBlock.append("1 = 1");
		} else {
			whereBlock.append(_where);
		}

		String fdFromId = cv.poll("fdFromId");
		if (StringUtil.isNotNull(fdFromId)) {
			whereBlock.append(" and fdMain.fdFromId = :fdFromId");
			hqlInfo.setParameter("fdFromId", fdFromId);
		}
		String fdToId = cv.poll("fdToId");
		if (StringUtil.isNotNull(fdToId)) {
			whereBlock.append(" and fdMain.fdToId = :fdToId");
			hqlInfo.setParameter("fdToId", fdToId);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		CriteriaUtil.buildHql(cv, hqlInfo, SysHandoverConfigAuthLogDetail.class);
	}

	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
			String curOrderBy) throws Exception {
		if (StringUtil.isNotNull(curOrderBy)) {
			return "sysHandoverConfigAuthLogDetail." + curOrderBy;
		}
		return super.getFindPageOrderBy(request, curOrderBy);
	}

	/**
	 * 获取模块清单
	 */
	public ActionForward getModules(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getModules", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			Map<String, String> map = HandoverPluginUtils.getAuthModel();
			for (String key : map.keySet()) {
				String value = map.get(key);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("text", ResourceUtil.getMessage("{" + value + "}"));
				jsonObj.put("value", value);
				array.add(jsonObj);
			}
			if (UserOperHelper.allowLogOper("getModules", null)) {
				UserOperHelper.setModelNameAndModelDesc(
						getServiceImp(request).getModelName());
				UserOperHelper.logMessage(array.toString());
				UserOperHelper.setOperSuccess(true);
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getModules", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

}
