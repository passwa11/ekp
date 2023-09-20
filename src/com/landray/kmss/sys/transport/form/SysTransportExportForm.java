package com.landray.kmss.sys.transport.form;

import com.landray.kmss.sys.transport.model.SysTransportExportConfig;

public class SysTransportExportForm extends ConfigForm {
	@Override
    public Class getModelClass() {
		return SysTransportExportConfig.class;
	}
}
