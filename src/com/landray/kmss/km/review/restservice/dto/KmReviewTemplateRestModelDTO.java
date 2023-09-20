package com.landray.kmss.km.review.restservice.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KmReviewTemplateRestModelDTO extends KmReviewTemplateRestBaseDTO {

	/*
	 * 修改人
	 */
	private IdNameProperty docAlteror;

	/*
	 * 修改时间
	 */
	private Date docAlterTime;

	/*
	 * 可阅读者
	 */
	protected List<IdNameProperty> authReaders = new ArrayList<>();

	/*
	 * 可编辑者
	 */
	protected List<IdNameProperty> authEditors = new ArrayList<>();

	/*
	 * 表单信息
	 */
	private List<SysDictExtendPropertyDTO> sysFormTemplateModels = new ArrayList<>();

	public IdNameProperty getDocAlteror() {
		if(docAlteror == null || docAlteror.getFdId() == null) {
			return null;
		}
		return docAlteror;
	}

	public void setDocAlteror(IdNameProperty docAlteror) {
		this.docAlteror = docAlteror;
	}

	public Date getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	public List<IdNameProperty> getAuthReaders() {
		return authReaders;
	}

	public void setAuthReaders(List<IdNameProperty> authReaders) {
		this.authReaders = authReaders;
	}

	public List<IdNameProperty> getAuthEditors() {
		return authEditors;
	}

	public void setAuthEditors(List<IdNameProperty> authEditors) {
		this.authEditors = authEditors;
	}

	public List<SysDictExtendPropertyDTO> getSysFormTemplateModels() {
		return sysFormTemplateModels;
	}

	public void setSysFormTemplateModels(List<SysDictExtendPropertyDTO> sysFormTemplateModels) {
		this.sysFormTemplateModels = sysFormTemplateModels;
	}

}
