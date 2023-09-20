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
import com.landray.kmss.eop.basedata.forms.EopBasedataInnerOrderForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataInnerOrderService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.hibernate.spi.HibernateWrapper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class EopBasedataInnerOrderServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataInnerOrderService {
	private IEopBasedataCompanyService eopBasedataCompanyService;
	
    public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}
    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataInnerOrder) {
            EopBasedataInnerOrder eopBasedataInnerOrder = (EopBasedataInnerOrder) model;
            eopBasedataInnerOrder.setDocAlterTime(new Date());
            eopBasedataInnerOrder.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataInnerOrderForm mainForm = (EopBasedataInnerOrderForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataInnerOrder eopBasedataInnerOrder = new EopBasedataInnerOrder();
		String parentId = requestContext.getParameter("parentId");
		if (StringUtil.isNotNull(parentId)) {
			EopBasedataInnerOrder parent = (EopBasedataInnerOrder) this.findByPrimaryKey(parentId, null, true);
			if (parent != null) {
				eopBasedataInnerOrder.setFdParent(parent);
			}
		}
        eopBasedataInnerOrder.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataInnerOrder.setDocCreateTime(new Date());
        eopBasedataInnerOrder.setDocAlterTime(new Date());
        eopBasedataInnerOrder.setDocCreator(UserUtil.getUser());
        eopBasedataInnerOrder.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataInnerOrder, requestContext);
        return eopBasedataInnerOrder;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataInnerOrder eopBasedataInnerOrder = (EopBasedataInnerOrder) model;
    }

    @Override
    public List<EopBasedataInnerOrder> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataInnerOrder.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }
    @SuppressWarnings("unchecked")
	@Override
	public void saveCopy(String ids, String modelName, String fdCompanyIds) throws Exception {
		List<String> comIds = Arrays.asList(fdCompanyIds.split(";"));
		List<EopBasedataInnerOrder> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有父级项目
		List<String> allIds = new ArrayList<String>();
		for(EopBasedataInnerOrder account:selectedAccountList){
			String[] hids = account.getFdHierarchyId().split("x");
			for(String hid:hids){
				if(StringUtil.isNotNull(hid)){
					allIds.add(hid);
				}
			}
		}
		String hql = "from "+EopBasedataInnerOrder.class.getName()+" where fdId in(:ids)";
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("ids", allIds);
		selectedAccountList = query.list();
		//保存原有层级关系 ，用于后续维护新增项目的层级
		Map<String,EopBasedataInnerOrder> oriExtendMap = new HashMap<String,EopBasedataInnerOrder>();
		List<String> codeList = new ArrayList<String>();
		for(EopBasedataInnerOrder order:selectedAccountList){
			codeList.add(order.getFdCode());
			oriExtendMap.put(order.getFdCode(), order);
		}
		//查询已存在的项目
		hql = "from "+EopBasedataInnerOrder.class.getName()+" where fdCode in(:codes) and fdCompany.fdId in(:fdCompanyIds)";
		query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("codes", codeList);
		query.setParameterList("fdCompanyIds",comIds);
		List<EopBasedataInnerOrder> existCenters = query.list();
		Map<String,EopBasedataInnerOrder> centerMap = new HashMap<String,EopBasedataInnerOrder>();
		Map<String,EopBasedataInnerOrder> extendMap = new HashMap<String,EopBasedataInnerOrder>();
		for(EopBasedataInnerOrder order:existCenters){
			centerMap.put(order.getFdCode(), order);
			extendMap.put(order.getFdCode(), order);
		}
		List<EopBasedataCompany> comps = eopBasedataCompanyService.findByPrimaryKeys(fdCompanyIds.split(";"));
		EopBasedataInnerOrder newOrder = null;
		String key = null;
		SysOrgPerson user = UserUtil.getUser();
		Date now = new Date();
		List<EopBasedataInnerOrder> addList = new ArrayList<EopBasedataInnerOrder>();
		for(EopBasedataInnerOrder order:selectedAccountList){
			for(EopBasedataCompany comp:comps){
				key = order.getFdCode()+comp.getFdCode();
				//如果当前公司已存在对应项目，直接跳过
				if(centerMap.containsKey(key)){
					continue;
				}
				newOrder = new EopBasedataInnerOrder();
				BeanUtils.copyProperties(newOrder,order);
				BeanUtils.setProperty(newOrder, "docCreator", user);
				BeanUtils.setProperty(newOrder, "docCreateTime", now);
				BeanUtils.setProperty(newOrder, "docAlterTime", now);
				BeanUtils.setProperty(newOrder, "fdCompany", comp);
				BeanUtils.setProperty(newOrder, "fdCompany", comp);
				newOrder.setFdId(null);
				addList.add(newOrder);
				extendMap.put(newOrder.getFdCode(), order);
			}
		}
		if(!ArrayUtil.isEmpty(addList)){
			//维护上下级关系
			EopBasedataInnerOrder parent = null;
			for(EopBasedataInnerOrder order:addList){
				//使用编号找到原成本中心的上级
				parent = (EopBasedataInnerOrder) oriExtendMap.get(order.getFdCode()).getFdParent();
				if(parent!=null){
					order.setFdParent(extendMap.get(parent.getFdCode()));
				}
			}
			getBaseDao().saveOrUpdateAll(addList);
		}
	}
	@Override
	public void saveEnable(String ids, String modelName) throws Exception {
		List<EopBasedataInnerOrder> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有父级科目 
		List<String> allIds = new ArrayList<String>();
		for(EopBasedataInnerOrder account:selectedAccountList){
			String[] hids = account.getFdHierarchyId().split("x");
			for(String hid:hids){
				if(StringUtil.isNotNull(hid)){
					allIds.add(hid);
				}
			}
		}
		selectedAccountList = findByPrimaryKeys(allIds.toArray(new String[allIds.size()]));
		for(EopBasedataInnerOrder account:selectedAccountList){
			account.setFdIsAvailable(true);
		}
		//更新档案的启用状态
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}
	@Override
	public void saveDisable(String ids, String modelName) throws Exception {
		List<EopBasedataInnerOrder> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有子级科目
		StringBuffer where = new StringBuffer();
		where.append("1=2 ");
		for(EopBasedataInnerOrder account:selectedAccountList){
			where.append("or eopBasedataInnerOrder.fdHierarchyId like '").append(account.getFdHierarchyId()).append("%' ");
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(where.toString());
		selectedAccountList = findList(hqlInfo);
		for(EopBasedataInnerOrder account:selectedAccountList){
			account.setFdIsAvailable(false);
		}
		//更新档案的启用状态
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}
}
