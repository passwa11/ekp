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
import com.landray.kmss.eop.basedata.model.EopBasedataCashFlow;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataCashFlowService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class EopBasedataCashFlowServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataCashFlowService {
	private IEopBasedataCompanyService eopBasedataCompanyService;
    public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataCashFlow) {
            EopBasedataCashFlow eopBasedataCashFlow = (EopBasedataCashFlow) model;
            eopBasedataCashFlow.setDocAlterTime(new Date());
            eopBasedataCashFlow.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataCashFlow eopBasedataCashFlow = new EopBasedataCashFlow();
		String parentId = requestContext.getParameter("parentId");
		if (StringUtil.isNotNull(parentId)) {
			EopBasedataCashFlow parent = (EopBasedataCashFlow) this.findByPrimaryKey(parentId, null, true);
			if (parent != null) {
				eopBasedataCashFlow.setFdParent(parent);
			}
		}
        eopBasedataCashFlow.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataCashFlow.setDocCreateTime(new Date());
        eopBasedataCashFlow.setDocAlterTime(new Date());
        eopBasedataCashFlow.setDocCreator(UserUtil.getUser());
        eopBasedataCashFlow.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataCashFlow, requestContext);
        return eopBasedataCashFlow;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataCashFlow eopBasedataCashFlow = (EopBasedataCashFlow) model;
    }

    @Override
    public List<EopBasedataCashFlow> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataCashFlow.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

	@Override
	public void saveEnable(String ids, String modelName) throws Exception {
		List<EopBasedataCashFlow> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有父级科目 
		List<String> allIds = new ArrayList<String>();
		for(EopBasedataCashFlow account:selectedAccountList){
			String[] hids = account.getFdHierarchyId().split("x");
			for(String hid:hids){
				if(StringUtil.isNotNull(hid)){
					allIds.add(hid);
				}
			}
		}
		selectedAccountList = findByPrimaryKeys(allIds.toArray(new String[allIds.size()]));
		for(EopBasedataCashFlow account:selectedAccountList){
			account.setFdIsAvailable(true);
		}
		//更新档案的启用状态
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}

	@Override
	public void saveDisable(String ids, String modelName) throws Exception {
		List<EopBasedataCashFlow> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有子级科目
		StringBuffer where = new StringBuffer();
		where.append("1=2 ");
		for(EopBasedataCashFlow account:selectedAccountList){
			where.append("or eopBasedataCashFlow.fdHierarchyId like '").append(account.getFdHierarchyId()).append("%' ");
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(where.toString());
		selectedAccountList = findList(hqlInfo);
		for(EopBasedataCashFlow account:selectedAccountList){
			account.setFdIsAvailable(false);
		}
		//更新档案的启用状态
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}

	@Override
	public void saveCopy(String ids, String modelName, String fdCompanyIds) throws Exception {
		List<String> comIds = Arrays.asList(fdCompanyIds.split(";"));
		List<EopBasedataCashFlow> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有父级项目
		List<String> allIds = new ArrayList<String>();
		for(EopBasedataCashFlow account:selectedAccountList){
			String[] hids = account.getFdHierarchyId().split("x");
			for(String hid:hids){
				if(StringUtil.isNotNull(hid)){
					allIds.add(hid);
				}
			}
		}
		String hql = "from "+EopBasedataCashFlow.class.getName()+" where fdId in(:ids)";
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("ids", allIds);
		selectedAccountList = query.list();
		//保存原有层级关系 ，用于后续维护新增项目的层级
		Map<String,EopBasedataCashFlow> oriExtendMap = new HashMap<String,EopBasedataCashFlow>();
		List<String> codeList = new ArrayList<String>();
		for(EopBasedataCashFlow order:selectedAccountList){
			codeList.add(order.getFdCode());
			oriExtendMap.put(order.getFdCode(), order);
		}
		//查询已存在的项目
		hql = "from "+EopBasedataCashFlow.class.getName()+" where fdCode in(:codes) and fdCompany.fdId in(:fdCompanyIds)";
		query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("codes", codeList);
		query.setParameterList("fdCompanyIds",comIds);
		List<EopBasedataCashFlow> existCenters = query.list();
		Map<String,EopBasedataCashFlow> centerMap = new HashMap<String,EopBasedataCashFlow>();
		Map<String,EopBasedataCashFlow> extendMap = new HashMap<String,EopBasedataCashFlow>();
		for(EopBasedataCashFlow order:existCenters){
			centerMap.put(order.getFdCode(), order);
			extendMap.put(order.getFdCode(), order);
		}
		List<EopBasedataCompany> comps = eopBasedataCompanyService.findByPrimaryKeys(fdCompanyIds.split(";"));
		EopBasedataCashFlow newOrder = null;
		String key = null;
		SysOrgPerson user = UserUtil.getUser();
		Date now = new Date();
		List<EopBasedataCashFlow> addList = new ArrayList<EopBasedataCashFlow>();
		for(EopBasedataCashFlow order:selectedAccountList){
			for(EopBasedataCompany comp:comps){
				key = order.getFdCode()+comp.getFdCode();
				//如果当前公司已存在对应项目，直接跳过
				if(centerMap.containsKey(key)){
					continue;
				}
				newOrder = new EopBasedataCashFlow();
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
			EopBasedataCashFlow parent = null;
			for(EopBasedataCashFlow order:addList){
				//使用编号找到原成本中心的上级
				parent = (EopBasedataCashFlow) oriExtendMap.get(order.getFdCode()).getFdParent();
				if(parent!=null){
					order.setFdParent(extendMap.get(parent.getFdCode()));
				}
			}
			getBaseDao().saveOrUpdateAll(addList);
		}
	}

	/**
	 * 根据会计科目获取现金流量项目
	 * @throws Exception 
	 */
	@Override
	public EopBasedataCashFlow getEopBasedataCashFlow(String fdAccountId ,String fdCompanyId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		if(StringUtil.isNotNull(fdCompanyId)){
        	hqlInfo.setJoinBlock(" left join eopBasedataCashFlow.fdCompanyList company ");
            hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                    "(company.fdId=:fdCompanyId or company is null)"));
            hqlInfo.setParameter("fdCompanyId", fdCompanyId);
        }
		if(StringUtil.isNotNull(fdAccountId)){
        	hqlInfo.setJoinBlock(hqlInfo.getJoinBlock() + " left join eopBasedataCashFlow.fdAccountsList accounts");
            hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                    "(accounts.fdId=:fdAccountsId)"));
            hqlInfo.setParameter("fdAccountsId", fdAccountId);
        }
        List<EopBasedataCashFlow> list=  this.findList(hqlInfo);
        if(list.size()>0) {
        	return list.get(0);
        } else {
        	return null;
        }
	}
}
