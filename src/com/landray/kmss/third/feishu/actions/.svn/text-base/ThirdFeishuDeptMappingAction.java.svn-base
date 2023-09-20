package com.landray.kmss.third.feishu.actions;

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
import com.landray.kmss.third.feishu.forms.ThirdFeishuDeptMappingForm;
import com.landray.kmss.third.feishu.model.ThirdFeishuDeptMapping;
import com.landray.kmss.third.feishu.service.IThirdFeishuDeptMappingService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class ThirdFeishuDeptMappingAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuDeptMappingAction.class);

    private IThirdFeishuDeptMappingService thirdFeishuDeptMappingService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdFeishuDeptMappingService == null) {
            thirdFeishuDeptMappingService = (IThirdFeishuDeptMappingService) getBean("thirdFeishuDeptMappingService");
        }
        return thirdFeishuDeptMappingService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdFeishuDeptMapping.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.feishu.util.ThirdFeishuUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.feishu.model.ThirdFeishuDeptMapping.class);
        com.landray.kmss.third.feishu.util.ThirdFeishuUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdFeishuDeptMappingForm thirdFeishuDeptMappingForm = (ThirdFeishuDeptMappingForm) super.createNewForm(mapping, form, request, response);
        ((IThirdFeishuDeptMappingService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdFeishuDeptMappingForm;
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
				String sql = "delete from ThirdFeishuDeptNoMapping";

				getServiceImp(request).getBaseDao().getHibernateSession()
						.createQuery(sql).executeUpdate();
				getServiceImp(request).getBaseDao().getHibernateSession()
						.flush();
				getServiceImp(request).getBaseDao().getHibernateSession()
						.clear();
				// 数据匹配
				((IThirdFeishuDeptMappingService) getServiceImp(request))
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
