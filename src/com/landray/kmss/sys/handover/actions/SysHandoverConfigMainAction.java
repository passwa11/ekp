package com.landray.kmss.sys.handover.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.model.BaseCreateInfoModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.handover.constant.SysHandoverConstant;
import com.landray.kmss.sys.handover.forms.SysHandoverConfigMainForm;
import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchResult;
import com.landray.kmss.sys.handover.model.SysHandoverConfigAuthLogDetail;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLog;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLogDetail;
import com.landray.kmss.sys.handover.model.SysHandoverConfigMain;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigLogDetailService;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigLogService;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigMainService;
import com.landray.kmss.sys.handover.service.spring.HandoverPluginUtils;
import com.landray.kmss.sys.handover.service.spring.HandoverPluginUtils.HandoverConfig;
import com.landray.kmss.sys.handover.support.config.doc.AbstractDocHandler;
import com.landray.kmss.sys.handover.support.util.HandModelUtil;
import com.landray.kmss.sys.handover.support.util.ProcessNodeUtil;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmExpecterLog;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmRtNodeHandlersDefine;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.lbpmservice.support.util.LbpmTemplateUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.quartz.model.SysQuartzJob;
import com.landray.kmss.sys.quartz.scheduler.ISysQuartzJobExecutor;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import de.sty.io.mimetype.helper.NullPointerException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

/**
 * 工作交接查询、处理 Action
 * 
 * @author
 * @version 1.0 2014-07-22
 */
