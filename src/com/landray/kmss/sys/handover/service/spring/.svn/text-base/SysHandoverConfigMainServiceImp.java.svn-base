package com.landray.kmss.sys.handover.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictListProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.handover.constant.SysHandoverConstant;
import com.landray.kmss.sys.handover.dao.ISysHandoverConfigMainDao;
import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverProvider;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLog;
import com.landray.kmss.sys.handover.model.SysHandoverConfigMain;
import com.landray.kmss.sys.handover.model.SysHandoverTaskSetting;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigLogService;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigMainService;
import com.landray.kmss.sys.handover.service.ISysHandoverService;
import com.landray.kmss.sys.handover.service.spring.HandoverPluginUtils.HandoverConfig;
import com.landray.kmss.sys.handover.support.config.auth.DocAuthItemHandler;
import com.landray.kmss.sys.handover.support.config.auth.DocAuthProvider;
import com.landray.kmss.sys.handover.support.config.doc.handler.HandlerHandler;
import com.landray.kmss.sys.handover.support.config.doc.handler.LaterHandlerHandler;
import com.landray.kmss.sys.handover.support.config.item.AbstractItemHandler;
import com.landray.kmss.sys.handover.support.config.item.ItemDetailPage;
import com.landray.kmss.sys.handover.support.util.HandModelUtil;
import com.landray.kmss.sys.hibernate.spi.ConnectionWrapper;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class SysHandoverConfigMainServiceImp extends BaseServiceImp implements
		ISysHandoverConfigMainService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysHandoverConfigMainServiceImp.class);

	protected ISysHandoverService sysHandoverService;
	protected ISysOrgElementService sysOrgElementService;
	private ISysQuartzCoreService sysQuartzCoreService;

	private ISysHandoverConfigMainDao sysHandoverConfigMainDao;
	private ISysHandoverConfigLogService sysHandoverConfigLogService;

	public void setSysHandoverConfigMainDao(
			ISysHandoverConfigMainDao sysHandoverConfigMainDao) {
		this.sysHandoverConfigMainDao = sysHandoverConfigMainDao;
	}

	public void setSysHandoverService(ISysHandoverService sysHandoverService) {
		this.sysHandoverService = sysHandoverService;
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public void setSysQuartzCoreService(
			ISysQuartzCoreService sysQuartzCoreService) {
		this.sysQuartzCoreService = sysQuartzCoreService;
	}

	public void setSysHandoverConfigLogService(
			ISysHandoverConfigLogService sysHandoverConfigLogService) {
		this.sysHandoverConfigLogService = sysHandoverConfigLogService;
	}

	@Override
	public JSONObject search(RequestContext request) throws Exception {
		String fdKey = request.getParameter("fdKey");
		String fdFromId = request.getParameter("fdFromId");
		String fdToId = request.getParameter("fdToId");
		// 交接人
		SysOrgElement fdFromOrgElement = (SysOrgElement) sysOrgElementService
				.findByPrimaryKey(fdFromId, null, true);
		// 接收人
		SysOrgElement fdToOrgElement = (SysOrgElement) sysOrgElementService
				.findByPrimaryKey(fdToId, null, true);
		return sysHandoverService.configHandoverSearch(fdFromOrgElement,
				fdToOrgElement, fdKey, request);
	}

	@Override
	public JSONObject execute(RequestContext request) throws Exception {
		String mainId = request.getParameter("mainId");

		String fdFromId = request.getParameter("fdFromId");
		String fdToId = request.getParameter("fdToId");
		// 交接人
		SysOrgElement fdFromOrgElement = (SysOrgElement) sysOrgElementService
				.findByPrimaryKey(fdFromId, null, true);
		// 接收人
		SysOrgElement fdToOrgElement = null;
		if (StringUtil.isNotNull(fdToId)) {
			fdToOrgElement = (SysOrgElement) sysOrgElementService
					.findByPrimaryKey(fdToId, null, true);
		}
		String ids = request.getParameter("ids");
		String executeIds = "";
		String module = "";
		String[] idsArr = ids.split(",");
		// 格式化ID
		String split = IHandoverHandler.ID_SPLIT;
		for (int i = 0; i < idsArr.length; i++) {
			executeIds += idsArr[i].substring(idsArr[i].indexOf(split)
					+ split.length())
					+ ",";
			if (StringUtil.isNull(module)) {
				module = idsArr[i].substring(0, idsArr[i].indexOf(split));
			}
		}
		// 模块处理
		request.setParameter("recordIds", executeIds);
		request.setParameter("mainId", mainId);
		JSONObject result = sysHandoverService.configHandoverExecute(
				fdFromOrgElement, fdToOrgElement, module, request);

		// 完成
		sysHandoverConfigMainDao.updateState(
				SysHandoverConstant.HANDOVER_STATE_SUCC, mainId);
		return result;
	}

	@Override
	public Page detail(HttpServletRequest request, HQLInfo pagedHqlInfo) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		if ("auth".equals(request.getParameter("type"))) {	// 文档中权限		
			return detailAuth(requestContext, pagedHqlInfo);			
		} else if ("item".equals(request.getParameter("type"))) {	// 事项交接
			return detailItem(requestContext, pagedHqlInfo);
		} else { // 在途的流程
			String fdFromId = request.getParameter("fdFromId");
			String module = request.getParameter("moduleName");
			String item = request.getParameter("item");
			// 交接人
			SysOrgElement fdFromOrgElement = (SysOrgElement) sysOrgElementService.findByPrimaryKey(fdFromId, null, true);
			return sysHandoverService.detail(pagedHqlInfo, fdFromOrgElement, module, item, requestContext);
		}
	}

	private HandoverItem getHandoverItem(IHandoverProvider provider, String itemName) throws Exception {
		List<HandoverItem> items = provider.items();
		HandoverItem item = null;
		for (HandoverItem hItem : items) {
			if (hItem.getItem().equals(itemName)) {
				item = hItem;
				break;
			}
		}
		return item;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String submit(RequestContext requestContext) throws Exception {
		String fdId = requestContext.getParameter("fdId");
		String fdFromId = requestContext.getParameter("fdFromId");
		String fdToId = requestContext.getParameter("fdToId");
		String fdContent = requestContext.getParameter("fdContent");

		// 交接类型：在途流程(doc),事项交接(item)
		String type = requestContext.getParameter("type");
		// 事项交接模式：替换交接人(0),追加交接人(1)
		String execMode = requestContext.getParameter("execMode");

		SysHandoverConfigMain main = new SysHandoverConfigMain();
		if (StringUtil.isNull(fdId)) {
			fdId = IDGenerator.generateID();
		}
		main.setFdId(fdId);

		JSONObject contentJson = JSONObject.fromObject(fdContent);
		JSONArray modules = (JSONArray) contentJson.get("modules");
		for (int i = 0; i < modules.size(); i++) {
			JSONObject module = modules.getJSONObject(i);
			// 未选择记录的空模块
			if (module.isEmpty()) {
				continue;
			}
			String modelName = module.getString("module");
			JSONArray items = module.getJSONArray("items");
			
			// 获取系统文档类配置
			HandoverConfig config = HandoverPluginUtils.getConfigHandoverByModule(modelName, type);
			for (int j = 0; j < items.size(); j++) {
				JSONObject temp = items.getJSONObject(j);
				String item = temp.getString("item");
				if ("item".equals(type)) {
					// 获取所有交接文档ID
					if (temp.has("isAll") && "true".equals(temp.getString("isAll"))) {
						// 事项交接
						HandoverItem handoverItem = getHandoverItem(config.getProvider(), item);
						if (handoverItem != null) {
							// 执行工作交接
							List<String> ids = ((AbstractItemHandler) handoverItem.getHandler()).getHandoverIds(fdFromId, fdToId, modelName, item);
							temp.element("ids", StringUtil.join(ids, ","));
							temp.remove("isAll");
						}
					}
				} else {
					// 在途流程交接
					HQLInfo hqlInfo = null;
					// 如果是当前处理人，不管是否全选，都会进行处理
					if ("handlerIds".equals(item)) {
						HandlerHandler handler = new HandlerHandler();
						// 如果有ID，则传入
						String selectIds = (String) (temp.has("ids") ? temp
								.remove("ids") : null);
						hqlInfo = handler.getProcessIdsHQLInfo(modelName, fdFromId,
								selectIds);
	
						// 如果是当前处理人，还需要获取流程ID，如果在执行定时任务时当前节点已经结束了，在LbpmExpecterLog表中的记录将会被删除，此时需要获取对应的流程ID作为忽略日志的记录
						hqlInfo.setSelectBlock("lbpmExpecterLog.fdId, lbpmExpecterLog.fdProcessId, lbpmExpecterLog.fdFactId");
						List<Object[]> list = getBaseDao().findValue(hqlInfo);
						List<String> ids = new ArrayList<String>(); // 节点ID
						List<String> processIds = new ArrayList<String>(); // 流程ID
						List<String> factIds = new ArrayList<String>(); // 节点编号
						for (Object[] objs : list) {
							ids.add((String) objs[0]);
							processIds.add((String) objs[1]);
							factIds.add((String) objs[2]);
						}
	
						temp.element("ids", StringUtil.join(ids, ","));
						temp.element("processIds", StringUtil.join(processIds, ","));
						temp.element("factIds", StringUtil.join(factIds, ","));
	
						if (temp.has("isAll")) {
							temp.remove("isAll");
						}
					} else if (temp.has("isAll")
							&& "true".equals(temp.getString("isAll"))) {
						// 获取所有ID
						if ("handlerIds_later".equals(item)) {
							item = "handlerIds";
						}
	
						hqlInfo = new HQLInfo();
						hqlInfo.setSelectBlock("lbpmRtNodeHandlersDefine.fdId");
						LaterHandlerHandler handler = new LaterHandlerHandler();
						handler.buildDetailHQLInfo(hqlInfo, modelName, fdFromId,item,requestContext);
						List<String> ids = getBaseDao().findValue(hqlInfo);
						temp.element("ids", StringUtil.join(ids, ","));
						temp.remove("isAll");
					}
				}
			}
		}
		SysOrgElement fdFromOrgElement = (SysOrgElement) sysOrgElementService
				.findByPrimaryKey(fdFromId, null, true);
		main.setFdFromId(fdFromId);
		main.setFdFromName(fdFromOrgElement.getFdName());

		if (StringUtil.isNotNull(fdToId)) {
			SysOrgElement fdToOrgElement = (SysOrgElement) sysOrgElementService
					.findByPrimaryKey(fdToId, null, true);
			main.setFdToId(fdToId);
			main.setFdToName(fdToOrgElement.getFdName());
		}
		main.setTotal(Long.valueOf((Integer) contentJson.get("total")));
		main.setFdContent(contentJson.toString());
		main.setDocCreateTime(new Date());
		main.setDocCreator(UserUtil.getUser());
		if ("item".equals(type)) {
			main.setHandoverType(SysHandoverConstant.HANDOVER_TYPE_ITEM);
			main.setExecMode(StringUtil.getIntFromString(execMode, 0));
		} else {
			main.setHandoverType(SysHandoverConstant.HANDOVER_TYPE_DOC);
		}
		main.setFdState(SysHandoverConstant.HANDOVER_STATE_WAIT); // 等待执行

		// 添加定时任务
		saveQuartz(main, type);
		// 记录操作日志
		if (UserOperHelper.allowLogOper("submit", getModelName())) {
			UserOperContentHelper
					.putAdd(main.getFdId(), main.getFdFromId(), getModelName())
					.putSimple("fdFromName", main.getFdFromName())
					.putSimple("fdToId", main.getFdToId())
					.putSimple("fdToName", main.getFdToName())
					.putSimple("total", main.getTotal())
					.putSimple("fdContent", main.getFdContent())
					.putSimple("docCreateTime", main.getDocCreateTime())
					.putSimple("docCreator", main.getDocCreator())
					.putSimple("handoverType", main.getHandoverType())
					.putSimple("fdState", main.getFdState());
		}
		return add(main);
	}

	/**
	 * 生成定时任务
	 * 
	 * @param handMain
	 * @throws Exception
	 */
	private void saveQuartz(SysHandoverConfigMain handMain, String type)
			throws Exception {
		SysQuartzModelContext quartzContext = new SysQuartzModelContext();
		quartzContext.setQuartzJobMethod("executeJob");
		quartzContext.setQuartzJobService("sysHandoverConfigMainService");
		quartzContext.setQuartzKey("executeJob");
		JSONObject parameter = new JSONObject();
		parameter.put("fdId", handMain.getFdId());
		parameter.put("type", type);
		quartzContext.setQuartzParameter(parameter.toString());
		String toName = handMain.getFdToName();
		if (StringUtil.isNull(toName)) {
			toName = "无交接人";
		}
		quartzContext.setQuartzSubject(handMain.getFdFromName()
				+ "==>"
				+ toName
				+ "_"
				+ ResourceUtil.getString("sys-handover:sysHandoverConfigMain.handoverType." + type)
				+ "工作交接_"
				+ DateUtil.convertDateToString(handMain.getDocCreateTime(),
						DateUtil.PATTERN_DATETIME));
		quartzContext.setQuartzCronExpression(getCronExpression(handMain
				.getDocCreateTime(), -1));
		quartzContext.setQuartzRequired(true); // 必须执行
		sysQuartzCoreService.saveScheduler(quartzContext, handMain);
	}

	/**
	 * 执行job
	 * 
	 * @param context
	 * @throws Exception
	 */
	@Override
	public void executeJob(SysQuartzJobContext context) throws Exception {
		JSONObject parameter = JSONObject.fromObject(context.getParameter());
		SysHandoverConfigMain handMain = (SysHandoverConfigMain) this
				.findByPrimaryKey(parameter.getString("fdId"));
		// 已经执行过
		if (SysHandoverConstant.HANDOVER_STATE_SUCC == handMain.getFdState()) {
			logger.error("该定时任务已经执行过，请勿手动非法执行");
			return;
		}
		String type = parameter.getString("type");
		String content = handMain.getFdContent();
		JSONObject contentJson = JSONObject.fromObject(content);
		JSONArray modules = (JSONArray) contentJson.get("modules");
		// 按module处理
		for (int i = 0; i < modules.size(); i++) {
			JSONObject module = modules.getJSONObject(i);
			// 未选择记录的空模块
			if (module.isEmpty()) {
				continue;
			}
			sysHandoverService.docHandoverExecute(module, handMain, type);
		}

		// 完成
		sysHandoverConfigMainDao.updateState(
				SysHandoverConstant.HANDOVER_STATE_SUCC, handMain.getFdId());
	}

	/**
	 * cronExpression表达式
	 */
	private String getCronExpression(Date date, int beforeDay) throws Exception {
		StringBuffer cronExpression = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SysHandoverTaskSetting().getRunTimeForJob());
		cronExpression.append("0 ")
			.append(calendar.get(Calendar.MINUTE) + " ")
			.append(calendar.get(Calendar.HOUR_OF_DAY) + " ")
			.append(calendar.get(Calendar.DAY_OF_MONTH) + " ")
			.append((calendar.get(Calendar.MONTH) + 1) + " ")
			.append("? " + calendar.get(Calendar.YEAR));
		 return cronExpression.toString();
	}

	@Override
	public String submitAuth(RequestContext request) throws Exception {
		String fdId = request.getParameter("fdId");
		String fdFromId = request.getParameter("fdFromId");
		String fdToId = request.getParameter("fdToId");
		String fdContent = request.getParameter("fdContent");

		SysHandoverConfigMain main = new SysHandoverConfigMain();
		if (StringUtil.isNull(fdId)) {
			fdId = IDGenerator.generateID();
		}
		main.setFdId(fdId);
		
		SysOrgElement fdFromOrgElement = (SysOrgElement) sysOrgElementService.findByPrimaryKey(fdFromId, null, true);
		main.setFdFromId(fdFromId);
		main.setFdFromName(fdFromOrgElement.getFdName());
		SysOrgElement fdToOrgElement = (SysOrgElement) sysOrgElementService.findByPrimaryKey(fdToId, null, true);
		main.setFdToId(fdToId);
		main.setFdToName(fdToOrgElement.getFdName());
		
		main.setFdContent(fdContent);
		main.setDocCreateTime(new Date());
		main.setDocCreator(UserUtil.getUser());
		main.setHandoverType(SysHandoverConstant.HANDOVER_TYPE_AUTH);
		main.setFdState(SysHandoverConstant.HANDOVER_STATE_WAIT); // 等待执行

		return add(main);
	}

	/*********************************** 以下为文档权限交接任务 ****************************************/

	/**
	 * 查询权限文档的明细
	 * 
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private Page detailAuth(RequestContext requestContext, HQLInfo pagedHqlInfo) throws Exception {
		String item = requestContext.getParameter("item");
		DocAuthItemHandler handler = new DocAuthItemHandler(item);
		return handler.detail(pagedHqlInfo, requestContext);
	}

	// 任务是否正在运行
	private boolean isRunning = false;
	// 是否终止权限交接任务
	private boolean isEndForAuthJob = false;
	// 终止权限任务的定时器
	private Timer timer;

	/**
	 * 设置一个定时器，用于中断权限交接任务
	 * <p>
	 * 当权限交接执行时间超过后台配置时长时，中断交接任务
	 * 
	 * @throws Exception
	 */
	private void setTimerTaskForAuthJob() throws Exception {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				isEndForAuthJob = true;
				isRunning = false;
			}
		}, new SysHandoverTaskSetting().getRuntimes() * 60 * 60 * 1000);
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void executeAuth() throws Exception {
		if (isRunning) {
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("开始执行“文档权限”交接工作：" + DateUtil.convertDateToString(
					new Date(), DateUtil.PATTERN_DATETIME));
		}
		
		isRunning = true;
		isEndForAuthJob = false;
		Connection con = null;
		SysHandoverConfigMain configMain = null;
		try {
			setTimerTaskForAuthJob();
			con = ConnectionWrapper.getInstance().getConnection(getBaseDao().openSession());

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("fdId");
			hqlInfo.setWhereBlock("handoverType = 3 and fdState in(0, 2, 3)");
			hqlInfo.setOrderBy("fdState desc");
			Iterator<String> iter = getBaseDao().findValueIterator(hqlInfo);
			while (iter.hasNext()) {
				if (isEndForAuthJob) // 到了终止时间，不再继续执行交接任务，等待下次再执行
				{
					break;
				}
	
				// 交接任务ID
				String fdId = iter.next();
				configMain = (SysHandoverConfigMain) findByPrimaryKey(fdId);
				JSONObject contentJson = JSONObject.fromObject(configMain.getFdContent());
				JSONArray items = contentJson.getJSONArray("items");
				long allTotal = 0L;

				if (logger.isDebugEnabled()) {
					logger.debug("“文档权限”交接工作内容：" + contentJson.toString());
				}
				for (int i = 0; i < items.size(); i++) {
					if (isEndForAuthJob) // 到了终止时间，不再继续执行交接任务，等待下次再执行
					{
						break;
					}
	
					JSONObject item = items.getJSONObject(i);
					String authType = item.getString("id");
					JSONArray modules = item.getJSONArray("modules");
					long authTypeTotal = item.getLong("total");
					for (int j = 0; j < modules.size(); j++) {
						if (isEndForAuthJob) // 到了终止时间，不再继续执行交接任务，等待下次再执行
						{
							break;
						}
	
						JSONObject module = modules.getJSONObject(j);
						String moduleName = module.getString("id");
						SysDictModel dictModel = SysDataDict.getInstance().getModel(moduleName);
						Map<String, SysDictCommonProperty> propertyMap = dictModel.getPropertyMap();
						long total = module.getLong("total");
						Date startTime = new Date();

						String[] selectedIds = null;
						if (module.containsKey("itemIds")) {
							String itemIds = module.getString("itemIds"); // 自行选择的交接文档
							if (StringUtil.isNotNull(itemIds)) {
								selectedIds = itemIds.split(",");
							}
						}

						if ("authReaders".equals(authType)) {
							// 阅读权限：
							// 查找authAllReaders，如果有交接人有接收人，则不处理
							// 查找authAllReaders，如果有交接人无接收人，则增加接收人，并在authOtherReaders中增加接收人
							// 备注：若部署过程出现有authAllReaders，但无authOtherReaders的模块，需重新讨论方案
							
							SysDictCommonProperty commonOtherProperty = propertyMap.get("authOtherReaders");
							Set<String[]> updateIds = new HashSet<String[]>();
							if (commonOtherProperty != null) {
								total = executeAuth(propertyMap, moduleName, "authAllReaders", configMain, con, authType, selectedIds, updateIds);
								if (!updateIds.isEmpty()) {
									executeAuthOther(propertyMap, "authOtherReaders", configMain, con, selectedIds, updateIds);
								}
							}
						} else if ("authEditors".equals(authType)) {
							// 编辑权限：
							// 查找authAllEditors，如果有交接人有接收人，则不处理
							// 查找authAllEditors，如果有交接人无接收人，则增加接收人，并在authOtherEditors中增加接收人，同时判断authAllReaders，若无接收人则追加
							// 备注：若部署过程出现有authAllEditors，但无authOtherEditors的模块，需重新讨论方案

							SysDictCommonProperty commonOtherProperty = propertyMap.get("authOtherEditors");
							Set<String[]> updateIds = new HashSet<String[]>();
							if (commonOtherProperty != null) {
								total = executeAuth(propertyMap, moduleName, "authAllEditors", configMain, con, authType, selectedIds, updateIds);
								if (!updateIds.isEmpty()) {
									executeAuthOther(propertyMap, "authOtherEditors", configMain, con, selectedIds, updateIds);
								}
							}
						} else if ("authLbpmReaders".equals(authType)) {
							// 流程意见权限（与阅读权限平级）：
							// 查找lbpm_audit_note_reader，如果有交接人有接收人，则不处理
							// 查找lbpm_audit_note_reader，如果有交接人无接收人，则增加接收人
							// 查找lbpm_audit_note_rt_reader，如果有交接人有接收人，则不处理
							// 查找lbpm_audit_note_rt_reader，如果有交接人无接收人，则增加接收人
	
							total = executeAuthForLbpm(moduleName, configMain, con, authType, selectedIds);
						} else if ("authAttPrints".equals(authType)) {
							// 附件可打印者：
							// 查找authAttPrints，如果有交接人有接收人，则不处理
							// 查找authAttPrints，如果有交接人无接收人，则增加接收人
	
							total = executeAuth(propertyMap, moduleName, "authAttPrints", configMain, con, authType, selectedIds, null);
						} else if ("authAttCopys".equals(authType)) {
							// 附件拷贝权限：
							// 查找authAttCopys，如果有交接人有接收人，则不处理
							// 查找authAttCopys，如果有交接人无接收人，则增加接收人
	
							total = executeAuth(propertyMap, moduleName, "authAttCopys", configMain, con, authType, selectedIds, null);
						} else if ("authAttDownloads".equals(authType)) {
							// 附件下载权限：
							// 查找authAttDownloads，如果有交接人有接收人，则不处理
							// 查找authAttDownloads，如果有交接人无接收人，则增加接收人
	
							total = executeAuth(propertyMap, moduleName, "authAttDownloads", configMain, con, authType, selectedIds, null);
						}
						if (total > 0) {
							saveAuthLog(configMain, authType, moduleName,
									ResourceUtil.getString(dictModel.getMessageKey()), total, startTime);
							authTypeTotal += total;
							module.put("total", total);
						}
					}
					item.put("total", authTypeTotal);
					allTotal += authTypeTotal;
				}
				configMain.setTotal(allTotal);
				configMain.setFdContent(contentJson.toString());
				if (isEndForAuthJob) {
					configMain.setFdState(SysHandoverConstant.HANDOVER_STATE_EXECUTING);
				} else {
					configMain.setFdState(SysHandoverConstant.HANDOVER_STATE_SUCC);
				}
				update(configMain);
			}
		} catch (Exception e) {
			// 失败时需要修改交接状态为“执行失败”
			configMain.setFdState(SysHandoverConstant.HANDOVER_STATE_FAIL);
			update(configMain);
			throw e;
		} finally {
			JdbcUtils.closeConnection(con);
			// 销毁定时器
			timer.cancel();
			isRunning = false;
		}
	}

	/**
	 * 保存文档权限交接日志
	 * 
	 * @param configMain
	 * @param item
	 * @param module
	 * @param moduleName
	 * @param total
	 * @param startTime
	 * @throws Exception
	 */
	private void saveAuthLog(SysHandoverConfigMain configMain, String item,
			String module, String moduleName, long total, Date startTime) throws Exception {
		SysHandoverConfigLog sysHandoverConfigLog = new SysHandoverConfigLog();
		sysHandoverConfigLog.setFdMain(configMain);
		sysHandoverConfigLog.setFdIsSucc(true);
		sysHandoverConfigLog.setFdStatus(SysHandoverConstant.HANDOVER_STATE_SUCC);
		sysHandoverConfigLog.setFdCount(total);
		sysHandoverConfigLog.setFdModule(module);
		sysHandoverConfigLog.setFdModuleName(moduleName);
		sysHandoverConfigLog.setFdItem(item);
		sysHandoverConfigLog.setFdItemName(DocAuthProvider.getMessage(item));
		sysHandoverConfigLog.setFdStartTime(startTime);
		sysHandoverConfigLog.setFdEndedTime(new Date());
		sysHandoverConfigLogService.add(sysHandoverConfigLog);
	}

	/**
	 * 保存文档权限交接日志明细
	 * 
	 * @throws Exception
	 */
	private void saveAuthLogDetail(SysHandoverConfigMain configMain, String authType,
			List<String[]> docList, Connection con) throws Exception {
		if (!docList.isEmpty()) {
			String sql = "insert into sys_handover_auth_log_detail (fd_id, fd_model_id, fd_model_name, fd_model_message_key, fd_model_subject, fd_model_url, fd_auth_type, fd_main_id, doc_create_time, fd_node_name) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(sql);
				IBaseModel model = null;
				for (String[] docs : docList) {
					model = getBaseDao().findByPrimaryKey(docs[0], docs[1], true);
					ps.setString(1, IDGenerator.generateID());
					ps.setString(2, docs[0]); // fd_model_id
					ps.setString(3, docs[1]); // fd_model_name
					ps.setString(4, HandoverPluginUtils.getAuthModel().get(docs[1])); // fd_model_message_key
					ps.setString(5, HandModelUtil.getDocSubject(model)); // fd_model_subject
					ps.setString(6, HandModelUtil.getUrl(model)); // fd_model_url
					ps.setString(7, authType); // fd_auth_type
					ps.setString(8, configMain.getFdId()); // fd_main_id
					ps.setTimestamp(9, new java.sql.Timestamp(System.currentTimeMillis())); // doc_create_time
					ps.setString(10, docs[2]); // fd_node_name
					ps.addBatch();
				}
				ps.executeBatch();
			} finally {
				JdbcUtils.closeStatement(ps);
			}
		}
	}

	/**
	 * 执行权限交接：流程意见权限
	 * 
	 * @return
	 * @throws Exception
	 */
	private long executeAuthForLbpm(String modelName, SysHandoverConfigMain configMain,
			Connection con, String authType, String[] selectedIds) throws Exception {
		String[] tableNames = { "lbpm_audit_note_reader", "lbpm_audit_note_rt_reader" };
		String selectSql = "select r.fd_note_id, r.fd_process_id, p.fd_model_id, p.fd_model_name, n.fd_fact_node_id, n.fd_fact_node_name"
				+ " from {table} r, lbpm_process p, lbpm_audit_note n where r.fd_org_id = ? and r.fd_process_id = p.fd_id and p.fd_model_name = ? and n.fd_id = r.fd_note_id"
				+ " and r.fd_note_id not in (select distinct r1.fd_note_id from {table} r1, lbpm_process p1 where p1.fd_model_name = ? and r1.fd_org_id = ? and r1.fd_process_id = p1.fd_id)";
		String insertSql = "insert into {table} (fd_id, fd_note_id, fd_process_id, fd_org_id) values (?, ?, ?, ?)";
		long total = 0;
		String fromId = configMain.getFdFromId();
		String toId = configMain.getFdToId();
		PreparedStatement selectPs = null; // 查询
		PreparedStatement insertPs = null; // 追加
		ResultSet rs = null;

		Map<String, List<String>> selectedIdMap = new HashMap<String, List<String>>();
		// 文档ID, 表名, 节点ID
		// 15e4bdacd5895cbee10d1ec4e68af2ff;;lbpm_audit_note_reader;;15e4bdb8c452ffb3b2ddd474e979c280
		if (selectedIds != null && selectedIds.length > 0) {
			for (String id : selectedIds) {
				String[] __id = id.split(";;");
				List<String> ids = selectedIdMap.get(__id[1]);
				if (ids == null) {
					ids = new ArrayList<String>();
					ids.add(__id[2]);
					selectedIdMap.put(__id[1], ids);
				} else {
					ids.add(__id[2]);
				}
			}
		}

		List<String[]> docList = new ArrayList<String[]>();
		for (String tableName : tableNames) {
			int updateCount = new SysHandoverTaskSetting().getBatchUpdateCount();
			int index = 0;
			try {
				List<String> ids = selectedIdMap.get(tableName);
				if (ids != null) {
					selectSql += " and r.fd_note_id " + HQLUtil.buildLogicIN("", ids);
				}

				selectPs = con.prepareStatement(StringUtil.replace(selectSql, "{table}", tableName));
				selectPs.setString(1, fromId);
				selectPs.setString(2, modelName);
				selectPs.setString(3, modelName);
				selectPs.setString(4, toId);
				rs = selectPs.executeQuery();

				insertPs = con.prepareStatement(StringUtil.replace(insertSql, "{table}", tableName));
				while (rs.next()) {
					if (index >= updateCount) {
						insertPs.executeBatch();
						saveAuthLogDetail(configMain, authType, docList, con);
						docList.clear();
						index = 0;
					}
					insertPs.setString(1, IDGenerator.generateID());
					insertPs.setString(2, rs.getString(1));
					insertPs.setString(3, rs.getString(2));
					insertPs.setString(4, toId);
					insertPs.addBatch();
					// modelId, modelName, nodeName
					docList.add(new String[] { rs.getString(3), rs.getString(4), rs.getString(6) + "(" + rs.getString(5) + ")" });
					total++;
					index++;
				}
				if (index > 0) {
					insertPs.executeBatch();
				}
				if (!docList.isEmpty()) {
					saveAuthLogDetail(configMain, authType, docList, con);
					docList.clear();
				}
			} finally {
				JdbcUtils.closeStatement(selectPs);
				JdbcUtils.closeStatement(insertPs);
				JdbcUtils.closeResultSet(rs);
			}
		}

		return total;
	}

	/**
	 * 追加其它可阅读者（其它可编辑者）权限
	 * 
	 * @param propertyMap
	 * @param property
	 * @param configMain
	 * @param con
	 * @param docIds
	 * @return
	 * @throws Exception
	 */
	private long executeAuthOther(Map<String, SysDictCommonProperty> propertyMap,
			String property, SysHandoverConfigMain configMain, Connection con, String[] selectedIds,
			Set<String[]> updateIds) throws Exception {
		long total = 0L;
		SysDictCommonProperty commonProperty = propertyMap.get(property);
		if (commonProperty != null && commonProperty instanceof SysDictListProperty) {
			SysDictListProperty listProperty = (SysDictListProperty) commonProperty;
			String tableName = listProperty.getTable(); // 取表名
			String elementColumn = listProperty.getElementColumn(); // 取机构字段名
			String docColumn = listProperty.getColumn(); // 取主文档字段名

			int updateCount = new SysHandoverTaskSetting().getBatchUpdateCount();
			int index = 0;
			String fromId = configMain.getFdFromId();
			String toId = configMain.getFdToId();
			PreparedStatement selectPs = null; // 查询
			PreparedStatement insertPs = null; // 追加
			ResultSet rs = null;
			try {
				// 查询
				StringBuffer selectSql = new StringBuffer(1000);
				selectSql.append("select ").append(docColumn).append(" from ").append(tableName)
						.append(" where ").append(elementColumn).append(" = ?")
						.append(" and ").append(docColumn).append(" not in ")
						.append("(select distinct ").append(docColumn)
						.append(" from ").append(tableName)
						.append(" where ").append(elementColumn).append(" = ?)");
				if (selectedIds != null && selectedIds.length > 0) {
					selectSql.append(" and ").append(docColumn)
							.append(HQLUtil.buildLogicIN("", ArrayUtil.convertArrayToList(selectedIds)));
				}
				
				if (logger.isDebugEnabled()) {
					logger.debug("“文档权限”查询SQL：" + selectSql.toString() + "参数：["
							+ fromId + "," + toId + "]");
				}

				selectPs = con.prepareStatement(selectSql.toString());
				selectPs.setString(1, fromId);
				selectPs.setString(2, toId);
				rs = selectPs.executeQuery();

				while (rs.next()) {
					updateIds.add(new String[] { rs.getString(1), toId });
				}
	
				// 追加
				StringBuffer insertSql = new StringBuffer(1000);
				insertSql.append("insert into ").append(tableName).append(" (")
						.append(docColumn).append(", ").append(elementColumn)
						.append(") values (?, ?)");
				if (logger.isDebugEnabled()) {
					logger.debug("“文档权限”追加SQL：" + insertSql.toString());
				}

				insertPs = con.prepareStatement(insertSql.toString());
				for (String[] ids : updateIds) {
					try {
						if (index >= updateCount) {
							insertPs.executeBatch();
							index = 0;
						}
						insertPs.setString(1, ids[0]);
						insertPs.setString(2, ids[1]);
						insertPs.addBatch();
						total++;
						index++;
					}catch(Exception e) {
						logger.error("-----执行文档权限追加操作时发生异常-----" , e);
						logger.error("-----异常id：-----" + ids[0]+"-----异常id：-----" + ids[1]);
					}
				}
				if (index > 0) {
					insertPs.executeBatch();
				}
			}catch(Exception e) {
				logger.error("执行文档权限追加操作时发生异常",e);
			} finally {
				JdbcUtils.closeStatement(selectPs);
				JdbcUtils.closeStatement(insertPs);
				JdbcUtils.closeResultSet(rs);
			}
		}
		return total;
	}

	/**
	 * 执行权限交接：阅读权限、编辑权限、附件可打印者、附件拷贝权限、附件下载权限
	 * 
	 * @param propertyMap
	 * @param modelName
	 * @param property
	 * @param configMain
	 * @param con
	 * @param authType
	 * @param updateIds
	 * @return
	 * @throws Exception
	 */
	private long executeAuth(Map<String, SysDictCommonProperty> propertyMap, String modelName,
			String property, SysHandoverConfigMain configMain, Connection con, String authType,
			String[] selectedIds, Set<String[]> updateIds) throws Exception {
		long total = 0L;
		SysDictCommonProperty commonProperty = propertyMap.get(property);
		if (commonProperty != null && commonProperty instanceof SysDictListProperty) {
			SysDictListProperty listProperty = (SysDictListProperty) commonProperty;
			String tableName = listProperty.getTable(); // 取表名
			String elementColumn = listProperty.getElementColumn(); // 取机构字段名
			String docColumn = listProperty.getColumn(); // 取主文档字段名

			int updateCount = new SysHandoverTaskSetting().getBatchUpdateCount();
			int index = 0;
			String fromId = configMain.getFdFromId();
			String toId = configMain.getFdToId();
			PreparedStatement selectPs = null; // 查询
			PreparedStatement insertPs = null; // 追加
			ResultSet rs = null;
			List<String[]> docList = new ArrayList<String[]>();
			try {
				// 查询
				StringBuffer selectSql = new StringBuffer(1000);
				selectSql.append("select ").append(docColumn).append(" from ").append(tableName)
						.append(" where ").append(elementColumn).append(" = ?")
						.append(" and ").append(docColumn).append(" not in ")
						.append("(select distinct ").append(docColumn)
						.append(" from ").append(tableName)
						.append(" where ").append(elementColumn).append(" = ?)");
				if (selectedIds != null && selectedIds.length > 0) {
					selectSql.append(" and ").append(docColumn)
							.append(HQLUtil.buildLogicIN("", ArrayUtil.convertArrayToList(selectedIds)));
				}
				
				if (logger.isDebugEnabled()) {
					logger.debug("“文档权限”查询SQL：" + selectSql.toString() + "参数：["
							+ fromId + "," + toId + "]");
				}

				selectPs = con.prepareStatement(selectSql.toString());
				selectPs.setString(1, fromId);
				selectPs.setString(2, toId);
				rs = selectPs.executeQuery();
	
				// 追加
				StringBuffer insertSql = new StringBuffer(1000);
				insertSql.append("insert into ").append(tableName).append(" (")
						.append(docColumn).append(", ").append(elementColumn)
						.append(") values (?, ?)");

				if (logger.isDebugEnabled()) {
					logger.debug("“文档权限”追加SQL：" + insertSql.toString());
				}

				insertPs = con.prepareStatement(insertSql.toString());
				String docId = null;
				while (rs.next()) {
					if (index >= updateCount) {
						insertPs.executeBatch();
						saveAuthLogDetail(configMain, authType, docList, con);
						docList.clear();
						index = 0;
					}
					docId = rs.getString(1);
					insertPs.setString(1, docId);
					insertPs.setString(2, toId);
					insertPs.addBatch();
					if (updateIds != null) {
						updateIds.add(new String[] { docId, toId });
					}
					docList.add(new String[] { docId, modelName, null });
					total++;
					index++;
				}
				if (index > 0) {
					insertPs.executeBatch();
				}
				if (!docList.isEmpty()) {
					saveAuthLogDetail(configMain, authType, docList, con);
					docList.clear();
				}
			} finally {
				JdbcUtils.closeStatement(selectPs);
				JdbcUtils.closeStatement(insertPs);
				JdbcUtils.closeResultSet(rs);
			}
		}
		return total;
	}

	/*********************************** 以下为事项交接任务 ****************************************/

	/**
	 * 事项交接明细
	 * 
	 * @param request
	 * @param pagedHqlInfo
	 * @return
	 * @throws Exception
	 */
	private Page detailItem(RequestContext requestContext, HQLInfo pagedHqlInfo)
			throws Exception {
		String fdFromId = requestContext.getParameter("fdFromId");
		String fdToId = requestContext.getParameter("fdToId");
		String module = requestContext.getParameter("moduleName");
		String item = requestContext.getParameter("item");
		String type = requestContext.getParameter("type");
		HandoverConfig config = HandoverPluginUtils.getConfigHandoverByModule(
				module, type);
		IHandoverProvider provider = config.getProvider();
		List<HandoverItem> items = provider.items();
		ItemDetailPage page = null;
		for (HandoverItem hItem : items) {
			if (hItem.getItem().equals(item)) {
				AbstractItemHandler handler = (AbstractItemHandler) hItem.getHandler();
				page = handler.detail(pagedHqlInfo, fdFromId, fdToId, module, item, requestContext);
				break;
			}
		}
		if (page == null) {
			page = new ItemDetailPage();
		}
		// 因为明细列表中的数据除了固定的“ID”、“标题”、“URL”之外，业务模块还可以自定义其它的属性，所以这里就不适合用Page来处理了，而是对返回的数据进行了处理
		requestContext.setAttribute("itemPage", page);
		return page.getPage();
	}

}
