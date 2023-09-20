package com.landray.kmss.sys.filestore.state;

import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertLog;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

import java.util.Date;

/**
 * 转换失败
 *
 */
public class ConvertedFailureState extends AbstractConvertQueueState {
	
	/**
	 * 转换成功  otherConvertFinishFailure
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateConvertQueue(Command receiveMessage,SysFileConvertQueue convertQueue,
			ConvertQueueInfo convertQueueInfo) throws Exception {
		SysFileConvertClient queueClient = getQueueClient(convertQueue, convertQueueInfo.getClientId());
		convertQueue.setFdClientId(queueClient != null ? queueClient.getFdId() : "*");
		convertQueue.setFdConvertStatus(SysFileConvertConstant.FAILURE);
		convertQueue.setFdConvertNumber(convertQueue.getFdConvertNumber() + 1);
		SysFileConvertLog convertLog = new SysFileConvertLog();
		convertLog.setFdQueueId(convertQueue.getFdId());
		convertLog.setFdConvertKey(queueClient != null ? queueClient.getConverterFullKey() : "*");
		convertLog.setFdConvertStatus(SysFileConvertConstant.FAILURE);
		convertLog.setFdStatusTime(new Date());
		convertLog.setFdStatusInfo(convertQueueInfo.getStatusDesc());
		if (queueClient != null) {
			queueClient.addTaskConvertingNum();
		}
		
		updateConvertQueue(convertQueue);
		saveQueueClient(queueClient);
		saveConvertLog(convertLog);
		return true;
	}

}
