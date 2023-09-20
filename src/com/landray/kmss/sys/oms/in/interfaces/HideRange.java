package com.landray.kmss.sys.oms.in.interfaces;

/**
 * 组织隐藏范围
 * @description:
 * @author: wangjf
 * @time: 2021/9/28 4:44 下午
 */
public class HideRange {
	
	private String fdId;
	
	/**
	 * 是否开启组织成员查看组织范围
	 */
	private Boolean fdIsOpenLimit;
	
	/**
	 * 查看类型
	 */
	private Integer fdViewType;

	/**
	 * 其他可查看组织/人员
	 */
	private String[] fdOther;

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public Boolean getFdIsOpenLimit() {
		return fdIsOpenLimit;
	}

	public void setFdIsOpenLimit(Boolean fdIsOpenLimit) {
		this.fdIsOpenLimit = fdIsOpenLimit;
	}

	public Integer getFdViewType() {
		return fdViewType;
	}

	public void setFdViewType(Integer fdViewType) {
		this.fdViewType = fdViewType;
	}

	public String[] getFdOther() {
		return fdOther;
	}

	public void setFdOther(String[] fdOther) {
		this.fdOther = fdOther;
	}
}
