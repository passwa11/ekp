package com.landray.kmss.km.archives.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.actions.SimpleCategoryNodeAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.FieldNotFoundException;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.archives.forms.KmArchivesMainForm;
import com.landray.kmss.km.archives.model.ExcelImportResult;
import com.landray.kmss.km.archives.model.KmArchivesCategory;
import com.landray.kmss.km.archives.model.KmArchivesDense;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.model.KmArchivesPeriod;
import com.landray.kmss.km.archives.service.IKmArchivesAppraiseService;
import com.landray.kmss.km.archives.service.IKmArchivesBorrowService;
import com.landray.kmss.km.archives.service.IKmArchivesCategoryService;
import com.landray.kmss.km.archives.service.IKmArchivesDenseService;
import com.landray.kmss.km.archives.service.IKmArchivesDestroyService;
import com.landray.kmss.km.archives.service.IKmArchivesDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesLibraryService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.km.archives.service.IKmArchivesPeriodService;
import com.landray.kmss.km.archives.service.IKmArchivesUnitService;
import com.landray.kmss.km.archives.service.spring.KmArchivesModuleSelectService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.property.model.SysPropertyTemplate;
import com.landray.kmss.sys.property.util.SysPropertyCriteriaUtil;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.sys.workflow.engine.WorkflowEngineContext;
import com.landray.kmss.sys.workflow.interfaces.ISysWfProcessSubService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.WorkBook;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import org.apache.commons.lang.ArrayUtils;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KmArchivesMainAction extends SimpleCategoryNodeAction {

    private IKmArchivesMainService kmArchivesMainService;

	@Override
	public IKmArchivesMainService getServiceImp(HttpServletRequest request) {
        if (kmArchivesMainService == null) {
            kmArchivesMainService = (IKmArchivesMainService) getBean("kmArchivesMainService");
        }
        return kmArchivesMainService;
    }

	private IKmArchivesCategoryService kmArchivesCategoryService;

	public IKmArchivesCategoryService getCategoryServiceImp() {
		if (kmArchivesCategoryService == null) {
			kmArchivesCategoryService = (IKmArchivesCategoryService) getBean(
					"kmArchivesCategoryService");
		}
		return kmArchivesCategoryService;
	}

	private IKmArchivesLibraryService kmArchivesLibraryService;

	public IKmArchivesLibraryService getKmArchivesLibraryService() {
		if (kmArchivesLibraryService == null) {
			kmArchivesLibraryService = (IKmArchivesLibraryService) getBean(
					"kmArchivesLibraryService");
		}
		return kmArchivesLibraryService;
	}

	private IKmArchivesPeriodService kmArchivesPeriodService;

	public IKmArchivesPeriodService getKmArchivesPeriodService() {
		if (kmArchivesPeriodService == null) {
			kmArchivesPeriodService = (IKmArchivesPeriodService) getBean(
					"kmArchivesPeriodService");
		}
		return kmArchivesPeriodService;
	}

	private IKmArchivesUnitService kmArchivesUnitService;

	public IKmArchivesUnitService getKmArchivesUnitService() {
		if (kmArchivesUnitService == null) {
			kmArchivesUnitService = (IKmArchivesUnitService) getBean(
					"kmArchivesUnitService");
		}
		return kmArchivesUnitService;
	}

	private IKmArchivesDenseService kmArchivesDenseService;

	public IKmArchivesDenseService getKmArchivesDenseService() {
		if (kmArchivesDenseService == null) {
			kmArchivesDenseService = (IKmArchivesDenseService) getBean(
					"kmArchivesDenseService");
		}
		return kmArchivesDenseService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	private KmArchivesModuleSelectService kmArchivesModuleSelectService;

	public KmArchivesModuleSelectService getKmArchivesModuleSelectService() {
		if (kmArchivesModuleSelectService == null) {
			kmArchivesModuleSelectService = (KmArchivesModuleSelectService) getBean(
					"kmArchivesModuleSelectService");
		}
		return kmArchivesModuleSelectService;
	}

	private IKmArchivesDetailsService kmArchivesDetailsService;

	public IKmArchivesDetailsService getKmArchivesDetailsService() {
		if (kmArchivesDetailsService == null) {
			kmArchivesDetailsService = (IKmArchivesDetailsService) getBean(
					"kmArchivesDetailsService");
		}
		return kmArchivesDetailsService;
	}
	
	private IKmArchivesAppraiseService kmArchivesAppraiseService;
	public IKmArchivesAppraiseService getKmArchivesAppraiseService() {
		if (kmArchivesAppraiseService == null) {
			kmArchivesAppraiseService = (IKmArchivesAppraiseService) getBean(
					"kmArchivesAppraiseService");
		}
		return kmArchivesAppraiseService;
	}
	
	private IKmArchivesBorrowService kmArchivesBorrowService;
	public IKmArchivesBorrowService getKmArchivesBorrowService() {
		if (kmArchivesBorrowService == null) {
			kmArchivesBorrowService = (IKmArchivesBorrowService) getBean(
					"kmArchivesBorrowService");
		}
		return kmArchivesBorrowService;
	}
	
	private IKmArchivesDestroyService kmArchivesDestroyService;
	public IKmArchivesDestroyService getKmArchivesDestroyService() {
		if (kmArchivesDestroyService == null) {
			kmArchivesDestroyService = (IKmArchivesDestroyService) getBean(
					"kmArchivesDestroyService");
		}
		return kmArchivesDestroyService;
	}

    @Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		String categoryId = request.getParameter("categoryId");
		String method = request.getParameter("method");
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNotNull(categoryId) && !"manageList".equals(method)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesMain.docTemplate.fdId = :categoryId)");
			hqlInfo.setParameter("categoryId", categoryId);
		}
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"(kmArchivesMain.fdDestroyed = false)");
		CriteriaValue cv = new CriteriaValue(request);
		String navType = cv.poll("navType");

		if ("examine".equals(navType)) { // 档案审核
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesMain.docStatus = '20' or kmArchivesMain.docStatus = '11')");
		}
		String kStatus = cv.poll("kStatus"); // 档案状态
		// 在库
		if ("library".equals(kStatus)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmArchivesMain.docStatus = '30' and (kmArchivesMain.fdValidityDate > :fdValidityDate or kmArchivesMain.fdValidityDate is null)");
			hqlInfo.setParameter("fdValidityDate", new Date());
		} else if ("expire".equals(kStatus)) { // 已过期
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesMain.fdValidityDate <= :fdValidityDate)");
			hqlInfo.setParameter("fdValidityDate", new Date());
		}
		String docStatus = cv.poll("docStatus");
		if (StringUtil.isNotNull(docStatus)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesMain.docStatus = :docStatus)");
			hqlInfo.setParameter("docStatus", docStatus);

		}
		
		//add by caoyong PC端档案管理-档案录入-我录入的页面中数据显示不对，应该只展示登录用户录入的档案，但是现在显示了所有人录入的档案
		String mydoc = cv.poll("mydoc");
		if ("myCreate".equals(mydoc)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesMain.docCreator.fdId=:docCreator)");
			hqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());
		}
		
		// 查询只显示最新的版本
		whereBlock += " and kmArchivesMain.docIsNewVersion = '1' and (kmArchivesMain.fdIsPreFile !=:fdIsPreFile or kmArchivesMain.fdIsPreFile is null)";
		hqlInfo.setParameter("fdIsPreFile", Boolean.TRUE);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setModelName(KmArchivesMain.class.getName());
		String docTemplateId = cv.poll("docTemplate");
		if (StringUtil.isNotNull(docTemplateId)) {
			KmArchivesCategory category = (KmArchivesCategory) getCategoryServiceImp()
					.findByPrimaryKey(docTemplateId);
			List<String> idLists = new ArrayList<String>();
			SysPropertyTemplate temp = category.getSysPropertyTemplate();
			if (temp != null) {
				SysPropertyCriteriaUtil.buildHql(cv, hqlInfo, temp, idLists);
			}
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
					"kmArchivesMain.docTemplate.fdHierarchyId like :fdHierarchyId"));
			hqlInfo.setParameter("fdHierarchyId", category.getFdHierarchyId() + "%");
		}
		CriteriaUtil.buildHql(cv, hqlInfo, KmArchivesMain.class.getName());
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmArchivesMainForm kmArchivesMainForm = (KmArchivesMainForm) super.createNewForm(mapping, form, request, response);
		kmArchivesMainForm.setFdFileDate(DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATE));
        ((IKmArchivesMainService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return kmArchivesMainForm;
    }

	@Override
	public ISysSimpleCategoryService getSysSimpleCategoryService() {
		return (ISysSimpleCategoryService) getCategoryServiceImp();
	}

	@Override
	protected String getParentProperty() {
		return "docTemplate";
	}

	/**
	 * 批量更新
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String selects = request.getParameter("selects");
			if (StringUtil.isNull(selects)) {
                throw new IllegalArgumentException();
            }
			String[] selectIds = selects.split(";");
			KmArchivesMainForm mainForm = (KmArchivesMainForm) form;
			String fieldName = request.getParameter("fieldName");
			List<KmArchivesMain> list = (List<KmArchivesMain>) getServiceImp(
					request).findByPrimaryKeys(selectIds);
			for (int i = 0; i < list.size(); i++) {
				KmArchivesMain mainModel = list.get(i);
				if ("docTemplate".equals(fieldName)) {
					String docTemplateId = mainForm.getDocTemplateId();
					KmArchivesCategory category = (KmArchivesCategory) getCategoryServiceImp()
							.findByPrimaryKey(docTemplateId);
					if (UserOperHelper.allowLogOper("batchUpdate",
							getServiceImp(request).getModelName())) {
						UserOperContentHelper.putUpdate(mainModel).putSimple(
								"docTemplate", mainModel.getDocTemplate(),
								category);
					}
					mainModel.setDocTemplate(category);
				} else if ("fdLibrary".equals(fieldName)) {
					if (UserOperHelper.allowLogOper("batchUpdate",
							getServiceImp(request).getModelName())) {
						UserOperContentHelper.putUpdate(mainModel).putSimple(
								"fdLibrary", mainModel.getFdLibrary(),
								mainForm.getFdLibrary());
					}
					mainModel.setFdLibrary(mainForm.getFdLibrary());
				} else if ("fdVolumeYear".equals(fieldName)) {
					if (UserOperHelper.allowLogOper("batchUpdate",
							getServiceImp(request).getModelName())) {
						UserOperContentHelper.putUpdate(mainModel).putSimple(
								"fdVolumeYear", mainModel.getFdVolumeYear(),
								mainForm.getFdVolumeYear());
					}
					mainModel.setFdVolumeYear(mainForm.getFdVolumeYear());
				} else if ("fdPeriod".equals(fieldName)) {
					if (UserOperHelper.allowLogOper("batchUpdate",
							getServiceImp(request).getModelName())) {
						UserOperContentHelper.putUpdate(mainModel).putSimple(
								"fdPeriod", mainModel.getFdPeriod(),
								mainForm.getFdPeriod());
					}
					KmArchivesPeriod period = (KmArchivesPeriod) getKmArchivesPeriodService().findByPrimaryKey(mainForm.getFdPeriod(), null, true);
					if (period != null) {
						// 根据保管期限更新档案有效期
						Calendar cal = Calendar.getInstance();
						cal.setTime(mainModel.getFdFileDate());
						cal.add(Calendar.YEAR, period.getFdSaveLife());
						Date fdValidityDate = cal.getTime();
						mainModel.setFdValidityDate(fdValidityDate);
						mainModel.setFdPeriod(period.getFdName());
					}
				} else if ("fdUnit".equals(fieldName)) {
					if (UserOperHelper.allowLogOper("batchUpdate",
							getServiceImp(request).getModelName())) {
						UserOperContentHelper.putUpdate(mainModel).putSimple(
								"fdUnit", mainModel.getFdUnit(),
								mainForm.getFdUnit());
					}
					mainModel.setFdUnit(mainForm.getFdUnit());
				} else if ("fdDenseId".equals(fieldName)) {
					if (UserOperHelper.allowLogOper("batchUpdate",
							getServiceImp(request).getModelName())) {
						UserOperContentHelper.putUpdate(mainModel).putSimple(
								"fdDenseId", mainModel.getFdDenseLevel(),
								mainForm.getFdDenseLevel());
					}
					String fdDenseId = mainForm.getFdDenseId();
					if (StringUtil.isNotNull(fdDenseId)) {
						KmArchivesDense fdDense = (KmArchivesDense) getKmArchivesDenseService()
								.findByPrimaryKey(fdDenseId);
						if (fdDense != null) {
							mainModel.setFdDenseLevel(fdDense.getFdName());
							mainModel.setFdDense(fdDense);
						}
					} else {
						mainModel.setFdDenseLevel("");
						mainModel.setFdDense(null);
					}
				} else if ("fdStorekeeper".equals(fieldName)) {
					SysOrgElement person = getSysOrgCoreService()
							.findByPrimaryKey(mainForm.getFdStorekeeperId(),
									SysOrgElement.class);
					if (UserOperHelper.allowLogOper("batchUpdate",
							getServiceImp(request).getModelName())) {
						UserOperContentHelper.putUpdate(mainModel).putSimple(
								"fdStorekeeper", mainModel.getFdStorekeeper(),
								person);
					}
					mainModel.setFdStorekeeper(person);
				} else if ("fdValidityDate".equals(fieldName)) {
					Date fdValidityDate = DateUtil.convertStringToDate(
							mainForm.getFdValidityDate(),
							DateUtil.PATTERN_DATE,
							ResourceUtil.getLocaleByUser());
					if (UserOperHelper.allowLogOper("batchUpdate",
							getServiceImp(request).getModelName())) {
						UserOperContentHelper.putUpdate(mainModel).putSimple(
								"fdValidityDate", mainModel.getFdValidityDate(),
								fdValidityDate);
					}
					mainModel.setFdValidityDate(fdValidityDate);
				} else if ("fdFileDate".equals(fieldName)) {
					Date fdFileDate = DateUtil.convertStringToDate(
							mainForm.getFdFileDate(), DateUtil.PATTERN_DATE,
							ResourceUtil.getLocaleByUser());
					if (UserOperHelper.allowLogOper("batchUpdate",
							getServiceImp(request).getModelName())) {
						UserOperContentHelper.putUpdate(mainModel).putSimple(
								"fdFileDate", mainModel.getFdFileDate(),
								fdFileDate);
					}
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setWhereBlock("kmArchivesPeriod.fdName = :fdName");
					hqlInfo.setParameter("fdName", mainModel.getFdPeriod());
					KmArchivesPeriod period = (KmArchivesPeriod) getKmArchivesPeriodService().findFirstOne(hqlInfo);
					if (period != null) {
						// 根据保管期限更新档案有效期
						Calendar cal = Calendar.getInstance();
						cal.setTime(fdFileDate);
						cal.add(Calendar.YEAR, period.getFdSaveLife());
						Date fdValidityDate = cal.getTime();
						mainModel.setFdValidityDate(fdValidityDate);
					}
					mainModel.setFdFileDate(fdFileDate);
				}
				getServiceImp(request).update(mainModel);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	/**
	 * 下载导入模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadTemplate(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String docTemplate = request.getParameter("docTemplate");
			if (StringUtil.isNull(docTemplate)) {
                throw new FieldNotFoundException();
            }
			WorkBook wb = getServiceImp(request).buildTemplateWorkbook(request);
			ExcelOutput output = new ExcelOutputImp();
			output.output(wb, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request,
					response);
		}
		return null;
	}

	/**
	 * 导入数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importArchives(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExcelImportResult importResult = new ExcelImportResult();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			KmArchivesMainForm mainForm = (KmArchivesMainForm) form;
			FormFile file = mainForm.getFile();
			String fileName = file.getFileName();
			if (!(fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))) {
				throw new KmssRuntimeException(new KmssMessage(
						ResourceUtil.getString("km-archives:kmArchivesMain.import.error.msg")));
			}
			String docTemplate = request.getParameter("docTemplate");
			importResult = getServiceImp(request).addImportData(
					file.getInputStream(), docTemplate, request.getLocale());
		} catch (Exception e) {
			logger.error(e.getMessage());
			importResult.setImportMsg(ResourceUtil
					.getString("km-archives:kmArchivesMain.import.fail"));
			importResult.getOtherErrors().add(e.getMessage());
		}
		String result = getServiceImp(request).replaceCharacter(importResult.toString());
		com.alibaba.fastjson.JSONArray errorRows = com.alibaba.fastjson.JSONObject.parseObject(result).getJSONArray("errorRows");
		if(!errorRows.isEmpty()){
			com.alibaba.fastjson.JSONArray errorInfo = ((com.alibaba.fastjson.JSONObject) errorRows.get(0)).getJSONArray("errInfos");
			if(!errorInfo.isEmpty()){
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < errorInfo.size(); i++) {
					stringBuilder.append(errorInfo.get(i));
				}
				logger.error(stringBuilder.toString());
			}
		}
		response.setCharacterEncoding("UTF-8");
		request.setAttribute("result", result);
		return getActionForward("uploadResult", mapping, form, request, response);
	}

	/**
	 * 生成新版本
	 * 
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward newEdition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String originId = request.getParameter("originId");
			if (StringUtil.isNull(originId)) {
                throw new NoRecordException();
            }
			request.setAttribute("newEdition", "true");
			KmArchivesMainForm kmArchivesMainForm = (KmArchivesMainForm) form;
			KmArchivesMain kmArchivesMain = (KmArchivesMain) getServiceImp(
					request)
							.findByPrimaryKey(originId);
			kmArchivesMainForm = (KmArchivesMainForm) getServiceImp(request)
					.cloneModelToForm(kmArchivesMainForm, kmArchivesMain,
							new RequestContext(request));
			kmArchivesMainForm
					.setDocCreatorName(UserUtil.getUser().getFdName());
			kmArchivesMainForm.setDocCreateTime(DateUtil.convertDateToString(
					new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));
			kmArchivesMainForm.setDocNumber(null);
			kmArchivesMainForm.setMethod("add");
			kmArchivesMainForm.setMethod_GET("add");
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	/**
	 * 获得模块名
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getModuleNames(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String modules = request.getParameter("modules");
		String fdModules = getKmArchivesModuleSelectService()
				.getModuleNames(modules);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(fdModules);
		out.flush();
		out.close();
		return null;
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
				if (!getServiceImp(request).validateArchives(id)) {
					throw new KmssException(new KmssMessage(
							"km-archives:borrow.cannot.delete"));
				}
				getServiceImp(request).delete(id);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");

			for (String id : ids) {
				if (!getServiceImp(request).validateArchives(id)) {
					throw new KmssException(new KmssMessage(
							"km-archives:borrow.cannot.delete.batch"));
				}
			}
			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
                    getServiceImp(request).delete(authIds);
                }
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		return getActionForward("success", mapping, form, request,
					response);
	}

	public ActionForward getArchivesMobileIndex(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getCusledgerMobileIndex", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		JSONArray ja = new JSONArray();
		try {
			ja = getServiceImp(request).getArchivesMobileIndex();
		} catch (Exception e) {
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-getArchivesMobileIndex", false,
				getClass());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(ja.toString());

		return null;
	}

	public ActionForward docRightUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }

			KmArchivesMainForm mainForm = (KmArchivesMainForm) form;
			String fdIds = request.getParameter("fdIds");
			String oprType = request.getParameter("oprType");
			String modelName = mainForm.getModelClass().getCanonicalName();
			if (StringUtil.isNull(fdIds)) {
				throw new NoRecordException();
			}
			String[] authIds = fdIds.split("\\s*[;,]\\s*");
			// 阻止更新上级场所的数据
			if (SysAuthAreaUtils.isAreaEnabled(modelName)) {
				String[] ids = fdIds.split("\\s*[;,]\\s*");
				authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						modelName, "method=edit&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
				}
				if (!ArrayUtils.isEmpty(authIds)) {
					getServiceImp(request).updateDocRight(mainForm, authIds,
							oprType);
				}
			} else {
				getServiceImp(request).updateDocRight(mainForm, authIds,
						oprType);
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	public void changeFindPageHQLInfo4PreFile(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		String categoryId = request.getParameter("categoryId");
		String method = request.getParameter("method");
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNotNull(categoryId) && !"manageList".equals(method)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", "(kmArchivesMain.docTemplate.fdId = :categoryId)");
			hqlInfo.setParameter("categoryId", categoryId);
		}
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"(kmArchivesMain.fdDestroyed = false and kmArchivesMain.docStatus = :docStatus and kmArchivesMain.fdModelId is not null and kmArchivesMain.fdModelName is not null)");
		hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_DRAFT);

		CriteriaValue cv = new CriteriaValue(request);

		// 查询只显示最新的版本
		whereBlock += " and kmArchivesMain.docIsNewVersion = '1'";
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setModelName(KmArchivesMain.class.getName());
		String docTemplateId = request.getParameter("q.docTemplate");
		if (StringUtil.isNotNull(docTemplateId)) {
			KmArchivesCategory category = (KmArchivesCategory) getCategoryServiceImp().findByPrimaryKey(docTemplateId);
			List<String> idLists = new ArrayList<String>();
			SysPropertyTemplate temp = category.getSysPropertyTemplate();
			if (temp != null) {
				SysPropertyCriteriaUtil.buildHql(cv, hqlInfo, temp, idLists);
			}
		}
		HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesMain.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
	}

	public ActionForward listPreFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-listPreFile", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
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
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
			changeFindPageHQLInfo4PreFile(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listPreFile", mapping, form, request, response);
		}
	}

	private ICoreOuterService dispatchCoreService;

	protected ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) getBean("dispatchCoreService");
		}
		return dispatchCoreService;
	}

	public ActionForward deletePreFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return delete(mapping, form, request, response);
	}

	public ActionForward deleteallPreFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return deleteall(mapping, form, request, response);
	}

	public ActionForward viewPreFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-viewPreFile", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-viewPreFile", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("viewPreFile", mapping, form, request, response);
		}
	}

	public ActionForward editPreFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			form.reset(mapping, request);
			IExtendForm rtnForm = null;
			String id = request.getParameter("fdId");
			if (!StringUtil.isNull(id)) {
				KmArchivesMain model = (KmArchivesMain) getServiceImp(request).findByPrimaryKey(id, null, true);
				if (model != null) {
					IBackgroundAuthService backgroundAuthService = (IBackgroundAuthService) SpringBeanUtil.getBean("backgroundAuthService");
					// 切换归档人用户，使得启动流程的创建者与归档人保持一致
					backgroundAuthService.switchUserById(UserUtil.getUser().getFdId(),new Runner(){
						@Override
						public Object run(Object mainModel) throws Exception {
							ISysWfProcessSubService sysWfProcessSubService = (ISysWfProcessSubService) SpringBeanUtil.getBean("sysWfProcessSubService");
							KmArchivesMain model = (KmArchivesMain)mainModel;
							model.getSysWfBusinessModel().setCanStartProcess("false");
							WorkflowEngineContext subContext = sysWfProcessSubService.init(model, "kmArchivesMain",
									model.getDocTemplate(), "kmArchivesMain");
							sysWfProcessSubService.doAction(subContext, model);
							return null;
						}
					},model);
					rtnForm = getServiceImp(request).convertModelToForm((IExtendForm) form, model,
							new RequestContext(request));
					UserOperHelper.logFind(model);
					KmArchivesMainForm kmArchivesMainForm = (KmArchivesMainForm) rtnForm;
					if (kmArchivesMainForm.getSysWfBusinessForm() == null
							|| StringUtil.isNull(kmArchivesMainForm
									.getSysWfBusinessForm().getFdProcessId())) {
					getDispatchCoreService().initFormSetting(kmArchivesMainForm,
							"kmArchivesMain",
							model.getDocTemplate(), "kmArchivesMain",
							new RequestContext(request));
					}
					kmArchivesMainForm.setMethod_GET("edit");
				}
			}
			if (rtnForm == null) {
                throw new NoRecordException();
            }
			request.setAttribute(getFormName(rtnForm, request), rtnForm);

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	public ActionForward updatePreFiles(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updatePreFiles", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] fdIds = request.getParameterValues("List_Selected");
			getServiceImp(request).updatePreFiles(fdIds);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-updatePreFiles", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	public ActionForward updatePreFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updatePreFile", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).updatePreFile(id);
            }
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-updatePreFiles", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	public ActionForward updateChangeCates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updatePreFiles", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] fdIds = request.getParameterValues("List_Selected");
			String cateGoryId = request.getParameter("categoryId");
			getServiceImp(request).updateChangeCates(fdIds, cateGoryId);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-updatePreFiles", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	public ActionForward updateChangeCate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateChangeCate", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			String cateGoryId = request.getParameter("categoryId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).updateChangeCate(id, cateGoryId);
            }
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-updateChangeCate", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
	
	/**
	 * 获取已处理档案统计Echart数据（用于移动端公文首页图表渲染）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approvedArchivesChart(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String dateType = request.getParameter("dateType");
			Date startTime = null;
			Date endTime = null;
			switch(dateType){
		    case "week" :  // 本周
		       startTime = DateUtil.getBeginDayOfWeek();
		       endTime = DateUtil.getEndDayOfWeek();
		       break; 
		    case "month" :  // 本月
			   startTime = DateUtil.getBeginDayOfMonth();
			   endTime = DateUtil.getEndDayOfMonth();
		       break;
		    case "year" :  // 本年
			   startTime = DateUtil.getBeginDayOfYear();
			   endTime = DateUtil.getEndDayOfYear();
			   break;  	   
	        }
			
			// 获取已审档案审批总计数
			IKmArchivesMainService kmArchivesMainService = getServiceImp(request);
			Long mainCount = kmArchivesMainService.getApprovedStatisticalCount(startTime, endTime);
			
			// 获取已审档案鉴定总计数
			IKmArchivesAppraiseService kmArchivesAppraiseService = getKmArchivesAppraiseService();
			Long appraiseCount = kmArchivesAppraiseService.getApprovedStatisticalCount(startTime, endTime);
			
			// 获取已审档案借阅总计数
			IKmArchivesBorrowService kmArchivesBorrowService = getKmArchivesBorrowService();
			Long borrowCount = kmArchivesBorrowService.getApprovedStatisticalCount(startTime, endTime);
			
			// 获取已审档案销毁总计数
			IKmArchivesDestroyService kmArchivesDestroyService = getKmArchivesDestroyService();
			Long destroyCount = kmArchivesDestroyService.getApprovedStatisticalCount(startTime, endTime);
			
			// 所有已审总计数
			Long allApprovedCount = mainCount + appraiseCount + borrowCount +destroyCount;
			
			JSONArray datas = new JSONArray();
			
			JSONObject mainJson = new JSONObject();
			mainJson.put("name",  ResourceUtil.getString("kmArchives.chart.type.main", "km-archives", ResourceUtil.getLocaleByUser()));
			mainJson.put("value", calculateApprovedProportion(mainCount,allApprovedCount));
			datas.add(mainJson);
			
			JSONObject appraiseJson = new JSONObject();
			appraiseJson.put("name",  ResourceUtil.getString("kmArchives.chart.type.appraise", "km-archives", ResourceUtil.getLocaleByUser()));
			appraiseJson.put("value",  calculateApprovedProportion(appraiseCount,allApprovedCount));
			datas.add(appraiseJson);
			
			JSONObject borrowJson = new JSONObject();
			borrowJson.put("name",  ResourceUtil.getString("kmArchives.chart.type.borrow", "km-archives", ResourceUtil.getLocaleByUser()));
			borrowJson.put("value",  calculateApprovedProportion(borrowCount,allApprovedCount));
			datas.add(borrowJson);
			
			JSONObject destroyJson = new JSONObject();
			destroyJson.put("name",  ResourceUtil.getString("kmArchives.chart.type.destroy", "km-archives", ResourceUtil.getLocaleByUser()));
			destroyJson.put("value",  calculateApprovedProportion(destroyCount,allApprovedCount));
			datas.add(destroyJson);
			
			request.setAttribute("datas", datas);
			
		} catch (Exception e) {
			messages.addError(e);
		}
		return getActionForward("approvedArchivesChart", mapping, form, request, response);
	}
	
	/**
	 * 计算已处理档案比例
	 * @param a
	 * @param b
	 * @return
	 */
	private double calculateApprovedProportion(double a,double b){
		double result = 0;
		if(new BigDecimal(b).compareTo(new BigDecimal(0))!=0){
			// 计算比例（四舍五入保留两位小数）
			result = new BigDecimal(a/b*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		KmArchivesMainForm kmArchivesMainForm = (KmArchivesMainForm) request.getAttribute(getFormName(form, request));
		String fdPeriod = kmArchivesMainForm.getFdPeriod();
		if ("view".equals(request.getParameter("method")) && StringUtil.isNotNull(fdPeriod)) {
			try {
				//保管期限必须为整数，防止出错
				KmArchivesPeriod period = getKmArchivesPeriodService().findByValue(Integer.parseInt(fdPeriod));
				if (period != null) {
					((KmArchivesMainForm) form).setFdPeriod(period.getFdName());
				}
			} catch (Exception e) {
				
			}

		}
	}

}
