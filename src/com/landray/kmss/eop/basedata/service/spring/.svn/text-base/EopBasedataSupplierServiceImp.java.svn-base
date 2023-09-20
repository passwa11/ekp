package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.imp.EopBasedataImportUtil;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportMessage;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataContact;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplierAccount;
import com.landray.kmss.eop.basedata.service.IEopBasedataSupplierCheckService;
import com.landray.kmss.eop.basedata.service.IEopBasedataSupplierService;
import com.landray.kmss.eop.basedata.service.IEopBasedataSyncDataService;
import com.landray.kmss.eop.basedata.util.EopBasedataExcelUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.upload.FormFile;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.query.Query;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangwh
 * @description:供应商业务类实现类
 * @date 2021/5/7
 */
public class EopBasedataSupplierServiceImp extends ExtendDataServiceImp implements IEopBasedataSupplierService {

    private ISysNumberFlowService sysNumberFlowService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataSupplier) {
            EopBasedataSupplier eopBasedataSupplier = (EopBasedataSupplier) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataSupplier eopBasedataSupplier = new EopBasedataSupplier();
        eopBasedataSupplier.setFdIsAvailable(true);
        eopBasedataSupplier.setDocCreateTime(new Date());
        eopBasedataSupplier.setDocCreator(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataSupplier, requestContext);
        return eopBasedataSupplier;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataSupplier eopBasedataSupplier = (EopBasedataSupplier) model;
    }

    public ISysNumberFlowService getSysNumberFlowService() {
        if (sysNumberFlowService == null) {
            sysNumberFlowService = (ISysNumberFlowService) SpringBeanUtil.getBean("sysNumberFlowService");
        }
        return sysNumberFlowService;
    }

    @Override
    public List<EopBasedataSupplier> findByUserId(String userId) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataSupplier.fdUser.fdId=:fdUserId");
        hqlInfo.setParameter("fdUserId", userId);
        return this.findList(hqlInfo);
    }
    
    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataSupplier eopBasedataSupplier = (EopBasedataSupplier) modelObj;
        String newNo = eopBasedataSupplier.getFdCode();
        if(StringUtil.isNull(newNo)) {
        	newNo = getSysNumberFlowService().generateFlowNumber(eopBasedataSupplier);
        }
        //编码唯一性校验
        if(checkCodeExist(newNo)){
            throw new KmssException(new KmssMessage("eop-basedata:supplierCodeIsNotUnique"));
        }
        eopBasedataSupplier.setFdCode(newNo);
        String fdId = super.add(eopBasedataSupplier);

		IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.eop.data.sync.extend");
		IExtension[] extensions = point.getExtensions();
		if(extensions.length > 0){
			for (IExtension extension : extensions) {
				if ("setting".equals(extension.getAttribute("name"))) {
					String serviceName = Plugin.getParamValue(extension, "serviceName");
					if ("relativeSyncDataService".equals(serviceName)) {
						//目前只是EOP新增数据时同步到相对方，所以加了判断
						IEopBasedataSyncDataService syncService = (IEopBasedataSyncDataService) SpringBeanUtil.getBean(serviceName);
						syncService.updateEopSupplierDataToBusi(eopBasedataSupplier);
					}
				}
			}
		}

        return fdId;
    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {
        EopBasedataSupplier eopBasedataSupplier = (EopBasedataSupplier) modelObj;
        super.update(modelObj);

		IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.eop.data.sync.extend");
		IExtension[] extensions = point.getExtensions();
		if(extensions.length > 0){
			for (IExtension extension : extensions) {
				if ("setting".equals(extension.getAttribute("name"))) {
					String serviceName = Plugin.getParamValue(extension, "serviceName");
					IEopBasedataSyncDataService syncService = (IEopBasedataSyncDataService) SpringBeanUtil.getBean(serviceName);
					syncService.updateEopSupplierDataToBusi(eopBasedataSupplier);
				}
			}
		}

    }

    @Override
    public List<EopBasedataSupplier> findByName(String fdName) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataSupplier.fdName=:fdName");
        hqlInfo.setParameter("fdName", fdName);
        return this.findList(hqlInfo);
    }

    private boolean checkCodeExist(String fdCode) throws Exception {
        boolean flag = false;
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataSupplier.fdCode=:fdCode");
        hqlInfo.setParameter("fdCode", fdCode);
        List<EopBasedataSupplier> list =  this.findList(hqlInfo);
        if(!CollectionUtils.isEmpty(list)){
            flag = true;
        }
        return flag;
    }

	@Override
    public boolean checkFdCreditCode(String fdId, String fdCreditCode) throws Exception {
		boolean flag = false;
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "eopBasedataSupplier.fdCreditCode=:fdCreditCode";
		hqlInfo.setParameter("fdCreditCode", fdCreditCode);
		if (StringUtil.isNotNull(fdId)) {
			whereBlock += " and eopBasedataSupplier.fdId!=:fdId";
			hqlInfo.setParameter("fdId", fdId);
		}
		hqlInfo.setWhereBlock(whereBlock);
		List<EopBasedataSupplier> list = this.findList(hqlInfo);
		if (CollectionUtils.isEmpty(list)) {
			flag = true;
		}
		return flag;
	}

    /**
	 * 根据供应商编号查询对应的供应商
	 * @throws Exception 
	 */
	@Override
    public EopBasedataSupplier findSupplierByCode(String fdCode, String fdCompanyCode) throws Exception {
		if (StringUtil.isNull(fdCode)) {
			return null;
		}
		HQLInfo hql = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer();
		whereBlock.append("eopBasedataSupplier.fdCode = :fdCode and eopBasedataSupplier.fdIsAvailable=:fdIsAvailable");
		hql.setParameter("fdCode", fdCode);
		hql.setParameter("fdIsAvailable", true);
		if(StringUtil.isNotNull(fdCompanyCode)) {
			hql.setJoinBlock(" left join eopBasedataSupplier.fdCompanyList comp");
			whereBlock.append(" and comp.fdCode = :fdCompanyCode");
			hql.setParameter("fdCompanyCode", fdCompanyCode);
		}
		hql.setWhereBlock(whereBlock.toString());
		List<EopBasedataSupplier> resList = this.findList(hql);
		if (!resList.isEmpty()) {
			return resList.get(0);
		}
		return null;
	}
	
	@Override
	public void downloadTemp(HttpServletResponse response) throws Exception {
		String filename = ResourceUtil.getString("table.eopBasedataSupplier","eop-basedata");
		filename = new String(filename.getBytes("GBK"), "ISO-8859-1") +".xls";
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filename);
		Workbook workBook = new HSSFWorkbook();
		//客商信息模板
		String sheetName = ResourceUtil.getString("table.eopBasedataSupplier","eop-basedata");
		Sheet sheet = workBook.createSheet(sheetName);
		String[] mainTitle=ResourceUtil.getString("eopBasedataSupplier.main.export.title","eop-basedata").split(";");
		for (int i = 0; i <= mainTitle.length; i++) {
			sheet.setColumnWidth((short) i, (short) 4000);
		}
		CellStyle style = EopBasedataImportUtil.getTitleStyle(workBook);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(ResourceUtil.getString("page.serial"));
		cell.setCellStyle(style);
		for (int i = 0; i < mainTitle.length; i++) {
			cell = row.createCell(i+1);
			cell.setCellValue(mainTitle[i]);
			cell.setCellStyle(style);
		}
		//收款账户模板
		sheetName = ResourceUtil.getString("table.eopBasedataSupplierAccount","eop-basedata");
		sheet = workBook.createSheet(sheetName);
		row = sheet.createRow(0);
		String[] accountTitle=new String[]{};
		String switchValue= EopBasedataFsscUtil.getSwitchValue("fdUseBank");
		if((";"+switchValue+";").indexOf(";CMB")>-1 ||(";"+switchValue+";").indexOf(";CBS")>-1 ||(";"+switchValue+";").indexOf(";CMInt")>-1 ){
			accountTitle=ResourceUtil.getString("eopBasedataSupplier.account.export.title.bank","eop-basedata").split(";");
		}else{
			accountTitle=ResourceUtil.getString("eopBasedataSupplier.account.export.title","eop-basedata").split(";");
		}
		for (int i = 0; i <= accountTitle.length; i++) {
			sheet.setColumnWidth((short) i, (short) 4000);
		}
		cell.setCellStyle(style);
		for (int i = 0; i < accountTitle.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(accountTitle[i]);
			cell.setCellStyle(style);
		}
		//联系人模板
		sheetName = ResourceUtil.getString("table.eopBasedataContact","eop-basedata");
		sheet = workBook.createSheet(sheetName);
		row = sheet.createRow(0);
		String[] contactTitle=ResourceUtil.getString("eopBasedataContact.export.title","eop-basedata").split(";");
		for (int i = 0; i <= contactTitle.length; i++) {
			sheet.setColumnWidth((short) i, (short) 4000);
		}
		cell.setCellStyle(style);
		for (int i = 0; i < contactTitle.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(contactTitle[i]);
			cell.setCellStyle(style);
		}
		workBook.write(os);
		os.flush();
		os.close();
	}
    
    @Override
	public List<EopBasedataImportMessage> saveImport(FormFile fdFile) throws Exception {
    	List<EopBasedataImportMessage> messages=new ArrayList<>();
		Map<String,EopBasedataSupplier> dbCache=getSupplierCache();  //缓存数据库现有数据
		Map<String,EopBasedataSupplier> dataCache=new HashMap<>();  //缓存Excel数据
		KmssCache companyCache=getCompanyCache();  //缓存记账公司数据
		Workbook wb = WorkbookFactory.create(fdFile.getInputStream());
		//获取客商主表数据
		Sheet sheet = wb.getSheetAt(0);
		String[] mainProperty={"fdCode","fdName","fdAbbreviation","fdTaxNo","fdErpNo","fdCreditCode","fdCodeValidityPeriod"};
		List<Object> valueList=new ArrayList<>();
		Row row = null;
		for(int i=1,j=sheet.getPhysicalNumberOfRows();i<j;i++){
			row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			EopBasedataImportMessage message=new EopBasedataImportMessage();
			List<String> moreMessages=new ArrayList<>();  //同一行提示信息
			valueList=new ArrayList<>();
			EopBasedataSupplier customer=new EopBasedataSupplier();
			String fdCode=EopBasedataExcelUtil.getCellValue(row.getCell(5));  //客户编码
			if(StringUtil.isNull(fdCode)){
				moreMessages.add(ResourceUtil.getString("message.validation.required", "eop-basedata").replace("{0}", 
						ResourceUtil.getString("table.eopBasedataSupplier", "eop-basedata")+ResourceUtil.getString("eopBasedataSupplier.fdCode", "eop-basedata")));
			}
			customer.setFdIsAvailable(Boolean.TRUE);
			String fdCompanyCode=EopBasedataExcelUtil.getCellValue(row.getCell(2));  //公司编码
			if(StringUtil.isNotNull(fdCompanyCode)){
				List<EopBasedataCompany> companyList=new ArrayList<EopBasedataCompany>();
				if(fdCompanyCode.indexOf(";")>-1) {
					String[] codes=fdCompanyCode.split(";");
					for(String code:codes) {
						companyList.add((EopBasedataCompany) companyCache.get(code));
					}
				}else {
					companyList.add((EopBasedataCompany) companyCache.get(fdCompanyCode));
				}
				customer.getFdCompanyList().clear();
				customer.getFdCompanyList().addAll(companyList);
			}
			valueList.add(fdCode);
			String value=EopBasedataExcelUtil.getCellValue(row.getCell(3));  //客户名称
			if(StringUtil.isNull(value)){
				moreMessages.add(ResourceUtil.getString("message.validation.required", "eop-basedata").replace("{0}", 
						ResourceUtil.getString("table.eopBasedataSupplier", "eop-basedata")+ResourceUtil.getString("eopBasedataSupplier.fdName", "eop-basedata")));
			}
			valueList.add(value);
			value=EopBasedataExcelUtil.getCellValue(row.getCell(4));  //客户简称
			valueList.add(value);
			//纳税人识别号、ERP号、统一社会信用代码、信用证有效截止日期
			for(int m=6;m<10;m++){
				value=EopBasedataExcelUtil.getCellValue(row.getCell(m));  
				valueList.add(value);
			}
			for(int n=0,len=valueList.size();n<len;n++){
				if("fdCodeValidityPeriod".equals(mainProperty[n])){
					if(valueList.get(n)!=null&&StringUtil.isNotNull(String.valueOf(valueList.get(n)))){
						Date validityDate=null;
						try{
							validityDate=DateUtil.getTimeByNubmer(Long.parseLong(String.valueOf(valueList.get(n))));  //excel直接填写日期格式
						}catch (Exception e){
							validityDate=DateUtil.convertStringToDate(String.valueOf(valueList.get(n)),DateUtil.PATTERN_DATE);
						}
						PropertyUtils.setProperty(customer, mainProperty[n], validityDate);
					}
				}else{
					PropertyUtils.setProperty(customer, mainProperty[n], valueList.get(n));
				}
			}
			dataCache.put(fdCode, customer);
			if(!ArrayUtil.isEmpty(moreMessages)){
				message.setMessageType(0);
				message.setMessage(ResourceUtil.getString("table.eopBasedataSupplier", "eop-basedata")+"："+ResourceUtil.getString("message.validation.rowNum", "eop-basedata").replace("{0}", String.valueOf(i)));
				message.setMoreMessages(moreMessages);
				messages.add(message);
			}
		}
		//获取收款账户信息
		sheet = wb.getSheetAt(1);
		String[] accountProperty=new String[]{};
		if(checkCmbBankcher()){
			accountProperty=ResourceUtil.getString("eopBasedataSupplier.account.import.title.bank", "eop-basedata").split(";");
		}else{
			accountProperty=ResourceUtil.getString("eopBasedataSupplier.account.import.title", "eop-basedata").split(";");
		}
		Map<String,List<EopBasedataSupplierAccount>> accountMap=new HashMap<>();
		row = null;
		for(int i=1,j=sheet.getPhysicalNumberOfRows();i<j;i++){
			row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			EopBasedataImportMessage message=new EopBasedataImportMessage();
			List<String> moreMessages=new ArrayList<>();  //同一行提示信息
			EopBasedataSupplierAccount account=new EopBasedataSupplierAccount();
			String fdCode=EopBasedataExcelUtil.getCellValue(row.getCell(0));  //客商编码
			if(StringUtil.isNull(fdCode)){
				moreMessages.add(ResourceUtil.getString("message.validation.required", "eop-basedata").replace("{0}", 
						ResourceUtil.getString("table.eopBasedataSupplier", "eop-basedata")+ResourceUtil.getString("eopBasedataSupplier.fdCode", "eop-basedata")));
			}
			if(dataCache.get(fdCode)!=null){
				account.setDocMain((EopBasedataSupplier) dataCache.get(fdCode));
			}
			int count=10;  //循环读取账户信息，默认为10
			String switchValue= EopBasedataFsscUtil.getSwitchValue("fdUseBank");
			List<String> areaNameList=new ArrayList<String>();
			if(checkCmbBankcher()){
				count=11;  //增加了所属城市
				//读取账户归属地区excel值，校验在对应的城市信息是否维护
				areaNameList=getBankCityList(switchValue);
			}
			for(int m=1;m<=count;m++){
				String value=EopBasedataExcelUtil.getCellValue(row.getCell(m));
				if(checkCmbBankcher()&&m==6){
					if(!areaNameList.contains(value)){
						moreMessages.add(ResourceUtil.getString("message.validation.exists", "eop-basedata").replace("{0}",
								ResourceUtil.getString("eopBasedataSupplierAccount.fdAccountAreaName", "eop-basedata")));
					}
					account.setFdAccountAreaCode(value);
					account.setFdAccountAreaName(value);
				}
				if(("fdSupplierArea".equals(accountProperty[m])
						||"fdAccountName".equals(accountProperty[m])
						||"fdBankName".equals(accountProperty[m])
						||"fdBankAccount".equals(accountProperty[m]))&&StringUtil.isNull(value)){
					moreMessages.add(ResourceUtil.getString("message.validation.required", "eop-basedata").replace("{0}", 
							ResourceUtil.getString("eopBasedataSupplierAccount."+accountProperty[m], "eop-basedata")));
				}
				if(("fdBankSwift".equals(accountProperty[m])
						||"fdReceiveCompany".equals(accountProperty[m])
						||"fdReceiveBankName".equals(accountProperty[m])
						||"fdReceiveBankAddress".equals(accountProperty[m]))
						&&StringUtil.isNull(value)&&EopBasedataConstant.FSSC_BASE_SUPPLIER_AREA_1.equals(account.getFdSupplierArea())){
					moreMessages.add(ResourceUtil.getString("message.validation.required", "eop-basedata").replace("{0}", 
							ResourceUtil.getString("eopBasedataSupplierAccount."+accountProperty[m], "eop-basedata")));
				}
				PropertyUtils.setProperty(account, accountProperty[m], value);
			}
			List<EopBasedataSupplierAccount> accountList=new ArrayList<>();
			if(accountMap.containsKey(fdCode)){
				accountList=accountMap.get(fdCode);
			}
			accountList.add(account);
			accountMap.put(fdCode, accountList);
			if(!ArrayUtil.isEmpty(moreMessages)){
				message.setMessageType(0);
				message.setMessage(ResourceUtil.getString("table.eopBasedataSupplierAccount", "eop-basedata")+"："+ResourceUtil.getString("message.validation.rowNum", "eop-basedata").replace("{0}", String.valueOf(i)));
				message.setMoreMessages(moreMessages);
				messages.add(message);
			}
		}
		//获取联系人信息
		sheet = wb.getSheetAt(2);
		String[] contactProperty={"fdCode","fdName","fdPosition","fdPhone","fdEmail","fdAddress","fdRemarks","fdIsfirst"};
		valueList=new ArrayList<>();
		Map<String,List<EopBasedataContact>> contactMap=new HashMap<>();
		row = null;
		for(int i=1,j=sheet.getPhysicalNumberOfRows();i<j;i++){
			row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			EopBasedataImportMessage message=new EopBasedataImportMessage();
			List<String> moreMessages=new ArrayList<>();  //同一行提示信息
			EopBasedataContact contact=new EopBasedataContact();
			String fdCode=EopBasedataExcelUtil.getCellValue(row.getCell(0));  //供应商编码
			if(StringUtil.isNull(fdCode)){
				moreMessages.add(ResourceUtil.getString("message.validation.required", "eop-basedata").replace("{0}", 
						ResourceUtil.getString("table.eopBasedataSupplier", "eop-basedata")+ResourceUtil.getString("eopBasedataSupplier.fdCode", "eop-basedata")));
			}
			if(dataCache.get(fdCode)!=null){
				contact.setDocMain((EopBasedataSupplier) dataCache.get(fdCode));
			}
			for(int m=1;m<=7;m++){
				String value=EopBasedataExcelUtil.getCellValue(row.getCell(m));
				if(("fdName".equals(contactProperty[m])
						||"fdPhone".equals(contactProperty[m])
						||"fdEmail".equals(contactProperty[m])
						||"fdIsfirst".equals(contactProperty[m]))&&StringUtil.isNull(value)){
					moreMessages.add(ResourceUtil.getString("message.validation.required", "eop-basedata").replace("{0}", 
							ResourceUtil.getString("eopBasedataContact."+contactProperty[m], "eop-basedata")));
				}
				if("fdIsfirst".equals(contactProperty[m])) {
					PropertyUtils.setProperty(contact, contactProperty[m], Boolean.parseBoolean(value));
				}else {
					PropertyUtils.setProperty(contact, contactProperty[m], value);
				}
			}
			List<EopBasedataContact> contactList=new ArrayList<>();
			if(contactMap.containsKey(fdCode)){
				contactList=contactMap.get(fdCode);
			}
			contactList.add(contact);
			contactMap.put(fdCode, contactList);
			if(!ArrayUtil.isEmpty(moreMessages)){
				message.setMessageType(0);
				message.setMessage(ResourceUtil.getString("table.eopBasedataContact", "eop-basedata")+"："+ResourceUtil.getString("message.validation.rowNum", "eop-basedata").replace("{0}", String.valueOf(i)));
				message.setMoreMessages(moreMessages);
				messages.add(message);
			}
		}
		if(ArrayUtil.isEmpty(messages)){
			//全部校验通过
			IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.eop.data.sync.extend");
			IExtension[] extensions = point.getExtensions();
			IEopBasedataSyncDataService syncService = null;
			if(extensions.length > 0){
				for (IExtension extension : extensions) {
					if ("setting".equals(extension.getAttribute("name"))) {
						String serviceName = Plugin.getParamValue(extension, "serviceName");
						if ("relativeSyncDataService".equals(serviceName)) {
							//目前只是EOP新增数据时同步到相对方，所以加了判断
							syncService = (IEopBasedataSyncDataService) SpringBeanUtil.getBean(serviceName);
						}
					}
				}
			}
			for(String key : dataCache.keySet()){
				EopBasedataSupplier customer=(EopBasedataSupplier) dataCache.get(key);
				List<EopBasedataSupplierAccount> detailList=new ArrayList<>();
				if(accountMap.containsKey(customer.getFdCode())){
					//有对应的账号信息
					detailList=customer.getFdAccountList();
					if(!ArrayUtil.isEmpty(detailList)){
						detailList.clear();
					}else{
						detailList=new ArrayList<>();
					}
					if(accountMap.containsKey(customer.getFdCode())
							&&accountMap.get(customer.getFdCode())!=null){
						detailList.addAll(accountMap.get(customer.getFdCode()));
					}
					customer.setFdAccountList(detailList);
				}else{
					if(!ArrayUtil.isEmpty(customer.getFdAccountList())){
						customer.getFdAccountList().clear();
					}
				}
				if(contactMap.containsKey(customer.getFdCode())){
					//有对应的联系人信息
					List<EopBasedataContact> contactList=customer.getFdContactPerson();
					if(!ArrayUtil.isEmpty(contactList)){
						contactList.clear();
					}else{
						contactList=new ArrayList<>();
					}
					if(contactMap.containsKey(customer.getFdCode())
							&&contactMap.get(customer.getFdCode())!=null){
						contactList.addAll(contactMap.get(customer.getFdCode()));
					}
					customer.setFdContactPerson(contactList);
				}else{
					if(!ArrayUtil.isEmpty(customer.getFdContactPerson())){
						customer.getFdContactPerson().clear();
					}
				}
				SysOrgPerson user=UserUtil.getUser();
				customer.setDocCreator(user);
				customer.setDocCreateTime(new Date());
				EopBasedataSupplier temp=new EopBasedataSupplier();
				if(dbCache.get(customer.getFdCode())!=null){
					//数据库已存在
					temp=(EopBasedataSupplier) dbCache.get(customer.getFdCode());
				}
				temp=cloneSupplier(customer,temp);
				getBaseDao().getHibernateTemplate().saveOrUpdate(temp);
				if (syncService != null) {
					syncService.updateEopSupplierDataToBusi(temp);
				}
			}
		}
		dbCache.clear();
		dataCache.clear();
		companyCache.clear();
		return messages;
	}

	/**
	 * 校验是否开启了cmb、cbs、cmbint其中一个且模块存在
	 * @return
	 * @throws Exception
	 */
	public Boolean checkCmbBankcher() throws Exception {
		String switchValue= EopBasedataFsscUtil.getSwitchValue("fdUseBank");
		return ((";"+switchValue+";").indexOf(";CMB")>-1 &&null!= SysConfigs.getInstance().getModule("/fssc/cmb/"))
				||((";"+switchValue+";").indexOf(";CBS")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cbs/"))
				||((";"+switchValue+";").indexOf(";CMInt")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cmbint/"));
	}
    
    public EopBasedataSupplier cloneSupplier(EopBasedataSupplier from,EopBasedataSupplier to) throws Exception{
    	Map<String, SysDictCommonProperty> dictMap=SysDataDict.getInstance().getModel(EopBasedataSupplier.class.getName()).getPropertyMap();
    	for (Map.Entry<String, SysDictCommonProperty> entry : dictMap.entrySet()) {
           String property=entry.getKey();
           if(!"fdId".equals(property)){
        	   if("fdAccountList".equals(property)){
        		   List<EopBasedataSupplierAccount> newDetailList=new ArrayList<>();
        		   List<EopBasedataSupplierAccount> oldDetailList=from.getFdAccountList();
        		   if(oldDetailList!=null){
        			   for(EopBasedataSupplierAccount oldAccount:oldDetailList){
        				   EopBasedataSupplierAccount newAccout=new EopBasedataSupplierAccount();
            			   newAccout.setDocMain(to);//替换对应的客商主表
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
            			   newContact.setDocMain(to);//替换对应的客商主表
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
	 * 根据开关获取所属城市
	 * @param switchValue
	 * @return
	 * @throws Exception
	 */
	public List<String> getBankCityList(String switchValue) throws Exception {
		List<String> cityList=new ArrayList<String>();
		String hql="";
		if((";"+switchValue+";").indexOf(";CBS")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cmb/")){
			hql="select t.fdCity from FsscCbsCity t ";
			cityList=this.getBaseDao().getHibernateSession().createQuery(hql).list();
		}else if((";"+switchValue+";").indexOf(";CMB")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cmb/")){
			hql="select t.fdCity from FsscCmbCityCode t where t.fdIsAvailable=:fdIsAvailable";
			cityList=this.getBaseDao().getHibernateSession().createQuery(hql)
					.setParameter("fdIsAvailable",true).list();
		}else if((";"+switchValue+";").indexOf(";CMInt")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cmbint/")){
			hql="select t.fdCity from FsscCmbIntCityCode t where t.fdIsAvailable=:fdIsAvailable";
			cityList=this.getBaseDao().getHibernateSession().createQuery(hql)
					.setParameter("fdIsAvailable",true).list();
		}
		return cityList;
	}
	
    /**
     * 获取数据库现有客商数据
     * @param fdCompanyId
     * @return
     * @throws Exception
     */
    public Map<String,EopBasedataSupplier> getSupplierCache() throws Exception{
    	Map<String,EopBasedataSupplier> map=new HashMap<String,EopBasedataSupplier>();
    	HQLInfo hqlInfo=new HQLInfo();
    	String where="eopBasedataSupplier.fdIsAvailable=:fdIsAvailable";
    	hqlInfo.setParameter("fdIsAvailable", true);
    	hqlInfo.setWhereBlock(where);
    	List<EopBasedataSupplier> dataList=this.findList(hqlInfo);
    	for(EopBasedataSupplier supplier:dataList){
    		map.put(supplier.getFdCode(), supplier);
    	}
    	return map;
    }
    /**
     * 获取数据库现有公司数据
     * @param fdCompanyId
     * @return
     * @throws Exception
     */
    public KmssCache getCompanyCache() throws Exception{
    	KmssCache cache=new KmssCache(EopBasedataCompany.class);
    	String hql="select eopBasedataCompany from EopBasedataCompany eopBasedataCompany where eopBasedataCompany.fdIsAvailable=:fdIsAvailable";
    	List<EopBasedataCompany> dataList=this.getBaseDao().getHibernateSession().createQuery(hql)
    			.setParameter("fdIsAvailable", true).list();
    	for(EopBasedataCompany company:dataList){
    		cache.put(company.getFdCode(), company);
    	}
    	return cache;
    }

	/**
     * 导出客商数据
     */
    @Override
	public void exportSupplier(HttpServletResponse response,String fdCompanyId) throws Exception {
		String hql = "select t from EopBasedataSupplier t";
		hql += " where t.fdIsAvailable=:fdIsAvailable";
		if(StringUtil.isNotNull(fdCompanyId)){
			hql += " and t.fdCompany.fdId=:fdCompanyId";
		}
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdIsAvailable", true);
		if(StringUtil.isNotNull(fdCompanyId)){
			query.setParameter("fdCompanyId", fdCompanyId);
		}
		List<EopBasedataSupplier> models = query.list();
		String filename = ResourceUtil.getString("table.eopBasedataSupplier","eop-basedata");
		filename = new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls";
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filename);
		Workbook workBook = new HSSFWorkbook();
		String[] mainTitle=ResourceUtil.getString("eopBasedataSupplier.main.export.title","eop-basedata").split(";");
		String[] mainProperty={"fdName","fdAbbreviation","fdCode","fdTaxNo","fdErpNo","fdCreditCode","fdCodeValidityPeriod"};
		String[] accountTitle=new String[]{};
		String switchValue= EopBasedataFsscUtil.getSwitchValue("fdUseBank");
		if(((";"+switchValue+";").indexOf(";CMB")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cmb/"))
				||((";"+switchValue+";").indexOf(";CBS")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cbs/"))
				||((";"+switchValue+";").indexOf(";CMInt")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cmbint/"))){
			accountTitle=ResourceUtil.getString("eopBasedataSupplier.account.export.title.bank","eop-basedata").split(";");
		}else{
			accountTitle=ResourceUtil.getString("eopBasedataSupplier.account.export.title","eop-basedata").split(";");
		}
		String[] accountProperty={"fdSupplierArea","fdAccountName","fdBankName","fdBankNo","fdBankAccount","fdAccountAreaName","fdBankSwift","fdReceiveCompany","fdReceiveBankName","fdReceiveBankAddress","fdInfo"};
		String[] contactTitle=ResourceUtil.getString("eopBasedataContact.export.title","eop-basedata").split(";");
		String[] contactProperty={"fdName","fdPosition","fdPhone","fdEmail","fdAddress","fdRemarks","fdIsfirst"};
		List<EopBasedataSupplierAccount> accountList=new ArrayList<>();
		List<EopBasedataContact> contactList=new ArrayList<>();
		//主表信息导出
		String sheetName = ResourceUtil.getString("table.eopBasedataSupplier","eop-basedata");
		Sheet sheet = workBook.createSheet(sheetName);
		for (int i = 0; i <= mainTitle.length; i++) {
			sheet.setColumnWidth((short) i, (short) 4000);
		}
		CellStyle title = EopBasedataImportUtil.getTitleStyle(workBook);
		CellStyle content = EopBasedataImportUtil.getNormalStyle(workBook);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(ResourceUtil.getString("page.serial"));
		cell.setCellStyle(title);
		for (int i = 0; i < mainTitle.length; i++) {
			cell = row.createCell(i+1);
			cell.setCellValue(mainTitle[i]);
			cell.setCellStyle(title);
		}
		int index = 1;
		for(EopBasedataSupplier customer:models){
			row = sheet.createRow(index++);
			cell = row.createCell(0); //序号
			cell.setCellValue(index-1);
			String fdCompanyNames="",fdCompanyCodes="";
			List<EopBasedataCompany> companyList=customer.getFdCompanyList();
			if(!ArrayUtil.isEmpty(companyList)) {
				for(EopBasedataCompany company:companyList) {
					fdCompanyNames=StringUtil.linkString(fdCompanyNames, ";", company.getFdName());
					fdCompanyCodes=StringUtil.linkString(fdCompanyCodes, ";", company.getFdCode());
				}
			}
			cell.setCellStyle(content);
			cell = row.createCell(1);  //公司名称
			cell.setCellValue(fdCompanyNames);
			cell.setCellStyle(content);
			cell = row.createCell(2);  //公司代码
			cell.setCellValue(fdCompanyCodes);
			cell.setCellStyle(content);
			for (int i = 0; i < mainProperty.length; i++) {
				cell = row.createCell(i+3);
				Object value=PropertyUtils.getProperty(customer, mainProperty[i]);
				if(value!=null){
					if("fdCodeValidityPeriod".equals(mainProperty[i])){
						cell.setCellValue(DateUtil.convertDateToString((Date)value,DateUtil.PATTERN_DATE));
					}else{
						cell.setCellValue(String.valueOf(value));
					}
				}
				cell.setCellStyle(content);
			}
			accountList.addAll(customer.getFdAccountList());
			contactList.addAll(customer.getFdContactPerson());
		}
		//账号信息导出
		sheetName = ResourceUtil.getString("table.eopBasedataSupplierAccount","eop-basedata");
		sheet = workBook.createSheet(sheetName);
		for (int i = 0; i < accountTitle.length; i++) {
			sheet.setColumnWidth((short) i, (short) 4000);
		}
		row = sheet.createRow(0);
		for (int i = 0; i < accountTitle.length; i++) {
			if(!checkCmbBankcher()&&"fdAccountAreaName".equals(accountTitle[i])){ //未开启cmb相关开关，且所属城市字段跳过执行
				continue;  //未启用开关或者不存在模块，则跳过所属城市到处
			}
			cell = row.createCell(i);
			cell.setCellValue(accountTitle[i]);
			cell.setCellStyle(title);
		}
		index = 1;
		for(EopBasedataSupplierAccount account:accountList){
			row = sheet.createRow(index++);
			cell = row.createCell(0);  //客商编码
			cell.setCellValue(account.getDocMain().getFdCode());
			cell.setCellStyle(content);
			for (int i = 0; i < accountProperty.length; i++) {
				if(!checkCmbBankcher()&&"fdAccountAreaName".equals(accountTitle[i])){ //未开启cmb相关开关，且所属城市字段跳过执行
					continue;  //未启用开关或者不存在模块，则跳过所属城市到处
				}
				cell = row.createCell(i+1);
				Object value=PropertyUtils.getProperty(account, accountProperty[i]);
				if(value!=null){
					cell.setCellValue(String.valueOf(value));
				}
				cell.setCellStyle(content);
			}
		}
		//联系人信息导出
		sheetName = ResourceUtil.getString("table.eopBasedataContact","eop-basedata");
		sheet = workBook.createSheet(sheetName);
		for (int i = 0; i < contactTitle.length; i++) {
			sheet.setColumnWidth((short) i, (short) 4000);
		}
		row = sheet.createRow(0);
		for (int i = 0; i < contactTitle.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(contactTitle[i]);
			cell.setCellStyle(title);
		}
		index = 1;
		for(EopBasedataContact contact:contactList){
			row = sheet.createRow(index++);
			cell = row.createCell(0);  //客商编码
			cell.setCellValue(contact.getDocMain().getFdCode());
			cell.setCellStyle(content);
			for (int i = 0; i < contactProperty.length; i++) {
				cell = row.createCell(i+1);
				Object value=PropertyUtils.getProperty(contact, contactProperty[i]);
				if(value!=null){
					if("fdIsfirst".equals(contactProperty[i])) {
						cell.setCellValue(Boolean.parseBoolean(String.valueOf(value))?"1":"0");
					}else {
						cell.setCellValue(String.valueOf(value));
					}
				}
				cell.setCellStyle(content);
			}
		}
		workBook.write(os);
		os.flush();
		os.close();
	}

	/**
	 * 采购供应商不允许删除
	 * @param modelObj
	 *            model对象
	 * @throws Exception
	 */
	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		EopBasedataSupplier eopBasedataSupplier = (EopBasedataSupplier) modelObj;
		IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.eop.supplier.info.extend");
		IExtension[] extensions = point.getExtensions();
		boolean canDelete = true;
		String serviceName = "";
		if(extensions.length > 0){
			for (IExtension extension : extensions) {
				if ("setting".equals(extension.getAttribute("name"))) {
					serviceName = Plugin.getParamValue(extension, "modelName");
					canDelete = ((IEopBasedataSupplierCheckService) SpringBeanUtil.getBean(serviceName)).checkSupplierCanDelete(eopBasedataSupplier);
					if (!canDelete) {
						break;
					}
				}
			}
		}
		//true代表供应商没有被采购业务使用，可以删除，false则相反
		if(canDelete){
			super.delete(modelObj);
		}else{
			if ("relativeSupplierCheckService".equals(serviceName)) {
				throw new KmssRuntimeException(new KmssMessage("eop-basedata:relative.supplier.can.not.delete"));
			} else {
				throw new KmssRuntimeException(new KmssMessage("eop-basedata:pruchase.supplier.can.not.delete"));
			}


		}
	}

	@Override
    public EopBasedataSupplier getEopBasedataSupplierByCode(String fdCompanyId, String fdCode) throws Exception {
		if(StringUtil.isNull(fdCompanyId) || StringUtil.isNull(fdCode)){
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock("left join eopBasedataSupplier.fdCompanyList comp");
		hqlInfo.setWhereBlock(" (comp.fdId = :fdCompanyId or comp.fdId is null) and UPPER(eopBasedataSupplier.fdCode) = :fdCode ");
		hqlInfo.setParameter("fdCompanyId", fdCompanyId);
		hqlInfo.setParameter("fdCode", fdCode.toUpperCase());
		List<EopBasedataSupplier> list = this.findList(hqlInfo);
		return ArrayUtil.isEmpty(list)?null:list.get(0);
	}
}
