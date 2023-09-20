package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.eop.basedata.service.IEopBasedataVoucherTypeService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataVoucherTypeServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataVoucherTypeService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataVoucherType) {
            EopBasedataVoucherType eopBasedataVoucherType = (EopBasedataVoucherType) model;
            eopBasedataVoucherType.setDocAlterTime(new Date());
            eopBasedataVoucherType.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataVoucherType eopBasedataVoucherType = new EopBasedataVoucherType();
        eopBasedataVoucherType.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataVoucherType.setDocCreateTime(new Date());
        eopBasedataVoucherType.setDocAlterTime(new Date());
        eopBasedataVoucherType.setDocCreator(UserUtil.getUser());
        eopBasedataVoucherType.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataVoucherType, requestContext);
        return eopBasedataVoucherType;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataVoucherType eopBasedataVoucherType = (EopBasedataVoucherType) model;
    }

    @Override
    public List<EopBasedataVoucherType> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataVoucherType.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }
}
