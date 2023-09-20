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
import com.landray.kmss.third.feishu.forms.ThirdFeishuPersonMappingForm;
import com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping;
import com.landray.kmss.third.feishu.service.IThirdFeishuPersonMappingService;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdFeishuPersonMappingAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuPersonMappingAction.class);

    private IThirdFeishuPersonMappingService thirdFeishuPersonMappingService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdFeishuPersonMappingService == null) {
            thirdFeishuPersonMappingService = (IThirdFeishuPersonMappingService) getBean("thirdFeishuPersonMappingService");
        }
        return thirdFeishuPersonMappingService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdFeishuPersonMapping.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.feishu.util.ThirdFeishuUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping.class);
        com.landray.kmss.third.feishu.util.ThirdFeishuUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdFeishuPersonMappingForm thirdFeishuPersonMappingForm = (ThirdFeishuPersonMappingForm) super.createNewForm(mapping, form, request, response);
        ((IThirdFeishuPersonMappingService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdFeishuPersonMappingForm;
    }

	public ActionForward deleteFeishuOrg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteFeishuOrg", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			IThirdFeishuService thirdFeishuService = (IThirdFeishuService) SpringBeanUtil
					.getBean("thirdFeishuService");

			JSONArray users = thirdFeishuService.getUsers("0", true);
			for (int i = 0; i < users.size(); i++) {
				JSONObject o = users.getJSONObject(i);
				String employee_id = o.getString("open_id");
				logger.warn("删除用户：" + o.toString());
				try {
					thirdFeishuService.delPerson(employee_id);
				} catch (Exception e) {
					logger.error("", e);
					messages.addError(e);
				}
			}
			JSONArray depts = thirdFeishuService.getDepts("0", true);
			for (int i = depts.size() - 1; i >= 0; i--) {
				JSONObject o = depts.getJSONObject(i);
				String id = o.getString("open_department_id");
				if ("0".equals(id)) {
					logger.error("不能删除根部门");
					continue;
				}
				logger.warn("删除部门：" + o.toString());
				try {
					thirdFeishuService.delDept(id);
				} catch (Exception e) {
					logger.error("", e);
					messages.addError(e);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-deleteFeishuOrg", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
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
				String sql = "delete from ThirdFeishuPersonNoMapp";

				getServiceImp(request).getBaseDao().getHibernateSession()
						.createQuery(sql).executeUpdate();
				getServiceImp(request).getBaseDao().getHibernateSession()
						.flush();
				getServiceImp(request).getBaseDao().getHibernateSession()
						.clear();
				// 数据匹配
				((IThirdFeishuPersonMappingService) getServiceImp(request))
						.updatePerson(json);

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
