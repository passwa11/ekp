package com.landray.kmss.km.review.model;

import java.util.Date;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;

/**
 * 创建日期 2013-12-5
 * 
 * @author 朱湖强 流程分类概览
 */
public class KmReviewOverview extends BaseModel implements InterceptFieldEnabled,ISysAuthAreaModel{
	
	public KmReviewOverview() {
		super();
	}
	
	@Override
    public Class getFormClass() {
		return null;
	}

	
	private String fdPreContent;

	public String getFdPreContent() {
		return (String) readLazyField("fdPreContent", fdPreContent);
	}

	public void setFdPreContent(String fdPreContent) {
		this.fdPreContent = (String) writeLazyField("fdPreContent",
				this.fdPreContent, fdPreContent);
	}

	private Date docAlterTime;

	public Date getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}
	
	/*
	 * 所属场所
	 */
	protected SysAuthArea authArea;

	@Override
    public SysAuthArea getAuthArea() {
		return authArea;
	}

	@Override
    public void setAuthArea(SysAuthArea authArea) {
		this.authArea = authArea;
	}


}
