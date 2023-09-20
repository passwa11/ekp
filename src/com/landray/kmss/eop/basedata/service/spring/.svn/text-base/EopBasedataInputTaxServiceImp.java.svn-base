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
import com.landray.kmss.eop.basedata.forms.EopBasedataInputTaxForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataInputTax;
import com.landray.kmss.eop.basedata.service.IEopBasedataInputTaxService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataInputTaxServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataInputTaxService,IXMLDataBean {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataInputTax) {
            EopBasedataInputTax eopBasedataInputTax = (EopBasedataInputTax) model;
            eopBasedataInputTax.setDocAlterTime(new Date());
            eopBasedataInputTax.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
        EopBasedataInputTaxForm mainForm = (EopBasedataInputTaxForm) super.initFormSetting(form, requestContext);
        return mainForm;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataInputTax eopBasedataInputTax = new EopBasedataInputTax();
        eopBasedataInputTax.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataInputTax.setDocCreateTime(new Date());
        eopBasedataInputTax.setDocAlterTime(new Date());
        eopBasedataInputTax.setFdIsInputTax(Boolean.valueOf("false"));
        eopBasedataInputTax.setDocCreator(UserUtil.getUser());
        eopBasedataInputTax.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataInputTax, requestContext);
        return eopBasedataInputTax;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataInputTax eopBasedataInputTax = (EopBasedataInputTax) model;
    }

    @Override
    public List<EopBasedataInputTax> findByFdItem(EopBasedataExpenseItem fdItem, Double fdInputTaxRate) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setJoinBlock(" left join eopBasedataInputTax.fdItem fdExpenseItem");
        hqlInfo.setWhereBlock("fdExpenseItem.fdId=:fdId and eopBasedataInputTax.fdIsAvailable = :fdIsAvailable" +
                " and eopBasedataInputTax.fdIsInputTax = :fdIsInputTax and eopBasedataInputTax.fdTaxRate = :fdTaxRate");
        hqlInfo.setParameter("fdId", fdItem.getFdId());
        hqlInfo.setParameter("fdIsAvailable", true);
        hqlInfo.setParameter("fdIsInputTax", true);
        hqlInfo.setParameter("fdTaxRate", fdInputTaxRate);	//税率
        return this.findList(hqlInfo);
    }


    @Override
    public List<EopBasedataInputTax> findByFdAccount(EopBasedataAccounts fdAccount) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataInputTax.fdAccount.fdId=:fdId and eopBasedataInputTax.fdIsAvailable = :fdIsAvailable");
        hqlInfo.setParameter("fdId", fdAccount.getFdId());
        hqlInfo.setParameter("fdIsAvailable", true);
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String,Object>> rtn = new ArrayList<Map<String,Object>>();
		String fdExpenseItemId = requestInfo.getParameter("fdExpenseItemId");
		String multi = requestInfo.getParameter("multi");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataInputTax.fdItem.fdId=:fdExpenseItemId and eopBasedataInputTax.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdExpenseItemId",fdExpenseItemId);
		hqlInfo.setParameter("fdIsAvailable",true);
		List<EopBasedataInputTax> list = findList(hqlInfo);
		if("true".equals(multi)) {//查询所有的税率
			for(EopBasedataInputTax tax:list) {
				Map<String,Object> node = new HashMap<String,Object>();
				node.put("fdTaxRate", tax.getFdTaxRate());
				node.put("fdTaxRateId", tax.getFdId());
				node.put("fdIsInputTax", tax.getFdIsInputTax());
				rtn.add(node);
			}
		}else {
			Map<String,Object> node = new HashMap<String,Object>();
			if(!ArrayUtil.isEmpty(list)){
				node.put("fdTaxRate", list.get(0).getFdTaxRate());
				node.put("fdTaxRateId", list.get(0).getFdId());
				node.put("fdIsInputTax", list.get(0).getFdIsInputTax());
			}else{
				node.put("fdTaxRate", 0d);
			}
			rtn.add(node);
		}
		return rtn;
	}
}
