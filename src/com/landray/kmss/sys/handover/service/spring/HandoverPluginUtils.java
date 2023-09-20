package com.landray.kmss.sys.handover.service.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.config.design.SysCfgFlowDef;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverProvider;
import com.landray.kmss.sys.handover.support.config.doc.DocProvider;
import com.landray.kmss.util.KmssPinyinUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.comparator.ChinesePinyinComparator;

/**
 * 工作交接扩展点的工具类
 * 
 * @author 缪贵荣
 * @see
 */
public abstract class HandoverPluginUtils {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(HandoverPluginUtils.class);
	private static final String HANDLER_CONFIG_POINT = "com.landray.kmss.sys.handover";
	private static final String HANDLER_DOC_AUTH_POINT = "com.landray.kmss.sys.handover.doc.auth";
	private static final String HANDLER_ITEM_POINT = "com.landray.kmss.sys.handover.item";
	private static final String HANDLER_CONFIG_ITEM = "config";
	private static List<Map<String, String>> handlerConfigList = null;
	private static List<Map<String, String>> handlerDocList = null;
	private static List<Map<String, String>> handlerDocAuthList = null;
	private static List<Map<String, String>> handlerItemList = null;
	
	private static Map<String, String> authModelMap = null;
	private static Map<String, String> authSubSqlMap = null;
	
	public static Map<String, String> getAuthModel() {
		if (authModelMap == null) {
			authModelMap = new HashMap<String, String>();
			authSubSqlMap = new HashMap<String, String>();
			IExtension[] extensions = HandoverPluginUtils.getHandlerExtensions("auth");
			for (IExtension extension : extensions) {
				String module = Plugin.getParamValue(extension, "module");
				String messageKey = Plugin.getParamValue(extension, "messageKey");
				authModelMap.put(module, messageKey);
				String subPrimaryKey = Plugin.getParamValue(extension, "subSql");
				if (StringUtil.isNotNull(subPrimaryKey)) {
					authSubSqlMap.put(module, subPrimaryKey);
				}
			}
		}
		return authModelMap;
	}
	
	public static String getAuthSubSql(String modelName) {
		if (authSubSqlMap == null) {
            getAuthModel();
        }
		return authSubSqlMap.get(modelName);
	}

	/**
	 * 获取配置类工作交接类型
	 * 
	 * @return
	 */
	public static List<Map<String, String>> getConfigHandoverTypes(String type) {
		if ("auth".equals(type)) {
			if (handlerDocAuthList == null) {
				synchronized (HandoverPluginUtils.class) {
					if (handlerDocAuthList == null) {
						handlerDocAuthList = new ArrayList<Map<String, String>>();
						getConfigHandoverByType(handlerDocAuthList, type);
					}
				}
			}
			return handlerDocAuthList;
		} else if ("item".equals(type)) {
			if (handlerItemList == null) {
				synchronized (HandoverPluginUtils.class) {
					if (handlerItemList == null) {
						handlerItemList = new ArrayList<Map<String, String>>();
						getConfigHandoverByType(handlerItemList, type);
					}
				}
			}
			return handlerItemList;
		} else {
			if (handlerConfigList == null) {
				synchronized (HandoverPluginUtils.class) {
					if (handlerConfigList == null) {
						handlerConfigList = new ArrayList<Map<String, String>>();
						getConfigHandoverByType(handlerConfigList, type);
					}
				}
			}
			return handlerConfigList;
		}
	}
	
