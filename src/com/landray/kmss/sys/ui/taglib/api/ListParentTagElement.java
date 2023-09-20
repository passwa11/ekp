package com.landray.kmss.sys.ui.taglib.api;

import java.util.Map;

/**
 * 可以进行类似列表添加项目的父标签接口
 * 
 *
 */
public interface ListParentTagElement {

	void addItem(Map<String, Object> child);
}
