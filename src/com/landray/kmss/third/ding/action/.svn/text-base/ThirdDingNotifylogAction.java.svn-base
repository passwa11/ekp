package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.forms.ThirdDingNotifylogForm;
import com.landray.kmss.third.ding.model.ThirdDingNotifylog;
import com.landray.kmss.third.ding.service.IThirdDingNotifylogService;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class ThirdDingNotifylogAction extends ExtendAction {

    private IThirdDingNotifylogService thirdDingNotifylogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingNotifylogService == null) {
            thirdDingNotifylogService = (IThirdDingNotifylogService) getBean("thirdDingNotifylogService");
        }
        return thirdDingNotifylogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();

        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingNotifylog.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));

		CriteriaValue cv = new CriteriaValue(request);
		String fdSearchName = cv.poll("fdSearchName");
		if (StringUtil.isNotNull(fdSearchName)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", "thirdDingNotifylog.docSubject like :docSubject");
			hqlInfo.setParameter("docSubject", "%" + fdSearchName + "%");
			hqlInfo.setWhereBlock(whereBlock);
		}

    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingNotifylogForm thirdDingNotifylogForm = (ThirdDingNotifylogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingNotifylogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingNotifylogForm;
    }
}
