package com.landray.kmss.common.module.core.register.loader;

import com.landray.kmss.common.module.core.exception.ModuleLoaderException;
import com.landray.kmss.common.module.core.register.loader.model.Declare;
import com.landray.kmss.common.module.core.register.loader.model.Depend;
import com.landray.kmss.common.module.core.register.loader.model.MechanismModelConfig;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author 严明镜
 * @version 1.0 2021年03月19日
 */
public class ModuleDictFactory {

	private static final Logger log = LoggerFactory.getLogger(ModuleDictFactory.class);

	private static final String DECLARE_POINT = "com.landray.kmss.common.module.core.register.declare";
	private static final String DECLARE_ITEM = "declare";
	private static final String DECLARE_ID = "id";
	private static final String DECLARE_MESSAGEKEY = "messageKey";
	private static final String DECLARE_DECLARE_MECHANISM_MODELS = "mechanismModels";
	private static final String DEPEND_POINT = "com.landray.kmss.common.module.core.register.depend";
	private static final String DEPEND_ITEM = "depend";
	private static final String DEPEND_ID = "id";
	private static final String DEPEND_MESSAGEKEY = "messageKey";
	private static final String DEPEND_DEPEND_MODULES = "dependModules";
	private static final String DEPEND_DEPEND_MODELS = "dependModels";

	/**
	 * 注册的模块
	 * key为模块id
	 */
	private static final Map<String, Declare> declares = new HashMap<>();

	protected static Map<String, Declare> getDeclares() {
		return declares;
	}

	/**
	 * 模块的依赖声明
	 * key为模块id
	 */
	private static final Map<String, Depend> depends = new HashMap<>();

	protected static Map<String, Depend> getDepends() {
		return depends;
	}

	/**
	 * 所有机制接口对应的机制Model配置
	 * key=机制接口 value=机制配置信息
	 */
	private static final Map<String, MechanismModelConfig> indexMechanismConfigByInterface = new HashMap<>();

	protected static Map<String, MechanismModelConfig> getIndexMechanismConfigByInterface() {
		return indexMechanismConfigByInterface;
	}

	/**
	 * 所有机制Key对应的机制Model配置
	 * key=机制Key value=机制配置信息
	 */
	private static final Map<String, MechanismModelConfig> indexMechanismConfigByKey = new HashMap<>();

	protected static Map<String, MechanismModelConfig> getIndexMechanismConfigByKey() {
		return indexMechanismConfigByKey;
	}

	/**
	 * 所有模块的依赖声明中的Model所实现的机制列表，用于快速判断某个Model是否实现某机制
	 * key=模块Model简单类名 value=机制接口列表
	 */
	private static final Map<String, List<String>> indexModelDepends = new HashMap<>();

	protected static Map<String, List<String>> getIndexModelDepends() {
		return indexModelDepends;
	}

	/**
	 * 初始化
	 */
	public static void init() {
		declares.clear();
		initDeclare();
		log.info("注册的模块:" + declares.size());
		initDepend();
		log.info("依赖模块关系:" + depends.size());
	}

	/**
	 * 初始化模块及机制声明
	 */
	private static void initDeclare() {
		IExtension[] declareEtensions = Plugin.getExtensions(DECLARE_POINT, "*", DECLARE_ITEM);
		if (declareEtensions == null || declareEtensions.length < 1) {
			log.warn("未找到任何模块注册声明！");
			return;
		}
		for (IExtension extension : declareEtensions) {
			Declare declare = new Declare();
			//模块信息
			declare.setId(Plugin.getParamValueString(extension, DECLARE_ID));
			declare.setMessageKey(Plugin.getParamValueString(extension, DECLARE_MESSAGEKEY));
			//机制信息
			Collection<String> mechanismModels = Plugin.getParamValues(extension, DECLARE_DECLARE_MECHANISM_MODELS);
			if(mechanismModels != null && mechanismModels.size() > 0) {
				for (String mechanismModel : mechanismModels) {
					MechanismModelConfig mechanism = splitMechanismModelConfig(mechanismModel);
					if (mechanism == null) {
						continue;
					}
					mechanism.setModuleId(declare.getId());
					//机制接口重名
					if (indexMechanismConfigByInterface.containsKey(mechanism.getInterfaceName())) {
						log.error("已忽略机制", new ModuleLoaderException("发现重名的机制接口定义 '" + mechanismModel + "' " + declare + " 与 " + declares.get(indexMechanismConfigByInterface.get(mechanism.getInterfaceName()).getModuleId())));
						continue;
					}
					//机制Key重名
					if (mechanism.getMechanismKey() != null && indexMechanismConfigByKey.containsKey(mechanism.getMechanismKey())) {
						log.error("已忽略机制", new ModuleLoaderException("发现重名的机制Key定义 '" + mechanismModel + "' " + declare + " 与 " + declares.get(indexMechanismConfigByKey.get(mechanism.getMechanismKey()).getModuleId())));
						continue;
					}
					declare.getMechanismModels().put(mechanism.getInterfaceName(), mechanism);
					//机制信息索引
					indexMechanismConfigByInterface.put(mechanism.getInterfaceName(), mechanism);
					if (mechanism.getMechanismKey() != null) {
						indexMechanismConfigByKey.put(mechanism.getMechanismKey(), mechanism);
					}
				}
			}
			if (declares.containsKey(declare.getId())) {
				ModuleLoaderException terminateException = new ModuleLoaderException("发现重名的模块 " + declare + " 与 " + declares.get(declare.getId()));
				log.error("系统已终止启动", terminateException);
				throw terminateException;
			}
			declares.put(declare.getId(), declare);
		}
	}

