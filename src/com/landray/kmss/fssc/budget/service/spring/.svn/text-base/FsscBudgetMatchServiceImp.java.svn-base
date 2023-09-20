package com.landray.kmss.fssc.budget.service.spring;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.slf4j.Logger;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataItemBudget;
import com.landray.kmss.eop.basedata.service.IEopBasedataItemBudgetService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budget.constant.FsscBudgetConstant;
import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetMatchService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscBudgetMatchServiceImp extends ExtendDataServiceImp implements IFsscCommonBudgetMatchService {
	
	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(FsscBudgetMatchServiceImp.class);
	
	protected IEopBasedataItemBudgetService eopBasedataItemBudgetService;
	
    public void setEopBasedataItemBudgetService(IEopBasedataItemBudgetService eopBasedataItemBudgetService) {
		this.eopBasedataItemBudgetService = eopBasedataItemBudgetService;
	}
    
    protected IFsscBudgetDataService fsscBudgetDataService;
    
	public void setFsscBudgetDataService(IFsscBudgetDataService fsscBudgetDataService) {
		this.fsscBudgetDataService = fsscBudgetDataService;
	}
	
	protected ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	/********************************************************
     * 功能：匹配预算
     * @param messageJson 匹配预算信息
     * ******************************************************/
	
	@Override
	public JSONObject matchFsscBudget(JSONObject messageJson) throws Exception {
		JSONObject jsonObject=new JSONObject();
		StringBuilder errorMessage=new StringBuilder();
		JSONArray budgetArray=new JSONArray();
		try {
			String fdBudgetScopeIds=EopBasedataFsscUtil.getSwitchValue("fdBudgetScopeIds");  //获取预算启用范围
			if (StringUtil.isNotNull(fdBudgetScopeIds)) {
				// 启用预算范围Id
				List<String> scopeOrgIds = ArrayUtil.convertArrayToList(fdBudgetScopeIds.split(";"));
				//公司
				String fdCompanyIdOrCode=messageJson.containsKey("fdCompanyId")?messageJson.getString("fdCompanyId"):"";
				if(StringUtil.isNull(fdCompanyIdOrCode)){
					//若是Id为空，则获取编码
					fdCompanyIdOrCode=messageJson.containsKey("fdCompanyCode")?messageJson.getString("fdCompanyCode"):"";
				}
				Query query=null;
				String hql="";
				//获取记账公司，因为方案无公司情况，需要根据单据所传公司预算币和预算币种转换，故公司参数必传
				EopBasedataCompany company=null;
				if(StringUtil.isNotNull(fdCompanyIdOrCode)){
					hql="select eopBasedataCompany from EopBasedataCompany eopBasedataCompany where (eopBasedataCompany.fdCode = :fdCompanyIdOrCode or eopBasedataCompany.fdId = :fdCompanyIdOrCode)";
					query=fsscBudgetDataService.getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdCompanyIdOrCode", fdCompanyIdOrCode);
					List<EopBasedataCompany> cmpanyList=query.list();
					if(!ArrayUtil.isEmpty(cmpanyList)){
						//若是Id为空，则获取编码
						company=cmpanyList.get(0);
					}
				}
				if(company!=null) {
					//成本中心
					String fdCostCenterIdOrCode=messageJson.containsKey("fdCostCenterId")?messageJson.getString("fdCostCenterId"):"";
					if(StringUtil.isNull(fdCostCenterIdOrCode)){
						//若是Id为空，则获取编码
						fdCostCenterIdOrCode=messageJson.containsKey("fdCostCenterCode")?messageJson.getString("fdCostCenterCode"):"";
					}
					EopBasedataCostCenter costCenter=null;
					if(StringUtil.isNotNull(fdCostCenterIdOrCode)){
						hql="select eopBasedataCostCenter from EopBasedataCostCenter eopBasedataCostCenter left join eopBasedataCostCenter.fdCompanyList company  where (eopBasedataCostCenter.fdCode = :fdCostCenterIdOrCode or eopBasedataCostCenter.fdId = :fdCostCenterIdOrCode)";
						if(StringUtil.isNotNull(fdCompanyIdOrCode)){
							hql+=" and (company.fdCode = :fdCompanyIdOrCode or company.fdId = :fdCompanyIdOrCode or company is null)";
						}
						query=fsscBudgetDataService.getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdCostCenterIdOrCode", fdCostCenterIdOrCode);
						if(StringUtil.isNotNull(fdCompanyIdOrCode)){
							query.setParameter("fdCompanyIdOrCode", fdCompanyIdOrCode);
						}
						List<EopBasedataCostCenter> centerList=query.list();
						if(!ArrayUtil.isEmpty(centerList)){
							//若是Id为空，则获取编码
							costCenter=centerList.get(0);
						}
					}
					//费用类型
					String fdExpenseItemIdOrCode=messageJson.containsKey("fdExpenseItemId")?messageJson.getString("fdExpenseItemId"):"";
					if(StringUtil.isNull(fdExpenseItemIdOrCode)){
						fdExpenseItemIdOrCode=messageJson.containsKey("fdExpenseItemCode")?messageJson.getString("fdExpenseItemCode"):"";
					}
					EopBasedataExpenseItem expenseItem=null;
					if(StringUtil.isNotNull(fdExpenseItemIdOrCode)){
						hql="select eopBasedataExpenseItem from EopBasedataExpenseItem eopBasedataExpenseItem left join eopBasedataExpenseItem.fdCompanyList company where (eopBasedataExpenseItem.fdCode = :fdExpenseItemIdOrCode or eopBasedataExpenseItem.fdId = :fdExpenseItemIdOrCode)";
						if(StringUtil.isNotNull(fdCompanyIdOrCode)){
							hql+=" and (company.fdCode = :fdCompanyIdOrCode or company.fdId = :fdCompanyIdOrCode or company is null)";
						}
						query=fsscBudgetDataService.getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdExpenseItemIdOrCode", fdExpenseItemIdOrCode);
						if(StringUtil.isNotNull(fdCompanyIdOrCode)){
							query.setParameter("fdCompanyIdOrCode", fdCompanyIdOrCode);
						}
						List<EopBasedataExpenseItem> itemList=query.list();
						if(!ArrayUtil.isEmpty(itemList)){
							expenseItem=itemList.get(0);
						}
					}
					//人员
					String fdPersonIdOrNo=messageJson.containsKey("fdPersonId")?messageJson.getString("fdPersonId"):"";
					if(StringUtil.isNull(fdPersonIdOrNo)){
						fdPersonIdOrNo=messageJson.containsKey("fdPersonNo")?messageJson.getString("fdPersonNo"):"";
					}
					SysOrgElement person=null;
					if(StringUtil.isNotNull(fdPersonIdOrNo)){
						List<SysOrgElement> personList=sysOrgElementService.getBaseDao().getHibernateSession().createQuery("select sysOrgElement from SysOrgElement sysOrgElement where sysOrgElement.fdNo = :fdPersonIdOrNo or sysOrgElement.fdId = :fdPersonIdOrNo")
								.setParameter("fdPersonIdOrNo", fdPersonIdOrNo).list();
						if(!ArrayUtil.isEmpty(personList)){
							person=personList.get(0);
						}
					}
					//部门
					String fdDeptIdOrNo=messageJson.containsKey("fdDeptId")?messageJson.getString("fdDeptId"):"";
					if(StringUtil.isNull(fdDeptIdOrNo)){
						fdDeptIdOrNo=messageJson.containsKey("fdDeptNo")?messageJson.getString("fdDeptNo"):"";
					}
					if (expenseItem != null) {
						HQLInfo hqlInfo=new HQLInfo();
						hqlInfo.setWhereBlock(" eopBasedataItemBudget.fdItems.fdId=:fdExpenseItemId and eopBasedataItemBudget.fdIsAvailable=:fdIsAvailable");
						hqlInfo.setParameter("fdExpenseItemId", expenseItem.getFdId());
						hqlInfo.setParameter("fdIsAvailable", true);
						List<EopBasedataItemBudget> itemBudgetList=eopBasedataItemBudgetService.findList(hqlInfo);
						if (!ArrayUtil.isEmpty(itemBudgetList)) {
							for (EopBasedataItemBudget itemBudget : itemBudgetList) {
								// 得到预算科目Id
								String budgetItemId =EopBasedataFsscUtil.getBudgetItemIds(costCenter,expenseItem);
								// 得到预算分类
								EopBasedataBudgetScheme budgetScheme = (EopBasedataBudgetScheme) fsscBudgetDataService.findByPrimaryKey(itemBudget.getFdCategory().getFdId(),
										EopBasedataBudgetScheme.class, true);
								if (budgetScheme != null) {
									// 费用类型-预算控制行政架构id
									List<String> itemBudgetOrg =new ArrayList<String>();
									String[] fdOrgsArr=ArrayUtil.joinProperty( itemBudget.getFdOrgs(), "fdId", ";");
									if(fdOrgsArr.length>0&&StringUtil.isNotNull(fdOrgsArr[0])){
										itemBudgetOrg=ArrayUtil.convertArrayToList(fdOrgsArr[0].split(";"));
									}
									// 人员层级id分隔
									List<String> fdEkpOrgIds=new ArrayList<String>();
									if(person!=null){
										if(person.getFdIsAvailable()){//过滤无效的人员或者组织架构
											ArrayUtil.concatTwoList(ArrayUtil.convertArrayToList(person
													.getFdHierarchyId().substring(1,person.getFdHierarchyId().length() - 1)
													.split("x")), fdEkpOrgIds);
										}
									}
									if(isBudgetControl(fdEkpOrgIds,itemBudgetOrg,scopeOrgIds)){
										messageJson.put("fdBudgetSchemeId", budgetScheme.getFdId()); //预算方案
										if(StringUtil.isNotNull(budgetItemId)){
											messageJson.put("fdBudgetItemId", budgetItemId); //预算科目
										}
										//重新匹配预算，回传原来的预算信息。若是回传的预算未结转，出现跨月也要匹配提交时匹配的预算。若已结转，则直接匹配当月预算
										if(messageJson.containsKey("fdBudgetInfo")&&StringUtil.isNotNull(messageJson.optString("fdBudgetInfo"))
												&&JSONArray.fromObject(messageJson.get("fdBudgetInfo")).size()>0){
											JSONArray fdBudgetInfo=JSONArray.fromObject(messageJson.get("fdBudgetInfo"));
											for(int m=0,len=fdBudgetInfo.size();m<len;m++){
												JSONObject budgetJson=fdBudgetInfo.getJSONObject(m);
												String fdBudgetId=budgetJson.containsKey("fdBudgetId")?budgetJson.getString("fdBudgetId"):"";
												List<String> idList=new ArrayList<>();
												idList.add(fdBudgetId);
												if(StringUtil.isNotNull(fdBudgetId)){
													if(fsscBudgetDataService.checkBudgetIsKnots(idList)){//存在已结转，则认为所有相关预算都已结转，匹配当前月份，跳出循环
														JSONArray rtnArray=fsscBudgetDataService.findBudget(messageJson, FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_ALL,messageJson.containsKey("fdYear")?messageJson.getString("fdYear"):null, messageJson.containsKey("fdPeriod")?messageJson.getString("fdPeriod"):null);
														FsscCommonUtil.concatTwoJSON(rtnArray, budgetArray);
														break;
													}else{//未结转
														String fdPeriodType=budgetJson.containsKey("fdPeriodType")?budgetJson.getString("fdPeriodType"):"";
														String fdPeriod=budgetJson.containsKey("fdPeriod")?budgetJson.getString("fdPeriod"):"";
														String year=messageJson.optString("fdYear");
														if(StringUtil.isNull(year)) {
															FsscBudgetData budget=(FsscBudgetData) fsscBudgetDataService.findByPrimaryKey(fdBudgetId, null, true);
															if(budget.getFdYear()!=null&&StringUtil.isNotNull(budget.getFdYear())) {
																messageJson.put("fdYear", budget.getFdYear().substring(1, 5));
															}
														}
														JSONArray rtnArray=fsscBudgetDataService.findBudget(messageJson, fdPeriodType, messageJson.optString("fdYear"), fdPeriod);
														FsscCommonUtil.concatTwoJSON(rtnArray, budgetArray);
													}
												}
											}
										}else{
											JSONArray rtnArray=fsscBudgetDataService.findBudget(messageJson, FsscBudgetConstant.FSSC_BUDGET_PERIOD_TYPE_ALL,messageJson.containsKey("fdYear")?messageJson.getString("fdYear"):null, messageJson.containsKey("fdPeriod")?messageJson.getString("fdPeriod"):null);
											FsscCommonUtil.concatTwoJSON(rtnArray, budgetArray);
										}
									}
								} else {
									logger.error(ResourceUtil.getString("message.budget.no.scheme", "fssc-budget"));//"没找到预算方案"写入本系统日志文件
									errorMessage.append(ResourceUtil.getString("message.budget.no.scheme", "fssc-budget"));  //写入接口返回信息
								}
							}

						} else {
							logger.error(ResourceUtil.getString("message.budget.expenseItem.no.budget.control", "fssc-budget"));
							errorMessage.append(ResourceUtil.getString("message.budget.expenseItem.no.budget.control", "fssc-budget"));  //"费用类型没有做预算控制"，写入接口返回信息
						}
					} else {
						logger.error(ResourceUtil.getString("message.budget.main.notfound.expenseItem", "fssc-budget"));
						errorMessage.append(ResourceUtil.getString("message.budget.main.notfound.expenseItem", "fssc-budget"));  //"没找到费用类型"，写入接口返回信息
					}
				}else {
					logger.error(ResourceUtil.getString("message.budget.match.error.company.isnull", "fssc-budget"));
					errorMessage.append(ResourceUtil.getString("message.budget.match.error.company.isnull", "fssc-budget"));  //"没找到公司"，写入接口返回信息
				}
			}

		} catch (Exception e) {
			jsonObject.put("result", "0");  //后台运行代码报错异常
			jsonObject.put("errorMessage", ResourceUtil.getString("message.budget.match.error", "fssc-budget")+e.getMessage());  //"匹配预算信息出错，错误信息为："
			logger.error("预算匹配出错，错误原因：",e.getMessage());
			e.printStackTrace();
		}
		if(StringUtil.isNotNull(errorMessage.toString())){
			jsonObject.put("result", "1");  //匹配预算有异常，程序无异常
			jsonObject.put("errorMessage", ResourceUtil.getString("message.budget.match.error", "fssc-budget")+errorMessage);
		}else{
			jsonObject.put("result", "2");  //匹配成功
			jsonObject.put("data", budgetArray);
		}
		return jsonObject;
	}
	
	/**
	 * 
	 * @param fdEkpOrgIds ：公司、成本中心组、成本中心、人员等对应行政架构层级id
	 * @param itemBudgetOrg ：费用类型-预算控制维护
	 * @param scopeOrgIds ：预算启用范围
	 * @return
	 * @throws Exception
	 */
	private boolean isBudgetControl(List<String> fdEkpOrgIds, List<String> itemBudgetOrg,
			List<String> scopeOrgIds) throws Exception {
		boolean rtnBoolean=false;
		List<String> retainList =new ArrayList<String>();
		if(ArrayUtil.isEmpty(fdEkpOrgIds)){//说明人员是无效人员
			retainList=itemBudgetOrg;
		}else{
			retainList=fdEkpOrgIds;//则是成本中心对应组织架构和费用类型-预算控制维护的交集
		}
		retainList.retainAll(itemBudgetOrg);// 得到交集
		if(!ArrayUtil.isEmpty(retainList)){
			//交集若为空，说明报人员不在费用类型组织架构范围内，直接返回false
			//交集不为空，则报销人员在费用类型组织架构范围内
			List<String> retainListHierarchys = findRetainListHierarchys(retainList);// 交集层级id并集去重
			// 启用预算，并且启用预算范围为空或者应用范围与retainListHierarchys有交集
			if (!ArrayUtil.isEmpty(scopeOrgIds)&& ArrayUtil.isListIntersect(scopeOrgIds,retainListHierarchys)) {
				rtnBoolean=true;
			}
		}
		return rtnBoolean;
	}
	
	private List<String> findRetainListHierarchys(List<String> retainList)
			throws Exception {
		List<String> rtnList = new ArrayList<String>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOrgElement.fdIsAvailable=:fdIsAvailable and "+HQLUtil.buildLogicIN("sysOrgElement.fdId", retainList));
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setSelectBlock("sysOrgElement.fdHierarchyId");
		List<String> hierarchyIdStrs = sysOrgElementService.findValue(hqlInfo);
		if (!ArrayUtil.isEmpty(hierarchyIdStrs)) {
			for (String hierarchyId : hierarchyIdStrs) {
				if ("0".equals(hierarchyId)) {
					continue;
				} else {
					ArrayUtil.concatTwoList(ArrayUtil
							.convertArrayToList(hierarchyId.substring(1,
									hierarchyId.length() - 1).split("x")),
							rtnList);
				}
			}
		}
		return rtnList;
	}
}
