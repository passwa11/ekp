package com.landray.kmss.sys.zone.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysZoneMportalAction extends BaseAction {

	public ActionForward loadPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-loadPerson", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			JSONArray array = new JSONArray();
			ISysOrgPersonService service = (ISysOrgPersonService) this
					.getBean("sysOrgPersonService");
			if (service != null) {

				String fdIds = request.getParameter("fdIds");

				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("sysOrgPerson.fdIsAvailable=:fdIsAvailable and  sysOrgPerson.fdIsBusiness=:fdIsBusiness");
				hqlInfo.setParameter("fdIsAvailable", true);
				hqlInfo.setParameter("fdIsBusiness", true);
				hqlInfo.setPageNo(1);

				if (StringUtil.isNotNull(fdIds)) {
					hqlInfo.setWhereBlock(StringUtil.linkString(
							hqlInfo.getWhereBlock(), " and ",
							"sysOrgPerson.fdId in (:fdIds) "));
					hqlInfo.setParameter("fdIds",
							ArrayUtil.convertArrayToList(fdIds.split(";")));
					hqlInfo.setRowSize(ArrayUtil
							.convertArrayToList(fdIds.split(";")).size());
				} else {
					hqlInfo.setRowSize(10);
				}

				String driverClass = ResourceUtil
						.getKmssConfigString("hibernate.connection.driverClass");
				if ("com.mysql.jdbc.Driver".equals(driverClass)) {
					hqlInfo.setOrderBy(" rand()");
				} else if ("oracle.jdbc.driver.OracleDriver"
						.equals(driverClass)) {
					hqlInfo.setOrderBy(" dbms_random.value()");
				} else if ("net.sourceforge.jtds.jdbc.Driver"
						.equals(driverClass)) {
					hqlInfo.setOrderBy("newid()");
				}
				List<SysOrgPerson> list = service.findPage(hqlInfo).getList();
				for (SysOrgPerson person : list) {
					JSONObject json = new JSONObject();
					String fdId = person.getFdId();
					json.put("id", fdId);
					json.put("fdSex", person.getFdSex());
					json.put("text", person.getFdName());
					json.put("href",
							"/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId="
									+ fdId);
					json.put("icon", PersonInfoServiceGetter
							.getPersonHeadimageUrl(fdId, "m"));
					array.add(json);
				}
				request.setAttribute("lui-source", array);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-loadPerson", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}
}
