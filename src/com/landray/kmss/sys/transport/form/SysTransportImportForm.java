package com.landray.kmss.sys.transport.form;

import com.landray.kmss.sys.transport.model.SysTransportImportConfig;

public class SysTransportImportForm extends ConfigForm {
	@Override
    public Class getModelClass() {
		return SysTransportImportConfig.class;
	}
}
