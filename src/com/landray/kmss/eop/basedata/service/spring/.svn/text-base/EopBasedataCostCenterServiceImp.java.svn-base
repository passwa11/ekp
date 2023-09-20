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
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCostType;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostCenterService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostTypeService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class EopBasedataCostCenterServiceImp extends EopBasedataBusinessServiceImp
		implements IEopBasedataCostCenterService, IXMLDataBean {
	private IEopBasedataCompanyService eopBasedataCompanyService;

    public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}

	protected IEopBasedataCostTypeService eopBasedataCostTypeService;

	public void setEopBasedataCostTypeService(IEopBasedataCostTypeService eopBasedataCostTypeService) {
		this.eopBasedataCostTypeService = eopBasedataCostTypeService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataCostCenter) {
            EopBasedataCostCenter eopBasedataCostCenter = (EopBasedataCostCenter) model;
            eopBasedataCostCenter.setDocAlterTime(new Date());
            eopBasedataCostCenter.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataCostCenter eopBasedataCostCenter = new EopBasedataCostCenter();
		String parentId = requestContext.getParameter("parentId");
		if (StringUtil.isNotNull(parentId)) {
			EopBasedataCostCenter parent = (EopBasedataCostCenter) this.findByPrimaryKey(parentId, null, true);
			if (parent != null) {
				eopBasedataCostCenter.setFdParent(parent);
			}
		}
        eopBasedataCostCenter.setFdIsAvailable(Boolean.valueOf("true"));
       String fdFinancialSystem = EopBasedataFsscUtil.getSwitchValue("fdFinancialSystem");
		if (StringUtil.isNotNull(fdFinancialSystem)) {
			String[] property = fdFinancialSystem.split(";");
			requestContext.setAttribute("financialSystemList", ArrayUtil.convertArrayToList(property));
		}
        eopBasedataCostCenter.setDocCreateTime(new Date());
        eopBasedataCostCenter.setDocAlterTime(new Date());
        eopBasedataCostCenter.setDocCreator(UserUtil.getUser());
        eopBasedataCostCenter.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataCostCenter, requestContext);
        return eopBasedataCostCenter;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataCostCenter eopBasedataCostCenter = (EopBasedataCostCenter) model;
    }

    @Override
    public List<EopBasedataCostCenter> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataCostCenter.fdCompanyList.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataCostCenter> findByFdType(EopBasedataCostType fdType) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataCostCenter.fdType.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdType.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataCostCenter> findByDocParent(EopBasedataCostCenter docParent) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataCostCenter.hbmParent.fdId=:fdId");
        hqlInfo.setParameter("fdId", docParent.getFdId());
        return this.findList(hqlInfo);
    }

	/**
	 * 判断成本中心组是否为最末级，true为最末级，false为非末级
	 * 
	 * @param center
	 * @return
	 * @throws Exception
	 */
	public boolean checkIsLastStage(EopBasedataCostCenter center) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		StringBuilder whereBlock=new StringBuilder();
		whereBlock.append(" eopBasedataCostCenter.docParent.fdId=:docParentId");
		hqlInfo.setWhereBlock(whereBlock.toString());
		return ArrayUtil.isEmpty(this.findList(hqlInfo));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveCopy(String ids, String modelName, String fdCompanyIds) throws Exception {
		List<String> comIds = Arrays.asList(fdCompanyIds.split(";"));
		List<EopBasedataCostCenter> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有父级科目 
		List<String> allIds = new ArrayList<String>();
		for(EopBasedataCostCenter account:selectedAccountList){
			String[] hids = account.getFdHierarchyId().split("x");
			for(String hid:hids){
				if(StringUtil.isNotNull(hid)){
					allIds.add(hid);
				}
			}
		}
		String hql = "from "+EopBasedataCostCenter.class.getName()+" where fdId in(:ids)";
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("ids", allIds);
		selectedAccountList = query.list();
		//保存原有层级关系 ，用于后续维护新增成本中心的层级
		Map<String,EopBasedataCostCenter> oriExtendMap = new HashMap<String,EopBasedataCostCenter>();
		List<String> typeList = new ArrayList<String>();
		List<String> codeList = new ArrayList<String>();
		for(EopBasedataCostCenter center:selectedAccountList){
			EopBasedataCostType type = center.getFdType();
			codeList.add(center.getFdCode());
			oriExtendMap.put(center.getFdCode(), center);
			if(!typeList.contains(type.getFdCode())){
				typeList.add(type.getFdCode());
			}
		}
		//查询所选公司下对应成本中心类型
		List<EopBasedataCostType> typeListExists = new ArrayList<EopBasedataCostType>();
		if(!ArrayUtil.isEmpty(typeList)){
			hql = "from "+EopBasedataCostType.class.getName()+" where fdCode in(:codes) and fdCompany.fdId in(:fdCompanyIds)";
			query = getBaseDao().getHibernateSession().createQuery(hql);
			query.setParameterList("codes", typeList);
			query.setParameterList("fdCompanyIds",comIds);
			typeListExists = query.list();
		}
		Map<String,EopBasedataCostType> typeMap = new HashMap<String,EopBasedataCostType>();
		for(EopBasedataCostType type:typeListExists){
			typeMap.put(type.getFdCode(), type);
		}
		//查询已存在的成本中心
		hql = "from "+EopBasedataCostCenter.class.getName()+" where fdCode in(:codes) and fdCompany.fdId in(:fdCompanyIds)";
		query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("codes", codeList);
		query.setParameterList("fdCompanyIds",comIds);
		List<EopBasedataCostCenter> existCenters = query.list();
		Map<String,EopBasedataCostCenter> centerMap = new HashMap<String,EopBasedataCostCenter>();
		Map<String,EopBasedataCostCenter> extendMap = new HashMap<String,EopBasedataCostCenter>();
		for(EopBasedataCostCenter center:existCenters){
			centerMap.put(center.getFdCode(), center);
			extendMap.put(center.getFdCode(), center);
		}
		List<EopBasedataCompany> comps = eopBasedataCompanyService.findByPrimaryKeys(fdCompanyIds.split(";"));
		EopBasedataCostCenter newCenter = null;
		String key = null;
		SysOrgPerson user = UserUtil.getUser();
		Date now = new Date();
		List<EopBasedataCostCenter> addList = new ArrayList<EopBasedataCostCenter>();
		List<EopBasedataCostType> relList = new ArrayList<EopBasedataCostType>();
		EopBasedataCostType newType = null;
		for(EopBasedataCostCenter center:selectedAccountList){
			for(EopBasedataCompany comp:comps){
				key = center.getFdCode()+comp.getFdCode();
				//如果当前公司已存在对应成本中心，直接跳过
				if(centerMap.containsKey(key)){
					continue;
				}
				newCenter = new EopBasedataCostCenter();
				BeanUtils.copyProperties(newCenter,center);
				BeanUtils.setProperty(newCenter, "docCreator", user);
				BeanUtils.setProperty(newCenter, "docCreateTime", now);
				BeanUtils.setProperty(newCenter, "docAlteror", user);
				BeanUtils.setProperty(newCenter, "docAlterTime", now);
				BeanUtils.setProperty(newCenter, "fdCompany", comp);
				newCenter.setFdId(null);
				EopBasedataFsscUtil.copyCollectionProperty(center, newCenter, "fdFirstCharger");
				EopBasedataFsscUtil.copyCollectionProperty(center, newCenter, "fdSecondCharger");
				EopBasedataFsscUtil.copyCollectionProperty(center, newCenter, "fdManager");
				EopBasedataFsscUtil.copyCollectionProperty(center, newCenter, "fdBudgetManager");
				EopBasedataFsscUtil.copyCollectionProperty(center, newCenter, "fdEkpOrg");
				key = center.getFdType().getFdCode()+comp.getFdCode();
				if(typeMap.containsKey(key)){
					newCenter.setFdType(typeMap.get(key));
				}else{
					newType = new EopBasedataCostType();
					BeanUtils.copyProperties(newType, center.getFdType());
					BeanUtils.setProperty(newType, "docCreator", user);
					BeanUtils.setProperty(newType, "docCreateTime", now);
					BeanUtils.setProperty(newType, "docAlteror", user);
					BeanUtils.setProperty(newType, "docAlterTime", now);
					BeanUtils.setProperty(newType, "fdCompany", comp);
					newType.setFdId(null);
					relList.add(newType);
					newCenter.setFdType(newType);
					typeMap.put(newType.getFdCode()+comp.getFdCode(), newType);
				}
				
				addList.add(newCenter);
				extendMap.put(newCenter.getFdCode(), center);
			}
		}
		if(!ArrayUtil.isEmpty(addList)){
			//维护上下级关系
			EopBasedataCostCenter parent = null;
			for(EopBasedataCostCenter center:addList){
				//使用编号找到原成本中心的上级
				parent = (EopBasedataCostCenter) oriExtendMap.get(center.getFdCode()).getFdParent();
				if(parent!=null){
					center.setFdParent(extendMap.get(parent.getFdCode()));
				}
			}
			getBaseDao().saveOrUpdateAll(addList);
		}
		if(!ArrayUtil.isEmpty(relList)){
			getBaseDao().saveOrUpdateAll(relList);
		}
	}

	@Override
	public List getDataList(RequestContext request) throws Exception {
		String flag = request.getParameter("flag");
		String fdCompanyId=request.getParameter("fdCompanyId");
		List rtnList = new ArrayList();
		if ("costCenterType".equals(flag)) {
			// 获取成本中心的类型
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("new Map(eopBasedataCostType.fdId as id,eopBasedataCostType.fdName as name)");
			hqlInfo.setWhereBlock(
					"eopBasedataCostType.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", true);
			if(StringUtil.isNotNull(fdCompanyId)){
				hqlInfo.setJoinBlock(" left join eopBasedataCostType.fdCompanyList company");
				if(fdCompanyId.indexOf(";")>-1) {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"("+HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";"))))+" or company is null)");
				}else {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"(company.fdId=:fdCompanyId or company is null)"));
					hqlInfo.setParameter("fdCompanyId", fdCompanyId);
				}
			}else{	//公司为空时，只查询公共的成本中心类型
				hqlInfo.setJoinBlock(" left join eopBasedataCostType.fdCompanyList company");
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
						"company is null"));
			}
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			rtnList = eopBasedataCostTypeService.findList(hqlInfo);
		}else if("defaultCostCenter".equals(flag)){
			String fdPersonId=request.getParameter("fdPersonId");
			EopBasedataCostCenter defaultCostCenter=findCostCenterByUserId(fdCompanyId,fdPersonId);
			if(defaultCostCenter!=null){
				Map map=new HashMap();
				map.put("id", defaultCostCenter.getFdId());
				map.put("name", defaultCostCenter.getFdName());
				rtnList.add(map);
			}
		}else if("group".equals(flag)){
			String fdParentId = request.getParameter("fdParentId");
			// 获取成本中心组
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setJoinBlock(" left join eopBasedataCostCenter.hbmParent parent ");
						hqlInfo.setSelectBlock("new Map(eopBasedataCostCenter.fdId as value,eopBasedataCostCenter.fdName as text, parent.fdId as fdParentId)");
						String where ="eopBasedataCostCenter.fdIsAvailable=:fdIsAvailable and eopBasedataCostCenter.fdIsGroup=:fdIsGroup";
						if(StringUtil.isNotNull(fdParentId)){
							where+=" and parent.fdId =:fdParentId";
							hqlInfo.setParameter("fdParentId", fdParentId);
						}else{
							where+=" and parent.fdId is null";
						}
						hqlInfo.setWhereBlock(where);
						hqlInfo.setParameter("fdIsAvailable", true);
						hqlInfo.setParameter("fdIsGroup", "2");
						rtnList = this.findList(hqlInfo);
		}else if("notGroup".equals(flag)){
			String fdGroupId = request.getParameter("fdGroupId");
			// 获取成本中心组
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("new Map(eopBasedataCostCenter.fdId as value,eopBasedataCostCenter.fdName as text, '1' as isExpanded, '0' as isAutoFetch)");
			hqlInfo.setJoinBlock(" left join eopBasedataCostCenter.hbmParent parent ");
			hqlInfo.setWhereBlock(
					"eopBasedataCostCenter.fdIsAvailable=:fdIsAvailable and "
					+ "eopBasedataCostCenter.fdIsGroup=:fdIsGroup and parent.fdId=:fdGroupId");
			hqlInfo.setParameter("fdIsAvailable", true);
			hqlInfo.setParameter("fdIsGroup", "1");
			hqlInfo.setParameter("fdGroupId", fdGroupId);
			rtnList = this.findList(hqlInfo);
}
		return rtnList;
	}

	@Override
	public void saveEnable(String ids, String modelName) throws Exception {
		List<EopBasedataCostCenter> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有父级科目 
		List<String> allIds = new ArrayList<String>();
		for(EopBasedataCostCenter account:selectedAccountList){
			String[] hids = account.getFdHierarchyId().split("x");
			for(String hid:hids){
				if(StringUtil.isNotNull(hid)){
					allIds.add(hid);
				}
			}
		}
		selectedAccountList = findByPrimaryKeys(allIds.toArray(new String[allIds.size()]));
		for(EopBasedataCostCenter account:selectedAccountList){
			account.setFdIsAvailable(true);
		}
		//更新档案的启用状态
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}

	@Override
	public void saveDisable(String ids, String modelName) throws Exception {
		List<EopBasedataCostCenter> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有子级科目
		StringBuffer where = new StringBuffer();
		where.append("1=2 ");
		for(EopBasedataCostCenter account:selectedAccountList){
			where.append("or eopBasedataCostCenter.fdHierarchyId like '").append(account.getFdHierarchyId()).append("%' ");
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(where.toString());
		selectedAccountList = findList(hqlInfo);
		for(EopBasedataCostCenter account:selectedAccountList){
			account.setFdIsAvailable(false);
		}
		//更新档案的启用状态
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}
	@SuppressWarnings("unchecked")
	@Override
	public EopBasedataCostCenter getEopBasedataCostCenterByCode(String fdCompanyId,
			String fdCode) throws Exception {
		if(StringUtil.isNull(fdCompanyId) || StringUtil.isNull(fdCode)){
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock("left join eopBasedataCostCenter.fdCompanyList comp");
		hqlInfo.setWhereBlock(" (comp.fdId = :fdCompanyId or comp.fdId is null) and UPPER(eopBasedataCostCenter.fdCode) = :fdCode ");
		hqlInfo.setParameter("fdCompanyId", fdCompanyId);
		hqlInfo.setParameter("fdCode", fdCode.toUpperCase());
		List<EopBasedataCostCenter> list = this.findList(hqlInfo);
		return ArrayUtil.isEmpty(list)?null:list.get(0);
	}
	
	/***********************************************************
	 * 根据人员ID，记账公司ID获取人员所在成本中心，多个默认返回第一个
	 ************************************************************/
	@Override
    public EopBasedataCostCenter findCostCenterByUserId(String fdCompanyId, String fdUserId) throws Exception {
		EopBasedataCostCenter center = null;
		if (StringUtil.isNull(fdCompanyId) || StringUtil.isNull(fdUserId)) {
			return center;
		}
		SysOrgPerson user=(SysOrgPerson) this.findByPrimaryKey(fdUserId, SysOrgPerson.class, true);
		String hierarchyId = user.getFdHierarchyId(); //人员对应层级
		hierarchyId = hierarchyId.substring(1, hierarchyId.length() - 1).replaceAll("\\s*[x]\\s*", ";");
		List<String> idList=Arrays.asList(hierarchyId.split(";"));
		HQLInfo hql = new HQLInfo();
		StringBuffer whereBuffer = new StringBuffer();
		hql.setJoinBlock(" left join eopBasedataCostCenter.fdCompanyList company left join fetch eopBasedataCostCenter.fdEkpOrg org");
		whereBuffer.append(" (company.fdId=:fdCompanyId or company is null) ");
		whereBuffer.append(" and "+HQLUtil.buildLogicIN("org.fdId", idList));
		whereBuffer.append(" and eopBasedataCostCenter.fdIsGroup=:fdIsGroup ");
		whereBuffer.append(" and eopBasedataCostCenter.fdIsAvailable=:fdIsAvailable ");
		hql.setWhereBlock(whereBuffer.toString());
		hql.setParameter("fdCompanyId", fdCompanyId);
		hql.setParameter("fdIsGroup", EopBasedataConstant.FSSC_BASE_COST_CENTER_TYPE_CENTER);
		hql.setParameter("fdIsAvailable", Boolean.TRUE);
		List<EopBasedataCostCenter> resList = this.findList(hql);
		if (!resList.isEmpty()) {
			resList = EopBasedataFsscUtil.sortByCompany(resList);
			center = resList.get(0);
		}
		return center;
	}

	/**
	 * 获取成本中心、成本中心组（根据人员ID是第一负责人、第二负责人、业务财务经理、预算管理员）
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCenterByUserId(String fdUserId) throws Exception{
		List<String> list = new ArrayList<>();
		if (StringUtil.isNull(fdUserId)) {
			return list;
		}
		List<String> idList=new ArrayList<>();
		idList.add(fdUserId);
		HQLInfo hql = new HQLInfo();
		StringBuffer whereBuffer = new StringBuffer();
		hql.setJoinBlock(" left join eopBasedataCostCenter.fdFirstCharger fst left join  eopBasedataCostCenter.fdSecondCharger snd  left join  eopBasedataCostCenter.fdManager manager left join  eopBasedataCostCenter.fdBudgetManager budget");
		whereBuffer.append(" ("+HQLUtil.buildLogicIN("fst.fdId", idList));
		whereBuffer.append(" or "+HQLUtil.buildLogicIN("snd.fdId", idList));
		whereBuffer.append(" or "+HQLUtil.buildLogicIN("manager.fdId", idList));
		whereBuffer.append(" or "+HQLUtil.buildLogicIN("budget.fdId", idList) +")");
		whereBuffer.append(" and eopBasedataCostCenter.fdIsAvailable=:fdIsAvailable ");
		hql.setWhereBlock(whereBuffer.toString());
		hql.setParameter("fdIsAvailable", Boolean.TRUE);
		List<EopBasedataCostCenter> parentList = this.findList(hql);
		if (!ArrayUtil.isEmpty(parentList)) {
			for (EopBasedataCostCenter center : parentList) {
				//查找成本中心组下成本中心
				if(EopBasedataConstant.FSSC_BASE_COST_CENTER_TYPE_GROUP.equals(center.getFdIsGroup())) {
					hql.setJoinBlock(hql.getJoinBlock()+" left join eopBasedataCostCenter.hbmParent parent ");
					whereBuffer.append(" and parent.fdId=:fdParentId");
					whereBuffer.append(" and eopBasedataCostCenter.fdIsAvailable=:fdIsAvailable ");
					hql.setWhereBlock(whereBuffer.toString());
					hql.setParameter("fdParentId", center.getFdId());
					hql.setParameter("fdIsAvailable", Boolean.TRUE);
					List<EopBasedataCostCenter> resList = this.findList(hql);
					for (EopBasedataCostCenter costCenter : resList) {
						list.add(costCenter.getFdId());
					}
				}
				list.add(center.getFdId());
			}
		}
		return list;
	}
}
