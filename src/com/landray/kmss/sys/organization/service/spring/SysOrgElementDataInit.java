package com.landray.kmss.sys.organization.service.spring;

import java.util.Date;
import java.util.Map;

import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.datainit.service.ISysDatainitSurroundInterceptor;
import com.landray.kmss.sys.datainit.service.spring.ProcessRuntime;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.sso.client.oracle.StringUtil;

public class SysOrgElementDataInit implements ISysDatainitSurroundInterceptor {

	public SysOrgElementDataInit() {
		
	}

	@Override
	public void beforeRestoreModelData(IBaseModel model,
			Map<String, Object> data, Map<String, IBaseModel> cache,
			ProcessRuntime processRuntime) throws Exception {
		String modelName = (String) data.get("class");
		if (StringUtil.isNull(modelName)) {
			return;
		}
		IBaseModel baseModel = (IBaseModel) com.landray.kmss.util.ClassUtils.forName(modelName)
				.newInstance();
		if (baseModel instanceof SysOrgElement) {
			Date current = new Date();
			data.put("fdAlterTime", current);
			if (data.get("fdCreateTime") == null) {
				data.put("fdCreateTime", current);
			}
		}
	}

	// 导出扩展处理
	@Override
    @SuppressWarnings("unchecked")
	public void beforeStoreModelData(IBaseModel model, Map<String, Object> data)
			throws Exception {
		return;
	}
}
