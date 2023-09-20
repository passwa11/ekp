package com.landray.kmss.third.ding.service.bean;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.request.OapiProcessListbyuseridRequest;
import com.dingtalk.api.response.OapiProcessListbyuseridResponse;
import com.dingtalk.api.response.OapiProcessListbyuseridResponse.ProcessTopVo;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.StringUtil;

/**
 * <P>获取钉钉流程模板</P>
 * @author 孙佳
 * 2018年12月18日
 */
public class DingProcessTreeService implements IXMLDataBean {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(DingProcessTreeService.class);
	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {

		List<Object> rtnList = new ArrayList<Object>();
		try {
			String userId = requestInfo.getParameter("userId");
			String url = DingConstant.DING_PREFIX
					+ "/topapi/process/listbyuserid"
					+ DingUtil.getDingAppKeyByEKPUserId("?", null);
			log.debug("钉钉接口：" + url);
			ThirdDingTalkClient client = new ThirdDingTalkClient(url);
			OapiProcessListbyuseridRequest oapiRequest = new OapiProcessListbyuseridRequest();
			if (StringUtil.isNotNull(userId)) {
				oapiRequest.setUserid(userId);
			}
			oapiRequest.setOffset(0L);
			oapiRequest.setSize(100L);
			OapiProcessListbyuseridResponse oapiResponse = client.execute(oapiRequest,
					DingUtils.getDingApiService().getAccessToken());
			if (oapiResponse.getErrcode() == 0) {
				List<ProcessTopVo> processList = oapiResponse.getResult().getProcessList();
				for (ProcessTopVo vo : processList) {
					Object[] object = new Object[2];
					object[0] = vo.getProcessCode();
					object[1] = vo.getName();
					rtnList.add(object);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rtnList;
	}

}
