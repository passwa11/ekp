package com.landray.kmss.km.archives.depend;

import com.landray.kmss.common.module.core.enhance.IBeanEnhance;
import com.landray.kmss.common.module.core.register.annotation.declare.Declare;
import com.landray.kmss.km.archives.model.KmArchivesFileTemplate;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

/**
 * 用于机制解耦后调用业务模块的service的api接口申明，
 * 业务模块的service中需要有同样的方法。
 *
 * @author 严明镜
 * @version 1.0 2021年03月09日
 */
@Declare
public interface IFileDataServiceApi {

	/**
	 * 归档
	 *
	 * @param request      请求来源的HttpRequest
	 * @param fdId         要归档的主文档ID
	 * @param fileTemplate 用户级的配置（通过模块中心对Model增强并解耦）
	 */
	void addFileMainDoc(HttpServletRequest request, String fdId, @Nullable IBeanEnhance<KmArchivesFileTemplate> fileTemplate) throws Exception;
}
