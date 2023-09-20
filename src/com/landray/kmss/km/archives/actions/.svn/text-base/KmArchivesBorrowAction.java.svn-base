package com.landray.kmss.km.archives.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.archives.forms.KmArchivesBorrowForm;
import com.landray.kmss.km.archives.forms.KmArchivesDetailsForm;
import com.landray.kmss.km.archives.model.KmArchivesBorrow;
import com.landray.kmss.km.archives.model.KmArchivesConfig;
import com.landray.kmss.km.archives.model.KmArchivesDense;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.model.KmArchivesTemplate;
import com.landray.kmss.km.archives.service.IKmArchivesBorrowService;
import com.landray.kmss.km.archives.service.IKmArchivesDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.km.archives.service.IKmArchivesTemplateService;
import com.landray.kmss.km.archives.util.KmArchivesConstant;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

public class KmArchivesBorrowAction extends ExtendAction {

	private IKmArchivesMainService kmArchivesMainService;

	public IKmArchivesMainService getKmArchivesMainService() {
		if (kmArchivesMainService == null) {
			kmArchivesMainService = (IKmArchivesMainService) getBean(
					"kmArchivesMainService");
		}
		return kmArchivesMainService;
	}

	private IKmArchivesBorrowService kmArchivesBorrowService;

    @Override
	public IBaseService getServiceImp(HttpServletRequest request) {
        if (kmArchivesBorrowService == null) {
            kmArchivesBorrowService = (IKmArchivesBorrowService) getBean("kmArchivesBorrowService");
        }
        return kmArchivesBorrowService;
    }

	private IKmArchivesTemplateService kmArchivesTemplateService;

	public IKmArchivesTemplateService getTemplateServiceImp(HttpServletRequest request) {
		if (kmArchivesTemplateService == null) {
			kmArchivesTemplateService = (IKmArchivesTemplateService) getBean(
					"kmArchivesTemplateService");
		}
		return kmArchivesTemplateService;
	}

	protected ISysOrgCoreService sysOrgCoreService;

