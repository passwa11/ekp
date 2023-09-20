package com.landray.kmss.km.archives.service.spring;

import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.archives.model.KmArchivesDense;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.service.IKmArchivesDenseService;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.UserUtil;

public class KmArchivesDenseServiceImp extends ExtendDataServiceImp implements IKmArchivesDenseService {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmArchivesDenseServiceImp.class);
	
    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof KmArchivesDense) {
            KmArchivesDense kmArchivesDense = (KmArchivesDense) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        KmArchivesDense kmArchivesDense = new KmArchivesDense();
        kmArchivesDense.setDocCreateTime(new Date());
        kmArchivesDense.setDocCreator(UserUtil.getUser());
        KmArchivesUtil.initModelFromRequest(kmArchivesDense, requestContext);
        return kmArchivesDense;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        KmArchivesDense kmArchivesDense = (KmArchivesDense) model;
    }

	public static void main(String[] args) {
		KmArchivesMain model = new KmArchivesMain();
		try {
			Object obj = PropertyUtils.getProperty(model, "fdAaa");
			logger.info(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
