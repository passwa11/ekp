package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataTaxRate;
import com.landray.kmss.eop.basedata.service.IEopBasedataTaxRateService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataTaxRateServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataTaxRateService,IXMLDataBean {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataTaxRate) {
            EopBasedataTaxRate eopBasedataTaxRate = (EopBasedataTaxRate) model;
            eopBasedataTaxRate.setDocAlterTime(new Date());
            eopBasedataTaxRate.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataTaxRate eopBasedataTaxRate = new EopBasedataTaxRate();
        eopBasedataTaxRate.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataTaxRate.setDocCreateTime(new Date());
        eopBasedataTaxRate.setDocAlterTime(new Date());
        eopBasedataTaxRate.setDocCreator(UserUtil.getUser());
        eopBasedataTaxRate.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataTaxRate, requestContext);
        return eopBasedataTaxRate;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataTaxRate eopBasedataTaxRate = (EopBasedataTaxRate) model;
    }

    @Override
    public List<EopBasedataTaxRate> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataTaxRate.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataTaxRate> findByFdAccount(EopBasedataAccounts fdAccount) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataTaxRate.fdAccount.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdAccount.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public EopBasedataAccounts getEopBasedataAccounts(String fdCompanyId, double fdRate) throws Exception{
        if(StringUtil.isNull(fdCompanyId)){
            return null;
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("eopBasedataTaxRate.fdAccount");
        hqlInfo.setWhereBlock("eopBasedataTaxRate.fdIsAvailable = :fdIsAvailable and eopBasedataTaxRate.fdCompany.fdId=:fdCompanyId and eopBasedataTaxRate.fdRate = :fdRate");
        hqlInfo.setParameter("fdIsAvailable", true);
        hqlInfo.setParameter("fdCompanyId", fdCompanyId);
        hqlInfo.setParameter("fdRate", fdRate);
        List<EopBasedataAccounts> list = this.findValue(hqlInfo);
        return ArrayUtil.isEmpty(list)?null:list.get(0);
    }
    
    /**
     * 根据税率获取基础数据的税率对象
     */
    public EopBasedataTaxRate getEopBasedataTaxRate(String fdCompanyId, double fdRate) throws Exception{
    	if(StringUtil.isNull(fdCompanyId)){
    		return null;
    	}
    	HQLInfo hqlInfo = new HQLInfo();
    	hqlInfo.setWhereBlock("eopBasedataTaxRate.fdIsAvailable = :fdIsAvailable and eopBasedataTaxRate.fdCompany.fdId=:fdCompanyId and eopBasedataTaxRate.fdRate = :fdRate");
    	hqlInfo.setParameter("fdIsAvailable", true);
    	hqlInfo.setParameter("fdCompanyId", fdCompanyId);
    	hqlInfo.setParameter("fdRate", fdRate);
    	List<EopBasedataTaxRate> list = this.findValue(hqlInfo);
    	return ArrayUtil.isEmpty(list)?null:list.get(0);
    }

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map> rtn = new ArrayList<Map>();
		String fdCompanyId=requestInfo.getParameter("fdCompanyId");
		String fdRate=requestInfo.getParameter("fdRate");
		if(StringUtil.isNotNull(fdCompanyId)&&StringUtil.isNotNull(fdRate)){
			EopBasedataTaxRate rate=getEopBasedataTaxRate(fdCompanyId,Double.parseDouble(fdRate));
			if(rate!=null){
				Map node = new HashMap();
				node.put("fdTaxId", rate.getFdId());
				node.put("fdTax", rate.getFdRate());
				rtn.add(node);
			}
		}
		return rtn;
	}
}
