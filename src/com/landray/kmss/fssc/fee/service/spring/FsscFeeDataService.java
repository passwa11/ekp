package com.landray.kmss.fssc.fee.service.spring;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.fee.model.FsscFeeMapp;
import com.landray.kmss.fssc.fee.service.IFsscFeeMainService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;
@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class FsscFeeDataService implements IXMLDataBean{
	private IEopBasedataCompanyService eopBasedataCompanyService;
	public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}

	private IFsscFeeMainService fsscFeeMainService;

	public void setFsscFeeMainService(IFsscFeeMainService fsscFeeMainService) {
		this.fsscFeeMainService = fsscFeeMainService;
	}

	private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String type = requestInfo.getParameter("type");
		Method me = FsscFeeDataService.class.getDeclaredMethod(type, RequestContext.class);
		me.setAccessible(true);
		List<?> rtn = new ArrayList();
		try{
			rtn = (List<?>) me.invoke(this, requestInfo);
		}catch(Exception e){
			logger.error("", e);
		}
		return rtn;
	}
	
	private List getDefaultCompany(RequestContext requestInfo) throws Exception{
		SysOrgElement user = UserUtil.getUser();
        List<EopBasedataCompany> own = eopBasedataCompanyService.findCompanyByUserId(user.getFdId());
        List rtn = new ArrayList();
        if(!ArrayUtil.isEmpty(own)){
        	Map node = new HashMap();
        	node.put("id", own.get(0).getFdId());
        	node.put("name", own.get(0).getFdName());
        	node.put("code", own.get(0).getFdCode());
        	rtn.add(node);
        }
        return rtn;
	}
	
	private List getDefaultCostCenter(RequestContext request) throws Exception{
		SysOrgElement user = UserUtil.getUser();
		List rtn = new ArrayList();
		String fdCompanyId = request.getParameter("fdCompanyId");
		String hql = "select center from com.landray.kmss.eop.basedata.model.EopBasedataCostCenter center left join center.fdCompanyList comp where center.fdIsAvailable=:fdIsAvailable and (comp.fdId=:fdCompanyId or comp.fdId is null) and center.fdIsGroup=:fdIsGroup";
		Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
    	query.setParameter("fdIsAvailable", true);
    	query.setParameter("fdIsGroup", EopBasedataConstant.FSSC_BASE_COST_CENTER_TYPE_CENTER);
    	query.setParameter("fdCompanyId", fdCompanyId);
    	List<EopBasedataCostCenter> costs = query.list();
    	List<EopBasedataCostCenter> costsOwn = new ArrayList<EopBasedataCostCenter>();
    	for(EopBasedataCostCenter cost:costs){
    		List<SysOrgElement> fdEkpOrg = cost.getFdEkpOrg();
        	if(!ArrayUtil.isEmpty(fdEkpOrg)){
        		for(SysOrgElement org:fdEkpOrg){
        			if(user.getFdHierarchyId().indexOf(org.getFdHierarchyId())>-1&&!costsOwn.contains(cost)){
        				costsOwn.add(cost);
        			}
        		}
        	}
    	}
    	if(costsOwn.size()>0){
    		costsOwn = EopBasedataFsscUtil.sortByCompany(costsOwn);
    		Map node = new HashMap();
        	node.put("id", costsOwn.get(0).getFdId());
        	node.put("name", costsOwn.get(0).getFdName());
        	node.put("code", costsOwn.get(0).getFdCode());
        	rtn.add(node);
    	}
		return rtn;
	}
	
	private List getDefaultCurrency(RequestContext request) throws Exception{
		List rtn = new ArrayList();
		String fdCompanyId = request.getParameter("fdCompanyId");
		EopBasedataCompany comp = (EopBasedataCompany) fsscFeeMainService.findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
		Map node = new HashMap();
		node.put("id", comp.getFdAccountCurrency().getFdId());
		node.put("name", comp.getFdAccountCurrency().getFdName());
		String hql = "select rate from com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate rate left join rate.fdCompanyList comp where rate.fdIsAvailable=:fdIsAvailable and (comp.fdId=:fdCompanyId or comp.fdId is null) and rate.fdSourceCurrency.fdId=:fdSourceCurrencyId and rate.fdTargetCurrency.fdId=:fdTargetCurrencyId and rate.fdType=:fdType ";
		Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
		String value = EopBasedataFsscUtil.getSwitchValue("fdRateEnabled");
		if("true".equals(value)){
			query.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_CURRENT);
		}else{
			query.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
		}
		query.setParameter("fdIsAvailable", true);
    	query.setParameter("fdSourceCurrencyId", comp.getFdAccountCurrency().getFdId());
    	query.setParameter("fdTargetCurrencyId", comp.getFdAccountCurrency().getFdId());
    	query.setParameter("fdCompanyId", fdCompanyId);
    	List<EopBasedataExchangeRate> rates =  query.list();
    	if(rates.size()>0){
    		rates = EopBasedataFsscUtil.sortByCompany(rates);
    		node.put("_cost_rate", rates.get(0).getFdRate());
    	}
    	query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
    	query.setParameter("fdIsAvailable", true);
    	query.setParameter("fdTargetCurrencyId", comp.getFdBudgetCurrency().getFdId());
    	query.setParameter("fdSourceCurrencyId", comp.getFdAccountCurrency().getFdId());
    	query.setParameter("fdCompanyId", fdCompanyId);
    	if("true".equals(value)){
			query.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_CURRENT);
		}else{
			query.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
		}
    	rates =  query.list();
    	if(rates.size()>0){
    		rates = EopBasedataFsscUtil.sortByCompany(rates);
    		node.put("_budget_rate", rates.get(0).getFdRate());
    	}
    	rtn.add(node);
		return rtn;
	}
	
	private List checkTemplateMappExist(RequestContext request) throws Exception{
		List rtn = new ArrayList();
		String fdTempalteId = request.getParameter("fdTemplateId");
		String fdId = request.getParameter("fdId");
		String hql = "from com.landray.kmss.fssc.fee.model.FsscFeeMapp where fdTemplate.fdId=:fdTempalteId and fdId!=:fdId";
		Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
    	query.setParameter("fdTempalteId", fdTempalteId);
    	query.setParameter("fdId", fdId);
		List<FsscFeeMapp> list = query.list();
		Map node = new HashMap();
    	node.put("exists", !ArrayUtil.isEmpty(list));
    	rtn.add(node);
		return rtn;
	}
	
	private List changePerson(RequestContext request) throws Exception{
		List rtn = new ArrayList();
		Map<String,String> map = new HashMap<String,String>();
		String fdPersonId = request.getParameter("fdPersonId");
		SysOrgElement user = (SysOrgElement) fsscFeeMainService.findByPrimaryKey(fdPersonId, SysOrgElement.class, true);
		SysOrgElement parent = user.getFdParent();
		if(parent!=null){
			map.put("fdParentId", parent.getFdId());
			map.put("fdParentName", parent.getFdName());
		}else{
			map.put("fdParentId", "");
			map.put("fdParentId", "");
		}
		List<EopBasedataCompany> companys = eopBasedataCompanyService.findCompanyByUserId(fdPersonId);
		if(!ArrayUtil.isEmpty(companys)){
			map.put("fdCompanyId", companys.get(0).getFdId());
			map.put("fdCompanyName", companys.get(0).getFdName());
		}else{
			map.put("fdCompanyId", "");
			map.put("fdCompanyName", "");
		}
		String fdCompanyId = map.get("fdCompanyId");
		String hql = "select center from com.landray.kmss.eop.basedata.model.EopBasedataCostCenter center left join center.fdCompanyList comp where center.fdIsAvailable=:fdIsAvailable and (comp.fdId=:fdCompanyId or comp.fdId is null) and center.fdIsGroup=:fdIsGroup";
		Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
    	query.setParameter("fdIsAvailable", true);
    	query.setParameter("fdIsGroup", EopBasedataConstant.FSSC_BASE_COST_CENTER_TYPE_CENTER);
    	query.setParameter("fdCompanyId", fdCompanyId);
    	List<EopBasedataCostCenter> costs = query.list();
    	List<EopBasedataCostCenter> costsOwn = new ArrayList<EopBasedataCostCenter>();
    	for(EopBasedataCostCenter cost:costs){
    		List<SysOrgElement> fdEkpOrg = cost.getFdEkpOrg();
        	if(!ArrayUtil.isEmpty(fdEkpOrg)){
        		for(SysOrgElement org:fdEkpOrg){
        			if(user.getFdHierarchyId().indexOf(org.getFdHierarchyId())>-1&&!costsOwn.contains(cost)){
        				costsOwn.add(cost);
        			}
        		}
        	}
    	}
    	if(costsOwn.size()>0){
    		costsOwn = EopBasedataFsscUtil.sortByCompany(costsOwn);
    		map.put("fdCostCenterId",costsOwn.get(0).getFdId());
    		map.put("fdCostCenterName",costsOwn.get(0).getFdName());
    	}else{
    		map.put("fdCostCenterId","");
    		map.put("fdCostCenterName","");
    	}
    	rtn.add(map);
		return rtn;
	}

}
