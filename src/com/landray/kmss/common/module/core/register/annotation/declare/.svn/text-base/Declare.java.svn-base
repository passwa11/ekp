package com.landray.kmss.common.module.core.register.annotation.declare;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记此接口为需要其它模块实现的Api，其它模块实现时使用@ApiImplement方法注解
 *
 * <br><b>示例：</b>
 * <pre>
 *   {@literal @}Declare
 *    public interface IFileDataServiceApi {
 *    	void addFileMainDoc(HttpServletRequest request, String fdId, @Nullable IBeanEnhance<KmArchivesFileTemplate> fileTemplate) throws Exception;
 *    }
 * </pre>
 *
 * @author 严明镜
 * @version 1.0 2021年03月24日
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Declare {
}
