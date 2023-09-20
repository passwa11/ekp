package com.landray.kmss.hr.ratify.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyOverview;
import com.landray.kmss.hr.ratify.model.HrRatifyTemPre;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyMainService;
import com.landray.kmss.hr.ratify.service.IHrRatifyOverviewService;
import com.landray.kmss.hr.ratify.service.IHrRatifyTemplateService;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrRatifyOverviewServiceImp extends ExtendDataServiceImp implements IHrRatifyOverviewService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrRatifyOverview) {
            HrRatifyOverview hrRatifyOverview = (HrRatifyOverview) model;
            hrRatifyOverview.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifyOverview hrRatifyOverview = new HrRatifyOverview();
        hrRatifyOverview.setDocAlterTime(new Date());
        HrRatifyUtil.initModelFromRequest(hrRatifyOverview, requestContext);
        return hrRatifyOverview;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyOverview hrRatifyOverview = (HrRatifyOverview) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	private ISysCategoryMainService sysCategoryMainService;

	public void setSysCategoryMainService(
			ISysCategoryMainService sysCategoryMainService) {
		this.sysCategoryMainService = sysCategoryMainService;
	}

	private IHrRatifyMainService hrRatifyMainService;

	public void
			setHrRatifyMainService(IHrRatifyMainService hrRatifyMainService) {
		this.hrRatifyMainService = hrRatifyMainService;
	}

	private IHrRatifyTemplateService hrRatifyTemplateService;

	public void setHrRatifyTemplateService(
			IHrRatifyTemplateService hrRatifyTemplateService) {
		this.hrRatifyTemplateService = hrRatifyTemplateService;
	}

	@Override
	public List<HrRatifyMain> getLatestDoc() throws Exception {
		List<HrRatifyMain> list = new ArrayList<HrRatifyMain>();
		int rowSize = 10;
		String whereBlock = "hrRatifyMain.docStatus='"
				+ SysDocConstant.DOC_STATUS_PUBLISH + "'";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("hrRatifyMain.docPublishTime desc");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(rowSize);
		list = hrRatifyMainService.findPage(hqlInfo).getList();
		return list;
	}

	@Override
	public List<HrRatifyMain> getHotDoc() throws Exception {
		List<HrRatifyMain> list = new ArrayList<HrRatifyMain>();
		int rowSize = 9;
		String whereBlock = "hrRatifyMain.docStatus='"
				+ SysDocConstant.DOC_STATUS_PUBLISH + "'";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("hrRatifyMain.docReadCount desc");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(rowSize);
		list = hrRatifyMainService.findPage(hqlInfo).getList();
		return list;
	}

	@Override
	public String updateReview() throws Exception {
		HQLInfo hql = new HQLInfo();
		List<HrRatifyOverview> hrRatifyPreList = this.findList(hql);
		if (hrRatifyPreList.size() > 0) {
			for (HrRatifyOverview hrRatifyPre : hrRatifyPreList) {
				updateBuildTmp(hrRatifyPre);
			}
		} else {
			return buildTmp();
		}
		return null;
	}

	@Override
	public String getReviewPre() throws Exception {
		String fdPreContent = null;
		List<HrRatifyOverview> hrRatifyOverviewList = new ArrayList<HrRatifyOverview>();
		HQLInfo hql = new HQLInfo();
		hrRatifyOverviewList = this.findList(hql);

		if (UserOperHelper.allowLogOper("preview", getModelName())) {
			for (HrRatifyOverview o : hrRatifyOverviewList) {
				UserOperContentHelper.putFind((IBaseModel) o);
			}
		}

		if (hrRatifyOverviewList.size() > 0) {
			fdPreContent = hrRatifyOverviewList.get(0).getFdPreContent();
		}
		return fdPreContent;
	}

	private String updateBuildTmp(HrRatifyOverview overview) throws Exception {
		String fdPreContent = outContent();
		if (UserOperHelper.allowLogOper("preview", getModelName())) {
			UserOperContentHelper.putUpdate(overview).putSimple("fdPreContent",
					overview.getFdPreContent(), fdPreContent);
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
		String fdPreContent = outContent();
		HrRatifyOverview overview = new HrRatifyOverview();
		overview.setFdPreContent(fdPreContent);
		overview.setDocAlterTime(new Date());

		this.add(overview);
		if (UserOperHelper.allowLogOper("preview", getModelName())) {
			UserOperContentHelper.putAdd(overview, "fdPreContent");
		}
		return fdPreContent.toString();
	}

	public String outContent() throws Exception {
		List<HrRatifyTemPre> hrRatifyPreList = getContentList();
		JSONArray array1 = new JSONArray();
		for (int i = 0; i < hrRatifyPreList.size(); i++) {
			HrRatifyTemPre p1 = hrRatifyPreList.get(i);
			JSONObject obj1 = new JSONObject();
			obj1.put("text", p1.getTempName());
			obj1.put("count", p1.getDocAmount());
			obj1.put("id", p1.getCategoryId());
			obj1.put("nodeType", p1.getIsTemOrCate());
			JSONArray array2 = new JSONArray();
			for (int t = 0; t < p1.getTempList().size(); t++) {
				HrRatifyTemPre p2 = p1.getTempList().get(t);
				JSONObject obj2 = new JSONObject();
				obj2.put("text", p2.getTempName());
				obj2.put("count", p2.getDocAmount());
				obj2.put("id", p2.getCategoryId());
				obj2.put("nodeType", p2.getIsTemOrCate());
				JSONArray array3 = new JSONArray();
				for (int m = 0; m < p2.getTempList().size(); m++) {
					HrRatifyTemPre p3 = p2.getTempList().get(m);
					JSONObject obj3 = new JSONObject();
					obj3.put("text", p3.getTempName());
					obj3.put("count", p3.getDocAmount());
					obj3.put("id", p3.getCategoryId());
					obj3.put("nodeType", p3.getIsTemOrCate());
					array3.add(obj3);
				}
				obj2.put("children", array3);
				array2.add(obj2);
			}
			obj1.put("children", array2);
			array1.add(obj1);
		}
		return array1.toString();
	}

	@Override
	public List<HrRatifyTemPre> getContentList() throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock(
				"sysCategoryMain.hbmParent is null and sysCategoryMain.fdModelName='com.landray.kmss.hr.ratify.model.HrRatifyTemplate'");
		hql.setOrderBy("sysCategoryMain.fdOrder,sysCategoryMain.fdId");
		List<SysCategoryMain> firstCategoryList = sysCategoryMainService
				.findList(hql);
		List<HrRatifyTemPre> hrRatifyTemPreList = new ArrayList<HrRatifyTemPre>();
		for (SysCategoryMain sysCategoryMain : firstCategoryList) {
			List<SysCategoryMain> secondCategoryList = getSecCategoryList(
					sysCategoryMain.getFdId());
			List<HrRatifyTemplate> secondTemplateList = getSecTemplateList(
					sysCategoryMain.getFdId());
			if (secondCategoryList.size() > 0
					|| secondTemplateList.size() > 0) {
				HrRatifyTemPre hrRatifyTemPre = new HrRatifyTemPre();
				hrRatifyTemPre.setCategoryId(sysCategoryMain.getFdId());
				hrRatifyTemPre.setTempName(sysCategoryMain.getFdName());
				hrRatifyTemPre.setIsFirstCate("1");
				hrRatifyTemPre.setIsTemOrCate("CATEGORY");
				hrRatifyTemPre.setDocAmount(getDocAmount(sysCategoryMain));
				List<HrRatifyTemPre> tempList = new ArrayList<HrRatifyTemPre>();
				for (SysCategoryMain sysSecCategoryMain : secondCategoryList) {
					HrRatifyTemPre hrDocSecCategoryPreview = new HrRatifyTemPre();
					hrDocSecCategoryPreview
							.setCategoryId(sysSecCategoryMain.getFdId());
					hrDocSecCategoryPreview.setTempName(sysSecCategoryMain
							.getFdName());
					hrDocSecCategoryPreview.setIsFirstCate("0");
					hrDocSecCategoryPreview.setIsTemOrCate("CATEGORY");
					hrDocSecCategoryPreview
							.setDocAmount(getDocAmount(sysSecCategoryMain));
					tempList.add(hrDocSecCategoryPreview);
				}
				for (HrRatifyTemplate hrRatifyTemplate : secondTemplateList) {
					HrRatifyTemPre hrDocSecTemplatePreview = new HrRatifyTemPre();
					hrDocSecTemplatePreview
							.setCategoryId(hrRatifyTemplate.getFdId());
					hrDocSecTemplatePreview.setTempName(hrRatifyTemplate
							.getFdName());
					hrDocSecTemplatePreview.setIsFirstCate("0");
					hrDocSecTemplatePreview
							.setDocAmount(
									getDocAmount(hrRatifyTemplate.getFdId()));
					hrDocSecTemplatePreview.setIsTemOrCate("TEMPLATE");
					tempList.add(hrDocSecTemplatePreview);
				}
				hrRatifyTemPre.setTempList(tempList);
				for (int i = 0; i < tempList.size(); i++) {
					List<HrRatifyTemPre> thirdList = new ArrayList<HrRatifyTemPre>();
					HrRatifyTemPre k = tempList.get(i);
					if (!"TEMPLATE".equals(k.getIsTemOrCate())) {
						List<SysCategoryMain> thirdCategoryList = getSecCategoryList(
								k.getCategoryId());
						List<HrRatifyTemplate> thirdTemplateList = getSecTemplateList(
								k.getCategoryId());
						for (SysCategoryMain sysThirdCategoryMain : thirdCategoryList) {
							HrRatifyTemPre hrThirdCategoryPreview = new HrRatifyTemPre();
							hrThirdCategoryPreview.setCategoryId(
									sysThirdCategoryMain.getFdId());
							hrThirdCategoryPreview
									.setTempName(sysThirdCategoryMain
											.getFdName());
							hrThirdCategoryPreview.setIsFirstCate("0");
							hrThirdCategoryPreview.setIsTemOrCate("CATEGORY");
							hrThirdCategoryPreview
									.setDocAmount(
											getDocAmount(sysThirdCategoryMain));
							thirdList.add(hrThirdCategoryPreview);
						}
						for (HrRatifyTemplate sysThirdTemplate : thirdTemplateList) {
							HrRatifyTemPre hrThirdTemplatePreview = new HrRatifyTemPre();
							hrThirdTemplatePreview
									.setCategoryId(sysThirdTemplate.getFdId());
							hrThirdTemplatePreview.setTempName(sysThirdTemplate
									.getFdName());
							hrThirdTemplatePreview.setIsFirstCate("0");
							hrThirdTemplatePreview
									.setDocAmount(getDocAmount(
											sysThirdTemplate.getFdId()));
							hrThirdTemplatePreview.setIsTemOrCate("TEMPLATE");
							thirdList.add(hrThirdTemplatePreview);
						}
						k.setTempList(thirdList);
					}
				}
				hrRatifyTemPreList.add(hrRatifyTemPre);
			}
		}
		return hrRatifyTemPreList;
	}

	@Override
	public Integer getDocAmount(SysCategoryMain sysCategoryMain)
			throws Exception {
		String fdHierarchyId = sysCategoryMain.getFdHierarchyId();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setFromBlock(" HrRatifyMain hrRatifyMain ");
		hqlInfo.setJoinBlock(
				" left join hrRatifyMain.docTemplate.docCategory a ");
		// 优化
		hqlInfo.setWhereBlock(" a.fdHierarchyId like :fdHierarchyId");
		hqlInfo.setParameter("fdHierarchyId", fdHierarchyId + "%");

		List list = hrRatifyMainService.findValue(hqlInfo);
		Object ret = list.get(0);
		if (ret == null) {
			return 0;
		} else {
			return Integer.parseInt(ret.toString());
		}
	}

	@Override
	public Integer getDocAmount(String templateId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setFromBlock(" HrRatifyMain hrRatifyMain ");
		hqlInfo.setWhereBlock(
				" hrRatifyMain.docTemplate.fdId='" + templateId + "'");
		List list = hrRatifyMainService.findValue(hqlInfo);
		Object ret = list.get(0);
		if (ret == null) {
			return 0;
		} else {
			return Integer.parseInt(ret.toString());
		}
	}

	@Override
	public List getSecTemplateList(String categoryId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock(
				"hrRatifyTemplate.docCategory.fdId=:categoryId and (hrRatifyTemplate.fdIsAvailable is null or hrRatifyTemplate.fdIsAvailable = :fdIsAvailable)");
		hql.setOrderBy("hrRatifyTemplate.fdOrder ,hrRatifyTemplate.fdId");
		hql.setParameter("categoryId", categoryId);
		hql.setParameter("fdIsAvailable", Boolean.TRUE);
		List<HrRatifyTemplate> secondTemplateList = hrRatifyTemplateService
				.findList(hql);
		return secondTemplateList;
	}

	@Override
	public List getSecCategoryList(String categoryId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("sysCategoryMain.hbmParent.fdId =:categoryId");
		hql.setOrderBy("sysCategoryMain.fdOrder ,sysCategoryMain.fdId");
		hql.setParameter("categoryId", categoryId);
		List<SysCategoryMain> secondCategoryList = sysCategoryMainService
				.findList(hql);
		return secondCategoryList;
	}
}
