package com.landray.kmss.sys.oms.notify.model;

import java.util.List;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.notify.interfaces.ISysNotifySettingModel;
import com.landray.kmss.sys.oms.notify.forms.OrgSynchroNotifyTemplateEmptyForm;

/**
 * 创建日期 2006-12-26
 * 
 * 组织机构同步发布设置
 * 
 * @author 吴兵
 */
public class OrgSynchroNotifyTemplateEmpty extends BaseModel implements
		ISysNotifySettingModel {
	public OrgSynchroNotifyTemplateEmpty() {
		setFdId("0");
	}

	@Override
    public Class getFormClass() {
		return OrgSynchroNotifyTemplateEmptyForm.class;
	}

	private List notifySettingModels;

	@Override
    public List getNotifySettingModels() {
		return notifySettingModels;
	}

	@Override
    public void setNotifySettingModels(List notifySettingModels) {
		this.notifySettingModels = notifySettingModels;
	}
}
