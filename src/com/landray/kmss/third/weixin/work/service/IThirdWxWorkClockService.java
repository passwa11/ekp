package com.landray.kmss.third.weixin.work.service;
import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONObject;

public interface IThirdWxWorkClockService extends IExtendDataService {

	public List<JSONObject> getUserWeChatClock(Date startTime, Date endTime,
			List<String> idList) throws Exception;
}
