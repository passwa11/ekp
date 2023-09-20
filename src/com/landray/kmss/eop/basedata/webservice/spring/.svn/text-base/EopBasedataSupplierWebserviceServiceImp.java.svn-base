package com.landray.kmss.eop.basedata.webservice.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataContact;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplierAccount;
import com.landray.kmss.eop.basedata.service.IEopBasedataSupplierService;
import com.landray.kmss.eop.basedata.webservice.IEopBasedataSupplierWebserviceService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/eop-basedata/eopBasedataSupplierWebserviceService", method = RequestMethod.POST)
@RestApi(docUrl = "/eop/basedata/eop_basedata_supplier_webservice/eopBasedataSupplier_webserviceHelp.jsp", name = "eopBasedataSupplierRestService", resourceKey = "eop-basedata:eopBasedataSupplierWebserviceService.title")
public class EopBasedataSupplierWebserviceServiceImp implements IEopBasedataSupplierWebserviceService{

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	private IEopBasedataSupplierService eopBasedataSupplierService;

	public void setEopBasedataSupplierService(IEopBasedataSupplierService eopBasedataSupplierService) {
		this.eopBasedataSupplierService = eopBasedataSupplierService;
	}

	@Override
	public String outsideBaseSupplier(String str) throws Exception{
		logger.info("请求的数据:" + str);
		
		StringBuilder returnMsg = new StringBuilder();
		
		// 校验信息
		JSONArray jsonArray = null;
		
		List<EopBasedataSupplier> slist=new ArrayList();
		
		EopBasedataSupplier supplier=new EopBasedataSupplier();
		Map<String,EopBasedataCompany> companyMap=getCompanyCache();  //缓存记账公司数据
		
		String fdCompanyCode = null, fdCode = null, fdName = null, fdAbbreviation = null, fdTaxNo = null, fdErpNo = null,
				fdCreditCode = null, fdCodeValidityPeriod = null, fdIndustry = null, fdLegalPerson = null, fdRegistCapital = null, fdEstablishDate = null,
				fdAddress = null, fdUrl= null, fdBusinessScope = null, fdDesc = null, fdIsAvailable = null, accountDetail = null, contactDetail = null;
		try{
			jsonArray = JSONArray.fromObject(str);
			if (jsonArray != null&&!jsonArray.isEmpty()) {
				for(int i = 0; i < jsonArray.size(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					fdCompanyCode = json.optString("fdCompanyCode");	//所属公司编码
					fdCode = json.optString("fdCode");	//编码
					fdName = json.optString("fdName");	//名称
					fdAbbreviation = json.optString("fdAbbreviation");	//简称
					fdTaxNo = json.optString("fdTaxNo");	//纳税人识别号
					fdErpNo = json.optString("fdErpNo");	//ERP号
					fdCreditCode = json.optString("fdCreditCode");	//统一社会信用代码
					fdCodeValidityPeriod = json.optString("fdCodeValidityPeriod");	//信用证有效截止日期
					fdIndustry = json.optString("fdIndustry");	//所属行业
					fdLegalPerson = json.optString("fdLegalPerson");	//法人代表
					fdRegistCapital = json.optString("fdRegistCapital");	//注册资金
					fdEstablishDate = json.optString("fdEstablishDate");	//成立日期
					fdAddress = json.optString("fdAddress");	//企业地址
					fdUrl = json.optString("fdUrl");	//企业网址
					fdBusinessScope = json.optString("fdBusinessScope");	//经营范围
					fdDesc = json.optString("fdDesc");	//企业简介
					fdIsAvailable = json.optString("fdIsAvailable");	//是否有效
					accountDetail = json.optString("accountDetail");	//银行账户信息
					contactDetail = json.optString("contactDetail");	//联系人信息
					if(StringUtil.isNotNull(fdCompanyCode)) {	//所属公司不能为空
						String[] codes=fdCompanyCode.split(";");
						for(String code:codes){
							if(!companyMap.containsKey(code)){
								returnMsg.append(ResourceUtil.getString("message.isNotExist.fdCompany", "eop-basedata").replace("%rowNum%", (i+1) + ""));
							}
						}
					}
					if(StringUtil.isNull(fdName)) {	//名称不能为空
						returnMsg.append(ResourceUtil.getString("message.isNull.fdName", "eop-basedata").replace("%rowNum%", (i+1) + ""));
					}
					if(StringUtil.isNull(fdCode)) {	//编码不能为空
						returnMsg.append(ResourceUtil.getString("message.isNull.fdCode", "eop-basedata").replace("%rowNum%", (i+1) + ""));
					}
					if(StringUtil.isNull(fdCreditCode)){	//统一社会信用代码不能为空
						returnMsg.append(ResourceUtil.getString("message.isNull.fdCreditCode", "eop-basedata").replace("%rowNum%", (i+1) + ""));
					}
					if(StringUtil.isNull(fdCodeValidityPeriod)){	//信用证有效截止日期不能为空
						returnMsg.append(ResourceUtil.getString("message.isNull.fdCodeValidityPeriod", "eop-basedata").replace("%rowNum%", (i+1) + ""));
					}
					if(StringUtil.isNotNull(accountDetail)) {
						JSONArray accountJson = JSONArray.fromObject(accountDetail);	//把字符串转成JSONArray
						if(accountJson.size()>0) {
							for(int j=0; j<accountJson.size(); j++) {
								JSONObject acc = accountJson.getJSONObject(j);	//遍历JSONArray数组
								if(StringUtil.isNotNull(acc.optString("fdSupplierArea")))	{	//校验境内、境外银行账户必填项
									if(!"01".contains(acc.optString("fdSupplierArea"))) {
										returnMsg.append(ResourceUtil.getString("message.isError.fdSupplierArea", "eop-basedata").replace("%rowNum%", (i+1) + "").replace("%rowNum2%", (j+1) + ""));
									} else if("0".equals(acc.optString("fdSupplierArea"))) {	//境内
										if(StringUtil.isNull(acc.optString("fdAccountName")) || StringUtil.isNull(acc.optString("fdBankName")) || StringUtil.isNull(acc.optString("fdBankAccount"))) {
											returnMsg.append(ResourceUtil.getString("message.isNull.fdSupplierArea.0", "eop-basedata").replace("%rowNum%", (i+1) + "").replace("%rowNum2%", (j+1) + ""));
										}
									} else if("1".equals(acc.optString("fdSupplierArea"))) {	//境外
										if(StringUtil.isNull(acc.optString("fdAccountName")) || StringUtil.isNull(acc.optString("fdBankName")) || StringUtil.isNull(acc.optString("fdBankAccount"))
												|| StringUtil.isNull(acc.optString("fdBankSwift")) || StringUtil.isNull(acc.optString("fdReceiveCompany")) || StringUtil.isNull(acc.optString("fdReceiveBankName")) || StringUtil.isNull(acc.optString("fdReceiveBankAddress"))) {
											returnMsg.append(ResourceUtil.getString("message.isNull.fdSupplierArea.1", "eop-basedata").replace("%rowNum%", (i+1) + "").replace("%rowNum2%", (j+1) + ""));
										}
									}
								}else{
									if(StringUtil.isNull(acc.optString("fdAccountName")) || StringUtil.isNull(acc.optString("fdBankName")) || StringUtil.isNull(acc.optString("fdBankAccount"))) {
										returnMsg.append(ResourceUtil.getString("message.isNull.fdSupplierArea.0", "eop-basedata").replace("%rowNum%", (i+1) + "").replace("%rowNum2%", (j+1) + ""));
									}
								}
							}
						}
					}
					if(StringUtil.isNotNull(contactDetail)) {
						JSONArray contactJson = JSONArray.fromObject(contactDetail);	//把字符串转成JSONArray
						if(contactJson.size()>0) {
							for(int j=0; j<contactJson.size(); j++) {
								JSONObject con = contactJson.getJSONObject(j);	//遍历JSONArray数组
								if(StringUtil.isNull(con.optString("fdName")) || StringUtil.isNull(con.optString("fdPhone")) || StringUtil.isNull(con.optString("fdEmail"))) {
									returnMsg.append(ResourceUtil.getString("message.isNull.fdContactPerson", "eop-basedata").replace("%rowNum%", (i+1) + "").replace("%rowNum2%", (j+1) + ""));
								}
							}
						}
					}
					supplier=getSupplier(fdCompanyCode,fdCode,fdName,fdAbbreviation,fdTaxNo,fdErpNo,
							fdCreditCode,fdCodeValidityPeriod,fdIndustry,fdLegalPerson,fdRegistCapital,fdEstablishDate,
							fdAddress,fdUrl,fdBusinessScope,fdDesc,fdIsAvailable,accountDetail,contactDetail);
					slist.add(supplier);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("传输的JSON串格式不正确" + e);
			returnMsg.append("传输的JSON串格式不正确,详细 :" + e);
		}
		JSONObject retJson = new JSONObject();
		if (StringUtil.isNotNull(returnMsg.toString())) {
			retJson.put("result", "1");// 返回标识 "0"代表成功，其他数字代表失败的错误码
			retJson.put("errormsg", returnMsg.toString());// 失败信息
		} else {
			retJson = this.addSupplier(slist);
		}
		return retJson.toString();
	}
	
	public EopBasedataSupplier getSupplier(String fdCompanyCode,String fdCode,String fdName,String fdAbbreviation,String fdTaxNo,String fdErpNo,
								String fdCreditCode,String fdCodeValidityPeriod,String fdIndustry,String fdLegalPerson,String fdRegistCapital,String fdEstablishDate,
								String fdAddress,String fdUrl,String fdBusinessScope,String fdDesc,String fdIsAvailable,String accountDetail,String contactDetail) throws Exception{
		EopBasedataSupplier supplier = new EopBasedataSupplier();
		EopBasedataSupplierAccount account = null;	//银行账户信息
		EopBasedataContact contact = null;	//联系人信息
		List<EopBasedataSupplierAccount> accountList = new ArrayList<EopBasedataSupplierAccount>();
		List<EopBasedataContact> contactList = new ArrayList<EopBasedataContact>();
		Map<String,EopBasedataCompany> companyMap=getCompanyCache();  //缓存记账公司数据
		if(StringUtil.isNotNull(fdCompanyCode)) {	//装入所属公司
			List<EopBasedataCompany> companyList=new ArrayList<EopBasedataCompany>();
			String[] codes=fdCompanyCode.split(";");
			for(String code:codes) {
				companyList.add((EopBasedataCompany) companyMap.get(code));
			}
			supplier.getFdCompanyList().clear();
			supplier.getFdCompanyList().addAll(companyList);
		}
		supplier.setFdCode(fdCode);	//装入编码
		supplier.setFdName(fdName);	//装入名称
		supplier.setFdAbbreviation(fdAbbreviation);	//装入简称
		supplier.setFdTaxNo(fdTaxNo);	//装入纳税人识别号
		supplier.setFdErpNo(fdErpNo);	//装入ERP号
		supplier.setFdCreditCode(fdCreditCode);	//装入统一社会信用代码
		if(StringUtil.isNotNull(fdCodeValidityPeriod)) {	//装入信用证有效截止日期
			supplier.setFdCodeValidityPeriod(DateUtil.convertStringToDate(fdCodeValidityPeriod,DateUtil.PATTERN_DATE));
		}
		supplier.setFdIndustry(fdIndustry);	//装入所属行业
		supplier.setFdLegalPerson(fdLegalPerson);	//装入法人代表
		supplier.setFdRegistCapital(fdRegistCapital);	//装入注册资金
		if(StringUtil.isNotNull(fdEstablishDate)){	//装入成立日期
			supplier.setFdEstablishDate(DateUtil.convertStringToDate(fdEstablishDate,DateUtil.PATTERN_DATE));
		}
		supplier.setFdAddress(fdAddress);	//装入企业地址
		supplier.setFdUrl(fdUrl);	//装入企业网址
		supplier.setFdBusinessScope(fdBusinessScope);	//装入经营范围
		supplier.setFdDesc(fdDesc);	//装入企业简介
		if(StringUtil.isNotNull(fdIsAvailable)){//是否有效
			if("0".equals(fdIsAvailable)){
				supplier.setFdIsAvailable(false);
		    }else if("1".equals(fdIsAvailable)){
			    supplier.setFdIsAvailable(true);
			}
		}else{
			supplier.setFdIsAvailable(true);
		}
		
		// 装入银行账户信息
		if (StringUtil.isNotNull(accountDetail)) {
			JSONArray accountJson = JSONArray.fromObject(accountDetail);// 把字符串转成JSONArray
			if (accountJson.size() > 0) {
				for (int i = 0; i < accountJson.size(); i++) {
					JSONObject accObj = accountJson.getJSONObject(i); // 遍历JSONArray数组
					account = new EopBasedataSupplierAccount();

					account.setFdSupplierArea(accObj.optString("fdSupplierArea"));	// 所在区域(境内/境外)
					account.setFdAccountName(accObj.optString("fdAccountName"));	// 账户名
					account.setFdBankName(accObj.optString("fdBankName"));	// 开户行
					account.setFdBankNo(accObj.optString("fdBankNo"));	//联行号
					account.setFdBankAccount(accObj.optString("fdBankAccount"));	//账号
					account.setFdBankSwift(accObj.optString("fdBankSwift"));	//收款银行swift号
					account.setFdReceiveCompany(accObj.optString("fdReceiveCompany"));	//收款公司名称
					account.setFdReceiveBankName(accObj.optString("fdReceiveBankName"));	//收款银行名称(境外)
					account.setFdReceiveBankAddress(accObj.optString("fdReceiveBankAddress"));	//收款银行地址(境外)
					account.setFdInfo(accObj.optString("fdInfo"));	//其他信息
					accountList.add(account);
				}
				JSONObject obj = accountJson.getJSONObject(0);
				supplier.setFdBankAccountName(obj.optString("fdAccountName"));	//账户名
				supplier.setFdBankName(obj.optString("fdBankName"));	//开户行
				supplier.setFdBankAccount(obj.optString("fdBankAccount"));	//银行账号

			}
			supplier.setFdAccountList(accountList);
		}
		// 装入联系人信息
		if (StringUtil.isNotNull(contactDetail)) {
			JSONArray contactJson = JSONArray.fromObject(contactDetail);// 把字符串转成JSONArray
			if (contactJson.size() > 0) {
				for (int i = 0; i < contactJson.size(); i++) {
					JSONObject conObj = contactJson.getJSONObject(i); // 遍历JSONArray数组
					contact = new EopBasedataContact();
					contact.setFdName(conObj.optString("fdName"));	// 姓名
					contact.setFdPosition(conObj.optString("fdPosition"));	// 职务
					contact.setFdPhone(conObj.optString("fdPhone"));	// 联系电话
					contact.setFdEmail(conObj.optString("fdEmail"));	// 电子邮箱
					contact.setFdAddress(conObj.optString("fdAddress"));	// 联系地址
					contact.setFdRemarks(conObj.optString("fdRemarks"));	// 备注
					if(StringUtil.isNotNull(conObj.optString("fdIsfirst"))){
						if("0".equals(conObj.optString("fdIsfirst"))){
							contact.setFdIsfirst(false);
						}else if("1".equals(conObj.optString("fdIsfirst"))){
							contact.setFdIsfirst(true);
						}
					}
					contactList.add(contact);
				}
			}
			supplier.setFdContactPerson(contactList);
		}
		return supplier;
	}
		
	
	public JSONObject addSupplier(List<EopBasedataSupplier> slist) throws Exception{
			JSONObject rtn =new JSONObject();
			Map<String,EopBasedataSupplier> supplierMap=getSupplierCache();
			try{
				for(EopBasedataSupplier supplier:slist){
					EopBasedataSupplier supp=supplierMap.get(supplier.getFdCode());
					if(supp!=null){
				    	supp = cloneSupplier(supplier,supp);
				    	eopBasedataSupplierService.update(supp);
					}else{
						eopBasedataSupplierService.add(supplier);
					}
				}
			 }catch (Exception e) {
			// 可记日志
			e.printStackTrace();
		}
		rtn.put("result", "0");
		rtn.put("errormsg", "");
		return rtn;
	}

	public EopBasedataSupplier cloneSupplier(EopBasedataSupplier from,EopBasedataSupplier to) throws Exception{
		Map<String, SysDictCommonProperty> dictMap= SysDataDict.getInstance().getModel(EopBasedataSupplier.class.getName()).getPropertyMap();
		for (Map.Entry<String, SysDictCommonProperty> entry : dictMap.entrySet()) {
			String property=entry.getKey();
			if(!"fdId".equals(property)){
				if("fdAccountList".equals(property)){
					List<EopBasedataSupplierAccount> newDetailList=new ArrayList<>();
					List<EopBasedataSupplierAccount> oldDetailList=from.getFdAccountList();
					if(oldDetailList!=null){
						for(EopBasedataSupplierAccount oldAccount:oldDetailList){
							EopBasedataSupplierAccount newAccout=new EopBasedataSupplierAccount();
							newAccout.setDocMain(to);//替换对应的供应商主表
							newDetailList.add(cloneAccount(oldAccount, newAccout));
						}
					}
					if(to.getFdAccountList()!=null){
						if(ArrayUtil.isEmpty(to.getFdAccountList())){
							to.getFdAccountList().addAll(newDetailList);
						}else{
							to.getFdAccountList().clear();
							to.getFdAccountList().addAll(newDetailList);
						}
					}else{
						to.setFdAccountList(newDetailList);
					}
				}else if("fdContactPerson".equals(property)){
					List<EopBasedataContact> newDetailList=new ArrayList<>();
					List<EopBasedataContact> oldDetailList=from.getFdContactPerson();
					if(oldDetailList!=null){
						for(EopBasedataContact oldContact:oldDetailList){
							EopBasedataContact newContact=new EopBasedataContact();
							newContact.setDocMain(to);//替换对应的供应商主表
							newDetailList.add(cloneContact(oldContact, newContact));
						}
					}
					if(to.getFdContactPerson()!=null){
						if(ArrayUtil.isEmpty(to.getFdAccountList())){
							to.getFdContactPerson().addAll(newDetailList);
						}else{
							to.getFdContactPerson().clear();
							to.getFdContactPerson().addAll(newDetailList);
						}
					}else{
						to.setFdContactPerson(newDetailList);
					}
				}else{
					PropertyUtils.setProperty(to, property, PropertyUtils.getProperty(from, property));
				}
			}
		}
		return to;
	}

	public EopBasedataSupplierAccount cloneAccount(EopBasedataSupplierAccount fromAccount,EopBasedataSupplierAccount toAccount) throws Exception{
		Map<String, SysDictCommonProperty> dictMap=SysDataDict.getInstance().getModel(EopBasedataSupplierAccount.class.getName()).getPropertyMap();
		for (Map.Entry<String, SysDictCommonProperty> entry : dictMap.entrySet()) {
			String property=entry.getKey();
			SysDictCommonProperty pro=dictMap.get(property);
			if(!"fdId".equals(property)&&!"docMain".equals(property)){
				PropertyUtils.setProperty(toAccount, property, PropertyUtils.getProperty(fromAccount, property));
			}
		}
		return toAccount;
	}

	public EopBasedataContact cloneContact(EopBasedataContact fromContact,EopBasedataContact toContact) throws Exception{
		Map<String, SysDictCommonProperty> dictMap=SysDataDict.getInstance().getModel(EopBasedataContact.class.getName()).getPropertyMap();
		for (Map.Entry<String, SysDictCommonProperty> entry : dictMap.entrySet()) {
			String property=entry.getKey();
			SysDictCommonProperty pro=dictMap.get(property);
			if(!"fdId".equals(property)&&!"docMain".equals(property)){
				PropertyUtils.setProperty(toContact, property, PropertyUtils.getProperty(fromContact, property));
			}
		}
		return toContact;
	}
	
	 /**
     * 获取数据库现有客商数据
     * @return
     * @throws Exception
     */
    public Map<String,EopBasedataSupplier> getSupplierCache() throws Exception{
    	Map<String,EopBasedataSupplier> map=new HashMap<String,EopBasedataSupplier>();
    	HQLInfo hqlInfo=new HQLInfo();
    	List<EopBasedataSupplier> dataList=eopBasedataSupplierService.findList(hqlInfo);
    	for(EopBasedataSupplier supplier:dataList){
    		map.put(supplier.getFdCode(), supplier);
    	}
    	return map;
    }

	/**
	 * 获取数据库现有公司数据
	 * @return
	 * @throws Exception
	 */
	public Map<String,EopBasedataCompany> getCompanyCache() throws Exception{
		Map<String,EopBasedataCompany> map=new HashMap<String,EopBasedataCompany>();
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setModelName(EopBasedataCompany.class.getName());
		String where="eopBasedataCompany.fdIsAvailable=:fdIsAvailable";
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setWhereBlock(where);
		List<EopBasedataCompany> dataList=eopBasedataSupplierService.findList(hqlInfo);
		for(EopBasedataCompany company:dataList){
			map.put(company.getFdCode(), company);
		}
		return map;
	}
}
