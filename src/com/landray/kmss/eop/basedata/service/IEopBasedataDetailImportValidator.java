package com.landray.kmss.eop.basedata.service;

import java.util.Map;

public interface IEopBasedataDetailImportValidator {
	/**
	 * 明细导入属性校验
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Boolean validate(Map<String,Object> context,Object value) throws Exception;
}
