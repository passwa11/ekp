package com.landray.kmss.km.archives.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.archives.model.KmArchivesLibrary;
import com.landray.kmss.km.archives.service.IKmArchivesLibraryService;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.UserUtil;

public class KmArchivesLibraryServiceImp extends ExtendDataServiceImp
		implements IKmArchivesLibraryService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof KmArchivesLibrary) {
            KmArchivesLibrary kmArchivesLibrary = (KmArchivesLibrary) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        KmArchivesLibrary kmArchivesLibrary = new KmArchivesLibrary();
        kmArchivesLibrary.setDocCreateTime(new Date());
        kmArchivesLibrary.setDocCreator(UserUtil.getUser());
        KmArchivesUtil.initModelFromRequest(kmArchivesLibrary, requestContext);
        return kmArchivesLibrary;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        KmArchivesLibrary kmArchivesLibrary = (KmArchivesLibrary) model;
    }
}
