package com.landray.kmss.sys.mportal.service.spring;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.mportal.forms.SysMportalTopmenuAllForm;
import com.landray.kmss.sys.mportal.forms.SysMportalTopmenuForm;
import com.landray.kmss.sys.mportal.model.SysMportalTopmenu;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;

/**
 * @author 
 * @version 1.0 2015-11-13
 */
public class SysMportalTopmenuServiceImp 
	extends ExtendDataServiceImp implements ISysMportalTopmenuService {
	
	@SuppressWarnings("unchecked")
	@Override
	public SysMportalTopmenuAllForm getAllMenuForm() throws Exception {
		
		SysMportalTopmenuAllForm rtnForm = new SysMportalTopmenuAllForm();
		
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy(" sysMportalTopmenu.fdOrder asc");
		
		List<SysMportalTopmenu> menuList = 
			(List<SysMportalTopmenu>)this.findList(hqlInfo);
		RequestContext request = new RequestContext();
		for(SysMportalTopmenu menu : menuList) {
			IExtendForm form = convertModelToForm(new SysMportalTopmenuForm(),
					menu, request);
			rtnForm.getTopmenus().add(form);
		}
		return rtnForm;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateAll(SysMportalTopmenuAllForm form)
			throws Exception {
		RequestContext request = new RequestContext();
		List ids = new ArrayList<String>();
		AutoArrayList formList = form.getTopmenus();
		for(Object item  : formList) {
			SysMportalTopmenuForm 
				itemform = (SysMportalTopmenuForm)item;
			if(StringUtil.isNull(itemform.getFdId())) {
				itemform.setFdId(IDGenerator.generateID());
			}
			ids.add(itemform.getFdId());
			SysMportalTopmenu 
				menu = (SysMportalTopmenu)this.convertFormToModel(itemform, null, request);
			this.getBaseDao().update(menu);	
		}
		String hql = null;
		Query q =  null;
		if(ArrayUtil.isEmpty(ids)) {
			hql = " delete from " + SysMportalTopmenu.class.getName();
			q = this.getBaseDao().getHibernateSession().createQuery(hql);
		} else { 
			hql = " delete from " + SysMportalTopmenu.class.getName()
			+ " a where a.fdId not in(:ids)";
			q = this.getBaseDao().getHibernateSession().createQuery(hql);
			q.setParameterList("ids", ids);
		}
		q.executeUpdate();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray toItemData(RequestContext request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy(" sysMportalTopmenu.fdOrder asc");
		List <SysMportalTopmenu> list = 
			(List <SysMportalTopmenu>)this.findList(hqlInfo);
		JSONArray datas = new JSONArray();
		for (SysMportalTopmenu item : list) {
			JSONObject data = new JSONObject();
			data.element("icon", item.getFdIcon());
			data.element("url", item.getFdUrl());
			data.element("text", item.getFdName());
			datas.add(data);
		}
		return datas;
	}

}
