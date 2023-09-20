package com.landray.kmss.third.ding.oms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class DingSynchroOrgDing2EkpAction extends BaseAction {

	protected SynchroOrgDing2Ekp synchroOrgDing2Ekp;

	public SynchroOrgDing2Ekp getSynchroOrgDing2Ekp() {
		if (synchroOrgDing2Ekp == null) {
			synchroOrgDing2Ekp = (SynchroOrgDing2Ekp) getBean("synchroOrgDing2Ekp");
		}
		return synchroOrgDing2Ekp;
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingSynchroOrgDing2EkpAction.class);

	public ActionForward generateMapping(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-generateMapping", true, getClass());
		SynchroOrgDing2Ekp synchroOrgDing2Ekp = getSynchroOrgDing2Ekp();
		try {
			synchroOrgDing2Ekp.generateMapping();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("success");
			TimeCounter.logCurrentTime("Action-generateMapping", true,
					getClass());
		} catch (Exception e) {
			logger.error("建立映射关系过程出错:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
