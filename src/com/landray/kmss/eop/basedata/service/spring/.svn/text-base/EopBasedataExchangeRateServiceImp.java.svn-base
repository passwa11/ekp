package com.landray.kmss.eop.basedata.service.spring;

import java.text.DecimalFormat;
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
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.service.IEopBasedataExchangeRateService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataExchangeRateServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataExchangeRateService,IXMLDataBean {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataExchangeRate) {
            EopBasedataExchangeRate eopBasedataExchangeRate = (EopBasedataExchangeRate) model;
            eopBasedataExchangeRate.setDocAlterTime(new Date());
            eopBasedataExchangeRate.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataExchangeRate eopBasedataExchangeRate = new EopBasedataExchangeRate();
        eopBasedataExchangeRate.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataExchangeRate.setDocCreateTime(new Date());
        eopBasedataExchangeRate.setDocAlterTime(new Date());
        eopBasedataExchangeRate.setFdType(String.valueOf("1"));
        eopBasedataExchangeRate.setDocCreator(UserUtil.getUser());
        eopBasedataExchangeRate.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataExchangeRate, requestContext);
        return eopBasedataExchangeRate;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataExchangeRate eopBasedataExchangeRate = (EopBasedataExchangeRate) model;
    }

    @Override
    public List<EopBasedataExchangeRate> findByFdSourceCurrency(EopBasedataCurrency fdSourceCurrency) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataExchangeRate.fdSourceCurrency.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdSourceCurrency.getFdId());
        return this.findList(hqlInfo);
    }

	@Override
    public List<EopBasedataExchangeRate> findByFdTargetCurrency(EopBasedataCurrency fdTargetCurrency) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataExchangeRate.fdTargetCurrency.fdId=:fdId");
		hqlInfo.setParameter("fdId", fdTargetCurrency.getFdId());
		return this.findList(hqlInfo);
	}

	@Override
    public double getRateByAccountCurrency(EopBasedataCompany fdCompany, String fdCurrencyId) throws Exception {
    	if(fdCompany == null || StringUtil.isNull(fdCurrencyId)){
			return 1d;
		}
		//查询本位币的汇率
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("eopBasedataExchangeRate");
		hqlInfo.setJoinBlock("left join eopBasedataExchangeRate.fdCompanyList comp");
		StringBuffer where = new StringBuffer();
		where.append("eopBasedataExchangeRate.fdSourceCurrency.fdId=:fdSourceCurrencyId ");
		where.append("and eopBasedataExchangeRate.fdTargetCurrency.fdId=:fdTargetCurrencyId ");
		where.append("and (comp.fdId=:fdCompanyId or comp.fdId is null)");
		where.append("and eopBasedataExchangeRate.fdIsAvailable=:fdIsAvailable ");
		where.append("and eopBasedataExchangeRate.fdType=:fdType ");
		hqlInfo.setWhereBlock(where.toString());
		hqlInfo.setParameter("fdSourceCurrencyId",fdCurrencyId);
		hqlInfo.setParameter("fdTargetCurrencyId",fdCompany.getFdAccountCurrency().getFdId());
		hqlInfo.setParameter("fdCompanyId",fdCompany.getFdId());
		hqlInfo.setParameter("fdIsAvailable",true);
		String value = EopBasedataFsscUtil.getSwitchValue("fdRateEnabled");
		if("true".equals(value)){
			hqlInfo.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_CURRENT);
		}else{
			hqlInfo.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
		}
		List<EopBasedataExchangeRate> list = findList(hqlInfo);
		if(list.size()>0) {
			list = EopBasedataFsscUtil.sortByCompany(list);
		}
		return ArrayUtil.isEmpty(list)?1d:list.get(0).getFdRate();
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String fdCompanyId = requestInfo.getParameter("fdCompanyId");
		String fdCurrencyId = requestInfo.getParameter("fdCurrencyId");
		String type = requestInfo.getParameter("type");
		if(StringUtil.isNull(fdCompanyId)){
			return null;
		}
		//根据选择的币种获取对应公司本位币的汇率以及预算币种的汇率
		if("getRateByCurrency".equals(type)){
			List<Map<String,Object>> rtn  = new ArrayList<Map<String,Object>>();
			EopBasedataCompany comp = (EopBasedataCompany) findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
			//查询本位币的汇率
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setJoinBlock(" left join eopBasedataExchangeRate.fdCompanyList comp");
			hqlInfo.setSelectBlock("new map(eopBasedataExchangeRate.fdId as id,eopBasedataExchangeRate.fdRate as rate)");
			StringBuffer where = new StringBuffer();
			where.append("eopBasedataExchangeRate.fdSourceCurrency.fdId=:fdSourceCurrencyId ");
			where.append("and eopBasedataExchangeRate.fdTargetCurrency.fdId=:fdTargetCurrencyId ");
			where.append("and (comp.fdId=:fdCompanyId or comp.fdId is null) ");
			where.append("and eopBasedataExchangeRate.fdIsAvailable=:fdIsAvailable ");
			where.append("and eopBasedataExchangeRate.fdType=:fdType ");
			hqlInfo.setWhereBlock(where.toString());
			hqlInfo.setParameter("fdSourceCurrencyId",fdCurrencyId);
			hqlInfo.setParameter("fdTargetCurrencyId",comp.getFdAccountCurrency().getFdId());
			hqlInfo.setParameter("fdCompanyId",comp.getFdId());
			hqlInfo.setParameter("fdIsAvailable",true);
			String value = EopBasedataFsscUtil.getSwitchValue("fdRateEnabled");
			if("true".equals(value)){
				hqlInfo.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_CURRENT);
			}else{
				hqlInfo.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
			}
			List<Map<String,Object>> list = findList(hqlInfo);
			Map<String,Object> map = new HashMap<String,Object>();
			rtn.add(map);
			DecimalFormat df = new DecimalFormat("0.00####");
			try {
				Number rate = (Number) list.get(0).get("rate");
				map.put("fdExchangeRate", df.format(rate));
			} catch (Exception e) {
			}
			rtn.get(0).put("fdStandardCurrencyId", comp.getFdAccountCurrency().getFdId());
			//查询预算币的汇率
			hqlInfo.setParameter("fdTargetCurrencyId",comp.getFdBudgetCurrency().getFdId());
			list = findList(hqlInfo);
			try {
				Number rate = (Number) list.get(0).get("rate");
				map.put("fdBudgetRate", df.format(rate));
			} catch (Exception e) {
			}
			EopBasedataCurrency currency=(EopBasedataCurrency) this.findByPrimaryKey(fdCurrencyId, EopBasedataCurrency.class, true);
			if(currency!=null){
				rtn.get(0).put("code", currency.getFdSymbol());
			}
			return rtn;
		}
		return null;
	}
	
	/**
	 * 根据选择的币种获取对应公司本位币的汇率以及预算币种的汇率
	 * @param fdCurrencyId
	 * @param fdCompanyId
	 * @return
	 * @throws Exception
	 */
	@Override
    public Double getExchangeRate(String fdCurrencyId, String fdCompanyId) throws Exception{
		Double exchange=null;
		EopBasedataCompany comp = (EopBasedataCompany) findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
		//查询本位币的汇率
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("new map(eopBasedataExchangeRate.fdId as id,eopBasedataExchangeRate.fdRate as rate)");
		hqlInfo.setJoinBlock(" left join eopBasedataExchangeRate.fdSourceCurrency sourceCurrency left join eopBasedataExchangeRate.fdTargetCurrency targetCurrency left join eopBasedataExchangeRate.fdCompanyList company ");
		StringBuffer where = new StringBuffer();
		where.append(" sourceCurrency.fdId=:fdSourceCurrencyId ");
		where.append(" and targetCurrency.fdId=:fdTargetCurrencyId ");
		where.append(" and (company.fdId=:fdCompanyId or company is null)");
		where.append(" and eopBasedataExchangeRate.fdIsAvailable=:fdIsAvailable ");
		where.append(" and eopBasedataExchangeRate.fdType=:fdType ");
		hqlInfo.setWhereBlock(where.toString());
		hqlInfo.setParameter("fdSourceCurrencyId",fdCurrencyId);
		hqlInfo.setParameter("fdTargetCurrencyId",comp.getFdAccountCurrency().getFdId());
		hqlInfo.setParameter("fdCompanyId",comp.getFdId());
		hqlInfo.setParameter("fdIsAvailable",true);
		String value = EopBasedataFsscUtil.getSwitchValue("fdRateEnabled");
		if("true".equals(value)){
			hqlInfo.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_CURRENT);
		}else{
			hqlInfo.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
		}
		List<Map<String,Object>> list = findList(hqlInfo);
		try {
			exchange = (Double) list.get(0).get("rate");
		} catch (Exception e) {
		}
		return exchange;
	}
	/**
	 * 根据选择的币种获取对应公司预算币种的汇率
	 * @param fdCurrencyId
	 * @param fdCompanyId
	 * @return
	 * @throws Exception
	 */
	@Override
    public Double getBudgetRate(String fdCurrencyId, String fdCompanyId) throws Exception{
		Double exchange=null;
		EopBasedataCompany comp = (EopBasedataCompany) findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
		//查询本位币的汇率
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("new map(eopBasedataExchangeRate.fdId as id,eopBasedataExchangeRate.fdRate as rate)");
		StringBuffer where = new StringBuffer();
		where.append("eopBasedataExchangeRate.fdSourceCurrency.fdId=:fdSourceCurrencyId ");
		where.append("and eopBasedataExchangeRate.fdTargetCurrency.fdId=:fdTargetCurrencyId ");
		where.append("and (comp.fdId=:fdCompanyId or comp.fdId is null) ");
		where.append("and eopBasedataExchangeRate.fdIsAvailable=:fdIsAvailable ");
		where.append("and eopBasedataExchangeRate.fdType=:fdType ");
		hqlInfo.setWhereBlock(where.toString());
		hqlInfo.setParameter("fdSourceCurrencyId",fdCurrencyId);
		hqlInfo.setParameter("fdTargetCurrencyId",comp.getFdBudgetCurrency().getFdId());
		hqlInfo.setParameter("fdCompanyId",comp.getFdId());
		hqlInfo.setParameter("fdIsAvailable",true);
		hqlInfo.setJoinBlock(" left join eopBasedataExchangeRate.fdCompanyList comp ");
		String value = EopBasedataFsscUtil.getSwitchValue("fdRateEnabled");
		if("true".equals(value)){
			hqlInfo.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_CURRENT);
		}else{
			hqlInfo.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
		}
		List<Map<String,Object>> list = findList(hqlInfo);
		try {
			exchange = (Double) list.get(0).get("rate");
		} catch (Exception e) {
		}
		return exchange;
	}
	
	/**
	 * 根据对应公司预算币种换为参数币种的汇率
	 * @param fdCurrencyId
	 * @param fdCompanyId
	 * @return
	 * @throws Exception
	 */
	@Override
    public Double getBudgetToRate(String fdCurrencyId, String fdCompanyId) throws Exception{
		Double exchange=null;
		EopBasedataCompany comp = (EopBasedataCompany) findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
		//查询本位币的汇率
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("new map(eopBasedataExchangeRate.fdId as id,eopBasedataExchangeRate.fdRate as rate)");
		StringBuffer where = new StringBuffer();
		where.append("eopBasedataExchangeRate.fdSourceCurrency.fdId=:fdSourceCurrencyId ");
		where.append("and eopBasedataExchangeRate.fdTargetCurrency.fdId=:fdTargetCurrencyId ");
		where.append("and (comp.fdId=:fdCompanyId or comp.fdId is null) ");
		where.append("and eopBasedataExchangeRate.fdIsAvailable=:fdIsAvailable ");
		where.append("and eopBasedataExchangeRate.fdType=:fdType ");
		hqlInfo.setWhereBlock(where.toString());
		hqlInfo.setParameter("fdSourceCurrencyId",comp.getFdBudgetCurrency().getFdId());
		hqlInfo.setParameter("fdTargetCurrencyId",fdCurrencyId);
		hqlInfo.setParameter("fdCompanyId",comp.getFdId());
		hqlInfo.setParameter("fdIsAvailable",true);
		hqlInfo.setJoinBlock(" left join eopBasedataExchangeRate.fdCompanyList comp ");
		String value = EopBasedataFsscUtil.getSwitchValue("fdRateEnabled");
		if("true".equals(value)){
			hqlInfo.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_CURRENT);
		}else{
			hqlInfo.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
		}
		List<Map<String,Object>> list = findList(hqlInfo);
		try {
			exchange = (Double) list.get(0).get("rate");
		} catch (Exception e) {
		}
		return exchange;
	}
	
	@Override
	public void afterImportValidate(String modelName,List<IBaseModel> addList,List<IBaseModel> updateList, List<List<Object>> dataList) throws Exception {
		super.afterImportValidate(modelName, addList, updateList, dataList);
		//如果没有启用实时汇率，默认设置为核算汇率
		String value = EopBasedataFsscUtil.getSwitchValue("fdRateEnabled");
		if(StringUtil.isNull(value)||"false".equals(value)){
			for(IBaseModel baseModel:addList) {
				EopBasedataExchangeRate model=(EopBasedataExchangeRate) baseModel;
				model.setFdType(EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
				this.getBaseDao().getHibernateSession().saveOrUpdate(model);
			}
			for(IBaseModel baseModel:updateList) {
				EopBasedataExchangeRate model=(EopBasedataExchangeRate) baseModel;
				model.setFdType(EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
				this.getBaseDao().getHibernateSession().saveOrUpdate(model);
			}
		}
	}
}