	/**
	 * 从配置中解析机制Model信息
	 *
	 * @param mechanismModel 机制Model配置
	 * @return MechanismModelInfo
	 */
	private static MechanismModelConfig splitMechanismModelConfig(String mechanismModel) {
		String[] split = mechanismModel.split(":");
		if (split.length <= 0) {
			log.error("mechanismModels的格式定义错误：" + mechanismModel + "，正确格式：'机制接口1:机制Key1:机制Model全类名1;机制接口2:机制Key2:机制Model全类名2...'");
			return null;
		}
		MechanismModelConfig mechanism = new MechanismModelConfig();
		mechanism.setInterfaceName(split[0]);
		if (split.length >= 2) {
			mechanism.setMechanismKey(split[1]);
		}
		if (split.length >= 3) {
			mechanism.setModelClassName(split[2]);
		}
		return mechanism;
	}

	/**
	 * 初始化模块依赖声明
	 */
	private static void initDepend() {
		IExtension[] dependExtensions = Plugin.getExtensions(DEPEND_POINT, "*", DEPEND_ITEM);
		if (dependExtensions == null || dependExtensions.length < 1) {
			log.warn("未找到任何模块依赖声明！");
			return;
		}
		for (IExtension extension : dependExtensions) {
			Depend depend = new Depend();
			//模块信息
			depend.setId(Plugin.getParamValueString(extension, DEPEND_ID));
			depend.setMessageKey(Plugin.getParamValueString(extension, DEPEND_MESSAGEKEY));
			//依赖模块
			Collection<String> moduleDepends = Plugin.getParamValues(extension, DEPEND_DEPEND_MODULES);
			depend.setDependModules(new ArrayList<>(moduleDepends));
			//依赖Model
			Map<String, List<String>> dependModels = new HashMap<>();
			Collection<String> modelDepends = Plugin.getParamValues(extension, DEPEND_DEPEND_MODELS);
			for (String modelDepend : modelDepends) {
				String[] split = modelDepend.split(":");
				if (split.length != 2) {
					log.error("models格式出错:" + modelDepend + "，已忽略该依赖关系！");
					continue;
				}
				String mainModel = split[0];
				String mechanismModel = split[1];
				putMapList(dependModels, mainModel, mechanismModel);
			}
			depend.setDependModels(dependModels);
			depends.put(depend.getId(), depend);
			//Model依赖索引
			indexModelDepends.putAll(dependModels);
		}
	}

	/**
	 * 往map的list值中存，list中已存在时不add并打印警告
	 *
	 * @param map       操作的map
	 * @param mapKey    map的Key
	 * @param listValue list中的值
	 */
	private static void putMapList(Map<String, List<String>> map, String mapKey, String listValue) {
		if (map.containsKey(mapKey)) {
			List<String> mechanismList = map.get(mapKey);
			if (mechanismList == null) {
				mechanismList = new ArrayList<>();
				mechanismList.add(listValue);
				map.put(mapKey, mechanismList);
			}
			if (!mechanismList.contains(listValue)) {
				mechanismList.add(listValue);
			} else {
				log.warn("Model依赖出现重复配置项 " + mapKey + ":" + listValue);
			}
		} else {
			List<String> mechanismList = new ArrayList<>();
			mechanismList.add(listValue);
			map.put(mapKey, mechanismList);
		}
	}

	/**
	 * 模块依赖性、机制依赖性检查
	 */
	public static void check() {
		if (depends.isEmpty()) {
			log.warn("未找到任何模块依赖关系，已跳过模块字典的检测！");
			return;
		}
		for (Depend depend : depends.values()) {
			//检查依赖模块
			if (log.isDebugEnabled()) {
				log.debug(depend + " 依赖模块： " + depend.getDependModules());
			}
			for (String module : depend.getDependModules()) {
				if (!declares.containsKey(module)) {
					log.warn("未找到" + depend + " 所依赖的 " + module + " 模块的注册配置");
				}
			}
			for (Map.Entry<String, List<String>> entry : depend.getDependModels().entrySet()) {
				String modelName = entry.getKey();
				List<String> mechanismInterfaceList = entry.getValue();
				if (log.isDebugEnabled()) {
					log.debug(modelName + "依赖机制Model： " + mechanismInterfaceList);
				}
				for (String mechanismInterface : mechanismInterfaceList) {
					if (!indexMechanismConfigByInterface.containsKey(mechanismInterface)) {
						log.warn("未找到" + depend + "的 '" + modelName + "' 所依赖的 '" + mechanismInterface + "' 机制的注册配置");
					}
				}
			}
		}
	}

}
