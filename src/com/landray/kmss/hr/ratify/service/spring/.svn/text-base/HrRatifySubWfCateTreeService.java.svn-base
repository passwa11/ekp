package com.landray.kmss.hr.ratify.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyTemplateService;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.category.service.SysCatetoryTreeAuthCheck;
import com.landray.kmss.sys.category.service.spring.SysCategoryTreeService;
import com.landray.kmss.sys.config.design.SysCfgCategoryMng;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 子流程模板树筛选
 * 
 */
public class HrRatifySubWfCateTreeService implements IXMLDataBean,ApplicationContextAware {
	ApplicationContext applicationContext;
	private ISysCategoryMainService sysCategoryMainService;
	public void setSysCategoryMainService(
			ISysCategoryMainService sysCategoryMainService) {
		this.sysCategoryMainService = sysCategoryMainService;
	}

	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysCategoryTreeService.class);

	@Override
    public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
    public List getDataList(RequestContext xmlContext) throws Exception {
		String fdKey = xmlContext.getParameter("fdKey");
		String categoryId = xmlContext.getParameter("categoryId");
		String modelName = "com.landray.kmss.hr.ratify.model.HrRatifyTemplate";
		String isGetTemplate = "1";
		String showType = "0";
		//String extendPara = xmlContext.getParameter("extendPara");
		SysCatetoryTreeAuthCheck sysCatetoryTreeAuthCheck = new SysCatetoryTreeAuthCheck(
				xmlContext.getParameter("authType"));
		sysCatetoryTreeAuthCheck
				.setFilterUrl("sys_category_main/sysCategoryMain.do");
		if (modelName == null) {
			throw new KmssRuntimeException(new KmssMessage(
					"sys-category:error.noenoughparameter"));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("获取分类信息,categoryId=" + categoryId + ",modelName="
					+ modelName + ",isGetTemplate=" + isGetTemplate
					+ ",showType=" + showType + ",authType="
					+ sysCatetoryTreeAuthCheck.getAuthType());
		}

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("sysCategoryMain.fdName, sysCategoryMain.fdId");
		hqlInfo.setOrderBy("sysCategoryMain.fdOrder");

		StringBuffer whereString = new StringBuffer(
				"sysCategoryMain.fdModelName=:modelname");
		hqlInfo.setParameter("modelname", modelName);

		String strOrgId = xmlContext.getParameter("orgId");
		if (StringUtil.isNull(strOrgId)) {
			strOrgId = UserUtil.getKMSSUser().getAuthAreaId();
		}

		if (StringUtil.isNotNull(strOrgId)
				&& SysAuthAreaUtils.isAreaEnabled(modelName)
				&& ISysAuthConstant.IS_ISOLATION_ENABLED) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
					SysAuthConstant.AreaCheck.YES);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation,
					ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		}

		if (StringUtil.isNull(categoryId)) {
			whereString.append(" and sysCategoryMain.hbmParent is null");
		} else {
			whereString
					.append(" and sysCategoryMain.hbmParent.fdId=:categoryId");
			hqlInfo.setParameter("categoryId", categoryId);
		}
		hqlInfo.setWhereBlock(whereString.toString());

		if (logger.isDebugEnabled()) {
			logger.debug("搜索类别信息：" + whereString.toString());
		}

		List<?> categoriesList = sysCategoryMainService.findValue(hqlInfo);

		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < categoriesList.size(); i++) {
			Object[] info = (Object[]) categoriesList.get(i);
			sysCatetoryTreeAuthCheck.checkAuth(info[1].toString());
			if (sysCatetoryTreeAuthCheck.isShowInfo()) {
				Map<String, Object> node = new HashMap<String, Object>();
				node.put("text", info[0]);

				if (sysCatetoryTreeAuthCheck.isShowValue()) {
					String beanName = "hrRatifySubWfCateTreeService&showType=0&authType=00&getTemplate=1&modelName="
							+ modelName + "&categoryId=" + info[1] + "&orgId="
							+ strOrgId+"&fdKey="+fdKey;
					//if (StringUtil.isNotNull(extendPara)) {
					//	beanName += "&extendPara=" + extendPara;
					//}
					node.put("beanName", beanName);
				}
				if (!sysCatetoryTreeAuthCheck.isShowValue()
						|| "1".equals(isGetTemplate)) {
					node.put("isShowCheckBox", "0");
					node.put("href", "");
				}
				node.put("value", info[1]);
				node.put("nodeType", "CATEGORY");
				rtnList.add(node);
			}
		}
		// 获取域模型对应的模板信息
		if (!"0".equals(isGetTemplate) && StringUtil.isNotNull(categoryId)) {
			SysConfigs cateConfig = SysConfigs.getInstance();
			if (cateConfig.getCategoryMngs().containsKey(modelName)) {
				SysCfgCategoryMng category = (SysCfgCategoryMng) cateConfig
						.getCategoryMngs().get(modelName);
				String serviceName = category.getServiceName();
				IXMLDataBean baseService = (IXMLDataBean) applicationContext
						.getBean(serviceName);
				RequestContext rContext = new RequestContext();
				rContext.setParameter("modelName", modelName);
				rContext.setParameter("categoryId", categoryId);
				rContext.setParameter("getType", "category");
				rContext.setParameter("checkAuth", sysCatetoryTreeAuthCheck
						.getCheckType());
				//rContext.setParameter("extendPara", extendPara);
				List<Map<String, Object>> templateList = baseService.getDataList(rContext);
				for(int i= 0;i<templateList.size();i++){
					Map<String, Object> node = templateList.get(i);
					Object id = node.get("value");
					IHrRatifyTemplateService hrRatifyTemplateService = (IHrRatifyTemplateService) SpringBeanUtil
							.getBean("hrRatifyTemplateService");
					HrRatifyTemplate hrRatifyTemplate = (HrRatifyTemplate) hrRatifyTemplateService
							.findByPrimaryKey(id.toString());
					if (fdKey.equals(hrRatifyTemplate.getFdTempKey())) {
						rtnList.add(node);
					}
				}
				//rtnList.addAll(templateList);
			}
		}
		return rtnList;
	}
}
