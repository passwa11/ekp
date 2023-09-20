package com.landray.kmss.fssc.fee.service.spring;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataItemBudget;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostCenterService;
import com.landray.kmss.eop.basedata.service.IEopBasedataExchangeRateService;
import com.landray.kmss.eop.basedata.service.IEopBasedataStandardService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetMatchService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonFeeService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.fee.constant.FsscFeeConstant;
import com.landray.kmss.fssc.fee.forms.FsscFeeMainForm;
import com.landray.kmss.fssc.fee.model.FsscFeeExpenseItem;
import com.landray.kmss.fssc.fee.model.FsscFeeLedger;
import com.landray.kmss.fssc.fee.model.FsscFeeMain;
import com.landray.kmss.fssc.fee.model.FsscFeeMapp;
import com.landray.kmss.fssc.fee.model.FsscFeeMobileConfig;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.fssc.fee.service.IFsscFeeLedgerService;
import com.landray.kmss.fssc.fee.service.IFsscFeeMainService;
import com.landray.kmss.fssc.fee.service.IFsscFeeTemplateService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.metadata.service.ISysMetadataService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.xform.base.model.SysFormTemplate;
import com.landray.kmss.sys.xform.service.DictLoadService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscCommonFeeServiceImp extends ExtendDataServiceImp implements IFsscCommonFeeService {
	private IFsscCommonBudgetOperatService fsscBudgetOperatService;
	public IFsscCommonBudgetOperatService getFsscBudgetOperatService() {
		if(fsscBudgetOperatService==null){
			fsscBudgetOperatService = (IFsscCommonBudgetOperatService) SpringBeanUtil.getBean("fsscBudgetOperatService");
		}
		return fsscBudgetOperatService;
	}
	
	private IFsscCommonBudgetMatchService fsscCommonBudgetService;

	public IFsscCommonBudgetMatchService getFsscCommonBudgetService() {
		if (fsscCommonBudgetService == null) {
			fsscCommonBudgetService = (IFsscCommonBudgetMatchService) SpringBeanUtil.getBean("fsscBudgetMatchService");
        }
		return fsscCommonBudgetService;
	}
	private IFsscFeeLedgerService fsscFeeLedgerService;

    public void setFsscFeeLedgerService(IFsscFeeLedgerService fsscFeeLedgerService) {
		this.fsscFeeLedgerService = fsscFeeLedgerService;
	}

	private IFsscFeeMainService fsscFeeMainService;
	
	public ICoreOuterService dispatchCoreService;

	public ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) SpringBeanUtil.getBean("dispatchCoreService");
		}
		return dispatchCoreService;
	}
	
	protected IBackgroundAuthService backgroundAuthService;
	
	public IBackgroundAuthService getBackgroundAuthService() {
		if (backgroundAuthService == null) {
			backgroundAuthService = (IBackgroundAuthService) SpringBeanUtil.getBean("backgroundAuthService");
		}
		return backgroundAuthService;
	}
	
	private IEopBasedataExchangeRateService eopBasedataExchangeService;
	public IEopBasedataExchangeRateService getEopBasedataExchangeService() {
		if (eopBasedataExchangeService == null) {
			eopBasedataExchangeService = (IEopBasedataExchangeRateService) SpringBeanUtil.getBean("eopBasedataExchangeRateService");
		}
		return eopBasedataExchangeService;
	}
	
	private IEopBasedataStandardService eopBasedataStandardService;

    public IEopBasedataStandardService getEopBasedataStandardService() {
    	if (eopBasedataStandardService == null) {
    		eopBasedataStandardService = (IEopBasedataStandardService) SpringBeanUtil.getBean("eopBasedataStandardService");
		}
		return eopBasedataStandardService;
	}
    
    private IEopBasedataCompanyService eopBasedataCompanyService;
    
	public IEopBasedataCompanyService getEopBasedataCompanyService() {
		if(eopBasedataCompanyService==null){
			eopBasedataCompanyService=(IEopBasedataCompanyService) SpringBeanUtil.getBean("eopBasedataCompanyService");
		}
		return eopBasedataCompanyService;
	}
	
	private IEopBasedataCostCenterService eopBasedataCostCenterService;

	public IEopBasedataCostCenterService getEopBasedataCostCenterService() {
		if(eopBasedataCostCenterService==null){
			eopBasedataCostCenterService=(IEopBasedataCostCenterService) SpringBeanUtil.getBean("eopBasedataCostCenterService");
		}
		return eopBasedataCostCenterService;
	}
	
	private IFsscFeeTemplateService fsscFeeTemplateService;
	
	public IFsscFeeTemplateService getFsscFeeTemplateService() {
		if (fsscFeeTemplateService == null) {
			fsscFeeTemplateService = (IFsscFeeTemplateService) SpringBeanUtil.getBean("fsscFeeTemplateService");
		}
		return fsscFeeTemplateService;
	}

	/**
     *	根据单号获取事前信息
     * @param docNumber 事前单号
     *
     * @return Map<String, Object>
     *          result:success 成功，failure，失败，
     *          feeMap Map<String, String>{
     *              fdId:id
     *              fdModelName:ModelName
     *              docNumber:单号
     *          }
     *          message：失败信息
     * @throws Exception
     */
    @Override
    public Map<String, Object> getFeeInfo(String docNumber) throws Exception{
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
            //设置传参必填字段
            if(StringUtil.isNull(docNumber)){
                KmssMessage msg = new KmssMessage("fssc-fee:message.fsscFee.setParameterError");
                throw new KmssRuntimeException(msg);
            }
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock(" fsscFeeMain.docNumber = :docNumber ");
            hqlInfo.setParameter("docNumber", docNumber);
            List<FsscFeeMain> mainList = fsscFeeMainService.findList(hqlInfo);
            Map<String, String> feeMap = new HashMap<String, String>();
            if(!ArrayUtil.isEmpty(mainList)){
                feeMap.put("fdId", mainList.get(0).getFdId());
                feeMap.put("fdModelName", ModelUtil.getModelClassName(mainList.get(0)));
                feeMap.put("docNumber", mainList.get(0).getDocNumber());
                feeMap.put("docSubject", mainList.get(0).getDocSubject());
            }
            rtnMap.put("feeMap", feeMap);
            rtnMap.put("result", "success");
        } catch (Exception e) {
            e.printStackTrace();
            rtnMap.put("result", "failure");
            rtnMap.put("message", e.toString());
        }

        return rtnMap;
    }

    public void setFsscFeeMainService(IFsscFeeMainService fsscFeeMainService) {
        this.fsscFeeMainService = fsscFeeMainService;
    }

	@Override
	public void getFeeTemplatePage(Page page,String keyWord) throws Exception {
		String hql = "select count(fdId) from com.landray.kmss.fssc.fee.model.FsscFeeTemplate ";
		if(StringUtil.isNotNull(keyWord)){
			hql = StringUtil.linkString(hql, " where ", "(fdName like :keyWord or docCategory.fdName like :keyWord)");
		}
		Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
		if(StringUtil.isNotNull(keyWord)){
			query.setParameter("keyWord", "%"+keyWord+"%");
		}
		Number cnt = (Number) query.uniqueResult();
		page.setTotal(cnt.intValue());
		page.setTotalrows(cnt.intValue());
		page.excecute();
		hql = "select new map(fdId as fdId,fdName as fdName,docCategory.fdName as docCategory) from com.landray.kmss.fssc.fee.model.FsscFeeTemplate";
		if(StringUtil.isNotNull(keyWord)){
			hql = StringUtil.linkString(hql, " where ", "(fdName like :keyWord or docCategory.fdName like :keyWord)");
		}
		query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
		if(StringUtil.isNotNull(keyWord)){
			query.setParameter("keyWord", "%"+keyWord+"%");
		}
		query.setFirstResult(page.getStart());
		query.setMaxResults(page.getRowsize());
		page.setList(query.list());
	}

	@Override
	public void getFeePage(Page page, Map<String, String> params) throws Exception {
		String hql = "select count(fdId) from com.landray.kmss.fssc.fee.model.FsscFeeMain where (fdIsClosed is null or fdIsClosed<>:fdIsClosed) and docStatus=:docStatus and docCreator.fdId in(:docCreatorId)";
		String keyWord = params.get("keyWord");
		if(StringUtil.isNotNull(keyWord)){
			hql = StringUtil.linkString(hql, " and ", "(docSubject like :keyWord or docNumber like :keyWord)");
		}
		String docTemplateId="";
		if(params.containsKey("docTemplateId")&&StringUtil.isNotNull(params.get("docTemplateId"))){
			docTemplateId = params.get("docTemplateId");
			hql = StringUtil.linkString(hql, " and ", "docTemplate.fdId=:docTemplateId");
		}
		if(params.containsKey("source")&&StringUtil.isNotNull(params.get("source"))){
			List<String> feeIds=getFeeIds();
			StringBuilder notInBlock=new StringBuilder();
			for(int i=0,size=feeIds.size();i<size;i++){
				notInBlock.append("'"+feeIds.get(i)+"'");
				if(i<size-1){
					notInBlock.append(",");
				}
			}
			if(StringUtil.isNotNull(notInBlock.toString())){
				hql = StringUtil.linkString(hql, " and ", "fdId not in("+notInBlock+")");
			}
		}
		Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
		if(StringUtil.isNotNull(keyWord)){
			query.setParameter("keyWord", "%"+keyWord+"%");
		}
		query.setParameter("fdIsClosed", true);
		query.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		if(StringUtil.isNotNull(docTemplateId)){
			query.setParameter("docTemplateId", docTemplateId);
		}
		List<String> personIds  = new ArrayList<String>();
		personIds.add(UserUtil.getUser().getFdId());
		if(params.containsKey("fdPersonId")) {
			personIds.add(params.get("fdPersonId"));
		}
		query.setParameterList("docCreatorId", personIds);
		Number cnt = (Number) query.uniqueResult();
		page.setTotal(cnt.intValue());
		page.setTotalrows(cnt.intValue());
		page.excecute();
		hql = "select new map(fdId as fdId,docSubject as docSubject,docNumber as docNumber,docCreator.fdName as docCreator,docCreateTime as docCreateTime) from com.landray.kmss.fssc.fee.model.FsscFeeMain where (fdIsClosed is null or fdIsClosed<>:fdIsClosed) and docStatus=:docStatus and docCreator.fdId in(:docCreatorId)";
		if(StringUtil.isNotNull(keyWord)){
			hql = StringUtil.linkString(hql, " and ", "(docSubject like :keyWord or docNumber like :keyWord)");
		}
		if(StringUtil.isNotNull(docTemplateId)){
			hql = StringUtil.linkString(hql, " and ", "docTemplate.fdId=:docTemplateId");
		}
		if(params.containsKey("source")&&StringUtil.isNotNull(params.get("source"))){
			List<String> feeIds=getFeeIds();
			StringBuilder notInBlock=new StringBuilder();
			for(int i=0,size=feeIds.size();i<size;i++){
				notInBlock.append("'"+feeIds.get(i)+"'");
				if(i<size-1){
					notInBlock.append(",");
				}
			}
			if(StringUtil.isNotNull(notInBlock.toString())){
				hql = StringUtil.linkString(hql, " and ", "fdId not in("+notInBlock+")");
			}
		}
		query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
		if(StringUtil.isNotNull(keyWord)){
			query.setParameter("keyWord", "%"+keyWord+"%");
		}
		query.setParameter("fdIsClosed", true);
		query.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		if(StringUtil.isNotNull(docTemplateId)){
			query.setParameter("docTemplateId", docTemplateId);
		}
		query.setParameterList("docCreatorId", personIds);
		query.setFirstResult(page.getStart());
		query.setMaxResults(page.getRowsize());
		page.setList(query.list());
	}

	//查找当前登录人员所有在途的报销单关联的事前申请
	public List<String> getFeeIds() throws Exception{
		List<String> ids=new ArrayList<>();
		List<String> feeIds = new ArrayList<>();
		if(FsscCommonUtil.checkHasModule("/fssc/expense/")){
			List<String> feeIdsByExpense=fsscFeeMainService.getBaseDao().getHibernateSession()
					.createQuery("select t.fdFeeIds from FsscExpenseMain t where (t.docStatus=:examine) and t.docCreator.fdId=:userId and t.fdIsCloseFee=:fdIsCloseFee")
					.setParameter("examine", SysDocConstant.DOC_STATUS_EXAMINE)
					.setParameter("userId", UserUtil.getUser().getFdId())
					.setParameter("fdIsCloseFee", Boolean.TRUE).list();
			if(!feeIdsByExpense.isEmpty()) {
				feeIds.addAll(feeIdsByExpense);
			}
		}
		if(FsscCommonUtil.checkHasModule("/fssc/payment/")) {
			List<String> feeIdsByPayment=fsscFeeMainService.getBaseDao().getHibernateSession()
					.createQuery("select t.fdFeeIds from FsscPaymentMain t where (t.docStatus=:examine) and t.docCreator.fdId=:userId and t.fdIsCloseFee=:fdIsCloseFee")
					.setParameter("examine", SysDocConstant.DOC_STATUS_EXAMINE)
					.setParameter("userId", UserUtil.getUser().getFdId())
					.setParameter("fdIsCloseFee", Boolean.TRUE).list();
			if(!feeIdsByPayment.isEmpty()) {
				feeIds.addAll(feeIdsByPayment);
			}
		}
		if(!ArrayUtil.isEmpty(feeIds)){
			for(String id:feeIds){
				ids.addAll(ArrayUtil.convertArrayToList(id.split(";")));
			}
		}
		return ids;
	}


	@Override
	public JSONObject getFeeLedgerData(JSONObject param) throws Exception {
		JSONObject rtn = new JSONObject();
		String fdFeeIds = param.getString("fdFeeIds");
		List<String> ids = Arrays.asList(fdFeeIds.split(";"));
		FsscFeeMain main = (FsscFeeMain) fsscFeeMainService.findByPrimaryKey(ids.get(0), null, true);
		rtn.put("fdForbid", main.getDocTemplate().getFdForbid());
		JSONArray data = (JSONArray) param.get("data");
		Map<String,Object> cache = new HashMap<String,Object>();
		String hql = "select item.fdCategory.fdId from "+EopBasedataItemBudget.class.getName()+" item left join item.fdCompanyList comp left join item.fdItems ex where (comp.fdId=:fdCompanyId or comp.fdId is null) and ex.fdId=:fdExpenseItemId";
		Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
		for(int i=0,j=data.size();i<j;i++){
			JSONObject feeData = new JSONObject();
			JSONArray feeArray = new JSONArray();
			JSONObject detail = data.getJSONObject(i);
			String fdExpenseItemId = detail.getString("fdExpenseItemId");
			String fdCompanyId = detail.getString("fdCompanyId");
			String fdModelId = detail.optString("fdModelId","");
			List<String> fdSchemeId = null;
			if(cache.containsKey(fdExpenseItemId)){
				fdSchemeId = (List<String>) cache.get(fdExpenseItemId);
			}else{
				//通过费用类型和公司查找对应预算方案
				query.setParameter("fdCompanyId", fdCompanyId);
				query.setParameter("fdExpenseItemId", fdExpenseItemId);
				fdSchemeId =  query.list();
			}
			//如果没有找到对应的预算方案，则返回空的
			if(ArrayUtil.isEmpty(fdSchemeId)){
				feeData.put(detail.get("fdDetailId"), feeArray);
				continue;
			}
			cache.put(fdExpenseItemId, fdSchemeId);
			//补全预算科目
			String fdCostCenterId = "";
			String fdBudgetItemId = "";
			if(detail.containsKey("fdCostCenterId")){
				fdCostCenterId = detail.getString("fdCostCenterId");
			}
			if(cache.containsKey(fdCostCenterId+fdExpenseItemId)){
				fdBudgetItemId = (String) cache.get(fdCostCenterId+fdExpenseItemId);
			}else{
				EopBasedataCostCenter cost = (EopBasedataCostCenter) fsscFeeLedgerService.findByPrimaryKey(fdCostCenterId, EopBasedataCostCenter.class, true);
				cache.put(fdCostCenterId, cost);
				EopBasedataExpenseItem exp = (EopBasedataExpenseItem) fsscFeeLedgerService.findByPrimaryKey(fdExpenseItemId, EopBasedataExpenseItem.class, true);
				fdBudgetItemId = EopBasedataFsscUtil.getBudgetItemIds(cost, exp);
				cache.put(fdCostCenterId+fdExpenseItemId, fdBudgetItemId);
			}
			detail.put("fdBudgetItemId", fdBudgetItemId);
			//补全公司组、成本中心组
			if(cache.get(fdCostCenterId)!=null){
				EopBasedataCostCenter cost = (EopBasedataCostCenter)cache.get(fdCostCenterId);
				if(cost.getFdParent()!=null){
					detail.put("fdCostCenterGroupId", cost.getFdParent().getFdId());
				}
			}
			if(cache.get(fdCompanyId)!=null){
				EopBasedataCompany comp = (EopBasedataCompany) cache.get(fdCompanyId);
				if(comp.getFdGroup()!=null){
					detail.put("fdCompanyGroupId", comp.getFdGroup().getFdId());
				}
			}else{
				EopBasedataCompany comp = (EopBasedataCompany)fsscFeeLedgerService.findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
				cache.put(fdCompanyId, comp);
				if(comp!=null&&comp.getFdGroup()!=null){
					detail.put("fdCompanyGroupId", comp.getFdGroup().getFdId());
				}
			}
			HQLInfo hqlInfo = new HQLInfo();
			String where = "fsscFeeLedger.fdModelName=:fdModelName and fsscFeeLedger.fdModelId in(:ids)";
			hqlInfo.setParameter("fdModelName",FsscFeeMain.class.getName());
			hqlInfo.setParameter("ids",ids);
			Map<String,List<String>> properties = null;
			for(String sc:fdSchemeId) {
				if(cache.containsKey(fdSchemeId)){
					properties = (Map<String, List<String>>) cache.get(fdSchemeId);
				}else{
					//根据预算方案获取对应属性
					properties = EopBasedataFsscUtil.getPropertyByScheme(sc);
					cache.put(sc, properties);
				}
				List<String> inPropertyList = properties.get("inPropertyList");
				for(String str:inPropertyList){
					String prop = str+"Id";
					where = StringUtil.linkString(where, " and ", "fsscFeeLedger."+prop+"=:"+prop);
					hqlInfo.setParameter(prop,detail.get(prop));
				}
				List<String> notInPropertyList = properties.get("notInPropertyList");
				for(String str:notInPropertyList){
					String prop = str+"Id";
					where = StringUtil.linkString(where, " and ", "fsscFeeLedger."+prop+" is null");
				}
				hqlInfo.setWhereBlock(where);
				hqlInfo.setOrderBy("fsscFeeLedger.docCreateTime desc");
				List<FsscFeeLedger> list = fsscFeeLedgerService.findList(hqlInfo);
				for(FsscFeeLedger ledger:list){
					JSONObject le = new JSONObject();
					le.put("fdLedgerId", ledger.getFdId());
					for(HQLParameter hp:hqlInfo.getParameterList()){
						le.put(hp.getName(), hp.getValue());
					}
					JSONObject money = null;
					//获取台账的总额、可使用额等
					if(cache.containsKey(ledger.getFdId())){
						money = (JSONObject) cache.get(ledger.getFdId());
					}else{
						money = fsscFeeLedgerService.getLedgerMoney(ledger.getFdId(),fdModelId);
						cache.put(ledger.getFdId(), money);
					}
					le.put("fdIsUseBudget", ledger.getFdIsUseBudget());
					le.putAll(money);
					feeArray.add(le);
				}
			}
			rtn.put(detail.get("fdDetailId"), feeArray);
		}
		return rtn;
	}
	
	/**
	 * 根据事前ID获取事前申请总额，用于借款控制
	 */
	@Override
	public JSONObject getFeeTotalMoney(JSONObject param) throws Exception {
		JSONObject rtn = new JSONObject();
		String fdFeeId = param.getString("fdFeeId");
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock("fsscFeeLedger.fdModelId=:fdModelId and fsscFeeLedger.fdType=:fdType");
		hqlInfo.setParameter("fdModelId", fdFeeId);
		hqlInfo.setParameter("fdType", FsscFeeConstant.FSSC_FEE_LEDGER_TYPE_INIT);
		hqlInfo.setSelectBlock("sum(fsscFeeLedger.fdStandardMoney)");
		List<Object> moneyList=fsscFeeLedgerService.findList(hqlInfo);
		if(!ArrayUtil.isEmpty(moneyList)){
			rtn.put("totalMoney", moneyList.get(0));
		}
		return rtn;
	}

	@Override
	public JSONObject updateFsscFeeLedger(JSONArray param) throws Exception {
		if(param==null||param.size()==0){
			return null;
		}
		this.deleteFeeLedgerByDetail(param.getJSONObject(0).getString("fdModelId"),param.getJSONObject(0).getString("fdModelName"),param.getJSONObject(0).getString("fdDetailId"));
		SysDictModel dict = SysDataDict.getInstance().getModel(FsscFeeLedger.class.getName());
		for(int i=0;i<param.size();i++){
			JSONObject obj = param.getJSONObject(i);
			FsscFeeLedger ledger = new FsscFeeLedger();
			for(Iterator it = obj.keySet().iterator();it.hasNext();){
				String key = (String) it.next();
				if(dict.getPropertyMap().containsKey(key)){
					PropertyUtils.setProperty(ledger, key, obj.get(key));
				}
			}
			fsscFeeLedgerService.add(ledger);
		}
		return null;
	}
	
	@Override
	public JSONObject addFsscFeeLedger(JSONArray param) throws Exception {
		SysDictModel dict = SysDataDict.getInstance().getModel(FsscFeeLedger.class.getName());
		for(int i=0;i<param.size();i++){
			JSONObject obj = param.getJSONObject(i);
			Double fdMoney=(obj.containsKey("fdBudgetMoney")&&obj.get("fdBudgetMoney")!=null)?obj.getDouble("fdBudgetMoney"):0.0;
			if(fdMoney<=0) {
				continue;
			}
			FsscFeeLedger ledger = new FsscFeeLedger();
			for(Iterator it = obj.keySet().iterator();it.hasNext();){
				String key = (String) it.next();
				if(dict.getPropertyMap().containsKey(key)){
					PropertyUtils.setProperty(ledger, key, obj.get(key));
				}
			}
			fsscFeeLedgerService.add(ledger);
		}
		return null;
	}
	
	public void deleteFeeLedgerByDetail(String fdModelId,String fdModelName,String fdDetailId)throws Exception{
		String hql = "delete from "+FsscFeeLedger.class.getName() +" where fdModelId=:fdModelId and fdModelName=:fdModelName and fdDetailId=:fdDetailId";
		Query query = fsscFeeLedgerService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdModelId", fdModelId);
		query.setParameter("fdModelName", fdModelName);
		query.setParameter("fdDetailId", fdDetailId);
		query.executeUpdate();
	}
	
	
	@Override
    public void deleteFeeLedgerByModel(String fdModelId, String fdModelName)throws Exception{
		String hql = "delete from "+FsscFeeLedger.class.getName() +" where fdModelId=:fdModelId and fdModelName=:fdModelName";
		Query query = fsscFeeLedgerService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdModelId", fdModelId);
		query.setParameter("fdModelName", fdModelName);
		query.executeUpdate();
	}

	@Override
	public JSONObject updateFsscFeeBudget(String fdModelId, String fdModelName,String fdCompanyId) throws Exception {
		//更新占用记录为已使用
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fsscFeeLedger.fdModelId=:fdModelId and fsscFeeLedger.fdModelName = :fdModelName");
		hqlInfo.setParameter("fdModelId",fdModelId);
		hqlInfo.setParameter("fdModelName",fdModelName);
		List<FsscFeeLedger> list = fsscFeeLedgerService.findList(hqlInfo);
		Map<String,List<FsscFeeLedger>> ledgerMap = new HashMap<String,List<FsscFeeLedger>>();
		List<String> ids = new ArrayList<String>();
		for(FsscFeeLedger ledger:list){
			ledger.setFdType(FsscFeeConstant.FSSC_FEE_LEDGER_TYPE_USED);
			ids.add(ledger.getFdLedgerId());
			if(ledgerMap.containsKey(ledger.getFdLedgerId())){
				ledgerMap.get(ledger.getFdLedgerId()).add(ledger);
			}else{
				List<FsscFeeLedger> llist = new ArrayList<FsscFeeLedger>();
				llist.add(ledger);
				ledgerMap.put(ledger.getFdLedgerId(), llist);
			}
		}
		if(ArrayUtil.isEmpty(ids)){
			return  null;
		}
		fsscFeeLedgerService.getBaseDao().saveOrUpdateAll(list);
		//查找对应的事前台账
		hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fsscFeeLedger.fdId in(:ids)");
		hqlInfo.setParameter("ids",ids);
		List<FsscFeeLedger> initList = fsscFeeLedgerService.findList(hqlInfo);
		JSONArray updateInfo = new JSONArray();
		// 查询对应事前占用的预算
		for(FsscFeeLedger init:initList){
			JSONObject obj = new JSONObject();
			obj.put("fdModelId", init.getFdModelId());
			obj.put("fdModelName", init.getFdModelName());
			obj.put("fdDetailId", init.getFdDetailId());
			obj.put("fdCompanyId", fdCompanyId);
			obj.put("fdType", FsscFeeConstant.FSSC_FEE_LEDGER_TYPE_USING);
			JSONObject budgetObject = getFsscBudgetOperatService().matchFsscBudgetExecute(obj);
			JSONArray budgets = budgetObject.getJSONArray("data");
			List<FsscFeeLedger> useList = ledgerMap.get(init.getFdId());
			for(int i=0;i<budgets.size();i++){
				JSONObject budget = budgets.getJSONObject(i);
				if(budget.isEmpty()){
					continue;
				}
				Double fdMoney = budget.getDouble("fdMoney");
				//循环报销占用的数据，拆分为多条已使用记录
				for(FsscFeeLedger use:useList){
					JSONObject data = new JSONObject();
					data.putAll(budget);
					//金额为当前报销使用的金额
					data.put("fdMoney", use.getFdBudgetMoney());
					//标记为已使用
					data.put("fdType", FsscFeeConstant.FSSC_FEE_LEDGER_TYPE_USED);
					//扣除当前金额
					fdMoney = FsscNumberUtil.getSubtraction(fdMoney, use.getFdBudgetMoney());
					data.put("fdModelId", fdModelId);
					data.put("fdModelName", fdModelName);
					data.put("fdId", null);
					updateInfo.add(data);
				}
				//如果事前还有剩余金额，重新插入一条占用记录
				if(fdMoney>0){
					JSONObject data = new JSONObject();
					data.putAll(budget);
					data.put("fdMoney", fdMoney);
					data.put("fdId", null);
					updateInfo.add(data);
				}
				budget.put("fdModelId", init.getFdModelId());
				budget.put("fdModelName", init.getFdModelName());
				getFsscBudgetOperatService().deleteFsscBudgetExecute(budget);
			}
		}
		getFsscBudgetOperatService().addFsscBudgetExecute(updateInfo);
		return null;
	}
	@Override
	public Page getFeePageByLoan(Map<String, Object> params) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		if(params.containsKey("orderby")){
			hqlInfo.setOrderBy(params.get("orderby")+"");
		}else{
			hqlInfo.setOrderBy("fdId desc");
		}
		hqlInfo.setPageNo((Integer)params.get("pageno"));
		hqlInfo.setRowSize((Integer)params.get("rowsize"));
		StringBuilder where = new StringBuilder();
		where.append(" fsscFeeMain.docStatus = :docStatus ");
		hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		where.append(" and (fsscFeeMain.fdIsClosed = :fdIsClosed or fsscFeeMain.fdIsClosed is null) ");
		hqlInfo.setParameter("fdIsClosed", Boolean.FALSE);
		String keyWord = params.get("keyWord")+"";
		if(StringUtil.isNotNull(keyWord) && !"null".equals(keyWord)){
			where.append(" and (upper(fsscFeeMain.docNumber) like :keyWord or upper(fsscFeeMain.docSubject) like :keyWord) ");
			hqlInfo.setParameter("keyWord", "%"+keyWord.toUpperCase()+"%");
		}
		// #127231
		List<String> personIds  = new ArrayList<String>();
		personIds.add(UserUtil.getUser().getFdId());
		if(params.containsKey("fdPersonId")) {
			personIds.add(params.get("fdPersonId").toString());
		}
		where.append(" and fsscFeeMain.docCreator.fdId in (:docCreator )");
		hqlInfo.setParameter("docCreator", personIds);
		hqlInfo.setWhereBlock(where.toString());
		hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
		return fsscFeeMainService.findPage(hqlInfo);
	}
	
	@Override
	public Page getFeePageByCondition(Page page, Map<String, Object> params) throws Exception {
		String hql = "select count(fdId) from com.landray.kmss.fssc.fee.model.FsscFeeMain " +
			" where docStatus = :docStatus " +
			" and ( fdIsClosed = :fdIsClosed or fdIsClosed is null ) " +
			" and docCreator.fdId in( :docCreatorId)";
		String keyWord = params.get("keyWord")+ "";
		if(StringUtil.isNotNull(keyWord) && !"null".equals(keyWord)){
			hql = StringUtil.linkString(hql, " and ", "(docSubject like :keyWord or docNumber like :keyWord)");
		}
		Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
		if(StringUtil.isNotNull(keyWord) && !"null".equals(keyWord)){
			query.setParameter("keyWord", "%"+keyWord+"%");
		}
		query.setParameter("fdIsClosed", Boolean.FALSE);
		query.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		List<String> personIds  = new ArrayList<String>();
		personIds.add(UserUtil.getUser().getFdId());
		if(params.containsKey("fdPersonId")) {
			personIds.add(params.get("fdPersonId").toString());
		}
		query.setParameterList("docCreatorId", personIds);
		Number cnt = (Number) query.uniqueResult();
		page.setTotal(cnt.intValue());
		page.setTotalrows(cnt.intValue());
		page.excecute();
		hql = "select fsscFeeMain " +
			" from com.landray.kmss.fssc.fee.model.FsscFeeMain  fsscFeeMain " +
			" where fsscFeeMain.docStatus = :docStatus " +
			" and ( fsscFeeMain.fdIsClosed = :fdIsClosed or fsscFeeMain.fdIsClosed is null ) " +
			" and fsscFeeMain.docCreator.fdId in( :docCreatorId)";
		if(StringUtil.isNotNull(keyWord) && !"null".equals(keyWord)){
			hql = StringUtil.linkString(hql, " and ", "(docSubject like :keyWord or docNumber like :keyWord)");
		}
		if(params.containsKey("orderby")){
			hql = StringUtil.linkString(hql, "  ", "  order by " + params.get("orderby"));
		} else {
			hql = StringUtil.linkString(hql, "  ", " order by fdId desc ");
		}
		query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
		if(StringUtil.isNotNull(keyWord) && !"null".equals(keyWord)){
			query.setParameter("keyWord", "%"+keyWord+"%");
		}
		query.setParameter("fdIsClosed", Boolean.FALSE);
		query.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		query.setParameterList("docCreatorId", personIds);
		query.setFirstResult(page.getStart());
		query.setMaxResults(page.getRowsize());
		List<FsscFeeMain> list = (List<FsscFeeMain>)query.list();
		page.setList(list);
		return  page;
	}

	@Override
	public Boolean isMonthStandardUsed(String fdPersonId, String fdExpenseItemId, Date fdHappenDate,String fdDetailId) throws Exception {
		String hql = "from "+FsscFeeLedger.class.getName()+" where fdPersonId=:fdPersonId and fdExpenseItemId=:fdExpenseItemId and fdStartDate < :fdHappenDate and fdEndDate > :fdHappenDate";
		Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdExpenseItemId", fdExpenseItemId);
		query.setParameter("fdPersonId", fdPersonId);
		query.setParameter("fdHappenDate", fdHappenDate);
		return !ArrayUtil.isEmpty(query.list());
	}
	@Override
	public List<EopBasedataExpenseItem> getExpenseItemByCategory(String fdCompanyId, String categoryId) throws Exception {
		String hql = "select main from "+FsscFeeExpenseItem.class.getName()+" main left join main.fdCompany company left join main.fdTemplate template where (company.fdId=:fdCompanyId or company is null) and template.fdId=:docCategoryId";
		Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdCompanyId", fdCompanyId);
		query.setParameter("docCategoryId", categoryId);
		List<FsscFeeExpenseItem> list = query.list();
		List<EopBasedataExpenseItem> rtn = new ArrayList<EopBasedataExpenseItem>();
		for(FsscFeeExpenseItem config:list){
			if(!ArrayUtil.isEmpty(config.getFdItemList())){
				ArrayUtil.concatTwoList(config.getFdItemList(), rtn);
			}
		}
		return rtn;
	}

	@Override
	public JSONObject getFeeInfoById(String fdFeeMainId) throws Exception {
		FsscFeeMain main = (FsscFeeMain) fsscFeeMainService.findByPrimaryKey(fdFeeMainId, null, true);
		JSONObject rtn = new JSONObject();
		rtn.put("fdId", fdFeeMainId);
		rtn.put("docNumber", main.getDocNumber());
		rtn.put("docSubject", main.getDocSubject());
		return rtn;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void saveFeeFromObject(final JSONObject data) throws Exception {
		final String fdTemplateId = data.getString("fdTemplateId");
		SysOrgPerson user = UserUtil.getUser();
		final Map<String, Object> formMap = new HashMap<String, Object>();
		final FsscFeeTemplate temp = (FsscFeeTemplate) fsscFeeMainService.getBaseDao().findByPrimaryKey(fdTemplateId, FsscFeeTemplate.class, true);
		List<FsscFeeMobileConfig> configs = temp.getFdConfig();
		List<FsscFeeMobileConfig> details = new ArrayList<FsscFeeMobileConfig>(); 
		List<FsscFeeMobileConfig> mains = new ArrayList<FsscFeeMobileConfig>(); 
		for(FsscFeeMobileConfig config:configs){
			if(FsscFeeConstant.FSSC_FEE_MOBILE_CONFIG_FIELD_POSITION_MAIN.equals(config.getFdFieldPosition())){
				mains.add(config);
			}else{
				details.add(config);
			}
		}
		final Map detailObject = new HashMap();
		//处理主表字段
		Iterator<String> it = data.keys();
		for(;it.hasNext();){
			String key = it.next();
			for(FsscFeeMobileConfig config:mains){
				setProperty(formMap,config,data,key,details,detailObject,data);
			}
		}
		System.out.println(formMap.toString());
		// 启动流程
		getBackgroundAuthService().switchUser(user.getFdNo(), new Runner() {
			@Override
            public Object run(Object parameter) throws Exception {
				IExtendDataService targetService = (IExtendDataService) SpringBeanUtil
						.getBean("fsscFeeMainService");
				RequestContext requestContext = new RequestContext();
				requestContext.setParameter("i.docTemplate", fdTemplateId);
				requestContext.setAttribute(
						ISysMetadataService.INIT_MODELDATA_KEY, formMap);
				FsscFeeMainForm form_ = (FsscFeeMainForm) new FsscFeeMainForm();
				form_.setDocTemplateId(fdTemplateId);
				IExtendForm subForm = targetService.initFormSetting(form_,
						requestContext);
				form_ = (FsscFeeMainForm) subForm;
				form_.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
				form_.setFdId((String)formMap.get("fdId"));
				Iterator<String> it = detailObject.keySet().iterator();
				for(;it.hasNext();){
					String key = it.next();
					form_.getExtendDataFormInfo().getFormData().put(key, detailObject.get(key));
				}
				form_.setDocCreateTime(DateUtil.convertDateToString(new Date(),
						DateUtil.PATTERN_DATE));
				//form_.setFdId(data.getString("fdId"));
				String id = targetService.add(form_, requestContext);
				getEopBasedataExchangeService().getBaseDao().getHibernateSession().createQuery("update SysAttMain set fdModelId=:fdModelId where fdModelId=:fdId")
				.setParameter("fdModelId", id).setParameter("fdId", data.getString("fdId")).executeUpdate();
				return form_;
			}
		}, null);
	}

	private void setProperty(Map<String, Object> formMap, FsscFeeMobileConfig config, JSONObject data,String key,List<FsscFeeMobileConfig> details,Map detailObject,JSONObject src) throws Exception {
		String _key_ = key;
		if(key.indexOf(".")>-1){
			_key_ = key.split("\\.")[1];
		}
		//保存预算信息及标准信息
		if(key.endsWith("_budget_status")||key.endsWith("_standard_status")||key.endsWith("_budget_info")){
			formMap.put(_key_, data.get(key));
			return;
		}
		if(config.getFdTemplateFieldId().indexOf(key)==-1){
			return;
		}
		if(FsscFeeConstant.FSSC_FEE_MOBILE_CONFIG_FIELD_TYPE_NORMAL.equals(config.getFdFieldType())||FsscFeeConstant.FSSC_FEE_MOBILE_CONFIG_FIELD_TYPE_DATETIME.equals(config.getFdFieldType())){
			//本位币
			if("9".equals(config.getFdInitOption())){
				try {
					String baseOnId = config.getFdBaseOnId();
					baseOnId = baseOnId.replaceAll("\\$", "");
					String currencyField = baseOnId.split(";")[0];
					if(currencyField.indexOf(".")>-1){
						currencyField = currencyField.split("\\.")[1];
					}
					String moneyFiled = baseOnId.split(";")[1];
					if(moneyFiled.indexOf(".")>-1){
						moneyFiled = moneyFiled.split("\\.")[1];
					}
					Double rate = (Double) formMap.get(currencyField+"_cost_rate");
					Object money = formMap.get(moneyFiled);
					formMap.put(_key_, FsscNumberUtil.getMultiplication(rate, Double.valueOf(money.toString()), 2));
				} catch (Exception e) {
				}
				return;
			}
			formMap.put(_key_, data.get(key));
		}
		if(FsscFeeConstant.FSSC_FEE_MOBILE_CONFIG_FIELD_TYPE_OBJECT.equals(config.getFdFieldType())){
			Object value = data.get(key);
			if(value instanceof JSONArray){
				JSONArray arr = (JSONArray) value;
				formMap.put(_key_, arr.size()>0?arr.get(0):"");
			}else{
				String id = (String) data.get(key);
				if(id!=null&&id.indexOf("__")>-1){
					id = id.split("__")[1];
				}
				formMap.put(_key_, id);
			}
			formMap.put(_key_+"_name", data.get(key+".name"));
			//币种，需要带出汇率
			if("5".equals(config.getFdInitOption())){
				String fdCurrencyId = (String) formMap.get(_key_);
				String baseOnId = config.getFdBaseOnId().replaceAll("\\$", "");
				if(src.containsKey(baseOnId)){
					Object fdCompanyId = src.get(baseOnId);
					String id ="";
					if(fdCompanyId instanceof JSONArray){
						id = ((JSONArray)fdCompanyId).getString(0);
					}else{
						id = (String) fdCompanyId;
					}
					Double budgetRate = eopBasedataExchangeService.getBudgetRate(fdCurrencyId, id);
					Double costRate = eopBasedataExchangeService.getExchangeRate(fdCurrencyId, id);
					formMap.put(_key_+"_cost_rate", costRate);
					formMap.put(_key_+"_budget_rate", budgetRate);
				}
			}
		}
		if(FsscFeeConstant.FSSC_FEE_MOBILE_CONFIG_FIELD_TYPE_ORG.equals(config.getFdFieldType())){
			if(config.getFdIsMulti()!=null&&config.getFdIsMulti()){
				List<Map<String,String>> list = new ArrayList<Map<String,String>>();
				String ids = (String) data.get(key);
				String names = (String) data.get(key+".name");
				if(StringUtil.isNotNull(ids)){
					String[] dd = ids.split(";");
					String[] nn = names.split(";");
					for(int i=0;i<dd.length;i++){
						Map<String,String> org = new HashMap<String,String>();
						org.put("id", dd[i]);
						org.put("name", nn[i]);
						list.add(org);
					}
				}
				formMap.put(_key_, list);
			}else{
				Map<String,String> org = new HashMap<String,String>();
				org.put("id", (String) data.get(key));
				org.put("name", (String) data.get(key+".name"));
				formMap.put(_key_, org);
			}
		}
		//处理明细字段
		if(FsscFeeConstant.FSSC_FEE_MOBILE_CONFIG_FIELD_TYPE_DETAIL.equals(config.getFdFieldType())){
			List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
			JSONArray dataArray = data.getJSONArray(key);
			for(int i=0;i<dataArray.size();i++){
				Map<String,Object> detailMap = new HashMap<String,Object>();
				JSONObject detailData = dataArray.getJSONObject(i);
				for(FsscFeeMobileConfig con:details){
					//如果是隐藏字段，则前台不会传值过来
					if("3".equals(con.getFdShowStatus())){
						setProperty(detailMap,con,detailData,con.getFdTemplateFieldId().replaceAll("\\$", ""),null,null,src);
						continue;
					}
					Iterator<String> it = detailData.keys();
					for(;it.hasNext();){
						String _key = it.next();
						setProperty(detailMap,con,detailData,_key,null,null,src);
					}
				}
				detailList.add(detailMap);
			}
			detailObject.put(_key_, detailList);
		}
	}

	@Override
	public JSONArray getFeeList(String fdPersonId) throws Exception {
		JSONArray data = new JSONArray();
		String hql = "from FsscFeeMain main where main.docCreator.fdId=:fdPersonId and main.docTemplate.fdIsMobile=:fdIsMobile";
		hql+=" and (main.docStatus=:draft or  main.docStatus=:examine or main.docStatus=:publish)";
		
		hql+=" order by main.fdId desc";
		List<FsscFeeMain> list = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdPersonId", UserUtil.getUser().getFdId())
				.setParameter("fdIsMobile", true)
				.setParameter("draft", SysDocConstant.DOC_STATUS_DRAFT)
				.setParameter("examine", SysDocConstant.DOC_STATUS_EXAMINE)
				.setParameter("publish", SysDocConstant.DOC_STATUS_PUBLISH).list();
		for(FsscFeeMain main:list){
			JSONObject obj = new JSONObject();
			obj.put("title", main.getDocSubject());
			obj.put("date", DateUtil.convertDateToString(main.getDocCreateTime(), DateUtil.PATTERN_DATE));
			obj.put("status", EnumerationTypeUtil.getColumnEnumsLabel("common_status", main.getDocStatus()));
			obj.put("clazz", "status"+main.getDocStatus());
			obj.put("id", main.getFdId());
			obj.put("count", fsscFeeMainService.getTotalMoney(main.getFdId()));
			obj.put("link", "/fee/detailView?id="+main.getFdId());
			data.add(obj);
		}
		return data;
	}

	@Override
	public JSONObject getFeeById(String fdId) throws Exception {
		JSONObject data = new JSONObject();
		FsscFeeMain main = (FsscFeeMain) fsscFeeMainService.getBaseDao().findByPrimaryKey(fdId, FsscFeeMain.class, true);
		Map values = main.getExtendDataModelInfo().getModelData();
		FsscFeeTemplate temp = main.getDocTemplate();
		List<FsscFeeMobileConfig> details = new ArrayList<FsscFeeMobileConfig>(); 
		List<FsscFeeMobileConfig> mains = new ArrayList<FsscFeeMobileConfig>(); 
		for(FsscFeeMobileConfig config:temp.getFdConfig()){
			if(FsscFeeConstant.FSSC_FEE_MOBILE_CONFIG_FIELD_POSITION_MAIN.equals(config.getFdFieldPosition())){
				mains.add(config);
			}else{
				details.add(config);
			}
		}
		for(FsscFeeMobileConfig config:mains){
			getFeeMainValues(config,values,data,details,false);
		}
		List<SysAttMain> atts = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery("from SysAttMain where fdModelId=:fdId").setParameter("fdId", fdId).list();
		JSONArray arr = new JSONArray();
		for(SysAttMain att:atts){
			JSONObject a = new JSONObject();
			a.put("title", att.getFdFileName());
			a.put("id", att.getFdId());
			arr.add(a);
		}
		data.put("attachments", arr);
		return data;
	}
	
	private void getFeeMainValues(FsscFeeMobileConfig config,Map values,JSONObject data,List<FsscFeeMobileConfig> details,Boolean isDetail){
		String key = config.getFdTemplateFieldId().replaceAll("\\$", "");
		if(isDetail){
			key = key.split("\\.")[1];
		}
		if(!values.containsKey(key)|| "3".equals(config.getFdShowStatus())){
			return;
		}
		if(FsscFeeConstant.FSSC_FEE_MOBILE_CONFIG_FIELD_TYPE_NORMAL.equals(config.getFdFieldType())){
			data.put(key, values.get(key));
		}
		if(FsscFeeConstant.FSSC_FEE_MOBILE_CONFIG_FIELD_TYPE_DATETIME.equals(config.getFdFieldType())){
			Date date = (Date) values.get(key);
			data.put(key, DateUtil.convertDateToString(date, DateUtil.PATTERN_DATE));
		}
		if(FsscFeeConstant.FSSC_FEE_MOBILE_CONFIG_FIELD_TYPE_OBJECT.equals(config.getFdFieldType())){
			data.put(key, values.get(key+"_name"));
		}
		if(FsscFeeConstant.FSSC_FEE_MOBILE_CONFIG_FIELD_TYPE_ORG.equals(config.getFdFieldType())){
			Map<String,Object> map = (Map<String, Object>) values.get(key);
			data.put(key, map.get("name"));
		}
		if(FsscFeeConstant.FSSC_FEE_MOBILE_CONFIG_FIELD_TYPE_DETAIL.equals(config.getFdFieldType())){
			List<Map<String,Object>> list = (List<Map<String, Object>>) values.get(key);
			JSONArray detailList = new JSONArray();
			for(Map<String,Object> val:list){
				JSONObject detailData = new JSONObject();
				for(FsscFeeMobileConfig con:details){
					getFeeMainValues(con,val,detailData,details,true);
				}
				for(Iterator it=val.keySet().iterator();it.hasNext();){
					if(detailData.containsKey("fdBudgetStatus")&&detailData.containsKey("fdStandardStatus")){
						break;
					}
					String name = (String) it.next();
					if(name.endsWith("_budget_status")){
						String status = (String) val.get(name);
						if(StringUtil.isNotNull(status)){
							detailData.put("fdBudgetStatus", ResourceUtil.getString("py.budget."+status,"fssc-fee"));
						}
					}
					if(name.endsWith("_standard_status")){
						String status = (String) val.get(name);
						if(StringUtil.isNotNull(status)){
							detailData.put("fdStandardStatus", ResourceUtil.getString("py.standard."+status,"fssc-fee"));
						}
					}
				}
				if(StringUtil.isNotNull(config.getFdLeftShow())){
					String left = "",right = "";
					for(Iterator it=detailData.keys();it.hasNext();){
						String key1 = (String) it.next();
						Object value = detailData.getString(key1);
						if(config.getFdLeftShowId().contains(key1)&&value!=null){
							if(left.length()>0){
								left+=";";
							}
							left+=value.toString();
						}
						if(config.getFdRightShowId().contains(key1)&&value!=null){
							if(right.length()>0){
								right+=";";
							}
							right+=value.toString();
						}
					}
					detailData.put("leftShow", left);
					detailData.put("rightShow", right);
				}
				detailList.add(detailData);
			}
			data.put(key, detailList);
		}
	}

	@Override
	public JSONArray getTodoList(List<String> ids) throws Exception {
		JSONArray data = new JSONArray();
		StringBuilder hql=new StringBuilder();
		hql.append("from FsscFeeMain main where ").append(HQLUtil.buildLogicIN("main.fdId", ids));
		/*modify by xiexx，发布单据特殊业务也会给业务人员发送待办（如报销线下领取现金），故去除发布状态*/
		hql.append("  order by main.fdId desc");
		List<FsscFeeMain> mainList=fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql.toString()).list();
		for (FsscFeeMain main : mainList) {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("title", main.getDocSubject());
			jsonObj.put("date", DateUtil.convertDateToString(main.getDocCreateTime(), DateUtil.PATTERN_DATE));
			try {
				JSONObject param  = new JSONObject();
				param.put("fdFeeId", main.getFdId());
				jsonObj.put("count", fsscFeeMainService.getTotalMoney(main.getFdId()));
			} catch (Exception e) {
				jsonObj.put("count","0.00");
			}
			jsonObj.put("company", main.getExtendDataModelInfo().getModelData().get("fd_company_name"));
			jsonObj.put("dept",main.getExtendDataModelInfo().getModelData().get("fd_cost_center_name"));
			jsonObj.put("clazz", "status"+main.getDocStatus());
			jsonObj.put("status", EnumerationTypeUtil.getColumnEnumsLabel("common_status", main.getDocStatus()));
			jsonObj.put("link", "");
			jsonObj.put("id", main.getFdId());
			jsonObj.put("templateId", main.getDocTemplate()!=null?main.getDocTemplate().getFdId():"");
			data.add(jsonObj);
		}
		return data;
	}

	@Override
	public JSONArray getFeeMain(String fdTemplateId,String keyword, String fdPersonId)  throws Exception {
		List<String> personIds  = new ArrayList<String>();
		personIds.add(UserUtil.getUser().getFdId());
		String fdIsAuthorize=EopBasedataFsscUtil.getSwitchValue("fdIsAuthorize");
		if ("true".equals(fdIsAuthorize)) {
			if(StringUtil.isNotNull(fdPersonId)) {
				personIds.add(fdPersonId);
			}
		}
		JSONArray data = new JSONArray();
		String hql = "from FsscFeeMain main where main.docCreator.fdId in ( :fdPersonId)";
		hql+=" and main.docStatus=:publish and main.fdIsClosed=:fdIsClosed ";
		String feeHql = "select fdFeeIds from com.landray.kmss.fssc.expense.model.FsscExpenseMain where fdIsCloseFee=:fdCloseFee and (docStatus=:docStatus1 or docStatus=:docStatus2)";
		List<String> ids = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(feeHql)
				.setParameter("fdCloseFee", true).setParameter("docStatus1", SysDocConstant.DOC_STATUS_PUBLISH)
				.setParameter("docStatus2", SysDocConstant.DOC_STATUS_EXAMINE).list();
		if(!ArrayUtil.isEmpty(ids)){
			hql+=" and main.fdId not in(:closedIds)";
		}
		if(StringUtil.isNotNull(fdTemplateId)){
			hql+=" and main.docTemplate.fdId=:fdTemplateId ";
		}
		if(StringUtil.isNotNull(keyword)){
			hql+=" and (main.docSubject like :keyword or main.docNumber like :keyword)";
		}
		hql+=" order by main.fdId desc";
		Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("fdPersonId", personIds);
		query.setParameter("fdIsClosed", false);
		query.setParameter("publish", SysDocConstant.DOC_STATUS_PUBLISH);
		if(StringUtil.isNotNull(keyword)){
			query.setParameter("keyword", "%"+keyword+"%");
		}
		if(StringUtil.isNotNull(fdTemplateId)){
			query.setParameter("fdTemplateId", fdTemplateId);
		}
		if(!ArrayUtil.isEmpty(ids)){
			List<String> closedIds = new ArrayList<String>();
			for(String id:ids){
				closedIds.addAll(Arrays.asList(id.split(";")));
			}
			query.setParameterList("closedIds", closedIds);
		}
		List<FsscFeeMain> list = query.list();
		for(FsscFeeMain main:list){
			JSONObject json = new JSONObject();
			json.put("name", main.getDocSubject()+"("+main.getDocNumber()+")");
			json.put("value", main.getFdId());
			json.put("parent", "0");
			data.add(json);
		}
		return data;
	}

	@Override
	public JSONArray getCateList(HttpServletRequest request) throws Exception {
		JSONArray templateArr=new JSONArray();
		String where="fsscFeeTemplate.fdIsMobile=:fdIsMobile";
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setParameter("fdIsMobile", Boolean.TRUE);
		hqlInfo.setWhereBlock(where);
		hqlInfo.setRowSize(1000);  //分类显示条数
		Page page=getFsscFeeTemplateService().findPage(hqlInfo);
		if(page!=null){
			List<FsscFeeTemplate> templateList=page.getList();
			for(FsscFeeTemplate template:templateList){
				JSONObject jsonObj=new JSONObject();
				jsonObj.put("value", template.getFdId());
				jsonObj.put("text", template.getFdName());
				jsonObj.put("name", template.getFdName());
				templateArr.add(jsonObj);
			}
		}
		return templateArr;
	}
		


	@Override
	public JSONArray getFormData(String fdTemplateId) throws  Exception {
		FsscFeeTemplate template = (FsscFeeTemplate) fsscFeeMainService.findByPrimaryKey(fdTemplateId, FsscFeeTemplate.class, true);
		if(template==null){
			FsscFeeMain main = (FsscFeeMain) fsscFeeMainService.findByPrimaryKey(fdTemplateId, FsscFeeMain.class, true);
			template = main.getDocTemplate();
		}
		String hql = "from "+FsscFeeMobileConfig.class.getName()+" where docMain.fdId=:fdTemplateId order by fdOrder asc";
		List<FsscFeeMobileConfig> list = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdTemplateId", template.getFdId()).list();
		JSONArray rtn = new JSONArray();
		for(FsscFeeMobileConfig temp:list){
			JSONObject obj = new JSONObject();
			String fdFieldId = temp.getFdTemplateFieldId();
			obj.put("name", fdFieldId.replaceAll("\\$", ""));
			obj.put("text", temp.getFdTemplateField());
			obj.put("title", temp.getFdFieldName());
			obj.put("init", temp.getFdInitOption());
			obj.put("required", temp.getFdIsRequired());
			obj.put("type", temp.getFdFieldType());
			obj.put("dataSource", temp.getFdDataService());
			obj.put("baseOn", temp.getFdBaseOnId()==null?"":temp.getFdBaseOnId().replaceAll("\\$", ""));
			obj.put("position", temp.getFdFieldPosition());
			obj.put("showStatus", temp.getFdShowStatus());
			obj.put("orgType", temp.getFdOrgType());
			obj.put("multi", temp.getFdIsMulti());
			obj.put("leftShow", temp.getFdLeftShowId()==null?"":temp.getFdLeftShowId().replaceAll("\\$", ""));
			obj.put("rightShow", temp.getFdRightShowId()==null?"":temp.getFdRightShowId().replaceAll("\\$", ""));
			rtn.add(obj);
		}
		return rtn;
	}
	
	@Override
	public Map getFormFields(HttpServletRequest request) throws  Exception {
		Map rtnMap=new HashMap<>();
		String formBeanName="fsscFeeMainForm";
		String mainFormName = null;
		String xformFormName = null;
		int indexOf = formBeanName.indexOf('.');
		if (indexOf > -1) {
			mainFormName = formBeanName.substring(0, indexOf);
			xformFormName = formBeanName.substring(indexOf + 1);
		} else {
			mainFormName = formBeanName;
		}

		Object mainForm = request.getAttribute(mainFormName);
		Object xform = xformFormName == null ? mainForm : PropertyUtils.getProperty(mainForm, xformFormName);
		DictLoadService dictService=(DictLoadService)SpringBeanUtil.getBean("sysFormDictLoadService");
		String path = "";
		if(xform instanceof IExtendForm){
			IExtendForm extendForm = (IExtendForm)xform;
			path = (String) PropertyUtils.getProperty(extendForm,"extendDataFormInfo.extendFilePath");
			path = dictService.findExtendFileFullPath(path, false);
		}else{
			path = (String) PropertyUtils.getProperty(xform, "extendDataFormInfo.extendFilePath");
			path=dictService.findExtendFileFullPath(path);
		}
		Map<String,List<Map<String,String>>> xmlMap=new HashMap<>();
		if(StringUtil.isNotNull(path)){
			xmlMap=parseXmlConfig(path);
		}
		String fdTemplateId = request.getParameter("i.docTemplate");
		FsscFeeTemplate template = (FsscFeeTemplate) fsscFeeMainService.findByPrimaryKey(fdTemplateId, FsscFeeTemplate.class, true);
		if(template==null){
			return rtnMap;
		}
		String hql = "from "+FsscFeeMobileConfig.class.getName()+" where docMain.fdId=:fdTemplateId order by fdOrder asc";
		List<FsscFeeMobileConfig> list = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdTemplateId", template.getFdId()).list();
		List<Map<String,Object>> mainList = new ArrayList<>();
		Map<String,List> detailMap = new HashMap<>();
		Map<String,Object> hiddenMap = new HashMap<>();
		String fdDetailFeildId="";
		EopBasedataCompany com=null;
		List<EopBasedataCompany> comList=getEopBasedataCompanyService().findCompanyByUserId(UserUtil.getUser().getFdId());
		if(!ArrayUtil.isEmpty(comList)){
			com=comList.get(0);
			rtnMap.put("fdCompanyId", com!=null?com.getFdId():"");
		}
		Map<String,List> funcMap=new HashMap<>();
		List funcList=new ArrayList<>();
		for(FsscFeeMobileConfig temp:list){
			String fdBaseOnId=temp.getFdBaseOnId();
			if(StringUtil.isNotNull(fdBaseOnId)&&StringUtil.isNotNull(temp.getFdInitOption())&&StringUtil.isNotNull(temp.getFdTemplateFieldId())){
				JSONObject structsObj=getFunction(temp.getFdInitOption(),temp.getFdBaseOnId(),temp.getFdTemplateFieldId());
				String[] fdBaseOnIds=fdBaseOnId.split(";");
				for(String key:fdBaseOnIds){
					funcList=new ArrayList<>();
					key=key.replaceAll("\\$", "");
					if(funcMap.containsKey(key)&&!ArrayUtil.isEmpty(funcMap.get(key))){//一个字段绑定多个联动函数
						funcList.addAll(funcMap.get(key));
					}
					String funcStr=structsObj.toString().replaceAll("\"", "&quot;");
					if(!funcList.contains(funcStr)){
						funcList.add(funcStr);
					}
					funcMap.put(key, funcList);
				}
			}
			if("5".equals(temp.getFdInitOption())){
				String templateFieldId=temp.getFdTemplateFieldId();
				if(StringUtil.isNotNull(templateFieldId)){
					templateFieldId=templateFieldId.replaceAll("\\$", "");
					String key=templateFieldId.indexOf("\\.")>-1?(templateFieldId.split("\\.")[1]):templateFieldId;
					List tempFuncList=new ArrayList();
					if(funcMap.containsKey(key)){
						tempFuncList=funcMap.get(key);
					}
					JSONObject json=new JSONObject();
					json.put("params", (StringUtil.isNotNull(temp.getFdBaseOnId())?temp.getFdBaseOnId().replaceAll("\\$", ""):"")+";"+templateFieldId);
					json.put("func", "setRate");
					tempFuncList.add(json.toString().replaceAll("\"", "&quot;"));
					funcMap.put(key, tempFuncList);
				}
			}
			Map<String,Object> map = new HashMap<>();
			String position=temp.getFdFieldPosition();
			String fdFieldId = temp.getFdTemplateFieldId();
			map.put("name", fdFieldId.replaceAll("\\$", ""));
			map.put("text", temp.getFdTemplateField());
			map.put("title", temp.getFdFieldName());
			Map<String,Object> initMap=getInitValue(temp.getFdInitOption(),fdBaseOnId,temp.getFdTemplateFieldId(),com);
			map.put("init",initMap.get("initValueMap"));
			map.put("validate", temp.getFdValidate());
			map.put("type", temp.getFdFieldType());
			map.put("dataSource", temp.getFdDataService());
			Map baseOnMap=new HashMap<>();
			baseOnMap.put(StringUtil.isNotNull(temp.getFdBaseOn())?temp.getFdBaseOn():"", StringUtil.isNull(fdBaseOnId)?"":fdBaseOnId.replaceAll("\\$", ""));
			map.put("baseOn", baseOnMap);
			map.put("position", temp.getFdFieldPosition());
			map.put("showStatus", temp.getFdShowStatus());
			map.put("orgType", temp.getFdOrgType());
			map.put("multi", temp.getFdIsMulti()!=null&&temp.getFdIsMulti()?"true":"false");
			map.put("leftShow", temp.getFdLeftShowId()==null?"":temp.getFdLeftShowId().replaceAll("\\$", ""));
			map.put("rightShow", temp.getFdRightShowId()==null?"":temp.getFdRightShowId().replaceAll("\\$", ""));
			if("7".equals(temp.getFdFieldType())||"8".equals(temp.getFdFieldType())||"9".equals(temp.getFdFieldType())){
				String fdTemplateFieldId=temp.getFdTemplateFieldId().replaceAll("\\$", "");
				if(fdTemplateFieldId.indexOf(".")>-1){
					fdTemplateFieldId = fdTemplateFieldId.split("\\.")[1];
				}
				List<Map<String,String>> attrList = xmlMap.get(fdTemplateFieldId);
				if(attrList==null) { //配置的字段不存在导致页面加载空白
					continue;
				}
				for(Map<String,String> attr:attrList){
					if(attr.containsKey("enumValues")){
						String enumValues = attr.get("enumValues");
						if(StringUtil.isNotNull(enumValues)){
							List<Map<String,Object>> itemList = new ArrayList<Map<String,Object>>();
							for(String item:enumValues.split("\\;")){
								Map<String,Object> itemMap = new HashMap<String,Object>();
								String[] itemValue =  item.split("\\|");
								itemMap.put("text",itemValue[0]);
								itemMap.put("value",itemValue[1]);
								itemList.add(itemMap);
							}
							map.put("enumValues", itemList);
							map.put("enumValuesText", enumValues);
							map.put("defaultValue", attr.containsKey("defaultValue")?attr.get("defaultValue"):"");
							break;
						}
					}
				}
			}
			if("2".equals(position)){  //明细
				String fdTemplateFieldId=temp.getFdTemplateFieldId();
				if(StringUtil.isNotNull(fdTemplateFieldId)){
					fdTemplateFieldId=fdTemplateFieldId.replaceAll("\\$", "");
					fdTemplateFieldId=fdTemplateFieldId.split("\\.")[0];
					if(fdDetailFeildId.indexOf(fdTemplateFieldId)==-1) {
						fdDetailFeildId=StringUtil.linkString(fdDetailFeildId, ";", "$"+fdTemplateFieldId+"$");
					}
				}
				String name=map.containsKey("name")?map.get("name").toString():null;
				if(StringUtil.isNotNull(name)){
					//明细增加基准行name和内容行name
					int index=name.indexOf(".");
					String refer_name="";
					String content_name="";
					if(index>-1){
						refer_name=name.substring(0, index)+".!{index}"+name.substring(index, name.length());
						content_name=name.substring(0, index)+".index"+name.substring(index, name.length());
					}
					map.put("refer_name", refer_name);  //基准行name
					map.put("content_name", content_name);  //内容行name
				}
				String[] temArr=fdFieldId.replaceAll("\\$", "").split("\\.");
				if(temArr.length==2){
					String temp_name=temArr[1];
					if(xmlMap.containsKey(temp_name)&&!ArrayUtil.isEmpty(xmlMap.get(temp_name))){
						List rtnOtherList=new ArrayList<>();
						List<Map<String,String>> otherList=xmlMap.get(temp_name);
						for(Map<String,String> otherMap:otherList){
							String detail_name=otherMap.containsKey("name")?otherMap.get("name"):"";
							if(StringUtil.isNotNull(detail_name)&&!detail_name.equals(temp_name)&&!detail_name.equals(temp_name+"_name")){
								hiddenMap=new HashMap<>();
								if(StringUtil.isNotNull(detail_name)){
									//明细增加基准行name和内容行name
									int index=name.indexOf(".");
									String refer_name="";
									String content_name="";
									if(index>-1){
										refer_name=name.substring(0, index)+".!{index}."+detail_name;
										content_name=name.substring(0, index)+".index."+detail_name;
									}
									hiddenMap.put("name", name.substring(0, index+1)+detail_name);  //内容行name
									hiddenMap.put("refer_name", refer_name);  //基准行name
									hiddenMap.put("content_name", content_name);  //内容行name
								}
								hiddenMap.put("text", otherMap.containsKey("label")?otherMap.get("label"):"");
								hiddenMap.put("title", otherMap.containsKey("label")?otherMap.get("label"):"");
								hiddenMap.put("required", "false");
								hiddenMap.put("type", "0");  //隐藏域
								rtnOtherList.add(hiddenMap);
							}
						}
						map.put("other", rtnOtherList);
					}
				}
				List detailFileList=new ArrayList<>();
				if(detailMap.containsKey(fdTemplateFieldId)){
					detailFileList=detailMap.get(fdTemplateFieldId);
					detailFileList.add(map);
				}else{
					detailFileList.add(map);
				}
				detailMap.put(fdTemplateFieldId, detailFileList);
			}else{
				fdFieldId=fdFieldId.replaceAll("\\$", "");
				if(xmlMap.containsKey(fdFieldId)&&!ArrayUtil.isEmpty(xmlMap.get(fdFieldId))){
					List rtnOtherList=new ArrayList<>();
					List<Map<String,String>> otherList=xmlMap.get(fdFieldId);
					for(Map<String,String> otherMap:otherList){
						String name=otherMap.containsKey("name")?otherMap.get("name"):"";
						if(StringUtil.isNotNull(name)&&!name.equals(fdFieldId)&&!name.equals(fdFieldId+"_name")){
							hiddenMap=new HashMap<>();
							hiddenMap.put("name", name);
							hiddenMap.put("text", name);
							hiddenMap.put("title", name);
							hiddenMap.put("required", "false");
							hiddenMap.put("type", "0");  //隐藏域
							rtnOtherList.add(hiddenMap);
						}
					}
					map.put("other", rtnOtherList);
				}
				mainList.add(map);
			}
		}
		xmlMap.clear();
		rtnMap.put("mainFieldList", mainList);
		rtnMap.put("detailFieldList", detailMap);
		Map<String,List<String>> mappListMap=getDisplayList(template.getFdId(),fdDetailFeildId);
		if(mappListMap.containsKey("displayList")) {
			rtnMap.put("displayList", mappListMap.get("displayList"));
		}
		if(mappListMap.containsKey("budgetMatchList")) {
			rtnMap.put("budgetMatchList", mappListMap.get("budgetMatchList"));
		}
		rtnMap.put("funcMap", funcMap);
		rtnMap.put("expressMap", parseExpressionFromJsp(template.getFdId()));
		return rtnMap;
	}
	/**
	 * 构造对应的联动JS函数
	 * @param option
	 * @param fdBaseOnId
	 * @param fdTemplateFieldId
	 * @return
	 */
	public JSONObject getFunction(String option, String fdBaseOnId, String fdTemplateFieldId) {
		JSONObject structsObj=new JSONObject();
		structsObj.put("params", fdBaseOnId.replaceAll("\\$", ""));  //需要监听的参数
		structsObj.put("target", fdTemplateFieldId.replaceAll("\\$", ""));  //监听改变后需要改变的值
		if("2".equals(option)){ //当前部门
			structsObj.put("func","setDeptByPerson");  //切换申请人重新设置部门
		}else if("3".equals(option)){ //默认公司
			structsObj.put("func","setCompanyByPerson");  //切换申请人重新设置公司
		}else if("4".equals(option)){ //默认成本中心
			structsObj.put("func","setCostCenterByCompany");  //切换公司重新设置成本中心
		}else if("5".equals(option)){ //默认币种
			structsObj.put("func","setCurrencyByCompany");  //切换公司重新设置币种
		}else if("6".equals(option)){ //默认汇率
			
		}else if("7".equals(option)){ //计算天数
			structsObj.put("func","CalculateDay");  //监听触发的函数名
		}else if("8".equals(option)){ //金额合计
			structsObj.put("func","sumMoney");  //监听触发的函数名
		}else if("9".equals(option)){ //本位币换算
			structsObj.put("func","setLocalMoney");  //监听触发的函数名
		}else if("11".equals(option)){ //当前城市
			structsObj.put("func","setCityByCompany");  //监听触发的函数名
		}
		return structsObj;
	}

	/**
	 * 获取明细显示字段及主表绑定预维度算相关的字段
	 * @param fdId
	 * @param fdDetailFeildId
	 * @return
	 * @throws Exception
	 */
	public Map<String,List<String>> getDisplayList(String fdId, String fdDetailFeildId) throws Exception{
		Map<String,List<String>> mappListMap=new HashMap<String,List<String>>();
		List<String> displayList=new ArrayList();
		List<String> budgetMatchList=new ArrayList();
		String hql = "from "+FsscFeeMapp.class.getName()+" where fdTemplate.fdId=:fdTemplateId ";
		if(StringUtil.isNotNull(fdDetailFeildId)) {
			hql += " and "+HQLUtil.buildLogicIN("fdTableId", ArrayUtil.convertArrayToList(fdDetailFeildId.split(";")));
		}
		List<FsscFeeMapp> list = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdTemplateId", fdId).list();
		if(!ArrayUtil.isEmpty(list)){
			FsscFeeMapp mapp=list.get(0);
			String expenseItem=StringUtil.isNotNull(mapp.getFdExpenseItemId())?mapp.getFdExpenseItemId().replaceAll("\\$", ""):"";
			displayList.add(expenseItem); //费用类型
			String fdMoneyId=StringUtil.isNotNull(mapp.getFdMoneyId())?mapp.getFdMoneyId().replaceAll("\\$", ""):"";
			displayList.add(fdMoneyId); //金额
			String fdCurrencyId=StringUtil.isNotNull(mapp.getFdCurrencyId())?mapp.getFdCurrencyId().replaceAll("\\$", ""):"";
			displayList.add(fdCurrencyId); //币种汇率
			String fdPersonId=StringUtil.isNotNull(mapp.getFdPersonId())?mapp.getFdPersonId().replaceAll("\\$", ""):"";
			displayList.add(fdPersonId); //人员
			String budgetRule=StringUtil.isNotNull(mapp.getFdRuleId())?mapp.getFdRuleId().replaceAll("\\$", ""):"";
			displayList.add(budgetRule); //预算
			displayList.add(StringUtil.isNotNull(mapp.getFdStandardId())?mapp.getFdStandardId().replaceAll("\\$", ""):""); //标准
			if(StringUtil.isNotNull(budgetRule)&&budgetRule.indexOf(".")==-1) {//预算显示控件在主表，获取移动端需要监控的字段
				budgetMatchList.add(budgetRule);  //预算规则
				budgetMatchList.add(expenseItem);  //费用类型
				budgetMatchList.add(fdMoneyId);//金额
				budgetMatchList.add(fdCurrencyId);//币种
				budgetMatchList.add(fdPersonId);//人员
				budgetMatchList.add(StringUtil.isNotNull(mapp.getFdCompanyId())?mapp.getFdCompanyId().replaceAll("\\$", ""):""); //公司
				budgetMatchList.add(StringUtil.isNotNull(mapp.getFdCostCenterId())?mapp.getFdCostCenterId().replaceAll("\\$", ""):"");//成本中心
				budgetMatchList.add(StringUtil.isNotNull(mapp.getFdProjectId())?mapp.getFdProjectId().replaceAll("\\$", ""):"");//项目
				budgetMatchList.add(StringUtil.isNotNull(mapp.getFdInnerOrderId())?mapp.getFdInnerOrderId().replaceAll("\\$", ""):"");//内部订单
				budgetMatchList.add(StringUtil.isNotNull(mapp.getFdWbsId())?mapp.getFdWbsId().replaceAll("\\$", ""):"");//WBS
				budgetMatchList.add(StringUtil.isNotNull(mapp.getFdDeptId())?mapp.getFdDeptId().replaceAll("\\$", ""):"");//部门
				budgetMatchList.add(StringUtil.isNotNull(mapp.getFdTravelDaysId())?mapp.getFdTravelDaysId().replaceAll("\\$", ""):"");//天数
				budgetMatchList.add(StringUtil.isNotNull(mapp.getFdPersonNumId())?mapp.getFdPersonNumId().replaceAll("\\$", ""):"");//人数
				budgetMatchList.add(StringUtil.isNotNull(mapp.getFdAreaId())?mapp.getFdAreaId().replaceAll("\\$", ""):"");//地域
				budgetMatchList.add(StringUtil.isNotNull(mapp.getFdVehicleId())?mapp.getFdVehicleId().replaceAll("\\$", ""):"");//交通工具
			}
		}
		mappListMap.put("displayList", displayList);
		if(!ArrayUtil.isEmpty(budgetMatchList)) {
			mappListMap.put("budgetMatchList", budgetMatchList);
		}
		return mappListMap;
	}

	/***********************************************************
	 * 解析预算主表字段配置信息
	 * *********************************************************/
	
	public static final Map<String,List<Map<String,String>>> parseXmlConfig(String path) throws Exception{
		Map<String,List<Map<String,String>>> xmlMap=new HashMap<>();
		String basePath = ConfigLocationsUtil.getWebContentPath();
		String configFilePath = basePath+"/"+path+".xml";
		Document document = getDocument(configFilePath);
		Element rootElement = document.getRootElement();
		List<Element> elements = rootElement.elements();
		List<Map<String,String>> mapList=new ArrayList<>();
		String key="";  //当前解析字段的name
		String tempKey="";  //上一字段解析name
		for(Element child:elements){
			mapList=new ArrayList<>();
			Map map=new ConcurrentHashMap<String,String>();
			List<Element> e=child.elements();
			if(e.size()>0){
				key="";  //当前解析字段的name
				tempKey="";  //上一字段解析name
				for(Element ele:e){//明细表
					mapList=new ArrayList<>();
					map=new ConcurrentHashMap<String,String>();
					Iterator it=ele.attributeIterator();
					while(it.hasNext()){
						Attribute attribute=(Attribute) it.next();
						if("name".equals(attribute.getName())){
							key=attribute.getValue();
						}
						map.put(attribute.getName(), attribute.getValue());
					}
					if(StringUtil.isNull(key)){
						continue;
					}
					if(!key.startsWith(tempKey)){
						tempKey=key;  //交换，用于下次判断
					}
					if(StringUtil.isNotNull(tempKey)&&!key.equals(tempKey)&&key.startsWith(tempKey)){//同一行字段
						mapList=xmlMap.get(tempKey);
					}
					if(StringUtil.isNull(tempKey)){
						tempKey=key;  //第一次赋值
					}
					mapList.add(map);
					xmlMap.put(tempKey, mapList);
				}
			}else{//主表字段
				Iterator iterator=child.attributeIterator();
				while(iterator.hasNext()){
					Attribute attribute=(Attribute) iterator.next();
					if("name".equals(attribute.getName())){
						key=attribute.getValue();
					}
					map.put(attribute.getName(), attribute.getValue());
				}
				if(!key.startsWith(tempKey)){
					tempKey=key;  //交换，用于下次判断
				}
				if(StringUtil.isNotNull(tempKey)&&!key.equals(tempKey)&&key.startsWith(tempKey)){//同一行字段
					mapList=xmlMap.get(tempKey);
				}
				if(StringUtil.isNull(tempKey)){
					tempKey=key;  //第一次赋值
				}
				mapList.add(map);
				xmlMap.put(tempKey, mapList);
			}
		}
		return xmlMap;
	}
	
	public static Document getDocument(String path) {
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			File file = new File(path);
			if(file.exists() && file.length()> 0) {  
				document = saxReader.read(file);
			}  
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}
	/**
	 * 获取默认值，对象返回value，text，简单值返回text
	 * @param option
	 * @param com 
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getInitValue(String option,String fdBaseOnId,String FdTemplateFieldId, EopBasedataCompany com) throws Exception{
		Map<String,Object> rtnMap=new HashMap<>();
		Map<String,Object> valueMap=new HashMap<>();
		SysOrgPerson user=UserUtil.getUser();
		if("1".equals(option)){//当前用户
			valueMap.put("value", user.getFdId());
			valueMap.put("text", user.getFdName());
			valueMap.put("parentId", user.getFdParent()!=null?user.getFdParent().getFdId():"");
		}else if("2".equals(option)){ //当前部门
			SysOrgElement org=user.getFdParent();
			if(org!=null){
				valueMap.put("value", org.getFdId());
				valueMap.put("text", org.getFdName());
				valueMap.put("parentId", org.getFdParent()!=null?org.getFdParent().getFdId():"");
			}
		}else if("3".equals(option)){ //默认公司
			if(com!=null){
				valueMap.put("value", com.getFdId());
				valueMap.put("text", com.getFdName());
			}
		}else if("4".equals(option)){ //默认成本中心
			if(com!=null){
				EopBasedataCostCenter costCenter=getEopBasedataCostCenterService().findCostCenterByUserId(com.getFdId(), user.getFdId());
				if(costCenter!=null){
					valueMap.put("value", costCenter.getFdId());
					valueMap.put("text", costCenter.getFdName());
				}
			}
		}else if("5".equals(option)){ //默认币种
			if(com!=null){
				valueMap.put("value", com.getFdAccountCurrency()!=null?com.getFdAccountCurrency().getFdId():"");
				valueMap.put("text", com.getFdAccountCurrency()!=null?com.getFdAccountCurrency().getFdName():"");
			}
		}else if("6".equals(option)){ //默认汇率
			
		}else if("7".equals(option)){ //计算天数
			valueMap.put("text", "1");
		}else if("8".equals(option)){ //金额合计
			
		}else if("9".equals(option)){ //本位币换算
			
		}else if("10".equals(option)){ //当前日期
			valueMap.put("text", DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATE));
		}else if("11".equals(option)){ //当前城市
			valueMap.put("value", "currentCityId");
			valueMap.put("text", "currentCityName");
		}
		rtnMap.put("initValueMap", valueMap);
		return rtnMap;
	}
	/**
	 * 从jsp文件获取表达式的值
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public   Map<String,String> parseExpressionFromJsp(String fdTemplateId) throws Exception{
		String hql="from SysFormTemplate docMain  where docMain.fdModelId=:fdTemplateId   order by fdCreateTime desc ";
		SysFormTemplate sysFormTemplate= (SysFormTemplate) fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdTemplateId", fdTemplateId).setMaxResults(1).uniqueResult();;
		if(sysFormTemplate!=null&&StringUtils.isNotEmpty(sysFormTemplate.getFdMobileJsp())) {
			HashMap  map=new HashMap();
			//获取div
			String[] divArr=sysFormTemplate.getFdMobileJsp().split("<div");
			for(String d:divArr) {
				int endIndex=d.indexOf("</div>");
				if(endIndex>0) {
				String divStr=d.substring(0, endIndex);
				if(divStr.contains("expression")) {
					map.put(getValue(divStr,"name"), getValue(divStr,"expression"));
				}
				}
			}
			return map;
		}else {
			return null;
		}
		
		
	}
	public static String  getValue(String divHtml,String key) {
		if("expression".equals(key)) {
		int index=divHtml.indexOf("expression=");
		int endIndex=divHtml.indexOf("autoCalculate", index);
		return divHtml.substring(index+key.length()+1,endIndex).replace("\"", "");
		}else if("name".equals(key)) {
			int index=divHtml.indexOf("name");
			int endIndex=divHtml.indexOf(",\"subject\"", index);
			return     divHtml.substring(index+key.length()+3,endIndex).replace("\"", "").replace("${vstatus.index}.", "").replace("!{index}.", "");
		}
		return null;
	}
	

	@Override
	public JSONObject checkMobileBudget(JSONObject data) throws Exception {
		JSONObject rtn = new JSONObject();
		//如果没有预算模块，不校验预算
		if(!FsscCommonUtil.checkHasModule("/fssc/budget/")){
			rtn.put("pass", true);
			rtn.put("data", data);
			return rtn;
		}
		String fdTemplateId = data.getString("fdTemplateId");
		FsscFeeTemplate temp = (FsscFeeTemplate) fsscFeeMainService.findByPrimaryKey(fdTemplateId, FsscFeeTemplate.class, true);
		String hql = "from "+FsscFeeMapp.class.getName()+" where fdTemplate.fdId=:fdTemplateId";
		List<FsscFeeMapp> list = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdTemplateId", fdTemplateId).list();
		//如果没有配置台账映射，则不校验预算
		if(ArrayUtil.isEmpty(list)){
			rtn.put("pass", true);
			rtn.put("data", data);
			return rtn;
		}
		//如果没有配置预算规则，不校验预算
		FsscFeeMapp mapp = list.get(0);
		if(StringUtil.isNull(mapp.getFdRuleId())){
			rtn.put("pass", true);
			rtn.put("data", data);
			return rtn;
		}
		//预算在明细
		if(mapp.getFdRuleId().indexOf(".")>-1){
			String fdTableId = mapp.getFdRuleId().replaceAll("\\$", "").split("\\.")[0];
			//未在移动表单中配置对应明细表，不校验预算
			if(!data.containsKey(fdTableId)){
				rtn.put("pass", true);
				rtn.put("data", data);
				return rtn;
			}
			JSONArray detailList = data.getJSONArray(fdTableId);
			FsscFeeMobileConfig table = getConfigByProperty(temp.getFdConfig(), mapp.getFdTableId());
			String fdTableName = mapp.getFdTable().replaceAll("\\$", "");
			if(table!=null){
				fdTableName = table.getFdFieldName();
			}
			//用于多明细匹配到同一预算时计算累计使用金额
			Map<String,Map<String,Object>> budgetMap = new HashMap<String,Map<String,Object>>();
			for(int i=0;i<detailList.size();i++){
				JSONObject detailData = detailList.getJSONObject(i);
				String fdExpenseItemId = (String) getValueByProperty(mapp.getFdExpenseItemId(), data, detailData);
				if(StringUtil.isNull(fdExpenseItemId)){
					continue;
				}
				String fdCompanyId = (String) getValueByProperty(mapp.getFdCompanyId(), data, detailData);
				String fdCostCenterId = (String) getValueByProperty(mapp.getFdCostCenterId(), data, detailData);
				String fdProjectId = (String) getValueByProperty(mapp.getFdProjectId(), data, detailData);
				String fdWbsId = (String) getValueByProperty(mapp.getFdWbsId(), data, detailData);
				String fdInnerOrderId = (String) getValueByProperty(mapp.getFdInnerOrderId(), data, detailData);
				String fdPersonId = (String) getValueByProperty(mapp.getFdPersonId(), data, detailData);
				String fdDeptId = (String) getValueByProperty(mapp.getFdDeptId(), data, detailData);
				JSONObject budgetInfo = new JSONObject();
				budgetInfo.put("fdCompanyId", fdCompanyId);
				budgetInfo.put("fdExpenseItemId", fdExpenseItemId);
				budgetInfo.put("fdCostCenterId", fdCostCenterId);
				budgetInfo.put("fdWbsId", fdWbsId);
				budgetInfo.put("fdProjectId", fdProjectId);
				budgetInfo.put("fdInnerOrderId", fdInnerOrderId);
				budgetInfo.put("fdPersonId", fdPersonId);
				budgetInfo.put("fdDeptId", fdDeptId);
				JSONObject budget = getFsscCommonBudgetService().matchFsscBudget(budgetInfo);
				JSONArray budgetList = new JSONArray();
				if(budget.containsKey("data")){
					budgetList = budget.getJSONArray("data");
				}
				detailData.put(mapp.getFdRuleId().replaceAll("\\$", "").replace("_budget_status", "_budget_info")+"_budget_info", budgetList.toString().replaceAll("\"", "\'"));
				String fdCurrencyId = (String) getValueByProperty(mapp.getFdCurrencyId(), data, detailData);
				//获取汇率
				Double budgetRate = getEopBasedataExchangeService().getBudgetRate(fdCurrencyId, fdCompanyId);
				Double money = Double.valueOf((String) getValueByProperty(mapp.getFdMoneyId(), data, detailData));
				Double budgetMoney = FsscNumberUtil.getMultiplication(money, budgetRate);
				//默认无预算
				String status = "0";
				for(int k=0;k<budgetList.size();k++){
					status = "2".equals(status)?status:"1";
					//判断是否超出可使用额度，需要加上弹性比例
					Double fdCanUseAmount = budgetList.getJSONObject(k).getDouble("fdCanUseAmount");
					Double fdElasticPercent = 0d;
					if(budgetList.getJSONObject(k).containsKey("fdElasticPercent")){
						fdElasticPercent = budgetList.getJSONObject(k).getDouble("fdElasticPercent");
					}
					fdElasticPercent = fdElasticPercent==null?0d:fdElasticPercent;
					Double fdTotalAmount = budgetList.getJSONObject(k).getDouble("fdTotalAmount");
					fdCanUseAmount =  FsscNumberUtil.getAddition(fdCanUseAmount, FsscNumberUtil.getMultiplication(fdTotalAmount, FsscNumberUtil.getDivide(fdElasticPercent, 100)));
					//刚控且超预算，不能提交
					if(fdCanUseAmount!=null&&fdCanUseAmount<budgetMoney){
						status = "2";
						if("1".equals(budgetList.getJSONObject(k).get("fdRule"))||"3".equals(budgetList.getJSONObject(k).get("fdRule"))){
							rtn.put("pass", false);
							rtn.put("data", data);
							rtn.put("message", ResourceUtil.getString("tips.budget.over.row.mobile","fssc-fee").replace("{0}", fdTableName).replace("{row}", (i+1)+""));
							return rtn;
						}
					}
					String fdBudgetId = budgetList.getJSONObject(k).getString("fdBudgetId");
					//累计总额
					if(budgetMap.containsKey(fdBudgetId)){
						Map<String,Object> map = budgetMap.get(fdBudgetId);
						Double mon = (Double) map.get("useMoney");
						map.put("useMoney", FsscNumberUtil.getAddition(money, mon));
						List<Integer> index = (List<Integer>) map.get("index");
						index.add(i);
						budgetMap.put(fdBudgetId, map);
					}else{
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("useMoney", money);
						map.put("forbid", budgetList.getJSONObject(k).get("fdRule"));
						List<Integer> index = new ArrayList<Integer>();
						index.add(i);
						map.put("index", index);
						map.put("budgetMoney", fdCanUseAmount);
						budgetMap.put(fdBudgetId, map);
					}
				}
				detailData.put(mapp.getFdRuleId().replaceAll("\\$", ""), status);
				detailData.put(mapp.getFdRuleId().replaceAll("\\$", "").replace("_budget_status", "_budget_info"), budgetList.toString().replaceAll("\"", "\'"));
			}
			//校验多条明细占同一预算的情况
			Iterator<String> it = budgetMap.keySet().iterator();
			for(;it.hasNext();){
				String key = it.next();
				Map<String,Object> map = budgetMap.get(key);
				Double useMoney = (Double) map.get("useMoney");
				Double budMoney = (Double) map.get("budgetMoney");
				List<Integer> index = (List<Integer>) map.get("index");
				String forbid = (String) map.get("forbid");
				if(useMoney>budMoney){
					if("1".equals(forbid)||"3".equals(forbid)){
						rtn.put("pass", false);
						rtn.put("data", data);
						rtn.put("message", ResourceUtil.getString("tips.budget.over.row.mobile","fssc-fee").replace("{0}", fdTableName).replace("{row}", index.size()+""));
						return rtn;
					}
					//覆盖预算状态
					for(Integer i:index){
						detailList.getJSONObject(i).put(mapp.getFdRuleId().replaceAll("\\$", ""), "2");
					}
				}
			}
		}else{//预算在主表
			String fdExpenseItemId = (String) getValueByProperty(mapp.getFdExpenseItemId(), data, null);
			if(StringUtil.isNull(fdExpenseItemId)){
				rtn.put("pass", true);
				rtn.put("data", data);
				return rtn;
			}
			String fdCompanyId = (String) getValueByProperty(mapp.getFdCompanyId(), data, null);
			String fdCostCenterId = (String) getValueByProperty(mapp.getFdCostCenterId(), data, null);
			String fdProjectId = (String) getValueByProperty(mapp.getFdProjectId(), data, null);
			String fdWbsId = (String) getValueByProperty(mapp.getFdWbsId(), data, null);
			String fdInnerOrderId = (String) getValueByProperty(mapp.getFdInnerOrderId(), data, null);
			String fdPersonId = (String) getValueByProperty(mapp.getFdPersonId(), data, null);
			String fdDeptId = (String) getValueByProperty(mapp.getFdDeptId(), data, null);
			JSONObject budgetInfo = new JSONObject();
			budgetInfo.put("fdCompanyId", fdCompanyId);
			budgetInfo.put("fdExpenseItemId", fdExpenseItemId);
			budgetInfo.put("fdCostCenterId", fdCostCenterId);
			budgetInfo.put("fdWbsId", fdWbsId);
			budgetInfo.put("fdProjectId", fdProjectId);
			budgetInfo.put("fdInnerOrderId", fdInnerOrderId);
			budgetInfo.put("fdPersonId", fdPersonId);
			budgetInfo.put("fdDeptId", fdDeptId);
			JSONObject budget = getFsscCommonBudgetService().matchFsscBudget(budgetInfo);
			JSONArray budgetList = budget.getJSONArray("data");
			if(budgetList==null){
				budgetList = new JSONArray();
			}
			data.put(mapp.getFdRuleId().replaceAll("\\$", "").replace("_budget_status","_budget_info"), budgetList.toString().replaceAll("\"", "\'"));
			String fdCurrencyId = (String) getValueByProperty(mapp.getFdCurrencyId(), data, null);
			//获取汇率
			Double budgetRate = getEopBasedataExchangeService().getBudgetRate(fdCurrencyId, fdCompanyId);
			Double money = Double.valueOf((String) getValueByProperty(mapp.getFdMoneyId(), data, null));
			Double budgetMoney = FsscNumberUtil.getMultiplication(money, budgetRate);
			//默认无预算
			String status = "0";
			for(int k=0;k<budgetList.size();k++){
				status = "2".equals(status)?status:"1";
				//判断是否超出可使用额度，需要加上弹性比例
				Double fdCanUseAmount = budgetList.getJSONObject(k).getDouble("fdCanUseAmount");
				Double fdElasticPercent = 0d;
				if(budgetList.getJSONObject(k).containsKey("fdElasticPercent")){
					fdElasticPercent = budgetList.getJSONObject(k).getDouble("fdElasticPercent");
					fdElasticPercent = FsscNumberUtil.getDivide(fdElasticPercent, 100);
				}
				fdElasticPercent = fdElasticPercent==null?0d:fdElasticPercent;
				Double fdTotalAmount = budgetList.getJSONObject(k).getDouble("fdTotalAmount");
				if(fdCanUseAmount!=null&&fdCanUseAmount<budgetMoney){
					status = "2";
				}
				fdCanUseAmount =  FsscNumberUtil.getAddition(fdCanUseAmount, FsscNumberUtil.getMultiplication(fdTotalAmount, fdElasticPercent));
				//刚控且超预算，不能提交
				if(fdCanUseAmount!=null&&fdCanUseAmount<budgetMoney){
					if("1".equals(budgetList.getJSONObject(k).get("fdForbid"))){
						rtn.put("pass", false);
						rtn.put("data", data);
						rtn.put("message", ResourceUtil.getString("tips.budget.over","fssc-fee"));
						return rtn;
					}
				}
				
			}
			data.put(mapp.getFdRuleId().replaceAll("\\$", ""), status);
			data.put(mapp.getFdRuleId().replaceAll("\\$", "").replace("_budget_status", "_budget_info"), budgetList.toString().replaceAll("\"", "\'"));
		}
		if(!rtn.containsKey("pass")){
			rtn.put("pass", true);
			rtn.put("data", data);
		}
		return rtn;
	}
	/**
	 * 校验是否超标准
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject checkMobileStandard(JSONObject data) throws Exception{
		JSONObject rtn = new JSONObject();
		String fdTemplateId = data.getString("fdTemplateId");
		FsscFeeTemplate temp = (FsscFeeTemplate) fsscFeeMainService.findByPrimaryKey(fdTemplateId, FsscFeeTemplate.class, true);
		String hql = "from "+FsscFeeMapp.class.getName()+" where fdTemplate.fdId=:fdTemplateId";
		List<FsscFeeMapp> list = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdTemplateId", fdTemplateId).list();
		//如果没有配置台账映射，则不校验标准
		if(ArrayUtil.isEmpty(list)){
			rtn.put("pass", true);
			rtn.put("data", data);
			return rtn;
		}
		FsscFeeMapp mapp = list.get(0);
		//如果没有配置标准规则，不校验标准
		if(StringUtil.isNull(mapp.getFdStandardId())){
			rtn.put("pass", true);
			rtn.put("data", data);
			return rtn;
		}
		//标准在明细
		if(mapp.getFdStandardId().indexOf(".")>-1){
			String fdTableId = mapp.getFdStandardId().replaceAll("\\$", "").split("\\.")[0];
			//未在移动表单中配置对应明细表，不校验标准
			if(!data.containsKey(fdTableId)){
				rtn.put("pass", true);
				rtn.put("data", data);
				return rtn;
			}
			JSONArray detailList = data.getJSONArray(fdTableId);
			FsscFeeMobileConfig table = getConfigByProperty(temp.getFdConfig(), mapp.getFdTableId());
			String fdTableName = mapp.getFdTable().replaceAll("\\$", "");
			if(table!=null){
				fdTableName = table.getFdFieldName();
			}
			for(int i=0;i<detailList.size();i++){
				JSONObject detailData = detailList.getJSONObject(i);
				String fdExpenseItemId = (String) getValueByProperty(mapp.getFdExpenseItemId(), data, detailData);
				if(StringUtil.isNull(fdExpenseItemId)){
					continue;
				}
				String fdCompanyId = (String) getValueByProperty(mapp.getFdCompanyId(), data, detailData);
				String fdAreaId = (String) getValueByProperty(mapp.getFdAreaId(), data, detailData);
				String fdPersonId = (String) getValueByProperty(mapp.getFdPersonId(), data, detailData);
				String fdVehicleId = (String) getValueByProperty(mapp.getFdVehicleId(), data, detailData);
				String fdPersonNum = (String) getValueByProperty(mapp.getFdPersonNumId(), data, detailData);
				Double money = Double.valueOf((String) getValueByProperty(mapp.getFdMoneyId(), data, detailData));
				String days = (String) getValueByProperty(mapp.getFdTravelDaysId(), data, detailData);
				Integer fdTravelDays = StringUtil.isNotNull(days)?Integer.parseInt(days):1;
				String fdCurrencyId = (String) getValueByProperty(mapp.getFdCurrencyId(), data, detailData);
				JSONObject standardInfo = new JSONObject();
				standardInfo.put("fdCompanyId", fdCompanyId);
				standardInfo.put("fdExpenseItemId", fdExpenseItemId);
				standardInfo.put("fdPersonId", fdPersonId);
				standardInfo.put("fdAreaId", StringUtil.isNotNull(fdAreaId)?fdAreaId.split("__")[1]:"");
				standardInfo.put("fdMoney", money);
				standardInfo.put("fdPersonNumber", StringUtil.isNull(fdPersonNum)?1:Integer.parseInt(fdPersonNum));
				standardInfo.put("fdTravelDays", fdTravelDays);
				standardInfo.put("fdCurrencyId", fdCurrencyId);
				standardInfo.put("fdVehicleId", fdVehicleId);
				JSONObject standard = getEopBasedataStandardService().getStandardData(standardInfo);
				if("0".equals(standard.get("submit"))){
					rtn.put("pass", false);
					rtn.put("data", data);
					rtn.put("message", ResourceUtil.getString("tips.standard.over.row.mobile","fssc-fee").replace("{0}", fdTableName).replace("{row}", (i+1)+""));
					return rtn;
				}
				detailData.put(mapp.getFdStandardId().replaceAll("\\$", ""), standard.get("status"));
			}
		}else{//标准在主表
			String fdExpenseItemId = (String) getValueByProperty(mapp.getFdExpenseItemId(), data, null);
			if(StringUtil.isNull(fdExpenseItemId)){
				return rtn;
			}
			String fdCompanyId = (String) getValueByProperty(mapp.getFdCompanyId(), data, null);
			String fdAreaId = (String) getValueByProperty(mapp.getFdAreaId(), data, null);
			String fdPersonId = (String) getValueByProperty(mapp.getFdPersonId(), data, null);
			String fdVehicleId = (String) getValueByProperty(mapp.getFdVehicleId(), data, null);
			Number fdPersonNum = (Number) getValueByProperty(mapp.getFdPersonNumId(), data, null);
			Double money = Double.valueOf((String) getValueByProperty(mapp.getFdMoneyId(), data, null));
			String fdCurrencyId = (String) getValueByProperty(mapp.getFdCurrencyId(), data, null);
			JSONObject standardInfo = new JSONObject();
			standardInfo.put("fdCompanyId", fdCompanyId);
			standardInfo.put("fdExpenseItemId", fdExpenseItemId);
			standardInfo.put("fdPersonId", fdPersonId);
			standardInfo.put("fdAreaId", fdAreaId);
			standardInfo.put("fdMoney", money);
			standardInfo.put("fdPersonNumber", fdPersonNum);
			standardInfo.put("fdCurrencyId", fdCurrencyId);
			standardInfo.put("fdVehicleId", fdVehicleId);
			JSONObject standard = getEopBasedataStandardService().getStandardData(standardInfo);
			if("0".equals(standard.get("submit"))){
				rtn.put("pass", false);
				rtn.put("data", data);
				rtn.put("message", ResourceUtil.getString("tips.standard.over.mobile","fssc-fee").replace("{0}", mapp.getFdTable().replaceAll("\\$", "")));
				return rtn;
			}
			data.put(mapp.getFdStandardId().replaceAll("\\$", ""), standard.get("status"));
		}
		if(!rtn.containsKey("pass")){
			rtn.put("pass", true);
			rtn.put("data", data);
		}
		return rtn;
	}

	private FsscFeeMobileConfig getConfigByProperty(List<FsscFeeMobileConfig> list,String property){
		if(StringUtil.isNull(property)||ArrayUtil.isEmpty(list)){
			return null;
		}
		for(FsscFeeMobileConfig config:list){
			if(property.indexOf(config.getFdTemplateFieldId())>-1){
				return config;
			}
		}
		return null;
	}
	
	private Object getValueByProperty(String id,JSONObject main,JSONObject detail){
		Object rtnVal = "";
		if(StringUtil.isNull(id)){
			return null;
		}
		id = id.replaceAll("\\$", "");
		if(id.indexOf(".")>-1){
			rtnVal = detail.containsKey(id)?detail.get(id):"";
		}else{
			rtnVal = main.containsKey(id)?main.get(id):"";
		}
		if(rtnVal!=null&&rtnVal instanceof JSONArray){
			JSONArray rtn = (JSONArray) rtnVal;
			return rtn.size()>0?rtn.get(0).toString():"";
		}
		return rtnVal.toString();
	}

	@Override
	public JSONObject checkHasBudget(JSONObject data) throws Exception {
		JSONObject rtn = new JSONObject();
		//如果没有预算模块，不校验预算
		if(!FsscCommonUtil.checkHasModule("/fssc/budget/")){
			rtn.put("pass", true);
			rtn.put("data", data);
			return rtn;
		}
		String fdTemplateId = data.getString("fdTemplateId");
		FsscFeeTemplate temp = (FsscFeeTemplate) fsscFeeMainService.findByPrimaryKey(fdTemplateId, FsscFeeTemplate.class, true);
		String hql = "from "+FsscFeeMapp.class.getName()+" where fdTemplate.fdId=:fdTemplateId";
		List<FsscFeeMapp> list = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdTemplateId", fdTemplateId).list();
		//如果没有配置台账映射，则不校验预算
		if(ArrayUtil.isEmpty(list)){
			rtn.put("pass", true);
			rtn.put("data", data);
			return rtn;
		}
		//如果没有配置预算规则，不校验预算
		FsscFeeMapp mapp = list.get(0);
		if(StringUtil.isNull(mapp.getFdRuleId())){
			rtn.put("pass", true);
			rtn.put("data", data);
			return rtn;
		}
		//预算在明细
		if(mapp.getFdRuleId().indexOf(".")>-1){
			String fdTableId = mapp.getFdRuleId().replaceAll("\\$", "").split("\\.")[0];
			//未在移动表单中配置对应明细表，不校验预算
			if(!data.containsKey(fdTableId)){
				rtn.put("pass", true);
				rtn.put("data", data);
				return rtn;
			}
			FsscFeeMobileConfig table = getConfigByProperty(temp.getFdConfig(), mapp.getFdTableId());
			String fdTableName = mapp.getFdTable().replaceAll("\\$", "");
			if(table!=null){
				fdTableName = table.getFdFieldName();
			}
			JSONArray detailList = data.getJSONArray(fdTableId);
			hql = "from "+FsscFeeExpenseItem.class.getName()+" where fdCompany.fdId=:fdCompanyId and fdTemplate.fdId=:fdTemplateId and fdItemList.fdId=:fdExpenseItemId";
			for(int i=0;i<detailList.size();i++){
				JSONObject detailData = detailList.getJSONObject(i);
				String fdExpenseItemId = (String) getValueByProperty(mapp.getFdExpenseItemId(), data, detailData);
				String fdExpenseItemName = mapp.getFdExpenseItemId().replaceAll("\\$", "");
				fdExpenseItemName = detailData.getString(fdExpenseItemName+".name");
				String fdCompanyId = (String) getValueByProperty(mapp.getFdCompanyId(), data, detailData);
				Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
				query.setParameter("fdCompanyId", fdCompanyId);
				query.setParameter("fdTemplateId", fdTemplateId);
				query.setParameter("fdExpenseItemId", fdExpenseItemId);
				List<FsscFeeExpenseItem> items = query.list();
				if(!ArrayUtil.isEmpty(items)){
					for(FsscFeeExpenseItem item:items){
						if(item.getFdIsNeedBudget()){
							String key = mapp.getFdRuleId().replaceAll("\\$", "").replace("_budget_status", "_budget_info");
							String message = ResourceUtil.getString("tips.expense.budgetRequired.mobile","fssc-fee")
									.replace("{0}", fdTableName)
									.replace("{1}", (i+1)+"")
									.replace("{2}", fdExpenseItemName);
							if(!detailData.containsKey(key)){
								rtn.put("pass", false);
								rtn.put("data", data);
								rtn.put("message", message);
								return rtn;
							}
							String budgetInfo = detailData.getString(key);
							if(StringUtil.isNull(budgetInfo)||"[]".equals(budgetInfo)){
								rtn.put("pass", false);
								rtn.put("data", data);
								rtn.put("message", message);
								return rtn;
							}
						}
					}
				}
			}
		}else{//预算在主表
			String fdExpenseItemId = (String) getValueByProperty(mapp.getFdExpenseItemId(), data, null);
			String fdExpenseItemName = mapp.getFdExpenseItemId().replaceAll("\\$", "");
			fdExpenseItemName = data.getString(fdExpenseItemName+"_name");
			String fdCompanyId = (String) getValueByProperty(mapp.getFdCompanyId(), data, null);
			Query query = fsscFeeMainService.getBaseDao().getHibernateSession().createQuery(hql);
			query.setParameter("fdCompanyId", fdCompanyId);
			query.setParameter("fdTemplateId", fdTemplateId);
			query.setParameter("fdExpenseItemId", fdExpenseItemId);
			List<FsscFeeExpenseItem> items = query.list();
			if(!ArrayUtil.isEmpty(items)){
				for(FsscFeeExpenseItem item:items){
					if(item.getFdIsNeedBudget()){
						String key = mapp.getFdRuleId().replaceAll("\\$", "").replace("_budget_status", "_budget_info");
						String message = ResourceUtil.getString("tips.expense.budgetRequired.mobile.main","fssc-fee").replace("{2}", fdExpenseItemName);
						if(!data.containsKey(key)){
							rtn.put("pass", false);
							rtn.put("data", data);
							rtn.put("message", message);
							return rtn;
						}
						String budgetInfo = data.getString(key);
						if(StringUtil.isNull(budgetInfo)||"[]".equals(budgetInfo)){
							rtn.put("pass", false);
							rtn.put("data", data);
							rtn.put("message", message);
							return rtn;
						}
					}
				}
			}
		}
		if(!rtn.containsKey("pass")){
			rtn.put("pass", true);
			rtn.put("data", data);
		}
		return rtn;
	}

	@Override
	public void updateCloseFee(String fdFeeId) throws Exception {
		fsscFeeMainService.updateCloseFee(fdFeeId);
	}

}
