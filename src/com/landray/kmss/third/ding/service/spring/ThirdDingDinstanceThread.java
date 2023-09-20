package com.landray.kmss.third.ding.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.dingtalk.api.response.OapiProcessWorkrecordUpdateResponse;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.model.ThirdDingDinstance;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingDinstanceService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

public class ThirdDingDinstanceThread implements Runnable {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingDinstanceThread.class);

	private String instanceId;

	private boolean isAgree;

	public ThirdDingDinstanceThread(String instanceId, boolean isAgree) {
		this.instanceId = instanceId;
		this.isAgree = isAgree;
	}

	@Override
	public void run() {
		IThirdDingDinstanceService thirdDingDinstanceService = (IThirdDingDinstanceService) SpringBeanUtil
				.getBean("thirdDingDinstanceService");
		IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
				.getBean("omsRelationService");
		TransactionStatus status = null;
		try {
			Thread.sleep(2000);

			String token = DingUtils.getDingApiService().getAccessToken();
			String ekpUserId = null;

			status = TransactionUtils
					.beginNewTransaction();

			ThirdDingDinstance dinstance = (ThirdDingDinstance) thirdDingDinstanceService
					.findByPrimaryKey(instanceId, null, true);
			SysOrgElement person = dinstance.getFdCreator();
			if (person != null) {
				ekpUserId = omsRelationService
						.getDingUserIdByEkpUserId(person.getFdId());
			}
			if (StringUtil.isNotNull(dinstance.getFdInstanceId())) {
				OapiProcessWorkrecordUpdateResponse response = DingNotifyUtil
						.updateInstanceState(token,
								dinstance.getFdInstanceId(),
								dinstance.getFdTemplate()
										.getFdAgentId(),
								ekpUserId, isAgree);
				if (response.getErrcode() == 0) {
					logger.debug("更新实例成功！");
				} else {
					logger.error("更新所有的钉钉流程实例待办异常，详细错误："
							+ response.getBody());
				}
			}

			TransactionUtils.commit(status);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (status != null) {
				TransactionUtils.rollback(status);
			}
		}

	}

}
