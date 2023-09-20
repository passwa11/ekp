package com.landray.kmss.fssc.budgeting.service.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetItemService;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetSchemeService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budgeting.constant.FsscBudgetingConstant;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingDetail;
import com.landray.kmss.fssc.budgeting.util.FsscBudgetingUtil;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;
/*************************************************
 * 功能描述：ajax调用获取信息，实现公司getDataList方法
 * ***********************************************/
public class FsscBudgetingDataService extends ExtendDataServiceImp implements IXMLDataBean{

    protected IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService;
    
    public void setEopBasedataBudgetSchemeService(
			IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService) {
    	if(eopBasedataBudgetSchemeService==null){
    		eopBasedataBudgetSchemeService=(IEopBasedataBudgetSchemeService) SpringBeanUtil.getBean("eopBasedataBudgetSchemeService");
    	}
		this.eopBasedataBudgetSchemeService = eopBasedataBudgetSchemeService;
	}
    
    protected IEopBasedataBudgetItemService eopBasedataBudgetItemService;
    

	public void setEopBasedataBudgetItemService(IEopBasedataBudgetItemService eopBasedataBudgetItemService) {
		if(eopBasedataBudgetItemService==null){
			eopBasedataBudgetItemService=(IEopBasedataBudgetItemService) SpringBeanUtil.getBean("eopBasedataBudgetItemService");
    	}
		this.eopBasedataBudgetItemService = eopBasedataBudgetItemService;
	}


	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnList=new ArrayList();
		String source=requestInfo.getParameter("source");
		String fdSchemeId=requestInfo.getParameter("fdSchemeId");
		HQLInfo hqlInfo=new HQLInfo();
		if("scheme".equals(source)){//获取预算方案期间和开关设置期间交集
			String fdCompanyId=requestInfo.getParameter("fdCompanyId");
			if(StringUtil.isNotNull(fdSchemeId)&&StringUtil.isNotNull(fdCompanyId)){
				EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) eopBasedataBudgetSchemeService.findByPrimaryKey(fdSchemeId);
				String fdPeriods=scheme.getFdPeriod()+";";
				if(StringUtil.isNotNull(fdCompanyId)&&fdPeriods.indexOf("1;")==-1){//不是无限的情况
					String rule=EopBasedataFsscUtil.getDetailPropertyValue(fdCompanyId, "fdRulePeriod");
					if(rule!=null){
						rule+=";";
						if(!"1;".equals(rule)&&rule!=null){  //说明预算导入规则设定非全年，才需要两边交集
							String[] str=fdPeriods.split(";");
							for(String s:str){
								if(rule.indexOf(s)==-1){
									fdPeriods=fdPeriods.replace(s+";", ""); //若是方案期间有，公司期间没有，则移除该期间
								}
							}
						}
					}
				}
				HashMap map = new HashMap();
				map.put("period", fdPeriods);
				rtnList.add(map);
			}
		}else if("budgetItemInfo".equals(source)){
			//判断当前选择预算科目是否为最末级预算科目，获取其父级科目
			String fdBudgetItemId=requestInfo.getParameter("fdBudgetItemId");
			HashMap map = new HashMap();
			if(StringUtil.isNotNull(fdBudgetItemId)){
				EopBasedataBudgetItem budgetItem=(EopBasedataBudgetItem) eopBasedataBudgetItemService.findByPrimaryKey(fdBudgetItemId);
				if(budgetItem!=null){
					EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) eopBasedataBudgetSchemeService.findByPrimaryKey(fdSchemeId);
					if(!FsscCommonUtil.isContain(scheme.getFdDimension(), "5;", ";")&&!FsscCommonUtil.isContain(scheme.getFdDimension(), "6;", ";")
	        				&&!FsscCommonUtil.isContain(scheme.getFdDimension(), "7;", ";")&&!FsscCommonUtil.isContain(scheme.getFdDimension(), "10;", ";")
	        				&&FsscCommonUtil.isContain(scheme.getFdDimension(), "8;", ";")){
						//不包含项目、wbs、内部订单、员工，则赋值上级ID，不然影响预算总额统计
					map.put("fdParentId", budgetItem.getFdParent()!=null?budgetItem.getFdParent().getFdId():"");
					}
					List result=eopBasedataBudgetItemService.getBaseDao().getHibernateSession()
							.createQuery("select count(t.fdId) from EopBasedataBudgetItem t where t.hbmParent.fdId=:fdParentId")
							.setParameter("fdParentId", budgetItem.getFdId()).list();
					if(ArrayUtil.isEmpty(result)){
						map.put("fdIsLastStage", "1"); //最末级预算科目
					}else{
						if(result.get(0)==null||Integer.parseInt(String.valueOf(result.get(0)))==0){
							map.put("fdIsLastStage", "1"); //最末级预算科目
						}else{
							map.put("fdIsLastStage", "0"); //非最末级预算科目
						}
					}
				}
			}
			if(FsscBudgetingConstant.FD_BUDTING_TYPE_DOWN.equals(EopBasedataFsscUtil.getSwitchValue("fdBudgetingType"))){
				String fdOrgId=requestInfo.getParameter("fdOrgId");
				SysOrgElement org=(SysOrgElement) eopBasedataBudgetSchemeService.findByPrimaryKey(fdOrgId, SysOrgElement.class, true);
				String fdparentOrgId=null;
				if(org!=null){
					fdparentOrgId=org.getFdParent()!=null?org.getFdParent().getFdId():null;
				}
				if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(requestInfo.getParameter("fdOrgType"))){
					//获取新选择的预算科目上级科目可申请金额
					map=getCanApplyByBudgetItem(requestInfo,map);
				}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(requestInfo.getParameter("fdOrgType"))
						&&StringUtil.isNotNull(fdparentOrgId)){
					//组织架构类型，非公司
					//获取新选择的预算科目上级科目可申请金额
					map=getCanApplyByBudgetItem(requestInfo,map);
				}
			}
			rtnList.add(map);
		}
		return rtnList;
	}

	/**
	 * 根据科目获取上级金额和剩余可申请金额
	 * @param requestInfo
	 * @param map
	 * @return
	 */
	public HashMap getCanApplyByBudgetItem(RequestContext requestInfo,HashMap map) throws Exception{
		String[] propertys={"fdTotal","fdPeriodOne","fdPeriodTwo","fdPeriodThree","fdPeriodFour","fdPeriodFive","fdPeriodSix",
				"fdPeriodSeven","fdPeriodEight","fdPeriodNine","fdPeriodTen","fdPeriodEleven","fdPeriodTwelve"};
		List canApply=new ArrayList<>();//剩余可申请金额
		List parent=new ArrayList<>();//上级预算金额
		String fdOrgId=requestInfo.getParameter("fdOrgId");
		String fdSchemeId=requestInfo.getParameter("fdSchemeId");
		String fdYear=requestInfo.getParameter("fdYear");
		String fdOrgType=requestInfo.getParameter("fdOrgType");
		String fdparentOrgId=null;
		if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(fdOrgType)){
			EopBasedataCostCenter org=(EopBasedataCostCenter) eopBasedataBudgetSchemeService.findByPrimaryKey(fdOrgId, EopBasedataCostCenter.class, true);
			//fdparentOrgId=org.getFdParent()!=null?org.getFdParent().getFdId():(org.getFdCompany()!=null?org.getFdCompany().getFdId():null);
		}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)){
			SysOrgElement org=(SysOrgElement) eopBasedataBudgetSchemeService.findByPrimaryKey(fdOrgId, SysOrgElement.class, true);
			fdparentOrgId=org.getFdParent()!=null?org.getFdParent().getFdId():null;
		}
		StringBuilder hql=new StringBuilder("select detail from FsscBudgetingDetail detail where detail.docMain.fdYear=:fdYear");
		hql.append(" and detail.docMain.fdSchemeId=:fdSchemeId and detail.docMain.fdOrgId=:fdOrgId ");
		hql.append(" and detail.docMain.docIsNewVersion=:docIsNewVersion ");
		hql.append(" and (detail.fdStatus=:audited or detail.fdStatus=:effect or detail.fdStatus=:examine)");
		hql.append(getDimendHql(fdSchemeId,requestInfo));
		Query query=eopBasedataBudgetSchemeService.getBaseDao().getHibernateSession().createQuery(hql.toString())
		.setParameter("fdYear", fdYear).setParameter("fdSchemeId", fdSchemeId)
		.setParameter("fdOrgId", fdparentOrgId)
		.setParameter("docIsNewVersion", true)
		.setParameter("audited", FsscBudgetingConstant.FD_STATUS_AUDITED)
		.setParameter("effect", FsscBudgetingConstant.FD_STATUS_EFFECT)
		.setParameter("examine", FsscBudgetingConstant.FD_STATUS_EXAMINE);
		setQueryParam(query,requestInfo);
		List<FsscBudgetingDetail> detailList=query.list();
		if(ArrayUtil.isEmpty(detailList)){//上级没有对应的科目，则可申请金额为0
			canApply=Arrays.asList(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
		}else{//上级有对应的科目
			FsscBudgetingDetail detail=detailList.get(0);
			for(int k=0;k<13;k++){
				parent.add(PropertyUtils.getProperty(detail, propertys[k]));
			}
			hql=new StringBuilder();
	   		hql.append("select sum(detail.fdTotal) as fdTotal, ");
	   		hql.append("sum(detail.fdPeriodOne) as fdPeriodOne,sum(detail.fdPeriodTwo) as fdPeriodTwo,");
	   		hql.append("sum(detail.fdPeriodThree) as fdPeriodThree,sum(detail.fdPeriodFour) as fdPeriodFour,");
	   		hql.append("sum(detail.fdPeriodFive) as fdPeriodFive,sum(detail.fdPeriodSix) as fdPeriodSix,");
	   		hql.append("sum(detail.fdPeriodSeven) as fdPeriodSeven,sum(detail.fdPeriodEight) as fdPeriodEight,");
	   		hql.append("sum(detail.fdPeriodNine) as fdPeriodNine,sum(detail.fdPeriodTen) as fdPeriodTen,");
	   		hql.append("sum(detail.fdPeriodEleven) as fdPeriodEleven,sum(detail.fdPeriodTwelve) as fdPeriodTwelve");
	   		hql.append(" from FsscBudgetingDetail detail ");
	   		hql.append(" where detail.docMain.fdYear=:fdYear");
	   		hql.append(" and  detail.docMain.fdSchemeId=:fdSchemeId and detail.docMain.docIsNewVersion=:docIsNewVersion ");
	   		hql.append(" and (detail.fdStatus=:audited or detail.fdStatus=:effect or detail.fdStatus=:examine)");
	   		hql.append(getDimendHql(fdSchemeId,requestInfo));
	   		if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_COSTCENTER.equals(fdOrgType)){
				hql.append(" and detail.docMain.fdOrgId in(select c.fdId from EopBasedataCostCenter c where (c.hbmParent.fdId=:fdOrgId or c.fdCompany.fdId=:fdOrgId))");
			}else if(FsscBudgetingConstant.FSSC_BUDGETING_ORGTYPE_DEPT.equals(fdOrgType)){
				hql.append(" and detail.docMain.fdOrgId in(select s.fdId from SysOrgElement s where s.hbmParent.fdId=:fdOrgId)");
			}
	   		query=eopBasedataBudgetSchemeService.getBaseDao().getHibernateSession().createQuery(hql.toString())
	   				.setParameter("fdYear", fdYear)
	   				.setParameter("fdSchemeId", fdSchemeId).setParameter("docIsNewVersion", true)
	   				.setParameter("fdOrgId", fdparentOrgId)
	   				.setParameter("audited", FsscBudgetingConstant.FD_STATUS_AUDITED)
	   				.setParameter("effect", FsscBudgetingConstant.FD_STATUS_EFFECT)
	   				.setParameter("examine", FsscBudgetingConstant.FD_STATUS_EXAMINE);
	   		setQueryParam(query,requestInfo);
	   		List<Object[]> result=query.list();
	   		if(ArrayUtil.isEmpty(result)){
	   			result.add(new Object[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0});;
	   		}
	   		if(parent.size()>0){
	   			for(int k=0;k<13;k++){
		   			canApply.add(FsscNumberUtil.getSubtraction(FsscBudgetingUtil.getObjVale(parent.get(k)), FsscBudgetingUtil.getObjVale(result.get(0)[k]), 2));
				}
	   		}
		}
		JSONObject canApplyObj=new JSONObject();
		if(canApply.size()>0){
			for(int k=0;k<13;k++){
				canApplyObj.put(propertys[k], canApply.get(k));
			}
		}
		JSONObject parentObj=new JSONObject();
		if(parent.size()>0){
			for(int k=0;k<13;k++){
				parentObj.put(propertys[k], FsscBudgetingUtil.getObjVale(parent.get(k)));
			}
		}
		map.put("canApply", canApplyObj);
		map.put("parent", parentObj);
		return map;
	}
	
	public Query setQueryParam(Query query, RequestContext requestInfo) throws Exception{
		SysDataDict dataDict = SysDataDict.getInstance();
    	Map<String, SysDictCommonProperty> propMap = dataDict.getModel(FsscBudgetingDetail.class.getName()).getPropertyMap();
		Map<String,List<String>> propertyMap=FsscBudgetingUtil.getPropertyByScheme(requestInfo.getParameter("fdSchemeId"));
		List<String> inPropertyList=propertyMap.get("inPropertyList");  //选中维度对应的属性
		for (String property : inPropertyList) {
			if(propMap.containsKey(property)){
				SysDictCommonProperty dict=propMap.get(property);
				if(dict.getType().startsWith("com.landray.kmss.")){
    				if(StringUtil.isNotNull(requestInfo.getParameter(property+"Id"))){
    					query.setParameter(property+"Id", requestInfo.getParameter(property+"Id"));
    				}
				}else{
					if(StringUtil.isNotNull(requestInfo.getParameter(property))){
						query.setParameter(property, requestInfo.getParameter(property));
					}
				}
			}
		}
		return query;
	}


	public String getDimendHql(String fdSchemeId,RequestContext requestInfo) throws Exception{
		StringBuilder where=new StringBuilder();
		SysDataDict dataDict = SysDataDict.getInstance();
    	Map<String, SysDictCommonProperty> propMap = dataDict.getModel(FsscBudgetingDetail.class.getName()).getPropertyMap();
		Map<String,List<String>> propertyMap=FsscBudgetingUtil.getPropertyByScheme(fdSchemeId);
		List<String> inPropertyList=propertyMap.get("inPropertyList");  //选中维度对应的属性
		List<String> notInPropertyList=propertyMap.get("notInPropertyList");  //未选中维度对应的属性
		for (String property : inPropertyList) {
			if(propMap.containsKey(property)){
				SysDictCommonProperty dict=propMap.get(property);
				if(dict.getType().startsWith("com.landray.kmss.")){
    				if(StringUtil.isNotNull(requestInfo.getParameter(property+"Id"))){
    					where.append(" and detail."+property+".fdId=:"+property+"Id");
    				}else{//需要的维度未传值，直接设置null
    					where.append(" and detail."+property+".fdId is null");
    				}
				}else{
					if(StringUtil.isNotNull(requestInfo.getParameter(property))){
						where.append(" and detail."+property+"=:"+property);
					}else{//需要的维度未传值，直接设置null
						where.append(" and detail."+property+" is null");
					}
				}
			}
		}
		for (String property : notInPropertyList) {
			if(propMap.containsKey(property)){
				SysDictCommonProperty dict=propMap.get(property);
				if(dict.getType().startsWith("com.landray.kmss.")){
					where.append(" and detail."+property+".fdId is null");
				}else{
					where.append(" and detail."+property+" is null");
				}
			}
		}
		return where.toString();
	}
}
