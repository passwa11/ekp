package com.landray.kmss.eop.basedata.service.spring;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.eop.basedata.service.IEopBasedataCustomerCheckService;
import com.landray.kmss.eop.basedata.service.IEopBasedataSupplierCheckService;
import com.landray.kmss.eop.basedata.service.IEopBasedataSyncDataService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.util.KmssMessage;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.imp.EopBasedataImportUtil;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportMessage;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomer;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomerAccount;
import com.landray.kmss.eop.basedata.service.IEopBasedataCustomerService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPayWayService;
import com.landray.kmss.eop.basedata.util.EopBasedataExcelUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.upload.FormFile;

public class EopBasedataCustomerServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataCustomerService {

    private IEopBasedataCustomerService eopBasedataCustomerService;

    public IEopBasedataCustomerService getEopBasedataCustomerService() {
        if (eopBasedataCustomerService == null) {
            eopBasedataCustomerService = (IEopBasedataCustomerService) SpringBeanUtil.getBean("eopBasedataCustomerService");
        }
        return eopBasedataCustomerService;
    }

    private IEopBasedataPayWayService eopBasedataPayWayService;

    public IEopBasedataPayWayService getEopBasedataPayWayService() {
        if (eopBasedataPayWayService == null) {
            eopBasedataPayWayService = (IEopBasedataPayWayService) SpringBeanUtil.getBean("eopBasedataPayWayService");
        }
        return eopBasedataPayWayService;
    }

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataCustomer) {
            EopBasedataCustomer eopBasedataCustomer = (EopBasedataCustomer) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataCustomer eopBasedataCustomer = new EopBasedataCustomer();
        eopBasedataCustomer.setFdIsAvailable(true);
        eopBasedataCustomer.setDocCreateTime(new Date());
        eopBasedataCustomer.setDocCreator(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataCustomer, requestContext);
        return eopBasedataCustomer;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataCustomer eopBasedataCustomer = (EopBasedataCustomer) model;
    }

    @Override
    public List<EopBasedataCustomer> findByUserId(String userId) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataCustomer.fdUser.fdId=:fdUserId");
        hqlInfo.setParameter("fdUserId", userId);
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataCustomer> findByName(String fdName) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataCustomer.fdName=:fdName");
        hqlInfo.setParameter("fdName", fdName);
        return this.findList(hqlInfo);
    }

    @Override
    public void downloadTemp(HttpServletResponse response) throws Exception {
        String filename = ResourceUtil.getString("table.eopBasedataCustomer", "eop-basedata");
        filename = new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls";
        OutputStream os = response.getOutputStream();
        response.reset();
        response.setContentType("application/vnd.ms-excel; charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename="
                + filename);
        Workbook workBook = new HSSFWorkbook();
        //客商信息模板
        String sheetName = ResourceUtil.getString("table.eopBasedataCustomer", "eop-basedata");
        Sheet sheet = workBook.createSheet(sheetName);
        String[] mainTitle = ResourceUtil.getString("eopBasedataCustomer.main.export.title", "eop-basedata").split(";");
        for (int i = 0; i <= mainTitle.length; i++) {
            sheet.setColumnWidth((short) i, (short) 4000);
        }
        CellStyle style = EopBasedataImportUtil.getTitleStyle(workBook);
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(ResourceUtil.getString("page.serial"));
        cell.setCellStyle(style);
        for (int i = 0; i < mainTitle.length; i++) {
            cell = row.createCell(i + 1);
            cell.setCellValue(mainTitle[i]);
            cell.setCellStyle(style);
        }
        //收款账户模板
        sheetName = ResourceUtil.getString("table.eopBasedataCustomerAccount", "eop-basedata");
        sheet = workBook.createSheet(sheetName);
        row = sheet.createRow(0);
        String[] accountTitle=new String[]{};
        String switchValue= EopBasedataFsscUtil.getSwitchValue("fdUseBank");
        if((";"+switchValue+";").indexOf(";CMB")>-1 ||(";"+switchValue+";").indexOf(";CBS")>-1 ||(";"+switchValue+";").indexOf(";CMInt")>-1 ){
            accountTitle=ResourceUtil.getString("eopBasedataCustomer.account.export.title.bank","eop-basedata").split(";");
        }else{
            accountTitle=ResourceUtil.getString("eopBasedataCustomer.account.export.title", "eop-basedata").split(";");
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
        workBook.write(os);
        os.flush();
        os.close();
    }

    @Override
    public List<EopBasedataImportMessage> saveImport(FormFile fdFile) throws Exception {
        List<EopBasedataImportMessage> messages = new ArrayList<>();
        Map<String, EopBasedataCustomer> dbCache = getCustomerCache();  //缓存数据库现有数据
        Map<String, EopBasedataCustomer> dataCache = new HashMap<>();  //缓存Excel数据
        KmssCache companyCache = getCompanyCache();  //缓存记账公司数据
        Workbook wb = WorkbookFactory.create(fdFile.getInputStream());
        //获取客商主表数据
        Sheet sheet = wb.getSheetAt(0);
        String[] mainProperty={"fdCode","fdName","fdAbbreviation","fdTaxNo","fdErpNo","fdCreditCode"};
        List<Object> valueList = new ArrayList<>();
        Row row = null;
        for (int i = 1, j = sheet.getPhysicalNumberOfRows(); i < j; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            EopBasedataImportMessage message = new EopBasedataImportMessage();
            List<String> moreMessages = new ArrayList<>();  //同一行提示信息
            valueList = new ArrayList<>();
            EopBasedataCustomer customer = new EopBasedataCustomer();
            String fdCode = EopBasedataExcelUtil.getCellValue(row.getCell(5));  //客户编码
            if (StringUtil.isNull(fdCode)) {
                moreMessages.add(ResourceUtil.getString("message.validation.required", "eop-basedata").replace("{0}",
                        ResourceUtil.getString("table.eopBasedataCustomer", "eop-basedata") + ResourceUtil.getString("eopBasedataCustomer.fdCode", "eop-basedata")));
            }
            customer.setFdIsAvailable(Boolean.TRUE);
            String fdCompanyCode = EopBasedataExcelUtil.getCellValue(row.getCell(2));  //公司编码
            if (StringUtil.isNotNull(fdCompanyCode)) {
                List<EopBasedataCompany> companyList = new ArrayList<EopBasedataCompany>();
                if (fdCompanyCode.indexOf(";") > -1) {
                    String[] codes = fdCompanyCode.split(";");
                    for (String code : codes) {
                        companyList.add((EopBasedataCompany) companyCache.get(code));
                    }
                } else {
                    companyList.add((EopBasedataCompany) companyCache.get(fdCompanyCode));
                }
                customer.getFdCompanyList().clear();
                customer.getFdCompanyList().addAll(companyList);
            }
            valueList.add(fdCode);
            String value = EopBasedataExcelUtil.getCellValue(row.getCell(3));  //客户名称
            if (StringUtil.isNull(value)) {
                moreMessages.add(ResourceUtil.getString("message.validation.required", "eop-basedata").replace("{0}",
                        ResourceUtil.getString("table.eopBasedataCustomer", "eop-basedata") + ResourceUtil.getString("eopBasedataCustomer.fdName", "eop-basedata")));
            }
            valueList.add(value);
            value = EopBasedataExcelUtil.getCellValue(row.getCell(4));  //客户简称
            valueList.add(value);
            //纳税人识别号、ERP号、统一社会信用代码、信用证有效截止日期
            for (int m = 6; m < 9; m++) {
                value = EopBasedataExcelUtil.getCellValue(row.getCell(m));
                valueList.add(value);
            }
            for (int n = 0, len = valueList.size(); n < len; n++) {
                PropertyUtils.setProperty(customer, mainProperty[n], valueList.get(n));
            }
            dataCache.put(fdCode, customer);
            if (!ArrayUtil.isEmpty(moreMessages)) {
                message.setMessageType(0);
                message.setMessage(ResourceUtil.getString("table.eopBasedataCustomer", "eop-basedata") + "：" + ResourceUtil.getString("message.validation.rowNum", "eop-basedata").replace("{0}", String.valueOf(i)));
                message.setMoreMessages(moreMessages);
                messages.add(message);
            }
        }
        //获取收款账户信息
        sheet = wb.getSheetAt(1);
        String[] accountProperty = {"fdCode", "fdSupplierArea", "fdAccountName", "fdBankName", "fdBankNo", "fdBankAccount","fdAccountAreaName", "fdBankSwift", "fdReceiveCompany", "fdReceiveBankName", "fdReceiveBankAddress", "fdInfo"};
        valueList = new ArrayList<>();
        Map<String, List<EopBasedataCustomerAccount>> accountMap = new HashMap<>();
        row = null;
        for (int i = 1, j = sheet.getPhysicalNumberOfRows(); i < j; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            EopBasedataImportMessage message = new EopBasedataImportMessage();
            List<String> moreMessages = new ArrayList<>();  //同一行提示信息
            EopBasedataCustomerAccount account = new EopBasedataCustomerAccount();
            String fdCode = EopBasedataExcelUtil.getCellValue(row.getCell(0));  //客商编码
            if (StringUtil.isNull(fdCode)) {
                moreMessages.add(ResourceUtil.getString("message.validation.required", "eop-basedata").replace("{0}",
                        ResourceUtil.getString("table.eopBasedataCustomer", "eop-basedata") + ResourceUtil.getString("eopBasedataCustomer.fdCode", "eop-basedata")));
            }
            if (dataCache.get(fdCode) != null) {
                account.setDocMain((EopBasedataCustomer) dataCache.get(fdCode));
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
                String value = EopBasedataExcelUtil.getCellValue(row.getCell(m));
                if(checkCmbBankcher()&&m==6){
                    if(!areaNameList.contains(value)){
                        moreMessages.add(ResourceUtil.getString("message.validation.exists", "eop-basedata").replace("{0}",
                                ResourceUtil.getString("eopBasedataCustomerAccount.fdAccountAreaName", "eop-basedata")));
                    }
                    account.setFdAccountAreaCode(value);
                    account.setFdAccountAreaName(value);
                }
                if (("fdSupplierArea".equals(accountProperty[m])
                        || "fdAccountName".equals(accountProperty[m])
                        || "fdBankName".equals(accountProperty[m])
                        || "fdBankAccount".equals(accountProperty[m])) && StringUtil.isNull(value)) {
                    moreMessages.add(ResourceUtil.getString("message.validation.required", "eop-basedata").replace("{0}",
                            ResourceUtil.getString("eopBasedataCustomer." + accountProperty[m], "eop-basedata")));
                }
                if (("fdBankSwift".equals(accountProperty[m])
                        || "fdReceiveCompany".equals(accountProperty[m])
                        || "fdReceiveBankName".equals(accountProperty[m])
                        || "fdReceiveBankAddress".equals(accountProperty[m]))
                        && StringUtil.isNull(value) && EopBasedataConstant.FSSC_BASE_SUPPLIER_AREA_1.equals(account.getFdSupplierArea())) {
                    moreMessages.add(ResourceUtil.getString("message.validation.required", "eop-basedata").replace("{0}",
                            ResourceUtil.getString("eopBasedataCustomer." + accountProperty[m], "eop-basedata")));
                }
                PropertyUtils.setProperty(account, accountProperty[m], value);
            }
            List<EopBasedataCustomerAccount> accountList = new ArrayList<>();
            if (accountMap.containsKey(fdCode)) {
                accountList = accountMap.get(fdCode);
            }
            accountList.add(account);
            accountMap.put(fdCode, accountList);
            if (!ArrayUtil.isEmpty(moreMessages)) {
                message.setMessageType(0);
                message.setMessage(ResourceUtil.getString("table.eopBasedataCustomerAccount", "eop-basedata") + "：" + ResourceUtil.getString("message.validation.rowNum", "eop-basedata").replace("{0}", String.valueOf(i)));
                message.setMoreMessages(moreMessages);
                messages.add(message);
            }
        }
        if (ArrayUtil.isEmpty(messages)) {
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
            for (String key : dataCache.keySet()) {
                EopBasedataCustomer customer = (EopBasedataCustomer) dataCache.get(key);
                List<EopBasedataCustomerAccount> detailList = new ArrayList<>();
                if (accountMap.containsKey(customer.getFdCode())) {
                    //有对应的账号信息
                    detailList = customer.getFdAccountList();
                    if (!ArrayUtil.isEmpty(detailList)) {
                        detailList.clear();
                    } else {
                        detailList = new ArrayList<>();
                    }
                    if (accountMap.containsKey(customer.getFdCode())
                            && accountMap.get(customer.getFdCode()) != null) {
                        detailList.addAll(accountMap.get(customer.getFdCode()));
                    }
                    customer.setFdAccountList(detailList);
                } else {
                    if (!ArrayUtil.isEmpty(customer.getFdAccountList())) {
                        customer.getFdAccountList().clear();
                    }
                }
                SysOrgPerson user = UserUtil.getUser();
                customer.setDocCreator(user);
                customer.setDocCreateTime(new Date());
                EopBasedataCustomer temp = new EopBasedataCustomer();
                if (dbCache.get(customer.getFdCode()) != null) {
                    //数据库已存在
                    temp = (EopBasedataCustomer) dbCache.get(customer.getFdCode());
                }
                temp = cloneCustomer(customer, temp);
                getBaseDao().getHibernateTemplate().saveOrUpdate(temp);
                if (syncService != null) {
                    syncService.updateEopCustomerDataToBusi(temp);
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
        return ((";"+switchValue+";").indexOf(";CMB")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cmb/"))
                ||((";"+switchValue+";").indexOf(";CBS")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cbs/"))
                ||((";"+switchValue+";").indexOf(";CMInt")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cmbint/"));
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
    

    public EopBasedataCustomer cloneCustomer(EopBasedataCustomer from, EopBasedataCustomer to) throws Exception {
        Map<String, SysDictCommonProperty> dictMap = SysDataDict.getInstance().getModel(EopBasedataCustomer.class.getName()).getPropertyMap();
        for (Map.Entry<String, SysDictCommonProperty> entry : dictMap.entrySet()) {
            String property = entry.getKey();
            if (!"fdId".equals(property)) {
                if ("fdAccountList".equals(property)) {
                    List<EopBasedataCustomerAccount> newDetailList = new ArrayList<>();
                    List<EopBasedataCustomerAccount> oldDetailList = from.getFdAccountList();
                    if (oldDetailList != null) {
                        for (EopBasedataCustomerAccount oldAccount : oldDetailList) {
                            EopBasedataCustomerAccount newAccout = new EopBasedataCustomerAccount();
                            newAccout.setDocMain(to);//替换对应的客商主表
                            newDetailList.add(cloneAccount(oldAccount, newAccout));
                        }
                    }
                    if (to.getFdAccountList() != null) {
                        if (ArrayUtil.isEmpty(to.getFdAccountList())) {
                            to.getFdAccountList().addAll(newDetailList);
                        } else {
                            to.getFdAccountList().clear();
                            to.getFdAccountList().addAll(newDetailList);
                        }
                    } else {
                        to.setFdAccountList(newDetailList);
                    }
                } else if(!"fdContactPerson".equals(property)){
                    PropertyUtils.setProperty(to, property, PropertyUtils.getProperty(from, property));
                }
            }
        }
        return to;
    }

    public EopBasedataCustomerAccount cloneAccount(EopBasedataCustomerAccount fromAccount, EopBasedataCustomerAccount toAccount) throws Exception {
        Map<String, SysDictCommonProperty> dictMap = SysDataDict.getInstance().getModel(EopBasedataCustomerAccount.class.getName()).getPropertyMap();
        for (Map.Entry<String, SysDictCommonProperty> entry : dictMap.entrySet()) {
            String property = entry.getKey();
            SysDictCommonProperty pro = dictMap.get(property);
            if (!"fdId".equals(property) && !"docMain".equals(property)) {
                PropertyUtils.setProperty(toAccount, property, PropertyUtils.getProperty(fromAccount, property));
            }
        }
        return toAccount;
    }

    /**
     * 获取数据库现有客商数据
     *
     * @param fdCompanyId
     * @return
     * @throws Exception
     */
    public Map<String, EopBasedataCustomer> getCustomerCache() throws Exception {
        Map<String, EopBasedataCustomer> map = new HashMap<String, EopBasedataCustomer>();
        HQLInfo hqlInfo = new HQLInfo();
        String where = "eopBasedataCustomer.fdIsAvailable=:fdIsAvailable";
        hqlInfo.setParameter("fdIsAvailable", true);
        hqlInfo.setWhereBlock(where);
        List<EopBasedataCustomer> dataList = this.findList(hqlInfo);
        for (EopBasedataCustomer supplier : dataList) {
            map.put(supplier.getFdCode(), supplier);
        }
        return map;
    }

    /**
     * 获取数据库现有公司数据
     *
     * @param fdCompanyId
     * @return
     * @throws Exception
     */
    public KmssCache getCompanyCache() throws Exception {
        KmssCache cache = new KmssCache(EopBasedataCompany.class);
        String hql = "select eopBasedataCompany from EopBasedataCompany eopBasedataCompany where eopBasedataCompany.fdIsAvailable=:fdIsAvailable";
        List<EopBasedataCompany> dataList = this.getBaseDao().getHibernateSession().createQuery(hql)
                .setParameter("fdIsAvailable", true).list();
        for (EopBasedataCompany company : dataList) {
            cache.put(company.getFdCode(), company);
        }
        return cache;
    }

    /**
     * 导出客商数据
     */
    @Override
    public void exportCustomer(HttpServletResponse response, String fdCompanyId) throws Exception {
        String hql = "select t from EopBasedataCustomer t";
        hql += " where t.fdIsAvailable=:fdIsAvailable";
        if (StringUtil.isNotNull(fdCompanyId)) {
            hql += " and t.fdCompany.fdId=:fdCompanyId";
        }
        Query query = getBaseDao().getHibernateSession().createQuery(hql);
        query.setParameter("fdIsAvailable", true);
        if (StringUtil.isNotNull(fdCompanyId)) {
            query.setParameter("fdCompanyId", fdCompanyId);
        }
        List<EopBasedataCustomer> models = query.list();
        String filename = ResourceUtil.getString("table.eopBasedataCustomer", "eop-basedata");
        filename = new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls";
        OutputStream os = response.getOutputStream();
        response.reset();
        response.setContentType("application/vnd.ms-excel; charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename="
                + filename);
        Workbook workBook = new HSSFWorkbook();
        String[] mainTitle = ResourceUtil.getString("eopBasedataCustomer.main.export.title", "eop-basedata").split(";");
        String[] mainProperty = {"fdName","fdAbbreviation","fdCode", "fdTaxNo", "fdErpNo", "fdCreditCode", "fdCodeValidityPeriod"};
        String[] accountTitle=new String[]{};
        String switchValue= EopBasedataFsscUtil.getSwitchValue("fdUseBank");
        if(((";"+switchValue+";").indexOf(";CMB")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cmb/"))
                ||((";"+switchValue+";").indexOf(";CBS")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cbs/"))
                ||((";"+switchValue+";").indexOf(";CMInt")>-1 &&null!=SysConfigs.getInstance().getModule("/fssc/cmbint/"))){
            accountTitle=ResourceUtil.getString("eopBasedataCustomer.account.export.title.bank","eop-basedata").split(";");
        }else{
            accountTitle=ResourceUtil.getString("eopBasedataCustomer.account.export.title", "eop-basedata").split(";");
        }
        String[] accountProperty = {"fdSupplierArea", "fdAccountName", "fdBankName", "fdBankNo", "fdBankAccount","fdAccountAreaName", "fdBankSwift", "fdReceiveCompany", "fdReceiveBankName", "fdReceiveBankAddress", "fdInfo"};
        List<EopBasedataCustomerAccount> accountList = new ArrayList<>();
        //主表信息导出
        String sheetName = ResourceUtil.getString("table.eopBasedataCustomer", "eop-basedata");
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
            cell = row.createCell(i + 1);
            cell.setCellValue(mainTitle[i]);
            cell.setCellStyle(title);
        }
        int index = 1;
        for (EopBasedataCustomer customer : models) {
            row = sheet.createRow(index++);
            cell = row.createCell(0); //序号
            cell.setCellValue(index - 1);
            String fdCompanyNames = "", fdCompanyCodes = "";
            List<EopBasedataCompany> companyList = customer.getFdCompanyList();
            if (!ArrayUtil.isEmpty(companyList)) {
                for (EopBasedataCompany company : companyList) {
                    fdCompanyNames = StringUtil.linkString(fdCompanyNames, ";", company.getFdName());
                    fdCompanyCodes = StringUtil.linkString(fdCompanyCodes, ";", company.getFdCode());
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
                cell = row.createCell(i + 3);
                Object value = PropertyUtils.getProperty(customer, mainProperty[i]);
                if (value != null) {
                    cell.setCellValue(String.valueOf(value));
                }
                cell.setCellStyle(content);
            }
            accountList.addAll(customer.getFdAccountList());
        }
        //账号信息导出
        sheetName = ResourceUtil.getString("table.eopBasedataCustomerAccount", "eop-basedata");
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
        for (EopBasedataCustomerAccount account : accountList) {
            row = sheet.createRow(index++);
            cell = row.createCell(0);  //客商编码
            cell.setCellValue(account.getDocMain().getFdCode());
            cell.setCellStyle(content);
            for (int i = 0; i < accountProperty.length; i++) {
                if(!checkCmbBankcher()&&"fdAccountAreaName".equals(accountTitle[i])){ //未开启cmb相关开关，且所属城市字段跳过执行
                    continue;  //未启用开关或者不存在模块，则跳过所属城市到处
                }
                cell = row.createCell(i + 1);
                Object value = PropertyUtils.getProperty(account, accountProperty[i]);
                if (value != null) {
                    cell.setCellValue(String.valueOf(value));
                }
                cell.setCellStyle(content);
            }
        }
        workBook.write(os);
        os.flush();
        os.close();
    }
    
    /**
 	 * 根据客户编号查询对应的客户
 	 * @throws Exception 
 	 */
 	@Override
    public EopBasedataCustomer findCustomerByCode(String fdCode, String fdCompanyCode) throws Exception {
 		if (StringUtil.isNull(fdCode)) {
            return null;
        }
 		HQLInfo hql = new HQLInfo();
 		StringBuffer whereBlock = new StringBuffer();
 		whereBlock.append("eopBasedataCustomer.fdCode = :fdCode and eopBasedataCustomer.fdIsAvailable=:fdIsAvailable");
 		hql.setParameter("fdCode", fdCode);
 		hql.setParameter("fdIsAvailable", true);
 		if(StringUtil.isNotNull(fdCompanyCode)) {
 			hql.setJoinBlock(" left join eopBasedataCustomer.fdCompanyList comp");
 			whereBlock.append(" and comp.fdCode = :fdCompanyCode");
 			hql.setParameter("fdCompanyCode", fdCompanyCode);
 		}
 		hql.setWhereBlock(whereBlock.toString());
 		List<EopBasedataCustomer> resList = this.findList(hql);
 		if (!resList.isEmpty()) {
 			return resList.get(0);
 		}
 		return null;
 	}

    @Override
    public EopBasedataCustomer getEopBasedataCustomerByCode(String fdCompanyId, String fdCode) throws Exception {
        if(StringUtil.isNull(fdCompanyId) || StringUtil.isNull(fdCode)){
            return null;
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setJoinBlock("left join eopBasedataCustomer.fdCompanyList comp");
        hqlInfo.setWhereBlock(" (comp.fdId = :fdCompanyId or comp.fdId is null) and UPPER(eopBasedataCustomer.fdCode) = :fdCode ");
        hqlInfo.setParameter("fdCompanyId", fdCompanyId);
        hqlInfo.setParameter("fdCode", fdCode.toUpperCase());
        List<EopBasedataCustomer> list = this.findList(hqlInfo);
        return ArrayUtil.isEmpty(list)?null:list.get(0);
    }

    @Override
    public void delete(IBaseModel modelObj) throws Exception {
        EopBasedataCustomer eopBasedataCustomer = (EopBasedataCustomer) modelObj;
        IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.eop.customer.info.extend");
        IExtension[] extensions = point.getExtensions();
        boolean canDelete = true;
        String serviceName = "";
        if(extensions.length > 0){
            for (IExtension extension : extensions) {
                if ("setting".equals(extension.getAttribute("name"))) {
                    serviceName = Plugin.getParamValue(extension, "modelName");
                    canDelete = ((IEopBasedataCustomerCheckService) SpringBeanUtil.getBean(serviceName)).checkCustomerCanDelete(eopBasedataCustomer);
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
            if ("relativeCustomerCheckService".equals(serviceName)) {
                throw new KmssRuntimeException(new KmssMessage("eop-basedata:relative.customer.can.not.delete"));
            } else {
                throw new KmssRuntimeException(new KmssMessage("eop-basedata:relative.customer.can.not.delete"));
            }
        }
    }

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataCustomer eopBasedataCustomer = (EopBasedataCustomer) modelObj;
 	    String fdId = super.add(eopBasedataCustomer);

        IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.eop.data.sync.extend");
        IExtension[] extensions = point.getExtensions();
        if(extensions.length > 0){
            for (IExtension extension : extensions) {
                if ("setting".equals(extension.getAttribute("name"))) {
                    String serviceName = Plugin.getParamValue(extension, "serviceName");
                    if ("relativeSyncDataService".equals(serviceName)) {
                        //目前只是EOP新增数据时同步到相对方，所以加了判断
                        IEopBasedataSyncDataService syncService = (IEopBasedataSyncDataService) SpringBeanUtil.getBean(serviceName);
                        syncService.updateEopCustomerDataToBusi(eopBasedataCustomer);
                    }
                }
            }
        }

        return fdId;
    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {
        EopBasedataCustomer eopBasedataCustomer = (EopBasedataCustomer) modelObj;
        super.update(modelObj);

        IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.eop.data.sync.extend");
        IExtension[] extensions = point.getExtensions();
        if(extensions.length > 0){
            for (IExtension extension : extensions) {
                if ("setting".equals(extension.getAttribute("name"))) {
                    String serviceName = Plugin.getParamValue(extension, "serviceName");
                    IEopBasedataSyncDataService syncService = (IEopBasedataSyncDataService) SpringBeanUtil.getBean(serviceName);
                    syncService.updateEopCustomerDataToBusi(eopBasedataCustomer);
                }
            }
        }
    }
}
