package com.landray.kmss.third.im.kk.service.spring;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.im.kk.model.ThirdImKKGzhDynamicinfo;
import com.landray.kmss.third.im.kk.service.IThirdImKKGzhDynamicinfoService;
import com.landray.kmss.third.im.kk.service.IThirdImKKWriteDynamicInfoService;
import com.landray.kmss.third.im.kk.util.KK5Util;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdImKKWriteDynamicInfoServiceImp implements IThirdImKKWriteDynamicInfoService {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ThirdImKKWriteDynamicInfoServiceImp.class);

	private IThirdImKKGzhDynamicinfoService thirdImKKGzhDynamicinfoService;

	public void setThirdImKKGzhDynamicinfoService(IThirdImKKGzhDynamicinfoService thirdImKKGzhDynamicinfoService) {
		this.thirdImKKGzhDynamicinfoService = thirdImKKGzhDynamicinfoService;
	}

	public IThirdImKKGzhDynamicinfoService getThirdImKKGzhDynamicinfoService() {
		return thirdImKKGzhDynamicinfoService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void writeDynamicInfo() {
		List<ThirdImKKGzhDynamicinfo> dynamicInfos = null;
		try {
			//每次只推送一周以内失败的信息
			HQLInfo hqlInfo = new HQLInfo();
			String day = DateUtil.convertDateToString(DateUtil.getNextDay(new Date(), -7), DateUtil.PATTERN_DATE);
			hqlInfo.setWhereBlock("thirdImKKGzhDynamicinfo.docCreateTime >= :day");
			hqlInfo.setParameter("day", DateUtil.getNextDay(new Date(), -7));
			dynamicInfos = thirdImKKGzhDynamicinfoService.findList(hqlInfo);
			if (dynamicInfos != null && dynamicInfos.size() > 0) {
				for (ThirdImKKGzhDynamicinfo dynamicinfo : dynamicInfos) {
					JSONObject returnJson = KK5Util.pushToServiceUser(getJsonDynamicInfo(dynamicinfo));
					if (returnJson.getInt("result") == 0) {
						thirdImKKGzhDynamicinfoService.delete(dynamicinfo);
					}
				}
			}
		} catch (Exception e) {
			log.debug("error", e);
		}
	}

	private static JSONObject getJsonDynamicInfo(ThirdImKKGzhDynamicinfo dynamicinfo) {
		JSONObject postData = new JSONObject();
		postData.accumulate("corp", dynamicinfo.getCorpId());
		postData.accumulate("service", dynamicinfo.getServiceCode());
		postData.accumulate("userType", 2);
		postData.accumulate("users", new JSONArray());
		postData.accumulate("type", 2);
		JSONObject mainMsg = new JSONObject();
		mainMsg.accumulate("title", dynamicinfo.getDocTitle());
		String docDescription = dynamicinfo.getDocDescription();
		if (StringUtil.isNull(docDescription)) {
			docDescription = dynamicinfo.getDocTitle();
		}
		mainMsg.accumulate("content", docDescription);
		mainMsg.accumulate("picurl", dynamicinfo.getPicUrl());
		mainMsg.accumulate("url", dynamicinfo.getDocUrl());
		mainMsg.accumulate("time", DateUtil.convertDateToString(new Date(), "yyyyMMddHHmmss"));
		postData.accumulate("mainMsg", mainMsg);
		postData.accumulate("attachMsgs", new JSONArray());
		return postData;
	}

}
