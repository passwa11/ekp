package com.landray.kmss.common.module.core.register.loader;

import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.module.core.register.loader.model.MechanismModelConfig;
import com.landray.kmss.common.module.core.util.RuntimeClassUtil;
import com.landray.kmss.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * 模块依赖关系工具
 *
 * @author 严明镜
 * @version 1.0 2021年02月24日
 */
public class ModuleDictUtil {

	private static final Logger log = LoggerFactory.getLogger(ModuleDictUtil.class);

	/**
	 * 主文档是否依赖于某个机制
	 *
	 * @param mainForm               主文档Form(用于获取对应Model)
	 * @param requiredInterfaceClass 依赖的接口
	 */
	public static boolean isRequired(IExtendForm mainForm, Class<?> requiredInterfaceClass) {
		return isRequired(mainForm, requiredInterfaceClass.getSimpleName());
	}

	public static boolean isRequired(IExtendForm mainForm, String interfaceName) {
		return isRequired(mainForm.getModelClass(), interfaceName);
	}

	/**
	 * 主文档是否依赖于某个模块(机制)
	 *
	 * @param mainModel              主文档Model
	 * @param requiredInterfaceClass 依赖的接口
	 */
	public static boolean isRequired(IBaseModel mainModel, Class<?> requiredInterfaceClass) {
		return isRequired(mainModel, requiredInterfaceClass.getSimpleName());
	}

	public static boolean isRequired(IBaseModel mainModel, String interfaceName) {
		return isRequired(mainModel.getClass(), interfaceName);
	}

	/**
	 * 主文档是否依赖于某个模块(机制)
	 *
	 * @param clz                    Class
	 * @param requiredInterfaceClass 依赖的接口
	 */
	public static boolean isRequired(Class<?> clz, Class<?> requiredInterfaceClass) {
		return isRequired(clz, requiredInterfaceClass.getSimpleName());
	}

	public static boolean isRequired(Class<?> mainModel, String interfaceName) {
		if (mainModel == null) {
			return false;
		}
		return isRequired(getFixModelName(mainModel.getSimpleName()), interfaceName);
	}

	/**
	 * 主文档所属模块是否依赖于某个机制
	 *
	 * @param modelSimpleName 主文档简单类名
	 * @param interfaceName   所依赖的机制接口简单类名
	 */
	public static boolean isRequired(String modelSimpleName, String interfaceName) {
		return ModuleDictFactory.getIndexModelDepends().containsKey(modelSimpleName)
				&& ModuleDictFactory.getIndexModelDepends().get(modelSimpleName).contains(interfaceName);
	}

	/**
	 * 获取Class的简单类名，并将第一位字母转为小写、过滤代理对象的名称
	 *
	 * @param className 类名
	 */
	public static String getFixModelName(String className) {
		if (className == null) {
			return null;
		}
		return filterProxyName(getSimpleName(className));
	}

	private static String getSimpleName(String className) {
		if (!className.contains(".")) {
			return className;
		}
		return className.substring(className.lastIndexOf(".") + 1);
	}

	private static String filterProxyName(String className) {
		if (!className.contains("$")) {
			return className;
		}
		return className.substring(0, className.indexOf("$"));
	}

	/**
	 * 获取对象所属的模块id
	 *
	 * @param mainModel 主文档
	 */
	public static String getModuleId(IBaseModel mainModel) {
		return getModuleId(ModelUtil.getModelClassName(mainModel));
	}

	/**
	 * 获取类所属的模块id
	 *
	 * @param clz 类
	 */
	public static String getModuleId(Class<?> clz) {
		return getModuleId(clz.getName());
	}

	private static String getModuleId(String modelName) {
		try {
			//取前两个
			String[] split = modelName.substring("com.landray.kmss.".length()).split("\\.");
			return split[0] + "/" + split[1];
		} catch (Exception e) {
			log.error("获取主文档所属的模块id", e);
		}
		return null;
	}

