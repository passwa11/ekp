package com.landray.kmss.sys.attend.actions;

import com.google.common.collect.Lists;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attend.cache.SysAttendUserCacheUtil;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryDeductForm;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryForm;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryWorktimeForm;
import com.landray.kmss.sys.attend.forms.SysAttendImportForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate;
import com.landray.kmss.sys.attend.model.SysAttendCategoryLocation;
import com.landray.kmss.sys.attend.model.SysAttendCategoryRule;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTemplate;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTime;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendSynConfig;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryATemplateService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryPluginService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryTemplateService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendSynConfigService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendPlugin;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.time.util.SysTimeImportUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 签到事项 Action
 * 
 * @author
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryAction extends SysAttendImportAction {
	protected ISysAttendCategoryService sysAttendCategoryService;
	protected ISysAttendCategoryPluginService sysAttendCategoryPluginService;
	protected ISysAttendMainService sysAttendMainService;
	protected ISysAttendCategoryTemplateService sysAttendCategoryTemplateService;
	protected ISysAttendCategoryATemplateService sysAttendCategoryATemplateService;

	@Override
    protected ISysAttendCategoryService getServiceImp(HttpServletRequest request) {
		if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) getBean("sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}

	protected ISysAttendCategoryPluginService getSysAttendCategoryPluginService() {
		if (sysAttendCategoryPluginService == null) {
			sysAttendCategoryPluginService = (ISysAttendCategoryPluginService) getBean(
					"sysAttendCategoryPluginService");
		}
		return sysAttendCategoryPluginService;
	}

	protected ISysAttendMainService getSysAttendMainService() {
		if (sysAttendMainService == null) {
			sysAttendMainService = (ISysAttendMainService) getBean("sysAttendMainService");
		}
		return sysAttendMainService;
	}

	public ISysAttendCategoryTemplateService getSysAttendCategoryTemplService() {
		if (sysAttendCategoryTemplateService == null) {
			sysAttendCategoryTemplateService = (ISysAttendCategoryTemplateService) getBean(
					"sysAttendCategoryTemplateService");
		}
		return sysAttendCategoryTemplateService;
	}

	public ISysAttendCategoryATemplateService getSysAttendCategoryATemplateService() {
		if (sysAttendCategoryATemplateService == null) {
			sysAttendCategoryATemplateService = (ISysAttendCategoryATemplateService) getBean(
					"sysAttendCategoryATemplateService");
		}
		return sysAttendCategoryATemplateService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}
	
	private ISysAttendSynConfigService sysAttendSynConfigService;

	public ISysAttendSynConfigService getSysAttendSynConfigService() {
		if (sysAttendSynConfigService == null) {
			sysAttendSynConfigService = (ISysAttendSynConfigService) SpringBeanUtil.getBean("sysAttendSynConfigService");
		}
		return sysAttendSynConfigService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.createNewForm(mapping, form, request, response);
		SysAttendCategoryForm sysAttendCategoryForm = (SysAttendCategoryForm) form;
		sysAttendCategoryForm.setDocCreatorId(UserUtil.getUser().getFdId());
		sysAttendCategoryForm.setDocCreatorName(UserUtil.getUser().getFdName());
		sysAttendCategoryForm.setDocCreateTime(
				DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));
		sysAttendCategoryForm.setFdStatus(AttendConstant.DOING);
		sysAttendCategoryForm.setFdWeek(StringUtil.join(new String[] { AttendConstant.MONDAY, AttendConstant.TUESDAY,
				AttendConstant.WENDSDAY, AttendConstant.THURSDAY, AttendConstant.FRIDAY }, ";"));
		sysAttendCategoryForm.setFdPeriodType(AttendConstant.FDPERIODTYPE_WEEK);
		sysAttendCategoryForm.setFdWork(AttendConstant.FDWORK_ONCE);
		sysAttendCategoryForm.setFdNotifyOnTime("0");
		sysAttendCategoryForm.setFdNotifyOffTime("0");
		sysAttendCategoryForm.setFdQRCodeTime("60");
		sysAttendCategoryForm.setFdMinHour(AttendConstant.FD_MIN_HOUR);
		sysAttendCategoryForm.setFdManagerId(UserUtil.getUser().getFdId());
		sysAttendCategoryForm.setFdManagerName(UserUtil.getUser().getFdName());
		sysAttendCategoryForm.setFdIsOvertime(true);
		sysAttendCategoryForm.setFdOvtReviewType(0);
		sysAttendCategoryForm.setFdOsdReviewType("0");
		sysAttendCategoryForm
				.setFdEffectTime(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));
		sysAttendCategoryForm.setFdIsPatch("true");
		
		sysAttendCategoryForm.setFdIsOvertimeDeduct(false);
		sysAttendCategoryForm.setFdOvtDeductType(0);
		AutoArrayList overtimeDeducts = new AutoArrayList(
				SysAttendCategoryDeductForm.class);
		SysAttendCategoryDeductForm form2 = new SysAttendCategoryDeductForm();
		form2.setFdStartTime("1970-01-01 12:00:00");
		form2.setFdEndTime("1970-01-01 13:00:00");
		form2.setFdThreshold("8");
		form2.setFdDeductHours("1");
		overtimeDeducts.add(form2);
		sysAttendCategoryForm.setOvertimeDeducts(overtimeDeducts);
		
		request.setAttribute("fdLimit", 500);
		request.setAttribute("fdLateTime", 1);
		request.setAttribute("fdLeftTime", 1);
		request.setAttribute("fdOnTime1", "09:00");
		request.setAttribute("fdOffTime1", "18:00");
		request.setAttribute("fdWTAvailable1", "true");
		request.setAttribute("fdWTAvailable2", "false");
		// 判断是否集成启用kk
		boolean isEnableKKConfig = AttendUtil.isEnableKKConfig();
		if (!isEnableKKConfig) {
			sysAttendCategoryForm.setFdSecurityMode(null);
		}
		request.setAttribute("isEnableKKConfig", isEnableKKConfig);
		// 判断是否继承启动钉钉
		boolean isEnableDingConfig = AttendUtil.isEnableDingConfig();
		request.setAttribute("isEnableDingConfig", isEnableDingConfig);
		// 判断是否继承启动企业微信
		boolean isEnableWxConfig = AttendUtil.isEnableWx();
		request.setAttribute("isEnableWxConfig", isEnableWxConfig);
		// 考勤同步来源
		SysAttendSynConfig config = getSysAttendSynConfigService().getSysAttendSynConfig();
		if (config != null) {
			request.setAttribute("synConfigType", config.getFdSynType());
		}
				
		String type = request.getParameter("type");
		if ("custom".equals(type)) { // 自定义签到，否则为考勤
			sysAttendCategoryForm.setFdType(String.valueOf(AttendConstant.FDTYPE_CUST));
		} else {
			sysAttendCategoryForm.setFdType(String.valueOf(AttendConstant.FDTYPE_ATTEND));
		}
		sysAttendCategoryForm.setFdStartTime("00:00");
		sysAttendCategoryForm.setFdEndDay("1");
		sysAttendCategoryForm.setFdEndTime("23:59");

		// 设置场所
		if (form instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}

		// 从第三方模块发起签到事项，初始化数据
		String fdModelName = request.getParameter("fdModelName");
		String fdModelId = request.getParameter("fdModelId");
		if (StringUtil.isNotNull(fdModelName) && StringUtil.isNotNull(fdModelId)) {
			getSysAttendCategoryPluginService().initFormSetting(sysAttendCategoryForm, fdModelName, fdModelId);
			request.setAttribute("fromModel", true);
		}
		// 签到组分类
		String fdTemplateId = request.getParameter("fdTemplateId");
		if (StringUtil.isNotNull(fdTemplateId)) {
			SysAttendCategoryTemplate template = (SysAttendCategoryTemplate) getSysAttendCategoryTemplService()
					.findByPrimaryKey(fdTemplateId, null, true);
			if (template != null) {
				sysAttendCategoryForm.setFdTemplateName(template.getFdName());
				sysAttendCategoryForm.setFdTemplateId(fdTemplateId);
				// 默认可阅读者
				List readerIds = template.getAuthTmpReaders();
				if (readerIds != null && !readerIds.isEmpty()) {
					String[] strArr = ArrayUtil.joinProperty(readerIds, "fdId:fdName", ";");
					sysAttendCategoryForm.setAuthReaderIds(strArr[0]);
					sysAttendCategoryForm.setAuthReaderNames(strArr[1]);
				}
				// 默认可编辑者
				List editorIds = template.getAuthTmpEditors();
				if (editorIds != null && !editorIds.isEmpty()) {
					String[] strArr = ArrayUtil.joinProperty(editorIds, "fdId:fdName", ";");
					sysAttendCategoryForm.setAuthEditorIds(strArr[0]);
					sysAttendCategoryForm.setAuthEditorNames(strArr[1]);
				}
			}
		}
		// 考勤组分类
		String fdATemplateId = request.getParameter("fdATemplateId");
		if (StringUtil.isNotNull(fdATemplateId)) {
			SysAttendCategoryATemplate atemplate = (SysAttendCategoryATemplate) getSysAttendCategoryATemplateService()
					.findByPrimaryKey(fdATemplateId, null, true);
			if (atemplate != null) {
				sysAttendCategoryForm.setFdATemplateName(atemplate.getFdName());
				sysAttendCategoryForm.setFdATemplateId(fdATemplateId);
				// 默认可阅读者
				List readerIds = atemplate.getAuthTmpReaders();
				if (readerIds != null && !readerIds.isEmpty()) {
					String[] strArr = ArrayUtil.joinProperty(readerIds, "fdId:fdName", ";");
					sysAttendCategoryForm.setAuthReaderIds(strArr[0]);
					sysAttendCategoryForm.setAuthReaderNames(strArr[1]);
				}
				// 默认可编辑者
				List editorIds = atemplate.getAuthTmpEditors();
				if (editorIds != null && !editorIds.isEmpty()) {
					String[] strArr = ArrayUtil.joinProperty(editorIds, "fdId:fdName", ";");
					sysAttendCategoryForm.setAuthEditorIds(strArr[0]);
					sysAttendCategoryForm.setAuthEditorNames(strArr[1]);
				}
			}
		}
		sysAttendCategoryForm.setFdShiftType("0");
		sysAttendCategoryForm.setFdSameWorkTime("0");
		sysAttendCategoryForm.setFdRestStartTime("12:00");
		sysAttendCategoryForm.setFdRestEndTime("13:00");
		sysAttendCategoryForm.setFdTotalTime("8");
		sysAttendCategoryForm.setFdIsAllowView("true");

		return sysAttendCategoryForm;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		SysAttendCategoryForm cateForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id, null, true);
			if (model != null) {
				UserOperHelper.logFind(model);// 添加日志信息
			}
			rtnForm = getServiceImp(request).convertModelToForm((IExtendForm) form, model, new RequestContext(request));
			// 兼容代码
			cateForm = (SysAttendCategoryForm) rtnForm;
			if ("1".equals(cateForm.getFdType())) {// 考勤
				String fdPeriodType = cateForm.getFdPeriodType();
				boolean isTimeArea = "1".equals(cateForm.getFdShiftType());
				//综合工时
				boolean isComprehensive = "3".equals(cateForm.getFdShiftType());
				//不固定工作时间
				boolean isUnfixed = "4".equals(cateForm.getFdShiftType());
				if ("1".equals(fdPeriodType) && !isTimeArea && !isComprehensive && StringUtil.isNull(cateForm.getFdSameWorkTime())) {// 原：周期
					// 固定周期
					if(!isUnfixed){
						cateForm.setFdShiftType("0");
					}

					// 一周内相同工作时间
					cateForm.setFdSameWorkTime("0");
				} else if ("2".equals(fdPeriodType) && !isTimeArea) {// 原：自定义
					// 自定义
					cateForm.setFdShiftType("2");
				}
				if(StringUtil.isNotNull(cateForm.getFdMinHour()) && cateForm.getFdMinOverTime() ==null){
					cateForm.setFdMinOverTime(Integer.valueOf(cateForm.getFdMinHour())*60);
				}

				// 判断是否集成启用kk
				boolean isEnableKKConfig = AttendUtil.isEnableKKConfig();
				request.setAttribute("isEnableKKConfig", isEnableKKConfig);

				// 判断是否继承启动钉钉
				boolean isEnableDingConfig = AttendUtil.isEnableDingConfig();
				request.setAttribute("isEnableDingConfig", isEnableDingConfig);
				
				// 判断是否继承启动企业微信
				boolean isEnableWxConfig = AttendUtil.isEnableWx();
				request.setAttribute("isEnableWxConfig", isEnableWxConfig);

				// 考勤同步来源
				SysAttendSynConfig config = getSysAttendSynConfigService().getSysAttendSynConfig();
				if (config != null) {
					request.setAttribute("synConfigType", config.getFdSynType());
				}

				// 默认允许补卡
				if (cateForm.getFdIsPatch() == null) {
					cateForm.setFdIsPatch("true");
				}
			}
		}
		if (cateForm == null) {
			throw new NoRecordException();
		}
		
		AutoArrayList list = cateForm.getOvertimeDeducts();
		// 加班扣除设置默认值
		if (list != null && list.size() > 0) {
			SysAttendCategoryDeductForm first = (SysAttendCategoryDeductForm) list
					.get(0);
			if (StringUtil.isNull(first.getFdStartTime())
					|| StringUtil.isNull(first.getFdEndTime())) {
				first.setFdStartTime("1970-01-01 12:00:00");
				first.setFdEndTime("1970-01-01 13:00:00");
			}
			if (StringUtil.isNull(first.getFdThreshold())
					|| StringUtil.isNull(first.getFdDeductHours())) {
				first.setFdThreshold("8");
				first.setFdDeductHours("1");
			}
		} else if (list == null || list.size() == 0) {
			list = new AutoArrayList(
					SysAttendCategoryDeductForm.class);
			SysAttendCategoryDeductForm temp = new SysAttendCategoryDeductForm();
			temp.setFdStartTime("1970-01-01 12:00:00");
			temp.setFdEndTime("1970-01-01 13:00:00");
			temp.setFdThreshold("8");
			temp.setFdDeductHours("1");
			list.add(temp);
			cateForm.setOvertimeDeducts(list);
		}
		
		request.setAttribute(getFormName(rtnForm, request), cateForm);
	}

	@Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();

		this.saveCategory(form,request,messages);
		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return add(mapping, form, request, response);
		}
	}

	/**
	 * 提起公用方法。保存，和保存新建
	 * @param form
	 * @param request
	 * @param messages
	 */
	private void saveCategory( ActionForm form, HttpServletRequest request,KmssMessages messages){
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			//#119332 在保存之前把无效的排班时间移除，避免保存多余的排班信息
			SysAttendCategoryForm mainForm = (SysAttendCategoryForm) form;
			removeWorkTime(mainForm);
			String categoryId = getServiceImp(request).add((IExtendForm) form, new RequestContext(request));
			//重新生效有效考勤记录。
			resetAttendMain(null,categoryId);
		} catch (Exception e) {
			messages.addError(e);
		}
	}

	/**
	 * 删除无效的 排班信息
	 * @param mainForm
	 */
	private void removeWorkTime(SysAttendCategoryForm mainForm){
		AutoArrayList fdWorkTimes = mainForm.getFdWorkTime();
		if(CollectionUtils.isNotEmpty(fdWorkTimes)){
			Iterator it = fdWorkTimes.iterator();
			while (it.hasNext()) {
				SysAttendCategoryWorktimeForm wor = (SysAttendCategoryWorktimeForm) it.next();
				if(!"true".equalsIgnoreCase(wor.getFdIsAvailable())){
					it.remove();
				}
			}
		}
	}
	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		this.saveCategory(form,request,messages);
		SysAttendCategoryForm mainForm = (SysAttendCategoryForm) form;
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			String fdAppUrl = mainForm.getFdAppUrl();
			if (StringUtil.isNotNull(fdAppUrl)) {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton("button.back", mainForm.getFdAppUrl() + "&showtab=attend", false)
						.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			}

			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			SysAttendCategoryForm cateForm = (SysAttendCategoryForm) form;

			//该考勤组保存之前的所有打卡人
			SysAttendCategory categoryOld = (SysAttendCategory) getServiceImp(request).findByPrimaryKey(cateForm.getFdId());
			cateForm.setFdOldStatusFlag(categoryOld.getFdStatus());
			List<String> orgListOld = getSysOrgCoreService().expandToPersonIds(categoryOld.getFdTargets());
			//清理该考勤组对应的所有人员 今日所属的考勤组缓存
			SysAttendUserCacheUtil.clearUserCache(orgListOld,new Date());
			//执行考勤组更新
			getServiceImp(request).update((IExtendForm) form, new RequestContext(request));
			String fdStatusFlag = request.getParameter("fdStatusFlag");
			if(CategoryUtil.ENABLE_FLAG.equalsIgnoreCase(fdStatusFlag)){
				//立即生效。更新当日已经发送的考勤记录
				resetAttendMain(orgListOld,cateForm.getFdId());
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			// 返回按钮
			IExtendForm mainForm = (IExtendForm) form;
			String fdModelId = mainForm.getFdId();
			String fdModelName = mainForm.getModelClass().getName();
			SysDictModel model = SysDataDict.getInstance().getModel(fdModelName);
			if (model != null && !StringUtil.isNull(model.getUrl())) {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton("button.back", formatModelUrl(fdModelId, model.getUrl()), false).save(request);
			}

			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 重新生成有效考勤记录
	 * @param orgListOld 历史人员
	 * @param categoryId 考勤组ID。(原始考勤组的ID)
	 * @throws Exception
	 */
	private void resetAttendMain(List<String> orgListOld,String categoryId) throws Exception {
		//重新统计当日考勤记录
		SysAttendCategory category = (SysAttendCategory) getServiceImp(null).findByPrimaryKey(categoryId);
		//考情组才执行。签到组不执行
		if(CategoryUtil.CATEGORY_FD_TYPE_TRUE.equals(category.getFdType())) {
			//考勤组更新以后的打卡人
			List<String> orgListNew = getSysOrgCoreService().expandToPersonIds(category.getFdTargets());
			//考勤组原人员和新人员合并处理。
			Set<String> orgList = new HashSet<String>();
			if(CollectionUtils.isNotEmpty(orgListNew)){
				orgList.addAll(orgListNew);
			}
			if(CollectionUtils.isNotEmpty(orgListOld)) {
				//合并新老人员
				orgList.addAll(orgListOld);
			} else {
				orgList.addAll(orgListNew);
			}
			if(CollectionUtils.isNotEmpty(orgList)) {
				this.getServiceImp(null).updateAttendMainRecord(category.getFdId(),Lists.newArrayList(orgList),UserUtil.getUser());
			}
		}
	}

	private String formatModelUrl(String value, String url) {
		if (StringUtil.isNull(url)) {
			return null;
		}
		Pattern p = Pattern.compile("\\$\\{([^}]+)\\}");
		Matcher m = p.matcher(url);
		while (m.find()) {
			String property = m.group(1);
			try {
				url = StringUtil.replace(url, "${" + property + "}", value);
			} catch (Exception e) {
			}
		}
		return url;
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		StringBuffer sb = new StringBuffer();
		String[] fdStatus = cv.polls("fdStatus");
		if (fdStatus != null && fdStatus.length > 0) {
			sb.append(HQLUtil.buildLogicIN("sysAttendCategory.fdStatus", Arrays.asList(fdStatus)));
		} else {
			sb.append("sysAttendCategory.fdStatus!=3");
		}
		String docSubject = cv.poll("docSubject");
		if (StringUtil.isNotNull(docSubject)) {
			sb.append(" and sysAttendCategory.fdName like :fdName");
			hqlInfo.setParameter("fdName", "%" + docSubject + "%");
		}
		String fdName = request.getParameter("fdName");
		if (StringUtil.isNotNull(fdName)) {
			sb.append(" and sysAttendCategory.fdName like :fdName");
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
		}
		String fdType = request.getParameter("type");
		if (StringUtil.isNotNull(fdType)) {
			if ("attend".equals(fdType)) {
				sb.append(" and sysAttendCategory.fdType=1");
			} else if ("custom".equals(fdType)) {
				sb.append(" and sysAttendCategory.fdType=2");
				// 签到组
				String appKey = request.getParameter("appKey");
				String appName = request.getParameter("appName");// 兼容
				if (StringUtil.isNotNull(appKey)) {
					sb.append(" and (sysAttendCategory.fdAppKey=:fdAppKey or sysAttendCategory.fdAppName=:fdAppName)");
					hqlInfo.setParameter("fdAppKey", appKey);
					hqlInfo.setParameter("fdAppName", appName);
				} else {
					sb.append(" and (sysAttendCategory.fdAppId is null or sysAttendCategory.fdAppId='')");
				}
			}
		}
		String fdTemplateId = cv.poll("fdTemplate");
		if (StringUtil.isNotNull(fdTemplateId)) {
			sb.append(" and sysAttendCategory.fdTemplate.fdId =:fdTemplateId");
			hqlInfo.setParameter("fdTemplateId", fdTemplateId);
		}
		String fdATemplateId = cv.poll("fdATemplate");
		if (StringUtil.isNotNull(fdATemplateId)) {
			sb.append(" and sysAttendCategory.fdATemplate.fdId =:fdATemplateId");
			hqlInfo.setParameter("fdATemplateId", fdATemplateId);
		}
		String fdATemplateIds = request.getParameter("fdATemplateIds");
		if (StringUtil.isNotNull(fdATemplateIds)) {
			sb.append(" and " + HQLUtil.buildLogicIN("sysAttendCategory.fdATemplate.fdId",
					Arrays.asList(fdATemplateIds.split("[,;]"))));
		}
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		hqlInfo.setWhereBlock(sb.toString());
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendCategory.class);
	}

	public ActionForward updateStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateStatus", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			String fdStatusFlag = request.getParameter("fdStatusFlag");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).updateCategoryOver(id,fdStatusFlag);
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton("button.back", "sysAttendCategory.do?method=view&fdId=" + id, false).save(request);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-updateStatus", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward findConflictElement(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray conflictArray = new JSONArray();
			String orgIds = request.getParameter("orgIds");
			String categoryId = request.getParameter("categoryId");
			if (StringUtil.isNotNull(orgIds)) {
				conflictArray = getServiceImp(request).findConflictElement(orgIds, categoryId);
			}
			request.setAttribute("lui-source", conflictArray);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return mapping.findForward("lui-source");
		}
	}

	/**
	 * PC端获取会议签到配置信息，生成二维码等
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewdata(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		JSONArray array = new JSONArray();
		try {
			String appId = request.getParameter("appId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("sysAttendCategory.fdAppId=:appId and sysAttendCategory.fdStatus!=3");
			hqlInfo.setParameter("appId", appId);
			hqlInfo.setOrderBy("sysAttendCategory.docCreateTime desc");
			List<SysAttendCategory> categories = getServiceImp(request).findList(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(categories, getServiceImp(request).getModelName());
			// SysAttendCategory category = findSysAttendCategory(request);
			for (SysAttendCategory category : categories) {
				JSONObject result = new JSONObject();
				result.accumulate("fdId", category.getFdId());
				result.accumulate("fdType", category.getFdType());
				List<SysAttendCategoryTime> sysAttendCategoryTimes = category.getFdTimes();
				List<SysAttendCategoryLocation> cateLocations = category.getFdLocations();
				List<SysAttendCategoryRule> fdRuleList = category.getFdRule();

				if (sysAttendCategoryTimes != null && sysAttendCategoryTimes.size() > 0) {
					JSONArray times = new JSONArray();
					Date fdInTime = null;
					// fdTime签到日期，fdInTime签到时间
					for (SysAttendCategoryTime time : sysAttendCategoryTimes) {
						Date fdTime = time.getFdTime();
						if (fdRuleList != null && !fdRuleList.isEmpty()) {
							fdInTime = fdRuleList.get(0).getFdInTime();
							if (fdInTime != null) {
								fdTime.setHours(fdInTime.getHours());
								fdTime.setMinutes(fdInTime.getMinutes());
							}
						}
						times.add(DateUtil.convertDateToString(fdTime, DateUtil.TYPE_DATETIME, null));
					}
					// fdTimes签到日期时间，fdInTime时间戳
					result.accumulate("fdTimes", times);
					result.accumulate("fdInTime", fdInTime == null ? 0 : fdInTime.getTime());
				}
				if (cateLocations != null && !cateLocations.isEmpty()) {
					JSONArray locations = new JSONArray();
					for (SysAttendCategoryLocation fdLocation : cateLocations) {
						JSONObject locObj = new JSONObject();
						locObj.accumulate("address", fdLocation.getFdLocation());
						locObj.accumulate("coord", fdLocation.getFdLatLng());
						locations.add(locObj);
					}
					result.accumulate("fdLocations", locations);
				}
				if (fdRuleList != null && !fdRuleList.isEmpty()) {
					result.accumulate("fdLimit", fdRuleList.get(0).getFdLimit());
				}
				boolean isfdAttender = UserUtil.checkUserModels(category.getFdTargets());
				if (category.getFdUnlimitTarget()) {
					isfdAttender = true;
				}
				result.accumulate("isfdAttender", isfdAttender);
				result.accumulate("isfdManager", UserUtil.checkUserId(category.getFdManager().getFdId()));
				result.accumulate("fdStartTime",
						DateUtil.convertDateToString(category.getFdStartTime(), DateUtil.TYPE_TIME, null));
				result.accumulate("fdEndTime",
						DateUtil.convertDateToString(category.getFdEndTime(), DateUtil.TYPE_TIME, null));
				result.accumulate("fdStatus", category.getFdStatus());
				result.accumulate("fdPermState",
						"true".equals(category.getFdPermState()) ? "1" : "0");
				result.accumulate("fdQRCodeUrl",
						StringUtil.formatUrl("/resource/sys/attend/sysAttendAnym.do?method=scanToSign&categoryId="
								+ category.getFdId() + "&qrCodeTime=" + category.getFdQRCodeTime()));
				array.add(result);
			}

			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	/**
	 * 获取会议签到记录数据，如人数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward stat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		JSONArray array = new JSONArray();
		try {
			String appId = request.getParameter("appId");
			HQLInfo hql = new HQLInfo();
			hql.setWhereBlock("sysAttendCategory.fdAppId=:appId");
			hql.setParameter("appId", appId);
			hql.setOrderBy("sysAttendCategory.docCreateTime desc");
			String fdCateogryId = request.getParameter("fdCategoryId");
			if (StringUtil.isNotNull(fdCateogryId)) {
				hql.setWhereBlock(hql.getWhereBlock() + " and sysAttendCategory.fdId=:fdCategoryId");
				hql.setParameter("fdCategoryId", fdCateogryId);
			}
			List<SysAttendCategory> categories = getServiceImp(request).findList(hql);
			ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
			for (SysAttendCategory category : categories) {
				JSONObject result = new JSONObject();
				result.put("fdId", category.getFdId());
				List<String> elemIds = sysOrgCoreService.expandToPersonIds(category.getFdTargets());
				List<String> excTargets = sysOrgCoreService.expandToPersonIds(category.getFdExcTargets());
				if (!excTargets.isEmpty()) {
					elemIds.removeAll(excTargets);
				}
				result.accumulate("count", elemIds.size());// 应签到人数
				// 正常签到人数
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"sysAttendMain.fdCategory.fdId=:categoryId and sysAttendMain.fdStatus=1 and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
				hqlInfo.setParameter("categoryId", category.getFdId());
				List<SysAttendMain> mains = getSysAttendMainService().findList(hqlInfo);
				// 已签到人数
				HQLInfo hqlInfo1 = new HQLInfo();
				hqlInfo1.setWhereBlock(
						"sysAttendMain.fdCategory.fdId=:categoryId and sysAttendMain.fdStatus>0 and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
				hqlInfo1.setParameter("categoryId", category.getFdId());
				List<SysAttendMain> signedList = getSysAttendMainService().findList(hqlInfo1);
				// 未签到人数
				HQLInfo hqlInfo2 = new HQLInfo();
				hqlInfo2.setWhereBlock(
						"sysAttendMain.fdCategory.fdId=:categoryId and sysAttendMain.fdStatus=0 and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
				hqlInfo2.setParameter("categoryId", category.getFdId());
				List<SysAttendMain> unSignedList = getSysAttendMainService().findList(hqlInfo2);

				result.accumulate("attendcount", signedList.size()); // 已签到人数
				result.accumulate("unattendcount", unSignedList.size()); // 未签到人数
				result.accumulate("normalcount", mains.size()); // 正常签到人数
				result.accumulate("latecount", signedList.size() - mains.size()); // 迟到人数
				// 是否可以查看统计数据
				result.accumulate("isStatSignReader", getServiceImp(request).isStatSignReader(category));
				array.add(result);
				String modelName = getServiceImp(request).getModelName();
				if (UserOperHelper.allowLogOper("stat", getServiceImp(request).getModelName())) {
					UserOperContentHelper.putFind("attendcount", result.getString("attendcount"), modelName);
					UserOperContentHelper.putFind("unattendcount", result.getString("unattendcount"), modelName);
					UserOperContentHelper.putFind("normalcount", result.getString("normalcount"), modelName);
					UserOperContentHelper.putFind("latecount", result.getString("latecount"), modelName);
				}
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	/**
	 * 签到统计
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listStat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-listStat", true, getClass());
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

			if (isReserve) {
				orderby += " desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(
					"sysAttendCategory.fdType=2 and sysAttendCategory.fdStatus in(1,2) and (sysAttendCategory.fdAppId is null or sysAttendCategory.fdAppId='')");
			hqlInfo.setOrderBy("sysAttendCategory.fdStatus asc,sysAttendCategory.docCreateTime desc");
			hqlInfo.setAuthCheckType("cateStat");

			Page page = getServiceImp(request).findPage(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			// 统计
			Map<String, JSONObject> countMap = new HashMap<String, JSONObject>();
			List<SysAttendCategory> list = page.getList();
			ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
			com.alibaba.fastjson.JSONArray arrays = getServiceImp(request).filterAttendCategory(list, null, false, null);
			for (SysAttendCategory cate : list) {
				String fdId = cate.getFdId();
				boolean signing = false;
				for (int i = 0; i < arrays.size(); i++) {
					com.alibaba.fastjson.JSONObject json = (com.alibaba.fastjson.JSONObject) arrays.get(i);
					if (fdId.equals(json.getString("fdId")) && cate.getFdStatus() == 1) {
						signing = true;
						break;
					}
				}

				List<String> elemIds = sysOrgCoreService.expandToPersonIds(cate.getFdTargets());

				// 已签到人数
				HQLInfo hqlInfo1 = new HQLInfo();
				hqlInfo1.setSelectBlock("count(distinct sysAttendMain.docCreator.fdId)");
				hqlInfo1.setWhereBlock(
						"sysAttendMain.docCreateTime>=:beginTime and sysAttendMain.docCreateTime<:endTime and sysAttendMain.fdCategory.fdId=:categoryId and sysAttendMain.fdStatus>0 and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
				hqlInfo1.setParameter("categoryId", fdId);
				hqlInfo1.setParameter("beginTime", DateUtil.getDate(0));
				hqlInfo1.setParameter("endTime", DateUtil.getDate(1));
				List signedList = getSysAttendMainService().findValue(hqlInfo1);
				Number result = (Number) signedList.get(0);
				int signCount = (int) result.longValue();

				JSONObject json = new JSONObject();
				json.put("count", elemIds.size());
				json.put("signCount", (int) result.longValue());
				int unsignCount = signing || result.intValue() > 0 ? elemIds.size() - signCount : 0;
				json.put("unsignCount", unsignCount);
				countMap.put(fdId, json);

			}
			request.setAttribute("queryPage", page);
			request.setAttribute("countMap", countMap);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-listStat", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listStat", mapping, form, request, response);
		}
	}

	@SuppressWarnings("unchecked")
	private SysAttendCategory findSysAttendCategory(HttpServletRequest request) throws Exception {
		SysAttendCategory category = null;
		String categoryId = request.getParameter("categoryId");
		String appId = request.getParameter("appId");
		if (StringUtil.isNotNull(categoryId)) {
			category = (SysAttendCategory) getServiceImp(request).findByPrimaryKey(categoryId);
		} else if (StringUtil.isNotNull(appId)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("sysAttendCategory.fdAppId=:appId");
			hqlInfo.setParameter("appId", appId);
			List<SysAttendCategory> categories = getServiceImp(request).findList(hqlInfo);
			if (categories != null && categories.size() > 0) {
				category = categories.get(0);
			}
		}
		return category;
	}

	public ActionForward getCategoryMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		JSONArray array = new JSONArray();
		JSONObject result = new JSONObject();
		result.accumulate("title", "");
		try {
			JSONArray children = new JSONArray();
			JSONObject obj = new JSONObject();
			obj.accumulate("text", "本模块");
			String href = "javascript:window.open('${LUI_ContextPath }/sys/attend/sys_attend_main/index.jsp?categoryType=custom&appKey=default','_self');";
			href = href.replace("${LUI_ContextPath }", request.getContextPath());
			obj.accumulate("href", href);
			obj.accumulate("target", "_self");
			children.add(obj);
			IExtension[] extensions = AttendPlugin.getExtensions();
			for (IExtension extension : extensions) {
				obj = new JSONObject();
				obj.accumulate("text", Plugin.getParamValueString(extension, "moduleName"));
				href = "javascript:window.open('${LUI_ContextPath }/sys/attend/sys_attend_main/index.jsp?categoryType=custom&appKey=";
				href += Plugin.getParamValueString(extension, "modelKey");
				href += "&appName=" + Plugin.getParamValueString(extension, "moduleName") + "','_self');";
				href = href.replace("${LUI_ContextPath }", request.getContextPath());
				obj.accumulate("href", href);
				obj.accumulate("target", "_self");
				children.add(obj);
			}
			result.accumulate("children", children);
			array.add(result);
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	@Override
	public String getTempletName() {
		return ResourceUtil.getString("sys-attend:sysAttendCategory.import.wifi.template");
	}

	@Override
	public HSSFWorkbook buildTemplateWorkBook() {
		// 第一步，创建一个workbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(ResourceUtil.getString("sys-time:sysTime.import.sheet1.title"));
		sheet.setDefaultColumnWidth(25); // 设置宽度
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) (20 * 20));
		// 第四步，创建单元格
		HSSFCell cell = null;

		// 定义普通字体样式
		HSSFFont font1 = wb.createFont();
		font1.setBold(true); // 字体增粗
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平布局：居中
		style1.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
		style1.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
		style1.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.PALE_BLUE.index);
		style1.setFont(font1);

		// 定义必填字体样式
		HSSFFont font2 = wb.createFont();
		font2.setBold(true); // 字体增粗
		font2.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.index); // 字体颜色
		HSSFCellStyle style2 = wb.createCellStyle();
		style2.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平布局：居中
		style2.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
		style2.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
		style2.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.PALE_BLUE.index);
		style2.setFont(font2);

		/********** 设置头部内容 **********/
		// 第一列为：考勤地点名称
		int colIndex = 0;
		cell = row.createCell(colIndex++);
		cell.setCellValue(ResourceUtil.getString("sys-attend:sysAttendCategory.import.wifi.name"));
		cell.setCellStyle(style2);

		// 第二列为：mac地址
		cell = row.createCell(colIndex++);
		cell.setCellValue(ResourceUtil.getString("sys-attend:sysAttendCategory.import.wifi.mac"));
		cell.setCellStyle(style2);
		return wb;
	}

	@Override
	public KmssMessage saveImportData(ActionForm form, HttpServletRequest request, boolean isRollback) {
		Workbook wb = null;
		Sheet sheet = null;
		JSONObject result = new JSONObject();
		JSONArray data = new JSONArray();
		SysAttendImportForm importForm = (SysAttendImportForm) form;
		InputStream inputStream = null;
		try {
			inputStream = importForm.getFile().getInputStream();
			// 抽象类创建Workbook，适合excel 2003和2007以上
			wb = WorkbookFactory.create(inputStream);
			sheet = wb.getSheetAt(0);


			// 一份标准的模板，最少包含2行，第一行是字段名称，导入的数据应该是从第二行开始
			int rowNum = sheet.getLastRowNum();
			if (rowNum < 1) {
				throw new RuntimeException(ResourceUtil.getString("sys-time:sysTime.import.empty"));
			}

			KmssMessages messages = null;
			StringBuffer errorMsg = new StringBuffer();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				messages = new KmssMessages();
				JSONObject json = new JSONObject();
				Row row = sheet.getRow(i);
				if (row == null) { // 跳过空行
					continue;
				}
				int colIndex = 0;
				// 名称
				String name = SysTimeImportUtil.getCellValue(row.getCell(colIndex++));
				// 地址
				String mac = SysTimeImportUtil.getCellValue(row.getCell(colIndex++));

				if (StringUtil.isNull(mac)) {
					messages.addError(
							new KmssMessage(ResourceUtil.getString("sys-attend:sysAttendCategory.import.error.mac")));
				}
				if (isWifiRepeated(data, mac)) {
					messages.addError(new KmssMessage(
							ResourceUtil.getString("sys-attend:sysAttendCategory.import.error.mac.repeated")));
				}
				if (!messages.hasError()) {
					json.put("name", StringUtil.getString(name));
					json.put("mac", mac);
					data.add(json);
				} else {
					errorMsg.append(ResourceUtil.getString("sysTime.import.error.num", "sys-time", null, i));
					// 解析错误信息
					for (KmssMessage message : messages.getMessages()) {
						errorMsg.append(message.getMessageKey());
					}
					errorMsg.append("<br>");
				}

			}
			KmssMessage message = null;
			if (errorMsg.length() > 0) {
				if (isRollback) {
					// 如果有一条数据校验失败，则需要数据回滚。
					throw new RuntimeException(errorMsg.toString());
				} else {
					errorMsg.insert(0,
							ResourceUtil.getString("sysTime.import.portion.failed", "sys-time", null, data.size())
									+ "<br>");
					message = new KmssMessage(errorMsg.toString());
					message.setMessageType(KmssMessage.MESSAGE_ERROR);
				}
			} else {
				message = new KmssMessage(ResourceUtil.getString("sysTime.import.success", "sys-time", null, data.size()));
				message.setMessageType(KmssMessage.MESSAGE_COMMON);
			}
			result.put("data", data);
			request.setAttribute("resultData", result.toString());
			return message;
		} catch (Exception e) {
			throw new RuntimeException(ResourceUtil.getString("sys-time:sysTime.import.error"));
		} finally {
			IOUtils.closeQuietly(wb);
		}
	}

	private boolean isWifiRepeated(JSONArray array, String mac) {
		for (int i = 0; i < array.size(); i++) {
			JSONObject json = (JSONObject) array.get(i);
			if (json.containsValue(mac)) {
				return true;
			}
		}
		return false;
	}

}
