package com.landray.kmss.fssc.budgeting.service.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetItemService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataAuthUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budgeting.constant.FsscBudgetingConstant;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingMainForm;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalAuth;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingAuth;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingDetail;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingMain;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingPeriod;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingApprovalAuthService;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingAuthService;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingDetailService;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingEffectAuthService;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingMainService;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingOrgService;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingPeriodService;
import com.landray.kmss.fssc.budgeting.util.FsscBudgetingUtil;
import com.landray.kmss.fssc.budgeting.util.LoggerUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.sys.edition.interfaces.ISysEditionMainModel;
import com.landray.kmss.sys.edition.service.ISysEditionMainService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForward;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscBudgetingMainServiceImp extends ExtendDataServiceImp implements IFsscBudgetingMainService{
	
	String[] feilds={"fdTotal","fdPeriodOne","fdPeriodTwo","fdPeriodThree","fdPeriodFour","fdPeriodFive","fdPeriodSix",
			"fdPeriodSeven","fdPeriodEight","fdPeriodNine","fdPeriodTen","fdPeriodEleven","fdPeriodTwelve"};

    private ISysNotifyMainCoreService sysNotifyMainCoreService;
    
    public void setSysNotifyMainCoreService(ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

    private IFsscBudgetingPeriodService fsscBudgetingPeriodService;
    
	public void setFsscBudgetingPeriodService(IFsscBudgetingPeriodService fsscBudgetingPeriodService) {
		this.fsscBudgetingPeriodService = fsscBudgetingPeriodService;
	}
	
	protected IEopBasedataCompanyService eopBasedataCompanyService;
	
	public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}
	
	protected IFsscBudgetingAuthService fsscBudgetingAuthService;
	
	public void setFsscBudgetingAuthService(IFsscBudgetingAuthService fsscBudgetingAuthService) {
		this.fsscBudgetingAuthService = fsscBudgetingAuthService;
	}
	
	protected IEopBasedataBudgetItemService eopBasedataBudgetItemService;
	
	public void setEopBasedataBudgetItemService(IEopBasedataBudgetItemService eopBasedataBudgetItemService) {
		this.eopBasedataBudgetItemService = eopBasedataBudgetItemService;
	}
	
	protected ISysOrgCoreService sysOrgCoreService;
	
	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}
	
	protected IFsscBudgetingApprovalAuthService fsscBudgetingApprovalAuthService;
	
	public void setFsscBudgetingApprovalAuthService(
			IFsscBudgetingApprovalAuthService fsscBudgetingApprovalAuthService) {
		this.fsscBudgetingApprovalAuthService = fsscBudgetingApprovalAuthService;
	}
	
	protected IFsscBudgetingDetailService fsscBudgetingDetailService;
	
	public void setFsscBudgetingDetailService(IFsscBudgetingDetailService fsscBudgetingDetailService) {
		this.fsscBudgetingDetailService = fsscBudgetingDetailService;
	}
	
	private IFsscCommonBudgetOperatService fsscBudgetOperatService;
	
	public IFsscCommonBudgetOperatService getFsscBudgetOperatService() {
		if(fsscBudgetOperatService==null){
			fsscBudgetOperatService=(IFsscCommonBudgetOperatService) SpringBeanUtil.getBean("fsscBudgetOperatService");
		}
		return fsscBudgetOperatService;
	}
	
	private ISysEditionMainService sysEditionMainService;

	public void setSysEditionMainService(ISysEditionMainService sysEditionMainService) {
		this.sysEditionMainService = sysEditionMainService;
	}
	
	private IFsscBudgetingEffectAuthService fsscBudgetingEffectAuthService;

	public void setFsscBudgetingEffectAuthService(IFsscBudgetingEffectAuthService fsscBudgetingEffectAuthService) {
		this.fsscBudgetingEffectAuthService = fsscBudgetingEffectAuthService;
	}
	
	private IFsscBudgetingOrgService fsscBudgetingOrgService;
	
	public void setFsscBudgetingOrgService(IFsscBudgetingOrgService fsscBudgetingOrgService) {
		this.fsscBudgetingOrgService = fsscBudgetingOrgService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscBudgetingMain) {
            FsscBudgetingMain fsscBudgetingMain = (FsscBudgetingMain) model;
            FsscBudgetingMainForm fsscBudgetingMainForm = (FsscBudgetingMainForm) form;
            if (fsscBudgetingMain.getDocStatus() == null || fsscBudgetingMain.getDocStatus().startsWith("1")) {
                if (fsscBudgetingMainForm.getDocStatus() != null && (fsscBudgetingMainForm.getDocStatus().startsWith("1") || fsscBudgetingMainForm.getDocStatus().startsWith("2"))) {
                    fsscBudgetingMain.setDocStatus(fsscBudgetingMainForm.getDocStatus());
                }
            }
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscBudgetingMain fsscBudgetingMain = new FsscBudgetingMain();
        String fdOrgId=requestContext.getParameter("fdOrgId");  //对应的架构Id
        String orgType=requestContext.getParameter("orgType");  //对应的架构类型
        String fdSchemeId=requestContext.getParameter("fdSchemeId");  //预算方案ID
        fsscBudgetingMain.setFdSchemeId(fdSchemeId);
        fsscBudgetingMain.setFdStatus(FsscBudgetingConstant.FD_STATUS_DRAFT);  //新建默认为草稿状态
        String fdHierarchyId=null;
        String fdParentId=null;
    	String fdBudgetingType=EopBasedataFsscUtil.getSwitchValue("fdBudgetingType");//获取预算编制方式
        SysOrgElement user=UserUtil.getUser();
        List<EopBasedataCompany> companyList=eopBasedataCompanyService.findCompanyByUserId(user.getFdId());
        String fdCompanyId=requestContext.getParameter("fdCompanyId");
        EopBasedataCompany company=null;
        if(StringUtil.isNotNull(fdCompanyId)){
        	company=(EopBasedataCompany) eopBasedataCompanyService.findByPrimaryKey(fdCompanyId, null, true);
        }
        if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(orgType)){
        	//部门
        	SysOrgElement org=(SysOrgElement) this.findByPrimaryKey(fdOrgId, SysOrgElement.class, true);
        	fsscBudgetingMain.setFdOrgId(org.getFdId());
        	fsscBudgetingMain.setFdOrgName(org.getFdName());
        	if(company!=null){
        		fsscBudgetingMain.setFdCompany(company);
        	}else if(!ArrayUtil.isEmpty(companyList)){
        		fsscBudgetingMain.setFdCompany(companyList.get(0));
        	}
        	fdHierarchyId=org.getFdHierarchyId();  //部门层级ID，统计下级
        	fdParentId=org.getFdParent()!=null?org.getFdParent().getFdId():null;
        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(orgType)){
        	//成本中心
        	EopBasedataCostCenter org=(EopBasedataCostCenter) this.findByPrimaryKey(fdOrgId, EopBasedataCostCenter.class, true);
        	fsscBudgetingMain.setFdOrgId(org.getFdId());
        	fsscBudgetingMain.setFdOrgName(org.getFdName());
        	if(company!=null){
        		fsscBudgetingMain.setFdCompany(company);
        	}
        	fdHierarchyId=org.getFdHierarchyId();//成本中心层级ID，统计下级
        	fdParentId=org.getFdParent()!=null?org.getFdParent().getFdId():(company!=null?company.getFdId():null);
        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(orgType)){
        	//记账公司
        	EopBasedataCompany org=(EopBasedataCompany) this.findByPrimaryKey(fdOrgId, EopBasedataCompany.class, true);
        	fsscBudgetingMain.setFdOrgId(org.getFdId());
        	fsscBudgetingMain.setFdOrgName(org.getFdName());
        	if(company!=null){
        		fsscBudgetingMain.setFdCompany(company);
        	}else{
        		fsscBudgetingMain.setFdCompany(org);
        	}
        	fdHierarchyId=org.getFdId();  //公司ID
        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY_GROUP.equals(orgType)){
        	//公司组
        	EopBasedataCompanyGroup org=(EopBasedataCompanyGroup) this.findByPrimaryKey(fdOrgId, EopBasedataCompanyGroup.class, true);
        	fsscBudgetingMain.setFdOrgId(org.getFdId());
        	fsscBudgetingMain.setFdOrgName(org.getFdName());
        	if(company!=null){
        		fsscBudgetingMain.setFdCompany(company);
        	}else if(!ArrayUtil.isEmpty(companyList)){
        		fsscBudgetingMain.setFdCompany(companyList.get(0));
        	}
        }
        fsscBudgetingMain.setFdOrgType(orgType);
        FsscBudgetingPeriod period=getLastestPeriod();
        String fdPeriod=period!=null?period.getFdStartPeriod():"";
        if(StringUtil.isNotNull(fdPeriod)){
        	fsscBudgetingMain.setFdYear(fdPeriod.substring(0, 4));
        }
        if(!FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY_GROUP.equals(orgType)){
        	//判断上/下级是否全部预算编制完成
        	requestContext.setAttribute("rtnMsg", checkBudgeting(orgType,fdOrgId,fdParentId,fsscBudgetingMain.getFdYear(),fdSchemeId));
        }
        if(StringUtil.isNotNull(fdSchemeId)){
        	EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) this.findByPrimaryKey(fdSchemeId, EopBasedataBudgetScheme.class, true);
        	if(scheme!=null){
        		requestContext.setAttribute("period", scheme.getFdPeriod());
        		String fdDimension=scheme.getFdDimension()+";";
        		if(!FsscCommonUtil.isContain(fdDimension, "5;", ";")&&!FsscCommonUtil.isContain(fdDimension, "6;", ";")
        				&&!FsscCommonUtil.isContain(fdDimension, "7;", ";")&&!FsscCommonUtil.isContain(fdDimension, "10;", ";")
        				&&FsscCommonUtil.isContain(fdDimension, "8;", ";")){
        			//不包含项目、wbs、内部订单、员工，则初始化预算科目层级
        			initBudgetingDetail(fsscBudgetingMain,(Map<String, Boolean>)requestContext.getAttribute("authMap"));
        			JSONObject rtnObj=new JSONObject();
        			if(FsscBudgetingConstant.FD_BUDTING_TYPE_UP.equals(fdBudgetingType)){//自下而上
        				rtnObj=getChildAmount(fsscBudgetingMain,fdHierarchyId);//获取下级汇总金额
        				requestContext.setAttribute("childAmountJson",rtnObj.getJSONObject("child"));
            			requestContext.setAttribute("selfAmountJson",rtnObj.getJSONObject("self"));
        			}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_DOWN.equals(fdBudgetingType)){//自上而下
        				rtnObj=getParentAmount(fsscBudgetingMain,fdParentId,fdHierarchyId,"add");//获取上级预算金额和剩余预算金额
        				requestContext.setAttribute("parentAmountJson",rtnObj.getJSONObject("parent"));
            			requestContext.setAttribute("canApplyAmountJson",rtnObj.getJSONObject("canApply"));
        			}
        		}else{
        			//包含项目、wbs、内部订单、员工，则设置标识位，页面只显示新预算数据行
        			requestContext.setAttribute("oneLine", true);
        		}
        	}
        }
        FsscBudgetingUtil.initModelFromRequest(fsscBudgetingMain, requestContext);
        return fsscBudgetingMain;
    }
    
    /**
     * 校验上、下级是否预算编制完成
     * @param orgType
     * @param fdParentId 
     * @param fdYear 
     * @param fdId
     */
    protected String checkBudgeting(String orgType, String fdOrgId, String fdParentId, String fdYear,String fdSchemeId) throws Exception{
    	String renMsg="";
    	Map<String,Integer> resultMap=new HashMap<String,Integer>();  //查询结果，对应的机构ID和对应的审核文档数量
    	List<String> idList=new ArrayList<String>();  //需校验的相关的机构ID，自下而上校验下级，自上而下校验上级
    	String fdBudgetingType=EopBasedataFsscUtil.getSwitchValue("fdBudgetingType");//获取预算编制方式
    	
    	if(FsscBudgetingConstant.FD_BUDTING_TYPE_UP.equals(fdBudgetingType)){//自下而上
    		String hql=""; //需校验的相关的机构ID，自下而上校验下级，自上而下校验上级
    		StringBuilder upHql=new StringBuilder("select main.fdOrgId,count(main.fdId) from FsscBudgetingMain main where (main.fdStatus=:audited or main.fdStatus=:effect)");  //自下而上查询语句
    		upHql.append(" and main.fdYear=:fdYear");
        	if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(orgType)){
            	//部门
            	upHql.append(" and main.fdOrgId in (select org.fdId from SysOrgElement org where org.hbmParent.fdId=:fdOrgId)");
            	hql="select org.fdId from SysOrgElement org where org.hbmParent.fdId=:fdOrgId and org.fdOrgType=2";
            }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(orgType)){
            	//成本中心
            	upHql.append(" and main.fdOrgId in (select org.fdId from EopBasedataCostCenter org where org.hbmParent.fdId=:fdOrgId)");
            	hql="select org.fdId from EopBasedataCostCenter org where org.hbmParent.fdId=:fdOrgId";
            }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(orgType)){
            	//记账公司下顶级的成本中心
            	upHql.append(" and main.fdOrgId in (select org.fdId from EopBasedataCostCenter org ");
            	upHql.append(" where org.hbmParent.fdId is null and org.fdCompany.fdId=:fdOrgId)");
            	hql="select org.fdId from EopBasedataCostCenter org where org.hbmParent.fdId is null and org.fdCompany.fdId=:fdOrgId";
            }
        	upHql.append(" and main.docIsNewVersion=:docIsNewVersion");
        	upHql.append(" and main.fdSchemeId=:fdSchemeId");
        	upHql.append(" group by main.fdOrgId");
        	
        	if(StringUtil.isNotNull(hql)){
        		idList=this.getBaseDao().getHibernateSession().createQuery(hql)
        				.setParameter("fdOrgId", fdOrgId).list();
        	}
        	
    		List<Object[]> objList=this.getBaseDao().getHibernateSession().createQuery(upHql.toString())
        			.setParameter("audited", FsscBudgetingConstant.FD_STATUS_AUDITED)
        			.setParameter("effect", FsscBudgetingConstant.FD_STATUS_EFFECT)
        			.setParameter("fdYear", fdYear)
        			.setParameter("docIsNewVersion", true)
        			.setParameter("fdSchemeId", fdSchemeId)
        			.setParameter("fdOrgId", fdOrgId).list();
    		for(Object[] obj:objList){
    			if(obj[1]!=null){
    				resultMap.put(String.valueOf(obj[0]), Integer.parseInt(String.valueOf(obj[1])));
				}
    		}
    		for(String id:idList){
    			if(!(resultMap.containsKey(id)&&resultMap.get(id)>0)){
    				renMsg=ResourceUtil.getString("error.child.hasNoBudgeting", "fssc-budgeting");
    			}
    		}
    	}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_DOWN.equals(fdBudgetingType)){//自上而下
    		if(fdParentId!=null){
    			idList.add(fdParentId);
    		}
    		StringBuilder downHql=new StringBuilder("select main.fdOrgId,count(main.fdId) from FsscBudgetingMain main where (main.fdStatus=:audited or main.fdStatus=:effect)"); //自上而下查询语句
        	downHql.append(" and main.fdOrgId=:fdParentId");
        	downHql.append(" and main.fdYear=:fdYear");
        	downHql.append(" and main.docIsNewVersion=:docIsNewVersion");
        	downHql.append(" and main.fdSchemeId=:fdSchemeId");
        	downHql.append("  group by main.fdOrgId");
    		if(!FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(orgType)){//自上而下公司不做校验
    			List<Object[]> objList=this.getBaseDao().getHibernateSession().createQuery(downHql.toString())
    					.setParameter("audited", FsscBudgetingConstant.FD_STATUS_AUDITED)
            			.setParameter("effect", FsscBudgetingConstant.FD_STATUS_EFFECT)
            			.setParameter("fdYear", fdYear)
            			.setParameter("docIsNewVersion", true)
            			.setParameter("fdSchemeId", fdSchemeId)
            			.setParameter("fdParentId", fdParentId).list();
    			for(Object[] obj:objList){
        			if(obj[1]!=null){
        				resultMap.put(String.valueOf(obj[0]), Integer.parseInt(String.valueOf(obj[1])));
    				}
        		}
        		for(String id:idList){
        			if(!(resultMap.containsKey(id)&&resultMap.get(id)>0)){
        				renMsg=ResourceUtil.getString("error.parent.hasNoBudgeting", "fssc-budgeting");
        			}
        		}
    		}
    	}
    	return renMsg;
	}

	/***
     * 新建时，初始化预算编制明细
     * @param fsscBudgetingMain
     * @throws Exception
     */
	public  void initBudgetingDetail(FsscBudgetingMain fsscBudgetingMain,Map<String, Boolean> authMap) throws Exception{
		if(authMap==null){
			authMap=getViewAuth(fsscBudgetingMain.getFdOrgId());
		}
		List<EopBasedataBudgetItem> budgetItemList=getDetailBudgetItem(fsscBudgetingMain,authMap);
		List<FsscBudgetingDetail> detailList=new ArrayList<FsscBudgetingDetail>();
		for (EopBasedataBudgetItem budgetItem : budgetItemList) {
			FsscBudgetingDetail detail=new FsscBudgetingDetail();
			detail.setFdParentId(budgetItem.getFdParent()!=null?budgetItem.getFdParent().getFdId():"");
			detail.setFdBudgetItem(budgetItem);
			Map<String, Object> customPropMap=budgetItem.getCustomPropMap();
			if(customPropMap.containsKey("fdIsLastStage")){
				String fdIsLastStage=customPropMap.get("fdIsLastStage").toString();
				detail.setFdIsLastStage(fdIsLastStage);
			}
			detailList.add(detail);
		} 
		fsscBudgetingMain.setFdDetailList(detailList);
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscBudgetingMain fsscBudgetingMain = (FsscBudgetingMain) model;
    }
    /***************************************************************
     * 根据最新的有效的预算期间，判断是否存在预算编制
     * 如新建了2020年预算期间，无效，2019年是有效的，则查找2019年预算编制
     * 查找按照时间排序最近的，默认是false
     **************************************************************/
	@Override
	public boolean isView(HttpServletRequest request) throws Exception {
		boolean isView=false;
		FsscBudgetingPeriod period=getLastestPeriod();
		if(period!=null){
			//根据信息查找已经存在的预算编制
			FsscBudgetingMain main=findBudgeting(request,period);
			if(main!=null){
				isView=true;
			}
		}
		return isView;
	}
	

	/***************************************************************
	 * 查找按照时间排序最近的有效的预算编制期间
	 **************************************************************/
	public FsscBudgetingPeriod getLastestPeriod() throws Exception {
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock("fsscBudgetingPeriod.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setOrderBy("fsscBudgetingPeriod.fdStartPeriod desc");
		List<FsscBudgetingPeriod> periodList=fsscBudgetingPeriodService.findList(hqlInfo);
		if(!ArrayUtil.isEmpty(periodList)){
			return periodList.get(0);
		}else{
			return null;
		}
	}
	
	/***************************************************************
	 * 查找当前机构的预算编制Id
	 **************************************************************/
	public FsscBudgetingMain findBudgeting(HttpServletRequest request,FsscBudgetingPeriod period) 
		throws Exception{
			FsscBudgetingMain main=null;
			HQLInfo hqlInfo=new HQLInfo();
			StringBuilder whereBlock=new StringBuilder();
			whereBlock.append(" fsscBudgetingMain.fdYear=:fdYear and fsscBudgetingMain.fdOrgId=:fdOrgId");
			whereBlock.append(" and fsscBudgetingMain.fdOrgType=:fdOrgType");
			whereBlock.append(" and fsscBudgetingMain.fdSchemeId=:fdSchemeId");
			whereBlock.append(" and fsscBudgetingMain.docIsNewVersion=:docIsNewVersion");
			whereBlock.append(" and fsscBudgetingMain.fdStatus<>:fdStatus"); //不查找废弃的
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("fdYear", period.getFdStartPeriod().substring(0, 4));
			hqlInfo.setParameter("fdOrgId", request.getParameter("fdOrgId"));
			hqlInfo.setParameter("fdOrgType", request.getParameter("orgType"));
			hqlInfo.setParameter("fdSchemeId", request.getParameter("fdSchemeId"));
			hqlInfo.setParameter("docIsNewVersion", true);
			hqlInfo.setParameter("fdStatus", FsscBudgetingConstant.FD_STATUS_DISCARD);
			hqlInfo.setOrderBy("fsscBudgetingMain.docCreateTime desc");
			List<FsscBudgetingMain> mainList=this.findList(hqlInfo);
			if(!ArrayUtil.isEmpty(mainList)){
				request.setAttribute("fdId", mainList.get(0).getFdId());
				main=mainList.get(0);
			}else{
				request.setAttribute("fdId", "");
			}
			return main;
	}
	/**
	 * 根据年份、机构ID、机构类型查找对应的预算编制
	 * @param fdYear
	 * @param fdOrgId
	 * @param fdOrgType
	 * @return
	 * @throws Exception
	 */
	public FsscBudgetingMain findBudgetingMain(String fdYear,String fdOrgId,String fdOrgType) 
			throws Exception{
		FsscBudgetingMain main=null;
		HQLInfo hqlInfo=new HQLInfo();
		StringBuilder whereBlock=new StringBuilder();
		whereBlock.append(" fsscBudgetingMain.fdYear=:fdYear and fsscBudgetingMain.fdOrgId=:fdOrgId");
		whereBlock.append(" and fsscBudgetingMain.fdOrgType=:fdOrgType");
		whereBlock.append(" and fsscBudgetingMain.docIsNewVersion=:docIsNewVersion");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fdYear", fdYear);
		hqlInfo.setParameter("fdOrgId", fdOrgId);
		hqlInfo.setParameter("fdOrgType", fdOrgType);
		hqlInfo.setParameter("docIsNewVersion", true);
		hqlInfo.setOrderBy("fsscBudgetingMain.docCreateTime desc");
		List<FsscBudgetingMain> mainList=this.findList(hqlInfo);
		if(!ArrayUtil.isEmpty(mainList)){
			main=mainList.get(0);
		}
		return main;
	}
	
	/***
	 * 获取预算编制权限里的预算科目，按照层级展现
	 * @param fdCompanyId
	 * @return
	 * @throws Exception
	 */
    public List<EopBasedataBudgetItem> getDetailBudgetItem(FsscBudgetingMain main,Map<String, Boolean> authMap) throws Exception{
    	List<String> hierarchyIdList=new ArrayList<String>();
    	HQLInfo hqlInfo=new HQLInfo();
		StringBuilder whereBlock=new StringBuilder();  //HQL语句where条件
		//获取当前登录人员预算编制权限配置的预算科目
		List<String> idList=getViewBudgetItemComList(main.getFdOrgType(),main,authMap);
		if(!ArrayUtil.isEmpty(idList)){
			hqlInfo=new HQLInfo();
			whereBlock=new StringBuilder();
			hqlInfo.setJoinBlock(" left join eopBasedataBudgetItem.fdCompanyList company ");
			whereBlock.append("eopBasedataBudgetItem.fdIsAvailable=:fdIsAvailable and "+HQLUtil.buildLogicIN("eopBasedataBudgetItem.fdId", idList));
			whereBlock.append(" and (company.fdId=:fdCompanyId or company is null)");
			hqlInfo.setSelectBlock("eopBasedataBudgetItem.fdHierarchyId");
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("fdIsAvailable", true);  //有效科目
			hqlInfo.setParameter("fdCompanyId", main.getFdCompany().getFdId()); 
			hierarchyIdList=eopBasedataBudgetItemService.findList(hqlInfo);
		}
		//只需要idList包含的科目，传null为全部
		List<EopBasedataBudgetItem> rtnList=getBudgetItems(hierarchyIdList,main.getFdCompany().getFdId(),idList);
		for(Iterator<EopBasedataBudgetItem> it=rtnList.iterator();it.hasNext();){
			EopBasedataBudgetItem budgetItem=it.next();
			if(!idList.contains(budgetItem.getFdId())){
				it.remove();
			}
		}
		return rtnList;
	}
    /***
     * 从底级科目逐级解析直到最顶级科目
     * @param budgetItemList
     * @param arr
     * @param comBudgetItemMap
     * @throws Exception
     */
    public void parseToBudgetItemCom(List<EopBasedataBudgetItem> budgetItemList, JSONArray arr, Map<String, EopBasedataBudgetItem> comBudgetItemMap) 
    	throws Exception{
    	for(int i=0,len=arr.size();i<len;i++){
    		String fdIsLastStage="0";  //默认不是最末级
    		EopBasedataBudgetItem bcom=null;
    		JSONObject obj=arr.getJSONObject(i);
    		JSONArray child=new JSONArray();
    		String node=(obj.containsKey("node")&&obj.get("node")!=null)?obj.getString("node"):"";
    		if(StringUtil.isNotNull(node)){
    			 Map<String, Object> customPropMap = new HashMap<String, Object>();  //自定义属性，保存该科目是否有子级标识
    			if(comBudgetItemMap.containsKey(node)){
    				bcom=comBudgetItemMap.get(node);
    			}else{
    				bcom=(EopBasedataBudgetItem) eopBasedataBudgetItemService.findByPrimaryKey(node);
    				comBudgetItemMap.put(bcom.getFdId(), bcom);
    			}
    			if(obj.containsKey("child")){
        			child=obj.getJSONArray("child");
        			if(child.size()==0){//说明是最末级科目
        				fdIsLastStage="1";
        			}
        		}
    			customPropMap.put("fdIsLastStage", fdIsLastStage);
    			bcom.setCustomPropMap(customPropMap);
    			budgetItemList.add(bcom);
    		}
			if("0".equals(fdIsLastStage)){//不是最末级，继续解析
				parseToBudgetItemCom(budgetItemList,child,comBudgetItemMap);
			}
    	}
		
	}

	//递归查询父级信息，直到最顶级科目
    public void getBudgetItemInfo(JSONObject currTemp) throws Exception{
    	JSONObject obj=new JSONObject();
    	JSONArray arr=new JSONArray();
    	Iterator iterator = currTemp.keys();
    	String key=(String) iterator.next();
    	EopBasedataBudgetItem budgetItem=(EopBasedataBudgetItem) eopBasedataBudgetItemService.findByPrimaryKey(key,null,true);
    	if(budgetItem!=null){
    		obj.put("key", budgetItem.getFdHierarchyId());
    		obj.put("node", budgetItem.getFdId());
    		obj.put("child", currTemp.get(budgetItem.getFdId()));
    		arr.add(obj);
    		String fdParentId=budgetItem.getFdParent()!=null?budgetItem.getFdParent().getFdId():"";
    		if(StringUtil.isNotNull(fdParentId)){//父级ID不为空继续递归
    			currTemp.put(fdParentId, arr);
    			getBudgetItemInfo(currTemp);
    		}else{
    			currTemp.put(budgetItem.getFdId(), arr);
    		}
    	}
    }
    
    /**
     * 功能：根据预算科目和部门/成本中心汇总下级预算金额
     * @param fdBudgetItemComId 预算科目ID
     * @param fdOrgType  组织机构类型
     * @return
     * @throws Exception
     */
    public JSONObject getChildAmount(FsscBudgetingMain fsscBudgetingMain,String fdHierarchyId) throws Exception{
    	List<String> idList=getMainIdListUp(fsscBudgetingMain,fdHierarchyId);
    	List<String> budgetItemList=ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(fsscBudgetingMain.getFdDetailList(), "fdBudgetItem.fdId", ";")[0].split(";"));//预算科目ID
    	return getChildByMain(idList,budgetItemList,fsscBudgetingMain,"add");
    }
    
    /**
     * 获取下级汇总的对应的预算编制单据ID（自下而上）
     * @return
     * @throws Exception
     */
    public List<String> getMainIdListUp(FsscBudgetingMain fsscBudgetingMain,String fdHierarchyId) throws Exception{
    	StringBuilder hql=new StringBuilder();
    	hql=new StringBuilder();
		hql.append("select main.fdId");
		hql.append(" from FsscBudgetingMain main where main.fdOrgId in ");
    	if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fsscBudgetingMain.getFdOrgType())){
         	//部门
    		hql.append("(select org.fdId from SysOrgElement org where org.fdHierarchyId like :fdOrgHierarchyId) ");
        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(fsscBudgetingMain.getFdOrgType())){
         	//成本中心
        	hql.append("(select org.fdId from EopBasedataCostCenter org where org.fdHierarchyId like :fdOrgHierarchyId) ");
        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(fsscBudgetingMain.getFdOrgType())){
         	//记账公司，统计记账公司下的成本中心
        	hql.append("(select org.fdId from EopBasedataCostCenter org where org.fdCompany.fdId like :fdOrgHierarchyId) ");
        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY_GROUP.equals(fsscBudgetingMain.getFdOrgType())){
         	//公司组
        	hql.append("(select org.fdId from EopBasedataCompanyGroup org where org.fdHierarchyId like :fdOrgHierarchyId) ");
        }
    	 hql.append(" and main.fdYear=:fdYear ");
    	 hql.append(" and main.docIsNewVersion=:docIsNewVersion ");
    	 hql.append(" and main.fdId<>:fdId ");
    	 hql.append(" and main.fdStatus<>:draft and main.fdStatus<>:discard and main.fdStatus<>:refuse");
    	 ISysEditionMainModel docOriginDoc=fsscBudgetingMain.getDocOriginDoc();
    	 if(docOriginDoc!=null){
    		 //若是新版本，统计对应的金额需排除对应的历史版本
    		 hql.append(" and main.fdId<>:oldId ");
    		 List docHistoryEditions=fsscBudgetingMain.getDocHistoryEditions();
    		 for(int k=0;k<docHistoryEditions.size();k++){
    			 hql.append(" and main.fdId<> '"+((ISysEditionMainModel) docHistoryEditions.get(k)).getFdId()+"'");
    		 }
    	 }
    	 Query query=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
				 .setParameter("fdOrgHierarchyId", fdHierarchyId+"%")
				 .setParameter("fdYear", fsscBudgetingMain.getFdYear())
				 .setParameter("docIsNewVersion", true)
				 .setParameter("fdId", fsscBudgetingMain.getFdId())
				 .setParameter("draft", FsscBudgetingConstant.FD_STATUS_DRAFT)
				 .setParameter("discard", FsscBudgetingConstant.FD_STATUS_DISCARD)
				 .setParameter("refuse", FsscBudgetingConstant.FD_STATUS_REFUSE);
    	 if(docOriginDoc!=null){
    		 //若是新版本，统计对应的金额需排除对应的历史版本
    		 query.setParameter("oldId", docOriginDoc.getFdId());
    	 }
		return query.list();
    }
    /**
     * 功能：获取上级预算金额
     * @param fdHierarchyId 
     * @param fdOrgId  部门/成本中心 ID
     * @param fdBudgetItemComId 预算科目ID
     * @param fdOrgType  组织机构类型
     * @return
     * @throws Exception
     */
    public JSONObject getParentAmount(FsscBudgetingMain fsscBudgetingMain,String fdParentId, String fdHierarchyId,String method) throws Exception{
    	List<String> idList=getMainIdListDown(fsscBudgetingMain,fdParentId);//获取上级部门预算ID
    	if(!"add".equals(method)){
    		idList.add(fsscBudgetingMain.getFdId());
    	}
    	List<String> budgetItemList=ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(fsscBudgetingMain.getFdDetailList(), "fdBudgetItem.fdId", ";")[0].split(";"));//预算科目ID
    	return getParentByMain(idList,budgetItemList,fdParentId,fdHierarchyId,fsscBudgetingMain,method);
    }
    
    /**
     * 获取上级预算编制单据ID（自上而下）
     * @return
     * @throws Exception
     */
    public List<String> getMainIdListDown(FsscBudgetingMain fsscBudgetingMain,String fdParentId) throws Exception{
    	StringBuilder hql=new StringBuilder();
    	hql=new StringBuilder();
    	hql.append("select main.fdId");
    	hql.append(" from FsscBudgetingMain main where main.fdOrgId=:fdParentId ");
    	hql.append(" and main.fdYear=:fdYear ");
    	hql.append(" and main.docIsNewVersion=:docIsNewVersion ");
    	hql.append(" and main.fdId<>:fdId ");
    	hql.append(" and main.fdStatus<>:draft and main.fdStatus<>:discard and main.fdStatus<>:refuse");
    	ISysEditionMainModel docOriginDoc=fsscBudgetingMain.getDocOriginDoc();
    	if(docOriginDoc!=null){
    		//若是新版本，统计对应的金额需排除对应的历史版本
    		hql.append(" and main.fdId<>:oldId ");
    		List docHistoryEditions=fsscBudgetingMain.getDocHistoryEditions();
    		for(int k=0;k<docHistoryEditions.size();k++){
    			hql.append(" and main.fdId<> '"+((ISysEditionMainModel) docHistoryEditions.get(k)).getFdId()+"'");
    		}
    	}
    	Query query=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
    			.setParameter("fdParentId", fdParentId)
    			.setParameter("fdYear", fsscBudgetingMain.getFdYear())
    			.setParameter("docIsNewVersion", true)
    			.setParameter("fdId", fsscBudgetingMain.getFdId())
    			.setParameter("draft", FsscBudgetingConstant.FD_STATUS_DRAFT)
    			.setParameter("discard", FsscBudgetingConstant.FD_STATUS_DISCARD)
    			.setParameter("refuse", FsscBudgetingConstant.FD_STATUS_REFUSE);
    	if(docOriginDoc!=null){
    		//若是新版本，统计对应的金额需排除对应的历史版本
    		query.setParameter("oldId", docOriginDoc.getFdId());
    	}
		return query.list();
    }
    /**
     * 获取同级预算编制单据ID（自上而下）
     * @return
     * @throws Exception
     */
    public List<String> getBrotherMainIdList(FsscBudgetingMain fsscBudgetingMain,String fdParentId) throws Exception{
    	StringBuilder hql=new StringBuilder();
    	hql=new StringBuilder();
    	hql.append("select main.fdId");
    	hql.append(" from FsscBudgetingMain main where main.fdOrgId in ");
    	if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fsscBudgetingMain.getFdOrgType())){
         	//部门,统计同一级的部门已分配预算
    		hql.append("(select org.fdId from SysOrgElement org left join  org.hbmParent parent where parent.fdId = :fdParentId) ");
        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(fsscBudgetingMain.getFdOrgType())){
         	//成本中心,统计同一级的成本中心已分配预算
        	hql.append("(select org.fdId from EopBasedataCostCenter org left join org.hbmParent parent left join  org.fdCompanyList company where (parent.fdId = :fdParentId or (company.fdId = :fdParentId and parent.fdId is null))) ");
        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(fsscBudgetingMain.getFdOrgType())){
         	//记账公司，统计同一公司组下的记账公司
        	hql.append("(select org.fdId from EopBasedataCompany org where org.fdGroup.fdId = :fdParentId) ");
        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY_GROUP.equals(fsscBudgetingMain.getFdOrgType())){
         	//公司组
        	hql.append("(select org.fdId from EopBasedataCompanyGroup org where org.hbmParent.fdId = :fdParentId) ");
        }
    	hql.append(" and main.fdYear=:fdYear ");
    	hql.append(" and main.docIsNewVersion=:docIsNewVersion ");
    	hql.append(" and main.fdId<>:fdId ");
    	hql.append(" and main.fdStatus<>:draft and main.fdStatus<>:discard and main.fdStatus<>:refuse");
    	ISysEditionMainModel docOriginDoc=fsscBudgetingMain.getDocOriginDoc();
    	if(docOriginDoc!=null){
    		//若是新版本，统计对应的金额需排除对应的历史版本
    		hql.append(" and main.fdId<>:oldId ");
    		List docHistoryEditions=fsscBudgetingMain.getDocHistoryEditions();
    		for(int k=0;k<docHistoryEditions.size();k++){
    			hql.append(" and main.fdId<> '"+((ISysEditionMainModel) docHistoryEditions.get(k)).getFdId()+"'");
    		}
    	}
    	Query query=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
    			.setParameter("fdParentId", fdParentId)
    			.setParameter("fdYear", fsscBudgetingMain.getFdYear())
    			.setParameter("docIsNewVersion", true)
    			.setParameter("fdId", fsscBudgetingMain.getFdId())
    			.setParameter("draft", FsscBudgetingConstant.FD_STATUS_DRAFT)
    			.setParameter("discard", FsscBudgetingConstant.FD_STATUS_DISCARD)
    			.setParameter("refuse", FsscBudgetingConstant.FD_STATUS_REFUSE);
    	if(docOriginDoc!=null){
    		//若是新版本，统计对应的金额需排除对应的历史版本
    		query.setParameter("oldId", docOriginDoc.getFdId());
    	}
    	return query.list();
    }
    
    public Double getObjVale(Object val) throws Exception{
    	return val!=null?(Double.parseDouble(val.toString())):0.0;
    }
    
    /**
     * 查看/编辑/新版本页面显示预算编制页面，领导是显示本部门所有的编制，编制者只能查看自己所编制的预算
     * @param method 
     */
	@Override
	public void completeDetail(HttpServletRequest request,FsscBudgetingMain main, String method, Map<String, Boolean> authMap) throws Exception {
		String fdBudgetingType=EopBasedataFsscUtil.getSwitchValue("fdBudgetingType");//获取预算编制方式
		List<String> idList=new ArrayList<String>();
		List<String> budgetItemList=getViewBudgetItemComList(request.getParameter("orgType"),main,authMap);//当前有权限的科目ID
		List<String> projectList=getViewProjectList(request.getParameter("orgType"),main,authMap);//获取有权限的项目ID
		idList.add(main.getFdId()); //查看页面统计当前主表ID
		String fdHierarchyId=null;
		String fdParentId=null;  //组织上级ID
		String fdOrgId=main.getFdOrgId();  //对应的架构Id
        String orgType=main.getFdOrgType();
        if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(orgType)){
        	//部门
        	SysOrgElement org=(SysOrgElement) this.findByPrimaryKey(fdOrgId, SysOrgElement.class, true);
        	fdHierarchyId=org.getFdHierarchyId();  //部门层级ID，统计下级
        	fdParentId=org.getFdParent()!=null?org.getFdParent().getFdId():null;
        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(orgType)){
        	//成本中心
        	EopBasedataCostCenter org=(EopBasedataCostCenter) this.findByPrimaryKey(fdOrgId, EopBasedataCostCenter.class, true);
        	fdHierarchyId=org.getFdHierarchyId();//成本中心层级ID，统计下级
        	fdParentId=org.getFdParent()!=null?org.getFdParent().getFdId():null;
        	if(StringUtil.isNull(fdParentId)){//为空为公司下第一级成本中心
        		String fdCompanyId=request.getParameter("fdCompanyId");
        		EopBasedataCompany company=(EopBasedataCompany) this.findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
        		if(company!=null) {
        			fdParentId=company.getFdId();
        		}
        	}
        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(orgType)){
        	//记账公司
        	EopBasedataCompany org=(EopBasedataCompany) this.findByPrimaryKey(fdOrgId, EopBasedataCompany.class, true);
        	fdHierarchyId=org.getFdId();  //公司ID
        	fdParentId=org.getFdId();
        }
        JSONObject rtnObj=new JSONObject();
        JSONObject childObj=new JSONObject();  //下级预算额，自上而下是表示下级科目，自下而上表示下级科目和下级部门
        EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) this.findByPrimaryKey(main.getFdSchemeId(), EopBasedataBudgetScheme.class, true);
        if(scheme!=null){
        	String fdDimension=scheme.getFdDimension()+";";
    		if(!FsscCommonUtil.isContain(fdDimension, "5;", ";")&&!FsscCommonUtil.isContain(fdDimension, "6;", ";")
    				&&!FsscCommonUtil.isContain(fdDimension, "7;", ";")&&!FsscCommonUtil.isContain(fdDimension, "10;", ";")
    				&&FsscCommonUtil.isContain(fdDimension, "8;", ";")){
    			//不包含项目、wbs、内部订单、员工，包含预算科目维度，则初始化预算科目层级
    			if(FsscBudgetingConstant.FD_BUDTING_TYPE_UP.equals(fdBudgetingType)){ //自下而上
    	        	idList.addAll(getMainIdListUp(main,fdHierarchyId));
    	        	if(!ArrayUtil.isEmpty(budgetItemList)){
    	        		if("newEdition".equals(method)){//新版本排除当前最新版本的ID
    	        			 for (Iterator<String> ite = idList.iterator(); ite.hasNext();) {
    	        		            String id = ite.next();
    	        		            if (id.equals(request.getParameter("originId"))) {
    	        		                ite.remove();
    	        		            }
    	        		        }
    	        		}
    	        		rtnObj=getChildByMain(idList,budgetItemList,main,method);
    	        		request.setAttribute("selfAmountJson",rtnObj.getJSONObject("self"));
    	        		childObj=rtnObj.getJSONObject("child");
    	        	    request.setAttribute("childAmountJson",childObj);	
    	        	}
    	        }else if(FsscBudgetingConstant.FD_BUDTING_TYPE_DOWN.equals(fdBudgetingType)){ //自上而下
    	        	if("initView".equals(method)){//机构/公司查看
    	        		rtnObj=budgetingInitTotal(request, main, method);
    	        		childObj=rtnObj.getJSONObject("child");
    	        	    request.setAttribute("childAmountJson",childObj);
    	        	}else if("childView".equals(method)){//非机构/公司查看
    	        		rtnObj=getParentAmount(main, fdParentId, fdHierarchyId,"view");
    	        		request.setAttribute("parentAmountJson",rtnObj.containsKey("parent")?rtnObj.get("parent"):null);
    	        		request.setAttribute("canApplyAmountJson",rtnObj.containsKey("canApply")?rtnObj.get("canApply"):null);
    	        	}
    	        }
    		}else{
    			//包含项目、wbs、内部订单、员工，则设置标识位，页面只显示新预算数据行
    			request.setAttribute("oneLine", true);
    		}
        }
		List<FsscBudgetingDetail> detailList=main.getFdDetailList();
		List<FsscBudgetingDetail> newList=new ArrayList<FsscBudgetingDetail>();
		for(FsscBudgetingDetail detail:detailList){
			String fdBudgetItemId=detail.getFdBudgetItem()!=null?detail.getFdBudgetItem().getFdId():"";
			if(StringUtil.isNotNull(fdBudgetItemId)){
				if(!budgetItemList.contains(fdBudgetItemId)){
					continue;
				}
				if(FsscCommonUtil.isContain(scheme.getFdDimension(), "5;", ";")){
					String fdProjectId=detail.getFdProject()!=null?detail.getFdProject().getFdId():"";
					if(!projectList.contains(fdProjectId)){
						continue;
					}
				}
				String fdIsLastStage=detail.getFdIsLastStage();
				if(FsscBudgetingConstant.FSSC_BUDGETING_LASTSTAGE_NO.equals(fdIsLastStage)
						&&FsscBudgetingConstant.FD_BUDTING_TYPE_UP.equals(fdBudgetingType)){
					//同一个部门/成本中心多个人编制预算会导致上级科目单个编制主表数据并不准确，故重新计算
					if(childObj.containsKey(fdBudgetItemId)&&childObj.get(fdBudgetItemId)!=null){
						List valList=(List) childObj.get(fdBudgetItemId);
						for(int i=0,len=feilds.length;i<len;i++){
							PropertyUtils.setProperty(detail, feilds[i], valList.get(i));
						}
					}else{
						for(int i=0,len=feilds.length;i<len;i++){
							PropertyUtils.setProperty(detail, feilds[i], 0.00);
						}
					}
				}
			}
			newList.add(detail);
		}
		main.setFdDetailList(newList);
		//统计季度、半年度、年度预算
		Map<String,List> rtnMap=new ConcurrentHashMap<>();
		 if(FsscBudgetingConstant.FD_BUDTING_TYPE_UP.equals(fdBudgetingType)){ //自下而上
			 rtnMap=getAllTargetData(main,rtnObj,budgetItemList);
		 }else if(FsscBudgetingConstant.FD_BUDTING_TYPE_DOWN.equals(fdBudgetingType)){ //自上而下
			 if("initView".equals(method)){//机构/公司查看
				 rtnMap=getInitIndepcollectData(main);
	        	}else if("childView".equals(method)){//非机构/公司查看
	        		 rtnMap=getDowncollectData(main,rtnObj);
	        	}
        }else if(FsscBudgetingConstant.FD_BUDTING_TYPE_INDEPENDENT.equals(fdBudgetingType)){//独立预算编制
        	rtnMap=getInitIndepcollectData(main);
        }
		request.setAttribute("quarterList", rtnMap.get("quarter"));
		request.setAttribute("halfYearList", rtnMap.get("halfYear"));
		request.setAttribute("yearList", rtnMap.get("year"));
		if(!FsscCommonUtil.isContain(scheme.getFdDimension()+";", "8;", ";")){//无科目，标识可勾选。若是无权限没数据也不影响勾选状态
			 request.setAttribute("noBudgetItem", true);
		 }
	}
	
	/**
	 * 获取需展现的科目列表，若是编制者则展现当前文档的科目，若是部门领导则展现所有的科目
	 * @param request
	 * @param main
	 * @param authMap 
	 * @return
	 * @throws Exception
	 */
	public List<String> getViewBudgetItemComList(String fdOrgType,FsscBudgetingMain main, Map<String, Boolean> authMap) throws Exception{
		//默认当前文档科目
		List<String> budgetItemList=new ArrayList<String>();
		 // 判断当前登录人是否为部门领导，是否拥有预算编制权限，获取对应的科目
		 boolean isLeader=false;  //默认非部门/成本中心领导
		 if(authMap.containsKey("total")){
			 isLeader=authMap.get("total");  //统计全部则权限默认为领导权限，即统计所有科目
		 }
		 boolean authFlag=authMap.containsKey("authFlag")?authMap.get("authFlag"):false;  //默认无权限查看
		 boolean budgetAuth=authMap.containsKey("budgetAuth")?authMap.get("budgetAuth"):false;  //默认非预算编制者
		 boolean approvalAuth=authMap.containsKey("approvalAuth")?authMap.get("approvalAuth"):false;  //默认非审核者
		 boolean effectAuth=authMap.containsKey("effectAuth")?authMap.get("effectAuth"):false;  //默认非生效者
	
		 SysOrgElement user=UserUtil.getUser();
		 if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)){
         	//部门
    		SysOrgElement org=(SysOrgElement) this.findByPrimaryKey(main.getFdOrgId(), SysOrgElement.class, true);
    		if(org!=null){
    			SysOrgElement thisLeader=org.getHbmThisLeader();  //获取部门领导
    			List<SysOrgElement> orgList=new ArrayList<SysOrgElement>();
    			orgList.add(thisLeader);
    			List<String> ids=sysOrgCoreService.expandToPersonIds(orgList);
    			if(ids.contains(user.getFdId())){
    				isLeader=true;  //说明为部门领导
    			}
    		}
         }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(fdOrgType)){
         	//成本中心
        	 EopBasedataCostCenter org=(EopBasedataCostCenter) this.findByPrimaryKey(main.getFdOrgId(), EopBasedataCostCenter.class, true);
        	 List<SysOrgPerson> firstCharger=org.getFdFirstCharger();  //成本中心第一负责人
        	 List<SysOrgPerson> secondCharger=org.getFdSecondCharger(); //成本中心第二负责人
        	 if(firstCharger.contains(user)||secondCharger.contains(user)){
        		 isLeader=true;  //说明为成本中心负责人
        	 }
         }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(fdOrgType)){
         	//记账公司
        	 EopBasedataCompany org=(EopBasedataCompany) this.findByPrimaryKey(main.getFdOrgId(), EopBasedataCompany.class, true);
        	 List<SysOrgPerson> orgList=org.getFdFinancialManager();
        	 List<String> ids=sysOrgCoreService.expandToPersonIds(orgList);
    			if(ids.contains(user.getFdId())){
    				isLeader=true;  //说明为记账公司财务管理员
    			}
         }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY_GROUP.equals(fdOrgType)){
         	//公司组
         }
		 if(isLeader||checkBudgetingViewAuth(main.getFdId())){//如果是部门领导或者财务人员、查看权限、有上级部门审批权限则显示所有的科目
			 budgetItemList=getAllBudgetItem(main);
		 }else if(budgetAuth&&(FsscBudgetingConstant.FD_STATUS_DRAFT.equals(main.getFdStatus())
				 ||FsscBudgetingConstant.FD_STATUS_REFUSE.equals(main.getFdStatus()))){//编制者，则显示编制权限科目
			 budgetItemList=getBudgetItemComIds(main,"FsscBudgetingAuth");
		 }else if(approvalAuth&&FsscBudgetingConstant.FD_STATUS_EXAMINE.equals(main.getFdStatus())){//审核权限科目
			 budgetItemList=getBudgetItemComIds(main,"FsscBudgetingApprovalAuth");
		 }else if(effectAuth&&FsscBudgetingConstant.FD_STATUS_AUDITED.equals(main.getFdStatus())){//生效权限
			 budgetItemList=getAllBudgetItem(main);
		 }else if(authFlag){//单纯查看权限
			 ArrayUtil.concatTwoList(getBudgetItemComIds(main,"FsscBudgetingAuth"), budgetItemList);
			 ArrayUtil.concatTwoList(getBudgetItemComIds(main,"FsscBudgetingApprovalAuth"), budgetItemList);
			 if(effectAuth){
				 ArrayUtil.concatTwoList(getAllBudgetItem(main), budgetItemList);
			 }
		 }
		return budgetItemList;
	}
	
	/**
	 * 获取编制权限/编制审核/预算生效，预算科目ID
	 * @param main
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<String> getBudgetItemComIds(FsscBudgetingMain main,String tableName) throws Exception{
		List<String> rtnList=new ArrayList<String>();
		StringBuilder hql=new StringBuilder();
		hql.append("select distinct budgetItem.fdCode from "+tableName+" t ");
		hql.append(" left join t.fdCompanyList company ");
		hql.append(" left join t.fdPersonList person ");
		hql.append(" left join t.fdOrgList org ");
		hql.append(" left join t.fdCostCenterList center ");
		hql.append(" left join t.fdProjectList project ");
		hql.append(" left join t.fdBudgetItemList budgetItem ");
		hql.append(" where t.fdIsAvailable=:fdIsAvailable");
		hql.append(" and person.fdId=:fdPersonId");
		hql.append(" and (org.fdId=:fdOrgId");
		hql.append("  or company.fdId=:fdOrgId ");
		hql.append("  or center.fdId=:fdOrgId ");
		hql.append("  or project.fdId=:fdOrgId) ");
		List<String> codeList=fsscBudgetingAuthService.getBaseDao().getHibernateSession().createQuery(hql.toString())
				.setParameter("fdIsAvailable", true)
				.setParameter("fdOrgId", main.getFdOrgId())
				.setParameter("fdPersonId", UserUtil.getUser().getFdId()).list();
		if(!ArrayUtil.isEmpty(codeList)){
			HQLInfo hqlInfo=new HQLInfo();
			hqlInfo.setJoinBlock(" left join eopBasedataBudgetItem.fdCompanyList company ");
			StringBuilder whereBlock=new StringBuilder();
			whereBlock.append("eopBasedataBudgetItem.fdIsAvailable=:fdIsAvailable ");
			whereBlock.append(" and (company.fdId=:fdCompanyId or company is null)");
			whereBlock.append(" and "+HQLUtil.buildLogicIN("eopBasedataBudgetItem.fdCode", codeList));
			hqlInfo.setSelectBlock("eopBasedataBudgetItem.fdId");
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("fdIsAvailable", true);  //公司下科目是有效状态
			hqlInfo.setParameter("fdCompanyId", main.getFdCompany()!=null?main.getFdCompany().getFdId():""); 
			rtnList=eopBasedataBudgetItemService.findList(hqlInfo);
		}
		return rtnList;
	}
	/**
	 * 获取需展现的科目列表，若是编制者则展现当前文档的科目，若是部门领导则展现所有的科目
	 * @param request
	 * @param main
	 * @param authMap 
	 * @return
	 * @throws Exception
	 */
	public List<String> getViewProjectList(String fdOrgType,FsscBudgetingMain main, Map<String, Boolean> authMap) throws Exception{
		//默认当前文档项目
		List<String> projectList=new ArrayList<String>();
		// 判断当前登录人是否为部门领导，是否拥有预算编制权限，获取对应的项目
		boolean isLeader=false;  //默认非部门/成本中心领导
		if(authMap.containsKey("total")){
			isLeader=authMap.get("total");  //统计全部则权限默认为领导权限，即统计所有项目
		}
		boolean authFlag=authMap.containsKey("authFlag")?authMap.get("authFlag"):false;  //默认无权限查看
		boolean budgetAuth=authMap.containsKey("budgetAuth")?authMap.get("budgetAuth"):false;  //默认非预算编制者
		boolean approvalAuth=authMap.containsKey("approvalAuth")?authMap.get("approvalAuth"):false;  //默认非审核者
		boolean effectAuth=authMap.containsKey("effectAuth")?authMap.get("effectAuth"):false;  //默认非生效者
		
		SysOrgElement user=UserUtil.getUser();
		if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)){
			//部门
			SysOrgElement org=(SysOrgElement) this.findByPrimaryKey(main.getFdOrgId(), SysOrgElement.class, true);
			if(org!=null){
				SysOrgElement thisLeader=org.getHbmThisLeader();  //获取部门领导
				List<SysOrgElement> orgList=new ArrayList<SysOrgElement>();
				orgList.add(thisLeader);
				List<String> ids=sysOrgCoreService.expandToPersonIds(orgList);
				if(ids.contains(user.getFdId())){
					isLeader=true;  //说明为部门领导
				}
			}
		}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(fdOrgType)){
			//成本中心
			EopBasedataCostCenter org=(EopBasedataCostCenter) this.findByPrimaryKey(main.getFdOrgId(), EopBasedataCostCenter.class, true);
			List<SysOrgPerson> firstCharger=org.getFdFirstCharger();  //成本中心第一负责人
			List<SysOrgPerson> secondCharger=org.getFdSecondCharger(); //成本中心第二负责人
			if(firstCharger.contains(user)||secondCharger.contains(user)){
				isLeader=true;  //说明为成本中心负责人
			}
		}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(fdOrgType)){
			//记账公司
			EopBasedataCompany org=(EopBasedataCompany) this.findByPrimaryKey(main.getFdOrgId(), EopBasedataCompany.class, true);
			List<SysOrgPerson> orgList=org.getFdFinancialManager();
			List<String> ids=sysOrgCoreService.expandToPersonIds(orgList);
			if(ids.contains(user.getFdId())){
				isLeader=true;  //说明为记账公司财务管理员
			}
		}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY_GROUP.equals(fdOrgType)){
			//公司组
		}
		 if(isLeader||checkBudgetingViewAuth(main.getFdId())){//如果是部门领导或者财务人员、查看权限、有上级部门审批权限则显示所有的项目
			List<FsscBudgetingDetail> detailList=main.getFdDetailList();
			for(FsscBudgetingDetail detail:detailList){
				if(detail.getFdProject()!=null){
					projectList.add(detail.getFdProject().getFdId());
				}
			}
		}else if(budgetAuth&&(FsscBudgetingConstant.FD_STATUS_DRAFT.equals(main.getFdStatus())
				||FsscBudgetingConstant.FD_STATUS_REFUSE.equals(main.getFdStatus()))){//编制者，则显示编制权限项目
			projectList=getProjectIds(main,"FsscBudgetingAuth");
		}else if(approvalAuth&&FsscBudgetingConstant.FD_STATUS_EXAMINE.equals(main.getFdStatus())){//审核权限项目
			projectList=getProjectIds(main,"FsscBudgetingApprovalAuth");
		}else if(effectAuth&&FsscBudgetingConstant.FD_STATUS_AUDITED.equals(main.getFdStatus())){//生效权限
			List<FsscBudgetingDetail> detailList=main.getFdDetailList();
			for(FsscBudgetingDetail detail:detailList){
				if(detail.getFdProject()!=null){
					projectList.add(detail.getFdProject().getFdId());
				}
			}
		}else if(authFlag){//单纯查看权限
			ArrayUtil.concatTwoList(getProjectIds(main,"FsscBudgetingAuth"), projectList);
			ArrayUtil.concatTwoList(getProjectIds(main,"FsscBudgetingApprovalAuth"), projectList);
			if(effectAuth){
				List<String> tempList=new ArrayList<String>();
				List<FsscBudgetingDetail> detailList=main.getFdDetailList();
				for(FsscBudgetingDetail detail:detailList){
					if(detail.getFdProject()!=null){
						tempList.add(detail.getFdProject().getFdId());
					}
				}
				ArrayUtil.concatTwoList(tempList, projectList);
			}
		}
		return projectList;
	}
	
	/**
	 * 获取编制权限/编制审核/预算生效，预算科目ID
	 * @param main
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<String> getProjectIds(FsscBudgetingMain main,String tableName) throws Exception{
		List<String> rtnList=new ArrayList<String>();
		StringBuilder hql=new StringBuilder();
		hql.append("select distinct t from "+tableName+" t ");
		hql.append(" left join t.fdCompanyList company ");
		hql.append(" left join t.fdPersonList person ");
		hql.append(" left join t.fdOrgList org ");
		hql.append(" left join t.fdCostCenterList center ");
		hql.append(" left join t.fdProjectList project ");
		hql.append(" where t.fdIsAvailable=:fdIsAvailable");
		hql.append(" and person.fdId=:fdPersonId");
		hql.append(" and (org.fdId=:fdOrgId");
		hql.append("  or company.fdId=:fdOrgId ");
		hql.append("  or center.fdId=:fdOrgId )");
		List authList=fsscBudgetingAuthService.getBaseDao().getHibernateSession().createQuery(hql.toString())
				.setParameter("fdIsAvailable", true)
				.setParameter("fdOrgId", main.getFdOrgId())
				.setParameter("fdPersonId", UserUtil.getUser().getFdId()).list();
		if(!ArrayUtil.isEmpty(authList)){//有权限配置，获取项目配置
			if("FsscBudgetingAuth".equals(tableName)){
				for(int k=0,size=authList.size();k<size;k++){
					FsscBudgetingAuth auth=(FsscBudgetingAuth) authList.get(k);
					List<EopBasedataProject> projectList=auth.getFdProjectList();
					for(EopBasedataProject project:projectList){
						rtnList.add(project.getFdId());
					}
				}
			}else if("FsscBudgetingApprovalAuth".equals(tableName)){
				for(int k=0,size=authList.size();k<size;k++){
					FsscBudgetingApprovalAuth auth=(FsscBudgetingApprovalAuth) authList.get(k);
					List<EopBasedataProject> projectList=auth.getFdProjectList();
					for(EopBasedataProject project:projectList){
						if(!rtnList.contains(project.getFdId())){
							rtnList.add(project.getFdId());
						}
					}
				}
			}
			//有权限配置，但是项目未配置，则不过滤
			if(ArrayUtil.isEmpty(rtnList)){
				List<FsscBudgetingDetail> detailList=main.getFdDetailList();
				for(FsscBudgetingDetail detail:detailList){
					if(detail.getFdProject()!=null){
						rtnList.add(detail.getFdProject().getFdId());
					}
				}
			}
		}
		return rtnList;
	}
	
	public List<String> getAllBudgetItem(FsscBudgetingMain main) throws Exception{
		List<String> idList=new ArrayList<String>();
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setJoinBlock(" left join eopBasedataBudgetItem.fdCompanyList company ");
		StringBuilder whereBlock=new StringBuilder();
		whereBlock.append("eopBasedataBudgetItem.fdIsAvailable=:fdIsAvailable ");
		whereBlock.append(" and (company.fdId=:fdCompanyId or company is null)");
		hqlInfo.setSelectBlock("eopBasedataBudgetItem.fdHierarchyId");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);  //有效科目
		hqlInfo.setParameter("fdCompanyId", main.getFdCompany()!=null?main.getFdCompany().getFdId():""); 
		List<String> hierarchyIdList=eopBasedataBudgetItemService.findList(hqlInfo);
		List<EopBasedataBudgetItem> BudgetItemComList=getBudgetItems(hierarchyIdList,main.getFdCompany()!=null?main.getFdCompany().getFdId():"",null);
		if(!ArrayUtil.isEmpty(BudgetItemComList)){
			idList=ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(BudgetItemComList, "fdId", ";")[0].split(";"));//预算科目ID
		}
		return idList;
	}

	/**
	 * 根据参数逐级查询层级关系科目
	 * @param hierarchyIdList
	 * @param fdCompanyId 
	 * @param idList 
	 * @return
	 * @throws Exception
	 */
	public List<EopBasedataBudgetItem> getBudgetItems(List<String> hierarchyIdList, String fdCompanyId, List<String> idList) throws Exception{
		List<EopBasedataBudgetItem> budgetItemList=new ArrayList<EopBasedataBudgetItem>();
		//查找权限设置的科目和其下级科目
		StringBuilder whereBlock=new StringBuilder();
		HQLInfo hqlInfo=new HQLInfo();
		if(!ArrayUtil.isEmpty(hierarchyIdList)){
			Map<String,EopBasedataBudgetItem> comBudgetItemMap=new ConcurrentHashMap();
			JSONObject currTemp=new JSONObject();  //当前层级的信息
			JSONObject nextTemp=new JSONObject();  //当前层级的下一级信息
			int lenTemp=0;  //中间长度临时变量
			whereBlock=new StringBuilder();
			String where="";
			for (int i=0,len=hierarchyIdList.size();i<len;i++) {
				where=StringUtil.linkString(where, " or ", "eopBasedataBudgetItem.fdHierarchyId like '%"+hierarchyIdList.get(i)+"'");
			}
			if(StringUtil.isNotNull(where)){
				whereBlock.append("(").append(where).append(")");
			}
			whereBlock.append(" and eopBasedataBudgetItem.fdIsAvailable=:fdIsAvailable");
			whereBlock.append(" and (company.fdId=:fdCompanyId or company is null)");
			hqlInfo=new HQLInfo();
			hqlInfo.setJoinBlock(" left join eopBasedataBudgetItem.fdCompanyList company");
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("fdIsAvailable", true);
			hqlInfo.setParameter("fdCompanyId", fdCompanyId); 
			hqlInfo.setOrderBy("length(eopBasedataBudgetItem.fdHierarchyId) desc");
			List<EopBasedataBudgetItem> comBudgetItemList=eopBasedataBudgetItemService.findList(hqlInfo);
			//倒序，层级最低的在最前面
			for(EopBasedataBudgetItem comBudgetItem:comBudgetItemList){
				comBudgetItemMap.put(comBudgetItem.getFdId(), comBudgetItem);
				int length=comBudgetItem.getFdHierarchyId().length();   //层级长度，根据层级长度的变化确认是否层级往上变化了。若无默认为最高级
				if(Math.abs(lenTemp-length)>0.001){//判断lenTemp和length是不是相等，整数相减大于0.001认为不相等
					lenTemp=length;
					nextTemp=currTemp;  //层级变了，将本层级的json对象赋值给下级接送对象
					currTemp=new JSONObject();  //层级发生变化重新初始化本层级对象
				}
				String fdParentId=comBudgetItem.getFdParent()!=null?comBudgetItem.getFdParent().getFdId():"";
				JSONArray arr=new JSONArray();
				if(currTemp.containsKey(fdParentId)){
					//若是存在相同父级的id，直接获取数组，添加在一起
					arr=currTemp.getJSONArray(fdParentId);
				}
				JSONObject json=new JSONObject();
				json.put("key", comBudgetItem.getFdHierarchyId());
				json.put("node", comBudgetItem.getFdId());
				if(!nextTemp.isEmpty()&&nextTemp.containsKey(comBudgetItem.getFdId())){
					json.put("child", nextTemp.get(comBudgetItem.getFdId()));
				}else{
					json.put("child", new JSONArray());
				}
				arr.add(json);
				//若是无上级ID说明到了最高级，无需拼接json数组，parentId由科目ID+||字符串，即不做处理
				if(StringUtil.isNotNull(fdParentId)){
					currTemp.put(fdParentId, arr); 
				}else{
					currTemp.put(comBudgetItem.getFdId()+"||", arr); 
				}
			}
			//递归获取所有科目，直到最顶级
			Iterator iterator = currTemp.keys();
			 while(iterator.hasNext()){
				 String key=(String) iterator.next();
				 if(key.indexOf("||")>-1){
					 continue;
				 }else{
					 getBudgetItemInfo(currTemp);
				 }
			 }
			 iterator = currTemp.keys();
			 while(iterator.hasNext()){
				 String key=(String) iterator.next();
				 JSONArray arr=currTemp.getJSONArray(key);
				 //解析json获取预算科目类表
				 parseToBudgetItemCom(budgetItemList,arr,comBudgetItemMap);
			 }
		}
		return budgetItemList;
	}
	/**
	 * 统计各个科目汇总金额，若是有主文档ID说明只是查找主文档对应的科目，若是没有，则统计所有最新版本的主文档的科目汇总，
	 * @param idList
	 * @param budgetItemList
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public JSONObject getChildByMain(List<String> idList,List<String> budgetItemList,FsscBudgetingMain main,
			String method) throws Exception{
		JSONObject rtnObj=new JSONObject();  //返回值
		JSONObject childObj=new JSONObject();  //子级汇总
		JSONObject selfObj=new JSONObject();  //当前科目下级部门/成本中心汇总
		List valueList=new ArrayList();
		 //由于查询会出现main.fdDetailList.fdBudgetItem.fdId，导致查询数据库不支持，故分开查询
	   	 if(!ArrayUtil.isEmpty(idList)||"view".equals(method)){
	   		 StringBuilder hql=new StringBuilder();
	   		 hql.append("sum(detail.fdTotal) as fdTotal, ");
	   		 hql.append("sum(detail.fdPeriodOne) as fdPeriodOne,sum(detail.fdPeriodTwo) as fdPeriodTwo,");
	   		 hql.append("sum(detail.fdPeriodThree) as fdPeriodThree,sum(detail.fdPeriodFour) as fdPeriodFour,");
	   		 hql.append("sum(detail.fdPeriodFive) as fdPeriodFive,sum(detail.fdPeriodSix) as fdPeriodSix,");
	   		 hql.append("sum(detail.fdPeriodSeven) as fdPeriodSeven,sum(detail.fdPeriodEight) as fdPeriodEight,");
	   		 hql.append("sum(detail.fdPeriodNine) as fdPeriodNine,sum(detail.fdPeriodTen) as fdPeriodTen,");
	   		 hql.append("sum(detail.fdPeriodEleven) as fdPeriodEleven,sum(detail.fdPeriodTwelve) as fdPeriodTwelve");
	   		 hql.append(" from FsscBudgetingDetail detail left join detail.docMain main  left join detail.fdBudgetItem budgetItem");
	   		 hql.append(" where "+HQLUtil.buildLogicIN("budgetItem.fdId", budgetItemList));
	   		 if(!ArrayUtil.isEmpty(idList)||!"newEdition".equals(method)){
	   			 hql.append(" and "+HQLUtil.buildLogicIN("main.fdId", idList));  
	   		 }
	   		 ISysEditionMainModel docOriginDoc=main.getDocOriginDoc();
	    	 if(docOriginDoc!=null){
	    		 //若是新版本，统计对应的金额需排除对应的历史版本
	    		 hql.append(" and main.fdId<>:oldId ");
	    	 }
	    	 hql.append(" and main.fdSchemeId=:fdSchemeId");
	   		 //查询下级科目下级部门/成本中心汇总
	   		 String childSql="select detail.fdParentId as fdParentId,"+hql.toString();
	   		 childSql+="  group by detail.fdParentId ";
	   		 Query query=this.getBaseDao().getHibernateSession().createQuery(childSql).setParameter("fdSchemeId", main.getFdSchemeId());
	   		if(docOriginDoc!=null){
	    		 query.setParameter("oldId", docOriginDoc.getFdId());
	    	 }
	   		 List<Object[]> result=query.list();
	   		 for(Object[] obj:result){
	   			 if(obj.length==14&&StringUtil.isNotNull(obj[0]!=null?obj[0].toString():"")){
	   				 if(childObj.containsKey(obj[0])&&childObj.get(obj[0])!=null){
	   					List newList=new ArrayList();
	   					valueList=(List) childObj.get(obj[0]);
	   					for(int n=0;n<13;n++){
	   						newList.add(FsscNumberUtil.getAddition(getObjVale(obj[n]), (Double)valueList.get(n),2));
	   					}
	   				 }else{
	   					valueList=Arrays.asList(getObjVale(obj[1]),getObjVale(obj[2]),getObjVale(obj[3]),
		   						 getObjVale(obj[4]),getObjVale(obj[5]),getObjVale(obj[6]),getObjVale(obj[7]),
		   						 getObjVale(obj[8]),getObjVale(obj[9]),getObjVale(obj[10]),getObjVale(obj[11]),
		   						 getObjVale(obj[12]),getObjVale(obj[13])); 
	   				 }
	   				 childObj.put(obj[0], valueList);
	   			 }
	   		 }
	   		 if("view".equals(method)||"newEdition".equals(method)){
	   			//查看页面本级预算科目金额显示的下级汇总不包括本身及其所有的版本
	   			List<String> docIdList=getAllDocIdByMainId(main);
	   			if("view".equals(method)){
	   				//查看页面，下级预算汇总需排除本身金额
	   				docIdList.add(main.getFdId());
	   			}
	    		 for(String id:docIdList){
		   			 //当前查看若为历史版本，则不过滤历史版本本身
	   				hql.append(" and main.fdId<> '"+id+"'");
		   		 }
	   		 }
	   		 //上级历史版本的数据的下级预算金额统计的也是最新版本
   			 hql.append(" and main.docIsNewVersion=:docIsNewVersion");
	   		 //查询本级科目下级部门/成本中心汇总，需排除自己
	   		String selfSql="select budgetItem.fdId as fdBudgetItemId,"+hql.toString()+" group by budgetItem.fdId";
	   		query=this.getBaseDao().getHibernateSession().createQuery(selfSql).setParameter("fdSchemeId", main.getFdSchemeId());
	   		if(docOriginDoc!=null){
	   			query.setParameter("oldId", docOriginDoc.getFdId());
	   		}
   			//上级历史版本的数据的下级预算金额统计的也是最新版本
   			query.setParameter("docIsNewVersion",true);
	   		result=query.list();
	   		 for(Object[] obj:result){
	   			 if(obj.length==14&&StringUtil.isNotNull(obj[0]!=null?obj[0].toString():"")){
	   				 if(selfObj.containsKey(obj[0])&&selfObj.get(obj[0])!=null){
	   					List newList=new ArrayList();
	   					valueList=(List) selfObj.get(obj[0]);
	   					for(int n=0;n<13;n++){
	   						newList.add(FsscNumberUtil.getAddition(getObjVale(obj[n]), (Double)valueList.get(n),2));
	   					}
	   				 }else{
	   					valueList=Arrays.asList(getObjVale(obj[1]),getObjVale(obj[2]),getObjVale(obj[3]),
		   						 getObjVale(obj[4]),getObjVale(obj[5]),getObjVale(obj[6]),getObjVale(obj[7]),
		   						 getObjVale(obj[8]),getObjVale(obj[9]),getObjVale(obj[10]),getObjVale(obj[11]),
		   						 getObjVale(obj[12]),getObjVale(obj[13])); 
	   				 }
	   				 selfObj.put(obj[0], valueList);
	   			 }
	   		 }
   			 //若不存在下级，默认赋值为0
	   		 for(String id:budgetItemList){
	   			 if(!selfObj.containsKey(id)){
	   				valueList=Arrays.asList(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
		   			selfObj.put(id, valueList);
	   			 }
	   		 } 
	   	 }else{
	   		 //若不存在下级，默认赋值为0
	   		 for(String id:budgetItemList){
	   			valueList=Arrays.asList(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
	   			childObj.put(id, valueList);
	   			selfObj.put(id, valueList);
	   		 }
	   	 }
	   	 rtnObj.put("child", childObj);  //父级科目下级汇总
	   	 rtnObj.put("self", selfObj);  //底层科目下级部门/成本中心汇总
		return rtnObj;
	}
	/**
	 * 获取兄弟机构各科目已分配预算额
	 * @param idList
	 * @param budgetItemList
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public JSONObject getBrotherByMain(List<String> idList,List<String> budgetItemList,FsscBudgetingMain main,
			String method) throws Exception{
		JSONObject rtnObj=new JSONObject();  //返回值
		JSONObject brotherObj=new JSONObject();  //兄弟机构汇总
		List valueList=new ArrayList();
		//由于查询会出现main.fdDetailList.fdBudgetItem.fdId，导致查询数据库不支持，故分开查询
		if(!ArrayUtil.isEmpty(idList)||"view".equals(method)){
			StringBuilder hql=new StringBuilder();
			hql.append("sum(detail.fdTotal) as fdTotal, ");
			hql.append("sum(detail.fdPeriodOne) as fdPeriodOne,sum(detail.fdPeriodTwo) as fdPeriodTwo,");
			hql.append("sum(detail.fdPeriodThree) as fdPeriodThree,sum(detail.fdPeriodFour) as fdPeriodFour,");
			hql.append("sum(detail.fdPeriodFive) as fdPeriodFive,sum(detail.fdPeriodSix) as fdPeriodSix,");
			hql.append("sum(detail.fdPeriodSeven) as fdPeriodSeven,sum(detail.fdPeriodEight) as fdPeriodEight,");
			hql.append("sum(detail.fdPeriodNine) as fdPeriodNine,sum(detail.fdPeriodTen) as fdPeriodTen,");
			hql.append("sum(detail.fdPeriodEleven) as fdPeriodEleven,sum(detail.fdPeriodTwelve) as fdPeriodTwelve");
			hql.append(" from FsscBudgetingDetail detail left join detail.fdBudgetItem budgetItem left join detail.docMain main ");
			hql.append(" where "+HQLUtil.buildLogicIN("budgetItem.fdId", budgetItemList));
			String tempSql=" and ";
			if(!"add".equals(method)){
				tempSql+="(";
			}
			if(!ArrayUtil.isEmpty(idList)){
				tempSql+="("+HQLUtil.buildLogicIN("main.fdId", idList);  
				//统计非驳回、草稿状态
				tempSql+=" and detail.fdStatus<>:draft  and detail.fdStatus<>:refuse and detail.fdStatus<>:discard)";
			}
			if(!"add".equals(method)){
				tempSql+=" or main.fdId=:docMainId ) ";  //本身查看不管什么状态都计算在内
			}
			hql.append(tempSql);
			ISysEditionMainModel docOriginDoc=main.getDocOriginDoc();
			if(docOriginDoc!=null){
				//若是新版本，统计对应的金额需排除对应的历史版本
				hql.append(" and main.fdId<>:oldId ");
			}
			//查询兄弟机构已分配预算
			String brotherSql="select budgetItem.fdId as fdBudgetItemId,"+hql.toString();
			brotherSql+="  group by budgetItem.fdId ";
			Query query=this.getBaseDao().getHibernateSession().createQuery(brotherSql);
			if(!ArrayUtil.isEmpty(idList)){
				query.setParameter("draft", FsscBudgetingConstant.FD_STATUS_DRAFT)
				.setParameter("refuse", FsscBudgetingConstant.FD_STATUS_REFUSE)
				.setParameter("discard", FsscBudgetingConstant.FD_STATUS_DISCARD);
			}
			if(!"add".equals(method)){
				query.setParameter("docMainId", main.getFdId());  //本身查看不管什么状态都计算在内
			}
			if(docOriginDoc!=null){
				query.setParameter("oldId", docOriginDoc.getFdId());
			}
			List<Object[]> result=query.list();
			for(Object[] obj:result){
				if(obj.length==14&&StringUtil.isNotNull(obj[0]!=null?obj[0].toString():"")){
					if(brotherObj.containsKey(obj[0])&&brotherObj.get(obj[0])!=null){
						List newList=new ArrayList();
						valueList=(List) brotherObj.get(obj[0]);
						for(int n=0;n<13;n++){
							newList.add(FsscNumberUtil.getAddition(getObjVale(obj[n]), (Double)valueList.get(n),2));
						}
					}else{
						valueList=Arrays.asList(getObjVale(obj[1]),getObjVale(obj[2]),getObjVale(obj[3]),
								getObjVale(obj[4]),getObjVale(obj[5]),getObjVale(obj[6]),getObjVale(obj[7]),
								getObjVale(obj[8]),getObjVale(obj[9]),getObjVale(obj[10]),getObjVale(obj[11]),
								getObjVale(obj[12]),getObjVale(obj[13])); 
					}
					brotherObj.put(obj[0], valueList);
				}
			}
		}
		rtnObj.put("brother", brotherObj);  //兄弟机构已分配预算汇总
		return rtnObj;
	}
	/**
	 * 统计各个科目上级预算金额，若是有主文档ID说明只是查找主文档对应的科目，若是没有，则统计所有最新版本的主文档的科目汇总，
	 * @param idList
	 * @param budgetItemList
	 * @param fdHierarchyId 
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public JSONObject getParentByMain(List<String> idList,List<String> budgetItemList,String fdParentId,String fdHierarchyId, 
			FsscBudgetingMain main,String method) throws Exception{
		JSONObject rtnObj=new JSONObject();  //返回值
		JSONObject parentObj=new JSONObject();  //子级汇总
		JSONObject canApplyObj=new JSONObject();  //当前科目下级部门/成本中心汇总
		List valueList=new ArrayList();
		//由于查询会出现main.fdDetailList.fdBudgetItem.fdId，导致查询数据库不支持，故分开查询
		if(!ArrayUtil.isEmpty(idList)||"view".equals(method)){
			StringBuilder hql=new StringBuilder();
			StringBuilder parentHql=new StringBuilder();
			hql.append(" sum(detail.fdTotal) as fdTotal, ");
			hql.append("sum(detail.fdPeriodOne) as fdPeriodOne,sum(detail.fdPeriodTwo) as fdPeriodTwo,");
			hql.append("sum(detail.fdPeriodThree) as fdPeriodThree,sum(detail.fdPeriodFour) as fdPeriodFour,");
			hql.append("sum(detail.fdPeriodFive) as fdPeriodFive,sum(detail.fdPeriodSix) as fdPeriodSix,");
			hql.append("sum(detail.fdPeriodSeven) as fdPeriodSeven,sum(detail.fdPeriodEight) as fdPeriodEight,");
			hql.append("sum(detail.fdPeriodNine) as fdPeriodNine,sum(detail.fdPeriodTen) as fdPeriodTen,");
			hql.append("sum(detail.fdPeriodEleven) as fdPeriodEleven,sum(detail.fdPeriodTwelve) as fdPeriodTwelve");
			hql.append(" from FsscBudgetingDetail detail left join detail.fdBudgetItem budgetItem left join detail.docMain main");
			parentHql.append("select budgetItem.fdId as fdParentId,");
			parentHql.append(hql).append("  where main.fdOrgId =:fdParentId");
			if(!ArrayUtil.isEmpty(idList)){
				parentHql.append(" and "+HQLUtil.buildLogicIN("main.fdId", idList));  
			}
			parentHql.append(" and "+HQLUtil.buildLogicIN("budgetItem.fdId", budgetItemList));
			
			ISysEditionMainModel docOriginDoc=main.getDocOriginDoc();
			if(docOriginDoc!=null){
				//若是新版本，统计对应的金额需排除对应的历史版本
				parentHql.append(" and main.fdId<>:oldId ");
			}
			//统计非驳回、草稿状态
			parentHql.append(" and detail.fdStatus<>:draft  and detail.fdStatus<>:refuse  and detail.fdStatus<>:discard");
			parentHql.append("   group by budgetItem.fdId");
			Query query=this.getBaseDao().getHibernateSession().createQuery(parentHql.toString())
					.setParameter("fdParentId", fdParentId)
					.setParameter("draft", FsscBudgetingConstant.FD_STATUS_DRAFT)
					.setParameter("refuse", FsscBudgetingConstant.FD_STATUS_REFUSE)
					.setParameter("discard", FsscBudgetingConstant.FD_STATUS_DISCARD);
			if(docOriginDoc!=null){
				query.setParameter("oldId", docOriginDoc.getFdId());
			}
			List<Object[]> result=query.list();
			for(Object[] obj:result){
				if(obj.length==14&&StringUtil.isNotNull(obj[0]!=null?obj[0].toString():"")){
					if(parentObj.containsKey(obj[0])&&parentObj.get(obj[0])!=null){
						List newList=new ArrayList();
						valueList=(List) parentObj.get(obj[0]);
						for(int n=0;n<13;n++){
							newList.add(FsscNumberUtil.getAddition(getObjVale(obj[n]), (Double)valueList.get(n),2));
						}
					}else{
						valueList=Arrays.asList(getObjVale(obj[1]),getObjVale(obj[2]),getObjVale(obj[3]),
								getObjVale(obj[4]),getObjVale(obj[5]),getObjVale(obj[6]),getObjVale(obj[7]),
								getObjVale(obj[8]),getObjVale(obj[9]),getObjVale(obj[10]),getObjVale(obj[11]),
								getObjVale(obj[12]),getObjVale(obj[13])); 
					}
					parentObj.put(obj[0], valueList);
				}
			}
			//获取fdParentId下所有自己部门已编制预算
			JSONObject tempObj=new JSONObject();
			JSONObject dataObj=new JSONObject();
			idList=getBrotherMainIdList(main,fdParentId);
			if("add".equals(method)){
				//获取兄弟
				tempObj=getBrotherByMain(idList,budgetItemList,main,"add");
			}else{
				//非新建需把自身预算金额加进去
				idList.add(main.getFdId());
				tempObj=getBrotherByMain(idList,budgetItemList,main,"view");
			}
			
			//新建时，获取父级机构下所有已编制预算
			dataObj=tempObj.getJSONObject("brother");
			List childList=Arrays.asList(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);//默认兄弟节点都未编制预算
			List tempList=new ArrayList();//存储计算的的剩余可分配预算
			//上级预算-兄弟节点已编制预算=剩余可分配预算
			String key=null;
			Iterator iterator = parentObj.keys();
			while(iterator.hasNext()){
				tempList=new ArrayList();
	            key = (String) iterator.next();
		        valueList = (List) parentObj.get(key);
		        if(dataObj.containsKey(key)&&dataObj.get(key)!=null){
		        	childList=(List) dataObj.get(key);
		        }
		        if(!ArrayUtil.isEmpty(valueList)){
		        	for(int k=0;k<valueList.size();k++){
			        	tempList.add(FsscNumberUtil.getSubtraction(getObjVale(valueList.get(k)), getObjVale(childList.get(k)), 2));
			        }
			        canApplyObj.put(key, tempList);
		        }
			}
		}
		rtnObj.put("parent", parentObj);  //上级预算科目金额
		rtnObj.put("canApply", canApplyObj);  //底层科目下级部门/成本中心汇总
		return rtnObj;
	}
	
	/**
	 * 自下而上编制预算，季度、月度、年度汇总
	 * @param main
	 * @param rtnObj
	 * @return
	 * @throws Exception
	 */
	public Map<String,List> getAllTargetData(FsscBudgetingMain main, JSONObject rtnObj,List<String> budgetItemList) throws Exception{
		Map<String,List> rtnMap=new ConcurrentHashMap<>();
		JSONObject childObj=rtnObj.getJSONObject("child");
		JSONObject selfObj=rtnObj.getJSONObject("self");
		List quarterList=new ArrayList(); //季度列表
		List halfYearList=new ArrayList(); //半年度列表
		List yearList=new ArrayList(); //年度度列表
		String[] fdProperty={"fdProject","fdWbs","fdInnerOrder","fdBudgetItem","fdAsset","fdPerson"};
		EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) this.findByPrimaryKey(main.getFdSchemeId(), EopBasedataBudgetScheme.class, true);
		String[] fdDimension=scheme.getFdDimension().split(";");
		List<String> propertyList=new ArrayList<String>();
		for(String dimension:fdDimension){
			if(Integer.parseInt(dimension)-5>=0&&Integer.parseInt(dimension)-5<fdProperty.length){
				propertyList.add(fdProperty[Integer.parseInt(dimension)-5]);
			}
		}
		Double quarterTemp=0.0;
		Double halfYearTemp=0.0;
		List<FsscBudgetingDetail> detailList=main.getFdDetailList();
		for (FsscBudgetingDetail detail : detailList) {
			String fdBudgetItemId=detail.getFdBudgetItem()!=null?detail.getFdBudgetItem().getFdId():"";
			if(StringUtil.isNotNull(fdBudgetItemId)&&!budgetItemList.contains(fdBudgetItemId)){
				continue;
			}
			String fdIsLastStage=detail.getFdIsLastStage();
			if(StringUtil.isNull(fdIsLastStage)) {
				fdIsLastStage=FsscBudgetingConstant.FSSC_BUDGETING_LASTSTAGE_YES;
			}
			List quarterTmpList=new ArrayList();
			List halfYearTmpList=new ArrayList();
			List yearTmpList=new ArrayList();
			quarterTmpList.add(fdIsLastStage);
			halfYearTmpList.add(fdIsLastStage);
			yearTmpList.add(fdIsLastStage);
			for(String property:propertyList){
				Object obj=PropertyUtils.getProperty(detail, property);
				if(obj!=null){
					quarterTmpList.add(PropertyUtils.getProperty(obj, "fdId"));
					quarterTmpList.add(PropertyUtils.getProperty(obj, "fdName"));
					halfYearTmpList.add(PropertyUtils.getProperty(obj, "fdId"));
					halfYearTmpList.add(PropertyUtils.getProperty(obj, "fdName"));
					yearTmpList.add(PropertyUtils.getProperty(obj, "fdId"));
					yearTmpList.add(PropertyUtils.getProperty(obj, "fdName"));
				}
			}
			if(FsscBudgetingConstant.FSSC_BUDGETING_LASTSTAGE_YES.equals(fdIsLastStage)){
			/**非末级只需要显示一行，季度数据结构：[fdIsLastStage，预算id，预算名称，全年金额,一期金额，二期金额，三期金额，四期金额，
			 * 全年，一期下级汇总金额，二期下级汇总金额，三期下级汇总金额，四期下级汇总金额]
			 */
			//新预算额
			quarterTmpList.add(detail.getFdTotal());
			halfYearTmpList.add(detail.getFdTotal());
			yearTmpList.add(detail.getFdTotal());
			for(int i=1,len=feilds.length;i<len;i++){
				quarterTemp=FsscNumberUtil.getAddition(quarterTemp, getObjVale(PropertyUtils.getProperty(detail, feilds[i])), 2);
				halfYearTemp=FsscNumberUtil.getAddition(halfYearTemp, getObjVale(PropertyUtils.getProperty(detail, feilds[i])), 2);
				if(i%3==0){//季度
					quarterTmpList.add(quarterTemp);
					quarterTemp=0.0;  //3次重新初始化
				}
				if(i%6==0){//半年
					halfYearTmpList.add(halfYearTemp);
					halfYearTemp=0.0;  //6次重新初始化
				}
			}
			//下级汇总额
			if(selfObj.containsKey(fdBudgetItemId)&&selfObj.get(fdBudgetItemId)!=null){
				List valueList=(List) selfObj.get(fdBudgetItemId);
				quarterTmpList.add(valueList.get(0));  //全年
				halfYearTmpList.add(valueList.get(0));  //全年
				yearTmpList.add(valueList.get(0));
				for(int i=1,len=valueList.size();i<len;i++){
					quarterTemp=FsscNumberUtil.getAddition(quarterTemp, getObjVale(valueList.get(i)), 2);
					halfYearTemp=FsscNumberUtil.getAddition(halfYearTemp, getObjVale(valueList.get(i)), 2);
					if(i%3==0){
						quarterTmpList.add(quarterTemp);
						quarterTemp=0.0;  //3次重新初始化
					}
					if(i%6==0){
						halfYearTmpList.add(halfYearTemp);
						halfYearTemp=0.0;  //3次重新初始化
					}
				}
			}else{
				quarterTmpList.add(0.0); //全年
				quarterTmpList.add(0.0);//第一季度
				quarterTmpList.add(0.0);//第二季度
				quarterTmpList.add(0.0);//第三季度
				quarterTmpList.add(0.0);//第四季度
				halfYearTmpList.add(0.0);//全年
				halfYearTmpList.add(0.0);//上半年
				halfYearTmpList.add(0.0);//下半年
				yearTmpList.add(0.0);//全年
			}
			}else if(FsscBudgetingConstant.FSSC_BUDGETING_LASTSTAGE_NO.equals(fdIsLastStage)){
				//非末级只需要显示一行，季度数据结构：[fdIsLastStage，预算id，预算名称，全年金额，一期金额，二期金额，三期金额，四期金额]
				if(childObj.containsKey(fdBudgetItemId)&&childObj.get(fdBudgetItemId)!=null){
					List valueList=(List) childObj.get(fdBudgetItemId);
					quarterTmpList.add(FsscNumberUtil.doubleToUp(getObjVale(valueList.get(0))));
					halfYearTmpList.add(FsscNumberUtil.doubleToUp(getObjVale(valueList.get(0))));
					yearTmpList.add(FsscNumberUtil.doubleToUp(getObjVale(valueList.get(0))));
					for(int i=1,len=valueList.size();i<len;i++){
						quarterTemp=FsscNumberUtil.getAddition(quarterTemp, getObjVale(valueList.get(i)), 2);
						halfYearTemp=FsscNumberUtil.getAddition(halfYearTemp, getObjVale(valueList.get(i)), 2);
						if(i%3==0){
							quarterTmpList.add(quarterTemp);
							quarterTemp=0.0;  //3次重新初始化
						}
						if(i%6==0){
							halfYearTmpList.add(halfYearTemp);
							halfYearTemp=0.0;  //3次重新初始化
						}
					}
				}else{
					quarterTmpList.add(0.0); //全年
					quarterTmpList.add(0.0);//第一季度
					quarterTmpList.add(0.0);//第二季度
					quarterTmpList.add(0.0);//第三季度
					quarterTmpList.add(0.0);//第四季度
					halfYearTmpList.add(0.0);//全年
					halfYearTmpList.add(0.0);//上半年
					halfYearTmpList.add(0.0);//下半年
					yearTmpList.add(0.0);//全年
				}
			}
			quarterList.add(quarterTmpList);
			halfYearList.add(halfYearTmpList);
			yearList.add(yearTmpList);
		}
		rtnMap.put("quarter", quarterList);
		rtnMap.put("halfYear", halfYearList);
		rtnMap.put("year", yearList);
		return rtnMap;
	}
	/**
	 * 自上而下编制预算，季度、月度、年度汇总
	 * @param main
	 * @param rtnObj
	 * @return
	 * @throws Exception
	 */
	public Map<String,List> getDowncollectData(FsscBudgetingMain main, JSONObject rtnObj) throws Exception{
		Map<String,List> rtnMap=new ConcurrentHashMap<>();
		JSONObject parentObj=rtnObj.getJSONObject("parent");
		JSONObject canApplyObj=rtnObj.getJSONObject("canApply");
		List quarterList=new ArrayList(); //季度列表
		List halfYearList=new ArrayList(); //半年度列表
		List yearList=new ArrayList(); //年度度列表
		String[] fdProperty={"fdProject","fdWbs","fdInnerOrder","fdBudgetItem","fdAsset","fdPerson"};//由于编制明细无部门维度，故不能在此添加
		EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) this.findByPrimaryKey(main.getFdSchemeId(), EopBasedataBudgetScheme.class, true);
		String[] fdDimension=scheme.getFdDimension().split(";");
		List<String> propertyList=new ArrayList<String>();
		for(String dimension:fdDimension){
			if(Integer.parseInt(dimension)-5>=0&&Integer.parseInt(dimension)-5<fdProperty.length){
				propertyList.add(fdProperty[Integer.parseInt(dimension)-5]);
			}
		}
		Double quarterTemp=0.0;
		Double halfYearTemp=0.0;
		List<FsscBudgetingDetail> detailList=main.getFdDetailList();
		for (FsscBudgetingDetail detail : detailList) {
			String fdBudgetItemId=detail.getFdBudgetItem()!=null?detail.getFdBudgetItem().getFdId():null;
			List quarterTmpList=new ArrayList();
			List halfYearTmpList=new ArrayList();
			List yearTmpList=new ArrayList();
			for(String property:propertyList){
				Object obj=PropertyUtils.getProperty(detail, property);
				if(obj!=null){
					quarterTmpList.add(PropertyUtils.getProperty(obj, "fdId"));
					quarterTmpList.add(PropertyUtils.getProperty(obj, "fdName"));
					halfYearTmpList.add(PropertyUtils.getProperty(obj, "fdId"));
					halfYearTmpList.add(PropertyUtils.getProperty(obj, "fdName"));
					yearTmpList.add(PropertyUtils.getProperty(obj, "fdId"));
					yearTmpList.add(PropertyUtils.getProperty(obj, "fdName"));
				}
			}
			/**数据结构：[维度id，维度名称，
			 * 全年，一期上级预算汇总金额，二期上级预算汇总金额，三期上级预算汇总金额，四期上级预算汇总金额,
			 * 全年，一期可使用预算汇总金额，二期可使用预算汇总金额，三期可使用预算汇总金额，四期可使用预算汇总金额,
			 * 全年,一期金额，二期金额，三期金额，四期金额]
			 */
			//上级汇总额
			if(parentObj.containsKey(fdBudgetItemId)&&parentObj.get(fdBudgetItemId)!=null){
				List valueList=(List) parentObj.get(fdBudgetItemId);
				quarterTmpList.add(valueList.get(0));  //全年总金额
				halfYearTmpList.add(valueList.get(0));  //全年总金额
				yearTmpList.add(valueList.get(0));
				for(int i=1,len=valueList.size();i<len;i++){
					quarterTemp=FsscNumberUtil.getAddition(quarterTemp, getObjVale(valueList.get(i)), 2);
					halfYearTemp=FsscNumberUtil.getAddition(halfYearTemp, getObjVale(valueList.get(i)), 2);
					if(i%3==0){
						quarterTmpList.add(quarterTemp);
						quarterTemp=0.0;  //3次重新初始化
					}
					if(i%6==0){
						halfYearTmpList.add(halfYearTemp);
						halfYearTemp=0.0;  //3次重新初始化
					}
				}
			}else{
				quarterTmpList.add(0.0); //全年
				quarterTmpList.add(0.0);//第一季度
				quarterTmpList.add(0.0);//第二季度
				quarterTmpList.add(0.0);//第三季度
				quarterTmpList.add(0.0);//第四季度
				halfYearTmpList.add(0.0);//全年
				halfYearTmpList.add(0.0);//上半年
				halfYearTmpList.add(0.0);//下半年
				yearTmpList.add(0.0);//全年
			}
			//可使用预算汇总额
			if(canApplyObj.containsKey(fdBudgetItemId)&&canApplyObj.get(fdBudgetItemId)!=null){
				List valueList=(List) canApplyObj.get(fdBudgetItemId);
				quarterTmpList.add(valueList.get(0));  //全年总金额
				halfYearTmpList.add(valueList.get(0));  //全年总金额
				yearTmpList.add(valueList.get(0));
				for(int i=1,len=valueList.size();i<len;i++){
					quarterTemp=FsscNumberUtil.getAddition(quarterTemp, getObjVale(valueList.get(i)), 2);
					halfYearTemp=FsscNumberUtil.getAddition(halfYearTemp, getObjVale(valueList.get(i)), 2);
					if(i%3==0){
						quarterTmpList.add(quarterTemp);
						quarterTemp=0.0;  //3次重新初始化
					}
					if(i%6==0){
						halfYearTmpList.add(halfYearTemp);
						halfYearTemp=0.0;  //3次重新初始化
					}
				}
			}else{
				quarterTmpList.add(0.0); //全年
				quarterTmpList.add(0.0);//第一季度
				quarterTmpList.add(0.0);//第二季度
				quarterTmpList.add(0.0);//第三季度
				quarterTmpList.add(0.0);//第四季度
				halfYearTmpList.add(0.0);//全年
				halfYearTmpList.add(0.0);//上半年
				halfYearTmpList.add(0.0);//下半年
				yearTmpList.add(0.0);//全年
			}
			//新预算额
			quarterTmpList.add(detail.getFdTotal());
			halfYearTmpList.add(detail.getFdTotal());
			yearTmpList.add(detail.getFdTotal());
			for(int i=1,len=feilds.length;i<len;i++){
				quarterTemp=FsscNumberUtil.getAddition(quarterTemp, getObjVale(PropertyUtils.getProperty(detail, feilds[i])), 2);
				halfYearTemp=FsscNumberUtil.getAddition(halfYearTemp, getObjVale(PropertyUtils.getProperty(detail, feilds[i])), 2);
				if(i%3==0){//季度
					quarterTmpList.add(quarterTemp);
					quarterTemp=0.0;  //3次重新初始化
				}
				if(i%6==0){//半年
					halfYearTmpList.add(halfYearTemp);
					halfYearTemp=0.0;  //6次重新初始化
				}
			}
			quarterList.add(quarterTmpList);
			halfYearList.add(halfYearTmpList);
			yearList.add(yearTmpList);
		}
		rtnMap.put("quarter", quarterList);
		rtnMap.put("halfYear", halfYearList);
		rtnMap.put("year", yearList);
		return rtnMap;
	}
	/**
	 * 独立预算编制和自上而下机构/公司年度、半年度、季度汇总
	 * @param main
	 * @param rtnObj
	 * @return
	 * @throws Exception
	 */
	public Map<String,List> getInitIndepcollectData(FsscBudgetingMain main) throws Exception{
		Map<String,List> rtnMap=new ConcurrentHashMap<>();
		List quarterList=new ArrayList(); //季度列表
		List halfYearList=new ArrayList(); //半年度列表
		List yearList=new ArrayList(); //年度度列表
		String[] fdProperty={"fdProject","fdWbs","fdInnerOrder","fdBudgetItem","fdAsset","fdPerson"};
		EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) this.findByPrimaryKey(main.getFdSchemeId(), EopBasedataBudgetScheme.class, true);
		String[] fdDimension=scheme.getFdDimension().split(";");
		List<String> propertyList=new ArrayList<String>();
		for(String dimension:fdDimension){
			if(Integer.parseInt(dimension)-5>=0&&Integer.parseInt(dimension)-5<fdProperty.length){
				propertyList.add(fdProperty[Integer.parseInt(dimension)-5]);
			}
		}
		Double quarterTemp=0.0;
		Double halfYearTemp=0.0;
		List<FsscBudgetingDetail> detailList=main.getFdDetailList();
		for (FsscBudgetingDetail detail : detailList) {
			List quarterTmpList=new ArrayList();
			List halfYearTmpList=new ArrayList();
			List yearTmpList=new ArrayList();
			for(String property:propertyList){
				Object obj=PropertyUtils.getProperty(detail, property);
				if(obj!=null){
					quarterTmpList.add(PropertyUtils.getProperty(obj, "fdId"));
					quarterTmpList.add(PropertyUtils.getProperty(obj, "fdName"));
					halfYearTmpList.add(PropertyUtils.getProperty(obj, "fdId"));
					halfYearTmpList.add(PropertyUtils.getProperty(obj, "fdName"));
					yearTmpList.add(PropertyUtils.getProperty(obj, "fdId"));
					yearTmpList.add(PropertyUtils.getProperty(obj, "fdName"));
				}
			}
			/**数据结构：[维度id，维度名称，全年,一期金额，二期金额，三期金额，四期金额]
			 */
			//新预算额
			quarterTmpList.add(detail.getFdTotal());
			halfYearTmpList.add(detail.getFdTotal());
			yearTmpList.add(detail.getFdTotal());
			for(int i=1,len=feilds.length;i<len;i++){
				quarterTemp=FsscNumberUtil.getAddition(quarterTemp, getObjVale(PropertyUtils.getProperty(detail, feilds[i])), 2);
				halfYearTemp=FsscNumberUtil.getAddition(halfYearTemp, getObjVale(PropertyUtils.getProperty(detail, feilds[i])), 2);
				if(i%3==0){//季度
					quarterTmpList.add(quarterTemp);
					quarterTemp=0.0;  //3次重新初始化
				}
				if(i%6==0){//半年
					halfYearTmpList.add(halfYearTemp);
					halfYearTemp=0.0;  //6次重新初始化
				}
			}
			quarterList.add(quarterTmpList);
			halfYearList.add(halfYearTmpList);
			yearList.add(yearTmpList);
		}
		rtnMap.put("quarter", quarterList);
		rtnMap.put("halfYear", halfYearList);
		rtnMap.put("year", yearList);
		return rtnMap;
	}

	@Override
	public void createNewEdition(HttpServletRequest request,FsscBudgetingMain main,Map<String,Boolean> authMap) throws Exception {
		String fdBudgetingType=EopBasedataFsscUtil.getSwitchValue("fdBudgetingType");//获取预算编制方式
		String fdHierarchyId="";
		 if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(main.getFdOrgType())){
			 	//部门
			 	SysOrgElement org=(SysOrgElement) this.findByPrimaryKey(main.getFdOrgId(), SysOrgElement.class, true);
	        	fdHierarchyId=org.getFdHierarchyId();  //部门层级ID，统计下级
	        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(main.getFdOrgType())){
	        	//成本中心
	        	EopBasedataCostCenter org=(EopBasedataCostCenter) this.findByPrimaryKey(main.getFdOrgId(), EopBasedataCostCenter.class, true);
	        	fdHierarchyId=org.getFdHierarchyId();//成本中心层级ID，统计下级
	        }else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(main.getFdOrgType())){
	        	//记账公司
	        	EopBasedataCompany org=(EopBasedataCompany) this.findByPrimaryKey(main.getFdOrgId(), EopBasedataCompany.class, true);
	        	fdHierarchyId=org.getFdId();  //公司ID
	        }
		  String fdSchemeId=request.getParameter("fdSchemeId");
		  if(StringUtil.isNotNull(fdSchemeId)){
	        	EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) this.findByPrimaryKey(fdSchemeId, EopBasedataBudgetScheme.class, true);
	        	if(scheme!=null){
	        		request.setAttribute("period", scheme.getFdPeriod());
	        		String fdDimension=scheme.getFdDimension()+";";
	        		if(!FsscCommonUtil.isContain(fdDimension, "5;", ";")&&!FsscCommonUtil.isContain(fdDimension, "6;", ";")
	        				&&!FsscCommonUtil.isContain(fdDimension, "7;", ";")&&!FsscCommonUtil.isContain(fdDimension, "10;", ";")
	        				&&FsscCommonUtil.isContain(fdDimension, "8;", ";")){
	        			if(FsscBudgetingConstant.FD_BUDTING_TYPE_DOWN.equals(fdBudgetingType)
	        					&&(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(main.getFdOrgType())
	        							||FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(main.getFdOrgType()))){
	        				//不包含项目、wbs、内部订单、员工，则初始化预算科目层级
		        			completeDetail(request,main,"childView",authMap);
	        			}else{
	        				//不包含项目、wbs、内部订单、员工，则初始化预算科目层级
		        			completeDetail(request,main,"view",authMap);
	        			}
	        		}else{
	        			//包含项目、wbs、内部订单、员工，则设置标识位，页面只显示新预算数据行
	        			request.setAttribute("oneLine", true);
	        		}
	        	}
	        }
	}
	
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		FsscBudgetingMain main=(FsscBudgetingMain) modelObj;
		if (!SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())) {
			updateEdition(main);  //更新版本信息
	        //保存提交操作到日志表
	        List<FsscBudgetingDetail> detailList=main.getFdDetailList();
	        String fdMainId=main.getFdId();
	        for (FsscBudgetingDetail detail : detailList) {
	        	LoggerUtil.createApprovalLog(fdMainId, detail.getFdId(), FsscBudgetingConstant.FD_BUDTING_OPERAT_SUBMIT,
	        			new Date(), ResourceUtil.getString("fsscBudgetingApprovalLog.operatSubmit", "fssc-budgeting"), UserUtil.getUser());
			}
	        //提交发送待办给对应的审批人员
	        sendTodo(main,"approvalBudgeting","approval",SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL,"/fssc/budgeting/index.jsp",
	        		getApprovalToDoList(main));
	        //删除上一版本发的驳回的待办，如果存在的话
	        FsscBudgetingMain origindoc=(FsscBudgetingMain) main.getDocOriginDoc();
	        if(origindoc!=null){
	        	  deleteTodo(origindoc,"budgeting");
	        }
	        //若是新建，清除开始预算编制的待办
	        if(main.getDocAuxiVersion()==0&&main.getDocMainVersion()==1){
	        	List persons=new ArrayList<>();
		        persons.add(UserUtil.getUser());
		        fsscBudgetingOrgService.cancelTodo(persons);
	        }
		}
		return super.add(main);
	}
	
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		FsscBudgetingMain main=(FsscBudgetingMain) modelObj;
		if (!SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())) {
			updateEdition(main);  //更新版本信息
	        //保存提交操作到日志表
	        List<FsscBudgetingDetail> detailList=main.getFdDetailList();
	        String fdMainId=main.getFdId();
	        for (FsscBudgetingDetail detail : detailList) {
	        	LoggerUtil.createApprovalLog(fdMainId, detail.getFdId(), FsscBudgetingConstant.FD_BUDTING_OPERAT_SUBMIT,
	        			new Date(), ResourceUtil.getString("fsscBudgetingApprovalLog.operatSubmit", "fssc-budgeting"), main.getDocCreator());
			}
	        //提交发送待办给对应的审批人员
	        sendTodo(main,"approvalBudgeting","approval",SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL,"/fssc/budgeting/index.jsp",
	        		getApprovalToDoList(main));
	        //删除上一版本发的驳回的待办，如果存在的话
	        FsscBudgetingMain origindoc=(FsscBudgetingMain) main.getDocOriginDoc();
	        if(origindoc!=null){
	        	  deleteTodo(origindoc,"budgeting");
	        }
		}
		super.update(main);
	}
	
	/**
	 * 更新版本信息
	 * @param main
	 * @throws Exception
	 */
	public void updateEdition(FsscBudgetingMain main) throws Exception{
		ISysEditionMainModel originModel=main.getDocOriginDoc();
		if(originModel!=null){
			if (FsscBudgetingConstant.FD_STATUS_DISCARD.equals(main.getFdStatus())) {
				try {
					sysEditionMainService.saveLockedFlag(originModel, false);
					main.setDocOriginDoc(null);
					sysEditionMainService.saveMainModel(main);
				} catch (Exception e) {
					throw new KmssRuntimeException(e);
				}
			} else{
				try {
					List historyList = originModel.getDocHistoryEditions();
					if (historyList != null) {
						for (int i = 0; i < historyList.size(); i++) {
							ISysEditionMainModel historyDoc = (ISysEditionMainModel) historyList
									.get(i);
							FsscBudgetingMain history=(FsscBudgetingMain) historyDoc;
							if (historyDoc.getFdId().equals(main.getFdId()) 
									|| FsscBudgetingConstant.FD_STATUS_DRAFT.equals(history.getFdStatus())
									|| FsscBudgetingConstant.FD_STATUS_DISCARD.equals(history.getFdStatus())) {
                                continue;
                            }
							historyDoc.setDocOriginDoc(main);
							sysEditionMainService.saveMainModel(historyDoc);
						}
					}
					originModel.setDocOriginDoc(main);
					originModel.setDocIsNewVersion(new Boolean(false));
					sysEditionMainService.saveLockedFlag(originModel, false);
					main.setDocIsNewVersion(new Boolean(true));
					main.setDocOriginDoc(null);
					sysEditionMainService.saveMainModel(main);
				} catch (Exception e) {
					throw new KmssRuntimeException(e);
				}
			}
		}
	}
	/***
	 * 根据当前单据ID获取该单据所有的版本ID，包括最新版本ID，用于查看下级金额排除自身数据干扰
	 * @param main
	 * @return
	 * @throws Exception
	 */
	protected List<String> getAllDocIdByMainId(FsscBudgetingMain main) throws Exception{
		List<String> idList=new ArrayList<String>();
		List historyList = main.getDocHistoryEditions(); //当前版本的历史版本，若当前单据非最新版本，则不包含之前的历史版本
		if(!ArrayUtil.isEmpty(historyList)){
			for (int i = 0; i < historyList.size(); i++) {
				ISysEditionMainModel hmodel = (ISysEditionMainModel) historyList.get(i);
				idList.add(hmodel.getFdId());
			}
		}
		return idList;
	}
	/**
	 * 公司/机构/公司组汇总数据展现
	 */
	@Override
	public List<Map> gatherOrg(HttpServletRequest request) throws Exception {
		List<Map> dataList=new ArrayList<Map>();
		Map map=new HashMap();
		String fdHierarchyId=null;
		FsscBudgetingPeriod period=getLastestPeriod();
		String fdYear="";
		if(period!=null&&StringUtil.isNotNull(period.getFdStartPeriod())){
			fdYear=period.getFdStartPeriod().substring(0, 4);
		}else{
			fdYear=String.valueOf(Calendar.YEAR);
		}
		map.put("fdYear", fdYear);
		String fdOrgId=request.getParameter("fdOrgId");
		String fdOrgType=request.getParameter("orgType"); 
		if(StringUtil.isNotNull(fdOrgId)){
			if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)){
				SysOrgElement org=(SysOrgElement) this.findByPrimaryKey(fdOrgId, SysOrgElement.class, true);
				fdHierarchyId=org.getFdHierarchyId();
				map.put("fdName", org.getFdName());
				map.put("fdOrgType", ResourceUtil.getString("fsscBudgetingMain.fdOrg", "fssc-budgeting"));
			}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(fdOrgType)){
				EopBasedataCompany org=(EopBasedataCompany) this.findByPrimaryKey(fdOrgId, EopBasedataCompany.class, true);
				map.put("fdName", org.getFdName());
				map.put("fdOrgType", ResourceUtil.getString("fsscBudgetingMain.fdCompany", "fssc-budgeting"));
				fdHierarchyId=org.getFdId();
			}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY_GROUP.equals(fdOrgType)){
				EopBasedataCompanyGroup org=(EopBasedataCompanyGroup) this.findByPrimaryKey(fdOrgId, EopBasedataCompanyGroup.class, true);
				map.put("fdName", org.getFdName());
				map.put("fdOrgType", ResourceUtil.getString("enums.org_type.4", "fssc-budgeting"));
				fdHierarchyId=org.getFdHierarchyId();
			}
		}
		map.put("fdTotal", getGatherMoney(fdYear,fdOrgId,fdOrgType,fdHierarchyId));
		dataList.add(map);
		return dataList;
	}
	/**
	 * 自下而上编制预算，公司/机构汇总预算总额
	 * @param fdYear
	 * @param fdOrgId
	 * @param fdOrgType
	 * @param fdHierarchyId
	 * @return
	 * @throws Exception
	 */
	protected Double getGatherMoney(String fdYear, String fdOrgId, String fdOrgType,String fdHierarchyId) throws Exception{
		StringBuilder hql=new StringBuilder();
		hql.append("select sum(detail.fdTotal) from FsscBudgetingDetail detail left join  detail.docMain main where main.docIsNewVersion=:docIsNewVersion ");
		if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)){
			hql.append(" and main.fdOrgId in (select org.fdId from SysOrgElement org where org.fdHierarchyId like :fdHierarchyId)");
		}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(fdOrgType)){
			hql.append(" and main.fdOrgId in (select org.fdId from EopBasedataCostCenter org left join org.fdCompanyList company where company.fdId like :fdHierarchyId)");
		}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY_GROUP.equals(fdOrgType)){
			hql.append(" and main.fdOrgId in (select org.fdId from EopBasedataCostCenter org left join org.fdCompanyList comp where comp.fdId in ");
			hql.append(" (select company.fdId from EopBasedataCompany company left join company.fdGroup comgroup where comgroup.fdHierarchyId like :fdHierarchyId ))");
		}
		hql.append(" and main.fdYear=:fdYear");
		hql.append(" and (main.fdStatus=:effect or main.fdStatus=:audited)");
		Query query=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
				.setParameter("docIsNewVersion", true)
				.setParameter("effect", FsscBudgetingConstant.FD_STATUS_EFFECT)
				.setParameter("audited", FsscBudgetingConstant.FD_STATUS_AUDITED)
				.setParameter("fdYear", fdYear);
		if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY_GROUP.equals(fdOrgType)){
			query.setParameter("fdHierarchyId", fdHierarchyId+"%");
		}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)
				||FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(fdOrgType)){
			query.setParameter("fdHierarchyId", fdOrgId+"%");
		}
		List result=query.list();
		if(ArrayUtil.isEmpty(result)){
			return 0.0;
		}else{
			return Double.parseDouble(result.get(0)!=null?String.valueOf(result.get(0)):"0.0");
		}
	}
	
	/***************************************************************
     * 根据最新的有效的预算期间，判断是否存在预算编制
     * 如新建了2020年预算期间，无效，2019年是有效的，则查找2019年预算编制
     * 查找按照时间排序最近的，默认是false
     **************************************************************/
	@Override
	public boolean isInitView(HttpServletRequest request) throws Exception {
		boolean isInitView=false;
		FsscBudgetingPeriod period=getLastestPeriod();
		if(period!=null){
			//根据信息查找已经存在的预算编制
			FsscBudgetingMain main=findBudgeting(request,period);
			if(main!=null){
				request.setAttribute("fdId", main.getFdId());
				isInitView=true;
			}
		}
		return isInitView;
	}
	/**
	 * 机构/公司查看页面，汇总下级预算科目金额
	 * @param request
	 * @param main
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public JSONObject budgetingInitTotal(HttpServletRequest request,FsscBudgetingMain main, String method) throws Exception {
		JSONObject rtnObj=new JSONObject();  //返回值
		String fdId=request.getParameter("fdId");
		Map<String, Boolean> authMap=new ConcurrentHashMap<>();
		authMap.put("total", Boolean.TRUE);
		List<String> budgetItemList=getViewBudgetItemComList(request.getParameter("orgType"),main,authMap);
	 	StringBuilder hql=new StringBuilder();
	 	hql.append("sum(detail.fdTotal) as fdTotal, ");
	 	hql.append("sum(detail.fdPeriodOne) as fdPeriodOne,sum(detail.fdPeriodTwo) as fdPeriodTwo,");
	 	hql.append("sum(detail.fdPeriodThree) as fdPeriodThree,sum(detail.fdPeriodFour) as fdPeriodFour,");
	 	hql.append("sum(detail.fdPeriodFive) as fdPeriodFive,sum(detail.fdPeriodSix) as fdPeriodSix,");
	 	hql.append("sum(detail.fdPeriodSeven) as fdPeriodSeven,sum(detail.fdPeriodEight) as fdPeriodEight,");
	 	hql.append("sum(detail.fdPeriodNine) as fdPeriodNine,sum(detail.fdPeriodTen) as fdPeriodTen,");
	 	hql.append("sum(detail.fdPeriodEleven) as fdPeriodEleven,sum(detail.fdPeriodTwelve) as fdPeriodTwelve");
	 	hql.append(" from FsscBudgetingDetail detail ");
   		hql.append(" where "+HQLUtil.buildLogicIN("detail.fdBudgetItem.fdId", budgetItemList));
   		ISysEditionMainModel docOriginDoc=main.getDocOriginDoc();
    	if(docOriginDoc!=null){
    		//若是新版本，统计对应的金额需排除对应的历史版本
    		hql.append(" and detail.docMain.fdId<>:oldId ");
    	}
   		//查询下级科目汇总
   		String childSql="select detail.fdParentId as fdParentId,"+hql.toString();
   		if(!"add".equals(method)){//view或者newEdition
   			childSql+=" and detail.docMain.fdId=:mainId ";
   		}
   		childSql+="  group by detail.fdParentId ";
   		Query query=this.getBaseDao().getHibernateSession().createQuery(childSql);
   		if(!"add".equals(method)){
   			query.setParameter("mainId", main.getFdId());
   		}
   		if(docOriginDoc!=null){
    		 query.setParameter("oldId", docOriginDoc.getFdId());
    	 }
   		List<Object[]> result=query.list();
   		List valueList=new ArrayList();
   		JSONObject childObj=new JSONObject();
   		for(Object[] obj:result){
   			 if(obj.length==14&&StringUtil.isNotNull(obj[0]!=null?obj[0].toString():"")){
   				 if(childObj.containsKey(obj[0])&&childObj.get(obj[0])!=null){
   					List newList=new ArrayList();
   					valueList=(List) childObj.get(obj[0]);
   					for(int n=0;n<13;n++){
   						newList.add(FsscNumberUtil.getAddition(getObjVale(obj[n]), (Double)valueList.get(n),2));
   					}
   				 }else{
   					valueList=Arrays.asList(getObjVale(obj[1]),getObjVale(obj[2]),getObjVale(obj[3]),
	   						 getObjVale(obj[4]),getObjVale(obj[5]),getObjVale(obj[6]),getObjVale(obj[7]),
	   						 getObjVale(obj[8]),getObjVale(obj[9]),getObjVale(obj[10]),getObjVale(obj[11]),
	   						 getObjVale(obj[12]),getObjVale(obj[13])); 
   				 }
   				 childObj.put(obj[0], valueList);
   			 }
   		 }
   		rtnObj.put("child", childObj);
		return rtnObj;
	}
	/**
	 * 独立预算编制查看页面，汇总下级预算科目金额
	 * @param request
	 * @param main
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public JSONObject budgetingIndependentTotal(HttpServletRequest request,FsscBudgetingMain main, String method) throws Exception {
		JSONObject rtnObj=new JSONObject();  //返回值
		String fdId=request.getParameter("fdId");
		Map<String, Boolean> authMap=new ConcurrentHashMap<>();
		authMap.put("total", Boolean.TRUE);  //统计则全部设置为全部科目
		List<String> budgetItemList=getViewBudgetItemComList(request.getParameter("orgType"),main,authMap);
		StringBuilder hql=new StringBuilder();
		hql.append("sum(detail.fdTotal) as fdTotal, ");
		hql.append("sum(detail.fdPeriodOne) as fdPeriodOne,sum(detail.fdPeriodTwo) as fdPeriodTwo,");
		hql.append("sum(detail.fdPeriodThree) as fdPeriodThree,sum(detail.fdPeriodFour) as fdPeriodFour,");
		hql.append("sum(detail.fdPeriodFive) as fdPeriodFive,sum(detail.fdPeriodSix) as fdPeriodSix,");
		hql.append("sum(detail.fdPeriodSeven) as fdPeriodSeven,sum(detail.fdPeriodEight) as fdPeriodEight,");
		hql.append("sum(detail.fdPeriodNine) as fdPeriodNine,sum(detail.fdPeriodTen) as fdPeriodTen,");
		hql.append("sum(detail.fdPeriodEleven) as fdPeriodEleven,sum(detail.fdPeriodTwelve) as fdPeriodTwelve");
		hql.append(" from FsscBudgetingDetail detail ");
		hql.append(" where "+HQLUtil.buildLogicIN("detail.fdBudgetItem.fdId", budgetItemList));
		ISysEditionMainModel docOriginDoc=main.getDocOriginDoc();
		if(docOriginDoc!=null){
			//若是新版本，统计对应的金额需排除对应的历史版本
			hql.append(" and detail.docMain.fdId<>:oldId ");
		}
		//查询下级科目汇总
		String childSql="select detail.fdParentId as fdParentId,"+hql.toString();
		if(!"add".equals(method)){//view或者newEdition
			childSql+=" and detail.docMain.fdId=:mainId ";
		}
		childSql+="  group by detail.fdParentId ";
		Query query=this.getBaseDao().getHibernateSession().createQuery(childSql);
		if(!"add".equals(method)){
			query.setParameter("mainId", main.getFdId());
		}
		if(docOriginDoc!=null){
			query.setParameter("oldId", docOriginDoc.getFdId());
		}
		List<Object[]> result=query.list();
		List valueList=new ArrayList();
		JSONObject childObj=new JSONObject();
		for(Object[] obj:result){
			if(obj.length==14&&StringUtil.isNotNull(obj[0]!=null?obj[0].toString():"")){
				if(childObj.containsKey(obj[0])&&childObj.get(obj[0])!=null){
					List newList=new ArrayList();
					valueList=(List) childObj.get(obj[0]);
					for(int n=0;n<13;n++){
						newList.add(FsscNumberUtil.getAddition(getObjVale(obj[n]), (Double)valueList.get(n),2));
					}
				}else{
					valueList=Arrays.asList(getObjVale(obj[1]),getObjVale(obj[2]),getObjVale(obj[3]),
							getObjVale(obj[4]),getObjVale(obj[5]),getObjVale(obj[6]),getObjVale(obj[7]),
							getObjVale(obj[8]),getObjVale(obj[9]),getObjVale(obj[10]),getObjVale(obj[11]),
							getObjVale(obj[12]),getObjVale(obj[13])); 
				}
				childObj.put(obj[0], valueList);
			}
		}
		rtnObj.put("child", childObj);
		return rtnObj;
	}
	
	/**
	 * 预算编制提交给对应人员发送待办
	 * @param main  预算编制主表
	 * @param notifyKey  待办唯一key
	 * @param type 发送业务待办类型 approval ：预算审核，effect：预算生效
	 * @param notifyType  待办还是待阅
	 * @param fdLink  待办待阅跳转链接
	 * @param targets  发送待办待阅人员
	 * @throws Exception
	 */

	private void sendTodo(FsscBudgetingMain main,String notifyKey,String type,int notifyType,String fdLink,List<SysOrgPerson> targets) throws Exception {
		NotifyContext notifyContext = null;
		notifyContext = sysNotifyMainCoreService.getContext("fssc-budgeting:fsscBudgetingMain.notify."+type+".message"+ main.getFdOrgName());
		notifyContext.setNotifyType("todo");
		notifyContext.setKey(notifyKey);
		notifyContext.setNotifyTarget(targets);
		notifyContext.setSubject(ResourceUtil.getString("fsscBudgetingMain.notify."+type+".message", "fssc-budgeting").replace("%subject%", main.getFdOrgName()));
		// 设计通知显示的类型
		notifyContext.setFlag(notifyType);
		notifyContext.setLink(fdLink);
		HashMap<String, String> replaceMap = new HashMap<String, String>();
		replaceMap.put("title", main.getFdOrgName());
		sysNotifyMainCoreService.send(main, notifyContext,replaceMap);
	}
	/**
	 * 预算编制已处理删除待办
	 * 
	 * @throws Exception
	 */
	
	private void deleteTodo(FsscBudgetingMain main,String notifyKey) throws Exception {
		sysNotifyMainCoreService.getTodoProvider().remove(main, notifyKey);

	}

	/**
	 * 获取当前预算编制需要审核的人员列表
	 * @param main
	 * @return
	 * @throws Exception
	 */
	protected List<SysOrgPerson> getApprovalToDoList(FsscBudgetingMain main) throws Exception{
		List<SysOrgPerson> personList=new ArrayList<SysOrgPerson>();
		StringBuilder hql=new StringBuilder();
		hql.append("select person from FsscBudgetingApprovalAuth t ");
		hql.append(" left join t.fdCompanyList company ");
		hql.append(" left join t.fdPersonList person ");
		hql.append(" left join t.fdOrgList org ");
		hql.append(" left join t.fdCostCenterList center ");
		hql.append(" where t.fdIsAvailable=:fdIsAvailable");
		hql.append(" and (org.fdId=:fdOrgId");
		hql.append("  or company.fdId=:fdOrgId ");
		hql.append("  or center.fdId=:fdOrgId )");
		personList=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
				.setParameter("fdIsAvailable", true)
				.setParameter("fdOrgId", main.getFdOrgId()).list();
		return FsscCommonUtil.removeRepeat(personList);
	}
	/**
	 * 获取当前预算编制需要生效的人员列表
	 * @param main
	 * @return
	 * @throws Exception
	 */
	protected List<SysOrgPerson> getEffectToDoList(FsscBudgetingMain main) throws Exception{
		List<SysOrgPerson> personList=new ArrayList<SysOrgPerson>();
		HQLInfo hqlInfo=new HQLInfo();
		StringBuilder whereBlock=new StringBuilder();
		whereBlock.append(" fsscBudgetingEffectAuth.fdIsAvailable=:fdIsAvailable ");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setSelectBlock("fsscBudgetingEffectAuth.fdPersonList");
		personList=fsscBudgetingEffectAuthService.findList(hqlInfo);
		return personList;
	}

	/**
	 * 审核预算,修改预算状态 1、勾选部分，点击驳回，则勾选的被驳回，未勾选的被审核通过；2、勾选部分，点击通过，则勾选的被通过，未勾选的被驳回；
	 */
	@Override
	public void updateApprovalDoc(HttpServletRequest request) throws Exception {
		String[] ids = request.getParameterValues("List_Selected");
		List idList=ArrayUtil.convertArrayToList(ids);
		String[] allIds=request.getParameterValues("allSelect");
		String status=request.getParameter("status");
		List<FsscBudgetingDetail> detailList=fsscBudgetingDetailService.findByPrimaryKeys(allIds);
		String fdMainId=request.getParameter("fdMainId");
		FsscBudgetingMain main=null;
		for (FsscBudgetingDetail detail : detailList) {
			if(main==null){
				main=detail.getDocMain();
			}
        	String fdStatus=detail.getFdStatus();  //当新版本时，如果开始已经审批通过的，不需要再改变原有状态
        	if(!FsscBudgetingConstant.FD_STATUS_AUDITED.equals(fdStatus)){
        		if(FsscBudgetingConstant.FD_STATUS_REFUSE.equals(status)){//点击驳回
            		if(idList.contains(detail.getFdId())){//被选中的驳回
            			detail.setFdStatus(FsscBudgetingConstant.FD_STATUS_REFUSE);
            			//添加日志
            			LoggerUtil.createApprovalLog(fdMainId, detail.getFdId(), FsscBudgetingConstant.FD_BUDTING_OPERAT_REJECT, new Date(), 
            					ResourceUtil.getString("fsscBudgetingApprovalLog.operatReject", "fssc-budgeting"), UserUtil.getUser());
            		}else{
            			//未选中的通过
            			detail.setFdStatus(FsscBudgetingConstant.FD_STATUS_AUDITED);
            			//添加日志
            			LoggerUtil.createApprovalLog(fdMainId, detail.getFdId(), FsscBudgetingConstant.FD_BUDTING_OPERAT_PASS, new Date(), 
            					ResourceUtil.getString("fsscBudgetingApprovalLog.operatPass", "fssc-budgeting"), UserUtil.getUser());
            		}
            		fsscBudgetingDetailService.update(detail);
            	}else if(FsscBudgetingConstant.FD_STATUS_AUDITED.equals(status)){//点击审核通过
            		if(idList.contains(detail.getFdId())){//被选中的审核通过
            			//未选中的驳回
            			detail.setFdStatus(FsscBudgetingConstant.FD_STATUS_AUDITED);
            			//添加日志
            			LoggerUtil.createApprovalLog(fdMainId, detail.getFdId(), FsscBudgetingConstant.FD_BUDTING_OPERAT_PASS, new Date(), 
            					ResourceUtil.getString("fsscBudgetingApprovalLog.operatPass", "fssc-budgeting"), UserUtil.getUser());
            		}else{
            			detail.setFdStatus(FsscBudgetingConstant.FD_STATUS_REFUSE);
            			//添加日志
            			LoggerUtil.createApprovalLog(fdMainId, detail.getFdId(), FsscBudgetingConstant.FD_BUDTING_OPERAT_REJECT, new Date(), 
            					ResourceUtil.getString("fsscBudgetingApprovalLog.operatReject", "fssc-budgeting"), UserUtil.getUser());
            		}
            		fsscBudgetingDetailService.update(detail);
            	}
        	}
		}
    	//根据明细更新主文档状态
		updateMainStatus(main,status,ids.length==allIds.length);
		if(main!=null&&StringUtil.isNotNull(main.getFdSchemeId())){
			EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) this.findByPrimaryKey(main.getFdSchemeId(),EopBasedataBudgetScheme.class,true);
			if(!FsscCommonUtil.isContain(scheme.getFdDimension(), "5;", ";")&&!FsscCommonUtil.isContain(scheme.getFdDimension(), "6;", ";")
    				&&!FsscCommonUtil.isContain(scheme.getFdDimension(), "7;", ";")&&!FsscCommonUtil.isContain(scheme.getFdDimension(), "10;", ";")
    				&&FsscCommonUtil.isContain(scheme.getFdDimension(), "8;", ";")){
				//检查明细所有状态，更新上级预算科目状态
				updateParentBudgetItemStatus(main);
			}
		}
		//清除审批待办
		deleteTodo(main,"approvalBudgeting");//驳回情况下清除审核人待办
		//保存审批意见
		String approveOption=request.getParameter("approveOption");
		if(StringUtil.isNotNull(approveOption)) {
			main.setFdApprovalOpinions(approveOption);
			this.getBaseDao().update(main);
		}
	}

	/**
	 * 预算生效，生成预算数据
	 */
	@Override
	public void addBudgeting(HttpServletRequest request) throws Exception {
		if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
			//存在预算模块，数据写入预算模块
			String[] ids = request.getParameterValues("List_Selected");
			List idList=ArrayUtil.convertArrayToList(ids);
			String[] allIds=request.getParameterValues("allSelect");
			String status=request.getParameter("status");
			List<FsscBudgetingDetail> detailList=fsscBudgetingDetailService.findByPrimaryKeys(ids);
			String fdMainId=request.getParameter("fdMainId");
			FsscBudgetingMain main=(FsscBudgetingMain) this.findByPrimaryKey(fdMainId, null, true);
			String fdCompanyCode=main!=null?main.getFdCompany().getFdCode():null;
			String fdYear=main.getFdYear();
			String fdSchemeId=main.getFdSchemeId();
			String fdYearRule=main.getFdYearRule();
			String fdYearApply=main.getFdYearApply();
			String fdQuarterRule=main.getFdQuarterRule();
			String fdQuarterApply=main.getFdQuarterApply();
			String fdMonthRule=main.getFdMonthRule();
			String fdMonthApply=main.getFdMonthApply();
			Double fdElasticPercent=main.getFdElasticPercent();
			EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) this.findByPrimaryKey(fdSchemeId,EopBasedataBudgetScheme.class,true);
			String fdSchemeCode=scheme!=null?scheme.getFdCode():null;
			JSONArray dataJson=new JSONArray();
			for (FsscBudgetingDetail detail : detailList) {
				JSONObject objJson=new JSONObject();
				objJson.put("fdCompanyCode", fdCompanyCode);  //公司编码
				objJson.put("fdYear", "5"+fdYear+"0000");  //预算年份
				objJson.put("fdSchemeCode",fdSchemeCode);  //预算年份
				String fdOrgId=main.getFdOrgId();
				String fdOrgType=main.getFdOrgType();
				if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(fdOrgType)){
					EopBasedataCostCenter org=(EopBasedataCostCenter) this.findByPrimaryKey(fdOrgId, EopBasedataCostCenter.class, true);
					objJson.put("fdCostCenterCode", org!=null?org.getFdCode():null);  //成本中心编码
				}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)){
					SysOrgElement org=(SysOrgElement) this.findByPrimaryKey(fdOrgId, SysOrgElement.class, true);
					objJson.put("fdDeptCode", (org!=null&&StringUtil.isNotNull(org.getFdNo()))?org.getFdNo():org.getFdId());  //部门编码
					if(org!=null){
						if(StringUtil.isNotNull(org.getFdNo())){
							objJson.put("fdDeptCode", org.getFdNo());
						}else{
							objJson.put("fdDeptCode", org.getFdId());
						}
					}
				}
				objJson.put("fdBudgetItemCode", detail.getFdBudgetItem()!=null?detail.getFdBudgetItem().getFdCode():null);
				objJson.put("fdProjectCode", detail.getFdProject()!=null?detail.getFdProject().getFdCode():null);
				objJson.put("fdInnerOrderCode", detail.getFdInnerOrder()!=null?detail.getFdInnerOrder().getFdCode():null);
				objJson.put("fdWbsCode", detail.getFdWbs()!=null?detail.getFdWbs().getFdCode():null);
				if(detail.getFdPerson()!=null){
					if(StringUtil.isNotNull(detail.getFdPerson().getFdNo())){
						objJson.put("fdPersonCode", detail.getFdPerson().getFdNo());
					}else{
						objJson.put("fdPersonCode", detail.getFdPerson().getFdId());
					}
				}
				objJson.put("fdYearMoney", detail.getFdTotal());
				objJson.put("fdJanMoney", detail.getFdPeriodOne());
				objJson.put("fdFebMoney", detail.getFdPeriodTwo());
				objJson.put("fdMarMoney", detail.getFdPeriodThree());
				objJson.put("fdFirstQuarterMoney", FsscNumberUtil.getAddition(
						FsscNumberUtil.getAddition(detail.getFdPeriodOne()!=null?detail.getFdPeriodOne():0.00,
								detail.getFdPeriodTwo()!=null?detail.getFdPeriodTwo():0.00), 
						detail.getFdPeriodThree()!=null?detail.getFdPeriodThree():0.00, 2));
				objJson.put("fdAprMoney", detail.getFdPeriodFour());
				objJson.put("fdMayMoney", detail.getFdPeriodFive());
				objJson.put("fdJunMoney", detail.getFdPeriodSix());
				objJson.put("fdSecondQuarterMoney", FsscNumberUtil.getAddition(
						FsscNumberUtil.getAddition(detail.getFdPeriodFour()!=null?detail.getFdPeriodFour():0.00,
								detail.getFdPeriodFive()!=null?detail.getFdPeriodFive():0.00), 
						detail.getFdPeriodSix()!=null?detail.getFdPeriodSix():0.00, 2));
				objJson.put("fdJulMoney", detail.getFdPeriodSeven());
				objJson.put("fdAugMoney", detail.getFdPeriodEight());
				objJson.put("fdSeptMoney", detail.getFdPeriodNine());
				objJson.put("fdThirdQuarterMoney", FsscNumberUtil.getAddition(
						FsscNumberUtil.getAddition(detail.getFdPeriodSeven()!=null?detail.getFdPeriodSeven():0.00,
								detail.getFdPeriodEight()!=null?detail.getFdPeriodEight():0.00), 
						detail.getFdPeriodNine()!=null?detail.getFdPeriodNine():0.00, 2));
				objJson.put("fdOctMoney", detail.getFdPeriodTen());
				objJson.put("fdNovMoney", detail.getFdPeriodEleven());
				objJson.put("fdDecMoney", detail.getFdPeriodTwelve());
				objJson.put("fdFourthQuarterMoney", FsscNumberUtil.getAddition(
						FsscNumberUtil.getAddition(detail.getFdPeriodTen()!=null?detail.getFdPeriodTen():0.00,
								detail.getFdPeriodEleven()!=null?detail.getFdPeriodEleven():0.00), 
						detail.getFdPeriodTwelve()!=null?detail.getFdPeriodTwelve():0.00, 2));
				objJson.put("fdYearRule", fdYearRule);
				objJson.put("fdYearApply", fdYearApply);
				objJson.put("fdQuarterRule", fdQuarterRule);
				objJson.put("fdQuarterApply", fdQuarterApply);
				objJson.put("fdMonthRule", fdMonthRule);
				objJson.put("fdMonthApply", fdMonthApply);
				objJson.put("fdElasticPercent", fdElasticPercent);
	        	detail.setFdStatus(FsscBudgetingConstant.FD_STATUS_EFFECT);
	        	objJson.put("docCreator", main.getDocCreator().getFdId());
	        	dataJson.add(objJson);
				//添加日志
				LoggerUtil.createApprovalLog(fdMainId, detail.getFdId(), FsscBudgetingConstant.FD_BUDTING_OPERAT_EFFECT, new Date(), 
						ResourceUtil.getString("fsscBudgetingApprovalLog.operatReject", "fssc-budgeting"), UserUtil.getUser());
			}
			updateMainStatus(main,status,ids.length==allIds.length);
			getFsscBudgetOperatService().addFsscBudgetData(dataJson);
		}
	}
	
	/**
	 * 若是驳回，则将主文档状态置为驳回状态，若是通过状态，则判断主表其他明细是否为审核通过状态，
	 * 若是都通过了，则将主文档置为已审核，若是不全是审核通过，则不做操作
	 * 生效全部生效则将文档置为生效
	 * @param docMain
	 * @param status
	 * @param eq 
	 * @throws Exception
	 */
	protected void updateMainStatus(FsscBudgetingMain docMain, String status, boolean eq) throws Exception{
		List<SysOrgPerson> target=new ArrayList<SysOrgPerson>();  //发送待办的人员
		if(FsscBudgetingConstant.FD_STATUS_REFUSE.equals(status)){//驳回
    		docMain.setFdStatus(FsscBudgetingConstant.FD_STATUS_REFUSE);
    		deleteTodo(docMain,"approvalBudgeting");//驳回情况下清除审核人待办
    		target.add(docMain.getDocCreator());
			sendTodo(docMain,"budgeting","budgeting",SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL,"/fssc/budgeting/index.jsp",target);//向编制人发送待办
    	}else if(FsscBudgetingConstant.FD_STATUS_AUDITED.equals(status)){//审核通过
    		if(eq){//说明该人员全部审批通过，判断是否整个单据全部审核通过
    			List<FsscBudgetingDetail> detailList=docMain.getFdDetailList();
        		boolean canUpdate=true;
        		for (FsscBudgetingDetail detail : detailList) {
    				if(FsscBudgetingConstant.FSSC_BUDGETING_LASTSTAGE_YES.equals(detail.getFdIsLastStage())
    						&&!FsscBudgetingConstant.FD_STATUS_AUDITED.equals(detail.getFdStatus())){//非审核通过的
    					canUpdate=false;
    					break;
    				}
    			}
        		if(canUpdate){
        			docMain.setFdStatus(FsscBudgetingConstant.FD_STATUS_AUDITED);
        			sendTodo(docMain,"effectBudgeting","effect",SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL,"/fssc/budgeting/index.jsp",
        	        		getEffectToDoList(docMain));//全部审核通过，给生效权限人发送待办
        		}
    		}else{//说明部分通过
    			docMain.setFdStatus(FsscBudgetingConstant.FD_STATUS_REFUSE);
    			deleteTodo(docMain,"approvalBudgeting");//驳回情况下清除审核人待办
    			sendTodo(docMain,"budgeting","budgeting",SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL,"/fssc/budgeting/index.jsp",target);//向编制人发送待办
    		}
    	}else if(FsscBudgetingConstant.FD_STATUS_EFFECT.equals(status)&&eq){
    		//说明该人员全部生效，判断是否整个单据全部生效
			List<FsscBudgetingDetail> detailList=docMain.getFdDetailList();
    		boolean canUpdate=true;
    		for (FsscBudgetingDetail detail : detailList) {
				if(FsscBudgetingConstant.FSSC_BUDGETING_LASTSTAGE_YES.equals(detail.getFdIsLastStage())
						&&!FsscBudgetingConstant.FD_STATUS_EFFECT.equals(detail.getFdStatus())){//非生效的
					canUpdate=false;
					break;
				}
			}
    		if(canUpdate){
    			docMain.setFdStatus(FsscBudgetingConstant.FD_STATUS_EFFECT);
    			sendTodo(docMain,"budgeting","budgeting",SysNotifyConstant.NOTIFY_TODOTYPE_ONCE,"/fssc/budgeting/index.jsp",target);//向编制人发送待办
    			deleteTodo(docMain,"effectBudgeting"); //其中一个人生效，清除其他有权限的人的待办
    		}
    	}
		this.getBaseDao().update(docMain);
	}
	
	/**
	 * 根据下级科目状态更新上级科目状态
	 * @param main
	 */
	public void updateParentBudgetItemStatus(FsscBudgetingMain main) throws Exception{
		List<FsscBudgetingDetail> detailList=main.getFdDetailList();
		Map<String,FsscBudgetingDetail> detailMap=new ConcurrentHashMap<>();//状态集合
		Map<String,List<String>> parentMap=new ConcurrentHashMap<>();//上级科目id为key，子级id的list集合为值
		for(FsscBudgetingDetail detail:detailList){
			detailMap.put(detail.getFdBudgetItem()!=null?detail.getFdBudgetItem().getFdId():"", detail);
			String fdParentId=detail.getFdParentId();
			if(StringUtil.isNotNull(fdParentId)){
				List<String> idList=new ArrayList();
				if(parentMap.containsKey(fdParentId)){
					idList=parentMap.get(fdParentId);
				}
				idList.add(detail.getFdBudgetItem()!=null?detail.getFdBudgetItem().getFdId():"");
				parentMap.put(fdParentId, idList);
			}
		}
		StringBuilder hql=new StringBuilder();
		hql.append("select t.fdId from EopBasedataBudgetItem t where t.fdId in (");
		hql.append("select d.fdBudgetItem.fdId from FsscBudgetingDetail d where d.fdIsLastStage=:fdIsLastStage");
		hql.append(" and d.docMain.fdId=:fdMainId) order by length(t.fdHierarchyId) desc");
		List<String> result=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
				.setParameter("fdIsLastStage", FsscBudgetingConstant.FSSC_BUDGETING_LASTSTAGE_NO)
				.setParameter("fdMainId", main.getFdId()).list();//根据层级ID排序，层级
		for(String parId:result){
			List<String> ids=parentMap.get(parId);
			String status="";
			boolean isUpdate=true;
			for(String id:ids){
				if(StringUtil.isNull(status)){
					status=detailMap.get(id).getFdStatus();
				}
				if(!status.equals(detailMap.get(id).getFdStatus())){
					isUpdate=false;
				}
			}
			if(isUpdate){
				FsscBudgetingDetail parentDetail=detailMap.get(parId);
				parentDetail.setFdStatus(status);
				fsscBudgetingDetailService.update(parentDetail);
			}
		}
	}
	
	/**
	 * 判断当前人员是否有当前机构预算编制权限
	 */
	@Override
    public boolean checkBudgetingAuth(String fdOrgId) throws Exception{
		boolean authFlag=false;  //默认是无权限的
		StringBuilder hql=new StringBuilder();
		hql.append("select count(t.fdId) from FsscBudgetingAuth t ");
		hql.append(" left join t.fdCompanyList company ");
		hql.append(" left join t.fdPersonList person ");
		hql.append(" left join t.fdOrgList org ");
		hql.append(" left join t.fdCostCenterList center ");
		hql.append(" left join t.fdProjectList project ");
		hql.append(" where t.fdIsAvailable=:fdIsAvailable");
		hql.append(" and person.fdId=:fdUserId");
		hql.append(" and (org.fdId=:fdOrgId");
		hql.append("  or company.fdId=:fdOrgId ");
		hql.append("  or center.fdId=:fdOrgId ");
		hql.append("  or project.fdId=:fdOrgId) ");
		List result=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
				.setParameter("fdIsAvailable", true)
				.setParameter("fdUserId", UserUtil.getUser().getFdId())
				.setParameter("fdOrgId", fdOrgId).list();
		if(!ArrayUtil.isEmpty(result)){
			if(result.get(0)!=null&&Integer.parseInt(String.valueOf(result.get(0)))>0){
				authFlag=true;
			}
		}
		return authFlag;
	}
	/**
	 * 判断当前人员是否有当前预算编制审批权限
	 */
	@Override
    public boolean checkBudgetingApprovalAuth(String fdOrgId) throws Exception{
		boolean authFlag=false;  //默认是无权限的
		StringBuilder hql=new StringBuilder();
		hql.append("select count(t.fdId) from FsscBudgetingApprovalAuth t ");
		hql.append(" left join t.fdCompanyList company ");
		hql.append(" left join t.fdPersonList person ");
		hql.append(" left join t.fdOrgList org ");
		hql.append(" left join t.fdCostCenterList center ");
		hql.append(" left join t.fdProjectList project ");
		hql.append(" where t.fdIsAvailable=:fdIsAvailable");
		hql.append(" and person.fdId=:fdUserId");
		hql.append(" and (org.fdId=:fdOrgId");
		hql.append("  or company.fdId=:fdOrgId ");
		hql.append("  or center.fdId=:fdOrgId ");
		hql.append("  or project.fdId=:fdOrgId) ");
		List result=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
				.setParameter("fdIsAvailable", true)
				.setParameter("fdUserId", UserUtil.getUser().getFdId())
				.setParameter("fdOrgId", fdOrgId).list();
		if(!ArrayUtil.isEmpty(result)){
			if(result.get(0)!=null&&Integer.parseInt(String.valueOf(result.get(0)))>0){
				authFlag=true;
			}
		}
		return authFlag;
	}
	/**
	 * 判断当前人员是否有当前预算编制生效权限
	 */
	@Override
    public boolean checkBudgetingEffectAuth() throws Exception{
		boolean authFlag=false;  //默认是无权限的
		StringBuilder hql=new StringBuilder();
		hql.append("select count(t.fdId) from FsscBudgetingEffectAuth t ");
		hql.append(" left join t.fdPersonList person ");
		hql.append(" where t.fdIsAvailable=:fdIsAvailable");
		hql.append(" and person.fdId=:fdUserId");
		List result=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
				.setParameter("fdIsAvailable", true)
				.setParameter("fdUserId", UserUtil.getUser().getFdId()).list();
		if(!ArrayUtil.isEmpty(result)){
			if(result.get(0)!=null&&Integer.parseInt(String.valueOf(result.get(0)))>0){
				authFlag=true;
			}
		}
		return authFlag;
	}
	
	/**
	 * 点击版本记录的列表链接跳转
	 */
	@Override
	public ActionForward getViewForward(FsscBudgetingMainForm mainForm) throws Exception {
		String fdOrgType=mainForm.getFdOrgType();
		SysOrgElement org=null;
		if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)){//部门，查找对应对象，判断是部门还是机构
			org=(SysOrgElement) this.findByPrimaryKey(mainForm.getFdOrgId(), SysOrgElement.class, true);
		}
		ActionForward forward=new ActionForward("/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=view&orgType="
				+fdOrgType+"&fdOrgId="+mainForm.getFdOrgId()+"&fdSchemeId="+mainForm.getFdSchemeId()+"&fdId="+mainForm.getFdId()+"&viewVersion=true",true);
		String fdBudgetingType=EopBasedataFsscUtil.getSwitchValue("fdBudgetingType");//获取预算编制方式
		if(FsscBudgetingConstant.FD_BUDTING_TYPE_INDEPENDENT.equals(fdBudgetingType)){
			forward=new ActionForward("/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=independentView&orgType="
					+fdOrgType+"&fdOrgId="+mainForm.getFdOrgId()+"&fdSchemeId="+mainForm.getFdSchemeId()+"&fdId="+mainForm.getFdId()+"&viewVersion=true",true); //独立预算编制查看页面
		}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_DOWN.equals(fdBudgetingType)){
			//自上而下
			if((FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY_GROUP.equals(fdOrgType)
					||FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(fdOrgType))
					||(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)&&org!=null&&SysOrgConstant.ORG_TYPE_ORG==org.getFdOrgType())){
				forward=new ActionForward("/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=initView&orgType="
						+fdOrgType+"&fdOrgId="+mainForm.getFdOrgId()+"&fdSchemeId="+mainForm.getFdSchemeId()+"&fdId="+mainForm.getFdId()+"&viewVersion=true",true); //公司/机构
			}else{
				forward=new ActionForward("/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=childView&orgType="
						+fdOrgType+"&fdOrgId="+mainForm.getFdOrgId()+"&fdSchemeId="+mainForm.getFdSchemeId()+"&fdId="+mainForm.getFdId()+"&viewVersion=true",true); //非公司/非机构
			}
		}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_UP.equals(fdBudgetingType)){
			//自下而上
			if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY_GROUP.equals(fdOrgType)
					||FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(fdOrgType)
					||(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)&&org!=null&&SysOrgConstant.ORG_TYPE_ORG==org.getFdOrgType())){
				forward=new ActionForward("/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingUp&orgType="
						+fdOrgType+"&fdOrgId="+mainForm.getFdOrgId()+"&fdSchemeId="+mainForm.getFdSchemeId()+"&fdId="+mainForm.getFdId()+"&viewVersion=true",true); //公司/机构
			}else{
				forward=new ActionForward("/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=view&orgType="
						+fdOrgType+"&fdOrgId="+mainForm.getFdOrgId()+"&fdSchemeId="+mainForm.getFdSchemeId()+"&fdId="+mainForm.getFdId()+"&viewVersion=true",true); //非公司/非机构
			}
		}
		return forward;
	}

	@Override
	public void updateBudgetingStatus() throws Exception {
		FsscBudgetingPeriod period=getLastestPeriod();
		StringBuilder hql=new StringBuilder();
		hql.append("update FsscBudgetingMain t set t.fdStatus=:fdStatus where t.fdYear=:fdYear");
		this.getBaseDao().getHibernateSession().createQuery(hql.toString())
		.setParameter("fdStatus", FsscBudgetingConstant.FD_STATUS_DISCARD)
		.setParameter("fdYear", period.getFdStartPeriod().substring(0, 4)).executeUpdate();
		hql=new StringBuilder();
		hql.append("update FsscBudgetingDetail t set t.fdStatus=:fdStatus where t.docMain.fdId in (select m.fdId from FsscBudgetingMain m where m.fdYear=:fdYear)");
		this.getBaseDao().getHibernateSession().createQuery(hql.toString()).setParameter("fdStatus", FsscBudgetingConstant.FD_STATUS_DISCARD)
		.setParameter("fdYear", period.getFdStartPeriod().substring(0, 4)).executeUpdate();
	}
	
	//判断当前文档是否有预算查看、预算审核、预算生效权限
		@Override
        public Map<String,Boolean> getBudgetingViewAuth(HttpServletRequest request, String fdOrgId) throws Exception{
			Map<String,Boolean> authMap=new ConcurrentHashMap<String,Boolean>();
			authMap.put("authFlag", Boolean.FALSE);//默认无法查看
			authMap.put("budgetAuth", Boolean.FALSE);  //默认不允许编制，查看页面用于做新版班控制
			authMap.put("approvalAuth", Boolean.FALSE);  //默认不允许审批
			authMap.put("effectAuth", Boolean.FALSE);  //默认不允许生效
			//校验当前人员是否有当前机构的预算编制审核/编制权限/预算生效权限
			if(checkBudgetingAuth(fdOrgId)){
				authMap.put("authFlag", Boolean.TRUE);//有编制预算可以查看
				authMap.put("budgetAuth", Boolean.TRUE);  //默认允许编制，查看页面用于做新版本控制
				request.setAttribute("budgetAuth", Boolean.TRUE);
			}
			if(checkBudgetingApprovalAuth(fdOrgId)){
				authMap.put("authFlag", Boolean.TRUE);//有审核预算可以查看
				authMap.put("approvalAuth", Boolean.TRUE);  //允许审批
				request.setAttribute("approvalAuth", Boolean.TRUE);
			}
			if(checkBudgetingEffectAuth()){
				authMap.put("authFlag", Boolean.TRUE);//有预算生效可以查看
				authMap.put("effectAuth", Boolean.TRUE);//有预算生效权限
				request.setAttribute("effectAuth", Boolean.TRUE);  //允许生效
			}
			if(checkBudgetingViewAuth(request.getParameter("fdId"))){
				authMap.put("authFlag", Boolean.TRUE);//有预算编制查看权限
			}
			return authMap;
		}
		
		//判断当前文档是否有预算查看、预算审核、预算生效权限
		public Map<String,Boolean> getViewAuth(String fdOrgId) throws Exception{
			Map<String,Boolean> authMap=new ConcurrentHashMap<String,Boolean>();
			authMap.put("authFlag", Boolean.FALSE);//默认无法查看
			authMap.put("budgetAuth", Boolean.FALSE);  //默认不允许编制，查看页面用于做新版班控制
			authMap.put("approvalAuth", Boolean.FALSE);  //默认不允许审批
			authMap.put("effectAuth", Boolean.FALSE);  //默认不允许生效
			//校验当前人员是否有当前机构的预算编制审核/编制权限/预算生效权限
			if(checkBudgetingAuth(fdOrgId)){
				authMap.put("authFlag", Boolean.TRUE);//有编制预算可以查看
				authMap.put("budgetAuth", Boolean.TRUE);  //默认允许编制，查看页面用于做新版本控制
			}
			if(checkBudgetingApprovalAuth(fdOrgId)){
				authMap.put("authFlag", Boolean.TRUE);//有审核预算可以查看
				authMap.put("approvalAuth", Boolean.TRUE);  //允许审批
			}
			if(checkBudgetingEffectAuth()){
				authMap.put("authFlag", Boolean.TRUE);//有预算生效可以查看
				authMap.put("effectAuth", Boolean.TRUE);//有预算生效权限
			}
			if(checkBudgetingViewAuth(fdOrgId)){
				authMap.put("authFlag", Boolean.TRUE);//有预算编制查看权限
			}
			return authMap;
		}
		
		/**
		 *审批人可以看到自己有权限审批的下级所有部门或者成本中心的预算
		 *财务人员可以看到整个公司的预算编制权限
		 *有该权限的均可以看所有的权限
		 * @param authMap
		 * @param fdOrgId
		 * @return
		 * @throws Exception
		 */
		public boolean checkBudgetingViewAuth(String fdMainId) throws Exception{
			boolean auth=false;
			SysOrgElement user=UserUtil.getUser();
			if(UserUtil.checkRole("ROLE_FSSCBUDGETING_VIEW")){//有预算编制查看权限
				auth=true;
			}
			FsscBudgetingMain main=(FsscBudgetingMain) this.findByPrimaryKey(fdMainId, null, true);
			if(main!=null){
				String fdOrgType=main.getFdOrgType();
				String fdCompanyId=main.getFdCompany()!=null?main.getFdCompany().getFdId():null;
				//新建main未空，不需要判断查看权限
				if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(fdOrgType)
						||FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(fdOrgType)){
					//公司类型或者成本中心类型才需要根据公司判断
					List<SysOrgElement> staffList=EopBasedataAuthUtil.getFinanceSaffList(fdCompanyId);
					if(staffList.contains(user)){//是财务人员
						auth=true;
					}
				}
				StringBuilder hql=new StringBuilder();
				hql.append("select t from FsscBudgetingApprovalAuth t ");
				hql.append(" left join t.fdCompanyList company ");
				hql.append(" left join t.fdPersonList person ");
				hql.append(" left join t.fdOrgList org ");
				hql.append(" left join t.fdCostCenterList center ");
				hql.append(" left join t.fdProjectList project ");
				hql.append(" where t.fdIsAvailable=:fdIsAvailable");
				hql.append(" and person.fdId=:fdUserId");
				List<FsscBudgetingApprovalAuth> result=this.getBaseDao().getHibernateSession().createQuery(hql.toString())
						.setParameter("fdIsAvailable", true)
						.setParameter("fdUserId", user.getFdId()).list();
				List<String> resultList=new ArrayList<>();
				if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COMPANY.equals(fdOrgType)
						||FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(fdOrgType)){
					//是公司情况下，审核权限包含该公司/成本中心情况下包含该成本中心的公司，也可以查看该公司下所有成本中心
					for(FsscBudgetingApprovalAuth approval:result){
						for(EopBasedataCompany com:approval.getFdCompanyList()){
							resultList.add(com.getFdId());  //有权限的公司ID
						}
					}
					if(resultList.contains(fdCompanyId)){
						auth=true;
					}
				}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(fdOrgType)){
					//若是有上级成本中心权限，也可查看
					resultList=new ArrayList<>();
					for(FsscBudgetingApprovalAuth approval:result){
						for(EopBasedataCostCenter costCenter:approval.getFdCostCenterList()){
							resultList.addAll(ArrayUtil.convertArrayToList(costCenter.getFdHierarchyId().split("x")));  //有权限的成本中心层级
						}
					}
					EopBasedataCostCenter center=(EopBasedataCostCenter) this.findByPrimaryKey(main.getFdOrgId(), EopBasedataCostCenter.class, true);
					if(ArrayUtil.isListIntersect(resultList, ArrayUtil.convertArrayToList(center.getFdHierarchyId().split("x")))){//有交集，说明有上级成本中心权限
						auth=true;
					}
				}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)){
					//若是有上级组织架构权限，也可查看
					resultList=new ArrayList<>();
					for(FsscBudgetingApprovalAuth approval:result){
						for(SysOrgElement org:approval.getFdOrgList()){
							resultList.addAll(ArrayUtil.convertArrayToList(org.getFdHierarchyId().split("x")));  //有权限的成本中心层级
						}
					}
					if(ArrayUtil.isListIntersect(resultList, ArrayUtil.convertArrayToList(user.getFdHierarchyId().split("x")))){//有交集，说明有上级成本中心权限
						auth=true;
					}
				}
			}
			return auth;
		}
		/**
		 * 获取历史审批记录
		 */
		@Override
		public List getHistoryOptionList(String fdId) throws Exception {
			List<Map> historyOptionMapList=new ArrayList();
			HQLInfo hqlInfo=new HQLInfo();
			hqlInfo.setWhereBlock("fsscBudgetingMain.docOriginDoc.fdId=:orgId");
			hqlInfo.setParameter("orgId", fdId);
			List<FsscBudgetingMain> historyOptionList=this.findList(hqlInfo);
			for(FsscBudgetingMain main:historyOptionList) {
				Map map=new HashMap<>();
				map.put("version", main.getDocMainVersion()+"."+main.getDocAuxiVersion());
				map.put("option", main.getFdApprovalOpinions());
				historyOptionMapList.add(map);
			}
			return historyOptionMapList;
		}
}
