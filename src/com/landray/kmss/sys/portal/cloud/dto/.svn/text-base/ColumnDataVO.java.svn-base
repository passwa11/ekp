package com.landray.kmss.sys.portal.cloud.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 与&lt;list:data-column&gt;对应
 * <p>
 * property: string
 * </p>
 * <p>
 * title: string
 * </p>
 * <p>
 * align?: 'center' | 'left' | 'right'
 * </p>
 * <p>
 * width?: string
 * </p>
 * <p>
 * color?: string
 * </p>
 * 
 * @author chao
 *
 */
// 属性为NULL则不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnDataVO {
	/** key */
	private String property;
	/** 列头名 */
	private String title;
	/** 对齐方式 */
	private String align;
	/** 列头宽 */
	private String width;
	/** 列头文字颜色 */
	private String color;
	/** 列类型 */
	private String renderType;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRenderType() {
		return renderType;
	}

	public void setRenderType(String renderType) {
		this.renderType = renderType;
	}
}
