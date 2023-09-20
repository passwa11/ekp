package com.landray.kmss.km.review.service.spring;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.ICheckUniqueBean;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.km.review.forms.KmReviewTemplateForm;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.model.KmReviewSnContext;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewGenerateSnService;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2007-Aug-30
 * 
 * @author 舒斌 审批流程模板业务接口实现
 */
public class KmReviewTemplateServiceImp extends BaseServiceImp implements
		IKmReviewTemplateService, ICheckUniqueBean {

	private IKmReviewGenerateSnService kmReviewGenerateSnService;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmReviewTemplateServiceImp.class);

	public void setKmReviewGenerateSnService(
			IKmReviewGenerateSnService kmReviewGenerateSnService) {
		this.kmReviewGenerateSnService = kmReviewGenerateSnService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		if (UserUtil.getUser() != null) {
			KmReviewTemplate template = (KmReviewTemplate) modelObj;
			template.setDocAlteror(UserUtil.getUser());
			template.setDocAlterTime(new Date());
		}
		KmReviewTemplate kmReviewTemplate = (KmReviewTemplate) modelObj;
		KmReviewSnContext context = new KmReviewSnContext();
		context.setFdPrefix(kmReviewTemplate.getFdNumberPrefix());
		context.setFdModelName(KmReviewMain.class.getName());
		context.setFdTemplate(modelObj);
		kmReviewGenerateSnService.initalizeSerialNumber(context);
		if (Boolean.TRUE.equals(kmReviewTemplate.getFdUseForm())) {
			kmReviewTemplate.setDocContent(null); // 使用表单时清空RTF
		}
		super.update(modelObj);
		if (kmReviewTemplate.getFdIsAvailable()) {
			dealWithTemplate2Ding(modelObj, "update",
					kmReviewTemplate.getAuthAllReaders());
		} else {
			dealWithTemplate2Ding(modelObj, "delete",
					kmReviewTemplate.getAuthAllReaders());
		}

	}

	@Override
	public IExtendForm cloneModelToForm(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		KmReviewTemplateForm newForm = (KmReviewTemplateForm) super
				.cloneModelToForm(form, model, requestContext);
		newForm.setFdName(newForm.getFdName() + ResourceUtil.getString("km-review:kmReviewMain.template.clone"));
		return newForm;
	}
	
	@Override
	public IExtendForm cloneModelToFormNoName(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception{
		KmReviewTemplateForm newForm = (KmReviewTemplateForm) super
				.cloneModelToForm(form, model, requestContext);
		newForm.setFdName(newForm.getFdName());
		return newForm;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmReviewTemplate kmReviewTemplate = (KmReviewTemplate) modelObj;
		KmReviewSnContext context = new KmReviewSnContext();
		context.setFdPrefix(kmReviewTemplate.getFdNumberPrefix());
		context.setFdModelName(KmReviewMain.class.getName());
		context.setFdTemplate(modelObj);
		kmReviewGenerateSnService.initalizeSerialNumber(context);
		if (Boolean.TRUE.equals(kmReviewTemplate.getFdUseForm())) {
			kmReviewTemplate.setDocContent(null); // 使用表单时清空RTF
		}

		// #41131 外部流程不能实例化流程图
		if (kmReviewTemplate.getFdIsExternal() != null
				&& kmReviewTemplate.getFdIsExternal()) {
			kmReviewTemplate.setSysWfTemplateModels(null);
		}
		String fdId = super.add(modelObj);
		// 处理钉钉模板同步问题
		if (kmReviewTemplate.getFdIsAvailable()) {
			dealWithTemplate2Ding(modelObj, "add",
					kmReviewTemplate.getAuthAllReaders());
		}
		return fdId;

	}

	@Override
    public String checkUnique(RequestContext requestInfo) throws Exception {
		String tempId = requestInfo.getParameter("tempId");
		String prefixStr = requestInfo.getParameter("prefixStr");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock(" kmReviewTemplate.fdNumberPrefix=:prefixStr and kmReviewTemplate.fdId<>:tempId");
		hqlInfo.setParameter("prefixStr", prefixStr);
		hqlInfo.setParameter("tempId", tempId);
		List<KmReviewTemplate> list = findList(hqlInfo);
		String result = "";
		for (KmReviewTemplate template : list) {
			result += template.getDocCategory().getFdName() + "/"
					+ template.getFdName() + ";";
		}
		return ";" + result;
	}
	
	/**
	 * 新建文档获取模板时，过滤不可用的模板
	 */
	@Override
	public List findValue(HQLInfo hqlInfo) throws Exception {
		if (StringUtil.isNull(hqlInfo.getModelName())
				|| hqlInfo.getModelName().contains("KmReviewTemplate")) {
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNotNull(whereBlock)) {
				hqlInfo.setWhereBlock(whereBlock
						+ " and (kmReviewTemplate.fdIsAvailable = :fdIsAvailable or kmReviewTemplate.fdIsAvailable is null)");
			} else {
				hqlInfo.setWhereBlock(
						"kmReviewTemplate.fdIsAvailable = :fdIsAvailable or kmReviewTemplate.fdIsAvailable is null");
			}
			hqlInfo.setParameter("fdIsAvailable", true);
		}

		return super.findValue(hqlInfo);
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {

		super.delete(modelObj);
	}

	// 处理钉钉模版
	public void dealWithTemplate2Ding(IBaseModel modelObj, String method,
			List allReaders) {
		try {
			logger.debug("模板method:" + method);
			if (new File(
					PluginConfigLocationsUtil.getKmssConfigPath()
							+ "/third/ding/").exists()) {
				logger.debug("-------------有钉钉模块-----------------");
				// 模板分类Id
				String categoryId = ((KmReviewTemplate) modelObj)
						.getDocCategory().getFdId();
				String name = ((KmReviewTemplate) modelObj).getFdName();

				String desc = ((KmReviewTemplate) modelObj).getFdDesc();

				Object dingUtil = Class.forName(
						"com.landray.kmss.third.ding.util.DingUtil")
						.newInstance();
				Class dingUtilClazz = dingUtil.getClass();
				Method dealWithDingTemplate = dingUtilClazz.getMethod(
						"dealWithDingTemplate",
						String.class, String.class, String.class, String.class,
						String.class, List.class, String.class);
				if ("delete".equals(method)) {
					dealWithDingTemplate.invoke(dingUtil, method, null,
							modelObj.getFdId(), categoryId, name, allReaders,null);
					return;
				}

				// 外部流程
				boolean fdIsExternal = ((KmReviewTemplate) modelObj)
						.getFdIsExternal();
				String type = "";
				if (!fdIsExternal) {
					Object formUtil = Class.forName(
							"com.landray.kmss.third.ding.xform.util.ThirdDingXFormTemplateUtil")
							.newInstance();
					Class clazz = formUtil.getClass();
					Method getXFormTemplateType = clazz.getMethod(
							"getXFormTemplateType",
							IBaseModel.class);
					type = (String) getXFormTemplateType.invoke(formUtil,
							modelObj);
				}
				logger.debug("-----type------" + type);
				dealWithDingTemplate.invoke(dingUtil, method, type,
						modelObj.getFdId(), categoryId, name, allReaders, desc);

			}

		} catch (Exception e) {
			logger.error("修改钉钉模板失败:" + e.getMessage(), e);
		}
	}

	@Override
	public boolean updateTemplate2OtherCategory(String oldCategoryId,
			String newCategoryId) {
		
		try {
			ISysCategoryMainService sysCategoryMainService = (ISysCategoryMainService) SpringBeanUtil
					.getBean("sysCategoryMainService");
			SysCategoryMain oldSysCategoryMain = (SysCategoryMain) sysCategoryMainService
					.findByPrimaryKey(oldCategoryId);
			if (oldSysCategoryMain == null) {
				logger.error("旧分类不存在    oldCategoryId：" + oldCategoryId);
				return false;
			}
			SysCategoryMain newSysCategoryMain = (SysCategoryMain) sysCategoryMainService
					.findByPrimaryKey(newCategoryId);
			if (newSysCategoryMain == null) {
				logger.error("新分类不存在    newCategoryId：" + newCategoryId);
				return false;
			}

			HQLInfo hql = new HQLInfo();
			hql.setWhereBlock("docCategory.fdId=:fdId");
			hql.setParameter("fdId", oldCategoryId);
			List<KmReviewTemplate> list = super.findList(hql);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					// KmReviewTemplate temp = (KmReviewTemplate)
					// sysCategoryMainService
					// .findByPrimaryKey(list.get(i).getFdId(),
					// KmReviewTemplate.class, true);
					KmReviewTemplate temp = list.get(i);
					logger.warn("迁移模版：" + temp.getFdName() + "  到分类 "
							+ newSysCategoryMain.getFdName() + "  下");
					temp.setDocCategory(newSysCategoryMain);
					super.update(temp);

					// 更新钉钉模版
					if (temp.getFdIsAvailable()) {
						dealWithTemplate2Ding(temp, "update",
								temp.getAuthAllReaders());
					}
				}
			} else {
				logger.warn("原分类   " + oldSysCategoryMain.getFdName()
						+ "  没有流程模版，不需要迁移！");
			}
			
			return true;
		} catch (Exception e) {
			logger.warn("oldCategoryId:" + oldCategoryId + "   newCategoryId:"
					+ newCategoryId);
			logger.error("模版迁移到其他分类失败：" + e.getMessage(), e);
		}
		return false;
	}

}
