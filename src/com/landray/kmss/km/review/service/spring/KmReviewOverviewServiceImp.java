package com.landray.kmss.km.review.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.model.KmReviewOverview;
import com.landray.kmss.km.review.model.KmReviewTemPre;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.km.review.service.IKmReviewOverviewService;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.authorization.service.ISysAuthAreaService;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryConfigService;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author zhangwentian 2010-9-13
 */
public class KmReviewOverviewServiceImp extends BaseServiceImp implements IKmReviewOverviewService {
	private ISysCategoryMainService categoryMainService;

	public void setCategoryMainService(
			ISysCategoryMainService categoryMainService) {
		this.categoryMainService = categoryMainService;
	}

	private ISysCategoryConfigService categoryConfigService;

	public void setCategoryConfigService(
			ISysCategoryConfigService categoryConfigService) {
		this.categoryConfigService = categoryConfigService;
	}

	private IKmReviewMainService kmReviewMainService;

	public void setKmReviewMainService(IKmReviewMainService kmReviewMainService) {
		this.kmReviewMainService = kmReviewMainService;
	}

	private IKmReviewTemplateService kmReviewTemplateService;

	public void setKmReviewTemplateService(
			IKmReviewTemplateService kmReviewTemplateService) {
		this.kmReviewTemplateService = kmReviewTemplateService;
	}
	private ISysAuthAreaService sysAuthAreaService;
	public void setSysAuthAreaService(ISysAuthAreaService sysAuthAreaService) {
		this.sysAuthAreaService = sysAuthAreaService;
	}

	@Override
    public List<KmReviewMain> getLatestDoc() throws Exception {
		List<KmReviewMain> lateseDocList = new ArrayList<KmReviewMain>();
		int rowSize = 10;
		String whereBlock = "kmReviewMain.docStatus='"
				+ SysDocConstant.DOC_STATUS_PUBLISH + "'";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("kmReviewMain.docPublishTime desc");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(rowSize);
		lateseDocList = kmReviewMainService.findPage(hqlInfo).getList();
		return lateseDocList;
	}

	@Override
    public List<KmReviewMain> getHotDoc() throws Exception {
		List<KmReviewMain> hotDocList = new ArrayList<KmReviewMain>();
		int rowSize = 9;
		String whereBlock = "kmReviewMain.docStatus='"
				+ SysDocConstant.DOC_STATUS_PUBLISH + "'";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("kmReviewMain.docReadCount desc");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(rowSize);
		hotDocList = kmReviewMainService.findPage(hqlInfo).getList();
		return hotDocList;
	}
	
	@Override
    public String getReviewPre() throws Exception {
		String fdPreContent = null;
		List<KmReviewOverview> kmReviewOverviewList = new ArrayList<KmReviewOverview>();
		HQLInfo hql = new HQLInfo();
		hql.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
				SysAuthConstant.AreaCheck.YES);
		kmReviewOverviewList = this.findList(hql);
		
		if(UserOperHelper.allowLogOper("preview", getModelName())){
			for(KmReviewOverview o :kmReviewOverviewList){
				UserOperContentHelper.putFind((IBaseModel)o);
			}
		}
		
