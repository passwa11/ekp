package com.landray.kmss.sys.zone.actions;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.person.actions.PageQuery;
import com.landray.kmss.sys.person.interfaces.LinkType;
import com.landray.kmss.sys.person.service.plugin.LinksHelp;
import com.landray.kmss.sys.person.util.SelectPredicate;
import com.landray.kmss.sys.zone.util.SysZoneConfigUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;

public class SysZoneNavLinkAction extends BaseAction {

	public ActionForward dialog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("dialog");
	}

	private List<Object> searchText(List<Object> all, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String searchText = request.getParameter("q.key");
		if (StringUtil.isNotNull(searchText)) {
			searchText = URLDecoder.decode(searchText, "utf-8");
			// filter
			all = new SelectPredicate(searchText).from(all);
		}
		return all;
	}

	// ---------------- 个人空间导航 ----------------
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-select", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String showType = request.getParameter("showType");
			if (StringUtil.isNull(showType)) {
				showType = SysZoneConfigUtil.TYPE_PC_KEY;
			}
			List<Object> all = new ArrayList<Object>();
			all.addAll(LinksHelp.findByType(LinkType.ZONE_NAV, showType));
			all = searchText(all, request);
			PageQuery.findPage(request, all);
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-select", false, getClass());
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("select");
        }
	}
}
