package com.landray.kmss.hr.staff.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.staff.forms.HrStaffPersonExperienceContractForm;
import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBase;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffContractTypeService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceContractService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 合同信息
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceContractAction extends
		HrStaffPersonExperienceBaseAction {
	private IHrStaffPersonExperienceContractService hrStaffPersonExperienceContractService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrStaffPersonExperienceContractService == null) {
            hrStaffPersonExperienceContractService = (IHrStaffPersonExperienceContractService) getBean("hrStaffPersonExperienceContractService");
        }
		return hrStaffPersonExperienceContractService;
	}
	
	private IHrStaffContractTypeService hrStaffContractTypeService;

	public IHrStaffContractTypeService getHrStaffContractTypeService() {
		if (hrStaffContractTypeService == null) {
			hrStaffContractTypeService = (IHrStaffContractTypeService) getBean(
					"hrStaffContractTypeService");
		}
		return hrStaffContractTypeService;
	}

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	protected IHrStaffPersonInfoService getHrStaffPersonInfoServiceImp() {
		if (hrStaffPersonInfoService == null) {
            hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean("hrStaffPersonInfoService");
        }
		return hrStaffPersonInfoService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		// 解析其它筛选属性
		CriteriaUtil.buildHql(cv, hqlInfo,
				HrStaffPersonExperienceContract.class);
		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		}
		whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonExperienceContract", hqlInfo);
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	private String getFdContType(String fdContType, String fdRelatedProcess)
			throws Exception {
		boolean exist = getHrStaffContractTypeService()
				.checkExist(fdContType);
		if (exist) {
			return fdContType;
		} else {
			String[] str = fdContType.split("\\~");
			return HrStaffPersonUtil.getText(str[0], str[1], fdRelatedProcess);
		}
	}

	private String getFdSignType(String fdSignType, String fdRelatedProcess)
			throws Exception {
		List<String> typeList = new ArrayList<String>();
		typeList.add("1");
		typeList.add("2");
		if (typeList.contains(fdSignType)) {
			return ResourceUtil.getString(
					"hr-staff:hrStaffPersonExperience.contract.fdSignType."
							+ fdSignType);
		} else {
			String[] str = fdSignType.split("\\~");
			return HrStaffPersonUtil.getText(str[0], str[1], fdRelatedProcess);
		}
	}

	public void listData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String personInfoId = request.getParameter("personInfoId");

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"hrStaffPersonExperienceContract.fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", personInfoId);
		List<HrStaffPersonExperienceContract> list = getServiceImp(request)
				.findPage(hqlInfo).getList();

		JSONArray source = new JSONArray();
		try {
			for (int i = 0; i < list.size(); i++) {
				JSONObject json = new JSONObject();
				json.put("fdId", list.get(i).getFdId());
				Date begin = list.get(i).getFdBeginDate();
				Date end = list.get(i).getFdEndDate();
				Date fdCancelDate = list.get(i).getFdCancelDate();
				json.put("fdBeginDate",
						DateUtil.convertDateToString(begin,
								DateUtil.PATTERN_DATE));
				json.put("fdEndDate",
						DateUtil.convertDateToString(end,
								DateUtil.PATTERN_DATE));
				json.put("fdName", list.get(i).getFdName());
				if(list.get(i).getFdStaffContType()!=null){
					json.put("fdContType", list.get(i).getFdStaffContType().getFdName());
				}else{
					json.put("fdContType", "");
				}
				json.put("fdMemo", list.get(i).getFdMemo());
				String fdSignType = list.get(i).getFdSignType();
				String fdContStatus = list.get(i).getFdContStatus();
				if("1".equals(fdSignType)){
					fdSignType="首次签订";
				}else if("2".equals(fdSignType)){
					fdSignType="续订";
				}
				json.put("fdSignType", fdSignType);
				if ("1".equals(fdContStatus)){
					fdContStatus = "正常";
				} else if ("2".equals(fdContStatus)){
					fdContStatus = "已到期";
				} else if ("3".equals(fdContStatus)){
					fdContStatus = "已解除";
				}
				json.put("fdContStatus", fdContStatus);
				json.put("fdContractYear", list.get(i).getFdContractYear());
				json.put("fdContractMonth", list.get(i).getFdContractMonth());
				json.put("fdContractUnit", list.get(i).getFdContractUnit());
				source.add(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		// 子类自己处理JOSN数组
		// JSONArray array = handleJSONArray(list);

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(source);
		response.getWriter().flush();
		response.getWriter().close();
	}

	@Override
	public JSONArray handleJSONArray(List<HrStaffPersonExperienceBase> list) {
		JSONArray array = new JSONArray();
		try {
			for (HrStaffPersonExperienceBase _info : list) {
				HrStaffPersonExperienceContract info = (HrStaffPersonExperienceContract) _info;
				JSONObject obj = new JSONObject();
				obj.put("fdId", info.getFdId());
				obj.put("fdName", StringUtil.XMLEscape(info.getFdName()));
				String fdRelatedProcess = info.getFdRelatedProcess();
				String fdContType = info.getFdContType();
				if (StringUtil.isNotNull(fdContType)
						&& StringUtil.isNotNull(fdRelatedProcess)) {
					obj.put("fdContType",
							getFdContType(fdContType, fdRelatedProcess));
				} else {
					obj.put("fdContType",
							info.getFdStaffContType() != null
									? info.getFdStaffContType().getFdName()
									: fdContType);
				}
				String fdSignType = info.getFdSignType();
				if (StringUtil.isNotNull(fdSignType)) {
					obj.put("fdSignType",
							getFdSignType(fdSignType, fdRelatedProcess));
				}
				if (StringUtil.isNull(info.getFdContStatus())) {
					obj.put("fdContStatus", ResourceUtil.getString(
							"hr-staff:hrStaffPersonExperience.contract.fdContStatus.1"));
				} else {
					obj.put("fdContStatus",
							ResourceUtil.getString(
									"hr-staff:hrStaffPersonExperience.contract.fdContStatus."
											+ info.getFdContStatus()));
				}
				obj.put("fdBeginDate", DateUtil.convertDateToString(info
						.getFdBeginDate(), DateUtil.PATTERN_DATE));
				if(info.getFdContractPeriod()!=null){
					obj.put("fdContractPeriod", DateUtil.convertDateToString(info
							.getFdContractPeriod(), DateUtil.PATTERN_DATE));
				}

				obj.put("fdContractUnit", info.getFdContractUnit());
				if (Boolean.TRUE.equals(info.getFdIsLongtermContract())) {
					obj.put("fdEndDate", ResourceUtil.getString(
							"hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract.1"));
				} else {
					obj.put("fdEndDate", DateUtil.convertDateToString(info
							.getFdEndDate(), DateUtil.PATTERN_DATE));
				}
				obj.put("fdMemo", StringUtil.XMLEscape(info.getFdMemo()));
				array.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffPersonExperience.contract.templetName");
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
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			e.printStackTrace();
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
			return getActionForward("success", mapping, form, request,
					response);
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
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
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
			// 返回按钮
			IExtendForm mainForm = (IExtendForm) form;
			String fdModelId = mainForm.getFdId();
			String fdModelName = mainForm.getModelClass().getName();
			SysDictModel model = SysDataDict.getInstance()
					.getModel(fdModelName);
			if (model != null && !StringUtil.isNull(model.getUrl())) {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton(
								"button.back",
								formatModelUrl(fdModelId, model.getUrl()),
								false)
						.save(request);
			}

			return getActionForward("success", mapping, form, request,
					response);
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
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		HrStaffPersonExperienceContractForm contractForm = (HrStaffPersonExperienceContractForm) form;
		contractForm.setFdName(contractForm.getDocSubject());
		String fdRelatedProcess = contractForm.getFdRelatedProcess();
		String fdContType = contractForm.getFdContType();
		if (StringUtil.isNotNull(fdContType)
				&& StringUtil.isNotNull(fdRelatedProcess)) {
			contractForm
					.setFdContType(getFdContType(fdContType, fdRelatedProcess));
		}
		String fdSignType = contractForm.getFdSignType();
		if (StringUtil.isNotNull(fdSignType)) {
			String signType = getFdSignType(fdSignType, fdRelatedProcess);
			request.setAttribute("signType", signType);
		}
		String fdContStatus = contractForm.getFdContStatus();
		if (StringUtil.isNull(fdContStatus)) {
			contractForm.setFdContStatus("1");
		}
	}

	/**
	 * <p>合同变更</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward changeContract(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			HrStaffPersonExperienceContractForm contractForm = (HrStaffPersonExperienceContractForm) form;
			//员工信息
			HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoServiceImp()
					.findByPrimaryKey(contractForm.getFdPersonInfoId());
			request.setAttribute("hrStaffPersonInfo", hrStaffPersonInfo);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("changeContract", mapping, form, request, response);
		}
	}

	/**
	 * <p>合同续签页面</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward renewContractPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			HrStaffPersonExperienceContract contract = (HrStaffPersonExperienceContract) getServiceImp(request)
					.findByPrimaryKey(fdId);
			ActionForm newForm = createNewForm(mapping, form, request, response);
			HrStaffPersonExperienceContractForm contractForm = (HrStaffPersonExperienceContractForm) newForm;
			contractForm
					.setFdBeginDate(DateUtil.convertDateToString(contract.getFdBeginDate(), DateUtil.PATTERN_DATETIME));
			contractForm.setFdHandleDate(DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME));
			contractForm.setFdContType(contract.getFdContType());
			if(contract.getFdStaffContType() != null){
				contractForm.setFdStaffContTypeId(contract.getFdStaffContType().getFdId());
			}
			contractForm.setFdName(contract.getFdName());
			contractForm.setFdPersonInfoId(contract.getFdPersonInfo().getFdId());
			contractForm.setFdPersonInfoName(contract.getFdPersonInfo().getFdName());
			request.setAttribute(getFormName(newForm, request), newForm);

			//员工信息
			HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoServiceImp()
					.findByPrimaryKey(contract.getFdPersonInfo().getFdId());
			request.setAttribute("hrStaffPersonInfo", hrStaffPersonInfo);
			//历史合同信息
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"hrStaffPersonExperienceContract.fdPersonInfo.fdId =:fdPersonInfoId and hrStaffPersonExperienceContract.fdContStatus in('1', '2', '3')");
			hqlInfo.setParameter("fdPersonInfoId", hrStaffPersonInfo.getFdId());
			hqlInfo.setOrderBy("hrStaffPersonExperienceContract.fdEndDate desc");
			List<HrStaffPersonExperienceContract> contracts = getServiceImp(request).findList(hqlInfo);
			request.setAttribute("contracts", contracts);
			request.setAttribute("oldContractId", fdId);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("renewContract", mapping, form, request, response);
		}
	}

	/**
	 * <p>合同续签</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward saveRenewContract(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String oldContractId = request.getParameter("oldContractId");
			((IHrStaffPersonExperienceContractService) getServiceImp(request)).saveRenewContract(oldContractId,
					(IExtendForm) form,
					new RequestContext(request));

		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * <p>合同签订</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward signContract(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String personId = request.getParameter("personId");
			//员工信息
			HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoServiceImp()
					.findByPrimaryKey(personId);

			ActionForm newForm = createNewForm(mapping, form, request, response);
			HrStaffPersonExperienceContractForm contractForm = (HrStaffPersonExperienceContractForm) newForm;
			contractForm.setFdPersonInfoId(hrStaffPersonInfo.getFdId());
			contractForm.setFdPersonInfoName(hrStaffPersonInfo.getFdName());
			if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }

			request.setAttribute("hrStaffPersonInfo", hrStaffPersonInfo);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("signContract", mapping, form, request, response);
		}
	}

	/**
	 * <p>批量续签</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward batchRenewPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("ids");
			List<HrStaffPersonInfo> list = getHrStaffPersonInfoServiceImp().findByPrimaryKeys(ids.split(";"));
			request.setAttribute("list", list);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("batchRenewPage", mapping, form, request, response);
		}
	}

	public ActionForward saveBatchRenew(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("ids");
			List<HrStaffPersonInfo> list = getHrStaffPersonInfoServiceImp().findByPrimaryKeys(ids.split(";"));
			HrStaffPersonExperienceContractForm contractForm = (HrStaffPersonExperienceContractForm) form;
			HrStaffPersonExperienceContractForm experienceContractForm = null;
			for (HrStaffPersonInfo hrStaffPersonInfo : list) {
				//查询原合同
				HrStaffPersonExperienceContract contract = ((IHrStaffPersonExperienceContractService) getServiceImp(
						request))
						.findContractByPersonId(hrStaffPersonInfo.getFdId());
				String oldContract = (null != contract) ? contract.getFdId() : null;
				contractForm.setFdId(IDGenerator.generateID());
				contractForm.setFdPersonInfoId(hrStaffPersonInfo.getFdId());
				contractForm.setFdPersonInfoName(hrStaffPersonInfo.getFdName());
				if (StringUtil.isNull(contractForm.getFdBeginDate())) {
					contractForm.setFdBeginDate(
							DateUtil.convertDateToString(contract.getFdEndDate(), DateUtil.PATTERN_DATETIME));
				}
				((IHrStaffPersonExperienceContractService) getServiceImp(request)).saveRenewContract(oldContract,
						(IExtendForm) form, new RequestContext(request));
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		JSONObject json = new JSONObject();
		json.put("status", true);// 执行结果
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * <p>批量签订</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward batchSignPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("ids");
			List<HrStaffPersonInfo> list = getHrStaffPersonInfoServiceImp().findByPrimaryKeys(ids.split(";"));
			request.setAttribute("list", list);

		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("batchSignPage", mapping, form, request, response);
		}
	}

	public ActionForward saveBatchSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("ids");
			List<HrStaffPersonInfo> list = getHrStaffPersonInfoServiceImp().findByPrimaryKeys(ids.split(";"));
			HrStaffPersonExperienceContractForm contractForm = (HrStaffPersonExperienceContractForm) form;
			HrStaffPersonExperienceContractForm experienceContractForm = null;
			for (HrStaffPersonInfo hrStaffPersonInfo : list) {
				contractForm.setFdId(IDGenerator.generateID());
				contractForm.setFdPersonInfoId(hrStaffPersonInfo.getFdId());
				contractForm.setFdPersonInfoName(hrStaffPersonInfo.getFdName());
				getServiceImp(request).add((IExtendForm) contractForm, new RequestContext(request));
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		JSONObject json = new JSONObject();
		json.put("status", true);// 执行结果
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	protected ISysPrintMainCoreService sysPrintMainCoreService;

	public ISysPrintMainCoreService getSysPrintMainCoreService() {
		if (sysPrintMainCoreService == null) {
            sysPrintMainCoreService = (ISysPrintMainCoreService) getBean(
                    "sysPrintMainCoreService");
        }
		return sysPrintMainCoreService;
	}

	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-print", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HtmlToMht.setLocaleWhenExport(request);
			loadActionForm(mapping, form, request, response);
			HrStaffPersonExperienceContractForm contractForm = (HrStaffPersonExperienceContractForm) form;
			String fdStaffContTypeId = contractForm.getFdStaffContTypeId();
			boolean enable = false;
			if (StringUtil.isNotNull(fdStaffContTypeId)) {
				HrStaffContractType contractType = (HrStaffContractType) getHrStaffContractTypeService()
						.findByPrimaryKey(fdStaffContTypeId);
				enable = getSysPrintMainCoreService()
						.isEnablePrintTemplate(contractType, null, request);
			}
			HrStaffPersonExperienceContract contract = (HrStaffPersonExperienceContract) getServiceImp(
					request).findByPrimaryKey(contractForm.getFdId());
			getSysPrintMainCoreService().initPrintData(contract, contractForm,
					request);
			if (enable) {
				request.setAttribute("isShowSwitchBtn", "true");
			}
			String printPageType = request.getParameter("_ptype");
			if (enable && !"old".equals(printPageType)) {
				return mapping.findForward("sysprint");
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-print", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("print", mapping, form, request, response);
		}
	}

	public ActionForward getRepeatContract(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		boolean flag = true;
		try {
			String personInfoId = request.getParameter("personInfoId");
			//员工信息
			HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoServiceImp().findByPrimaryKey(personInfoId, null, true);
			if (hrStaffPersonInfo != null) {
				//查询重复合同
				List<HrStaffPersonExperienceContract> contractList = ((IHrStaffPersonExperienceContractService) getServiceImp(request)).findByContract(hrStaffPersonInfo, new RequestContext(request));
				if (contractList != null && !contractList.isEmpty()) {
					flag = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		JSONObject json = new JSONObject();
		json.put("result", flag);// 执行结果
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	public ActionForward getBatchRepeatContract(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		boolean flag = true;
		StringBuilder errorMsg = new StringBuilder();
		try {
			//员工信息
			String ids = request.getParameter("ids");
			List<HrStaffPersonInfo> list = getHrStaffPersonInfoServiceImp().findByPrimaryKeys(ids.split(";"));
			for (HrStaffPersonInfo hrStaffPersonInfo : list) {
				if (hrStaffPersonInfo != null) {
					//查询重复合同
					List<HrStaffPersonExperienceContract> contractList = ((IHrStaffPersonExperienceContractService) getServiceImp(request)).findByContract(hrStaffPersonInfo, new RequestContext(request));
					if (contractList != null && !contractList.isEmpty()) {
						errorMsg.append(hrStaffPersonInfo.getFdName())
								.append(":")
								.append(ResourceUtil.getString("hr-staff:hrStaff.import.error.contract.repeat"))
								.append("<br/>");
						flag = false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		JSONObject json = new JSONObject();
		json.put("result", flag);// 执行结果
		json.put("error", errorMsg.toString());// 错误提示
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

}
