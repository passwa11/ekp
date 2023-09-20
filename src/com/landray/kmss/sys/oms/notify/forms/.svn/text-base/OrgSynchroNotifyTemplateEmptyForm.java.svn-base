package com.landray.kmss.sys.oms.notify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.notify.forms.SysNotifySettingForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifySettingForm;
import com.landray.kmss.sys.oms.notify.model.OrgSynchroNotifyTemplateEmpty;
import com.landray.kmss.util.AutoHashMap;

/**
 * 创建日期 2006-12-26
 * 
 * 组织机构同步发布设置
 * 
 * @author 吴兵
 */
public class OrgSynchroNotifyTemplateEmptyForm extends ExtendForm implements
		ISysNotifySettingForm {

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		notifySettingForms.clear();

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return OrgSynchroNotifyTemplateEmpty.class;
	}

	private AutoHashMap notifySettingForms = new AutoHashMap(
			SysNotifySettingForm.class);

	@Override
    public AutoHashMap getNotifySettingForms() {
		return notifySettingForms;
	}
}
