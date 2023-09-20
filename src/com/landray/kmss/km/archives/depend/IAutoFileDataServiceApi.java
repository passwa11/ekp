package com.landray.kmss.km.archives.depend;

import com.landray.kmss.common.module.core.enhance.IBeanEnhance;
import com.landray.kmss.common.module.core.register.annotation.declare.Declare;
import com.landray.kmss.km.archives.model.KmArchivesFileTemplate;

/**
 * 用于机制解耦后调用业务模块的service的api接口申明，
 * 业务模块的service中需要有同样的方法。
 *
 * @author 严明镜
 * @version 1.0 2021年03月09日
 */
@Declare
public interface IAutoFileDataServiceApi {

    /**
     * 自动归档
     * @param fdId
     * @throws Exception
     */
    void addAutoFileMainDoc(String fdId, IBeanEnhance<KmArchivesFileTemplate> fileTemplate) throws Exception;
    
    /**
     * 自动归档
     * @param fdId
     * @throws Exception
     */
    void addAutoFileMainDoc(String fdId) throws Exception;
}
