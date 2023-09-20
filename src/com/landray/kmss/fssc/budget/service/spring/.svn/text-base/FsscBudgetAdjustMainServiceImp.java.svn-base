package com.landray.kmss.fssc.budget.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budget.constant.FsscBudgetConstant;
import com.landray.kmss.fssc.budget.forms.FsscBudgetAdjustMainForm;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustDetail;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain;
import com.landray.kmss.fssc.budget.service.IFsscBudgetAdjustLogService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetAdjustMainService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.fssc.budget.util.FsscBudgetParseXmlUtil;
import com.landray.kmss.fssc.budget.util.FsscBudgetUtil;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import javassist.bytecode.stackmap.BasicBlock;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FsscBudgetAdjustMainServiceImp extends ExtendDataServiceImp implements IFsscBudgetAdjustMainService {
	
	private IFsscBudgetDataService fsscBudgetDataService;
	

	public void setFsscBudgetDataService(IFsscBudgetDataService fsscBudgetDataService) {
		if(fsscBudgetDataService==null){
			fsscBudgetDataService=(IFsscBudgetDataService) SpringBeanUtil.getBean("fsscBudgetDataService");
		}
		this.fsscBudgetDataService = fsscBudgetDataService;
	}
	
	protected IFsscBudgetExecuteService fsscBudgetExecuteService;

	public void setFsscBudgetExecuteService(IFsscBudgetExecuteService fsscBudgetExecuteService) {
		if(fsscBudgetExecuteService==null){
			fsscBudgetExecuteService=(IFsscBudgetExecuteService) SpringBeanUtil.getBean("fsscBudgetExecuteService");
		}
		this.fsscBudgetExecuteService = fsscBudgetExecuteService;
	}
	
	protected IFsscBudgetAdjustLogService fsscBudgetAdjustLogService;
	
	public void setFsscBudgetAdjustLogService(IFsscBudgetAdjustLogService fsscBudgetAdjustLogService) {
		if(fsscBudgetAdjustLogService==null){
			fsscBudgetAdjustLogService=(IFsscBudgetAdjustLogService) SpringBeanUtil.getBean("fsscBudgetAdjustLogService");
		}
		this.fsscBudgetAdjustLogService = fsscBudgetAdjustLogService;
	}
	
	protected IEopBasedataCompanyService eopBasedataCompanyService;
    
    public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}

	private ISysNumberFlowService sysNumberFlowService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscBudgetAdjustMain) {
            FsscBudgetAdjustMain fsscBudgetAdjustMain = (FsscBudgetAdjustMain) model;
            FsscBudgetAdjustMainForm fsscBudgetAdjustMainForm = (FsscBudgetAdjustMainForm) form;
            if (fsscBudgetAdjustMain.getDocStatus() == null || fsscBudgetAdjustMain.getDocStatus().startsWith("1")) {
                if (fsscBudgetAdjustMainForm.getDocStatus() != null && (fsscBudgetAdjustMainForm.getDocStatus().startsWith("1") || fsscBudgetAdjustMainForm.getDocStatus().startsWith("2"))) {
                    fsscBudgetAdjustMain.setDocStatus(fsscBudgetAdjustMainForm.getDocStatus());
                }
            }
            if (fsscBudgetAdjustMain.getDocNumber() == null && (fsscBudgetAdjustMain.getDocStatus().startsWith("2") || fsscBudgetAdjustMain.getDocStatus().startsWith("3"))) {
                fsscBudgetAdjustMain.setDocNumber(sysNumberFlowService.generateFlowNumber(fsscBudgetAdjustMain));
            }
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscBudgetAdjustMain fsscBudgetAdjustMain = new FsscBudgetAdjustMain();
        fsscBudgetAdjustMain.setDocCreateTime(new Date());
        fsscBudgetAdjustMain.setDocCreator(UserUtil.getUser());
        String fdBudgetSchemeId=requestContext.getParameter("i.fdBudgetScheme");
        EopBasedataBudgetScheme budgetScheme=null;
        if(StringUtil.isNotNull(fdBudgetSchemeId)){
        	budgetScheme=(EopBasedataBudgetScheme) this.findByPrimaryKey(fdBudgetSchemeId, EopBasedataBudgetScheme.class, true);
        	String fdPeriod=budgetScheme.getFdPeriod();
        	String fdSchemePeriod="";
        	if(FsscCommonUtil.isContain(fdPeriod, "4;", ";")) {//包含月度
        		fdSchemePeriod="1";
        	}else if(FsscCommonUtil.isContain(fdPeriod, "3;", ";")) {//包含季度
        		fdSchemePeriod="3";
        	}else if(FsscCommonUtil.isContain(fdPeriod, "2;", ";")) {//包含年度
        		fdSchemePeriod="5";
        	}
        	requestContext.setAttribute("fdSchemePeriod", fdSchemePeriod);
        }
        if(budgetScheme!=null&&FsscCommonUtil.isContain(budgetScheme.getFdDimension(), "2;", ";")) {//包含公司维度
        	//为空自动获取默认记账公司
        	List<EopBasedataCompany> companyList=eopBasedataCompanyService.findCompanyByUserId(UserUtil.getUser().getFdId());
        	if(!ArrayUtil.isEmpty(companyList)){
				List<EopBasedataCompany> comList=budgetScheme.getFdCompanys();
				if(!ArrayUtil.isEmpty(comList)){
					//若是预算方案可使用范围不为空，过滤所在公司在不在范围内
					for(EopBasedataCompany com:comList){
						if(companyList.contains(com)){
							fsscBudgetAdjustMain.setFdCompany(com);
							fsscBudgetAdjustMain.setFdCurrency(com.getFdBudgetCurrency());
							break;
						}
					}
				}else{
					fsscBudgetAdjustMain.setFdCompany(companyList.get(0));
					fsscBudgetAdjustMain.setFdCurrency(companyList.get(0).getFdBudgetCurrency());
				}
        	}
        }else {//维度不包含公司，币种从开关设置获取
        	String fdCurrencyId=EopBasedataFsscUtil.getSwitchValue("fdCommonBudgetCurrencyId");
        	EopBasedataCurrency currency=(EopBasedataCurrency) eopBasedataCompanyService.findByPrimaryKey(fdCurrencyId, EopBasedataCurrency.class, true);
        	if(currency!=null) {
        		fsscBudgetAdjustMain.setFdCurrency(currency);
        	}
        }
        FsscBudgetUtil.initModelFromRequest(fsscBudgetAdjustMain, requestContext);
		requestContext.setAttribute("docTemplate", fsscBudgetAdjustMain.getDocTemplate());
        requestContext.setAttribute("adjustType", fsscBudgetAdjustMain.getDocTemplate().getFdAdjustType());
        return fsscBudgetAdjustMain;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscBudgetAdjustMain fsscBudgetAdjustMain = (FsscBudgetAdjustMain) model;
        if (fsscBudgetAdjustMain.getDocTemplate() != null) {
        	requestContext.setAttribute("adjustType", fsscBudgetAdjustMain.getDocTemplate().getFdAdjustType());
            dispatchCoreService.initFormSetting(form, "fsscBudgetAdjustMain", fsscBudgetAdjustMain.getDocTemplate(), "fsscBudgetAdjustMain", requestContext);
        }
    }

    @Override
    public List<FsscBudgetAdjustMain> findByDocTemplate(FsscBudgetAdjustCategory docTemplate) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetAdjustMain.docTemplate.fdId=:fdId");
        hqlInfo.setParameter("fdId", docTemplate.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetAdjustMain> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetAdjustMain.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetAdjustMain> findByFdCurrency(EopBasedataExchangeRate fdCurrency) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetAdjustMain.fdCurrency.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCurrency.getFdId());
        return this.findList(hqlInfo);
    }

    public void setSysNumberFlowService(ISysNumberFlowService sysNumberFlowService) {
        this.sysNumberFlowService = sysNumberFlowService;
    }
    
    @Override
	public String add(IBaseModel modelObj) throws Exception {
		FsscBudgetAdjustMain main = (FsscBudgetAdjustMain) modelObj;
		if (null != main) {
			if (!SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())) {
				// 根据明细信息将对应的预算进行冻结
				operationBudget(main, "create");
			}
		}
		FsscBudgetAdjustCategory cate = main.getDocTemplate();
		if(cate != null && "2".equals(cate.getFdSubjectType())){
			FormulaParser parser = FormulaParser.getInstance(main);
			main.setDocSubject((String) parser.parseValueScript(cate.getFdSubjectRule()));
		}
		return super.add(modelObj);
	}
	
	
	/***
	 * 更新申请单
	 */
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		FsscBudgetAdjustMain main = (FsscBudgetAdjustMain) modelObj;
		if(main!=null){
			// 如果进入待审状态，需要往费用明细表写入已占用的金额，将该费用冻结
			if (SysDocConstant.DOC_STATUS_EXAMINE.equals(main.getDocStatus())) {
				// 重新对预算进行占用
				operationBudget(main, "update");
			}
		}
		FsscBudgetAdjustCategory cate = main.getDocTemplate();
		if(cate != null && "2".equals(cate.getFdSubjectType())){
			FormulaParser parser = FormulaParser.getInstance(main);
			main.setDocSubject((String) parser.parseValueScript(cate.getFdSubjectRule()));
		}
		super.update(main);
	}

	 /**
		 * 预算调整校验当前行借出金额是否足够
		 */
	@Override
	public JSONObject checkLendMoney(HttpServletRequest request) throws Exception {
		JSONArray budgetArray=new JSONArray(); 
		JSONArray currentBudgetArray=new JSONArray(); //获取当前期间最小的预算
		JSONArray allBudgetArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		String params=request.getParameter("hashMapArray");
		if(StringUtil.isNotNull(params)){
			JSONObject dataJson=JSONObject.fromObject(params);
	        Calendar cal=Calendar.getInstance();
	        String fdYear=String.valueOf(cal.get(Calendar.YEAR)),fdQuart="0"+String.valueOf(cal.get(Calendar.MONTH)/3),fdMonth=cal.get(Calendar.MONTH)>9?String.valueOf(cal.get(Calendar.MONTH)):("0"+cal.get(Calendar.MONTH));
	        if(dataJson.containsKey("fdPeriod")&&dataJson.opt("fdPeriod")!=null) {
	        	String fdPeriod=dataJson.optString("fdPeriod");
	        	String periodType=fdPeriod.substring(0, 1);
	        	fdYear=fdPeriod.substring(1, 5);
	        	String period=fdPeriod.substring(5, 7);
	        	if("1".equals(periodType)) {//年、季度、月度，则period为月度值
	        		fdMonth=period;
	        		fdQuart="0"+String.valueOf(Integer.parseInt(period)/3);
	        	}else if("3".equals(periodType)) {//年、季度
	        		fdQuart=period;
	        		fdMonth="";
	        	}else {//年度
	        		fdQuart="";
	        		fdMonth="";
	        	}
	        }
			budgetArray=fsscBudgetDataService.findBudget(dataJson,FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_MONTH,fdYear,fdMonth);
			FsscCommonUtil.concatTwoJSON(budgetArray, allBudgetArray);
			currentBudgetArray=budgetArray;
			budgetArray=fsscBudgetDataService.findBudget(dataJson,FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_QUARTER,fdYear,fdQuart);
			FsscCommonUtil.concatTwoJSON(budgetArray, allBudgetArray);
			if(currentBudgetArray.size()==0){//无月度预算，查找对应的季度预算
				currentBudgetArray=budgetArray;
			}
			budgetArray=fsscBudgetDataService.findBudget(dataJson,FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_YEAR,fdYear,null);
			FsscCommonUtil.concatTwoJSON(budgetArray, allBudgetArray);
			if(currentBudgetArray.size()==0){//无月度、季度预算，查找对应的年度度预算
				currentBudgetArray=budgetArray;
			}
			if(currentBudgetArray.size()>0){
				jsonObject.put("minPeriod", currentBudgetArray.get(0));
			}
			jsonObject.put("allPeriod", allBudgetArray);
		}
		return jsonObject;  //正常情况下，月度只会有一条预算结果，出现多条只获取第一条
	}
	/**
	 * 预算调整/追加校验当前行借入成本中心对应预算是否存在
	 */
	@Override
	public JSONObject checkBorrowMoney(HttpServletRequest request) throws Exception {
		JSONObject rtnObj=new JSONObject();
		double canUse=0.0;
		JSONArray budgetArray=new JSONArray();
		String params=request.getParameter("hashMapArray");
		if(StringUtil.isNotNull(params)){
			JSONObject dataJson=JSONObject.fromObject(params);
			Calendar cal=Calendar.getInstance();
	        String fdYear=String.valueOf(cal.get(Calendar.YEAR)),fdQuart="0"+String.valueOf(cal.get(Calendar.MONTH)/3),fdMonth=cal.get(Calendar.MONTH)>9?String.valueOf(cal.get(Calendar.MONTH)):("0"+cal.get(Calendar.MONTH));
	        if(dataJson.containsKey("fdPeriod")&&dataJson.opt("fdPeriod")!=null) {
	        	String fdPeriod=dataJson.optString("fdPeriod");
	        	String periodType=fdPeriod.substring(0, 1);
	        	fdYear=fdPeriod.substring(1, 5);
	        	String period=fdPeriod.substring(5, 7);
	        	if("1".equals(periodType)) {//年、季度、月度，则period为月度值
	        		fdMonth=period;
	        		fdQuart="0"+String.valueOf(Integer.parseInt(period)/3);
	        	}else if("3".equals(periodType)) {//年、季度
	        		fdQuart=period;
	        	}
	        }
			budgetArray=fsscBudgetDataService.findBudget(dataJson,FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_MONTH,fdYear,fdMonth);
		}
		int size=budgetArray.size();
		if(size>0){
			canUse=budgetArray.getJSONObject(0).getDouble("fdCanUseAmount");
			for(int k=1;k<size;k++){
				double fdCanUseAmount=budgetArray.getJSONObject(0).getDouble("fdCanUseAmount");
				if(canUse>fdCanUseAmount){
					canUse=fdCanUseAmount;
				}
			}
		}
		rtnObj.put("result", !budgetArray.isEmpty());
		rtnObj.put("canUse", canUse);
		return rtnObj;  //返回值为true则预算存在
	}
	
	@Override
    public void operationBudget(FsscBudgetAdjustMain fsscBudgetAdjust, String method) throws Exception{
		JSONArray budgetArray=new JSONArray();  //所有对应预算信息
		String fdAdjustType=fsscBudgetAdjust.getDocTemplate().getFdAdjustType();
		List<FsscBudgetAdjustDetail> detailList=fsscBudgetAdjust.getFdDetailList();
		String fdSchemeId=fsscBudgetAdjust.getFdBudgetScheme().getFdId();
		List<String> propertyList=FsscBudgetParseXmlUtil.getAdjustSchemeList("dimension", fdSchemeId, null, null, null,
				FsscBudgetConstant.FSSC_BUDGET_ADJUST_TYPE_ADJUST);
		//需要执行的预算信息，考虑到同一预算情况，同一放到最外层，防止后面预算删除前面预算的执行信息
		JSONArray lendExecuteArr=new JSONArray();  //借出
		JSONArray borrowExecuteArr=new JSONArray();   //借入 
		for(FsscBudgetAdjustDetail detail:detailList){
			//借入信息json
			JSONObject borrowJson=new JSONObject();
			borrowJson.put("fdBudgetSchemeId", fsscBudgetAdjust.getFdBudgetScheme().getFdId());
			//借出信息json
			JSONObject lendJson=new JSONObject();
			lendJson.put("fdBudgetSchemeId", fsscBudgetAdjust.getFdBudgetScheme().getFdId());
			if(FsscCommonUtil.isContain(fsscBudgetAdjust.getFdBudgetScheme().getFdDimension(), "1;", ";")){
				String fdCompanyGroupId=fsscBudgetAdjust.getFdCompany()!=null?
						(fsscBudgetAdjust.getFdCompany().getFdGroup()!=null?fsscBudgetAdjust.getFdCompany().getFdGroup().getFdId():null):null;
				//包含公司组
				borrowJson.put("fdCompanyGroupId", fdCompanyGroupId);
				//包含公司组
				lendJson.put("fdCompanyGroupId", fdCompanyGroupId);
			}
			if(FsscCommonUtil.isContain(fsscBudgetAdjust.getFdBudgetScheme().getFdDimension(), "2;", ";")){
				String fdCompanyId=fsscBudgetAdjust.getFdCompany()!=null?fsscBudgetAdjust.getFdCompany().getFdId():null;
				//包含公司
				borrowJson.put("fdCompanyId", fdCompanyId);
				//包含公司
				lendJson.put("fdCompanyId", fdCompanyId);
			}
			for(String property:propertyList){
				Object object=PropertyUtils.getProperty(detail, property);
				if(property.indexOf("Borrow")>-1&&!"fdBorrowParentName".equals(property)){//借入信息
					borrowJson.put(property.replace("Borrow", "")+"Id", PropertyUtils.getProperty(object, "fdId"));
				}else if(property.indexOf("Lend")>-1&&!"fdLendParentName".equals(property)){//借出信息
					if(FsscBudgetConstant.FSSC_BUDGET_ADJUST_TYPE_ADJUST.equals(fdAdjustType)){//只有调整才有借出成本中心操作
						try{
							lendJson.put(property.replace("Lend", "")+"Id", PropertyUtils.getProperty(object, "fdId"));
						}catch (Exception e){
							//
						}
					}
				}
			}
			if("publish".equals(method)){
				//获取所有借入预算，增加调整记录
				String fdBorrowPeriod=detail.getFdBorrowPeriod();
				if(StringUtil.isNotNull(fdBorrowPeriod)) {
					String periodType=fdBorrowPeriod.substring(0, 1);
					String monthPeriod=fdBorrowPeriod.substring(5, 7);
					if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_QUARTER.equals(periodType)) {
						monthPeriod=Integer.parseInt(monthPeriod)*3<10?("0"+Integer.parseInt(monthPeriod)*3):String.valueOf(Integer.parseInt(monthPeriod)*3);  //如果是对应的季度，转为季度中某个月份查找，因为查找按照月度查找的
					}
					budgetArray=fsscBudgetDataService.findBudget(borrowJson,FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_ALL,fdBorrowPeriod.substring(1, 5),monthPeriod);
				}else {
					budgetArray=fsscBudgetDataService.findBudget(borrowJson,FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_ALL,null,null);
				}
				JSONArray rtnArray=getAddOrUpdateExecuteArray(budgetArray,propertyList,detail.getFdMoney(),detail,FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST,"Borrow");
				FsscCommonUtil.concatTwoJSON(rtnArray, borrowExecuteArr);
				addAdjustLog(budgetArray,detail.getFdMoney(),fsscBudgetAdjust);//增加预算调整记录
			}
			if(FsscBudgetConstant.FSSC_BUDGET_ADJUST_TYPE_ADJUST.equals(fdAdjustType)){//只有调整才有借出成本中心操作
				//获取所有借出预算，增加调整记录
				String fdBudgetInfo=detail.getFdBudgetInfo();
				if(StringUtil.isNotNull(fdBudgetInfo)){
					budgetArray=JSONArray.fromObject(fdBudgetInfo);
					List<String> idList=new ArrayList<>();
					for(int n=0,size=budgetArray.size();n<size;n++){
						JSONObject json=budgetArray.getJSONObject(n);
						if(json.containsKey("fdBudgetId")){
							idList.add(json.getString("fdBudgetId"));
						}
					}
					String fdLendPeriod=detail.getFdLendPeriod();
					if(StringUtil.isNotNull(fdLendPeriod)) {
						String periodType=fdLendPeriod.substring(0, 1);
						String monthPeriod=fdLendPeriod.substring(5, 7);
						if(FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_QUARTER.equals(periodType)) {
							monthPeriod=Integer.parseInt(monthPeriod)*3<10?("0"+Integer.parseInt(monthPeriod)*3):String.valueOf(Integer.parseInt(monthPeriod)*3);  //如果是对应的季度，转为季度中某个月份查找，因为查找按照月度查找的
						}
						budgetArray=fsscBudgetDataService.findBudget(lendJson,FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_ALL,fdLendPeriod.substring(1, 5),monthPeriod); 
					}else {
						budgetArray=fsscBudgetDataService.findBudget(lendJson,FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_ALL,null,null);
					}
				}
				JSONArray rtnArray=new JSONArray();
				if("create".equals(method)){//借出方，冻结对应的预算
					rtnArray=getAddOrUpdateExecuteArray(budgetArray,propertyList,detail.getFdMoney(),detail,FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU,"Lend");
				}else if("update".equals(method)){//借出方重新冻结对应的预算
					rtnArray=getAddOrUpdateExecuteArray(budgetArray,propertyList,detail.getFdMoney(),detail,FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU,"Lend");
				}else if("publish".equals(method)){
					addAdjustLog(budgetArray,FsscNumberUtil.getSubtraction(0, detail.getFdMoney(), 2),fsscBudgetAdjust);  //增加预算调整记录
					rtnArray=getAddOrUpdateExecuteArray(budgetArray,propertyList,FsscNumberUtil.getSubtraction(0, detail.getFdMoney(), 2),detail,FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_ADJUST,"Lend");
				}else if("delete".equals(method)){
					deleteExecuteArray(budgetArray,propertyList,detail);
				}
				FsscCommonUtil.concatTwoJSON(rtnArray, lendExecuteArr);
			}
		}
		//操作借出方
		if("publish".equals(method)||"create".equals(method)||"update".equals(method)) {
			JSONObject param=new JSONObject();
			param.put("fdModelId", fsscBudgetAdjust.getFdId());
			fsscBudgetExecuteService.deleteFsscBudgetExecute(param);
			if(lendExecuteArr.size()>0) {
				for(int n=0;n<lendExecuteArr.size();n++) {
					//预算执行表插入type为调整的记录，便于后面统计预算总额
					fsscBudgetExecuteService.updateFsscBudgetExecute(lendExecuteArr.getJSONObject(n));
				}
			}
		}
		//操作借入方
		if("publish".equals(method)) {
			if(borrowExecuteArr.size()>0) {
				for(int n=0;n<borrowExecuteArr.size();n++) {
					//预算执行表插入type为调整的记录，便于后面统计预算总额
					fsscBudgetExecuteService.updateFsscBudgetExecute(borrowExecuteArr.getJSONObject(n));
				}
			}
		}
	} 
	
	/************************************************************
	 * @param budgetArray需要操作的预算 json格式
	 * @param propertyList调整预算维度
	 * @param fdMoney 调整金额
	 * @param detail 调整明细model
	 * @param fdExecuteType 执行类型，冻结、使用、调整
	 * @param fdType 类型，Borrow 为借入，Lend为借出
	 * ************************************************************/
	public JSONArray getAddOrUpdateExecuteArray(JSONArray budgetArray,List<String> propertyList,Double fdMoney,FsscBudgetAdjustDetail detail,String fdExecuteType,String fdType) throws Exception{
		FsscBudgetAdjustMain main=detail.getDocMain();
		EopBasedataBudgetScheme scheme=main.getFdBudgetScheme();
		List<String> notHavePeriod=new ArrayList<String>();
		if(scheme!=null){
			String fdPeriod=scheme.getFdPeriod();
			if(StringUtil.isNotNull(fdPeriod)){
				if(!FsscCommonUtil.isContain(fdPeriod, "2;", ";")){//包含年
					notHavePeriod.add("5");
				}
				if(!FsscCommonUtil.isContain(fdPeriod, "3;", ";")){//包含季
					notHavePeriod.add("3");
				}
				if(!FsscCommonUtil.isContain(fdPeriod, "4;", ";")){//包含月
					notHavePeriod.add("1");
				}
			}
		}
		JSONObject executeJson=new JSONObject();//对应预算执行信息
		JSONArray executeArr=new JSONArray();
		for(int i=0,len=budgetArray.size();i<len;i++){
			executeJson=new JSONObject();
			JSONObject budgetJson=(JSONObject) budgetArray.get(i);
			executeJson.put("fdModelId", detail.getDocMain().getFdId()); //预算调整ID
			executeJson.put("fdModelName", FsscBudgetAdjustMain.class.getName()); //预算modelName
			executeJson.put("fdMoney", fdMoney); //调整金额
			executeJson.put("fdType", fdExecuteType);
			executeJson.put("fdDetailId", detail.getFdId());
			executeJson.put("fdBudgetId", budgetJson.containsKey("fdBudgetId")?budgetJson.get("fdBudgetId"):"");
			if(notHavePeriod.contains(budgetJson.get("fdPeriodType"))){
				notHavePeriod.remove(budgetJson.get("fdPeriodType"));  //有对应的预算，则移除
			}
			for(String property:propertyList){
				if(property.indexOf(fdType)>-1){//借入信息
					Object object=PropertyUtils.getProperty(detail, property);
					try{
						PropertyUtils.getProperty(object, "fdId");
					}catch (Exception e){
						continue;
					}
					executeJson.put(property.replace(fdType, "")+"Id", PropertyUtils.getProperty(object, "fdId"));
					if(property.endsWith("Person")||property.endsWith("Dept")){
						executeJson.put(property.replace(fdType, "")+"Code", PropertyUtils.getProperty(object, "fdNo"));
					}else{
						executeJson.put(property.replace(fdType, "")+"Code", PropertyUtils.getProperty(object, "fdCode"));
					}
				}
			}
			if("Borrow".equals(fdType)){//拼接预算调整信息
				if(!ArrayUtil.isEmpty(notHavePeriod)){
					String fdTips=ResourceUtil.getString("message.adjust.tips", "fssc-budget");
					String tips="";
					if(notHavePeriod.contains("1")){
						tips+=ResourceUtil.getString("message.adjust.tips.month", "fssc-budget");
					}
					if(notHavePeriod.contains("3")){
						tips+=ResourceUtil.getString("message.adjust.tips.quarter", "fssc-budget");
					}
					if(notHavePeriod.contains("5")){
						tips+=ResourceUtil.getString("message.adjust.tips.year", "fssc-budget");
					}
					if(StringUtil.isNotNull(tips)){
						fdTips=fdTips.replaceAll("tips", tips.substring(0, tips.length()-1));
					}
					main.setFdTips(fdTips);
					this.getBaseDao().update(main);
				}
			}
			executeJson.put("noCompany", "true");
			executeArr.add(executeJson);
		}
		return executeArr;
	}
	/************************************************************
	 * @param budgetArray需要操作的预算 json格式
	 * @param propertyList调整预算维度
	 * @param detail 调整明细model
	 * ************************************************************/
	public void deleteExecuteArray(JSONArray budgetArray,List<String> propertyList,FsscBudgetAdjustDetail detail) throws Exception{
		JSONObject executeJson=new JSONObject();//对应预算执行信息
		for(int i=0,len=budgetArray.size();i<len;i++){
			executeJson=new JSONObject();
			JSONObject budgetJson=(JSONObject) budgetArray.get(i);
			executeJson.put("fdModelId", detail.getDocMain()!=null?detail.getDocMain().getFdId():null); //预算调整ID
			executeJson.put("fdModelName", FsscBudgetAdjustMain.class.getName()); //预算modelName
			executeJson.put("fdDetailId", detail.getFdId());
			executeJson.put("fdBudgetId", budgetJson.containsKey("fdBudgetId")?budgetJson.get("fdBudgetId"):"");  //预算ID
			for(String property:propertyList){
				if(property.indexOf("Lend")>-1&&!"fdLendParentName".equals(property)&&!"fdLendCanUseMoney".equals(property)){//借入信息
					Object object=PropertyUtils.getProperty(detail, property);
					executeJson.put(property.replace("Lend", ""), PropertyUtils.getProperty(object, "fdId"));
					if(property.endsWith("Person")||property.endsWith("Dept")){
						executeJson.put(property.replace("Lend", "").replace("Id", "Code"), PropertyUtils.getProperty(object, "fdNo"));
					}else{
						executeJson.put(property.replace("Lend", "").replace("Id", "Code"), PropertyUtils.getProperty(object, "fdCode"));
					}
				}
			}
			//create删除是没数据的，正常情况下，update先删除历史占用的，插入新的占用计算，publish和update类似
			fsscBudgetExecuteService.deleteFsscBudgetExecute(executeJson);
		}
	}
	/************************************************************
	 * @param budgetArray需要操作的预算 json格式
	 * @param fdMoney 调整金额
	 * @param fdPersonId 调整人Id
	 * @param fdAdjustMainId 调整单Id
	 * ************************************************************/
	public void addAdjustLog(JSONArray budgetArray,Double fdMoney,FsscBudgetAdjustMain fsscBudgetAdjust) throws Exception{
		JSONObject logJson=new JSONObject();//对应预算执行信息
		for(int i=0,len=budgetArray.size();i<len;i++){
			logJson=new JSONObject();
			JSONObject budgetJson=(JSONObject) budgetArray.get(i);
			logJson.put("fdModelId", fsscBudgetAdjust.getFdId()); //预算调整ID
			logJson.put("fdModelName", FsscBudgetAdjustMain.class.getName()); //预算调整modelName
			logJson.put("fdBudgetId", budgetJson.containsKey("fdBudgetId")?budgetJson.get("fdBudgetId"):""); //预算ID
			logJson.put("fdMoney", fdMoney);
			logJson.put("fdPersonId", fsscBudgetAdjust.getDocCreator().getFdId());
			logJson.put("fdDesc", fsscBudgetAdjust.getFdDesc());
			//create删除是没数据的没正常情况下，update先删除历史占用的，插入新的占用计算，publish和update类似
			fsscBudgetAdjustLogService.addAdjust(logJson);
		}
	}
	
	/**
	 * 校验预算数据查看页面调整，调减金额不能不能调为负数
	 */
	@Override
	public boolean checkAdjust(HttpServletRequest request) throws Exception {
		boolean result=Boolean.TRUE; //默认校验通过
		String params=request.getParameter("hashMapArray");
		if(StringUtil.isNotNull(params)){
			JSONObject dataJson=JSONObject.fromObject(params);
			String fdBudgetId=dataJson.containsKey("fdBudgetId")?dataJson.getString("fdBudgetId"):null;
			if(StringUtil.isNotNull(fdBudgetId)){
				Map<String, String> resultMap=fsscBudgetDataService.getBudgetAcountInfo(fdBudgetId,"double");
				Double fdMoney=(resultMap.containsKey("fdTotalAmount")&&resultMap.get("fdTotalAmount")!=null)
						?Double.parseDouble(String.valueOf(resultMap.get("fdTotalAmount"))):0.00;//可使用额
				Double fdAdjustMoney=dataJson.containsKey("fdAdjustMoney")?Double.parseDouble(dataJson.getString("fdAdjustMoney")):0.00; //调整金额
				if(FsscNumberUtil.getSubtraction(Math.abs(fdAdjustMoney),fdMoney)>0.000001){//调整金额绝对值大于可使用额，不允许调整
					result=Boolean.FALSE;
				}
			}
		}
		return result;
	}
}
