package com.landray.kmss.common.module.util;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.module.core.cache.CachedProxyFactory;
import com.landray.kmss.common.module.core.enhance.BeanEnhance;
import com.landray.kmss.common.module.core.enhance.IBeanEnhance;
import com.landray.kmss.common.module.core.enhance.gennerator.BeanEnhanceGenerator;
import com.landray.kmss.common.module.core.proxy.IDynamicProxy;
import com.landray.kmss.common.module.core.proxy.ServiceProxy;
import com.landray.kmss.common.module.core.proxy.generator.ApiProxyGenerator;
import com.landray.kmss.common.module.core.proxy.generator.DeclareApiProxyGenerator;
import com.landray.kmss.common.module.core.proxy.generator.ServiceProxyGenerator;
import com.landray.kmss.common.module.core.proxy.generator.StaticProxyGenerator;
import com.landray.kmss.common.module.core.register.loader.ModuleDictUtil;
import com.landray.kmss.common.module.core.util.RuntimeClassUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.ModelUtil;
import org.springframework.util.Assert;

/**
 * 模块解耦中心调用入口
 *
 * @author 严明镜
 * @version 1.0 2021年02月19日
 */
public class ModuleCenter {

	private static final CachedProxyFactory generator = new CachedProxyFactory();

	/**
	 * <br><b>1.调用方声明Api(根据类注释@Dependency及方法注释@ApiMethod，预创建代理类的Api接口)</b>
	 * <pre>
	 *   {@literal @}Dependency("kmArchivesFileTemplateService")
	 *    public interface IKmArchivesServiceApi extends IBaseCoreInnerService {
	 *
	 *    	void addFileMainDoc(HttpServletRequest request, IBaseModel mainModel, IBaseModel templateModel,
	 *    						IBeanEnhance<?> fileTemplate, String filePrintPageUrl) throws Exception;
	 *
	 *    	void addAutoFileMainDoc(IBaseModel mainModel, IBaseModel templateModel, String filePrintPageUrl) throws Exception;
	 *
	 *      {@literal @}ApiMethod(value = "validateArchivesSignature", serviceBean = "kmArchivesSignService")
	 *       boolean validateArchivesSignature(String expires, String fdId, String sign, Logger logger) throws Exception;
	 *    }
	 * </pre>
	 *
	 * <b>2.调用方进行调用(通过本方法获取Api接口的代理类，从而使用接口调用的风格写代码。)</b>
	 * <pre>
	 *    IKmArchivesServiceApi apiProxy = ModuleCenter.getApiProxy(IKmArchivesServiceApi.class);
	 *    if (apiProxy != null) {
	 *        apiProxy.addAutoFileMainDoc(mainModel, mainModel.getFdModel(), filePrintPageUrl);
	 *    }
	 * </pre>
	 *
	 * @param apiInterface 使用@Dependency及方法注释@ApiMethod声明需代理的api类及方法
	 * @param <I>          api类
	 * @return 被代理的api类，执行时会通过getServiceProxy(..)的逻辑进行service方法的调用
	 */
	public static <I> I getApiProxy(Class<I> apiInterface) {
		return generator.createProxy(new ApiProxyGenerator<>(apiInterface));
	}

	/**
	 * 用于机制使用，通过serviceName获取serviceBean，调用业务模块所声明的方法
	 * <br><b>1.在机制模块中，申明所需要调用业务模块的service的api接口：</b>
	 * <pre>
	 *   {@literal @}Declare
	 *    public interface IFileDataServiceApi {
	 *    	void addFileMainDoc(HttpServletRequest request, String fdId, @Nullable IBeanEnhance<KmArchivesFileTemplate> fileTemplate) throws Exception;
	 *    }
	 * </pre>
	 * <br><b>2.业务模块的service中需要实现(无需继承)该方法：</b>
	 * <pre>
	 *   {@literal @}ApiImplement("com.landray.kmss.km.archives.depend.IFileDataServiceApi")
	 *    public void addFileMainDoc(HttpServletRequest request, String fdId, IBeanEnhance<?> fileTemplate) throws Exception {
	 *    	ModelingAppModelMain mainModel = (ModelingAppModelMain) findByPrimaryKey(fdId);
	 *    	if (!mainModel.getDocStatus().equals("30") && !mainModel.getDocStatus().equals("31")) {
	 *    		throw new KmssRuntimeException(new KmssMessage("km-archives:file.notsupport"));
	 *        }
	 *    	addFileMainDocForModeling(request, mainModel, fileTemplate);
	 *    }
	 * </pre>
	 * <br><b>3.在机制模块中，即可使用此方式进行调用(serviceName为业务模块实现(无需继承)该方法的Service):</b>
	 * <pre>
	 *    IFileDataServiceApi apiProxy = ModuleCenter.getDeclareApiProxy(IFileDataServiceApi.class, serviceName);
	 * 	  if (apiProxy != null) {
	 *        apiProxy.addFileMainDoc(request, fdModelId, ModuleCenter.enhanceBean(nFlieTemplate));
	 *    }
	 * </pre>
	 *
	 * @param apiInterface 使用@Dependency及方法注释@ApiMethod声明需代理的api类及方法
	 * @param serviceName  已在spring配置中申明的serviceBean
	 * @param <I>          api类
	 * @return 被代理的api类，执行时会通过getServiceProxy(..)的逻辑进行service方法的调用
	 */
	public static <I> I getDeclareApiProxy(Class<I> apiInterface, String serviceName) {
		return generator.createProxy(new DeclareApiProxyGenerator<>(apiInterface, serviceName));
	}

