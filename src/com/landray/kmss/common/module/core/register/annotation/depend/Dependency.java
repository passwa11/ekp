package com.landray.kmss.common.module.core.register.annotation.depend;

import java.lang.annotation.*;

/**
 * 声明该接口为其它模块的Api代理接口
 * <p>
 * 1.声明@Dependency的value指定对应ServiceBean的名称。<br/>
 * 2.通过ModuleCenter.getApiProxy(接口Class)获取代理类进行调用。<br/>
 * <p>
 * 类注解@Dependency必须有，其参数值为整个Api的默认ServiceBeanName。<br/>
 * a)	不写方法注解@ApiMethod时默认取类注解@Dependency的ServiceBeanName。<br/>
 * b)	以及在需要通过继承调用IBaseCoreInnerService及IBaseService中的增删改查/convert等方法时使用（一般在模块依赖的情况下可能有该场景，如果是依赖的机制，增删改查应该是机制处理，或机制提供相关的业务接口）。<br/>
 * <p>
 * 注:可通过继承IBaseService实现基本CRUD接口
 *
 * <br><b>示例：</b>
 * <pre>
 *  {@literal @}Dependency("kmArchivesFileTemplateService")
 *   public interface IKmArchivesServiceApi extends IBaseCoreInnerService {
 *
 *   	void addFileMainDoc(HttpServletRequest request, IBaseModel mainModel, IBaseModel templateModel,
 *   						IBeanEnhance<?> fileTemplate, String filePrintPageUrl) throws Exception;
 *
 *   	void addAutoFileMainDoc(IBaseModel mainModel, IBaseModel templateModel, String filePrintPageUrl) throws Exception;
 *
 *   {@literal @}ApiMethod(value = "validateArchivesSignature", serviceBean = "kmArchivesSignService")
 *      boolean validateArchivesSignature(String expires, String fdId, String sign, Logger logger) throws Exception;
 *   }
 * </pre>
 *
 * @author 严明镜
 * @version 1.0 2021年03月24日
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dependency {

	/**
	 * ServiceBean名称
	 */
	String value();

}