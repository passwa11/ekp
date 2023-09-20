package com.landray.kmss.third.weixin.work.service.spring;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.third.weixin.constant.WxConstant;
import com.landray.kmss.third.weixin.util.WxHttpClientUtil;
import com.landray.kmss.third.weixin.work.api.WxworkApiServiceImpl;
import com.landray.kmss.third.weixin.work.model.api.WxAttend;
import com.landray.kmss.third.weixin.work.service.IThirdWxWorkClockService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;

public class ThirdWxWorkClockServiceImp extends ExtendDataServiceImp
		implements IThirdWxWorkClockService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWxWorkClockServiceImp.class);

	@Override
	public JSONArray getUserWeChatClock(Date startTime, Date endTime,
			List<String> idList) throws Exception {

		WxworkApiServiceImpl wxApiService = new WxworkApiServiceImpl();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strtTimeStr = DateUtil.convertDateToString(startTime, DateUtil.PATTERN_DATE) + " 00:00:00";
		String endTimeStr = DateUtil.convertDateToString(endTime, DateUtil.PATTERN_DATE) + " 23:59:59";
		WxAttend wxAttend = new WxAttend();
		wxAttend.setOpencheckindatatype(3);
		// 接口只识别取10位时间戳，取前10位
		wxAttend.setStarttime(df.parse(strtTimeStr).getTime() / 1000);
		wxAttend.setEndtime(df.parse(endTimeStr).getTime() / 1000);
		wxAttend.setUseridlist(idList.toArray(new String[idList.size()]));

		BaseAppConfig appConfig = (BaseAppConfig) com.landray.kmss.util.ClassUtils.forName("com.landray.kmss.third.weixin.work.model.WeixinWorkConfig").newInstance();
		if (appConfig.getDataMap() == null || StringUtil.isNull(appConfig.getDataMap().get("wxSSOAttendSecret"))) {
			logger.error("企业微信集成未配置必要参数(" + DateUtil.convertDateToString(startTime, DateUtil.PATTERN_DATE) + "~" + DateUtil.convertDateToString(endTime, DateUtil.PATTERN_DATE) + ")");
			throw new Exception("无法获取打卡信息(" + DateUtil.convertDateToString(startTime, DateUtil.PATTERN_DATE) + "~" + DateUtil.convertDateToString(endTime, DateUtil.PATTERN_DATE) + ")");
		}

		String url = WxConstant.WX_PREFIX + "/checkin/getcheckindata?access_token="
				+ wxApiService.getAccessBasicsToken(appConfig.getDataMap().get("wxSSOAttendSecret"),"attendId");
		JSONObject response = WxHttpClientUtil.httpPost(url, wxAttend, null, null);

		if (response.getIntValue("errcode") == 0) {
			logger.debug(response.toJSONString());
			return JSONArray.fromObject(response.getJSONArray("checkindata"));
		} else {
			logger.error("无法获取打卡信息(" + DateUtil.convertDateToString(startTime, DateUtil.PATTERN_DATE) + "~" + DateUtil.convertDateToString(endTime, DateUtil.PATTERN_DATE) + ")"
					+ response.toJSONString());
			throw new Exception("无法获取打卡信息(" + DateUtil.convertDateToString(startTime, DateUtil.PATTERN_DATE) + "~" + DateUtil.convertDateToString(endTime, DateUtil.PATTERN_DATE) + ")"
					+ response.toJSONString());
		}
	}
}
