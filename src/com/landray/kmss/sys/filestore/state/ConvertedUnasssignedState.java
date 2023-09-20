package com.landray.kmss.sys.filestore.state;

import java.util.Date;

import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertLog;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

/**
 * 未分配的任务  taskUnAssigned
 * 
 *
 */
public class ConvertedUnasssignedState extends AbstractConvertQueueState {

	
	@Override
	public Boolean updateConvertQueue(Command receiveMessage,SysFileConvertQueue convertQueue,
			ConvertQueueInfo convertQueueInfo) throws Exception{
		SysFileConvertClient queueClient = getQueueClient(convertQueue, convertQueueInfo.getClientId());
		convertQueue.setFdClientId("");
		convertQueue.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
		convertQueue.setFdConvertNumber(convertQueue.getFdConvertNumber() + 1);
		SysFileConvertLog convertLog = new SysFileConvertLog();
		convertLog.setFdQueueId(convertQueue.getFdId());
		convertLog.setFdConvertKey(queueClient != null ? queueClient.getConverterFullKey() : "*");
		convertLog.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
		convertLog.setFdStatusTime(new Date());
		convertLog.setFdStatusInfo(convertQueueInfo.getStatusDesc());
		if (queueClient != null) {
			queueClient.subTaskConvertingNum();
		}
		
		updateConvertQueue(convertQueue);
		saveQueueClient(queueClient);
		saveConvertLog(convertLog);
		return true;
	}

	
}
