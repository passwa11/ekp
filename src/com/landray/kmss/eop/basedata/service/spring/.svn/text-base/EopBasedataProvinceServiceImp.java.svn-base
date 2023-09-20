package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.service.IEopBasedataProvinceService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import java.util.List;
import com.landray.kmss.eop.basedata.model.EopBasedataProvince;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.eop.basedata.model.EopBasedataCountry;

public class EopBasedataProvinceServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataProvinceService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataProvince) {
            EopBasedataProvince eopBasedataProvince = (EopBasedataProvince) model;
            eopBasedataProvince.setDocAlterTime(new Date());
            eopBasedataProvince.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataProvince eopBasedataProvince = new EopBasedataProvince();
        eopBasedataProvince.setDocCreateTime(new Date());
        eopBasedataProvince.setDocAlterTime(new Date());
        eopBasedataProvince.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataProvince.setDocCreator(UserUtil.getUser());
        eopBasedataProvince.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataProvince, requestContext);
        return eopBasedataProvince;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataProvince eopBasedataProvince = (EopBasedataProvince) model;
    }

    @Override
    public List<EopBasedataProvince> findByFdCountry(EopBasedataCountry fdCountry) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataProvince.fdCountry.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCountry.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
