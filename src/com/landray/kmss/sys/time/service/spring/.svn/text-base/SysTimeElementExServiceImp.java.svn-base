package com.landray.kmss.sys.time.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.time.service.ISysTimeElementExService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import java.util.List;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimeBusinessEx;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.time.model.SysTimeElementEx;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.util.SpringBeanUtil;

public class SysTimeElementExServiceImp extends ExtendDataServiceImp implements ISysTimeElementExService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysTimeElementEx) {
            SysTimeElementEx sysTimeElementEx = (SysTimeElementEx) model;
        }
        return model;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysTimeElementEx sysTimeElementEx = (SysTimeElementEx) model;
    }

    @Override
    public List<SysTimeElementEx> findByFdWork(SysTimeWork fdWork) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysTimeElementEx.fdWork.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdWork.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<SysTimeElementEx> findByFdPatchwork(SysTimePatchwork fdPatchwork) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysTimeElementEx.fdPatchwork.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdPatchwork.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<SysTimeElementEx> findByFdVacation(SysTimeElementEx fdVacation) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysTimeElementEx.fdVacation.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdVacation.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<SysTimeElementEx> findByFdOrgElementTime(SysTimeOrgElementTime fdOrgElementTime) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysTimeElementEx.fdOrgElementTime.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdOrgElementTime.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<SysTimeElementEx> findByFdTimeBussines(SysTimeBusinessEx fdTimeBussines) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysTimeElementEx.fdTimeBussines.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdTimeBussines.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
