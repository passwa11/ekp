package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.eop.basedata.service.IEopBasedataPayBankService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataPayBankServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataPayBankService,IXMLDataBean {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataPayBank) {
            EopBasedataPayBank eopBasedataPayBank = (EopBasedataPayBank) model;
            eopBasedataPayBank.setDocAlterTime(new Date());
            eopBasedataPayBank.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataPayBank eopBasedataPayBank = new EopBasedataPayBank();
        eopBasedataPayBank.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataPayBank.setDocCreateTime(new Date());
        eopBasedataPayBank.setDocAlterTime(new Date());
        eopBasedataPayBank.setDocCreator(UserUtil.getUser());
        eopBasedataPayBank.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataPayBank, requestContext);
        return eopBasedataPayBank;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataPayBank eopBasedataPayBank = (EopBasedataPayBank) model;
    }

    @Override
    public List<EopBasedataPayBank> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataPayBank.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }
    @Override
    public List<Map<String, String>> getDataList(RequestContext request)
            throws Exception {
        String fdCompanyId = request.getParameter("fdCompanyId");

        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock(" new Map(eopBasedataPayBank.fdId as value,eopBasedataPayBank.fdAccountName||'('||eopBasedataPayBank.fdBankAccount||')' as text) ");
        if(StringUtil.isNotNull(fdCompanyId)){
        	hqlInfo.setJoinBlock(" left join eopBasedataPayBank.fdCompanyList company ");
            hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                    "(company.fdId=:fdCompanyId or company is null) "));
            hqlInfo.setParameter("fdCompanyId", fdCompanyId);
        }
        hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                " eopBasedataPayBank.fdIsAvailable = :fdIsAvailable "));
        hqlInfo.setParameter("fdIsAvailable", true);
        return findList(hqlInfo);
    }
}
