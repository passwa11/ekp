package com.landray.kmss.sys.portal.cloud.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author chao
 *
 */
// 属性为NULL则不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableDataVO {
	/**
	 * 分页参数
	 */
	private PageDataVO page;
	/**
	 * 表头
	 */
	private List<ColumnDataVO> columns;
	/**
	 * 行数据
	 */
	private List<RowDataVO> data;

	public PageDataVO getPage() {
		return page;
	}

	public void setPage(PageDataVO page) {
		this.page = page;
	}

	public List<ColumnDataVO> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnDataVO> columns) {
		this.columns = columns;
	}

	public List<RowDataVO> getData() {
		return data;
	}

	public void setData(List<RowDataVO> data) {
		this.data = data;
	}
}
