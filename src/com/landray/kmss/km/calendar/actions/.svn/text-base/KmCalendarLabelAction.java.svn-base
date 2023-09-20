package com.landray.kmss.km.calendar.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.km.calendar.model.KmCalendarAgendaLabel;
import com.landray.kmss.km.calendar.model.KmCalendarLabel;
import com.landray.kmss.km.calendar.service.IKmCalendarAgendaLabelService;
import com.landray.kmss.km.calendar.service.IKmCalendarLabelService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 新日程标签管理 Action
 *
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarLabelAction extends ExtendAction {

	protected IKmCalendarLabelService kmCalendarLabelService;

	private static Set<String> synset = Collections
			.synchronizedSet(new HashSet<String>());

	@Override
	protected IKmCalendarLabelService getServiceImp(HttpServletRequest request) {
		if (kmCalendarLabelService == null) {
			kmCalendarLabelService = (IKmCalendarLabelService) getBean("kmCalendarLabelService");
		}
		return kmCalendarLabelService;
	}

	protected IKmCalendarMainService kmCalendarMainService;

	public IKmCalendarMainService getKmCalendarMainService() {
		if (kmCalendarMainService == null) {
			kmCalendarMainService = (IKmCalendarMainService) SpringBeanUtil
					.getBean("kmCalendarMainService");
		}
		return kmCalendarMainService;
	}

	protected IKmCalendarAgendaLabelService kmCalendarAgendaLabelService;

	public IKmCalendarAgendaLabelService getKmCalendarAgendaLabelService() {
		if (kmCalendarAgendaLabelService == null) {
			kmCalendarAgendaLabelService = (IKmCalendarAgendaLabelService) SpringBeanUtil
					.getBean("kmCalendarAgendaLabelService");
		}
		return kmCalendarAgendaLabelService;
	}

	protected ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	/**
	 * 新增日程标签JSon接口（供JS异步调用）
	 */
	@SuppressWarnings("unchecked")
	public ActionForward addJson(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("fdId");
		String fdName = request.getParameter("fdName");
		String fdDescription = request.getParameter("fdDescription");
		String fdColor = request.getParameter("fdColor");
		String fdOrderStr = request.getParameter("fdOrder");
		Integer fdOrder = StringUtils.isNotEmpty(fdOrderStr) ? Integer
				.valueOf(fdOrderStr) : new Integer(100);
		if (StringUtils.isNotEmpty(fdName)) {
			SysOrgPerson fdCreator = UserUtil.getUser();
			KmCalendarLabel kmCalendarLabel = new KmCalendarLabel(fdName,
					fdDescription, fdColor, fdOrder, fdCreator);
			kmCalendarLabel.setFdId(fdId);
			if (UserOperHelper.allowLogOper("addJson",
					getServiceImp(request).getModelName())) {
				UserOperContentHelper.putAdd(kmCalendarLabel, fdId, fdName);
				UserOperHelper.setOperSuccess(true);
			}
			fdId = getServiceImp(request).add(kmCalendarLabel);
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			String successFlag = (StringUtils.isNotEmpty(fdId)) ? "1" : "0";
			jsonObject.accumulate("success", successFlag);
			jsonArray.add(jsonObject);
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(jsonArray.toString());
		}
		return null;
	}

	/**
	 * 修改日程标签JSon接口（供JS异步调用）
	 */
	@SuppressWarnings("unchecked")
	public ActionForward updateJson(ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("fdId");
		String fdName = request.getParameter("fdName");
		String fdDescription = request.getParameter("fdDescription");
		String fdColor = request.getParameter("fdColor");
		String fdOrderStr = request.getParameter("fdOrder");
		String fdModelName = request.getParameter("fdModelName");
		Integer fdOrder = StringUtils.isNotEmpty(fdOrderStr) ? Integer
				.valueOf(fdOrderStr) : new Integer(0);
		if (StringUtils.isNotEmpty(fdId)) {
			SysOrgPerson fdCreator = UserUtil.getUser();
			KmCalendarLabel kmCalendarLabel = new KmCalendarLabel(fdName,
					fdDescription, fdColor, fdOrder, fdCreator);
			kmCalendarLabel.setFdId(fdId);
			kmCalendarLabel.setFdModelName(fdModelName);
			if (UserOperHelper.allowLogOper("updateJson",
					getServiceImp(request).getModelName())) {
				UserOperContentHelper.putUpdate(fdId, fdName,
						getServiceImp(request).getModelName());
				UserOperHelper.setOperSuccess(true);
			}
			getServiceImp(request).update(kmCalendarLabel);
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("success", "1");
			jsonArray.add(jsonObject);
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(jsonArray.toString());
		}
		return null;
	}

	/**
	 * 删除日程标签JSon接口（供JS异步调用）
	 */
	@SuppressWarnings("unchecked")
	public ActionForward deleteJson(ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("fdId");
		if (StringUtils.isNotEmpty(fdId)) {
			getServiceImp(request).delete(fdId);
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("success", "1");
			jsonArray.add(jsonObject);
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(jsonArray.toString());
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ActionForward list(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			updateAgendaLabel(request);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("kmCalendarLabel.fdCreator.fdId=:fdId ");
			hqlInfo.setParameter("fdId", UserUtil.getUser().getFdId());
			hqlInfo.setOrderBy(" kmCalendarLabel.fdOrder asc");
			List<KmCalendarLabel> labels = getServiceImp(request).findList(
					hqlInfo);
			// 记录日志
			UserOperHelper.logFindAll(labels,
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", labels);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("list", mapping, form, request, response);
		}
	}

	/**
	 * 查询日程标签列表JSon接口（供JS异步调用）
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listJson(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = request.getParameter("userId");
		if ("null".equalsIgnoreCase(userId) || "undefined".equalsIgnoreCase(userId)) {
			userId = null;
		}
		updateAgendaLabel(request);
		HQLInfo hqlInfo = new HQLInfo();
		StringBuilder sbWhere = new StringBuilder();
		if (StringUtil.isNull(userId)) {
			userId = UserUtil.getUser().getFdId();
		}
		// #169334 查询他人所有标签 - 与移动端一致
		sbWhere.append("kmCalendarLabel.fdCreator.fdId=:fdId ");

		hqlInfo.setWhereBlock(sbWhere.toString());
		hqlInfo.setParameter("fdId", userId);

		hqlInfo.setOrderBy(" kmCalendarLabel.fdOrder asc");
		List calendarLabelList = getServiceImp(request).findList(hqlInfo);
		// 记录日志
		UserOperHelper.logFindAll(calendarLabelList,
				getServiceImp(request).getModelName());
		UserOperHelper.setOperSuccess(true);
		JSONArray jsonArray = new JSONArray();
		JSONArray allLabelArray = new JSONArray();
		if (calendarLabelList != null && calendarLabelList.size() > 0) {
			for (int i = 0; i < calendarLabelList.size(); i++) {
				KmCalendarLabel kmCalendarLabel = (KmCalendarLabel) calendarLabelList.get(i);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("fdId", kmCalendarLabel.getFdId());
				//记录所有标签
				if (kmCalendarLabel.getFdCommonFlag() != null && !"0".equals(kmCalendarLabel.getFdCommonFlag())) {
					String[] __labelFlag = kmCalendarLabel.getFdCommonFlag().split("\\|");
					jsonObject.put("isCommon", "1");
					jsonObject.put("commonLabel", __labelFlag.length > 1 ? __labelFlag[1] : "");
				} else {
					jsonObject.put("isCommon", "0");
				}
				if (kmCalendarLabel.getFdSelectedFlag() != null && !kmCalendarLabel.getFdSelectedFlag()) {
					jsonObject.put("selectedFlag", "0");
				} else {
					jsonObject.put("selectedFlag", "1");
				}
				allLabelArray.add(jsonObject);

				//只查询展示非通用标签（自定义标签） - 0
				if (kmCalendarLabel.getFdCommonFlag() != null && !"0".equals(kmCalendarLabel.getFdCommonFlag())) {
					continue;
				}

				String fdName = StringUtil
						.XMLEscape(kmCalendarLabel.getFdName());
				if (StringUtil.isNotNull(kmCalendarLabel.getFdModelName())) {
					SysDictModel sysDictModel = SysDataDict.getInstance()
							.getModel(kmCalendarLabel.getFdModelName());
					if (sysDictModel != null) {
						String messageKey = sysDictModel.getMessageKey();
						String fdName_lang = ResourceUtil.getString(messageKey,
								request.getLocale());
						fdName = fdName_lang != null ? fdName_lang : fdName;
					}
				}
				jsonObject.put("fdName", fdName);
				jsonObject.put("fdDescription", kmCalendarLabel
						.getFdDescription());
				jsonObject.put("fdColor", kmCalendarLabel.getFdColor());
				jsonObject.put("fdOrder", kmCalendarLabel.getFdOrder());
				jsonArray.add(jsonObject);
			}
		}

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		JSONObject rsObject = new JSONObject();
		rsObject.put("showLabelData", jsonArray);
		rsObject.put("allLabelData", allLabelArray);
		response.getWriter().write(rsObject.toString());
		return null;
	}

	public ActionForward updateLabel2Selected(ActionMapping mapping, ActionForm form,
											  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject rsObject = new JSONObject();
		rsObject.put("rsCode", "0000");
		try {
			//String userId = UserUtil.getUser().getFdId();
			String labelFdId = request.getParameter("labelFdId");
			String commanLabelFlag = request.getParameter("commanLabelFlag");
			String isSelected = request.getParameter("isSelected");

			boolean isCommon = commanLabelFlag.length() == 32 ? false : true;
			String commonFlag = isCommon ? "1" : "0";
			boolean selectedFlag = "0".equals(isSelected) ? false : true;
			//通用标签 加上通用的标志 eg.myEvent(以前就前台写死的)
			if ("1".equals(commonFlag)) {
				commonFlag = commonFlag + "|" + commanLabelFlag;
			}
			KmCalendarLabel model = new KmCalendarLabel();
			model.setFdSelectedFlag(selectedFlag);
			model.setFdCommonFlag(commonFlag);
			if (StringUtil.isNull(labelFdId) || "null".equals(labelFdId)) {
				//add
				String newFdId = IDGenerator.generateID();
				model.setFdId(newFdId);
				//只为了记录选中，不展示
				model.setFdName(commanLabelFlag);
				model.setFdCreator(UserUtil.getUser());
				model.setFdOrder(0);
				getServiceImp(request).updLabelSelect(model, "add");
				rsObject.put("rsData", newFdId);
			} else {
				//update
				model.setFdId(labelFdId);
				getServiceImp(request).updLabelSelect(model, "update");
			}
		} catch (Exception e) {
			rsObject.put("rsCode", "9999");
			rsObject.put("rsMsg", e.getMessage());
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(rsObject.toString());
		return null;
	}

	/**
	 * 批量更新移动端的标签选择
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchUpdateLabel2Selected(ActionMapping mapping, ActionForm form,
												   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject rsObject = new JSONObject();
		rsObject.put("rsCode", "0000");
		//数组
		String myCateSelArrStr = request.getParameter("myCateSelArr");
		try {
			//fastJson转jsonArray
			JSONArray myCateSelArr = JSONArray.fromObject(myCateSelArrStr);
			for (int i = 0; i < myCateSelArr.size(); i++) {
				JSONObject json = JSONObject.fromObject(myCateSelArr.get(i));
				String fdId = json.getString("fdId");
				boolean selectedFlag = json.getBoolean("selectedFlag");
				KmCalendarLabel model = new KmCalendarLabel();
				model.setFdId(fdId);
				model.setFdSelectedFlag(selectedFlag);
				getServiceImp(request).updLabelSelect(model, "update");
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			rsObject.put("rsCode", "9999");
			rsObject.put("rsMsg", e.getMessage());
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(rsObject.toString());
		return null;
	}

	/**
	 * 更新后台创建的标签
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateAgendaLabelByMobile(ActionMapping mapping,
											ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		updateAgendaLabel(request);
		return null;
	}

	/**
	 * 初始化移动端-标签列表的展示
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLabelSelectList(ActionMapping mapping,
											ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String userId = UserUtil.getUser().getFdId();
		//查询用户相关的标签列表
		hqlInfo.setWhereBlock("kmCalendarLabel.fdCreator.fdId=:fdId");
		hqlInfo.setParameter("fdId", userId);
		hqlInfo.setOrderBy(" kmCalendarLabel.fdOrder asc");
		List<KmCalendarLabel> calendarLabelList = getServiceImp(request).findList(hqlInfo);
		// 记录日志
		UserOperHelper.logFindAll(calendarLabelList, getServiceImp(request).getModelName());
		UserOperHelper.setOperSuccess(true);
		JSONArray allLabelArray = new JSONArray();
		Map<String, JSONObject> commonLabels = new HashMap<>(3);
		// 我的日历 - CALENDAR_MY_EVENT - km-calendar:kmCalendar.nav.title
		// 群组日程 - CALENDAR_MY_GROUP_EVENT - km-calendar:kmCalendarMain.group.header.title
		// 我的笔记 - CALENDAR_MY_NOTE - km-calendar:module.km.calendar.tree.my.note
		if (!CollectionUtils.isEmpty(calendarLabelList)) {
			for (KmCalendarLabel kmCalendarLabel : calendarLabelList) {
				String commonLabel = null;
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("value", kmCalendarLabel.getFdId());
				//记录所有标签
				if (kmCalendarLabel.getFdCommonFlag() != null && !"0".equals(kmCalendarLabel.getFdCommonFlag())) {
					String[] __labelFlag = kmCalendarLabel.getFdCommonFlag().split("\\|");
					jsonObject.put("isCommon", "1");
					commonLabel = __labelFlag.length > 1 ? __labelFlag[1] : "";
					jsonObject.put("commonLabel", commonLabel);
				} else {
					jsonObject.put("isCommon", "0");
				}
				if (kmCalendarLabel.getFdSelectedFlag() != null && !kmCalendarLabel.getFdSelectedFlag()) {
					jsonObject.put("selectedFlag", "0");
				} else {
					jsonObject.put("selectedFlag", "1");
				}

				String fdName = StringUtil.XMLEscape(kmCalendarLabel.getFdName());
				if (StringUtil.isNotNull(kmCalendarLabel.getFdModelName())) {
					SysDictModel sysDictModel = SysDataDict.getInstance().getModel(kmCalendarLabel.getFdModelName());
					if (sysDictModel != null) {
						String messageKey = sysDictModel.getMessageKey();
						String fdName_lang = ResourceUtil.getString(messageKey, request.getLocale());
						fdName = fdName_lang != null ? fdName_lang : fdName;
					}
				}
				jsonObject.put("text", fdName);
				jsonObject.put("userId", UserUtil.getKMSSUser().getUserId());

				//通用标签-翻译
				if ("1".equals(jsonObject.get("isCommon"))) {
					// 我的日历 - CALENDAR_MY_EVENT - km-calendar:kmCalendar.nav.title
					// 群组日程 - CALENDAR_MY_GROUP_EVENT - km-calendar:kmCalendarMain.group.header.title
					// 我的笔记 - CALENDAR_MY_NOTE - km-calendar:module.km.calendar.tree.my.note
					if (KmCalendarConstant.CALENDAR_MY_EVENT.equals(commonLabel)) {
						jsonObject.put("text", ResourceUtil.getString("km-calendar:kmCalendar.nav.title"));
					} else if (KmCalendarConstant.CALENDAR_MY_GROUP_EVENT.equals(commonLabel)) {
						jsonObject.put("text", ResourceUtil.getString("km-calendar:kmCalendarMain.group.header.title"));
					} else {
						jsonObject.put("text", ResourceUtil.getString("km-calendar:module.km.calendar.tree.my.note"));
					}
					commonLabels.put(commonLabel, jsonObject);
					continue;
				}
				allLabelArray.add(jsonObject);
			}
		}

		// 处理公共标签（不存在，则新增）
		List<String> commonList = new ArrayList<String>();
		commonList.add(KmCalendarConstant.CALENDAR_MY_EVENT);
		commonList.add(KmCalendarConstant.CALENDAR_MY_NOTE);
		commonList.add(KmCalendarConstant.CALENDAR_MY_GROUP_EVENT);
		for (String key : commonList) {
			if (!commonLabels.containsKey(key)) {
				// 不存在， 需要新增
				KmCalendarLabel model = new KmCalendarLabel();
				model.setFdSelectedFlag(true);
				model.setFdCommonFlag("1|" + key);
				model.setFdId(IDGenerator.generateID());
				String name = null;
				if (KmCalendarConstant.CALENDAR_MY_EVENT.equals(key)) {
					name = ResourceUtil.getString("km-calendar:kmCalendar.nav.title");
				} else if (KmCalendarConstant.CALENDAR_MY_GROUP_EVENT.equals(key)) {
					name = ResourceUtil.getString("km-calendar:kmCalendarMain.group.header.title");
				} else {
					name = ResourceUtil.getString("km-calendar:module.km.calendar.tree.my.note");
				}
				model.setFdName(name);
				model.setFdCreator(UserUtil.getUser());
				model.setFdOrder(0);
				getServiceImp(request).updLabelSelect(model, "add");

				// 返回数据
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("value", model.getFdId());
				jsonObject.put("isCommon", "1");
				jsonObject.put("selectedFlag", "1");
				jsonObject.put("text", name);
				jsonObject.put("commonLabel", key);
				jsonObject.put("userId", UserUtil.getKMSSUser().getUserId());
				commonLabels.put(key, jsonObject);
			}
		}
		// 我的日历放在第1位
		allLabelArray.add(0, commonLabels.get(KmCalendarConstant.CALENDAR_MY_EVENT));
		// 群组日程，我的笔记 放到最后
		allLabelArray.add(commonLabels.get(KmCalendarConstant.CALENDAR_MY_GROUP_EVENT));
		allLabelArray.add(commonLabels.get(KmCalendarConstant.CALENDAR_MY_NOTE));

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(allLabelArray.toString());
		return null;
	}

	/**
	 * 通用标签是否存在数据库中
	 *
	 * @param allLabelArray
	 * @param commonLabel
	 * @return
	 */
	private Boolean existLabelArr(JSONArray allLabelArray, String commonLabel) {
		boolean existFlag = false;
		for (Object j : allLabelArray) {
			JSONObject jj = (JSONObject) j;
			if (jj.containsKey("commonLabel") && jj.getString("commonLabel").equals(commonLabel)) {
				existFlag = true;
				break;
			}
		}
		return existFlag;
	}

	private void updateAgendaLabel(HttpServletRequest request) throws Exception {
		String userId = request.getParameter("userId");
		if ("null".equalsIgnoreCase(userId) || "undefined".equalsIgnoreCase(userId)) {
			return;
		}
		SysOrgPerson currentUser = null;
		if (StringUtil.isNotNull(userId)) {
			currentUser = (SysOrgPerson) getSysOrgPersonService().findByPrimaryKey(userId);
		} else {
			currentUser = UserUtil.getUser();
		}
		if (currentUser == null) {
			return;
		}
		if (synset.contains(currentUser.getFdId())) {
			return;
		} else {
			synset.add(currentUser.getFdId());
		}
		List<KmCalendarAgendaLabel> agendaLabels = getKmCalendarAgendaLabelService()
				.getValidAgendaLabels();

		if (agendaLabels != null && agendaLabels.size() > 0) {
			List<KmCalendarLabel> updateLabels = new ArrayList<>();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"  kmCalendarLabel.fdCreator.fdId=:fdId and kmCalendarLabel.fdModelName is not null and kmCalendarLabel.fdModelName <> ' '");
			hqlInfo.setParameter("fdId", currentUser.getFdId());
			hqlInfo.setOrderBy(" kmCalendarLabel.fdOrder asc");
			List<KmCalendarLabel> calendarLabelList = getServiceImp(request)
					.findList(hqlInfo);
			if (calendarLabelList == null) {
				addAgendaLabel(agendaLabels, currentUser);
			} else {
				List<KmCalendarAgendaLabel> agendaLabels_old = new ArrayList<KmCalendarAgendaLabel>();
				for (KmCalendarLabel kmCalendarLabel : calendarLabelList) {
					//过滤通用的标签
					if (kmCalendarLabel.getFdCommonFlag() != null && !"0".equals(kmCalendarLabel.getFdCommonFlag())) {
						continue;
					}
					String labelModelName = kmCalendarLabel.getFdModelName();
					for (KmCalendarAgendaLabel kmCalendarAgendaLabel : agendaLabels) {
						if (labelModelName.equals(kmCalendarAgendaLabel
								.getFdAgendaModelName())) {
							agendaLabels_old.add(kmCalendarAgendaLabel);
							// 待更新
							copyLabel(kmCalendarAgendaLabel, kmCalendarLabel);
							updateLabels.add(kmCalendarLabel);
							break;
						}
					}
				}
				agendaLabels.removeAll(agendaLabels_old);
				addAgendaLabel(agendaLabels, currentUser);
				// 更新
				updateLabel(updateLabels);
			}
		}
		synset.remove(currentUser.getFdId());

	}

	private void addAgendaLabel(List<KmCalendarAgendaLabel> agendaLabels, SysOrgPerson person)
			throws Exception {
		for (KmCalendarAgendaLabel agendaLabel : agendaLabels) {
			KmCalendarLabel kmCalendarLabel = new KmCalendarLabel();
			kmCalendarLabel.setFdId(IDGenerator.generateID());
			kmCalendarLabel.setFdColor(agendaLabel.getFdColor());
			kmCalendarLabel.setFdCreator(person);
			kmCalendarLabel.setFdDescription(null);
			kmCalendarLabel.setFdModelName(agendaLabel.getFdAgendaModelName());
			kmCalendarLabel.setFdName(agendaLabel.getFdName());
			kmCalendarLabel.setFdOrder(200);
			getServiceImp(null).add(kmCalendarLabel);
		}
	}

	private void copyLabel(KmCalendarAgendaLabel src, KmCalendarLabel target) {
		target.setFdName(src.getFdName());
		target.setFdColor(src.getFdColor());
		target.setFdDescription(src.getFdDescription());
		target.setFdOrder(src.getFdOrder());
	}

	/**
	 * 更新标签
	 * @param labels
	 * @throws Exception
	 */
	private void updateLabel(List<KmCalendarLabel> labels) throws Exception {
		for (KmCalendarLabel agendaLabel : labels) {
			getServiceImp(null).update(agendaLabel);

		}
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			KmCalendarLabel model = (KmCalendarLabel) getServiceImp(request).findByPrimaryKey(id, null, true);
			if (model != null) {
				String labelName = model.getFdName();
				if (KmCalendarConstant.CALENDAR_MY_EVENT.equals(labelName)) {
					labelName = ResourceUtil.getString("km-calendar:kmCalendar.nav.title");
				} else if (KmCalendarConstant.CALENDAR_MY_GROUP_EVENT.equals(labelName)) {
					labelName = ResourceUtil.getString("km-calendar:kmCalendarMain.group.header.title");
				} else if (KmCalendarConstant.CALENDAR_MY_NOTE.equals(labelName)) {
					labelName = ResourceUtil.getString("km-calendar:module.km.calendar.tree.my.note");
				}
				model.setFdName(labelName);
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				UserOperHelper.logFind(model);
			}
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}
}
