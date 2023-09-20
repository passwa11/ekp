package com.landray.kmss.fssc.budget.service.spring;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.imp.EopBasedataImportUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetSchemeService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budget.constant.FsscBudgetConstant;
import com.landray.kmss.fssc.budget.forms.FsscBudgetMainForm;
import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.fssc.budget.model.FsscBudgetDetail;
import com.landray.kmss.fssc.budget.model.FsscBudgetMain;
import com.landray.kmss.fssc.budget.service.IFsscBudgetAdjustLogService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDetailService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetMainService;
import com.landray.kmss.fssc.budget.util.FsscBudgetParseXmlUtil;
import com.landray.kmss.fssc.budget.util.FsscBudgetUtil;
import com.landray.kmss.fssc.budget.util.FsscBudgetValidateUtil;
import com.landray.kmss.fssc.common.util.DataBaseUtil;
import com.landray.kmss.fssc.common.util.ExcelUtil;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.upload.FormFile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscBudgetMainServiceImp extends ExtendDataServiceImp implements IFsscBudgetMainService {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(FsscBudgetMainServiceImp.class);
	protected IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService;
	
    public void setEopBasedataBudgetSchemeService(
			IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService) {
		this.eopBasedataBudgetSchemeService = eopBasedataBudgetSchemeService;
	}
    
    protected IEopBasedataCompanyService eopBasedataCompanyService;
    
    public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}

	protected IFsscBudgetDataService fsscBudgetDataService;
    

	public void setFsscBudgetDataService(
			IFsscBudgetDataService fsscBudgetDataService) {
		this.fsscBudgetDataService = fsscBudgetDataService;
	}
	
	protected IFsscBudgetDetailService fsscBudgetDetailService;

	public void setFsscBudgetDetailService(
			IFsscBudgetDetailService fsscBudgetDetailService) {
		this.fsscBudgetDetailService = fsscBudgetDetailService;
	}
	
	protected IFsscBudgetExecuteService fsscBudgetExecuteService;
	
	public void setFsscBudgetExecuteService(
			IFsscBudgetExecuteService fsscBudgetExecuteService) {
		this.fsscBudgetExecuteService = fsscBudgetExecuteService;
	}
	
	protected IFsscBudgetAdjustLogService fsscBudgetAdjustLogService;
	
	public void setFsscBudgetAdjustLogService(IFsscBudgetAdjustLogService fsscBudgetAdjustLogService) {
		this.fsscBudgetAdjustLogService = fsscBudgetAdjustLogService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscBudgetMain) {
            FsscBudgetMain fsscBudgetMain = (FsscBudgetMain) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscBudgetMain fsscBudgetMain = new FsscBudgetMain();
        fsscBudgetMain.setDocCreateTime(new Date());
        fsscBudgetMain.setDocCreator(UserUtil.getUser());
        fsscBudgetMain.setFdEnableDate(new Date());
        String fdSchemeId=requestContext.getParameter("fdSchemeId");
        EopBasedataBudgetScheme budgetScheme=null;
        if(StringUtil.isNotNull(fdSchemeId)){
        	budgetScheme=(EopBasedataBudgetScheme) this.findByPrimaryKey(fdSchemeId, EopBasedataBudgetScheme.class, true);
			fsscBudgetMain.setFdBudgetScheme(budgetScheme);
        }
        String fdCompanyId=requestContext.getParameter("fdCompanyId");
        if(budgetScheme!=null&&FsscCommonUtil.isContain(budgetScheme.getFdDimension(), "2;", ";")) {//维度包含公司
        	 if(StringUtil.isNull(fdCompanyId)){
             	//为空自动获取默认记账公司
             	List<EopBasedataCompany> companyList=eopBasedataCompanyService.findCompanyByUserId(UserUtil.getUser().getFdId());
             	if(!ArrayUtil.isEmpty(companyList)){
             		if(budgetScheme!=null){
             			List<EopBasedataCompany> comList=budgetScheme.getFdCompanys();
             			if(ArrayUtil.isEmpty(comList)){
             				fdCompanyId=companyList.get(0).getFdId();
             			}else{
             				//若是预算方案可使用范围不为空，过滤所在公司在不在范围内
             				for(EopBasedataCompany com:comList){
             					if(companyList.contains(com)){
             						fdCompanyId=companyList.get(0).getFdId();
             						break;
             					}
             				}
             			}
             		}
             	}
             }
             if(StringUtil.isNotNull(fdCompanyId)){
             	EopBasedataCompany company=(EopBasedataCompany) this.findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
             	fsscBudgetMain.setFdCompany(company);
             	fsscBudgetMain.setFdCurrency(company.getFdBudgetCurrency());
             }
        }else {//维度不包含公司，币种从开关设置获取
        	String fdCurrencyId=EopBasedataFsscUtil.getSwitchValue("fdCommonBudgetCurrencyId");
        	EopBasedataCurrency currency=(EopBasedataCurrency) eopBasedataCompanyService.findByPrimaryKey(fdCurrencyId, EopBasedataCurrency.class, true);
        	if(currency!=null) {
        		fsscBudgetMain.setFdCurrency(currency);
        	}
        }
        FsscBudgetUtil.initModelFromRequest(fsscBudgetMain, requestContext);
        return fsscBudgetMain;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscBudgetMain fsscBudgetMain = (FsscBudgetMain) model;
    }

    @Override
    public List<FsscBudgetMain> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetMain.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetMain> findByFdBudgetScheme(EopBasedataBudgetScheme fdBudgetScheme) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetMain.fdBudgetScheme.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdBudgetScheme.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetMain> findByFdCompanyGroup(EopBasedataCompanyGroup fdCompanyGroup) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetMain.fdCompanyGroup.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompanyGroup.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetMain> findByFdCurrency(EopBasedataExchangeRate fdCurrency) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetMain.fdCurrency.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCurrency.getFdId());
        return this.findList(hqlInfo);
    }
    /***************************************************
     * 根据预算方案导出excel模板
     * **********************************************/

	@Override
	public void downTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fdSchemeId=request.getParameter("fdSchemeId");
		String fdCompanyId=request.getParameter("fdCompanyId");
		List<String> titleList=FsscBudgetParseXmlUtil.getSchemeList("excel", fdSchemeId,null,null,null,fdCompanyId);//下载模板只需要表头
		EopBasedataBudgetScheme eopBasedataBudgetScheme=(EopBasedataBudgetScheme) eopBasedataBudgetSchemeService.findByPrimaryKey(fdSchemeId, null, true);
		String filename=eopBasedataBudgetScheme.getFdName()+ResourceUtil.getString("fsscBudgetMain.importTemplate","fssc-budget");
		filename = new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls";
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filename);
		Workbook workBook = new HSSFWorkbook();
		String sheetName = new String(filename.getBytes("ISO8859-1"), "GBK");
		String newSheetName = transformSheetName(sheetName);
		Sheet sheet = workBook.createSheet(newSheetName);
		for (int i = 0; i <= titleList.size(); i++) {
			sheet.setColumnWidth((short) i, (short) 4000);
		}
		CellStyle style = EopBasedataImportUtil.getTitleStyle(workBook);
		Row row = sheet.createRow(0);
		Cell cell = null;
		for (int i = 0; i < titleList.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(titleList.get(i));
			cell.setCellStyle(style);
		}
		workBook.write(os);
		os.flush();
		os.close();
	}

	private String transformSheetName(String sheetName){
		//对"*"、"/"、":"、"?"、"["、"\\"、"]"使用正则进行匹配
		Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|\\[\\]]");
		Matcher matcher = pattern.matcher(sheetName);
		String result = matcher.replaceAll("_"); // 将匹配到的非法字符以空替换

		return result;
	}
	
	/**
	 * 预算明细
	 * 
	 * @param importForm
	 * @return
	 * @throws Exception
	 */
	@Override
    @SuppressWarnings("unchecked")
	public JSONArray saveImport(FsscBudgetMainForm mainForm, HttpServletRequest request) throws Exception {
		String fdSchemeId=request.getParameter("fdSchemeId");
		String fdCompanyId=request.getParameter("fdCompanyId");
		String fdYear=request.getParameter("fdYear");
		JSONArray detailJsonArray = new JSONArray();  //excel数据
		List<Map<Integer,List<String>>> validateInfoList=new ArrayList<Map<Integer,List<String>>>();  //全部预算校验信息
		String validateStr="";  //单条错误信息接收
		boolean templateError=false;  //模板校验是否被删除列数
		String templateErrorTips="";  //模板错误提示
		FormFile file = mainForm.getFdFile();
		try {
			List<String> columnList=FsscBudgetParseXmlUtil.getSchemeList("column", fdSchemeId,null,null,null,fdCompanyId);
			SysDictModel sysDictModel=SysDataDict.getInstance().getModel(FsscBudgetDetail.class.getName());
			Map<String, SysDictCommonProperty> propertyMap=sysDictModel.getPropertyMap();
			Workbook wb = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = wb.getSheetAt(0);
			int columnNum=sheet.getRow(0).getPhysicalNumberOfCells(); //获取模板表头列数
			int initNum=getTemplateNum(fdSchemeId);
			if(columnNum-initNum!=0) {
				templateError=true;
				templateErrorTips=ResourceUtil.getString("message.budget.import.template.tips", "fssc-budget");
			}
			Map cacheMap=new ConcurrentHashMap<>();  //缓存对应的维度对象id，name信息
			Map budgetDataMap=getHaveData(fdSchemeId,fdCompanyId,fdYear);  //缓存对应的维度data数据对象id，name信息
			String moneyKey="";  //获取对应的金额
			// 取到工作表里面的数据
			for (int i = 1,shLen=sheet.getLastRowNum(); i <=shLen ; i++) {
				List<String> validateInfo=new ArrayList<String>();  //一行的校验信息
				Row row = sheet.getRow(i);
				// 判断是否空行
				if (ExcelUtil.isBlankRow(row)) {
					validateInfo.add(ResourceUtil.getString("message.import.excel.blank", "fssc-budget").replaceAll("{0}", String.valueOf(i)));
					continue;
				}
				JSONObject object = new JSONObject();
				object.put("fdId", IDGenerator.generateID());
				int n=0;  //当前读取excel的列索引数
				String rule="";  //接收本行数据预算控制规则是否有弹性，若无，则不需校验弹性比例字段
				//动态属性
				moneyKey="";
				Map<String,List<String>> propertyListMap=EopBasedataFsscUtil.getPropertyByScheme(fdSchemeId);
				List inPropertyList=propertyListMap.get("inPropertyList");
				if(inPropertyList.contains("fdCompany")){
					moneyKey+=fdCompanyId;
				}
				for(int k=0,tiLen=columnList.size();k<tiLen;k++){
					String pro=columnList.get(k);
					SysDictCommonProperty dict=propertyMap.get(pro);
					if(dict!=null){
						if(dict.getType().startsWith("com.landray.kmss")){
							n=n+2;
							String fdCode=ExcelUtil.getCellValue(row.getCell(2*k+1));  //获取编号，
							if("fdDept".equals(pro)||"fdPerson".equals(pro)){
								validateStr=FsscBudgetValidateUtil.validateProperty(null, pro, fdCode, "isNull");
							}else{
								validateStr=FsscBudgetValidateUtil.validateProperty(fdCompanyId, pro, fdCode, "isNull");
							}
							if(StringUtil.isNotNull(validateStr)){
								validateInfo.add(validateStr);
							}
							if("fdDept".equals(pro)||"fdPerson".equals(pro)){
								validateStr=FsscBudgetValidateUtil.validateProperty(null, pro, fdCode, "isExist");
							}else{
								validateStr=FsscBudgetValidateUtil.validateProperty(fdCompanyId, pro, fdCode, "isExist");
							}
							if(StringUtil.isNotNull(validateStr)){
								validateInfo.add(validateStr);
							}
							if(!ArrayUtil.isEmpty(validateInfo)){
								continue;
							}
							String hql="";
							List<Object[]> result=new ArrayList<Object[]>();
							String key=dict.getType()+"."+fdCode;  //map的key
							if(cacheMap.containsKey(key)){//则说明前面已经查过
								result=(List<Object[]>) cacheMap.get(key);
							}else{//未处理，重新查询
								if("fdPerson".equals(pro)||"fdDept".equals(pro)){//人员、部门单独处理
									hql="select model.fdId,model.fdName from "+dict.getType()+" model where model.fdNo=:fdCode and model.fdIsAvailable=:fdIsAvailable";
									result=this.getBaseDao().getHibernateSession().createQuery(hql)
											.setParameter("fdCode", fdCode)
											.setParameter("fdIsAvailable", true).list();
								}else{
									hql="select model.fdId,model.fdName from "+dict.getType()+" model left join model.fdCompanyList company  where model.fdCode=:fdCode and (company.fdId=:fdCompanyId or company is null)";
									if(SysDataDict.getInstance().getModel(dict.getType()).getPropertyMap().containsKey("fdIsAvailable")){
										hql+=" and model.fdIsAvailable=:fdIsAvailable";
									}
									Query query=this.getBaseDao().getHibernateSession().createQuery(hql)
											.setParameter("fdCode", fdCode)
											.setParameter("fdCompanyId", fdCompanyId);
									if(SysDataDict.getInstance().getModel(dict.getType()).getPropertyMap().containsKey("fdIsAvailable")){
										query.setParameter("fdIsAvailable", true);
									}
									result=query.list();
								}
								cacheMap.put(key, result);
							}
							if(!ArrayUtil.isEmpty(result)){
								Object[] obj=result.get(0);
								object.put(pro+"Id", obj[0]);
								object.put(pro+"Name", obj[1]);
								moneyKey+=obj[0];
							}
						}else{
							String value=ExcelUtil.getCellValue(row.getCell(n));
							if(pro.endsWith("Rule")&&"3".equals(value)){
								//首次出现弹性控制则赋值，以免本行后续覆盖该值
								rule="3";
							}
							//其他字段不做判断，若是空值，则不会生成预算数据，或者由页面必填校验控制
							if("fdElasticPercent".equals(pro)){
								if("3".equals(rule)){//年、季、月其中出现一次弹性规则，则需要校验弹性比例字段，其他不校验
									validateStr=FsscBudgetValidateUtil.validateProperty(fdCompanyId, pro, value, "isNull");
								}else if(StringUtil.isNotNull(value)){
									//没有弹性控制，且弹性有值
									validateInfo.add(ResourceUtil.getString("message.import.excel.elastic.percent", "fssc-budget").replace("{0}", String.valueOf(i)));
								}
							}
							n++;
							if(StringUtil.isNotNull(validateStr)&&!validateInfo.contains(validateStr)){
								validateInfo.add(validateStr);
							}
							if(!ArrayUtil.isEmpty(validateInfo)){
								continue;
							}
							moneyKey+=pro;
							if(budgetDataMap.containsKey(moneyKey)&&budgetDataMap.get(moneyKey)!=null){
								if(FsscNumberUtil.isNumber(value)&&pro.endsWith("Money")){
									object.put(pro, NumberUtil.roundDecimal(budgetDataMap.get(moneyKey), "###.00")+"@"); //根据数据库值赋值，拼接标识位@该字段设置为只读
								}else{
									object.put(pro, NumberUtil.roundDecimal(budgetDataMap.get(moneyKey), "###.00")+"@"); //根据数据库值赋值，拼接标识位@该字段设置为只读
								}
							}else{
								if(FsscNumberUtil.isNumber(value)&&pro.endsWith("Money")){
									value=NumberUtil.roundDecimal(value, "###.00");
								}
								object.put(pro, value); //正常获取到excel的值赋值
							}
							//还原key，只保留维度拼接部分
							moneyKey=moneyKey.replace(pro, "");
						}
					}else{
						n=n+2; //目前只有资产不确定，按对象处理
					}
				}
				if(!ArrayUtil.isEmpty(validateInfo)){
					Map map=new HashMap<>();
					map.put("index", i+1);
					map.put("error", validateInfo);
					validateInfoList.add(map);
				}
				detailJsonArray.add(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(templateError) {//模板不对
			request.setAttribute("templateErrorTips", templateErrorTips);
		}
		if(!ArrayUtil.isEmpty(validateInfoList)){
			request.setAttribute("validateInfoList", validateInfoList);
		}
		return detailJsonArray;
	}
	
	//获取系统默认生成的模板的列数
	public int getTemplateNum(String fdSchemeId) throws Exception{
		EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) this.findByPrimaryKey(fdSchemeId, EopBasedataBudgetScheme.class, true);
		String fdDimension=scheme.getFdDimension();  //维度，目前全市对象，excel对应的表头列数为2
		int templateNum=StringUtil.isNotNull(fdDimension)?fdDimension.split(";").length*2:0;
		if(FsscCommonUtil.isContain(fdDimension, "1;", ";")) { //排除公司和公司组维度，不需要导入
			templateNum-=2;
		}
		if(FsscCommonUtil.isContain(fdDimension, "2;", ";")) { //排除公司和公司组维度，不需要导入
			templateNum-=2;
		}
		String fdPeriod=scheme.getFdPeriod();
		if(FsscCommonUtil.isContain(fdPeriod, "1;", ";")||FsscCommonUtil.isContain(fdPeriod, "2;", ";")) { //不限、年，金额、规则等是4列
			templateNum+=4;
		}
		if(FsscCommonUtil.isContain(fdPeriod, "3;", ";")) { //季，增加金额4列，规则2列
			templateNum+=6;
		}
		if(FsscCommonUtil.isContain(fdPeriod, "4;", ";")) { //月，金额增加12列，规则2列
			templateNum+=14;
		}
		return templateNum;
	}

	/**
	 * 预算批量导入
	 * 
	 * @param importForm
	 * @return
	 * @throws Exception
	 */
	@Override
    @SuppressWarnings("unchecked")
	public JSONObject initSaveImport(FsscBudgetMainForm mainForm, HttpServletRequest request) throws Exception {
		JSONObject rtnObj = new JSONObject(); 
		List<Map<Integer,List<String>>> validateInfoList=new ArrayList<Map<Integer,List<String>>>();  //全部预算校验信息
		String validateStr="";  //单条错误信息接收
		boolean templateError=false;  //模板校验是否被删除列数
		String templateErrorTips="";  //模板错误提示
		FormFile file = mainForm.getFdFile();
		List<Map<String,FsscBudgetData>> dataList=new ArrayList<>();
		Map<String,FsscBudgetData> dataMap=new HashMap<>();
		Workbook wb = WorkbookFactory.create(file.getInputStream());
		Sheet sheet = wb.getSheetAt(0);
		int columnNum=sheet.getRow(0).getPhysicalNumberOfCells(); //获取模板表头列数
		if(columnNum-51!=0) {
			templateError=true;
			templateErrorTips=ResourceUtil.getString("message.budget.import.template.tips", "fssc-budget");
		}
		String budgetCurrencyErrorTips="";  //公共预算币种未设置提示
		if(StringUtil.isNull(EopBasedataFsscUtil.getSwitchValue("fdCommonBudgetCurrencyId"))) {
			budgetCurrencyErrorTips=ResourceUtil.getString("message.common.budget.currency.notSet", "eop-basedata");
		}
		String fdCurrencyId=EopBasedataFsscUtil.getSwitchValue("fdCommonBudgetCurrencyId");
    	EopBasedataCurrency currency=(EopBasedataCurrency) eopBasedataCompanyService.findByPrimaryKey(fdCurrencyId, EopBasedataCurrency.class, true);
		FsscBudgetData data=new FsscBudgetData();
		Map cacheMap=new HashMap<>();  //缓存对应的维度对象
		Map<String,FsscBudgetData> budgetMap=new HashMap<>();
		Map<String,Object> checkResultMap=new HashMap<>();
		Map<String,Integer> excelDataMap=new HashMap<>();  //缓存excel数据
		String budgetKey="";  //获取维度ID拼接的唯一键
		// 取到工作表里面的数据
		for (int i = 1,shLen=sheet.getLastRowNum(); i <=shLen ; i++) {
			Map temp=new HashMap<>();
			List<String> validateInfo=new ArrayList<String>();  //一行的校验信息
			Row row = sheet.getRow(i);
			// 判断是否空行
			if (ExcelUtil.isBlankRow(row)) {
				validateInfo.add(ResourceUtil.getString("message.import.excel.blank", "fssc-budget").replaceAll("{0}", String.valueOf(i)));
				continue;
			}
			String[] propertys={"fsscBudgetScheme","fdCompanyGroup","fdCompany","fdCostCenterGroup","fdCostCenter","fdProject","fdWbs","fdInnerOrder","fdBudgetItem","fdPerson","fdDept"};
			String[] modelName={"EopBasedataBudgetScheme","EopBasedataCompanyGroup","EopBasedataCompany","EopBasedataCostCenterGroup","EopBasedataCostCenter","EopBasedataProject","EopBasedataWbs","EopBasedataInnerOrder","EopBasedataBudgetItem","SysOrgElement","SysOrgElement"};
			String fdSchemeCode=ExcelUtil.getCellValue(row.getCell(1));  //预算方案编码
			if(cacheMap.containsKey("scheme"+fdSchemeCode)){//防止出现不同的对象编码一样导致对象取值错乱
				temp.put("fdBudgetScheme", cacheMap.get("scheme"+fdSchemeCode));
			}else{
				EopBasedataBudgetScheme scheme=eopBasedataBudgetSchemeService.findBudgetSchemeByCode(fdSchemeCode);
				if(scheme!=null){
					cacheMap.put("scheme"+fdSchemeCode, scheme);
					temp.put("fdBudgetScheme", scheme);
				}else{
					validateInfo.add(ResourceUtil.getString("message.import.excel.available", "fssc-budget")
							.replaceAll("\\{title\\}", ResourceUtil.getString("fsscBudgetData.fdBudgetScheme", "fssc-budget"))
							.replaceAll("\\{value\\}", fdSchemeCode)); //公司编码错误或者无效
				}
			}
			//校验对应维度是否存在
			for(int k=1,len=propertys.length;k<len;k++){
				String pro=propertys[k];
				String fdCode=ExcelUtil.getCellValue(row.getCell(2*k+1));  //获取编号，
				if(StringUtil.isNull(fdCode)){
					continue;
				}
				if(!cacheMap.containsKey(pro+fdCode)){
					if("fdDept".equals(pro)||"fdPerson".equals(pro)){
						checkResultMap=FsscBudgetValidateUtil.validatePropertys("org", pro, fdCode, "isExist");
					}else{
						checkResultMap=FsscBudgetValidateUtil.validatePropertys("basedata", pro, fdCode, "isExist");
					}
					validateStr=String.valueOf(checkResultMap.get("info"));
					if(checkResultMap.containsKey("object")&&checkResultMap.get("object")!=null){
						cacheMap.put(pro+fdCode, checkResultMap.get("object"));
					}
				}
				if(StringUtil.isNotNull(validateStr)&&!validateInfo.contains(validateStr)){
					validateInfo.add(validateStr);
				}
				if(!ArrayUtil.isEmpty(validateInfo)){
					continue;
				}
				String hql="";
				List<Object> result=new ArrayList<Object>();
				String key=pro+fdCode;  //map的key
				if(cacheMap.containsKey(key)){//则说明前面已经查过
					temp.put(pro, cacheMap.get(key));
				}else{//未处理，重新查询
					if("fdPerson".equals(pro)||"fdDept".equals(pro)){//人员、部门单独处理
						hql="select model from com.landray.kmss.sys.organization.model."+modelName[k]+" model where model.fdNo=:fdCode and model.fdIsAvailable=:fdIsAvailable";
						result=this.getBaseDao().getHibernateSession().createQuery(hql)
								.setParameter("fdCode", fdCode)
								.setParameter("fdIsAvailable", true).list();
					}else{
						hql="select model from com.landray.kmss.eop.basedata.model."+modelName[k]+" model where model.fdCode=:fdCode";
						if(SysDataDict.getInstance().getModel("com.landray.kmss.eop.basedata.model."+modelName[k]).getPropertyMap().containsKey("fdIsAvailable")){
							hql+=" and model.fdIsAvailable=:fdIsAvailable";
						}
						Query query=this.getBaseDao().getHibernateSession().createQuery(hql)
								.setParameter("fdCode", fdCode);
						if(SysDataDict.getInstance().getModel("com.landray.kmss.eop.basedata.model."+modelName[k]).getPropertyMap().containsKey("fdIsAvailable")){
							query.setParameter("fdIsAvailable", true);
						}
						result=query.list();
						if(!ArrayUtil.isEmpty(result)){
							temp.put(pro, result.get(0));
							cacheMap.put(key, result.get(0));
						}
					}
				}
			}
			if(ArrayUtil.isEmpty(validateInfo)){
				if(temp.containsKey("fdBudgetScheme")&&!FsscCommonUtil.isContain(((EopBasedataBudgetScheme)temp.get("fdBudgetScheme")).getFdDimension(), "2;", ";")) {
					temp.put("currency", currency);
				}
				String fdType= ExcelUtil.getCellValue(row.getCell(50));  //调整还是新增
				String fdYear=ExcelUtil.getCellValue(row.getCell(22));  //年份
				if(StringUtil.isNull(fdYear)){//无年份，维度为不限
					String fdMoney=ExcelUtil.getCellValue(row.getCell(23));  //不限，预算金额
					if(StringUtil.isNotNull(fdMoney)){
						data=new FsscBudgetData();
						budgetKey=getBudgetKey(row);
						if(!budgetMap.containsKey(budgetKey)){
							budgetMap=getBudgetDataMap(row);  //第一次或者维度已经变化的，重新查找
						}
						if("1".equals(fdType)){//更新
							if(budgetMap.containsKey(budgetKey)){//前面维度已经缓存
								data=budgetMap.get(budgetKey);
							}else{
								// 调整未找到对应的预算
								validateStr=ResourceUtil.getString("message.import.excel.adjust.error", "fssc-budget").replace("{0}", String.valueOf(i+1))
										.replace("{1}", ResourceUtil.getString("message.budget.init.dy", "fssc-budget"));
								if(StringUtil.isNotNull(validateStr)){
									validateInfo.add(validateStr);
								}
							}
						}else{//新增，默认未结转
							data.setFdIsKnots(FsscBudgetConstant.FSSC_BUDGET_TRANSFER_NO);
							data.setFdMoney(Double.parseDouble(fdMoney));
							if(excelDataMap.containsKey(budgetKey)){
								// 前面excel出现重复数据
								validateStr=ResourceUtil.getString("message.budget.init.excel.have", "fssc-budget").replace("{0}", String.valueOf(i+1))
										.replace("{1}", String.valueOf(excelDataMap.get(budgetKey)));
								if(StringUtil.isNotNull(validateStr)&&!validateInfo.contains(validateStr)){
									validateInfo.add(validateStr);
								}
							}else{
								excelDataMap.put(budgetKey, i);
							}
							if(budgetMap.containsKey(budgetKey)){
								// 本次新增数据数据库已存在
								validateStr=ResourceUtil.getString("message.budget.init.db.have", "fssc-budget").replace("{0}", String.valueOf(i+1));
								if(StringUtil.isNotNull(validateStr)&&!validateInfo.contains(validateStr)){
									validateInfo.add(validateStr);
								}
							}
						}
						data=setFsscBudgetData(data,temp,propertys);
						String value=ExcelUtil.getCellValue(row.getCell(44));  //控制规则
						data.setFdRule(StringUtil.isNotNull(value)?value:null);
						value=ExcelUtil.getCellValue(row.getCell(48));  //运用规则
						data.setFdApply(StringUtil.isNotNull(value)?value:null);
						value=ExcelUtil.getCellValue(row.getCell(49));  //弹性比例
						data.setFdElasticPercent(StringUtil.isNotNull(value)?Double.parseDouble(value):null);
						dataMap=new HashMap<>();
						dataMap.put(fdType+";"+fdMoney, data);
						dataList.add(dataMap);
						if(!ArrayUtil.isEmpty(validateInfo)){
							Map map=new HashMap<>();
							map.put("index", i+1);
							map.put("error", validateInfo);
							validateInfoList.add(map);
						}
					}
				}else{//非不限
					fdYear="5"+fdYear+"0000";
					String fdMoney=ExcelUtil.getCellValue(row.getCell(24));  //年度预算额
					if(StringUtil.isNotNull(fdMoney)){
						data=new FsscBudgetData();
						budgetKey=getBudgetKey(row);
						budgetKey+=fdYear+"5";
						if(!budgetMap.containsKey(budgetKey)){
							budgetMap=getBudgetDataMap(row);  //第一次或者维度已经变化的，重新查找
						}
						if("1".equals(fdType)){//更新
							if(budgetMap.containsKey(budgetKey)){//前面维度已经缓存
								data=budgetMap.get(budgetKey);
							}else{
								// 调整未找到对应的预算
								validateInfo.add(ResourceUtil.getString("message.import.excel.adjust.error", "fssc-budget").replace("{0}", String.valueOf(i+1))
										.replace("{1}", ResourceUtil.getString("fsscBudgetData.fdYear", "fssc-budget")));
							}
						}else{//新增，默认未结转
							data.setFdIsKnots(FsscBudgetConstant.FSSC_BUDGET_TRANSFER_NO);
							data.setFdMoney(Double.parseDouble(fdMoney));
							if(excelDataMap.containsKey(budgetKey)){
								// 前面excel出现重复数据
								validateStr=ResourceUtil.getString("message.budget.init.excel.have", "fssc-budget").replace("{0}", String.valueOf(i+1))
										.replace("{1}", String.valueOf(excelDataMap.get(budgetKey)));
								if(StringUtil.isNotNull(validateStr)&&!validateInfo.contains(validateStr)){
									validateInfo.add(validateStr);
								}
							}else{
								excelDataMap.put(budgetKey, i);
							}
							if(budgetMap.containsKey(budgetKey)){
								// 本次新增数据数据库已存在
								validateStr=ResourceUtil.getString("message.budget.init.db.have", "fssc-budget").replace("{0}", String.valueOf(i+1));
								if(StringUtil.isNotNull(validateStr)&&!validateInfo.contains(validateStr)){
									validateInfo.add(validateStr);
								}
							}
						}
						data.setFdYear(fdYear);
						data.setFdPeriodType(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_YEAR);
						data=setFsscBudgetData(data,temp,propertys);
						String value=ExcelUtil.getCellValue(row.getCell(41));  //控制规则
						data.setFdRule(StringUtil.isNotNull(value)?value:null);
						value=ExcelUtil.getCellValue(row.getCell(45));  //运用规则
						data.setFdApply(StringUtil.isNotNull(value)?value:null);
						value=ExcelUtil.getCellValue(row.getCell(49));  //弹性比例
						data.setFdElasticPercent(StringUtil.isNotNull(value)?Double.parseDouble(value):null);
						dataMap=new HashMap<>();
						dataMap.put(fdType+";"+fdMoney, data);
						dataList.add(dataMap);
					}
					for(int n=0;n<4;n++){
						fdMoney=ExcelUtil.getCellValue(row.getCell(25+n*4));  //季度度预算额
						if(StringUtil.isNotNull(fdMoney)){
							data=new FsscBudgetData();
							budgetKey=getBudgetKey(row);
							budgetKey+=fdYear+"3"+"0"+n;
							if(!budgetMap.containsKey(budgetKey)){
								budgetMap=getBudgetDataMap(row);  //第一次或者维度已经变化的，重新查找
							}
							if("1".equals(fdType)){//更新
								if(budgetMap.containsKey(budgetKey)){//前面维度已经缓存
									data=budgetMap.get(budgetKey);
								}else{
									// 调整未找到对应的预算
									validateInfo.add(ResourceUtil.getString("message.import.excel.adjust.error", "fssc-budget").replace("{0}", String.valueOf(i+1))
											.replace("{1}", (n+1)+ResourceUtil.getString("fsscBudgetData.fdQuarter", "fssc-budget")));
									continue;
								}
							}else{//新增，默认未结转
								data.setFdIsKnots(FsscBudgetConstant.FSSC_BUDGET_TRANSFER_NO);
								data.setFdMoney(Double.parseDouble(fdMoney));	
								if(excelDataMap.containsKey(budgetKey)){
									// 前面excel出现重复数据
									validateStr=ResourceUtil.getString("message.budget.init.excel.have", "fssc-budget").replace("{0}", String.valueOf(i+1))
											.replace("{1}", String.valueOf(excelDataMap.get(budgetKey)));
									if(StringUtil.isNotNull(validateStr)&&!validateInfo.contains(validateStr)){
										validateInfo.add(validateStr);
									}
								}else{
									excelDataMap.put(budgetKey, i);
								}
								if(budgetMap.containsKey(budgetKey)){
									// 本次新增数据数据库已存在
									validateStr=ResourceUtil.getString("message.budget.init.db.have", "fssc-budget").replace("{0}", String.valueOf(i+1));
									if(StringUtil.isNotNull(validateStr)&&!validateInfo.contains(validateStr)){
										validateInfo.add(validateStr);
									}
								}
							}
							data.setFdYear(fdYear);
							data.setFdPeriodType(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_QUARTER);
							data.setFdPeriod("0"+n);
							data=setFsscBudgetData(data,temp,propertys);
							String value=ExcelUtil.getCellValue(row.getCell(42));  //控制规则
							data.setFdRule(StringUtil.isNotNull(value)?value:null);
							value=ExcelUtil.getCellValue(row.getCell(46));  //运用规则
							data.setFdApply(StringUtil.isNotNull(value)?value:null);
							value=ExcelUtil.getCellValue(row.getCell(49));  //弹性比例
							data.setFdElasticPercent(StringUtil.isNotNull(value)?Double.parseDouble(value):null);
							dataMap=new HashMap<>();
							dataMap.put(fdType+";"+fdMoney, data);
							dataList.add(dataMap);
						}
					}
					int k=0;
					for(int n=0;n<15;n++){
						if((n+1)%4==0){
							k++;
							continue;
						}
						fdMoney=ExcelUtil.getCellValue(row.getCell(26+n));  //月度度预算额
						if(StringUtil.isNotNull(fdMoney)){
							data=new FsscBudgetData();
							budgetKey=getBudgetKey(row);
							budgetKey+=fdYear+"1"+((n-k)>9?String.valueOf((n-k)):"0"+(n-k));
							if(!budgetMap.containsKey(budgetKey)){
								budgetMap=getBudgetDataMap(row);  //第一次或者维度已经变化的，重新查找
							}
							if("1".equals(fdType)){//更新
								if(budgetMap.containsKey(budgetKey)){//前面维度已经缓存
									data=budgetMap.get(budgetKey);
								}else{
									// 调整未找到对应的预算
									validateInfo.add(ResourceUtil.getString("message.import.excel.adjust.error", "fssc-budget").replace("{0}", String.valueOf(i+1))
											.replace("{1}",((n-k+1))+ ResourceUtil.getString("enums.budget.period.type.month", "fssc-budget")));
									continue;
								}
							}else{//新增，默认未结转
								data.setFdIsKnots(FsscBudgetConstant.FSSC_BUDGET_TRANSFER_NO);
								data.setFdMoney(Double.parseDouble(fdMoney));
								if(excelDataMap.containsKey(budgetKey)){
									// 前面excel出现重复数据
									validateStr=ResourceUtil.getString("message.budget.init.excel.have", "fssc-budget").replace("{0}", String.valueOf(i+1))
											.replace("{1}", String.valueOf(excelDataMap.get(budgetKey)));
									if(StringUtil.isNotNull(validateStr)&&!validateInfo.contains(validateStr)){
										validateInfo.add(validateStr);
									}
								}else{
									excelDataMap.put(budgetKey, i);
								}
								if(budgetMap.containsKey(budgetKey)){
									// 本次新增数据数据库已存在
									validateStr=ResourceUtil.getString("message.budget.init.db.have", "fssc-budget").replace("{0}", String.valueOf(i+1));
									if(StringUtil.isNotNull(validateStr)&&!validateInfo.contains(validateStr)){
										validateInfo.add(validateStr);
									}
								}
							}
							data=setFsscBudgetData(data,temp,propertys);
							data.setFdYear(fdYear);
							data.setFdPeriodType(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_MONTH);
							data.setFdPeriod((n-k)>9?String.valueOf((n-k)):"0"+(n-k));
							String value=ExcelUtil.getCellValue(row.getCell(43));  //控制规则
							data.setFdRule(StringUtil.isNotNull(value)?value:null);
							value=ExcelUtil.getCellValue(row.getCell(47));  //运用规则
							data.setFdApply(StringUtil.isNotNull(value)?value:null);
							value=ExcelUtil.getCellValue(row.getCell(49));  //弹性比例
							data.setFdElasticPercent(StringUtil.isNotNull(value)?Double.parseDouble(value):null);
							dataMap=new HashMap<>();
							dataMap.put(fdType+";"+fdMoney, data);
							dataList.add(dataMap);
						}
					}
					if(!ArrayUtil.isEmpty(validateInfo)){
						Map map=new HashMap<>();
						map.put("index", i+1);
						map.put("error", validateInfo);
						validateInfoList.add(map);
					}
				}
			}else{
				Map map=new HashMap<>();
				map.put("index", i+1);
				map.put("error", validateInfo);
				validateInfoList.add(map);
			}
		}
		if(!ArrayUtil.isEmpty(validateInfoList)){//校验未通过
			request.setAttribute("validateInfoList", validateInfoList);
			rtnObj.put("result", "failure"); 
		}else if(templateError) {//模板不对
			request.setAttribute("templateErrorTips", templateErrorTips);
		}else if(StringUtil.isNotNull(budgetCurrencyErrorTips)) {//公共预算币种未设置
			request.setAttribute("budgetCurrencyErrorTips", budgetCurrencyErrorTips);
		}else{
			//校验通过
			for(Map<String,FsscBudgetData> map:dataList){
				Iterator<String> iter = map.keySet().iterator();
				  while(iter.hasNext()){
				   String key=iter.next();
				   FsscBudgetData fsscBudgetData=map.get(key);
				   if("1".equals(key.split(";")[0])){//调整
					   Double adjustMoney=key.split(";").length>1?Double.parseDouble(key.split(";")[1]):0.0;
					   addExecuteAndLog(fsscBudgetData,adjustMoney);
				   }else{//新增
					   addInitExecuteData(fsscBudgetData);
				   }
				   fsscBudgetDataService.getBaseDao().getHibernateSession().saveOrUpdate(fsscBudgetData);
				  }
			}
			rtnObj.put("result", "success");
		}
		return rtnObj;
	}
	
	//增加调整金额和调整日志
	public  void addExecuteAndLog(FsscBudgetData fsscBudgetData, Double adjustMoney) throws Exception{
		JSONObject executeJson=new JSONObject();
		executeJson.put("fdModelId", fsscBudgetData.getFdId());
		executeJson.put("fdModelName", ModelUtil.getModelClassName(fsscBudgetData));
		executeJson.put("fdBudgetId", fsscBudgetData.getFdId());
		executeJson.put("fdType", FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST);
		executeJson.put("fdDetailId", fsscBudgetData.getFdId());
		executeJson.put("fdMoney", adjustMoney); //调整金额
		executeJson.put("noCompany", "true");
		Map<String, SysDictCommonProperty> dictMap = SysDataDict.getInstance().getModel(FsscBudgetData.class.getName()).getPropertyMap();
		String[] propertys={"fsscBudgetScheme","fdCompanyGroup","fdCompany","fdCostCenterGroup","fdCostCenter","fdProject","fdWbs","fdInnerOrder","fdBudgetItem","fdPerson","fdDept"};
		List<String> proList=ArrayUtil.convertArrayToList(propertys);
		for (String key : dictMap.keySet()) {
			SysDictCommonProperty dict = dictMap.get(key);
			if(dict.getType().startsWith("com.landray.kmss.")&&proList.contains(dict.getName())){
				String property=dict.getName();
				Object obj=PropertyUtils.getProperty(fsscBudgetData, property);
				if(obj!=null){
					executeJson.put(property+"Id", PropertyUtils.getProperty(obj, "fdId"));
					if("fdDept".equals(property)||"fdPerson".equals(property)){
						executeJson.put(property+"Code", PropertyUtils.getProperty(obj, "fdNo"));
					}else{
						executeJson.put(property+"Code", PropertyUtils.getProperty(obj, "fdCode"));
					}
				}
			}
         }
		//删除前面的调整执行记录
		HQLInfo hqlInfo =new HQLInfo();
		hqlInfo.setWhereBlock("fsscBudgetExecute.fdBudgetId=:fdBudgetId and fsscBudgetExecute.fdType=:fdType");
		hqlInfo.setParameter("fdBudgetId", fsscBudgetData.getFdId());
		hqlInfo.setParameter("fdType", FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST);
		fsscBudgetExecuteService.addFsscBudgetExecute(executeJson);
		executeJson=new JSONObject();
		executeJson.put("fdModelId", fsscBudgetData.getFdId()); //预算调整ID
		executeJson.put("fdModelName", FsscBudgetData.class.getName()); //预算调整modelName
		executeJson.put("fdBudgetId", fsscBudgetData.getFdId()); //预算ID
		executeJson.put("fdMoney", adjustMoney);
		executeJson.put("fdPersonId", UserUtil.getUser().getFdId());
		executeJson.put("fdDesc", ResourceUtil.getString("message.budget.init.desc", "fssc-budget"));
		//create删除是没数据的没正常情况下，update先删除历史占用的，插入新的占用计算，publish和update类似
		fsscBudgetAdjustLogService.addAdjust(executeJson);
	}

	public String getBudgetKey(Row row)throws Exception{
		String budgetKey="";
		for(int n=0;n<11;n++){
			budgetKey=budgetKey+ExcelUtil.getCellValue(row.getCell(2*n+1));
		}
		return budgetKey;
	}
	
	public Map<String,FsscBudgetData> getBudgetDataMap(Row row) throws Exception{
		Map<String,FsscBudgetData> rtnMap=new HashMap<>();
		String budgetKey="";
		HQLInfo hqlInfo=new HQLInfo();
		String[] propertys={"fdBudgetScheme","fdCompanyGroup","fdCompany","fdCostCenterGroup","fdCostCenter","fdProject","fdWbs","fdInnerOrder","fdBudgetItem","fdPerson","fdDept"};
		StringBuilder hql=new StringBuilder();
		for(int k=0,len=propertys.length;k<len;k++){
			String pro=propertys[k];
			String value=ExcelUtil.getCellValue(row.getCell(2*k+1));
			if(k>0){
				hql.append(" and ");
			}
			if(StringUtil.isNotNull(value)){
				if("fdPerson".equals(pro)||"fdDept".equals(pro)){
					hql.append("fsscBudgetData."+pro+".fdNo=:"+pro+"Code");
				}else{
					hql.append("fsscBudgetData."+pro+".fdCode=:"+pro+"Code");
				}
			}else{
				hql.append("fsscBudgetData."+pro+" is null");
			}
			if(StringUtil.isNotNull(value)){
				hqlInfo.setParameter(propertys[k]+"Code", ExcelUtil.getCellValue(row.getCell(2*k+1)));
			}
		}
		hqlInfo.setWhereBlock(hql.toString());
		List<FsscBudgetData> dataList=fsscBudgetDataService.findList(hqlInfo);
		for(FsscBudgetData data:dataList){
			budgetKey="";
			for(int k=0,len=propertys.length;k<len;k++){
				Object obj=PropertyUtils.getProperty(data, propertys[k]);
				if(obj!=null){
					Object code=null;
					if("fdPerson".equals(propertys[k])||"fdDept".equals(propertys[k])){
						code=PropertyUtils.getProperty(obj, "fdNo");
						
					}else{
						code=PropertyUtils.getProperty(obj, "fdCode");
					}
					if(code!=null){
						budgetKey+=String.valueOf(code);
					}
				}
			}
			String fdYear=data.getFdYear();
			if(StringUtil.isNotNull(fdYear)){
				budgetKey+=fdYear;
			}
			String fdPeriodType=data.getFdPeriodType();
			if(StringUtil.isNotNull(fdPeriodType)){
				budgetKey+=fdPeriodType;
			}
			String fdPeriod=data.getFdPeriod();
			if(StringUtil.isNotNull(fdPeriod)){
				budgetKey+=fdPeriod;
			}
			rtnMap.put(budgetKey, data);
		}
		return rtnMap;
	}
	
	public FsscBudgetData setFsscBudgetData(FsscBudgetData data,Map temp,String[] propertys) throws Exception{
		for(String pro:propertys){
			Object obj=temp.containsKey(pro)?temp.get(pro):null;
			if(obj!=null){
				PropertyUtils.setProperty(data, pro, obj);
				String code=null;
				if("fdPerson".equals(pro)||"fdDept".equals(pro)){
					code=String.valueOf(PropertyUtils.getProperty(obj, "fdNo"));
				}else{
					code=String.valueOf(PropertyUtils.getProperty(obj, "fdCode"));
				}
				PropertyUtils.setProperty(data, pro+"Code", code);
			}
		}
		Object obj=temp.containsKey("fdBudgetScheme")?temp.get("fdBudgetScheme"):null;
		if(obj!=null){
			EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) obj;
			data.setFdBudgetScheme(scheme);
			data.setFdBudgetSchemeCode(scheme.getFdCode());
		}
		if(temp.containsKey("currency")&&temp.get("currency")!=null) {
			data.setFdCurrency((EopBasedataCurrency)temp.get("currency"));
		}else if(temp.containsKey("fdCompany")&&temp.get("fdCompany")!=null){
			EopBasedataCompany company=(EopBasedataCompany) temp.get("fdCompany");
			data.setFdCurrency(company.getFdBudgetCurrency());
		}
		data.setFdBudgetStatus(FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE);
		data.setDocCreateTime(new Date());
		data.setDocCreator(UserUtil.getUser());
		return data;
	}

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		FsscBudgetMain main=(FsscBudgetMain) modelObj;
		if(DateUtil.convertDateToString(new Date(),DateUtil.PATTERN_DATE).equals(
				DateUtil.convertDateToString(main.getFdEnableDate(),DateUtil.PATTERN_DATE))){
			addBudgetData(main);  //生成预算数据和初始化执行数据
		}
		return super.add(main);
	}
	
	/*****************************************************
	 * 将新建的预算转为预算数据
	 * ****************************************************/
	@Override
    public void addBudgetData(FsscBudgetMain main) throws Exception{
 		List<FsscBudgetDetail> fdDetailList=main.getFdDetailList();
		EopBasedataBudgetScheme eopBasedataBudgetScheme=(EopBasedataBudgetScheme) eopBasedataBudgetSchemeService
				.findByPrimaryKey(main.getFdBudgetScheme()!=null?main.getFdBudgetScheme().getFdId():"", null, true);
		if(eopBasedataBudgetScheme!=null){
			List<Map<String,String>>  propertyList=FsscBudgetParseXmlUtil.getImportProperty();
			String fdDimensions=";"+eopBasedataBudgetScheme.getFdDimension();
			String fdPeriods=eopBasedataBudgetScheme.getFdPeriod();
			FsscBudgetData budgetData=null;
			//循环前先找出对应的维度和期间，减少三重循环次数
			List<Map<String,String>> dimensList=new ArrayList<Map<String,String>>();
			List<Map<String,String>> peroidList=new ArrayList<Map<String,String>>();
			for(Map<String,String> map:propertyList){
				if(map.containsKey("period")&&FsscCommonUtil.isContain(fdPeriods, map.get("period")+";", ";")){
					if(!map.get("property").endsWith("Rule")&&!map.get("property").endsWith("Apply")){//控制规则和运用规则排除
						peroidList.add(map);
					}
				}else if(map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";")){
					dimensList.add(map);
				}
			}
			dimensList.addAll(FsscBudgetParseXmlUtil.parseCompanyOrCompanyGroup(fdDimensions));
			if(FsscCommonUtil.isContain(fdDimensions, "1;", ";")){//说明维度中包含公司组，给detail设置公司组的值
				main.setFdCompanyGroup(main.getFdCompany()!=null?main.getFdCompany().getFdGroup():null);
			}
			String key="";  //缓存当前对象的的key唯一键，重复覆盖
			Map<String,FsscBudgetData> dataCache=new HashMap<String,FsscBudgetData>();
			for (FsscBudgetDetail detail : fdDetailList) {
				for(Map<String,String> map:peroidList){//一年、一个季度、一个月各生成一条预算记录
					key="";
					String property=map.get("property");
					Double fdMoney=(Double)PropertyUtils.getProperty(detail, property);
					if(fdMoney==null){
						continue;
					}
					budgetData=getExistBudgetData(main.getFdYear(),detail,dimensList,map,main.getFdBudgetScheme().getFdId());
					if(budgetData==null){
						budgetData=new FsscBudgetData();
					}else{
						//预算数据不为空，则说明预算已经导入，直接跳过，需走预算调整或者追加
						continue;
					}
					if(!"1".equals(fdPeriods)){//非不限，设置年份
						budgetData.setFdYear(main.getFdYear());
						key+=main.getFdYear();
					}
					budgetData.setFdBudgetStatus(FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE);
					if(FsscCommonUtil.isContain(fdDimensions, "2;", ";")){//预算方案维度包含公司
						budgetData.setFdCompany(main.getFdCompany());
						budgetData.setFdCompanyCode(main.getFdCompany()!=null?main.getFdCompany().getFdCode():null);
						key+=main.getFdCompany()!=null?main.getFdCompany().getFdCode():"";
					}
					if(FsscCommonUtil.isContain(fdDimensions, "1;", ";")){//预算方案维度包含公司组
						budgetData.setFdCompanyGroup(main.getFdCompanyGroup());
						budgetData.setFdCompanyGroupCode(main.getFdCompanyGroup()!=null?main.getFdCompanyGroup().getFdCode():null);
						key+=main.getFdCompanyGroup()!=null?main.getFdCompanyGroup().getFdCode():"";
					}
					budgetData.setFdCurrency(main.getFdCurrency());
					budgetData.setFdPeriod(map.get("fdPeriod"));
					key+=map.get("fdPeriod");
					budgetData.setFdPeriodType(map.get("fdPeriodType"));
					key+=map.get("fdPeriodType");
					budgetData.setFdMoney(fdMoney);
					String rule=String.valueOf(PropertyUtils.getProperty(detail, map.get("rule")));
					budgetData.setFdRule(rule);
					budgetData.setFdApply(String.valueOf(PropertyUtils.getProperty(detail, map.get("apply"))));
					if(StringUtil.isNotNull(rule)&&"3".equals(rule)){//控制规则为弹性
						budgetData.setFdElasticPercent(detail.getFdElasticPercent());
					}
					budgetData.setFdBudgetScheme(main.getFdBudgetScheme());
					budgetData.setFdBudgetSchemeCode(main.getFdBudgetScheme()!=null?main.getFdBudgetScheme().getFdCode():null);
					key+=main.getFdBudgetScheme()!=null?main.getFdBudgetScheme().getFdCode():"";
					budgetData.setFdIsKnots(FsscBudgetConstant.FSSC_BUDGET_TRANSFER_NO);  //默认初始化为未结转
					
					for(Map<String,String> diMap:dimensList){
						String diProperty=diMap.get("property");
						if(diMap.containsKey("type")&&"model".equals(diMap.get("type"))){
							if("fdAsset".equals(diProperty)){//资产排除掉，后续增加直接删除该判断即可
								continue;
							}
							Object obj=null;
							if("fdCompany".equals(diProperty)){
								obj=PropertyUtils.getProperty(detail.getDocMain(), diProperty);
							}else if("fdCompanyGroup".equals(diProperty)) {
								Object object=PropertyUtils.getProperty(detail.getDocMain(), "fdCompany");
								if(object!=null) {
									obj=PropertyUtils.getProperty(object, "fdGroup");
								}
							}else{
								obj=PropertyUtils.getProperty(detail, diProperty);
							}
							if(null==obj) {
								continue;
							}
							PropertyUtils.setProperty(budgetData, diProperty, obj);//维度的保持一致，直接获取值
							if("fdPerson".equals(diProperty)||"fdDept".equals(diProperty)){//人员单独处理
								PropertyUtils.setProperty(budgetData, diProperty+"Code", PropertyUtils.getProperty(obj, "fdNo"));//设置编号
								key+=PropertyUtils.getProperty(obj, "fdNo");
							}else{
								PropertyUtils.setProperty(budgetData, diProperty+"Code", PropertyUtils.getProperty(obj, "fdCode"));//设置编号
								key+=PropertyUtils.getProperty(obj, "fdCode");
							}
						}
					}
					budgetData.setDocCreator(main.getDocCreator());
					budgetData.setDocCreateTime(new Date());
					dataCache.put(key,budgetData);  //key值一致直接覆盖
				}
				detail.setFdIsEnable("1");
				fsscBudgetDetailService.getBaseDao().update(detail);
			}
			for(Entry<String, FsscBudgetData> entry : dataCache.entrySet()){
			    String keys = entry.getKey();
				FsscBudgetData data=(FsscBudgetData) dataCache.get(keys);
				fsscBudgetDataService.getBaseDao().add(data);
				addInitExecuteData(data);
			}
		}
	}
	/***
	 * 预算结转，按照年度，每个公司分月度预算结转、季度预算结转、年度预算结转
	 * ***/
	@Override
	public void updateTransferBudget(String startMonth,String endMonth,String fdCompanyIds) throws Exception {
		try {
			if(StringUtil.isNotNull(startMonth)&&StringUtil.isNotNull(endMonth)){
				String[] ids={};
				if(StringUtil.isNotNull(fdCompanyIds)){
					ids=fdCompanyIds.split(";");
				}
				String sYear=startMonth.substring(0, 4);  //对应需结转年份
				String sMonth=startMonth.substring(4, 6);  //对应需结转月份
				 
				String eYear=endMonth.substring(0, 4);  //对应结转到的年份
				endMonth.substring(0, 4);  
				String eMonth=endMonth.substring(4, 6);  //对应结转到的月份
				String sQuare="";  //对应需结转季度
				String eQuare=""; //对应结转到的季度
				switch(sMonth){
					case "02":
					sQuare="00";	
					eQuare="01";
					break;
					case "05":
					sQuare="01";	
					eQuare="02";
					break;
					case "08":
					sQuare="02";	
					eQuare="03";
					break;
					case "11":
					sQuare="03";	
					eQuare="00";
					break;
				}
				int size=ids.length;
				if(size==0){//没有选择公司
					size=1;
				}
				String fdCompanyId="";
				for (int n=0;n<size;n++) {
					/*************************预算结转开始***************************************/
					//获取结转月和下个月对应的预算Id
					if(ids.length>0){
						fdCompanyId=ids[n];
					}
					Map monthMap=getMonthBdgetData(sYear,sMonth,eYear,eMonth,sQuare,eQuare,fdCompanyId);
					if(monthMap.size()==1){//说明只有一个preIds空的list，未找到下个月预算，不做处理
						continue;
					}
					//获取对应的结转月需结转的金额（占用+可使用）
					Map monthCanUse=getBudgetCanUseMoney((List)monthMap.get("preIds"));
					Iterator it=monthMap.entrySet().iterator();
					int index=0;
					while(it.hasNext()){
						Entry entry=(Entry) it.next();
						String key=String.valueOf(entry.getKey());
						if("preIds".equals(key)){
							continue;
						}
						Map nextBudgetMap=(Map) monthMap.get(key);
						if(!nextBudgetMap.containsKey("fdId")){
							continue;
						}
						//结转处理，1、下月的预算新增fdType为4的预算执行记录；2、新增调整记录；3、更新对应的预算占用；4、更新预算为已结转及对应的结转金额
						String sql="insert into fssc_budget_execute (fd_id,fd_model_id,fd_model_name,fd_money,fd_type,fd_budget_id,"
								+"fd_detail_id,fd_company_group_id,fd_company_id,fd_cost_center_id,fd_budget_item_id,fd_project_id,fd_inner_order_id,fd_wbs_id,"
								+"fd_person_id,fd_company_group_code,fd_company_code,fd_cost_center_group_id,fd_cost_center_code,fd_cost_center_group_code,"
								+"fd_budget_item_code,fd_project_code,fd_inner_order_code,fd_wbs_code,fd_person_code,fd_currency)"
								+"  values(:fdId,:fdModelId,:fdModelName,:fdMoney,:fdType,:fdBudgetId,"
								+":fdDetailId,:fdCompanyGroupId,:fdCompanyId,:fdCostCenterId,:fdBudgetItemId,:fdProjectId,:fdInnerOrderId,:fdWbsId,"
								+":fdPersonId,:fdCompanyGroupCode,:fdCompanyCode,:fdCostCenterGroupId,:fdCostCenterCode,:fdCostCenterGroupCode,"
								+":fdBudgetItemCode,:fdProjectCode,:fdInnerOrderCode,:fdWbsCode,:fdPersonCode,:fdCurrencyId);";
						NativeQuery query=this.getBaseDao().getHibernateSession().createNativeQuery(sql)
						.setParameter("fdId", IDGenerator.generateID())
						.setParameter("fdModelId", nextBudgetMap.get("fdId"))
						.setParameter("fdModelName", FsscBudgetData.class.getName())
						.setParameter("fdMoney", monthCanUse.get(key))
						.setParameter("fdType", FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST)
						.setParameter("fdBudgetId", nextBudgetMap.get("fdId"))
						.setParameter("fdDetailId", null);
						String[] property={"fdCompanyGroupId","fdCompanyId","fdCostCenterId","fdBudgetItemId","fdProjectId",
								"fdInnerOrderId","fdWbsId","fdPersonId","fdCompanyGroupCode","fdCompanyCode","fdCostCenterGroupId","fdCostCenterCode",
								"fdCostCenterGroupCode","fdBudgetItemCode","fdProjectCode","fdInnerOrderCode","fdWbsCode","fdPersonCode","fdCurrencyId"};
						for(int k=0;k<property.length;k++){
							query.setParameter(property[k], nextBudgetMap.containsKey(property[k])?nextBudgetMap.get(property[k]):null);
						}
						query.addSynchronizedQuerySpace("fssc_budget_execute");
						query.executeUpdate();
						query.setParameter("fdId", IDGenerator.generateID())
						.setParameter("fdModelId", key)
						.setParameter("fdModelName", FsscBudgetData.class.getName())
						.setParameter("fdMoney", FsscNumberUtil.getSubtraction(0.00, (monthCanUse.get(key)!=null?Double.parseDouble(String.valueOf(monthCanUse.get(key))):0.00),2))
						.setParameter("fdType", FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST)
						.setParameter("fdBudgetId", key)
						.setParameter("fdDetailId", null);
						for(int k=0;k<property.length;k++){
							query.setParameter(property[k], nextBudgetMap.containsKey(property[k])?nextBudgetMap.get(property[k]):null);
						}
						query.addSynchronizedQuerySpace("fssc_budget_execute");
						query.executeUpdate();
						//2、新增调整记录,下个月的调增
						sql="insert into fssc_budget_adjust_log (fd_id,fd_desc,doc_create_time,fd_budget_id,fd_model_name,fd_model_id,fd_amount,doc_creator_id)";
						sql+="  values(:fdId,:fdDesc,:docCreateTime,:fdBudgetId,:fdModelName,:fdModelId,:fdAmount,:docCreatorId);";
						query=this.getBaseDao().getHibernateSession().createNativeQuery(sql)
						.setParameter("fdId", IDGenerator.generateID())
						.setParameter("fdDesc", ResourceUtil.getString("fsscBudgetData.transferBudget.fdDesc", "fssc-budget"))
						.setParameter("docCreateTime", new Date())
						.setParameter("fdBudgetId", nextBudgetMap.get("fdId"))
						.setParameter("fdModelName", FsscBudgetData.class.getName())
						.setParameter("fdModelId", nextBudgetMap.get("fdId"))
						.setParameter("fdAmount", monthCanUse.get(key))
						.setParameter("docCreatorId", UserUtil.getUser().getFdId());
						query.addSynchronizedQuerySpace("fssc_budget_adjust_log");
						query.executeUpdate();
						//需结转月的调减
						query.setParameter("fdId", IDGenerator.generateID())
						.setParameter("fdDesc", ResourceUtil.getString("fsscBudgetData.transferBudget.fdDesc", "fssc-budget"))
						.setParameter("docCreateTime", new Date())
						.setParameter("fdBudgetId", key)
						.setParameter("fdModelName", FsscBudgetData.class.getName())
						.setParameter("fdModelId",key)
						.setParameter("fdAmount", FsscNumberUtil.getSubtraction(0.00, (monthCanUse.get(key)!=null?Double.parseDouble(String.valueOf(monthCanUse.get(key))):0.00),2))
						.setParameter("docCreatorId", UserUtil.getUser().getFdId())
						.executeUpdate();
						//3、更新对应的预算占用；
						sql="update fssc_budget_execute set fd_budget_id=:nextBudgetId where fd_budget_id=:preBudgetId and fd_type=:fdType";
						query=this.getBaseDao().getHibernateSession().createNativeQuery(sql)
						.setParameter("nextBudgetId", nextBudgetMap.get("fdId"))
						.setParameter("preBudgetId", key)
						.setParameter("fdType", FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU);
						query.addSynchronizedQuerySpace("fssc_budget_execute");
						query.executeUpdate();
						//4、更新预算为已结转及对应的结转金额
						sql="update fssc_budget_data set fd_is_knots=:fdIsKonts,fd_transfer_acount=:fdTransferAcount where fd_id=:fdBudgetId";
						query=this.getBaseDao().getHibernateSession().createNativeQuery(sql)
						.setParameter("fdIsKonts", true)
						.setParameter("fdTransferAcount", monthCanUse.get(key))
						.setParameter("fdBudgetId", key);
						query.addSynchronizedQuerySpace("fssc_budget_data");
						query.executeUpdate();
						if(index>0&&(index%199==0||index==monthCanUse.size()-1)){//每200提交一次，或者最后一次不管多少直接提交
							this.getBaseDao().getHibernateSession().flush();
						}
						index++;
					}
					/*************************预算结转结束***************************************/
				}
			}
		} catch (Exception e) {
			logger.error(ResourceUtil.getString("fsscBudgetData.transferBudget.error", "fssc-budget") +e);
		}
	}
	/****
	 * 获取需要结转的月份及其对应的下个月的预算id  map
	 * @param id 
	 * @param sQuare 
	 * @param eQuare 
	 * *****/
	public Map getMonthBdgetData(String sYear,String sMonth, 
				String eYear, String eMonth, String sQuarter, String eQuarter,String fdCompanyId) throws Exception{
		Map rtnMap=new ConcurrentHashMap<>();
		StringBuilder hql=new StringBuilder();
		hql.append("select pre.fd_id fd_pre_id,next.fd_id fd_next_id,next.fd_company_group_id,next.fd_company_id,next.fd_cost_center_id,next.fd_budget_item_id,next.fd_project_id,");
		hql.append("next.fd_inner_order_id,next.fd_wbs_id,next.fd_person_id,next.fd_company_group_code,next.fd_company_code,next.fd_cost_center_group_id,");
		hql.append("next.fd_cost_center_code,next.fd_cost_center_group_code,next.fd_budget_item_code,next.fd_project_code,next.fd_inner_order_code,next.fd_wbs_code,");
		hql.append("next.fd_person_code,next.fd_currency_id");
		hql.append(" from (select * from fssc_budget_data where  fd_budget_status=:fdBudgetStatus  and  ");
		String child="";
		if(StringUtil.isNotNull(sMonth)&&StringUtil.isNotNull(eMonth)){//月度结转
			child=StringUtil.linkString(child, " or ", " (fd_period_type=:fdMonthPeriodType  and fd_year=:sYear and  fd_period=:sMonthPeriod )");
		}
		if(StringUtil.isNotNull(sQuarter)&&StringUtil.isNotNull(eQuarter)){//季度结转
			child=StringUtil.linkString(child, " or ", " (fd_period_type=:fdQuarterPeriodType  and fd_year=:sYear and  fd_period=:sQuarterPeriod )");
		}
		if(!sYear.equals(eYear)){//跨年结转
			child=StringUtil.linkString(child, " or ", " (fd_period_type =:fdYearPeriodType  and fd_year=:sYear and  fd_period is null )");
		}
		if(StringUtil.isNotNull(child)){
			hql.append("(").append(child).append(")");
		}
		if(StringUtil.isNotNull(fdCompanyId)){
			hql.append("  and fd_company_id=:fdCompanyId ");
		}
		hql.append("  and fd_is_knots!=:fdIsKnots and fd_apply=:fdApply) pre ");
		hql.append(" left join(select * from fssc_budget_data where  fd_budget_status=:fdBudgetStatus and ");
		child="";
		if(StringUtil.isNotNull(sMonth)&&StringUtil.isNotNull(eMonth)){//月度结转
			child=StringUtil.linkString(child, " or ", " (fd_period_type=:fdMonthPeriodType  and fd_year=:eYear and  fd_period=:eMonthPeriod )");
		}
		if(StringUtil.isNotNull(sQuarter)&&StringUtil.isNotNull(eQuarter)){//季度结转
			child=StringUtil.linkString(child, " or ", " (fd_period_type=:fdQuarterPeriodType  and fd_year=:eYear and  fd_period=:eQuarterPeriod )");
		}
		if(!sYear.equals(eYear)){//跨年结转
			child=StringUtil.linkString(child, " or ", " (fd_period_type =:fdYearPeriodType  and fd_year=:eYear and  fd_period is null )");
		}
		if(StringUtil.isNotNull(child)){
			hql.append("(").append(child).append(")");
		}
		if(StringUtil.isNotNull(fdCompanyId)){
			hql.append("  and fd_company_id=:fdCompanyId ");
		}
		hql.append(") next");
		String[] propertys={"fd_company_group_code","fd_company_code","fd_cost_center_group_code","fd_cost_center_code","fd_budget_item_code",
				"fd_project_code","fd_inner_order_code","fd_wbs_code","fd_person_code","fd_budget_scheme_code","fd_company_id",
				"fd_company_group_id","fd_cost_center_id","fd_budget_item_id","fd_project_id","fd_inner_order_id",
				"fd_wbs_id","fd_person_id","fd_budget_scheme_id","fd_cost_center_group_id","fd_currency_id"};
		StringBuilder where=new StringBuilder(" on pre.fd_period_type=next.fd_period_type"); 
		for(int i=0;i<propertys.length;i++){
			where.append("  and (pre."+propertys[i]+"=next."+propertys[i]+" or pre."+propertys[i]+" is null)");
		}
		hql.append(where);
		Query query=fsscBudgetDataService.getBaseDao().getHibernateSession().createNativeQuery(hql.toString());
		query.setParameter("fdBudgetStatus", FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE)
		.setParameter("sYear", "5"+sYear+"0000")
		.setParameter("eYear", "5"+eYear+"0000")
		.setParameter("fdIsKnots", FsscBudgetConstant.FSSC_BUDGET_TRANSFER_YES)
		.setParameter("fdApply", FsscBudgetConstant.FSSC_BUDGET_RULE_ROLL);
		if(StringUtil.isNotNull(fdCompanyId)){
			query.setParameter("fdCompanyId", fdCompanyId);
		}
		if(StringUtil.isNotNull(sMonth)&&StringUtil.isNotNull(eMonth)){
			query.setParameter("fdMonthPeriodType", FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_MONTH);
			query.setParameter("sMonthPeriod", sMonth);
			query.setParameter("eMonthPeriod", eMonth);
		}
		if(StringUtil.isNotNull(sQuarter)&&StringUtil.isNotNull(eQuarter)){
			query.setParameter("fdQuarterPeriodType", FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_QUARTER);
			query.setParameter("sQuarterPeriod", sQuarter);
			query.setParameter("eQuarterPeriod", eQuarter);
		}
		if(!sYear.equals(eYear)){//跨年结转
			query.setParameter("fdYearPeriodType", FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_YEAR);
		}
		String dataBaseType=DataBaseUtil.getDataBaseType();
		if("mysql".equals(dataBaseType)){
			//处理mysql别名问题
			String[] scalars={"fd_pre_id","fd_next_id","fd_company_group_id","fd_company_id","fd_cost_center_id","fd_budget_item_id",
					"fd_project_id","fd_inner_order_id","fd_wbs_id","fd_person_id","fd_company_group_code","fd_company_code","fd_cost_center_group_id",
					"fd_cost_center_code","fd_cost_center_group_code","fd_budget_item_code","fd_project_code","fd_inner_order_code","fd_wbs_code",
					"fd_person_code","fd_currency_id"};
			for(int n=0,len=scalars.length;n<len;n++){
				((NativeQuery) query).addScalar(scalars[n]);
			}
		}
		List<Object[]> rtnList=query.list();
		List<String> preIdList=new ArrayList<String>();//需结转的月度预算
		for(int i=0;i<rtnList.size();i++){
			Object[] obj=rtnList.get(i);
			Map valMap=new ConcurrentHashMap<>();
			String[] property={"fdId","fdCompanyGroupId","fdCompanyId","fdCostCenterId","fdBudgetItemId","fdProjectId",
					"fdInnerOrderId","fdWbsId","fdPersonId","fdCompanyGroupCode","fdCompanyCode","fdCostCenterGroupId","fdCostCenterCode",
					"fdCostCenterGroupCode","fdBudgetItemCode","fdProjectCode","fdInnerOrderCode","fdWbsCode","fdPersonCode","fdCurrencyId"};
			for(int k=0;k<property.length;k++){
				if(obj[k+1]!=null){
					valMap.put(property[k], obj[k+1]);
				}
			}
			rtnMap.put(obj[0].toString(), valMap);
			preIdList.add(obj[0].toString());
		}
		rtnMap.put("preIds", preIdList);
		return rtnMap;
	}
	/**
	 * 获取对应的结转月需结转的金额（占用+可使用）
	 * ****/
	public Map getBudgetCanUseMoney(List<String> preIdList) throws Exception{
		Map rtnMap=new ConcurrentHashMap<>();
		StringBuilder hql=new StringBuilder();
		hql.append("select init.fd_budget_id,init.fd_init_money,occu.fd_occu_money,used.fd_used_money");
		hql.append(" from (select fd_budget_id,sum(fd_money) fd_init_money from fssc_budget_execute ");
		hql.append(" where (fd_type='"+FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_INIT+"'");
		hql.append(" or fd_type='"+FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST+"')");
		hql.append(" and "+HQLUtil.buildLogicIN("fd_budget_id", preIdList));
		hql.append(" group by fd_budget_id) init left join (select fd_budget_id,sum(fd_money) fd_occu_money from fssc_budget_execute  ");
		hql.append(" where fd_type='"+FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU+"'");
		hql.append(" and "+HQLUtil.buildLogicIN("fd_budget_id", preIdList));
		hql.append(" group by fd_budget_id) occu on init.fd_budget_id=occu.fd_budget_id  left join (select fd_budget_id,sum(fd_money) fd_used_money from fssc_budget_execute  ");
		hql.append(" where fd_type='"+FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_INUSE+"'");
		hql.append(" and "+HQLUtil.buildLogicIN("fd_budget_id", preIdList));
		hql.append(" group by fd_budget_id) used on init.fd_budget_id=used.fd_budget_id ");
		Query query=this.getBaseDao().getHibernateSession().createNativeQuery(hql.toString());
		String driveName = DataBaseUtil.getDataBaseType();
		if("mysql".equals(driveName)){
			//处理mysql别名问题
			String[] scalars={"fd_budget_id","fd_init_money","fd_occu_money","fd_used_money"};
			for(int n=0,len=scalars.length;n<len;n++){
				((NativeQuery) query).addScalar(scalars[n]);
			}
		}
		List<Object[]> result=query.list();
		for(int i=0;i<result.size();i++){
			Object[] obj=result.get(i);
			rtnMap.put(obj[0].toString(), (obj[1]!=null?Double.parseDouble(obj[1].toString()):0.0)
					-(obj[3]!=null?Double.parseDouble(obj[3].toString()):0.0));
		}
		return rtnMap;
	}
	
	public FsscBudgetData getExistBudgetData(String fdYear,FsscBudgetDetail detail,
			List<Map<String, String>> dimensList, Map<String, String> map, String fdSchemeId)  throws Exception{
		HQLInfo hqlInfo=new HQLInfo();
		StringBuilder where=new StringBuilder(" fsscBudgetData.fdYear=:fdYear");
		hqlInfo.setParameter("fdYear", fdYear);
		where.append(" and fsscBudgetData.fdBudgetStatus=:fdBudgetStatus");
		hqlInfo.setParameter("fdBudgetStatus", FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE);
		where.append(" and fsscBudgetData.fdBudgetScheme.fdId=:fdSchemeId");
		hqlInfo.setParameter("fdSchemeId", fdSchemeId);
		if(map.get("fdPeriod")==null){
			where.append(" and fsscBudgetData.fdPeriod is null");
		}else{
			where.append(" and fsscBudgetData.fdPeriod=:fdPeriod");
			hqlInfo.setParameter("fdPeriod", map.get("fdPeriod"));
		}
		where.append(" and fsscBudgetData.fdPeriodType=:fdPeriodType");
		hqlInfo.setParameter("fdPeriodType", map.get("fdPeriodType"));
		for(Map<String,String> diMap:dimensList){
			String diProperty=diMap.get("property");
			if(diMap.containsKey("type")&&"model".equals(diMap.get("type"))){
				if("fdAsset".equals(diProperty)){//资产排除掉，后续增加直接删除该判断即可
					continue;
				}
				Object obj=null;
				if("fdCompany".equals(diProperty)){
					obj=PropertyUtils.getProperty(detail.getDocMain(), diProperty);
				}else if("fdCompanyGroup".equals(diProperty)) {
					Object object=PropertyUtils.getProperty(detail.getDocMain(), "fdCompany");
					if(object!=null) {
						obj=PropertyUtils.getProperty(object, "fdGroup");
					}
				}else{
					obj=PropertyUtils.getProperty(detail, diProperty);
				}
				where.append(" and fsscBudgetData."+diProperty+".fdId=:"+diProperty+"Id");
				hqlInfo.setParameter(diProperty+"Id", obj!=null?PropertyUtils.getProperty(obj, "fdId"):"");
			}
		}
		hqlInfo.setWhereBlock(where.toString());
		List<FsscBudgetData> budgetList=fsscBudgetDataService.findList(hqlInfo);
		if(!ArrayUtil.isEmpty(budgetList)){
			return budgetList.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * 根据方案ID，公司ID，年度获取所有的预算数据
	 * @param dimensList 
	 * ***/
	public Map getHaveData(String fdSchemeId, String fdCompanyId,String fdYear) throws Exception{
		Map<String,List<String>>  propertyMap=EopBasedataFsscUtil.getPropertyByScheme(fdSchemeId);
    	List<String> inPropertyList=propertyMap.get("inPropertyList");
		HQLInfo hqlInfo=new HQLInfo();
		StringBuilder where=new StringBuilder();
		where.append(" fsscBudgetData.fdBudgetScheme.fdId=:fdSchemeId ");
		if(inPropertyList.contains("fdCompany")){
			where.append("and fsscBudgetData.fdCompany.fdId=:fdCompanyId");
		}
		where.append(" and fsscBudgetData.fdYear=:fdYear and fsscBudgetData.fdBudgetStatus=:fdBudgetStatus");
		hqlInfo.setWhereBlock(where.toString());
		hqlInfo.setParameter("fdSchemeId", fdSchemeId);
		if(inPropertyList.contains("fdCompany")){
			hqlInfo.setParameter("fdCompanyId", fdCompanyId);
		}
		hqlInfo.setParameter("fdYear", fdYear);
		hqlInfo.setParameter("fdBudgetStatus", FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE);
		List<FsscBudgetData> dataList=fsscBudgetDataService.findList(hqlInfo);
		Map<String, SysDictCommonProperty> propMap = SysDataDict.getInstance().getModel(FsscBudgetData.class.getName()).getPropertyMap();
		Map rtnMap=new ConcurrentHashMap<>();
		String key="";
		Map<String,Double> rtnVal=fsscBudgetExecuteService.getExceuteMapByType(ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(dataList, "fdId", ";")[0].split(";")), FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST);
		for(FsscBudgetData data:dataList){
			key="";
			for(String property:inPropertyList){
				if(propMap.containsKey(property)){
					SysDictCommonProperty dict=propMap.get(property);
					if(dict.getType().startsWith("com.landray.kmss.")){
						if("fdAsset".equals(property)){//资产排除掉，后续增加直接删除该判断即可
							continue;
						}
						Object obj=PropertyUtils.getProperty(data, property);
						if(obj!=null){
							key+=PropertyUtils.getProperty(obj, "fdId");
						}
					}
				}
			}
			String caseStr=data.getFdPeriodType()+(StringUtil.isNotNull(data.getFdPeriod())?data.getFdPeriod():"");
			switch(caseStr){
				case "5":
					key+="fdYearMoney";  //年度金额
					break;
				case "300":
					key+="fdFirstQuarterMoney";  //第一季度
					break;
				case "100":
					key+="fdJanMoney";  //1月
					break;
				case "101":
					key+="fdFebMoney";  //2月
					break;
				case "102":
					key+="fdMarMoney";  //3月
					break;
				case "301":
					key+="fdSecondQuarterMoney";  //第二季度
					break;
				case "103":
					key+="fdAprMoney";  //4月
					break;
				case "104":
					key+="fdMayMoney";  //5月
					break;
				case "105":
					key+="fdJunMoney";  //6月
					break;
				case "302":
					key+="fdThirdQuarterMoney";  //第三季度
					break;
				case "106":
					key+="fdJulMoney";  //7月
					break;
				case "107":
					key+="fdAugMoney";  //8月
					break;
				case "108":
					key+="fdSeptMoney";  //9月
					break;
				case "303":
					key+="fdFourthQuarterMoney";  //第四季度
					break;
				case "109":
					key+="fdOctMoney";  //10月
					break;
				case "110":
					key+="fdNovMoney";  //11月
					break;
				case "111":
					key+="fdDecMoney";  //12月
					break;
			}
			rtnMap.put(key, FsscNumberUtil.getAddition(rtnVal.containsKey(data.getFdId())?rtnVal.get(data.getFdId()):0.0, data.getFdMoney(), 2));
		}
		return rtnMap;
	}
	
	/***************************************************
	 * 获取执行数据关联的单据信息，用于列表显示
	 * ***********************************************/
	@Override
	public void addFsscBudgetData(JSONArray dataJson) throws Exception {
		//设置对应的维度对应的modelName
		Map<String,String> modelMap=new ConcurrentHashMap<>();
		modelMap.put("fdCompany", EopBasedataCompany.class.getName());
		modelMap.put("fdCostCenter", EopBasedataCostCenter.class.getName());
		modelMap.put("fdBudgetItem", EopBasedataBudgetItem.class.getName());
		modelMap.put("fdProject", EopBasedataProject.class.getName());
		modelMap.put("fdInnerOrder", EopBasedataInnerOrder.class.getName());
		modelMap.put("fdWbs", EopBasedataWbs.class.getName());
		modelMap.put("fdPerson", SysOrgPerson.class.getName());
		modelMap.put("fdDept", SysOrgElement.class.getName());
		//Map缓存对象
		Map<String,Object> objMap=new HashMap<String,Object>();
		Map<String,EopBasedataBudgetScheme> schemeMap=new ConcurrentHashMap<>();
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataBudgetScheme.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable", true);
		List<EopBasedataBudgetScheme> schemeList=eopBasedataBudgetSchemeService.findList(hqlInfo);
		for (EopBasedataBudgetScheme scheme : schemeList) {
			schemeMap.put(scheme.getFdCode(), scheme);
		}
		Map<String,EopBasedataCompany> comMap=new ConcurrentHashMap<>();
		hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataCompany.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable", true);
		List<EopBasedataCompany> comList=eopBasedataCompanyService.findList(hqlInfo);
		for (EopBasedataCompany com : comList) {
			comMap.put(com.getFdCode(), com);
		}
		List<Map<String,String>>  propertyList=FsscBudgetParseXmlUtil.getImportProperty();
		String fdCurrencyId=EopBasedataFsscUtil.getSwitchValue("fdCommonBudgetCurrencyId");
    	EopBasedataCurrency currency=(EopBasedataCurrency) eopBasedataCompanyService.findByPrimaryKey(fdCurrencyId, EopBasedataCurrency.class, true);
		FsscBudgetData budgetData=null;
		SysOrgPerson docCreator=null;
		String key="";  //维度的编码拼接字符串，用于判断预算维度是否一致
		KmssCache dataCache=new KmssCache(FsscBudgetData.class);
		for(int i=0,size=dataJson.size();i<size;i++){
			JSONObject objJson=dataJson.getJSONObject(i);
			if(docCreator==null&&objJson.containsKey("docCreator")){
				docCreator=(SysOrgPerson) this.findByPrimaryKey(objJson.getString("docCreator"), SysOrgPerson.class, true);
				if(docCreator==null){//通过编号查找一遍
					List<SysOrgPerson> persons=this.getBaseDao().getHibernateSession().createQuery("select t from SysOrgPerson t where t.fdNo=:fdNo and t.fdIsAvailable=:fdIsAvailable")
					.setParameter("fdNo", objJson.getString("docCreator")).setParameter("fdIsAvailable", true).list();
					if(!ArrayUtil.isEmpty(persons)){
						docCreator=persons.get(0);
					}
				}
			}
			String fdSchemeCode=objJson.getString("fdSchemeCode");
			EopBasedataBudgetScheme eopBasedataBudgetScheme=schemeMap.get(fdSchemeCode);
			if(eopBasedataBudgetScheme!=null){
				String fdDimensions=eopBasedataBudgetScheme.getFdDimension()+";";
				String fdPeriods=eopBasedataBudgetScheme.getFdPeriod()+";";
				//循环前先找出对应的维度和期间，减少三重循环次数
				List<Map<String,String>> dimensList=new ArrayList<Map<String,String>>();
				List<Map<String,String>> peroidList=new ArrayList<Map<String,String>>();
				for(Map<String,String> map:propertyList){
					if(map.containsKey("period")&&FsscCommonUtil.isContain(fdPeriods, map.get("period")+";", ";")){
						if(!map.get("property").endsWith("Rule")&&!map.get("property").endsWith("Apply")){//控制规则和运用规则排除
							peroidList.add(map);
						}
					}else if(map.containsKey("dimension")&&FsscCommonUtil.isContain(fdDimensions, map.get("dimension")+";", ";")){
						dimensList.add(map);
					}
				}
				for(Map<String,String> map:peroidList){//一年、一个季度、一个月各生成一条预算记录
					 key="";
					String property=map.get("property");
					Double fdMoney=objJson.containsKey(property)?objJson.getDouble(property):null;
					if(fdMoney==null){
						continue;
					}
					budgetData=new FsscBudgetData();
					if(!"1;".equals(fdPeriods)){
						budgetData.setFdYear(objJson.getString("fdYear"));
						key+=objJson.getString("fdYear");
					}
					budgetData.setFdBudgetStatus(FsscBudgetConstant.FSSC_BUDGET_STATUS_ENABLE);
					String fdCompanyCode=objJson.getString("fdCompanyCode");
					if(comMap.containsKey(fdCompanyCode)&&comMap.get(fdCompanyCode)!=null){
						if(FsscCommonUtil.isContain(fdDimensions, "2;", ";")){//预算方案维度包含公司
							budgetData.setFdCompany(comMap.get(fdCompanyCode));
							budgetData.setFdCompanyCode(fdCompanyCode);
							key+=fdCompanyCode;
							budgetData.setFdCurrency(comMap.get(fdCompanyCode).getFdBudgetCurrency()!=null
									?comMap.get(fdCompanyCode).getFdBudgetCurrency():null);
						}else {//不包含公司维度
				        	if(currency!=null) {
				        		budgetData.setFdCurrency(currency);
				        	}
						}
						if(FsscCommonUtil.isContain(fdDimensions, "1;", ";")){//预算方案维度包含公司组
							budgetData.setFdCompanyGroup(comMap.get(fdCompanyCode).getFdGroup());
							budgetData.setFdCompanyGroupCode(comMap.get(fdCompanyCode).getFdGroup()!=null
									?comMap.get(fdCompanyCode).getFdGroup().getFdCode():null);
							key+=comMap.get(fdCompanyCode).getFdGroup()!=null
									?comMap.get(fdCompanyCode).getFdGroup().getFdCode():null;
						}
					}
					budgetData.setFdPeriod(map.get("fdPeriod"));
					key+=map.get("fdPeriod");
					budgetData.setFdPeriodType(map.get("fdPeriodType"));
					key+=map.get("fdPeriodType");
					budgetData.setFdMoney(fdMoney);
					String rule=objJson.getString(map.get("rule"));
					budgetData.setFdRule(rule);
					budgetData.setFdApply(objJson.getString(map.get("apply")));
					if(StringUtil.isNotNull(rule)&&"3".equals(rule)){//控制规则为弹性
						budgetData.setFdElasticPercent(objJson.getDouble("fdElasticPercent"));
					}
					budgetData.setFdBudgetScheme(eopBasedataBudgetScheme);
					budgetData.setFdBudgetSchemeCode(fdSchemeCode);
					budgetData.setFdIsKnots(FsscBudgetConstant.FSSC_BUDGET_TRANSFER_NO);  //默认初始化为未结转
					
					for(Map<String,String> diMap:dimensList){
						String diProperty=diMap.get("property");
						if(diMap.containsKey("type")&&"model".equals(diMap.get("type"))){
							if("fdAsset".equals(diProperty)){//资产排除掉，后续增加直接删除该判断即可
								continue;
							}
							String fdCode=objJson.containsKey(diProperty+"Code")?objJson.getString(diProperty+"Code"):null;
							key+=fdCode;
							Object obj=null;
							if(StringUtil.isNotNull(fdCode)){
								obj=objMap.get(diProperty+fdCode);
								if(obj==null){
									StringBuilder hql=new StringBuilder("select t from "+modelMap.get(diProperty)+" t ");
									if("fdDept".equals(diProperty)||"fdPerson".equals(diProperty)){
										hql.append(" where (t.fdNo=:fdCode or t.fdId=:fdCode)");  //人员部门无编号情况下，传过来的值就是ID
									}else{
										hql.append(" left join  t.fdCompanyList company where  t.fdCode=:fdCode");
									}
									hql.append(" and t.fdIsAvailable=:fdIsAvailable");
									if("fdCostCenter".equals(diProperty)||"fdBudgetItem".equals(diProperty)
											||"fdProject".equals(diProperty)||"fdInnerOrder".equals(diProperty)
											||"fdWbs".equals(diProperty)){
										hql.append(" and (company.fdCode=:fdCompanyCode or company is null)");
									}
									Query query=this.getBaseDao().getHibernateSession().createQuery(hql.toString());
									query.setParameter("fdCode", fdCode);
									query.setParameter("fdIsAvailable", true); //有效
									if("fdCostCenter".equals(diProperty)||"fdBudgetItem".equals(diProperty)
											||"fdProject".equals(diProperty)||"fdInnerOrder".equals(diProperty)
											||"fdWbs".equals(diProperty)){
										query.setParameter("fdCompanyCode", objJson.getString("fdCompanyCode"));
									}
									List result=query.list();
									if(!ArrayUtil.isEmpty(result)){
										obj=result.get(0);
										objMap.put(diProperty+fdCode, obj);
									}
								}
								PropertyUtils.setProperty(budgetData, diProperty, obj);//维度的保持一致，直接获取值
								if("fdPerson".equals(diProperty)||"fdDept".equals(diProperty)){//人员单独处理
									PropertyUtils.setProperty(budgetData, diProperty+"Code", PropertyUtils.getProperty(obj, "fdNo"));//设置编号
								}else{
									PropertyUtils.setProperty(budgetData, diProperty+"Code", PropertyUtils.getProperty(obj, "fdCode"));//设置编号
								}
								PropertyUtils.setProperty(budgetData, diProperty, obj);//设置对象
							}
						}
					}
					budgetData.setDocCreator(docCreator);
					budgetData.setDocCreateTime(new Date());
					dataCache.put(key, budgetData);
				}
			}
		}
		for(String keys:dataCache.getCacheKeys()){
			FsscBudgetData data=(FsscBudgetData) dataCache.get(keys);
			fsscBudgetDataService.getBaseDao().add(data);
			addInitExecuteData(data);
		}
		dataCache.clear();
	}
	/**
	 * 
	 * @param budgetData
	 * @throws Exception
	 */
	public void addInitExecuteData(FsscBudgetData budgetData) throws Exception{
		JSONObject executeJson=new JSONObject();
		executeJson.put("fdModelId", budgetData.getFdId());
		executeJson.put("fdModelName", ModelUtil.getModelClassName(budgetData));
		executeJson.put("fdBudgetId", budgetData.getFdId());
		executeJson.put("fdType", FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_INIT);
		executeJson.put("fdDetailId", budgetData.getFdId());
		executeJson.put("fdMoney", budgetData.getFdMoney());
		executeJson.put("noCompany", "true");
		String[] propertys={"fsscBudgetScheme","fdCompanyGroup","fdCompany","fdCostCenterGroup","fdCostCenter","fdProject","fdWbs","fdInnerOrder","fdBudgetItem","fdPerson","fdDept"};
		List<String> proList=ArrayUtil.convertArrayToList(propertys);
		Map<String, SysDictCommonProperty> dictMap = SysDataDict.getInstance().getModel(FsscBudgetData.class.getName()).getPropertyMap();
		for (String key : dictMap.keySet()) {
			SysDictCommonProperty dict = dictMap.get(key);
			if(dict.getType().startsWith("com.landray.kmss.")&&proList.contains(dict.getName())){
				String property=dict.getName();
				Object obj=PropertyUtils.getProperty(budgetData, property);
				if(obj!=null){
					executeJson.put(property+"Id", PropertyUtils.getProperty(obj, "fdId"));
					if("fdDept".equals(property)||"fdPerson".equals(property)){
						executeJson.put(property+"Code", PropertyUtils.getProperty(obj, "fdNo"));
					}else{
						executeJson.put(property+"Code", PropertyUtils.getProperty(obj, "fdCode"));
					}
				}
			}
         }
		fsscBudgetExecuteService.addFsscBudgetExecute(executeJson);
	}
}
