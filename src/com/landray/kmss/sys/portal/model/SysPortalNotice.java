package com.landray.kmss.sys.portal.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.portal.forms.SysPortalNoticeForm;

/**
 * @author linxiuxian
 *
 */
public class SysPortalNotice extends BaseModel {
	private String docContent;

	private Date docStartTime;

	private Date docEndTime;

	private SysOrgPerson docCreator;

	private Date docCreateTime;

	private Boolean fdState = true;

	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocContentOri() {
		return docContent;
	}

	public void setDocContentOri(String docContent) {
		this.docContent = docContent;
	}

	public String getDocContent() {
		return SysLangUtil.getPropValue(this, "docContent", this.docContent);
	}

	public void setDocContent(String docContent) {
		this.docContent = docContent;
		SysLangUtil.setPropValue(this, "docContent", docContent);
	}

	public Date getDocStartTime() {
		return docStartTime;
	}

	public void setDocStartTime(Date docStartTime) {
		this.docStartTime = docStartTime;
	}

	public Date getDocEndTime() {
		return docEndTime;
	}

	public void setDocEndTime(Date docEndTime) {
		this.docEndTime = docEndTime;
	}

	public Boolean getFdState() {
		if (fdState == null) {
            return true;
        }
		return fdState;
	}

	public void setFdState(Boolean fdState) {
		this.fdState = fdState;
	}

	@Override
	public Class getFormClass() {
		return SysPortalNoticeForm.class;
	}

	private static ModelToFormPropertyMap modelToFormPropertyMap = null;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (modelToFormPropertyMap == null) {
			modelToFormPropertyMap = new ModelToFormPropertyMap();
			modelToFormPropertyMap.putAll(super.getToFormPropertyMap());
			// 创建者
			modelToFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			modelToFormPropertyMap.put("docCreator.fdName", "docCreatorName");
		}
		return modelToFormPropertyMap;
	}
}
