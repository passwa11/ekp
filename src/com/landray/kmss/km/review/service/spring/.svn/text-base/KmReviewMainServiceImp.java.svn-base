package com.landray.kmss.km.review.service.spring;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.km.review.Constant;
import com.landray.kmss.km.review.dao.IKmReviewMainDao;
import com.landray.kmss.km.review.forms.KmReviewMainForm;
import com.landray.kmss.km.review.model.KmReviewConfigNotify;
import com.landray.kmss.km.review.model.KmReviewDocKeyword;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.model.KmReviewSnContext;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.model.KmReviewTemplateKeyword;
import com.landray.kmss.km.review.service.IKmReviewGenerateSnService;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.km.review.service.IKmReviewOutSignService;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.km.review.util.KmReviewTitleUtil;
import com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge;
import com.landray.kmss.kms.multidoc.model.KmsMultidocSubside;
import com.landray.kmss.kms.multidoc.model.KmsMultidocSubsideConfig;
import com.landray.kmss.kms.multidoc.service.IKmsMultidocSubsideService;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaMainCoreService;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaMainForm;
import com.landray.kmss.sys.agenda.interfaces.SysAgendaMainContextFormula;
import com.landray.kmss.sys.agenda.util.SysAgendaTypeEnum;
import com.landray.kmss.sys.archives.config.SysArchivesConfig;
import com.landray.kmss.sys.archives.interfaces.IArchAutoFileDataService;
import com.landray.kmss.sys.archives.interfaces.IArchFileDataService;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.archives.model.SysArchivesParamModel;
import com.landray.kmss.sys.archives.service.ISysArchivesFileTemplateService;
import com.landray.kmss.sys.archives.util.SysArchivesUtil;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.authorization.service.ISysAuthAreaService;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.category.interfaces.CategoryUtil;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.rest.api.ISysCategoryCountTreeBuilder;
import com.landray.kmss.sys.category.rest.api.bo.SQLInfo;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.service.ISysFileConvertClientService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction;
import com.landray.kmss.sys.lbpm.engine.integrate.rules.IRuleProvider;
import com.landray.kmss.sys.lbpm.engine.integrate.rules.RuleFact;
import com.landray.kmss.sys.lbpm.engine.manager.NoExecutionEnvironment;
import com.landray.kmss.sys.lbpm.engine.manager.ProcessServiceManager;
import com.landray.kmss.sys.lbpm.engine.persistence.AccessManager;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmNodeDefinition;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmNodeDefinitionHandler;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo;
import com.landray.kmss.sys.lbpm.engine.support.def.XMLUtils;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.portal.cloud.dto.IconDataVO;
import com.landray.kmss.sys.portal.cloud.util.ListDataUtil;
import com.landray.kmss.sys.portal.util.PortletTimeUtil;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowDiscard;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建日期 2007-Aug-30
 * 
 * @author 舒斌 审批文档基本信息业务接口实现
 */
