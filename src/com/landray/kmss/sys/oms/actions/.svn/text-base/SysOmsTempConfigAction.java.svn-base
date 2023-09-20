package com.landray.kmss.sys.oms.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.service.ISysLogJobService;
import com.landray.kmss.sys.oms.forms.SysOmsTempConfigForm;
import com.landray.kmss.sys.oms.model.SysOmsTempConfig;
import com.landray.kmss.sys.oms.service.ISysOmsTempConfigService;
import com.landray.kmss.sys.oms.temp.SysOmsJdbcConfig;
import com.landray.kmss.sys.quartz.scheduler.ISysQuartzJobExecutor;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class SysOmsTempConfigAction extends ExtendAction {

	private ISysOmsTempConfigService sysOmsTempConfigService;

	@Override
	public IBaseService getServiceImp(HttpServletRequest request) {
		if (sysOmsTempConfigService == null) {
			sysOmsTempConfigService = (ISysOmsTempConfigService) getBean("sysOmsTempConfigService");
		}
		return sysOmsTempConfigService;
	}

	@Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo, SysOmsTempConfig.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		com.landray.kmss.sys.oms.util.SysOmsUtil.buildHqlInfoDate(hqlInfo, request,
				com.landray.kmss.sys.oms.model.SysOmsTempConfig.class);
		com.landray.kmss.sys.oms.util.SysOmsUtil.buildHqlInfoModel(hqlInfo, request);
	}

	@Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		SysOmsTempConfigForm sysOmsTempConfigForm = (SysOmsTempConfigForm) super.createNewForm(mapping, form, request,
				response);
		((ISysOmsTempConfigService) getServiceImp(request)).initFormSetting((IExtendForm) form,
				new RequestContext(request));
		return sysOmsTempConfigForm;
	}

	protected ISysQuartzJobService sysQuartzJobService;

	protected ISysQuartzJobService getSysQuartzJobServiceImp() {
		if (sysQuartzJobService == null) {
            sysQuartzJobService = (ISysQuartzJobService) getBean("sysQuartzJobService");
        }
		return sysQuartzJobService;
	}

	protected ISysLogJobService sysLogJobService;

	protected ISysLogJobService getSysLogJobServiceImp() {
		if (sysLogJobService == null) {
            sysLogJobService = (ISysLogJobService) getBean("sysLogJobService");
        }
		return sysLogJobService;
	}

	public ActionForward cleanTime(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-cleanTime", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			HQLInfo hql = new HQLInfo();
			hql.setSelectBlock("fdId");
			hql.setWhereBlock("fdJobService=:fdJobService and fdJobMethod=:fdJobMethod");
			hql.setParameter("fdJobService", "synchroInService");
			hql.setParameter("fdJobMethod", "synchro");
			List<?> list = getSysQuartzJobServiceImp().findValue(hql);
			if (!ArrayUtil.isEmpty(list) && list.get(0) != null) {
				SysOmsJdbcConfig config = new SysOmsJdbcConfig();
				config.getDataMap().put("kmss.oms.temp.syn.synTimestamp", null);
				config.save();
				String fdId = list.get(0).toString();
				((ISysQuartzJobExecutor) getBean("sysQuartzJobExecutor")).execute(fdId);
			} else {
				json.put("status", 0);
				json.put("msg", "找不到对应的任务，无法执行全量同步，请先执行系统任务初始化！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-cleanTime", false, getClass());
		return null;
	}

}
