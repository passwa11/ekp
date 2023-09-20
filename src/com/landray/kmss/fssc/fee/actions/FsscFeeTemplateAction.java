package com.landray.kmss.fssc.fee.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.fee.forms.FsscFeeTemplateForm;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.fssc.fee.service.IFsscFeeTemplateService;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class FsscFeeTemplateAction extends ExtendAction {

    private IFsscFeeTemplateService fsscFeeTemplateService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscFeeTemplateService == null) {
            fsscFeeTemplateService = (IFsscFeeTemplateService) getBean("fsscFeeTemplateService");
        }
        return fsscFeeTemplateService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscFeeTemplate.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        String where = hqlInfo.getWhereBlock();
        String parentId = request.getParameter("parentId");
        if(StringUtil.isNotNull(parentId)){
        	where = StringUtil.linkString(where, " and ", "fsscFeeTemplate.docCategory.fdId = :parentId");
        	hqlInfo.setParameter("parentId",parentId);
        }
        hqlInfo.setWhereBlock(where);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscFeeTemplateForm fsscFeeTemplateForm = (FsscFeeTemplateForm) super.createNewForm(mapping, form, request, response);
        ((IFsscFeeTemplateService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        String parentId=request.getParameter("parentId");
        if(StringUtil.isNotNull(parentId)){
            SysCategoryMain category=(SysCategoryMain) getServiceImp(request).findByPrimaryKey(parentId, SysCategoryMain.class, true);
            if(category!=null){
                fsscFeeTemplateForm.setDocCategoryId(category.getFdId());
                fsscFeeTemplateForm.setDocCategoryName(category.getFdName());
            }
        }
        return fsscFeeTemplateForm;
    }
}
