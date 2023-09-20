package com.landray.kmss.sys.transport.service.extendsion;

import java.util.Set;

/**
 * @Author lifangmin
 * @Date 2021/1/28 13:36
 * @VERSION 1.0.0
 * @Description 导入导出过滤规约
 */
public interface ISysTransportFilter {

    /**
     * 获取需要过滤的属性集合
     * @return
     */
    Set<String> getFilterProps(String modelName);
}