	private static void getConfigHandoverByType(List<Map<String, String>> list, String type) {
		// 系统配置
		Map<String, String[]> sysConfigMap = new HashMap<String, String[]>();
		// 模块配置
		Map<String, String[]> moduleConfigMap = new HashMap<String, String[]>();

		IExtension[] extensions = getHandlerExtensions(type);
		for (IExtension extenstion : extensions) {
			String module = Plugin.getParamValue(extenstion,
					"module");
			String messageKey = ResourceUtil.getString(Plugin
					.getParamValue(extenstion, "messageKey")
					.toString());
			if (StringUtil.isNull(messageKey)) {
				logger.warn("获取模块时:" + module + "的模块名获取为空");
			}
			String order = "";
			Object _order = Plugin.getParamValue(extenstion, "order");
			if (_order != null) {
				order = _order.toString();
			}
			if (module.indexOf("com.landray.kmss") > -1) {
				moduleConfigMap.put(module, new String[] { order,
						messageKey });
			} else {
				sysConfigMap.put(module, new String[] { order,
						messageKey });
			}
		}
		// 排序
		List<Map.Entry<String, String[]>> sysConfigList = new ArrayList<Map.Entry<String, String[]>>(
				sysConfigMap.entrySet());
		List<Map.Entry<String, String[]>> moduleConfigList = new ArrayList<Map.Entry<String, String[]>>(
				moduleConfigMap.entrySet());

		Collections.sort(sysConfigList,
				new Comparator<Map.Entry<String, String[]>>() {
					@Override
					public int compare(
							Map.Entry<String, String[]> mapping1,
							Map.Entry<String, String[]> mapping2) {
						String value1 = mapping1.getValue()[0];
						String value2 = mapping2.getValue()[0];
						return Integer.parseInt(value1)
								- Integer.parseInt(value2);
					}
				});

		Collections.sort(moduleConfigList,
				new Comparator<Map.Entry<String, String[]>>() {
					@Override
					public int compare(
							Map.Entry<String, String[]> mapping1,
							Map.Entry<String, String[]> mapping2) {
						String value1 = mapping1.getValue()[1];
						String value2 = mapping2.getValue()[1];
						return getPinYinString(value1).compareTo(
								getPinYinString(value2));
					}
				});
		sysConfigList.addAll(moduleConfigList);
		for (Map.Entry<String, String[]> mapping : sysConfigList) {
			Map<String, String> sortedMap = new HashMap<String, String>();
			sortedMap.put(mapping.getKey(), mapping.getValue()[1]);
			list.add(sortedMap);
		}
	}
	
	/**
	 * 获取所有文档类交接模块<br>
	 * 1、先取系统中所有扩展了流程的模块<br>
	 * 2、合并各模块中注册的文档类交接模块(第二阶段文档权限交接使用)
	 * 
	 * @return 排序后的列表模块
	 */
	public static synchronized List<Map<String, String>> getDocHandoverModules() {
		if( handlerDocList == null){
			synchronized(HandoverPluginUtils.class){
				if(handlerDocList == null){
					handlerDocList = new ArrayList<Map<String,String>>();
					Map<String, String> moduleMap = new HashMap<String, String>();
					List<SysCfgFlowDef> sysCfgFlowDefs = SysConfigs.getInstance()
							.getAllFlowDef();
					for (SysCfgFlowDef sysCfgFlowDef : sysCfgFlowDefs) {
						// 模块信息
						if (moduleMap.get(sysCfgFlowDef.getModelName()) == null) {
							moduleMap.put(sysCfgFlowDef.getModelName(), ResourceUtil
									.getString(sysCfgFlowDef.getModuleMessageKey()));
						}
					}
					
					// 按拼音排序
					List<Map.Entry<String, String>> sortedList = new ArrayList<Map.Entry<String, String>>(
							moduleMap.entrySet());
					Collections.sort(sortedList,
							new Comparator<Map.Entry<String, String>>() {
								@Override
								public int compare(Map.Entry<String, String> o1,
												   Map.Entry<String, String> o2) {
									return ChinesePinyinComparator.compare(o1.getValue(),
											o2.getValue());
								}
							});
					
					
					for (Map.Entry<String, String> mapping : sortedList) {
						Map<String, String> sortedMap = new HashMap<String, String>();
						sortedMap.put(mapping.getKey(), mapping.getValue());
						handlerDocList.add(sortedMap);
					}
					
				}
			}
		}
		return handlerDocList;
	}

