package com.landray.kmss.sys.attachment.service;

import net.sf.json.JSONObject;

public interface ISysAttComputingTimeService {

	public JSONObject updateComputingTime(String fdId, String templateId,
			String contentId, String time) throws Exception;

}