		if (kmReviewOverviewList.size()>0) {
			if(ISysAuthConstant.IS_AREA_ENABLED){
				for(int i=0;i<kmReviewOverviewList.size();i++){
					if(kmReviewOverviewList.get(i).getAuthArea()!=null){
						if(kmReviewOverviewList.get(i).getAuthArea().getFdId().equals(UserUtil.getKMSSUser().getAuthAreaId())){
					      fdPreContent = kmReviewOverviewList.get(i).getFdPreContent();
						}
					}
				}
			}else{
				fdPreContent = kmReviewOverviewList.get(0).getFdPreContent();
			}
		}
		return fdPreContent;
	}
	
	/**
	 * 定时任务更新流程分类概览
	 */
	@Override
    public String updateReview() throws Exception {
		String areaId = UserUtil.getKMSSUser().getAuthAreaId();
		HQLInfo hql = new HQLInfo();
		if (StringUtil.isNotNull(areaId)) {
			hql.setCheckParam(SysAuthConstant.CheckType.AreaSpecified, areaId);
		}
		List<KmReviewOverview> kmReviewPreList = this.findList(hql);
		if (kmReviewPreList.size() > 0&&StringUtil.isNull(areaId)) {
			for (KmReviewOverview kmReviewPre : kmReviewPreList) {
				updateBuildTmp(kmReviewPre);
			}
		} else {
			return buildTmp();
		}
		return null;
	}
	
	

	/**
	 * 更新概览
	 * 
	 * @param pre
	 * @return
	 * @throws Exception
	 */
	private String updateBuildTmp(KmReviewOverview overview) throws Exception {
		
		String fdPreContent = outContent(overview.getAuthArea() != null ? overview
				.getAuthArea().getFdId() : null);
		if(UserOperHelper.allowLogOper("preview", getModelName())){
			UserOperContentHelper.putUpdate(overview).putSimple("fdPreContent",overview.getFdPreContent() , fdPreContent);
		}
		overview.setFdPreContent(fdPreContent);
		overview.setDocAlterTime(new Date());
		this.update(overview);
		return fdPreContent.toString();
	}

	/**
	 * 构建分类概览
	 * 
	 * @param fdCategoryId
	 * @return
	 * @throws Exception
	 */
	private String buildTmp() throws Exception {
		String areaId = UserUtil.getKMSSUser().getAuthAreaId();
		String fdPreContent = outContent(areaId);
		KmReviewOverview overview = new KmReviewOverview();
		overview.setFdPreContent(fdPreContent);
		overview.setDocAlterTime(new Date());
		// 设置场所
		if (overview instanceof ISysAuthAreaModel) {
			KMSSUser user = UserUtil.getKMSSUser();
			SysAuthArea authArea = (SysAuthArea) sysAuthAreaService
					.findByPrimaryKey(user.getAuthAreaId());
			overview.setAuthArea(authArea);
		}
		this.add(overview);
		if(UserOperHelper.allowLogOper("preview", getModelName())){
			UserOperContentHelper.putAdd(overview, "fdPreContent","authArea");
		}
		return fdPreContent.toString();
	}
	
	
	
	public String outContent(String authAreaId) throws Exception{
		List<KmReviewTemPre> kmReviewPreList = getContentList(authAreaId);
		JSONArray array1 = new JSONArray();
		for(int i=0;i<kmReviewPreList.size();i++){
			KmReviewTemPre p1 = kmReviewPreList.get(i);
			JSONObject obj1 = new JSONObject();
			obj1.put("text", p1.getTempName());
			obj1.put("count", p1.getDocAmount());
			obj1.put("id", p1.getCategoryId());
			obj1.put("nodeType", p1.getIsTemOrCate());
			JSONArray array2 = new JSONArray();
			for(int t=0;t<p1.getTempList().size();t++){
				KmReviewTemPre p2 = p1.getTempList().get(t);
				JSONObject obj2 = new JSONObject();
				obj2.put("text", p2.getTempName());
				obj2.put("count", p2.getDocAmount());
				obj2.put("id", p2.getCategoryId());
				obj2.put("nodeType", p2.getIsTemOrCate());
				JSONArray array3 = new JSONArray();
				for(int m=0;m<p2.getTempList().size();m++){
					KmReviewTemPre p3 = p2.getTempList().get(m);
					JSONObject obj3 = new JSONObject();
					obj3.put("text", p3.getTempName());
					obj3.put("count", p3.getDocAmount());
					obj3.put("id", p3.getCategoryId());
					obj3.put("nodeType", p3.getIsTemOrCate());
					array3.add(obj3);
				}
				obj2.put("children",array3);
				array2.add(obj2);
			}
			obj1.put("children",array2);
			array1.add(obj1);
		}
		return array1.toString();
	}
	
	

	@Override
    public List<KmReviewTemPre> getContentList(String authAreaId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock(
				"sysCategoryMain.hbmParent is null and sysCategoryMain.fdModelName='com.landray.kmss.km.review.model.KmReviewTemplate'");
		hql.setOrderBy("sysCategoryMain.fdOrder,sysCategoryMain.fdId");
		hql.setCheckParam(SysAuthConstant.CheckType.AreaSpecified,authAreaId);
		hql.setCheckParam(SysAuthConstant.CheckType.AreaIsolation,
				ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		List<SysCategoryMain> firstCategoryList = categoryMainService.findList(hql);
		List<KmReviewTemPre> kmReviewTemPreList = new ArrayList<KmReviewTemPre>();
		for (SysCategoryMain sysCategoryMain : firstCategoryList) {
			List<SysCategoryMain> secondCategoryList = getSecCategoryList(sysCategoryMain.getFdId(), authAreaId);
			List<KmReviewTemplate> secondTemplateList = getSecTemplateList(sysCategoryMain.getFdId(), authAreaId);
			if(secondCategoryList.size()>0||secondTemplateList.size()>0){
			KmReviewTemPre kmReviewTemPre = new KmReviewTemPre();
			kmReviewTemPre.setCategoryId(sysCategoryMain.getFdId());
			kmReviewTemPre.setTempName(sysCategoryMain.getFdName());
			kmReviewTemPre.setIsFirstCate("1");
			kmReviewTemPre.setIsTemOrCate("CATEGORY");
			kmReviewTemPre.setDocAmount(getDocAmount(sysCategoryMain,authAreaId));
			List<KmReviewTemPre> tempList = new ArrayList<KmReviewTemPre>();
			for (SysCategoryMain sysSecCategoryMain : secondCategoryList) {
				KmReviewTemPre kmDocSecCategoryPreview = new KmReviewTemPre();
				kmDocSecCategoryPreview.setCategoryId(sysSecCategoryMain.getFdId());
				kmDocSecCategoryPreview.setTempName(sysSecCategoryMain
						.getFdName());
				kmDocSecCategoryPreview.setIsFirstCate("0");
				kmDocSecCategoryPreview.setIsTemOrCate("CATEGORY");
				kmDocSecCategoryPreview
						.setDocAmount(getDocAmount(sysSecCategoryMain,authAreaId));
				tempList.add(kmDocSecCategoryPreview);
			}
			for (KmReviewTemplate kmReviewTemplate : secondTemplateList) {
				KmReviewTemPre kmDocSecTemplatePreview = new KmReviewTemPre();
				kmDocSecTemplatePreview.setCategoryId(kmReviewTemplate.getFdId());
				kmDocSecTemplatePreview.setTempName(kmReviewTemplate
						.getFdName());
				kmDocSecTemplatePreview.setIsFirstCate("0");
				kmDocSecTemplatePreview
						.setDocAmount(getDocAmount(kmReviewTemplate.getFdId(),authAreaId));
				kmDocSecTemplatePreview.setIsTemOrCate("TEMPLATE");
				tempList.add(kmDocSecTemplatePreview);
			}
			kmReviewTemPre.setTempList(tempList);
			for(int i=0;i <tempList.size();i++ ){
				List<KmReviewTemPre> thirdList = new ArrayList<KmReviewTemPre>();
				KmReviewTemPre k = tempList.get(i);
				if(!"TEMPLATE".equals(k.getIsTemOrCate())){
					List<SysCategoryMain> thirdCategoryList = getSecCategoryList(k.getCategoryId(), authAreaId);
					List<KmReviewTemplate> thirdTemplateList = getSecTemplateList(k.getCategoryId(), authAreaId);
					for (SysCategoryMain sysThirdCategoryMain : thirdCategoryList) {
						KmReviewTemPre kmThirdCategoryPreview = new KmReviewTemPre();
						kmThirdCategoryPreview.setCategoryId(sysThirdCategoryMain.getFdId());
						kmThirdCategoryPreview.setTempName(sysThirdCategoryMain
								.getFdName());
						kmThirdCategoryPreview.setIsFirstCate("0");
						kmThirdCategoryPreview.setIsTemOrCate("CATEGORY");
						kmThirdCategoryPreview
								.setDocAmount(getDocAmount(sysThirdCategoryMain,authAreaId));
						thirdList.add(kmThirdCategoryPreview);
					}
					for (KmReviewTemplate sysThirdTemplate : thirdTemplateList) {
						KmReviewTemPre kmThirdTemplatePreview = new KmReviewTemPre();
						kmThirdTemplatePreview.setCategoryId(sysThirdTemplate.getFdId());
						kmThirdTemplatePreview.setTempName(sysThirdTemplate
								.getFdName());
						kmThirdTemplatePreview.setIsFirstCate("0");
						kmThirdTemplatePreview
								.setDocAmount(getDocAmount(sysThirdTemplate.getFdId(),authAreaId));
						kmThirdTemplatePreview.setIsTemOrCate("TEMPLATE");
						thirdList.add(kmThirdTemplatePreview);
					}
					k.setTempList(thirdList);
				}
			}
			
			//this.add(kmReviewPre);
			kmReviewTemPreList.add(kmReviewTemPre);
		}
	 }
		return kmReviewTemPreList;
	}

	@Override
    public Integer getDocAmount(SysCategoryMain sysCategoryMain, String authAreaId)
			throws Exception {
		String fdHierarchyId = sysCategoryMain.getFdHierarchyId();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setFromBlock(" KmReviewMain kmReviewMain ");
		hqlInfo.setJoinBlock(
				" left join kmReviewMain.fdTemplate.docCategory docCategory ");
//		hqlInfo.setWhereBlock(" (kmReviewMain.docStatus <> '10') and substring(a.fdHierarchyId,1,"
//						+ fdHierarchyId.length() + ")='" + fdHierarchyId + "'");
		//优化
		hqlInfo.setWhereBlock(" docCategory.fdHierarchyId like :fdHierarchyId");
		hqlInfo.setParameter("fdHierarchyId", fdHierarchyId+ "%");
		
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaSpecified,
				authAreaId);
		List list = kmReviewMainService.findValue(hqlInfo);
		Object ret = list.get(0);
		if (ret == null) {
			return 0;
		} else {
			return Integer.parseInt(ret.toString());
		}
	}

	@Override
    public Integer getDocAmount(String templateId, String authAreaId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setFromBlock(" KmReviewMain kmReviewMain ");
		hqlInfo.setWhereBlock(" kmReviewMain.fdTemplate.fdId='"+ templateId + "'");
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaSpecified,
				authAreaId);
		List list = kmReviewMainService.findValue(hqlInfo);
		Object ret = list.get(0);
		if (ret == null) {
			return 0;
		} else {
			return Integer.parseInt(ret.toString());
		}
	}

	@Override
    public List getSecCategoryList(String categoryId, String authAreaId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("sysCategoryMain.hbmParent.fdId =:categoryId");
		hql.setOrderBy("sysCategoryMain.fdOrder ,sysCategoryMain.fdId");
		hql.setParameter("categoryId", categoryId);
		hql.setCheckParam(SysAuthConstant.CheckType.AreaSpecified,authAreaId);
		hql.setCheckParam(SysAuthConstant.CheckType.AreaIsolation,
				ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		List<SysCategoryMain> secondCategoryList = categoryMainService
				.findList(hql);
		return secondCategoryList;
	}

	@Override
    public List getSecTemplateList(String categoryId, String authAreaId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock(
				"kmReviewTemplate.docCategory.fdId=:categoryId and  ( kmReviewTemplate.fdIsAvailable is null or kmReviewTemplate.fdIsAvailable = :fdIsAvailable)");
		hql.setOrderBy("kmReviewTemplate.fdOrder ,kmReviewTemplate.fdId");
		hql.setParameter("categoryId", categoryId);
		hql.setParameter("fdIsAvailable", Boolean.TRUE);
		/*hql.setParameter("fdIsAvailable", Boolean.TRUE);*/
		hql.setCheckParam(SysAuthConstant.CheckType.AreaSpecified,authAreaId);
		hql.setCheckParam(SysAuthConstant.CheckType.AreaIsolation,
				ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		List<KmReviewTemplate> secondTemplateList = kmReviewTemplateService
				.findList(hql);
		return secondTemplateList;
	}
}
