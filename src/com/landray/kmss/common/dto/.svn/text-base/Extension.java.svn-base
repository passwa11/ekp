package com.landray.kmss.common.dto;

import java.lang.annotation.ElementType;

/**
 * 扩展
 * 
 * @author 叶中奇
 */
public interface Extension {
	/**
	 * ID，若扩展点未定义ID，则使用注解所在类类名
	 * 
	 * @return
	 */
	String getId();

	/**
	 * 中文名
	 * 
	 * @return
	 */
	String getLabel();

	/**
	 * 国际化标识
	 * 
	 * @return
	 */
	String getMessageKey();

	/**
	 * 模块
	 * 
	 * @return
	 */
	String getModule();

	/**
	 * 排序号，小的在前面
	 * 
	 * @return
	 */
	Integer getOrder();

	/**
	 * 扩展作用的类名
	 * 
	 * @return
	 */
	String getRefName();

	/**
	 * 扩展作用的成员
	 * 
	 * @return
	 */
	String getElementName();

	/**
	 * 扩展作用点类型
	 * 
	 * @return
	 */
	ElementType getElementType();
}
