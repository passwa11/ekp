package com.landray.kmss.fssc.voucher.formula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.model.BaseModel;
import org.apache.commons.beanutils.PropertyUtils;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCashFlow;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomer;
import com.landray.kmss.eop.basedata.model.EopBasedataErpPerson;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.eop.basedata.service.IEopBasedataCashFlowService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCustomerService;
import com.landray.kmss.eop.basedata.service.IEopBasedataErpPersonService;
import com.landray.kmss.eop.basedata.service.IEopBasedataExpenseItemService;
import com.landray.kmss.eop.basedata.service.IEopBasedataItemAccountService;
import com.landray.kmss.eop.basedata.service.IEopBasedataSupplierService;
import com.landray.kmss.eop.basedata.service.IEopBasedataTaxRateService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonCashierPaymentService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLoanService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class FsscVoucherCommon {

	private static IEopBasedataErpPersonService eopBasedataErpPersonService;

	private static IEopBasedataItemAccountService eopBasedataItemAccountService;

	private static IFsscCommonLoanService fsscCommonLoanService;

	private static IFsscCommonCashierPaymentService fsscCommonCashierPaymentService;

	private static IEopBasedataExpenseItemService eopBasedataExpenseItemService;

	private static IEopBasedataTaxRateService eopBasedataTaxRateService;
	
	private static IEopBasedataCashFlowService	eopBasedataCashFlowService;
	
	private static IEopBasedataSupplierService eopBasedataSupplierService;
	
	private static IEopBasedataCustomerService eopBasedataCustomerService;
	
	/**
	 * 判断是否生成冲预提贷方分录
	 * @return
	 * @throws Exception
	 */
	public static List checkProvisionCreditMoney() throws Exception {
		List rtn = new ArrayList();
		IBaseModel main = (IBaseModel) FormulaParser.getRunningData();
		EopBasedataCompany comp = (EopBasedataCompany) PropertyUtils.getProperty(main, "fdCompany");
		//冲抵逻辑，1含税，2不含税
		String  fdProvisionRule=EopBasedataFsscUtil.getDetailPropertyValue(comp.getFdId(),"fdProvisionRule");
		List<Object> detailList = (List<Object>) PropertyUtils.getProperty(main, "fdDetailList");
		for(Object detail:detailList) {
			if("2".equals(fdProvisionRule)) {//如果是不含税，不需要生成
				rtn.add(false);
			}else {
				//冲抵预提金额
				Double fdProvisionMoney = (Double) PropertyUtils.getProperty(detail, "fdProvisionMoney");
				fdProvisionMoney = fdProvisionMoney==null?0d:fdProvisionMoney;
				//税额
				Double fdTaxMoney = (Double) PropertyUtils.getProperty(detail, "fdInputTaxMoney");
				fdTaxMoney = fdTaxMoney==null?0d:fdTaxMoney;
				//含税费用
				String className = ModelUtil.getModelClassName(main);
				Double fdExpenesMoney = 0d;
				if(className.contains("expense")) {//报销
					fdExpenesMoney = (Double) PropertyUtils.getProperty(detail, "fdApprovedStandardMoney");
				}else {//付款
					fdExpenesMoney = (Double) PropertyUtils.getProperty(detail, "fdOrgAmount");
					fdExpenesMoney = fdExpenesMoney==null?0d:fdExpenesMoney;
					Double fdRate = (Double) PropertyUtils.getProperty(main, "fdRate");
					fdExpenesMoney = FsscNumberUtil.getMultiplication(fdExpenesMoney, fdRate,2);
				}
				fdExpenesMoney = fdExpenesMoney==null?0d:fdExpenesMoney;
				rtn.add(fdExpenesMoney>0&&FsscNumberUtil.getAddition(fdProvisionMoney, fdTaxMoney)>fdExpenesMoney);
			}
		}
		return rtn;
	}
	
	/**
	 * 获取冲预提贷方费用分录金额
	 * @return
	 * @throws Exception
	 */
	public static List getProvisionCreditMoney() throws Exception {
		List rtn = new ArrayList();
		IBaseModel main = (IBaseModel) FormulaParser.getRunningData();
		EopBasedataCompany comp = (EopBasedataCompany) PropertyUtils.getProperty(main, "fdCompany");
		//冲抵逻辑，1含税，2不含税
		String  fdProvisionRule=EopBasedataFsscUtil.getDetailPropertyValue(comp.getFdId(),"fdProvisionRule");
		List<Object> detailList = (List<Object>) PropertyUtils.getProperty(main, "fdDetailList");
		for(Object detail:detailList) {
			if("2".equals(fdProvisionRule)) {//如果是不含税，不需要生成,未配置按照不含税
				rtn.add(0d);
			}else {
				//冲抵预提金额
				Double fdProvisionMoney = (Double) PropertyUtils.getProperty(detail, "fdProvisionMoney");
				fdProvisionMoney = fdProvisionMoney==null?0d:fdProvisionMoney;
				//税额
				Double fdTaxMoney = (Double) PropertyUtils.getProperty(detail, "fdInputTaxMoney");
				if(fdProvisionMoney==null||fdTaxMoney==null) {
					fdTaxMoney=0d;
				}
				//含税费用
				String className = ModelUtil.getModelClassName(main);
				Double fdExpenesMoney = 0d;
				if(className.contains("expense")) {//报销
					fdExpenesMoney = (Double) PropertyUtils.getProperty(detail, "fdApprovedStandardMoney");
				}else {//付款
					fdExpenesMoney = (Double) PropertyUtils.getProperty(detail, "fdOrgAmount");
					fdExpenesMoney = fdExpenesMoney==null?0d:fdExpenesMoney;
					Double fdRate = (Double) PropertyUtils.getProperty(main, "fdRate");
					fdExpenesMoney = FsscNumberUtil.getMultiplication(fdExpenesMoney, fdRate,2);
				}
				fdExpenesMoney = fdExpenesMoney==null?0d:fdExpenesMoney;
				Double fdMoney = FsscNumberUtil.getAddition(fdProvisionMoney, fdTaxMoney);
				fdMoney = FsscNumberUtil.getSubtraction(fdMoney, fdExpenesMoney);
				rtn.add(fdMoney);
			}
		}
		return rtn;
	}
	
	/**
	 * 获取ERP人员
	 *
	 * @return
	 * @throws Exception
	 */
	public static List getEopBasedataErpPerson(Object object) throws Exception {
		List<EopBasedataErpPerson> list = new ArrayList<>();
		if(object instanceof SysOrgPerson){
			if(object != null){
				list.add(getEopBasedataErpPersonService().getEopBasedataErpPersonByFdPersonId(PropertyUtils.getProperty(object, "fdId")+""));
			}else{
				list.add(null);
			}
		}else if(object instanceof List){
			List objs = (List) object;
			for(int i=0;i<objs.size();i++){
				if(objs.get(i) != null){
					list.add(getEopBasedataErpPersonService().getEopBasedataErpPersonByFdPersonId(PropertyUtils.getProperty(objs.get(i), "fdId")+""));
				}else{
					list.add(null);
				}
			}
		}
		return list;
	}

	/**
	 * 获取借款单的会计科目
	 *
	 * @return
	 * @throws Exception
	 */
	public static List<EopBasedataAccounts> getLoanEopBasedataAccounts(Object object) throws Exception {
		List<EopBasedataAccounts> fdBaseAccountsList = new ArrayList<EopBasedataAccounts>();
		if(!FsscCommonUtil.checkHasModule("/fssc/loan/")){//不存在借款模块
			return fdBaseAccountsList;
		}
		if(object instanceof String){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fdLoanIdList", new ArrayList<String>().add(object+""));
			Map<String, Object> rtnMap = getFsscCommonLoanService().getLoanEopBasedataAccounts(map);
			if("success".equals(rtnMap.get("result")+"")){
				fdBaseAccountsList.addAll((List<EopBasedataAccounts>) rtnMap.get("fdBaseAccountsList"));
			}
		}else if(object instanceof List){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fdLoanIdList", object);
			Map<String, Object> rtnMap = getFsscCommonLoanService().getLoanEopBasedataAccounts(map);
			if("success".equals(rtnMap.get("result")+"")){
				fdBaseAccountsList.addAll((List<EopBasedataAccounts>) rtnMap.get("fdBaseAccountsList"));
			}
		}
		return fdBaseAccountsList;
	}

	/**
	 * 获取借款单的成本中心
	 *
	 * @return
	 * @throws Exception
	 */
	public static List<EopBasedataCostCenter> getLoanEopBasedataCostCenter(Object object) throws Exception {
		List<EopBasedataCostCenter> fdBaseCostCenterList = new ArrayList<EopBasedataCostCenter>();
		if(!FsscCommonUtil.checkHasModule("/fssc/loan/")&&!FsscCommonUtil.checkHasModule("/fssc/payment/")){//不存在借款模块
			return fdBaseCostCenterList;
		}
		if(object instanceof String){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fdLoanIdList", new ArrayList<String>().add(object+""));
			Map<String, Object> rtnMap = getFsscCommonLoanService().getLoanEopBasedataCostCenter(map);
			if("success".equals(rtnMap.get("result")+"")){
				fdBaseCostCenterList.addAll((List<EopBasedataCostCenter>) rtnMap.get("fdBaseCostCenterList"));
			}
		}else if(object instanceof List){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fdLoanIdList", object);
			Map<String, Object> rtnMap = getFsscCommonLoanService().getLoanEopBasedataCostCenter(map);
			if("success".equals(rtnMap.get("result")+"")){
				fdBaseCostCenterList.addAll((List<EopBasedataCostCenter>) rtnMap.get("fdBaseCostCenterList"));
			}
		}
		return fdBaseCostCenterList;
	}

	/**
	 * 获取借款单的接收人
	 *
	 * @return
	 * @throws Exception
	 */
	public static List<SysOrgPerson> getLoanChargePerson(Object object) throws Exception {
		List<SysOrgPerson> fdLoanPersonList = new ArrayList<SysOrgPerson>();
		if(!FsscCommonUtil.checkHasModule("/fssc/loan/")){//不存在借款模块
			return fdLoanPersonList;
		}
		if(object instanceof String){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fdLoanIdList", new ArrayList<String>().add(object+""));
			Map<String, Object> rtnMap = getFsscCommonLoanService().getLoanChargePerson(map);
			if("success".equals(rtnMap.get("result")+"")){
				fdLoanPersonList.addAll((List<SysOrgPerson>) rtnMap.get("fdLoanChargeList"));
			}
		}else if(object instanceof List){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fdLoanIdList", object);
			Map<String, Object> rtnMap = getFsscCommonLoanService().getLoanChargePerson(map);
			if("success".equals(rtnMap.get("result")+"")){
				fdLoanPersonList.addAll((List<SysOrgPerson>) rtnMap.get("fdLoanChargeList"));
			}
		}
		return fdLoanPersonList;
	}

	/**
	 * 获取费用类型对应待摊科目
	 *
	 * @return
	 * @throws Exception
	 */
	public static List<EopBasedataAccounts> getEopBasedataAccountsList1(Object obj, String itemProperty) throws Exception {
		List<EopBasedataAccounts> fdBaseAccountsList = new ArrayList<EopBasedataAccounts>();
		if(obj == null){
			return fdBaseAccountsList;
		}
		List<EopBasedataExpenseItem> items = new ArrayList<EopBasedataExpenseItem>();
		if(obj instanceof List && StringUtil.isNotNull(itemProperty)){//明细
			List objList = (List) obj;
			for(int i=0;i<objList.size();i++){
				if(objList.get(i) != null){
					fdBaseAccountsList.add(getEopBasedataItemAccountService().getEopBasedataAccounts(PropertyUtils.getProperty(PropertyUtils.getProperty(objList.get(i), itemProperty), "fdId")+""));
				}else{
					fdBaseAccountsList.add(null);
				}
			}
		}else{
			fdBaseAccountsList.add(getEopBasedataItemAccountService().getEopBasedataAccounts(PropertyUtils.getProperty(obj, "fdId")+""));
		}
		return fdBaseAccountsList;
	}
	
	/**
	 * 获取费用类型对应预提科目
	 *
	 * @return
	 * @throws Exception
	 */
	public static List<EopBasedataAccounts> getEopBasedatafdAccrualsAccountsList(Object obj, String itemProperty) throws Exception {
		List<EopBasedataAccounts> fdBaseAccountsList = new ArrayList<EopBasedataAccounts>();
		if(obj == null){
			return fdBaseAccountsList;
		}
		List<EopBasedataExpenseItem> items = new ArrayList<EopBasedataExpenseItem>();
		if(obj instanceof List && StringUtil.isNotNull(itemProperty)){//明细
			List objList = (List) obj;
			for(int i=0;i<objList.size();i++){
				if(objList.get(i) != null){
					fdBaseAccountsList.add(getEopBasedataItemAccountService().getFsscAccrualsAccounts(PropertyUtils.getProperty(PropertyUtils.getProperty(objList.get(i), itemProperty), "fdId")+""));
				}else{
					fdBaseAccountsList.add(null);
				}
			}
		}else{
			fdBaseAccountsList.add(getEopBasedataItemAccountService().getFsscAccrualsAccounts(PropertyUtils.getProperty(obj, "fdId")+""));
		}
		return fdBaseAccountsList;
	}

	/**
	 * 获取费用类型的会计科目
	 *
	 * @return
	 * @throws Exception
	 */
	public static List<EopBasedataAccounts> getEopBasedataAccountsList2(Object obj1, String itemProperty, Object obj2, String costCenterProperty) throws Exception {
		List<EopBasedataAccounts> fdBaseAccountsList = new ArrayList<EopBasedataAccounts>();
		if(obj1 == null || obj2 == null){
			return fdBaseAccountsList;
		}
		List<EopBasedataExpenseItem> items = new ArrayList<EopBasedataExpenseItem>();
		List<EopBasedataCostCenter> costCenters = new ArrayList<EopBasedataCostCenter>();
		if(obj1 instanceof List && StringUtil.isNotNull(itemProperty)){//费用类型在明细
			List obj1s = (List) obj1;
			if(obj2 instanceof List && StringUtil.isNotNull(costCenterProperty)){//成本中心在明细
				List obj2s = (List) obj2;
				if(obj1s.size() != obj2s.size()){//长度不一致抛异常
					throw new Exception(ResourceUtil.getString("getEopBasedataAccountsList2.error.size", "fssc-voucher"));
				}
				for(int i=0;i<obj2s.size();i++){
					costCenters.add((EopBasedataCostCenter) PropertyUtils.getProperty(obj2s.get(i), costCenterProperty));
				}
			}else if(obj2 instanceof List && StringUtil.isNull(costCenterProperty)){//成本中心对象集合
				List obj2s = (List) obj2;
				if(obj1s.size() != obj2s.size()){//长度不一致抛异常
					throw new Exception(ResourceUtil.getString("getEopBasedataAccountsList2.error.size", "fssc-voucher"));
				}
				costCenters.addAll(obj2s);
			}else{
				for(int i=0;i<obj1s.size();i++){
					costCenters.add((EopBasedataCostCenter) obj2);
				}
			}
			for(int i=0;i<obj1s.size();i++){//费用类型
				items.add((EopBasedataExpenseItem) PropertyUtils.getProperty(obj1s.get(i), itemProperty));
			}
		}else if(obj1 instanceof List && StringUtil.isNull(itemProperty)){//费用类型对象集合
			List obj1s = (List) obj1;
			if(obj2 instanceof List && StringUtil.isNotNull(costCenterProperty)){//成本中心在明细
				List obj2s = (List) obj2;
				if(obj1s.size() != obj2s.size()){//长度不一致抛异常
					throw new Exception(ResourceUtil.getString("getEopBasedataAccountsList2.error.size", "fssc-voucher"));
				}
				for(int i=0;i<obj2s.size();i++){
					costCenters.add((EopBasedataCostCenter) PropertyUtils.getProperty(obj2s.get(i), costCenterProperty));
				}
			}else if(obj2 instanceof List && StringUtil.isNull(costCenterProperty)){//成本中心对象集合
				List obj2s = (List) obj2;
				if(obj1s.size() != obj2s.size()){//长度不一致抛异常
					throw new Exception(ResourceUtil.getString("getEopBasedataAccountsList2.error.size", "fssc-voucher"));
				}
				costCenters.addAll(obj2s);
			}else{
				for(int i=0;i<obj1s.size();i++){
					costCenters.add((EopBasedataCostCenter) obj2);
				}
			}
			items.addAll(obj1s);
		}else{
			items.add((EopBasedataExpenseItem) obj1);
			costCenters.add((EopBasedataCostCenter) obj2);
		}
		for(int i=0;i<items.size();i++){
			List<EopBasedataAccounts> accounts = items.get(i).getFdAccounts();
			if(!ArrayUtil.isEmpty(accounts) && accounts.size() == 1){
				fdBaseAccountsList.add(accounts.get(0));
				continue;
			}
			String fdCode = costCenters.get(i).getFdType()==null?"":costCenters.get(i).getFdType().getFdCode();
			if(!ArrayUtil.isEmpty(accounts) && StringUtil.isNotNull(fdCode)){
				boolean fdIsBoolean = false;
				for(int j=0;j<accounts.size();j++){
					if(accounts.get(j).getFdCode().indexOf(fdCode) == 0){
						fdIsBoolean = true;
						fdBaseAccountsList.add(accounts.get(j));
						break;
					}
				}
				if(!fdIsBoolean){
					fdBaseAccountsList.add(null);
				}
			}else{
				fdBaseAccountsList.add(null);
			}
		}
		return fdBaseAccountsList;
	}

	/**
	 * 获取付款单对应会计科目
	 *
	 * @return
	 * @throws Exception
	 */
	public static List<EopBasedataAccounts> getEopBasedataAccountsList3(String fdModelId, String fdModelName) throws Exception {
		if(!FsscCommonUtil.checkHasModule("/fssc/cashier/")){//不存在出纳工作台模块
			return null;
		}
		JSONObject json = new JSONObject();
		json.put("fdModelId", fdModelId+"");
		json.put("fdModelName", fdModelName+"");
		Map<String, Object> rtnMap = getFsscCommonCashierPaymentService().getPaymentDetailInfo(json);
		if("success".equals(rtnMap.get("result")+"")){
			return (List<EopBasedataAccounts>) rtnMap.get("fdBaseAccountsList");
		}
		return null;
	}
	

	/**
	 * 获取付款单对应银行
	 *
	 * @return
	 * @throws Exception
	 */
	public static List<EopBasedataPayBank> getEopBasedataBankListByPayment(String fdModelId, Object fdDetail) throws Exception {
		if(!FsscCommonUtil.checkHasModule("/fssc/cashier/")){//不存在出纳工作台模块
			return null;
		}
		Map<String, Object> rtnMap = getFsscCommonCashierPaymentService().getPaymentBank(fdModelId,fdDetail);
		if("success".equals(rtnMap.get("result")+"")){
			return (List<EopBasedataPayBank>) rtnMap.get("dataList");
		}
		return null;
	}

	/**
	 * 获取付款单总金额
	 *
	 * @return
	 * @throws Exception
	 */
	public static double getFdPaymentTotalMoney(Object fdModelId, Object fdModelName) throws Exception {
		if(!FsscCommonUtil.checkHasModule("/fssc/cashier/")){//不存在出纳工作台模块
			return 0;
		}
		if(fdModelId instanceof String && fdModelName instanceof String){
			JSONObject json = new JSONObject();
			json.put("fdModelId", fdModelId+"");
			json.put("fdModelName", fdModelName+"");
			Map<String, Object> rtnMap = getFsscCommonCashierPaymentService().getFdTotalMoney(json);
			if("success".equals(rtnMap.get("result")+"")){
				return FsscNumberUtil.doubleToUp(rtnMap.get("fdTotalMoney")+"");
			}
		}
		return 0;
	}

	/**
	 *  获取发票明细的会计科目
	 *
	 * @return
	 * @throws Exception
	 */
	public static List<EopBasedataAccounts> getEopBasedataAccountsList4(Object companyObj, Object invoiceObj) throws Exception {
		List<EopBasedataAccounts> fdBaseAccountsList = new ArrayList<EopBasedataAccounts>();
		if(!FsscCommonUtil.checkHasModule("/fssc/expense/")){//不存在报销模块
			//throw new Exception("fssc-voucher-error:not /fssc/expense/!");
			return fdBaseAccountsList;
		}
		if(companyObj == null || !(companyObj instanceof EopBasedataCompany) || invoiceObj == null){
			return fdBaseAccountsList;
		}

		if(invoiceObj instanceof List){//在明细
			List invoiceObjs = (List) invoiceObj;
			for(int i=0;i<invoiceObjs.size();i++){//费用类型
				if((Boolean)PropertyUtils.getProperty(invoiceObjs.get(i), "fdIsVat")){//是增值税发票
					fdBaseAccountsList.add(getEopBasedataTaxRateService().getEopBasedataAccounts(PropertyUtils.getProperty(companyObj, "fdId")+"", FsscNumberUtil.doubleToUp(PropertyUtils.getProperty(invoiceObjs.get(i), "fdTax")+"")));
				}
			}
		}
		return fdBaseAccountsList;
	}
	
	/**
	 * 根据会计科目获取现金流量项目
	 * @param companyObj
	 * @return
	 * @throws Exception 
	 */
	public static List<EopBasedataCashFlow> getEopBasedataCashFlowProject(Object fdAccountsCom,Object companyObj) throws Exception{
		List<EopBasedataCashFlow> list = new ArrayList<>();
		if(null==fdAccountsCom || null==companyObj){
			list.add(null);
		}
		//在明细
		if(fdAccountsCom instanceof List){
			List fdAccountsComList = (List) fdAccountsCom;
			if(!ArrayUtil.isEmpty(fdAccountsComList)){
				for(int i=0;i<fdAccountsComList.size();i++){
					Object fdAccountsComObj = fdAccountsComList.get(i);
					EopBasedataCashFlow eopBasedataCashFlow = null;
					if(fdAccountsComObj instanceof EopBasedataAccounts && companyObj instanceof EopBasedataCompany){
						EopBasedataCompany company =(EopBasedataCompany)companyObj;
						eopBasedataCashFlow = getEopBasedataCashFlow().getEopBasedataCashFlow(((EopBasedataAccounts)fdAccountsComObj).getFdId(),company.getFdId());
					}
					list.add(eopBasedataCashFlow);
				}
			}else{
				list.add(null);
			}
		}else if(fdAccountsCom instanceof EopBasedataAccounts && companyObj instanceof EopBasedataCompany){
			EopBasedataCompany company =(EopBasedataCompany)companyObj;
			list.add(getEopBasedataCashFlow().getEopBasedataCashFlow(((EopBasedataAccounts)fdAccountsCom).getFdId(),company.getFdId()));
		}
		return list;
	}

	public static List getSupplierOrCustomer(String fdId, String fdType) throws Exception{
		if(StringUtil.isNull(fdId) || StringUtil.isNull(fdType)){
			return null;
		}
		List<EopBasedataSupplier> supplierList = new ArrayList<EopBasedataSupplier>();
		List<EopBasedataCustomer> customerList = new ArrayList<EopBasedataCustomer>();
		if(StringUtil.isNotNull(fdId) && StringUtil.isNotNull(fdType)){
			if("supplier".equals(fdType)){
				EopBasedataSupplier supplier = (EopBasedataSupplier) getEopBasedataSupplierService().findByPrimaryKey(fdId, null, true);
				supplierList.add(supplier);
				return supplierList;
			}else if("customer".equals(fdType)){
				EopBasedataCustomer customer = (EopBasedataCustomer) getEopBasedataCustomerService().findByPrimaryKey(fdId, null, true);
				customerList.add(customer);
				return customerList;
			}else{
				return null;
			}
		}
		return null;
	}

	/**
	 * 获取报销单/付款单主Model
	 * @param fdModelId
	 * @param fdType
	 * @return
	 * @throws Exception
	 */
	public static BaseModel getExpenseOrPaymentModel(String fdModelId, String fdType) throws Exception {
		if("1".equals(fdType)){
			return (BaseModel) getEopBasedataErpPersonService().findByPrimaryKey(fdModelId, "com.landray.kmss.fssc.expense.model.FsscExpenseMain", true);
		}else if("2".equals(fdType)){
			return (BaseModel) getEopBasedataErpPersonService().findByPrimaryKey(fdModelId, "com.landray.kmss.fssc.payment.model.FsscPaymentMain", true);
		}
		return null;
	}
	
	public static IEopBasedataErpPersonService getEopBasedataErpPersonService() {
		if(eopBasedataErpPersonService == null){
			eopBasedataErpPersonService = (IEopBasedataErpPersonService) SpringBeanUtil.getBean("eopBasedataErpPersonService");
		}
		return eopBasedataErpPersonService;
	}

	public static IEopBasedataItemAccountService getEopBasedataItemAccountService() {
		if(eopBasedataItemAccountService == null){
			eopBasedataItemAccountService = (IEopBasedataItemAccountService) SpringBeanUtil.getBean("eopBasedataItemAccountService");
		}
		return eopBasedataItemAccountService;
	}

	public static IFsscCommonLoanService getFsscCommonLoanService() {
		if(fsscCommonLoanService == null){
			fsscCommonLoanService = (IFsscCommonLoanService) SpringBeanUtil.getBean("fsscCommonLoanService");
		}
		return fsscCommonLoanService;
	}

	public static IFsscCommonCashierPaymentService getFsscCommonCashierPaymentService() {
		if(fsscCommonCashierPaymentService == null){
			fsscCommonCashierPaymentService = (IFsscCommonCashierPaymentService) SpringBeanUtil.getBean("fsscCommonCashierPaymentService");
		}
		return fsscCommonCashierPaymentService;
	}

	public static IEopBasedataExpenseItemService getEopBasedataExpenseItemService() {
		if(eopBasedataExpenseItemService == null){
			eopBasedataExpenseItemService = (IEopBasedataExpenseItemService) SpringBeanUtil.getBean("eopBasedataExpenseItemService");
		}
		return eopBasedataExpenseItemService;
	}

	public static IEopBasedataTaxRateService getEopBasedataTaxRateService() {
		if(eopBasedataTaxRateService == null){
			eopBasedataTaxRateService = (IEopBasedataTaxRateService) SpringBeanUtil.getBean("eopBasedataTaxRateService");
		}
		return eopBasedataTaxRateService;
	}
	
    public static IEopBasedataCashFlowService getEopBasedataCashFlow() {
        if (eopBasedataCashFlowService == null) {
            eopBasedataCashFlowService = (IEopBasedataCashFlowService) SpringBeanUtil.getBean("eopBasedataCashFlowService");
        }
        return eopBasedataCashFlowService;
    }
    
    public static IEopBasedataSupplierService getEopBasedataSupplierService() {
		if(eopBasedataSupplierService == null){
			eopBasedataSupplierService = (IEopBasedataSupplierService) SpringBeanUtil.getBean("eopBasedataSupplierService");
		}
		return eopBasedataSupplierService;
	}
	
	public static IEopBasedataCustomerService getEopBasedataCustomerService() {
	    if (eopBasedataCustomerService == null) {
	         eopBasedataCustomerService = (IEopBasedataCustomerService) SpringBeanUtil.getBean("eopBasedataCustomerService");
	    }
	    return eopBasedataCustomerService;
	}

}
