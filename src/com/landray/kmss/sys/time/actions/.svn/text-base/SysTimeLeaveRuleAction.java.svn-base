package com.landray.kmss.sys.time.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.sys.time.forms.SysTimeLeaveRuleForm;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-31
 */
public class SysTimeLeaveRuleAction extends ExtendAction {

	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;
	@Override
	protected ISysTimeLeaveRuleService
			getServiceImp(HttpServletRequest request) {
		if (sysTimeLeaveRuleService == null) {
			sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) getBean(
					"sysTimeLeaveRuleService");
		}
		return sysTimeLeaveRuleService;
	}

	private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

	public ISysTimeLeaveAmountItemService getSysTimeLeaveAmountItemService() {
		if(sysTimeLeaveAmountItemService==null){
			sysTimeLeaveAmountItemService = (ISysTimeLeaveAmountItemService) getBean(
					"sysTimeLeaveAmountItemService");
		}
		return sysTimeLeaveAmountItemService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		SysTimeLeaveRuleForm sysTimeLeaveRuleForm = (SysTimeLeaveRuleForm) form;
		sysTimeLeaveRuleForm.setFdIsAvailable("true");
		sysTimeLeaveRuleForm.setFdStatType("1");
		sysTimeLeaveRuleForm.setFdStatDayType("1");
		sysTimeLeaveRuleForm.setFdDayConvertTime("8");
		sysTimeLeaveRuleForm.setFdIsAmount("false");
		sysTimeLeaveRuleForm.setFdAmountType("1");
		sysTimeLeaveRuleForm.setFdAutoAmount("5");
		sysTimeLeaveRuleForm.setFdAmountCalType("1");
		List<String> numList = getSerialNumList(request);
		if (!numList.isEmpty()) {
			request.setAttribute("serialNums", StringUtil
					.join(numList.toArray(new String[numList.size()]), ";"));
		}
		List<String> nameList = getNameList(request);
		if (!nameList.isEmpty()) {
			request.setAttribute("leaveNames", StringUtil
					.join(nameList.toArray(new String[nameList.size()]), ";"));
		}
		return form;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		List<String> numList = getSerialNumList(request);
		if (!numList.isEmpty()) {
			request.setAttribute("serialNums", StringUtil
					.join(numList.toArray(new String[numList.size()]), ";"));
		}
		List<String> nameList = getNameList(request);
		if (!nameList.isEmpty()) {
			request.setAttribute("leaveNames", StringUtil
					.join(nameList.toArray(new String[nameList.size()]), ";"));
		}
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		String fdName = cv.poll("fdName");
		if (StringUtil.isNotNull(fdName)) {
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
					" and ",
					"sysTimeLeaveRule.fdName like '%" + fdName + "%'"));
		}
	}

	@Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			saveCommon(form,request);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return add(mapping, form, request, response);
		}
	}

	/**
	 * 执行添加的公共方法
	 * @param form
	 * @param request
	 * @throws Exception
	 */
	private void saveCommon( ActionForm form,HttpServletRequest request) throws Exception {
		SysTimeLeaveRuleForm leaveruleForm = (SysTimeLeaveRuleForm) form;
		String fdId = getServiceImp(request).add((IExtendForm) form,
				new RequestContext(request));
		if ("1".equals(leaveruleForm.getIsUpdateAmount())) {
			SysTimeLeaveRule leaveRule = new SysTimeLeaveRule();
			leaveRule = (SysTimeLeaveRule) getServiceImp(request).convertFormToModel(leaveruleForm, leaveRule,new RequestContext(request));
			leaveRule.setFdId(fdId);
			//事务提交以后才执行
			String state="add";
			getServiceImp(request).executionAmountTask(leaveRule,state);
		}
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
			saveCommon(form,request);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
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
			SysTimeLeaveRuleForm ruleForm = (SysTimeLeaveRuleForm) form;
			getServiceImp(request).update(ruleForm,
					new RequestContext(request));
			if ("1".equals(ruleForm.getIsUpdateAmount())) {
				SysTimeLeaveRule sysTimeLeaveRule = null;
				sysTimeLeaveRule = (SysTimeLeaveRule) getServiceImp(request)
						.convertFormToModel(ruleForm, sysTimeLeaveRule,
								new RequestContext(request));

				String state = "update";
				getServiceImp(request).executionAmountTask(sysTimeLeaveRule,state);
			}
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
			return getActionForward("success", mapping, form, request,
					response);
		}
	}


	public ActionForward enable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-enable", true, getClass());
		boolean fail = false;
		JSONObject result = new JSONObject();
		String errMsg = null;
		try {
			String id = request.getParameter("fdId");

			if (StringUtil.isNull(id)) {
				throw new NoRecordException();
			} else {
				List<String> numList = getSerialNumList(request);
				List<String> nameList = getNameList(request);
				SysTimeLeaveRule sysTimeLeaveRule = (SysTimeLeaveRule) getServiceImp(
						request).findByPrimaryKey(id);
				if (sysTimeLeaveRule != null) {
					if (numList.contains(sysTimeLeaveRule.getFdSerialNo())) {
						errMsg = ResourceUtil.getString(
								"sysTimeLeaveRule.fdSerialNo.unique",
								"sys-time");
						fail = true;
					} else if (nameList
							.contains(sysTimeLeaveRule.getFdName())) {
						errMsg = ResourceUtil.getString(
								"sysTimeLeaveRule.fdName.unique",
								"sys-time");
						fail = true;
					} else {
						if (UserOperHelper.allowLogOper(
								SysLogOperXml.LOGPOINT_UPDATE,
								getServiceImp(request).getModelName())) {
							UserOperContentHelper.putUpdate(sysTimeLeaveRule)
									.putSimple("fdIsAvailable",
											sysTimeLeaveRule.getFdIsAvailable(),
											true);
							UserOperHelper.setOperSuccess(true);
						}
						sysTimeLeaveRule.setFdIsAvailable(true);
						getServiceImp(request).update(sysTimeLeaveRule);
					}
				} else {
					throw new NoRecordException();
				}
			}
		} catch (Exception e) {
			fail = true;
		}

		TimeCounter.logCurrentTime("Action-enable", false, getClass());
		if (fail) {
			result.accumulate("status", false);
			result.accumulate("errMsg", errMsg);
			request.setAttribute("lui-source", result);
			return getActionForward("lui-source", mapping, form, request,
					response);
		} else {
			result.accumulate("status", true);
			request.setAttribute("lui-source", result);
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	public ActionForward disable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-disable", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String id = request.getParameter("fdId");

			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				SysTimeLeaveRule sysTimeLeaveRule = (SysTimeLeaveRule) getServiceImp(
						request).findByPrimaryKey(id);
				if (sysTimeLeaveRule != null) {
					if (UserOperHelper.allowLogOper(
							SysLogOperXml.LOGPOINT_UPDATE,
							getServiceImp(request).getModelName())) {
						UserOperContentHelper.putUpdate(sysTimeLeaveRule)
								.putSimple("fdIsAvailable",
										sysTimeLeaveRule.getFdIsAvailable(),
										false);
						UserOperHelper.setOperSuccess(true);
					}
					sysTimeLeaveRule.setFdIsAvailable(false);
					getServiceImp(request).update(sysTimeLeaveRule);
				} else {
					messages.addError(new NoRecordException());
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-disable", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	public ActionForward updateAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateAmount", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			SysTimeLeaveRuleForm ruleForm = (SysTimeLeaveRuleForm) form;
			SysTimeLeaveRule sysTimeLeaveRule = null;
			sysTimeLeaveRule = (SysTimeLeaveRule) getServiceImp(
					request).convertFormToModel(ruleForm, sysTimeLeaveRule,
							new RequestContext(request));
			if (sysTimeLeaveRule != null) {
				getServiceImp(request).updateAmount(sysTimeLeaveRule);
			} else {
				messages.addError(new NoRecordException());
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-updateAmount", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	public ActionForward addAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-addAmount", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			SysTimeLeaveRuleForm leaveRuleForm = (SysTimeLeaveRuleForm) form;
			SysTimeLeaveRule leaveRule = new SysTimeLeaveRule();
			leaveRule = (SysTimeLeaveRule) getServiceImp(
					request).convertFormToModel(leaveRuleForm, leaveRule,
							new RequestContext(request));
			if (leaveRule != null) {
				getServiceImp(request).addAmount(leaveRule);
			} else {
				messages.addError(new NoRecordException());
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-addAmount", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	private List<String> getSerialNumList(HttpServletRequest request) {
		List<String> newList = new ArrayList<String>();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("sysTimeLeaveRule.fdSerialNo");
			hqlInfo.setWhereBlock(
					"sysTimeLeaveRule.fdIsAvailable=:isAvailable");
			hqlInfo.setParameter("isAvailable", true);
			List<String> numList = getServiceImp(request).findValue(hqlInfo);
			for (String str : numList) {
				newList.add(Integer.valueOf(str).toString());
			}
		} catch (Exception e) {
		}
		return newList;
	}

	private List<String> getNameList(HttpServletRequest request) {
		List<String> nameList = new ArrayList<String>();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("sysTimeLeaveRule.fdName");
			hqlInfo.setWhereBlock(
					"sysTimeLeaveRule.fdIsAvailable=:isAvailable");
			hqlInfo.setParameter("isAvailable", true);
			nameList = getServiceImp(request).findValue(hqlInfo);
		} catch (Exception e) {
		}
		return nameList;
	}

	
	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
			String curOrderBy) throws Exception {
		if (curOrderBy == null) {
			String className = getServiceImp(request).getModelName();
			if (StringUtil.isNull(className)) {
				return null;
			}
			SysDictModel model = SysDataDict.getInstance().getModel(className);
			if (model == null) {
				return null;
			}
			String modelName = ModelUtil.getModelTableName(className);
			Map propertyMap = model.getPropertyMap();
			/*
			 * 首先按排序号排序，如果相同按创建时间倒序 ;排序号为空 排在有排序号的后面，且，排序号为空的假期中，按创建时间倒序。
			 */
			curOrderBy = "";
			if (propertyMap.get("fdOrder") != null) {
				curOrderBy = "case when " + modelName
						+ ".fdOrder is null then 1 else 0 end asc, " + modelName
						+ ".fdOrder asc";
				if (propertyMap.get("fdId") != null) {
					curOrderBy += "," + modelName + ".fdId desc";
				}
			} else if (propertyMap.get("fdId") != null) {
				curOrderBy += modelName + ".fdId desc";
			}
		}
		return curOrderBy;
	}
}
