package com.landray.kmss.sys.praise.forms;

import java.util.List;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.util.AutoArrayList;

public class SysPraiseInfoConfigMainForm extends ExtendForm{

	@Override
	public Class getModelClass() {
		
		return null;
	}
	
	private List<SysPraiseInfoConfigForm> configList = new AutoArrayList(SysPraiseInfoConfigForm.class);

	public List<SysPraiseInfoConfigForm> getConfigList() {
		return configList;
	}

	public void setConfigList(List<SysPraiseInfoConfigForm> configList) {
		this.configList = configList;
	}


}
