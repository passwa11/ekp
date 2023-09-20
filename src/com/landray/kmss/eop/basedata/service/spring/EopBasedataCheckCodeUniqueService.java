package com.landray.kmss.eop.basedata.service.spring;

import java.util.List;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;

public class EopBasedataCheckCodeUniqueService implements IXMLDataBean{
	private IEopBasedataCompanyService eopBasedataCompanyService;

	public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String fdId = requestInfo.getParameter("fdId");
		String fdCode = requestInfo.getParameter("fdCode");
		String fdCompanyId = requestInfo.getParameter("fdCompanyId");
		String modelName = requestInfo.getParameter("modelName");
		StringBuffer hql = new StringBuffer();
		hql.append("select new map(count(*) as count) from ");
		hql.append(modelName+" model");
		if(StringUtil.isNotNull(fdCompanyId)){
			hql.append(" left join model.fdCompanyList company ");
		}
		hql.append("  where model.fdCode=:fdCode ");
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		if(dict.getPropertyMap().containsKey("fdStatus")){
			hql.append(" and  model.fdStatus =:status ");
		}else{
			hql.append(" and  model.fdIsAvailable !=:fdIsAvailable ");
		}
		if(StringUtil.isNotNull(fdId)){
			hql.append("and model.fdId<>:fdId ");
		}
		if(StringUtil.isNull(fdCode)){
			hql.append(" and 1=2 ");
		}
		if(StringUtil.isNotNull(fdCompanyId)){
			hql.append("and ("+HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";")))+" or company is null) ");
		}
		Query query = eopBasedataCompanyService.getBaseDao().getHibernateSession().createQuery(hql.toString());
		query.setParameter("fdCode", fdCode);
		if(StringUtil.isNotNull(fdId)){
			query.setParameter("fdId", fdId);
		}
		if(dict.getPropertyMap().containsKey("fdStatus")){
			query.setParameter("status", 0);
		}else{
			query.setParameter("fdIsAvailable", Boolean.FALSE);
		}
		return query.list();
	}
}