public class SysHandoverConfigMainAction extends ExtendAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysHandoverConfigMainAction.class);
	protected ISysHandoverConfigLogService sysHandoverConfigLogService;
	protected ISysHandoverConfigLogDetailService sysHandoverConfigLogDetailService;
	protected ISysHandoverConfigMainService sysHandoverConfigMainService;
	protected ILbpmProcessService lbpmProcessService;
	protected ISysQuartzJobService sysQuartzJobService;

	@Override
	protected ISysHandoverConfigMainService getServiceImp(
			HttpServletRequest request) {
		if (sysHandoverConfigMainService == null) {
			sysHandoverConfigMainService = (ISysHandoverConfigMainService) getBean("sysHandoverConfigMainService");
		}
		return sysHandoverConfigMainService;
	}

	protected ISysHandoverConfigLogService getConfigLogServiceImp(
			HttpServletRequest request) {
		if (sysHandoverConfigLogService == null) {
			sysHandoverConfigLogService = (ISysHandoverConfigLogService) getBean("sysHandoverConfigLogService");
		}
		return sysHandoverConfigLogService;
	}

	protected ISysHandoverConfigLogDetailService getConfigLogDetailServiceImp(
			HttpServletRequest request) {
		if (sysHandoverConfigLogDetailService == null) {
			sysHandoverConfigLogDetailService = (ISysHandoverConfigLogDetailService) getBean("sysHandoverConfigLogDetailService");
		}
		return sysHandoverConfigLogDetailService;
	}

	public ILbpmProcessService getLbpmProcessService() {
		if (lbpmProcessService == null) {
			lbpmProcessService = (ILbpmProcessService) SpringBeanUtil
					.getBean("lbpmProcessService");
		}
		return lbpmProcessService;
	}

	public ISysQuartzJobService getSysQuartzJobService() {
		if (sysQuartzJobService == null) {
			sysQuartzJobService = (ISysQuartzJobService) getBean("sysQuartzJobService");
		}
		return sysQuartzJobService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		StringBuffer whereBlock = new StringBuffer();
		String _where = StringUtil.getString(hqlInfo.getWhereBlock());
		if (StringUtil.isNull(_where)) {
			whereBlock.append("1 = 1");
		} else {
			whereBlock.append(_where);
		}

		String fdFromId = cv.poll("fdFromId");
		if (StringUtil.isNotNull(fdFromId)) {
			whereBlock
					.append(" and sysHandoverConfigMain.fdFromId = :fdFromId");
			hqlInfo.setParameter("fdFromId", fdFromId);
		}
		String fdToId = cv.poll("fdToId");
		if (StringUtil.isNotNull(fdToId)) {
			whereBlock.append(" and sysHandoverConfigMain.fdToId = :fdToId");
			hqlInfo.setParameter("fdToId", fdToId);
		}

		// 交接类型
		String handoverType = request.getParameter("type");
		if (StringUtil.isNotNull(handoverType)) {
			if ("doc".equals(handoverType)) { // 流程实例
				hqlInfo.setParameter("handoverType",
						SysHandoverConstant.HANDOVER_TYPE_DOC);
				whereBlock.append(
						" and sysHandoverConfigMain.handoverType = :handoverType");
			} else if ("auth".equals(handoverType)) { // 文档权限
				hqlInfo.setParameter("handoverType",
						SysHandoverConstant.HANDOVER_TYPE_AUTH);
				whereBlock.append(
						" and sysHandoverConfigMain.handoverType = :handoverType");
			} else if ("item".equals(handoverType)) { // 事项交接
				hqlInfo.setParameter("handoverType",
						SysHandoverConstant.HANDOVER_TYPE_ITEM);
				whereBlock.append(
						" and sysHandoverConfigMain.handoverType = :handoverType");
			} else { // 模板配置
				hqlInfo.setParameter("handoverType",
						SysHandoverConstant.HANDOVER_TYPE_CONFIG);
				whereBlock.append(
						" and (sysHandoverConfigMain.handoverType = :handoverType or sysHandoverConfigMain.handoverType is null)");
			}
		}

		hqlInfo.setWhereBlock(whereBlock.toString());
		CriteriaUtil.buildHql(cv, hqlInfo, SysHandoverConfigMain.class);
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysHandoverConfigMainForm sysHandoverConfigMainForm = (SysHandoverConfigMainForm) super
				.createNewForm(mapping, form, request, response);

		// 获取模块
		List<Map<String, String>> moduleList = new ArrayList<Map<String, String>>();
		String type = request.getParameter("type");
		if (StringUtil.isNotNull(type) && "doc".equals(type)) {
			moduleList = HandoverPluginUtils.getDocHandoverModules();
		} else if (StringUtil.isNotNull(type) && "item".equals(type)) {
			moduleList = HandoverPluginUtils.getConfigHandoverTypes(type);
		} else {
			moduleList = HandoverPluginUtils.getConfigHandoverTypes(type);
		}

		String fdFromId = request.getParameter("fdFromId");
		if(StringUtil.isNotNull(fdFromId)) {
			SysOrgElement element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(fdFromId);
			sysHandoverConfigMainForm.setFdFromId(fdFromId);
			sysHandoverConfigMainForm.setFdFromName(element.getFdName());
		}

		request.setAttribute("moduleMapList", moduleList);
		return sysHandoverConfigMainForm;
	}

	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
			String curOrderBy) throws Exception {
		if (StringUtil.isNotNull(curOrderBy)) {
			return "sysHandoverConfigMain." + curOrderBy;
		}
		return super.getFindPageOrderBy(request, curOrderBy);
	}

	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward af = super.add(mapping, form, request, response);
		String type = request.getParameter("type");
		// 文档权限与其它2种类型的处理方法不一样，页面也不一样，所以分开处理
		if ("auth".equals(type)) {
			af = getActionForward("edit_auth", mapping, form, request,
					response);
		}
		return af;
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			JSONObject jsonObject = getServiceImp(request).search(new RequestContext(request));
			String type = request.getParameter("type");
			jsonObject.put("type", type);
			if (UserOperHelper.allowLogOper("search", null)) {
				UserOperHelper.setModelNameAndModelDesc(
						getServiceImp(request).getModelName());
				UserOperHelper.logMessage(jsonObject.toString());
				UserOperHelper.setOperSuccess(true);
			}
			out.print(jsonObject.toString());
		} catch (Exception e) {
			logger.error("查询异常,请查看日志", e);
			out.print(false);
		}
		return null;
	}

	public ActionForward getMainLogId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			String mainId = IDGenerator.generateID();
			if (UserOperHelper.allowLogOper("getMainLogId", null)) {
				UserOperHelper.setModelNameAndModelDesc(
						getServiceImp(request).getModelName());
				UserOperHelper.logMessage(mainId);
				UserOperHelper.setOperSuccess(true);
			}
			out.print(mainId);
		} catch (Exception e) {
			logger.error("执行处理方法异常", e);
			out.print("");
		}
		return null;
	}

	/**
	 * 文档类交接 提交一个任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			String fdId = getServiceImp(request).submit(
					new RequestContext(request));

			List<SysQuartzJob> list = getSysQuartzJobService()
					.findList(
							"fdModelName='com.landray.kmss.sys.handover.model.SysHandoverConfigMain' and fdModelId='"
									+ fdId + "'", null);
			if (!ArrayUtil.isEmpty(list)) {
				// 执行时间类型（0：系统空闲执行[定时任务]，1：立即执行）
				String execType = request.getParameter("execType");
				SysQuartzJob job = list.get(0);
				if ("1".equals(execType)) { // 立即执行
					((ISysQuartzJobExecutor) getBean("sysQuartzJobExecutor"))
							.execute(job.getFdId());
					out.print("true");
				} else {
					out.print(ResourceUtil.getString(
							"sysHandoverConfigMain.quartz.desc",
							"sys-handover", null, DateUtil.convertDateToString(
									job.getFdRunTime(),
									DateUtil.PATTERN_DATETIME)));
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			out.print("");
		}
		return null;
	}

	public ActionForward perform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			JSONObject resultObject = getServiceImp(request).execute(new RequestContext(request));
			if (UserOperHelper.allowLogOper("perform", null)) {
				UserOperHelper.setModelNameAndModelDesc(
						getServiceImp(request).getModelName());
				UserOperHelper.logMessage(resultObject.toString());
				UserOperHelper.setOperSuccess(true);
			}
			out.print(resultObject);
		} catch (Exception e) {
			JSONObject resultObject = new JSONObject();
			resultObject.put("errorKey",e.getMessage());
			out.print(resultObject);
			logger.error("执行处理方法异常", e);
		}
		return null;
	}

	/**
	 * 查看文档权限交接记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param configMain
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward viewAuth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SysHandoverConfigMain configMain) throws Exception {
		TimeCounter.logCurrentTime("Action-viewAuth", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray logData = new JSONArray();
			long totalCount = 0L;
			if (configMain.getFdState() != SysHandoverConstant.HANDOVER_STATE_WAIT) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("fdMain = :fdMain");
				hqlInfo.setParameter("fdMain", configMain);
				hqlInfo.setOrderBy("fdStartTime");
				List<SysHandoverConfigLog> list = getConfigLogServiceImp(request).findList(hqlInfo);
	
				Map<String, List<SysHandoverConfigLog>> map = new LinkedHashMap<String, List<SysHandoverConfigLog>>();
				for (SysHandoverConfigLog log : list) {
					if (map.get(log.getFdItem()) == null) {
						List<SysHandoverConfigLog> _list = new ArrayList<SysHandoverConfigLog>();
						_list.add(log);
						map.put(log.getFdItem(), _list);
					} else {
						map.get(log.getFdItem()).add(log);
					}
				}
	
				for (String key : map.keySet()) {
					List<SysHandoverConfigLog> _list = map.get(key);
					JSONObject _logItem = new JSONObject();
					JSONArray _logItemModules = new JSONArray();
					long _itemCount = 0L;
					for (SysHandoverConfigLog log : _list) {
						_logItem.put("text", log.getFdItemName());
						_itemCount += log.getFdCount();
	
						JSONObject _logModule = new JSONObject();
						_logModule.put("text", log.getFdModuleName());
						_logModule.put("total", log.getFdCount());
						_logModule.put("name", log.getFdItem() + "-" + log.getFdModule());
						_logItemModules.add(_logModule);
					}
	
					_logItem.put("total", _itemCount);
					_logItem.put("modules", _logItemModules);
					totalCount += _itemCount;
					logData.add(_logItem);
				}
			}

			configMain.setTotal(totalCount);
			if (UserOperHelper.allowLogOper("viewAuth", null)) {
				UserOperHelper.setModelNameAndModelDesc(
						getServiceImp(request).getModelName());
				UserOperHelper.logMessage(logData.toString());
			}
			request.setAttribute("logData", logData);
			request.setAttribute("configMain", configMain);
			request.setAttribute("succTotal", totalCount);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-viewAuth", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("viewAuth", mapping, form, request, response);
		}
	}
	
	public ActionForward viewItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SysHandoverConfigMain configMain) throws Exception {
		TimeCounter.logCurrentTime("Action-viewItem", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray logData = new JSONArray();
			JSONArray ignoreData = new JSONArray();
			long totalCount = 0L;
			long ignoreTotal = 0L;
			if (configMain.getFdState() != SysHandoverConstant.HANDOVER_STATE_WAIT) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("fdMain = :fdMain");
				hqlInfo.setParameter("fdMain", configMain);
				hqlInfo.setOrderBy("fdStartTime");
				List<SysHandoverConfigLog> list = getConfigLogServiceImp(request).findList(hqlInfo);
				
				Map<String, List<SysHandoverConfigLog>> map = new LinkedHashMap<String, List<SysHandoverConfigLog>>();
				for (SysHandoverConfigLog log : list) {
					if (map.get(log.getFdModule()) == null) {
						List<SysHandoverConfigLog> _list = new ArrayList<SysHandoverConfigLog>();
						_list.add(log);
						map.put(log.getFdModule(), _list);
					} else {
						map.get(log.getFdModule()).add(log);
					}
				}
				
				for (String key : map.keySet()) {
					List<SysHandoverConfigLog> _list = map.get(key);
					JSONObject _logItem = new JSONObject();
					JSONArray _logItemModules = new JSONArray();
					long _itemCount = 0L;

					JSONObject _logIgnoreItem = new JSONObject();
					JSONArray _logIgnoreItemModules = new JSONArray();
					long _itemIgnoreCount = 0L;
					for (SysHandoverConfigLog log : _list) {
						JSONObject _logModule = new JSONObject();
						_logModule.put("text", log.getFdItemName());
						_logModule.put("name", log.getFdItem() + "-" + log.getFdModule());

						if (log.getFdCount() != null && log.getFdCount().longValue() > 0) {
							_logModule.put("total", log.getFdCount());
							_logItem.put("text", log.getFdModuleName());
							_itemCount += log.getFdCount();
							_logItemModules.add(_logModule);
						}
						if (log.getFdIgnoreCount() != null && log.getFdIgnoreCount().longValue() > 0) {
							_logModule.put("total", log.getFdIgnoreCount());
							_logIgnoreItem.put("text", log.getFdModuleName());
							_itemIgnoreCount += log.getFdIgnoreCount();
							_logIgnoreItemModules.add(_logModule);
						}

					}

					if (_logItemModules.size() > 0) {
						_logItem.put("total", _itemCount);
						_logItem.put("modules", _logItemModules);
						logData.add(_logItem);
						totalCount += _itemCount;
					}
					if (_logIgnoreItemModules.size() > 0) {
						_logIgnoreItem.put("total", _itemIgnoreCount);
						_logIgnoreItem.put("modules", _logIgnoreItemModules);
						ignoreData.add(_logIgnoreItem);
						ignoreTotal += _itemIgnoreCount;
					}
				}
			}
			
			configMain.setTotal(totalCount + ignoreTotal);
			if (UserOperHelper.allowLogOper("viewItem", null)) {
				UserOperHelper.setModelNameAndModelDesc(getServiceImp(request).getModelName());
				UserOperHelper.logMessage(logData.toString());
			}
			request.setAttribute("logData", logData);
			request.setAttribute("configMain", configMain);
			request.setAttribute("succTotal", totalCount);
			request.setAttribute("ignoreData", ignoreData);
			request.setAttribute("ignoreTotal", ignoreTotal);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-viewItem", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
			.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("viewItem", mapping, form, request,
					response);
		}
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String id = request.getParameter("fdId");
			SysHandoverConfigMain configMain = (SysHandoverConfigMain) getServiceImp(
					request).findByPrimaryKey(id, null, true);
			// 记录操作日志
			UserOperHelper.logFind(configMain);
			if (configMain == null) {
				throw new NoRecordException();
			}
			
			// 文档权限交接另外处理
			if (configMain.getHandoverType() != null
					&& configMain.getHandoverType().intValue() == SysHandoverConstant.HANDOVER_TYPE_AUTH) {
				return viewAuth(mapping, form, request, response, configMain);
			}
			
			// 事项交接
			if (configMain.getHandoverType() != null
					&& configMain.getHandoverType().intValue() == SysHandoverConstant.HANDOVER_TYPE_ITEM) {
				return viewItem(mapping, form, request, response, configMain);
			}

			// 执行成功
			JSONObject logModuleData = new JSONObject();
			// 忽略执行
			JSONObject logModuleIgnoreData = new JSONObject();
			// 是否配置类
			boolean isConfig = configMain.getHandoverType() == null
					|| configMain.getHandoverType() != 2;
			long totalCount = 0L;
			long ignoreTotal = 0L;

			// 处理成功日志数据
			for (int i = 0; i < configMain.getConfigLogs().size(); i++) {
				JSONObject moduleObject = new JSONObject();
				moduleObject.put("type", isConfig ? "template" : "doc");
				JSONArray itemArray = new JSONArray();

				SysHandoverConfigLog configLog = configMain.getConfigLogs()
						.get(i);
				if (configLog.getFdCount() < 1) {
					continue;
				}
				totalCount += configLog.getFdCount();

				HandoverConfig config = new HandoverConfig();
				config.setModule(configLog.getFdModule());
				config.setMessageKey(configLog.getFdModuleName());
				HandoverItem item = new HandoverItem();
				item.setItem(configLog.getFdItem());
				item.setItemMessageKey(configLog.getFdItemName());

				HandoverSearchContext context = new HandoverSearchContext(null,
						null, config, item);
				// 如果是配置类，则直接获取详细的日志
				if (isConfig) {
					for (int j = 0; j < configLog.getConLogDetails().size(); j++) {
						// 日志明细
						SysHandoverConfigLogDetail logDetail = configLog
								.getConLogDetails().get(j);
						String s = logDetail.getFdDescription();
						context.addHandoverRecord(logDetail.getFdId(),
								logDetail.getFdUrl(), new String[] { s });
					}
				}

				// 处理记录
				if (StringUtil.isNotNull(configLog.getFdItem())) {
					context.setTotal(configLog.getFdCount());

					if (logModuleData.get(configLog.getFdModule()) != null) {
						moduleObject = (JSONObject) logModuleData.get(configLog
								.getFdModule());
					} else {
						// 获取模块总数
						long total = getConfigLogServiceImp(request).getCounts(
								configMain, configLog.getFdModule())[0];
						moduleObject.put("module", configLog.getFdModule());
						moduleObject.put("total", total);
						moduleObject.put("moduleMessageKey", configLog
								.getFdModuleName());
					}
					// item信息累加
					if (moduleObject.get("item") != null) {
						itemArray = (JSONArray) moduleObject.get("item");
					} else {
						itemArray = new JSONArray();
					}
					HandoverSearchResult searchResult = context
							.getHandoverSearchResult();
					itemArray.add(JSONObject.fromObject(searchResult));
					moduleObject.put("item", itemArray);
					// 记录模块的信息
					logModuleData.put(configLog.getFdModule(), moduleObject);
				} else {
					context.setTotal(configLog.getFdCount());
					// 如果是配置类，则直接获取详细的日志
					if (isConfig) {
						HandoverSearchResult result = context
								.getHandoverSearchResult();
						moduleObject = JSONObject.fromObject(result);
					}
					// 记录模块信息
					logModuleData.put(configLog.getFdModule(), moduleObject);
				}

			}

			// 处理忽略日志数据
			for (int i = 0; i < configMain.getConfigLogs().size(); i++) {
				if (isConfig) {
					continue;
				}
				JSONObject moduleObject = new JSONObject();
				moduleObject.put("type", "doc");
				JSONArray itemArray = new JSONArray();

				SysHandoverConfigLog configLog = configMain.getConfigLogs()
						.get(i);
				if (configLog.getFdIgnoreCount() < 1) {
					continue;
				}
				ignoreTotal += configLog.getFdIgnoreCount();

				HandoverConfig config = new HandoverConfig();
				config.setModule(configLog.getFdModule());
				config.setMessageKey(configLog.getFdModuleName());
				HandoverItem item = new HandoverItem();
				item.setItem(configLog.getFdItem());
				item.setItemMessageKey(configLog.getFdItemName());

				HandoverSearchContext context = new HandoverSearchContext(null,
						null, config, item);

				// 处理记录
				if (StringUtil.isNotNull(configLog.getFdItem())) {
					context.setTotal(configLog.getFdIgnoreCount());

					if (logModuleIgnoreData.get(configLog.getFdModule()) != null) {
						moduleObject = (JSONObject) logModuleIgnoreData
								.get(configLog.getFdModule());
					} else {
						// 获取模块总数
						long total = getConfigLogServiceImp(request).getCounts(
								configMain, configLog.getFdModule())[1];
						moduleObject.put("module", configLog.getFdModule());
						moduleObject.put("total", total);
						moduleObject.put("moduleMessageKey", configLog
								.getFdModuleName());
					}
					// item信息累加
					if (moduleObject.get("item") != null) {
						itemArray = (JSONArray) moduleObject.get("item");
					} else {
						itemArray = new JSONArray();
					}
					HandoverSearchResult searchResult = context
							.getHandoverSearchResult();
					itemArray.add(JSONObject.fromObject(searchResult));
					moduleObject.put("item", itemArray);
					// 记录模块的信息
					logModuleIgnoreData.put(configLog.getFdModule(),
							moduleObject);
				} else {
					context.setTotal(configLog.getFdIgnoreCount());
					// 记录模块信息
					logModuleIgnoreData.put(configLog.getFdModule(),
							moduleObject);
				}

			}
			if (isConfig) {
				configMain.setTotal(totalCount);
			}
			request.setAttribute("logData", logModuleData);
			request.setAttribute("ignoreData", logModuleIgnoreData);
			request.setAttribute("configMain", configMain);
			request.setAttribute("succTotal", totalCount);
			request.setAttribute("ignoreTotal", ignoreTotal);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("view", mapping, form, request, response);
		}
	}

	private HQLInfo buildPageHQLInfo(HttpServletRequest request) {
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
		return hqlInfo;
	}

	/**
	 * 查询流程实例明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = buildPageHQLInfo(request);
			Page page = getServiceImp(request).detail(request, hqlInfo);
			if (page == null) {
				throw new NullPointerException();
			}

			// 在途流程需要获取文档标题
			if ("doc".equals(request.getParameter("type"))) {
				getDetailInfo(page, request);
			} else if ("item".equals(request.getParameter("type"))) {
				getDetailInfoByItem(page, request);
			} else {
				Map<String, String> subjectMap = new HashMap<String, String>();
				Map<String, String> urlMap = new HashMap<String, String>();
				Map<String, String> creatorMap = new HashMap<String, String>();
				Map<String, String> createTimeMap = new HashMap<String, String>();
				
				if ("authLbpmReaders".equals(request.getParameter("item"))) {
					List<Map<String, String>> list = new ArrayList<Map<String, String>>();
					Map<String, String> dataMap = new HashMap<String, String>();
					Map<String, String> noteMap = new HashMap<String, String>();
					
					// 流程意见阅读权限 取文档的方式不一样，需要另外处理
					IBaseModel model = null;
					for (Object obj : page.getList()) {
						Object[] data = (Object[]) obj;
						String fdId = (String) data[0];
						String noteId = (String) data[2];
						Map<String, String> listMap = new HashMap<String, String>();
						listMap.put("fdId", fdId);
						listMap.put("noteId", noteId);
						list.add(listMap);

						// p.fd_id, p.fd_model_name, r.fd_note_id, 'table_name', n.fd_fact_node_id, n.fd_fact_node_name ";
						try {
							model = getServiceImp(request).getBaseDao().findByPrimaryKey(fdId, (String) data[1], true);
						} catch (Exception e) {
							logger.debug(data[0] + "所在模块:" + data[1] + "已经被移除,忽略......", e);
							continue;
						}
						String docSubject = LbpmTemplateUtil.getDocSubject(model);
						subjectMap.put(fdId, StringUtil.XMLEscape(docSubject));
						urlMap.put(fdId, HandModelUtil.getUrl(model));
						
						if (model instanceof BaseCreateInfoModel) {
							BaseCreateInfoModel createInfoModel = (BaseCreateInfoModel) model;
							creatorMap.put(fdId, createInfoModel.getDocCreator() == null ? "-" : createInfoModel.getDocCreator().getFdName());
							createTimeMap.put(fdId, DateUtil.convertDateToString(createInfoModel.getDocCreateTime(), DateUtil.PATTERN_DATE));
						}

						noteMap.put(noteId, data[5] + "(" + data[4] + ")");
						dataMap.put(noteId, fdId + ";;" + data[3] + ";;" + data[2]);
					}
					page.setList(list);
					request.setAttribute("dataMap", dataMap);
					request.setAttribute("noteMap", noteMap);
				} else {
					for (Object obj : page.getList()) {
						IBaseModel model = (IBaseModel) obj;
						String docSubject = LbpmTemplateUtil.getDocSubject(model);
						subjectMap.put(model.getFdId(), StringUtil.XMLEscape(docSubject));
						urlMap.put(model.getFdId(), HandModelUtil.getUrl(model));
						if (model instanceof BaseCreateInfoModel) {
							BaseCreateInfoModel createInfoModel = (BaseCreateInfoModel) model;
							creatorMap.put(model.getFdId(), createInfoModel.getDocCreator() == null ? "-" : createInfoModel.getDocCreator().getFdName());
							createTimeMap.put(model.getFdId(), DateUtil.convertDateToString(createInfoModel.getDocCreateTime(), DateUtil.PATTERN_DATE));
						}
					}
				}
				// 记录操作日志
				if (UserOperHelper.allowLogOper("detail", null)) {
					Set<Entry<String, String>> entrySet = subjectMap.entrySet();
					Iterator<Entry<String, String>> iter = entrySet.iterator();
					while (iter.hasNext()) {
						Entry<String, String> entry = iter.next();
						UserOperContentHelper.putFind(entry.getKey(),
								entry.getValue(), null);
					}
				}
				request.setAttribute("subjectMap", subjectMap);
				request.setAttribute("urlMap", urlMap);
				request.setAttribute("creatorMap", creatorMap);
				request.setAttribute("createTimeMap", createTimeMap);
			}
			request.setAttribute("page", page);
		} catch (Exception e) {
			messages.addError(e);
			logger.error("", e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			if ("auth".equals(request.getParameter("type"))) {
				return mapping.findForward("listChildren_auth");
			} else if ("item".equals(request.getParameter("type"))) {
				return mapping.findForward("listChildren_item");
			} else {
				return mapping.findForward("listChildren_doc");
			}
		}
	}

	/**
	 * 根据查出来的流程实例清查获取文档标题等信息
	 * 
	 * @param page
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void getDetailInfo(Page page, HttpServletRequest request)
			throws Exception {
		Map<String, String> subjectMap = new HashMap<String, String>();
		Map<String, String> urlMap = new HashMap<String, String>();

		String item = request.getParameter("item");
		List<Object> list = page.getList();
		if (AbstractDocHandler.HANDLER.equals(item)) { // 当前处理人，获取LbpmExpecterLog对象
			for (int i = 0; i < list.size(); i++) {
				LbpmExpecterLog lbpmExpecterLog = (LbpmExpecterLog) list.get(i);
				LbpmProcess lbpmProcess = (LbpmProcess) getLbpmProcessService()
						.findByPrimaryKey(lbpmExpecterLog.getFdProcessId());
				getSubjectAndUrl(lbpmProcess, request, subjectMap, urlMap);

				Map<String, Object> detailInfo = new HashMap<String, Object>();
				detailInfo.put("fdId", lbpmExpecterLog.getFdId());
				detailInfo.put("fdProcess", lbpmProcess);
				detailInfo.put("fdFactId", ProcessNodeUtil.getProcessNodeName(
						lbpmProcess.getFdId(), lbpmExpecterLog.getFdFactId()));

				list.set(i, detailInfo);
			}
		} else { // 其它处理人，获取LbpmRtNodeHandlersDefine对象
			for (int i = 0; i < list.size(); i++) {
				LbpmRtNodeHandlersDefine lbpmRtNodeHandlersDefine = (LbpmRtNodeHandlersDefine) list
						.get(i);
				getSubjectAndUrl(lbpmRtNodeHandlersDefine.getFdProcess(),
						request, subjectMap, urlMap);

				Map<String, Object> detailInfo = new HashMap<String, Object>();
				detailInfo.put("fdId", lbpmRtNodeHandlersDefine.getFdId());
				detailInfo.put("fdProcess", lbpmRtNodeHandlersDefine
						.getFdProcess());
				detailInfo.put("fdFactId", ProcessNodeUtil.getProcessNodeName(
						lbpmRtNodeHandlersDefine.getFdProcess().getFdId(),
						lbpmRtNodeHandlersDefine.getFdFactId()));

				list.set(i, detailInfo);
			}
		}
		// 记录操作日志
		if (UserOperHelper.allowLogOper("detail", null)) {
			Set<Entry<String, String>> entrySet = subjectMap.entrySet();
			Iterator<Entry<String, String>> iter = entrySet.iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				UserOperContentHelper.putFind(entry.getKey(),
						entry.getValue(), null);
			}
		}
		request.setAttribute("subjectMap", subjectMap);
		request.setAttribute("urlMap", urlMap);
	}

	private void getDetailInfoByItem(Page page, HttpServletRequest request)
			throws Exception {
		Map<String, String> subjectMap = new HashMap<String, String>();
		Map<String, String> urlMap = new HashMap<String, String>();
		Map<String, String> creatorMap = new HashMap<String, String>();
		Map<String, String> createTimeMap = new HashMap<String, String>();

		String item = request.getParameter("item");
		List<Object> list = page.getList();

		// 记录操作日志
		if (UserOperHelper.allowLogOper("detail", null)) {
			Set<Entry<String, String>> entrySet = subjectMap.entrySet();
			Iterator<Entry<String, String>> iter = entrySet.iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				UserOperContentHelper.putFind(entry.getKey(), entry.getValue(), null);
			}
		}
		request.setAttribute("subjectMap", subjectMap);
		request.setAttribute("urlMap", urlMap);
	}

	/**
	 * 获取标题和URL
	 * 
	 * @param lbpmProcess
	 * @param request
	 * @param subjectMap
	 * @param urlMap
	 * @throws Exception
	 */
	private void getSubjectAndUrl(LbpmProcess lbpmProcess,
			HttpServletRequest request, Map<String, String> subjectMap,
			Map<String, String> urlMap) throws Exception {
		IBaseModel model = null;
		try {
			model = getServiceImp(request).getBaseDao().findByPrimaryKey(
					lbpmProcess.getFdId(), lbpmProcess.getFdModelName(), true);
		} catch (Exception e) {
			logger.debug(lbpmProcess.getFdId() + "所在模块:"
					+ lbpmProcess.getFdModelName() + "已经被移除,忽略......", e);
		}
		if (model != null) {
			String docSubject = LbpmTemplateUtil.getDocSubject(model);
			subjectMap.put(lbpmProcess.getFdId(), StringUtil
					.XMLEscape(docSubject));
			urlMap.put(lbpmProcess.getFdId(), HandModelUtil.getUrl(model));
		} else {
			subjectMap.put(lbpmProcess.getFdId(), "【文档不存在】");
		}
	}

	/**
	 * 查询文档类日志明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detailLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String forward = "listLogChildren_doc";
		try {
			String type = request.getParameter("type");
			String fdMainId = request.getParameter("fdMainId");
			String moduleName = request.getParameter("moduleName");
			String item = request.getParameter("item");
		    String titleSearchText = request.getParameter("q.docSubject"); // 前端搜索框输入的模糊搜索关键字

			HQLInfo hqlInfo = buildPageHQLInfo(request);
			if ("doc".equals(type) || "item".equals(type)) { // 在途流程交接、事项交接
				hqlInfo.setJoinBlock(", SysHandoverConfigLog log");
				StringBuffer whereBlock = new StringBuffer();
				whereBlock.append("sysHandoverConfigLogDetail.fdLog.fdId = log.fdId ");
				whereBlock.append("and log.fdModule = :moduleName ");
				whereBlock.append("and log.fdItem = :item ");
				whereBlock.append("and log.fdMain.fdId = :fdMainId ");
				if(StringUtil.isNotNull(titleSearchText)){
					whereBlock.append("and sysHandoverConfigLogDetail.fdDescription like :titleSearchText ");
					hqlInfo.setParameter("titleSearchText", "%"+titleSearchText.trim()+"%");
				}

				hqlInfo.setParameter("fdMainId", fdMainId);
				hqlInfo.setParameter("item", item);
				hqlInfo.setParameter("moduleName", moduleName);
				hqlInfo.setWhereBlock(whereBlock.toString());
				
				// 增加日志状态条件
				String state = request.getParameter("state");
				if (StringUtil.isNotNull(state) && ("1".equals(state) || "2".equals(state))) {
					hqlInfo.setWhereBlock(hqlInfo.getWhereBlock() + " and sysHandoverConfigLogDetail.fdState = :state");
					hqlInfo.setParameter("state", Integer.valueOf(state));
				}

				if ("item".equals(type)) {
					forward = "listLogChildren_item";
				}
			} else { // 文档权限交接
				hqlInfo.setModelName("com.landray.kmss.sys.handover.model.SysHandoverConfigAuthLogDetail");
				StringBuffer whereBlock = new StringBuffer();
				whereBlock.append("sysHandoverConfigAuthLogDetail.fdMain.fdId = :fdMainId ");
				whereBlock.append("and sysHandoverConfigAuthLogDetail.fdModelName = :moduleName ");
				whereBlock.append("and sysHandoverConfigAuthLogDetail.fdAuthType = :item ");	
				if(StringUtil.isNotNull(titleSearchText)){
					whereBlock.append("and sysHandoverConfigAuthLogDetail.fdModelSubject like :titleSearchText ");
					hqlInfo.setParameter("titleSearchText", "%"+titleSearchText.trim()+"%");
				}

				hqlInfo.setParameter("fdMainId", fdMainId);
				hqlInfo.setParameter("item", item);
				hqlInfo.setParameter("moduleName", moduleName);
				hqlInfo.setWhereBlock(whereBlock.toString());
				
				forward = "listLogChildren_auth";
			}

			Page page = getConfigLogDetailServiceImp(request).findPage(hqlInfo);
			// 记录操作日志
			if (UserOperHelper.allowLogOper("detailLog", null)) {
				UserOperHelper.logFindAll(page.getList(),
						getConfigLogDetailServiceImp(request).getModelName());
			}
			if ("auth".equals(type)) {
				Map<String, String> creatorMap = new HashMap<String, String>();
				Map<String, String> createTimeMap = new HashMap<String, String>();
				for (Object obj : page.getList()) {
					SysHandoverConfigAuthLogDetail log = (SysHandoverConfigAuthLogDetail) obj;
					IBaseModel model = null;
					try {
						model = getServiceImp(request).getBaseDao().findByPrimaryKey(log.getFdModelId(), log.getFdModelName(), true);
					} catch (Exception e) {
						continue;
					}
					
					if (model instanceof BaseCreateInfoModel) {
						BaseCreateInfoModel createInfoModel = (BaseCreateInfoModel) model;
						creatorMap.put(log.getFdId(), createInfoModel.getDocCreator().getFdName());
						createTimeMap.put(log.getFdId(), DateUtil.convertDateToString(createInfoModel.getDocCreateTime(), DateUtil.PATTERN_DATE));
					}
				}
				request.setAttribute("creatorMap", creatorMap);
				request.setAttribute("createTimeMap", createTimeMap);
			}
			request.setAttribute("page", page);
		} catch (Exception e) {
			messages.addError(e);
			logger.error("", e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward(forward);
		}
	}

	private ISysOrgElementService sysOrgElementService = null;

	protected ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	/**
	 * 文档权限交接提交任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitAuth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			String mainId = getServiceImp(request).submitAuth(new RequestContext(request));
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("mainId", mainId);
			jsonObject.put("state", true);
			if (UserOperHelper.allowLogOper("submitAuth", null)) {
				UserOperHelper.setModelNameAndModelDesc(
						getServiceImp(request).getModelName());
				UserOperHelper.logMessage(jsonObject.toString());
				UserOperHelper.setOperSuccess(true);
			}
			out.print(jsonObject);
		} catch (Exception e) {
			logger.error("执行处理方法异常", e);
		}
		return null;
	}

}
