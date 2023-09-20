package com.landray.kmss.sys.portal.cloud.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 单元格
 * <p>
 * col: string
 * </p>
 * <p>
 * value: string
 * </p>
 * <p>
 * href?: string
 * </p>
 * <p>
 * color?: IColor
 * </p>
 * <p>
 * icons
 * </p>
 * 
 * @author chao
 *
 */
// 属性为NULL则不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CellDataVO {
	/** key */
	private String col;
	/** 列值 */
	private Object value;
	/** 单元格链接 */
	private String href;
	/** 单元格文字颜色 */
	private String color;
	/** 图标集 */
	private List<IconDataVO> icons;

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<IconDataVO> getIcons() {
		return icons;
	}

	public void setIcons(List<IconDataVO> icons) {
		this.icons = icons;
	}

	static ThreadLocal<CellDataVO> holder = new ThreadLocal<>();

	private static CellDataVO init() {
		CellDataVO vo = new CellDataVO();
		vo.setCol("qqq");
		holder.set(vo);
		return vo;
	}

	public static void main(String[] args) {
		CellDataVO vo = holder.get();
		if (vo == null) {
			System.out.println("为null");
			vo = init();
			System.out.println(vo);
			System.out.println(vo.getCol());
		}
	}
}