	protected ISysOrgCoreService getSysOrgServiceImp(
			HttpServletRequest request) {
		if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) getBean(
                    "sysOrgCoreService");
        }
		return sysOrgCoreService;
	}

	private ICoreOuterService dispatchCoreService;

	protected ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) getBean(
					"dispatchCoreService");
		}
		return dispatchCoreService;
	}

	private ProcessExecuteService processExecuteService;

	public ProcessExecuteService getProcessExecuteService() {
		if (processExecuteService == null) {
			processExecuteService = (ProcessExecuteService) getBean(
					"lbpmProcessExecuteServiceTarget");
		}
		return processExecuteService;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		String fdId = request.getParameter("fdId");
		boolean canRenew = KmArchivesUtil.isCanRenew(fdId);
		request.setAttribute("canRenew", canRenew);
		boolean canReBorrow = KmArchivesUtil.isCanReBorrow(fdId);
		request.setAttribute("canReBorrow", canReBorrow);
		request.setAttribute("currentUserId",
				UserUtil.getKMSSUser().getUserId());
	}

	@Override
	public void changeFindPageHQLInfo(HttpServletRequest request,
									  HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		String status = request.getParameter("q.status");
		if (StringUtil.isNotNull(status)) {
			whereBlock = StringUtil.linkString(whereBlock, "and",
					" kmArchivesBorrow.docStatus = :docStatus ");
			hqlInfo.setParameter("docStatus", status);
		}
		hqlInfo.setWhereBlock(whereBlock);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesBorrow.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmArchivesBorrowForm kmArchivesBorrowForm = (KmArchivesBorrowForm) super.createNewForm(mapping, form, request, response);
		kmArchivesBorrowForm.setFdBorrowerId(UserUtil.getUser().getFdId());
		kmArchivesBorrowForm.setFdBorrowerName(UserUtil.getUser().getFdName());
		kmArchivesBorrowForm.setFdBorrowDate(
				DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));
		if (UserUtil.getUser().getFdParent() != null) {

			kmArchivesBorrowForm.setDocDeptId(UserUtil.getUser().getFdParent().getFdId());
			kmArchivesBorrowForm.setDocDeptName(UserUtil.getUser().getFdParent().getFdName());
		}
		kmArchivesBorrowForm.setDocCreatorId(UserUtil.getUser().getFdId());
		kmArchivesBorrowForm.setDocCreatorName(UserUtil.getUser().getFdName());
		kmArchivesBorrowForm.setDocCreateTime(
				DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));

		// ((IKmArchivesBorrowService)
		// getServiceImp(request)).initFormSetting((IExtendForm) form,
		// new RequestContext(request));
		List<KmArchivesTemplate> kmArchivesTemplateList = null;
		
		// 主文档Id(录入档案的Id)
		String fdMainId = request.getParameter("fdMainId");
		// 标识（如借阅流程变更 type=change）
		String type = request.getParameter("type");
		// 借阅Id,多用于再次借阅
		String fdBorrowId = request.getParameter("fdBorrowId");
		
		// 从录入的文档查看页借阅,获取文档的密级并设置对应的借阅流程
		if (StringUtil.isNotNull(fdMainId) && StringUtil.isNull(type)) {
			KmArchivesMain kmArchivesMain = (KmArchivesMain) getKmArchivesMainService()
					.findByPrimaryKey(fdMainId);
			KmArchivesDense kmArchivesDense = kmArchivesMain.getFdDense();
			if (kmArchivesDense != null) {
				List fdDenseIds = new ArrayList();
				fdDenseIds.add(kmArchivesDense.getFdId());
				kmArchivesTemplateList = getTemplateServiceImp(request).getTemplateByDenses(fdDenseIds, false);
			} else {
				kmArchivesTemplateList = getTemplateServiceImp(request).getTemplateByDenses(null, false);
			}
			if (kmArchivesTemplateList != null && !kmArchivesTemplateList.isEmpty()) {
				AutoArrayList fdBorrowDetail_Form = new AutoArrayList(KmArchivesDetailsForm.class);
				KmArchivesDetailsForm kmArchivesDetailsForm = new KmArchivesDetailsForm();
				kmArchivesDetailsForm.setFdArchId(kmArchivesMain.getFdId());
				kmArchivesDetailsForm.setFdArchives(kmArchivesMain);
				kmArchivesDetailsForm.setFdAuthorityRange(new KmArchivesConfig().getFdDefaultRange());
				kmArchivesDetailsForm.setFdBorrowerId(UserUtil.getKMSSUser().getUserId());
				kmArchivesDetailsForm.setFdStatus(KmArchivesConstant.BORROW_STATUS_READY);
				fdBorrowDetail_Form.add(kmArchivesDetailsForm);
				kmArchivesBorrowForm.setFdBorrowDetail_Form(fdBorrowDetail_Form);
			}
		}
		
        // 再次借阅，赋值相关信息
		if (StringUtil.isNotNull(fdBorrowId)) {
			KmArchivesBorrow kmArchivesBorrow = (KmArchivesBorrow) getServiceImp(request).findByPrimaryKey(fdBorrowId);

			if (kmArchivesBorrow != null) {
				KmArchivesBorrowForm copyForm = new KmArchivesBorrowForm();
				copyForm = (KmArchivesBorrowForm) getServiceImp(request).cloneModelToForm(copyForm, kmArchivesBorrow,
						new RequestContext(request));
				AutoArrayList FdBorrowDetails = copyForm.getFdBorrowDetail_Form();
				// 过期范围
				String expireRange = new KmArchivesConfig().getFdSoonExpireDate();
				int range = 0;
				if (StringUtil.isNotNull(expireRange)) {
					try {
						range = Integer.parseInt(expireRange);
					} catch (Exception e) {

					}
				}
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, range);
				int size = FdBorrowDetails.size();
				AutoArrayList FdRemoveList = new AutoArrayList(KmArchivesDetailsForm.class);
				for (int i = 0; i < size; i++) {
					Boolean flag = false;
					KmArchivesDetailsForm kmArchivesDetailsForm = (KmArchivesDetailsForm) FdBorrowDetails.get(i);
					kmArchivesDetailsForm.setFdStatus(
							KmArchivesConstant.BORROW_STATUS_READY);
					String fdArchId = kmArchivesDetailsForm.getFdArchId();
					KmArchivesMain kmArchivesMain = (KmArchivesMain) getKmArchivesMainService()
							.findByPrimaryKey(fdArchId);
					Boolean fdExpired = true;
					String authUrl = ModelUtil.getModelUrl(kmArchivesMain);
					if (UserUtil.checkAuthentication(authUrl, "GET")) {
						Boolean fdDstroyed = kmArchivesMain.getFdDestroyed();
						if (kmArchivesMain.getFdValidityDate() == null || (kmArchivesMain.getFdValidityDate() != null
								&& (kmArchivesMain.getFdValidityDate().getTime() > cal.getTimeInMillis()))) {
							fdExpired = false;
						}
						if (!fdDstroyed && !fdExpired) {
							flag = true;
						}
					}
					if (flag) {
						kmArchivesDetailsForm.setFdReturnDate(null);
					} else {
						FdRemoveList.add(kmArchivesDetailsForm);
					}
				}
				if (!FdRemoveList.isEmpty()) {
					FdBorrowDetails.removeAll(FdRemoveList);
				}
				copyForm.setFdBorrowDetail_Form(FdBorrowDetails);
				copyForm.setMethod_GET("add");
				request.setAttribute("fdDenses", KmArchivesUtil
						.getTemplateDense(kmArchivesBorrow.getDocTemplate()));
				getDispatchCoreService().initFormSetting((IExtendForm) form, "kmArchivesBorrow",
						kmArchivesBorrow.getDocTemplate(),
	    				"kmArchivesBorrow", new RequestContext(request));
				return copyForm;
			}
		}
		
		// 设置借阅流程模板，并初始化相关机制
		if (kmArchivesTemplateList == null || kmArchivesTemplateList.size() == 0) {
			kmArchivesTemplateList = (List<KmArchivesTemplate>) getTemplateServiceImp(request)
					.getTemplateByDenses(null,true);
		}
		KmArchivesTemplate kmArchivesTemplate = null;
		if (kmArchivesTemplateList != null && !kmArchivesTemplateList.isEmpty()) {
			KmArchivesTemplate fdDefaultTemplate = kmArchivesTemplateService.getDefaultTemplate();
	        if (fdDefaultTemplate != null) {
	        	kmArchivesTemplate = fdDefaultTemplate;
	        } else {
	        	kmArchivesTemplate = kmArchivesTemplateList.get(0);
	        }
		}
        
        if(kmArchivesTemplate != null) {
        	request.setAttribute("fdDenses", KmArchivesUtil.getTemplateDense(kmArchivesTemplate));
    		kmArchivesBorrowForm.setDocTemplateId(kmArchivesTemplate.getFdId());
    		kmArchivesBorrowForm.setDocTemplateName(kmArchivesTemplate.getFdName());
    		getDispatchCoreService().initFormSetting((IExtendForm) form, "kmArchivesBorrow",
    				kmArchivesTemplate,
    				"kmArchivesBorrow", new RequestContext(request));
        }
		
        return kmArchivesBorrowForm;
    }

	/**
	 * 修改借阅流程模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward change(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-change", true, getClass());
		KmssMessages messages = new KmssMessages();
		
		try{
			KmArchivesBorrowForm kmArchivesBorrowForm = (KmArchivesBorrowForm)form;

			String docTemplateId = request.getParameter("docTemplateId");

			if(StringUtil.isNotNull(docTemplateId)){
				KmArchivesTemplate kmArchivesTemplate = (KmArchivesTemplate) getTemplateServiceImp(
						request).findByPrimaryKey(docTemplateId);
				request.setAttribute("fdDenses", KmArchivesUtil.getTemplateDense(kmArchivesTemplate));
				kmArchivesBorrowForm.setDocTemplateId(docTemplateId);
				kmArchivesBorrowForm.setFdBorrowerId(UserUtil.getUser().getFdId());
				kmArchivesBorrowForm.setFdBorrowerName(UserUtil.getUser().getFdName());
				kmArchivesBorrowForm
						.setFdBorrowDate(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME,
								request.getLocale()));
				if (UserUtil.getUser().getFdParent() != null) {

					kmArchivesBorrowForm.setDocDeptId(UserUtil.getUser().getFdParent().getFdId());
					kmArchivesBorrowForm.setDocDeptName(UserUtil.getUser().getFdParent().getFdName());
				}
				kmArchivesBorrowForm
						.setDocTemplateName(kmArchivesTemplate.getFdName());
				kmArchivesBorrowForm
						.setDocCreatorId(UserUtil.getUser().getFdId());
				kmArchivesBorrowForm
						.setDocCreatorName(UserUtil.getUser().getFdName());
				kmArchivesBorrowForm
						.setDocCreateTime(DateUtil.convertDateToString(
								new Date(), DateUtil.TYPE_DATETIME,
								request.getLocale()));
				// kmArchivesBorrowForm.getFdBorrowDetail_Form().clear();
				kmArchivesBorrowForm.setMethod_GET("add");
				// 启动模板流程
				kmArchivesBorrowForm.getSysWfBusinessForm()
						.setCanStartProcess("true");
				getDispatchCoreService().initFormSetting(
						kmArchivesBorrowForm, "kmArchivesBorrow",
						kmArchivesTemplate, "kmArchivesBorrow",
						new RequestContext(request));
			}
			
			// #67953 借阅界面变更流程后，档案明细表部分字段为空；出现了不正常的审批记录界面
//			List<KmArchivesDetailsForm> detailForms = kmArchivesBorrowForm.getFdBorrowDetail_Form();
//			for(KmArchivesDetailsForm detailForm: detailForms) {
//				KmArchivesMain kmArchivesMain = (KmArchivesMain) getKmArchivesMainService()
//						.findByPrimaryKey(detailForm.getFdArchId());
//				detailForm.setFdArchives(kmArchivesMain);
//			}
			
			// #108006 借阅流程和密级程度绑定，所以变更流程后需要清空借阅明细（变更后的流程的密级程度可能和之前所选的档案的密级程度不匹配）
			kmArchivesBorrowForm.setFdBorrowDetail_Form(new AutoArrayList(KmArchivesDetailsForm.class));
			
		}catch(Exception e){
			messages.addError(e);
		}
		
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	public ActionForward getApplicantDept(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdBorrowId = request.getParameter("fdBorrowerId");
		String deptId = "";
		String deptName = "";
		try {
			if (StringUtil.isNotNull(fdBorrowId)) {
				SysOrgElement sysOrgElement = getSysOrgServiceImp(request)
						.findByPrimaryKey(fdBorrowId);
				if (sysOrgElement.getFdParent() != null) {
					deptId = sysOrgElement.getFdParent().getFdId();
					deptName = sysOrgElement.getFdParent().getFdName();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		JSONObject json = new JSONObject();
		json.put("deptId", deptId);
		json.put("deptName", deptName);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	private IKmArchivesDetailsService kmArchivesDetailsService;

	public IKmArchivesDetailsService
			getDetailsServiceImp(HttpServletRequest request) {
		if (kmArchivesDetailsService == null) {
			kmArchivesDetailsService = (IKmArchivesDetailsService) getBean(
					"kmArchivesDetailsService");
		}
		return kmArchivesDetailsService;
	}

	/**
	 * 档案信息页面查询借用记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward borrowList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			String fdCertId = request.getParameter("fdId");
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(
					" kmArchivesDetails.fdArchives.fdId = :fdId and kmArchivesDetails.fdStatus!=0");
			hqlInfo.setParameter("fdId", fdCertId);
			Page page = getDetailsServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("borrowList", mapping, form, request,
					response);
		}
	}

	/**
	 * 移动端新建借阅
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add4m(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = null;
			String docTemplateId = request.getParameter("docTemplateId");
			if (StringUtil.isNotNull(docTemplateId)) {
				newForm = super.createNewForm(mapping, form, request, response);
				KmArchivesBorrowForm borrowForm = (KmArchivesBorrowForm) newForm;
				KmArchivesTemplate kmArchivesTemplate = (KmArchivesTemplate) getTemplateServiceImp(request)
						.findByPrimaryKey(docTemplateId);
				borrowForm.setDocTemplateId(docTemplateId);
				borrowForm.setDocTemplateName(kmArchivesTemplate.getFdName());
				borrowForm.setFdBorrowerId(UserUtil.getUser().getFdId());
				borrowForm.setFdBorrowerName(UserUtil.getUser().getFdName());
				borrowForm
						.setFdBorrowDate(
								DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME,
								request.getLocale()));
				if (UserUtil.getUser().getFdParent() != null) {
					borrowForm.setDocDeptId(UserUtil.getUser().getFdParent().getFdId());
					borrowForm.setDocDeptName(UserUtil.getUser().getFdParent().getFdName());
				}
				borrowForm.setDocCreatorId(UserUtil.getUser().getFdId());
				borrowForm.setDocCreatorName(UserUtil.getUser().getFdName());
				borrowForm.setDocCreateTime(
						DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));
				borrowForm.setMethod_GET("add");
				// 启动模板流程
				borrowForm.getSysWfBusinessForm().setCanStartProcess("true");
				getDispatchCoreService().initFormSetting(borrowForm, "kmArchivesBorrow", kmArchivesTemplate,
						"kmArchivesBorrow", new RequestContext(request));
			} else {
				newForm = createNewForm(mapping, form, request, response);
			}
			if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }

		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-add", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		
		String type = request.getParameter("type");
		try {
			if(StringUtil.isNull(type)) {
				ActionForm newForm = createNewForm(mapping, form, request,response);
				if (newForm != form) {
					request.setAttribute(getFormName(newForm, request), newForm);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		
		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			
			if(StringUtil.isNotNull(type) && "change".equals(type)) {
				return change(mapping, form, request, response);
			} else {
				return getActionForward("edit", mapping, form, request, response);
			}
		}
	}
	public ActionForward approve(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmArchivesBorrowForm mainForm = (KmArchivesBorrowForm) form;
		mainForm.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		return super.update(mapping, form, request, response);
	}
}
