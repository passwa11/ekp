package com.landray.kmss.sys.filestore.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.actions.BaseAction;

import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.design.SysCfgModuleInfo;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.filestore.forms.SysFileInQueueForm;
import com.landray.kmss.sys.filestore.model.InqueueModuleScope;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.comparator.ChinesePinyinComparator;

public class SysFileInQueueAction extends BaseAction {

	private ISysFileConvertDataService dataService = null;

	protected ISysFileConvertDataService getDataService() {
		if (dataService == null) {
			dataService = (ISysFileConvertDataService) SpringBeanUtil.getBean("sysFileConvertDataService");
		}
		return dataService;
	}

	public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-select", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			List<Map<String, String>> moduleList = new ArrayList<Map<String, String>>();
			List<?> moduleInfoList = SysConfigs.getInstance().getModuleInfoList();
			for (int i = 0; i < moduleInfoList.size(); i++) {
				SysCfgModuleInfo sysCfgModuleInfo = (SysCfgModuleInfo) moduleInfoList.get(i);
				Map<String, String> map = new HashMap<String, String>();
				map.put("urlPrefix", sysCfgModuleInfo.getUrlPrefix());
				String messageKey = sysCfgModuleInfo.getMessageKey();
				if (StringUtil.isNotNull(messageKey) && StringUtil.isNotNull(ResourceUtil.getString(messageKey))) {
					map.put("name", ResourceUtil.getString(messageKey));
					moduleList.add(map);
				}
			}
			Collections.sort(moduleList, ModuleComparator.INSTANCE);

			request.setAttribute("moduleList", moduleList);

		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-select", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("select");
		}
	}

	public ActionForward check(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-check", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			check(form, request);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-select", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}

	private void check(ActionForm form, HttpServletRequest request) throws Exception {
		SysFileInQueueForm inQueueForm = (SysFileInQueueForm) form;
		InqueueModuleScope scope = null;
		if ("1".equals(inQueueForm.getFdInqueueType())) {
			scope = new InqueueModuleScope(true);

		} else {
			String[] fdScopes = inQueueForm.getFdScope();
			Set<String> scopes = new HashSet<String>();
			if (fdScopes != null) {
				for (String scopeItem : fdScopes) {
					scopes.add(scopeItem);
				}
			}
			scope = new InqueueModuleScope(scopes);
		}
		oldAttInQueue(scope);
	}

	private void oldAttInQueue(InqueueModuleScope scope) throws Exception {
		if (scope.isAll()) {
			getDataService().newRuleToQueue("");
		} else {
			for (String model : scope.getFdModules()) {
				getDataService().newRuleToQueue(model);
			}
		}
	}
}

class ModuleComparator implements Comparator<Map<String, String>> {

	public static final Comparator<Map<String, String>> INSTANCE = new ModuleComparator();

	@Override
    public int compare(Map<String, String> s1, Map<String, String> s2) {
		if (s1 == null || s2 == null) {
			return 0;
		}
		if (s1.containsKey("name") && s2.containsKey("name")) {
			return ChinesePinyinComparator.compare(s1.get("name"), s2.get("name"));
		}
		if (s1.containsKey("value") && s2.containsKey("value")) {
			return ChinesePinyinComparator.compare(s1.get("value"), s2.get("value"));
		}
		return 0;
	}
}
