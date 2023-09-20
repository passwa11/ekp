package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.service.IEopBasedataCityService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import java.util.List;
import com.landray.kmss.eop.basedata.model.EopBasedataCity;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.eop.basedata.model.EopBasedataCountry;

public class EopBasedataCityServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataCityService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataCity) {
            EopBasedataCity eopBasedataCity = (EopBasedataCity) model;
            eopBasedataCity.setDocAlterTime(new Date());
            eopBasedataCity.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataCity eopBasedataCity = new EopBasedataCity();
        eopBasedataCity.setDocCreateTime(new Date());
        eopBasedataCity.setDocAlterTime(new Date());
        eopBasedataCity.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataCity.setDocCreator(UserUtil.getUser());
        eopBasedataCity.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataCity, requestContext);
        return eopBasedataCity;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataCity eopBasedataCity = (EopBasedataCity) model;
    }

    @Override
    public List<EopBasedataCity> findByFdProvince(EopBasedataCountry fdProvince) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataCity.fdProvince.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdProvince.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
