package com.landray.kmss.sys.praise.message;

import com.landray.kmss.sys.cluster.interfaces.message.DefaultMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.model.SysClusterParameter;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoConfigService;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 集群消息接收器
 * 
 * @author tangyw
 * @date 2021-08-02
 */
public class SysPraiseInfoConfigMessageReceiver extends DefaultMessageReceiver {
	private static final Log logger = LogFactory.getLog(SysPraiseInfoConfigMessageReceiver.class);

	private ISysPraiseInfoConfigService sysPraiseInfoConfigService;

	public ISysPraiseInfoConfigService getConfigService() {
		if (sysPraiseInfoConfigService == null) {
			sysPraiseInfoConfigService = (ISysPraiseInfoConfigService) SpringBeanUtil
					.getBean("sysPraiseInfoConfigService");
		}
		return sysPraiseInfoConfigService;
	}

	@Override
	public void receiveMessage(IMessage message) throws Exception {
		logger.debug("node->" + SysClusterParameter.getInstance().getLocalServer().getFdId() + " received message = " + message);
		if (message instanceof SysPraiseConfigInfoUpdateMessage) {
			// 更新集群内其他节点
			SysPraiseConfigInfoUpdateMessage updateMessage = (SysPraiseConfigInfoUpdateMessage) message;
			getConfigService().updateToCluster(updateMessage.getBaseModel());
		}
	}
}
