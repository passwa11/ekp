package com.landray.kmss.hr.ratify.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.ratify.forms.HrRatifyOverviewForm;
import com.landray.kmss.hr.ratify.model.HrRatifyOverview;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyOverviewService;
import com.landray.kmss.hr.ratify.service.IHrRatifyTemplateService;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrRatifyOverviewAction extends ExtendAction {

    private IHrRatifyOverviewService hrRatifyOverviewService;

	@Override
    public IHrRatifyOverviewService getServiceImp(HttpServletRequest request) {
        if (hrRatifyOverviewService == null) {
            hrRatifyOverviewService = (IHrRatifyOverviewService) getBean("hrRatifyOverviewService");
        }
        return hrRatifyOverviewService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrRatifyOverview.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.ratify.model.HrRatifyOverview.class);
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrRatifyOverviewForm hrRatifyOverviewForm = (HrRatifyOverviewForm) super.createNewForm(mapping, form, request, response);
        ((IHrRatifyOverviewService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return hrRatifyOverviewForm;
    }

	protected IHrRatifyTemplateService hrRatifyTemplateService;

	protected ISysCategoryMainService sysCategoryMainService;

	public IHrRatifyTemplateService getHrRatifyTemplateService() {
		if (hrRatifyTemplateService == null) {
			hrRatifyTemplateService = (IHrRatifyTemplateService) getBean(
					"hrRatifyTemplateService");
		}
		return hrRatifyTemplateService;
	}

	public ISysCategoryMainService getCategoryMainService() {
		if (sysCategoryMainService == null) {
			sysCategoryMainService = (ISysCategoryMainService) getBean(
					"sysCategoryMainService");
		}
		return sysCategoryMainService;
	}

	public ActionForward preview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String arrayString = getServiceImp(request)
					.getReviewPre();
			if (StringUtil.isNull(arrayString)) {
				arrayString = getServiceImp(request)
						.updateReview();
			}
			JSONArray array = new JSONArray();
			array = JSONArray.fromObject(arrayString);

			if (SysLangUtil.isLangEnabled()) {
				updateText(array);
			}

			request.setAttribute("lui-source", array);

		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-index", false, getClass());
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

	private void updateText(JSONArray array) throws Exception {
		if (array != null) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject o = (JSONObject) array.get(i);
				String id = o.getString("id");
				String nodeType = o.getString("nodeType");
				if ("TEMPLATE".equals(nodeType)) {
					HrRatifyTemplate temp = (HrRatifyTemplate) getHrRatifyTemplateService()
							.findByPrimaryKey(id, null, true);
					if (temp != null) {
						o.put("text", temp.getFdName());
					} else {
						array.remove(i);
					}
				} else if ("CATEGORY".equals(nodeType)) {
					SysCategoryMain category = (SysCategoryMain) getCategoryMainService()
							.findByPrimaryKey(id, null, true);
					if (category != null) {
						o.put("text", category.getFdName());
					}
					if (o.containsKey("children")) {
						try {
							updateText(o.getJSONArray("children"));
						} catch (net.sf.json.JSONException e) {

						}
					}
				}
			}
		}
	}
}
