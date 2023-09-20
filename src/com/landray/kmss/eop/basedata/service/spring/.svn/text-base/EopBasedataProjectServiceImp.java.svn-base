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
import com.landray.kmss.eop.basedata.forms.EopBasedataProjectForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataProjectService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.hibernate.spi.HibernateWrapper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class EopBasedataProjectServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataProjectService {
	private IEopBasedataCompanyService eopBasedataCompanyService;
	
    public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}
	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataProject) {
            EopBasedataProject eopBasedataProject = (EopBasedataProject) model;
            eopBasedataProject.setDocAlterTime(new Date());
            eopBasedataProject.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }
    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataProjectForm mainForm = (EopBasedataProjectForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataProject eopBasedataProject = new EopBasedataProject();
		String parentId = requestContext.getParameter("parentId");
		if (StringUtil.isNotNull(parentId)) {
			EopBasedataProject parent = (EopBasedataProject) this.findByPrimaryKey(parentId, null, true);
			if (parent != null) {
				eopBasedataProject.setFdParent(parent);
			}
		}
        eopBasedataProject.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataProject.setDocCreateTime(new Date());
        eopBasedataProject.setDocAlterTime(new Date());
        eopBasedataProject.setDocCreator(UserUtil.getUser());
        eopBasedataProject.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataProject, requestContext);
        return eopBasedataProject;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataProject eopBasedataProject = (EopBasedataProject) model;
    }

    @Override
    public List<EopBasedataProject> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataProject.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }
	@SuppressWarnings("unchecked")
	@Override
	public void saveCopy(String ids, String modelName, String fdCompanyIds) throws Exception {
		List<String> comIds = Arrays.asList(fdCompanyIds.split(";"));
		List<EopBasedataProject> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有父级项目
		List<String> allIds = new ArrayList<String>();
		for(EopBasedataProject account:selectedAccountList){
			String[] hids = account.getFdHierarchyId().split("x");
			for(String hid:hids){
				if(StringUtil.isNotNull(hid)){
					allIds.add(hid);
				}
			}
		}
		String hql = "from "+EopBasedataProject.class.getName()+" where fdId in(:ids)";
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("ids", allIds);
		selectedAccountList = query.list();
		//保存原有层级关系 ，用于后续维护新增项目的层级
		Map<String,EopBasedataProject> oriExtendMap = new HashMap<String,EopBasedataProject>();
		List<String> codeList = new ArrayList<String>();
		for(EopBasedataProject project:selectedAccountList){
			codeList.add(project.getFdCode());
			oriExtendMap.put(project.getFdCode(), project);
		}
		//查询已存在的项目
		hql = "from "+EopBasedataProject.class.getName()+" where fdCode in(:codes) and fdCompany.fdId in(:fdCompanyIds)";
		query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("codes", codeList);
		query.setParameterList("fdCompanyIds",comIds);
		List<EopBasedataProject> existCenters = query.list();
		Map<String,EopBasedataProject> centerMap = new HashMap<String,EopBasedataProject>();
		Map<String,EopBasedataProject> extendMap = new HashMap<String,EopBasedataProject>();
		for(EopBasedataProject center:existCenters){
			centerMap.put(center.getFdCode(), center);
			extendMap.put(center.getFdCode(), center);
		}
		List<EopBasedataCompany> comps = eopBasedataCompanyService.findByPrimaryKeys(fdCompanyIds.split(";"));
		EopBasedataProject newProject = null;
		String key = null;
		SysOrgPerson user = UserUtil.getUser();
		Date now = new Date();
		List<EopBasedataProject> addList = new ArrayList<EopBasedataProject>();
		for(EopBasedataProject project:selectedAccountList){
			for(EopBasedataCompany comp:comps){
				key = project.getFdCode()+comp.getFdCode();
				//如果当前公司已存在对应项目，直接跳过
				if(centerMap.containsKey(key)){
					continue;
				}
				newProject = new EopBasedataProject();
				BeanUtils.copyProperties(newProject,project);
				BeanUtils.setProperty(newProject, "docCreator", user);
				BeanUtils.setProperty(newProject, "docCreateTime", now);
				BeanUtils.setProperty(newProject, "docAlteror", user);
				BeanUtils.setProperty(newProject, "docAlterTime", now);
				BeanUtils.setProperty(newProject, "fdCompany", comp);
				newProject.setFdId(null);
				addList.add(newProject);
				extendMap.put(newProject.getFdCode(), project);
			}
		}
		if(!ArrayUtil.isEmpty(addList)){
			//维护上下级关系
			EopBasedataProject parent = null;
			for(EopBasedataProject project:addList){
				//使用编号找到原成本中心的上级
				parent = (EopBasedataProject) oriExtendMap.get(project.getFdCode()).getFdParent();
				if(parent!=null){
					project.setFdParent(extendMap.get(parent.getFdCode()));
				}
			}
			getBaseDao().saveOrUpdateAll(addList);
		}
	}
	@Override
	public void saveEnable(String ids, String modelName) throws Exception {
		List<EopBasedataProject> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有父级科目 
		List<String> allIds = new ArrayList<String>();
		for(EopBasedataProject account:selectedAccountList){
			String[] hids = account.getFdHierarchyId().split("x");
			for(String hid:hids){
				if(StringUtil.isNotNull(hid)){
					allIds.add(hid);
				}
			}
		}
		selectedAccountList = findByPrimaryKeys(allIds.toArray(new String[allIds.size()]));
		for(EopBasedataProject account:selectedAccountList){
			account.setFdIsAvailable(true);
		}
		//更新档案的启用状态
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}
	@Override
	public void saveDisable(String ids, String modelName) throws Exception {
		List<EopBasedataProject> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有子级科目
		StringBuffer where = new StringBuffer();
		where.append("1=2 ");
		for(EopBasedataProject account:selectedAccountList){
			where.append("or eopBasedataProject.fdHierarchyId like '").append(account.getFdHierarchyId()).append("%' ");
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(where.toString());
		selectedAccountList = findList(hqlInfo);
		for(EopBasedataProject account:selectedAccountList){
			account.setFdIsAvailable(false);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EopBasedataProject getEopBasedataProjectByCode(String fdCompanyId, String fdCode)
			throws Exception {
		if(StringUtil.isNull(fdCompanyId) || StringUtil.isNull(fdCode)){
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock("left join eopBasedataProject.fdCompanyList comp");
		hqlInfo.setWhereBlock(" (comp.fdId = :fdCompanyId or comp.fdId is null) and UPPER(eopBasedataProject.fdCode) = :fdCode");
		hqlInfo.setParameter("fdCompanyId", fdCompanyId);
		hqlInfo.setParameter("fdCode", fdCode.toUpperCase());
		List<EopBasedataProject> list = this.findList(hqlInfo);
		return ArrayUtil.isEmpty(list)?null:list.get(0);
	}
}