	/**
	 * 获取拼音值
	 * 
	 * @param str
	 * @return
	 */
	private static String getPinYinString(String str) {
		if(StringUtil.isNull(str)){
			return "";
		}
		return KmssPinyinUtil.toPinyin(str, "",true);
	}  
	/**
	 * 
	 * 获取指定handler配置信息
	 * 
	 * @param module
	 *            handler 类型
	 * @return
	 * 
	 */
	public static HandoverConfig getConfigHandoverByModule(String module, String type)
			throws Exception {
		IExtension[] extensions = getHandlerExtensions(type);
		IExtension extension = Plugin.getExtension(extensions, "module", module);
		
		//1、没有注册，则默默认DOC处理人类交接
		//2、有注册，则直接从注册中获取
		if(extension == null){
			// 如果是事项交接，业务模块必须要实现交接逻辑
			if ("item".equals(type)) {
				throw new Exception("事项交接必须实现交接逻辑！");
			}
			return getDocDefaultConfigHanoverByModule(module);
		}
		
		String messageKey = Plugin.getParamValue(extension, "messageKey");
		IHandoverHandler handler = Plugin.getParamValue(extension, "handler");
		IHandoverProvider provider = Plugin.getParamValue(extension, "provider");
		int order = Plugin.getParamValue(extension, "order");

		HandoverConfig handoverConfig = new HandoverConfig();
		handoverConfig.setModule(module);
		handoverConfig.setMessageKey(ResourceUtil.getString(messageKey));
		handoverConfig.setHandler(handler);
		handoverConfig.setProvider(provider);
		handoverConfig.setOrder(order);
		return handoverConfig;
	}
	
	private static HandoverConfig getDocDefaultConfigHanoverByModule(
			String module) throws Exception {
		// 取默认provider
		String messageKey = "";
		SysDictModel sysDictModel = SysDataDict.getInstance().getModel(
				module);
		if(sysDictModel != null){
			messageKey = sysDictModel.getMessageKey();
		}else{
			logger.debug("无法根据"+module+"获取数据字典。请配置数据字典");
		}
		HandoverConfig handoverConfig = new HandoverConfig();
		handoverConfig.setModule(module);
		handoverConfig.setMessageKey(ResourceUtil.getString(messageKey));
		handoverConfig.setProvider(new DocProvider());
		return handoverConfig;
	}

	/**
	 * 
	 * 获取系统中所有配置类handler
	 * 
	 * @return
	 * 
	 */
	public static IExtension[] getHandlerExtensions(String type) {
		if ("auth".equals(type)) {
			return Plugin.getExtensions(HANDLER_DOC_AUTH_POINT, "*", HANDLER_CONFIG_ITEM);
		} else if ("item".equals(type)) {
			return Plugin.getExtensions(HANDLER_ITEM_POINT, "*", HANDLER_CONFIG_ITEM);
		} else {
			return Plugin.getExtensions(HANDLER_CONFIG_POINT, "*", HANDLER_CONFIG_ITEM);
		}
	}

	public static class HandoverConfig {

		private String module;

		public String getModule() {
			return module;
		}

		public void setModule(String module) {
			this.module = module;
		}

		private String messageKey;

		public String getMessageKey() {
			return messageKey;
		}

		public void setMessageKey(String messageKey) {
			this.messageKey = messageKey;
		}

		private IHandoverHandler handler;

		public IHandoverHandler getHandler() {
			return handler;
		}

		public void setHandler(IHandoverHandler handler) {
			this.handler = handler;
		}

		private IHandoverProvider provider;

		public IHandoverProvider getProvider() {
			return provider;
		}

		public void setProvider(IHandoverProvider provider) {
			this.provider = provider;
		}

		private int order;

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}
	}
}
