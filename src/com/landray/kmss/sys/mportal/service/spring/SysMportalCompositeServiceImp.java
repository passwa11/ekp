package com.landray.kmss.sys.mportal.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.mportal.forms.SysMportalCompositeForm;
import com.landray.kmss.sys.mportal.forms.SysMportalCpageRelationForm;
import com.landray.kmss.sys.mportal.model.SysMportalComposite;
import com.landray.kmss.sys.mportal.model.SysMportalCpage;
import com.landray.kmss.sys.mportal.model.SysMportalCpageRelation;
import com.landray.kmss.sys.mportal.model.SysMportalLogoInfo;
import com.landray.kmss.sys.mportal.service.ISysMportalCompositeService;
import com.landray.kmss.sys.mportal.service.ISysMportalCpageRelationService;
import com.landray.kmss.sys.mportal.service.ISysMportalCpageService;
import com.landray.kmss.sys.mportal.util.SysMportalConstant;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.sso.client.oracle.StringUtil;

import net.sf.json.JSONObject;

public class SysMportalCompositeServiceImp extends ExtendDataServiceImp implements ISysMportalCompositeService {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SysMportalCompositeServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysMportalComposite) {
            SysMportalComposite sysMportalComposite = (SysMportalComposite) model;
            sysMportalComposite.setDocAlterTime(new Date());
            sysMportalComposite.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public List<SysMportalComposite> findByFdPages(SysMportalCpage fdPages) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysMportalComposite.fdPages.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdPages.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    
    private ISysMportalCpageService sysMportalCpageService;
    public ISysMportalCpageService getSysMportalCpageService() {
        if (sysMportalCpageService == null) {
        	sysMportalCpageService = (ISysMportalCpageService) SpringBeanUtil.getBean("sysMportalCpageService");
        }
        return sysMportalCpageService;
    }
    
    private ISysMportalCpageRelationService sysMportalCpageRelationService;
    public ISysMportalCpageRelationService getSysMportalCpageRelationService() {
        if (sysMportalCpageRelationService == null) {
        	sysMportalCpageRelationService = (ISysMportalCpageRelationService) SpringBeanUtil.getBean("sysMportalCpageRelationService");
        }
        return sysMportalCpageRelationService;
    }
    
