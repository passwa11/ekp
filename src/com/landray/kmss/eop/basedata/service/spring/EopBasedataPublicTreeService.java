package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.service.IEopBasedataMateCateService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.StringUtil;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EopBasedataPublicTreeService implements IXMLDataBean {
	
	private IEopBasedataMateCateService eopBasedataMateCateService;

	public void setEopBasedataMateCateService(IEopBasedataMateCateService eopBasedataMateCateService) {
		this.eopBasedataMateCateService = eopBasedataMateCateService;
	}
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String parentId = requestInfo.getParameter("parentId");
		String modelName = requestInfo.getParameter("modelName");
		Map<String,Object> params = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer();
		//该判断逻辑下，说明是物料-物料类别-各不同物料展示
		String flag=requestInfo.getParameter("flag");
		hql.append("select new map(");
		hql.append("tableName.fdId as value,");
		hql.append("tableName.fdName as text) from ");
		if(!StringUtil.isNotNull(parentId) && "com.landray.kmss.eop.basedata.model.EopBasedataMateCate".equals(modelName)) {
			hql.append(modelName);
				SysDictModel model = SysDataDict.getInstance().getModel(modelName);
			hql.append(" tableName where 1=1");
				if(hasProperty(model,"hbmParent")){
					if(StringUtil.isNotNull(parentId)){
						hql.append(" and tableName.hbmParent.fdId=:parentId");
						params.put("parentId", parentId);
					}else{
						hql.append(" and tableName.hbmParent.fdId is null ");
			}
				}
				return getQueryList(hql,params,model);
		}else if(StringUtil.isNotNull(parentId) && "com.landray.kmss.eop.basedata.model.EopBasedataMateCate".equals(modelName)) {
			hql.append(modelName);
			hql.append(" tableName where 1=1");
			SysDictModel model = SysDataDict.getInstance().getModel(modelName);
			if(hasProperty(model,"hbmParent")){
				if(StringUtil.isNotNull(parentId)){
					hql.append(" and tableName.hbmParent.fdId=:parentId");
					params.put("parentId", parentId);
				}else{
					hql.append(" and tableName.hbmParent.fdId is null ");
				}
			}
			return getQueryList(hql,params,model);
		}else if(!StringUtil.isNotNull(parentId) && "com.landray.kmss.eop.basedata.model.EopBasedataMaterial".equals(modelName)){
				modelName = "com.landray.kmss.eop.basedata.model.EopBasedataMateCate";
				hql.append(modelName);
				hql.append(" tableName where 1=1");
				SysDictModel model = SysDataDict.getInstance().getModel(modelName);
				if(hasProperty(model,"hbmParent")){
					if(StringUtil.isNotNull(parentId)){
						hql.append(" and tableName.hbmParent.fdId=:parentId");
						params.put("parentId", parentId);
					}else{
						hql.append(" and tableName.hbmParent.fdId is null ");
					}
				}
				return getQueryList(hql,params,model);
		}else {
			//集合中应该包括物料类别和物料两种实体
			StringBuffer hql2 = new StringBuffer();
			hql2.append(hql.toString());
			//获取可能存在的物料集合
			hql2.append(modelName);
			hql2.append(" tableName where 1=1");
			SysDictModel model = SysDataDict.getInstance().getModel(modelName);
			if(hasProperty(model,"fdType")){
				hql2.append(" and tableName.fdType.fdId=:parentId");
				params.put("parentId", parentId);
			}
			List list = getQueryList(hql2,params,model);
			//获取可能存在的物料类别集合
			hql2.setLength(0); //清空
			hql2.append(hql.toString());
			hql2.append("com.landray.kmss.eop.basedata.model.EopBasedataMateCate");
			SysDictModel model2 = SysDataDict.getInstance().getModel("com.landray.kmss.eop.basedata.model.EopBasedataMateCate");
			hql2.append(" tableName where 1=1");
			if(hasProperty(model2,"hbmParent")){
				if(StringUtil.isNotNull(parentId)){
					hql2.append(" and tableName.hbmParent.fdId=:parentId");
					params.put("parentId", parentId);
				}else{
					hql2.append(" and tableName.hbmParent.fdId is null ");
				}
			}
			List list2 = getQueryList(hql2,params,model);
			list.addAll(list2);
			return list;
		}
	}
	private List getQueryList(StringBuffer hql,Map<String,Object> params,SysDictModel model) throws Exception {
		if(hasProperty(model,"fdIsAvailable")){
			hql.append(" and tableName.fdIsAvailable=:fdIsAvailable");
			params.put("fdIsAvailable", true);
		}
		if(hasProperty(model,"fdStatus")){
			hql.append(" and tableName.fdStatus=:fdStatus");
			params.put("fdStatus", 0);
		}
		if(hasProperty(model,"fdOrder")){
			hql.append(" order by fdOrder asc nulls first ");
		}
		Query query = eopBasedataMateCateService.getBaseDao().getHibernateSession().createQuery(hql.toString());
		for(Iterator<String> it = params.keySet().iterator();it.hasNext();){
			String key = it.next();
			query.setParameter(key, params.get(key));
		}
		return query.list();
	}
	
	private Boolean hasProperty(SysDictModel model ,String Property){
		List<SysDictCommonProperty> properties = model.getPropertyList();
		for(SysDictCommonProperty p:properties){
			if(p.getName().equals(Property)){
				return true;
			}
		}
		return false;
	}

}
