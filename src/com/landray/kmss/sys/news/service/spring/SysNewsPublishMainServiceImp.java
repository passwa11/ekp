package com.landray.kmss.sys.news.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseCoreInnerServiceImp;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModelAdapter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.news.dao.ISysNewsMainDao;
import com.landray.kmss.sys.news.forms.SysNewsMainForm;
import com.landray.kmss.sys.news.interfaces.ISysNewsPublishMainModel;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.news.model.SysNewsPublishMain;
import com.landray.kmss.sys.news.model.SysNewsTemplate;
import com.landray.kmss.sys.news.service.ISysNewsMainService;
import com.landray.kmss.sys.news.service.ISysNewsPublishMainService;
import com.landray.kmss.sys.news.service.ISysNewsTemplateService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.recycle.event.SysRecycleHardDelEvent;
import com.landray.kmss.sys.recycle.event.SysRecycleSoftDelEvent;
import com.landray.kmss.sys.recycle.model.SysRecycleConstant;
import com.landray.kmss.sys.workflow.engine.WorkflowEngineContext;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.workflow.interfaces.ISysWfProcessSubService;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2009-八月-02
 * 
 * @author 周超 发布机制主文档业务接口实现
 */
public class SysNewsPublishMainServiceImp extends BaseCoreInnerServiceImp
		implements ISysNewsPublishMainService, ApplicationListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysNewsPublishMainServiceImp.class);
	
	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private ISysNewsTemplateService sysNewsTemplateService;

	public void setSysNewsTemplateService(
			ISysNewsTemplateService sysNewsTemplateService) {
		this.sysNewsTemplateService = sysNewsTemplateService;
	}

	private ISysNewsMainDao sysNewsMainDao;

	public void setSysNewsMainDao(ISysNewsMainDao sysNewsMainDao) {
		this.sysNewsMainDao = sysNewsMainDao;
	}

	private ISysWfProcessSubService sysWfProcessSubService = null;

	public void setSysWfProcessSubService(
			ISysWfProcessSubService sysWfProcessSubService) {
		this.sysWfProcessSubService = sysWfProcessSubService;
	}

	public IBackgroundAuthService backgroundAuthService;

	public void setBackgroundAuthService(
			IBackgroundAuthService backgroundAuthService) {
		this.backgroundAuthService = backgroundAuthService;
	}

	private ISysNewsMainService sysNewsMainService;

	public void setSysNewsMainService(ISysNewsMainService sysNewsMainService) {
		this.sysNewsMainService = sysNewsMainService;
	}

	/**
	 * 手动发布
	 */
	@Override
    public void addManuaPublish(IExtendForm form, RequestContext requestContext)
			throws Exception {

		SysNewsMainForm sysNewsMainForm = (SysNewsMainForm) form;

		// #140626 可以发布多个分类
		String cateIds = sysNewsMainForm.getFdTemplateId();
		String[] cateIdArray = cateIds.split(";");
		for (String cateId : cateIdArray) {
			addManuaPublishExecute(sysNewsMainForm, cateId);
		}
	}

	/**
	 * 手动发布执行器
	 * @param sysNewsMainForm
	 * @param cateId
	 */
	private void addManuaPublishExecute(SysNewsMainForm sysNewsMainForm, String cateId) throws Exception {
		String fdModelId = sysNewsMainForm.getFdModelId();
		String fdModelName = sysNewsMainForm.getFdModelName();
		Object modelInfo = com.landray.kmss.util.ClassUtils.forName(fdModelName);
		IBaseModel baseModel = findByPrimaryKey(fdModelId, modelInfo, false);
		SysNewsMain sysNewsMain = new SysNewsMain();
		sysNewsMain.setFdImportance(Long.valueOf(sysNewsMainForm
				.getFdImportance()));// 重要度
		sysNewsMain.setFdModelId(fdModelId);
		sysNewsMain.setFdModelName(fdModelName);
		sysNewsMain.setFdKey(sysNewsMainForm.getFdKey());
		SysNewsTemplate fdTemplate = (SysNewsTemplate) sysNewsTemplateService
				.findByPrimaryKey(cateId, null, true);// 分类
		sysNewsMain.setFdTemplate(fdTemplate); // 设置新闻的分类
		ISysNewsPublishMainModel sysNewsPublishModel = (ISysNewsPublishMainModel) baseModel;
		sysNewsMain.setDocPublishTime(new Date());// 发布时间
		sysNewsMain.setDocAlterTime(new Date());// 修改时间 by张文添 2010-8-30
		recalculateEditorField(sysNewsMain, fdTemplate);// 根据模板重新复制编辑者
		setSysNewsMainCommon(sysNewsPublishModel, sysNewsMain, false);// 新闻基本信息设置
		copyFlowFromNewsCategory(sysNewsMain, fdTemplate);// 生成新闻并加载流程
	}

	protected ISysAttMainCoreInnerService sysAttMainService;

	public ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	/**
	 * 手动发布附件新闻
	 */
	@Override
    public void addAttPublish(IExtendForm form, RequestContext requestContext)
			throws Exception {
		//String fdNotify = requestContext.getParameter("fdNotifyType");
		SysNewsMainForm sysNewsMainForm = (SysNewsMainForm) form;
		String fdModelId = sysNewsMainForm.getFdModelId();
		String fdModelName = sysNewsMainForm.getFdModelName();
		Object modelInfo = com.landray.kmss.util.ClassUtils.forName(fdModelName);
		IBaseModel baseModel = findByPrimaryKey(fdModelId, modelInfo, false);
		SysNewsMain sysNewsMain = new SysNewsMain();
		sysNewsMain.setFdImportance(Long.valueOf(sysNewsMainForm
				.getFdImportance()));// 重要度
		sysNewsMain.setFdModelId(fdModelId);
		sysNewsMain.setFdModelName(fdModelName);
		sysNewsMain.setFdKey(sysNewsMainForm.getFdKey());
		sysNewsMain.setFdNotifyType(sysNewsMainForm.getFdNotifyType());
		SysNewsTemplate fdTemplate = (SysNewsTemplate) sysNewsTemplateService
				.findByPrimaryKey(sysNewsMainForm.getFdTemplateId(), null, true);// 分类
		sysNewsMain.setFdTemplate(fdTemplate); // 设置新闻的分类
		ISysNewsPublishMainModel sysNewsPublishModel = (ISysNewsPublishMainModel) baseModel;
		sysNewsMain.setDocPublishTime(new Date());// 发布时间
		sysNewsMain.setDocAlterTime(new Date());// 修改时间 by张文添 2010-8-30
		SysAttMain sysAttMain = null;
		//正文附件key
		String contentAttKeyString = requestContext.getParameter("contentAttKey");
		if(!"editonline".equals(contentAttKeyString)){
		    sysNewsMain.setFdContentType("rtf");
		}else{
		    sysNewsMain.setFdContentType("word");
		}
		List l1 = getSysAttMainService().findByModelKey(fdModelName,
				fdModelId, contentAttKeyString);
		if(l1.size()>0){
			for (int j = 0; j < l1.size(); j++) {
				sysAttMain = getSysAttMainService()
						.clone((SysAttMain) l1.get(j));
				String fileName = sysAttMain.getFdFileName();
				sysAttMain.setFdAttType("byte");
				sysAttMain.setFdModelId(sysNewsMain.getFdId());
				sysAttMain.setFdFileName(sysNewsPublishModel
						.getDocSubject()
						+ fileName.substring(fileName.lastIndexOf(".")));
				if (!"editonline".equals(contentAttKeyString)) {
					  //普通附件复制，作为新闻文档的普通附件
						sysAttMain.setFdKey("fdAttachment");
				}else{
				        sysAttMain.setFdKey("editonline");
				}
				sysAttMain
						.setFdModelName("com.landray.kmss.sys.news.model.SysNewsMain");
				getSysAttMainService().update(sysAttMain);
			}
		}
		//普通附件key
		String attKeyString = requestContext.getParameter("attKey");
		String[] attKeyArray = attKeyString.split("\\|");
		for (int i = 0; i < attKeyArray.length; i++) {
			List l2 = getSysAttMainService().findByModelKey(fdModelName,
					fdModelId, attKeyArray[i]);
			for (int j = 0; j < l2.size(); j++) {
				sysAttMain = getSysAttMainService()
						.clone((SysAttMain) l2.get(j));
				String fileName = sysAttMain.getFdFileName();
				sysAttMain.setFdAttType("byte");
				sysAttMain.setFdModelId(sysNewsMain.getFdId());
				if (!"editonline".equals(attKeyArray[i])) {
				  //普通附件复制，作为新闻文档的普通附件
					sysAttMain.setFdKey("fdAttachment");
				} else {
					sysAttMain.setFdFileName(sysNewsPublishModel
							.getDocSubject()
							+ fileName.substring(fileName.lastIndexOf(".")));
					sysAttMain.setFdKey("editonline");
				}
				sysAttMain
						.setFdModelName("com.landray.kmss.sys.news.model.SysNewsMain");
				getSysAttMainService().update(sysAttMain);
			}
		}
		if (StringUtil.isNotNull(sysNewsMainForm.getAuthReaderIds())) {
			sysNewsMain.setAuthReaderFlag(false);
			List authReaderAllList = new ArrayList(sysOrgElementService
					.findByPrimaryKeys(sysNewsMainForm.getAuthReaderIds()
							.split(";")));
			sysNewsMain.getAuthReaders().addAll(authReaderAllList);
		}
		// else {
		// #62777 推送到新闻不继承原文档权限
			// setAuthFromModel(sysNewsPublishModel, sysNewsMain);
		// }
		recalculateAttReaderField(sysNewsMain, fdTemplate);// 根据模板重新复制附件权限
		// recalculateEditorField(sysNewsMain, fdTemplate);// 根据模板重新复制编辑者
		// 继承分类的可编辑者
		sysNewsMain.setAuthEditorFlag(fdTemplate.getAuthEditorFlag());
		if (fdTemplate.getAuthTmpEditors() != null) {
			sysNewsMain.getAuthEditors().addAll(
					new ArrayList(fdTemplate.getAuthTmpEditors()));
		}
		setSysNewsMainCommon(sysNewsPublishModel, sysNewsMain, true);// 新闻基本信息设置
		copyFlowFromNewsCategory(sysNewsMain, fdTemplate);// 生成新闻并加载流程
	}

	// 手动自动发布加载流程
	private void copyFlowFromNewsCategory(SysNewsMain sysNewsMain,
			SysNewsTemplate fdTemplate) throws Exception {
		// 添加日志信息
		if (UserOperHelper.allowLogOper("add", sysNewsMainDao.getModelName())) {
			UserOperContentHelper.putAdd(sysNewsMain, "docSubject",
					"fdImportance", "fdModelId", "fdModelName", "fdKey",
					"docPublishTime", "docAlterTime", "docCreateTime",
					"fdLinkUrl", "fdIsLink", "docCreator", "authArea",
					"fdAuthor", "fdDepartment", "docContent", "authFromModel",
					"docStatus");
		}
		String fdKey = "newsMainDoc";// 新闻的key
		sysNewsMainService.add(sysNewsMain);// 生成新闻
		WorkflowEngineContext subContext = sysWfProcessSubService.init(
				sysNewsMain, fdKey, fdTemplate, fdKey);
		sysWfProcessSubService.doAction(subContext, sysNewsMain);
	}

	/**
	 * 自动发布事件触发（仅对部署了流程的文档）
	 */
	@Override
    public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
			return;
		}
		Object obj = event.getSource();
		// 回收站事件
		if (event instanceof SysRecycleSoftDelEvent) {
			deleteByPublish((IBaseModel) obj, SysRecycleConstant.OPT_TYPE_SOFTDELETE);
		} else if (event instanceof SysRecycleHardDelEvent) {
			deleteByPublish((IBaseModel) obj, SysRecycleConstant.OPT_TYPE_HARDDELETE);
		}

		if (!(obj instanceof ISysLbpmMainModelAdapter
				&& obj instanceof ISysNewsPublishMainModel)) {
			return;
		}
		IBaseModel model = (IBaseModel) obj;
		if ((event instanceof Event_SysFlowFinish)) {// 流程结束时
			try {
				model.recalculateFields();// 重新计算文档的所有阅读者
				addAutoPublish(model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 自动发布（仅对未部署流程的文档）
	 */
	@Override
    public void AutoPublish(IBaseModel mainModel) throws Exception {
		Boolean flag = getMainModelDocStatus(mainModel);
		if (flag) {
			mainModel.recalculateFields();// 重新计算文档的所有阅读者
			addAutoPublish(mainModel);
		}
	}

	// 根据fdId查询主文档在数据库中的状态，并比较当前提交的状态，如果为第一次发布，则发布该文档
	private Boolean getMainModelDocStatus(IBaseModel mainModel)
			throws Exception {
		Boolean flag = false;
		String fdModelId = mainModel.getFdId();
		String fdModelName = ModelUtil.getModelClassName(mainModel);
		Object modelInfo = com.landray.kmss.util.ClassUtils.forName(fdModelName);
		IBaseModel baseModel = findByPrimaryKey(fdModelId, modelInfo, false);
		if (baseModel == null) {
			return flag;
		}
		ISysNewsPublishMainModel sysNewsPublishMainModel = (ISysNewsPublishMainModel) baseModel;
		if (sysNewsPublishMainModel == null) {
			return flag;
		}
		if (sysNewsPublishMainModel.getSysNewsPublishMain() == null) {// 新建时
			flag = true;
		} else {
			String docStatusDb = sysNewsPublishMainModel
					.getSysNewsPublishMain().getDocStatus();
			if (StringUtil.isNull(docStatusDb)// 新建时
					|| SysDocConstant.DOC_STATUS_EXAMINE.equals(docStatusDb)) {// 更新时
				flag = true;
			}
		}
		return flag;
	}

	// 自动发布
	private void addAutoPublish(IBaseModel model) throws Exception {
		List list = getCoreModels(model, null);
		SysNewsPublishMain sysNewsPublishMain = new SysNewsPublishMain();
		if (list == null || list.size() == 0) {
			return;
		}
		sysNewsPublishMain = (SysNewsPublishMain) (list.get(0));
		// 如果设为不自动发布或者新闻发布类型选择为空则不发布新闻
		if (sysNewsPublishMain.getFdIsAutoPublish() == null
				|| !sysNewsPublishMain.getFdIsAutoPublish()
				|| sysNewsPublishMain.getFdCategoryId() == null
				|| ("").equals(sysNewsPublishMain.getFdCategoryId())) {
			return;
		}
		// 判断是否需要启动流程
		if (sysNewsPublishMain.getFdIsFlow() != null
				&& !sysNewsPublishMain.getFdIsFlow()) {
			if (model instanceof ISysNewsPublishMainModel) {// 不经流程审批发布新闻
				ISysNewsPublishMainModel mainModel = (ISysNewsPublishMainModel) model;
				addAutoPublishIsnotFlow(mainModel, sysNewsPublishMain);
			}
		} else if (sysNewsPublishMain.getFdIsFlow() != null
				&& sysNewsPublishMain.getFdIsFlow()) {// 需要流程审批发布新闻
			addAutoPublishIsFlow(model);
		}
	}

	// 自动发布:不经无流程发布新闻
	private void addAutoPublishIsnotFlow(ISysNewsPublishMainModel model,
			SysNewsPublishMain sysNewsPublishMain) throws Exception {
		// #140626 可以发布多个分类
		String cateIds = sysNewsPublishMain.getFdCategoryId();
		String[] cateIdArray = cateIds.split(";");
		for (String cateId : cateIdArray) {
			SysNewsMain sysNewsMain = new SysNewsMain();
			setSysNewsMainTemplateAll(model, sysNewsPublishMain, cateId, sysNewsMain);
			sysNewsMain.setDocPublishTime(new Date());
			sysNewsMain.setDocAlterTime(new Date());// 修改时间 by张文添 2010-8-30
			sysNewsMain.setDocStatus(SysDocConstant.DOC_STATUS_PUBLISH);// 无审批新闻直接为发布状态
			sysNewsMain.setDocCreator(model.getDocCreator());
			sysNewsMain.setFdAuthor(model.getDocCreator());
			// sysNewsMain.setAuthArea(model.getAuthArea());
			// 发布新闻所属部门和主文档一致
			if (PropertyUtils.isReadable(model, "docDept")) {
				if (model.getDocDept() != null) {
					sysNewsMain.setFdDepartment(model.getDocDept());
				}
			} else {
				if (model.getDocCreator().getFdParent() != null) {
					sysNewsMain
							.setFdDepartment(model.getDocCreator().getFdParent());// 部门
				}
			}
			sysNewsMainDao.add(sysNewsMain);// 生成新闻
		}
	}

	// 手动自动发布新闻设置
	private void setSysNewsMainCommon(ISysNewsPublishMainModel model,
			SysNewsMain sysNewsMain, Boolean isAttNews) throws Exception {
		sysNewsMain.setDocCreateTime(new Date());
		sysNewsMain.setDocAlterTime(new Date());// 修改时间 by张文添 2010-8-30
		sysNewsMain.setFdLinkUrl(ModelUtil.getModelUrl(model));// 新闻的链接URL
		if (!isAttNews) {
			sysNewsMain.setFdIsLink(true);// 是否链接
		}
		sysNewsMain.setDocCreator(UserUtil.getUser());// 手动发布新闻创建者为当前登录者
		sysNewsMain.setAuthArea(sysNewsMain.getFdTemplate().getAuthArea());
		sysNewsMain.setFdAuthor(model.getDocCreator());
		// 发布新闻所属部门和主文档一致
		if (PropertyUtils.isReadable(model, "docDept")) {
			if (model.getDocDept() != null) {
				sysNewsMain.setFdDepartment(model.getDocDept());
			}
		} else {
			// 如果作者部门为空则取当前登录者所属部门
			if (model.getDocCreator().getFdParent() != null) {
				sysNewsMain
						.setFdDepartment(model.getDocCreator().getFdParent());// 作者属部门
			} else if (UserUtil.getUser().getFdParent() != null) {
				sysNewsMain.setFdDepartment(UserUtil.getUser().getFdParent());// 当前登录者所属部门
			}
		}
		String docSubject = model.getDocSubject();// 主文档信息
		sysNewsMain.setDocSubject(docSubject);// 设置新闻标题
		sysNewsMain.setDocContent(docSubject);// 设置新闻内容
		// 如果是附件推送新闻，则不继承原文档的可阅读者权限
		if (!isAttNews) {
			setAuthFromModel(model, sysNewsMain);
		}
		sysNewsMain.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);// 发布状态
	}

	private void setAuthFromModel(ISysNewsPublishMainModel model,
			SysNewsMain sysNewsMain) {
		Boolean authReaderFlag = model.getAuthReaderFlag();// 阅读标记
		sysNewsMain.setAuthReaderFlag(authReaderFlag);
		if (authReaderFlag != null && !authReaderFlag) {// 如果没有可阅读标记或所有人可阅读则不加入到新闻阅读者中
			Set authReaderList = new HashSet(model.getAuthReaders());
			if (authReaderList != null && !authReaderList.isEmpty()) {
				sysNewsMain.getAuthReaders().addAll(authReaderList);
			} else {
				// #65040
				// authReaderFlag为false，同时authReaders为空，说明是流程或者公文等“可阅读者为空则只有作者和相关人员可阅读”的场景，此时把所有可阅读者字段继承过来
				Set authAllReaderList = new HashSet(model.getAuthAllReaders());
				if (authAllReaderList != null && !authAllReaderList.isEmpty()) {
					sysNewsMain.getAuthReaders().addAll(authAllReaderList);
				}
			}

			Set authReaderOtherList = new HashSet(model
					.getAuthOtherReaders());
			if (authReaderOtherList != null && !authReaderOtherList.isEmpty()) {
				sysNewsMain.getAuthOtherReaders().addAll(authReaderOtherList);
			}
		}
	}

	// 手动自动发布根据模板复制附件权限
	private void recalculateAttReaderField(SysNewsMain sysNewsMain,
			SysNewsTemplate fdTemplate) {
		if (fdTemplate.getAuthTmpAttNodownload() != null
				&& fdTemplate.getAuthTmpAttNocopy()) {
			sysNewsMain.setAuthAttNocopy(fdTemplate.getAuthTmpAttNocopy());
		} else {
			if (fdTemplate.getAuthTmpAttCopys() != null) {
				sysNewsMain.getAuthAttCopys().addAll(
						new ArrayList(fdTemplate.getAuthTmpAttCopys()));
			}
		}
		if (fdTemplate.getAuthTmpAttNodownload() != null
				&& fdTemplate.getAuthTmpAttNodownload()) {
			sysNewsMain.setAuthAttNodownload(fdTemplate
					.getAuthTmpAttNodownload());
		} else {
			if (fdTemplate.getAuthTmpAttDownloads() != null) {
				sysNewsMain.getAuthAttDownloads().addAll(
						new ArrayList(fdTemplate.getAuthTmpAttDownloads()));
			}
		}
		if (fdTemplate.getAuthTmpAttNoprint() != null
				&& fdTemplate.getAuthTmpAttNoprint()) {
			sysNewsMain.setAuthAttNoprint(fdTemplate.getAuthTmpAttNoprint());
		} else {
			if (fdTemplate.getAuthTmpAttPrints() != null) {
				sysNewsMain.getAuthAttPrints().addAll(
						new ArrayList(fdTemplate.getAuthTmpAttPrints()));
			}
		}
	}

	// 手动自动发布根据模板复制编辑者
	private void recalculateEditorField(SysNewsMain sysNewsMain,
			SysNewsTemplate fdTemplate) {
		sysNewsMain.setAuthEditorFlag(fdTemplate.getAuthEditorFlag());
		if (fdTemplate.getAuthAllEditors() != null) {
			sysNewsMain.getAuthAllEditors().addAll(
					new ArrayList(fdTemplate.getAuthAllEditors()));
		}
		if (fdTemplate.getAuthEditors() != null) {
			sysNewsMain.getAuthEditors().addAll(
					new ArrayList(fdTemplate.getAuthEditors()));
		}
		if (fdTemplate.getAuthOtherEditors() != null) {
			sysNewsMain.getAuthOtherEditors().addAll(
					new ArrayList(fdTemplate.getAuthOtherEditors()));
		}
	}

	// 自动发布机制设置
	private void setNewsPublishAutoInfo(SysNewsMain sysNewsMain,
			SysNewsPublishMain sysNewsPublishMain) {
		sysNewsMain.setFdImportance(sysNewsPublishMain.getFdImportance());
		sysNewsMain.setFdModelId(sysNewsPublishMain.getFdModelId());
		sysNewsMain.setFdModelName(sysNewsPublishMain.getFdModelName());
		sysNewsMain.setFdKey(sysNewsPublishMain.getFdKey());
	}

	// 自动发布获取模板以及所有设置
	private SysNewsTemplate setSysNewsMainTemplateAll(ISysNewsPublishMainModel model, SysNewsPublishMain sysNewsPublishMain,
													   String cateId, SysNewsMain sysNewsMain) throws Exception {
		setNewsPublishAutoInfo(sysNewsMain, sysNewsPublishMain);
		SysNewsTemplate fdTemplate = (SysNewsTemplate) sysNewsTemplateService
				.findByPrimaryKey(cateId, null,
						true);// 根据发布机制的类别id获取新闻的类别模板
		sysNewsMain.setFdTemplate(fdTemplate); // 设置新闻的分类
		recalculateEditorField(sysNewsMain, fdTemplate); // 根据模板复制計算编辑者
		setSysNewsMainCommon(model, sysNewsMain, false);
		return fdTemplate;
	}

	// 自动发布 需要流程审批发布新闻
	private void addAutoPublishIsFlow(IBaseModel baseModel) throws Exception {
		try {
			// 后台用户切换
			backgroundAuthService.switchUser(
					((ISysNewsPublishMainModel) baseModel).getDocCreator(),
					new Runner() {
						@Override
                        public Object run(Object parameter) throws Exception {
							ISysNewsPublishMainModel model = (ISysNewsPublishMainModel) parameter;
							List list = getCoreModels(model, null);
							SysNewsPublishMain sysNewsPublishMain = new SysNewsPublishMain();
							if (list != null && list.size() > 0) {
								sysNewsPublishMain = (SysNewsPublishMain) (list
										.get(0));
								addAutoPublishIsFlowSwitchUser(
										sysNewsPublishMain, model);
							}
							return null;
						}
					}, baseModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 切换用户
	private void addAutoPublishIsFlowSwitchUser(SysNewsPublishMain sysNewsPublishMain, ISysNewsPublishMainModel model) throws Exception {
		String cateIds = sysNewsPublishMain.getFdCategoryId();
		String[] cateIdArray = cateIds.split(";");
		for (String cateId : cateIdArray) {
			SysNewsMain sysNewsMain = new SysNewsMain();
			SysNewsTemplate fdTemplate = setSysNewsMainTemplateAll(model,
					sysNewsPublishMain, cateId, sysNewsMain);// 类别
			sysNewsMain.setDocCreator(model.getDocCreator());
			sysNewsMain.setFdAuthor(model.getDocCreator());
			// 发布新闻所属部门和主文档一致
			if (PropertyUtils.isReadable(model, "docDept")) {
				if (model.getDocDept() != null) {
					sysNewsMain.setFdDepartment(model.getDocDept());
				}
			} else {
				if (model.getDocCreator().getFdParent() != null) {
					sysNewsMain
							.setFdDepartment(model.getDocCreator().getFdParent());// 部门
				}
			}
			copyFlowFromNewsCategory(sysNewsMain, fdTemplate);// 生成新闻加载流程
		}
	}

	/**
	 * 删除文档（当接收到源文档被删除时，发布到新闻的文档也一并删除）
	 * 
	 * @param model
	 * @param type
	 */
	private void deleteByPublish(IBaseModel model, int type) {
		try {
			// 查询文档是否在发布新闻里
			HQLInfo info = new HQLInfo();
			info.setWhereBlock("fdModelName = :fdModelName and fdModelId = :fdModelId");
			info.setDocDeleteFlag(5);
			info.setParameter("fdModelName", ModelUtil.getModelClassName(model));
			info.setParameter("fdModelId", model.getFdId());
			List<SysNewsMain> list = sysNewsMainService.findList(info);

			if (list != null && !list.isEmpty()) {
				for (SysNewsMain main : list) {
					switch (type) {
					case SysRecycleConstant.OPT_TYPE_SOFTDELETE:
						sysNewsMainService.delete(main);
						break;
					case SysRecycleConstant.OPT_TYPE_HARDDELETE:
						sysNewsMainService.deleteHard(main);
						break;
					default:
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("删除发布新闻失败：", e);
		}
	}

}