@SuppressWarnings("unchecked")
public class KmReviewMainServiceImp extends ExtendDataServiceImp implements
		IKmReviewMainService, ApplicationListener,
		com.landray.kmss.kms.multidoc.interfaces.IFileDataService, ISysCategoryCountTreeBuilder, IXMLDataBean, IArchAutoFileDataService, IArchFileDataService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmReviewMainServiceImp.class);
	
	public ISysAttMainCoreInnerService sysAttMainService;
	
	public IKmReviewTemplateService kmReviewTemplateService;

	public ISysNotifyMainCoreService sysNotifyMainCoreService;

	public ISysCategoryMainService sysCategoryMainService;

	private IKmReviewGenerateSnService kmReviewGenerateSnService;
	

	private IKmsMultidocSubsideService kmsMultidocSubsideService;

	public void setKmsMultidocSubsideService(
			IKmsMultidocSubsideService kmsMultidocSubsideService) {
		this.kmsMultidocSubsideService = kmsMultidocSubsideService;
	}

	public void setSysAttMainService(ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	public void setKmReviewGenerateSnService(
			IKmReviewGenerateSnService kmReviewGenerateSnService) {
		this.kmReviewGenerateSnService = kmReviewGenerateSnService;
	}


	protected ISysOrgElementService getSysOrgElementService() {
		return (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
	}
	
	private IKmReviewOutSignService kmReviewOutSignService = null;

	public IKmReviewOutSignService getKmReviewOutSignService() {
		if (kmReviewOutSignService == null) {
			kmReviewOutSignService = (IKmReviewOutSignService) SpringBeanUtil
					.getBean("kmReviewOutSignService");
		}
		return kmReviewOutSignService;
	}
	
	
	/**
	 * 
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getOrgAndPost(HttpServletRequest request, String[] orgIds) throws Exception {
		List<String> orgIdList = new ArrayList<String>();
		for (String orgId : orgIds) {
			orgIdList.add(orgId);
		}

		List<String> postList = new ArrayList<String>();
		for (String orgId : orgIds) {
			SysOrgElement org = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(orgId);
			List<SysOrgElement> posts = org.getFdPosts();
			for (SysOrgElement post : posts) {
				if (!postList.contains(orgId)) {
					postList.add(post.getFdId());
				}
			}

		}
		for (String post : postList) {
			orgIdList.add(post);
		}
		return orgIdList;
	}

	private ISysNumberFlowService sysNumberFlowService;

	public void setSysNumberFlowService(
			ISysNumberFlowService sysNumberFlowService) {
		this.sysNumberFlowService = sysNumberFlowService;
	}

	/**
	 * 修改流程文档权限
	 * 
	 * @param documentId
	 *            文档ID
	 * @param form
	 * @throws Exception
	 */
	@Override
	public void updateDocumentPermission(IExtendForm form,
										 RequestContext requestContext) throws Exception {
		IBaseModel model = convertFormToModel(form, null, requestContext);
		if (model == null) {
            throw new NoRecordException();
        }
		this.update(model);

	}

	/**
	 * 转移流程文档
	 * 
	 * @param fdId
	 *            文档ID
	 * @param categoryId
	 *            目标模板ID
	 * @throws Exception
	 */
	@Override
	public void updateDocumentCategory(String fdId, String templateId)
			throws Exception {
		KmReviewMain main = (KmReviewMain) findByPrimaryKey(fdId);
		KmReviewTemplate template = (KmReviewTemplate) kmReviewTemplateService
				.findByPrimaryKey(templateId);
		main.setFdTemplate(template);
		update(main);
	}

	/**
	 * 新建流程文档
	 */
	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		KmReviewMainForm main = (KmReviewMainForm) form;
		if (!Constant.STATUS_DRAFT.equals(main.getDocStatus())) {
			// 修改为调用新服务取得流水编号 modify by limh 2010年11月5日
			// main.setFdNumber(generateFlowNumber(main.getFdTemplateId(),
			// null));

			String fdNumber = "";
			if (com.landray.kmss.sys.number.util.NumberResourceUtil
					.isModuleNumberEnable("com.landray.kmss.km.review.model.KmReviewMain")) {
				IBaseModel model = initModelSetting(requestContext);
				model = convertFormToModel(main, model, requestContext);
				fdNumber = sysNumberFlowService.generateFlowNumber(model);
			} else {
				KmReviewSnContext context = new KmReviewSnContext();
				String templateId = main.getFdTemplateId();
				KmReviewTemplate template = (KmReviewTemplate) kmReviewTemplateService
						.findByPrimaryKey(templateId);
				context.setFdPrefix(template.getFdNumberPrefix());
				context.setFdModelName(KmReviewMain.class.getName());
				context.setFdTemplate(template);
				synchronized (this) {
					fdNumber = kmReviewGenerateSnService
							.getSerialNumber(context);
				}
			}

			main.setFdNumber(fdNumber);
		}
		// 解决子流程模板设置日程同步，启动子流程时选择跳过起草节点，无法同步到日程
		if (main instanceof ISysAgendaMainForm
				&& StringUtil.isNull(main.getSyncDataToCalendarTime())) {
			String templateId = main.getFdTemplateId();
			KmReviewTemplate template = (KmReviewTemplate) kmReviewTemplateService
					.findByPrimaryKey(templateId);
			main.setSyncDataToCalendarTime(
					template.getSyncDataToCalendarTime());
		}
		String fdId = super.add(form, requestContext);
		return fdId;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmReviewMain mainModel = (KmReviewMain) modelObj;
		String docMessage = ResourceUtil.getString("km-review:kmReviewMain.docSubject.info");
		Boolean editDocSubject = mainModel.getFdTemplate().getEditDocSubject()==null?false:mainModel.getFdTemplate().getEditDocSubject();
		if (editDocSubject == true && StringUtil.isNotNull(mainModel.getDocSubject()) && !docMessage.equals(mainModel.getDocSubject().trim())) {
			// 如果开启 编辑主题，且主题默认内容发生改变,则不添加规则
		} else {
			mainModel.setTitleRegulation(mainModel.getFdTemplate().getTitleRegulation());
		}
		// 根据标题规则生成标题
		KmReviewTitleUtil.genTitle(modelObj);

		if(logger.isInfoEnabled()){
			logger.info("kmreviewmainserviceImp add begin");
		}
		String fdId = super.add(modelObj);

		if(logger.isInfoEnabled()){
			logger.info("kmreviewmainserviceImp add end fdId:"+fdId);
		}

		if ("flowSubmitAfter".equals(mainModel.getSyncDataToCalendarTime())) {
			// 日程机制新增同步(针对表单公式定义器模块)
			updateSyncDataToCalendarFormula(mainModel);
		}
		return fdId;
	}
	
	
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmReviewMain main = (KmReviewMain) modelObj;
		if (!Constant.STATUS_DRAFT.equals(main.getDocStatus())
				&& StringUtil.isNull(main.getFdNumber())) {
			// 修改为调用新服务取得流水编号 modify by limh 2010年11月5日
			// main.setFdNumber(generateFlowNumber(null, main.getFdTemplate()));
			String fdNumber = "";
			if (com.landray.kmss.sys.number.util.NumberResourceUtil
					.isModuleNumberEnable("com.landray.kmss.km.review.model.KmReviewMain")) {
				fdNumber = sysNumberFlowService.generateFlowNumber(main);
			} else {
				KmReviewSnContext context = new KmReviewSnContext();
				KmReviewTemplate template = main.getFdTemplate();
				context.setFdPrefix(template.getFdNumberPrefix());
				context.setFdModelName(KmReviewMain.class.getName());
				context.setFdTemplate(template);
				synchronized (this) {
					fdNumber = kmReviewGenerateSnService
							.getSerialNumber(context);
				}
			}
			main.setFdNumber(fdNumber);
		}
		// 根据标题规则生成标题
		KmReviewTitleUtil.genTitle(modelObj);

		super.update(modelObj);
		if ("flowSubmitAfter".equals(main.getSyncDataToCalendarTime())) {
			// 日程机制新增同步(针对表单公式定义器模块)
			updateSyncDataToCalendarFormula(main);
		} else {
			// 修改了同步时机,删除日程
			deleteSyncDataToCalendarFormula(main);
		}
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		final String fdId = modelObj.getFdId();
		KmReviewMain main = (KmReviewMain) modelObj;
		// 删除流程时删除日程
		if ("flowSubmitAfter".equals(main.getSyncDataToCalendarTime())
				|| "flowPublishAfter".equals(main.getSyncDataToCalendarTime())) {
			deleteSyncDataToCalendarFormula(main);
		}

		//更新钉钉套件的实例状态
		dealwithDingSuiteInstance(main.getFdId());

		this.getBaseDao().getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException {
						// TODO Auto-generated method stub
						StringBuilder sb = new StringBuilder();
						sb
								.append("delete KmReviewFeedbackInfo kmReviewFeedbackInfo where kmReviewFeedbackInfo.kmReviewMain.fdId='");
						sb.append(fdId);
						sb.append("'");

						return session.createQuery(sb.toString())
								.executeUpdate();
					}

				});
		Integer signCount = getKmReviewOutSignService()
				.findBySignId(modelObj.getFdId());
		if (signCount > 0) {
			// 删除易企签署信息
			getBaseDao().getHibernateSession().createNativeQuery(
					"delete from km_review_out_sign where fd_main_id = ?")
					.addSynchronizedQuerySpace("km_review_out_sign")
					.setString(0, modelObj.getFdId())
					.executeUpdate();
		}
		super.delete(modelObj);
	}

	private void dealwithDingSuiteInstance(String fdId) {
		try {
			if (new File(PluginConfigLocationsUtil.getKmssConfigPath()
					+ "/third/ding/").exists()) {
				Object dingUtil = Class.forName("com.landray.kmss.third.ding.util.DingUtil").newInstance();
				Class dingUtilClazz = dingUtil.getClass();
				Method deleteReviewMain = dingUtilClazz.getMethod("deleteReviewMain",String.class);
				deleteReviewMain.invoke(dingUtil,fdId);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(),e);
		}
	}

	@Override
	public int updateDucmentTemplate(String ids, String templateId)
			throws Exception {
		if(UserOperHelper.allowLogOper("changeTemplate", getModelName())){
			 String[] idArr = ids.split(",");
			 for (int i = 0; i < idArr.length; i++) {
				 KmReviewMain main = (KmReviewMain) findByPrimaryKey(idArr[i]);
				 UserOperContentHelper.putUpdate(main.getFdId(),main.getDocSubject(),getModelName()).putSimple("fdTemplateId", main.getFdTemplate().getFdId(), templateId);
			 } 
		}
		return ((IKmReviewMainDao) this.getBaseDao()).updateDocumentTemplate(
				ids, templateId);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
            return;
        }
		Object obj = event.getSource();
		if (!(obj instanceof KmReviewMain)) {
            return;
        }
		if (event instanceof Event_SysFlowFinish) {
			KmReviewMain main = (KmReviewMain) obj;
			main.setDocPublishTime(new Date());
			try {
				// getBaseDao().update(main);
				// 为支持bam2修改的代码块 begin 2013-08-22
				this.update(main);
				// 为支持bam2修改的代码块 end 2013-08-22
				List feedbackList = main.getFdFeedback();
				if (feedbackList.size() > 0) {
					KmReviewConfigNotify configNotify = new KmReviewConfigNotify();
					configNotify.getFdNotifyType();
					if (StringUtil.isNull(configNotify.getFdNotifyType())) {
                        return;
                    }
					// HashMap map = new HashMap();
					// map.put("km-review:kmReviewMain.docSubject", main
					// .getDocSubject());
					NotifyReplace notifyReplace = new NotifyReplace();
					notifyReplace.addReplaceText(
							"km-review:kmReviewMain.docSubject", main
							.getDocSubject());
					NotifyContext notifyContext = sysNotifyMainCoreService
							.getContext("km-review:kmReview.feedback.notify");
					notifyContext.setKey("passreadKey");
					notifyContext
							.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
					notifyContext.setNotifyTarget(feedbackList);
					notifyContext.setNotifyType(configNotify.getFdNotifyType());
					sysNotifyMainCoreService.sendNotify(main, notifyContext,
							notifyReplace);

				}
				if ("flowPublishAfter".equals(main.getSyncDataToCalendarTime())) {
					// 日程机制新增同步(针对表单公式定义器模块)
					updateSyncDataToCalendarFormula(main);
				}
			} catch (Exception e) {
				throw new KmssRuntimeException(e);
			}

		} else if (event instanceof Event_SysFlowDiscard) {
			KmReviewMain main = (KmReviewMain) obj;
			main.setDocPublishTime(new Date());
			try {
				this.update(main);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				throw new KmssRuntimeException(e1);
			}
			// 废弃时删除日程
			if ("flowSubmitAfter".equals(main.getSyncDataToCalendarTime())) {
				try {
					deleteSyncDataToCalendarFormula(main);
				} catch (Exception e) {
					throw new KmssRuntimeException(e);
				}
			}
		}
	}

	@Override
	public void updateFeedbackPeople(KmReviewMain main, List notifyTarget)
			throws Exception {
		super.update(main);
		if (main.getFdNotifyType() == null) {
            return;
        }
		// HashMap map = new HashMap();
		// map.put("km-review:kmReviewMain.docSubject", main.getDocSubject());
		NotifyReplace notifyReplace = new NotifyReplace();
		notifyReplace.addReplaceText("km-review:kmReviewMain.docSubject",
				main.getDocSubject());
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("km-review:kmReview.feedback.notify");
		notifyContext.setKey("passreadKey");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext.setNotifyTarget(notifyTarget);
		notifyContext.setNotifyType(main.getFdNotifyType());
		sysNotifyMainCoreService.sendNotify(main, notifyContext, notifyReplace);
	}

	public IKmReviewTemplateService getKmReviewTemplateService() {
		return kmReviewTemplateService;
	}

	public void setKmReviewTemplateService(
			IKmReviewTemplateService kmReviewTemplateService) {
		this.kmReviewTemplateService = kmReviewTemplateService;
	}

	public ISysCategoryMainService getSysCategoryMainService() {
		return sysCategoryMainService;
	}

	public void setSysCategoryMainService(
			ISysCategoryMainService sysCategoryMainService) {
		this.sysCategoryMainService = sysCategoryMainService;
	}

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	@Override
	protected IBaseModel initBizModelSetting(RequestContext requestContext)
			throws Exception {
		String templateId = requestContext.getParameter("fdTemplateId");
		if (!StringUtil.isNotNull(templateId)) {
			return null;
		}
		KmReviewMain model = new KmReviewMain();
		KmReviewTemplate template = (KmReviewTemplate) getKmReviewTemplateService()
				.findByPrimaryKey(templateId);
		// 加载机制数据
		// getKmReviewTemplateService().convertModelToForm(null, template,
		// requestContext);
		model.setFdTemplate(template);
		model.setDocContent(template.getDocContent());
		model.setFdFeedback(new ArrayList(template.getFdFeedback()));
		model.setFdFeedbackModify(template.getFdFeedbackModify() ? "1" : "0");
		List<KmReviewTemplateKeyword> templateList = template.getDocKeyword();
		List modelKeywordList = new ArrayList();
		for (KmReviewTemplateKeyword tkey : templateList) {
			KmReviewDocKeyword tKeyword = new KmReviewDocKeyword();
			tKeyword.setDocKeyword(tkey.getDocKeyword());
			modelKeywordList.add(tKeyword);
		}
		model.setDocKeyword(modelKeywordList);
		model.setFdLableReaders(new ArrayList(template.getFdLabelReaders()));
		model.setFdPosts(new ArrayList(template.getFdPosts()));
		model.setDocProperties(new ArrayList(template.getDocProperties()));
		model.setDocCreator(UserUtil.getUser());
		model.setDocCreateTime(new Date());
		// 增加是否使用表单模式的判断
		if (Boolean.FALSE.equals(template.getFdUseForm())
				&& Boolean.FALSE.equals(template.getFdUseWord())) {
			model.setDocContent(template.getDocContent());
		}
		model.setFdUseForm(template.getFdUseForm());
		model.setFdUseWord(template.getFdUseWord());
		model.setFdDisableMobileForm(template.getFdDisableMobileForm());
		model.setFdDepartment(UserUtil.getUser().getFdParent());
		
		// 支持移动端新建
		model.setFdIsMobileCreate(template.getFdIsMobileCreate());
		// 支持移动端审批
		model.setFdIsMobileApprove(template.getFdIsMobileApprove());
		
		// model.setExtendFilePath(XFormUtil.getFileName(template
		// .getSysFormTemplateModels(), "reviewMainDoc"));
		// 修改为新的接口

		if (Boolean.TRUE.equals(template.getFdUseForm())) {
			model.setExtendFilePath(XFormUtil.getFileName(template,
					"reviewMainDoc"));
		}
		return model;
	}

	@Override
	protected void initCoreServiceFormSetting(IExtendForm form,
			IBaseModel model, RequestContext requestContext) throws Exception {
		KmReviewMain kmReviewMain = (KmReviewMain) model;
		dispatchCoreService.initFormSetting(form, "reviewMainDoc", kmReviewMain
				.getFdTemplate(), "reviewMainDoc", requestContext);
	}

	// ********** 以下的代码为日程机制需要的代码，从业务模板同步数据到时间管理模块 开始**********
	private ISysAgendaMainCoreService sysAgendaMainCoreService = null;

	public void setSysAgendaMainCoreService(
			ISysAgendaMainCoreService sysAgendaMainCoreService) {
		this.sysAgendaMainCoreService = sysAgendaMainCoreService;
	}

	// 新增同步(针对表单公式定义器模块)
	public void addSyncDataToCalendarFormula(IBaseModel model) throws Exception {
		if ("true".equals(new KmReviewConfigNotify().getEnableSysAgenda())) {
			KmReviewMain mainModel = (KmReviewMain) model;
			SysAgendaMainContextFormula sysAgendaMainContextFormula = initSysAgendaMainContextFormula(mainModel);
			sysAgendaMainCoreService.addSyncDataToCalendar(
					sysAgendaMainContextFormula, mainModel);
		}
	}

	// 更新同步数据接口(针对表单公式定义器模块)
	public void updateSyncDataToCalendarFormula(IBaseModel model)
			throws Exception {
		if ("true".equals(new KmReviewConfigNotify().getEnableSysAgenda())) {
			KmReviewMain mainModel = (KmReviewMain) model;
			SysAgendaMainContextFormula sysAgendaMainContextFormula = initSysAgendaMainContextFormula(mainModel);
			sysAgendaMainCoreService.updateDataSyncDataToCalendar(
					sysAgendaMainContextFormula, mainModel);
		}
	}

	// 删除同步数据接口(针对表单公式定义器模块)
	public void deleteSyncDataToCalendarFormula(IBaseModel model)
			throws Exception {
		if ("true".equals(new KmReviewConfigNotify().getEnableSysAgenda())) {
			KmReviewMain mainModel = (KmReviewMain) model;
			sysAgendaMainCoreService.deleteSyncDataToCalendar(mainModel);
		}
	}

	// 初始化数据（针对表单公式定义器模块）
	private SysAgendaMainContextFormula initSysAgendaMainContextFormula(
			IBaseModel mainModel) {
		SysAgendaMainContextFormula sysAgendaMainContextFormula = new SysAgendaMainContextFormula();
		sysAgendaMainContextFormula
				.setDocUrl("/km/review/km_review_main/kmReviewMain.do?method=view&fdId="
						+ mainModel.getFdId());
		sysAgendaMainContextFormula
				.setCalType(SysAgendaTypeEnum.fdCalendarType.EVENT.getKey());
		sysAgendaMainContextFormula.setLunar(false);
		return sysAgendaMainContextFormula;
	}
	// ********** 以上的代码为日程机制需要的代码，从业务模板同步数据到时间管理模块 结束 **********
	
	/**
	 * 复制文档附件，用于“复制流程”功能
	 */
	@Override
	public void copyAttachment(KmReviewMainForm newForm, KmReviewMainForm oldForm) throws Exception {
		Map newMap = new HashMap();
		// 获取旧文档附件
		Map attMap = oldForm.getAttachmentForms();

		for (Object obj : attMap.keySet()) {
			Object _obj = attMap.get(obj);
			if (_obj instanceof AttachmentDetailsForm) {
				AutoArrayList newList = new AutoArrayList(SysAttMain.class);
				AttachmentDetailsForm _form = (AttachmentDetailsForm) _obj;
				List<SysAttMain> list = _form.getAttachments();
				for (SysAttMain attMain : list) {
					newList.add(sysAttMainService.clone(attMain));
				}
				// 封装新文档附件
				AttachmentDetailsForm newDetailsForm = new AttachmentDetailsForm();
				newDetailsForm.setAttachments(newList);
				newMap.put(obj, newDetailsForm);
			}
		}
		newForm.getAttachmentForms().putAll(newMap);
	}
	

	@Override
	public String getCount(String type, Boolean isDraft) throws Exception {

		String count = "0";
		HQLInfo hql = new HQLInfo();
		hql.setGettingCount(true);
		if ("draft".equals(type)) {
			String whereBlock = " kmReviewMain.docCreator.fdId=:createorId";
			if (isDraft) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" kmReviewMain.docStatus=:docStatus");
				hql.setParameter("docStatus", "10");
			}
			hql.setParameter("createorId", UserUtil.getUser().getFdId());
			hql.setWhereBlock(whereBlock);
		} else if ("approved".equals(type)) {
			LbpmUtil.buildLimitBlockForMyApproved("kmReviewMain", hql, UserUtil.getUser().getFdId());
		} else {
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
		}

		List<Long> list = this.getBaseDao().findValue(hql);
		if (list.size() > 0) {
			count = list.get(0).toString();
		} else {
			count = "0";
		}
		return count;
	}

	@Override
	public String getCount(HQLInfo hqlInfo) throws Exception {
		String count = "0";
		List<Long> list = this.getBaseDao().findValue(hqlInfo);
		if (list.size() > 0) {
			count = list.get(0).toString();
		} else {
			count = "0";
		}
		return count;
	}

	@Override
	public String addSubsideFileMainDoc(HttpServletRequest request, String fdId,
			KmsMultidocSubside subside)
			throws Exception {
		boolean defaultHandle = kmsMultidocSubsideService.isConvertByAspose();
		ISysFileConvertClientService sysFileConvertClientService = (ISysFileConvertClientService) SpringBeanUtil
				.getBean("sysFileConvertClientService");
		sysFileConvertClientService.refreshClients(false);
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("converterFullKey = :converterFullKey and avail = :avail ");
		hqlInfo.setParameter("converterFullKey", "toPDF-Aspose");
		hqlInfo.setParameter("avail", Boolean.TRUE);
		SysFileConvertClient findClients= (SysFileConvertClient) sysFileConvertClientService.findFirstOne(hqlInfo);
		boolean toPdfAlive = findClients != null ;
		KmReviewMain mainModel = (KmReviewMain) findByPrimaryKey(fdId);
		KmReviewTemplate tempalte = mainModel.getFdTemplate();
		List list = kmsMultidocSubsideService.getCoreModels(tempalte, null);
		KmsMultidocKnowledge kmsMultidocKnowledge = new KmsMultidocKnowledge();
		kmsMultidocKnowledge.setSubModelId(fdId);
		kmsMultidocKnowledge.setSubModelName(
				"com.landray.kmss.km.review.model.KmReviewMain");
		if (subside != null) {
			if (list.size() > 0 && list != null) {
				KmsMultidocSubside kmsMultidocSubside = (KmsMultidocSubside) list.get(0);
				subside.setDocSubjectMapping(kmsMultidocSubside.getDocSubjectMapping());
				subside.setDocCreatorMapping(kmsMultidocSubside.getDocCreatorMapping());
			}
			kmsMultidocSubsideService.addFileField(kmsMultidocKnowledge,
					subside, mainModel);
			String url = "/km/review/km_review_main/kmReviewMain.do?method=printSubsideDoc&fdId="
					+ mainModel.getFdId() + "&s_xform=default&fdSaveApproval="
					+ subside.getFdSaveApproval();
			String fileName = mainModel.getDocSubject() + ".html";
			if (defaultHandle) {
				//附加html文件到知识文档库主文档
				addFilePrintPage(kmsMultidocKnowledge,request, url, fileName,toPdfAlive);
				//附加附件到知识文档库主文档
				addFileAttachement(kmsMultidocKnowledge,mainModel,toPdfAlive);
			}else{
				//附加html文件到知识文档库主文档
				addFilePrintPageNew(kmsMultidocKnowledge, request, url, fileName);
				//附加附件到知识文档库主文档
				addFileAttachementNew(kmsMultidocKnowledge, mainModel);
			}
			kmsMultidocSubsideService.addSubside(kmsMultidocKnowledge, request);
			update(mainModel);
			//加入当前文档的附件到pdf转换队列
			if (defaultHandle) {
				if (toPdfAlive) {
					kmsMultidocSubsideService.addToPdfConventerQueen(kmsMultidocKnowledge, request);
				}
			} else {
				kmsMultidocSubsideService.addToConventerQueen(kmsMultidocKnowledge, request);
			}
			return kmsMultidocKnowledge.getFdId();
		} else {
			if (list.size() > 0 && list != null) {
				KmsMultidocSubside kmsMultidocSubside = (KmsMultidocSubside) list.get(0);
				kmsMultidocSubsideService.addFileField(kmsMultidocKnowledge,
						kmsMultidocSubside, mainModel);
				String url = "/km/review/km_review_main/kmReviewMain.do?method=printSubsideDoc&fdId="
						+ mainModel.getFdId();
				/**
				 * 自动沉淀逻辑
				 */
				String signKey = MD5Util.getMD5String(fdId);
				KmssCache cache = new KmssCache(KmsMultidocSubside.class);
				String signJsonStr = (String) cache.get(signKey);
				if (!StringUtil.isNull(signJsonStr)) {
					com.alibaba.fastjson.JSONObject sign = (com.alibaba.fastjson.JSONObject) JSON.parse(signJsonStr);
					sign.put("printUrl", url);
					cache.put(signKey, sign.toJSONString());
					url = "/km/review/km_review_main/kmReviewMainSubside.do?method=printSubsideDoc&fdId="+fdId;
				}
				
				String fileName = mainModel.getDocSubject() + ".html";
				if (defaultHandle) {
					//附加html文件到知识文档库主文档
					addFilePrintPage(kmsMultidocKnowledge,request, url, fileName,toPdfAlive);
					//附加附件到知识文档库主文档
					addFileAttachement(kmsMultidocKnowledge,mainModel,toPdfAlive);
				}else{
					//附加html文件到知识文档库主文档
					addFilePrintPageNew(kmsMultidocKnowledge, request, url, fileName);
					//附加附件到知识文档库主文档
					addFileAttachementNew(kmsMultidocKnowledge, mainModel);
				}
				kmsMultidocSubsideService.addSubside(kmsMultidocKnowledge, request);
				update(mainModel);
				if (kmsMultidocSubside.getCategory() != null) {
					//加入当前文档的附件到pdf转换队列
					if (defaultHandle) {
						if (toPdfAlive) {
							kmsMultidocSubsideService.addToPdfConventerQueen(kmsMultidocKnowledge, request);
						}
					} else {
						kmsMultidocSubsideService.addToConventerQueen(kmsMultidocKnowledge, request);
					}
					return kmsMultidocKnowledge.getFdId();
				} else {
					return null;
				}
			}
		}
		return null;
	}
	
	public void addFileAttachement(KmsMultidocKnowledge kmsMultidocKnowledge,
			IBaseModel mainModel, boolean toPdfAlive) throws Exception {
		List list = sysAttMainService.findAttListByModel(
				ModelUtil.getModelClassName(mainModel), mainModel.getFdId());
		if (list.size() > 0 && list != null) {
			for (int i = 0; i < list.size(); i++) {
				SysAttMain attmain = (SysAttMain) list.get(i);
				if (!isNotNeedWhenSubside(attmain)) {
					InputStream is = sysAttMainService.getInputStream(attmain);
					//1.解决同一个附件在不同主文档中沉淀时，相同文件aspose不再转换回写的问题
					//2.业务上沉淀的附件也应该和主文档隔离
					sysAttMainService.addAttachment(kmsMultidocKnowledge, toPdfAlive ? "attachment_tmp" : "attachment", IOUtils.toByteArray(is),
							attmain.getFdFileName(), "byte", true,null,false);
					IOUtils.closeQuietly(is);
				}
			}
		}

	}

	private void addFileAttachementNew(KmsMultidocKnowledge kmsMultidocKnowledge, IBaseModel mainModel) throws Exception {
		List list = sysAttMainService.findAttListByModel(ModelUtil.getModelClassName(mainModel), mainModel.getFdId());
		if (list.size() > 0 && list != null) {
			boolean toConvert = true;
			String subsideType = KmsMultidocSubsideConfig.newInstance().getSubsideType();
			if ("none".equals(subsideType)) {
				toConvert = false;
			}
			for (int i = 0; i < list.size(); i++) {
				SysAttMain attmain = (SysAttMain) list.get(i);
				if (!isNotNeedWhenSubside(attmain)) {
					InputStream is = sysAttMainService.getInputStream(attmain);
					boolean convertFlag = false;
					if (toConvert) {
						convertFlag = kmsMultidocSubsideService.checkConvertCfg(kmsMultidocKnowledge, attmain.getFdFileName());
					}
					sysAttMainService.addAttachment(kmsMultidocKnowledge, convertFlag ? "attachment_tmp" : "attachment", IOUtils.toByteArray(is),
							attmain.getFdFileName(), "byte", true, null, false);
					IOUtils.closeQuietly(is);
				}
			}
		}
	}
	
	public void addFilePrintPage(KmsMultidocKnowledge kmsMultidocKnowledge,
			HttpServletRequest request, String url, String fileName, boolean toPdfAlive)
			throws Exception {
		HtmlToMht htm = new HtmlToMht(false);
		String serverUrl = ResourceUtil.getKmssConfigString("kmss.innerUrlPrefix");
		url = serverUrl + url;
		String page = htm.getClientPage(request, url);
		page = new HtmlHandler().handlingHTMLAgain(page);
		if (StringUtil.isNotNull(page)) {
			SysAttMain sysAttMain = new SysAttMain();
			sysAttMain.setDocCreateTime(new Date());
			sysAttMain.setFdFileName(fileName);
			sysAttMain.setFdSize(Double.valueOf(page.getBytes("UTF-8").length));
			sysAttMain.setFdFileName(fileName);
			sysAttMain.setDocCreateTime(new Date());
			String contentType = FileMimeTypeUtil.getContentType(fileName);
			sysAttMain.setFdContentType(contentType);
			sysAttMain.setInputStream(new ByteArrayInputStream(page.getBytes("UTF-8")));
			sysAttMain.setFdKey(toPdfAlive?"attachment_tmp":"attachment");
			sysAttMain.setFdModelId(kmsMultidocKnowledge.getFdId());
			String modelName = ModelUtil.getModelClassName(kmsMultidocKnowledge);
			sysAttMain.setFdModelName(modelName);
			sysAttMain.setFdAttType("byte");
			sysAttMain.setFdOrder(0);
			sysAttMain.setAddQueue(false);
			sysAttMainService.add(sysAttMain);
		}
	}

	private void addFilePrintPageNew(KmsMultidocKnowledge kmsMultidocKnowledge, HttpServletRequest request, String url, String fileName) throws Exception {
		HtmlToMht htm = new HtmlToMht(false);
		String serverUrl = ResourceUtil.getKmssConfigString("kmss.innerUrlPrefix");
		url = serverUrl + url;
		String page = htm.getClientPage(request, url);
		page = new HtmlHandler().handlingHTMLAgain(page);
		if (StringUtil.isNotNull(page)) {
			SysAttMain sysAttMain = new SysAttMain();
			sysAttMain.setDocCreateTime(new Date());
			sysAttMain.setFdFileName(fileName);
			sysAttMain.setFdSize(Double.valueOf(page.getBytes("UTF-8").length));
			sysAttMain.setFdFileName(fileName);
			sysAttMain.setDocCreateTime(new Date());
			String contentType = FileMimeTypeUtil.getContentType(fileName);
			sysAttMain.setFdContentType(contentType);
			sysAttMain.setInputStream(new ByteArrayInputStream(page.getBytes("UTF-8")));
			sysAttMain.setFdKey("attachment_tmp");
			sysAttMain.setFdModelId(kmsMultidocKnowledge.getFdId());
			String modelName = ModelUtil.getModelClassName(kmsMultidocKnowledge);
			sysAttMain.setFdModelName(modelName);
			sysAttMain.setFdAttType("byte");
			sysAttMain.setFdOrder(0);
			sysAttMainService.add(sysAttMain, false, false);
		}
	}
	
	
	/**
	 * 判断是否是不需要归档的附件，一般是一些流程中的签批图片等
	 * 
	 * @param attMain
	 * @return
	 */
	private boolean isNotNeedWhenSubside(SysAttMain attMain) {
		/**
		 * sp语音，sg批注，qz签章，hw手写，不需要归档过去
		 */
		String[] noFileKeys = new String[] { "_sp", "_sg", "_qz", "_hw", "historyVersionAttachment","WPSCenterSelfAttachment"  };
		String fdKey = attMain.getFdKey();
		if (StringUtil.isNotNull(fdKey)) {
			for (String key : noFileKeys) {
				if (fdKey.endsWith(key)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Map<String, ?> listPortlet(RequestContext request) throws Exception {
		Map<String, Object> rtnMap = new HashMap<>();
		JSONArray datas = new JSONArray();// 列表形式使用
		Page page = Page.getEmptyPage();// 简单列表使用
		String dataview = request.getParameter("dataview");
		String owner = request.getParameter("owner");// 我发起的
		String status = request.getParameter("status");
		String myFlow = request.getParameter("myFlow");// 我审批的
		if("approved".equals(myFlow)){
			page = listApproved(request);
			getDetailInfo(page,request.getRequest());
		}else {
			HQLInfo hqlInfo = new HQLInfo();
			if (StringUtil.isNotNull(owner)) {
				getOwnerData(request, status, hqlInfo);
				request.setAttribute("flag", "owner");
			} else {
				getMyFlowDate(request, myFlow, hqlInfo);
			}
			hqlInfo.setGetCount(false);
			// 时间范围参数
			String scope = request.getParameter("scope");
			if (StringUtil.isNotNull(scope) && !"no".equals(scope)) {
				String block = hqlInfo.getWhereBlock();
				if ("all".equals(myFlow)) {
					hqlInfo.setWhereBlock(StringUtil.linkString(block, " and ",
							"kmReviewMain.docPublishTime > :fdStartTime"));
				} else {
					hqlInfo.setWhereBlock(StringUtil.linkString(block, " and ",
							"kmReviewMain.docCreateTime > :fdStartTime"));
				}
				hqlInfo.setParameter("fdStartTime", PortletTimeUtil
						.getDateByScope(scope));
			}
			// 门户不进行场所过滤
			if (!"all".equals(myFlow)) {
				hqlInfo.setCheckParam(
						SysAuthConstant.CheckType.AreaCheck,
						SysAuthConstant.AreaCheck.NO);
			}
			page = findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(), getModelName());
			if ("classic".equals(dataview)) {// 视图展现方式:classic(简单列表)
				List<KmReviewMain> topics = page.getList();
				for (KmReviewMain topic : topics) {
					JSONObject data = new JSONObject();
					// 主题
					data.put("text", topic.getDocSubject());
					if (request.isCloud()) {
						boolean isNew2 = "true".equals(request.getParameter("isNew2"));
						if (isNew2) {
							data.put("created", ListDataUtil.buildIinfo(topic.getDocCreateTime().getTime()));
							// 分类
							data.put("cateName", ListDataUtil.buildIinfo(null,
									topic.getFdTemplate().getFdName(),
									"/km/review/#cri.q=fdTemplate:" + topic.getFdTemplate().getFdId(),
									null, null));
							data.put("statusInfo", ListDataUtil.buildIinfo(null,
									ListDataUtil.getDocStatusString(topic.getDocStatus()),
									null, ListDataUtil.getDocStatusColor(topic.getDocStatus()), null));
						} else {
							data.put("created", topic.getDocCreateTime().getTime());
							// 分类
							data.put("cateName", topic.getFdTemplate().getFdName());
							data.put("cateHref",
									"/km/review/#cri.q=fdTemplate:"
											+ topic.getFdTemplate().getFdId());
							data.put("statusInfo", ListDataUtil
									.getDocStatusString(topic.getDocStatus()));
							data.put("statusColor", ListDataUtil
									.getDocStatusColor(topic.getDocStatus()));
						}
						data.put("creator",
								ListDataUtil.buildCreator(topic.getDocCreator()));
						if (isNew(topic, request.getParameter("isnew"))) {
							List<IconDataVO> icons = new ArrayList<>(1);
							IconDataVO icon = new IconDataVO();
							icon.setName("new");
							icon.setType("bitmap");
							icons.add(icon);
							data.put("icons", icons);
						}
					} else {
						// 创建人
						data.put("creator", topic.getDocCreator().getFdName());
						// 创建时间
						data.put("created", DateUtil.convertDateToString(topic
								.getDocCreateTime(), DateUtil.TYPE_DATE, null));
						// 分类
						data.put("catename", topic.getFdTemplate().getFdName());
						data.put("catehref",
								"/km/review/#cri.q=fdTemplate:"
										+ topic.getFdTemplate().getFdId());
					}
					StringBuffer sb = new StringBuffer();
					sb
							.append("/km/review/km_review_main/kmReviewMain.do?method=view");
					sb.append("&fdId=" + topic.getFdId());
					data.put("href", sb.toString());
					datas.add(data);
				}
			}
		}
		rtnMap.put("datas", datas);
		rtnMap.put("page", page);
		return rtnMap;
	}

	//封装参数返回页面
	private void getDetailInfo(Page page, HttpServletRequest request)
			throws Exception {
		IKmReviewTemplateService kmReviewTemplateService = (IKmReviewTemplateService) SpringBeanUtil.getBean("kmReviewTemplateService");
		ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
		ISysAuthAreaService sysAuthAreaService=(ISysAuthAreaService)SpringBeanUtil.getBean("sysAuthAreaService");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List kmReviewMains = page.getList();
		List<KmReviewMain> listMap = new ArrayList<KmReviewMain>();
		for (Object kmReviewMain : kmReviewMains) {
			KmReviewMain reviewMain = new KmReviewMain();
			Map<String, Object> newMap = new HashMap<String, Object>();
			Object [] km = (Object [])kmReviewMain;
			reviewMain.setFdId(km[0].toString());
			reviewMain.setDocSubject(km[1].toString());
			reviewMain.setFdNumber(km[2].toString());
			KmReviewTemplate kmReviewTemplate = (KmReviewTemplate) kmReviewTemplateService.findByPrimaryKey(km[3].toString());
			SysOrgPerson sysOrgPerson = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(km[5].toString());
			if(km[7] != null){
				SysAuthArea sysAuthArea = (SysAuthArea) sysAuthAreaService.findByPrimaryKey(km[7].toString());
				reviewMain.setAuthArea(sysAuthArea);
			}
			if(km[9] != null){
				reviewMain.setDocPublishTime(format.parse(km[9].toString()));
			}
			reviewMain.setFdTemplate(kmReviewTemplate);
			reviewMain.setDocCreator(sysOrgPerson);
			if(km[4] != null){
				reviewMain.setFdUseWord("1".equals(km[4].toString()));
			}
			if(km[8] != null){
				reviewMain.setDocCreateTime(format.parse(km[8].toString()));
			}
			if(km[10] != null){
				reviewMain.setFdIsFiling("1".equals(km[10].toString()));
			}
			reviewMain.setDocStatus(km[11]==null?"":km[11].toString());
			listMap.add(reviewMain);
		}
		page.setList(listMap);
		request.setAttribute("queryPage", page);
	}

	private boolean isNew(KmReviewMain mainModel, String isnew) {
		// MK-PAAS的NEW图标统一在前端呈现中处理
		// if (StringUtil.isNotNull(isnew)) {
		// int day = Integer.parseInt(isnew);
		// if (day > 0) {
		// Calendar now = Calendar.getInstance();
		// Calendar date = Calendar.getInstance();
		// date.setTime(mainModel.getDocCreateTime());
		// date.add(Calendar.DATE, day);
		// return date.after(now);
		// }
		// }
		return false;
	}

	private void getOwnerData(RequestContext request, String status,
			HQLInfo hqlInfo) throws Exception {
		String param = request.getParameter("rowsize");
		int rowsize = 6;
		if (!StringUtil.isNull(param)) {
            rowsize = Integer.parseInt(param);
        }
		String whereBlock = "";
		if ("all".equals(status)) {
			whereBlock = StringUtil.linkString(whereBlock, " AND ",
					"kmReviewMain.docCreator.fdId=:creatorId");
			hqlInfo.setParameter("creatorId", UserUtil.getUser().getFdId());
		} else {
			whereBlock = StringUtil
					.linkString(whereBlock, " AND ",
							"kmReviewMain.docStatus=:docStatus AND kmReviewMain.docCreator.fdId=:creatorId");
			hqlInfo.setParameter("docStatus", status);
			hqlInfo.setParameter("creatorId", UserUtil.getUser().getFdId());
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("kmReviewMain.docCreateTime desc");
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setPageNo(1);
		hqlInfo.setGetCount(false);
	}

	private void getMyFlowDate(RequestContext request, String myFlow,
			HQLInfo hqlInfo) throws Exception {
		String param = request.getParameter("rowsize");
		int rowsize = 6;
		if (!StringUtil.isNull(param)) {
            rowsize = Integer.parseInt(param);
        }
		String whereBlock = getTemplateString(request,hqlInfo);
		if ("approval".equals(myFlow)) {
			SysFlowUtil.buildLimitBlockForMyApproval("kmReviewMain", hqlInfo);
		} else if ("all".equals(myFlow)) {
			hqlInfo.setWhereBlock(whereBlock);
		}
		hqlInfo.setOrderBy("kmReviewMain.docCreateTime desc");
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setPageNo(1);
		hqlInfo.setGetCount(false);
	}

	// 分类ID的查询语句
	private String getTemplateString(RequestContext request,HQLInfo hqlInfo)
			throws Exception {
		String fdCategoryId = request.getParameter("fdCategoryId");
		StringBuffer whereBlock = new StringBuffer();
		if (StringUtil.isNotNull(fdCategoryId)) {
			// 选择的分类
			String templateProperty = "kmReviewMain.fdTemplate";
			whereBlock.append(CategoryUtil.buildChildrenWhereBlock(
					fdCategoryId, null, templateProperty,hqlInfo));
		} else {
			whereBlock.append("1=1 ");
		}
		return whereBlock.toString();
	}

	@Override
	public List<Map<String, String>> getRecentTemplate(int count)
			throws Exception {
		List<Map<String, String>> retVal = new ArrayList<Map<String, String>>();
		HQLInfo info = new HQLInfo();
		info.setSelectBlock(
				"kmReviewMain.fdTemplate.fdId,kmReviewMain.fdTemplate.fdName");
		info.setWhereBlock("kmReviewMain.docCreator.fdId=:userId");
		info.setOrderBy("kmReviewMain.docCreateTime desc");
		info.setParameter("userId", UserUtil.getUser().getFdId());
		// 避免重复的流程，只获取指定条目3倍的数量来获取
		info.setRowSize(count * 3);
		List templates = findValue(info);
		if (templates != null) {
			int size = 0;
			Map<String, Integer> addedTemplate = new HashMap<String, Integer>();
			for (Object template : templates) {
				Object[] tmp = (Object[]) template;
				if (!addedTemplate.containsKey(tmp[0])) {
					// 逐个添加最近使用模板列表
					Map<String, String> data = new HashMap<String, String>();
					data.put("id", (String) tmp[0]);
					data.put("name", (String) tmp[1]);
					retVal.add(data);
					addedTemplate.put((String) tmp[0], 1);
					size++;
					if (size >= count) {
						// 超出限定条目，则结束查找
						break;
					}
				}
			}
		}
		return retVal;
	}
	
	/**
	 * 将节点特权人增加为文档增加可阅读者
	 * @param mainModel
	 */
	private void addNodePriviledges(IBaseModel mainModel) {
		Map<String, SysOrgElement> allReadersMap = new HashMap<>();
		List<SysOrgElement> allNodePriviledgers = new ArrayList<SysOrgElement>();

		ProcessExecuteService processExecuteService = (ProcessExecuteService) SpringBeanUtil.getBean("lbpmProcessExecuteService");
		AccessManager accessManager = (AccessManager) SpringBeanUtil.getBean("accessManager");
		ProcessInstanceInfo processInfo = processExecuteService.load(mainModel.getFdId());
		List<?> currentNodeInfos = processInfo.getCurrentNodeInfos();
		// 查找节点特权人org类型
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("processDefId", processInfo.getProcessDefinitionInfo().getDefinition().getFdId());
		params.put("attribute", "nodePrivilegeIds");
		List<LbpmNodeDefinitionHandler> handlers = accessManager.find("LbpmNodeDefinitionHandler.findByAttributeAndDefinitionId", params);
		List<LbpmNodeDefinition> nodeDefinitions = accessManager.find("LbpmNodeDefinition.findDefinitionsByDefinitionId", processInfo.getProcessDefinitionInfo().getDefinition().getFdId());
		for (LbpmNodeDefinitionHandler h : handlers) {
			if (!allReadersMap.containsKey(h.getFdHandler().getFdId())) {
				allReadersMap.put(h.getFdHandler().getFdId(), h.getFdHandler());
			}
		}

		// 查找节点特权人formula类型
		for (LbpmNodeDefinition nodeDefinition : nodeDefinitions) {
			Document nodeDocument = XMLUtils.parse(nodeDefinition.getFdContent());
			Node rootNode = nodeDocument.getFirstChild();
			if (!"signNode".equals(rootNode.getNodeName()) && !"reviewNode".equals(rootNode.getNodeName())) {
				continue;
			}
			String modifyNodeHandler = XMLUtils.getAttrValue(rootNode, "modifyNodeHandler");
			String urgeHandler = XMLUtils.getAttrValue(rootNode, "urgeHandler");
			String nodePrivilegeIds = XMLUtils.getAttrValue(rootNode, "nodePrivilegeIds");
			String selectType = XMLUtils.getAttrValue(rootNode, "nodePrivilegeSelectType");

			if (StringUtil.isNotNull(nodePrivilegeIds) && "formula".equals(selectType)) {
				try {
					Class modelClass = com.landray.kmss.util.ClassUtils.forName(processInfo.getProcessInstance().getFdModelName());
					IBaseModel model = accessManager.get(modelClass, processInfo.getProcessInstance().getFdModelId());
					ProcessServiceManager processServiceManager = (ProcessServiceManager) SpringBeanUtil
							.getBean("lbpmProcessServiceManager");
					IRuleProvider ruleProvider = processServiceManager.getRuleService().getRuleProvider(new NoExecutionEnvironment(mainModel));
					// 追加解析器
					ruleProvider.addRuleParser(LbpmFunction.class.getName());
					// 规则事实参数
					RuleFact fact = new RuleFact(mainModel);
					fact.setScript(nodePrivilegeIds);
					fact.setReturnType(SysOrgElement.class.getName() + "[]");
					List<SysOrgElement> results = (List<SysOrgElement>) ruleProvider.executeRules(fact);
					if (results != null && results.size() > 0) {
						for (SysOrgElement result : results) {
							if (!allReadersMap.containsKey(result.getFdId())) {
								allReadersMap.put(result.getFdId(), result);
							}
						}
					}
				} catch (Exception e) {
					logger.error("公式解析节点特权人处理人出错", e);
				}
			}
		}

		for (Object readerObj : ((KmReviewMain)mainModel).getAuthReaders()) {
			SysOrgElement reader = (SysOrgElement) readerObj;
			if (!allReadersMap.containsKey(reader.getFdId())) {
				allReadersMap.put(reader.getFdId(), reader);
			}
		}

		((KmReviewMain)mainModel).setAuthReaders(new ArrayList(allReadersMap.values()));
	}

	@Override
	public SQLInfo buildFindPageSQLInfo(HttpServletRequest request) throws Exception {
		return new KmReviewCategoryCountTreeBuilder().buildFindPageSQLInfo(request);
	}

	@Override
	public List countAllStatus(HQLInfo hqlInfo) throws Exception {
		IKmReviewMainDao kmReviewMainDao = (IKmReviewMainDao) this.getBaseDao();
		return kmReviewMainDao.countAllStatus(hqlInfo);
	}

	@Override
	public Page listArrival(RequestContext requestContext) throws Exception {
		Page page = new Page();
		String s_pageno = requestContext.getParameter("pageno");
		String s_rowsize = requestContext.getParameter("rowsize");
		String s_pagingtype = requestContext.getParameter("pagingtype");
		String pagingSetting = requestContext.getParameter("pagingSetting");
		int pageno = 0;
		int rowsize = SysConfigParameters.getRowSize();
		if (s_pageno != null && s_pageno.length() > 0) {
			pageno = Integer.parseInt(s_pageno);
		}
		if (s_rowsize != null && s_rowsize.length() > 0) {
			rowsize = Integer.parseInt(s_rowsize);
		}
		//兼容后台展示设置的列表分页开关
		if("1".equals(pagingSetting)){
			page = defaultPageSetting(requestContext,page,pageno,rowsize);
		}else if("2".equals(pagingSetting)){
			page = simplePageSetting(requestContext,page,pageno,rowsize);
		}else{
			if("default".equals(s_pagingtype) || StringUtil.isNull(s_pagingtype)){
				page = defaultPageSetting(requestContext,page,pageno,rowsize);
			}else {
				page = simplePageSetting(requestContext,page,pageno,rowsize);
			}
		}

		return page;
	}

	/**
	*实现功能描述:待我审默认分页方式的封装
	*@param [requestContext, page, pageno, rowsize]
	*@return com.sunbor.web.tag.Page
	*/
	private Page defaultPageSetting(RequestContext requestContext,Page page,int pageno,int rowsize){
		int total = getArrivalListUseTotal(requestContext);// totalRows
		if (total > 0) {
			page.setRowsize(rowsize);
			page.setPageno(pageno);
			page.setTotalrows(total);
			page.excecute();
			Query sqlQuery = getArrivalUseNativeQuery(requestContext);// 分页查询sql
			sqlQuery.setFirstResult(page.getStart());
			sqlQuery.setMaxResults(page.getRowsize());
			List list = sqlQuery.list();
			page.setList(list);
		} else {
			page = Page.getEmptyPage();
		}
		return page;
	}

	/**
	*实现功能描述:待我审简单分页方式的封装
	*@param [requestContext, page, pageno, rowsize]
	*@return com.sunbor.web.tag.Page
	*/
	private Page simplePageSetting(RequestContext requestContext,Page page,int pageno,int rowsize){
		if (pageno == 0) {
			pageno = 1;
		}
		page.setRowsize(rowsize);
		page.setPageno(pageno);
		Query sqlQuery = getArrivalUseNativeQuery(requestContext);// 分页查询sql
		sqlQuery.setFirstResult(rowsize * (pageno - 1));
		sqlQuery.setMaxResults(rowsize + 1);
		List list = sqlQuery.list();
		page.setTotalrows(list.size() + rowsize * (pageno - 1));
		page.excecute();
		if (list.size() > rowsize) {
			list.remove(rowsize);
		}
		page.setList(list);
		return page;
	}

	/**
	 *实现功能描述:我已审默认分页方式的封装
	 *@param [requestContext, page, pageno, rowsize]
	 *@return com.sunbor.web.tag.Page
	 */
	private Page approvedDefaultPageSetting(RequestContext requestContext,Page page,int pageno,int rowsize){
		int total = getApprovedTotal(requestContext);// totalRows
		if (total > 0) {
			page.setRowsize(rowsize);
			page.setPageno(pageno);
			page.setTotalrows(total);
			page.excecute();
			Query sqlQuery = getApprovedQuery(requestContext);// 分页查询sql
			sqlQuery.setFirstResult(page.getStart());
			sqlQuery.setMaxResults(page.getRowsize());
			page.setList(sqlQuery.list());
		} else {
			page = Page.getEmptyPage();
		}
		return page;
	}

	/**
	 *实现功能描述:我已审简单分页方式的封装
	 *@param [requestContext, page, pageno, rowsize]
	 *@return com.sunbor.web.tag.Page
	 */
	private Page approvedSimplePageSetting(RequestContext requestContext,Page page,int pageno,int rowsize){
		if (pageno == 0) {
			pageno = 1;
		}
		page.setRowsize(rowsize);
		page.setPageno(pageno);
		Query sqlQuery = getApprovedQuery(requestContext);// 分页查询sql
		sqlQuery.setFirstResult(rowsize * (pageno - 1));
		sqlQuery.setMaxResults(rowsize + 1);
		List list = sqlQuery.list();
		page.setTotalrows(list.size() + rowsize * (pageno - 1));
		page.excecute();
		if (list.size() > rowsize) {
			list.remove(rowsize);
		}
		page.setList(list);
		return page;
	}

	/**
	 * 获取待我审列表total
	 */
	@Override
	public int getArrivalListUseTotal(RequestContext requestContext) {
		CriteriaValue cv = new CriteriaValue(requestContext.getRequest());
		String [] fdCreateTime = cv.polls("docCreateTime");
		String [] reviewTime = cv.polls("arrivalTime");
		String [] docPublishTime = cv.polls("docPublishTime");
		String approvalStartDate = requestContext.getParameter("approvalStartDate");
		String approvalEndDate = requestContext.getParameter("approvalEndDate");
		String sql = arrivalSqlTotal(requestContext);
		Query sqlQuery = getBaseDao().getHibernateSession().createSQLQuery(sql);
		try {
			sqlQuery.setParameter("orgIds",UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
			if(fdCreateTime != null){
				Map<String, SimpleDateFormat> createMap = checkListByTime(fdCreateTime);
				if(!"".equals(fdCreateTime[0])){
					sqlQuery.setParameter("fdStartDate",DateUtil.formatStartDate(createMap.get("sdf").parse(fdCreateTime[0])));
				}
				if(!"".equals(fdCreateTime[1])){
					sqlQuery.setParameter("fdEndDate",DateUtil.formatEndDate(createMap.get("sim").parse(fdCreateTime[1])));
				}
			}
			if(docPublishTime != null){
				Map<String,SimpleDateFormat> createMap = checkListByTime(docPublishTime);
				if(!"".equals(docPublishTime[0])){
					sqlQuery.setParameter("fdStartDate",DateUtil.formatStartDate(createMap.get("sdf").parse(docPublishTime[0])));
				}
				if(!"".equals(docPublishTime[1])){
					sqlQuery.setParameter("fdEndDate",DateUtil.formatEndDate(createMap.get("sim").parse(docPublishTime[1])));
				}
			}
			if(reviewTime != null){
				Map<String,SimpleDateFormat> reviewMap = checkListByTime(reviewTime);
				if(!"".equals(reviewTime[0])) {
					sqlQuery.setParameter("fdReviewStartDate", DateUtil.formatStartDate(reviewMap.get("sdf").parse(reviewTime[0])));
				}
				if(!"".equals(reviewTime[1])) {
					sqlQuery.setParameter("fdReviewEndDate", DateUtil.formatEndDate(reviewMap.get("sim").parse(reviewTime[1])));
				}
			}
			if(approvalStartDate != null && approvalEndDate != null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(!"".equals(approvalStartDate)) {
					sqlQuery.setParameter("approvalStartDate", DateUtil.formatStartDate(format.parse(approvalStartDate)));
				}
				if(!"".equals(approvalEndDate)) {
					sqlQuery.setParameter("approvalEndDate", DateUtil.formatEndDate(format.parse(approvalEndDate)));
				}
			}
		} catch (ParseException e) {
			logger.error("sql时间转换错误，请排查：",e.getMessage());
		}
		int totalCount = Integer.valueOf((sqlQuery.uniqueResult()).toString());
		return totalCount;
	}

	//待我审统计 sql语句拼接
	private String arrivalSqlTotal(RequestContext requestContext){
		String userid = UserUtil.getUser().getFdId();
		CriteriaValue cv = new CriteriaValue(requestContext.getRequest());
		String fdTemplateId = requestContext.getParameter("q.fdTemplate");
		String fdNumber = requestContext.getParameter("q.fdNumber");
		String fdCreatorId = requestContext.getParameter("q.docCreator");
		String fdCurrentHandlerId = requestContext.getParameter("q.fdCurrentHandler");
		String fdAlreadyHandlerId = requestContext.getParameter("q.fdAlreadyHandler");
		String fdDepartmentId = requestContext.getParameter("q.fdDepartment");
		String docSubject = requestContext.getParameter("q.docSubject");
		String [] fdCreateTime = cv.polls("docCreateTime");
		String [] reviewTime = cv.polls("arrivalTime");
		String [] docPublishTime = cv.polls("docPublishTime");
		String docProperties = requestContext.getParameter("q.docProperties");
		String fdIsFile = requestContext.getParameter("q.fdIsFile");
		String approvalStartDate = requestContext.getParameter("approvalStartDate");
		String approvalEndDate = requestContext.getParameter("approvalEndDate");
		StringBuffer buffer = new StringBuffer("select count(1) from (");
		try {

			buffer.append(" select distinct(us.fdId) from ");
			buffer.append("(select krm.fd_id as fdId,krm.fd_template_id as fdTemplateId,krm.fd_department_id as fdDepartmentId, krm.fd_number as fdNumber,");
			buffer.append("krm.doc_creator_id as docCreatorId,t1.fd_expected_id as fdExpectedId,krm.doc_subject as docSubject,");
			buffer.append("krm.doc_create_time as docCreateTime,krm.doc_publish_time as docPublishTime,t1.fd_start_date as fdApprovalTime,");
			buffer.append("krm.fd_is_filing as fdIsFiling");
			buffer.append(" from km_review_main krm ");
			buffer.append(" inner join lbpm_workitem t1 on t1.fd_process_id = krm.fd_id");
			buffer.append(" inner join lbpm_expecter_log lel on lel.fd_process_id = krm.fd_id");
			buffer.append(" where t1.fd_activity_type <> 'draftWorkitem'");
			buffer.append(" and t1.fd_status not in ('40','51')");
			buffer.append(" and krm.doc_status <>'10'");
			buffer.append(" and lel.fd_task_id = t1.fd_id");
			buffer.append(" and lel.fd_is_active = 1");
			buffer.append(" and lel.fd_handler_id in (:orgIds)");
			if(approvalStartDate != null && approvalEndDate != null){
				buffer.append(" and t1.fd_start_date between :approvalStartDate and :approvalEndDate");
			}
			buffer.append(") us");
			if (StringUtil.isNull(fdTemplateId)) {
				fdTemplateId = requestContext.getParameter("categoryId");
			}
			if (StringUtil.isNotNull(fdTemplateId)) {
				SysCategoryMain category = (SysCategoryMain) sysCategoryMainService.findByPrimaryKey(fdTemplateId, null, true);
				if (category != null) {
					buffer.append(" inner join km_review_template krt on us.fdTemplateId = krt.fd_id");
					buffer.append(" inner join sys_category_main scm on krt.fd_category_id = scm.fd_id");
				} else {
					buffer.append(" inner join km_review_template krt on us.fdTemplateId = krt.fd_id");
				}
			}
			if(fdDepartmentId != null){
				buffer.append(" inner join sys_org_element soe on us.fdDepartmentId = soe.fd_id");
			}
			if(fdAlreadyHandlerId != null){
				buffer.append(" inner join lbpm_history_workitem lhw on lhw.fd_process_id = us.fdId");
			}
			if(docProperties != null){
				buffer.append(" inner join km_review_main_property krmp on krmp.fd_doc_id = us.fdId");
			}
			buffer.append(" where 1=1 ");
			if (StringUtil.isNotNull(fdTemplateId)) {
				SysCategoryMain category = (SysCategoryMain) sysCategoryMainService.findByPrimaryKey(fdTemplateId, null, true);
				if (category != null) {
					buffer.append(" and scm.fd_hierarchy_id like '"+category.getFdHierarchyId()+"%'");
				} else {
					buffer.append(" and us.fdTemplateId = '"+fdTemplateId+"'");
				}
			}
			if(fdNumber != null){
				buffer.append(" and us.fdNumber like '%"+fdNumber+"%'");
			}
			if(fdCreatorId != null){
				buffer.append(" and us.docCreatorId = '"+fdCreatorId+"'");
			}
			if(fdCurrentHandlerId != null){
				buffer.append(" and us.fdExpectedId = '"+fdCurrentHandlerId+"'");
			}
			if(fdAlreadyHandlerId != null){
				buffer.append(" and lhw.fd_handler_id = '"+fdAlreadyHandlerId+"'");
			}
			if(docSubject != null){
				buffer.append(" and us.docSubject like '%"+docSubject+"%'");
			}
			if(fdDepartmentId != null){
				buffer.append(" and soe.fd_hierarchy_id like '%"+fdDepartmentId+"%'");
			}
			if(docProperties != null){
				buffer.append(" and krmp.fd_property_id = '"+docProperties+"'");
			}
			if(fdCreateTime != null && (!"".equals(fdCreateTime[0]) || !"".equals(fdCreateTime[1]))){
				if("".equals(fdCreateTime[0]) && !"".equals(fdCreateTime[1])){
					buffer.append(" and us.docCreateTime <= :fdEndDate");
				}else if (!"".equals(fdCreateTime[0]) && "".equals(fdCreateTime[1])) {
					buffer.append(" and us.docCreateTime >= :fdStartDate");
				}else {
					buffer.append(" and us.docCreateTime between :fdStartDate and :fdEndDate");
				}
			}
			if(docPublishTime != null && (!"".equals(docPublishTime[0]) || !"".equals(docPublishTime[1]))){
				if("".equals(docPublishTime[0]) && !"".equals(docPublishTime[1])){
					buffer.append(" and us.docPublishTime <= :fdEndDate");
				}else if (!"".equals(docPublishTime[0]) && "".equals(docPublishTime[1])) {
					buffer.append(" and us.docPublishTime >= :fdStartDate");
				}else {
					buffer.append(" and us.docPublishTime between :fdStartDate and :fdEndDate");
				}
			}
			if(reviewTime != null && (!"".equals(reviewTime[0]) || !"".equals(reviewTime[1]))){
				if("".equals(reviewTime[0]) && !"".equals(reviewTime[1])){
					buffer.append(" and us.fdApprovalTime <= :fdReviewEndDate");
				}else if (!"".equals(reviewTime[0]) && "".equals(reviewTime[1])) {
					buffer.append(" and us.fdApprovalTime >= :fdReviewStartDate");
				}else {
					buffer.append(" and us.fdApprovalTime between :fdReviewStartDate and :fdReviewEndDate");
				}
			}
			if(fdIsFile != null){
				buffer.append(" and us.fdIsFiling = '"+fdIsFile+"'");
			}
			buffer.append(" ) us1");
		} catch (Exception e) {
			logger.error("分类查询失败：",e.getMessage());
		}
		return buffer.toString();
	}

	/**
	 * 获取待我审列表查询语句
	 */
	private Query getArrivalUseNativeQuery(RequestContext requestContext) {
		CriteriaValue cv = new CriteriaValue(requestContext.getRequest());
		String [] fdCreateTime = cv.polls("docCreateTime");
		String [] reviewTime = cv.polls("arrivalTime");
		String [] docPublishTime = cv.polls("docPublishTime");

		String sql = arrivalSqlInfo(requestContext);
		Query sqlQuery = getBaseDao().getHibernateSession().createSQLQuery(sql);
		try {
			sqlQuery.setParameter("orgIds",UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
			if(fdCreateTime != null){
				Map<String,SimpleDateFormat> createMap = checkListByTime(fdCreateTime);
				if(!"".equals(fdCreateTime[0])){
					sqlQuery.setParameter("fdStartDate",DateUtil.formatStartDate(createMap.get("sdf").parse(fdCreateTime[0])));
				}
				if(!"".equals(fdCreateTime[1])){
					sqlQuery.setParameter("fdEndDate",DateUtil.formatEndDate(createMap.get("sim").parse(fdCreateTime[1])));
				}
			}
			if(docPublishTime != null){
				Map<String,SimpleDateFormat> createMap = checkListByTime(docPublishTime);
				if(!"".equals(docPublishTime[0])){
					sqlQuery.setParameter("fdStartDate",DateUtil.formatStartDate(createMap.get("sdf").parse(docPublishTime[0])));
				}
				if(!"".equals(docPublishTime[1])){
					sqlQuery.setParameter("fdEndDate",DateUtil.formatEndDate(createMap.get("sim").parse(docPublishTime[1])));
				}
			}
			if(reviewTime != null){
				Map<String,SimpleDateFormat> reviewMap = checkListByTime(reviewTime);
				if(!"".equals(reviewTime[0])) {
					sqlQuery.setParameter("fdReviewStartDate", DateUtil.formatStartDate(reviewMap.get("sdf").parse(reviewTime[0])));
				}
				if(!"".equals(reviewTime[1])) {
					sqlQuery.setParameter("fdReviewEndDate", DateUtil.formatEndDate(reviewMap.get("sim").parse(reviewTime[1])));
				}
			}
		} catch (ParseException e) {
			logger.error("sql时间转换错误，请排查：",e.getMessage());
		}
		return sqlQuery;
	}

	//待我审 sql语句拼接
	private String arrivalSqlInfo(RequestContext requestContext){
		String userid = UserUtil.getUser().getFdId();
		CriteriaValue cv = new CriteriaValue(requestContext.getRequest());
		String fdTemplateId = requestContext.getParameter("q.fdTemplate");
		String fdNumber = requestContext.getParameter("q.fdNumber");
		String fdCreatorId = requestContext.getParameter("q.docCreator");
		String fdCurrentHandlerId = requestContext.getParameter("q.fdCurrentHandler");
		String fdAlreadyHandlerId = requestContext.getParameter("q.fdAlreadyHandler");
		String fdDepartmentId = requestContext.getParameter("q.fdDepartment");
		String docSubject = requestContext.getParameter("q.docSubject");
		String [] fdCreateTime = cv.polls("docCreateTime");
		String [] reviewTime = cv.polls("arrivalTime");
		String [] docPublishTime = cv.polls("docPublishTime");
		String docProperties = requestContext.getParameter("q.docProperties");
		String fdIsFile = requestContext.getParameter("q.fdIsFile");
		String orderby = requestContext.getParameter("orderby");
		String ordertype = requestContext.getParameter("ordertype");
		String seq = requestContext.getParameter("__seq");


		StringBuffer buffer = new StringBuffer("select fdId,docSubject,fdNumber,fdTemplateId,fdUseWord,docCreatorId,docCreator,authAreaId,docCreateTime,docPublishTime,fdIsFiling,docStatus,min(arrivalTime) as arrTime from ");
		try {
			buffer.append("(select krm.fd_id as fdId,krm.doc_subject as docSubject,krm.fd_number as fdNumber,krm.fd_template_id as fdTemplateId,krm.fd_department_id as fdDepartmentId,");
			buffer.append("krm.fd_use_word as fdUseWord,krm.doc_creator_id as docCreatorId,s.fd_name as docCreator,t1.fd_expected_id as fdExpectedId,krm.auth_area_id as authAreaId,krm.doc_create_time as docCreateTime,");
			buffer.append("krm.doc_publish_time as docPublishTime,krm.fd_is_filing as fdIsFiling,krm.doc_status as docStatus,t1.fd_start_date as arrivalTime");
			buffer.append(" from km_review_main krm");
			buffer.append(" inner join lbpm_workitem t1 on t1.fd_process_id = krm.fd_id");
			buffer.append(" inner join sys_org_element s on krm.doc_creator_id = s.fd_id");
			buffer.append(" inner join lbpm_expecter_log lel on lel.fd_process_id = krm.fd_id");
			buffer.append(" where t1.fd_activity_type <> 'draftWorkitem'");
			buffer.append(" and t1.fd_status not in ('40','51')");
			buffer.append(" and krm.doc_status <>'10'");
			buffer.append(" and lel.fd_task_id = t1.fd_id");
			buffer.append(" and lel.fd_is_active = 1");
			buffer.append(" and lel.fd_handler_id in (:orgIds)");
			buffer.append(" ) us");

			if (StringUtil.isNull(fdTemplateId)) {
				fdTemplateId = requestContext.getParameter("categoryId");
			}
			if (StringUtil.isNotNull(fdTemplateId)) {
				SysCategoryMain category = (SysCategoryMain) sysCategoryMainService.findByPrimaryKey(fdTemplateId, null, true);
				if (category != null) {
					buffer.append(" inner join km_review_template krt on us.fdTemplateId = krt.fd_id");
					buffer.append(" inner join sys_category_main scm on krt.fd_category_id = scm.fd_id");
				} else {
					buffer.append(" inner join km_review_template krt on us.fdTemplateId = krt.fd_id");
				}
			}
			if(fdDepartmentId != null){
				buffer.append(" inner join sys_org_element soe on us.fdDepartmentId = soe.fd_id");
			}
			if(fdAlreadyHandlerId != null){
				buffer.append(" inner join lbpm_history_workitem lhw on lhw.fd_process_id = us.fdId");
			}
			if(docProperties != null){
				buffer.append(" inner join km_review_main_property krmp on krmp.fd_doc_id = us.fdId");
			}
			buffer.append(" where 1=1 ");
			if (StringUtil.isNotNull(fdTemplateId)) {
				SysCategoryMain category = null;

				category = (SysCategoryMain) sysCategoryMainService.findByPrimaryKey(fdTemplateId, null, true);
				if (category != null) {
					buffer.append(" and scm.fd_hierarchy_id like '"+category.getFdHierarchyId()+"%'");
				} else {
					buffer.append(" and us.fdTemplateId = '"+fdTemplateId+"'");
				}
			}
			if(fdNumber != null){
				buffer.append(" and us.fdNumber like '%"+fdNumber+"%'");
			}
			if(fdCreatorId != null){
				buffer.append(" and us.docCreatorId = '"+fdCreatorId+"'");
			}
			if(fdCurrentHandlerId != null){
				buffer.append(" and us.fdExpectedId = '"+fdCurrentHandlerId+"'");
			}
			if(fdAlreadyHandlerId != null){
				buffer.append(" and lhw.fd_handler_id = '"+fdAlreadyHandlerId+"'");
			}
			if(docSubject != null){
				buffer.append(" and us.docSubject like '%"+docSubject+"%'");
			}
			if(fdDepartmentId != null){
				buffer.append(" and soe.fd_hierarchy_id like '%"+fdDepartmentId+"%'");
			}
			if(docProperties != null){
				buffer.append(" and krmp.fd_property_id = '"+docProperties+"'");
			}
			if(fdCreateTime != null && (!"".equals(fdCreateTime[0]) || !"".equals(fdCreateTime[1]))){
				if("".equals(fdCreateTime[0]) && !"".equals(fdCreateTime[1])){
					buffer.append(" and us.docCreateTime <= :fdEndDate");
				}else if (!"".equals(fdCreateTime[0]) && "".equals(fdCreateTime[1])) {
					buffer.append(" and us.docCreateTime >= :fdStartDate");
				}else {
					buffer.append(" and us.docCreateTime between :fdStartDate and :fdEndDate");
				}
			}
			if(docPublishTime != null && (!"".equals(docPublishTime[0]) || !"".equals(docPublishTime[1]))){
				if("".equals(docPublishTime[0]) && !"".equals(docPublishTime[1])){
					buffer.append(" and us.docPublishTime <= :fdEndDate");
				}else if (!"".equals(docPublishTime[0]) && "".equals(docPublishTime[1])) {
					buffer.append(" and us.docPublishTime >= :fdStartDate");
				}else {
					buffer.append(" and us.docPublishTime between :fdStartDate and :fdEndDate");
				}
			}
			if(reviewTime != null && (!"".equals(reviewTime[0]) || !"".equals(reviewTime[1]))){
				if("".equals(reviewTime[0]) && !"".equals(reviewTime[1])){
					buffer.append(" and us.arrivalTime <= :fdReviewEndDate");
				}else if (!"".equals(reviewTime[0]) && "".equals(reviewTime[1])) {
					buffer.append(" and us.arrivalTime >= :fdReviewStartDate");
				}else {
					buffer.append(" and us.arrivalTime between :fdReviewStartDate and :fdReviewEndDate");
				}
			}
			if(fdIsFile != null){
				buffer.append(" and us.fdIsFiling = '"+fdIsFile+"'");
			}

			buffer.append(" group by us.fdId,us.docSubject,us.fdNumber,us.fdTemplateId,us.fdUseWord,us.docCreatorId,us.docCreator,us.authAreaId,us.docCreateTime,us.docPublishTime,us.fdIsFiling,us.docStatus ");
			//首次切换进入待我审页排序按照送审时间倒序排序
			if(StringUtil.isNotNull(seq)){
				buffer.append(" order by min(us.arrivalTime) desc");
			}else{
				if("arrivalTime".equals(orderby)){
					buffer.append(" order by min(us.arrivalTime)");
				}
				if("docCreateTime".equals(orderby)){
					buffer.append(" order by us.docCreateTime");
				}
				if("docPublishTime".equals(orderby)){
					buffer.append(" order by us.docPublishTime");
				}
				if(StringUtil.isNotNull(orderby) && "resolveTime".equals(orderby)){
					buffer.append(" order by min(us.arrivalTime)");
				}
				// 排序
				if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
					buffer.append(" desc");
				}
			}
		} catch (Exception e) {
			logger.error("分类查询失败：",e.getMessage());
		}
		return buffer.toString();
	}

	//处理中英文多语言切换时日期格式化报错，必须要用两个格式化的变量去转化，
	// 防止英文在有时间条件的情况下切换成中文带有英文格式的时间，变化其中一个时间，那么当前的时间条件就会是MM/dd/yyyy,yyyy-MM-dd
	//这样访问会报600错误
	private Map<String,SimpleDateFormat> checkListByTime(String[] times){
		Map<String, SimpleDateFormat> map = new HashMap<String, SimpleDateFormat>();
		if(times.length > 0) {
			SimpleDateFormat sdf = null;
			SimpleDateFormat sim = null;
			StringBuffer buffer = new StringBuffer();
			if ((!"".equals(times[0]) && times[0].contains("/"))) {
				sdf = new SimpleDateFormat("MM/dd/yyyy");
				map.put("sdf", sdf);
			} else {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				map.put("sdf", sdf);
			}
			if ((!"".equals(times[1]) && times[1].contains("/"))) {
				sim = new SimpleDateFormat("MM/dd/yyyy");
				map.put("sim", sim);
			} else {
				sim = new SimpleDateFormat("yyyy-MM-dd");
				map.put("sim", sim);
			}
		}
		return map;
	}

	@Override
	public Page listApproved(RequestContext requestContext) throws Exception {
		Page page = new Page();
		String s_pageno = requestContext.getParameter("pageno");
		String s_rowsize = requestContext.getParameter("rowsize");
		String s_pagingtype = requestContext.getParameter("pagingtype");
		String pagingSetting = requestContext.getParameter("pagingSetting");
		int pageno = 0;
		int rowsize = SysConfigParameters.getRowSize();
		if (s_pageno != null && s_pageno.length() > 0) {
			pageno = Integer.parseInt(s_pageno);
		}
		if (s_rowsize != null && s_rowsize.length() > 0) {
			rowsize = Integer.parseInt(s_rowsize);
		}
		//兼容后台展示设置的列表分页开关
		if("1".equals(pagingSetting)){
			page = approvedDefaultPageSetting(requestContext,page,pageno,rowsize);
		}else if("2".equals(pagingSetting)){
			page = approvedSimplePageSetting(requestContext,page,pageno,rowsize);
		}else{
			if("default".equals(s_pagingtype) || StringUtil.isNull(s_pagingtype)){
				page = approvedDefaultPageSetting(requestContext,page,pageno,rowsize);
			}else {
				page = approvedSimplePageSetting(requestContext,page,pageno,rowsize);
			}
		}
		return page;
	}

	/**
	 * 获取我已审列表total
	 */
	@Override
	public int getApprovedTotal(RequestContext requestContext) {
		CriteriaValue cv = new CriteriaValue(requestContext.getRequest());
		String [] fdCreateTime = cv.polls("docCreateTime");
		String [] resolveTime = cv.polls("resolveTime");
		String [] docPublishTime = cv.polls("docPublishTime");
		String approvedStartDate = requestContext.getParameter("approvedStartDate");
		String approvedEndDate = requestContext.getParameter("approvedEndDate");
		String sql = approvedSqlTotal(requestContext);
		Query sqlQuery = getBaseDao().getHibernateSession().createSQLQuery(sql);
		try {
			if(fdCreateTime != null){
				Map<String,SimpleDateFormat> createMap = checkListByTime(fdCreateTime);
				if(!"".equals(fdCreateTime[0])){
					sqlQuery.setParameter("fdStartDate",DateUtil.formatStartDate(createMap.get("sdf").parse(fdCreateTime[0])));
				}
				if(!"".equals(fdCreateTime[1])){
					sqlQuery.setParameter("fdEndDate",DateUtil.formatEndDate(createMap.get("sim").parse(fdCreateTime[1])));
				}
			}
			if(docPublishTime != null){
				Map<String,SimpleDateFormat> createMap = checkListByTime(docPublishTime);
				if(!"".equals(docPublishTime[0])){
					sqlQuery.setParameter("fdStartDate",DateUtil.formatStartDate(createMap.get("sdf").parse(docPublishTime[0])));
				}
				if(!"".equals(docPublishTime[1])){
					sqlQuery.setParameter("fdEndDate",DateUtil.formatEndDate(createMap.get("sim").parse(docPublishTime[1])));
				}
			}
			if(resolveTime != null){
				Map<String,SimpleDateFormat> reviewMap = checkListByTime(resolveTime);
				if(!"".equals(resolveTime[0])) {
					sqlQuery.setParameter("fdReviewStartDate", DateUtil.formatStartDate(reviewMap.get("sdf").parse(resolveTime[0])));
				}
				if(!"".equals(resolveTime[1])) {
					sqlQuery.setParameter("fdReviewEndDate", DateUtil.formatEndDate(reviewMap.get("sim").parse(resolveTime[1])));
				}
			}
			if(approvedStartDate != null && approvedEndDate !=null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(!"".equals(approvedStartDate)) {
					sqlQuery.setParameter("approvedStartDate", DateUtil.formatStartDate(format.parse(approvedStartDate)));
				}
				if(!"".equals(approvedEndDate)) {
					sqlQuery.setParameter("approvedEndDate", DateUtil.formatEndDate(format.parse(approvedEndDate)));
				}
			}
		} catch (ParseException e) {
			logger.error("sql时间转换错误，请排查：",e.getMessage());
		}
		int totalCount = Integer.valueOf((sqlQuery.uniqueResult()).toString());
		return totalCount;
	}

	//我已审统计 sql语句拼接
	private String approvedSqlTotal(RequestContext requestContext){
		String userid = UserUtil.getUser().getFdId();
		CriteriaValue cv = new CriteriaValue(requestContext.getRequest());
		String docSubject = requestContext.getParameter("q.docSubject");
		String fdTemplateId = requestContext.getParameter("q.fdTemplate");
		String fdNumber = requestContext.getParameter("q.fdNumber");
		String fdAlreadyHandlerId = requestContext.getParameter("q.fdAlreadyHandler");
		String fdDepartmentId = requestContext.getParameter("q.fdDepartment");
		String docCreatorId = requestContext.getParameter("q.docCreator");
		String docStatus = requestContext.getParameter("q.docStatus");
		String fdCurrentHandlerId = requestContext.getParameter("q.fdCurrentHandler");
		String [] fdCreateTime = cv.polls("docCreateTime");
		String [] resolveTime = cv.polls("resolveTime");
		String [] docPublishTime = cv.polls("docPublishTime");
		String docProperties = requestContext.getParameter("q.docProperties");
		String fdIsFile = requestContext.getParameter("q.fdIsFile");
		String approvedStartDate = requestContext.getParameter("approvedStartDate");
		String approvedEndDate = requestContext.getParameter("approvedEndDate");

		StringBuffer buffer = new StringBuffer("select count(1) from (");
		try {
			buffer.append(" select distinct(us.fdId) from ");
			buffer.append(" (select krm.fd_id as fdId,krm.doc_creator_id as docCreatorId,krm.fd_template_id as fdTemplateId,krm.fd_department_id as fdDePartmentId,");
			buffer.append(" krm.fd_number as fdNumber,his.fd_handler_id as fdHandlerId,krm.doc_subject as docSubject,");
			buffer.append(" krm.doc_status as docStatus,krm.doc_create_time as docCreateTime,krm.doc_publish_time as docPublishTime,");
			buffer.append(" his.fd_finish_date as fdApprovedTime,krm.fd_is_filing as fdIsFiling");
			buffer.append(" from km_review_main krm");
			buffer.append(" inner join lbpm_history_workitem his on krm.fd_id = his.fd_process_id");
			buffer.append(" where his.fd_activity_type <> 'draftWorkitem'");
			buffer.append(" and (his.fd_handler_id = '" + userid + "'");
			//增加判断加签临时表是否有数据，有的话则拼接条件，没有则不拼接
			NativeQuery query = this.getBaseDao().getHibernateSession().createNativeQuery("select count(1) from lbpm_workitem_assign_temp where original_assigner_id = '"+userid+"'");
			int count = Integer.valueOf((query.uniqueResult()).toString());
			if(count >0){
				buffer.append(" or exists (select tmp.fd_id from lbpm_workitem_assign_temp tmp where tmp.fd_process_id = krm.fd_id ");
				buffer.append(" and tmp.fd_assign_taskid = his.fd_id ");
				buffer.append(" and tmp.original_assigner_id = '"+userid+"')");
			}
			buffer.append(")) us ");
			if (StringUtil.isNull(fdTemplateId)) {
				fdTemplateId = requestContext.getParameter("categoryId");
			}
			SysCategoryMain category = null;
			if (StringUtil.isNotNull(fdTemplateId)) {
				category = (SysCategoryMain) sysCategoryMainService.findByPrimaryKey(fdTemplateId, null, true);
				if (category != null) {
					buffer.append(" inner join km_review_template krt on us.fdTemplateId = krt.fd_id");
					buffer.append(" inner join sys_category_main scm on krt.fd_category_id = scm.fd_id");
				} else {
					buffer.append(" inner join km_review_template krt on us.fdTemplateId = krt.fd_id");
				}
			}
			if (fdDepartmentId != null) {
				buffer.append(" inner join sys_org_element soe on us.fdDePartmentId = soe.fd_id");
			}
			if(docProperties != null){
				buffer.append(" inner join km_review_main_property krmp on krmp.fd_doc_id = us.fdId");
			}
			buffer.append(" where 1=1 ");
			if (StringUtil.isNotNull(fdTemplateId)) {
				if (category != null) {
					buffer.append(" and scm.fd_hierarchy_id like '" + category.getFdHierarchyId() + "%'");
				} else {
					buffer.append(" and us.fdTemplateId = '" + fdTemplateId + "'");
				}
			}
			if (fdNumber != null) {
				buffer.append(" and us.fdNumber like '%" + fdNumber + "%'");
			}
			if (docCreatorId != null) {
				buffer.append(" and us.docCreatorId = '" + docCreatorId + "'");
			}
			if (fdAlreadyHandlerId != null) {
				buffer.append(" and us.fdHandlerId = '" + fdAlreadyHandlerId + "'");
			}
			if (docSubject != null) {
				buffer.append(" and us.docSubject like '%" + docSubject + "%'");
			}
			if (docStatus != null) {
				buffer.append(" and us.docStatus = '" + docStatus + "'");
			}
			if (fdCurrentHandlerId != null) {
				buffer.append(" and exists (select t.fd_id from lbpm_expecter_log t where t.fd_process_id = us.fdId and t.fd_is_active = '1' and t.fd_handler_id = '" + fdCurrentHandlerId + "' )");
			}
			if (fdDepartmentId != null) {
				buffer.append(" and soe.fd_hierarchy_id like '%" + fdDepartmentId + "%'");
			}
			if(docProperties != null){
				buffer.append(" and krmp.fd_property_id = '"+docProperties+"'");
			}
			if (fdCreateTime != null && (!"".equals(fdCreateTime[0]) || !"".equals(fdCreateTime[1]))) {
				if ("".equals(fdCreateTime[0]) && !"".equals(fdCreateTime[1])) {
					buffer.append(" and us.docCreateTime <= :fdEndDate");
				} else if (!"".equals(fdCreateTime[0]) && "".equals(fdCreateTime[1])) {
					buffer.append(" and us.docCreateTime >= :fdStartDate");
				} else {
					buffer.append(" and us.docCreateTime between :fdStartDate and :fdEndDate");
				}
			}
			if(approvedStartDate != null && approvedEndDate != null){
				buffer.append("and us.docCreateTime between :approvedStartDate and :approvedEndDate");
			}
			if (docPublishTime != null && (!"".equals(docPublishTime[0]) || !"".equals(docPublishTime[1]))) {
				if ("".equals(docPublishTime[0]) && !"".equals(docPublishTime[1])) {
					buffer.append(" and us.docPublishTime <= :fdEndDate");
				} else if (!"".equals(docPublishTime[0]) && "".equals(docPublishTime[1])) {
					buffer.append(" and us.docPublishTime >= :fdStartDate");
				} else {
					buffer.append(" and us.docPublishTime between :fdStartDate and :fdEndDate");
				}
			}
			if (resolveTime != null && (!"".equals(resolveTime[0]) || !"".equals(resolveTime[1]))) {
				if ("".equals(resolveTime[0]) && !"".equals(resolveTime[1])) {
					buffer.append(" and us.fdApprovedTime <= :fdReviewEndDate");
				} else if (!"".equals(resolveTime[0]) && "".equals(resolveTime[1])) {
					buffer.append(" and us.fdApprovedTime >= :fdReviewStartDate");
				} else {
					buffer.append(" and us.fdApprovedTime between :fdReviewStartDate and :fdReviewEndDate");
				}
			}
			if (fdIsFile != null) {
				buffer.append(" and us.fdIsFiling = '" + fdIsFile + "'");
			}

			buffer.append(") us1");
		}catch (Exception e){
			logger.error("分类查询失败：",e.getMessage());
		}
		return buffer.toString();
	}

	/**
	 * 获取我已审列表查询语句
	 */
	private Query getApprovedQuery(RequestContext requestContext) {
		CriteriaValue cv = new CriteriaValue(requestContext.getRequest());
		String [] fdCreateTime = cv.polls("docCreateTime");
		String [] resolveTime = cv.polls("resolveTime");
		String [] docPublishTime = cv.polls("docPublishTime");

		String sql = approvedSqlInfo(requestContext);
		Query sqlQuery = getBaseDao().getHibernateSession().createSQLQuery(sql);
		try {
			if(fdCreateTime != null){
				Map<String,SimpleDateFormat> createMap = checkListByTime(fdCreateTime);
				if(!"".equals(fdCreateTime[0])){
					sqlQuery.setParameter("fdStartDate",DateUtil.formatStartDate(createMap.get("sdf").parse(fdCreateTime[0])));
				}
				if(!"".equals(fdCreateTime[1])){
					sqlQuery.setParameter("fdEndDate",DateUtil.formatEndDate(createMap.get("sim").parse(fdCreateTime[1])));
				}
			}
			if(docPublishTime != null){
				Map<String,SimpleDateFormat> createMap = checkListByTime(docPublishTime);
				if(!"".equals(docPublishTime[0])){
					sqlQuery.setParameter("fdStartDate",DateUtil.formatStartDate(createMap.get("sdf").parse(docPublishTime[0])));
				}
				if(!"".equals(docPublishTime[1])){
					sqlQuery.setParameter("fdEndDate",DateUtil.formatEndDate(createMap.get("sim").parse(docPublishTime[1])));
				}
			}
			if(resolveTime != null){
				Map<String,SimpleDateFormat> reviewMap = checkListByTime(resolveTime);
				if(!"".equals(resolveTime[0])) {
					sqlQuery.setParameter("fdReviewStartDate", DateUtil.formatStartDate(reviewMap.get("sdf").parse(resolveTime[0])));
				}
				if(!"".equals(resolveTime[1])) {
					sqlQuery.setParameter("fdReviewEndDate", DateUtil.formatEndDate(reviewMap.get("sim").parse(resolveTime[1])));
				}
			}
		} catch (ParseException e) {
			logger.error("sql时间转换错误，请排查：",e.getMessage());
		}
		return sqlQuery;
	}

	//我已审 sql语句拼接
	private String approvedSqlInfo(RequestContext requestContext){
		String userid = UserUtil.getUser().getFdId();
		CriteriaValue cv = new CriteriaValue(requestContext.getRequest());
		String docSubject = requestContext.getParameter("q.docSubject");
		String fdTemplateId = requestContext.getParameter("q.fdTemplate");
		String fdNumber = requestContext.getParameter("q.fdNumber");
		String fdAlreadyHandlerId = requestContext.getParameter("q.fdAlreadyHandler");
		String fdDepartmentId = requestContext.getParameter("q.fdDepartment");
		String docCreatorId = requestContext.getParameter("q.docCreator");
		String docStatus = requestContext.getParameter("q.docStatus");
		String fdCurrentHandlerId = requestContext.getParameter("q.fdCurrentHandler");
		String [] fdCreateTime = cv.polls("docCreateTime");
		String [] resolveTime = cv.polls("resolveTime");
		String [] docPublishTime = cv.polls("docPublishTime");
		String docProperties = requestContext.getParameter("q.docProperties");
		String fdIsFile = requestContext.getParameter("q.fdIsFile");
		String orderby = requestContext.getParameter("orderby");
		String ordertype = requestContext.getParameter("ordertype");
		String seq = requestContext.getParameter("__seq");

		StringBuffer buffer = new StringBuffer("select fdId,docSubject,fdNumber,fdTemplateId,fdUseWord,fdCreatorId,ele.fd_name as fdCreator,authAreaId,docCreateTime,docPublishTime,fdIsFiling,docStatus,max(resolveTime) as resoTime from ");
		try {
			buffer.append(" (select krm.fd_id as fdId,krm.doc_subject as docSubject,krm.fd_number as fdNumber,krm.fd_template_id as fdTemplateId,");
			buffer.append(" krm.fd_use_word as fdUseWord,krm.auth_area_id as authAreaId,krm.doc_creator_id as fdCreatorId,krm.doc_create_time as docCreateTime,");
			buffer.append(" krm.doc_publish_time as docPublishTime,krm.fd_is_filing as fdIsFiling,krm.doc_status as docStatus,his.fd_finish_date as resolveTime,");
			buffer.append("krm.fd_department_id as fdDepartmentId,his.fd_handler_id as fdHandlerId");
			buffer.append(" from km_review_main krm");
			buffer.append(" inner join lbpm_history_workitem his on krm.fd_id = his.fd_process_id");
			buffer.append(" where his.fd_activity_type <> 'draftWorkitem'");
			buffer.append(" and (his.fd_handler_id = '" + userid + "'");
			//增加判断加签临时表是否有数据，有的话则拼接条件，没有则不拼接
			NativeQuery query = this.getBaseDao().getHibernateSession().createNativeQuery("select count(1) from lbpm_workitem_assign_temp where original_assigner_id = '"+userid+"'");
			int count = Integer.valueOf((query.uniqueResult()).toString());
			if(count >0){
				buffer.append(" or exists (select tmp.fd_id from lbpm_workitem_assign_temp tmp where tmp.fd_process_id = krm.fd_id ");
				buffer.append(" and tmp.fd_assign_taskid = his.fd_id ");
				buffer.append(" and tmp.original_assigner_id = '"+userid+"')");
			}
			buffer.append(" )) us");
			buffer.append(" inner join sys_org_element ele on us.fdCreatorId = ele.fd_id");
			if (StringUtil.isNull(fdTemplateId)) {
				fdTemplateId = requestContext.getParameter("categoryId");
			}
			SysCategoryMain category = null;
			if (StringUtil.isNotNull(fdTemplateId)) {
				category = (SysCategoryMain) sysCategoryMainService.findByPrimaryKey(fdTemplateId, null, true);
				if (category != null) {
					buffer.append(" inner join km_review_template krt on us.fdTemplateId = krt.fd_id");
					buffer.append(" inner join sys_category_main scm on krt.fd_category_id = scm.fd_id");
				} else {
					buffer.append(" inner join km_review_template krt on us.fdTemplateId = krt.fd_id");
				}
			}
			if (fdDepartmentId != null) {
				buffer.append(" inner join sys_org_element soe on us.fdDepartmentId = soe.fd_id");
			}
			if(docProperties != null){
				buffer.append(" inner join km_review_main_property krmp on krmp.fd_doc_id = us.fdId");
			}
			buffer.append(" where 1=1 ");
			if (StringUtil.isNotNull(fdTemplateId)) {
				if (category != null) {
					buffer.append(" and scm.fd_hierarchy_id like '" + category.getFdHierarchyId() + "%'");
				} else {
					buffer.append(" and us.fdTemplateId = '" + fdTemplateId + "'");
				}
			}
			if (fdNumber != null) {
				buffer.append(" and us.fdNumber like '%" + fdNumber + "%'");
			}
			if (docCreatorId != null) {
				buffer.append(" and us.fdCreatorId = '" + docCreatorId + "'");
			}
			if (fdAlreadyHandlerId != null) {
				buffer.append(" and us.fdHandlerId = '" + fdAlreadyHandlerId + "'");
			}
			if (docSubject != null) {
				buffer.append(" and us.docSubject like '%" + docSubject + "%'");
			}
			if (docStatus != null) {
				buffer.append(" and us.docStatus = '" + docStatus + "'");
			}
			if (fdCurrentHandlerId != null) {
				buffer.append(" and exists (select t.fd_id from lbpm_expecter_log t where t.fd_process_id = us.fdId and t.fd_is_active = '1' and t.fd_handler_id = '" + fdCurrentHandlerId + "' )");
			}
			if (fdDepartmentId != null) {
				buffer.append(" and soe.fd_hierarchy_id like '%" + fdDepartmentId + "%'");
			}
			if(docProperties != null){
				buffer.append(" and krmp.fd_property_id = '"+docProperties+"'");
			}
			if (fdCreateTime != null && (!"".equals(fdCreateTime[0]) || !"".equals(fdCreateTime[1]))) {
				if ("".equals(fdCreateTime[0]) && !"".equals(fdCreateTime[1])) {
					buffer.append(" and us.docCreateTime <= :fdEndDate");
				} else if (!"".equals(fdCreateTime[0]) && "".equals(fdCreateTime[1])) {
					buffer.append(" and us.docCreateTime >= :fdStartDate");
				} else {
					buffer.append(" and us.docCreateTime between :fdStartDate and :fdEndDate");
				}
			}
			if (docPublishTime != null && (!"".equals(docPublishTime[0]) || !"".equals(docPublishTime[1]))) {
				if ("".equals(docPublishTime[0]) && !"".equals(docPublishTime[1])) {
					buffer.append(" and us.docPublishTime <= :fdEndDate");
				} else if (!"".equals(docPublishTime[0]) && "".equals(docPublishTime[1])) {
					buffer.append(" and us.docPublishTime >= :fdStartDate");
				} else {
					buffer.append(" and us.docPublishTime between :fdStartDate and :fdEndDate");
				}
			}
			if (resolveTime != null && (!"".equals(resolveTime[0]) || !"".equals(resolveTime[1]))) {
				if ("".equals(resolveTime[0]) && !"".equals(resolveTime[1])) {
					buffer.append(" and us.resolveTime <= :fdReviewEndDate");
				} else if (!"".equals(resolveTime[0]) && "".equals(resolveTime[1])) {
					buffer.append(" and us.resolveTime >= :fdReviewStartDate");
				} else {
					buffer.append(" and us.resolveTime between :fdReviewStartDate and :fdReviewEndDate");
				}
			}
			if (fdIsFile != null) {
				buffer.append(" and us.fdIsFiling = '" + fdIsFile + "'");
			}
			buffer.append(" group by fdId,docSubject,fdNumber,fdTemplateId,fdUseWord,fdCreatorId,ele.fd_name,authAreaId,docCreateTime,docPublishTime,fdIsFiling,docStatus");

			if(StringUtil.isNotNull(seq) && !"docPublishTime".equals(orderby)){
				buffer.append(" order by max(resolveTime) desc");
			}else{
				if ("resolveTime".equals(orderby)) {
					buffer.append(" order by max(resolveTime)");
				}
				if ("docCreateTime".equals(orderby)) {
					buffer.append(" order by docCreateTime");
				}
				if ("docPublishTime".equals(orderby)) {
					buffer.append(" order by us.docPublishTime");
				}
				if(StringUtil.isNotNull(orderby) && "arrivalTime".equals(orderby)){
					buffer.append(" order by max(resolveTime)");
				}
				// 排序
				if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
					buffer.append(" desc");
				}
			}
		}catch (Exception e){
			logger.error("分类查询失败：",e.getMessage());
		}
		return buffer.toString();
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtn = new ArrayList<Map<String, String>>();
		String type = requestInfo.getParameter("type");
		if("getCount".equals(type)){
			String docStatus = requestInfo.getParameter("docStatus");
			String isDraft = requestInfo.getParameter("isDraft");
			String count = getCount(docStatus, Boolean.valueOf(isDraft));
			Map<String, String> map = new HashMap<String, String>();
			map.put("count", count);
			rtn.add(map);
		}
		return rtn;
	}

	@Override
	public void addArchAutoFileModel(HttpServletRequest request, String fdId) throws Exception {
		if ("true".equals(SysArchivesConfig.newInstance().getSysArchEnabled())&& SysArchivesUtil.isStartFile("km/review")) {
			String sign = fdId.split(",")[1];
			KmReviewMain kmReviewMain = (KmReviewMain) findByPrimaryKey(fdId.split(",")[0]);
			KmReviewTemplate template = kmReviewMain.getFdTemplate();
			ISysArchivesFileTemplateService sysArchivesFileTemplateService = (ISysArchivesFileTemplateService) SpringBeanUtil.getBean("sysArchivesFileTemplateService");
			SysArchivesFileTemplate fileTemp = sysArchivesFileTemplateService.getFileTemplate(template, null);
			if (fileTemp != null) {
				SysArchivesParamModel paramModel = new SysArchivesParamModel();
				paramModel.setAuto("1");
				paramModel.setFileName(kmReviewMain.getDocSubject() + ".html");
				// 归档页面URL(若为多表单，暂时归档默认表单)
				int saveApproval = fileTemp.getFdSaveApproval() != null
						&& fileTemp.getFdSaveApproval() ? 1 : 0;
				//流程自动归档
				String fdModelId = kmReviewMain.getFdId();
				long expires = System.currentTimeMillis() + (3 * 60 * 1000);//下载链接3分钟有效
				String url = "/km/review/km_review_main/kmReviewMainArchives.do?method=printFileDocArchives&fdId="
						+ fdModelId + "&s_xform=default&saveApproval="+saveApproval + "&Signature=" + sign + "&Expires=" + expires;
				paramModel.setUrl(url);
				//设置归档人:防止归档人为匿名用户
				if(fileTemp.getFdFilePerson()==null){
					fileTemp.setFdFilePerson(kmReviewMain.getDocCreator());
				}
				sysArchivesFileTemplateService.addArchAutoFileModel(request, kmReviewMain, paramModel, fileTemp, sign);

				kmReviewMain.setFdIsFiling(true);
				super.update(kmReviewMain);
			} else {
				throw new RuntimeException("流程管理归档:请先在模板中配置归档数据!");
			}
		}
	}

	@Override
	public void addArchFileModel(HttpServletRequest request, String fdId) throws Exception {
		if ("true".equals(SysArchivesConfig.newInstance().getSysArchEnabled())&&SysArchivesUtil.isStartFile("km/review")) {
			KmReviewMain kmReviewMain = (KmReviewMain) findByPrimaryKey(fdId.split(",")[0]);
			KmReviewTemplate template = kmReviewMain.getFdTemplate();
			ISysArchivesFileTemplateService sysArchivesFileTemplateService= (ISysArchivesFileTemplateService)SpringBeanUtil.getBean("sysArchivesFileTemplateService");
			SysArchivesFileTemplate fileTemp = sysArchivesFileTemplateService.getFileTemplate(template,null);
			if(fileTemp!=null){
				SysArchivesParamModel paramModel = new SysArchivesParamModel();
				paramModel.setAuto("0");
				paramModel.setFileName(kmReviewMain.getDocSubject()+".html");
				// 归档页面URL(若为多表单，暂时归档默认表单)
				int saveApproval = fileTemp.getFdSaveApproval() != null
						&& fileTemp.getFdSaveApproval() ? 1 : 0;
				//流程自动归档
				String fdModelId = kmReviewMain.getFdId();
				String url = "/km/review/km_review_main/kmReviewMain.do?method=printFileDoc&fdId="
						+ kmReviewMain.getFdId() + "&s_xform=default&saveApproval="
						+ saveApproval;
				paramModel.setUrl(url);
				sysArchivesFileTemplateService.addArchFileModel(request,kmReviewMain,paramModel,fileTemp);

				kmReviewMain.setFdIsFiling(true);
				super.update(kmReviewMain);
			}else{
				throw new RuntimeException("流程管理归档:请先在模板中配置归档数据!");
			}
		}
	}
}
