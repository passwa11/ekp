package com.landray.kmss.sys.handover.service.spring;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.constant.SysAuthConstant;
import org.slf4j.Logger;
import org.springframework.util.Assert;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.handover.constant.SysHandoverConstant;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteResult;
import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchResult;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverProvider;
import com.landray.kmss.sys.handover.model.SysHandoverConfigMain;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigLogDetailService;
import com.landray.kmss.sys.handover.service.ISysHandoverLogService;
import com.landray.kmss.sys.handover.service.ISysHandoverService;
import com.landray.kmss.sys.handover.service.spring.HandoverPluginUtils.HandoverConfig;
import com.landray.kmss.sys.handover.support.config.auth.DocAuthProvider;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.spring.OrgDialogUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysHandoverServiceImp
		implements ISysHandoverService, IXMLDataBean {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysHandoverServiceImp.class);

	protected ISysHandoverLogService sysHandoverLogService;
	protected ISysHandoverConfigLogDetailService sysHandoverConfigLogDetailService;
	protected ISysOrgElementService sysOrgElementService;

	public void setSysHandoverLogService(
			ISysHandoverLogService sysHandoverLogService) {
		this.sysHandoverLogService = sysHandoverLogService;
	}

	public void setSysHandoverConfigLogDetailService(
			ISysHandoverConfigLogDetailService sysHandoverConfigLogDetailService) {
		this.sysHandoverConfigLogDetailService = sysHandoverConfigLogDetailService;
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	@Override
	public JSONObject configHandoverSearch(SysOrgElement fromOrgElement,
										   SysOrgElement toOrgElement, String module,
										   RequestContext requestContext) throws Exception {
		Assert.notNull(fromOrgElement);
		Assert.hasLength(module);

		String type = requestContext.getParameter("type");
		HandoverConfig config = HandoverPluginUtils.getConfigHandoverByModule(
				module, type);
		if (config.getHandler() != null) { //处理方式是handler
			JSONObject jsonObject = new JSONObject();
			HandoverSearchContext handoverSearchContext = new HandoverSearchContext(
					fromOrgElement, toOrgElement, config);
			// 执行工作交接搜索
			config.getHandler().search(handoverSearchContext);
			HandoverSearchResult result = handoverSearchContext
					.getHandoverSearchResult();
			//如果是矩阵组织就返回构造的json格式
			if("handOverSysOrgMatrix".equals(config.getModule())){
				jsonObject = handoverSearchContext.getSysMatrixJson();
			}else{
				jsonObject = JSONObject.fromObject(result);
			}
			return jsonObject;
		} else {
			IHandoverProvider provider = null;
			// 如果是文档权限，还需要获取选择的“交接权限类型”
			List<String> authItems = null;
			if ("auth".equals(type)) {
				authItems = new ArrayList<String>();
				String fdAuthType = requestContext.getParameter("authType");
				authItems.addAll(Arrays.asList(fdAuthType.split(",")));
				// 权限类交接强制使用默认的提供者
				provider = new DocAuthProvider();
			} else {
				provider = getHandoverProvider(config);
			}
			JSONObject handJsonObject = new JSONObject();
			Long total = 0L;

			for (HandoverItem item : provider.items()) {
				if (authItems != null && !authItems.contains(item.getItem())) {
					continue;
				}

				HandoverSearchContext handoverSearchContext = new HandoverSearchContext(
						fromOrgElement, toOrgElement, config, item);

				item.getHandler().search(handoverSearchContext);
				HandoverSearchResult result = handoverSearchContext
						.getHandoverSearchResult();
				if (provider.items().size() == 1) {
					JSONArray itemArray = new JSONArray();
					itemArray.add(JSONObject.fromObject(result));
					handJsonObject.accumulate("item", itemArray);
				} else {
					handJsonObject.accumulate("item", JSONObject
							.fromObject(result));
				}
				total += result.getTotal();
			}
			handJsonObject.put("module", config.getModule());
			handJsonObject.put("total", total);
			handJsonObject.put("moduleMessageKey", config.getMessageKey());
			return handJsonObject;
		}
	}

	private IHandoverProvider getHandoverProvider(HandoverConfig config) {
		IHandoverProvider provider = config.getProvider();
		Assert.notNull(provider);
		return provider;
	}

	@Override
	public JSONObject configHandoverExecute(SysOrgElement from,
											SysOrgElement to, String module, RequestContext context)
			throws Exception {
		Assert.notNull(from);
		Assert.hasLength(module);

		String mainId = context.getParameter("mainId");
		String type = context.getParameter("type");
		Integer handoverType = null;
		if ("auth".equals(type)) { // 文档权限
			handoverType = SysHandoverConstant.HANDOVER_TYPE_AUTH;
		}
		if ("config".equals(type)) { // 模板配置
			handoverType = SysHandoverConstant.HANDOVER_TYPE_CONFIG;
		}
		HandoverConfig config = HandoverPluginUtils.getConfigHandoverByModule(
				module, type);

		Assert.hasLength(mainId);
		String recordIdString = context.getParameter("recordIds");
		String[] recordIds = recordIdString.split(",");
		List<String> selectedHandoveRecordIds = Arrays.asList(recordIds);

		HandoverExecuteContext handoverExecuteContext = null;
		// 结果集
		List<HandoverExecuteResult> executeResults = new ArrayList<HandoverExecuteResult>();
		if (config.getHandler() != null) {
			// 新增日志
			String logId = sysHandoverLogService.beforeConfigHandover(config,
					null, from, to, mainId, handoverType);

			handoverExecuteContext = new HandoverExecuteContext(from, to,
					selectedHandoveRecordIds, config, logId);
			try {
				// 执行工作交接
				config.getHandler().execute(handoverExecuteContext);
			} catch (Exception e) {
				logger.error("交接失败：", e);
				handoverExecuteContext.getHandoverExecuteResult().setException(
						e);
				throw new RuntimeException(e);
			} finally {
				sysHandoverLogService
						.afterConfigHandover(handoverExecuteContext);
				// 记录结果
				executeResults.add(handoverExecuteContext
						.getHandoverExecuteResult());
			}
		} else {
			IHandoverProvider provider = getHandoverProvider(config);

			List<HandoverItem> items = provider.items();
			for (HandoverItem item : items) {
				// 新增日志
				String logId = sysHandoverLogService.beforeConfigHandover(
						config, item, from, to, mainId, handoverType);
				// Id按照item分类
				Map<String, List<String>> IdsMap = new HashMap<String, List<String>>();
				// 分割符
				String split = IHandoverHandler.ID_SPLIT;
				for (String id : selectedHandoveRecordIds) {
					String itemName = id.substring(0, id.indexOf(split));
					id = id.substring(id.indexOf(split) + split.length());
					if (IdsMap.get(itemName) == null) {
						List<String> currItemIds = new ArrayList<String>();
						currItemIds.add(id);
						IdsMap.put(itemName, currItemIds);
					} else {
						List<String> currItemIds = IdsMap.get(itemName);
						currItemIds.add(id);
						IdsMap.put(itemName, currItemIds);
					}
				}
				// 执行上下文
				handoverExecuteContext = new HandoverExecuteContext(from, to,
						IdsMap.get(item.getItem()), config, item, logId);
				try {
					// 执行工作交接
					item.getHandler().execute(handoverExecuteContext);
				} catch (Exception e) {
					logger.error("交接失败：", e);
					handoverExecuteContext.getHandoverExecuteResult()
							.setException(e);
					throw new RuntimeException(e);
				} finally {
					sysHandoverLogService
							.afterConfigHandover(handoverExecuteContext);
					// 记录结果
					executeResults.add(handoverExecuteContext
							.getHandoverExecuteResult());
				}

			}
		}

		// 结果
		return convertResultToJson(executeResults);
	}

	private JSONObject convertResultToJson(
			List<HandoverExecuteResult> executeResults) {
		JSONObject resultObect = new JSONObject();
		JSONObject infoObject = new JSONObject();
		JSONObject errObject = new JSONObject();
		for (HandoverExecuteResult handoverExecuteResult : executeResults) {
			Map<String, String> infoMap = handoverExecuteResult.getInfo();
			for (Map.Entry<String, String> entry : infoMap.entrySet()) {
				infoObject.put(entry.getKey(), entry.getValue());
			}
			Map<String, String> errMap = handoverExecuteResult.getError();
			for (Map.Entry<String, String> entry : errMap.entrySet()) {
				errObject.put(entry.getKey(), entry.getValue());
			}
		}
		resultObect.put("info", infoObject.size() == 0 ? "" : infoObject);
		resultObect.put("err", errObject.size() == 0 ? "" : errObject);
		return resultObect;
	}

	@Override
	public long docHandoverExecute(JSONObject moduleObject,
								   SysHandoverConfigMain handMain) throws Exception {
		return docHandoverExecute(moduleObject, handMain, null);
	}

	@Override
	public long docHandoverExecute(JSONObject moduleObject,
								   SysHandoverConfigMain handMain, String type) throws Exception {
		// 该module成功交接记录数
		long total = 0L;
		SysOrgElement from = (SysOrgElement) sysOrgElementService
				.findByPrimaryKey(handMain.getFdFromId());
		SysOrgElement to = null;
		if (StringUtil.isNotNull(handMain.getFdToId())) {
			to = (SysOrgElement) sysOrgElementService.findByPrimaryKey(handMain
					.getFdToId());
		}

		String module = moduleObject.getString("module");
		// 获取系统文档类配置
		HandoverConfig config = HandoverPluginUtils.getConfigHandoverByModule(
				module, type);

		JSONArray items = (JSONArray) moduleObject.get("items");
		for (int i = 0; i < items.size(); i++) {
			JSONObject itemObject = (JSONObject) items.get(i);
			String itemName = itemObject.getString("item");

			HandoverItem item = getItem(config.getProvider(), itemName);
			// 新增日志，设置状态为：running count为：0L
			String logId = sysHandoverLogService.beforeConfigHandover(config,
					item, from, to, handMain.getFdId());
			/* 执行交接 */
			Object ids = itemObject.get("ids");
			/* 执行交接流程 */
			Object processIds = itemObject.get("processIds");
			/* 执行交接节点编号 */
			Object factIds = itemObject.get("factIds");
			// 执行上下文
			HandoverExecuteContext handoverExecuteContext = new HandoverExecuteContext(
					from, to, ids == null ? null : Arrays.asList(ids.toString()
							.split(",")), config, item, logId);
			handoverExecuteContext
					.setSelectedProcessIds(processIds == null ? null : Arrays
							.asList(processIds.toString().split(",")));
			handoverExecuteContext.setSelectedFactIds(factIds == null ? null
					: Arrays.asList(factIds.toString().split(",")));
			if ("item".equals(type)) {
				handoverExecuteContext.setExecMode(handMain.getExecMode());
			}
			try {
				// 执行工作交接
				item.getHandler().execute(handoverExecuteContext);

				// 执行的数量 = 成功执行数+ 忽略执行数
				total += handoverExecuteContext.getSuccTotal();
				total += handoverExecuteContext.getIgnoreTotal();
			} catch (Exception e) {
				logger.error("交接失败：", e);
				handoverExecuteContext.getHandoverExecuteResult().setException(
						e);
				// throw new Exception(e);
			} finally {
				sysHandoverLogService
						.afterConfigHandover(handoverExecuteContext);
			}
		}
		return total;
	}

	/**
	 * 获取provider中指定name的item
	 * 
	 * @param provider
	 * @param itemName
	 * @return
	 * @throws Exception
	 */
	private HandoverItem getItem(IHandoverProvider provider, String itemName)
			throws Exception {
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

	@Override
	public Page detail(HQLInfo hqlInfo, SysOrgElement org, String module,
			String item, RequestContext context) throws Exception {
		String type = context.getParameter("type");
		HandoverConfig config = HandoverPluginUtils.getConfigHandoverByModule(module, type);
		IHandoverProvider provider = getHandoverProvider(config);
		List<HandoverItem> items = provider.items();
		Page page = null;
		for (HandoverItem hItem : items) {
			if (hItem.getItem().equals(item)) {
				Method detailMethod = hItem.getHandler().getClass().getMethod(
				"detail", HQLInfo.class, SysOrgElement.class,String.class, String.class, RequestContext.class);
				page = (Page) detailMethod.invoke(hItem.getHandler(), hqlInfo,org, module, item, context);
			}
		}
		return page;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String keyword = requestInfo.getParameter("fdName");
		if (StringUtil.isNull(keyword)) {
			keyword = requestInfo.getParameter("keyword");
		}
		String orgType = requestInfo.getParameter("orgType");
		List<Integer> orgTypes = new ArrayList<Integer>();
		orgTypes.add(SysOrgConstant.ORG_TYPE_POST);
		orgTypes.add(SysOrgConstant.ORG_TYPE_PERSON);
		if (StringUtil.isNotNull(orgType) && orgType.contains("ORG_TYPE_DEPT")) {
			orgTypes.add(SysOrgConstant.ORG_TYPE_DEPT);
		}
		HQLInfo hqlInfo = new HQLInfo();
		// 最多只能查询50条记录
		hqlInfo.setRowSize(50);
		hqlInfo.setWhereBlock("sysOrgElement.fdIsAvailable = :fdIsAvailable and sysOrgElement.fdOrgType in (:orgTypes)");
		// 查询无效组织
		hqlInfo.setParameter("fdIsAvailable", Boolean.FALSE);
		hqlInfo.setParameter("orgTypes", orgTypes);
		if (StringUtil.isNotNull(keyword)) {
			hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
					+ " and (sysOrgElement.fdLoginName like :keyword or sysOrgElement.fdMobileNo like :keyword or sysOrgElement.fdEmail like :keyword or sysOrgElement.fdNo like :keyword or sysOrgElement.fdName like :keyword)");
			hqlInfo.setParameter("keyword", "%" + keyword + "%");
		}
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		List<SysOrgElement> list = sysOrgElementService.findPage(hqlInfo).getList();
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (SysOrgElement elem : list) {
			data.add(OrgDialogUtil.getResultEntry(elem, requestInfo.getContextPath()));
		}
		return data;
	}

}
