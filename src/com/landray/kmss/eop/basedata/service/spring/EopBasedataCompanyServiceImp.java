package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.query.Query;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataExchangeRateService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPullAndPushService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPullAndPushService.DataAction;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.ftsearch.apache.commons.collections4.CollectionUtils;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

public class EopBasedataCompanyServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataCompanyService,IXMLDataBean {
	
	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		if(sysOrgCoreService==null){
			sysOrgCoreService=(ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
		}
		this.sysOrgCoreService = sysOrgCoreService;
	}
	
	private IEopBasedataExchangeRateService eopBasedataExchangeRateService;

	public IEopBasedataExchangeRateService getEopBasedataExchangeRateService() {
		if(eopBasedataExchangeRateService==null){
			eopBasedataExchangeRateService=(IEopBasedataExchangeRateService) SpringBeanUtil.getBean("eopBasedataExchangeRateService");
		}
		return eopBasedataExchangeRateService;
	}
	
	private ISysOrgElementService sysOrgElementService;
	
	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataCompany) {
            EopBasedataCompany eopBasedataCompany = (EopBasedataCompany) model;
            eopBasedataCompany.setDocAlterTime(new Date());
            eopBasedataCompany.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataCompany eopBasedataCompany = new EopBasedataCompany();
        eopBasedataCompany.setFdIsAvailable(Boolean.valueOf("true"));
		String fdFinancialSystem = EopBasedataFsscUtil.getSwitchValue("fdFinancialSystem");
		if (StringUtil.isNotNull(fdFinancialSystem)) {
			String[] property = fdFinancialSystem.split(";");
			requestContext.setAttribute("financialSystemList", ArrayUtil.convertArrayToList(property));
		}
        eopBasedataCompany.setDocCreateTime(new Date());
        eopBasedataCompany.setDocAlterTime(new Date());
        eopBasedataCompany.setDocCreator(UserUtil.getUser());
        eopBasedataCompany.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataCompany, requestContext);
        return eopBasedataCompany;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataCompany eopBasedataCompany = (EopBasedataCompany) model;
    }

    @Override
    public List<EopBasedataCompany> findByFdBudgetCurrency(EopBasedataCurrency fdBudgetCurrency) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataCompany.fdBudgetCurrency.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdBudgetCurrency.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataCompany> findByFdAccountCurrency(EopBasedataCurrency fdAccountCurrency) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataCompany.fdAccountCurrency.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdAccountCurrency.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataCompany> findByFdGroup(EopBasedataCompanyGroup fdGroup) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataCompany.fdGroup.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdGroup.getFdId());
        return this.findList(hqlInfo);
    }

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String fdCompanyId = requestInfo.getParameter("fdCompanyId");
		String type = requestInfo.getParameter("type");
		if("isCurrencySame".equals(type)){//判断公司的本位币和预算币是否相同
			EopBasedataCompany comp = (EopBasedataCompany) findByPrimaryKey(fdCompanyId, null, true);
			List<Map<String,String>> rtn = new ArrayList<Map<String,String>>();
			Map<String,String> data = new HashMap<String,String>();
			data.put("result", String.valueOf(comp.getFdAccountCurrency().getFdId().equals(comp.getFdBudgetCurrency().getFdId())));
			rtn.add(data);
			return rtn;
		}else if("getStandardCurrencyInfo".equals(type)&&StringUtil.isNotNull(fdCompanyId)){//查询公司本位币及相应的汇率
			EopBasedataCompany comp = (EopBasedataCompany) findByPrimaryKey(fdCompanyId, null, true);
			List<Map<String,String>> rtn = new ArrayList<Map<String,String>>();
			Map<String,String> data = new HashMap<String,String>();
			data.put("fdCurrencyId", comp.getFdAccountCurrency().getFdId());
			data.put("fdCurrencyName", comp.getFdAccountCurrency().getFdName());
			String hql = "select eopBasedataExchangeRate from "+EopBasedataExchangeRate.class.getName()
			+ " eopBasedataExchangeRate  left join eopBasedataExchangeRate.fdCompanyList company"
			+	" where eopBasedataExchangeRate.fdSourceCurrency.fdId=:fdSourceCurrencyId and eopBasedataExchangeRate.fdTargetCurrency.fdId=:fdTargetCurrencyId and eopBasedataExchangeRate.fdType=:fdType and eopBasedataExchangeRate.fdIsAvailable=:fdIsAvailable and (company.fdId=:fdCompanyId or company.fdId is null)";
			Query query = getBaseDao().getHibernateSession().createQuery(hql);
			query.setParameter("fdSourceCurrencyId", comp.getFdAccountCurrency().getFdId());
			query.setParameter("fdTargetCurrencyId", comp.getFdAccountCurrency().getFdId());
			query.setParameter("fdCompanyId", fdCompanyId);
			query.setParameter("fdIsAvailable", true);
			String value = EopBasedataFsscUtil.getSwitchValue("fdRateEnabled");
			if("true".equals(value)){
				query.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_CURRENT);
			}else{
				query.setParameter("fdType",EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
			}
			List<EopBasedataExchangeRate> list = query.list();
			Number rate = 1d;
			if(!ArrayUtil.isEmpty(list)) {
				list = EopBasedataFsscUtil.sortByCompany(list);
				rate = list.get(0).getFdRate();
			}
			data.put("fdExchangeRate", rate==null?"":rate.toString());
			query.setParameter("fdTargetCurrencyId", comp.getFdBudgetCurrency().getFdId());
			list = query.list();
			if(!ArrayUtil.isEmpty(list)) {
				list = EopBasedataFsscUtil.sortByCompany(list);
				rate = list.get(0).getFdRate();
			}
			data.put("fdBudgetRate", rate==null?"":rate.toString());
			String  fdDeduRule=EopBasedataFsscUtil.getDetailPropertyValue(fdCompanyId,"fdDeduRule");
			if(StringUtil.isNull(fdDeduRule)){
        		fdDeduRule="1";  //为空则默认为含税金额，保留原有逻辑
        	}
			data.put("fdDeduRule", fdDeduRule);
			rtn.add(data);
			return rtn;
		}else{//查询有效公司
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock(" new Map(eopBasedataCompany.id as value, eopBasedataCompany.fdName as text, '1' as isExpanded, '0' as isAutoFetch) ");
			hqlInfo.setWhereBlock("eopBasedataCompany.fdIsAvailable = :fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", true);
			return this.findList(hqlInfo);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public EopBasedataCompany getEopBasedataCompanyByCode(String fdCode)
			throws Exception {
		if(StringUtil.isNull(fdCode)){
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(" UPPER(eopBasedataCompany.fdCode) = :fdCode ");
		hqlInfo.setParameter("fdCode", fdCode.toUpperCase());
		List<EopBasedataCompany> list = this.findList(hqlInfo);
		return ArrayUtil.isEmpty(list)?null:list.get(0);
	}
	/***********************************************************
	 * 根据人员ID信息获取人员所在记账公司列表，去除重复项
	 ************************************************************/
	@Override
    public List<EopBasedataCompany> findCompanyByUserId(String fdUserId) throws Exception {
		List<EopBasedataCompany> rtnList = new ArrayList<EopBasedataCompany>();
		if(StringUtil.isNull(fdUserId)){
			return null;
		}
		SysOrgElement user = sysOrgCoreService.findByPrimaryKey(fdUserId);
		List<String> authIds = sysOrgCoreService.getOrgsUserAuthInfo(user).getAuthOrgIds();//人员相关组织架构信息
		HQLInfo hql = new HQLInfo();
		//查找对应的机构信息，若是机构还有上级，需将上级加入到列表，因为记账公司可能只配置了最顶级机构
		hql.setWhereBlock(HQLUtil.buildLogicIN("sysOrgElement.fdId", authIds)+" and sysOrgElement.fdOrgType=:fdOrgType");
		hql.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_ORG);
		List<SysOrgElement> orgList= sysOrgElementService.findList(hql);
		if(!ArrayUtil.isEmpty(orgList)){
			SysOrgElement parent=orgList.get(0).getFdParent();
			while(parent!=null){
				authIds.add(parent.getFdId());
				parent=parent.getFdParent();
			}
		}
		hql = new HQLInfo();
		hql.setWhereBlock(StringUtil.linkString("eopBasedataCompany.fdIsAvailable =:fdIsAvailable", " and ", HQLUtil.buildLogicIN("org.fdId", authIds)));
		hql.setParameter("fdIsAvailable", true);
		hql.setJoinBlock("left join eopBasedataCompany.fdEkpOrg org");
		hql.setOrderBy("eopBasedataCompany.fdId asc");
		Set<EopBasedataCompany> rtnSet = new HashSet<EopBasedataCompany>(this.findList(hql));// 用set封装，去除重复的记账公司
		rtnList=new ArrayList<EopBasedataCompany>(rtnSet);
		return rtnList;
	}

	/**
	 * 清除公司所属公司组信息
	 * @throws Exception
	 */
	@Override
	public void updateCompanyGroup() throws Exception {
		HQLInfo hqlInfo=new HQLInfo();
		List<EopBasedataCompany> companyList=this.findList(hqlInfo);
		for(EopBasedataCompany company:companyList){
			company.setFdGroup(null);
			this.update(company);
		}
	}

	@Override
	public void fillContactorInfo(JSONObject rtnData, String contactorId) throws Exception {
		SysOrgPerson contactor = (SysOrgPerson) EopBasedataUtil.getService(ISysOrgPersonService.class, null)
				.findByPrimaryKey(contactorId);
		rtnData.fluentPut("email", Optional.ofNullable(contactor.getFdEmail()).orElse("")).fluentPut("mobile_no",
				Optional.ofNullable(contactor.getFdMobileNo()).orElse(""));
	}

	@Override
	public void saveEnable(String ids, String modelName) throws Exception {
		super.saveEnable(ids, modelName);
		baseDataToBusiness(DataAction.enable, ids);
	}

	@Override
	public void saveDisable(String ids, String modelName) throws Exception {
		super.saveDisable(ids, modelName);
		baseDataToBusiness(DataAction.disable, ids);
	}

	private void baseDataToBusiness(DataAction action, String ids) throws Exception {
		List<IEopBasedataPullAndPushService> ppServiceList = EopBasedataUtil
				.getPullAndPushService(EopBasedataCompany.class.getName());
		if (!CollectionUtils.isEmpty(ppServiceList)) {
			IEopBasedataPullAndPushService ppservice = ppServiceList.get(0);
			List<EopBasedataCompany> baseCompanies = findByPrimaryKeys(ids.split(";"));
			for (EopBasedataCompany baseCompany : baseCompanies) {
				ppservice.asyncData2BizMod(action, baseCompany);
			}
		}
	}

	@Override
	public Page findPageForCase(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		StringBuilder whereBuilder = new StringBuilder();
		whereBuilder.append("eopBasedataCompany.fdIsAvailable = :fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);// 有效内部主体
		String keyWord = request.getParameter("q._keyword");
		if (StringUtil.isNotNull(keyWord)) {
			whereBuilder.append(
					" and (eopBasedataCompany.fdName like :keyword or eopBasedataCompany.fdCode like :keyword)");
			hqlInfo.setParameter("keyword", "%" + keyWord + "%");// 内部主体名称或编码
		}
		hqlInfo.setWhereBlock(whereBuilder.toString());
		return findPage(hqlInfo);
	}
}
