package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.forms.ThirdDingCategoryLogForm;
import com.landray.kmss.third.ding.model.ThirdDingCategoryLog;
import com.landray.kmss.third.ding.service.IThirdDingCategoryLogService;
import com.landray.kmss.third.ding.service.IThirdDingDtemplateXformService;
import com.landray.kmss.third.ding.service.spring.SynDingDirService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingCategoryLogAction extends ExtendAction {

    private IThirdDingCategoryLogService thirdDingCategoryLogService;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingCategoryLogAction.class);

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingCategoryLogService == null) {
            thirdDingCategoryLogService = (IThirdDingCategoryLogService) getBean("thirdDingCategoryLogService");
        }
        return thirdDingCategoryLogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingCategoryLog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingCategoryLog.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingCategoryLogForm thirdDingCategoryLogForm = (ThirdDingCategoryLogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingCategoryLogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingCategoryLogForm;
    }

	public void synCategoryFromDing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if(!UserUtil.getKMSSUser().isAdmin()){
			logger.warn("非超级管理员禁止访问！");
			return;
		}
		logger.warn("--------同步审批分类开始----------");
		logger.warn("dingAppKey :" + request.getParameter("dingAppKey")
				+ "  areaId:" + request.getParameter("areaId"));
		SynDingDirService.newInstance().synCategoryFromDing(
				request.getParameter("dingAppKey"),
				request.getParameter("areaId"));
		logger.warn("--------同步审批分类结束----------");
		// 更新流程模板映射关系
		logger.warn("准备更新流程模板映射关系...");
		IThirdDingDtemplateXformService thirdDingDtemplateXformService = (IThirdDingDtemplateXformService) SpringBeanUtil
				.getBean("thirdDingDtemplateXformService");
		thirdDingDtemplateXformService.updateTemplateInfo();
		logger.warn("----更新流程模板映射关系结束----");

	}

}
