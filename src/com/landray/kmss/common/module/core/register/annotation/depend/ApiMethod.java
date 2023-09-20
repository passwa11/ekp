package com.landray.kmss.common.module.core.register.annotation.depend;

import java.lang.annotation.*;

/**
 * 声明方法名及serviceName
 * <p>
 * 方法注解@ApiMethod非必须，参数value为对应的方法名，参数serviceBean为指定该方法覆盖@Dependency对应的ServiceBeanName。
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
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiMethod {
	/**
	 * 对应ServiceBean中的方法名
	 */
	String value();

	/**
	 * 指定该方法调用的ServiceBeanName
	 */
	String serviceBean() default "";
}