	/**
	 * Form.reset时触发
	 * 或在需要时初始化Form中的机制对象时主动调用
	 *
	 * @param mechanismMap 在form中的map
	 */
	public static void formReset(IExtendForm form, Map<String, Object> mechanismMap) {
		Assert.isTrue(form != null, "Form参数不应该为空！");
		if (log.isDebugEnabled()) {
			log.debug(form.getClass() + "开始初始化机制Form");
		}
		mechanismMap.clear();
		//获取Class的Model简单类名
		Class<?> modelClass = form.getModelClass();
		if (modelClass == null) {
			if (log.isDebugEnabled()) {
				log.debug("form中的ModelClass为空，初始化中止");
			}
			return;
		}
		String modelClassSimpleName = getFixModelName(modelClass.getSimpleName());
		if (!ModuleDictFactory.getIndexModelDepends().containsKey(modelClassSimpleName)) {
			if (log.isDebugEnabled()) {
				log.debug(modelClassSimpleName + "无对应依赖配置，初始化中止");
			}
			return;
		}
		//创建Model所有依赖(且存在)模块的机制类
		if (log.isDebugEnabled()) {
			log.debug(modelClassSimpleName + "依赖的所有机制：" + ModuleDictFactory.getIndexModelDepends().get(modelClassSimpleName));
		}
		for (String interfaceName : ModuleDictFactory.getIndexModelDepends().get(modelClassSimpleName)) {
			//存在的模块
			if (isMechanismInterfaceExist(interfaceName)) {
				MechanismModelConfig mechanismModelConfig = ModuleDictFactory.getIndexMechanismConfigByInterface().get(interfaceName);
				//未配置ModelClassName则不需要初始化
				if(mechanismModelConfig.getModelClassName() == null) {
					continue;
				}
				Class<?> mechanismFormClass = getMechanismFormClass(mechanismModelConfig.getModelClassName());
				if (mechanismFormClass == null) {
					if (log.isDebugEnabled()) {
						log.debug("未能通过ModelName找到FormName，跳过" + interfaceName);
					}
					continue;
				}
				IExtendForm mechanismFormInstance = (IExtendForm) RuntimeClassUtil.newInstanceSilent(mechanismFormClass);
				if (mechanismFormInstance == null) {
					if (log.isDebugEnabled()) {
						log.debug(mechanismFormClass + "实例化失败，跳过" + interfaceName);
					}
					continue;
				}
				if (log.isDebugEnabled()) {
					log.debug(mechanismFormClass + "实例化成功");
				}
				mechanismMap.put(mechanismModelConfig.getMechanismKey(), mechanismFormInstance);
			} else {
				if (log.isDebugEnabled()) {
					log.debug(interfaceName + "所属模块不存在");
				}
			}
		}
	}

	/**
	 * 根据机制Model全类名找到对应机制Form类
	 *
	 * @param mechanismModelFullName 机制Model全类名
	 * @return 机制Form类
	 */
	public static Class<?> getMechanismFormClass(String mechanismModelFullName) {
		Class<?> mechanismModelClass = RuntimeClassUtil.forNameSilent(mechanismModelFullName);
		if (mechanismModelClass == null) {
			if (log.isDebugEnabled()) {
				log.debug(mechanismModelFullName + "未找到对应Class");
			}
			return null;
		}
		IBaseModel mechanismModelInstance = (IBaseModel) RuntimeClassUtil.newInstanceSilent(mechanismModelClass);
		if (mechanismModelInstance == null) {
			if (log.isDebugEnabled()) {
				log.debug(mechanismModelClass + "实例化失败");
			}
			return null;
		}
		Class<?> mechanismFormClass = mechanismModelInstance.getFormClass();
		if (mechanismFormClass == null) {
			if (log.isDebugEnabled()) {
				log.debug(mechanismModelClass + "未定义FormClass");
			}
			return null;
		}
		return mechanismFormClass;
	}

	/**
	 * 机制对应模块在系统中是否存在
	 *
	 * @param mechanismInterfaceName 机制接口
	 * @return 是/否
	 */
	public static boolean isMechanismInterfaceExist(String mechanismInterfaceName) {
		return ModuleDictFactory.getIndexMechanismConfigByInterface().containsKey(mechanismInterfaceName);
	}

	/**
	 * 根据机制接口获取机制Key
	 *
	 * @param mechanismInterfaceName 机制接口
	 * @return 机制Key
	 */
	public static String getMechanismKeyByInterface(String mechanismInterfaceName) {
		if (isMechanismInterfaceExist(mechanismInterfaceName)) {
			return ModuleDictFactory.getIndexMechanismConfigByInterface().get(mechanismInterfaceName).getMechanismKey();
		}
		return null;
	}

	/**
	 * 根据机制Key找到对应ModelFullName
	 *
	 * @param mechanismKey 机制Key
	 * @return 机制ModelFullName，模块不存在或未配置时返回null
	 */
	public static String getMechanismModelFullNameByKey(String mechanismKey) {
		if (!ModuleDictFactory.getIndexMechanismConfigByKey().containsKey(mechanismKey)) {
			return null;
		}
		return ModuleDictFactory.getIndexMechanismConfigByKey().get(mechanismKey).getModelClassName();
	}

	/**
	 * 判断模块在系统中是否存在
	 *
	 * @param moduleId 模块ID，格式："sys/number"
	 */
	public static boolean isModuleExist(String moduleId) {
		return ModuleDictFactory.getDeclares().containsKey(moduleId);
	}

	/**
	 * 主文档所属模块是否依赖于某个模块
	 *
	 * @param mainModel 主文档对象
	 * @param moduleId  模块ID，格式："sys/number"
	 */
	protected static boolean isRequiredModule(IBaseModel mainModel, String moduleId) {
		return isRequiredModule(mainModel.getClass(), moduleId);
	}

	/**
	 * 主文档所属模块是否依赖于某个模块
	 *
	 * @param mainModelClass 主文档类
	 * @param moduleId       模块ID，格式："sys/number"
	 */
	protected static boolean isRequiredModule(Class<?> mainModelClass, String moduleId) {
		String modelModuleId = getModuleId(mainModelClass);
		if (ModuleDictFactory.getDepends().containsKey(modelModuleId)) {
			List<String> dependModules = ModuleDictFactory.getDepends().get(modelModuleId).getDependModules();
			for (String dependModule : dependModules) {
				if (dependModule.equals(moduleId)) {
					return true;
				}
			}
		}
		return false;
	}
}
