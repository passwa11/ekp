package com.landray.kmss.common.module.core.register.annotation.declare;

import java.lang.annotation.*;

/**
 * 声明该方法用于被其它模块调用
 *
 * <br><b>示例：</b>
 * <pre>
 *   {@literal @}ApiImplement("com.landray.kmss.km.archives.depend.IFileDataServiceApi")
 *    public void addFileMainDoc(HttpServletRequest request, String fdId, IBeanEnhance<?> fileTemplate) throws Exception {
 * 		ModelingAppSimpleMain mainModel = (ModelingAppSimpleMain) findByPrimaryKey(fdId);
 * 		addFileMainDocForModeling(request, mainModel, fileTemplate);
 *    }
 * </pre>
 *
 * @author 严明镜
 * @version 1.0 2021年03月24日
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiImplement {
	String value();
}
