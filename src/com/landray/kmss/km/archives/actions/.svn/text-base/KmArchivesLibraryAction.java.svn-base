package com.landray.kmss.km.archives.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.archives.forms.KmArchivesLibraryForm;
import com.landray.kmss.km.archives.model.KmArchivesLibrary;
import com.landray.kmss.km.archives.service.IKmArchivesLibraryService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KmArchivesLibraryAction extends ExtendAction {

    private IKmArchivesLibraryService kmArchivesLibraryService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (kmArchivesLibraryService == null) {
            kmArchivesLibraryService = (IKmArchivesLibraryService) getBean("kmArchivesLibraryService");
        }
        return kmArchivesLibraryService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesLibrary.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmArchivesLibraryForm kmArchivesLibraryForm = (KmArchivesLibraryForm) super.createNewForm(mapping, form, request, response);
        ((IKmArchivesLibraryService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return kmArchivesLibraryForm;
    }

	/**
	 * 筛选项获得所有卷库
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward criteria(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			List<KmArchivesLibrary> list = getServiceImp(request)
					.findList(new HQLInfo());
			if (list != null) {
				for (KmArchivesLibrary kmArchivesLibrary : list) {
					JSONObject obj = new JSONObject();
					obj.put("text", kmArchivesLibrary.getFdName());
					obj.put("value", kmArchivesLibrary.getFdId());
					array.add(obj);
				}
			}
			request.setAttribute("lui-source", array);

		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}
}
