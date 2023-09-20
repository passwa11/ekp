package com.landray.kmss.common.module.core.enhance;

import com.landray.kmss.common.forms.BaseCoreInnerForm;
import com.landray.kmss.common.model.IBaseCoreInnerModel;
import com.landray.kmss.common.module.core.proxy.IDynamicProxy;

/**
 * Bean对象的包装增强类
 *
 * @author 严明镜
 * @version 1.0 2021年03月01日
 */
public interface IBeanEnhance<M> extends IDynamicProxy{

	/**
	 * 设置一个字段的值
	 * <b>示例:</b>
	 * <pre>
	 *    IBeanEnhance model = ModuleCenter.createEnhanceBean("com.landray.kmss.km.archives.model.KmArchivesFileTemplate");
	 *    if(model != null) {
	 *        model.setProperty("fdModelName", modelName);
	 *        model.setProperty("fdModelId", modelId);
	 *        model.setProperty("docCreateTime", new Date());
	 *        model.setProperty("docCreator", modelingAppModel.getDocCreator());
	 *        model.setProperty("selectFilePersonType", "org");
	 *        model.setProperty("fdKey", modelId);
	 *    }
	 * </pre>
	 *
	 * @param property 字段名
	 * @param value    值
	 */
	void setProperty(String property, Object value);

	/**
	 * 获取一个字段的值
	 *
	 * @param property 字段名
	 * @return Object
	 * @see IBeanEnhance#getProperty(String, Class) 示例
	 */
	Object getProperty(String property);

	/**
	 * 获取一个字段的值，并在类型转换后传出
	 *
	 * <b>示例：</b>
	 * <pre>
	 *    IBeanEnhance fileTemplate = ModuleCenter.enhanceBean(list.get(0));
	 *    if (fileTemplate.getProperty("Category").isPresent()) {
	 *        saveApproval = fileTemplate.getProperty("fdSaveApproval", Boolean.class);
	 *    }
	 * </pre>
	 *
	 * @param property 字段名
	 * @param clz      需要返回的类型
	 * @return clz对应的类型
	 */
	<T> T getProperty(String property, Class<T> clz);

	/**
	 * 直接将对象机制类放入机制Map中(Model/Form)
	 *
	 * @param mechName   机制名
	 * @param mechObject 机制对象
	 */
	void setMechanism(String mechName, Object mechObject);

	/**
	 * set机制Model
	 * (业务模块or机制使用)
	 *
	 * <b>示例：</b>
	 * <pre>
	 *     ModuleCenter.enhanceBean(modelingAppModel).setMechanism(kmArchiveFileTemplate);
	 * </pre>
	 *
	 * @param model 机制Model
	 */
	void setMechanism(IBaseCoreInnerModel model);

	/**
	 * set机制Form
	 * (业务模块or机制使用)
	 *
	 * <b>示例：</b>
	 * <pre>
	 *     ModuleCenter.enhanceBean(modelingAppModelForm).setMechanism(kmArchiveFileTemplateForm);
	 * </pre>
	 *
	 * @param value 机制Form
	 */
	void setMechanism(BaseCoreInnerForm value);

	/**
	 * 自动取出包装类中的机制对象，并设置到当前包装类的机制Map中
	 */
	@SuppressWarnings("rawtypes")
	void setMechanism(IBeanEnhance modelEnhance);

	/**
	 * get机制Model，并在类型转换后传出
	 * (机制使用)
	 *
	 * <b>示例：</b>
	 * <pre>
	 *    KmArchivesFileTemplate modelOpt = ModuleCenter.enhanceBean(model).getMechanismModel(KmArchivesFileTemplate.class);
	 * </pre>
	 *
	 * @param clz 机制Form的Class
	 * @return clz对应的类型
	 */
	<T extends IBaseCoreInnerModel> T getMechanismModel(Class<T> clz);

	/**
	 * get机制Form，并在类型转换后传出。
	 * 如果不存在则初始化一个。
	 * (机制使用)
	 *
	 * <b>示例：</b>
	 * <pre>
	 *    KmArchivesFileTemplateForm formOpt = ModuleCenter.enhanceBean(form).getMechanismModel(KmArchivesFileTemplateForm.class);
	 * </pre>
	 *
	 * @param clz 机制Model的Class
	 * @return clz对应的类型
	 */
	<T extends BaseCoreInnerForm> T getMechanismForm(Class<T> clz);

	/**
	 * 直接获取机制类
	 *
	 * @param mechanismKey 机制KEY
	 * @return 增强类
	 */
	@SuppressWarnings("rawtypes")
	IBeanEnhance getMechanism(String mechanismKey);

	/**
	 * 返回值
	 */
	M get();

	/**
	 * 返回类型转换后的值
	 *
	 * @return clz对应的类型
	 */
	<T> T get(Class<T> clz);

	/**
	 * 返回Bean的Class
	 *
	 * @return Class
	 */
	Class<M> getBeanClass();

}
