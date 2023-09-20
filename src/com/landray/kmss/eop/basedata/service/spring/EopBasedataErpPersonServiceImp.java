package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataErpPerson;
import com.landray.kmss.eop.basedata.service.IEopBasedataErpPersonService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataErpPersonServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataErpPersonService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataErpPerson) {
            EopBasedataErpPerson eopBasedataErpPerson = (EopBasedataErpPerson) model;
            eopBasedataErpPerson.setDocAlterTime(new Date());
            eopBasedataErpPerson.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataErpPerson eopBasedataErpPerson = new EopBasedataErpPerson();
        eopBasedataErpPerson.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataErpPerson.setDocCreateTime(new Date());
        eopBasedataErpPerson.setDocAlterTime(new Date());
        eopBasedataErpPerson.setDocCreator(UserUtil.getUser());
        eopBasedataErpPerson.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataErpPerson, requestContext);
        return eopBasedataErpPerson;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataErpPerson eopBasedataErpPerson = (EopBasedataErpPerson) model;
    }

    @Override
    public List<EopBasedataErpPerson> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataErpPerson.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }
    @Override
    public EopBasedataErpPerson getEopBasedataErpPersonByFdPersonId(String fdPersonId) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataErpPerson.fdOrg.fdId=:fdPersonId and eopBasedataErpPerson.fdIsAvailable=:fdIsAvailable");
        hqlInfo.setParameter("fdIsAvailable", true);
        hqlInfo.setParameter("fdPersonId", fdPersonId);
        List<EopBasedataErpPerson> list = this.findList(hqlInfo);
        return ArrayUtil.isEmpty(list)?null:list.get(0);
    }
}
