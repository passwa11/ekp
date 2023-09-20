package com.landray.kmss.sys.portal.cloud.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 图标
 * <p>
 * type : string
 * </p>
 * <p>
 * theme? : 'outline' | 'fill'
 * </p>
 * <p>
 * style : object
 * </p>
 * 
 * @author chao
 *
 */
// 属性为NULL则不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IconDataVO {
	/** 图标id */
	private String name;
	/** 图标类型 */
	private String type;
	/** 样式 */
	private String style;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}
