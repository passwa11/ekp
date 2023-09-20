package com.landray.kmss.fssc.budgeting.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetSchemeService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budgeting.constant.FsscBudgetingConstant;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscBudgetingNavService extends ExtendDataServiceImp implements IXMLDataBean{

    protected IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService;
    
    public void setEopBasedataBudgetSchemeService(
			IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService) {
    	if(eopBasedataBudgetSchemeService==null){
    		eopBasedataBudgetSchemeService=(IEopBasedataBudgetSchemeService) SpringBeanUtil.getBean("eopBasedataBudgetSchemeService");
    	}
		this.eopBasedataBudgetSchemeService = eopBasedataBudgetSchemeService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnList=new ArrayList();
		String queryFlag=requestInfo.getParameter("queryFlag");
    	String fdBudgetingType=EopBasedataFsscUtil.getSwitchValue("fdBudgetingType");//获取预算编制方式
    	HashMap mp =  new HashMap();
    	mp.put("type", "".equals(fdBudgetingType) || null == fdBudgetingType ? "0" : fdBudgetingType);
    	mp.put("budgetCurrency", EopBasedataFsscUtil.getSwitchValue("fdCommonBudgetCurrencyId"));  //判断公共预算币种是否设置了
    	rtnList.add(mp);
		HQLInfo hqlInfo=new HQLInfo();
		if("scheme".equals(queryFlag)){//导航查询预算方案
			StringBuilder where=new StringBuilder();
			where.append(" eopBasedataBudgetScheme.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setWhereBlock(where.toString());
			hqlInfo.setParameter("fdIsAvailable", true);
			List<EopBasedataBudgetScheme> schemeList=eopBasedataBudgetSchemeService.findList(hqlInfo);
			for (EopBasedataBudgetScheme scheme : schemeList) {
				HashMap map = new HashMap();
				map.put("name", scheme.getFdName());
				String value=scheme.getFdId();
				if(FsscCommonUtil.isContain(scheme.getFdDimension()+";", "11;", ";")){
					value+="|11";  //拼接部门标识，用于前台判断是否需要再次查询后台
				}else if(FsscCommonUtil.isContain(scheme.getFdDimension()+";", "4;", ";")){
					value+="|4";//拼接成本中心标识，用于前台判断是否需要再次查询后台
				}
				map.put("id", value);
				rtnList.add(map);
			}
		}else if("org".equals(queryFlag)){//拼接组织架构或者财务架构
			JSONArray orgData=new JSONArray();  //需要展现的信息，最高层级信息包含所有子级的信息
			JSONObject currTemp=new JSONObject();  //当前层级的信息
			JSONObject nextTemp=new JSONObject();  //当前层级的下一级信息
			int lenTemp=0;  //中间长度临时变量
			String fdSchemeId=requestInfo.getParameter("fdSchemeId");
			if(StringUtil.isNotNull(fdSchemeId)){
				EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) eopBasedataBudgetSchemeService.findByPrimaryKey(fdSchemeId, null, true);
				String fdDimension=scheme.getFdDimension()+";";
				if(FsscCommonUtil.isContain(fdDimension, "4;", ";")||FsscCommonUtil.isContain(fdDimension, "2;", ";")){//成本中心维度，显示财务组织架构
					JSONObject comTemp=new JSONObject();  //层级发生变化重新初始化本层级对象
					StringBuilder hql=new StringBuilder();
					/***************************成本中心关系处理  start******************************/
					if(FsscCommonUtil.isContain(fdDimension, "4;", ";")){
						hql.append("select t.fdId,t.fdName,parent.fdId,length(t.fdHierarchyId),company.fdId from EopBasedataCostCenter t ");
						hql.append(" left join t.fdCompanyList company left join t.hbmParent parent");
						hql.append(" where t.fdIsAvailable=:fdIsAvailable  ");
						//只查询预算方案使用的公司
						if(!ArrayUtil.isEmpty(scheme.getFdCompanys())){
							hql.append("and company.fdId in (select s.fdCompanys.fdId from EopBasedataBudgetScheme s where s.fdIsAvailable=:fdIsAvailable and s.fdId=:fdBudgetSchemeId)");
						}
						hql.append("order by length(t.fdHierarchyId) desc");
						Query query=eopBasedataBudgetSchemeService.getBaseDao().getHibernateSession().createQuery(hql.toString())
								.setParameter("fdIsAvailable", true);
						if(!ArrayUtil.isEmpty(scheme.getFdCompanys())){
							query.setParameter("fdBudgetSchemeId", scheme.getFdId());
						}
						List<Object[]> costCenterList=query.list();
						for(Object[] obj:costCenterList){
							if(obj.length>0){
								int length=Integer.parseInt(obj[3]!=null?obj[3].toString():"34");   //层级长度，根据层级长度的变化确认是否层级往上变化了。若无默认为最高级
								if(Math.abs(lenTemp-length)>0.001){//判断lenTemp和length是不是相等，整数相减大于0.001认为不相等
									lenTemp=length;
									nextTemp=currTemp;  //层级变了，将本层级的json对象赋值给下级接送对象
									currTemp=new JSONObject();  //层级发生变化重新初始化本层级对象
								}
								String fdParentId=obj[2]!=null?String.valueOf(obj[2]):"";
								JSONArray arr=new JSONArray();
								if(currTemp.containsKey(fdParentId)){
									//若是存在相同父级的id，直接获取数组，添加在一起
									arr=currTemp.getJSONArray(fdParentId);
								}
								JSONObject json=new JSONObject();
								json.put("tid", String.valueOf(obj[0]));
								json.put("name", String.valueOf(obj[1]));
								if(StringUtil.isNotNull(fdBudgetingType)) {
									//成本中心预算编制页面
									if(FsscBudgetingConstant.FD_BUDTING_TYPE_UP.equals(fdBudgetingType)){//自下而上(非公司/机构)
										json.put("url",requestInfo.getContextPath()+ "/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgeting&orgType=2&fdOrgId="
												+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId+"&fdCompanyId={companyId}");
									}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_DOWN.equals(fdBudgetingType)){ //自上而下(非公司/机构)
										json.put("url",requestInfo.getContextPath()+ "/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingChild&orgType=2&fdOrgId="
												+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId+"&fdCompanyId={companyId}");
									}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_INDEPENDENT.equals(fdBudgetingType)){//独立预算编制
										json.put("url",requestInfo.getContextPath()+ "/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingIndependent&orgType=2&fdOrgId="
												+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId+"&fdCompanyId={companyId}");
									}
								}
								if(!nextTemp.isEmpty()&&nextTemp.containsKey(String.valueOf(obj[0]))){
									json.put("childlist", nextTemp.get(String.valueOf(obj[0])));
								}else{
									json.put("childlist", new JSONArray());
								}
								arr.add(json);
								//若是无上级ID说明到了最高级，无需拼接json数组，直接将其加入到记账公司的数组里
								if(StringUtil.isNotNull(fdParentId)){
									//若是fdParentId的key已有，则更新对应的数组。没有则直接添加
									currTemp.put(fdParentId, arr); 
								}else{ 
									JSONArray array=new JSONArray();
									//最高层级成本中心，加入到公司
									if(comTemp.containsKey(String.valueOf(obj[4]))){
										array=comTemp.getJSONArray(String.valueOf(obj[4]));
									}
									array.addAll(arr);
									comTemp.put(String.valueOf(obj[4]), array); 
								}
							}
						}
					}
					/***************************成本中心关系处理  end******************************/
					/***************************记账公司和成本中心关系处理  start******************************/
					hql=new StringBuilder();
					hql.append("select t.fdId,t.fdName,companygroup.fdId from EopBasedataCompany t left join t.fdGroup companygroup  where t.fdIsAvailable=:fdIsAvailable ");
					//只查询预算方案使用的公司
					if(!ArrayUtil.isEmpty(scheme.getFdCompanys())){
						hql.append("and t.fdId in (select s.fdCompanys.fdId from EopBasedataBudgetScheme s where s.fdIsAvailable=:fdIsAvailable and s.fdId=:fdBudgetSchemeId)");
					}
					Query query=eopBasedataBudgetSchemeService.getBaseDao().getHibernateSession().createQuery(hql.toString())
							.setParameter("fdIsAvailable", true);
					if(!ArrayUtil.isEmpty(scheme.getFdCompanys())){
						query.setParameter("fdBudgetSchemeId", scheme.getFdId());
					}
					List<Object[]> companyList=query.list();
					for(Object[] obj:companyList){
						String fdId=String.valueOf(obj[0]);
						JSONArray array=new JSONArray();
						JSONObject json=new JSONObject();
						json.put("tid", String.valueOf(obj[0]));
						json.put("name", String.valueOf(obj[1]));
						//记账公司预算编制页面
						if(StringUtil.isNotNull(fdBudgetingType)) {
						if(FsscBudgetingConstant.FD_BUDTING_TYPE_UP.equals(fdBudgetingType)){//自下而上
								json.put("url", requestInfo.getContextPath()+ "/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingUp&orgType=3&fdOrgId="
										+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId+"&fdCompanyId="+String.valueOf(obj[0]));
							}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_DOWN.equals(fdBudgetingType)){ //自上而下
								json.put("url", requestInfo.getContextPath()+ "/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingInit&orgType=3&fdOrgId="
										+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId+"&fdCompanyId="+String.valueOf(obj[0]));
							}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_INDEPENDENT.equals(fdBudgetingType)){//独立预算编制
								json.put("url", requestInfo.getContextPath()+ "/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingIndependent&orgType=3&fdOrgId="
										+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId+"&fdCompanyId="+String.valueOf(obj[0]));
							}
						}
						//由于一个成本中心可能会有多个公司，所以需要拼接本次公司的ID
						if(comTemp.containsKey(fdId)||comTemp.containsKey("null")) {
							JSONArray comTempArr=comTemp.optJSONArray(fdId);
							if(comTempArr==null) {
								comTempArr=new JSONArray();
							}else {
								String comTempArrStr=comTempArr.toString();
								comTempArr=JSONArray.fromObject(comTempArrStr.replace("{companyId}", fdId));
							}
							JSONArray comTempArrNull=comTemp.optJSONArray("null");
							if(comTempArrNull==null) {
								comTempArrNull=new JSONArray();
							}else {
								String comTempArrNullStr=comTempArrNull.toString();
								comTempArrNull=JSONArray.fromObject(comTempArrNullStr.replace("{companyId}", fdId));
							}
							FsscCommonUtil.concatTwoJSON(comTempArrNull, comTempArr);
							comTemp.put(fdId, comTempArr);
						}else {
							json.put("childlist", new JSONArray());
						}
						json.put("childlist", comTemp.containsKey(fdId)?comTemp.getJSONArray(fdId):(new JSONArray()));
						array.add(json);
						if(obj[2]!=null){
							if(comTemp.containsKey(String.valueOf(obj[2]))){//公司组下多个公司
								JSONArray tempArr=(JSONArray) comTemp.get(String.valueOf(obj[2])); //获取原有值
								array.addAll(tempArr);
								comTemp.put(String.valueOf(obj[2]), array);  //公司对应的公司组id
							}else{
								comTemp.put(String.valueOf(obj[2]), array);  //公司对应的公司组id
							}
							comTemp.remove(fdId);
						}else{
							comTemp.put(fdId, json);  //无公司组则直接对应公司ID
						}
					}
					/***************************记账公司和成本中心关系处理  end******************************/
					/***************************公司组和记账公司关系处理  start******************************/
					hql=new StringBuilder();
					hql.append("select t.fdId,t.fdName,t.hbmParent.fdId,length(t.fdHierarchyId) from EopBasedataCompanyGroup t");
					hql.append(" where t.fdIsAvailable=:fdIsAvailable order by length(t.fdHierarchyId) desc");
					List<Object[]> groupList=eopBasedataBudgetSchemeService.getBaseDao().getHibernateSession().createQuery(hql.toString())
							.setParameter("fdIsAvailable", true).list();
					for(Object[] obj:groupList){
						String fdId=String.valueOf(obj[0]);
						JSONObject json=new JSONObject();
						json.put("tid", String.valueOf(obj[0]));
						json.put("name", String.valueOf(obj[1]));
						if(StringUtil.isNotNull(fdBudgetingType)) {
							//公司组预算编制页面
							if(FsscBudgetingConstant.FD_BUDTING_TYPE_UP.equals(fdBudgetingType)){//自下而上
								json.put("url", requestInfo.getContextPath()+ "/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingUp&orgType=4&fdOrgId="
										+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId);
							}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_DOWN.equals(fdBudgetingType)){ //自上而下
								json.put("url", requestInfo.getContextPath()+ "/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingInit&orgType=4&fdOrgId="
										+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId);
							}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_INDEPENDENT.equals(fdBudgetingType)){//独立预算编制
								json.put("url", requestInfo.getContextPath()+ "/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingIndependent&orgType=4&fdOrgId="
										+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId);
							}
						}
						if(comTemp.containsKey(fdId)){
							JSONArray arrTemp=new JSONArray();
							arrTemp.addAll(comTemp.getJSONArray(fdId));
							json.put("childlist", arrTemp);
							comTemp.remove(fdId);  //如果该对象是另外一个的子级，则将该对象放入其父级的child，并从该json对象中移除
							comTemp.put(fdId, json);  //公司组
						}else{
							//公司组不包含公司，则直接显示
							json.put("childlist", new JSONArray());
							orgData.add(json);
						}
					}
					Iterator iterator = comTemp.keys();
					 while(iterator.hasNext()){
						 String key=(String) iterator.next();
						 Object temp=comTemp.get(key);
						 String type=temp.getClass().toString();
						 if("class net.sf.json.JSONObject".equals(type)){
							 orgData.add(comTemp.getJSONObject(key));
						 }
					 }
					/***************************公司组和记账公司关系处理    end******************************/
				}else if(FsscCommonUtil.isContain(fdDimension, "11;", ";")||FsscCommonUtil.isContain(fdDimension, "10;", ";")){//部门/员工维度，显示组织架构
					StringBuilder hql=new StringBuilder();
					hql.append("select t.fdId,t.fdName,parent.fdId,length(t.fdHierarchyId),t.fdOrgType from SysOrgElement t left join t.hbmParent parent where (t.fdOrgType=:org1 or t.fdOrgType=:org2)");
					hql.append(" and t.fdIsAvailable=:fdIsAvailable order by length(t.fdHierarchyId) desc");
					List<Object[]> orgList=eopBasedataBudgetSchemeService.getBaseDao().getHibernateSession().createQuery(hql.toString())
							.setParameter("org1", SysOrgConstant.ORG_TYPE_ORG)
							.setParameter("org2", SysOrgConstant.ORG_TYPE_DEPT)
							.setParameter("fdIsAvailable", true).list();
					//倒序，层级最低的在最前面
					for(Object[] obj:orgList){
						if(obj.length>0){
							int length=Integer.parseInt(obj[3]!=null?obj[3].toString():"34");   //层级长度，根据层级长度的变化确认是否层级往上变化了。若无默认为最高级
							if(Math.abs(lenTemp-length)>0.001){//判断lenTemp和length是不是相等，整数相减大于0.001认为不相等
								lenTemp=length;
								nextTemp=currTemp;  //层级变了，将本层级的json对象赋值给下级接送对象
								currTemp=new JSONObject();  //层级发生变化重新初始化本层级对象
							}
							String fdParentId=obj[2]!=null?String.valueOf(obj[2]):"";
							JSONArray arr=new JSONArray();
							if(currTemp.containsKey(fdParentId)){
								//若是存在相同父级的id，直接获取数组，添加在一起
								arr=currTemp.getJSONArray(fdParentId);
							}
							JSONObject json=new JSONObject();
							json.put("tid", String.valueOf(obj[0]));
							json.put("name", String.valueOf(obj[1]));
							if(StringUtil.isNotNull(fdBudgetingType)) {
								//组织架构预算编制页面
								if(FsscBudgetingConstant.FD_BUDTING_TYPE_UP.equals(fdBudgetingType)){//自下而上
									if(obj[4]!=null&&Integer.parseInt(String.valueOf(obj[4]))==1){
										json.put("url", requestInfo.getContextPath()+"/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingUp&orgType=1&fdOrgId="
												+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId);
									}else{
										json.put("url", requestInfo.getContextPath()+"/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgeting&orgType=1&fdOrgId="
												+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId);
									}
								}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_DOWN.equals(fdBudgetingType)){ //自上而下
									if(obj[4]!=null&&Integer.parseInt(String.valueOf(obj[4]))==1){//机构/公司
										json.put("url", requestInfo.getContextPath()+"/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingInit&orgType=1&fdOrgId="
												+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId);
									}else{//非机构/公司
										json.put("url", requestInfo.getContextPath()+"/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingChild&orgType=1&fdOrgId="
												+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId);
									}
								}else if(FsscBudgetingConstant.FD_BUDTING_TYPE_INDEPENDENT.equals(fdBudgetingType)){//独立预算编制
									json.put("url", requestInfo.getContextPath()+"/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingIndependent&orgType=1&fdOrgId="
											+String.valueOf(obj[0])+"&fdSchemeId="+fdSchemeId);
								}
							}
							if(!nextTemp.isEmpty()&&nextTemp.containsKey(String.valueOf(obj[0]))){
								json.put("childlist", nextTemp.get(String.valueOf(obj[0])));
							}else{
								json.put("childlist", new JSONArray());
							}
							arr.add(json);
							//若是无上级ID说明到了最高级，无需拼接json数组，直接将其加入到展现的数组里
							if(StringUtil.isNotNull(fdParentId)){
								currTemp.put(fdParentId, arr); 
							}else{
								orgData.add(json);
							}
						}
					}
				}
			}
			HashMap map = new HashMap();
			map.put("orglist", orgData);
			rtnList.add(map);
		}
		return rtnList;
	}
}
