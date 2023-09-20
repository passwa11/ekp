package com.landray.kmss.km.archives.service.spring;

import java.util.Date;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.km.archives.service.IKmArchivesUnitService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.archives.model.KmArchivesUnit;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;

public class KmArchivesUnitServiceImp extends ExtendDataServiceImp implements IKmArchivesUnitService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof KmArchivesUnit) {
            KmArchivesUnit kmArchivesUnit = (KmArchivesUnit) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        KmArchivesUnit kmArchivesUnit = new KmArchivesUnit();
        kmArchivesUnit.setDocCreateTime(new Date());
        kmArchivesUnit.setDocCreator(UserUtil.getUser());
        KmArchivesUtil.initModelFromRequest(kmArchivesUnit, requestContext);
        return kmArchivesUnit;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        KmArchivesUnit kmArchivesUnit = (KmArchivesUnit) model;
    }
}
