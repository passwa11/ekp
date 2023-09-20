package com.landray.kmss.sys.portal.cloud;

import net.sf.json.JSONObject;

public interface IVarKindParser {
	/**
	 * 变量&lt;var&gt类型
	 * 
	 * @return
	 */
	String getKind();

	/**
	 * 解析kind的内容
	 * 
	 * @param json
	 */
	void parse(JSONObject json);
}
