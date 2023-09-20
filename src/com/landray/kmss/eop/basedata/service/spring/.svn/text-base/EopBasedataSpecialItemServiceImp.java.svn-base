package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataSpecialItemForm;
import com.landray.kmss.eop.basedata.model.EopBasedataSpecialItem;
import com.landray.kmss.eop.basedata.service.IEopBasedataSpecialItemService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataSpecialItemServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataSpecialItemService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataSpecialItem) {
            EopBasedataSpecialItem eopBasedataSpecialItem = (EopBasedataSpecialItem) model;
            eopBasedataSpecialItem.setDocAlterTime(new Date());
            eopBasedataSpecialItem.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }
    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataSpecialItemForm mainForm = (EopBasedataSpecialItemForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}
    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataSpecialItem eopBasedataSpecialItem = new EopBasedataSpecialItem();
        eopBasedataSpecialItem.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataSpecialItem.setDocCreateTime(new Date());
        eopBasedataSpecialItem.setDocAlterTime(new Date());
        eopBasedataSpecialItem.setDocCreator(UserUtil.getUser());
        eopBasedataSpecialItem.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataSpecialItem, requestContext);
        return eopBasedataSpecialItem;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataSpecialItem eopBasedataSpecialItem = (EopBasedataSpecialItem) model;
    }
}
