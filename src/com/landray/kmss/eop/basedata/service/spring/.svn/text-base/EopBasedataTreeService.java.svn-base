package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.service.IEopBasedataAccountsService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.StringUtil;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EopBasedataTreeService implements IXMLDataBean {

	private IEopBasedataAccountsService eopBasedataAccountsService;

	public void setEopBasedataAccountsService(IEopBasedataAccountsService eopBasedataAccountsService) {
		this.eopBasedataAccountsService = eopBasedataAccountsService;
	}
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String fdCompanyId = requestInfo.getParameter("fdCompanyId");
		String parentId = requestInfo.getParameter("parentId");
		String modelName = requestInfo.getParameter("modelName");
		String flag = requestInfo.getParameter("flag");
		SysDictModel model = SysDataDict.getInstance().getModel(modelName);

		StringBuffer hql = new StringBuffer();
		hql.append("select new map(");
		hql.append("tableName.fdId as value,");
		hql.append("tableName.fdName as text) from ");
		hql.append(modelName);
		hql.append(" tableName where 1=1");
		Map<String, Object> params = new HashMap<String, Object>();

		if(hasProperty(model,"fdIsAvailable")){
			hql.append(" and tableName.fdIsAvailable=:fdIsAvailable");
			params.put("fdIsAvailable", true);
		}
		if(hasProperty(model,"fdStatus")){
			hql.append(" and tableName.fdStatus=:fdStatus");
			params.put("fdStatus", "0");
		}
		if(StringUtil.isNotNull(fdCompanyId)&&!"null".equals(fdCompanyId)){
			hql.append(" and tableName.fdCompany.fdId=:fdCompanyId");
			params.put("fdCompanyId", fdCompanyId);
		}
		if(StringUtil.isNotNull(parentId)){
			hql.append(" and tableName.hbmParent.fdId=:parentId");
			params.put("parentId", parentId);
		}else{
			hql.append(" and tableName.hbmParent.fdId is null");
		}
		if("com.landray.kmss.eop.basedata.model.EopBasedataCostCenter".equals(modelName)&&"list".equals(flag)){
			hql.append(" and tableName.fdIsGroup=:fdIsGroup");
			params.put("fdIsGroup", EopBasedataConstant.FSSC_BASE_COST_CENTER_TYPE_GROUP);
			params.put("fdStatus", 0);
		}

		if(hasProperty(model,"fdOrder")){
			hql.append(" order by fdOrder asc ");
		}
		Query query = eopBasedataAccountsService.getBaseDao().getHibernateSession().createQuery(hql.toString());
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
