package com.landray.kmss.sys.mportal.actions;

import java.net.URLDecoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.mportal.plugin.MportalMportletUtil;
import com.landray.kmss.sys.mportal.xml.SysMportalMportlet;
import com.landray.kmss.sys.person.actions.PageQuery;
import com.landray.kmss.sys.person.util.SelectPredicate;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;

/**
 * 获取配置移动门户配置
 * 
 */
public class SysMportalMportletAction extends BaseAction {

	/**
	 * 获取配置的分页数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-select", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String module = request.getParameter("q.module");
			List<SysMportalMportlet> list = MportalMportletUtil
					.getMportletListByModule(module);
			Collections.sort(list, new Comparator<SysMportalMportlet>() {
				@Override
				public int compare(SysMportalMportlet k1, SysMportalMportlet k2) {
					if (k1.getFdModule().compareTo(k2.getFdModule()) > 0) {
						return -1;
					} else {
						return 1;
					}
				}
			});
			String key = request.getParameter("q.key");
			if(StringUtil.isNotNull(key)) {
				String searchText = URLDecoder.decode(key, "utf-8");
				list = new SelectPredicate(searchText).from(list);
			}
			PageQuery.findPage(request, list);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-select", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("select");
		}
	}

}
