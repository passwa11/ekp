package com.landray.kmss.sys.handover.support.config.lbpmtemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.handover.support.util.ListSplitUtil;
import com.landray.kmss.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.design.SysCfgFlowDef;
import com.landray.kmss.sys.config.design.SysCfgModule;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverRecord;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLogDetail;
import com.landray.kmss.sys.handover.support.util.HandModelUtil;
import com.landray.kmss.sys.lbpm.engine.persistence.AccessManager;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmNodeDefinition;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmNodeDefinitionHandler;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcessDefinition;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmTemplate;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmTemplateService;
import com.landray.kmss.sys.organization.model.SysOrgElement;

public abstract class AbstractLbpmTemplateHandler implements IHandoverHandler {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractLbpmTemplateHandler.class);
	private static final String MESS_NODE = ResourceUtil.getString("sysHandoverConfigMain.node", "sys-handover");
	/*
	 * 特权人
	 */
	final static String ATT_PRIVILEGERIDS = "privilegerIds";
	/*
	 * 处理人
	 */
	final static String ATT_HANDLERIDS = "handlerIds";
	/*
	 * 意见可阅读者
	 */
	final static String ATT_O_CANVIEWCURNODEIDS = "otherCanViewCurNodeIds";
	/*
	 * 备选处理人
	 */
	final static String ATT_OPTHANDLERIdS = "optHandlerIds";
	/*
	 * 数据访问
	 */
	protected AccessManager accessManager;
	/*
	 * 流程模板service
	 */
	protected ILbpmTemplateService lbpmTemplateService;

	@Override
	public synchronized void execute(HandoverExecuteContext context) throws Exception {
		SysOrgElement fromOrg = context.getFrom();
		SysOrgElement toOrg = context.getTo();
		long successCount = 0L;
		List<String> ids = context.getSelectedRecordIds();
		if (ids == null) {
			context.setSuccTotal(0L);
			return;
		}
		for (int i = 0; i < ids.size(); i++) {
			String id = ids.get(i);
			LbpmNodeDefinitionHandler handler = getAccessManager().get(LbpmNodeDefinitionHandler.class, id);
			// 无交接人
			if (!handler.getFdHandler().getFdId().equals(fromOrg.getFdId())) {
				String fdId = context.getModule() + ID_SPLIT + context.getItem() + ID_SPLIT + id;
				addOrgErr(fdId, context);
				continue;
			}

			// 当交接类型为“处理人”和“特权人”时，接收人不能是部门
			if (toOrg != null && toOrg.getFdOrgType() == 2
					&& (ATT_HANDLERIDS.equals(handler.getFdAttribute())
							|| ATT_PRIVILEGERIDS.equals(handler.getFdAttribute()))) {
				String msg = ResourceUtil.getString(
						"sysHandoverConfigMain.skip", "sys-handover", null,
						new Object[] { ResourceUtil.getString("sys-handover-support-config-lbpmtemplate:sysHandoverConfigHandler." + handler.getFdAttribute()),
								fromOrg.getFdName(), toOrg.getFdName() });
				context.info(context.getModule() + ID_SPLIT
						+ handler.getFdAttribute() + ID_SPLIT + id, msg);
				continue;
			}

			// 去重处理
			if (checkIsRepeat(handler, toOrg)) {
				getAccessManager().delete(handler);
			} else {
				handler.setFdHandler(toOrg);
				getAccessManager().update(handler);
			}

			LbpmProcessDefinition processDefinition = handler.getFdProcess();
			String currNodeName = "";
			List<LbpmNodeDefinition> nodes = processDefinition.getNodeDefinitions();
			for (LbpmNodeDefinition node : nodes) {
				// 交接人所在节点名称
				if (node.getFdFactId().equals(handler.getFdFactId())) {
					currNodeName = node.getFdFactName();
					break;
				}
			}
			LbpmTemplate lbpmTemplate = (LbpmTemplate) getLbpmTemplateService().findByPrimaryKey(processDefinition.getFdTemplateId(), null, true);

			String modelName = lbpmTemplate.getFdModelName();
			String desc = getLbpmTemplateDesc(lbpmTemplate);
			desc += StringUtil.isNotNull(currNodeName) ? CONN_SYM + MESS_NODE + currNodeName : "";
			context.log(handler.getFdProcess().getFdTemplateId(), modelName, desc, getLbpmTemplateUrl(lbpmTemplate), currNodeName, SysHandoverConfigLogDetail.STATE_SUCC);
			successCount++;
		}
		context.setSuccTotal(successCount);
	}

	@Override
	public void search(HandoverSearchContext context) throws Exception {
		String hql = "select handler from LbpmNodeDefinitionHandler handler " 
				+ "where handler.fdAttribute = ? and handler.fdHandler.fdId = ?"
				+ " and handler.fdProcess.fdIsCurrentVersion = true";

		// 需要丢弃的流程定义
		List<String> discard = new ArrayList<String>();
		// 需要追加的流程定义
		List<String> append = new ArrayList<String>();

		/**
		 * 这里的逻辑需要解释一下，流程节点处理人的交接是查询所有处理人的表来获取数据
		 * 工作交接是查LbpmNodeDefinitionHandler（结合LbpmProcessDefinition）表中的所有处理人数据，正常情况下是没有问题的，
		 * 但是发现有一种情况，流程模板由“自定义”改为“引用默认模板”时，LbpmProcessDefinition表中的数据没有变化，有一个很重要的字段“fdIsCurrentVersion”还是指向了上一次自定义的模板。
		 * 但是在LbpmTemplate表中的fdCommon字段引用了通用模板。 在这种情况下，工作交接原来的逻辑就不支持了。
		 * 现在的逻辑改为如下：
		 * 先按以前的查询获取所有可交接的处理人，在处理时需要判断一下LbpmTemplate是否有引用通用流程，如有，将丢弃刚才查询到的同一个流程定义的数据，并保存通用流程模板的ID。
		 * 上一步处理完后，需要再处理通用流程模板的问题，处理逻辑是传入刚才保存的通用流程模板ID，获取新的流程定义数据
		 */

		// 1. 查询所有节点处理人，并处理数据（有些数据可能要被丢弃）
		List<LbpmNodeDefinitionHandler> handlerList = getAccessManager().find(hql, getFdAttribute(), context.getHandoverOrg().getFdId());
		findHandlers(context, handlerList, discard, append, true);
		
		// 2. 在上一步的查询中，可能有些数据要丢弃，有些数据要重新追加，所以需要重新再处理一次
		if (!append.isEmpty()) {
			hql = "select handler from LbpmNodeDefinitionHandler handler"
					+ " where handler.fdAttribute = :fdAttribute and handler.fdHandler.fdId = :fdHandlerId";
			Map<String, Object> params = new HashMap<String, Object>();
			StringBuffer _hql = new StringBuffer(hql);
			//list 大于1000时要进行拆分 #110385
			if(append.size() >= 1000) {
				List<List<String>> newList = ListSplitUtil.splitList(append, ListSplitUtil.MAX_LENGTH);
				for(int i = 0;i < newList.size();i++) {
					if(i == 0) {
						_hql.append(" and (handler.fdProcess.fdId in (:fdProcessIds").append("_").append(i).append(")");
					} else {
						_hql.append(" or handler.fdProcess.fdId in (:fdProcessIds").append("_").append(i).append(")");
					}

					String param = "defineIds_"+i;
					params.put(param, newList.get(i));
				}
				_hql.append(") ");
			}else {
				_hql.append(" and handler.fdProcess.fdId in (:fdProcessIds)");
				params.put("fdProcessIds", append);
			}
			params.put("fdAttribute", getFdAttribute());
			params.put("fdHandlerId", context.getHandoverOrg().getFdId());
			handlerList = getAccessManager().find(_hql.toString(), params);
			findHandlers(context, handlerList, discard, append, false);
		}

		// 结果排序处理
		Collections.sort(context.getHandoverSearchResult().getHandoverRecords(), new Comparator<HandoverRecord>() {
			@Override
			public int compare(HandoverRecord o1, HandoverRecord o2) {
				String[] s1 = o1.getDatas();
				String[] s2 = o2.getDatas();

				String str1 = s1 == null ? "" : (s1[0] == null ? "" : s1[0]);
				String str2 = s2 == null ? "" : (s2[0] == null ? "" : s2[0]);

				return str1.compareTo(str2);
			}
		});
		
	}
	
	private void findHandlers(HandoverSearchContext context, List<LbpmNodeDefinitionHandler> handlerList,
			List<String> discard, List<String> append, boolean isDiscard) throws Exception {
		for (int i = 0; i < handlerList.size(); i++) {
			LbpmNodeDefinitionHandler defiHandler = handlerList.get(i);
			LbpmProcessDefinition processDefinition = defiHandler.getFdProcess();
			
			if(isDiscard && discard.contains(processDefinition.getFdId())) {
				// 丢弃
				continue;
			}

			// 模板
			String templateId = processDefinition.getFdTemplateId();
			LbpmTemplate lbpmTemplate = (LbpmTemplate) getLbpmTemplateService().findByPrimaryKey(templateId, null, true);
			if (lbpmTemplate == null) {
				continue;
			}
			if(isDiscard) {
				// 判断是否引用通用流程模板
				LbpmTemplate common = lbpmTemplate.getFdCommon();
				if (common != null) {
					discard.add(processDefinition.getFdId());
					// 如果是引用了通用流程模块，就要使用通用流程模板的定义
					String _hql = "select pd from com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcessDefinition pd"
							+ " where pd.fdIsCurrentVersion = true and pd.fdTemplateId = ?";
					List<LbpmProcessDefinition> list = getAccessManager().find(_hql, common.getFdId());
					if (list != null && !list.isEmpty()) {
						append.add(list.get(0).getFdId());
						continue;
					}
				}
			}
			// 如果是流程管理模板，还需要判断模板是否已关闭
			if ("com.landray.kmss.km.review.model.KmReviewTemplate".equals(lbpmTemplate.getFdModelName())) {
				IBaseModel model = getBaseDao().findByPrimaryKey(lbpmTemplate.getFdModelId(), lbpmTemplate.getFdModelName(), true);
				if (model != null) {
					String value = BeanUtils.getProperty(model, "fdIsAvailable"); // 获取模板状态
					if ("false".equals(value)) { // 如果模板已关闭，则不进行交接
						continue;
					}
				}
			}
			
			// 节点
			String currNodeName = "";
			List<LbpmNodeDefinition> nodes = processDefinition.getNodeDefinitions();
			for (LbpmNodeDefinition node : nodes) {
				if (node.getFdFactId().equals(defiHandler.getFdFactId())) {
					currNodeName = node.getFdFactName() + "(" + node.getFdFactId() + ")";
					break;
				}
			}
			try {
				String desc = getLbpmTemplateDesc(lbpmTemplate);
				desc += StringUtil.isNotNull(currNodeName) ? CONN_SYM + MESS_NODE + currNodeName : "";
				context.addHandoverRecord(context.getModule() + ID_SPLIT + context.getItem() + ID_SPLIT + defiHandler.getFdId(),
						getLbpmTemplateUrl(lbpmTemplate), new String[] { desc });
			} catch (Exception e) {
				// 如果模块不存在则不查询
				logger.debug("无法找到" + lbpmTemplate.getFdModelId() + "对应的实例," + "可能是" + lbpmTemplate.getFdModelName() + "模块已经被移除");
			}

		}
	}

	/**
	 * 获取lbpmTemplate链接
	 * 
	 * @param lbpmTemplate
	 * @return
	 * @throws Exception
	 */
	private String getLbpmTemplateUrl(LbpmTemplate lbpmTemplate) throws Exception {
		SysDictModel model = null;
		model = StringUtil.isNotNull(lbpmTemplate.getFdModelName()) ? SysDataDict.getInstance().getModel(lbpmTemplate.getFdModelName()) : null;

		String url = "";
		if (lbpmTemplate.getFdIsCommon()) {
			url = "/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=view&fdModelName=" + lbpmTemplate.getFdModelName() + "&fdId=" + lbpmTemplate.getFdId();
		} else if (model != null) {
			// 有些模块的URL需要后期处理（如督办管理），这里将通过发布事件的方式都获取业务自己的URL
			BaseModel eventModel = (BaseModel) ClassUtils.forName(lbpmTemplate.getFdModelName()).newInstance();
			eventModel.setFdId(lbpmTemplate.getFdModelId());
			Map params = new HashMap();
			params.put("type", "getUrl");
			params.put("modelName", lbpmTemplate.getFdModelName());
			params.put("modelId", lbpmTemplate.getFdModelId());
			SpringBeanUtil.getApplicationContext().publishEvent(new Event_Common(eventModel, params));
			if (params.containsKey("url")) {
				url = (String) params.get("url");
			}
			if (StringUtil.isNull(url)) {
				url = model.getUrl();
				// 数据字典中未配置url
				if (url == null) {
					return "";
				}
				url = url.replace("${fdId}", lbpmTemplate.getFdModelId());
			}
		}
		return url;
	}

	/**
	 * 获取处理描述
	 * 
	 * @param lbpmTemplate
	 * @return
	 */
	private String getLbpmTemplateDesc(LbpmTemplate lbpmTemplate) throws Exception {
		// 模块数据字典
		SysDictModel dicModel = null;
		dicModel = StringUtil.isNotNull(lbpmTemplate.getFdModelName()) ? SysDataDict.getInstance().getModel(lbpmTemplate.getFdModelName()) : null;
		String desc = "";
		// 模块名
		String fdModelName = getModuleName(lbpmTemplate.getFdModelName());
		// 模板名
		if (lbpmTemplate.getFdIsCommon()) {
			SysCfgFlowDef flowDef = SysConfigs.getInstance().getFlowDefByTemplate(lbpmTemplate.getFdModelName(), lbpmTemplate.getFdKey());
			SysDictModel dicMainModel = SysDataDict.getInstance().getModel(flowDef.getModelName());
			desc += ResourceUtil.getString("sysHandoverConfigHandler.commonTemplate", "sys-handover-support-config-lbpmtemplate")
				+ "(" + ResourceUtil.getString(dicMainModel.getMessageKey()) + ")：" + lbpmTemplate.getFdName();
		} else {
			IBaseService templateService = (IBaseService) SpringBeanUtil.getBean(dicModel.getServiceBean());
			IBaseModel mainModel = templateService.findByPrimaryKey(lbpmTemplate.getFdModelId());
			String docSubject = HandModelUtil.getDocSubject(dicModel, mainModel);
			String templateMessage = ResourceUtil.getString(dicModel.getMessageKey());

			// 获取不到标题，数据字典配置出错，或者文档不存在
			desc += StringUtil.isNotNull(docSubject) ? templateMessage + "：" + docSubject : templateMessage + "：error[" 
					+ ResourceUtil.getString("sysHandoverConfigHandler.subjectNull", "sys-handover-support-config-lbpmtemplate") + "]";
			// 全路径
			desc = HandModelUtil.getTemplateHierarchy(mainModel, dicModel) + desc;
		}
		if (StringUtil.isNotNull(fdModelName)) {
			desc = fdModelName + CONN_SYM + desc;
		}

		return desc;
	}

	private String getModuleName(String fdModelName) {
		if (StringUtil.isNull(fdModelName)) {
			return "";
		}
		fdModelName = fdModelName.substring("com.landray.kmss.".length());
		String[] paths = fdModelName.split("\\.");
		if (paths.length < 2) {
			return "";
		}
		String modulePath = "/" + paths[0] + "/" + paths[1] + "/";
		SysCfgModule module = new SysConfigs().getModule(modulePath);
		if (module == null && paths.length > 2) {
			modulePath = "/" + paths[0] + "/" + paths[1] + "/" + paths[2] + "/";
			module = new SysConfigs().getModule(modulePath);
		}
		if (module == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("模块路径：" + fdModelName + "找不到所属模块");
			}
			return "";
		}
		return ResourceUtil.getString(module.getMessageKey());
	}

	private boolean checkIsRepeat(LbpmNodeDefinitionHandler handler, SysOrgElement toPerson) {
		if (toPerson == null) {
			return true;
		}
		String hql = "select count(*) from LbpmNodeDefinitionHandler handler " 
			+ "where handler.fdHandler.fdId =? and handler.fdAttribute =? and handler.fdFactId =? and handler.fdProcess.fdId =?";
		int num = getAccessManager().findCount(hql, toPerson.getFdId(), handler.getFdAttribute(), handler.getFdFactId(), handler.getFdProcess().getFdId());
		if (num > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取查询属性
	 * 
	 * @return
	 */
	public abstract String getFdAttribute();

	public AccessManager getAccessManager() {
		if (accessManager == null) {
			accessManager = (AccessManager) SpringBeanUtil.getBean("accessManager");
		}
		return accessManager;
	}

	public ILbpmTemplateService getLbpmTemplateService() {
		if (lbpmTemplateService == null) {
			lbpmTemplateService = (ILbpmTemplateService) SpringBeanUtil.getBean("lbpmTemplateService");
		}
		return lbpmTemplateService;
	}

	/**
	 * 交接人已被更新err
	 * 
	 * @param id
	 * @param context
	 */
	private void addOrgErr(String id, HandoverExecuteContext context) {
		context.error(id, ResourceUtil.getString("sysHandoverConfigMain.error", "sys-handover"));
	}
	
	private IBaseDao baseDao = null;

	public IBaseDao getBaseDao() {
		if (baseDao == null) {
			baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		}
		return baseDao;
	}
}