    public void updateInvalidated(String id, RequestContext requestContext) {
		try {
			SysMportalComposite sysMportalComposite = (SysMportalComposite) this.findByPrimaryKey(id);
			if (sysMportalComposite != null) {
				if (sysMportalComposite.getFdEnabled() == null
						|| sysMportalComposite.getFdEnabled() == true) {
					sysMportalComposite.setFdEnabled(new Boolean(false));
					update(sysMportalComposite);
					flushHibernateSession();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
    public void updateInvalidatedAll(String[] ids, RequestContext requestContext) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			this.updateInvalidated(ids[i], requestContext);
		}
	}

	public void updateValidated(String id, RequestContext requestContext) {
		try {
			SysMportalComposite sysMportalcomposite = (SysMportalComposite) this.findByPrimaryKey(id);
			if (sysMportalcomposite != null) {
				if (sysMportalcomposite.getFdEnabled() == null
						|| sysMportalcomposite.getFdEnabled() == false) {
					sysMportalcomposite.setFdEnabled(new Boolean(true));
					update(sysMportalcomposite);
					flushHibernateSession();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
    public void updateValidatedAll(String[] ids, RequestContext requestContext) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			this.updateValidated(ids[i], requestContext);
		}
	}
    
	
	@Override
    public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		IBaseModel model = (IBaseModel) requestContext.getAttribute("EXTENDDATASERVICE_MAIN_MODEL_CACHE");
		if (model == null){
			model = initModelSetting(requestContext);
		}
		UserOperHelper.logAdd(getModelName());
		model = convertFormToModel(form, model, requestContext);
		String fdId = add(model);
		SysMportalComposite sysMportalComposite = (SysMportalComposite) model;
		
		if(StringUtils.isEmpty(sysMportalComposite.getDocStatus())) {
			sysMportalComposite.setDocStatus(SysDocConstant.DOC_STATUS_PUBLISH);
		}
		
		SysMportalCompositeForm mportalCompositeForm = (SysMportalCompositeForm)form;
    	List<SysMportalCpageRelationForm> pageForms = mportalCompositeForm.getPages();
    	List<SysMportalCpageRelationForm> childForms = new ArrayList<SysMportalCpageRelationForm>();
    	Map<String,SysMportalCpageRelation>  formMap = new HashMap<>();
    	
    	//添加关联页面和页签
    	for(SysMportalCpageRelationForm pageForm : pageForms) { 
    		if(StringUtils.isNoneBlank(pageForm.getFdParentId())) {
    			childForms.add(pageForm);
    		}else {
    			SysMportalCpageRelation page = new SysMportalCpageRelation();
        		page.setFdId(pageForm.getFdId());
        		page.setFdIcon(pageForm.getFdIcon());
				page.setFdImg(pageForm.getFdImg());
				page.setFdType(pageForm.getFdType());
        		page.setFdName(pageForm.getFdName());
        		page.setFdOrder(Integer.valueOf(pageForm.getFdOrder()));
        		if(StringUtils.isNoneBlank(pageForm.getSysMportalCpageId())) {
        			SysMportalCpage sysMportalCpage = (SysMportalCpage) getSysMportalCpageService().findByPrimaryKey(pageForm.getSysMportalCpageId());
            		page.setSysMportalCpage(sysMportalCpage);
        		}
        		page.setFdMainCompositeId(sysMportalComposite.getFdId());
        		page.setSysMportalComposite(sysMportalComposite);
        		getSysMportalCpageRelationService().update(page);
        		formMap.put(page.getFdId(), page);
    		}
    	}
    	//添加页签子页面
    	for(SysMportalCpageRelationForm pageForm : childForms) {
    		SysMportalCpageRelation fdParent = formMap.get(pageForm.getFdParentId());
    		SysMportalCpageRelation page = new SysMportalCpageRelation();
    		page.setFdId(pageForm.getFdId());
    		page.setFdIcon(pageForm.getFdIcon());
			page.setFdImg(pageForm.getFdImg());
			page.setFdType(pageForm.getFdType());
    		page.setFdName(pageForm.getFdName());
    		page.setFdOrder(Integer.valueOf(pageForm.getFdOrder()));
    		page.setFdMainCompositeId(sysMportalComposite.getFdId());  		
    		SysMportalCpage sysMportalCpage = (SysMportalCpage) getSysMportalCpageService().findByPrimaryKey(pageForm.getSysMportalCpageId());
    		page.setSysMportalCpage(sysMportalCpage);
    		page.setFdParent(fdParent);
    		getSysMportalCpageRelationService().update(page);    		
    	}
		return fdId;
	}
    
    
    @Override
    public void update(IExtendForm form, RequestContext requestContext) throws Exception {

    	IBaseModel model = convertFormToModel(form, null, requestContext);
    	if (model == null){
    		throw new NoRecordException();
    	}
    	SysMportalComposite sysMportalComposite = (SysMportalComposite) model;
    	if(StringUtils.isEmpty(sysMportalComposite.getDocStatus())) {
			sysMportalComposite.setDocStatus(SysDocConstant.DOC_STATUS_PUBLISH);
		}
    	//删除旧数据
    	HQLInfo hqlInfo = new HQLInfo();
 		hqlInfo.setWhereBlock("sysMportalComposite.fdId=:fdId");
 		hqlInfo.setParameter("fdId", sysMportalComposite.getFdId());
 		List<SysMportalCpageRelation> oldPages = getSysMportalCpageRelationService().findList(hqlInfo);
 		Map<String,SysMportalCpageRelation> oldPagesMap = new HashMap<>();
 		Map<String,SysMportalCpageRelation> oldChildrenPagesMap = new HashMap<>();
 		for(SysMportalCpageRelation page : oldPages) {	
 			oldPagesMap.put(page.getFdId(), page);
 			//页签类型
 			if(SysMportalConstant.MPORTAL_PAGE_TYPE_2.equals(page.getFdType())) {
 				hqlInfo = new HQLInfo();
		 		hqlInfo.setWhereBlock("fdParent.fdId=:fdId");
		 		hqlInfo.setParameter("fdId", page.getFdId());
		 		List<SysMportalCpageRelation> childs = getSysMportalCpageRelationService().findList(hqlInfo);
		 		for(SysMportalCpageRelation child : childs) {
		 			oldChildrenPagesMap.put(child.getFdId(), child);
		 		}
 			}
 		}
 		
    	SysMportalCompositeForm mportalCompositeForm = (SysMportalCompositeForm)form;
    	List<SysMportalCpageRelationForm> pageForms = mportalCompositeForm.getPages();
    	List<SysMportalCpageRelationForm> childForms = new ArrayList<SysMportalCpageRelationForm>();
    	Map<String,SysMportalCpageRelation>  formMap = new HashMap<>();
    	//添加关联页面和页签
    	for(SysMportalCpageRelationForm pageForm : pageForms) { 
    		if(StringUtils.isNoneBlank(pageForm.getFdParentId())) {
    			childForms.add(pageForm);
    		}else {
    			SysMportalCpageRelation page = null;
    			if(oldPagesMap.containsKey(pageForm.getFdId())){
    				page = oldPagesMap.get(pageForm.getFdId());
    				oldPagesMap.remove(pageForm.getFdId());   				
    			}else {
    				page = new SysMportalCpageRelation();
    				if(StringUtils.isNoneBlank(pageForm.getSysMportalCpageId())) {
            			SysMportalCpage sysMportalCpage = (SysMportalCpage) getSysMportalCpageService().findByPrimaryKey(pageForm.getSysMportalCpageId());
                		page.setSysMportalCpage(sysMportalCpage);
            		}   				
            		page.setSysMportalComposite(sysMportalComposite);
    				page.setFdId(pageForm.getFdId());
    				page.setFdType(pageForm.getFdType());
    			}  
    			page.setFdMainCompositeId(sysMportalComposite.getFdId());
        		page.setFdIcon(pageForm.getFdIcon());
				page.setFdImg(pageForm.getFdImg()); //素材库图片url
				page.setFdOrder(Integer.valueOf(pageForm.getFdOrder()));
        		page.setFdName(pageForm.getFdName());         
        		getSysMportalCpageRelationService().update(page);
        		formMap.put(page.getFdId(), page);
    		}
    	}
    	//添加子页面
    	for(SysMportalCpageRelationForm pageForm : childForms) {
    		SysMportalCpageRelation page = null;
    		SysMportalCpageRelation fdParent = formMap.get(pageForm.getFdParentId());
    		if(oldChildrenPagesMap.containsKey(pageForm.getFdId())){
    			page = oldChildrenPagesMap.get(pageForm.getFdId());
    			oldChildrenPagesMap.remove(pageForm.getFdId());  			
    		}else{
    			page = new SysMportalCpageRelation();
    			page.setFdParent(fdParent);
    			SysMportalCpage sysMportalCpage = (SysMportalCpage) getSysMportalCpageService().findByPrimaryKey(pageForm.getSysMportalCpageId());
        		page.setSysMportalCpage(sysMportalCpage);
        		page.setFdId(pageForm.getFdId());
        		page.setFdType(pageForm.getFdType());
    		}  	
    		page.setFdMainCompositeId(sysMportalComposite.getFdId());
    		page.setFdIcon(pageForm.getFdIcon());
			page.setFdImg(pageForm.getFdImg());
			page.setFdOrder(Integer.valueOf(pageForm.getFdOrder()));
    		page.setFdName(pageForm.getFdName());    		
    		getSysMportalCpageRelationService().update(page);    		
    	}
    	
    	//移除子级页面
    	for(SysMportalCpageRelation page : oldChildrenPagesMap.values()) {
    		getSysMportalCpageRelationService().delete(page);
    	}
    	
    	//移除顶级页签、页面
    	for(SysMportalCpageRelation page : oldPagesMap.values()) {
    		getSysMportalCpageRelationService().delete(page);
    	}
    	
    	super.update(sysMportalComposite);
	}
    
    @Override
    public void delete(IBaseModel modelObj) throws Exception {
    	//删除关联页面数据
    	HQLInfo hqlInfo = new HQLInfo();
 		hqlInfo.setWhereBlock("sysMportalComposite.fdId=:fdId");
 		hqlInfo.setParameter("fdId", modelObj.getFdId());
 		List<SysMportalCpageRelation> oldPages = getSysMportalCpageRelationService().findList(hqlInfo);
 		for(SysMportalCpageRelation page : oldPages) {	
 			getSysMportalCpageRelationService().delete(page);
 		}
		super.delete(modelObj);
	}
    
   
    /**
     * 获取关联页面数据
     */
    @Override
    public String getPagesJsonById(String fdId) {
    	try {   		
     		JSONArray jsonArray = (JSONArray)JSON.toJSON(getListPagesById(fdId));    		
     		return jsonArray.toJSONString();
    	}catch(Exception e) {
    		logger.error("获取页面信息失败",e);
    	}
		return null;	
    }
    
    /**
     * 获取关联页面列表
     * @param fdId
     * @return
     */
    @Override
    public List<Map<String,Object>> getListPagesById(String fdId) {
    	return getListPagesById(fdId, false);
    }
    
    /**
     * 获取关联页面列表
     * @param fdId
     * @return
     */
    @Override
    public List<Map<String,Object>> getListPagesById(String fdId, Boolean enabled) {
    	List<Map<String,Object>> results = new ArrayList<>();
    	Map<String,Boolean> pageEditAuthMap = new HashMap<String,Boolean>();
    	try {
    		HQLInfo hqlInfo = new HQLInfo();
    		String hql = "sysMportalComposite.fdId=:fdId";
// 查询不成功
//     		if(enabled) {
//     			hql += " and ((sysMportalCpage is NULL and fdType=:fdType) or (sysMportalCpage is not NULL and sysMportalCpage.fdEnabled=:fdEnabled))";//)";
//     			hqlInfo.setParameter("fdEnabled", true);
//     			hqlInfo.setParameter("fdType", SysMportalConstant.MPORTAL_PAGE_TYPE_2);
//     		}
     		hqlInfo.setWhereBlock(hql);
     		hqlInfo.setParameter("fdId", fdId);
     		hqlInfo.setOrderBy("fdOrder");
     		List<SysMportalCpageRelation> pages = getSysMportalCpageRelationService().findList(hqlInfo);
     		for(SysMportalCpageRelation page : pages) {	
     			if(enabled && page.getSysMportalCpage() != null && !page.getSysMportalCpage().getFdEnabled()) {
     				continue;
     			}
     			Map<String,Object> resultMap = new HashMap<>();
     			resultMap.put("fdName", page.getFdName());
 				resultMap.put("fdId", page.getFdId());
 				resultMap.put("fdType", page.getFdType());
 				resultMap.put("fdIcon", page.getFdIcon());
				resultMap.put("fdImg", page.getFdImg());
     			//页签类型
     			if(SysMportalConstant.MPORTAL_PAGE_TYPE_2.equals(page.getFdType())) {
     				hqlInfo = new HQLInfo();
     				String childHql = "fdParent.fdId=:fdId";
     				if(enabled) {
     					childHql += " and (sysMportalCpage.fdEnabled=:fdEnabled)";
     	     			hqlInfo.setParameter("fdEnabled", true);
     	     		}
    		 		hqlInfo.setWhereBlock(childHql);
    		 		hqlInfo.setParameter("fdId", page.getFdId());
    		 		hqlInfo.setOrderBy("fdOrder");
    		 		List<SysMportalCpageRelation> childs = getSysMportalCpageRelationService().findList(hqlInfo);   		 		
    		 		List<Map<String,Object>> childList = new ArrayList<>();
    		 		for(SysMportalCpageRelation child : childs) {
    		 			Map<String,Object> childResult = new HashMap<>();   		 			
    		 			Boolean pageCanEdit = false;
    		 			String pageId = child.getSysMportalCpage().getFdId();
    		 			String requestUrl = "/sys/mportal/sys_mportal_cpage/sysMportalCpage.do?method=edit&fdId=" + pageId;
    		 			if(pageEditAuthMap.containsKey(pageId)) {
    		 				pageCanEdit = pageEditAuthMap.get(pageId);
    		 			}else {
    		 				pageCanEdit = UserUtil.checkAuthentication(requestUrl, "GET");
    		 				pageEditAuthMap.put(pageId, pageCanEdit);
    		 			}
    		 			childResult.put("pageCanEdit",pageCanEdit);
    		 			childResult.put("fdName", child.getFdName());
    		 			childResult.put("fdId", child.getFdId());
    		 			childResult.put("fdIcon", child.getFdIcon());
						childResult.put("fdImg", child.getFdImg());
    		 			childResult.put("fdType", child.getFdType());   		 			
    		 			childResult.put("pageId", child.getSysMportalCpage().getFdId());
    		 			childResult.put("pageUrl", child.getSysMportalCpage().getFdUrl());
    		 			childResult.put("pageType", child.getSysMportalCpage().getFdType()); 
    		 			childResult.put("pageEnabled", child.getSysMportalCpage().getFdEnabled());
    		 			childResult.put("pageUrlOpenType", child.getSysMportalCpage().getFdUrlOpenType());
         				if("1".equals(child.getSysMportalCpage().getFdType())) {
         					childResult.put("pageTypeDesc", ResourceUtil.getString("sysMportalCpage.fdType.page", "sys-mportal")); 
         				}else if("2".equals(child.getSysMportalCpage().getFdType())) {
         					childResult.put("pageTypeDesc", ResourceUtil.getString("sysMportalCpage.fdType.url", "sys-mportal")); 
         				}
    		 			childList.add(childResult);
    		 		}
    		 		resultMap.put("childs", childList);
     			}else {
     				Boolean pageCanEdit = false;
		 			String pageId = page.getSysMportalCpage().getFdId();
		 			String requestUrl = "/sys/mportal/sys_mportal_cpage/sysMportalCpage.do?method=edit&fdId=" + pageId;
		 			if(pageEditAuthMap.containsKey(pageId)) {
		 				pageCanEdit = pageEditAuthMap.get(pageId);
		 			}else {
		 				pageCanEdit = UserUtil.checkAuthentication(requestUrl, "GET");
		 				pageEditAuthMap.put(pageId, pageCanEdit);
		 			}
		 			resultMap.put("pageCanEdit",pageCanEdit);
     				resultMap.put("pageId", pageId);
     				resultMap.put("pageEnabled", page.getSysMportalCpage().getFdEnabled());
     				resultMap.put("pageType", page.getSysMportalCpage().getFdType()); 
     				resultMap.put("pageUrl", page.getSysMportalCpage().getFdUrl());
     				resultMap.put("pageUrlOpenType", page.getSysMportalCpage().getFdUrlOpenType());
     				if("1".equals(page.getSysMportalCpage().getFdType())) {
     					resultMap.put("pageTypeDesc", ResourceUtil.getString("sysMportalCpage.fdType.page", "sys-mportal")); 
     				}else if("2".equals(page.getSysMportalCpage().getFdType())) {
     					resultMap.put("pageTypeDesc", ResourceUtil.getString("sysMportalCpage.fdType.url", "sys-mportal")); 
     				}
     			}
     			results.add(resultMap);
     		}    		
    	}catch(Exception e) {
    		logger.error("获取页面信息失败",e);
    	}
    	return results;
    }
    
    /**
     * 根据页面ID获取门户列表
     * @param cpageId
     * @return
     * @throws Exception
     */
    @Override
    public List<SysMportalComposite> getListCompositeByPageId(String cpageId) throws Exception {
    	HQLInfo hqlInfo = new HQLInfo();
 		hqlInfo.setWhereBlock("fdId in (select r.fdMainCompositeId from SysMportalCpageRelation r where r.sysMportalCpage.fdId=:cpageId)");
 		hqlInfo.setParameter("cpageId", cpageId);
 		hqlInfo.setOrderBy("fdOrder");
 		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
 		return this.findList(hqlInfo);	
    }
    
    /**
     * 根据页面ID获取门户名称字符串
     * @param cpageId
     * @return
     * @throws Exception
     */
    @Override
    public String getStringCompositeByPageId(String cpageId) {
    	try {
    		List<SysMportalComposite> composites = getListCompositeByPageId(cpageId);
        	StringBuilder builder = new StringBuilder();
        	for(SysMportalComposite composite : composites) {
        		builder.append("[" + composite.getFdName() + "] ");
        	}
    		return builder.toString();  
    	}catch(Exception e) {
    		logger.error("根据页面ID获取门户名称字符串失败",e);
    	}
    	return null;	
    }
    
    @Override
    @SuppressWarnings("unchecked")
	public void initCompositeMessage(HttpServletRequest request, JSONObject jsonObject, Boolean isPreview) throws Exception {
		List<SysMportalComposite> rtnList = new ArrayList<SysMportalComposite>();
		//开启多语言情况下，当前用户的默认语言不为空时处理 #103354
		String langStr = ResourceUtil.getKmssConfigString("kmss.lang.support");
		if(StringUtil.isNotNull(langStr)) {
			if(StringUtil.isNotNull(UserUtil.getUser().getFdDefaultLang())) {
				rtnList = findLangPortal(UserUtil.getUser().getFdDefaultLang(), isPreview);
			}
		}
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = " 1=1 ";
		if(!isPreview) {
			whereBlock += " and sysMportalComposite.fdEnabled = :fdEnabled and  sysMportalComposite.docStatus = :docStatus";
			hqlInfo.setParameter("fdEnabled", true);
			hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		}
		hqlInfo.setOrderBy("sysMportalComposite.fdOrder asc, sysMportalComposite.docCreateTime desc");
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 移动门户内外隔离
			hqlInfo.setJoinBlock(" left join sysMportalComposite.authAllReaders readers");
			if (SysOrgEcoUtil.isExternal()) {
				whereBlock += " and readers.fdId in (:orgIds)";
			} else {
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				whereBlock += " and (readers.fdId is null or readers.fdId in (:orgIds))";
			}
			hqlInfo.setParameter("orgIds", orgIds);
		}
		hqlInfo.setWhereBlock(whereBlock);
		List<SysMportalComposite> list = this.findList(hqlInfo);
		
		//去重
		list.removeAll(rtnList);
		rtnList.addAll(list);
		
		if (!rtnList.isEmpty()) {
			JSONArray jsonArray = new JSONArray();
			for (SysMportalComposite sysMportalComposite : rtnList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("compositeId", sysMportalComposite.getFdId());
				jsonObj.put("compositeName", sysMportalComposite.getFdName());
				jsonObj.put("compositeHeadType", sysMportalComposite.getFdHeadType());
				jsonObj.put("compositelocale", sysMportalComposite.getFdLang());
				jsonObj.put("compositeHeadChangeEnabled", sysMportalComposite.getFdHeadChangeEnabled());				
				jsonObj.put("compositeNavLayout", sysMportalComposite.getFdNavLayout());
				if(StringUtils.isNotBlank(sysMportalComposite.getFdLogo())) {
					jsonObj.put("compositeLogo", sysMportalComposite.getFdLogo());
				}else {
					Map<String, String> config = ((ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService")).findByKey(SysMportalLogoInfo.class.getName());
					String logoUrl = config.get("logoUrl");
					if (StringUtil.isNotNull(logoUrl)) {
						jsonObj.put("compositeLogo", logoUrl);
					} else {
						jsonObj.put("compositeLogo", "/sys/mportal/resource/logo.png");
					}
				}
				jsonObj.put("pages", getListPagesById(sysMportalComposite.getFdId(), true));				
				jsonArray.add(jsonObj);
			}
			jsonObject.put("compositePortalList", jsonArray);
		}
	}
    
    /**
	 * 根据用户语言查找门户
	 * @param fdDefaultLang
	 * @return
	 * @throws Exception 
	 */
	private List<SysMportalComposite> findLangPortal(String fdDefaultLang,  Boolean isPreview) throws Exception {
		
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "sysMportalComposite.fdLang = :fdLang ";
		if(!isPreview) {
			whereBlock += " and sysMportalComposite.fdEnabled = :fdEnabled and  sysMportalComposite.docStatus = :docStatus";
			hqlInfo.setParameter("fdEnabled", true);
			hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		}
		hqlInfo.setParameter("fdLang", fdDefaultLang);
		hqlInfo.setOrderBy("sysMportalComposite.fdOrder asc, sysMportalComposite.docCreateTime desc");
		// 移动门户内外隔离
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			hqlInfo.setJoinBlock(" left join sysMportalComposite.authAllReaders readers");
			if (SysOrgEcoUtil.isExternal()) {
				whereBlock += " and readers.fdId in (:orgIds)";
			} else {
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				whereBlock += " and (readers.fdId is null or readers.fdId in (:orgIds))";
			}
			hqlInfo.setParameter("orgIds", orgIds);
		}
		
		hqlInfo.setWhereBlock(whereBlock);

		return this.findPage(hqlInfo).getList();
	}

	@Override
	public JSONArray getMportalInfoByLogo(List<String> logoList) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String selectBlock = " sysMportalComposite.fdLogo,sysMportalComposite.fdId,sysMportalComposite.fdName ";
		String whereBlock = " sysMportalComposite.fdLogo IN (:logoList) ORDER BY fdLogo";
		hqlInfo.setSelectBlock(selectBlock);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("logoList", logoList);
		List<Object[]> list = this.findList(hqlInfo);
		JSONArray array = new JSONArray();
		for (Object[] obj : list) {
			JSONObject json = new JSONObject();
			json.put("fdLogo", obj[0]);
			json.put("fdId", obj[1]);
			json.put("fdName", obj[2]);
			array.add(json);
		}
		return array;
	}
    
}
