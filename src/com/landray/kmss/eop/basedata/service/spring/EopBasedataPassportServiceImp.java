package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataPassport;
import com.landray.kmss.eop.basedata.service.IEopBasedataPassportService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EopBasedataPassportServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataPassportService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataPassport) {
            EopBasedataPassport eopBasedataPassport = (EopBasedataPassport) model;
            eopBasedataPassport.setDocAlterTime(new Date());
            eopBasedataPassport.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataPassport eopBasedataPassport = new EopBasedataPassport();
        eopBasedataPassport.setFdIsAvailable(Boolean.TRUE);
        eopBasedataPassport.setDocCreateTime(new Date());
        eopBasedataPassport.setDocAlterTime(new Date());
        SysOrgPerson user=UserUtil.getUser();
        eopBasedataPassport.setDocCreator(user);
        eopBasedataPassport.setDocAlteror(user);
        eopBasedataPassport.setFdPerson(user);
        EopBasedataUtil.initModelFromRequest(eopBasedataPassport, requestContext);
        return eopBasedataPassport;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataPassport eopBasedataPassport = (EopBasedataPassport) model;
    }

    /**
     * 根据员工id获取员工护照
     * @param fdPersonId
     * @return
     * @throws Exception
     */
    @Override
    public EopBasedataPassport getEopBasedataPassport(String fdPersonId) throws Exception{
        if(StringUtil.isNotNull(fdPersonId)){
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock(" eopBasedataPassport.fdPerson.fdId = :fdPersonId ");
            hqlInfo.setParameter("fdPersonId", fdPersonId);
            List<EopBasedataPassport> list = this.findList(hqlInfo);
            if(!ArrayUtil.isEmpty(list)){
                return list.get(0);
            }
        }
        return new EopBasedataPassport();
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
