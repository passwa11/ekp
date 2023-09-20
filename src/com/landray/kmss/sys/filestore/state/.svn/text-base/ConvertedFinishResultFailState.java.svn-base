package com.landray.kmss.sys.filestore.state;

import java.util.Date;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.event.ConvertFinishEvent;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertLog;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.util.SpringBeanUtil;

/**
 *  转换完成 convertFinish  - 结果不为true
 *
 */
public class ConvertedFinishResultFailState extends AbstractConvertQueueState {
	private static final Log logger = LogFactory.getLog(ConvertedFinishResultFailState.class);
	
	/**
	 * 转换成功 convertFinish  - 不为true
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateConvertQueue(Command receiveMessage,SysFileConvertQueue convertQueue,
			ConvertQueueInfo convertQueueInfo) throws Exception  {
		
		Map<String, String> receiveInfos = null;
		if (receiveMessage != null) {
			receiveInfos = receiveMessage.getExtFields();
		} else {
			return false;
		}
		SysFileConvertClient queueClient = null;
		SysFileConvertLog convertLog =  null;
		ConvertFinishEvent finishEvent = new ConvertFinishEvent(convertQueue);
		finishEvent.setSuccess(false);
		String failureInfo = receiveInfos.get(ConvertState.CONVERT_FAILURE_INFO);
		String failureType = receiveInfos.get(ConvertState.CONVERT_FAILURE_TYPE);
		if (convertQueue.getFdConvertStatus() != SysFileConvertConstant.CONVERTERTOOLFAIL
				|| convertQueue.getFdConvertStatus() != SysFileConvertConstant.TIMEOUTFAILURE
				|| convertQueue.getFdConvertStatus() != SysFileConvertConstant.OOMFAILURE
				|| convertQueue.getFdConvertStatus() != SysFileConvertConstant.FAILURE) {
			convertLog = new SysFileConvertLog();
			queueClient = getQueueClient(convertQueue, convertQueueInfo.getClientId());
			convertLog.setFdStatusInfo(failureInfo);
			if (ConvertState.CONVERT_FAILURE_TOOL.equals(failureType)) {
				convertQueue.setFdConvertStatus(SysFileConvertConstant.CONVERTERTOOLFAIL);
				convertLog.setFdConvertStatus(SysFileConvertConstant.CONVERTERTOOLFAIL);
			} else if (ConvertState.CONVERT_FAILURE_TIME_OUT.equals(failureType)) {
				SysFileConvertClient client = getQueueLongClient();
				if(logger.isDebugEnabled()) {
					logger.debug("client:"+client);
					logger.debug("queueClient.getIsLongTask():"+queueClient.getIsLongTask());
					logger.debug("convertQueue.getFdIsLongQueue():"+convertQueue.getFdIsLongQueue());
				}
				
				//存在长转换配置，且不是在长转换服务转换的时候，增加长转换标识，再重新分发一次
				if(client!=null && !queueClient.getIsLongTask() && !convertQueue.getFdIsLongQueue().equals(true)){
					logger.debug("run long again");
					convertQueue.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
					convertQueue.setFdIsLongQueue(true);
				}else{
					logger.debug("not run long");
					convertQueue.setFdConvertStatus(SysFileConvertConstant.TIMEOUTFAILURE);
				}
				convertLog.setFdConvertStatus(SysFileConvertConstant.TIMEOUTFAILURE);
			} else if (ConvertState.CONVERT_FAILURE_OOM.equals(failureType)) {
				convertQueue.setFdConvertStatus(SysFileConvertConstant.OOMFAILURE);
				convertLog.setFdConvertStatus(SysFileConvertConstant.OOMFAILURE);
			} else {
				convertQueue.setFdConvertStatus(SysFileConvertConstant.FAILURE);
				convertLog.setFdConvertStatus(SysFileConvertConstant.FAILURE);
			}
			convertQueue.setFdIsFinish(true);
			convertQueue.setFdConvertNumber(convertQueue.getFdConvertNumber() + 1);
			convertLog.setFdQueueId(convertQueue.getFdId());
			convertLog
					.setFdConvertKey(queueClient != null ? queueClient.getConverterFullKey() : "*");
			convertLog.setFdStatusTime(new Date());
			if (queueClient != null) {
				queueClient.subTaskConvertingNum();
			}
		}
		
		SpringBeanUtil.getApplicationContext().publishEvent(finishEvent);
		updateConvertQueue(convertQueue);
		saveQueueClient(queueClient);
		saveConvertLog(convertLog);
		
		return true;
	}

}
