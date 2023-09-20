package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.forms.EopBasedataBerthForm;
import com.landray.kmss.eop.basedata.model.EopBasedataBerth;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataVehicle;
import com.landray.kmss.eop.basedata.service.IEopBasedataBerthService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.hibernate.spi.HibernateWrapper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class EopBasedataBerthServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataBerthService,IXMLDataBean {
private IEopBasedataCompanyService eopBasedataCompanyService;
	
    public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}
    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataBerth) {
            EopBasedataBerth eopBasedataBerth = (EopBasedataBerth) model;
            eopBasedataBerth.setDocAlterTime(new Date());
            eopBasedataBerth.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }
    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataBerthForm mainForm = (EopBasedataBerthForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}
    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataBerth eopBasedataBerth = new EopBasedataBerth();
        eopBasedataBerth.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataBerth.setDocCreateTime(new Date());
        eopBasedataBerth.setDocAlterTime(new Date());
        eopBasedataBerth.setDocCreator(UserUtil.getUser());
        eopBasedataBerth.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataBerth, requestContext);
        return eopBasedataBerth;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataBerth eopBasedataBerth = (EopBasedataBerth) model;
    }

    @Override
    public List<EopBasedataBerth> findByFdVehicle(EopBasedataVehicle fdVehicle) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataBerth.fdVehicle.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdVehicle.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataBerth> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataBerth.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }
	@Override
	public void saveCopy(String ids, String modelName, String fdCompanyIds) throws Exception {
		List<EopBasedataBerth> selectedData = findByPrimaryKeys(ids.split(";"));
		List<String> vehicleCodes = new ArrayList<String>();
		List<String> codes = new ArrayList<String>();
		for(EopBasedataBerth model:selectedData){
			vehicleCodes.add(model.getFdVehicle().getFdCode());
			codes.add(model.getFdCode());
		}
		//查询已存在的交通工具数据 
		String hql = "from "+EopBasedataVehicle.class.getName()+" where fdCode in(:codes) and fdCompany.fdId in(:fdCompanyIds)";
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("codes", vehicleCodes);
		query.setParameterList("fdCompanyIds", Arrays.asList(fdCompanyIds.split(";")));
		List<EopBasedataVehicle> list = query.list();
		Map<String,EopBasedataVehicle> existVehicelMap = new HashMap<String,EopBasedataVehicle>();
		for(EopBasedataVehicle vehicle:list){
			existVehicelMap.put(vehicle.getFdCode(), vehicle);
		}
		//查询已存在的舱位数据
		hql = "from "+EopBasedataBerth.class.getName()+" where fdCode in(:codes) and fdCompany.fdId in(:fdCompanyIds)";
		query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("codes", codes);
		query.setParameterList("fdCompanyIds", Arrays.asList(fdCompanyIds.split(";")));
		List<EopBasedataBerth> existData = query.list();
		//保存与映射
		Map<String,EopBasedataBerth> existMap = new HashMap<String,EopBasedataBerth>();
		for(EopBasedataBerth model:existData){
			existMap.put(model.getFdCode(), model);
		}
		EopBasedataBerth newModel = null;
		SysOrgPerson user = UserUtil.getUser();
		Date now = new Date();
		String key = null;
		List<EopBasedataCompany> comps = eopBasedataCompanyService.findByPrimaryKeys(fdCompanyIds.split(";"));
		List<EopBasedataBerth> addList = new ArrayList<EopBasedataBerth>();
		List<EopBasedataVehicle> relList = new ArrayList<EopBasedataVehicle>();
		for(EopBasedataBerth model:selectedData){
			for(EopBasedataCompany comp:comps){				
				key = model.getFdCode()+comp.getFdCode();
				//如果已存在，直接跳过
				if(existMap.containsKey(key)){
					continue;
				}
				newModel = new EopBasedataBerth();
				BeanUtils.copyProperties(newModel, model);
				BeanUtils.setProperty(newModel, "docCreator", user);
				BeanUtils.setProperty(newModel, "docAlteror", user);
				BeanUtils.setProperty(newModel, "docCreateTime", now);
				BeanUtils.setProperty(newModel, "docAlterTime", now);
				BeanUtils.setProperty(newModel, "fdCompany", comp);
				newModel.setFdId(null);
				//根据编号设置对应的交通工具
				key = model.getFdVehicle().getFdCode()+comp.getFdCode();
				if(existVehicelMap.containsKey(key)){
					newModel.setFdVehicle(existVehicelMap.get(key));
				}else{
					EopBasedataVehicle newVechicle = new EopBasedataVehicle();
					BeanUtils.copyProperties(newVechicle, model.getFdVehicle());
					BeanUtils.setProperty(newVechicle, "docCreator", user);
					BeanUtils.setProperty(newVechicle, "docAlteror", user);
					BeanUtils.setProperty(newVechicle, "docCreateTime", now);
					BeanUtils.setProperty(newVechicle, "docAlterTime", now);
					BeanUtils.setProperty(newVechicle, "fdCompany", comp);
					newVechicle.setFdId(null);
					relList.add(newVechicle);
					newModel.setFdVehicle(newVechicle);
					existVehicelMap.put(key, newVechicle);
				}
				addList.add(newModel);
			}
		}
		if(!ArrayUtil.isEmpty(addList)){
			getBaseDao().saveOrUpdateAll(addList);
		}
		if(!ArrayUtil.isEmpty(relList)){
			getBaseDao().saveOrUpdateAll(relList);
		}
	}
	
	/**
	 * 根据舱位，返回交通工具
	 */
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnList=new ArrayList<>();
		String fdBerthId=requestInfo.getParameter("fdBerthId");
		if(StringUtil.isNotNull(fdBerthId)){
			EopBasedataBerth berth=(EopBasedataBerth) this.findByPrimaryKey(fdBerthId, EopBasedataBerth.class, true);
			if(berth!=null){
				Map<String,String> map=new HashMap<>();
				map.put("fdVehicleId", berth.getFdVehicle()!=null?berth.getFdVehicle().getFdId():"");
				rtnList.add(map);
			}
		}
		return rtnList;
	}
}
