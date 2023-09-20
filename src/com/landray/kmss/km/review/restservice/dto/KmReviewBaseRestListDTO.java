package com.landray.kmss.km.review.restservice.dto;

import java.util.List;

public class KmReviewBaseRestListDTO {
	private int pageNo;       //当前页
	private int rowSize;      //分页大小
	private int totalPage;    //总页数
	private int totalSize;    //总记录数
	private List<? extends IdProperty> datas;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public List<? extends IdProperty> getDatas() {
		return datas;
	}

	public void setDatas(List<? extends IdProperty> datas) {
		this.datas = datas;
	}
}
