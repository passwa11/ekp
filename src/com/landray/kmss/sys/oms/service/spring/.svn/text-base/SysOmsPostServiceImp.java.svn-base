package com.landray.kmss.sys.oms.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.oms.dao.ISysOmsPostDao;
import com.landray.kmss.sys.oms.model.SysOmsPost;
import com.landray.kmss.sys.oms.service.ISysOmsPostService;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOmsPostServiceImp extends ExtendDataServiceImp implements ISysOmsPostService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysOmsPost) {
            SysOmsPost sysOmsPost = (SysOmsPost) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysOmsPost sysOmsPost = new SysOmsPost();
        sysOmsPost.setFdIsAvailable(Boolean.valueOf("true"));
        sysOmsPost.setFdIsBusiness(Boolean.valueOf("true"));
        SysOmsUtil.initModelFromRequest(sysOmsPost, requestContext);
        return sysOmsPost;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysOmsPost sysOmsPost = (SysOmsPost) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void deleteHandledOrg() throws Exception {
		((ISysOmsPostDao) getBaseDao()).deleteHandledOrg();
	}
}
