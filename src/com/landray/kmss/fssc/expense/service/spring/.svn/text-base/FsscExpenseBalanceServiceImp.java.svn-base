package com.landray.kmss.fssc.expense.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostCenterService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.expense.constant.FsscExpenseConstant;
import com.landray.kmss.fssc.expense.forms.FsscExpenseBalanceForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalance;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalanceDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.service.IFsscExpenseBalanceService;
import com.landray.kmss.fssc.expense.util.FsscExpenseUtil;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCoreService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscExpenseBalanceServiceImp extends ExtendDataServiceImp implements IFsscExpenseBalanceService {
	private IFsscCommonBudgetOperatService fsscBudgetOperatService;
	
	public IFsscCommonBudgetOperatService getFsscBudgetOperatService() {
		if(fsscBudgetOperatService==null){
			fsscBudgetOperatService = (IFsscCommonBudgetOperatService) SpringBeanUtil.getBean("fsscBudgetOperatService");
		}
		return fsscBudgetOperatService;
	}
    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    private ILbpmProcessCoreService lbpmProcessCoreService;
    
    private ISysNumberFlowService sysNumberFlowService;

    public ISysNumberFlowService getSysNumberFlowService() {
		return sysNumberFlowService;
	}

	public void setSysNumberFlowService(ISysNumberFlowService sysNumberFlowService) {
		this.sysNumberFlowService = sysNumberFlowService;
	}
	
	private IEopBasedataCompanyService eopBasedataCompanyService;

	public IEopBasedataCompanyService getEopBasedataCompanyService() {
		if (eopBasedataCompanyService == null) {
			eopBasedataCompanyService = (IEopBasedataCompanyService) SpringBeanUtil.getBean("eopBasedataCompanyService");
        }
		return eopBasedataCompanyService;
	}
	
	private IEopBasedataCostCenterService eopBasedataCostCenterService;
	
    public IEopBasedataCostCenterService getEopBasedataCostCenterService() {
    	if (eopBasedataCostCenterService == null) {
    		eopBasedataCostCenterService = (IEopBasedataCostCenterService) SpringBeanUtil.getBean("eopBasedataCostCenterService");
        }
		return eopBasedataCostCenterService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscExpenseBalance) {
            FsscExpenseBalance fsscExpenseBalance = (FsscExpenseBalance) model;
            FsscExpenseBalanceForm fsscExpenseBalanceForm = (FsscExpenseBalanceForm) form;
            if (fsscExpenseBalance.getDocStatus() == null || fsscExpenseBalance.getDocStatus().startsWith("1")) {
                if (fsscExpenseBalanceForm.getDocStatus() != null && (fsscExpenseBalanceForm.getDocStatus().startsWith("1") || fsscExpenseBalanceForm.getDocStatus().startsWith("2"))) {
                    fsscExpenseBalance.setDocStatus(fsscExpenseBalanceForm.getDocStatus());
                }
            }
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscExpenseBalance fsscExpenseBalance = new FsscExpenseBalance();
        fsscExpenseBalance.setDocCreateTime(new Date());
        fsscExpenseBalance.setDocCreator(UserUtil.getUser());
        fsscExpenseBalance.setDocCreatorDept(UserUtil.getUser().getFdParent());
        FsscExpenseUtil.initModelFromRequest(fsscExpenseBalance, requestContext);
        //设置默认公司
        SysOrgElement user = UserUtil.getUser();
        boolean auth=true;  //默认有权限
        List<EopBasedataCompany> own = new ArrayList<EopBasedataCompany>();
        //设置默认公司
        List<EopBasedataCompany> ownList = getEopBasedataCompanyService().findCompanyByUserId(user.getFdId());;
        
        JSONObject valObj=EopBasedataFsscUtil.getAccountsDate(FsscExpenseMain.class.getName());
		String fdType=valObj.containsKey("fdType")?valObj.getString("fdType"):"";
		String fdDate=valObj.containsKey("fdDate")?valObj.getString("fdDate"):"";
		String fdCompanyIds=valObj.containsKey("fdCompanyIds")?valObj.getString("fdCompanyIds"):"";
		String currentDate=DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATE);
		List<EopBasedataCompany> companyList = new ArrayList<EopBasedataCompany>();
		if(StringUtil.isNotNull(fdCompanyIds)){
			companyList = getEopBasedataCompanyService().findByPrimaryKeys(fdCompanyIds.split(";"));
		}
		if(StringUtil.isNotNull(fdDate)&&StringUtil.isNotNull(fdType)){	//报销模块设置了开关帐
			if(EopBasedataConstant.FSSC_BASE_OPEN.equals(fdType)){//开账
				if(EopBasedataFsscUtil.dayDiff(DateUtil.convertStringToDate(fdDate, DateUtil.PATTERN_DATE), 
						DateUtil.convertStringToDate(currentDate, DateUtil.PATTERN_DATE))<0){
						//当前时间<开账时间
						auth=false; //不允许新建
					}
			}else if(EopBasedataConstant.FSSC_BASE_CLOSE.equals(fdType)){//关账
				if(EopBasedataFsscUtil.dayDiff(DateUtil.convertStringToDate(fdDate, DateUtil.PATTERN_DATE), 
						DateUtil.convertStringToDate(currentDate, DateUtil.PATTERN_DATE))>=0){
					//当前时间>关账时间
					auth=false; //不允许新建
				}
			}
			if(auth) {	//开账，允许新建
				if(StringUtil.isNotNull(fdCompanyIds)){	//开关帐公司不为空，取交集
					ownList.retainAll(companyList);
					own = ownList;
				} else {	//开关帐公司为空，取当前登录人的所属公司
					own = ownList;
				}
			} else {	//关帐，不允许新建
				if(StringUtil.isNotNull(fdCompanyIds)){	//开关帐公司不为空
					ownList.removeAll(companyList);
					own = ownList;
				}
			}
		}else{	//报销模块未设置开关帐
			own = ownList;
		}
        if(own.size()>0){
        	fsscExpenseBalance.setFdCompany(own.get(0));
        	fsscExpenseBalance.setFdCurrency(own.get(0).getFdAccountCurrency());
        	//查询公司预算币对本位币的汇率
        	Query query = getBaseDao().getHibernateSession().createQuery("select rate from "+EopBasedataExchangeRate.class.getName()+" rate left join rate.fdCompanyList comp where rate.fdIsAvailable=:fdIsAvailable and (comp.fdId=:fdCompanyId or comp.fdId is null) and rate.fdSourceCurrency.fdId=:fdSourceCurrencyId and rate.fdTargetCurrency.fdId=:fdTargetCurrencyId");
        	query.setParameter("fdIsAvailable", true);
        	query.setParameter("fdCompanyId", own.get(0).getFdId());
        	query.setParameter("fdSourceCurrencyId", own.get(0).getFdAccountCurrency().getFdId());
        	query.setParameter("fdTargetCurrencyId", own.get(0).getFdBudgetCurrency().getFdId());
        	List<EopBasedataExchangeRate> list = query.list();
        	if(ArrayUtil.isEmpty(list)){
        		fsscExpenseBalance.setFdBudgetRate(1d);
        	}else{
        		fsscExpenseBalance.setFdBudgetRate(list.get(0).getFdRate());
        	}
        	EopBasedataCostCenter costsOwn = getEopBasedataCostCenterService().findCostCenterByUserId(own.get(0).getFdId(), user.getFdId());
        	if(costsOwn!=null){
        		fsscExpenseBalance.setFdCostCenter(costsOwn);
        	}
        }
        return fsscExpenseBalance;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscExpenseBalance fsscExpenseBalance = (FsscExpenseBalance) model;
        if (fsscExpenseBalance.getDocTemplate() != null) {
        	requestContext.setAttribute("docTemplate", fsscExpenseBalance.getDocTemplate());
            dispatchCoreService.initFormSetting(form, "fsscExpenseBalance", fsscExpenseBalance.getDocTemplate(), "fsscExpenseBalance", requestContext);
        }
    }

    @Override
    public List<FsscExpenseBalance> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscExpenseBalance.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscExpenseBalance> findByFdCostCenter(EopBasedataCostCenter fdCostCenter) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscExpenseBalance.fdCostCenter.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCostCenter.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscExpenseBalance> findByFdVoucherType(EopBasedataVoucherType fdVoucherType) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscExpenseBalance.fdVoucherType.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdVoucherType.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscExpenseBalance> findByFdCurrency(EopBasedataCurrency fdCurrency) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscExpenseBalance.fdCurrency.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCurrency.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    public ILbpmProcessCoreService getLbpmProcessCoreService() {
        if (lbpmProcessCoreService == null) {
            lbpmProcessCoreService = (ILbpmProcessCoreService) SpringBeanUtil.getBean("lbpmProcessCoreService");
        }
        return lbpmProcessCoreService;
    }

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		FsscExpenseBalance main = (FsscExpenseBalance) modelObj;
		FsscExpenseBalanceCategory cate = main.getDocTemplate();
		if(!SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())){
			if(StringUtil.isNull(main.getDocNumber())){
				main.setDocNumber(sysNumberFlowService.generateFlowNumber(main));
			}
			addOrUpdateBudget(main);
		}
	   if(cate!=null&&"2".equals(cate.getFdSubjectType())&&StringUtil.isNull(main.getDocSubject())){
	       FormulaParser parser = FormulaParser.getInstance(main);
	       main.setDocSubject((String) parser.parseValueScript(cate.getFdSubjectRule()));
	        }
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		FsscExpenseBalance main = (FsscExpenseBalance) modelObj;
		FsscExpenseBalanceCategory cate=main.getDocTemplate();
		if(!SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())){
			if(StringUtil.isNull(main.getDocNumber())){
				main.setDocNumber(sysNumberFlowService.generateFlowNumber(main));
			}
			addOrUpdateBudget(main);
		}
	    if(cate!=null&&"2".equals(cate.getFdSubjectType())&&StringUtil.isNotNull(main.getDocSubject())){
	        	FormulaParser parser = FormulaParser.getInstance(main);
	        	main.setDocSubject((String) parser.parseValueScript(cate.getFdSubjectRule()));	
	       }
		super.update(modelObj);
	}

	private void addOrUpdateBudget(FsscExpenseBalance main) throws Exception{
		//没有预算模块，不进行操作
		if(!FsscCommonUtil.checkHasModule("/fssc/budget/")||getFsscBudgetOperatService()==null){
			return;
		}
		//是否启用了调账涉及预算变动
		if(!"true`".equals(EopBasedataFsscUtil.getSwitchValue("fdRebudget"))){
			return;
		}
		List<FsscExpenseBalanceDetail> list = main.getFdDetailList();
		//清除原占用预算
		JSONObject param = new JSONObject();
		param.put("fdModelId",main.getFdId());
		param.put("fdModelName",FsscExpenseBalance.class.getName());
		getFsscBudgetOperatService().deleteFsscBudgetExecute(param);
		for(FsscExpenseBalanceDetail detail:list){
			//如果是贷方，只能在流程发布时增加预算
			if(FsscExpenseConstant.FSSC_EXPENSE_VOUCHER_TYPE_PAY.equals(detail.getFdType())){
				continue;
			}
			String fdBudgetInfo = detail.getFdBudgetInfo();
			if(StringUtil.isNull(fdBudgetInfo)){
				continue;
			}
			JSONArray data = JSONArray.fromObject(fdBudgetInfo.replaceAll("'", "\""));
			JSONArray data1 = new JSONArray();
			for(int i=0;i<data.size();i++){
				JSONObject obj = data.getJSONObject(i);
				JSONObject row = new JSONObject();
				row.put("fdMoney", detail.getFdBudgetMoney());
				row.put("fdModelId", main.getFdId());
				row.put("fdModelName", FsscExpenseBalance.class.getName());
				row.put("fdBudgetId", obj.get("fdBudgetId"));
				row.put("fdDetailId", detail.getFdId());
				row.put("fdCompanyId", main.getFdCompany().getFdId());
				row.put("fdCostCenterId", detail.getFdCostCenter()==null?"":detail.getFdCostCenter().getFdId());
				row.put("fdExpenseItemId", detail.getFdExpenseItem()==null?"":detail.getFdExpenseItem().getFdId()); //由于结转情况下，需要重新匹配新的预算，所以需要传费用类型ID
				row.put("fdBudgetItemId", obj.get("fdBudgetItemId"));
				row.put("fdType", "2");
				row.put("fdProjectId", detail.getFdProject()==null?"":detail.getFdProject().getFdId());
				row.put("fdPersonId", detail.getFdPerson()==null?"":detail.getFdPerson().getFdId());
				row.put("fdCurrency", main.getFdCurrency().getFdId());
				data1.add(row);
			}
			getFsscBudgetOperatService().updateFsscBudgetExecute(data1);
		}
	}
}
