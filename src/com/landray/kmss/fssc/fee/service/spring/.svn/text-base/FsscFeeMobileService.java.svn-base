package com.landray.kmss.fssc.fee.service.spring;


import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonFeeService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.fee.model.FsscFeeMain;
import com.landray.kmss.fssc.fee.model.FsscFeeMapp;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.fssc.fee.service.IFsscFeeMainService;
import com.landray.kmss.fssc.fee.service.IFsscFeeMappService;
import com.landray.kmss.fssc.fee.service.IFsscFeeTemplateService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscFeeMobileService extends ExtendDataServiceImp {
	
	public Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	private IFsscFeeMappService fsscFeeMappService;
	
	public void setFsscFeeMappService(IFsscFeeMappService fsscFeeMappService) {
		this.fsscFeeMappService = fsscFeeMappService;
	}
	
	private IFsscCommonFeeService fsscCommonFeeService;
	public IFsscCommonFeeService getFsscCommonFeeService() {
		if (fsscCommonFeeService == null) {
			fsscCommonFeeService = (IFsscCommonFeeService) SpringBeanUtil.getBean("fsscCommonFeeService");
		}
		return fsscCommonFeeService;
	}
	
	 private ISysAttMainCoreInnerService sysAttMainService;

	public ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}
	
	private IEopBasedataCompanyService eopBasedataCompanyService;
    
	public IEopBasedataCompanyService getEopBasedataCompanyService() {
		if (eopBasedataCompanyService == null) {
			eopBasedataCompanyService = (IEopBasedataCompanyService) SpringBeanUtil.getBean("eopBasedataCompanyService");
		}
		return eopBasedataCompanyService;
	}
	
	private IFsscFeeMainService fsscFeeMainService;
    
	public IFsscFeeMainService getFsscFeeMainService() {
		if (fsscFeeMainService == null) {
			fsscFeeMainService = (IFsscFeeMainService) SpringBeanUtil.getBean("fsscFeeMainService");
		}
		return fsscFeeMainService;
	}
	
	private IFsscFeeTemplateService fsscFeeTemplateService;
	
	public IFsscFeeTemplateService getFsscFeeTemplateService() {
		if (fsscFeeTemplateService == null) {
			fsscFeeTemplateService = (IFsscFeeTemplateService) SpringBeanUtil.getBean("fsscFeeTemplateService");
		}
		return fsscFeeTemplateService;
	}

	public Map getMoreInfo(Page page) throws Exception{
		List<FsscFeeMain> feeList=page.getList();
		HQLInfo hqlInfo = new HQLInfo();
		List<FsscFeeMapp> list = fsscFeeMappService.findList(hqlInfo);
		Map<String,FsscFeeMapp> mapping=new HashMap<String,FsscFeeMapp>();
		for(FsscFeeMapp mapp:list){
			mapping.put(mapp.getFdTemplate().getFdId(), mapp);
		}
		Map rtnMap=new HashMap();
		Map map=new HashMap();
		String fdCompanyId="";
		for(FsscFeeMain fee:feeList){
			String templateId= fee.getDocTemplate().getFdId();
			FsscFeeMapp mapp=mapping.containsKey(templateId)?mapping.get(templateId):null;
			if(mapp!=null){
				FormulaParser parser = FormulaParser.getInstance(fee);
				map=new HashMap();
				List<Object> detailList = getDetailList(mapp,parser);
				//存在明细表，按明细生成
				if(!ArrayUtil.isEmpty(detailList)){
					for(int i=0;i<detailList.size();i++){
						Object money = getValue(mapp, parser, "fdMoneyId", i);
						Double fdApplyMoney = 0d;
						try{
							fdApplyMoney = ((Number)money).doubleValue();
						}catch(Exception e){
						}
					}
					map.put("standardMoney", getFsscFeeMainService().getTotalMoney(fee.getFdId()));
				}else{
					Object money = getValue(mapp, parser, "fdMoneyId", 0);
					Double fdApplyMoney = 0d;
					try{
						fdApplyMoney = ((Number)money).doubleValue();
					}catch(Exception e){
					}
					map.put("standardMoney", getFsscFeeMainService().getTotalMoney(fee.getFdId()));
				}
				fdCompanyId=(String) getValue(mapp,parser,"fdCompanyId",0);
				if(StringUtil.isNotNull(fdCompanyId)){
					EopBasedataCompany com=(EopBasedataCompany) fsscFeeMappService.findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
					if(com!=null){
						map.put("csymbol", com.getFdAccountCurrency().getFdSymbol());
					}
				}
				rtnMap.put(fee.getFdId(), map);
			}
		}
		
		return rtnMap;
	}
	
	private List<Object> getDetailList(FsscFeeMapp mapp,FormulaParser parser) throws Exception{
		SysDictModel dict = SysDataDict.getInstance().getModel(FsscFeeMapp.class.getName());
		for(SysDictCommonProperty property:dict.getPropertyList()){
			if("fdId".equals(property.getName())||property.getName().indexOf("Id")==-1){
				continue;
			}
			try {
				Object value = parser.parseValueScript((String) PropertyUtils.getProperty(mapp, property.getName()));
				if(value instanceof List){
					return (List<Object>) value;
				}
			} catch (Exception e) {
				logger.error("", e);
			}
			
		}
		return null;
	}
	
	private Object getValue(FsscFeeMapp mapp,FormulaParser parser, String property, int i)throws Exception{
		Object value =null;
		try {
			value=parser.parseValueScript((String) PropertyUtils.getProperty(mapp, property));
			if(value instanceof List){
				List<Object> data = (List<Object>) value;
				return data.get(i);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return value;
	}
	
	/**
	 * 获取事前移动化的分类
	 * @param request 
	 * @return
	 * @throws Exception
	 */
	public JSONArray getTemplate(HttpServletRequest request) throws Exception{
		JSONArray templateArr=new JSONArray();
		String keyword=request.getParameter("keyword");
		HQLInfo hqlInfo=new HQLInfo();
		String where="fsscFeeTemplate.fdIsMobile=:fdIsMobile";
		hqlInfo.setParameter("fdIsMobile", Boolean.TRUE);
		if(StringUtil.isNotNull(keyword)){
			where=StringUtil.linkString(where, " and ", " fsscFeeTemplate.fdName like:keyword");
		}
		hqlInfo.setWhereBlock(where);
		if(StringUtil.isNotNull(keyword)){
			hqlInfo.setParameter("keyword", "%"+keyword+"%");
		}
		hqlInfo.setRowSize(1000);  //分类显示条数
		Page page=getFsscFeeTemplateService().findPage(hqlInfo);
		if(page!=null){
			List<FsscFeeTemplate> templateList=page.getList();
			for(FsscFeeTemplate template:templateList){
				JSONObject jsonObj=new JSONObject();
				SysCategoryMain fdParent = (SysCategoryMain) template.getDocCategory();
				String fdTempName = "";
				if(null != fdParent){
					fdTempName = template.getFdName() +"/(";
					StringBuffer fdParentName = new StringBuffer();
					while (null != fdParent){
						fdParentName = fdParentName.append(fdParent.getFdName()).append("/");
						fdParent = (SysCategoryMain) fdParent.getFdParent();
					}
					fdTempName = fdTempName + fdParentName.toString().substring(0, fdParentName.toString().length()-1) +")";
				}else{
					fdTempName = template.getFdName();
				}
				jsonObj.put("value", template.getFdId());
				jsonObj.put("text", fdTempName);
				templateArr.add(jsonObj);
			}
		}
		return templateArr;
	}
	
	public Map<String,List<Map<String,String>>> getFormFields(HttpServletRequest request) throws Exception {
		Map<String,List<Map<String,String>>> fieldList=new HashMap<>();
		try {
			if(FsscCommonUtil.checkHasModule("/fssc/fee/")){
				fieldList = getFsscCommonFeeService().getFormFields(request);
			}
		} catch (Exception e) {
			logger.error("获取事前模板字段发生错误", e);
		}
		return fieldList;
	}


	/**
     * 
     * 上传附件是，保存附件，不保存fdModelId和fdModelName，提交时再更新
     * 
     */
  	public JSONObject saveAtt(HttpServletRequest request) throws Exception{
  		String key = request.getParameter("key");
  		String filename = request.getParameter("filename");
  		InputStream in = request.getInputStream();
  		SysAttMain att = new SysAttMain();
  		att.setInputStream(in);
  		att.setFdFileName(filename);
  		att.setFdKey(key);
  		String size=request.getParameter("size");
  		if(StringUtil.isNotNull(size)){
  			att.setFdSize(Double.parseDouble(size));
  		}else{
  			att.setFdSize(new Integer(in.available()).doubleValue());
  		}
  		att.setDocCreateTime(new Date());
  		if(StringUtil.isNotNull(filename)){
  			String suffix_name=filename.substring(filename.lastIndexOf(".")+1);
  			if("jpg".equals(suffix_name)){
  				att.setFdAttType("pic");
  			}else{
  				att.setFdAttType("byte");
  			}
  			if("xls".equals(suffix_name)){
  				att.setFdContentType("application/vnd.ms-excel");
  			}else if("xlsx".equals(suffix_name)){
  				att.setFdContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
  			}else if("docx".equals(suffix_name)){
  				att.setFdContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
  			}else if("pdf".equals(suffix_name)){
  				att.setFdContentType("application/pdf");
  			}else if("txt".equals(suffix_name)){
  				att.setFdContentType("text/plain");
  			}else if(FsscCommonUtil.isImageFile(filename)){
  				att.setFdContentType("image/jpeg");
  			}else{
  				att.setFdContentType("application/octet-stream");
  			}
  		}
  		getSysAttMainService().add(att);
  		JSONObject rtn = new JSONObject();
  		rtn.put("fdId", att.getFdId());
  		rtn.put("fdName", filename);
  		return rtn;
  	}


	/*
	 * 获取人员默认记账公司
	 */
	public JSONObject getDefaultCompany(HttpServletRequest request) throws Exception{
		JSONObject rtn=new JSONObject();
		JSONArray data = new JSONArray();
		String personId = request.getParameter("personId");
		if(StringUtil.isNull(personId)){
			personId = UserUtil.getUser().getFdId();
		}
		try {
			JSONObject node = new JSONObject();
			List<EopBasedataCompany> com =getEopBasedataCompanyService().findCompanyByUserId(personId);
			if(!ArrayUtil.isEmpty(com)){
				node.put("name", com.get(0).getFdName());
				node.put("id", com.get(0).getFdId());
				data.add(node);
			}
			rtn.put("result", "success");
			rtn.put("data", data);
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", "获取默认公司发生错误.");
			logger.error("获取默认公司发生错误.", e);
		}
		return rtn;
	}
	
	/*
	 * 获取人员部门
	 */
	public JSONObject getDefaultOrg(HttpServletRequest request) throws Exception{
		JSONObject rtnObj=new JSONObject();
		String personId = request.getParameter("personId");
		if(StringUtil.isNotNull(personId)){
			SysOrgElement user=(SysOrgElement) getSysAttMainService().findByPrimaryKey(personId, SysOrgElement.class, true);
			if(user!=null){
				rtnObj.put("name", user.getFdParent()!=null?user.getFdParent().getFdName():"");
				rtnObj.put("id", user.getFdParent()!=null?user.getFdParent().getFdId():"");
			}
		}
		return rtnObj;
	}
	
	


		public JSONObject getExchangeRate(HttpServletRequest request) throws Exception{
			JSONObject rtn = new JSONObject();
			String currencyId=request.getParameter("currencyId");
			String fdCompanyId=request.getParameter("fdCompanyId");
			if(StringUtil.isNotNull(fdCompanyId)&&StringUtil.isNotNull(currencyId)){
				EopBasedataCurrency currency=(EopBasedataCurrency) getSysAttMainService().findByPrimaryKey(currencyId, EopBasedataCurrency.class, true);
				EopBasedataCompany com=(EopBasedataCompany) getSysAttMainService().findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
				List<EopBasedataExchangeRate> exchangeList=getSysAttMainService().getBaseDao().getHibernateSession().createQuery(
						"select t from EopBasedataExchangeRate t left join t.fdCompanyList company where (company.fdId=:fdCompanyId or company is null) and t.fdSourceCurrency.fdId=:fdSourceCurrencyId"
						+ " and t.fdTargetCurrency.fdId=:fdTargetCurrencyId").setParameter("fdSourceCurrencyId", currency.getFdId())
						.setParameter("fdCompanyId", fdCompanyId)
						.setParameter("fdTargetCurrencyId", com.getFdAccountCurrency()!=null?com.getFdAccountCurrency().getFdId():"").list();
				if(!ArrayUtil.isEmpty(exchangeList)){
					rtn.put("rate", exchangeList.get(0).getFdRate());
				}else{
					rtn.put("message", ResourceUtil.getString("fssc.mobile.currency.not.found.tips", "fssc-mobile"));
				}
			}
			return rtn;
		}

	/**
	 * 获取公司对应字段，若是未配置，则其他公司相关只存储，不影响预算匹配	
	 * @param docTemplateId
	 * @return
	 * @throws Exception
	 */
		public String findMappFeild(String docTemplateId) throws Exception{
			JSONObject fdMappFeildObj=new JSONObject();
			List<FsscFeeMapp> mappList=fsscFeeMappService.getBaseDao().getHibernateSession().createQuery("select t from FsscFeeMapp t where t.fdTemplate.fdId=:fdTemplateId")
			.setParameter("fdTemplateId", docTemplateId).list();
			if(!ArrayUtil.isEmpty(mappList)){
				FsscFeeMapp mapp=mappList.get(0);
				fdMappFeildObj.put("fdTableId", StringUtil.isNotNull(mapp.getFdTableId())?mapp.getFdTableId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdCompanyId", StringUtil.isNotNull(mapp.getFdCompanyId())?mapp.getFdCompanyId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdCostCenterId", StringUtil.isNotNull(mapp.getFdCostCenterId())?mapp.getFdCostCenterId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdExpenseItemId", StringUtil.isNotNull(mapp.getFdExpenseItemId())?mapp.getFdExpenseItemId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdProjectId", StringUtil.isNotNull(mapp.getFdProjectId())?mapp.getFdProjectId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdInnerOrderId", StringUtil.isNotNull(mapp.getFdInnerOrderId())?mapp.getFdInnerOrderId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdWbsId", StringUtil.isNotNull(mapp.getFdWbsId())?mapp.getFdWbsId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdDeptId", StringUtil.isNotNull(mapp.getFdDeptId())?mapp.getFdDeptId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdPersonId", StringUtil.isNotNull(mapp.getFdPersonId())?mapp.getFdPersonId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdMoneyId", StringUtil.isNotNull(mapp.getFdMoneyId())?mapp.getFdMoneyId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdCurrencyId", StringUtil.isNotNull(mapp.getFdCurrencyId())?mapp.getFdCurrencyId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdRuleId", StringUtil.isNotNull(mapp.getFdRuleId())?mapp.getFdRuleId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdTravelDaysId", StringUtil.isNotNull(mapp.getFdTravelDaysId())?mapp.getFdTravelDaysId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdPersonNumId", StringUtil.isNotNull(mapp.getFdPersonNumId())?mapp.getFdPersonNumId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdAreaId", StringUtil.isNotNull(mapp.getFdAreaId())?mapp.getFdAreaId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdVehicleId", StringUtil.isNotNull(mapp.getFdVehicleId())?mapp.getFdVehicleId().replaceAll("\\$", ""):"");
				fdMappFeildObj.put("fdStandardId",StringUtil.isNotNull(mapp.getFdStandardId())? mapp.getFdStandardId().replaceAll("\\$", ""):"");
			}
			return fdMappFeildObj.toString().replaceAll("\"", "&quot;");
		}


	public Boolean getIsNeedBudget(HttpServletRequest request) throws Exception{
		String docTemplateId=request.getParameter("docTemplateId");
		Boolean idNeedBudget=Boolean.FALSE;   //默认是不需要强制校验预算
		List<String> resultList=fsscFeeMappService.getBaseDao().getHibernateSession().createQuery("select fdIsNeedBudget from FsscFeeExpenseItem where fdTemplate.fdId=:fdTemplateId")
				.setParameter("fdTemplateId", docTemplateId).list();
		if(!ArrayUtil.isEmpty(resultList)&&resultList.get(0)!=null){
			String result=String.valueOf(resultList.get(0));
			idNeedBudget=StringUtil.isNotNull(result)&&"true".equals(result)?Boolean.TRUE:Boolean.FALSE;
		}
		return idNeedBudget;
	}
	
	/**
	 * 获取币种符号
	 * @param request 
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getCurrencyData() throws Exception {
		Map<String,String> curencyMap=new HashMap<>();
		List<EopBasedataCurrency> currencyList=fsscFeeMappService.getBaseDao().getHibernateSession().createQuery("select t from EopBasedataCurrency t where t.fdStatus=:fdStatus")
				.setParameter("fdStatus", 0).list();
		for(EopBasedataCurrency currency:currencyList){
			curencyMap.put(currency.getFdId(), currency.getFdSymbol());
		}
		return curencyMap;
	}
	
	/**
  	 * 
  	 * 上传附件是，保存附件，不保存fdModelId和fdModelName，提交时再更新
  	 * 
  	 */
  	public JSONObject updateAtt(HttpServletRequest request) throws Exception{
  		String params=request.getParameter("params");
  		JSONObject rtn = new JSONObject();
  		if(StringUtil.isNotNull(params)){
  			JSONArray data = JSONArray.fromObject(params);
  			for(int i=0,size=data.size();i<size;i++){
  				JSONObject dataObj=data.getJSONObject(i);
  				String fdAttId = dataObj.containsKey("fdAttId")?dataObj.getString("fdAttId"):"";
  				if(StringUtil.isNotNull(fdAttId)){
	  	  	  		String fdModelId=dataObj.containsKey("fdModelId")?dataObj.getString("fdModelId"):"";
	  	  	  		String fdModelName=dataObj.containsKey("fdModelName")?dataObj.getString("fdModelName"):"";
	  	  	  		SysAttMain att=(SysAttMain) getSysAttMainService().findByPrimaryKey(fdAttId, SysAttMain.class, true);
	  	  	  		att.setFdModelId(fdModelId);
	  	  	  		att.setFdModelName(fdModelName);
	  	  	  		getSysAttMainService().update(att);
  				}
  			}
  		}
  		rtn.put("result", "success");
  		return rtn;
  	}
}
