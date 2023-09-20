package com.landray.kmss.fssc.budget.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.fssc.budget.service.IFsscBudgetMainService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.hibernate.query.Query;


public class FsscBudgetValidateUtil{
	
	protected static IFsscBudgetMainService fsscBudgetMainService;
	
	public static IFsscBudgetMainService getFsscBudgetMainService() {
		if(fsscBudgetMainService==null){
			fsscBudgetMainService=(IFsscBudgetMainService) SpringBeanUtil.getBean("fsscBudgetMainService");
		}
		return fsscBudgetMainService;
	}

	/*********************************************
	 * 校验该属性是否在该公司下存在、有效
	 * @param fdCompanyId公司ID  需要校验该属性是否在公司存在必须传，判空可不传
	 * @param property 属性名  需要校验的属性，必传
	 * @param value excel值
	 * @param validateType isNull判断为空  是否存在  isExist
	 ********************************************/
	public static String validateProperty(String fdCompanyId,String property,String value,String validateType) throws Exception{
		String info="";
		if("isNull".equals(validateType)){//判空
			if(StringUtil.isNull(value)){
				info=ResourceUtil.getString("message.import.excel.null", "fssc-budget").replaceAll("\\{title\\}", 
						ResourceUtil.getString("fsscBudgetDetail."+property, "fssc-budget"));//为空
			}
		}else if("isExist".equals(validateType)){//判断是否有效或者归属正确的公司
			List<String> idList=new ArrayList<String>();
			if("fdDept".equals(property)||"fdPerson".equals(property)){
				StringBuilder hql=new StringBuilder();
				hql.append("select fdId from SysOrgElement t ");
				hql.append(" where t.fdNo=:fdNo and t.fdIsAvailable=:fdIsAvailable");
				idList=getFsscBudgetMainService().getBaseDao().getHibernateSession().createQuery(hql.toString())
						.setParameter("fdNo", value)
						.setParameter("fdIsAvailable", true).list();
				if(ArrayUtil.isEmpty(idList)){
					info=ResourceUtil.getString("message.import.excel.available", "fssc-budget").replaceAll("\\{title\\}", 
							ResourceUtil.getString("fsscBudgetDetail."+property, "fssc-budget"))
							.replaceAll("\\{value\\}", value); //无效或者不存在
				}
			}else{
				String table="EopBasedata"+property.substring(2);  //表名
				if("fdCostCenterGroup".equals(property)){
					table="EopBasedataCostCenter";  //成本中心组合成本中心同表，特殊处理
				}
				StringBuilder hql=new StringBuilder();
				hql.append("select fdId from "+table+" t ");
				if(StringUtil.isNotNull(fdCompanyId)){
					hql.append(" left join t.fdCompanyList comp ");  //成本中心组合成本中心同表，特殊处理
				}
				hql.append(" where  t.fdIsAvailable=:fdIsAvailable and t.fdCode=:fdCode ");
				if("fdCostCenterGroup".equals(property)||"fdCostCenter".equals(property)){
					hql.append(" and t.fdIsGroup=:isGroup");  //成本中心组合成本中心同表，特殊处理
				}
				if(StringUtil.isNotNull(fdCompanyId)){
					hql.append(" and (comp.fdId=:fdCompanyId or comp.fdId is null)");  //成本中心组合成本中心同表，特殊处理
				}
				Query query=getFsscBudgetMainService().getBaseDao().getHibernateSession().createQuery(hql.toString());
				if("fdCostCenterGroup".equals(property)){
					query.setParameter("isGroup", EopBasedataConstant.FSSC_BASE_COST_CENTER_TYPE_GROUP);  //成本中心组合成本中心同表，特殊处理，成本中心组
				}else if("fdCostCenter".equals(property)){
					query.setParameter("isGroup", EopBasedataConstant.FSSC_BASE_COST_CENTER_TYPE_CENTER);  //成本中心组合成本中心同表，特殊处理，成本中心
				}
				if(StringUtil.isNotNull(fdCompanyId)){
					query.setParameter("fdCompanyId", fdCompanyId);
				}
				idList=query.setParameter("fdIsAvailable", true).setParameter("fdCode", value).list();
				if(ArrayUtil.isEmpty(idList)){
					info=ResourceUtil.getString("message.import.excel.available", "fssc-budget")
							.replaceAll("\\{title\\}", ResourceUtil.getString("fsscBudgetDetail."+property, "fssc-budget"))
							.replaceAll("\\{value\\}", value); //无效或者不存在
				}
			}
		}
		return info;
	}
	/*********************************************
	 * 校验该属性是否在该公司下存在、有效
	 * @param fdCompanyId公司ID  需要校验该属性是否在公司存在必须传，判空可不传
	 * @param property 属性名  需要校验的属性，必传
	 * @param value excel值
	 * @param validateType isNull判断为空  是否存在  isExist
	 * 
	 * 适用大数据量校验
	 ********************************************/
	public static Map<String,Object> validatePropertys(String mudleType,String property,String value,String validateType) throws Exception{
		String info="";
		Map<String,Object> rtnMap=new HashMap<>();
		if("isNull".equals(validateType)){//判空
			if(StringUtil.isNull(value)){
				info=ResourceUtil.getString("message.import.excel.null", "fssc-budget").replaceAll("\\{title\\}", 
						ResourceUtil.getString("fsscBudgetDetail."+property, "fssc-budget"));//为空
			}
		}else if("isExist".equals(validateType)){//判断是否有效或者归属正确的公司
			if("basedata".equals(mudleType)){
				List<Object> objList=new ArrayList<Object>();
				String table="EopBasedata"+property.substring(2);  //表名
				if("fdCostCenterGroup".equals(property)){
					table="EopBasedataCostCenter";  //成本中心组合成本中心同表，特殊处理
				}
				StringBuilder hql=new StringBuilder();
				hql.append("select t from "+table+" t ");
				hql.append(" where  t.fdIsAvailable=:fdIsAvailable and t.fdCode=:fdCode ");
				objList=getFsscBudgetMainService().getBaseDao().getHibernateSession().createQuery(hql.toString())
						.setParameter("fdIsAvailable", true)
						.setParameter("fdCode", value).list();
				if(ArrayUtil.isEmpty(objList)){
					info=ResourceUtil.getString("message.import.excel.available", "fssc-budget")
							.replaceAll("\\{title\\}", ResourceUtil.getString("fsscBudgetDetail."+property, "fssc-budget"))
							.replaceAll("\\{value\\}", value); //无效或者不存在
				}else{
					rtnMap.put("object", objList.get(0));
				}
			}else if("org".equals(mudleType)){
				StringBuilder hql=new StringBuilder();
				hql.append("select t from SysOrgElement t ");
				hql.append(" where t.fdNo=:fdNo and t.fdIsAvailable=:fdIsAvailable");
				List<String> objList=getFsscBudgetMainService().getBaseDao().getHibernateSession().createQuery(hql.toString())
						.setParameter("fdNo", value)
						.setParameter("fdIsAvailable", true).list();
				if(ArrayUtil.isEmpty(objList)){
					info=ResourceUtil.getString("message.import.excel.available", "fssc-budget").replaceAll("\\{title\\}", 
							ResourceUtil.getString("fsscBudgetDetail."+property, "fssc-budget"))
							.replaceAll("\\{value\\}", value); //无效或者不存在
				}else{
					rtnMap.put("object", objList.get(0));
				}
			}else{
				info=ResourceUtil.getString("message.import.excel.no.company", "fssc-budget");
			}
		}
		rtnMap.put("info", info);
		return rtnMap;
	}
}
