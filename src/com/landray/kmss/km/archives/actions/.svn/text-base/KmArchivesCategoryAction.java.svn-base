package com.landray.kmss.km.archives.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.archives.forms.KmArchivesCategoryForm;
import com.landray.kmss.km.archives.model.KmArchivesCategory;
import com.landray.kmss.km.archives.service.IKmArchivesCategoryService;
import com.landray.kmss.sys.property.model.SysPropertyReference;
import com.landray.kmss.sys.property.model.SysPropertyTemplate;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.sso.client.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KmArchivesCategoryAction extends SysSimpleCategoryAction {

    private IKmArchivesCategoryService kmArchivesCategoryService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (kmArchivesCategoryService == null) {
            kmArchivesCategoryService = (IKmArchivesCategoryService) getBean("kmArchivesCategoryService");
        }
        return kmArchivesCategoryService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesCategory.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmArchivesCategoryForm kmArchivesCategoryForm = (KmArchivesCategoryForm) super.createNewForm(mapping, form, request, response);
        ((IKmArchivesCategoryService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return kmArchivesCategoryForm;
    }

	/**
	 * 获得分类的扩展数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExtendData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String categoryId = request.getParameter("fdId");
			if (StringUtil.isNull(categoryId)) {
				throw new IllegalArgumentException();
			}
			KmArchivesCategory category = (KmArchivesCategory)getServiceImp(request).findByPrimaryKey(categoryId);
			SysPropertyTemplate sp = category.getSysPropertyTemplate();
			JSONArray jsonArray = new JSONArray();
			if (sp != null && sp.getFdReferences() != null) {
				List<SysPropertyReference> references = sp.getFdReferences();
				for (SysPropertyReference sysPropertyReference : references) {
					JSONObject obj = new JSONObject();
					obj.put("fdField", sysPropertyReference.getFdDefine()
							.getFdStructureName());
					obj.put("fdDisplayName",
							sysPropertyReference.getFdDisplayName());
					obj.put("notNull", sysPropertyReference.getFdIsNotNull());
					obj.put("fdType",
							sysPropertyReference.getFdDefine().getFdType());
					jsonArray.add(obj);
				}
			}
			request.setAttribute("lui-source", jsonArray);
		} catch (Exception e) {
			messages.addError(e);
		}
		
		if(!messages.hasError()) {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
		return null;
	}
}
