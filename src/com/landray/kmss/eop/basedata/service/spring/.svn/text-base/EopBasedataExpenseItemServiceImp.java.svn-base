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
import com.landray.kmss.eop.basedata.forms.EopBasedataExpenseItemForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataExpenseItemService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.hibernate.spi.HibernateWrapper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class EopBasedataExpenseItemServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataExpenseItemService {
	private IEopBasedataCompanyService eopBasedataCompanyService;
    public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}
    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataExpenseItem) {
            EopBasedataExpenseItem eopBasedataExpenseItem = (EopBasedataExpenseItem) model;
            eopBasedataExpenseItem.setDocAlterTime(new Date());
            eopBasedataExpenseItem.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataExpenseItemForm mainForm = (EopBasedataExpenseItemForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataExpenseItem eopBasedataExpenseItem = new EopBasedataExpenseItem();
		String parentId = requestContext.getParameter("parentId");
		if (StringUtil.isNotNull(parentId)) {
			EopBasedataExpenseItem parent = (EopBasedataExpenseItem) this.findByPrimaryKey(parentId, null, true);
			if (parent != null) {
				eopBasedataExpenseItem.setFdParent(parent);
			}
		}
        eopBasedataExpenseItem.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataExpenseItem.setDocCreateTime(new Date());
        eopBasedataExpenseItem.setDocAlterTime(new Date());
        eopBasedataExpenseItem.setDocCreator(UserUtil.getUser());
        eopBasedataExpenseItem.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataExpenseItem, requestContext);
        return eopBasedataExpenseItem;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataExpenseItem eopBasedataExpenseItem = (EopBasedataExpenseItem) model;
    }

    @Override
    public List<EopBasedataExpenseItem> findByDocParent(EopBasedataExpenseItem docParent) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataExpenseItem.hbmParent.fdId=:fdId");
        hqlInfo.setParameter("fdId", docParent.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataExpenseItem> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataExpenseItem.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataExpenseItem> findByFdBudgetItems(EopBasedataBudgetItem fdBudgetItems) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataExpenseItem.fdBudgetItems.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdBudgetItems.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataExpenseItem> findByFdAccounts(EopBasedataAccounts fdAccounts) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataExpenseItem.fdAccounts.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdAccounts.getFdId());
        return this.findList(hqlInfo);
    }
    @SuppressWarnings("unchecked")
	@Override
	public void saveCopy(String ids, String modelName, String fdCompanyIds) throws Exception {
		List<String> comIds = Arrays.asList(fdCompanyIds.split(";"));
		List<EopBasedataExpenseItem> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有父级费用类型
		List<String> allIds = new ArrayList<String>();
		for(EopBasedataExpenseItem account:selectedAccountList){
			String[] hids = account.getFdHierarchyId().split("x");
			for(String hid:hids){
				if(StringUtil.isNotNull(hid)){
					allIds.add(hid);
				}
			}
		}
		String hql = "from "+EopBasedataExpenseItem.class.getName()+" where fdId in(:ids)";
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("ids", allIds);
		selectedAccountList = query.list();
		//保存原有层级关系 ，用于后续维护新增费用类型的层级
		Map<String,EopBasedataExpenseItem> oriExtendMap = new HashMap<String,EopBasedataExpenseItem>();
		List<String> codeList = new ArrayList<String>();
		for(EopBasedataExpenseItem order:selectedAccountList){
			codeList.add(order.getFdCode());
			oriExtendMap.put(order.getFdCode(), order);
		}
		//查询已存在的费用类型
		hql = "from "+EopBasedataExpenseItem.class.getName()+" where fdCode in(:codes) and fdCompany.fdId in(:fdCompanyIds)";
		query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("codes", codeList);
		query.setParameterList("fdCompanyIds",comIds);
		List<EopBasedataExpenseItem> existCenters = query.list();
		Map<String,EopBasedataExpenseItem> centerMap = new HashMap<String,EopBasedataExpenseItem>();
		Map<String,EopBasedataExpenseItem> extendMap = new HashMap<String,EopBasedataExpenseItem>();
		for(EopBasedataExpenseItem order:existCenters){
			centerMap.put(order.getFdCode(), order);
			extendMap.put(order.getFdCode(), order);
		}
		List<EopBasedataCompany> comps = eopBasedataCompanyService.findByPrimaryKeys(fdCompanyIds.split(";"));
		EopBasedataExpenseItem newOrder = null;
		String key = null;
		SysOrgPerson user = UserUtil.getUser();
		Date now = new Date();
		List<EopBasedataExpenseItem> addList = new ArrayList<EopBasedataExpenseItem>();
		for(EopBasedataExpenseItem order:selectedAccountList){
			for(EopBasedataCompany comp:comps){
				key = order.getFdCode()+comp.getFdCode();
				//如果当前公司已存在对应费用类型，直接跳过
				if(centerMap.containsKey(key)){
					continue;
				}
				newOrder = new EopBasedataExpenseItem();
				BeanUtils.copyProperties(newOrder,order);
				BeanUtils.setProperty(newOrder, "docCreator", user);
				BeanUtils.setProperty(newOrder, "docCreateTime", now);
				BeanUtils.setProperty(newOrder, "docAlterTime", now);
				BeanUtils.setProperty(newOrder, "fdCompany", comp);
				newOrder.setFdId(null);
				newOrder.setFdAccounts(null);
				newOrder.setFdBudgetItems(null);
				addList.add(newOrder);
				extendMap.put(newOrder.getFdCode(), order);
			}
		}
		if(!ArrayUtil.isEmpty(addList)){
			//维护上下级关系
			EopBasedataExpenseItem parent = null;
			for(EopBasedataExpenseItem order:addList){
				//使用编号找到原费用类型的上级
				parent = (EopBasedataExpenseItem) oriExtendMap.get(order.getFdCode()).getFdParent();
				if(parent!=null){
					order.setFdParent(extendMap.get(parent.getFdCode()));
				}
			}
			getBaseDao().saveOrUpdateAll(addList);
		}
	}
	@Override
	public void saveEnable(String ids, String modelName) throws Exception {
		List<EopBasedataExpenseItem> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有父级科目 
		List<String> allIds = new ArrayList<String>();
		for(EopBasedataExpenseItem account:selectedAccountList){
			String[] hids = account.getFdHierarchyId().split("x");
			for(String hid:hids){
				if(StringUtil.isNotNull(hid)){
					allIds.add(hid);
				}
			}
		}
		selectedAccountList = findByPrimaryKeys(allIds.toArray(new String[allIds.size()]));
		for(EopBasedataExpenseItem account:selectedAccountList){
			account.setFdIsAvailable(true);
		}
		//更新档案的启用状态
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}
	@Override
	public void saveDisable(String ids, String modelName) throws Exception {
		List<EopBasedataExpenseItem> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有子级科目
		StringBuffer where = new StringBuffer();
		where.append("1=2 ");
		for(EopBasedataExpenseItem account:selectedAccountList){
			where.append("or eopBasedataExpenseItem.fdHierarchyId like '").append(account.getFdHierarchyId()).append("%' ");
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(where.toString());
		selectedAccountList = findList(hqlInfo);
		for(EopBasedataExpenseItem account:selectedAccountList){
			account.setFdIsAvailable(false);
		}
		//更新档案的启用状态
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}
}
