package com.landray.kmss.third.welink.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.third.welink.forms.ThirdWelinkDeptMappingForm;
import com.landray.kmss.third.welink.model.ThirdWelinkDeptMapping;
import com.landray.kmss.third.welink.service.IThirdWelinkDeptMappingService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class ThirdWelinkDeptMappingAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWelinkDeptMappingAction.class);

    private IThirdWelinkDeptMappingService thirdWelinkDeptMappingService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdWelinkDeptMappingService == null) {
            thirdWelinkDeptMappingService = (IThirdWelinkDeptMappingService) getBean("thirdWelinkDeptMappingService");
        }
        return thirdWelinkDeptMappingService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdWelinkDeptMapping.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.welink.util.ThirdWelinkUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.welink.model.ThirdWelinkDeptMapping.class);
        com.landray.kmss.third.welink.util.ThirdWelinkUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdWelinkDeptMappingForm thirdWelinkDeptMappingForm = (ThirdWelinkDeptMappingForm) super.createNewForm(mapping, form, request, response);
        ((IThirdWelinkDeptMappingService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdWelinkDeptMappingForm;
    }

	private static Object lock = new Object();

	public ActionForward omsInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-omsInit", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			synchronized (lock) {
				// 未匹配数据清除
				String sql = "delete from ThirdWelinkDeptNoMapping";

				getServiceImp(request).getBaseDao().getHibernateSession()
						.createQuery(sql).executeUpdate();
				getServiceImp(request).getBaseDao().getHibernateSession()
						.flush();
				getServiceImp(request).getBaseDao().getHibernateSession()
						.clear();
				// 数据匹配
				((IThirdWelinkDeptMappingService) getServiceImp(request))
						.updateDept(json);

			}
		} catch (Exception e) {
			logger.error("初始化映射失败", e);
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-omsInit", false, getClass());
		return null;
	}

}
