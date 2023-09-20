package com.landray.kmss.fssc.expense.service.spring;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataInputTax;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataInputTaxService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLedgerService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;
/**
 * 前台异步获取数据统一接口
 * @author wangjinman
 *
 */
public class FsscExpenseDataService implements IXMLDataBean{
	private IEopBasedataCompanyService eopBasedataCompanyService;
	
	public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}

	private IFsscExpenseMainService fsscExpenseMainService;
	public void setFsscExpenseMainService(IFsscExpenseMainService fsscExpenseMainService) {
		this.fsscExpenseMainService = fsscExpenseMainService;
	}
	
	private IFsscCommonLedgerService fsscCommonLedgerService;
	
	public IFsscCommonLedgerService getFsscCommonLedgerService() {
		if (fsscCommonLedgerService == null) {
			fsscCommonLedgerService = (IFsscCommonLedgerService) SpringBeanUtil.getBean("fsscCommonLedgerService");
        }
		return fsscCommonLedgerService;
	}
	
	private IEopBasedataInputTaxService eopBasedataInputTaxService;
	
	public IEopBasedataInputTaxService getEopBasedataInputTaxService() {
		if (eopBasedataInputTaxService == null) {
			eopBasedataInputTaxService = (IEopBasedataInputTaxService) SpringBeanUtil.getBean("eopBasedataInputTaxService");
        }
		return eopBasedataInputTaxService;
	}

	private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String type = requestInfo.getParameter("type");
		Method me = FsscExpenseDataService.class.getDeclaredMethod(type, RequestContext.class);
		me.setAccessible(true);
		List<?> rtn = new ArrayList();
		try{
			rtn = (List<?>) me.invoke(this, requestInfo);
		}catch(Exception e){
			logger.error("", e);
		}
		return rtn;
	}
	
	private List getDefaultCompany(RequestContext request) throws Exception{
		List rtn = new ArrayList();
		String fdPersonId = request.getParameter("fdPersonId");
        if(StringUtil.isNotNull(fdPersonId)){
        	List<EopBasedataCompany> list = eopBasedataCompanyService.findCompanyByUserId(fdPersonId);
        	JSONObject companyJson=EopBasedataFsscUtil.getAccountAuth("com.landray.kmss.fssc.expense.model.FsscExpenseMain",fdPersonId);
        	Boolean auth=companyJson.optBoolean("auth",Boolean.TRUE);
        	String fdCompanyIds=companyJson.optString("fdCompanyIds", "");
        	for(EopBasedataCompany com:list) {
        		Map map = new HashMap();
        		if(auth) {//设置开账
        			if(fdCompanyIds.indexOf(com.getFdId())>-1) {
        				map.put("fdId", com.getFdId());
            			map.put("fdName", com.getFdName());
            			rtn.add(map);
            			break;
        			}
        		}else {//关账
        			if(fdCompanyIds.indexOf(com.getFdId())==-1) {
        				map.put("fdId", com.getFdId());
            			map.put("fdName", com.getFdName());
            			rtn.add(map);
            			break;
        			}
        		}
        	}
        }
		return rtn;
	}
	
	private List getDefaultCostCenter(RequestContext request) throws Exception{
		List rtn = new ArrayList();
		List<EopBasedataCostCenter> costsOwn = new ArrayList<EopBasedataCostCenter>();
		String fdCompanyId = request.getParameter("fdCompanyId");
		String fdPersonId = request.getParameter("fdPersonId");
		if(StringUtil.isNotNull(fdPersonId)){
			SysOrgElement ele = (SysOrgElement) eopBasedataCompanyService.findByPrimaryKey(fdPersonId, SysOrgElement.class, true);
			fdPersonId = ele.getFdHierarchyId();
		}else{
			fdPersonId = "";
		}
		Query query = eopBasedataCompanyService.getBaseDao().getHibernateSession().createQuery("select cost from com.landray.kmss.eop.basedata.model.EopBasedataCostCenter cost left join cost.fdCompanyList comp where cost.fdIsAvailable=:fdIsAvailable and (comp.fdId=:fdCompanyId or comp.fdId is null) and cost.fdIsGroup=:fdIsGroup");
    	query.setParameter("fdIsAvailable", true);
    	query.setParameter("fdIsGroup", EopBasedataConstant.FSSC_BASE_COST_CENTER_TYPE_CENTER);
    	query.setParameter("fdCompanyId", fdCompanyId);
    	List<EopBasedataCostCenter> costs = query.list();
    	for(EopBasedataCostCenter cost:costs){
    		List<SysOrgElement> fdEkpOrg = cost.getFdEkpOrg();
        	if(!ArrayUtil.isEmpty(fdEkpOrg)){
        		for(SysOrgElement org:fdEkpOrg){
        			if(fdPersonId.indexOf(org.getFdHierarchyId())>-1&&!costsOwn.contains(cost)){
        				costsOwn.add(cost);
        			}
        		}
        	}
    	}
    	if(costsOwn.size()>0){
    		costsOwn = EopBasedataFsscUtil.sortByCompany(costsOwn);
    		Map map = new HashMap();
			map.put("fdId", costsOwn.get(0).getFdId());
			map.put("fdName", costsOwn.get(0).getFdName());
			rtn.add(map);
    	}
		return rtn;
	}
	private List getDefaultCurrency(RequestContext request) throws Exception{
		List rtn = new ArrayList();
		String fdCompanyId = request.getParameter("fdCompanyId");
		EopBasedataCompany comp = (EopBasedataCompany) fsscExpenseMainService.findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
		Map map = new HashMap();
		map.put("fdCurrencyId", comp.getFdAccountCurrency().getFdId());
		map.put("fdCurrencyName", comp.getFdAccountCurrency().getFdName());
		rtn.add(map);
		String hql = "select rate from com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate rate left join rate.fdCompanyList where rate.fdType=:fdType and (comp.fdId=:fdCompanyId or comp.fdId is null) and rate.fdSourceCurrency.fdId=:fdCurrencyId and rate.fdTargetCurrency.fdId=:fdCurrencyId and rate.fdIsAvailable=:fdIsAvailable";
		Query query = fsscExpenseMainService.getBaseDao().getHibernateSession().createQuery(hql);
		//判断是否启用了实时汇率
		String value = EopBasedataFsscUtil.getSwitchValue("fdRateEnabled");
		if("true".equals(value)){
			query.setParameter("fdType", EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_CURRENT);
		}else{
			query.setParameter("fdType", EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
		}
		query.setParameter("fdCompanyId", fdCompanyId);
		query.setParameter("fdIsAvailable", true);
		query.setParameter("fdCurrencyId", comp.getFdAccountCurrency().getFdId());
		List<EopBasedataExchangeRate> data = query.list();
		if(!ArrayUtil.isEmpty(data)){
			data = EopBasedataFsscUtil.sortByCompany(data);
			map.put("fdRate", data.get(0).getFdRate());
		}else{
			map.put("fdRate", "");
		}
		return rtn;
	}
	
	/**
	 * 根据费用类型带出会计科目
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private List getAccountByExpenseItem(RequestContext request) throws Exception{
		List rtn = new ArrayList();
		String fdExpenseItemId = request.getParameter("fdExpenseItemId");
		EopBasedataExpenseItem comp = (EopBasedataExpenseItem) fsscExpenseMainService.findByPrimaryKey(fdExpenseItemId, EopBasedataExpenseItem.class, true);
		List<EopBasedataAccounts> accounts = comp.getFdAccounts();
		if(!ArrayUtil.isEmpty(accounts)){
			Map map = new HashMap();
			rtn.add(map);
			map.put("id", accounts.get(0).getFdId());
			map.put("name", accounts.get(0).getFdName());
			map.put("item", accounts.get(0).getFdCostItem());
		}
		return rtn;
	}
	
	
	/**
	 * 根据发票号码获取发票信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private List getInvoiceInfoByCode(RequestContext request) throws Exception{
		List rtn = new ArrayList();
		String fdInvoiceNumber = request.getParameter("fdInvoiceNumber");
		String fdInvoiceCode=request.getParameter("fdInvoiceCode");
		if(StringUtil.isNotNull(fdInvoiceNumber)){
			rtn=getFsscCommonLedgerService().getInvoiceInfoByCode(fdInvoiceCode,fdInvoiceNumber);
        }
		return rtn;
	}

}
