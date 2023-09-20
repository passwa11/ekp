package com.landray.kmss.sys.simplecategory.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.model.BaseModel;

/**
 * 分类概览model
 * 
 * @author Administrator
 * 
 */
public abstract class SysSimpleCategoryPreviewModel extends BaseModel {

	private static final long serialVersionUID = -6512072066424927338L;
	private String fdPreContent;
	private Date createDate;
	private Date alterDate;
	private String authAreaId;
	protected String tempName = null;
	protected Integer docAmount = 0;
	protected static List<SysSimpleCategoryPreviewModel> tempList = new ArrayList<SysSimpleCategoryPreviewModel>();
	private String fdCategoryId;

	// 是否开启分级授权和允许数据隔离
	private String isDataIsolation;

	public String getFdPreContent() {
		return fdPreContent;
	}

	public void setFdPreContent(String fdPreContent) {
		this.fdPreContent = fdPreContent;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getAlterDate() {
		return alterDate;
	}
	
	public String getTempName() {
		return tempName;
	}
	
	public Integer getDocAmount() {
		return docAmount;
	}
	

	public String getAuthAreaId() {
		return authAreaId;
	}
	
	public static List<SysSimpleCategoryPreviewModel> getTempList() {
		return tempList;
	}
	
	public void setTempList(List<SysSimpleCategoryPreviewModel> tempList) {
		SysSimpleCategoryPreviewModel.tempList = tempList;
	}

	public void setAuthAreaId(String authAreaId) {
		this.authAreaId = authAreaId;
	}

	@Override
    public String getFdId() {
		return fdId;
	}

	@Override
    public void setFdId(String fdId) {
		this.fdId = fdId;
	}
	
	public String getIsDataIsolation() {
		return isDataIsolation;
	}

	public void setIsDataIsolation(String isDataIsolation) {
		this.isDataIsolation = isDataIsolation;
	}
	
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	
	public void setDocAmount(Integer docAmount) {
		this.docAmount = docAmount;
	}
	
	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}
	
	public String getFdCategoryId() {
		return fdCategoryId;
	}

	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}
}
