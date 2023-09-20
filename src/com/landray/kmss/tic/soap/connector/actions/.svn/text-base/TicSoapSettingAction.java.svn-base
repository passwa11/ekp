package com.landray.kmss.tic.soap.connector.actions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictSimpleProperty;
import com.landray.kmss.tic.core.log.model.TicCoreLogOpt;
import com.landray.kmss.tic.core.log.service.ITicCoreLogOptService;
import com.landray.kmss.tic.core.util.PasswordUtil;
import com.landray.kmss.tic.soap.connector.forms.TicSoapSettingForm;
import com.landray.kmss.tic.soap.connector.model.TicSoapSettCategory;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettCategoryService;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettingService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * WEBSERVICE服务配置 Action
 * 
 * @author
 * @version 1.0 2012-08-06
 */
public class TicSoapSettingAction extends ExtendAction {

	protected ITicSoapSettingService TicSoapSettingService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (TicSoapSettingService == null) {
			TicSoapSettingService = (ITicSoapSettingService) getBean("ticSoapSettingService");
		}
		return TicSoapSettingService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TicSoapSettingForm ticSoapSettingForm = (TicSoapSettingForm) form;
		String categoryId = request.getParameter("categoryId");
		ticSoapSettingForm.setDocCreatorId(UserUtil.getKMSSUser()
				.getUserId());
		ticSoapSettingForm.setDocCreatorName(UserUtil.getKMSSUser()
				.getUserName());
		ticSoapSettingForm.setDocCreateTime(DateUtil.convertDateToString(
				new Date(), null, null));
		ITicSoapSettCategoryService ticSoapSettCategoryService = (ITicSoapSettCategoryService) SpringBeanUtil
				.getBean("ticSoapSettCategoryService");
		if (StringUtil.isNotNull(categoryId)) {
			TicSoapSettCategory ticSoapSettCategory = (TicSoapSettCategory) ticSoapSettCategoryService
					.findByPrimaryKey(categoryId);
			ticSoapSettingForm.setSettCategoryId(categoryId);
			ticSoapSettingForm.setSettCategoryName(ticSoapSettCategory
					.getFdName());
		}
		return ticSoapSettingForm;
	}

	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			TicSoapSettingForm mainForm = (TicSoapSettingForm) form;
			mainForm.setDocAlterTime(DateUtil.convertDateToString(new Date(),
					null, null));
			// 添加操作者
			mainForm.setDocPoolAdmin(UserUtil.getUser().getFdName());
			String pass = mainForm.getFdPassword();
			if (StringUtil.isNotNull(pass) && !PasswordUtil.isEncrypted(pass)) {
				pass = PasswordUtil.encodePassword(pass);
			}
			TicSoapSetting TicSoapSetting = new TicSoapSetting();
			TicSoapSetting = (TicSoapSetting) getServiceImp(request)
					.convertFormToModel(mainForm, TicSoapSetting,
							new RequestContext());
			getServiceImp(request).add((IExtendForm) mainForm,
					new RequestContext(request));
			// 添加操作日志
			logSave(mainForm, request, "", 1, null);

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			TicSoapSettingForm mainForm = (TicSoapSettingForm) form;
			mainForm.setDocAlterTime(DateUtil.convertDateToString(new Date(),
					null, null));
			// 添加操作者
			mainForm.setDocPoolAdmin(UserUtil.getUser().getFdName());
			String pass = mainForm.getFdPassword();
			if (StringUtil.isNotNull(pass) && !PasswordUtil.isEncrypted(pass)) {
				pass = PasswordUtil.encodePassword(pass);
			}
			// 添加操作日志
			logSave(mainForm, request, "", 2, null);
			getServiceImp(request).update((IExtendForm) mainForm,
					new RequestContext(request));
			TicSoapSetting TicSoapSetting = new TicSoapSetting();
			TicSoapSetting = (TicSoapSetting) getServiceImp(request)
					.convertFormToModel(mainForm, TicSoapSetting,
							new RequestContext());

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 删除方法，添加操作日志
	 */
	@Override
    public ActionForward delete(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("fdId");
		String fdAppType = null;
		if (StringUtil.isNotNull(id)) {
			TicSoapSetting model = (TicSoapSetting) getServiceImp(request)
					.findByPrimaryKey(id);
			fdAppType = model.getFdAppType();

			logSave(null, request, id, 3, fdAppType);
		}
		Object result = super.delete(mapping, form, request, response);

		return (ActionForward) result;
	}

	/**
	 * 
	 * 删除方法，添加操作日志
	 */
	@Override
    public ActionForward deleteall(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Object result = super.deleteall(mapping, form, request, response);
		String[] ids = request.getParameterValues("List_Selected");
		if (ids != null) {
			logSave(form != null ? (TicSoapSettingForm) form : null, request,
					Arrays.toString(ids), 3, null);
		}
		return (ActionForward) result;
	}

	private String logInfoBuilder(TicSoapSettingForm mainForm,
			TicSoapSetting model) {
		SysDictModel dictModel = SysDataDict.getInstance().getModel(
				ModelUtil.getModelClassName(model));
		List<SysDictCommonProperty> properties = dictModel.getPropertyList();
		StringBuffer buffer = new StringBuffer("");
		String unitString = "!{messageKey}!{modelval}-------->!{formval}<br>";
		for (SysDictCommonProperty property : properties) {
			String ptName = property.getName();
			if ("fdEnviromentId".equals(ptName)) {
				continue;
			}
			// 只处理简单类型
			if (property instanceof SysDictSimpleProperty) {
				if (PropertyUtils.isReadable(model, ptName)
						&& PropertyUtils.isReadable(mainForm, ptName)) {
					String key = property.getMessageKey();
					String keyVal = ResourceUtil.getString(key);
					String str = unitString.replace("!{messageKey}", keyVal);
					try {
						Object modelval = PropertyUtils.getProperty(model,
								ptName);
						Object form = PropertyUtils.getProperty(mainForm,
								ptName);
						if (modelval == form) {
							continue;
						}
						if (modelval != null && form != null
								&& modelval.toString().equals(form)) {
							continue;
						}
						if (modelval == null) {
							str = str.replace("!{modelval}", " null ");
						} else {
							str = str.replace("!{modelval}", modelval
									.toString());
						}
						if (form == null) {
							str = str.replace("!{formval}", " null ");
						} else {
							str = str.replace("!{formval}", form.toString());
						}
						buffer.append(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return buffer.toString();
	}

	private String buildAddLogInfo(TicSoapSettingForm mainForm) {
		String className = mainForm.getModelClass().getName();
		SysDictModel dictModel = SysDataDict.getInstance().getModel(className);
		List<SysDictCommonProperty> properties = dictModel.getPropertyList();
		StringBuffer buffer = new StringBuffer("添加新配置项：<br>");

		for (SysDictCommonProperty property : properties) {
			String ptName = property.getName();
			// if(!property.isCanDisplay()){
			// continue;
			// }
			// 只处理简单类型
			if (property instanceof SysDictSimpleProperty) {
				if (PropertyUtils.isReadable(mainForm, ptName)) {
					String key = property.getMessageKey();
					String keyVal = ResourceUtil.getString(key);
					try {
						Object result = PropertyUtils.getProperty(mainForm,
								ptName);
						String realVal = result == null ? "null" : result
								.toString();
						buffer.append(keyVal + ":" + realVal);
						buffer.append("<br>");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return buffer.toString();
	}

	// 日志类型 1 添加,2 更新, 3 删除
	public void logSave(TicSoapSettingForm mainForm,
			HttpServletRequest request, Object ext, int type, String fdAppType)
			throws Exception {
		String fdEnviromentId = request.getParameter("fdEnviromentId");
		TicSoapSetting model = null;
		if (mainForm != null) {
			model = (TicSoapSetting) getServiceImp(request)
					.findByPrimaryKey(mainForm.getFdId());
		} else {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				model = (TicSoapSetting) getServiceImp(request)
						.findByPrimaryKey(fdId);
			}
		}
		if (StringUtil.isNull(fdAppType)) {
			if (mainForm != null) {
				fdAppType = mainForm.getFdAppType();
			} else if (model != null) {
				fdAppType = model.getFdAppType();
			}
		}

		TicCoreLogOpt ticCoreLogOpt = new TicCoreLogOpt();
		ticCoreLogOpt.setFdPerson(UserUtil.getUser().getFdName());
		ticCoreLogOpt.setFdAlertTime(new Date());
		ticCoreLogOpt.setFdUrl(request.getRequestURL().toString() + "?"
				+ request.getQueryString());
		ticCoreLogOpt.setFdAppType(fdAppType);
		if (StringUtil.isNotNull(fdEnviromentId)) {
			ticCoreLogOpt.setFdEnviromentId(fdEnviromentId);
		} else {
			ticCoreLogOpt.setFdEnviromentId(model.getFdEnviromentId());
		}
		String xml = "";
		switch (type) {
		case 1:
			xml += buildAddLogInfo(mainForm);
			break;
		case 2:
			xml = logInfoBuilder(mainForm, model);

			break;
		case 3:
			StringBuffer sb = new StringBuffer();
			sb.append(ResourceUtil.getString(
					"ticSoapSetting.lang.deleteConfigItem",
					"tic-soap-connector")
					+ "<br>");
			sb.append(ext.toString());
			xml += sb.toString();
			break;
		}
		ITicCoreLogOptService ticCoreLogOptService = (ITicCoreLogOptService) SpringBeanUtil
				.getBean("ticCoreLogOptService");
		ticCoreLogOpt.setFdContent(xml);
		if (!("".equals(xml))) {
			ticCoreLogOptService.add(ticCoreLogOpt);
		}
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String categoryId = request.getParameter("categoryId");
		String hql = hqlInfo.getWhereBlock();
		// hql=StringUtil.linkString(hql, " and ",
		// "ticSoapSetting.docIsNewVersion = :docIsNewVersion");
		// hqlInfo.setParameter("docIsNewVersion", true);
		if (!StringUtil.isNull(categoryId)) {
			hql = StringUtil
					.linkString(hql, " and ",
							"ticSoapSetting.settCategory.fdHierarchyId like :fdHierarchyId ");
			hqlInfo.setParameter("fdHierarchyId", "%" + categoryId + "%");
		}
		hqlInfo.setWhereBlock(hql);

	}

	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			TicSoapSetting model = (TicSoapSetting) getServiceImp(request)
					.findByPrimaryKey(id, null, true);
			String pass = model.getFdPassword();
			if (StringUtil.isNotNull(pass) && !PasswordUtil.isEncrypted(pass)) {
				pass = PasswordUtil.encodePassword(pass);
				model.setFdPassword(pass);
			}
			if (model != null) {
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
			}
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}
}
