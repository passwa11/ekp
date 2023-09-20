package com.landray.kmss.sys.zone.model;

import com.landray.kmss.common.model.BaseModel;

/**
 * 该model为 有事找人的扩展model，现只记录事项类型
 *
 */
public class SysZoneAddressCateVo extends BaseModel {

	@Override
	public Class getFormClass() {
		return null;
	}

	private String fdCategoryId;

	public String getFdCategoryId() {
		return fdCategoryId;
	}

	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}

	private String fdItemType;// 事项类型

	public String getFdItemType() {
		return fdItemType;
	}

	public void setFdItemType(String fdItemType) {
		this.fdItemType = fdItemType;
	}
}
