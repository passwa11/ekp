package com.landray.kmss.third.ding.service.spring;

import com.dingtalk.api.request.OapiAttendanceListRecordRequest;
import com.dingtalk.api.response.OapiAttendanceListRecordResponse;
import com.google.common.util.concurrent.RateLimiter;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IThirdDingClockService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.DateUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ThirdDingClockServiceImp extends ExtendDataServiceImp implements IThirdDingClockService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingClockServiceImp.class);
	//考勤同步限速器（钉钉存在接口调用限速的情况）
	private RateLimiter attendRateLimiter;

	/**
	 * 限速器初始化
	 *
	 * @param
	 * @description:
	 * @return: void
	 * @author: wangjf
	 * @time: 2022/2/8 11:10 上午
	 */
	private synchronized void initAttendRateLimiter() {
		if (attendRateLimiter != null) {
			return;
		}
		logger.info(".....初始化考勤同步限速器开始........");
		//ekp默认限速30
		double fdRateLimiter = 30.0F;
		if (StringUtils.isNumeric(DingConfig.newInstance().getDingEnableRateLimitCount())) {
			fdRateLimiter = Double.parseDouble(DingConfig.newInstance().getDingEnableRateLimitCount());
		}
		attendRateLimiter = RateLimiter.create(fdRateLimiter);
		logger.info(".....初始限速为:{}，初始化考勤同步限速器结束........", Math.round(attendRateLimiter.getRate()));
	}

	/**
	 * @param
	 * @description: 获取令牌
	 * @return: void
	 * @author: wangjf
	 * @time: 2022/3/2 4:07 下午
	 */
	private void getAcquire() {
		initAttendRateLimiter();
		logger.info(".....考勤同步限速器被调用，当前速度为:{}", Math.round(attendRateLimiter.getRate()));
		attendRateLimiter.acquire();
	}

	/**
	 * 更新限速器的速率
	 *
	 * @param syncRateLimiter
	 * @description:
	 * @return: void
	 * @author: wangjf
	 * @time: 2022/2/8 12:52 下午
	 */
	@Override
	public void updateRate(String syncRateLimiter) {
		if (!StringUtils.isNumeric(syncRateLimiter)) {
			return;
		}
		if(attendRateLimiter == null){
			return;
		}
		String rate = String.valueOf(Math.round(attendRateLimiter.getRate()));
		if (!rate.equals(syncRateLimiter)) {
			logger.info(".....考勤同步限速器速度被调整，调整前:{}，调整后:{}", Math.round(attendRateLimiter.getRate()), syncRateLimiter);
			attendRateLimiter.setRate(Double.parseDouble(syncRateLimiter));
		}
	}

	@Override
	public JSONArray getUserDingClock(Date startTime, Date endTime,
									  List<String> idList) throws Exception {
		//获取配置调用令牌，根据配置限制调用速度
		this.getAcquire();

		DingApiService service = DingUtils.getDingApiService();
		String url = DingConstant.DING_PREFIX + "/attendance/listRecord"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		logger.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiAttendanceListRecordRequest request = new OapiAttendanceListRecordRequest();
		request.setCheckDateFrom(DateUtil.convertDateToString(startTime, DateUtil.PATTERN_DATE) + " 00:00:00");
		request.setCheckDateTo(
				DateUtil.convertDateToString(endTime, DateUtil.PATTERN_DATE)
						+ " 23:59:59");
		request.setUserIds(idList);
		OapiAttendanceListRecordResponse response = client.execute(request, service.getAccessToken());
		if (response.getErrcode() == 0) {
			logger.debug(response.getBody());
			return JSONArray.fromObject(response.getRecordresult());
		} else {
			logger.error("无法获取打卡信息(" + DateUtil.convertDateToString(startTime, DateUtil.PATTERN_DATE) + "~" + DateUtil.convertDateToString(endTime, DateUtil.PATTERN_DATE) + ")"
					+ response.getBody());
			throw new Exception("无法获取打卡信息(" + DateUtil.convertDateToString(startTime, DateUtil.PATTERN_DATE) + "~" + DateUtil.convertDateToString(endTime, DateUtil.PATTERN_DATE) + ")"
					+ response.getBody());
		}
	}

	@Override
	public Map getAttendConfig() {
		try {
			DingApiService dingService = DingUtils.getDingApiService();
			return dingService.attendInfo();
		} catch (Exception e) {
			logger.error("获取钉钉考勤配置信息失败:" + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}

}