	/**
	 * 包装Service为反射调用的代理类。
	 * 一般不直接使用此方法，而是使用 ModuleCenter#getApiProxy 间接调用。
	 *
	 * @param beanName 在spring中注册的service的名称
	 * @return 用于后续调用service方法的包装类
	 * @see ServiceProxy#invoke(String, Object...) 说明
	 */
	public static IDynamicProxy getServiceProxy(String beanName) {
		return generator.createProxy(new ServiceProxyGenerator(beanName));
	}

	/**
	 * 包装静态类（如其它模块的Util）为反射调用的代理类
	 *
	 * <b>示例：</b>
	 * <pre>
	 *  IDynamicProxy staticProxy = ModuleCenter.getStaticProxy("com.landray.kmss.dbcenter.echarts.util.DbEchartsTotalUtil");
	 *  if(staticProxy != null) {
	 *    staticProxy.invoke("addDbEchartsTotal", getDbEchartsTotalService(), importContext.getForm());
	 *  }
	 * </pre>
	 *
	 * @param modelFullName 带包名的Model全名
	 * @return Invoker包装类
	 */
	public static IDynamicProxy getStaticProxy(String modelFullName) {
		return generator.createProxy(new StaticProxyGenerator(modelFullName));
	}

	/**
	 * 获取静态字段值
	 *
	 * <b>示例：</b>
	 * <pre>
	 *   String BORROW_STATUS_RETURNED = ModuleCenter.getStaticFiled("com.landray.kmss.km.archives.util.KmArchivesConstant", "BORROW_STATUS_RETURNED", String.class);
	 * </pre>
	 *
	 * @param fullClassName 类名全称
	 * @param fieldName     字段名
	 * @param returnType    返回值类型
	 * @return 属性值
	 */
	public static <T> T getStaticFiled(String fullClassName, String fieldName, Class<T> returnType) {
		return RuntimeClassUtil.getStaticFiled(fullClassName, fieldName, returnType);
	}

	/**
	 * 用于根据model名创建对象
	 *
	 * @param fullModelName 类全名
	 * @return 包装类
	 */
	@SuppressWarnings("rawtypes")
	public static IBeanEnhance createEnhanceBean(String fullModelName) {
		return generator.createProxy(new BeanEnhanceGenerator(fullModelName));
	}

	/**
	 * 用于将Model/Form增强后进行传递、间接调用方法
	 *
	 * @param bean Model/Model
	 * @return 包装类
	 */
	public static <T> IBeanEnhance<T> enhanceBean(T bean) {
		if (bean == null) {
			return null;
		}
		return new BeanEnhance<>(bean);
	}

	/**
	 * 根据Model对象的数据字典返回IBaseApi代理接口(Service需继承IBaseService)。
	 *
	 * <pre>
	 *   IDynamicProxy service = ModuleCenter.findServiceByModel(mainModel);
	 *   if(service != null) {
	 *       service.invoke("update", mainModel);
	 *   }
	 * </pre>
	 *
	 * @param model Model
	 * @return Service包装类
	 */
	public static IDynamicProxy findServiceByModel(IBaseModel model) {
		SysDictModel dictModel = SysDataDict.getInstance().getModel(ModelUtil.getModelClassName(model));
		if (dictModel == null) {
			throw new RuntimeException("未找到该Model的数据字典:" + ModelUtil.getModelClassName(model));
		} else {
			return getServiceProxy(dictModel.getServiceBean());
		}
	}

	/**
	 * 将fromObject中的机制设置到toObject中
	 *
	 * @param mechanismKey 机制Key
	 * @param fromObject   来源
	 * @param toObject     目标
	 */
	public static void copyMechanism(String mechanismKey, Object fromObject, Object toObject) {
		Assert.isTrue(fromObject != null, "机制拷贝来源不能为空");
		Assert.isTrue(toObject != null, "机制拷贝目标不能为空");
		IBeanEnhance<?> fromEnhance = ModuleCenter.enhanceBean(fromObject);
		IBeanEnhance<?> toEnhance = ModuleCenter.enhanceBean(toObject);
		IBeanEnhance<?> mechanism = fromEnhance.getMechanism(mechanismKey);
		if (mechanism == null) {
			toEnhance.setMechanism(mechanismKey, null);
		} else {
			toEnhance.setMechanism(mechanismKey, mechanism.get());
		}
	}
}
