package com.landray.kmss.sys.praise.interfaces;

import net.sf.json.JSONObject;

public interface ISysPraiseInfoTitleService {
	
	/**
	 * 获取被赞内容的一些来源信息
	 * @param fdModelName
	 * @param fdModelId
	 * @return
	 * @throws Exception
	 */
	public JSONObject getSysPraiseInfoSourceInfo(String fdModelName, String fdModelId) throws Exception;
}
