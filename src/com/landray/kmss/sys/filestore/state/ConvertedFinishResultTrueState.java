package com.landray.kmss.sys.filestore.state;

import java.util.Date;
import java.util.Map;

import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.event.ConvertFinishEvent;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertLog;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 
 * 转换完成  convertFinish  结果为true
 *
 */
public class ConvertedFinishResultTrueState extends AbstractConvertQueueState {
	
	/**
	 * 转换完成  convertFinish  结果为true
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
		SysFileConvertLog convertLog = null;
		SysFileConvertClient queueClient = null;
		ConvertFinishEvent finishEvent = new ConvertFinishEvent(convertQueue);
		finishEvent.setSuccess(true);
		if (convertQueue.getFdConvertStatus() != SysFileConvertConstant.SUCCESS) {
			queueClient = getQueueClient(convertQueue, convertQueueInfo.getClientId());
			convertQueue.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
			convertQueue.setFdIsFinish(Boolean.TRUE);
			convertQueue.setFdConvertNumber(convertQueue.getFdConvertNumber() + 1);
			convertLog = new SysFileConvertLog();
			convertLog.setFdQueueId(convertQueue.getFdId());
			convertLog.setFdConvertKey(queueClient != null ? queueClient.getConverterFullKey() : "*");
			convertLog.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
			convertLog.setFdStatusTime(new Date());
			convertLog.setFdStatusInfo(receiveInfos.get(ConvertState.CONVERT_STATE_CONVERT_ELAPSE_TIME));
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
